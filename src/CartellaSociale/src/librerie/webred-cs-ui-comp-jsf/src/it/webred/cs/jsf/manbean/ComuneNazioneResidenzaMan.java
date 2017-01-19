package it.webred.cs.jsf.manbean;

import it.webred.cs.jsf.manbean.superc.ComuneMan;
import it.webred.cs.jsf.manbean.superc.ComuneNazioneMan;
import it.webred.cs.jsf.manbean.superc.NazioneMan;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;

@ManagedBean
@NoneScoped
public class ComuneNazioneResidenzaMan extends ComuneNazioneMan implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private ComuneResidenzaMan comuneResidenzaMan;

	private NazioneResidenzaMan nazioneResidenzaMan;


	public ComuneNazioneResidenzaMan() {
		setComuneResidenzaMan(new ComuneResidenzaMan());
		setNazioneResidenzaMan(new NazioneResidenzaMan());
	}

	public ComuneMan getComuneMan() {
		return getComuneResidenzaMan();
	}

	public void setComuneMan(ComuneMan comuneMan) {
		setComuneResidenzaMan((ComuneResidenzaMan) comuneMan);
	}

	public NazioneMan getNazioneMan() {
		return getNazioneResidenzaMan();
	}

	public void setNazioneMan(NazioneMan nazioneMan) {
		setNazioneResidenzaMan((NazioneResidenzaMan) nazioneMan);
	}

	@Override
	public String getExtraLabel() {
		return "di residenza *:";
	}

	public ComuneResidenzaMan getComuneResidenzaMan() {
		return comuneResidenzaMan;
	}

	public void setComuneResidenzaMan(ComuneResidenzaMan comuneResidenzaMan) {
		this.comuneResidenzaMan = comuneResidenzaMan;
	}

	public NazioneResidenzaMan getNazioneResidenzaMan() {
		return nazioneResidenzaMan;
	}

	public void setNazioneResidenzaMan(NazioneResidenzaMan nazioneResidenzaMan) {
		this.nazioneResidenzaMan = nazioneResidenzaMan;
	}

	@Override
	public void comuneNazioneValueChangeListener(AjaxBehaviorEvent event) {
		super.comuneNazioneValueChangeListener(event);
		// cancellazione campo città
		FacesContext context = FacesContext.getCurrentInstance();
		ResidenzaCsaMan resBean = (ResidenzaCsaMan) context.getApplication()
				.evaluateExpressionGet(context,
						"#{datiAnaMan.residenzaCsaMan}", ResidenzaCsaMan.class);
		if (resBean != null && resBean.getIndirizzo() != null) {
			resBean.setCitta(null);
		}
		
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
