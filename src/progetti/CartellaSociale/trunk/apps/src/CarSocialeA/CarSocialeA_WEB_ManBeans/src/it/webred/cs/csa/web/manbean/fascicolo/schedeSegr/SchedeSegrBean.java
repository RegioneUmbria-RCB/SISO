package it.webred.cs.csa.web.manbean.fascicolo.schedeSegr;

import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.csa.web.manbean.schedaSegr.SchedaSegr;
import it.webred.cs.jsf.interfaces.IListaSchedeSegr;
import it.webred.cs.jsf.manbean.IterDialogMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.List;

import javax.faces.bean.ManagedProperty;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.SelectItem;

public class SchedeSegrBean extends FascicoloCompBaseBean implements IListaSchedeSegr {

	private LazyListaSchedeSegrFascicoloModel lazyListaSchedeSegrModel;
	private SchedaSegr schedaSelected;
	private List<SelectItem> lstProvenienza;
	
	@ManagedProperty( value="#{iterDialogMan}")
	private IterDialogMan iterDialogMan;
	
	
	@Override
	public void initializeData(Object data) {
		
		try{
			//Schede Segretariato nel fascicolo
			lazyListaSchedeSegrModel = new LazyListaSchedeSegrFascicoloModel();
			
			CeTBaseObject cet = new CeTBaseObject();
			CsUiCompBaseBean.fillEnte(cet);
			this.lstProvenienza = confService.getSsProvenienza(cet);	
			
			lazyListaSchedeSegrModel.setIdSoggetto(idSoggetto);
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
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

	@Override
	public boolean isRenderNuovaCartella() {
		return false;
	}
	
	@Override
	public boolean isRenderCaricaCartella() {
		return false;
	}
	
	@Override
	public boolean isRenderVista(){
		return false;
	}
	
	@Override
	public boolean isRenderRespinta(){
		return false;
	}

	public SchedaSegr getSchedaSelected() {
		return schedaSelected;
	}

	public void setSchedaSelected(SchedaSegr schedaSelected) {
		this.schedaSelected = schedaSelected;
	}
	
	public List<SelectItem> getLstProvenienza() {
		return lstProvenienza;
	}

	public void setLstProvenienza(List<SelectItem> lstProvenienza) {
		this.lstProvenienza = lstProvenienza;
	}

	public LazyListaSchedeSegrFascicoloModel getLazyListaSchedeSegrModel() {
		return lazyListaSchedeSegrModel;
	}

	public void setLazyListaSchedeSegrModel(
			LazyListaSchedeSegrFascicoloModel lazyListaSchedeSegrModel) {
		this.lazyListaSchedeSegrModel = lazyListaSchedeSegrModel;
	}



	@Override
	public void respingiScheda() {
		// TODO Auto-generated method stub
	}

	@Override
	public void vediScheda() {
		// TODO Auto-generated method stub		
	}
	    
}
