package it.webred.cs.csa.web.manbean.scheda.parenti;

import it.webred.cs.jsf.manbean.superc.ComuneMan;
import it.webred.cs.jsf.manbean.superc.ComuneNazioneMan;
import it.webred.cs.jsf.manbean.superc.NazioneMan;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;

public class ComuneNazioneResidenzaBean extends ComuneNazioneMan implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private ComuneResidenzaBean comuneResidenzaBean = new ComuneResidenzaBean();

	private NazioneResidenzaBean nazioneResidenzaBean = new NazioneResidenzaBean();

	@Override
	public ComuneMan getComuneMan() {
		return getComuneResidenzaBean();
	}

	@Override
	public void setComuneMan(ComuneMan comuneMan) {
	}

	@Override
	public NazioneMan getNazioneMan() {
		return getNazioneResidenzaBean();
	}

	@Override
	public void setNazioneMan(NazioneMan nazioneMan) {
	}

	@Override
	public String getExtraLabel() {
		return "di residenza";
	}

	public ComuneResidenzaBean getComuneResidenzaBean() {
		return comuneResidenzaBean;
	}

	public NazioneResidenzaBean getNazioneResidenzaBean() {
		return nazioneResidenzaBean;
	}

	@Override
	public void comuneNazioneValueChangeListener(AjaxBehaviorEvent event) {
		super.comuneNazioneValueChangeListener(event);
		// update del pannello
		String lblClientId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pnlToUpdateId");
		if(lblClientId != null)
			RequestContext.getCurrentInstance().update(lblClientId);
	}

	@Override
	public String getValidatorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMemoWidgetName() {
		// TODO Auto-generated method stub
		return null;
	}

}
