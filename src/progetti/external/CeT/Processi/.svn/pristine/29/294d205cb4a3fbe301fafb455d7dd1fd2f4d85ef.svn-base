package it.webred.ct.proc.ario.fonti;

import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.ct.data.model.indice.SitFabbricatoTotale;
import it.webred.ct.data.model.indice.SitOggettoTotale;
import it.webred.ct.data.model.indice.SitSoggettoTotale;
import it.webred.ct.data.model.indice.SitViaTotale;
import it.webred.ct.proc.ario.bean.Civico;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;



public abstract class DatoDwh {

	protected static final Logger log = Logger.getLogger(DatoDwh.class.getName());


	public abstract int getFkEnteSorgente();

	public abstract int getProgEs();	
		
	public abstract String getSql(String pID);
	
	public abstract String getTable();
	
	public abstract boolean existProcessId();	
	
	public abstract boolean queryWithParamCodEnte();
		
	public abstract void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert, String saveTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception;
		
	public abstract void prepareUpdateDato(DatoDwh classeFonte, Connection connForInsert, String saveTotale, String updateTotale, String searchTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception;
	
	public abstract String getDeleteSQL();
	
	public abstract String getInsertSQL();
	
	public abstract String getUpdateSQL();
	
	public abstract String getSearchSQL();
	
	public abstract boolean dwhIsDrop(Connection conn) throws Exception;
		
    public abstract String getQuerySQLSaveProcessId() throws Exception;
    
    public abstract String getQuerySQLUpdateProcessId() throws Exception;
	
    public abstract String getQuerySQLNewProcessId() throws Exception;
    
    public abstract String getQuerySQLgetProcessId() throws Exception;
    
    public abstract String getQuerySQLDeleteProcessId() throws Exception;
        
    
    QueryRunner ins = new QueryRunner();
    
	
	/**
	 * Metodi di SAVE/UPDATE
	 */
	
	
	//Metodo che controlla la presenza di un elemento nella tabella TOTALE
	protected String trovaDatoTotale(DatoDwh classeFonte,Connection conn,String sqlSearch, IndicePK srt)throws Exception{
		
		PreparedStatement prs = null;
		ResultSet res = null;
		String azione = "";
		
		try
		{					
			
			prs = conn.prepareStatement(sqlSearch, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			prs.setString(1, srt.getIdDwh());
			prs.setLong(2, srt.getFkEnteSorgente());
			prs.setLong(3, srt.getProgEs());	
									
			res = prs.executeQuery();	
						
				
			//Dovrei avere un solo elemento in totale per la terna di chiavi id_dwh, prog_Es, entesorg
			if (res.next()){
			
				String nuovoCtrHash = srt.getCtrHash();
				String vecchioCtrHash = res.getString("CTR_HASH");
				
				if(nuovoCtrHash.equals(vecchioCtrHash)){
					azione = "NULLA";	
				}else{
					azione = "AGGIORNA";
				}
			}else{
				azione = "INSERISCI";
			}
	
		}catch (SQLException sqle){
			throw new RulEngineException("Errore in select Ricerca tabella TOTALI per codice ente sorgente "+srt.getFkEnteSorgente()+":"+sqle.getMessage());
		}finally{
			if(prs != null)
				prs.close();
			if(res != null)
				res.close();			
		}
				
		return azione;
	}
	
	//Metodo che controlla la presenza di un elemento nella tabella TOTALE
	protected String trovaDatoTotaleCtrHash(DatoDwh classeFonte,Connection conn,String sqlSearch, IndicePK srt)throws Exception{
		
		PreparedStatement prs = null;
		ResultSet res = null;
		String azione = "";
		
		try
		{					
			prs = conn.prepareStatement(sqlSearch);
			
			prs.setString(1, srt.getIdDwh());
			prs.setLong(2, srt.getFkEnteSorgente());
			prs.setLong(3, srt.getProgEs());	
			prs.setString(4, srt.getCtrHash());
									
			res = prs.executeQuery();
			
			if (res.next()){							
				azione = "NULLA";					
			}else{
				azione = "INSERISCI";
			}
	
		}catch (SQLException sqle){
			throw new RulEngineException("Errore in select Ricerca tabella TOTALI per codice ente sorgente "+srt.getFkEnteSorgente()+":"+sqle.getMessage());
		}finally{
			if(prs != null)
				prs.close();
			if(res != null)
				res.close();			
		}
				
		return azione;
	}
	
	
	protected String getCtrHash(String stringa) 
	{
		
		MessageDigest md = null;
		try
		{
			md = MessageDigest.getInstance("SHA");
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}

		md.update(stringa.getBytes());


		byte[] b = md.digest();
		
		return new String(StringUtils.toHexString(b));
		
	}
	
	
	//Metodo che salva i dati in CIVICO TOTALE
	protected void saveSitCivicoTotale(DatoDwh classeFonte, Connection conn, String sql, SitCivicoTotale sct) throws Exception
	{
	
		try
		{			
			
			sct.setStato("N");

			if (sct.getCivLiv1()!=null) {
				sct.setCivLiv1(sct.getCivLiv1().equalsIgnoreCase("SNC")?"0":sct.getCivLiv1());
				sct.setCivLiv1(sct.getCivLiv1().equalsIgnoreCase("SN")?"0":sct.getCivLiv1());
				sct.setCivLiv1(sct.getCivLiv1().equalsIgnoreCase("SC")?"0":sct.getCivLiv1());
				sct.setCivLiv1(sct.getCivLiv1().equalsIgnoreCase("NP")?"0":sct.getCivLiv1());
			}
			sct.setCivLiv1(StringUtils.padding(sct.getCivLiv1()!=null?sct.getCivLiv1().trim():null, 8, '0', true));

			if ("00000000".equals(sct.getCivLiv1()))
				return;

			
			QueryRunner ins = new QueryRunner();
			
			Object[] paramsIns = new Object[] { 
					 sct.getId().getIdDwh(),
					 sct.getId().getFkEnteSorgente(),
					 sct.getId().getProgEs(),
					 sct.getId().getCtrHash(),					 					
					 sct.getCivicoComp(),
					 sct.getCivLiv1()!=null?sct.getCivLiv1().trim():null,
					 sct.getCivLiv2()!=null?sct.getCivLiv2().trim():null,
					 sct.getCivLiv3()!=null?sct.getCivLiv3().trim():null,
					 sct.getIdOrigViaTotale(),
					 sct.getIdStorico(),
					 sct.getDataFineVal(),
					 sct.getNote(),
					 sct.getAnomalia(),
					 sct.getStato(),
					 sct.getCodiceCivOrig(),
					 
					 sct.getField1(),
					 sct.getField2(),
					 sct.getField3(),
					 sct.getField4(),
					 sct.getField5(),
					 sct.getField6(),
					 sct.getField7(),
					 sct.getField8(),
					 sct.getField9(),
					 sct.getField10(),
					 sct.getField11()
		
					};
			ins.update(conn, sql, paramsIns);
			
		}catch (SQLException sqle){
			//verifico se e' errore diverso da chiave duplicata
			if (sqle.getErrorCode() != 1 && sqle.getErrorCode() != 1401) 
				throw sqle;
			else
				log.warn("Inserimento SIT_CIVICO_TOTALE da Ente "+sct.getId().getFkEnteSorgente()+":"+sqle.getMessage(),sqle);
		}
		finally {

		}
	}
	
	//Metodo che aggiorna i dati in CIVICO TOTALE
	protected void updateSitCivicoTotale(DatoDwh classeFonte, Connection conn, String sql, SitCivicoTotale sct) throws Exception
	{
	
		try
		{			

			if (sct.getCivLiv1()!=null) {
				sct.setCivLiv1(sct.getCivLiv1().equalsIgnoreCase("SNC")?"0":sct.getCivLiv1());
				sct.setCivLiv1(sct.getCivLiv1().equalsIgnoreCase("SN")?"0":sct.getCivLiv1());
				sct.setCivLiv1(sct.getCivLiv1().equalsIgnoreCase("SC")?"0":sct.getCivLiv1());
				sct.setCivLiv1(sct.getCivLiv1().equalsIgnoreCase("NP")?"0":sct.getCivLiv1());
			}
			sct.setCivLiv1(StringUtils.padding(sct.getCivLiv1()!=null?sct.getCivLiv1().trim():null, 8, '0', true));

						
			QueryRunner ins = new QueryRunner();
			
			Object[] paramsIns = new Object[] { 	
					 sct.getId().getCtrHash(),					 
					 sct.getFkCivico(),
					 sct.getRelDescr(),
					 sct.getRating(),
					 sct.getCivicoComp(),
					 sct.getCivLiv1()!=null?sct.getCivLiv1().trim():null,
					 sct.getCivLiv2()!=null?sct.getCivLiv2().trim():null,
					 sct.getCivLiv3()!=null?sct.getCivLiv3().trim():null,
					 sct.getIdOrigViaTotale(),
					 sct.getIdStorico(),
					 sct.getDataFineVal(),
					 sct.getNote(),
					 sct.getAnomalia(),
					 sct.getStato(),
					 sct.getCodiceCivOrig(),
					 
					 sct.getField1(),
					 sct.getField2(),
					 sct.getField3(),
					 sct.getField4(),
					 sct.getField5(),
					 sct.getField6(),
					 sct.getField7(),
					 sct.getField8(),
					 sct.getField9(),
					 sct.getField10(),
					 sct.getField11(),
					 
					 sct.getId().getIdDwh(),
					 sct.getId().getFkEnteSorgente(),
					 sct.getId().getProgEs(),					 
					 sct.getId().getCtrHash()
					};
			
			ins.update(conn, sql, paramsIns);
			
		}catch (SQLException sqle){
			//verifico se e' errore diverso da chiave duplicata
			if (sqle.getErrorCode() != 1 && sqle.getErrorCode() != 1401) 
				throw sqle;
			else
				log.warn("Aggiornamento SIT_CIVICO_TOTALE da Ente "+sct.getId().getFkEnteSorgente()+":"+sqle.getMessage(),sqle);
		}
		finally {

		}
	}
			
	protected String setCtrHashSitCivicoTotale(SitCivicoTotale sct)
	{
		String hash = sct.getIdOrigViaTotale()+sct.getCivLiv1()+sct.getCivLiv2()+sct.getCivLiv3();				
		String newHash = getCtrHash(hash);
		
		return newHash;
	}
	
	
	
	//GESTIONE SOGGETTI
	
	//Metodo che salva i dati in SoggettoTotale
	protected void saveSitSoggettoTotale(DatoDwh classeFonte, Connection conn, String sql, SitSoggettoTotale sst) throws Exception
	{	
		try
		{					
			sst.setStato("N");
			
			QueryRunner ins = new QueryRunner();
			// tolgo il sedime eventualmente gia duplicato nella descrizione
			Object[] paramsIns = new Object[] { 
					sst.getId().getIdDwh(),
					sst.getId().getFkEnteSorgente(),
					sst.getId().getProgEs(),
					sst.getId().getCtrHash(),										
					sst.getCognome(),
					sst.getNome(),
					sst.getDenominazione(),
					sst.getSesso(),
					sst.getCodfisc(),
					sst.getPi(),
					sst.getDtNascita(),
					sst.getTipoPersona(),
					sst.getCodProvinciaNascita(),
					sst.getCodComuneNascita(),
					sst.getDescProvinciaNascita(),
					sst.getDescComuneNascita(),
					sst.getCodProvinciaRes(),
					sst.getCodComuneRes(),
					sst.getDescProvinciaRes(),
					sst.getDescComuneRes(),
					sst.getIdStorico(),
					sst.getDtFineVal(),
					sst.getAnomalia(),
					sst.getStato(),
					sst.getCodiceSoggOrig(),
					
					sst.getProcessId(),
					sst.getDtInizioDato(),
					sst.getDtFineDato(),
					sst.getDtExpDato(),
					sst.getProvenienza(),
					sst.getDtInizioVal(),
					sst.getDataRegistrazione(),
					
					sst.getField1(),
					sst.getField2(),
					sst.getField3(),
					sst.getField4(),
					sst.getField5(),
					sst.getField6(),
					sst.getField7(),
					sst.getField8(),
					sst.getField9(),
					sst.getField10(),
					sst.getField11(),
					sst.getField12(),
					sst.getField13(),
					sst.getField14(),
					sst.getField15(),
					sst.getField16(),
					sst.getField17(),
					sst.getField18(),
					sst.getField19(),
					sst.getField20(),
					sst.getField21(),
					sst.getField22(),
					sst.getField23(),
					sst.getField24()
			};
			
			
			ins.update(conn, sql, paramsIns);
				

		}catch (SQLException sqle){
			//verifico se e' errore diverso da chiave duplicata
			if (sqle.getErrorCode() != 1 && sqle.getErrorCode() != 1401) 
				throw sqle;
			else
				log.warn("Inserimento SIT_SOGGETTO_TOTALE da Ente "+sst.getId().getFkEnteSorgente()+":"+sqle.getMessage(), sqle);
		}
		finally {
		}
		
	}
	
	//Metodo che aggiorna i dati in SoggettoTotale
	protected void updateSitSoggettoTotale(DatoDwh classeFonte, Connection conn, String sql, SitSoggettoTotale sst) throws Exception
	{
	
		try
		{			
			
			sst.setStato("A");

			QueryRunner ins = new QueryRunner();
			// tolgo il sedime eventualmente gia duplicato nella descrizione
			Object[] paramsIns = new Object[] { 
					sst.getId().getCtrHash(),					
					sst.getCognome(),
					sst.getNome(),
					sst.getDenominazione(),
					sst.getSesso(),
					sst.getCodfisc(),
					sst.getPi(),
					sst.getDtNascita(),
					sst.getTipoPersona(),
					sst.getCodProvinciaNascita(),
					sst.getCodComuneNascita(),
					sst.getDescProvinciaNascita(),
					sst.getDescComuneNascita(),
					sst.getCodProvinciaRes(),
					sst.getCodComuneRes(),
					sst.getDescProvinciaRes(),
					sst.getDescComuneRes(),
					sst.getFkSoggetto(),
					sst.getRelDescr(),
					sst.getRating(),
					sst.getNote(),														
					sst.getIdStorico(),
					sst.getDtFineVal(),
					sst.getAnomalia(),
					sst.getStato(),
					sst.getCodiceSoggOrig(),
					
					sst.getProcessId(),
					sst.getDtInizioDato(),
					sst.getDtFineDato(),
					sst.getDtExpDato(),
					sst.getProvenienza(),
					sst.getDtInizioVal(),
					sst.getDataRegistrazione(),
					
					sst.getField1(),
					sst.getField2(),
					sst.getField3(),
					sst.getField4(),
					sst.getField5(),
					sst.getField6(),
					sst.getField7(),
					sst.getField8(),
					sst.getField9(),
					sst.getField10(),
					sst.getField11(),
					sst.getField12(),
					sst.getField13(),
					sst.getField14(),
					sst.getField15(),
					sst.getField16(),
					sst.getField17(),
					sst.getField18(),
					sst.getField19(),
					sst.getField20(),
					sst.getField21(),
					sst.getField22(),
					sst.getField23(),
					sst.getField24(),
					
					sst.getId().getIdDwh(),
					sst.getId().getFkEnteSorgente(),
					sst.getId().getProgEs()					
			};
			
			
			ins.update(conn, sql, paramsIns);
				

		}catch (SQLException sqle){
			//verifico se e' errore diverso da chiave duplicata
			if (sqle.getErrorCode() != 1 && sqle.getErrorCode() != 1401) {
				log.error("ErrorCode=" + sqle.getErrorCode() + " SQLState=" + sqle.getSQLState() );
				throw sqle;
			}
				log.warn("Aggiornamento SIT_SOGGETTO_TOTALE da Ente "+sst.getId().getFkEnteSorgente()+":"+sqle.getMessage(), sqle);
		}
		finally {
		}		
	}
	
	
	
	//Metodo per il calcolo ctrHash
	protected String setCtrHashSitSoggettoTotale(SitSoggettoTotale sst)
	{
		
		if (sst.getPi()!=null) 
			sst.setPi(sst.getPi().trim().equals("00000000000")?null:sst.getPi().trim());
		if (sst.getPi()!=null) 
			sst.setPi(sst.getPi().trim().equals("")?null:sst.getPi().trim());
		if (sst.getPi()!=null) 
			sst.setPi(sst.getPi().length()>11?null:sst.getPi().trim());
		
		sst.setPi(StringUtils.padding(sst.getPi(), 11, '0', true));
		
		if (sst.getCodfisc()!=null) 
			sst.setCodfisc(sst.getCodfisc().trim().equals("")?null:sst.getCodfisc().trim());
		if (sst.getCodfisc()!=null) 
			sst.setCodfisc(sst.getCodfisc().length()>16?null:sst.getCodfisc().trim());

		if (sst.getCognome()!=null)
			sst.setCognome(sst.getCognome().trim().equals("")?null:sst.getCognome().trim());
		if (sst.getNome()!=null)
			sst.setNome(sst.getNome().trim().equals("")?null:sst.getNome().trim());
		
		
		
		String hash = sst.getCognome()+sst.getNome()+sst.getDenominazione()+sst.getSesso()
					 +sst.getCodfisc()+sst.getPi()+sst.getDtNascita()+sst.getTipoPersona()
					 +sst.getCodProvinciaNascita()+sst.getCodComuneNascita()+sst.getDescProvinciaNascita()
					 +sst.getDescComuneNascita()+sst.getCodProvinciaRes()+sst.getCodComuneRes()
					 +sst.getDescProvinciaRes()+sst.getDescComuneRes();
		
		
		String newHash = this.getCtrHash(hash);
		
		return newHash;
	}
	
	
	
	//GESTIONE VIE
	//Metodo che salva i dati in ViaTotale
	protected void saveSitViaTotale(DatoDwh classeFonte, Connection conn, String sql, SitViaTotale svt) throws Exception
	{
		try
		{
			svt.setStato("N");
			
			if (svt.getIndirizzo()==null)
				return;
			
			QueryRunner ins = new QueryRunner();
			
			// tolgo il sedime eventualmente gia duplicato nella descrizione
			Object[] paramsIns = new Object[] { svt.getId().getIdDwh()
					,svt.getId().getFkEnteSorgente()
					,svt.getId().getCtrHash()
					,svt.getSedime()
					,svt.getIndirizzo()
					,svt.getId().getProgEs()
					,svt.getNote()
					,svt.getIdStorico()
					,svt.getDtFineVal()
					,svt.getAnomalia()
					,svt.getStato()
					,svt.getCodiceViaOrig()
					
					,svt.getField1()
					,svt.getField2()
					,svt.getField3()
					,svt.getField4()
					,svt.getField5()
					,svt.getField6()
					,svt.getField7()
					,svt.getField8()
					,svt.getField9()
					,svt.getField10()
					,svt.getField11()
					
			};
			
			ins.update(conn, sql, paramsIns);

			
		}catch (SQLException sqle){
			//verifico se e' errore diverso da chiave duplicata
			if (sqle.getErrorCode() != 1 && sqle.getErrorCode() != 1401) {
				log.error("ErrorCode=" + sqle.getErrorCode() + " SQLState=" + sqle.getSQLState() );
				throw sqle;
			}
				log.warn("Inserimento SIT_VIA_TOTALE da Ente "+svt.getId().getFkEnteSorgente()+":"+sqle.getMessage(), sqle);
				
		}
		finally {
		}
	}

	
	//Metodo che aggiorna i dati in ViaTotale
	protected void updateSitViaTotale(DatoDwh classeFonte, Connection conn, String sql, String sqlAggCiv, SitViaTotale svt) throws Exception
	{
		try
		{
			svt.setStato("A");
			
			QueryRunner ins = new QueryRunner();
			
			// tolgo il sedime eventualmente gia duplicato nella descrizione
			Object[] paramsIns = new Object[] {
					svt.getId().getCtrHash(),
					svt.getFkVia(),
					svt.getRelDescr(),
					svt.getRating(),
					svt.getSedime(),
					svt.getIndirizzo(),					
					svt.getNote(),
					svt.getIdStorico(),
					svt.getDtFineVal(),
					svt.getAnomalia(),
					svt.getStato(),
					svt.getCodiceViaOrig(),
					
					svt.getField1(),
					svt.getField2(),
					svt.getField3(),
					svt.getField4(),
					svt.getField5(),
					svt.getField6(),
					svt.getField7(),
					svt.getField8(),
					svt.getField9(),
					svt.getField10(),
					svt.getField11(),
					
					svt.getId().getIdDwh(),
					svt.getId().getFkEnteSorgente(),
					svt.getId().getProgEs()					
			};
			
			ins.update(conn, sql, paramsIns);

											
			// tolgo il sedime eventualmente gia duplicato nella descrizione
			paramsIns = new Object[] {
					svt.getId().getIdDwh(),
					svt.getId().getFkEnteSorgente(),
					svt.getId().getProgEs()
			};
			
			ins.update(conn, sqlAggCiv, paramsIns);

			
			
			
		}catch (SQLException sqle){
			//verifico se e' errore diverso da chiave duplicata
			//verifico se e' errore diverso da chiave duplicata
			if (sqle.getErrorCode() != 1 && sqle.getErrorCode() != 1401) {
				log.error("ErrorCode=" + sqle.getErrorCode() + " SQLState=" + sqle.getSQLState() );
				throw sqle;
			}
				log.warn("Aggiornamento SIT_VIA_TOTALE da Ente "+svt.getId().getFkEnteSorgente()+":"+sqle.getMessage(), sqle);
				
		}
		finally {
		}
	}
	
	
	
	protected String setCtrHashSitViaTotale(SitViaTotale svt)
	{
		String hash = svt.getSedime()+svt.getIndirizzo();
		String newHash = getCtrHash(hash);
		return newHash;
	}

	
	
	//GESTIONE OGGETTI
	//Metodo che salva i dati in OggettoTotale
	protected void saveSitOggettoTotale(DatoDwh classeFonte, Connection conn, String sql, SitOggettoTotale sot) throws Exception
	{
		try
		{
			sot.setStato("N");
			
			//QueryRunner ins = new QueryRunner();
			String foglio = sot.getFoglio();
			String particella = sot.getParticella();
			String sub = sot.getSub();
			
			if ("0000".equals(foglio) && "00000".equals(particella) && "0000".equals(sub) )
				return;
			
			Object[] paramsIns = new Object[] { 
					 sot.getId().getIdDwh()
					,sot.getId().getFkEnteSorgente()
					,sot.getId().getCtrHash()
					,sot.getId().getProgEs()
					,foglio
					,particella
					,sub
					,sot.getCategoria()
					,sot.getClasse()
					,sot.getRendita()
					,sot.getZona()
					,sot.getFoglioUrbano()
					,sot.getSuperficie()
					,sot.getScala()
					,sot.getInterno()
					,sot.getPiano()
					,sot.getDtInizioVal()
					,sot.getFkOggetto()
					,sot.getRelDescr()
					,sot.getRating()
					,sot.getNote()
					,sot.getIdStorico()
					,sot.getDtFineVal()
					,sot.getAnomalia()
					,sot.getStato()
					,sot.getSezione()
					
					,sot.getProcessId()
					,sot.getDtInizioDato()
					,sot.getDtFineDato()
					,sot.getDtExpDato()
					,sot.getProvenienza()
					,sot.getDataRegistrazione()
					
					,sot.getField1()
					,sot.getField2()
					,sot.getField3()
					,sot.getField4()
					,sot.getField5()
					,sot.getField6()
					,sot.getField7()
					,sot.getField8()
					,sot.getField9()
					,sot.getField10()
					,sot.getField11()
					,sot.getField12()
					,sot.getField13()
					,sot.getField14()
					,sot.getField15()
					,sot.getField16()
					,sot.getField17()
					,sot.getField18()
					,sot.getField19()
					,sot.getField20()
					,sot.getField21()
					,sot.getField22()
					,sot.getField23()
					,sot.getField24()
					,sot.getField25()
					,sot.getField26()
					,sot.getField27()
					,sot.getField28()
					,sot.getField29()
					,sot.getField30()
					,sot.getField31()
					
					};
			ins.update(conn, sql, paramsIns);

			
		}catch (SQLException sqle){
			//verifico se e' errore diverso da chiave duplicata
			if (sqle.getErrorCode() != 1 && sqle.getErrorCode() != 1401) {
				log.error("ErrorCode=" + sqle.getErrorCode() + " SQLState=" + sqle.getSQLState() );
				throw sqle;
			}
			else
				log.warn("Inserimento SIT_OGGETTO_TOTALE da Ente "+sot.getId().getFkEnteSorgente()+":"+sqle.getMessage());
		}
		finally {
		}
	}
	
	
	//Metodo che salva i dati in OggettoTotale
	protected void updateSitOggettoTotale(DatoDwh classeFonte, Connection conn, String sql, SitOggettoTotale sot) throws Exception
	{
		try
		{
			sot.setStato("A");
			
			QueryRunner ins = new QueryRunner();
			String foglio = sot.getFoglio();
			String particella = sot.getParticella();
			String sub = sot.getSub();
			
			Object[] paramsIns = new Object[] { 
					sot.getId().getCtrHash(),					
					foglio,
					particella,
					sub,
					sot.getCategoria(),
					sot.getClasse(),
					sot.getRendita(),
					sot.getZona(),
					sot.getFoglioUrbano(),
					sot.getSuperficie(),
					sot.getScala(),
					sot.getInterno(),
					sot.getPiano(),
					sot.getDtInizioVal(),
					sot.getFkOggetto(),
					sot.getRelDescr(),
					sot.getRating(),
					sot.getNote(),					
					sot.getIdStorico(),
					sot.getDtFineVal(),
					sot.getAnomalia(),
					sot.getStato(),
					sot.getSezione(),
					
					sot.getProcessId(),
					sot.getDtInizioDato(),
					sot.getDtFineDato(),
					sot.getDtExpDato(),
					sot.getProvenienza(),
					sot.getDataRegistrazione(),
					
					sot.getField1(),
					sot.getField2(),
					sot.getField3(),
					sot.getField4(),
					sot.getField5(),
					sot.getField6(),
					sot.getField7(),
					sot.getField8(),
					sot.getField9(),
					sot.getField10(),
					sot.getField11(),
					sot.getField12(),
					sot.getField13(),
					sot.getField14(),
					sot.getField15(),
					sot.getField16(),
					sot.getField17(),
					sot.getField18(),
					sot.getField19(),
					sot.getField20(),
					sot.getField21(),
					sot.getField22(),
					sot.getField23(),
					sot.getField24(),
					sot.getField25(),
					sot.getField26(),
					sot.getField27(),
					sot.getField28(),
					sot.getField29(),
					sot.getField30(),
					sot.getField31(),
					
					sot.getId().getIdDwh(),
					sot.getId().getFkEnteSorgente(),
					sot.getId().getProgEs(),
					sot.getId().getCtrHash()
					};
			ins.update(conn, sql, paramsIns);

			
		}catch (SQLException sqle){
			//verifico se e' errore diverso da chiave duplicata
			if (sqle.getErrorCode() != 1 && sqle.getErrorCode() != 1401) {
				log.error("ErrorCode=" + sqle.getErrorCode() + " SQLState=" + sqle.getSQLState() );
				throw sqle;
			}
				log.warn("Aggiornamento SIT_OGGETTO_TOTALE da Ente "+sot.getId().getFkEnteSorgente()+":"+sqle.getMessage());
		}
		finally {
		}
	}
	
	protected String setCtrHashSitOggettoTotale(SitOggettoTotale sot)
	{
		String hash = sot.getSezione()+sot.getFoglio()+sot.getParticella()+sot.getSub()+sot.getCategoria()+sot.getClasse()+sot.getRendita()+sot.getFoglioUrbano()+sot.getScala()+sot.getPiano()+sot.getInterno()+sot.getZona()+sot.getSuperficie();
		String newHash = getCtrHash(hash);		
		return newHash;
	}
	
	
	protected static boolean executeIsDropDWh(String sql, Connection conn, int fkEnteSorgente, int prog_es) throws Exception{
		
		boolean drop = false;
		PreparedStatement prs = null;
		ResultSet res = null;		
		
		try{
					
			
			prs = conn.prepareStatement(sql);
			prs.setInt(1, fkEnteSorgente);		
			prs.setInt(2, prog_es);
			
			res = prs.executeQuery();
			
			if (res.next()){
				drop = true;
			}else{
				drop = false;
			}			
			
		}catch (SQLException sqle){
			throw new RulEngineException("Errore Controllo Drop tabella DWH per ente "+fkEnteSorgente+":"+sqle.getMessage());
		}finally{
			if(res!=null)
				res.close();
			if(prs!=null)
				prs.close();
		}
			
		return drop;
		
	}		
	
	
	
	//Metodo che esegue la cancellazione di una collezione di civici da tabella Totale
	protected void deleteCollezioneCivici(SitCivicoTotale sct, String sql, Connection conn, Collection<Civico> colCiv) throws Exception{
	
		try{							
						
			//Determino la lista di civici
			Iterator< Civico> iter = colCiv.iterator();			
			String listaHash = "";
			
			while(iter.hasNext()){										
				
				Civico civ =iter.next();
						
				sct.setCivLiv1(civ.getCivLiv1());
									
				String hash = setCtrHashSitCivicoTotale(sct);
				
				if(!"".equals(listaHash)){
					listaHash = listaHash + ",";	
				}				
				
				listaHash = listaHash + "'" + hash + "'";
				
			}
			
			// nessun civico
			if("".equals(listaHash))
				return;
			
			sql = sql.replace("$lista", listaHash);
			
			//Cancello record non presenti in collezione
			PreparedStatement ps = null;
			ps = conn.prepareStatement(sql);
			ps.setString(1, sct.getId().getIdDwh());
			ps.setLong(2, sct.getId().getFkEnteSorgente());
			ps.setLong(3, sct.getId().getProgEs());											
			
			
			ps.executeUpdate();
			
						
			ps.close();
			
			
		}catch (SQLException sqle){
			log.error("deleteCollezioneCivici sql :" + sql);
			throw new RulEngineException("Delete Collezione Civici per ente "+sct.getId().getFkEnteSorgente()+":"+sqle.getMessage());
		}
		
	}
	
	/**
	 * Metodo che legge il flag codice originario
	 */
	protected boolean getCodiceOriginario(String key, String codEnte, String fonte, String progEs, HashParametriConfBean paramConfBean) throws Exception{
		
		boolean codiceOrig = false;
		Hashtable<String, Object> hashTabParam = null;
						
		
		try{
			
			if(key.equals("codice.orig.soggetto")){
				hashTabParam = paramConfBean.getCodiceOrigSogg();
			}else if(key.equals("codice.orig.via")){
				hashTabParam = paramConfBean.getCodiceOrigVie();
			}else if(key.equals("codice.orig.civico")){
				hashTabParam = paramConfBean.getCodiceOrigCiv();
			}
			
			if(hashTabParam!=null){
				
				String chiave = key+fonte;
								
				String parCodOrig = (String)hashTabParam.get(chiave);
				
				String[] temp = null;
				
				if(parCodOrig!=null){
					
					temp = parCodOrig.split(",");
					
					for(int i =0; i < temp.length ; i++){
						if(temp[i].equals(progEs)){
							codiceOrig = true;
							break;
						}				
					}						
				}	
			}									
			
		}catch (Exception e) {
			throw new RulEngineException("Errore in reperimento Codice originario per ente "+codEnte+" fonte "+fonte+" :"+e.getMessage());
		}
		
		return codiceOrig; 
	}

	
	//GESTIONE FABBRICATI
	//Metodo che salva i dati in FabbricatoTotale
	protected void saveSitFabbricatoTotale(DatoDwh classeFonte, Connection conn, String sql, SitFabbricatoTotale sft) throws Exception
	{
		try
		{
			sft.setStato("N");
			
			QueryRunner ins = new QueryRunner();
			String foglio = sft.getFoglio();
			String particella = sft.getParticella();			
			
			
			if ("0000".equals(foglio) && "00000".equals(particella))
				return;
			
			Object[] paramsIns = new Object[] { 
					sft.getId().getIdDwh()
					,sft.getId().getFkEnteSorgente()
					,sft.getId().getCtrHash()
					,sft.getId().getProgEs()
					,foglio
					,particella
					,sft.getCategoria()
					,sft.getClasse()
					,sft.getRendita()
					,sft.getZona()
					,sft.getFoglioUrbano()
					,sft.getSuperficie()
					,sft.getScala()
					,sft.getInterno()
					,sft.getPiano()
					,sft.getDtInizioVal()
					,sft.getFkFabbricato()
					,sft.getRelDescr()
					,sft.getRating()
					,sft.getNote()
					,sft.getIdStorico()
					,sft.getDtFineVal()
					,sft.getAnomalia()
					,sft.getStato()
					,sft.getSezione()
					
					,sft.getProcessId()
					,sft.getDtInizioDato()
					,sft.getDtFineDato()
					,sft.getDtExpDato()
					,sft.getProvenienza()
					,sft.getDataRegistrazione()
					
					,sft.getField1()
					,sft.getField2()
					,sft.getField3()
					,sft.getField4()
					,sft.getField5()
					,sft.getField6()
					,sft.getField7()
					,sft.getField8()
					,sft.getField9()
					,sft.getField10()
					,sft.getField11()
					,sft.getField12()
					,sft.getField13()
					,sft.getField14()
					,sft.getField15()
					,sft.getField16()
					,sft.getField17()
					,sft.getField18()
					,sft.getField19()
					,sft.getField20()
					,sft.getField21()
					,sft.getField22()
					,sft.getField23()
					,sft.getField24()
					,sft.getField25()
					,sft.getField26()
					,sft.getField27()
					,sft.getField28()
					,sft.getField29()
					,sft.getField30()
					,sft.getField31()
					
					};
			ins.update(conn, sql, paramsIns);

			
		}catch (SQLException sqle){
			//verifico se e' errore diverso da chiave duplicata
			if (sqle.getErrorCode() != 1 && sqle.getErrorCode() != 1401) {
				log.error("ErrorCode=" + sqle.getErrorCode() + " SQLState=" + sqle.getSQLState() );
				throw sqle;
			}
				log.warn("Inserimento SIT_FABBRICATO_TOTALE da Ente "+sft.getId().getFkEnteSorgente()+":"+sqle.getMessage());
		}
		finally {
		}
	}
	
	
	//Metodo che salva i dati in OggettoTotale
	protected void updateSitFabbricatoTotale(DatoDwh classeFonte, Connection conn, String sql, SitFabbricatoTotale sft) throws Exception
	{
		try
		{
			sft.setStato("A");
			
			QueryRunner ins = new QueryRunner();
			String foglio = sft.getFoglio();
			String particella = sft.getParticella();			
			
			Object[] paramsIns = new Object[] { 
					sft.getId().getCtrHash(),					
					foglio,
					particella,				
					sft.getCategoria(),
					sft.getClasse(),
					sft.getRendita(),
					sft.getZona(),
					sft.getFoglioUrbano(),
					sft.getSuperficie(),
					sft.getScala(),
					sft.getInterno(),
					sft.getPiano(),
					sft.getDtInizioVal(),
					sft.getFkFabbricato(),
					sft.getRelDescr(),
					sft.getRating(),
					sft.getNote(),					
					sft.getIdStorico(),
					sft.getDtFineVal(),
					sft.getAnomalia(),
					sft.getStato(),
					sft.getSezione(),
					
					sft.getProcessId(),
					sft.getDtInizioDato(),
					sft.getDtFineDato(),
					sft.getDtExpDato(),
					sft.getProvenienza(),
					sft.getDataRegistrazione(),
					
					sft.getField1(),
					sft.getField2(),
					sft.getField3(),
					sft.getField4(),
					sft.getField5(),
					sft.getField6(),
					sft.getField7(),
					sft.getField8(),
					sft.getField9(),
					sft.getField10(),
					sft.getField11(),
					sft.getField12(),
					sft.getField13(),
					sft.getField14(),
					sft.getField15(),
					sft.getField16(),
					sft.getField17(),
					sft.getField18(),
					sft.getField19(),
					sft.getField20(),
					sft.getField21(),
					sft.getField22(),
					sft.getField23(),
					sft.getField24(),
					sft.getField25(),
					sft.getField26(),
					sft.getField27(),
					sft.getField28(),
					sft.getField29(),
					sft.getField30(),
					sft.getField31(),
					
					sft.getId().getIdDwh(),
					sft.getId().getFkEnteSorgente(),
					sft.getId().getProgEs(),
					sft.getId().getCtrHash()
					};
			ins.update(conn, sql, paramsIns);

			
		}catch (SQLException sqle){
			//verifico se e' errore diverso da chiave duplicata
			if (sqle.getErrorCode() != 1 && sqle.getErrorCode() != 1401) {
				log.error("ErrorCode=" + sqle.getErrorCode() + " SQLState=" + sqle.getSQLState() );
				throw sqle;
			}
				log.warn("Aggiornamento SIT_FABBRICATO_TOTALE da Ente "+sft.getId().getFkEnteSorgente()+":"+sqle.getMessage());
		}
		finally {
		}
	}
	
	protected String setCtrHashSitFabbricatoTotale(SitFabbricatoTotale sft)
	{
		String hash = sft.getSezione()+sft.getFoglio()+sft.getParticella()+sft.getCategoria()+sft.getClasse()+sft.getRendita()+sft.getFoglioUrbano()+sft.getScala()+sft.getPiano()+sft.getInterno()+sft.getZona()+sft.getSuperficie();
		String newHash = getCtrHash(hash);		
		return newHash;
	}
	
}
