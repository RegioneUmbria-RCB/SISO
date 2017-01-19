package it.webred.ss.web.bean.util;

import it.webred.ss.data.model.SsPuntoContatto;
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsUfficio;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.web.bean.NavigationBean;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
  
@ManagedBean
@ViewScoped
public class UfficiTableBean extends SegretariatoSocBaseBean {
	
	private static final String SCHEDE_URL = "schede.faces";
	private static final String NUOVA_SCHEDA_URL = "editScheda.faces";
  
    private List<Ufficio> uffici; 
    private List<Ufficio> filtroUffici;
	private List<SelectItem> listaUffici;
   
    private Ufficio selectedUfficio;
    
    public UfficiTableBean(){
    	if(uffici==null || listaUffici==null) 
    		this.populateUfficiOrganizzazione();
    }
	

	public void resetPreselectedUfficio(){
/*		PuntoContatto pcontatto  = getPreselectedPContatto();
		pcontatto.resetUfficio();
		this.getSession().setAttribute("preselectedPContatto", pcontatto);*/
		this.getPreselectedPContatto().resetUfficio();
		this.populateFiltroUffici(null);
	}
	
	public void populateUfficiOrganizzazione(){
		Long id = getPreselectedPContatto().getOrganizzazione().getId();
		if(id!=null)
			populateUffici(id);
		else
			logger.warn("ID Organizzazione NULL");
	    populateListaUffici();
	}
	
	public void onChangeOrganizzazione(){
	    this.populateUfficiOrganizzazione();
	    //this.getSession().setAttribute("preselectedPContatto", getPreselectedPContatto());
        populateFiltroUffici(null);
    }
	
	private void populateListaUffici(){
		listaUffici = new ArrayList<SelectItem>();
		if(uffici!=null){
	  		for(Ufficio ufficio : uffici){
			Long idUfficio = ufficio.getId();
			if(canAccessUfficio(idUfficio))
				listaUffici.add(new SelectItem(idUfficio, ufficio.getNome()));
	  		}
  		}
  		
 		if(listaUffici.size()==1)
  			this.getPreselectedPContatto().getUfficio().setId((Long)listaUffici.get(0).getValue());
 		
 		if(this.getPreselectedPContatto().getUfficio()!=null)
 			this.populateFiltroUffici(null);
	}
	
	public void populateFiltroUffici(AjaxBehaviorEvent event){
		filtroUffici = new ArrayList<Ufficio>();
		Long preselUfficioId = this.getPreselectedPContatto().getUfficio().getId();
	
		if(uffici!=null){
			if(preselUfficioId!=null){
				//Resitituisco solo l'ufficio selezionato
				for(Ufficio u : uffici){
					if(u.getId().doubleValue()==preselUfficioId.doubleValue()){
						filtroUffici.add(u);
						this.getPreselectedPContatto().setUfficio(u);
						if(u.getListaPContatto().size()==1){
							SelectItem si = u.getListaPContatto().get(0);
							this.getPreselectedPContatto().setIdPContatto((Long)si.getValue());
							this.getPreselectedPContatto().setNomePContatto(si.getLabel());
						}
						//this.getSession().setAttribute("preselectedPContatto", pcont);
					}
				}
			}else
				filtroUffici.addAll(uffici);
		}
	}
    	
    private boolean populateUffici(Long id) {  
    	this.uffici = new ArrayList<Ufficio>();
    	try {
    		SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
    		
    		BaseDTO dto = new BaseDTO();
        	fillUserData(dto);
        	dto.setOrganizzazione(id);
        	
        	List<SsRelUffPcontOrg> results = schedaService.readUffPcontByOrganizzazione(dto);
        	LinkedHashMap<String, Ufficio> mappaUffici = new LinkedHashMap<String, Ufficio>(); 
        	for(SsRelUffPcontOrg rel: results){
        		
        		SsUfficio ufficio = rel.getSsUfficio();
        		SsPuntoContatto pcont = rel.getSsPuntoContatto();
        		
        		if(!mappaUffici.containsKey(ufficio.getId().toString())){
        		
	        		dto.setObj(ufficio.getId());
	        		Long totale = schedaService.countTotaleSchedeInUfficio(dto);
	        		Long sospese = schedaService.countSchedeSospeseInUfficio(dto);
	        		Long inCarico = schedaService.countSchedeInCaricoInUfficio(dto);
	        		Long eliminate = schedaService.countSchedeEliminateInUfficio(dto);
	        		
	        		Ufficio uffDto = new Ufficio(ufficio, totale, sospese, inCarico, eliminate);
	        		if(pcont!=null && pcont.getAbilitato())
	        			uffDto.addPuntoContatto(new SelectItem(pcont.getId(),pcont.getNome()));
	        		mappaUffici.put(ufficio.getId().toString(),uffDto);
        		}else{
        			if(pcont.getAbilitato()){
        				Ufficio uffDto = mappaUffici.get(ufficio.getId().toString());
	        			uffDto.addPuntoContatto(new SelectItem(pcont.getId(),pcont.getNome()));
	        			mappaUffici.put(ufficio.getId().toString(), uffDto);
        			}
        		}
        	}
        	
        	//Trasferisco gli uffici dalla mappa alla lista
        	this.uffici.addAll(mappaUffici.values());
        	
        	return true;
    		
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		addError("lettura.error");
    		return false;
		}
    	
    }  
  
    public Ufficio getSelectedUfficio() {  
        return selectedUfficio;  
    }  
    public void setSelectedUfficio(Ufficio selectedUfficio) {  
        this.selectedUfficio = selectedUfficio;  
    }  
  
    public List<Ufficio> getUffici() { 
    	this.getSession().removeAttribute("preselectedUfficio");
    	return uffici;
    } 
    
    public void vaiAUfficio(Ufficio ufficio){
        this.selectedUfficio=ufficio;
		String url = addParameter(SCHEDE_URL, "ufficio", selectedUfficio.getId()+"");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			logger.error(e);
		}
    	
    }
    
   
	public void addInUfficio(){
    	try {
    		PuntoContatto pCont = this.getPreselectedPContatto();
    		if (pCont != null && pCont.getIdPContatto()!=null) {
    			//Matteo Leandri 30/06/2016
        		//recupero il parametro currentLocation. La pagina sulla quale viene fatto il redirect lo utilizza per creare il nome del pulsante "indietro"
        		String clParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentLocation");
        		String url = addParameter(NUOVA_SCHEDA_URL, "currentLocation", clParam != null ? clParam : "");
        		FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    		}
    		else
    			addError("no.puntoContatto.error");
		} catch (IOException e) {
			logger.error(e);
		}
    	                    
    }
	
	public List<SelectItem> getListaUffici() {
		return listaUffici;
	}


	public void setListaUffici(List<SelectItem> listaUffici) {
		this.listaUffici = listaUffici;
	}
 
   public List<Ufficio> getFiltroUffici() {
	   return filtroUffici;
	}

	public void setFiltroUffici(List<Ufficio> filtroUffici) {
		this.filtroUffici = filtroUffici;
	}

	public void setUffici(List<Ufficio> uffici) {
		this.uffici = uffici;
	}

}
