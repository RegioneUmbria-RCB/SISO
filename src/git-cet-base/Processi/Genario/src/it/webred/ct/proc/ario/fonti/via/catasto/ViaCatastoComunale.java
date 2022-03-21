package it.webred.ct.proc.ario.fonti.via.catasto;

import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitViaTotale;
import it.webred.ct.proc.ario.GestioneStringheVie;
import it.webred.ct.proc.ario.bean.Civico;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.bean.Indirizzo;
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

public class ViaCatastoComunale extends DatoDwh implements Via{

	private Properties props = null;

	protected static final Logger log = Logger.getLogger(ViaCatastoComunale.class.getName());
	
	
	public ViaCatastoComunale(){
		
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
		
	@Override
	//Metodo che restiuisce la chiave dell'ente sorgente 
	public int getFkEnteSorgente() {
	
		return 4;
	}
	
	
	@Override
	//Metodo che restituisce in numero di fonte del caricatore
	public int getProgEs() {
 
		return 2;
	}
	
	
	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {

		String sqlVia = this.getProperty("SQL_VIA_CATASTO");
						
		return sqlVia;
				
	}

	
	
	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		
		//Tabella del DHW da cui leggere i dati
		String tabella = "SITIDSTR";		
		return tabella;
	}

	
	@Override
	//Metodo che mappa normalizza e salva i dati
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert, String insViaTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

		SitViaTotale svt = new SitViaTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		try{			
			
			String id_dwh = rs.getString("ID_DWH");
			String sedime = (rs.getString("PREFISSO")!=null?rs.getString("PREFISSO"):"-");
			String sedime1 = GestioneStringheVie.trovaSedimeUnivoco(sedime);
			if (sedime1 != null)
				sedime = sedime1;
			String indirizzo = (rs.getString("NOME")!=null?rs.getString("NOME"):"-");
			String idStorico = rs.getString("ID_STORICO");
			Date dataFineVal = rs.getDate("DT_FINE_VAL");
			
			/*Indirizzo<String, String> ind1 = GestioneStringheVie.restituisciIndirizzo(indirizzo, sedime);
			indirizzo= ind1.getInd();
			sedime = ind1.getSed();*/
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
			
			
			
			svt.setIdStorico(idStorico);
			svt.setDtFineVal(dataFineVal);			
			svt.setSedime(sedime);
			svt.setIndirizzo(indirizzo);			
						
			//Setto il codice via se impostato da configurazione
			boolean codVia = this.codiceVia(codEnte, paramConfBean);			
			if(codVia){
				svt.setCodiceViaOrig(rs.getString("CODICE_VIA"));
			}else{
				svt.setCodiceViaOrig(null);
			}			

			
			//Normalizzazione
			nt.normalizzaVia(svt);
			
			//Setto indice Soggetto
			iPk.setIdDwh(id_dwh);
			iPk.setFkEnteSorgente(this.getFkEnteSorgente());
			iPk.setProgEs(this.getProgEs());							
			//Calcolo Hash di chiave
			iPk.setCtrHash(setCtrHashSitViaTotale(svt));		
			
			svt.setId(iPk);

			
			
			//Salvataggio
			super.saveSitViaTotale(classeFonte, connForInsert, insViaTotale, svt);
			
			
		}catch (Exception e) {
			log.warn("Errore:Save Via Catasto Comunale="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Save Via Catasto Comunale:"+e.getMessage());
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
			
			String id_dwh = rs.getString("ID_DWH");
			String sedime = (rs.getString("PREFISSO")!=null?rs.getString("PREFISSO"):"-");
			String sedime1 = GestioneStringheVie.trovaSedimeUnivoco(sedime);
			if (sedime1 != null)
				sedime = sedime1;
			String indirizzo = (rs.getString("NOME")!=null?rs.getString("NOME"):"-");
			String idStorico = rs.getString("ID_STORICO");
			Date dataFineVal = rs.getDate("DT_FINE_VAL");
			
			/*Indirizzo<String, String> ind1 = GestioneStringheVie.restituisciIndirizzo(indirizzo, sedime);
			indirizzo= ind1.getInd();
			sedime = ind1.getSed();*/
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
			
						
			svt.setIdStorico(idStorico);
			svt.setDtFineVal(dataFineVal);			
			svt.setSedime(sedime);
			svt.setIndirizzo(indirizzo);
			svt.setFkVia(null);
			svt.setRelDescr(null);
			svt.setRating(null);				
			
			
			//Setto il codice via se impostato da configurazione
			boolean codVia = this.codiceVia(codEnte, paramConfBean);			
			if(codVia){
				svt.setCodiceViaOrig(rs.getString("CODICE_VIA"));
			}else{
				svt.setCodiceViaOrig(null);
			}

			
			//Mappo i campi chiave (usati anche per la ricerca dei dati da aggiornare)
			iPk.setIdDwh(id_dwh);
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
			log.warn("Errore:Update Via Catasto Comunale="+e.getMessage(), e);
			Exception ea = new Exception("Errore:Update Via Catasto Comunale:"+e.getMessage());
			throw ea;
		}
		
	}
	
	
	//Metodo che indica se la query di lettura da DWH ha come parametro il CodiceEnte
	public boolean queryWithParamCodEnte(){
		return true;
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

	
	
	@Override
	//Metodo che restituisce la query per l'inserimento del PID elaborato per gestione con PID
	public String getQuerySQLSaveProcessId() throws Exception {
				
		return null;
	}

	
	@Override
	//Metodo che restituisce la query per l'update del PID elaborato per gestione con PID
	public String getQuerySQLUpdateProcessId() throws Exception {
					
		return null;
	}
	
	@Override
	//Metodo che restituisce la query dei nuovi processId da caricare
	public String getQuerySQLNewProcessId() throws Exception {

		return null;
	}
	
	//Metodo che restituisce la query per trovare i PID della tabella DWH
	@Override
	public String getQuerySQLgetProcessId() throws Exception {
		
		return null;
	}
	
	//Metodo che restituisce la query che aggiorna e cancella un processId dalla tabella di gestione PID
	@Override
	public String getQuerySQLDeleteProcessId() throws Exception {
		
		return null;
	}
	
	
	//Metodo che restituisce la query di caricamento letta da file di property
	private String getProperty(String propName) {

		String p = this.props.getProperty(propName+ "." + this.getFkEnteSorgente());
		
		if (p==null)
			p = this.props.getProperty(propName);
			
		return p;
	}
	
	
	@Override
	//Metodo che stabilisce se viene o no fornito il codice originario della via dal viario
	public boolean codiceVia(String codEnte, HashParametriConfBean paramConfBean) throws Exception{
		
		boolean codViaUsato = false;
		/*String usato = this.propsCodVia.getProperty("catasto.comunale.crit.via.orig");
		
		if(usato.equals("true")){
			codViaUsato = true;
		}else{
			codViaUsato = false;
		}*/
		
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
}
