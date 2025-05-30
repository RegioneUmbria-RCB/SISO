package it.webred.ct.proc.ario.fonti.soggetto.licenzeCommerciali;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitSoggettoTotale;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.fonti.soggetto.Soggetto;
import it.webred.ct.proc.ario.fonti.soggetto.tributi.SoggettoTributiIci;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;

public class SoggetoLicenzeCommerciali extends DatoDwh implements Soggetto{

	private Properties props = null;
	
	protected static final Logger log = Logger.getLogger(SoggetoLicenzeCommerciali.class.getName());
	
	
	public SoggetoLicenzeCommerciali(){
		
		//Caricamento del file di properties dei caricatori		
		this.props = new Properties();
		
		try {
			InputStream is = this.getClass().getResourceAsStream("/sql/caricatori.sql");
		    this.props.load(is);  
		}catch(Exception e) {
		    log.error("Eccezione: "+e.getMessage(), e);			   
		}																
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
	public boolean existProcessId() {		
		return true;
	}


	@Override
	public String getDeleteSQL() {
		String sqlDeleteTot = this.getProperty("SQL_DEL_SOGG_TOTALE");		
		return sqlDeleteTot;
	}

	@Override
	public int getFkEnteSorgente() {
		
		return 13;
	}

	@Override
	public String getInsertSQL() {
		String sqlInsertTot = this.getProperty("SQL_INS_SOGG_TOTALE");		
		return sqlInsertTot;
	}

	@Override
	public int getProgEs() {
		
		return 1;
	}

	//Metodo che restituisce la query che aggiorna e cancella un processId dalla tabella di gestione PID
	@Override
	public String getQuerySQLDeleteProcessId() throws Exception {
		
		String query = this.getProperty("SQL_DELETE_PID_SOGG");
		
		return query;
	}

	//Metodo che restituisce la query per trovare i PID della tabella DWH
	@Override
	public String getQuerySQLgetProcessId() throws Exception {
		
		String query = this.getProperty("SQL_GET_PID_DWH_SOGG");
		
		return query;
	}

	@Override
	//Metodo che restituisce la query dei nuovi processId da caricare
	public String getQuerySQLNewProcessId() throws Exception {

		String query = this.getProperty("SQL_NEW_PID_DWH_SOGG");
		
		return query;
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
	public String getSearchSQL() {
		String sqlSearchTot = this.getProperty("SQL_CERCA_SOGG_TOTALE");		
		return sqlSearchTot;
	}

	@Override
	public String getSql(String processID) {
		
		String sqlSoggetto = this.getProperty("SQL_SOGG_LIC_COMM");
		
		if (processID != null && !processID.equals("")){
			sqlSoggetto = sqlSoggetto + " WHERE PROCESSID =?";
		}
			
		return sqlSoggetto;
	
	}

	@Override
	public String getTable() {
		
		//Tabella del DHW da cui leggere i dati
		String tabella = "SIT_LICENZE_COMMERCIO_ANAG";		
		return tabella;
	}

	@Override
	public String getUpdateSQL() {
		String sqlUpdateTot = this.getProperty("SQL_UPDATE_SOGG_TOTALE");		
		return sqlUpdateTot;
	}

	@Override
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert,String insSoggettoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {
		
		SitSoggettoTotale sst = new SitSoggettoTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		try{			
								
			sst.setIdStorico(rs.getString("ID_STORICO"));
			sst.setDtFineVal(rs.getDate("DT_FINE_VAL"));			
			sst.setNome(rs.getString("NOME"));
			sst.setCognome(rs.getString("COGNOME"));
			sst.setDenominazione(rs.getString("DENOMINAZIONE"));
			sst.setSesso(rs.getString("SESSO"));
			sst.setCodfisc(rs.getString("COD_FISC"));
			sst.setPi(rs.getString("PI"));
			sst.setDtNascita(rs.getDate("DT_NASCITA"));
			sst.setTipoPersona(rs.getString("TIPO_PERSONA"));
			sst.setCodProvinciaNascita(rs.getString("COD_PROVINCIA_NASCITA"));
			sst.setCodComuneNascita(rs.getString("COD_COMUNE_NASCITA"));
			sst.setDescProvinciaNascita(rs.getString("DESC_PROVINCIA_NASCITA"));
			sst.setDescComuneNascita(rs.getString("DESC_COMUNE_NASCITA"));
			sst.setCodProvinciaRes(rs.getString("COD_PROVINCIA_RES"));
			sst.setCodComuneRes(rs.getString("COD_COMUNE_RES"));
			sst.setDescProvinciaRes(rs.getString("DESC_PROVINCIA_RES"));
			sst.setDescComuneRes(rs.getString("DESC_COMUNE_RES"));			
			
			//Setto il codice Soggetto se impostato da configurazione
			boolean codSogg = this.codiceSoggetto(codEnte, paramConfBean);			
			if(codSogg){
				sst.setCodiceSoggOrig(rs.getString("CODICE_SOGGETTO"));
			}else{
				sst.setCodiceSoggOrig(null);
			}
				
			
			sst.setProcessId(rs.getString("PROCESSID"));
			sst.setDtInizioDato(rs.getDate("DT_INIZIO_DATO"));
			sst.setDtFineDato(rs.getDate("DT_FINE_DATO"));
			sst.setDtExpDato(rs.getDate("DT_EXP_DATO"));
			sst.setProvenienza(rs.getString("PROVENIENZA"));
			sst.setDtInizioVal(rs.getDate("DT_INIZIO_VAL"));
			sst.setDataRegistrazione(rs.getDate("DATA_REGISTRAZIONE"));
			sst.setField1(rs.getString("FIELD1"));
			sst.setField2(rs.getString("FIELD2"));
			sst.setField3(rs.getString("FIELD3"));
			sst.setField4(rs.getString("FIELD4"));
			sst.setField5(rs.getString("FIELD5"));
			sst.setField6(rs.getString("FIELD6"));
			sst.setField7(rs.getString("FIELD7"));
			sst.setField8(rs.getString("FIELD8"));
			sst.setField9(rs.getString("FIELD9"));
			sst.setField10(rs.getString("FIELD10"));
			sst.setField11(rs.getString("FIELD11"));
			sst.setField12(rs.getString("FIELD12"));
			
			
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
			log.warn("Errore:Save Soggetto Licenze Commerciali="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Save Soggetto Licenze Commerciali:"+e.getMessage());
			throw ea;
		}
			
	}

	@Override
	public void prepareUpdateDato(DatoDwh classeFonte,Connection connForInsert, String insSoggettoTotale, String updateSoggettoTotale,String searchSoggettoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {
		
		
		SitSoggettoTotale sst = new SitSoggettoTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		try{			
					
			sst.setIdStorico(rs.getString("ID_STORICO"));
			sst.setDtFineVal(rs.getDate("DT_FINE_VAL"));			
			sst.setNome(rs.getString("NOME"));
			sst.setCognome(rs.getString("COGNOME"));
			sst.setDenominazione(rs.getString("DENOMINAZIONE"));
			sst.setSesso(rs.getString("SESSO"));
			sst.setCodfisc(rs.getString("COD_FISC"));
			sst.setPi(rs.getString("PI"));
			sst.setDtNascita(rs.getDate("DT_NASCITA"));
			sst.setTipoPersona(rs.getString("TIPO_PERSONA"));
			sst.setCodProvinciaNascita(rs.getString("COD_PROVINCIA_NASCITA"));
			sst.setCodComuneNascita(rs.getString("COD_COMUNE_NASCITA"));
			sst.setDescProvinciaNascita(rs.getString("DESC_PROVINCIA_NASCITA"));
			sst.setDescComuneNascita(rs.getString("DESC_COMUNE_NASCITA"));
			sst.setCodProvinciaRes(rs.getString("COD_PROVINCIA_RES"));
			sst.setCodComuneRes(rs.getString("COD_COMUNE_RES"));
			sst.setDescProvinciaRes(rs.getString("DESC_PROVINCIA_RES"));
			sst.setDescComuneRes(rs.getString("DESC_COMUNE_RES"));
			sst.setFkSoggetto(null);
			sst.setRelDescr(null);
			sst.setRating(null);				
			
			//Setto il codice soggetto se impostato da configurazione
			boolean codSogg = this.codiceSoggetto(codEnte, paramConfBean);			
			if(codSogg){
				sst.setCodiceSoggOrig(rs.getString("CODICE_SOGGETTO"));
			}else{
				sst.setCodiceSoggOrig(null);
			}	
			
			
			sst.setProcessId(rs.getString("PROCESSID"));
			sst.setDtInizioDato(rs.getDate("DT_INIZIO_DATO"));
			sst.setDtFineDato(rs.getDate("DT_FINE_DATO"));
			sst.setDtExpDato(rs.getDate("DT_EXP_DATO"));
			sst.setProvenienza(rs.getString("PROVENIENZA"));
			sst.setDtInizioVal(rs.getDate("DT_INIZIO_VAL"));
			sst.setDataRegistrazione(rs.getDate("DATA_REGISTRAZIONE"));
			sst.setField1(rs.getString("FIELD1"));
			sst.setField2(rs.getString("FIELD2"));
			sst.setField3(rs.getString("FIELD3"));
			sst.setField4(rs.getString("FIELD4"));
			sst.setField5(rs.getString("FIELD5"));
			sst.setField6(rs.getString("FIELD6"));
			sst.setField7(rs.getString("FIELD7"));
			sst.setField8(rs.getString("FIELD8"));
			sst.setField9(rs.getString("FIELD9"));
			sst.setField10(rs.getString("FIELD10"));
			sst.setField11(rs.getString("FIELD11"));
			sst.setField12(rs.getString("FIELD12"));
			
						
			//Normalizzazione Dati
			nt.normalizzaSoggetto(sst);			
			
			//Setto indice Soggetto
			iPk.setIdDwh(rs.getString("ID_DWH"));
			iPk.setFkEnteSorgente(this.getFkEnteSorgente());
			iPk.setProgEs(this.getProgEs());							
			//Calcolo Hash di chiave
			iPk.setCtrHash(setCtrHashSitSoggettoTotale(sst));		
			
			sst.setId(iPk);
					
						
			String azione = super.trovaDatoTotale(classeFonte, connForInsert,searchSoggettoTotale,iPk);								
			
			if(azione.equals("AGGIORNA")){
				//Aggiorna elemento già presente				
				super.updateSitSoggettoTotale(classeFonte,connForInsert,updateSoggettoTotale,sst);										
			}else if(azione.equals("INSERISCI")){
				//Salva il nuovo elemento					
				super.saveSitSoggettoTotale(classeFonte, connForInsert, insSoggettoTotale, sst);										
			}
			
		}catch (Exception e) {
			log.warn("Errore:Update Soggetto Licenze Commerciali="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Update Soggetto Licenze Commerciali:"+e.getMessage());
			throw ea;
		}				
		
	}
	
	@Override
	public boolean codiceSoggetto(String codEnte, HashParametriConfBean paramConfBean) throws Exception {
		
		boolean codSoggettoUsato = false;
		
		try{
			
			Integer enteSorgente = this.getFkEnteSorgente();
			String fonte = String.valueOf(enteSorgente);
			
			String progEs = String.valueOf(this.getProgEs());			
			
			codSoggettoUsato = super.getCodiceOriginario("codice.orig.soggetto", codEnte, fonte, progEs, paramConfBean);
			
		}catch (Exception e) {
			log.warn("Errore:Recupero Codice Originario Soggetti="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Recupero Codice Originario Soggetti:"+e.getMessage());
			throw ea;
		}
		
		  		
		return codSoggettoUsato;
	}

	
	//Metodo che indica se la query di lettura da DWH ha come parametro il CodiceEnte (Es: F704)
	public boolean queryWithParamCodEnte() {	
		return false;
	}
	
	
	//Metodo che restituisce la query di caricamento letta da file di property
	private String getProperty(String propName) {

		String p = this.props.getProperty(propName+ "." + this.getFkEnteSorgente());
		
		if (p==null)
			p = this.props.getProperty(propName);
			
		return p;
	}
	
	
}
