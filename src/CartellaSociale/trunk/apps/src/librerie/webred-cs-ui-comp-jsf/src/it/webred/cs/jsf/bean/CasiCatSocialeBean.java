package it.webred.cs.jsf.bean;

import it.webred.cs.csa.ejb.dto.ContatoreCasiDTO;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsOSettore;

public class CasiCatSocialeBean {
	
	private CsCCategoriaSociale catSociale;
	private CsOSettore settore;
	private ContatoreCasiDTO casi;
	private ContatoreCasiDTO casiInCarico;
	private ContatoreCasiDTO casiChiusi;
	
	public CsCCategoriaSociale getCatSociale() {
		return catSociale;
	}
	public void setCatSociale(CsCCategoriaSociale catSociale) {
		this.catSociale = catSociale;
	}
	public CsOSettore getSettore() {
		return settore;
	}
	public void setSettore(CsOSettore settore) {
		this.settore = settore;
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
	
}
