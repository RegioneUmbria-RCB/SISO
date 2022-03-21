package it.webred.ct.config.audit.dto;

import it.webred.ct.config.model.AmAudit;
import it.webred.ct.config.model.AmAuditDecode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AuditDTO implements Serializable {
	
	private AmAudit amAudit;
	private AmAuditDecode amAuditDecode;
	private String utente;
	private String sessione;
	private String fonte;
	private Date dataMinSessione;
	private Date dataMaxSessione;
	private String descrCompletaFonte;
	
	public String getUtente() {
		return utente;
	}
	public void setUtente(String utente) {
		this.utente = utente;
	}
	public String getSessione() {
		return sessione;
	}
	public void setSessione(String sessione) {
		this.sessione = sessione;
	}
	public String getFonte() {
		return fonte;
	}
	public void setFonte(String fonte) {
		this.fonte = fonte;
	}
	public AmAudit getAmAudit() {
		return amAudit;
	}
	public void setAmAudit(AmAudit amAudit) {
		this.amAudit = amAudit;
	}
	public Date getDataMinSessione() {
		return dataMinSessione;
	}
	public void setDataMinSessione(Date dataMinSessione) {
		this.dataMinSessione = dataMinSessione;
	}
	public Date getDataMaxSessione() {
		return dataMaxSessione;
	}
	public void setDataMaxSessione(Date dataMaxSessione) {
		this.dataMaxSessione = dataMaxSessione;
	}
	public String getDescrCompletaFonte() {
		return descrCompletaFonte;
	}
	public void setDescrCompletaFonte(String descrCompletaFonte) {
		this.descrCompletaFonte = descrCompletaFonte;
	}
	public AmAuditDecode getAmAuditDecode() {
		return amAuditDecode;
	}
	public void setAmAuditDecode(AmAuditDecode amAuditDecode) {
		this.amAuditDecode = amAuditDecode;
	}
	
}
