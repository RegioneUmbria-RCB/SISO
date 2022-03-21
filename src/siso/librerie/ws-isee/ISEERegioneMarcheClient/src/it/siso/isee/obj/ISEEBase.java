package it.siso.isee.obj;

import java.io.Serializable;

public class ISEEBase implements Serializable {

	private String dataRilascio;
	private ModalitaCalcoloISEE modalitaCalcolo;
	private Valori valori;
	
	public String getDataRilascio() {
		return dataRilascio;
	}
	public void setDataRilascio(String dataRilascio) {
		this.dataRilascio = dataRilascio;
	}
	public ModalitaCalcoloISEE getModalitaCalcolo() {
		return modalitaCalcolo;
	}
	public void setModalitaCalcolo(ModalitaCalcoloISEE modalitaCalcolo) {
		this.modalitaCalcolo = modalitaCalcolo;
	}
	public Valori getValori() {
		return valori;
	}
	public void setValori(Valori valori) {
		this.valori = valori;
	}
	
	
}
