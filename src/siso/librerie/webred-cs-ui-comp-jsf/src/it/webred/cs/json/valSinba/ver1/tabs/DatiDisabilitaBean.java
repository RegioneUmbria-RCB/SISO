package it.webred.cs.json.valSinba.ver1.tabs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatiDisabilitaBean {
	
	private int disabile;
	private int tipoDisabilita;
	private int certificazioneInvCivile;

	
	public DatiDisabilitaBean() {
		
	}


	public int getDisabile() {
		return disabile;
	}


	public void setDisabile(int disabile) {
		this.disabile = disabile;
	}


	public int getTipoDisabilita() {
		return tipoDisabilita;
	}


	public void setTipoDisabilita(int tipoDisabilita) {
		this.tipoDisabilita = tipoDisabilita;
	}


	public int getCertificazioneInvCivile() {
		return certificazioneInvCivile;
	}


	public void setCertificazioneInvCivile(int certificazioneInvCivile) {
		this.certificazioneInvCivile = certificazioneInvCivile;
	}

	public void onChangeDisabile() {
		if(disabile != 1) {
			this.setTipoDisabilita(0);
			this.setCertificazioneInvCivile(0);
		} 
	}
	
	public boolean isDisabileSelected() {
		return disabile==1;
	}

}
