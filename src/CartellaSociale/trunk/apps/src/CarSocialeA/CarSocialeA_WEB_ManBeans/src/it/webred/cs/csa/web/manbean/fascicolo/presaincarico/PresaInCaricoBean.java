package it.webred.cs.csa.web.manbean.fascicolo.presaincarico;

import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.csa.web.manbean.scheda.SchedaBean;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.jsf.bean.DatiCasoBean;
import it.webred.cs.jsf.interfaces.IPresaInCarico;
import it.webred.cs.jsf.manbean.IterDialogMan;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import javax.faces.bean.ManagedProperty;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.naming.NamingException;

public class PresaInCaricoBean extends FascicoloCompBaseBean implements IPresaInCarico {

	private IterInfoStatoMan casoInfo;
	private boolean aggiornaCartella = false;
	
	@ManagedProperty( value="#{iterDialogMan}")
	private IterDialogMan iterDialogMan;	
	
	AccessTableIterStepSessionBeanRemote iterSessionBean = (AccessTableIterStepSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");
	
	@Override
	protected void initializeData(Object data) {
		
		try{
			casoInfo = new IterInfoStatoMan();
			CsASoggettoLAZY csSogg = (CsASoggettoLAZY) getSession().getAttribute("soggetto");
			csSogg = csSogg==null ? this.getCsASoggetto() : csSogg;
			IterDTO itDto = new IterDTO();
			CsUiCompBaseBean.fillEnte(itDto);
			itDto.setIdCaso(csSogg.getCsACaso().getId());
			CsIterStepByCasoDTO itStep = iterSessionBean.getLastIterStepByCasoDTO(itDto);
			
			if( itStep != null ) {
				casoInfo.initialize( itStep);
				iterDialogMan = new IterDialogMan();
			}
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	protected void initializeDataSel(DatiCasoBean selectedCaso) {
		
		try{
			
			casoInfo = selectedCaso.getLastIterStepInfo();
			iterDialogMan = new IterDialogMan();
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
/*	@Override
	public ActionListener getCloseDialog() {
	    return new ActionListener() {
	        @Override
	        public void processAction(ActionEvent event) throws AbortProcessingException {
	        	initializeData(null);
	        	
	        }
	    };
	}*/
	
	public void saveDialogAndClose(){
		try {
			this.iterDialogMan.saveDialogWithAttributes();
			initializeData(null);
			if(aggiornaCartella){
				SchedaBean sb = (SchedaBean)getReferencedBean("schedaBean");
				if(sb!=null) sb.refreshSchedaContent();
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public IterInfoStatoMan getCasoInfo() {
		return casoInfo;
	}

	public void setCasoInfo(IterInfoStatoMan casoInfo) {
		this.casoInfo = casoInfo;
	}

	@Override
	public IterDialogMan getIterDialogMan() {
		return iterDialogMan;
	}

	public void setIterDialogMan(IterDialogMan iterDialogMan) {
		this.iterDialogMan = iterDialogMan;
	}

	public boolean isAggiornaCartella() {
		return aggiornaCartella;
	}

	public void setAggiornaCartella(boolean aggiornaCartella) {
		this.aggiornaCartella = aggiornaCartella;
	}
	
}
