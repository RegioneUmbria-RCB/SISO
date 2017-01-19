package it.webred.cs.csa.web.manbean.schedaSegr;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsSsSchedaSegr;
import it.webred.cs.jsf.interfaces.IListaSchedeSegr;
import it.webred.cs.jsf.manbean.IterDialogMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.primefaces.model.LazyDataModel;

@ManagedBean
@ViewScoped
public class ListaSchedeSegrBean extends CsUiCompBaseBean implements IListaSchedeSegr {

	private LazyDataModel<SchedaSegr> lazyListaSchedeSegrModel;
	private SchedaSegr schedaSelected;
	private String note;
	
	@ManagedProperty( value="#{iterDialogMan}")
	private IterDialogMan iterDialogMan;
	
	private AccessTableSchedaSegrSessionBeanRemote schedaSegrService = (AccessTableSchedaSegrSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
	
	public void respingiScheda() {
		
		try {
			
			CsSsSchedaSegr scheda = schedaSelected.getCsSsSchedaSegr();
			if(scheda!=null){
				scheda.setNotaStato(note);
				scheda.setStato(DataModelCostanti.SchedaSegr.STATO_RESPINTA);
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(scheda);
				schedaSegrService.updateSchedaSegr(dto);
			}
		
		} catch(Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public void vediScheda() {
		
		try {
			
			CsSsSchedaSegr scheda = schedaSelected.getCsSsSchedaSegr();
			if(scheda!=null){
				scheda.setStato(DataModelCostanti.SchedaSegr.STATO_VISTA);
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(scheda);
				schedaSegrService.updateSchedaSegr(dto);
			}
		
		} catch(Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public ActionListener getCloseDialog() {
	    return new ActionListener() {
	        @Override
	        public void processAction(ActionEvent event) throws AbortProcessingException {
	        	//loadListaCasi();
	        }
	    };
	}
	
	public IterDialogMan getIterDialogMan() {
		return iterDialogMan;
	}

	public void setIterDialogMan(IterDialogMan iterDialogMan) {
		this.iterDialogMan = iterDialogMan;
	}
	
	public ListaSchedeSegrBean() {
		lazyListaSchedeSegrModel = new LazyListaSchedeSegrModel();
	}

	public LazyDataModel<SchedaSegr> getLazyListaSchedeSegrModel() {
		return lazyListaSchedeSegrModel;
	}

	public void setLazyListaSchedeSegrModel(
			LazyDataModel<SchedaSegr> lazyListaSchedeSegrModel) {
		this.lazyListaSchedeSegrModel = lazyListaSchedeSegrModel;
	}

	public boolean isRenderNuovaCartella() {
		return checkPermesso(DataModelCostanti.PermessiCaso.CREAZIONE_CASO);
	}
	
	public boolean isRenderCaricaCartella() {
		return checkPermesso(DataModelCostanti.PermessiCaso.CREAZIONE_CASO);
	}

	public AccessTableSchedaSegrSessionBeanRemote getSchedaSegrService() {
		return schedaSegrService;
	}

	public void setSchedaSegrService(
			AccessTableSchedaSegrSessionBeanRemote schedaSegrService) {
		this.schedaSegrService = schedaSegrService;
	}

	public SchedaSegr getSchedaSelected() {
		return schedaSelected;
	}

	public void setSchedaSelected(SchedaSegr schedaSelected) {
		this.schedaSelected = schedaSelected;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
