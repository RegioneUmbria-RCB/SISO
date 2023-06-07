package it.webred.cs.json.provvedimentiMinori.ver1.tabs;

import it.webred.cs.json.dto.JsonBaseBean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InterventiTutelaBean extends JsonBaseBean {

	private boolean allontanamento;
	private String selTipoColocamento;
	private String selIncontriProtetti;
	private Boolean regIncontri;
	private String regIncontriConChi;
	private String selSospensioneRapporti;
	private String selQualiParenti;
	private String selAffidoParenti;
	private String selIntTutela;
	private String incontriProttAqualiParenti;
	private String[] selMultiIntTutela;

	public InterventiTutelaBean() {
		this.selIntTutela = "Non definito";
		this.selTipoColocamento = "Non definito";
		this.selIncontriProtetti = "Non definito";
	}

	public boolean isAllontanamento() {
		return allontanamento;
	}

	public void setAllontanamento(boolean allontanamento) {
		this.allontanamento = allontanamento;
	}

	public String getSelTipoColocamento() {
		return selTipoColocamento;
	}

	public void setSelTipoColocamento(String selTipoColocamento) {
		this.selTipoColocamento = selTipoColocamento;
	}

	public String getSelSospensioneRapporti() {
		return selSospensioneRapporti;
	}

	public void setSelSospensioneRapporti(String selSospensioneRapporti) {
		this.selSospensioneRapporti = selSospensioneRapporti;
	}

	public String getSelIncontriProtetti() {
		return selIncontriProtetti;
	}

	public void setSelIncontriProtetti(String selIncontriProtetti) {
		this.selIncontriProtetti = selIncontriProtetti;
	}

	public Boolean getRegIncontri() {
		return regIncontri;
	}

	public void setRegIncontri(Boolean regIncontri) {
		this.regIncontri = regIncontri;
	}

	public String getRegIncontriConChi() {
		return regIncontriConChi;
	}

	public void setRegIncontriConChi(String regIncontriConChi) {
		this.regIncontriConChi = regIncontriConChi;
	}

	public String getSelQualiParenti() {
		return selQualiParenti;
	}

	public void setSelQualiParenti(String selQualiParenti) {
		this.selQualiParenti = selQualiParenti;
	}

	public String getSelAffidoParenti() {
		return selAffidoParenti;
	}

	public void setSelAffidoParenti(String selAffidoParenti) {
		this.selAffidoParenti = selAffidoParenti;
	}

	public String getSelIntTutela() {
		return selIntTutela;
	}

	public void setSelIntTutela(String selIntTutela) {
		this.selIntTutela = selIntTutela;
	}

	public String[] getSelMultiIntTutela() {
		return selMultiIntTutela;
	}

	public void setSelMultiIntTutela(String[] selMultiIntTutela) {
		this.selMultiIntTutela = selMultiIntTutela;
	}

	public String getIncontriProttAqualiParenti() {
		return incontriProttAqualiParenti;
	}

	public void setIncontriProttAqualiParenti(String incontriProttAqualiParenti) {
		this.incontriProttAqualiParenti = incontriProttAqualiParenti;
	}

	@Override
	public List<String> checkObbligatorieta() {
		// TODO Auto-generated method stub
		return null;
	}

}
