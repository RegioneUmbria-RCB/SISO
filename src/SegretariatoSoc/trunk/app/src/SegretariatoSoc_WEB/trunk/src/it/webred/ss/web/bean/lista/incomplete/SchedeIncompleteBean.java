package it.webred.ss.web.bean.lista.incomplete;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.web.bean.SegretariatoSocSchedeTblBaseBean;
import it.webred.ss.web.bean.lista.Scheda;
import it.webred.ss.web.bean.util.PuntoContatto;
import it.webred.ss.web.bean.util.Ufficio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


@ManagedBean
@ViewScoped
public class SchedeIncompleteBean extends SegretariatoSocSchedeTblBaseBean {
	
	private static final String NUOVA_SCHEDA = "editScheda.faces";
	private static final String PAGINA_CORRENTE = "home.faces";
	
	private long ente;
	
	private Scheda selectedScheda;
	private LazySchedeIncompleteDataModel dataModel;
	
	

	public SchedeIncompleteBean() {  
		dataModel = new LazySchedeIncompleteDataModel();
		List<Object> ufficiAbilitati = new ArrayList<Object>();
    	ufficiAbilitati.add("*");
    	dataModel.setUfficiAbilitati(ufficiAbilitati);
    } 
   
   
/*	private void populateFromDB() {  
		if(schede==null || schede.isEmpty()){
			try {
	    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
	    			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
				
				SsSearchCriteria criteria = new SsSearchCriteria();
				fillUserData(criteria);
				ente = this.getPreselectedPContatto().getOrganizzazione().getId();
				criteria.setOrganizzazioneId(this.getPreselectedPContatto().getOrganizzazione().getId());
				Ufficio ufficio = this.getPreselectedPContatto().getUfficio();
				ptoContt = loadPContatto(ufficio.getListaPContatto());
				//ufficio = u.getId();
		    	if(ufficio!=null && ufficio.getId()!=null)
		    		criteria.setUfficioId(ufficio.getId());
		    
	        	List<SsScheda> results = schedaService.readSchedeIncomplete(criteria);
	        	
	        	BaseDTO dto = new BaseDTO();
	        	fillUserData(dto);
	        	for(SsScheda row: results){
	        		Long ufficioId = row.getAccesso().getSsRelUffPcontOrg().getSsUfficio().getId();
	        		if(canAccessUfficio(ufficioId)){
		        		dto.setObj(row.getSegnalato());
		        		SsSchedaSegnalato segnalato = schedaService.readSegnalatoById(dto);
		        		schede.add(new Scheda(row, segnalato));
	        		}
	        	}
	        	
		        
	    	} catch(Exception e) {
	    		logger.error(e.getMessage(), e);
	    		addError("lettura.error");
	    		
			}
		}
    }  */
	
	public LazySchedeIncompleteDataModel getDataModel() {
		return dataModel;
	}


	public void setDataModel(LazySchedeIncompleteDataModel dataModel) {
		this.dataModel = dataModel;
	}
	
    public Scheda getSelectedScheda() {  
        return selectedScheda;  
    }  
    public void setSelectedScheda(Scheda selectedScheda) {  
        this.selectedScheda = selectedScheda;  
    }  
  
 
    public void onViewClick(){
    	if(selectedScheda == null){
    		printSelectError();
    	} else {
    		//TODO: apri visualizzazione scheda
    	}
    }
    
    public void onEditClick(){
    	if(selectedScheda == null){
    		printSelectError();
    	} else {
    		//Matteo Leandri 30/06/2016
    		//recupero il parametro currentLocation. La pagina sulla quale viene fatto il redirect lo utilizza per creare il nome del pulsante "indietro"
    		String clParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentLocation");    		
    		String url = addParameter(NUOVA_SCHEDA, SCHEDA_KEY, selectedScheda.getId()+"");
    		url = addParameter(url, "currentLocation", clParam  != null ? clParam : "");
    		url = addParameter(url, "previousPage", PAGINA_CORRENTE);
    		try {
    			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    		} catch (IOException e) {
    			logger.error(e);
    		}
    	}
    }
    
    public void onNewClick(){
    	PuntoContatto pCont = this.getPreselectedPContatto();
		if (pCont == null || pCont.getIdPContatto()==null) {
			addError("no.puntoContatto.error");
		} else if(selectedScheda == null){
    		printSelectError();
    	} else if (selectedScheda.getCognome() == null || selectedScheda.getCognome().isEmpty() || selectedScheda.getNome() == null || selectedScheda.getNome().isEmpty()){
    		addError("seleziona.altro.soggetto.error");
    	} else {
    		String url="";    		
    		try {
    			String clParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentLocation");
    			url = addParameter(NUOVA_SCHEDA, SCHEDA_KEY, selectedScheda.getId()+"");
        		url = addParameter(url, "currentLocation", clParam  != null ? clParam : "");
        		url = addParameter(url, "creaNuovaScheda", "true");
        		url = addParameter(url, "previousPage", PAGINA_CORRENTE);
        		
    		} catch (Exception e) {
    			addError("lettura.error");
    			logger.error(e.getMessage(),e);
    		}
    		
    		try {
    			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    		} catch (IOException e) {
    			logger.error(e);
    		}
    	}
    }
    
    public void onPrintClick(){
    	if(selectedScheda == null){
    		printSelectError();
    	} else {
    		// TODO: crea pdf e apri dialog per salvare
    	}
    }
    
    public void onPrivacyClick(){
    	if(selectedScheda == null){
    		printSelectError();
    	} else {
    		// TODO: apri dialog per salvare foglio privacy
    	}
    }
    
    public void onDeleteClick(){
    	if(selectedScheda == null){
    		printSelectError();
    	} else {
    		try {
    			AccessTableSchedaSegrSessionBeanRemote schedaSegrService =
						(AccessTableSchedaSegrSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
				
    			String statoScheda = null;
    			if (selectedScheda.getId()!=null) {
					// http://progetti.asc.webred.it/browse/SISO-419
					// se CS_SS_SCHEDA_SEGR.STATO!=NULL non posso fare modifiche
					it.webred.cs.csa.ejb.dto.BaseDTO baseDto = new it.webred.cs.csa.ejb.dto.BaseDTO();
					fillUserData(baseDto);
					baseDto.setObj(selectedScheda.getId());
					statoScheda = schedaSegrService.findStatoSchedaSegrById(baseDto);
				}
    			
    			if (statoScheda==null || statoScheda.isEmpty()) {
	        		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
	        			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
	    			
	    			BaseDTO dto = new BaseDTO();
	    			fillUserData(dto);
	    			dto.setObj(selectedScheda.getId());
	            	schedaService.eliminaScheda(dto);
    			} else {
					addError("policy.error.cartella.state");
				}
        	} catch(Exception e) {
        		logger.error(e.getMessage(), e);
        		addError("scrittura.error");
        		
    		}
    	}
    }
    

}
