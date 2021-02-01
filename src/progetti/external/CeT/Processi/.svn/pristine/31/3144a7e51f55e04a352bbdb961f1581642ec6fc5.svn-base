package it.webred.ct.proc.ario.aggregatori;

import it.webred.ct.data.model.indice.SitSoggettoTotale;
import it.webred.ct.proc.ario.GestioneStringheVie;
//import it.webred.ct.proc.ario.bean.SitViaTotale;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.utils.SmithWatermanGotohNibbioWindowedAffine;
import it.webred.utils.GenericTuples;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;


public class AggregatoreVIe extends TipoAggregatore
{
	
	String SEPARATOR = "####";
	private boolean associazione = false;
	private boolean sostituzioneEleCorr = false;
	private static final Logger	log	= Logger.getLogger(AggregatoreVIe.class.getName());
	private Connection conn = null;
	private Object hackLastIdVia = null;		//hack per memorizzare l'id via quando elabora trova valorizzato lvieAltre con una sola via
	QueryRunner run = new QueryRunner();
	
	private long totNumRecAggiornati = 0;
	
	public void aggrega(String codEnte, HashParametriConfBean paramConfBean) throws AggregatoreException {
		
		ResultSet lVieTotDaAggr = null;
		try{			
			
			log.info("INIZIO Aggregazione dati di rifererimento");
			
			ArrayList<Object[]> paramFonteProgr = (ArrayList<Object[]>) run.query(conn,getProperty("SQL_FONTE_PROGRESSIVO_RIF_VIE"), new ArrayListHandler());
			if (paramFonteProgr == null)
				log.info("!!!!! ERRORE: FONTE DI RIFERIMENTO VIE NON TROVATA !!!!! continuo...");
			else{
				run.update(conn,getProperty("SQL_INSERISCI_VIA_RIFERIMENTO"),paramFonteProgr.get(0));
				
				this.rimuoviDuplicatiRifUnico(conn,paramFonteProgr.get(0));
				
				run.update(conn,getProperty("SQL_AGGIORNA_DATI_DEFAULT_VIA_TOT"),paramFonteProgr.get(0));							
			}
			conn.commit();
			
			log.info("FINE aggregazione dati di riferimento");
			
			//creo o aggiorno l'indice testuale di unico
			this.createTextIndex();
			
			//Effettuo caricamento viario Unico
			//HashMap<String, BeanViario> viarioUnico = this.caricaViarioUnico();		
						
			//Effettuo il caricamento della lista dei TOTALI da aggregare			
			lVieTotDaAggr = this.caricaDatiDaAggregare();
			
			
			log.info("Inizio Elaborazione controllo associabilità Vie in UNICO");						
			//this.elabora(conn,viarioUnico,lVieTotDaAggr);
			this.elabora(null,lVieTotDaAggr);
			log.info("Fine Elaborazione controllo associabilità Vie in UNICO");
						
			//Pulizia tabella UNICO (cancello tutti i record non collegati a totale e Validato <> 1)
			log.info("Inizio cancellazione elementi in UNICO");
			this.pulisciUnico();
			log.info("Fine cancellazione elementi in UNICO");									
			
		}catch (Exception e){
			
			try{
				conn.rollback();
			}catch(Exception er){
				log.error("ERRORE IN ROLLBACK AGGREGAZIONE VIE");
			}
			
			
			log.error("Errore:Aggregatore=" + e.getMessage(),e);
			AggregatoreException ea = new AggregatoreException(e.getMessage());
			throw ea;
		}
		finally{
			try {
				DbUtils.close(lVieTotDaAggr);
			} catch (SQLException e) {
				log.error("errore chiusura lVieTotDaAggr", e);
			}
			
			
		}

	}
	
	
	private boolean rimuoviDuplicatiRifUnico(Connection conn,Object[] paramFonteProgr) throws Exception {
		boolean elaborato = true;
		PreparedStatement st2,st3,st4;
		ResultSet rs = null;
		
		String sql =
				"select decode(u.sedime,null,' ',u.sedime) sedime, " +
				"decode(u.indirizzo,null,'-',u.indirizzo) indirizzo, " +
				"NVL(u.codice_via,'-') codice_via " +
				"from sit_via_unico u, " +
				"  (select distinct sedime, indirizzo, codice_via from sit_via_totale " +
				"   where fk_ente_sorgente='"+paramFonteProgr[0].toString()+"' " +
				"   and prog_es='"+paramFonteProgr[1].toString()+"' " +
				"   and fk_via is null) t " +
				"where decode(t.sedime,null,' ',t.sedime) = decode(u.sedime,null,' ',u.sedime) " +
				"AND decode(t.indirizzo,null,'-',t.indirizzo) = decode(u.indirizzo,null,'-',u.indirizzo) " +
				"AND NVL(t.codice_via,'-')=NVL(u.codice_via,'-') " +
				"group by u.sedime, u.indirizzo, u.codice_via having count(id_via)>1 ";
		
		String sql2 = 
				"select id_via from sit_via_unico u " +
				"where decode(u.sedime,null,' ',u.sedime) = ? and decode(u.indirizzo,null,'-',u.indirizzo)=? and NVL(u.codice_via,'-') = ? " +
				"order by u.dt_ins";
		
		String sql3 = 
				"update SIT_VIA_TOTALE t set t.FK_VIA = NULL , t.NOTE = NULL, t.RATING = NULL, t.REL_DESCR = NULL " +
				"WHERE t.fk_via=? and t.REL_DESCR = 'NATIVA' and (t.fk_ente_sorgente <> ?  or t.prog_es <> ? ) ";
		
		String sql4 = getProperty("SQL_PULISCI_VIA_UNICO") + " AND id_via = ? "; 
		
		st2 = conn.prepareStatement(sql2);
		st3 = conn.prepareStatement(sql3);
		st4 = conn.prepareStatement(sql4);
		
		try{
		ArrayList<Object[]> lstDuplicati =  (ArrayList<Object[]>) run.query(conn,sql,new ArrayListHandler());
		
		for(Object[] dup : lstDuplicati){
			
			String sedime = (String)dup[0];
			String indirizzo = (String)dup[1];
			String codVia = (String)dup[2];
			
			st2.setString(1,sedime);
			st2.setString(2,indirizzo);
			st2.setString(3,codVia);
			
			rs = st2.executeQuery();
			
			while(rs.next()){
				String fkVia = rs.getString("ID_VIA");
				
				//Pulisco la Unico (Se è nuova viene eliminata)
				try{
					st4.setString(1, fkVia);
					st4.executeUpdate();
				}catch(Exception e){
					log.debug("via non eliminata da unico:"+fkVia);
				}
				
				//Resetto il record nativo (non di riferimento) della totale
				st3.setString(1, fkVia);
				st3.setBigDecimal(2, (BigDecimal)paramFonteProgr[0]);
				st3.setBigDecimal(3, (BigDecimal)paramFonteProgr[1]);
				st3.executeUpdate();
				
			
				
			}
			
		}
		
		}catch(Throwable e){
			throw new Exception("Errore in Rimozione Duplicati Vie Unico (Aggregazione fonte di riferimento) "+e.getMessage());
		}finally{
			DbUtils.close(rs);
			DbUtils.close(st2);DbUtils.close(st3);DbUtils.close(st4);
			
		}
		
		return elaborato;
	}

	
	/**
	 * Controlla che due indirizzi siano simili
	 * Veridicando che uno sia il puntato dell'altro
	 * Per esempio Giuseppe Garibaldi con G. Garibaldi
	 * @param vu
	 * @param vnr
	 * @return
	 */
	private boolean associaSedimeDescrizionePuntata(BeanViario vu, BeanViario vnr) throws Exception {	
		try {
				/*if(vu.getSedime()==null || (vu.getSedime()!=null && vnr.getSedime()!=null && !vu.getSedime().equals(vnr.getSedime()))){
					return false;
				}*/		
				
				return GestioneStringheVie.ugualiSenzaPuntato(vu.getIndirizzo(),vnr.getIndirizzo());
		} catch (Exception e) {    		
			throw new Exception("Errore in confronto Descrizione Puntata "+e.getMessage());
    	}
	}
		

