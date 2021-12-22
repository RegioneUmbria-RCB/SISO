package it.webred.cs.csa.ejb.dto;

import java.util.Date;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class OperatoreDTO extends CeTBaseObject {
	private static final long serialVersionUID = 1L;
	
	private Long idOperatore;
	private Long idOrganizzazione;
	private Long idSettore;
	private Long idOperatoreSettore;
	private Date date;
	private String username;
	
	public Long getIdOperatore() {
		return idOperatore;
	}
	public void setIdOperatore(Long idOperatore) {
		this.idOperatore = idOperatore;
	}
	public Long getIdOrganizzazione() {
		return idOrganizzazione;
	}
	public void setIdOrganizzazione(Long idOrganizzazione) {
		this.idOrganizzazione = idOrganizzazione;
	}
	public Long getIdSettore() {
		return idSettore;
	}
	public void setIdSettore(Long idSettore) {
		this.idSettore = idSettore;
	}
	public Long getIdOperatoreSettore() {
		return idOperatoreSettore;
	}
	public void setIdOperatoreSettore(Long idOperatoreSettore) {
		this.idOperatoreSettore = idOperatoreSettore;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
