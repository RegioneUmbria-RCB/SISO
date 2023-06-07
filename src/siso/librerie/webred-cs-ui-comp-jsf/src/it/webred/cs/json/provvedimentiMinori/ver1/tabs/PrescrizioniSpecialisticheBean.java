package it.webred.cs.json.provvedimentiMinori.ver1.tabs;

import it.webred.cs.json.dto.JsonBaseBean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrescrizioniSpecialisticheBean extends JsonBaseBean{
	private String[] selValutazionePsc;
	private String[] selPsicoterapia;
	private String[] selValutazioneCptv;
	private String  selValutazioneRel;

	@Override
	public List<String> checkObbligatorieta() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getSelValutazionePsc() {
		return selValutazionePsc;
	}

	public void setSelValutazionePsc(String[] selValutazionePsc) {
		this.selValutazionePsc = selValutazionePsc;
	}

	public String[] getSelPsicoterapia() {
		return selPsicoterapia;
	}

	public void setSelPsicoterapia(String[] selPsicoterapia) {
		this.selPsicoterapia = selPsicoterapia;
	}

	public String[] getSelValutazioneCptv() {
		return selValutazioneCptv;
	}

	public void setSelValutazioneCptv(String[] selValutazioneCptv) {
		this.selValutazioneCptv = selValutazioneCptv;
	}

	public String getSelValutazioneRel() {
		return selValutazioneRel;
	}

	public void setSelValutazioneRel(String selValutazioneRel) {
		this.selValutazioneRel = selValutazioneRel;
	}

	

}
