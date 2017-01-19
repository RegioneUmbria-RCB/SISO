package it.webred.cs.jsf.manbean.superc;

import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.interfaces.INazione;
import it.webred.jsf.interfaces.gen.BasicManBean;
import it.webred.jsf.utils.NazioneConverter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.convert.Converter;
import javax.naming.NamingException;


public abstract class  NazioneMan extends BasicManBean implements INazione  {

	public AmTabNazioni nazione;
	protected static String currNazioneDenominazione = "ITALIA";

	
	public List<AmTabNazioni> getLstNazioni(String query) {
		List<AmTabNazioni> lstNazioni = new ArrayList<AmTabNazioni>();		
		try {
			AccessTableNazioniSessionBeanRemote bean = (AccessTableNazioniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableNazioniSessionBean");
			List<AmTabNazioni> beanLstNazioni = bean.getNazioniByDenomContains(query);
			if (beanLstNazioni != null) {
				return beanLstNazioni;
			}			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return lstNazioni;
	}
	
	public static AmTabNazioni getCurrNazione() {
		AmTabNazioni currNazione = null;		
		try {
			AccessTableNazioniSessionBeanRemote bean = (AccessTableNazioniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableNazioniSessionBean");
			currNazione = bean.getNazioneByDenominazione(currNazioneDenominazione);		
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return currNazione;
	}

	public void handleChangeNazione(javax.faces.event.AjaxBehaviorEvent event){  
		try {
			String a = ((it.webred.ct.config.model.AmTabNazioni)((javax.faces.component.UIInput)event.getComponent()).getValue()).getNazione();
			logger.debug("Valore nazione = "+a);
		} catch (NullPointerException e) {
			// il valore e' stato svuotato
			 logger.error("Nazione : il valore  svuotato");	
			 //FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Nazione " + getTipoNazione() + " Obbligatoria","");  
			 //FacesContext.getCurrentInstance().addMessage(null,msg);	
		}
	}

	public AmTabNazioni getNazione() {
		return nazione;
	}

	public void setNazione(AmTabNazioni nazione) {
		this.nazione = nazione;
	}
	
	public Converter getNazioneConverter() {
		return new NazioneConverter();
	}



	
	
	
}

