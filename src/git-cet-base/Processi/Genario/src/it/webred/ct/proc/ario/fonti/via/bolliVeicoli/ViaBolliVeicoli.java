package it.webred.ct.proc.ario.fonti.via.bolliVeicoli;

import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitViaTotale;
import it.webred.ct.proc.ario.GestioneStringheVie;
import it.webred.ct.proc.ario.bean.Civico;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.fonti.via.Via;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ViaBolliVeicoli extends DatoDwh implements Via{

	private Properties props = null;
	protected static final Logger log = Logger.getLogger(ViaBolliVeicoli.class.getName());

	public ViaBolliVeicoli() {
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
		return 46;
	}//-------------------------------------------------------------------------

	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
		String sqlInsertTot = this.getProperty("SQL_INS_VIA_TOTALE");		
		return sqlInsertTot;
	}//-------------------------------------------------------------------------

	@Override
	public int getProgEs() {
		return 1;	//AM_FONTE_TIPOINFO 1= Bolli Veicoli
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
		String sqlVia = this.getProperty("SQL_VIA_BOLLI_VEICOLI");
		return sqlVia;
	}//-------------------------------------------------------------------------

	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		//Tabella del DHW da cui leggere i dati
		String tabella = "BOLLI_VEICOLI";		
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
			//Date dataFineVal = rs.getDate("DT_FINE_VAL");
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"-");
			//String sedime = rs.getString("SEDIME"); 
			String sedime = "";
//			String sedime1 = GestioneStringheVie.trovaSedimeUnivoco(sedime);
//			if (sedime1 != null)
//				sedime = sedime1;
			String note = null;
			Collection<Civico> colCiv = GestioneStringheVie.restituisciCivico(indirizzo);
			Iterator<Civico> iter = colCiv.iterator();
			if(iter.hasNext()){
				Civico civ =iter.next();
				indirizzo= (String)civ.getInd();
				//if(isInvalid(sedime)){
					String civSedime= (String)civ.getSed();
					String civSedime1 = GestioneStringheVie.trovaSedimeUnivoco(civSedime);
					sedime = civSedime1!=null ? civSedime1 : civSedime;  
				//}
				note = civ.getNote();
			}
			  
						  
			//svt.setDtFineVal(dataFineVal);	
			//svt.setCodiceViaOrig(rs.getString("CODICE_VIA"));
			
			svt.setSedime(sedime);
			svt.setIndirizzo(indirizzo);					
			svt.setNote(note);
			
			svt.setField1(rs.getString("FIELD1"));
			svt.setField2(rs.getString("FIELD2"));
			svt.setField3(rs.getString("FIELD3"));
			svt.setField4(rs.getString("FIELD4"));
			svt.setField5(rs.getString("FIELD5"));

			
			//Setto il codice via letto (sarà sempre null per la fonte data)								
			//svt.setCodiceViaOrig(rs.getString("CODICE_VIA"));

			
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
			log.warn("Errore:Save Via Bolli Veicoli "+e.getMessage(), e);
			Exception ea = new Exception("Errore:Save Via Bolli Veicoli: "+e.getMessage());
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

