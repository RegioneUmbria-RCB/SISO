package it.webred.cs.csa.web.manbean.scheda.parenti;

import it.webred.cs.jsf.manbean.superc.ComuneMan;
import it.webred.cs.jsf.manbean.superc.ComuneNazioneMan;
import it.webred.cs.jsf.manbean.superc.NazioneMan;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;

public class ComuneNazioneNascitaBean extends ComuneNazioneMan implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private ComuneNascitaBean comuneNascitaBean = new ComuneNascitaBean();

	private NazioneNascitaBean nazioneNascitaBean = new NazioneNascitaBean();

	@Override
	public ComuneMan getComuneMan() {
		return getComuneNascitaBean();
	}

	@Override
	public void setComuneMan(ComuneMan comuneMan) {
	}

	@Override
	public NazioneMan getNazioneMan() {
		return getNazioneNascitaBean();
	}

	@Override
	public void setNazioneMan(NazioneMan nazioneMan) {
	}

	@Override
	public String getExtraLabel() {
		return "di nascita";
	}

	public ComuneNascitaBean getComuneNascitaBean() {
		return comuneNascitaBean;
	}

	public NazioneNascitaBean getNazioneNascitaBean() {
		return nazioneNascitaBean;
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
