package it.webred.rulengine.brick.loadDwh.load.docfa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.docfa.dto.DocfaPack;
import it.webred.rulengine.brick.loadDwh.load.docfa.tablebean.DocfaTableDC10;
import it.webred.rulengine.brick.loadDwh.load.docfa.tablebean.DocfaTableDM10;
import it.webred.rulengine.brick.loadDwh.load.docfa.tablebean.DocfaTableDOC10;
import it.webred.rulengine.brick.loadDwh.load.docfa.tablebean.DocfaTablePL10;
import it.webred.utils.DateFormat;
import it.webred.utils.StatusMonitor;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

public class ExportToTable {
	private static Logger log = Logger.getLogger(ExportToTable.class.getName());
	
	private Connection conn = null;
	
	public void analizzaDifferenze(DocfaPack dp) throws Exception {
		long start = System.currentTimeMillis();
		Statement stmt = null;
		conn = dp.getConn();
		
		try {
			int numeroRecordElaborati = 0;	
			String dataEstrazioneSt = dp.getDataEstrazione().toString();
			
			StatusMonitor.setStato(this, "Inizio analizza differenze.. TAB=" + dp.getNometabella());
			log.debug("Inizio analizza differenze");
			stmt = this.conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			List pk = new ArrayList();
			String nomeTab_t1 = dp.getNometabella() + "_T1";
			String nomeTab_t2 = dp.getNometabella() + "_T2";
			String sqlTrovaNomeKey = dp.getPkListQuery().replaceAll("TAB_LIKE", nomeTab_t1);
			log.debug(sqlTrovaNomeKey);
			
			ResultSet rTrova = stmt.executeQuery(sqlTrovaNomeKey);
			int i = 0;
			while (rTrova.next()) {
				pk.add(rTrova.getString(5));
				log.debug("Trovata Pk della tabella " + nomeTab_t1 + ": " + pk.get(i));
				i++;
			}
			
			rTrova.close();
			stmt.cancel();
			
			if (pk.size() > 0) {				
				StatusMonitor.setStato(this, "Analizzo differenze con PK. TAB=" + dp.getNometabella());
				log.debug("Analizzo differenze con PK");
				String sqlNew = "select " + dp.getNometabella() + "_T1.* from " + 
								nomeTab_t1 + " left join " + nomeTab_t2 + 
								concatenaKeyforON(pk, dp.getNometabella(), "_T2");
				
				String sqlLost = "select " + concatenaKey(pk, dp.getNometabella() + "_T2") + 
								 " from " + nomeTab_t1 + " right join " + nomeTab_t2 + 
								 concatenaKeyforON(pk, dp.getNometabella(), "_T1");
				
				String sqlMod = "SELECT T1.* FROM " + nomeTab_t1 + " T1, " + nomeTab_t2 + 
								" T2 " + concatenaKeyforWHERE(pk) + " AND T1.HASH <> T2.HASH";
				
				// INIZIO RICERCA E SCRITTURA DEI NUOVI RECORD
				StatusMonitor.setStato(this, "Ricarca record nuovi:" + sqlNew + ". TAB=" + dp.getNometabella());
				log.debug("Ricarca record nuovi:\n" + sqlNew);
				long intermedio = System.currentTimeMillis();
				//ResultSet rNew = stmt.executeQuery(sqlNew);
				//numeroRecordElaborati = ciclaResultSet(rNew, "new");
				numeroRecordElaborati = ciclaResultSet(stmt,sqlNew,dp.getPrefissotabella());
				//rNew.close();
				intermedio = System.currentTimeMillis() - intermedio;
				StatusMonitor.setStato(this, "Tempo di elaborazione per NEW: " + intermedio + "  ----- Numero di record: " + numeroRecordElaborati + ". TAB=" + dp.getNometabella());
				log.debug("Tempo di elaborazione per NEW: " + intermedio + "  ----- Numero di record: " + numeroRecordElaborati);
				intermedio = System.currentTimeMillis();
				numeroRecordElaborati = 0;
				
				stmt.cancel();
				
				// INIZIO RICERCA E SCRITTURA DEI RECORD MODIFICATI
				log.debug("Ricarca record mod:\n" + sqlMod);
				//ResultSet rMod = stmt.executeQuery(sqlMod);
				//numeroRecordElaborati = ciclaResultSet(rMod, "mod");
				numeroRecordElaborati = ciclaResultSet(stmt,sqlMod,dp.getPrefissotabella());
				//rMod.close();
				intermedio = System.currentTimeMillis() - intermedio;
				StatusMonitor.setStato(this, "Tempo di elaborazione per MOD: " + intermedio + "  ----- Numero di record: " + numeroRecordElaborati + ". TAB=" + dp.getNometabella());
				log.debug("Tempo di elaborazione per MOD: " + intermedio + "  ----- Numero di record: " + numeroRecordElaborati);
				intermedio = System.currentTimeMillis();
				numeroRecordElaborati = 0;
				
				stmt.cancel();
				
				// INIZIO RICERCA E SCRITTURA DEI RECORD ELIMINATI
				log.debug("Ricarca record lost:\n" + sqlLost);
				//ResultSet rLost = stmt.executeQuery(sqlLost);
				//numeroRecordElaborati = ciclaResultSet(rLost, "del");
				numeroRecordElaborati = ciclaResultSet(stmt,sqlLost,dp.getPrefissotabella());
				//rLost.close();
				intermedio = System.currentTimeMillis() - intermedio;
				StatusMonitor.setStato(this, "Tempo di elaborazione per LOST: " + intermedio + "  ----- Numero di record: " + numeroRecordElaborati + ". TAB=" + dp.getNometabella());
				log.debug("Tempo di elaborazione per LOST: " + intermedio + "  ----- Numero di record: " + numeroRecordElaborati);
				numeroRecordElaborati = 0;
			}
			else {
				StatusMonitor.setStato(this, "Analizzo diff  SENZA PK. TAB=" + dp.getNometabella());
				log.debug("Analizzo diff  SENZA PK");
				long intermedio = System.currentTimeMillis();
				String sqlAll = "SELECT * FROM " + nomeTab_t1;
				log.debug("Ricarca record all:\n" + sqlAll);
				stmt = this.conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				//ResultSet rAll = stmt.executeQuery(sqlAll);
				numeroRecordElaborati = ciclaResultSet(stmt,sqlAll,dp.getPrefissotabella());
				intermedio = System.currentTimeMillis() - intermedio;
				log.debug("Tempo di elaborazione per ALL: " + intermedio + "  ----- Numero di record: " + numeroRecordElaborati);
				numeroRecordElaborati = 0;

				stmt.cancel();
			}
			
			start = System.currentTimeMillis() - start;
			log.debug("Tempo totale export:" + start);
			
		}catch (Exception e)		{
			log.error("Eccezione: "+e.getMessage());
			throw e;
		}finally		{
			if (stmt != null)
				stmt.close();
			
			StatusMonitor.setStato(this, null);
		}
	}
	
	
	
	
	/**
	 * Concatena una lista di chiavi di una tabella per la ON di una query 
	 * @param chiavi Lista dell chiavi
	 * @param nomeTabella Nome della tabella 
	 * @param suffisso Suffisso della tabella
	 * @return La stringa con le chiavi concatenat
	 */
	private String concatenaKeyforON(List chiavi, String nomeTabella, String suffisso) {
		String stringaON = " on (";
		String stringaWHERE = " WHERE ";
		String nomeChiave = "";
		
		for (int i = 0; i < chiavi.size(); i++)	{
			nomeChiave = chiavi.get(i).toString();
			
			stringaON = stringaON + nomeTabella + "_T1." + nomeChiave + "=" + 
						nomeTabella + "_T2." + nomeChiave;
			
			stringaWHERE = stringaWHERE + nomeTabella + suffisso + "." + nomeChiave + " is null";
			if (i < chiavi.size() - 1)	{
				stringaON = stringaON + " AND ";
				stringaWHERE = stringaWHERE + " AND ";
			}
			else {
				stringaON = stringaON + ")";
			}
		}
		
		return stringaON + stringaWHERE;
	}
	
	
	
