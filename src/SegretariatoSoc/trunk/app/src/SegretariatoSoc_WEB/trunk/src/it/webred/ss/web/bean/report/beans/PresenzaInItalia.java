package it.webred.ss.web.bean.report.beans;

public class PresenzaInItalia {

	private String paeseOrigineNucleoFamiliare ="-";
	private boolean minoreStranieroNonAccompagnato;

	private ConoscenzaLinguaItaliana conoscenzaLinguaItaliana = new ConoscenzaLinguaItaliana();
	private CondizioneGiuridica condizioneGiuridica = new CondizioneGiuridica();
	private ArrivoInItalia arrivoInItalia = new ArrivoInItalia();
	private ArrivoInRegione arrivoInRegione = new ArrivoInRegione();



	public String getPaeseOrigineNucleoFamiliare() {
		return paeseOrigineNucleoFamiliare;
	}



	public void setPaeseOrigineNucleoFamiliare(String paeseOrigineNucleoFamiliare) {
		this.paeseOrigineNucleoFamiliare = paeseOrigineNucleoFamiliare;
	}



	public boolean isMinoreStranieroNonAccompagnato() {
		return minoreStranieroNonAccompagnato;
	}



	public void setMinoreStranieroNonAccompagnato(boolean minoreStranieroNonAccompagnato) {
		this.minoreStranieroNonAccompagnato = minoreStranieroNonAccompagnato;
	}



	public ConoscenzaLinguaItaliana getConoscenzaLinguaItaliana() {
		return conoscenzaLinguaItaliana;
	}



	public void setConoscenzaLinguaItaliana(ConoscenzaLinguaItaliana conoscenzaLinguaItaliana) {
		this.conoscenzaLinguaItaliana = conoscenzaLinguaItaliana;
	}



	public CondizioneGiuridica getCondizioneGiuridica() {
		return condizioneGiuridica;
	}



	public void setCondizioneGiuridica(CondizioneGiuridica condizioneGiuridica) {
		this.condizioneGiuridica = condizioneGiuridica;
	}



	public ArrivoInItalia getArrivoInItalia() {
		return arrivoInItalia;
	}



	public void setArrivoInItalia(ArrivoInItalia arrivoInItalia) {
		this.arrivoInItalia = arrivoInItalia;
	}



	public ArrivoInRegione getArrivoInRegione() {
		return arrivoInRegione;
	}



	public void setArrivoInRegione(ArrivoInRegione arrivoInRegione) {
		this.arrivoInRegione = arrivoInRegione;
	}

}