	public void setConnection(Connection conn)
	{
		this.conn = conn;
	}
		
			
	//private void elabora(Connection conn, HashMap<String, BeanViario> viarioUnico, List<BeanViario> lVieTotDaAggr) throws Exception {
	private void elabora(HashMap<String, BeanViario> viarioUnico, ResultSet lVieTotDaAggr) throws Exception {			
		try{
			
			int i = 0;
			while (lVieTotDaAggr.next()) {				
								
				BeanViario vnr = new BeanViario();
				vnr.setIddwh(lVieTotDaAggr.getString("IDDWH"));
				vnr.setFkentesorgente(lVieTotDaAggr.getString("FKENTESORGENTE"));
				vnr.setIndirizzo(lVieTotDaAggr.getString("INDIRIZZO"));
				vnr.setSedime(lVieTotDaAggr.getString("SEDIME"));
				vnr.setProges(lVieTotDaAggr.getString("PROGES"));
				vnr.setCodiceViaOrig(lVieTotDaAggr.getString("CODICEVIAORIG"));
				
				associazione = false;
				
				//Controlla Criterio A0 (Codice Via Fornito dall'ente)
				if(vnr.getCodiceViaOrig() != null){
					
					String codVia = this.cercaViaUnico(vnr.getCodiceViaOrig());
					if(codVia!=null){
						//Associo la via
						Object params[] = new Object[7];
						params[0]=codVia;
						params[1]=null;						//TODO note
						params[2]=100;
						params[3]="CAV-A00";				
						params[4]=vnr.getIddwh();
						params[5]=vnr.getFkentesorgente();
						params[6]=vnr.getProges();
						run.update(conn,getProperty("SQL_AGGIORNA_VIA_TOTALE"), params);
		
						associazione = true;
					}else{
						//Controlla Criterio A1 (Associazione tra due vie di fonti diverse)
						String codViaUnico = this.cercaViaInFontiDiverse(vnr);
						
						//Controllo la presenza del codice in unico
						codVia = this.cercaViaUnico(codViaUnico);
						
						if(codVia!=null){
							//Associo la via
							Object params[] = new Object[7];
							params[0]=codVia;
							params[1]=null;						//TODO note
							params[2]=100;
							params[3]="CAV-A10";				
							params[4]=vnr.getIddwh();
							params[5]=vnr.getFkentesorgente();
							params[6]=vnr.getProges();
							run.update(conn,getProperty("SQL_AGGIORNA_VIA_TOTALE"), params);
							
							associazione = true;
						}	
					}
					
				}else{
					associazione = false;
				}
				
				if(!associazione){
					
					String vnrIndirizzoNormalizzato = vnr.getIndirizzoNormalizzato();
					//Controlla Criterio A2 (Ugualianza descrizione)
					BeanViario viaUgualeNelUnico = cercaIndirizzoUnico(vnrIndirizzoNormalizzato, vnr.getSedime());						
					if (viaUgualeNelUnico != null)
					{
						
						// associo la via 
						Object params[] = new Object[7];
						params[0]=viaUgualeNelUnico.getIdvia();					
						params[1]=null;//TODO note???
						params[2]=100;
						if(viaUgualeNelUnico.getIndirizzoNormalizzato().equals(vnrIndirizzoNormalizzato))
							params[3]="UGUALI";
						else params[3]=viaUgualeNelUnico.getReldescr();
						params[4]=vnr.getIddwh();
						params[5]=vnr.getFkentesorgente();
						params[6]=vnr.getProges();
						run.update(conn,getProperty("SQL_AGGIORNA_VIA_TOTALE"), params);
							
						associazione = true;
					}
						
					if(!associazione){
						//Controllo Criterio A3/A5 (Corrispondenza Dati Catastali con ugual prefisso)
							
						if(!associazione){
							//Controllo Criterio A4/A6 (Soggetti relazionati ad una via con ugual prefisso)
							
							if(!associazione){
								//aggiorno il viaro unico con quello personalizzato per l'indirizzo che sto cercando
								viarioUnico = this.caricaViarioUnicoFuzzy(vnr.getIndirizzo());	
								
								//Cotrollo Criterio A6/A7 (Similitudine descrizione con uguale o differnte prefisso)
								this.associaVie(viarioUnico,vnr);
											
								if(!associazione){
									//Effettuo l'inserimento in UNICO del nuovo elemento
									this.inserisciInUnico(viarioUnico,vnr);
										
									//devo sincronizzare l'indice testuale di unico
									this.syncTextIndex();
									
									//Devo rileggere gli elementi presenti in unico				
									//viarioUnico = this.caricaViarioUnico();												
								}
							}													
						}
					}
				}
				
				//Effettuo la commit dei dati su DB
				i++;
				totNumRecAggiornati = totNumRecAggiornati + 1;
				if(i > 50000){
					i=0;
					log.debug("Aggiornamento record " + totNumRecAggiornati) ;
					conn.commit();
				}
				
			}
			
			conn.commit();
			
		}catch(Exception e){					
			conn.rollback();
			throw new Exception("Errore in operazione aggregazione fonte riferimento VIE: "+e.getMessage());
		}
		finally{
			if(lVieTotDaAggr!=null)
				lVieTotDaAggr.close();
		}
	
	}
	
	
	
