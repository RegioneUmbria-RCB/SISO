package it.webred.ct.proc.ario.fonti.soggetto.redditi;

import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitSoggettoTotale;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.fonti.soggetto.Soggetto;
import it.webred.ct.proc.ario.fonti.soggetto.successioni.SoggettoSuccessioniDefunto;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SoggettoRedditiDichiarante extends DatoDwh implements Soggetto{

	private Properties props = null;
	protected static final Logger log = Logger.getLogger(SoggettoRedditiDichiarante.class.getName());
	
	
	public SoggettoRedditiDichiarante(){
		
		//Caricamento del file di properties dei caricatori		
		this.props = new Properties();
		try {
			InputStream is = this.getClass().getResourceAsStream("/sql/caricatori.sql");
		    this.props.load(is);                     
		}catch(Exception e) {
		    log.error("Eccezione: "+e.getMessage());			   
		}																
	}
	
	
	
	@Override
	public boolean dwhIsDrop(Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	//Metodo che indica se la tabella è gestita o no tramite PROCESSID
	public boolean existProcessId(){
		return false;
	}


	//Metodo che restituisce la query di cancellazione tabella Totale
	public String getDeleteSQL(){
		
		String sqlDeleteTot = this.getProperty("SQL_DEL_SOGG_TOTALE");		
		return sqlDeleteTot;
	}

	@Override
	public int getFkEnteSorgente() {
		
		return 11;
	}

	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
		
		String sqlInsertTot = this.getProperty("SQL_INS_SOGG_TOTALE");		
		return sqlInsertTot;
	}

	@Override
	public int getProgEs() {
		
		return 1;
	}

	@Override
	public String getQuerySQLDeleteProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuerySQLgetProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuerySQLNewProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuerySQLSaveProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuerySQLUpdateProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	//Metodo che restituisce la query di ricerca in tabella Totale
	public String getSearchSQL(){
		
		String sqlSearchTot = this.getProperty("SQL_CERCA_SOGG_TOTALE");		
		return sqlSearchTot;
	}

	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {

		String sqlSoggetto = this.getProperty("SQL_SOGG_REDDITI_DICHIARANTE");
								
		return sqlSoggetto;
				
	}

	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		
		//Tabella del DHW da cui leggere i dati
		String tabella = "RED_DATI_ANAGRAFICI";		
		return tabella;
	}

	//Metodo che restituisce la query di update in tabella Totale
	public String getUpdateSQL(){
		
		String sqlUpdateTot = this.getProperty("SQL_UPDATE_SOGG_TOTALE");		
		return sqlUpdateTot;
	}

	@Override
	//Metodo che mappa normalizza e salva i dati
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert, String insSoggettoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

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
			log.warn("Errore:Save Soggetto Redditi Dichiarante ="+e.getMessage());
			Exception ea = new Exception("Errore:Save Soggetto Redditi Dichiarante:"+e.getMessage());
			throw ea;
		}
		
	}

	@Override 
	//Metodo che mappa normalizza e aggiorna i dati
	public void prepareUpdateDato(DatoDwh classeFonte, Connection connForInsert, String insSoggettoTotale, String updateSoggettoTotale, String searchSoggettoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {
		//TODO Metodo non usato nella gestione senza procedID		
	}

	//Metodo che indica se la query di lettura da DWH ha come parametro il CodiceEnte
	public boolean queryWithParamCodEnte(){
		return false;
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
