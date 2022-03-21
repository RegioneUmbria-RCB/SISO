package it.webred.ct.proc.ario.fonti.via.tributi;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitViaTotale;
import it.webred.ct.proc.ario.GestioneStringheVie;
import it.webred.ct.proc.ario.bean.Civico;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.fonti.via.Via;
import it.webred.ct.proc.ario.fonti.via.fornitureElettriche.ViaEnel;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;
import it.webred.rulengine.ario.bean.Indirizzo;

public class ViaTributiOggettoIci extends DatoDwh implements Via{

	private Properties props = null;
	
	protected static final Logger log = Logger.getLogger(ViaTributiOggettoIci.class.getName());
		
	
	public ViaTributiOggettoIci(){
		
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
		
		String sql = this.getProperty("SQL_DWH_IS_DROP_VIA");
		
		sql = sql.replace("$TAB", this.getTable());
		
		int fkEnteSorgente = this.getFkEnteSorgente();
		int progEs = this.getProgEs();
		
		return super.executeIsDropDWh(sql, conn, fkEnteSorgente, progEs);
	}
	

	@Override
	//Metodo che indica se la tabella è gestita o no tramite PROCESSID
	public boolean existProcessId(){
		return true;
	}


	//Metodo che restituisce la query di cancellazione tabella Totale
	public String getDeleteSQL(){
		
		String sqlDeleteTot = this.getProperty("SQL_DEL_VIA_TOTALE");		
		return sqlDeleteTot;
	}

	@Override
	public int getFkEnteSorgente() {
		
		return 2;
	}

	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
				
