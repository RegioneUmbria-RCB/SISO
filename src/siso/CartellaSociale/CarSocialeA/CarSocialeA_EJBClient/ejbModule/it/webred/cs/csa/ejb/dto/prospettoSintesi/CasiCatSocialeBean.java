package it.webred.cs.csa.ejb.dto.prospettoSintesi;

import java.io.Serializable;

import it.webred.cs.csa.ejb.dto.ContatoreCasiDTO;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsOSettore;

public class CasiCatSocialeBean implements Serializable{
	
	private Long catSocialeId;
	private String catSocialeDesc;
	
	private String settore;
	private String organizzazione;
	
	private ContatoreCasiDTO casi;
	private ContatoreCasiDTO casiInCarico;
	private ContatoreCasiDTO casiChiusi;
	
	
	public String getSettore() {
		return settore;
	}
	public void setSettore(String settore) {
		this.settore = settore;
	}
	public String getOrganizzazione() {
		return organizzazione;
	}
	public void setOrganizzazione(String organizzazione) {
		this.organizzazione = organizzazione;
	}
	public ContatoreCasiDTO getCasi() {
		return casi;
	}
	public ContatoreCasiDTO getCasiInCarico() {
		return casiInCarico;
	}
	public ContatoreCasiDTO getCasiChiusi() {
		return casiChiusi;
	}
	public void setCasi(ContatoreCasiDTO casi) {
		this.casi = casi;
	}
	public void setCasiInCarico(ContatoreCasiDTO casiInCarico) {
		this.casiInCarico = casiInCarico;
	}
	public void setCasiChiusi(ContatoreCasiDTO casiChiusi) {
		this.casiChiusi = casiChiusi;
	}
	public Long getCatSocialeId() {
		return catSocialeId;
	}
	public void setCatSocialeId(Long catSocialeId) {
		this.catSocialeId = catSocialeId;
	}
	public String getCatSocialeDesc() {
		return catSocialeDesc;
	}
	public void setCatSocialeDesc(String catSocialeDesc) {
		this.catSocialeDesc = catSocialeDesc;
	}
	
}
