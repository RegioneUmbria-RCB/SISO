package it.webred.cs.jsf.manbean;

import it.webred.cs.jsf.manbean.superc.ComuneMan;
import it.webred.cs.jsf.manbean.superc.ComuneNazioneMan;
import it.webred.cs.jsf.manbean.superc.NazioneMan;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.jsf.bean.ComuneBean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@ManagedBean
@NoneScoped
public class ComuneNazioneNascitaMan extends ComuneNazioneMan implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private ComuneNascitaMan comuneNascitaMan;

	private NazioneNascitaMan nazioneNascitaMan;
	
	 public ComuneNazioneNascitaMan() {
		 	comuneNascitaMan = new ComuneNascitaMan();
		 	nazioneNascitaMan = new NazioneNascitaMan();
	 }
	 
	public void init(AmTabComuni comune, AmTabNazioni nazione){
		comuneNascitaMan = new ComuneNascitaMan();
	 	nazioneNascitaMan = new NazioneNascitaMan();
		if (comune != null) {
			ComuneBean comuneBean = new ComuneBean(comune);
			getComuneNascitaMan().setComune(comuneBean);
			setComuneValue();
		} else {
			setNazioneValue();
			getNazioneMan().setNazione(nazione);
		}
	} 
	
	public void init(ComuneBean comuneBean, AmTabNazioni nazione){
		comuneNascitaMan = new ComuneNascitaMan();
	 	nazioneNascitaMan = new NazioneNascitaMan();
		if (comuneBean != null) {
			getComuneNascitaMan().setComune(comuneBean);
			setComuneValue();
		} else {
			setNazioneValue();
			getNazioneMan().setNazione(nazione);
		}
	} 
	
	public ComuneNascitaMan getComuneNascitaMan() {
		return comuneNascitaMan;
	}

	public void setComuneNascitaMan(ComuneNascitaMan comuneNascitaMan) {
		this.comuneNascitaMan = comuneNascitaMan;
	}

	public NazioneNascitaMan getNazioneNascitaMan() {
		return nazioneNascitaMan;
	}

	public void setNazioneNascitaMan(NazioneNascitaMan nazioneNascitaMan) {
		this.nazioneNascitaMan = nazioneNascitaMan;
	}

	public ComuneMan getComuneMan() {
		return getComuneNascitaMan();
	}

	public void setComuneMan(ComuneMan comuneMan) {
		setComuneNascitaMan((ComuneNascitaMan)comuneMan);
	}

	public NazioneMan getNazioneMan() {
		return getNazioneNascitaMan();
	}

	public void setNazioneMan(NazioneMan nazioneMan) {
		setNazioneNascitaMan((NazioneNascitaMan)nazioneMan);
	}

	@Override
	public String getExtraLabel() {
		return "di nascita";
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

	public String getDescrizioneLuogoDiNascita(){
		String s = "";
		if(isNazione() && getNazioneMan().getNazione()!=null)
			s+= getNazioneMan().getNazione().getNazione(); 
		else {
			ComuneBean cb = getComuneNascitaMan().getComune();
			s+= cb!=null ? cb.getDenominazione()+ " ("+cb.getSiglaProv()+")" : "";
		}
		return s;
	}


}
