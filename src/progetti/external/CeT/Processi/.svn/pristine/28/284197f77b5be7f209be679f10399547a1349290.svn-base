package it.webred.ct.proc.ario.fonti.oggetto.successioni;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitOggettoTotale;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.fonti.civico.fornitureElettriche.CivicoEnel;
import it.webred.ct.proc.ario.fonti.oggetto.Oggetto;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;

public class OggettoSuccessioni extends DatoDwh implements Oggetto{

		
	private Properties props = null;
	protected static final Logger log = Logger.getLogger(OggettoSuccessioni.class.getName());
	
	
	public OggettoSuccessioni(){
		
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
	@Override
	public String getDeleteSQL(){
		
		String sqlDeleteTot = this.getProperty("SQL_DEL_OGG_TOTALE");		
		return sqlDeleteTot;
	}


	@Override
	public int getFkEnteSorgente() {
		
		return 6;
	}

	@Override
	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
		
		String sqlInsertTot = this.getProperty("SQL_INS_OGG_TOTALE");		
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
		
		String sqlSearchTot = this.getProperty("SQL_CERCA_OGG_TOTALE");		
		return sqlSearchTot;
	}

	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {

		String sqlOggetto = this.getProperty("SQL_OGG_SUCCESSIONI");
						
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
		
		String sqlUpdateTot = this.getProperty("SQL_UPDATE_OGG_TOTALE");		
		return sqlUpdateTot;
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
			sot.setParticella(rs.getString("PARTICELLA1"));
			sot.setSub(rs.getString("SUBALTERNO1"));
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

			
			
			//Effettuo il salvataggio per il secondo Sub, Particella se presente
			String particella2 = rs.getString("PARTICELLA2");
			String sub2 = rs.getString("SUBALTERNO2");
			
			if((particella2 != null && !particella2.equals("")) || (sub2 != null && !sub2.equals(""))){
				
				log.info("Salvataggio Oggetto Particella2 / Sub2");
				
				sot.setIdStorico(rs.getString("ID_STORICO"));
				sot.setSezione(rs.getString("SEZIONE"));
				sot.setFoglio(rs.getString("FOGLIO"));
				sot.setParticella(particella2);
				sot.setSub(sub2);
				sot.setDtInizioVal(rs.getDate("DT_INIZIO_VAL"));
				sot.setDtFineVal(rs.getDate("DT_FINE_VAL"));				
				sot.setCategoria(null);
				sot.setClasse(null);
				sot.setRendita(null);
				sot.setZona(null);
				sot.setFoglioUrbano(null);
				sot.setSuperficie(null);
				sot.setScala(null);
				sot.setInterno(null);
				sot.setPiano(null);
				sot.setFkOggetto(null);
				sot.setRelDescr(null);
				sot.setRating(null);
				sot.setNote(null);
				sot.setAnomalia(null);					
				
						
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
			
			}
			
			
			
		}catch (Exception e) {
			log.warn("Errore:Save Oggetto Successioni="+e.getMessage());
			Exception ea = new Exception("Errore:Save Oggetto Successioni:"+e.getMessage());
			throw ea;
		}
		
		
	}

	@Override 
	//Metodo che mappa normalizza e aggiorna i dati
	public void prepareUpdateDato(DatoDwh classeFonte, Connection connForInsert, String insOggettoTotale, String updateOggettoTotale, String searchOggettoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {
		
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
