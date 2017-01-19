package it.webred.ss.ejb.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.util.Date;

public class DatiSchedaListDTO extends CeTBaseObject {

	private BigDecimal identificativo;
	private Long id;
	private Date dataAccesso;
	private String cognomeUtente;
	private String nomeUtente;
	private Date dataNascita;
	private String tipo;
	private String operatore;
	private String stato;
	private Long ufficioId;
	private String ufficio;
	private Long   pContattoId;
	private String pContatto;
	private Date dataModifica;
	private Date dataPrivacy;
	
	public Long getId() {
		return id;
	}
	public Date getDataAccesso() {
		return dataAccesso;
	}
	public String getCognomeUtente() {
		return cognomeUtente;
	}
	public String getNomeUtente() {
		return nomeUtente;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public String getTipo() {
		return tipo;
	}
	public String getOperatore() {
		return operatore;
	}
	public String getStato() {
		return stato;
	}
	public Long getUfficioId() {
		return ufficioId;
	}
	public String getUfficio() {
		return ufficio;
	}
	public Long getpContattoId() {
		return pContattoId;
	}
	public String getpContatto() {
		return pContatto;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setDataAccesso(Date dataAccesso) {
		this.dataAccesso = dataAccesso;
	}
	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public void setUfficioId(Long ufficioId) {
		this.ufficioId = ufficioId;
	}
	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}
	public void setpContattoId(Long pContattoId) {
		this.pContattoId = pContattoId;
	}
	public void setpContatto(String pContatto) {
		this.pContatto = pContatto;
	}
	public Date getDataModifica() {
		return dataModifica;
	}
	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}
	public Date getDataPrivacy() {
		return dataPrivacy;
	}
	public void setDataPrivacy(Date dataPrivacy) {
		this.dataPrivacy = dataPrivacy;
	}
	public BigDecimal getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(BigDecimal identificativo) {
		this.identificativo = identificativo;
	}
}
