package it.webred.ct.proc.ario.fonti.via.successioni;

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
import it.webred.ct.proc.ario.fonti.via.locazioni.ViaLocazioniA;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;

public class ViaSuccessioniOggetto extends DatoDwh implements Via{

	private Properties props = null;
	protected static final Logger log = Logger.getLogger(ViaSuccessioniOggetto.class.getName());
	
	
	public ViaSuccessioniOggetto(){
		
		//Caricamento del file di properties dei caricatori		
		this.props = new Properties();
		try {
			InputStream is = this.getClass().getResourceAsStream("/sql/caricatori.sql");
		    this.props.load(is);                     
		}catch(Exception e) {
		    log.error("Eccezione: "+e.getMessage());			   
		}																
	}
	

	/**
	 * Metodo che controlla se la tabella del DWH è stata droppata
	 */
	@Override
	public boolean dwhIsDrop(Connection conn) throws Exception {
		
		return false;
	}

	@Override
	//Metodo che indica se la tabella è gestita o no tramite PROCESSID
	public boolean existProcessId(){
		return false;
	}


	//Metodo che restituisce la query di cancellazione tabella Totale
	public String getDeleteSQL(){
		
		String sqlDeleteTot = this.getProperty("SQL_DEL_VIA_TOTALE");		
		return sqlDeleteTot;
	}

	@Override
	public int getFkEnteSorgente() {
		
		return 6;
	}

	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
		
		String sqlInsertTot = this.getProperty("SQL_INS_VIA_TOTALE");		
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

	//Metodo che restituisce la query di ricerca in tabella Totale
	public String getSearchSQL(){
		
		String sqlSearchTot = this.getProperty("SQL_CERCA_VIA_TOTALE");		
		return sqlSearchTot;
	}

	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {

		String sqlViaSuccessioni = this.getProperty("SQL_VIA_SUCCESSIONI_OGGETTO");
						
		return sqlViaSuccessioni;
				
	}

	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		
		//Tabella del DHW da cui leggere i dati
		String tabella = "SUCCESSIONI_C";		
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
			String idStorico = rs.getString("ID_STORICO");
			Date dataFineVal = rs.getDate("DT_FINE_VAL");
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"-");
			String sedime = "-";
			String note = null;
			Collection<Civico> colCiv = GestioneStringheVie.restituisciCivico(indirizzo);
			Iterator< Civico> iter = colCiv.iterator();
			if(iter.hasNext()){
				Civico civ =iter.next();
				indirizzo= (String)civ.getInd();
				sedime = (String)civ.getSed();
				note = civ.getNote();
			}
			  
									
			svt.setIdStorico(idStorico);
			svt.setDtFineVal(dataFineVal);			
			svt.setSedime(sedime);
			svt.setIndirizzo(indirizzo);					
			svt.setNote(note);
			
			//Setto il codice via letto (sarà sempre null per la fonte data)								
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
			log.warn("Errore:Save Via Successioni Oggetto="+e.getMessage());
			Exception ea = new Exception("Errore:Save Via Successioni Oggetto:"+e.getMessage());
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
			String sedime = "-";
			String note = null;
			Collection<Civico> colCiv = GestioneStringheVie.restituisciCivico(indirizzo);
			Iterator< Civico> iter = colCiv.iterator();
			if(iter.hasNext()){
				Civico civ =iter.next();
				indirizzo= (String)civ.getInd();
				sedime = (String)civ.getSed();
				note = civ.getNote();
			}
			  
									
			svt.setIdStorico(idStorico);
			svt.setDtFineVal(dataFineVal);			
			svt.setSedime(sedime);
			svt.setIndirizzo(indirizzo);			
			svt.setFkVia(null);
			svt.setRelDescr(null);
			svt.setRating(null);	
			
			//Setto il codice via letto (sarà sempre null per la fonte data)								
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
			log.warn("Errore:Update Via Successioni Oggetto="+e.getMessage());
			Exception ea = new Exception("Errore:Update Via Successioni Oggetto:"+e.getMessage());
			throw ea;
		}
		
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
	
	@Override
	//Metodo che stabilisce se viene o no fornito il codice originario della via dal viario
	public boolean codiceVia(String codEnte, HashParametriConfBean paramConfBean) throws Exception{
			
		//La fonte non gestisce le vie con viario di origine
		return false;
	}

}