		String sqlInsertTot = this.getProperty("SQL_INS_VIA_TOTALE");		
		return sqlInsertTot;
	}

	@Override
	public int getProgEs() {
		
		return 2;
	}

	//Metodo che restituisce la query che aggiorna e cancella un processId dalla tabella di gestione PID
	@Override
	public String getQuerySQLDeleteProcessId() throws Exception {
		
		String query = this.getProperty("SQL_DELETE_PID_VIA");
		
		return query;
	}

	//Metodo che restituisce la query per trovare i PID della tabella DWH
	@Override
	public String getQuerySQLgetProcessId() throws Exception {
		
		String query = this.getProperty("SQL_GET_PID_DWH_VIA");
		
		return query;
	}

	@Override
	//Metodo che restituisce la query dei nuovi processId da caricare
	public String getQuerySQLNewProcessId() throws Exception {

		String query = this.getProperty("SQL_NEW_PID_DWH_VIA");
		
		return query;
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

	//Metodo che restituisce la query di ricerca in tabella Totale
	public String getSearchSQL(){
		
		String sqlSearchTot = this.getProperty("SQL_CERCA_VIA_TOTALE");		
		return sqlSearchTot;
	}

	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {

		
		String sqlVia = this.getProperty("SQL_VIA_TRIBUTI_ICI");
				
		if (processID != null && !processID.equals("")){
			sqlVia = sqlVia + " WHERE iciv.PROCESSID =?";			
		}
			
		return sqlVia;
				
	}

	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		
		//Tabella del DHW da cui leggere i dati
		String tabella = "SIT_T_ICI_VIA";		
		return tabella;
	}

	//Metodo che restituisce la query di update in tabella Totale
	public String getUpdateSQL(){
				
		String sqlUpdateTot = this.getProperty("SQL_UPDATE_VIA_TOTALE");		
		return sqlUpdateTot;
	}

	@Override
	//Metodo che mappa normalizza e salva i dati
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert, String insViaTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

		SitViaTotale svt = new SitViaTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		
		try{			
			
			String idDwh = rs.getString("ID_DWH");			
			Date dataFineVal = rs.getDate("DT_FINE_VAL");			
			String sedime = rs.getString("VIASEDIME"); 
			String sedime1 = GestioneStringheVie.trovaSedimeUnivoco(sedime);
			if (sedime1 != null)
				sedime = sedime1;
			
			
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"-");			
			String note = null;
			Collection<Civico> colCiv = GestioneStringheVie.restituisciCivico(indirizzo);
			Iterator< Civico> iter = colCiv.iterator();
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
			  
			svt.setIdStorico(rs.getString("ID_STORICO"));									
			svt.setDtFineVal(dataFineVal);			
			svt.setSedime(sedime);
			svt.setIndirizzo(indirizzo);			
			svt.setNote(note);		
			
			//Setto il codice Via se impostato da configurazione
			boolean codVia = this.codiceVia(codEnte, paramConfBean);			
			if(codVia){
				svt.setCodiceViaOrig(rs.getString("CODICE_VIA"));
			}else{
				svt.setCodiceViaOrig(null);
			}
			
			
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
			log.warn("Errore:Save Via Tributi ICI="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Save Via Tributi ICI:"+e.getMessage());
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
			Date dataFineVal = rs.getDate("DT_FINE_VAL");			
			String sedime = rs.getString("VIASEDIME"); 
			String sedime1 = GestioneStringheVie.trovaSedimeUnivoco(sedime);
			if (sedime1 != null)
				sedime = sedime1;
			
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"-");
			
			String note = null;
			Collection<Civico> colCiv = GestioneStringheVie.restituisciCivico(indirizzo);
			Iterator< Civico> iter = colCiv.iterator();
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
			  
					
			svt.setIdStorico(rs.getString("ID_STORICO"));
			svt.setDtFineVal(dataFineVal);			
			svt.setSedime(sedime);
			svt.setIndirizzo(indirizzo);
			svt.setNote(note);
			svt.setFkVia(null);
			svt.setRelDescr(null);
			svt.setRating(null);		
						
			
			//Setto il codice Via se impostato da configurazione
			boolean codVia = this.codiceVia(codEnte, paramConfBean);			
			if(codVia){
				svt.setCodiceViaOrig(rs.getString("CODICE_VIA"));
			}else{
				svt.setCodiceViaOrig(null);
			}
			
			
						
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
				//Aggiorna elemento già presente in VIA TOTALE
				String sqlAggCiv = getProperty("SQL_AGG_CIV_DA_VIA");
				super.updateSitViaTotale(classeFonte,connForInsert,updateViaTotale,sqlAggCiv,svt);				
			}else if(azione.equals("INSERISCI")){
				//Salva il nuovo elemento				
				super.saveSitViaTotale(classeFonte, connForInsert, insViaTotale, svt);										
			}

			
		}catch (Exception e) {
			log.warn("Errore:Update Via Tributi ICI="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Update Via Tributi ICI:"+e.getMessage());
			throw ea;
		}
		
	}

	//Metodo che indica se la query di lettura da DWH ha come parametro il CodiceEnte
	public boolean queryWithParamCodEnte(){
		return false;
	}
	
	@Override
	//Metodo che stabilisce se viene o no fornito il codice originario della via dal viario
	public boolean codiceVia(String codEnte, HashParametriConfBean paramConfBean) throws Exception{
		
		boolean codViaUsato = false;
		
		try{
			
			Integer enteSorgente = this.getFkEnteSorgente();
			String fonte = String.valueOf(enteSorgente);
			
			String progEs = String.valueOf(this.getProgEs());			
						
			codViaUsato = super.getCodiceOriginario("codice.orig.via", codEnte, fonte, progEs, paramConfBean);
			
		}catch (Exception e) {
			log.warn("Errore:Recupero Codice Originario Vie="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Recupero Codice Originario Vie:"+e.getMessage());
			throw ea;
		}
		  		
		return codViaUsato;
	}
	
	//Metodo che restituisce la query di caricamento letta da file di property
	private String getProperty(String propName) {

		String p = this.props.getProperty(propName+ "." + this.getFkEnteSorgente());
		
		if (p==null)
			p = this.props.getProperty(propName);
			
		return p;
	}
}
