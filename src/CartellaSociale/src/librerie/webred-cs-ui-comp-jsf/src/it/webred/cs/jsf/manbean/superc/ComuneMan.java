package it.webred.cs.jsf.manbean.superc;

import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.interfaces.IComune;
import it.webred.jsf.interfaces.gen.BasicManBean;
import it.webred.jsf.utils.ComuneConverter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.convert.Converter;
import javax.naming.NamingException;


public abstract class ComuneMan extends BasicManBean implements IComune  {

	/**
	 * 
	 */
	public ComuneBean comune;

	public ComuneMan() {
		
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
	

	
}
