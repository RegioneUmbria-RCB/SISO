package it.webred.ct.proc.ario.fonti.fabbricato.successioni;

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

public class FabbricatoSuccessioni extends DatoDwh implements Fabbricato{

		
	private Properties props = null;
	protected static final Logger log = Logger.getLogger(FabbricatoSuccessioni.class.getName());
	
	
	public FabbricatoSuccessioni(){
		
		//Caricamento del file di properties dei caricatori		
		this.props = new Properties();
		try {
			InputStream is = this.getClass().getResourceAsStream("/sql/caricatori.sql");
		    this.props.load(is);                     
		}catch(Exception e) {
		    log.error("Eccezione: "+e.getMessage(), e);			   
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
	@Override
	public String getDeleteSQL(){
		
		String sqlDeleteTot = this.getProperty("SQL_DEL_FABB_TOTALE");		
		return sqlDeleteTot;
	}


	@Override
	public int getFkEnteSorgente() {
		
		return 6;
	}

	@Override
	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
		
		String sqlInsertTot = this.getProperty("SQL_INS_FABB_TOTALE");		
		return sqlInsertTot;
	}

	@Override
	public int getProgEs() {
		
		return 3;
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

	@Override
	//Metodo che restituisce la query di ricerca in tabella Totale
	public String getSearchSQL(){
		
		String sqlSearchTot = this.getProperty("SQL_CERCA_FABB_TOTALE");		
		return sqlSearchTot;
	}

	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {

		String sqlOggetto = this.getProperty("SQL_FABB_SUCCESSIONI");
						
		return sqlOggetto;
				
	}

	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		
		//Tabella del DHW da cui leggere i dati
		String tabella = "SUCCESSIONI_C";		
		return tabella;
	}

	//Metodo che restituisce la query di update in tabella Totale
	@Override	
	public String getUpdateSQL(){
		
		String sqlUpdateTot = this.getProperty("SQL_UPDATE_FABB_TOTALE");		
		return sqlUpdateTot;
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
			sft.setParticella(rs.getString("PARTICELLA1"));	
			sft.setSubalterno(rs.getString("SUBALTERNO1"));
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

			
			
			//Effettuo il salvataggio per il secondo Sub, Particella se presente
			String particella2 = rs.getString("PARTICELLA2");			
			
			if((particella2 != null && !particella2.equals(""))){
				
				log.info("Salvataggio Oggetto Particella2");
				
				sft.setIdStorico(rs.getString("ID_STORICO"));
				sft.setSezione(rs.getString("SEZIONE"));
				sft.setFoglio(rs.getString("FOGLIO"));
				sft.setParticella(particella2);		
				sft.setSubalterno(rs.getString("SUBALTERNO2"));
				sft.setDtInizioVal(rs.getDate("DT_INIZIO_VAL"));
				sft.setDtFineVal(rs.getDate("DT_FINE_VAL"));				
				sft.setCategoria(null);
				sft.setClasse(null);
				sft.setRendita(null);
				sft.setZona(null);
				sft.setFoglioUrbano(null);
				sft.setSuperficie(null);
				sft.setScala(null);
				sft.setInterno(null);
				sft.setPiano(null);
				sft.setFkFabbricato(null);
				sft.setRelDescr(null);
				sft.setRating(null);
				sft.setNote(null);
				sft.setAnomalia(null);					
				
						
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
			
			}
			
			
			
		}catch (Exception e) {
			log.warn("Errore:Save Fabbricato Successioni="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Save Fabbricato Successioni:"+e.getMessage());
			throw ea;
		}
		
		
	}

	@Override 
	//Metodo che mappa normalizza e aggiorna i dati
	public void prepareUpdateDato(DatoDwh classeFonte, Connection connForInsert, String insFabbricatoTotale, String updateFabbricatoTotale, String searchFabbricatoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {
		
	}
	

	//Metodo che indica se la query di lettura da DWH ha come parametro il CodiceEnte
	public boolean queryWithParamCodEnte(){
		return true;
	}
	
	
	//Metodo che restituisce la query di caricamento letta da file di property
	private String getProperty(String propName) {

		String p = this.props.getProperty(propName+ "." + this.getFkEnteSorgente());
		
		if (p==null)
			p = this.props.getProperty(propName);
			
		return p;
	}

}
