package it.webred.ss.web.bean.report.beans;

public class ArrivoInRegione {

	private String presenza = "-";
	private boolean richiedenteOTitolareProtezioneInternazionale;
	private String comuneTitolareSprar = "-";
	private String strutturaResidenzialeDiAccoglienza = "-";
	private String indirizzo = "-";



	public String getPresenza() {
		return presenza;
	}



	public void setPresenza(String presenza) {
		if (presenza != null) {
			this.presenza = presenza;
		}
	}



	public boolean isRichiedenteOTitolareProtezioneInternazionale() {
		return richiedenteOTitolareProtezioneInternazionale;
	}



	public void setRichiedenteOTitolareProtezioneInternazionale(boolean richiedenteOTitolareProtezioneInternazionale) {
		this.richiedenteOTitolareProtezioneInternazionale = richiedenteOTitolareProtezioneInternazionale;
	}



	public String getComuneTitolareSprar() {
		return comuneTitolareSprar;
	}



	public void setComuneTitolareSprar(String comuneTitolareSprar) {
		this.comuneTitolareSprar = comuneTitolareSprar;
	}



	public String getStrutturaResidenzialeDiAccoglienza() {
		return strutturaResidenzialeDiAccoglienza;
	}



	public void setStrutturaResidenzialeDiAccoglienza(String strutturaResidenzialeDiAccoglienza) {
		if (strutturaResidenzialeDiAccoglienza != null) {
			this.strutturaResidenzialeDiAccoglienza = strutturaResidenzialeDiAccoglienza;
		}
	}



	public String getIndirizzo() {
		return indirizzo;
	}



	public void setIndirizzo(String indirizzo) {
		if (indirizzo != null) {
			this.indirizzo = indirizzo;
		}
	}

}
