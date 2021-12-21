package it.webred.cs.sociosan.ejb.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.Date;

public class SegnalazioneSerenaDTO extends CeTBaseObject  {

	private long id;
	private long schedaId;
	private String provenienza;
	private Date dtIns;
	private Date dtMod;
	private String flgStato;
	private String codEnte;
	private String userIns;
	private String usrMod;
	private String stato;
	private String notaStato;
	private Boolean flgEsistente;
	private Long soggettoId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSchedaId() {
		return schedaId;
	}
	public void setSchedaId(long schedaId) {
		this.schedaId = schedaId;
	}
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	public Date getDtIns() {
		return dtIns;
	}
	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}
	public Date getDtMod() {
		return dtMod;
	}
	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}
	public String getFlgStato() {
		return flgStato;
	}
	public void setFlgStato(String flgStato) {
		this.flgStato = flgStato;
	}
	public String getCodEnte() {
		return codEnte;
	}
	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}
	public String getUserIns() {
		return userIns;
	}
	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}
	public String getUsrMod() {
		return usrMod;
	}
	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getNotaStato() {
		return notaStato;
	}
	public void setNotaStato(String notaStato) {
		this.notaStato = notaStato;
	}
	public Boolean getFlgEsistente() {
		return flgEsistente;
	}
	public void setFlgEsistente(Boolean flgEsistente) {
		this.flgEsistente = flgEsistente;
	}
	public Long getSoggettoId() {
		return soggettoId;
	}
	public void setSoggettoId(Long soggettoId) {
		this.soggettoId = soggettoId;
	}
}
