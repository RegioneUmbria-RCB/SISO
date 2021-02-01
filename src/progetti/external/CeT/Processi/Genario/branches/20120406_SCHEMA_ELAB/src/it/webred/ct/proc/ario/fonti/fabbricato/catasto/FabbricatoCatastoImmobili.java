package it.webred.ct.proc.ario.fonti.fabbricato.catasto;



import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitFabbricatoTotale;
import it.webred.ct.data.model.indice.SitOggettoTotale;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.fonti.fabbricato.Fabbricato;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.log4j.Logger;

public class FabbricatoCatastoImmobili extends DatoDwh implements Fabbricato{

	private Properties props = null;
	protected static final Logger log = Logger.getLogger(FabbricatoCatastoImmobili.class.getName());
	
	
	public FabbricatoCatastoImmobili(){
		
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

		String sqlOggettoCatasto1 = this.getProperty("SQL_FABB_CATASTO");
				
		return sqlOggettoCatasto1;
				
	}

	
	
	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		
		//Tabella del DHW da cui leggere i dati
		String tabella = "SITIPART";		
		return tabella;
	}

	
	@Override
	//Metodo che mappa normalizza e salva i dati
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert, String insFabbricatoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {
			
		SitFabbricatoTotale sft = new SitFabbricatoTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		
		try{			
			
			sft.setIdStorico(rs.getString("ID_STORICO"));
			sft.setSezione(rs.getString("SEZIONE"));
			sft.setFoglio(rs.getString("FOGLIO"));
			sft.setParticella(rs.getString("PARTICELLA"));			
			sft.setDtInizioVal(rs.getDate("DT_INIZIO_VAL"));
			sft.setDtFineVal(rs.getDate("DT_FINE_VAL"));						
			sft.setFkFabbricato(null);
			sft.setRelDescr(null);
			sft.setRating(null);
			sft.setNote(null);
			sft.setAnomalia(null);					
			
			
			sft.setProcessId(rs.getString("PROCESSID"));
			sft.setDtInizioDato(rs.getDate("DT_INIZIO_DATO"));
			sft.setDtFineDato(rs.getDate("DT_FINE_DATO"));
			sft.setDtExpDato(rs.getDate("DT_EXP_DATO"));
			sft.setProvenienza(rs.getString("PROVENIENZA"));
			sft.setDataRegistrazione(rs.getDate("DATA_REGISTRAZIONE"));
			
			
			sft.setCategoria(rs.getString("CATEGORIA"));
			sft.setClasse(rs.getString("CLASSE"));
			sft.setRendita(rs.getString("RENDITA"));
			sft.setZona(rs.getString("ZONA"));
			sft.setFoglioUrbano(rs.getString("FOGLIO_URBANO"));
			sft.setSuperficie(rs.getString("SUPERFICIE"));
			sft.setScala(rs.getString("SCALA"));
			sft.setInterno(rs.getString("INTERNO"));
			sft.setPiano(rs.getString("PIANO"));
			
			sft.setField1(rs.getString("FIELD1"));
			sft.setField2(rs.getString("FIELD2"));
			sft.setField3(rs.getString("FIELD3"));
			sft.setField4(rs.getString("FIELD4"));
			sft.setField5(rs.getString("FIELD5"));
			sft.setField6(rs.getString("FIELD6"));
			sft.setField7(rs.getString("FIELD7"));
			sft.setField8(rs.getString("FIELD8"));
			sft.setField9(rs.getString("FIELD9"));
			
									
					
			//Normalizzazione Dati
			nt.normalizzaFabbricato(sft);
			
			//Setto indice Fabbricato
			iPk.setIdDwh(rs.getString("ID_DWH"));
			iPk.setFkEnteSorgente(this.getFkEnteSorgente());
			iPk.setProgEs(this.getProgEs());							
			//Calcolo Hash di chiave
			iPk.setCtrHash(setCtrHashSitFabbricatoTotale(sft));		
			
			sft.setId(iPk);
		
			
			//Salvataggio
			super.saveSitFabbricatoTotale(classeFonte, connForInsert, insFabbricatoTotale, sft);

									
		}catch (Exception e) {
			log.warn("Errore:Save Fabbricato Catasto Immobili="+e.getMessage());
			Exception ea = new Exception("Errore:Save Fabbricato Catasto Immobili:"+e.getMessage());
			throw ea;
		}
					
	}
	
	
	@Override 
	//Metodo che mappa normalizza e aggiorna i dati
	public void prepareUpdateDato(DatoDwh classeFonte, Connection connForInsert, String insFabbricatoTotale, String updateFabbricatoTotale, String searchFabbricatoTotale, ResultSet r, String codEnte, HashParametriConfBean paramConfBean) throws Exception {
		
		//Metodo non utilizzato nel caricamento senza ProcessID
	}
	
	
	
	//Metodo che indica se la query di lettura da DWH ha come parametro il CodiceEnte
	public boolean queryWithParamCodEnte(){
		return true;
	}

	
	//Metodo che restituisce la query di cancellazione tabella Totale
	public String getDeleteSQL(){
		
		String sqlDeleteTot = this.getProperty("SQL_DEL_FABB_TOTALE");		
		return sqlDeleteTot;
	}
	
	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
		
		String sqlInsertTot = this.getProperty("SQL_INS_FABB_TOTALE");		
		return sqlInsertTot;
	}
	
	//Metodo che restituisce la query di update in tabella Totale
	public String getUpdateSQL(){
		
		String sqlUpdateTot = this.getProperty("SQL_UPDATE_FABB_TOTALE");		
		return sqlUpdateTot;
	}
	
	//Metodo che restituisce la query di ricerca in tabella Totale
	public String getSearchSQL(){
		
		String sqlSearchTot = this.getProperty("SQL_CERCA_FABB_TOTALE");		
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
