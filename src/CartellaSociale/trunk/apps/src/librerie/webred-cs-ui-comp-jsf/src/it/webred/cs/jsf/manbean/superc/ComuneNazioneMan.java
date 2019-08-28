package it.webred.cs.jsf.manbean.superc;

import it.webred.jsf.interfaces.IComuneNazione;
import it.webred.jsf.interfaces.gen.BasicManBean;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;

public abstract class ComuneNazioneMan extends BasicManBean implements IComuneNazione {
	
	private String value;
	
	public abstract String getExtraLabel();
	
	/**
	 * Questo metodo è stato creato per http://progetti.asc.webred.it/browse/SISO-396
	 * le classi per le quali sia necessario un comportamento particolare sovrascivono questo metodo
	 * @param required
	 * @return
	 */
	public String getExtraLabel(boolean required) {
		return getExtraLabel();
	}
	
	private String comuneValue = "C";
	private String nazioneValue = "N";
	
	protected String clientIdToUpdate;
	
	public abstract ComuneMan getComuneMan();



	
	public abstract void setComuneMan(ComuneMan comuneMan);
	
	public abstract NazioneMan getNazioneMan();

	public abstract void setNazioneMan(NazioneMan nazioneMan);
	
	public String getValue() {
		if (value == null) {
			value = comuneValue; //default
		}
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isComuneRendered() {
		boolean rendered = getValue().equalsIgnoreCase(comuneValue); 
		return rendered;
	}

	public void comuneNazioneValueChangeListener(AjaxBehaviorEvent event) {
		getNazioneMan().setNazione(null);
		getComuneMan().setComune(null);
		UIOutput comp = (UIOutput)event.getSource();
		value = (String)comp.getValue();
		
		// update del pannello
		String lblClientId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pnlToUpdateId");
		if(lblClientId != null)
			RequestContext.getCurrentInstance().update(lblClientId);
	}
	
	public String getComuneValue() {
		return comuneValue;
	}
	
	public void setComuneValue(String comuneValue) {
		this.comuneValue = comuneValue;
	}
	
	public String getNazioneValue() {
		return nazioneValue;
	}
	
	public void setNazioneValue(String nazioneValue) {
		this.nazioneValue = nazioneValue;
	}
	
	public String getClientIdToUpdate() {
		return clientIdToUpdate;
	}

	public void setClientIdToUpdate(String clientIdToUpdate) {
		this.clientIdToUpdate = clientIdToUpdate;
	}
	
}
