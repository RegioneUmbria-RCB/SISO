package it.webred.WsSitClient.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ResponseDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String errCode;
	private String errDescrizione;
	private Boolean flgErrore = false;
	
	private List<CivicoDTO> listaCivici;
	private List<ViaDTO> listaVie;
	
	
	private BigDecimal versione;
	
	public BigDecimal getVersione() {
		return versione;
	}
	public void setVersione(BigDecimal versione) {
		this.versione = versione;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrDescrizione() {
		return errDescrizione;
	}
	public void setErrDescrizione(String errDescrizione) {
		this.errDescrizione = errDescrizione;
	}
	public Boolean getFlgErrore() {
		return flgErrore;
	}
	public void setFlgErrore(Boolean flgErrore) {
		this.flgErrore = flgErrore;
	}
	public List<CivicoDTO> getListaCivici() {
		return listaCivici;
	}
	public void setListaCivici(List<CivicoDTO> listaCivici) {
		this.listaCivici = listaCivici;
	}
	public List<ViaDTO> getListaVie() {
		return listaVie;
	}
	public void setListaVie(List<ViaDTO> listaVie) {
		this.listaVie = listaVie;
	}
	
}
