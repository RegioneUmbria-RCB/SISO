package eu.smartpeg.rilevazionepresenze.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import eu.smartpeg.rilevazionepresenze.AreeSessionBeanRemote;
import eu.smartpeg.rilevazionepresenze.data.model.Area;

@ManagedBean
@ViewScoped
public class AreeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Area> aree;
	private List<Area> areeFiltered;
	private Area selectedArea;
    private String modalHeader;
    
	@EJB private AreeSessionBeanRemote areeEjb;
	
	
	public AreeController() {
		this.aree = new ArrayList<>();
	}
	
	@PostConstruct
	public void init() {
		this.selectedArea = null;
		readAree();
	}

	public List<Area> getAree() {		
		return aree;
	}

	public void setAree(List<Area> aree) {
		this.aree = aree;
	}
	
	public String readAree() {		
		setAree(areeEjb.findAll());	
		return null;
	}

	public void setSelectedArea(Area selectedArea) {
		setModalHeader("Modifica Area");		
		this.selectedArea = selectedArea;
    }

	public Area getSelectedArea() {
		return selectedArea;
	}
	
	public List<Area> getAreeFiltered() {
		return areeFiltered;
	}

	public void setAreeFiltered(List<Area> areeFiltered) {
		this.areeFiltered = areeFiltered;
	}

	public String getModalHeader() {
		return modalHeader;
	}

	public void setModalHeader(String modalHeader) {
		this.modalHeader = modalHeader;
	}
	 
	public void nuovo() {		
		setModalHeader("Nuova Area");
		this.selectedArea = new Area();
	}
	
	public void salva() throws Exception {
		try {	
			if(areeEjb.validaArea(this.getSelectedArea()).isEmpty()) {
				
			areeEjb.salva(this.getSelectedArea());
			
			addMessage(FacesMessage.SEVERITY_INFO, "INFO ", "Salvataggio avvenuto con successo");
			}
			else {
				addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio", areeEjb.validaArea(this.getSelectedArea()));
				
			}
			readAree();	
		}
		catch (Exception e) {
			//TODO: log
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio", e.getMessage());
		}		
	}
	
	public void eliminaArea() {
		try {
			areeEjb.elimina(this.getSelectedArea());
			
			this.init();
			addMessage(FacesMessage.SEVERITY_INFO, "INFO ", "Eliminazione avvenuta con successo");
		}
		catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore nell'eliminazione della area", "Non è possibile eliminare l'area perchè associata in anagrafica"); //e.getMessage());
		}
		
	}	

	public void addMessage(FacesMessage.Severity tipoMessaggio, String summary, String messaggio) {
		FacesMessage message = new FacesMessage(tipoMessaggio, summary, messaggio);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}