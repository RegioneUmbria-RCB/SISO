package it.webred.cs.csa.web.bean;

import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.data.access.aggregator.isee.IseeService;
import it.webred.ct.data.access.aggregator.isee.dto.InfoIseeDTO;
import it.webred.ct.data.access.aggregator.isee.dto.IseeDataIn;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.interfaces.IComune;
import it.webred.jsf.utils.ComuneConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.convert.Converter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.naming.NamingException;

public class ComuneTestMan extends CsUiCompBaseBean implements IComune {

	public ComuneBean comune;
	private String widgetVar;
	private String tipoComune;
	
	public String getWidgetVar() {
		return widgetVar;
	}
	
	public ArrayList<ComuneBean> getLstComuni(String query) {
		ArrayList<ComuneBean> lstComuni = new ArrayList<ComuneBean>();		
		try {
			AccessTableComuniSessionBeanRemote bean = (AccessTableComuniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableComuniSessionBean");
			List<AmTabComuni> beanLstComuni = bean.getComuniByDenomContains(query,false);
			if (beanLstComuni != null) {
				for(AmTabComuni comune : beanLstComuni) {  
					ComuneBean cb = new ComuneBean(comune);
					lstComuni.add(cb);
				}
			}			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return lstComuni;
	}
	
	

	
	public void handleChangeComune(javax.faces.event.AjaxBehaviorEvent event){  
		try {
			String a = ((ComuneBean)((javax.faces.component.UIInput)event.getComponent()).getValue()).getDenominazione();
			logger.debug("Valore comune = "+a);
		} catch (NullPointerException e) {
			// il valore e' stato svuotato
			 logger.error("Comune " + getTipoComune() + ": il valore è stato svuotato");	
			 //FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Comune " + getTipoComune() + " Obbligatorio","");  
			 //FacesContext.getCurrentInstance().addMessage(null,msg); 		
		}
	}

	public ComuneBean getComune() {
		return comune;
	}

	public void setComune(ComuneBean comune) {
		this.comune = comune;
		
	}
	
	public Converter getComuneConverter() {
		return new ComuneConverter();
	}

	@Override
	public String getTipoComune() {
		return tipoComune;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}

	public void setTipoComune(String tipoComune) {
		this.tipoComune = tipoComune;
	}
	
}
