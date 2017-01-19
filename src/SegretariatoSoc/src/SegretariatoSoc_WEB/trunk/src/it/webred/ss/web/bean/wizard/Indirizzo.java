package it.webred.ss.web.bean.wizard;

import java.util.ArrayList;
import java.util.List;

import javax.faces.convert.Converter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.interfaces.IComune;
import it.webred.jsf.utils.ComuneConverter;
import it.webred.ss.data.model.SsIndirizzo;

public class Indirizzo implements IComune {
	
	protected static Logger logger = Logger.getLogger("segretariatosoc.log");
	
	private Long id;
	private String via;
	//private String numero;
	private ComuneBean comune;
	private boolean comeResidenza = false;
	
	private String tipoComune;
	private String widgetVar;
	
	private String validatorMessage = "Il campo 'Comune' è obbligatorio.";
	
	public void initFromIndirizzo(Indirizzo indirizzo){
		via = indirizzo.getVia();
		//numero = indirizzo.getNumero();
		comune = indirizzo.getComune();
	}
	
	public Indirizzo(){
		
	}
	
	public Indirizzo(String tipoComune, String widgetVar){
		this.tipoComune = tipoComune;
		this.widgetVar = widgetVar;
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getValidatorMessage() {
		return validatorMessage;
	}

	public void setValidatorMessage(String validatorMessage) {
		this.validatorMessage = validatorMessage;
	}

	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}

	public boolean isComeResidenza() {
		return comeResidenza;
	}

	public void setComeResidenza(boolean comeResidenza) {
		this.comeResidenza = comeResidenza;
	}
	
	@Override
	public ArrayList<ComuneBean> getLstComuni(String query) {
		ArrayList<ComuneBean> lstComuni = new ArrayList<ComuneBean>();		
		try {
			AccessTableComuniSessionBeanRemote bean = (AccessTableComuniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableComuniSessionBean");
			List<AmTabComuni> beanLstComuni = bean.getComuniByDenomContains(query,true);
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
	
	public ComuneBean getComuneByBelfiore(String belfiore){
		try {
			LuoghiService bean = (LuoghiService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");
			AmTabComuni comune = bean.getComuneItaByBelfiore(belfiore);
			return new ComuneBean(comune);
						
		} catch (NamingException e) {
			logger.error(e);
		}
		return null;
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
	
	public void setTipoComune(String tipoComune){
		this.tipoComune = tipoComune;
	}

	@Override
	public String getTipoComune() {
		return tipoComune;
	}

	@Override
	public ComuneBean getComune() {
		return comune;
	}

	@Override
	public void setComune(ComuneBean comune) {
		this.comune = comune;
	}
	
	public void setWidgetVar(String widgetVar){
		this.widgetVar = widgetVar;
	}

	@Override
	public String getWidgetVar() {
		return widgetVar;
	}
	
	public void reset(){
		//numero=null;
		via = null;
		comune = null;
	}


	public void fillModel(SsIndirizzo model) {
		model.setId(id);
		model.setVia(via);
		//model.setNumero(numero);
		if(comune != null)
			model.setComune(comune.getCodIstatComune());
	}


	public void initFromModel(SsIndirizzo model) {
		id = model.getId();
		via = model.getVia();
		comune = getComune(model.getComune());
	}

/*	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}*/
	
}
