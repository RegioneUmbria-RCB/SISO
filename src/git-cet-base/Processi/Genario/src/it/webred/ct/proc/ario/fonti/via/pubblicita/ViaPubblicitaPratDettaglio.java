package it.webred.ct.proc.ario.fonti.via.pubblicita;

import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitViaTotale;
import it.webred.ct.proc.ario.GestioneStringheVie;
import it.webred.ct.proc.ario.bean.Civico;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
//import it.webred.ct.proc.ario.bean.SitRicercaTotale;
//import it.webred.ct.proc.ario.bean.SitViaTotale;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.fonti.via.Via;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ViaPubblicitaPratDettaglio extends DatoDwh implements Via{

	private Properties props = null;
	protected static final Logger log = Logger.getLogger(ViaPubblicitaPratDettaglio.class.getName());
	
	
	public ViaPubblicitaPratDettaglio(){
		
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
	
		return 27;
	}
	
	
	@Override
	//Metodo che restituisce in numero di fonte del caricatore
	public int getProgEs() {
 
		return 2;
	}
	
	
	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {

		String sqlVia = this.getProperty("SQL_VIA_PUBBLICITA_DETTAGLIO");
				
		if (processID != null && !processID.equals("")){
			sqlVia = sqlVia + " WHERE PROCESSID =?";
		}
			
		return sqlVia;
				
	}

	
	
	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		
		//Tabella del DHW da cui leggere i dati
		String tabella = "SIT_PUBBLICITA_PRAT_DETTAGLIO";		
		return tabella;
	}
	
	
	@Override
	//Metodo che mappa normalizza e salva i dati
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert, String insViaTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

		SitViaTotale svt = new SitViaTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		try{			
			
			String idDwh = rs.getString("ID_DWH");
			String idStorico = rs.getString("ID_STORICO");
			Date dataFineVal = rs.getDate("DT_FINE_VAL");
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"-");
			String sedime = rs.getString("VIASEDIME"); 
			String note = null;
			Collection<Civico> colCiv = GestioneStringheVie.restituisciCivico(indirizzo);
			Iterator<Civico> iter = colCiv.iterator();
			if(iter.hasNext()){
				Civico civ =iter.next();
				indirizzo= (String)civ.getInd();
				if(isInvalid(sedime)){
					String civSedime= (String)civ.getSed();
					String civSedime1 = GestioneStringheVie.trovaSedimeUnivoco(civSedime);
					sedime = civSedime1!=null ? civSedime1 : civSedime;
				}
				note = civ.getNote();
			}
			  
									
			svt.setIdStorico(idStorico);
			svt.setDtFineVal(dataFineVal);		
			svt.setSedime(sedime);
			svt.setIndirizzo(indirizzo);			
			svt.setNote(note);
			
			svt.setField1(rs.getString("FIELD1"));
			svt.setField2(rs.getString("FIELD2"));
			svt.setField3(rs.getString("FIELD3"));
			svt.setField4(rs.getString("FIELD4"));
			svt.setField5(rs.getString("FIELD5"));
			svt.setField6(rs.getString("FIELD6"));
			svt.setField7(rs.getString("FIELD7"));
			svt.setField8(rs.getString("FIELD8"));
			svt.setField9(rs.getString("FIELD9"));
			svt.setField10(rs.getString("FIELD10"));
			svt.setField11(rs.getString("FIELD11"));
			
			//Setto il codice via letto (saràsempre null per la fonte data)								
			svt.setCodiceViaOrig(null);

			
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
			log.warn("Errore:Save Via Pubblicita Dettaglio="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Save Via Pubblicita Dettaglio:"+e.getMessage());
			throw ea;
		}
		
	}
	
	
	@Override 
	//Metodo che mappa normalizza e aggiorna i dati
	public void prepareUpdateDato(DatoDwh classeFonte, Connection connForInsert, String insViaTotale, String updateViaTotale, String searchViaTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

		SitViaTotale svt = new SitViaTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		try{			
			
			String idDwh = rs.getString("ID_DWH");
			String idStorico = rs.getString("ID_STORICO");
			Date dataFineVal = rs.getDate("DT_FINE_VAL");
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"-");
			String sedime = rs.getString("VIASEDIME"); 
			String note = null;
			Collection<Civico> colCiv = GestioneStringheVie.restituisciCivico(indirizzo);
			Iterator<Civico> iter = colCiv.iterator();
			if(iter.hasNext()){
				Civico civ =iter.next();
				indirizzo= (String)civ.getInd();
				if(isInvalid(sedime)){
					String civSedime= (String)civ.getSed();
					String civSedime1 = GestioneStringheVie.trovaSedimeUnivoco(civSedime);
					sedime = civSedime1!=null ? civSedime1 : civSedime;
				}
				note = civ.getNote();
			}
			  		
			svt.setIdStorico(idStorico);
			svt.setDtFineVal(dataFineVal);			
			svt.setSedime(sedime);
			svt.setIndirizzo(indirizzo);			
			svt.setFkVia(null);
			svt.setRelDescr(null);
			svt.setRating(null);	
			
			svt.setField1(rs.getString("FIELD1"));
			svt.setField2(rs.getString("FIELD2"));
			svt.setField3(rs.getString("FIELD3"));
			svt.setField4(rs.getString("FIELD4"));
			svt.setField5(rs.getString("FIELD5"));
			svt.setField6(rs.getString("FIELD6"));
			svt.setField7(rs.getString("FIELD7"));
			svt.setField8(rs.getString("FIELD8"));
			svt.setField9(rs.getString("FIELD9"));
			svt.setField10(rs.getString("FIELD10"));
			svt.setField11(rs.getString("FIELD11"));
			
			//Setto il codice via letto (saràsempre null per la fonte data)								
			svt.setCodiceViaOrig(null);

			
			//Mappo i campi chiave (usati anche per la ricerca dei dati da aggiornare)
			iPk.setIdDwh(idDwh);
			iPk.setFkEnteSorgente(this.getFkEnteSorgente());
			iPk.setProgEs(this.getProgEs());

			
			//Normalizzazione Dati
			nt.normalizzaVia(svt);
			
			//Calcolo hash per la ricerca
			iPk.setCtrHash(setCtrHashSitViaTotale(svt));					
			//Setto la chiave
			svt.setId(iPk);
			
	
			String azione = super.trovaDatoTotale(classeFonte, connForInsert,searchViaTotale,iPk);								
			
			if(azione.equals("AGGIORNA")){
				//Aggiorna elemento già presente	
				String sqlAggCiv = getProperty("SQL_AGG_CIV_DA_VIA");
				super.updateSitViaTotale(classeFonte,connForInsert,updateViaTotale,sqlAggCiv,svt);										
			}else if(azione.equals("INSERISCI")){
				//Salva il nuovo elemento				
				super.saveSitViaTotale(classeFonte, connForInsert, insViaTotale, svt);										
			}

			
		}catch (Exception e) {
			log.warn("Errore:Update Via Pubblicita Dettaglio="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Update Via Pubblicita Dettaglio:"+e.getMessage());
			throw ea;
		}
		
	}
	
	
	//Metodo che indica se la query di lettura da DWH ha come parametro il CodiceEnte
	public boolean queryWithParamCodEnte(){
		return false;
	}

	
	//Metodo che restituisce la query di cancellazione tabella Totale
	public String getDeleteSQL(){
		
		String sqlDeleteTot = this.getProperty("SQL_DEL_VIA_TOTALE");		
		return sqlDeleteTot;
	}
	
	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
		
		String sqlInsertTot = this.getProperty("SQL_INS_VIA_TOTALE");		
		return sqlInsertTot;
	}
	
	//Metodo che restituisce la query di update in tabella Totale
	public String getUpdateSQL(){
		
		String sqlUpdateTot = this.getProperty("SQL_UPDATE_VIA_TOTALE");		
		return sqlUpdateTot;
	}
	
	//Metodo che restituisce la query di ricerca in tabella Totale
	public String getSearchSQL(){
		
		String sqlSearchTot = this.getProperty("SQL_CERCA_VIA_TOTALE");		
		return sqlSearchTot;
	}
	
	
	/**
	 * Metodo che controlla se la tabella del DWH è stata droppata
	 */
	@Override
	public boolean dwhIsDrop(Connection conn) throws Exception {
		
		String sql = this.getProperty("SQL_DWH_IS_DROP_VIA");
		
		sql = sql.replace("$TAB", this.getTable());
		
		int fkEnteSorgente = this.getFkEnteSorgente();
		int progEs = this.getProgEs();
		
		return super.executeIsDropDWh(sql, conn, fkEnteSorgente, progEs);
	}
	
	
	@Override
	//Metodo che restituisce la query per l'inserimento del PID elaborato per gestione con PID
	public String getQuerySQLSaveProcessId() throws Exception {
		
		String query = this.getProperty("SQL_INSERT_PID_VIA");
			
		return query;
	}

	@Override
	//Metodo che restituisce la query per l'update del PID elaborato per gestione con PID
	public String getQuerySQLUpdateProcessId() throws Exception {
			
		String query = this.getProperty("SQL_UPDATE_PID_VIA");
		
		return query;
	}
	
	@Override
	//Metodo che restituisce la query dei nuovi processId da caricare
	public String getQuerySQLNewProcessId() throws Exception {

		String query = this.getProperty("SQL_NEW_PID_DWH_VIA");
		
		return query;
	}

	//Metodo che restituisce la query per trovare i PID della tabella DWH
	@Override
	public String getQuerySQLgetProcessId() throws Exception {
		
		String query = this.getProperty("SQL_GET_PID_DWH_VIA");
		
		return query;
	}
	
	
	//Metodo che restituisce la query che aggiorna e cancella un processId dalla tabella di gestione PID
	@Override
	public String getQuerySQLDeleteProcessId() throws Exception {
		
		String query = this.getProperty("SQL_DELETE_PID_VIA");
		
		return query;
	}
	
	
	//Metodo che restituisce la query di caricamento letta da file di property
	private String getProperty(String propName) {

		String p = this.props.getProperty(propName+ "." + this.getFkEnteSorgente());
		
		if (p==null)
			p = this.props.getProperty(propName);
		
		log.debug(propName+ "." + this.getFkEnteSorgente() +" SQL Via_Pubblicita_Prat_Dettaglio["+p+"]");
		
		return p;
	}
	
	@Override
	//Metodo che stabilisce se viene o no fornito il codice originario della via dal viario
	public boolean codiceVia(String codEnte, HashParametriConfBean paramConfBean) throws Exception{
			
		//La fonte non gestisce le vie con viario di origine
		return false;
	}
}
