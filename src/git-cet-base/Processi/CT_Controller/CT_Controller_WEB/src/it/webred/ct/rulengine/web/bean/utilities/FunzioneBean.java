package it.webred.ct.rulengine.web.bean.utilities;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.controller.ejbclient.utilities.dto.GestioneUtilitiesDTO;
import it.webred.ct.controller.ejbclient.utilities.dto.InputFunzioneDTO;
import it.webred.ct.controller.ejbclient.utilities.dto.LogFunzioniDTO;
import it.webred.ct.controller.ejbclient.utilities.dto.RicercaLogFunzioniDTO;
import it.webred.ct.rulengine.controller.model.RConnection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

public class FunzioneBean extends UtilitiesBean {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(FunzioneBean.class.getName());

	private String funzioneCorrente;
	private String statoCorrente;
	
	private String enteSelezionato;
	private String tabellaSelezionata;
	private String connessioneSelezionata;
	
	private List<LogFunzioniDTO>  listaLog;
	
	
	private final String F_RIC_HASH = "Ricalcolo Hash";
	private final String F_EMPTY_TABLE = "Svuotamento Tabella";
	
	private final String STATO_ERRORE = "STATO_ERRORE";
	private final String STATO_OK = "STATO_OK";
	
	private HashMap<String, RConnection> listaConnessioni = new HashMap<String, RConnection>();
	
	private List<SelectItem> listaTipoConnessioni = new ArrayList<SelectItem>();
	private List<SelectItem> listaEnti = new ArrayList<SelectItem>();
	

	@PostConstruct
	public void init() {
		
		 logger.debug("Inizializzazione");
	
		doCaricaParams();
		
		if (listaEnti.size() > 0) 
			enteSelezionato = (String) listaEnti.get(0).getValue();
		
		if (listaTipoConnessioni.size() > 0) 
			connessioneSelezionata = "DWH";
		
		doCaricaLastStatoFunzione();
		
	}
	
	
	public void doCaricaLog(){
		
		RicercaLogFunzioniDTO ric = new RicercaLogFunzioniDTO();
		
		ric.setNome(this.funzioneCorrente);
		 logger.debug("Funzione corrente ricerca log: " + funzioneCorrente);
		
		listaLog = controllerUtService.getListaLogFunzioni(ric);
		
	}
	
	
	public void doCaricaLastStatoFunzione(){
		
		doCaricaLog();
		if(listaLog.size()>0)
			statoCorrente = listaLog.get(0).getStato();
		
	}
	
	
	public void doCaricaParams(){
		
		doCaricaListaEnti();
		doCaricaListaConnessioni();
		
	}
	
	public void doCaricaListaEnti(){
		
		List<AmComune> listaComuni = comuneService.getListaComune();
		for (AmComune comune : listaComuni) {
			listaEnti.add(new SelectItem(comune.getBelfiore(), comune.getDescrizione()));
		}
		
		
	}
	
	public void doAction(){
		
		if(this.funzioneCorrente.equalsIgnoreCase(this.F_RIC_HASH))
			this.doRicalcolaHash();
		else if(this.funzioneCorrente.equalsIgnoreCase(this.F_EMPTY_TABLE))
			this.doSvuotaTabella();
		//Completare con altre funzioni
		
	}
	
	public void doCaricaListaConnessioni(){
		
		 List<RConnection> result = utilService.getConnessioni();
		 
		 List<String> temp = new ArrayList<String>();
		 for(RConnection conn : result){
			
			 String nome = conn.getName();
		
			 int index = nome.indexOf("_");
			 
			 if(index>0)
				 nome = nome.substring(0,index);
			 
			 if(!temp.contains(nome))
				 temp.add(nome);
			 
			 listaConnessioni.put(conn.getName(), conn);
			 
		 }
		 
		
		 for(String s : temp){
		 	SelectItem siNome = new SelectItem(s, s);
		 	listaTipoConnessioni.add(siNome);
		 }
 
	}
	
		
	
	private GestioneUtilitiesDTO scriviLogInizio(HashMap<String, Object> params){
		
		GestioneUtilitiesDTO gu = new GestioneUtilitiesDTO();
		
		gu.setNomeFunzione(this.funzioneCorrente);
		gu.setOperatore(this.getUsername());
		gu.setParametri(params);
		
		Long idLog = controllerUtService.inserisciNuovoLogFunzione(gu);
		gu.setId(idLog);
		
		return gu;
	}
	
	private void scriviLogFine(GestioneUtilitiesDTO gu){
		//Aggiorno lo stato del log di esecuzione
		gu.setStato(this.STATO_OK);
		controllerUtService.updateFineLogFunzione(gu);
	
	}
		
	private void scriviLogError(GestioneUtilitiesDTO gu, String message){
		//Aggiorno lo stato del log di esecuzione
		
		gu.setStato(this.STATO_ERRORE);
		gu.setNote(message);
		controllerUtService.updateFineLogFunzione(gu);
	}
	
