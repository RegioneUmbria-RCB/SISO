package it.webred.ct.proc.ario.fonti.fabbricato.demanio;

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

public class FabbricatoBene extends DatoDwh implements Fabbricato{

	private Properties props = null;
	protected static final Logger log = Logger.getLogger(FabbricatoBene.class.getName());
	
	
	public FabbricatoBene(){
		
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
		
		String sql = this.getProperty("SQL_DWH_IS_DROP_FABB");
		
		sql = sql.replace("$TAB", this.getTable());
		
		int fkEnteSorgente = this.getFkEnteSorgente();
		int progEs = this.getProgEs();
		
		return super.executeIsDropDWh(sql, conn, fkEnteSorgente, progEs);
	}
	
	@Override
	//Metodo che indica se la tabella è gestita o no tramite PROCESSID
	public boolean existProcessId(){
		return false;
	}
	

	@Override
	//Metodo che restituisce la query di cancellazione tabella Totale
	public String getDeleteSQL(){
		
		String sqlDeleteTot = this.getProperty("SQL_DEL_FABB_TOTALE");		
		return sqlDeleteTot;
	}

	@Override
	public int getFkEnteSorgente() {
		
		return 42;
	}

	@Override
	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
		
		String sqlInsertTot = this.getProperty("SQL_INS_FABB_TOTALE");		
		return sqlInsertTot;
	}

	@Override
	public int getProgEs() {
		
		return 2;
	}

	//Metodo che restituisce la query che aggiorna e cancella un processId dalla tabella di gestione PID
	@Override
	public String getQuerySQLDeleteProcessId() throws Exception {
		
		String query = this.getProperty("SQL_DELETE_PID_FABB");
		
		return query;
	}

	//Metodo che restituisce la query per trovare i PID della tabella DWH
	@Override
	public String getQuerySQLgetProcessId() throws Exception {
		
		String query = this.getProperty("SQL_GET_PID_DWH_FABB");
		
		return query;
	}
	
	@Override
	//Metodo che restituisce la query dei nuovi processId da caricare
	public String getQuerySQLNewProcessId() throws Exception {

		String query = this.getProperty("SQL_NEW_PID_DWH_FABB");
		
		return query;
	}

	@Override
	//Metodo che restituisce la query per l'inserimento del PID elaborato per gestione con PID
	public String getQuerySQLSaveProcessId() throws Exception {
		
		String query = this.getProperty("SQL_INSERT_PID_FABB");
			
		return query;
	}

	@Override
	//Metodo che restituisce la query per l'update del PID elaborato per gestione con PID
	public String getQuerySQLUpdateProcessId() throws Exception {
			
		String query = this.getProperty("SQL_UPDATE_PID_FABB");
		
		return query;
	}

	@Override
	//Metodo che restituisce la query di ricerca in tabella Totale
	public String getSearchSQL(){
		
		String sqlSearchTot = this.getProperty("SQL_CERCA_FABB_TOTALE");		
		return sqlSearchTot;
	}

	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {

		String sqlFabbricato = this.getProperty("SQL_FABB_DEMANIO");
	
		return sqlFabbricato;
				
	}

	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		
		//Tabella del DHW da cui leggere i dati
		String tabella = "DM_B_MAPPALE";		
		return tabella;
	}

	@Override
	//Metodo che restituisce la query di update in tabella Totale
	public String getUpdateSQL(){
		
		String sqlUpdateTot = this.getProperty("SQL_UPDATE_FABB_TOTALE");		
		return sqlUpdateTot;
	}

	@Override
	//Metodo che mappa normalizza e salva i dati
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert, String insFabbricatoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

		SitFabbricatoTotale sft = new SitFabbricatoTotale();
		IndicePK iPk = new IndicePK();
		
		
		try{			
			
			sft.setIdStorico(rs.getString("ID_DWH"));
			sft.setSezione(rs.getString("SEZIONE"));
			sft.setFoglio(rs.getString("FOGLIO"));
			sft.setParticella(rs.getString("PARTICELLA"));	
			sft.setDtInizioVal(rs.getDate("DT_INIZIO_VAL"));
			sft.setDtFineVal(rs.getDate("DT_FINE_VAL"));							
			sft.setFkFabbricato(null);
			sft.setRelDescr(null);
			sft.setRating(null);
			sft.setAnomalia(null);					
			
			sft.setDtInizioDato(rs.getDate("DT_INIZIO_DATO"));
			sft.setDtFineDato(rs.getDate("DT_FINE_DATO"));
			sft.setDtExpDato(rs.getDate("DT_EXP_DATO"));
			sft.setProvenienza(rs.getString("PROVENIENZA"));
						
			sft.setField1(rs.getString("FIELD1"));
			sft.setField2(rs.getString("FIELD2"));
			
			//Normalizzazione Dati
			NormalizzaTotali.normalizzaFabbricato(sft);
			
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
			log.warn("Errore:Save Fabbricato Bene Demanio="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Save Bene Demanio:"+e.getMessage());
			throw ea;
		}
		
		
	}

	@Override 
	//Metodo che mappa normalizza e aggiorna i dati
	public void prepareUpdateDato(DatoDwh classeFonte, Connection connForInsert, String insFabbricatoTotale, String updateFabbricatoTotale, String searchFabbricatoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

		SitFabbricatoTotale sft = new SitFabbricatoTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		try{			
						
			sft.setIdStorico(rs.getString("ID_DWH"));
			sft.setSezione(rs.getString("SEZIONE"));
			sft.setFoglio(rs.getString("FOGLIO"));
			sft.setParticella(rs.getString("PARTICELLA"));			
			sft.setDtInizioVal(rs.getDate("DT_INIZIO_VAL"));
			sft.setDtFineVal(rs.getDate("DT_FINE_VAL"));							
			sft.setFkFabbricato(null);
			sft.setRelDescr(null);
			sft.setRating(null);
			sft.setAnomalia(null);					
			
			sft.setDtInizioDato(rs.getDate("DT_INIZIO_DATO"));
			sft.setDtFineDato(rs.getDate("DT_FINE_DATO"));
			sft.setDtExpDato(rs.getDate("DT_EXP_DATO"));
			sft.setProvenienza(rs.getString("PROVENIENZA"));
			
			sft.setField1(rs.getString("FIELD1"));
			sft.setField2(rs.getString("FIELD2"));
			
			//Normalizzazione Dati
			nt.normalizzaFabbricato(sft);
			
			//Setto indice Fabbricato
			iPk.setIdDwh(rs.getString("ID_DWH"));
			iPk.setFkEnteSorgente(this.getFkEnteSorgente());
			iPk.setProgEs(this.getProgEs());							
			//Calcolo Hash di chiave
			iPk.setCtrHash(setCtrHashSitFabbricatoTotale(sft));		
			
			sft.setId(iPk);
								
			
			String azione = super.trovaDatoTotaleCtrHash(classeFonte, connForInsert,searchFabbricatoTotale,iPk);								
														
			
			if(azione.equals("AGGIORNA")){
				//Aggiorna elemento già presente
				super.updateSitFabbricatoTotale(classeFonte,connForInsert,updateFabbricatoTotale,sft);										
			}else if(azione.equals("INSERISCI")){
				//Salva il nuovo elemento										
				super.saveSitFabbricatoTotale(classeFonte, connForInsert, insFabbricatoTotale, sft);										
			}

			
		}catch (Exception e) {
			log.warn("Errore:Update Fabbricato Demanio Bene= "+e.getMessage(), e);
			Exception ea = new Exception("Errore:Update Fabbricato Demanio Bene:"+e.getMessage());
			throw ea;
		}
		
	}

	@Override
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

}