	/**
	 * Concatena una lista di chiavi di una tabella
	 * @param chiavi Lista di chiavi 
	 * @param nomeTabella Nome della tabella
	 * @return La stringa con le chiavi concatenate
	 */
	private String concatenaKey(List chiavi, String nomeTabella)	{
		String stringa = "";
		for (int i = 0; i < chiavi.size(); i++)		{
			
			stringa = stringa + nomeTabella + "." + chiavi.get(i);
			
			if (i < chiavi.size() - 1)
				stringa = stringa + " ,";
			else
				stringa = stringa + "";
		}
		
		return stringa;
	}
	
	
	
	
	
	/**
	 * Concatena una lista di chiavi di una tabella per la WHERE di una query 
	 * @param chiavi Lista dell chiavi
	 * @return La stringa con le chiavi concatenat
	 */
	private String concatenaKeyforWHERE(List chiavi)	{
		String stringaON = "WHERE ";
		String nomeChiave = "";
		
		for (int i = 0; i < chiavi.size(); i++)		{
			nomeChiave = chiavi.get(i).toString();
			stringaON = stringaON + "T1." + nomeChiave + "=" + "T2." + nomeChiave;
			
			if (i < chiavi.size() - 1)
				stringaON = stringaON + " AND ";
		}
		
		return stringaON;
	}
	
	
	
	
	private int ciclaResultSet(Statement stmt, String query, String prefissoTabella) throws Exception	{
		
		int n = 0;
		ResultSet rs = stmt.executeQuery(query);
		log.debug(prefissoTabella);
		
		if(prefissoTabella.equals("DOC")) {
			n = this.scriviDocfaDOC(rs);
		}
		else if(prefissoTabella.equals("DC")) {
			n = this.scriviDocfaDC(rs);
		}
		else if(prefissoTabella.equals("PL")) {
			n = this.scriviDocfaPL(rs);
		}
		else if(prefissoTabella.equals("DM")) {
			n = this.scriviDocfaDM(rs);
		}
		
		return n;
	}
	
	
	
