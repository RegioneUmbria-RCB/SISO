package eu.smartpeg.rilevazionepresenze.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import eu.smartpeg.rilevazionepresenze.StruttureSessionBeanRemote;
import eu.smartpeg.rilevazionepresenze.data.model.Area;
import eu.smartpeg.rilevazionepresenze.data.model.Struttura;
import eu.smartpeg.rilevazionepresenze.datautil.DataModelCostanti.Villaggi.TIPO_VILLAGGIO;


@ManagedBean
@ViewScoped
public class StruttureController extends RilevazionePresenzeBaseController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Struttura> strutture;
	private List<Struttura> struttureFiltered;
	private Struttura selectedStruttura;
    private Area selectedArea;
    private String selectedAreaId;
    private String modalHeader;
    private String areaSelected;
    private Map<String,Area> aree;
    
	@EJB private StruttureSessionBeanRemote struttureEjb;
	
	
	public StruttureController() {
		this.strutture = new ArrayList<>();
		this.aree = new HashMap<>();
	}
	
	@PostConstruct
	public void init() {
		this.selectedStruttura = null;
		this.selectedArea = null;
		readStrutture();
		readAree();
	}

	public List<Struttura> getStrutture() {		
		return strutture;
	}

	public void setStrutture(List<Struttura> strutture) {
		this.strutture = strutture;
	}
	
	public String readStrutture() {		
		setStrutture(struttureEjb.findAll());	
		return null;
	}
	
	

	public String getSelectedAreaId() {
		return selectedAreaId;
	}

	public void setSelectedAreaId(String selectedAreaId) {
		this.selectedAreaId = selectedAreaId;
	}

	public void setSelectedStruttura(Struttura selectedStruttura) {
		setModalHeader("Modifica Villaggio");		
		this.selectedStruttura = selectedStruttura;
		readAree();
    }

	public String getAreaSelected() {
		return areaSelected;
	}

	public void setAreaSelected(String areaSelected) {
		this.areaSelected = areaSelected;
	}

	public Struttura getSelectedStruttura() {
		return selectedStruttura;
	}

	public String getModalHeader() {
		return modalHeader;
	}

	public void setModalHeader(String modalHeader) {
		this.modalHeader = modalHeader;
	}
 
	
   //Queste sono tutte le aree
   public Map<String,Area> getAree() {
		return aree;
	}

	public void setAree(Map<String,Area> aree) {
		this.aree = aree;
	}

	/**
	 * Legge aree da DB
	 * @return
	 */
	public String readAree() {		
		List<Area> listaAree = struttureEjb.findAllAreas();
		
		if (getSelectedStruttura() != null && getSelectedStruttura().getAreas() != null) {
	    	listaAree.removeAll(getSelectedStruttura().getAreas());
		}		
		
		if(aree != null) {
			aree.clear();
		}		
		for (Area area : listaAree) {
			aree.put(String.valueOf(area.getId()), area);
		}
	
//		ArrayList<Area> areePerDescrizione = new ArrayList<>(aree.values());
//		
//		Collections.sort(areePerDescrizione, new Comparator<Area>() {
//           @Override
//           public int compare(Area o1, Area o2) {
//                return o1.getDescrizione().compareTo(o2.getDescrizione());
//            }
//        });
		
		return null;
	}
	
	/**
	 * Area selezionata
	 * @return
	 */
	public Area getSelectedArea() {
		return selectedArea;
	}

	/**
	 * imposta area selezionata
	 * @param selectedArea
	 */
	public void setSelectedArea(Area selectedArea) {
		this.selectedArea = selectedArea;
	}


	public List<SelectItem> getListaTipologiaVillaggio() {
		List<SelectItem> result = new ArrayList<>();
		for(TIPO_VILLAGGIO p : TIPO_VILLAGGIO.values())
		{
			result.add(new SelectItem(p.ordinal(), p.getDescrizione()));
		}
		return result;
	}
	

	/**
	 *  Aggiungi area selezionata alla struttura
	 */
	 public void aggiungiAreaSelezionata() {
		 if(!this.selectedAreaId.isEmpty())
		 {
			 if (!getSelectedStruttura().getAreas().contains(selectedArea)) {
				 getSelectedStruttura().getAreas().add(selectedArea);
			 }
		 }
		 
		 this.selectedArea = null;		 
		 this.selectedAreaId = null;
	 }
	 
	public void nuova() {
		
		this.selectedArea = null;
		this.selectedAreaId = null;
		this.selectedStruttura = new Struttura();
		this.setSelectedStruttura(selectedStruttura);
		readAree();
		setModalHeader("Nuovo Villaggio");
	}
	
	
	
	public void initDialog() {
		this.selectedArea = null;		 
		this.selectedAreaId = null;	
		readAree();
	}
	
	
	public void salva() throws Exception {
		try {
			if (struttureEjb.validaStruttura(this.getSelectedStruttura()).isEmpty()) {

				struttureEjb.salva(this.getSelectedStruttura());

				addMessage(FacesMessage.SEVERITY_INFO, "INFO ", "Salvataggio avvenuto con successo");
			} 
			else {
				addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio", struttureEjb.validaStruttura(this.getSelectedStruttura()));
			}
			init();
		} catch (Exception e) {
			// TODO: log
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio", e.getMessage());
		}
	}
	
	public void eliminaStruttura() {
		try {
			struttureEjb.eliminaStruttura(this.getSelectedStruttura());
			
			this.init();
			addMessage(FacesMessage.SEVERITY_INFO, "INFO ", "Eliminazione avvenuta con successo");
		}
		catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore nell'eliminazione del villaggio", "Non è possibile eliminare il villaggio perchè associato in anagrafica"); //e.getMessage());
		}
	}
	
	public void eliminaArea() {
		try {			
			struttureEjb.eliminaArea(this.getSelectedStruttura(), this.getSelectedArea());
			selectedStruttura = struttureEjb.findStrutturaById(selectedStruttura.getId());
		}
		catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore nell'eliminazione dell'area", "Non è possibile eliminare l'area perchè associata in anagrafica"); //e.getMessage());
		}		
	}
	
	/**
	 * Handler evento selezione area in dropdown list
	 */
	public void onAreaChange() {
		if(selectedAreaId!=null && !selectedAreaId.isEmpty()) {
			selectedArea = aree.get(selectedAreaId);
		}else {
			selectedArea = null;
			this.selectedAreaId = null;
		}
	}
	
	
	
	public List<Struttura> getStruttureFiltered() {
		return struttureFiltered;
	}

	public void setStruttureFiltered(List<Struttura> struttureFiltered) {
		this.struttureFiltered = struttureFiltered;
	}
	

	public void addMessage(FacesMessage.Severity tipoMessaggio, String summary, String messaggio) {
		FacesMessage message = new FacesMessage(tipoMessaggio, summary, messaggio);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}




