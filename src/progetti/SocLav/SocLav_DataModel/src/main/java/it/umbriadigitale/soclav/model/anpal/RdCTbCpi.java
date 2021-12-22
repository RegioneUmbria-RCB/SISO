package it.umbriadigitale.soclav.model.anpal;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RDC_TB_CPI")
public class RdCTbCpi implements Serializable {
	
	@Id
	private String codice;
	private String denominazione;
	private String indirizzo;
	private String telefono;
	
	@Column(name="EMAIL_SEDE")
	private String emailSede;
	
	@Column(name="EMAIL_RESPONSABILE")
	private String emailResponsabile;
	
	@Column(name="DT_INIZIO_VAL")
	private Date dtInizioVal;
	
	@Column(name="DT_FINE_VAL")
	private Date dtFineVal;
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmailSede() {
		return emailSede;
	}
	public void setEmailSede(String emailSede) {
		this.emailSede = emailSede;
	}
	public String getEmailResponsabile() {
		return emailResponsabile;
	}
	public void setEmailResponsabile(String emailResponsabile) {
		this.emailResponsabile = emailResponsabile;
	}
	public Date getDtInizioVal() {
		return dtInizioVal;
	}
	public void setDtInizioVal(Date dtInizioVal) {
		this.dtInizioVal = dtInizioVal;
	}
	public Date getDtFineVal() {
		return dtFineVal;
	}
	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}

}
