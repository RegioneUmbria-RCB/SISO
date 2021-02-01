package it.webred.ct.proc.ario.fonti.oggetto.catasto;

//import it.webred.ct.proc.ario.bean.SitOggettoTotale;
//import it.webred.ct.proc.ario.bean.SitRicercaTotale;

import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitOggettoTotale;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.fonti.oggetto.Oggetto;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;
import it.webred.utils.db.BasicRsPrcessor;
import it.webred.utils.db.PojoParser;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.log4j.Logger;

public class OggettoCatastoImmobili extends DatoDwh implements Oggetto{

	private Properties props = null;
	protected static final Logger log = Logger.getLogger(OggettoCatastoImmobili.class.getName());
	
	
	public OggettoCatastoImmobili(){
		
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
		
	@Override
	//Metodo che restiuisce la chiave dell'ente sorgente 
	public int getFkEnteSorgente() {
	
		return 4;
	}
	
	
	@Override
	//Metodo che restituisce in numero di fonte del caricatore
	public int getProgEs() {
 
		return 1;
	}
	
	
	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {

		String sqlOggettoCatasto1 = this.getProperty("SQL_OGG_CATASTO");
				
		return sqlOggettoCatasto1;
				
	}

	
	
	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		
		//Tabella del DHW da cui leggere i dati
		String tabella = "SITIUIU";		
		return tabella;
	}

	
	@Override
	//Metodo che mappa normalizza e salva i dati
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert, String insOggettoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {
			
		SitOggettoTotale sot = new SitOggettoTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		
		try{			
			
			sot.setIdStorico(rs.getString("ID_STORICO"));
			sot.setSezione(rs.getString("SEZIONE"));
			sot.setFoglio(rs.getString("FOGLIO"));
			sot.setParticella(rs.getString("PARTICELLA"));
			sot.setSub(rs.getString("SUBALTERNO"));
			sot.setDtInizioVal(rs.getDate("DT_INIZIO_VAL"));
			sot.setDtFineVal(rs.getDate("DT_FINE_VAL"));						
			sot.setFkOggetto(null);
			sot.setRelDescr(null);
			sot.setRating(null);
			sot.setNote(null);
			sot.setAnomalia(null);					
			
			
			sot.setProcessId(rs.getString("PROCESSID"));
			sot.setDtInizioDato(rs.getDate("DT_INIZIO_DATO"));
			sot.setDtFineDato(rs.getDate("DT_FINE_DATO"));
			sot.setDtExpDato(rs.getDate("DT_EXP_DATO"));
			sot.setProvenienza(rs.getString("PROVENIENZA"));
			sot.setDataRegistrazione(rs.getDate("DATA_REGISTRAZIONE"));
			
			
			sot.setCategoria(rs.getString("CATEGORIA"));
			sot.setClasse(rs.getString("CLASSE"));
			sot.setRendita(rs.getString("RENDITA"));
			sot.setZona(rs.getString("ZONA"));
			sot.setFoglioUrbano(rs.getString("FOGLIO_URBANO"));
			sot.setSuperficie(rs.getString("SUPERFICIE"));
			sot.setScala(rs.getString("SCALA"));
			sot.setInterno(rs.getString("INTERNO"));
			sot.setPiano(rs.getString("PIANO"));
			
			sot.setField1(rs.getString("FIELD1"));
			sot.setField2(rs.getString("FIELD2"));
			sot.setField3(rs.getString("FIELD3"));
			sot.setField4(rs.getString("FIELD4"));
			sot.setField5(rs.getString("FIELD5"));
			sot.setField6(rs.getString("FIELD6"));
			sot.setField7(rs.getString("FIELD7"));
			sot.setField8(rs.getString("FIELD8"));
			sot.setField9(rs.getString("FIELD9"));
			
									
					
			//Normalizzazione Dati
			nt.normalizzaOggetto(sot);
			
			//Setto indice Soggetto
			iPk.setIdDwh(rs.getString("ID_DWH"));
			iPk.setFkEnteSorgente(this.getFkEnteSorgente());
			iPk.setProgEs(this.getProgEs());							
			//Calcolo Hash di chiave
			iPk.setCtrHash(setCtrHashSitOggettoTotale(sot));		
			
			sot.setId(iPk);
		
			
			//Salvataggio
			super.saveSitOggettoTotale(classeFonte, connForInsert, insOggettoTotale, sot);

									
		}catch (Exception e) {
			log.warn("Errore:Save Oggetto Catasto Immobili="+e.getMessage());
			Exception ea = new Exception("Errore:Save Oggetto Catasto Immobili:"+e.getMessage());
			throw ea;
		}					
		
	}
	
	
	@Override 
	//Metodo che mappa normalizza e aggiorna i dati
	public void prepareUpdateDato(DatoDwh classeFonte, Connection connForInsert, String insOggettoTotale, String updateOggettoTotale, String searchOggettoTotale, ResultSet r, String codEnte, HashParametriConfBean paramConfBean) throws Exception {
		
		//Metodo non utilizzato nel caricamento senza ProcessID
	}
	
	
	
	//Metodo che indica se la query di lettura da DWH ha come parametro il CodiceEnte
	public boolean queryWithParamCodEnte(){
		return true;
	}

	
	//Metodo che restituisce la query di cancellazione tabella Totale
	public String getDeleteSQL(){
		
		String sqlDeleteTot = this.getProperty("SQL_DEL_OGG_TOTALE");		
		return sqlDeleteTot;
	}
	
	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
		
		String sqlInsertTot = this.getProperty("SQL_INS_OGG_TOTALE");		
		return sqlInsertTot;
	}
	
	//Metodo che restituisce la query di update in tabella Totale
	public String getUpdateSQL(){
		
		String sqlUpdateTot = this.getProperty("SQL_UPDATE_OGG_TOTALE");		
		return sqlUpdateTot;
	}
	
	//Metodo che restituisce la query di ricerca in tabella Totale
	public String getSearchSQL(){
		
		String sqlSearchTot = this.getProperty("SQL_CERCA_OGG_TOTALE");		
		return sqlSearchTot;
	}
	
	
	
	@Override
	//Metodo che restituisce la query per l'inserimento del PID elaborato per gestione con PID
	public String getQuerySQLSaveProcessId() throws Exception {
				
		return null;
	}
	
	@Override
	//Metodo che restituisce la query per l'update del PID elaborato per gestione con PID
	public String getQuerySQLUpdateProcessId() throws Exception {
		
		return null;
	}
	
	@Override
	//Metodo che restituisce la query dei nuovi processId da caricare
	public String getQuerySQLNewProcessId() throws Exception {
	
		return null;
	}
	
	//Metodo che restituisce la query per trovare i PID della tabella DWH
	@Override
	public String getQuerySQLgetProcessId() throws Exception {
		
		return null;
	}
	
	//Metodo che restituisce la query che aggiorna e cancella un processId dalla tabella di gestione PID
	@Override
	public String getQuerySQLDeleteProcessId() throws Exception {

		return null;
	}
	
	
	//Metodo che restituisce la query di caricamento letta da file di property
	private String getProperty(String propName) {

		String p = this.props.getProperty(propName+ "." + this.getFkEnteSorgente());
		
		if (p==null)
			p = this.props.getProperty(propName);
			
		return p;
	}
	
}
