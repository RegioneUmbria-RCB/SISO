package it.webred.ct.proc.ario.fonti.civico.gas;


import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.ct.proc.ario.GestioneStringheVie;
import it.webred.ct.proc.ario.bean.BeanParser;
import it.webred.ct.proc.ario.bean.Civico;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
//import it.webred.ct.proc.ario.bean.SitCivicoTotale;
//import it.webred.ct.proc.ario.bean.SitRicercaTotale;
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

public class CivicoGas extends DatoDwh implements Civici{

	private Properties props = null;
	protected static final Logger log = Logger.getLogger(CivicoGas.class.getName());
	
	
	public CivicoGas(){
		
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
	//Metodo che indica se la tabella è gestita o no tramite PROCESSID
	public boolean existProcessId(){
		return true;
	}
		
	@Override
	//Metodo che restiuisce la chiave dell'ente sorgente 
	public int getFkEnteSorgente() {
	
		return 12;
	}
	
	
	@Override
	//Metodo che restituisce in numero di fonte del caricatore
	public int getProgEs() {
 
		return 1;
	}
	
	
	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {

		String sqlCivicoGas = this.getProperty("SQL_CIV_GAS");
				
		if (processID != null && !processID.equals("")){
			sqlCivicoGas = sqlCivicoGas + " WHERE PROCESSID =?";
		}
			
		return sqlCivicoGas;
				
	}

	
	
	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		
		//Tabella del DHW da cui leggere i dati
		String tabella = "SIT_U_GAS";		
		return tabella;
	}

	
	@Override
	//Metodo che mappa normalizza e salva i dati
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert, String insCivicoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

		SitCivicoTotale sct = new SitCivicoTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		try{			

			String idDwh = rs.getString("ID_DWH");
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"-");
			String idStorico = rs.getString("ID_STORICO");
			Date dataFineVal = rs.getDate("DT_FINE_VAL");
			Collection<Civico> colCiv = GestioneStringheVie.restituisciCivico(indirizzo);
			Iterator< Civico> iter = colCiv.iterator();
			