	public void doRicalcolaHash() {
		
		GestioneUtilitiesDTO gu = new GestioneUtilitiesDTO();
		
		try {
			
			if(tabellaSelezionata!=null && enteSelezionato!=null && !tabellaSelezionata.equalsIgnoreCase("")){
				
				String connessione = connessioneSelezionata+"_"+enteSelezionato.toUpperCase();
				logger.debug("Connessione: " + connessione);
				
				//Preparo il DTO per inserire il log di esecuzione
				HashMap<String, Object> params = new HashMap();
				params.put("Ente", enteSelezionato);
				params.put("Tabella", tabellaSelezionata);
				params.put("Connessione", connessione);
				
				gu = this.scriviLogInizio(params);
				
				//Preparo il DTO per l'esecuzione del ricalcolo
				InputFunzioneDTO input = new InputFunzioneDTO();
				input.setNomeTabella(tabellaSelezionata);
				input.setBelfiore(enteSelezionato);
				
				RConnection conn = listaConnessioni.get(connessione);
				input.setConnessione(conn);
				
				controllerUtService.ricalcolaHash(input);
				
				this.scriviLogFine(gu);
				super.addInfoMessage("utilities.funzione.completato");
				

			}else{
				
				super.addErrorMessage("utilities.funzione.noparams",null);
			}	
		} catch (Exception e) {
			String message = "Eccezione: " + e.getMessage();
			logger.error(message, e);	
			this.scriviLogError(gu, message);
			
			super.addErrorMessage("utilities.funzione.error", e.getMessage());
		}

	}
	
	public void doSvuotaTabella(){
		
		GestioneUtilitiesDTO gu = new GestioneUtilitiesDTO();
		
		try {
		
		if(tabellaSelezionata!=null && enteSelezionato!=null && !tabellaSelezionata.equalsIgnoreCase("")){
			
			String connessione = connessioneSelezionata+"_"+enteSelezionato.toUpperCase();
			logger.debug("Connessione: " + connessione);
			
			//Preparo il DTO per inserire il log di esecuzione
			HashMap<String, Object> params = new HashMap();
			params.put("Ente", enteSelezionato);
			params.put("Tabella", tabellaSelezionata);
			params.put("Connessione", connessione);
			
			gu = this.scriviLogInizio(params);
			
			//Preparo il DTO per l'esecuzione del ricalcolo
			InputFunzioneDTO input = new InputFunzioneDTO();
			input.setNomeTabella(tabellaSelezionata);
			
			RConnection conn = listaConnessioni.get(connessione);
			input.setConnessione(conn);
			
			controllerUtService.svuotaTabella(input);
			
			//Aggiorno lo stato del log di esecuzione
			this.scriviLogFine(gu);
			
			super.addInfoMessage("utilities.funzione.completato");
			
		}else{
			
			super.addErrorMessage("utilities.funzione.noparams",null);
			
			}	
		
		} catch (Exception e) {
			String message = "Eccezione: " + e.getMessage();
			logger.error(message, e);
			
			this.scriviLogError(gu, message);
			
			super.addErrorMessage("utilities.funzione.error", e.getMessage());
		}

			
	}
	

	public String goUtilities() {

		return "controller.utilities";
	}

	
	
	public void resetPage() {
		
		tabellaSelezionata = null;
		connessioneSelezionata = "DWH";
		
		
	}


	public void setTabellaSelezionata(String tabellaSelezionata) {
		this.tabellaSelezionata = tabellaSelezionata.toUpperCase();
		 logger.debug("set tab: " + tabellaSelezionata);
	}


	

	public void setFunzioneCorrente(String funzioneCorrente) {
		this.funzioneCorrente = funzioneCorrente;
		 logger.debug("Funzione: " + funzioneCorrente);
	}

	public String getF_RIC_HASH() {
		return F_RIC_HASH;
	}

	
	
	private void resetEnte(){
		if(this.listaEnti!=null && this.listaEnti.size()>0){}
		else
			this.doCaricaListaEnti();
		
		this.enteSelezionato= (String)this.listaEnti.get(0).getValue();
	}
	
	
	public void reset(){
		this.connessioneSelezionata="DWH";
		this.resetEnte();
		this.tabellaSelezionata=null;
	}

	public String getF_EMPTY_TABLE() {
		return F_EMPTY_TABLE;
	}


	public String getStatoCorrente() {
		return statoCorrente;
	}


	public void setStatoCorrente(String statoCorrente) {
		this.statoCorrente = statoCorrente;
	}


	public String getEnteSelezionato() {
		return enteSelezionato;
	}


	public void setEnteSelezionato(String enteSelezionato) {
		this.enteSelezionato = enteSelezionato;
	}


	public String getConnessioneSelezionata() {
		return connessioneSelezionata;
	}


	public void setConnessioneSelezionata(String connessioneSelezionata) {
		this.connessioneSelezionata = connessioneSelezionata;
	}


	public HashMap<String, RConnection> getListaConnessioni() {
		return listaConnessioni;
	}


	public void setListaConnessioni(HashMap<String, RConnection> listaConnessioni) {
		this.listaConnessioni = listaConnessioni;
	}


	public List<SelectItem> getListaTipoConnessioni() {
		return listaTipoConnessioni;
	}


	public void setListaTipoConnessioni(List<SelectItem> listaTipoConnessioni) {
		this.listaTipoConnessioni = listaTipoConnessioni;
	}


	public List<SelectItem> getListaEnti() {
		return listaEnti;
	}


	public void setListaEnti(List<SelectItem> listaEnti) {
		this.listaEnti = listaEnti;
	}


	public String getFunzioneCorrente() {
		return funzioneCorrente;
	}


	public String getTabellaSelezionata() {
		return tabellaSelezionata;
	}


	public String getSTATO_ERRORE() {
		return STATO_ERRORE;
	}


	public String getSTATO_OK() {
		return STATO_OK;
	}
	
	public List<LogFunzioniDTO> getListaLog() {
		return listaLog;
	}


	public void setListaLog(List<LogFunzioniDTO> listaLog) {
		this.listaLog = listaLog;
	}



	
}
