package it.webred.ct.proc.ario.fonti.civico.demografia;


import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.ct.proc.ario.GestioneStringheVie;
import it.webred.ct.proc.ario.bean.Civico;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
//import it.webred.ct.proc.ario.bean.SitCivicoTotale;
//import it.webred.ct.proc.ario.bean.SitRicercaTotale;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.fonti.civico.Civici;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CivicoDemografia extends DatoDwh implements Civici{

	private Properties props = null;
	
	protected static final Logger log = Logger.getLogger(CivicoDemografia.class.getName());
	
	
	public CivicoDemografia(){
		
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
	//Metodo che indica se la tabella è gestita o no tramite PROCESSID
	public boolean existProcessId(){
		return true;
	}
		
	@Override
	//Metodo che restiuisce la chiave dell'ente sorgente 
	public int getFkEnteSorgente() {
	
		return 1;
	}
	
	
	@Override
	//Metodo che restituisce in numero di fonte del caricatore
	public int getProgEs() {
 
		return 1;
	}
	
	
	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {

		String sqlCivico = this.getProperty("SQL_CIVICO_DEMOGRAFIA");
				
		if (processID != null && !processID.equals("")){
			sqlCivico = sqlCivico + " AND PROCESSID =?";
		}
			
		return sqlCivico;
				
	}

	
	
	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		
		//Tabella del DHW da cui leggere i dati
		String tabella = "SIT_D_VIA";		
		return tabella;
	}
			
	
	@Override
	//Metodo che mappa normalizza e salva i dati
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert, String insCivicoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

		SitCivicoTotale sct = new SitCivicoTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		try{		
			
			String strInd = "AAAA ";
			String civico =	(rs.getString("CIV_LIV1")!=null?rs.getString("CIV_LIV1"):"-");								
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"-");
			
			String indirCiv = "";
			if(civico==null || civico.equals("")){
				indirCiv = indirizzo;
			}else{
				indirCiv=strInd+civico;
			}						
			
			Collection<Civico> colCiv = GestioneStringheVie.restituisciCivico(indirCiv);
			Iterator< Civico> iter = colCiv.iterator();
			
			while(iter.hasNext()){
				
				Civico civ =iter.next();
				
				
				sct.setIdStorico(rs.getString("ID_STORICO"));
				sct.setDataFineVal(rs.getDate("DT_FINE_VAL"));				
				sct.setIdOrigViaTotale(rs.getString("ID_ORIG_VIA"));
				//sct.setCivLiv1(rs.getString("CIV_LIV1"));
				sct.setCivLiv1(civ.getCivLiv1());
				sct.setCivLiv2(rs.getString("CIV_LIV2"));
				sct.setCivLiv3(rs.getString("CIV_LIV3"));
				sct.setCivicoComp( rs.getObject("CIVICO_COMPOSTO"));				
				sct.setAnomalia(civ.getAnomalia());				
				
				//Setto il codice Civico se impostato da configurazione
				boolean codCiv = this.codiceCivico(codEnte, paramConfBean);			
				if(codCiv){
					sct.setCodiceCivOrig(rs.getString("CODICE_CIVICO"));
				}else{
					sct.setCodiceCivOrig(null);
				}			
												
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
			log.warn("Errore:Save Civico Demografia="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Save Civico Demografia:"+e.getMessage());
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
			
			String strInd = "AAAA ";
			String civico =	(rs.getString("CIV_LIV1")!=null?rs.getString("CIV_LIV1"):"-");									
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"-");
			
			String indirCiv = "";
			if(civico==null || civico.equals("")){
				indirCiv = indirizzo;
			}else{
				indirCiv=strInd+civico;
			}		
			
			Collection<Civico> colCiv = GestioneStringheVie.restituisciCivico(indirCiv);
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
			
				sct.setIdStorico(rs.getString("ID_STORICO"));
				sct.setDataFineVal(rs.getDate("DT_FINE_VAL"));
				//sct.setCivLiv1(rs.getString("CIV_LIV1"));
				sct.setCivLiv1(civ.getCivLiv1());
				sct.setCivLiv2(rs.getString("CIV_LIV2"));
				sct.setCivLiv3(rs.getString("CIV_LIV3"));
				sct.setCivicoComp( rs.getObject("CIVICO_COMPOSTO"));
				sct.setFkCivico(null);
				sct.setRelDescr(null);
				sct.setRating(null);							
				sct.setAnomalia(civ.getAnomalia());	
				sct.setIdOrigViaTotale(rs.getString("ID_ORIG_VIA"));
				
				
				//Setto il codice civico se impostato da configurazione
				boolean codCiv = this.codiceCivico(codEnte, paramConfBean);			
				if(codCiv){
					sct.setCodiceCivOrig(rs.getString("CODICE_CIVICO"));
				}else{
					sct.setCodiceCivOrig(null);
				}			
			
				
				//Normalizzazione Dato
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
			log.warn("Errore:Update Civico Demografia="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Update Civico Demografia:"+e.getMessage());
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
		
		boolean codCivicoUsato = false;
		
		try{
			
			Integer enteSorgente = this.getFkEnteSorgente();
			String fonte = String.valueOf(enteSorgente);
			
			String progEs = String.valueOf(this.getProgEs());			
						
			codCivicoUsato = super.getCodiceOriginario("codice.orig.civico", codEnte, fonte, progEs, paramConfBean);
			
		}catch (Exception e) {
			log.warn("Errore:Recupero Codice Originario Civico="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Recupero Codice Originario Civico:"+e.getMessage());
			throw ea;
		}
	  		
		return codCivicoUsato;
	}
}
