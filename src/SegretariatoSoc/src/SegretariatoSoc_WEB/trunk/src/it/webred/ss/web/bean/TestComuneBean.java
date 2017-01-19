package it.webred.ss.web.bean;

import java.util.ArrayList;
import java.util.List;

import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.interfaces.IComune;
import it.webred.jsf.utils.ComuneConverter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.convert.Converter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.naming.NamingException;

@ManagedBean
@ViewScoped
public class TestComuneBean extends SegretariatoSocBaseBean implements IComune{

	public ComuneBean comune;
	
	@Override
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
			logger.error(e);
		}
		return lstComuni;
	}
	
	public ComuneBean getComune(String id){
		try {
			AccessTableComuniSessionBeanRemote bean = (AccessTableComuniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableComuniSessionBean");
			AmTabComuni comune = bean.getComuneByIstat(id);
			if(comune!=null)
				return new ComuneBean(comune);
		} catch (NamingException e) {
			logger.error(e);
		}
		return null;
	}

	@Override
	public void handleChangeComune(AjaxBehaviorEvent event) {
	}

	@Override
	public Converter getComuneConverter() {
		return new ComuneConverter();
	}

	@Override
	public String getTipoComune() {
		return "Residenza";
	}

	@Override
	public ComuneBean getComune() {
		return comune;
	}

	@Override
	public void setComune(ComuneBean comune) {
		this.comune = comune;
	}

	@Override
	public String getWidgetVar() {
		return "wdgTestComune";
	}
	
}
