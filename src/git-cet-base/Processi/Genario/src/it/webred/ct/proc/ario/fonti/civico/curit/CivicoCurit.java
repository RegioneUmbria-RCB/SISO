package it.webred.ct.proc.ario.fonti.civico.curit;

import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.ct.proc.ario.GestioneStringheVie;
import it.webred.ct.proc.ario.bean.BeanParser;
import it.webred.ct.proc.ario.bean.Civico;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.fonti.civico.Civici;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CivicoCurit extends DatoDwh implements Civici{

	private Properties props = null;
	protected static final Logger log = Logger.getLogger(CivicoCurit.class.getName());
	
	
	public CivicoCurit(){
		
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
		// TODO Auto-generated method stub
		return false;
	}//-------------------------------------------------------------------------

	@Override
	//Metodo che indica se la tabella è gestita o no tramite PROCESSID
	public boolean existProcessId(){
		return false;
	}//-------------------------------------------------------------------------
		
	@Override
	//Metodo che restiuisce la chiave dell'ente sorgente 
	public int getFkEnteSorgente() {
		return 49;
	}//-------------------------------------------------------------------------
	
	@Override
	//Metodo che restituisce in numero di fonte del caricatore
	public int getProgEs() {
		return 1;	//AM_FONTE_TIPOINFO 1= CERTIFICAZIONE, 2= PROPRIETARIO, 3= CERTIFICATORE
	}//-------------------------------------------------------------------------
	
	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {
		String sqlCivicoLocazioniI = this.getProperty("SQL_CIV_CURIT");
		return sqlCivicoLocazioniI;
	}//-------------------------------------------------------------------------
	
	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		//Tabella del DHW da cui leggere i dati
		String tabella = "SIT_CURIT";		
		return tabella;
	}//-------------------------------------------------------------------------
	
	@Override
	//Metodo che mappa normalizza e salva i dati
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert, String insCivicoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {
		SitCivicoTotale sct = new SitCivicoTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		try{			
			String idDwh = rs.getString("ID_DWH");
			//String sedime = (rs.getString("SEDIME")!=null?rs.getString("SEDIME"):""); 
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"");
			String field1 = (rs.getString("FIELD1")!=null?rs.getString("FIELD1"):"");
			String field2 = (rs.getString("FIELD2")!=null?rs.getString("FIELD2"):"");
			String field3 = (rs.getString("FIELD3")!=null?rs.getString("FIELD3"):"");
			String field4 = (rs.getString("FIELD4")!=null?rs.getString("FIELD4"):"");
			String field5 = (rs.getString("FIELD5")!=null?rs.getString("FIELD5"):"");
			String field6 = (rs.getString("FIELD6")!=null?rs.getString("FIELD6"):"");
			String field7 = (rs.getString("FIELD7")!=null?rs.getString("FIELD7"):"");
			String field8 = (rs.getString("FIELD8")!=null?rs.getString("FIELD8"):"");
			

//				sct.setIdStorico(idStorico);
//				sct.setDataFineVal(dataFineVal);
				sct.setIdOrigViaTotale(idDwh);
				sct.setCivLiv1(field4);
				//sct.setCivicoComp( BeanParser.getCivicoComposto(civ));
				//sct.setNote(civ.getNote());				
				//sct.setAnomalia(civ.getAnomalia());				
				
				sct.setField1(field1);
				sct.setField2(field2);
				sct.setField3(field3);
				sct.setField4(field4);
				sct.setField5(field5);
				sct.setField6(field6);
				sct.setField7(field7);
				sct.setField8(field8);
				//sct.setField9( sedime );
				sct.setField9( indirizzo );
				
				//Normalizzazione
				nt.normalizzaCivico(sct);
				
				//Setto indice Civico
				iPk.setIdDwh(rs.getString("ID_DWH"));
				iPk.setFkEnteSorgente(this.getFkEnteSorgente());
				iPk.setProgEs(this.getProgEs());				
				//Calcolo Hash di chiave
				iPk.setCtrHash(setCtrHashSitCivicoTotale(sct));
				
				sct.setId(iPk);
							
				//Salvataggio
				super.saveSitCivicoTotale(classeFonte, connForInsert, insCivicoTotale, sct);
	
				
		}catch (Exception e) {
			log.warn("Errore:Save Civico Curit: "+e.getMessage(), e);
			Exception ea = new Exception("Errore:Save Civico Curit: "+e.getMessage());
			throw ea;
		}
		
	}//-------------------------------------------------------------------------
	
	@Override 
	//Metodo che mappa normalizza e aggiorna i dati
	public void prepareUpdateDato(DatoDwh classeFonte, Connection connForInsert, String insCivicoTotale, String updateCivicoTotale, String searchCivicoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

		SitCivicoTotale sct = new SitCivicoTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		try{			
			
			String idDwh = rs.getString("ID_DWH");
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"-");
//			String idStorico = rs.getString("ID_STORICO");
//			Date dataFineVal = rs.getDate("DT_FINE_VAL");
			Collection<Civico> colCiv = GestioneStringheVie.restituisciCivico(indirizzo);
			Iterator< Civico> iter = colCiv.iterator();
			
			//Mappo i campi chiave (usati anche per la ricerca dei dati da aggiornare)
			iPk.setIdDwh(rs.getString("ID_DWH"));
			iPk.setFkEnteSorgente(this.getFkEnteSorgente());
			iPk.setProgEs(this.getProgEs());

			//Setto la chiave
			sct.setId(iPk);
			
			//Cancello tutti i civici di chiave corrispondente che non sono in colCiv
			/* AAA la query segente nel file caricatori.sql oggi 15.03.2022 è commentata */
			String sqlDeleteColCiv = this.getProperty("SQL_DELETE_COLL_CIV");			
			deleteCollezioneCivici(sct, sqlDeleteColCiv, connForInsert, colCiv);

			while( iter.hasNext() ){

				Civico civ =iter.next();
			
//				sct.setIdStorico(idStorico);
//				sct.setDataFineVal(dataFineVal);
				sct.setCivLiv1(civ.getCivLiv1());
				sct.setCivicoComp( BeanParser.getCivicoComposto(civ));
				sct.setNote(civ.getNote());
				sct.setFkCivico(null);
				sct.setRelDescr(null);
				sct.setRating(null);							
				sct.setAnomalia(civ.getAnomalia());
				sct.setIdOrigViaTotale(idDwh);
						
				sct.setField1(rs.getString("FIELD1"));
				sct.setField2(rs.getString("FIELD2"));
				sct.setField3(rs.getString("FIELD3"));
				sct.setField4(rs.getString("FIELD4"));
				sct.setField5(rs.getString("FIELD5"));
				sct.setField6(rs.getString("FIELD6"));
				sct.setField7(rs.getString("FIELD7"));
				sct.setField8(rs.getString("FIELD8"));
				sct.setField9(rs.getString("FIELD9"));
				sct.setField10(rs.getString("FIELD10"));
				
				//Normalizzazione Dati
				nt.normalizzaCivico(sct);

				//Calcolo hash per la ricerca
				iPk.setCtrHash(setCtrHashSitCivicoTotale(sct));
							
				//Setto la chiave
				sct.setId(iPk);
				
				String azione = super.trovaDatoTotaleCtrHash(classeFonte, connForInsert,searchCivicoTotale,iPk);
				
				if(azione.equals("INSERISCI")){
					//Inserisco il nuovo civivo
					super.saveSitCivicoTotale(classeFonte, connForInsert, insCivicoTotale, sct);
				}		
							
			}		
					
		}catch (Exception e) {
			log.warn("Errore:Update Civico Curit: "+e.getMessage(), e);
			Exception ea = new Exception("Errore:Update Civico Curit: "+e.getMessage());
			throw ea;
		}
		
	}//-------------------------------------------------------------------------
	
	//Metodo che indica se la query di lettura da DWH ha come parametro il CodiceEnte
	public boolean queryWithParamCodEnte(){
		return false;
	}//-------------------------------------------------------------------------
	
	//Metodo che restituisce la query di cancellazione tabella Totale
	public String getDeleteSQL(){
		String sqlDeleteTot = this.getProperty("SQL_DEL_CIVICO_TOTALE");		
		return sqlDeleteTot;
	}//-------------------------------------------------------------------------
	
	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
		String sqlInsertTot = this.getProperty("SQL_INS_CIVICO_TOTALE");		
		return sqlInsertTot;
	}//-------------------------------------------------------------------------
	
	//Metodo che restituisce la query di update in tabella Totale
	public String getUpdateSQL(){
		String sqlUpdateTot = this.getProperty("SQL_UPDATE_CIV_TOTALE");		
		return sqlUpdateTot;
	}//-------------------------------------------------------------------------
	
	//Metodo che restituisce la query di ricerca in tabella Totale
	public String getSearchSQL(){
		String sqlSearchTot = this.getProperty("SQL_CERCA_CIV_TOTALE");		
		return sqlSearchTot;
	}//-------------------------------------------------------------------------
	
	@Override
	//Metodo che restituisce la query per l'inserimento del PID elaborato per gestione con PID
	public String getQuerySQLSaveProcessId() throws Exception {
		return null;
	}//-------------------------------------------------------------------------
	
	@Override
	//Metodo che restituisce la query per l'update del PID elaborato per gestione con PID
	public String getQuerySQLUpdateProcessId() throws Exception {
		return null;
	}//-------------------------------------------------------------------------
	
	@Override
	//Metodo che restituisce la query dei nuovi processId da caricare
	public String getQuerySQLNewProcessId() throws Exception {
		return null;
	}//-------------------------------------------------------------------------
	
	//Metodo che restituisce la query per trovare i PID della tabella DWH
	@Override
	public String getQuerySQLgetProcessId() throws Exception {
		return null;
	}//-------------------------------------------------------------------------
	
	//Metodo che restituisce la query che aggiorna e cancella un processId dalla tabella di gestione PID
	@Override
	public String getQuerySQLDeleteProcessId() throws Exception {
		return null;
	}//-------------------------------------------------------------------------
	
	//Metodo che restituisce la query di caricamento letta da file di property
	private String getProperty(String propName) {
		String p = this.props.getProperty(propName+ "." + this.getFkEnteSorgente());
		if (p==null)
			p = this.props.getProperty(propName);
		return p;
	}//-------------------------------------------------------------------------

	@Override
	//Metodo che stabilisce se viene o no fornito il codice originario del civico
	public boolean codiceCivico(String codEnte, HashParametriConfBean paramConfBean) throws Exception{
		//La fonte non gestisce il civico di origine
		return false;
	}//-------------------------------------------------------------------------



}