	/**
	 * Metodo che legge i dati dalla tabella SIT_VIA_UNICO
	 * @return
	 * @throws Exception
	 */
	protected HashMap<String, BeanViario> caricaViarioUnico() throws Exception{
		
		// Leggo vie da viario principale		
		String sqlVieUnico = getProperty("SQL_LEGGI_VIE_UNICO");
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sqlVieUnico);
		
		HashMap<String, BeanViario> viarioUnico = new HashMap<String, BeanViario>();		
		
		try {
			while (rs.next()) {
				BeanViario bv = new BeanViario();
				bv.setIdvia(rs.getString("IDVIA"));
				bv.setIndirizzo(rs.getString("INDIRIZZO"));
				bv.setSedime(rs.getString("SEDIME"));
				viarioUnico.put(rs.getString("INDIRIZZO") + SEPARATOR + rs.getString("SEDIME"), bv);				
			}
			
		return viarioUnico;	
			
		} catch (Exception e) {
			throw new Exception ("Errore in caricamento dati da tabella SIT_VIA_UNICO "+e.getMessage());
		} finally {
			if(rs!=null)
				rs.close();
			if(st!=null)
				st.close();
		}				
	}
	
	/**
	 * Metodo che legge i dati dalla tabella SIT_VIA_UNICO in modo FUZZY
	 * @return
	 * @throws Exception
	 */
	protected HashMap<String, BeanViario> caricaViarioUnicoFuzzy(String indirizzo) throws Exception{
				
		HashMap<String, BeanViario> viarioUnico = new HashMap<String, BeanViario>();		
		if (indirizzo==null || "-".equals(indirizzo)  || "".equals(indirizzo.replaceAll("[^a-zA-Z0-9]","")))
				return viarioUnico;
		
		//normalizzo l'indirizzo eliminando punti, apici e spazi non necessari
		indirizzo = indirizzo.replaceAll("[\\.]", " ");
		indirizzo = indirizzo.replaceAll("[\\']", " ");
		String u[] = indirizzo.split("[\\s]+");
		//sembra che il metodo fuzzy non permette parole minori di 3 caratteri(ORA-29902) quindi formatto
		String paramBase = "";
		for(int i = 0; i < u.length; i++ ){
			if(u[i].length() > 3)
				paramBase += (u[i] + " ");
		}
		//inizializzo tutti i parametri alla stringa normalizzata
		paramBase = paramBase.trim();
		String param1 = paramBase;
		String param2 = paramBase;
		String param3 = paramBase;
		String param4 = paramBase;
		
		if(!paramBase.equals("")){
			String v[] = paramBase.split("[\\s]+");
			param1 = v[0];
			//param4 fa riferimento al LIKE sulla query quindi prendo la prima parola
			param4 = v[0];
			if(v.length > 1){
				param2 = v[1];
				if(v.length > 2)
					param3 = v[2];
			}
		}
		

		
		//se è vuoto vuol dire che l'indirizzo ha solo parole da 2 o meno caratteri 
		if(param1.equals("")){
			param1 = indirizzo;
			param2 = indirizzo;
			param3 = indirizzo;
			param4 = indirizzo;
		}
		
		// Leggo vie da viario principale		
		String sqlVieUnico = getProperty("SQL_LEGGI_VIE_UNICO_FUZZY");
		sqlVieUnico = sqlVieUnico.replace("@1", param1.replaceAll("[^a-zA-Z0-9]+",""));
		sqlVieUnico = sqlVieUnico.replace("@2", param2.replaceAll("[^a-zA-Z0-9]+",""));
		sqlVieUnico = sqlVieUnico.replace("@3", param3.replaceAll("[^a-zA-Z0-9]+",""));
		sqlVieUnico = sqlVieUnico.replace("@4", param4.replaceAll("[^a-zA-Z0-9]+",""));
		
		ResultSet rs = null;
		Statement st = conn.createStatement();
		
		
		try {
			rs = st.executeQuery(sqlVieUnico);
			
			while (rs.next()) {
				BeanViario bv = new BeanViario();
				bv.setIdvia(rs.getString("IDVIA"));
				bv.setIndirizzo(rs.getString("INDIRIZZO"));
				bv.setSedime(rs.getString("SEDIME"));
				viarioUnico.put(rs.getString("INDIRIZZO") + SEPARATOR + rs.getString("SEDIME"), bv);				
			}
			
		return viarioUnico;	
			
		} catch (Exception e) {
			
			log.error("Indirizzo in errore:" +  indirizzo);
			log.error("_____ERRORE FUZZY____: " + sqlVieUnico);
			log.error(e.getMessage(),e);
			throw new Exception ("Errore in caricamento dati da tabella SIT_VIA_UNICO in modo FUZZY ",e );
		} finally {
			if(rs!=null)
				rs.close();
			if(st!=null)
				st.close();
		}				
	}
	
	/**
	 * Metodo che crea l'indice testuale per SIT_VIA_UNICO, necessario per la ricerca FUZZY
	 * @return
	 * @throws Exception
	 */
	protected void createTextIndex() throws Exception{
				
		String sqlDropIndex = getProperty("SQL_DROP_VIA_UNICO_TEXT_INDEX");
		String sqlCreateIndex = getProperty("SQL_CREA_VIA_UNICO_TEXT_INDEX");
		
		Statement st = conn.createStatement();
		try {
			st.executeUpdate(sqlDropIndex);
		} catch (Exception e) {
			//non esiste
		} finally {
			DbUtils.close(st);
		}				
		
		Statement st1 = conn.createStatement();
		try {
			st1.executeUpdate(sqlCreateIndex);	
		} catch (Exception e) {
			throw new Exception ("Errore in CREAZIONE INDICE TEXT "+e.getMessage());
		} finally {
			DbUtils.close(st1);
		}				
	}
	
	/**
	 * Metodo che esegue la sincronizzazione dell'indice testuale per SIT_VIA_UNICO, necessario per la ricerca FUZZY
	 * @return
	 * @throws Exception
	 */
	protected void syncTextIndex() throws Exception{
				
		String sqlSyncIndex = getProperty("SQL_SYNC_VIA_UNICO_TEXT_INDEX");
		CallableStatement proc = conn.prepareCall(sqlSyncIndex);
		
		try {
		    proc.execute();
		} catch (Exception e) {
			throw new Exception ("Errore in SYNC INDICE TEXT "+e.getMessage());
		} finally {
			if(proc!=null)
				proc.close();
		}				
	}
	
	/**
	 * Metodo che legge i dati da aggregare dalla tabella SIT_VIA_TOTALE
	 */
	protected ResultSet caricaDatiDaAggregare() throws Exception{					
		
		//List<BeanViario> lvieAltre = null;	
		ResultSet rs = null;
		
		try {
			
			//BeanListHandler h = new BeanListHandler(BeanViario.class);			
			
			String sql = getProperty("SQL_LEGGI_VIA_TOTALE");
				
			Statement st = connectionForLongResultset.createStatement();
			rs = st.executeQuery(sql);
			//lvieAltre = (List<BeanViario>) run.query(conn, sql, h);			
				
			log.info("Lista Dati da caricare in Totale caricata");
			
		}catch (Exception e) {
			throw new Exception("Errore in lettura dati da Aggregare in SIT_VIA_TOTALE "+e.getMessage());
		}
		
		return rs;
		
	}
	
	
	/**
	 * Metodo che associa le vie puntate
	 */
	protected void associaViePuntate(HashMap<String, BeanViario> viarioUnico, BeanViario vnr) throws Exception{
	
		
		float perc = 0;
		BeanViario trovato = null;		
	
		try{
					
			BeanViario trovatoInViaUnico = null;			
			for (String key : viarioUnico.keySet()){
				
				BeanViario vu = viarioUnico.get(key);
				

				float percTrovata =  associaSedimeDescrizionePuntata(vu, vnr)==true?90:0;
						
				if(percTrovata == 0){
					continue;
				}else{
					perc = percTrovata;
					trovato = vnr;
					trovatoInViaUnico = vu;						
				}						
			}
			
			if(trovato != null  && ((vnr.getIndirizzo().length() > 2 && trovatoInViaUnico.getIndirizzo().length() > 2 ) || (vnr.getIndirizzo().length() < 2 && trovatoInViaUnico.getIndirizzo().length() < 2 ))){			
				
				// associo la via
				Object params[] = new Object[7];
				 
				params[0]=vnr.getIdvia();				
				params[1]=null;							//TODO note
				params[2]=perc*(float)100.00;			//TODO rating
				if(vnr.getSedime() == null)
					params[3]="SIMILESENZASEDIMEEPUNTATO";	//TODO rel_descr
				if(vnr.getSedime() != null)
					params[3]="SIMILECONSEDIMEEPUNTATO";	//TODO rel_descr
				params[4]=vnr.getIddwh();
				params[5]=vnr.getFkentesorgente();
				params[6]=vnr.getProges();
				run.update(conn,getProperty("SQL_AGGIORNA_VIA_TOTALE"), params);
				trovatoInViaUnico.setRating(String.valueOf(perc));
				viarioUnico.put(vnr.getIndirizzo() + SEPARATOR + vnr.getSedime(), trovatoInViaUnico);	//chiave diversa stesso dato				
				
				associazione = true;
			}
				
		}catch (Exception e) {

			throw new Exception("Errore in Associa Vie Puntate " + e.getMessage());
			
		}	
			
	}
	
	
	
	
	/**
	 * Metodo che associa le vie per stesso sedime
	 */
	protected void associaVie(HashMap<String, BeanViario> viarioUnico, BeanViario vnr) throws Exception{
			
		float perc = 0;
		BeanViario trovato = null;		
		
				
		boolean associaConSedimeConPuntato = false;	
		boolean associaSenzaSedimeConPuntato = false;	
		
		//Inizializzo il vecchio oggetto da confrontare prima del salvataggio
		GenericTuples.T2<Boolean, Float>oggettoVecchio = new GenericTuples.T2<Boolean, Float>(true, (float)0.0);;
		
		try{
			
			BeanViario trovatoInViaUnico = null;			
			for (String key : viarioUnico.keySet()){
					
				BeanViario vu = viarioUnico.get(key);				
			
				associaConSedimeConPuntato = false;
				associaSenzaSedimeConPuntato = false;
				sostituzioneEleCorr = false;
					
				// controllo soltanto se le lunghezze degli indirizzi sono entrambe > 2 o entrambe <2 ... questo perche' alcuni algoritmi scavolano confrontando PO con POMEZIA
				if (!((vnr.getIndirizzo().length() > 2 && vu.getIndirizzo().length() > 2 ) || (vnr.getIndirizzo().length() < 2 && vu.getIndirizzo().length() < 2 )))
					continue;

				// ASSOCIO VIA CON STESSO SEDIME O NULLO E INDIRIZZO CON IL PUNTATO , PER ESEMPIO g.garibaldi con giuseppe garibaldi								
				if((vu.getIndirizzo().contains(".") || vnr.getIndirizzo().contains(".")) && associaSedimeDescrizionePuntata(vu, vnr)){
					trovato = vnr;
					trovatoInViaUnico = vu;
					if(vnr.getSedime() == null){
						associaSenzaSedimeConPuntato = true;
						perc = new Float(30);
					}
					if(vnr.getSedime() != null){
						associaConSedimeConPuntato = true;
						perc = new Float(90);
					}
					break;
				}else{
					String s1 = vnr.getIndirizzoNormalizzato().trim();
					String s2 = vu.getIndirizzoNormalizzato().trim();	
					
					String sedime1 = vnr.getSedime();
					String sedime2 = vu.getSedime();
					
					if(sedime1 == null)
						sedime1 = "-";
					if(sedime2 == null)
						sedime2 = "-";
																			
					
					//TODO l'algoritmo restituisce cmq 0 in caso di non associabilità, o una percentuale in caso di associabilità																			
					GenericTuples.T2<Boolean,Float>  oggettoDaAssociare = SmithWatermanGotohNibbioWindowedAffine.getSimilarity(sedime1, sedime2, s1, s2);					
					
					if(oggettoDaAssociare.secondObj > 0.3){	
						
						confrontaNuovoConVecchio(oggettoDaAssociare,oggettoVecchio);
						
						if(sostituzioneEleCorr){
							trovato = vnr;
							trovatoInViaUnico = vu;
							associaSenzaSedimeConPuntato = false;
							associaConSedimeConPuntato = false;
							perc = perc > (oggettoDaAssociare.secondObj * 100)? perc: oggettoDaAssociare.secondObj * 100;
						}	
					}													
				}												
			}
		
			if(trovato != null  && ((vnr.getIndirizzo().length() > 2 && trovatoInViaUnico.getIndirizzo().length() > 2 ) || (vnr.getIndirizzo().length() < 2 && trovatoInViaUnico.getIndirizzo().length() < 2 )))
			{
						
				// associo la via
				Object params[] = new Object[7];
				params[0]=trovatoInViaUnico.getIdvia();				
				params[1]=null;					//TODO note	
				params[2]=perc; //TODO rating
				String relDescr = "";
				if(associaConSedimeConPuntato){
					relDescr="SIMILECONSEDIMEEPUNTATO";			//TODO rel_descr
				}else if(associaSenzaSedimeConPuntato){
					relDescr="SIMILESENZASEDIMEEPUNTATO";	
				}else if(vnr.getSedime() != null){
					relDescr="SIMILECONSEDIME";					//TODO rel_descr
				}else if(vnr.getSedime() == null){
					relDescr="SIMILESENZASEDIME";					//TODO rel_descr
				}
				params[3]=relDescr;
				params[4]=vnr.getIddwh();
				params[5]=vnr.getFkentesorgente();
				params[6]=vnr.getProges();
				run.update(conn,getProperty("SQL_AGGIORNA_VIA_TOTALE"), params);
				trovatoInViaUnico.setRating(String.valueOf(perc));
				trovatoInViaUnico.setReldescr(relDescr);
				viarioUnico.put(vnr.getIndirizzo() + SEPARATOR + vnr.getSedime(), trovatoInViaUnico);//chiave diversa stesso dato			
					
				associazione = true;
			}

		}catch (Exception e) {
			throw new Exception("Errore in Associa Vie Stesso Sedime "+e.getMessage());
		}
		
	}
	


	
	/**
	 * Metodo che effetua il salvataggio del nuovo dato in SIT_VIA_UNICO e associa il relativo elemento di SIT_VIA_TOTALE 
	 */
	protected void inserisciInUnico(HashMap<String, BeanViario> viarioUnico, BeanViario vnr) throws Exception{	
		
		try{
			
			//prendo un id 
			Object nuovoId = ((Map) run.query(conn,getProperty("SQL_NEXT_UNICO"), new MapHandler())).get("ID");								
			
			// creo la via
			Object paramsUnico[] = new Object[3];
			paramsUnico[0]=nuovoId;
			
			if(vnr.getSedime()==null){
				paramsUnico[1]="-";
			}else{
				paramsUnico[1]=vnr.getSedime();
			}
			
			paramsUnico[2]=vnr.getIndirizzo();
			run.update(conn,getProperty("SQL_NUOVO_VIA_UNICO"), paramsUnico);			
			//AGGIORNO LA TAB SIT_VIA_TOTALE
			// associo la via ok
			Object params[] = new Object[7];
			params[0]=nuovoId;
			params[1]=null;				//TODO note
			params[2]=100;				//TODO rating
			params[3]="NATIVA";			//TODO rel_descr
			params[4]=vnr.getIddwh();
			params[5]=vnr.getFkentesorgente();
			params[6]=vnr.getProges();
			
			run.update(conn,getProperty("SQL_AGGIORNA_VIA_TOTALE"), params);
			

		}catch (Exception e) {
			throw new Exception("Errore in salvataggio dato in SIT_VIA_UNICO "+e.getMessage());
		}
	}
		

	/**
	 * Metodo che confronta il vecchio oggetto Via con il nuovo
	 */
	protected void confrontaNuovoConVecchio(GenericTuples.T2<Boolean,Float> oggettoDaAssociare,GenericTuples.T2<Boolean,Float> oggettoVecchio) throws Exception{
		
		try{
			
			if(oggettoDaAssociare.firstObj.equals(oggettoVecchio.firstObj)){
				if(oggettoDaAssociare.secondObj>oggettoVecchio.secondObj){
					//SEDIME UGUALE TRA TOTALE e UNICO, LA NUOVA STRINGA E' PRESA COME MIGLIORE							
					sostituzioneEleCorr = true;
				}
			}else{
				float diffPerc = oggettoDaAssociare.secondObj-oggettoVecchio.secondObj;
				
				if(oggettoDaAssociare.firstObj.equals(true)){			
					if(diffPerc>-0.10){
						//SEDIME DIVERSO TRA TOTALE e UNICOSEDIME, ma uguale tra oggVecchio e oggNuovo, VIENE PRESO IL NUOVO ELEMENTO 
						sostituzioneEleCorr = true;
					}	
				}else{		
					//TODO da commentare??? 
					if(diffPerc>0.10){
						//SEDIME DIVERSO MIGLIORE, VIENE PRESO IL NUOVO ELEMENTO
						sostituzioneEleCorr = true;
					}
				}				
			}
			
		}catch (Exception e) {
			throw new Exception("Errore in confronto vecchia via con nuova "+e.getMessage());
		}	
	}
	
	
	/**
	 * Metodo che pulisce la tabella dell'unico dopo l'associazione. 
	 * Vengono eliminati i record di UNICO che non hanno un corrispondente in TOTALE e che hanno il
	 * campo VALIDATO diverso da 1 (validato = 1 sta a significare che il dato UNICO è stato inserito a mano da interfaccia)
	 */
	
	protected void pulisciUnico() throws Exception{
		
		try{			
			//Cancellazione Elemento da SIT_CIVICO_UNICO
			String sqlDeleteCivicoUnico = getProperty("SQL_PULISCI_CIVICO_UNICO");
			
			PreparedStatement ps = conn.prepareStatement(sqlDeleteCivicoUnico);			
			ps.execute();
			ps.close();
			
			conn.commit();	
			
			//Cancellazione Elemento da SIT_VIA_UNICO
			String sqlDeleteUnico = getProperty("SQL_PULISCI_VIA_UNICO");
			
			ps = conn.prepareStatement(sqlDeleteUnico);			
			ps.execute();
			ps.close();
			
		}catch (Exception e) {
			throw new Exception("Errore nella cancellazione elementi da tabella UNICO :" + e.getMessage());
		}
		
	}
	
	
	
	/**
	 * Metodo che effettua la ricerca di una dato in unico dato il valore della via originaria del viario 
	 */	
	protected String cercaViaUnico(String viaOrig) throws Exception {
	
		String codiceVia = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try{
			
			String sql = getProperty("SQL_CERCA_VIA_UNICO");
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, viaOrig);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				codiceVia = rs.getString("ID_VIA");
			}
			
			ps.close();

			
		}catch (Exception e) {
			throw new Exception("Errore nella ricerca della via UNICO per codice VIA originario :" + e.getMessage());
		}finally{
			if(ps!=null)ps.close();
			if(rs!=null)rs.close();
		}
		
		
		return codiceVia;
		
	}
	
	
	/**
	 * Metodo che effettua la ricerca di una via nella tabella SIT_FONTI_ASS_DIVERSE per cercare vie associate di fonti diverse
	 */
	protected String cercaViaInFontiDiverse(BeanViario vnr) throws Exception{	
		
		String codiceVia = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try{
			
			String sql = getProperty("SQL_CERCA_VIA_FONTI_DIVERSE");
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, vnr.getCodiceViaOrig());
			ps.setString(2, vnr.getFkentesorgente());
			ps.setString(3, vnr.getProges());			
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				codiceVia = rs.getString("ID_VIA");
			}

			
		}catch (Exception e) {
			throw new Exception("Errore nella ricerca della via UNICO per codice VIA originario :" + e.getMessage());
		}finally{
			if(ps!=null)ps.close();
			if(rs!=null)rs.close();
		}
		
		
		return codiceVia;
	}
	
	/**
	 * Metodo che effettua la ricerca di una dato in unico dato l'indirizzo 
	 */	
	protected BeanViario cercaIndirizzoUnico(String indirizzo, String prefisso) throws Exception {
	
		BeanViario bv = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ResultSet rs1 = null;
		PreparedStatement ps1 = null;
		
		try{
			
			String sql = getProperty("SQL_CERCA_INDIRIZZO_UNICO_CON_PREFISSO");
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, prefisso);
			ps.setString(2, indirizzo);
			
			rs = ps.executeQuery();
			
			if(rs.next())
				bv = this.riempiBeanViario(bv, rs);
			else {
				sql = getProperty("SQL_CERCA_INDIRIZZO_UNICO");
				ps1 = conn.prepareStatement(sql);
				ps1.setString(1, indirizzo);
				rs1 = ps1.executeQuery();
				
				if(rs1.next())
					bv = this.riempiBeanViario(bv, rs1);
			}
			
		}catch (Exception e) {
			throw new Exception("Errore nella ricerca della via UNICO per INDIRIZZO :" + e.getMessage());
		}finally{
			DbUtils.close(rs);
			DbUtils.close(rs1);
			DbUtils.close(ps);
			DbUtils.close(ps1);
			
		}
	
		return bv;
		
	}
	
	private BeanViario riempiBeanViario(BeanViario bv, ResultSet rs) throws SQLException{
		
		bv = new BeanViario();
		bv.setIdvia(rs.getString("IDVIA"));
		bv.setIndirizzo(rs.getString("INDIRIZZO"));
		bv.setSedime(rs.getString("SEDIME"));
		
		return bv;
	}
	
	
	@Override
	public void setConnectionForLongResultset(
			Connection connectionForLongResultset) throws AggregatoreException {
		
		this.connectionForLongResultset = connectionForLongResultset;
		
	}
	
	
	
	
}
