package it.webred.ct.proc.ario.fonti.soggetto.versamenti.iciDM;


import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitSoggettoTotale;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.fonti.soggetto.Soggetto;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SoggettoViol extends DatoDwh implements Soggetto  {

	
	private Properties props = null;
	private Properties propsCodSogg = null;
	
	protected static final Logger log = Logger.getLogger(SoggettoViol.class.getName());
	
	
	public SoggettoViol(){
		
		//Caricamento del file di properties dei caricatori		
		this.props = new Properties();
		this.propsCodSogg = new Properties();
		try {
			InputStream is = this.getClass().getResourceAsStream("/sql/caricatori.sql");
		    this.props.load(is);  
		    
		    InputStream isCodSogg = this.getClass().getResourceAsStream("/it/webred/ct/proc/ario/fonti/soggetto/criteriAssociazioneSoggetti.properties");
		    this.propsCodSogg.load(isCodSogg);  
		}catch(Exception e) {
		    log.error("Eccezione: "+e.getMessage(), e);			   
		}																
	}
	
	
	@Override
	//Metodo che indica se la tabella è gestita o no tramite PROCESSID
	public boolean existProcessId(){
		return true;
	}
		
	@Override
	//Metodo che restiuisce la chiave dell'ente sorgente 
	public int getFkEnteSorgente() {
	
		return 37;
	}
	
	
	@Override
	//Metodo che restituisce in numero di fonte del caricatore
	public int getProgEs() {
 
		return 2;
	}
	
	
	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {

		String sqlSoggettoVersContrib = this.getProperty("SQL_SOGG_ICI_DM_VIOL");
				
		if (processID != null && !processID.equals("")){
			sqlSoggettoVersContrib = sqlSoggettoVersContrib + " WHERE PROCESSID =?";
		}
			
		return sqlSoggettoVersContrib;
				
	}

	
	
	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		
		//Tabella del DHW da cui leggere i dati
		String tabella = "SIT_T_ICI_DM_VIOLAZIONE";		
		return tabella;
	}
	
	
	@Override
	//Metodo che mappa normalizza e salva i dati
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert, String insSoggettoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

		SitSoggettoTotale sst = new SitSoggettoTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
		
		try{	
			
			sst.setIdStorico(rs.getString("ID_STORICO"));
			sst.setDtFineVal(rs.getDate("DT_FINE_VAL"));
			sst.setCodfisc(rs.getString("COD_FISC"));
			sst.setPi(rs.getString("PI"));
			sst.setTipoPersona(rs.getString("TIPO_PERSONA"));
			sst.setDenominazione(rs.getString("DENOMINAZIONE"));
			sst.setNome(rs.getString("NOME"));
			sst.setCognome(rs.getString("COGNOME"));
			
			sst.setCodProvinciaRes(rs.getString("COD_PROVINCIA_RES"));
			sst.setCodComuneRes(rs.getString("COD_COMUNE_RES"));
			sst.setDescProvinciaRes(rs.getString("DESC_PROVINCIA_RES"));
			sst.setDescComuneRes(rs.getString("DESC_COMUNE_RES"));		
			
			sst.setProcessId(rs.getString("PROCESSID"));
			sst.setDtInizioDato(rs.getDate("DT_INIZIO_DATO"));
			sst.setDtFineDato(rs.getDate("DT_FINE_DATO"));
			sst.setDtExpDato(rs.getDate("DT_EXP_DATO"));
			sst.setDtInizioVal(rs.getDate("DT_INIZIO_VAL"));
			sst.setField1(rs.getString("FIELD1"));
			sst.setField2(rs.getString("FIELD2"));
			sst.setField3(rs.getString("FIELD3"));	
			sst.setField4(rs.getString("FIELD4"));
			sst.setField5(rs.getString("FIELD5"));
			sst.setField6(rs.getString("FIELD6"));
									
			//Normalizzazione Dati
			nt.normalizzaSoggetto(sst);
			
			//Setto indice Soggetto
			iPk.setIdDwh(rs.getString("ID_DWH"));
			iPk.setFkEnteSorgente(this.getFkEnteSorgente());
			iPk.setProgEs(this.getProgEs());							
			//Calcolo Hash di chiave
			iPk.setCtrHash(setCtrHashSitSoggettoTotale(sst));		
			
			sst.setId(iPk);
			
			//Salvataggio
			super.saveSitSoggettoTotale(classeFonte, connForInsert, insSoggettoTotale, sst);
			
		}catch (Exception e) {
			log.warn("Errore:Save Soggetto Contribuente Violazioni ICI DM="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Save Soggetto Violazioni ICI DM:"+e.getMessage());
			throw ea;
		}
		
	}
	
	
	@Override 
	//Metodo che mappa normalizza e aggiorna i dati
	public void prepareUpdateDato(DatoDwh classeFonte, Connection connForInsert, String insSoggettoTotale, String updateSoggettoTotale, String searchSoggettoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

		SitSoggettoTotale sst = new SitSoggettoTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
		
		try{			
									
			sst.setIdStorico(rs.getString("ID_STORICO"));
			sst.setDtFineVal(rs.getDate("DT_FINE_VAL"));
			sst.setCodfisc(rs.getString("COD_FISC"));
			sst.setPi(rs.getString("PI"));
			sst.setTipoPersona(rs.getString("TIPO_PERSONA"));
			sst.setDenominazione(rs.getString("DENOMINAZIONE"));
			sst.setNome(rs.getString("NOME"));
			sst.setCognome(rs.getString("COGNOME"));
			
			sst.setCodProvinciaRes(rs.getString("COD_PROVINCIA_RES"));
			sst.setCodComuneRes(rs.getString("COD_COMUNE_RES"));
			sst.setDescProvinciaRes(rs.getString("DESC_PROVINCIA_RES"));
			sst.setDescComuneRes(rs.getString("DESC_COMUNE_RES"));
			sst.setFkSoggetto(null);
			sst.setRelDescr(null);
			sst.setRating(null);				
			
			sst.setProcessId(rs.getString("PROCESSID"));
			sst.setDtInizioDato(rs.getDate("DT_INIZIO_DATO"));
			sst.setDtFineDato(rs.getDate("DT_FINE_DATO"));
			sst.setDtExpDato(rs.getDate("DT_EXP_DATO"));
			sst.setDtInizioVal(rs.getDate("DT_INIZIO_VAL"));
			sst.setField1(rs.getString("FIELD1"));
			sst.setField2(rs.getString("FIELD2"));
			sst.setField3(rs.getString("FIELD3"));	
			sst.setField4(rs.getString("FIELD4"));
			sst.setField5(rs.getString("FIELD5"));
			sst.setField6(rs.getString("FIELD6"));
			
			//Normalizzazione Dati
			nt.normalizzaSoggetto(sst);			
			
			//Setto indice Soggetto
			iPk.setIdDwh(rs.getString("ID_DWH"));
			iPk.setFkEnteSorgente(this.getFkEnteSorgente());
			iPk.setProgEs(this.getProgEs());							
			//Calcolo Hash di chiave
			iPk.setCtrHash(setCtrHashSitSoggettoTotale(sst));		
			
			sst.setId(iPk);
					
			//System.out.println("id_dwh "+iPk.getIdDwh());
			
			
			String azione = super.trovaDatoTotale(classeFonte, connForInsert,searchSoggettoTotale,iPk);								
			
			if(azione.equals("AGGIORNA")){
				//Aggiorna elemento già presente				
				super.updateSitSoggettoTotale(classeFonte,connForInsert,updateSoggettoTotale,sst);										
			}else if(azione.equals("INSERISCI")){
				//Salva il nuovo elemento					
				super.saveSitSoggettoTotale(classeFonte, connForInsert, insSoggettoTotale, sst);										
			}
			
		}catch (Exception e) {
			log.warn("Errore:Save Soggetto Contribuente Violazioni ICI DM="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Save Soggetto Violazioni Versamenti ICI DM:"+e.getMessage());
			throw ea;
		}
		
	}
	
	
	//Metodo che indica se la query di lettura da DWH ha come parametro il CodiceEnte (F704)
	public boolean queryWithParamCodEnte(){
		return false;
	}

	
	//Metodo che restituisce la query di cancellazione tabella Totale
	public String getDeleteSQL(){
		
		String sqlDeleteTot = this.getProperty("SQL_DEL_SOGG_TOTALE");		
		return sqlDeleteTot;
	}
	
	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
		
		String sqlInsertTot = this.getProperty("SQL_INS_SOGG_TOTALE");		
		return sqlInsertTot;
	}
	
	//Metodo che restituisce la query di update in tabella Totale
	public String getUpdateSQL(){
		
		String sqlUpdateTot = this.getProperty("SQL_UPDATE_SOGG_TOTALE");		
		return sqlUpdateTot;
	}
	
	//Metodo che restituisce la query di ricerca in tabella Totale
	public String getSearchSQL(){
		
		String sqlSearchTot = this.getProperty("SQL_CERCA_SOGG_TOTALE");		
		return sqlSearchTot;
	}
	
	
	
	
	/**
	 * Metodo che controlla se la tabella del DWH è stata droppata
	 */
	@Override
	public boolean dwhIsDrop(Connection conn) throws Exception {
		
		String sql = this.getProperty("SQL_DWH_IS_DROP_SOGG");
		
		sql = sql.replace("$TAB", this.getTable());
		
		int fkEnteSorgente = this.getFkEnteSorgente();
		int progEs = this.getProgEs();
		
		return super.executeIsDropDWh(sql, conn, fkEnteSorgente, progEs);
	}

	

	@Override
	//Metodo che restituisce la query per l'inserimento del PID elaborato per gestione con PID
	public String getQuerySQLSaveProcessId() throws Exception {
		
		String query = this.getProperty("SQL_INSERT_PID_SOGG");
			
		return query;
	}

	@Override
	//Metodo che restituisce la query per l'update del PID elaborato per gestione con PID
	public String getQuerySQLUpdateProcessId() throws Exception {
			
		String query = this.getProperty("SQL_UPDATE_PID_SOGG");
		
		return query;
	}
	
	@Override
	//Metodo che restituisce la query dei nuovi processId da caricare
	public String getQuerySQLNewProcessId() throws Exception {

		String query = this.getProperty("SQL_NEW_PID_DWH_SOGG");
		
		return query;
	}
	
	//Metodo che restituisce la query per trovare i PID della tabella DWH
	@Override
	public String getQuerySQLgetProcessId() throws Exception {
		
		String query = this.getProperty("SQL_GET_PID_DWH_SOGG");
		
		return query;
	}
	
	//Metodo che restituisce la query che aggiorna e cancella un processId dalla tabella di gestione PID
	@Override
	public String getQuerySQLDeleteProcessId() throws Exception {
		
		String query = this.getProperty("SQL_DELETE_PID_SOGG");
		
		return query;
	}

	

	//Metodo che restituisce la query di caricamento letta da file di property
	private String getProperty(String propName) {

		String p = this.props.getProperty(propName+ "." + this.getFkEnteSorgente());
		
		if (p==null)
			p = this.props.getProperty(propName);
			
		return p;
	}
	
	
	@Override
	//Metodo che stabilisce se viene o no fornito il codice originario del soggetto
	public boolean codiceSoggetto(String codEnte, HashParametriConfBean paramConfBean) throws Exception{
			
		//La fonte non gestisce il soggetto di origine
		return false;
	}
}
