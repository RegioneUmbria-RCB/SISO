package it.webred.cs.csa.ejb.dto.erogazioni;

import java.io.Serializable;
import java.util.Date;

public class ErogazioneDettaglioSintesiDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date dataEsecuzione;
	private Date dataEsecuzioneA;
	private String stato;
	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Date getDataEsecuzione() {
		return dataEsecuzione;
	}
	public void setDataEsecuzione(Date dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}
	public Date getDataEsecuzioneA() {
		return dataEsecuzioneA;
	}
	public void setDataEsecuzioneA(Date dataEsecuzioneA) {
		this.dataEsecuzioneA = dataEsecuzioneA;
	}
}
