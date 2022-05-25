package it.webred.ct.proc.ario.fonti.via.curit;

import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitViaTotale;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.fonti.via.Via;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ViaCurit extends DatoDwh implements Via{

	private Properties props = null;
	protected static final Logger log = Logger.getLogger(ViaCurit.class.getName());

	public ViaCurit() {
		//Caricamento del file di properties dei caricatori		
		this.props = new Properties();
		try {
			InputStream is = this.getClass().getResourceAsStream("/sql/caricatori.sql");
		    this.props.load(is);                     
		}catch(Exception e) {
		    log.error("Eccezione: "+e.getMessage(), e);			   
		}		
	}//-------------------------------------------------------------------------
	
	@Override
	public boolean dwhIsDrop(Connection conn) throws Exception {
		return false;
	}//-------------------------------------------------------------------------

	@Override
	//Metodo che indica se la tabella è gestita o no tramite PROCESSID
	public boolean existProcessId(){
		return false;
	}//-------------------------------------------------------------------------

	//Metodo che restituisce la query di cancellazione tabella Totale
	public String getDeleteSQL(){
		String sqlDeleteTot = this.getProperty("SQL_DEL_VIA_TOTALE");		
		return sqlDeleteTot;
	}//-------------------------------------------------------------------------

	@Override
	public int getFkEnteSorgente() {
		return 49;
	}//-------------------------------------------------------------------------

	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
		String sqlInsertTot = this.getProperty("SQL_INS_VIA_TOTALE");		
		return sqlInsertTot;
	}//-------------------------------------------------------------------------

	@Override
	public int getProgEs() {
		return 1;	//AM_FONTE_TIPOINFO 1= CERTIFICAZIONE, 2= PROPRIETARIO, 3= CERTIFICATORE
	}//-------------------------------------------------------------------------

	@Override
	public String getQuerySQLDeleteProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}//-------------------------------------------------------------------------

	@Override
	public String getQuerySQLgetProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}//-------------------------------------------------------------------------

	@Override
	public String getQuerySQLNewProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}//-------------------------------------------------------------------------

	@Override
	public String getQuerySQLSaveProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}//-------------------------------------------------------------------------

	@Override
	public String getQuerySQLUpdateProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}//-------------------------------------------------------------------------

	//Metodo che restituisce la query di ricerca in tabella Totale
	public String getSearchSQL(){
		
		String sqlSearchTot = this.getProperty("SQL_CERCA_VIA_TOTALE");		
		return sqlSearchTot;
	}//-------------------------------------------------------------------------

	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {
		String sqlVia = this.getProperty("SQL_VIA_CURIT");
		return sqlVia;
	}//-------------------------------------------------------------------------

	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		//Tabella del DHW da cui leggere i dati
		String tabella = "SIT_CURIT";		
		return tabella;
	}//-------------------------------------------------------------------------

	//Metodo che restituisce la query di update in tabella Totale
	public String getUpdateSQL(){
		String sqlUpdateTot = this.getProperty("SQL_UPDATE_VIA_TOTALE");		
		return sqlUpdateTot;
	}//-------------------------------------------------------------------------
	
	@Override
	//Metodo che mappa normalizza e salva i dati
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert, String insViaTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

		SitViaTotale svt = new SitViaTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		try{		
			
			String idDwh = rs.getString("ID_DWH");
			String sedime = (rs.getString("SEDIME")!=null?rs.getString("SEDIME"):""); 
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"");
			String field1 = (rs.getString("FIELD1")!=null?rs.getString("FIELD1"):"");
			String field2 = (rs.getString("FIELD2")!=null?rs.getString("FIELD2"):"");
			String field3 = (rs.getString("FIELD3")!=null?rs.getString("FIELD3"):"");
			String field4 = (rs.getString("FIELD4")!=null?rs.getString("FIELD4"):"");
			String field5 = (rs.getString("FIELD5")!=null?rs.getString("FIELD5"):"");
			String field6 = (rs.getString("FIELD6")!=null?rs.getString("FIELD6"):"");
			String field7 = (rs.getString("FIELD7")!=null?rs.getString("FIELD7"):"");
			String field8 = (rs.getString("FIELD8")!=null?rs.getString("FIELD8"):"");
			String field9 = (rs.getString("FIELD9")!=null?rs.getString("FIELD9"):"");
			String field10 = (rs.getString("FIELD10")!=null?rs.getString("FIELD10"):"");

			svt.setSedime(sedime);
			svt.setIndirizzo(indirizzo);					
			svt.setField1(field1);
			svt.setField2(field2);
			svt.setField3(field3);
			svt.setField4(field4);
			svt.setField5(field5);
			svt.setField6(field6);
			svt.setField7(field7);
			svt.setField8(field8);
			svt.setField9(field9);
			svt.setField10(field10);

			//Normalizzazione
			nt.normalizzaVia(svt);
		
			//Setto indice Soggetto
			iPk.setIdDwh(idDwh);
			iPk.setFkEnteSorgente(this.getFkEnteSorgente());
			iPk.setProgEs(this.getProgEs());							
			//Calcolo Hash di chiave
			iPk.setCtrHash(setCtrHashSitViaTotale(svt));		
			
			svt.setId(iPk);
			
			//Salvataggio
			super.saveSitViaTotale(classeFonte, connForInsert, insViaTotale, svt);

			
		}catch (Exception e) {
			log.warn("Errore:Save Via Curit "+e.getMessage(), e);
			Exception ea = new Exception("Errore:Save Via Curit: "+e.getMessage());
			throw ea;
		}
		
	}//-------------------------------------------------------------------------

	@Override 
	//Metodo che mappa normalizza e aggiorna i dati
	public void prepareUpdateDato(DatoDwh classeFonte, Connection connForInsert, String insViaTotale, String updateViaTotale, String searchViaTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {
		//TODO Metodo non usato nella gestione senza ProcessID
	}//-------------------------------------------------------------------------

	//Metodo che indica se la query di lettura da DWH ha come parametro il CodiceEnte
	public boolean queryWithParamCodEnte(){
		return false;
	}//-------------------------------------------------------------------------
	
	//Metodo che restituisce la query di caricamento letta da file di property
	private String getProperty(String propName) {
		String p = this.props.getProperty(propName+ "." + this.getFkEnteSorgente());
		if (p==null)
			p = this.props.getProperty(propName);
		return p;
	}//-------------------------------------------------------------------------
	
	@Override
	//Metodo che stabilisce se viene o no fornito il codice originario della via dal viario
	public boolean codiceVia(String codEnte, HashParametriConfBean paramConfBean){
		return false;
	}//-------------------------------------------------------------------------

}
