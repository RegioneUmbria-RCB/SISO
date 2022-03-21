package it.webred.cs.jsf.manbean.comuneNazione;

import it.webred.cs.jsf.manbean.superc.ComuneMan;
import it.webred.cs.jsf.manbean.superc.ComuneNazioneMan;
import it.webred.cs.jsf.manbean.superc.NazioneMan;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@ManagedBean
@NoneScoped
public class ComuneNazioneGenericMan extends ComuneNazioneMan implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private ComuneGenericMan  comuneGenericMan;
	private NazioneGenericMan nazioneGenericMan;
	
	private String tipo;
	
	public ComuneNazioneGenericMan(){
		super();
	}

	public ComuneNazioneGenericMan(String tipo) {
		this.tipo = tipo;
		setComuneMan(new ComuneGenericMan(tipo));
		setNazioneMan(new NazioneGenericMan(tipo));
	}

	public ComuneMan getComuneMan() {
		return this.getComuneGenericMan();
	}

	public void setComuneMan(ComuneMan comuneMan) {
		setComuneGenericMan((ComuneGenericMan) comuneMan);
	}

	public NazioneMan getNazioneMan() {
		return getNazioneGenericMan();
	}

	public void setNazioneMan(NazioneMan nazioneMan) {
		setNazioneGenericMan((NazioneGenericMan) nazioneMan);
	}

	@Override
	public String getExtraLabel() {
		return "di "+tipo;
	}
	
	
	@Override
	public String getExtraLabel(boolean required) {
		if (required)
			return getExtraLabel() + " *";
		else
			return getExtraLabel();
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

	public ComuneGenericMan getComuneGenericMan() {
		return comuneGenericMan;
	}

	public NazioneGenericMan getNazioneGenericMan() {
		return nazioneGenericMan;
	}

	public void setComuneGenericMan(ComuneGenericMan comuneGenericMan) {
		this.comuneGenericMan = comuneGenericMan;
	}

	public void setNazioneGenericMan(NazioneGenericMan nazioneGenericMan) {
		this.nazioneGenericMan = nazioneGenericMan;
	}

	public boolean isValorizzato(){
		return  (this.comuneGenericMan.getComune()!=null && this.getComuneGenericMan().getComune().getCodIstatComune()!=null) ||
				(this.nazioneGenericMan.getNazione()!=null && this.getNazioneGenericMan().getNazione().getCodIstatNazione()!=null);
	}
}