	private int scriviDocfaDOC(ResultSet rs) throws Exception {
		List<DocfaTableDOC10> ll = new ArrayList<DocfaTableDOC10>();

		while (rs.next()) {
			DocfaTableDOC10 t = new DocfaTableDOC10(Calendar.getInstance().getTime(),null,new Long(0));
			t.setCodEnte(rs.getString("COD_ENTE"));
			
			String data = rs.getString("DATA"); //aaammgg
			t.setData(DateFormat.stringToDate(data, "yyyyMMdd"));
			t.setProtocollo(rs.getString("PROTOCOLLO"));
			t.setTipoRecord(rs.getString("TIPO_RECORD"));
			t.setRigaDettaglio(rs.getString("RIGA_DETTAGLIO"));
			
			ll.add(t);
		}
		
		rs.close();
		
		//insert record
		for(DocfaTableDOC10 t: ll) {
			PreparedStatement ps = this.conn.prepareStatement(DocfaTableDOC10.INSERT_RECORD);
			int j = 0;
			
			if(t.getCodEnte() != null) 
				ps.setString(++j, t.getCodEnte());	
			else 
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getData() != null) 
				ps.setDate(++j, new java.sql.Date(t.getData().getTime()));	
			else 
				ps.setNull(++j, Types.DATE);
			
			if(t.getProtocollo() != null) 
				ps.setString(++j, t.getProtocollo());	
			else 
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getTipoRecord() != null) 
				ps.setString(++j, t.getTipoRecord());	
			else 
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getRigaDettaglio() != null) 
				ps.setString(++j, t.getRigaDettaglio());	
			else 
				ps.setNull(++j, Types.VARCHAR);
			
			
			/*campi comuni*/
			if(t.getReDataInizioVal() != null)
				ps.setDate(++j, new java.sql.Date(t.getReDataInizioVal().getTime()));	
			else
				ps.setNull(++j, Types.DATE);
			
			if(t.getReDataFineVal() != null) 
				ps.setDate(++j, new java.sql.Date(t.getReDataFineVal().getTime()));	
			else 
				ps.setNull(++j, Types.DATE);
			
			if(t.getReFlagElaborato() != null) 
				ps.setLong(++j, t.getReFlagElaborato());	
			else 
				ps.setNull(++j, Types.NUMERIC);
			
			ps.executeUpdate();
			ps.close();
		}
		
		this.conn.commit();
		
		return ll.size();
	}
	
	private int scriviDocfaDC(ResultSet rs) throws Exception {
		List<DocfaTableDC10> ll = new ArrayList<DocfaTableDC10>();
		
		while (rs.next()) {			
			DocfaTableDC10 t = new DocfaTableDC10(Calendar.getInstance().getTime(),null,new Long(0));
			
			String data = rs.getString("DATA"); //aaammgg
			t.setData(DateFormat.stringToDate(data, "yyyyMMdd"));
			
			t.setCodiceEnte(rs.getString("CODICE_ENTE"));
			t.setSezione(rs.getString("SEZIONE"));
			
			String idImmo = rs.getString("IDENTIFICATIVO_IMMO");
			if(idImmo != null)
				t.setIdentificativoImmo(new Long(idImmo));
			
			t.setProtocolloReg(rs.getString("PROTOCOLLO_REG"));
			
			String dataReg = rs.getString("DATA_REG");
			if(dataReg != null)
				t.setDataReg(DateFormat.stringToDate(dataReg, "ddMMyyyy"));
			
			t.setZona(rs.getString("ZONA"));
			t.setCategoria(rs.getString("CATEGORIA"));
			t.setClasse(rs.getString("CLASSE"));
			t.setConsistenza(rs.getString("CONSISTENZA"));
			
			String superf = rs.getString("SUPERFICIE");
			if(superf != null)
				t.setSuperficie(new Long(superf));
			
			t.setRenditaEuro(rs.getString("RENDITA_EURO"));
			t.setPartitaSpeciale(rs.getString("PARTITA_SPECIALE"));
			t.setLotto(rs.getString("LOTTO"));
			t.setEdificio(rs.getString("EDIFICIO"));
			t.setScala(rs.getString("SCALA"));
			t.setInternoUno(rs.getString("INTERNO_UNO"));
			t.setInternoDue(rs.getString("INTERNO_DUE"));
			t.setPianoUno(rs.getString("PIANO_UNO"));
			t.setPianoDue(rs.getString("PIANO_DUE"));
			t.setPianoTre(rs.getString("PIANO_TRE"));
			t.setPianoQuattro(rs.getString("PIANO_QUATRO"));
			t.setSezioneUrbana(rs.getString("SEZIONE_URBANA"));
			t.setFoglio(rs.getString("FOGLIO"));
			t.setNumero(rs.getString("NUMERO"));
			
			String denom = rs.getString("DENOMINATORE");
			if(denom != null)
				t.setDenominatore(new Long(denom));
			
			t.setSubalterno(rs.getString("SUBALTERNO"));
			t.setEdificabilita(rs.getString("EDIFICABILITA"));
			t.setPresenzaGraffati(rs.getString("PRESENZA_GRAFFATI"));
			t.setToponimo(rs.getString("TOPONIMO"));
			t.setIndirizzo(rs.getString("INDIRIZZO"));
			t.setCivicoUno(rs.getString("CIVICO_UNO"));
			t.setCivicoDue(rs.getString("CIVICO_DUE"));
			t.setCivicoTre(rs.getString("CIVICO_TRE"));
			t.setUlterioriIndirizzi(rs.getString("ULTERIORI_INDIRIZZI"));
			
			ll.add(t);
			
		}
		
		rs.close();
		
		
		for(DocfaTableDC10 t: ll) {
			PreparedStatement ps = this.conn.prepareStatement(DocfaTableDC10.INSERT_RECORD);
			int j = 0;
			
			if(t.getData() != null)
				ps.setDate(++j, new java.sql.Date(t.getData().getTime()));	
			else
				ps.setNull(++j, Types.DATE);

			if(t.getCodiceEnte() != null)
				ps.setString(++j, t.getCodiceEnte());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getSezione() != null)
				ps.setString(++j, t.getSezione());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getIdentificativoImmo() != null)
				ps.setLong(++j, t.getIdentificativoImmo());
			else
				ps.setNull(++j, Types.NUMERIC);
			
			if(t.getProtocolloReg() != null)
				ps.setString(++j, t.getProtocolloReg());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getDataReg() != null)
				ps.setDate(++j, new java.sql.Date(t.getDataReg().getTime()));	
			else
				ps.setNull(++j, Types.DATE);
			
			if(t.getZona() != null)
				ps.setString(++j, t.getZona());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getCategoria() != null)
				ps.setString(++j, t.getCategoria());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getClasse() != null)
				ps.setString(++j, t.getClasse());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getConsistenza() != null)
				ps.setString(++j, t.getConsistenza());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getSuperficie() != null)
				ps.setLong(++j, t.getSuperficie());
			else
				ps.setNull(++j, Types.NUMERIC);
			
			if(t.getRenditaEuro() != null)
				ps.setString(++j, t.getRenditaEuro());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getPartitaSpeciale() != null)
				ps.setString(++j, t.getPartitaSpeciale());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getLotto() != null)
				ps.setString(++j, t.getLotto());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getEdificio() != null)
				ps.setString(++j, t.getEdificio());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getScala() != null)
				ps.setString(++j, t.getScala());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getInternoUno() != null)
				ps.setString(++j, t.getInternoUno());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getInternoDue() != null)
				ps.setString(++j, t.getInternoDue());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getPianoUno() != null)
				ps.setString(++j, t.getPianoUno());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getPianoDue() != null)
				ps.setString(++j, t.getPianoDue());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getPianoTre() != null)
				ps.setString(++j, t.getPianoTre());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getPianoQuattro() != null)
				ps.setString(++j, t.getPianoQuattro());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getSezioneUrbana() != null)
				ps.setString(++j, t.getSezioneUrbana());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getFoglio() != null)
				ps.setString(++j, t.getFoglio());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getNumero() != null)
				ps.setString(++j, t.getNumero());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getDenominatore() != null)
				ps.setLong(++j, t.getDenominatore());
			else
				ps.setNull(++j, Types.NUMERIC);
			
			if(t.getSubalterno() != null)
				ps.setString(++j, t.getSubalterno());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getEdificabilita() != null)
				ps.setString(++j, t.getEdificabilita());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getPresenzaGraffati() != null)
				ps.setString(++j, t.getPresenzaGraffati());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getToponimo() != null)
				ps.setString(++j, t.getToponimo());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getIndirizzo() != null)
				ps.setString(++j, t.getIndirizzo());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getCivicoUno() != null)
				ps.setString(++j, t.getCivicoUno());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getCivicoDue() != null)
				ps.setString(++j, t.getCivicoDue());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getCivicoTre() != null)
				ps.setString(++j, t.getCivicoTre());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getUlterioriIndirizzi() != null)
				ps.setString(++j, t.getUlterioriIndirizzi());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			
			/*campi comuni*/
			if(t.getReDataInizioVal() != null) 
				ps.setDate(++j, new java.sql.Date(t.getReDataInizioVal().getTime()));	
			else 
				ps.setNull(++j, Types.DATE);
			
			if(t.getReDataFineVal() != null) 
				ps.setDate(++j, new java.sql.Date(t.getReDataFineVal().getTime()));	
			else 
				ps.setNull(++j, Types.DATE);
			
			if(t.getReFlagElaborato() != null) 
				ps.setLong(++j, t.getReFlagElaborato());	
			else 
				ps.setNull(++j, Types.NUMERIC);

			
			ps.executeUpdate();
			ps.close();
		}
		
		this.conn.commit();
		
		return ll.size();
	}
	
	private int scriviDocfaDM(ResultSet rs) throws Exception {
		List<DocfaTableDM10> ll = new ArrayList<DocfaTableDM10>();
		
		while (rs.next()) {
			DocfaTableDM10 t = new DocfaTableDM10(Calendar.getInstance().getTime(),null,new Long(0));
			
			String data = rs.getString("DATA"); //aaammgg
			t.setData(DateFormat.stringToDate(data, "yyyyMMdd"));

			t.setCodEnte(rs.getString("COD_ENTE"));
			t.setSezione(rs.getString("SEZIONE"));
			
			String idImmo = rs.getString("IDENTIFICATIVO_IMMO");
			if(idImmo != null)
				t.setIdentificativoImmo(new Long(idImmo));
			
			t.setProtocolloReg(rs.getString("PROTOCOLLO_REG"));
			
			String dataReg = rs.getString("DATA_REG");
			if(dataReg != null)
				t.setDataReg(DateFormat.stringToDate(dataReg, "ddMMyyyy"));
			
			String prog = rs.getString("PROGRESSIVO_POL");
			if(prog != null)
				t.setProgressivoPol(new Long(prog));
			
			String superf = rs.getString("SUPERFICIE");
			if(superf != null)
				t.setSuperficie(new Long(superf));
			
			t.setAmbiente(rs.getString("AMBIENTE"));
			
			String alt = rs.getString("ALTEZZA");
			if(alt != null)
				t.setAltezza(new Long(alt));
			
			String altm = rs.getString("ALTEZZA_MAX");
			if(altm != null)
				t.setAltezzaMax(new Long(altm));
			
			ll.add(t);
		}
		
		rs.close();
		
		//insert record
		for(DocfaTableDM10 t: ll) {
			PreparedStatement ps = this.conn.prepareStatement(DocfaTableDM10.INSERT_RECORD);
			int j = 0;
			
			if(t.getData() != null) 
				ps.setDate(++j, new java.sql.Date(t.getData().getTime()));	
			else 
				ps.setNull(++j, Types.DATE);
			
			if(t.getCodEnte() != null) 
				ps.setString(++j, t.getCodEnte());	
			else 
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getSezione() != null)
				ps.setString(++j, t.getSezione());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getIdentificativoImmo() != null)
				ps.setLong(++j, t.getIdentificativoImmo());
			else
				ps.setNull(++j, Types.NUMERIC);
			
			if(t.getProtocolloReg() != null)
				ps.setString(++j, t.getProtocolloReg());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getDataReg() != null)
				ps.setDate(++j, new java.sql.Date(t.getDataReg().getTime()));	
			else
				ps.setNull(++j, Types.DATE);
			
			if(t.getProgressivoPol() != null)
				ps.setLong(++j, t.getProgressivoPol());
			else
				ps.setNull(++j, Types.NUMERIC);
			
			if(t.getSuperficie() != null)
				ps.setLong(++j, t.getSuperficie());
			else
				ps.setNull(++j, Types.NUMERIC);
			
			if(t.getAmbiente() != null)
				ps.setString(++j, t.getAmbiente());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getAltezza() != null)
				ps.setLong(++j, t.getAltezza());
			else
				ps.setNull(++j, Types.NUMERIC);
			
			if(t.getAltezzaMax() != null)
				ps.setLong(++j, t.getAltezzaMax());
			else
				ps.setNull(++j, Types.NUMERIC);
			
			
			/*campi comuni*/
			if(t.getReDataInizioVal() != null)
				ps.setDate(++j, new java.sql.Date(t.getReDataInizioVal().getTime()));	
			else
				ps.setNull(++j, Types.DATE);
			
			if(t.getReDataFineVal() != null) 
				ps.setDate(++j, new java.sql.Date(t.getReDataFineVal().getTime()));	
			else 
				ps.setNull(++j, Types.DATE);
			
			if(t.getReFlagElaborato() != null) 
				ps.setLong(++j, t.getReFlagElaborato());	
			else 
				ps.setNull(++j, Types.NUMERIC);
			
			ps.executeUpdate();
			ps.close();
		}
		
		this.conn.commit();
		
		return ll.size();
	}
	
	private int scriviDocfaPL(ResultSet rs) throws Exception {
		List<DocfaTablePL10> ll = new ArrayList<DocfaTablePL10>();
		
		while (rs.next()) {
			DocfaTablePL10 t = new DocfaTablePL10(Calendar.getInstance().getTime(),null,new Long(0));
			
			String data = rs.getString("DATA"); //aaammgg
			if(data != null &&  !"".equals(data.trim()))
				t.setData(DateFormat.stringToDate(data, "yyyyMMdd"));

			t.setCodEnte(rs.getString("COD_ENTE"));
			t.setSezione(rs.getString("SEZIONE"));
			
			String idImmo = rs.getString("IDENTIFICATIVO_IMMO");
			if(idImmo != null &&  !"".equals(idImmo.trim()))
				t.setIdentificativoImmo(new Long(idImmo));
			
			t.setProtocolloReg(rs.getString("PROTOCOLLO_REG"));
			
			String dataReg = rs.getString("DATA_REG");
			if(dataReg != null && !"".equals(dataReg.trim()))
				t.setDataReg(DateFormat.stringToDate(dataReg, "ddMMyyyy"));
			
			t.setNomePlan(rs.getString("NOME_PLAN"));	
			
			String prog = rs.getString("PROGRESSIVO");
			if(prog != null &&  !"".equals(prog.trim()))
				t.setProgressivo(new Long(prog));
			
			String formato = rs.getString("FORMATO");
			if(formato != null && !"".equals(formato.trim()))
				t.setFormato(new Long(formato));
			
			String scala = rs.getString("SCALA"); 
			if(scala != null &&  !"".equals(scala.trim()))
				t.setScala(new Long(scala));
			
			ll.add(t);
		}
		
		rs.close();
		
		//insert record
		for(DocfaTablePL10 t: ll) {
			PreparedStatement ps = this.conn.prepareStatement(DocfaTablePL10.INSERT_RECORD);
			int j = 0;
			
			if(t.getData() != null) 
				ps.setDate(++j, new java.sql.Date(t.getData().getTime()));	
			else 
				ps.setNull(++j, Types.DATE);
			
			if(t.getCodEnte() != null) 
				ps.setString(++j, t.getCodEnte());	
			else 
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getSezione() != null)
				ps.setString(++j, t.getSezione());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getIdentificativoImmo() != null)
				ps.setLong(++j, t.getIdentificativoImmo());
			else
				ps.setNull(++j, Types.NUMERIC);
			
			if(t.getProtocolloReg() != null)
				ps.setString(++j, t.getProtocolloReg());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getDataReg() != null)
				ps.setDate(++j, new java.sql.Date(t.getDataReg().getTime()));	
			else
				ps.setNull(++j, Types.DATE);
			
			if(t.getNomePlan() != null)
				ps.setString(++j, t.getNomePlan());
			else
				ps.setNull(++j, Types.VARCHAR);
			
			if(t.getProgressivo() != null)
				ps.setLong(++j, t.getProgressivo());
			else
				ps.setNull(++j, Types.NUMERIC);
			
			if(t.getFormato() != null)
				ps.setLong(++j, t.getFormato());
			else
				ps.setNull(++j, Types.NUMERIC);
			
			if(t.getScala() != null)
				ps.setLong(++j, t.getScala());
			else
				ps.setNull(++j, Types.NUMERIC);
			
			
			
			/*campi comuni*/
			if(t.getReDataInizioVal() != null)
				ps.setDate(++j, new java.sql.Date(t.getReDataInizioVal().getTime()));	
			else
				ps.setNull(++j, Types.DATE);
			
			if(t.getReDataFineVal() != null) 
				ps.setDate(++j, new java.sql.Date(t.getReDataFineVal().getTime()));	
			else 
				ps.setNull(++j, Types.DATE);
			
			if(t.getReFlagElaborato() != null) 
				ps.setLong(++j, t.getReFlagElaborato());	
			else 
				ps.setNull(++j, Types.NUMERIC);
			
			ps.executeUpdate();
			ps.close();
		}
		
		this.conn.commit();

		
		return ll.size();
	}
	
	


}