			while(iter.hasNext()){

				Civico civ =iter.next();
										
				sct.setIdStorico(idStorico);
				sct.setDataFineVal(dataFineVal);				
				sct.setIdOrigViaTotale(idDwh);
				sct.setCivLiv1(civ.getCivLiv1());
				sct.setCivicoComp( BeanParser.getCivicoComposto(civ));
				sct.setNote(civ.getNote());				
				sct.setAnomalia(civ.getAnomalia());				
				
				sct.setField1(rs.getString("FIELD1"));
				sct.setField2(rs.getString("FIELD2"));
				sct.setField3(rs.getString("FIELD3"));
				sct.setField4(rs.getString("FIELD4"));
				sct.setField5(rs.getString("FIELD5"));
				sct.setField6(rs.getString("FIELD6"));
				
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
							
			}
		
		}catch (Exception e) {
			log.warn("Errore:Save Civico Gas="+e.getMessage());
			Exception ea = new Exception("Errore:Save Civico Gas:"+e.getMessage());
			throw ea;
		}
				
	}
	
	
	@Override 
	//Metodo che mappa normalizza e aggiorna i dati
	public void prepareUpdateDato(DatoDwh classeFonte, Connection connForInsert, String insCivicoTotale, String updateCivicoTotale, String searchCivicoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {
		
		SitCivicoTotale sct = new SitCivicoTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		try{			

			String idDwh = rs.getString("ID_DWH");
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"-");
			String idStorico = rs.getString("ID_STORICO");
			Date dataFineVal = rs.getDate("DT_FINE_VAL");
			Collection<Civico> colCiv = GestioneStringheVie.restituisciCivico(indirizzo);
			Iterator< Civico> iter = colCiv.iterator();
			
			
			//Mappo i campi chiave (usati anche per la ricerca dei dati da aggiornare)
			iPk.setIdDwh(rs.getString("ID_DWH"));
			iPk.setFkEnteSorgente(this.getFkEnteSorgente());
			iPk.setProgEs(this.getProgEs());	
			
			//Setto la chiave
			sct.setId(iPk);

			
			//Cancello tutti i civici di chiave corrispondente che non sono in colCiv
			String sqlDeleteColCiv = this.getProperty("SQL_DELETE_COLL_CIV");			
			deleteCollezioneCivici(sct, sqlDeleteColCiv, connForInsert, colCiv);
			

			
			while(iter.hasNext()){

				Civico civ =iter.next();
										
				sct.setIdStorico(idStorico);
				sct.setDataFineVal(dataFineVal);
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
			log.warn("Errore:Update Civico Gas="+e.getMessage());
			Exception ea = new Exception("Errore:Update Civico Gas:"+e.getMessage());
			throw ea;
		}
		
		
	}

	
	//Metodo che indica se la query di lettura da DWH ha come parametro il CodiceEnte
	public boolean queryWithParamCodEnte(){
		return false;
	}

	
	//Metodo che restituisce la query di cancellazione tabella Totale
	public String getDeleteSQL(){
		
		String sqlDeleteTot = this.getProperty("SQL_DEL_CIVICO_TOTALE");		
		return sqlDeleteTot;
	}
	
	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
		
		String sqlInsertTot = this.getProperty("SQL_INS_CIVICO_TOTALE");		
		return sqlInsertTot;
	}
	
	//Metodo che restituisce la query di update in tabella Totale
	public String getUpdateSQL(){
		
		String sqlUpdateTot = this.getProperty("SQL_UPDATE_CIV_TOTALE");		
		return sqlUpdateTot;
	}
	
	//Metodo che restituisce la query di ricerca in tabella Totale
	public String getSearchSQL(){
		
		String sqlSearchTot = this.getProperty("SQL_CERCA_CIV_TOTALE");		
		return sqlSearchTot;
	}
	
	
	/**
	 * Metodo che controlla se la tabella del DWH è stata droppata
	 */
	@Override
	public boolean dwhIsDrop(Connection conn) throws Exception {
		
		String sql = this.getProperty("SQL_DWH_IS_DROP_CIV");
		
		sql = sql.replace("$TAB", this.getTable());
		
		int fkEnteSorgente = this.getFkEnteSorgente();
		int progEs = this.getProgEs();
		
		return super.executeIsDropDWh(sql, conn, fkEnteSorgente, progEs);
	}
	
	
	@Override
	//Metodo che restituisce la query per l'inserimento del PID elaborato per gestione con PID
	public String getQuerySQLSaveProcessId() throws Exception {
		
		String query = this.getProperty("SQL_INSERT_PID_CIV");
			
		return query;
	}
	
	
	@Override
	//Metodo che restituisce la query per l'update del PID elaborato per gestione con PID
	public String getQuerySQLUpdateProcessId() throws Exception {
			
		String query = this.getProperty("SQL_UPDATE_PID_CIV");
		
		return query;
	}
	
	@Override
	//Metodo che restituisce la query dei nuovi processId da caricare
	public String getQuerySQLNewProcessId() throws Exception {

		String query = this.getProperty("SQL_NEW_PID_DWH_CIV");
		
		return query;
	}
	
	//Metodo che restituisce la query per trovare i PID della tabella DWH
	@Override
	public String getQuerySQLgetProcessId() throws Exception {
		
		String query = this.getProperty("SQL_GET_PID_DWH_CIV");
		
		return query;
	}
	
	//Metodo che restituisce la query che aggiorna e cancella un processId dalla tabella di gestione PID
	@Override
	public String getQuerySQLDeleteProcessId() throws Exception {
		
		String query = this.getProperty("SQL_DELETE_PID_CIV");
		
		return query;
	}
	
	
	//Metodo che restituisce la query di caricamento letta da file di property
	private String getProperty(String propName) {

		String p = this.props.getProperty(propName+ "." + this.getFkEnteSorgente());
		
		if (p==null)
			p = this.props.getProperty(propName);
			
		return p;
	}
	
	@Override
	//Metodo che stabilisce se viene o no fornito il codice originario del civico
	public boolean codiceCivico(String codEnte, HashParametriConfBean paramConfBean) throws Exception{
			
		//La fonte non gestisce il civico di origine
		return false;
	}
}
