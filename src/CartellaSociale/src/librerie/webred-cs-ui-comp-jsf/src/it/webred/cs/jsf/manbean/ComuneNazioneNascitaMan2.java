package it.webred.cs.jsf.manbean;

import it.webred.cs.jsf.manbean.superc.ComuneMan;
import it.webred.cs.jsf.manbean.superc.ComuneNazioneMan;
import it.webred.cs.jsf.manbean.superc.NazioneMan;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@ManagedBean
@NoneScoped
public class ComuneNazioneNascitaMan2 extends ComuneNazioneMan implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private ComuneNascitaMan comuneNascitaMan;

	private NazioneNascitaMan nazioneNascitaMan;
	
	 public ComuneNazioneNascitaMan2() {
		 	comuneNascitaMan = new ComuneNascitaMan();
		 	nazioneNascitaMan = new NazioneNascitaMan();
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
		return "di nascita 2 (*):";
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
