package it.escsolution.escwebgis.acqua.bean;

import it.escsolution.escwebgis.common.EscObject;
import it.escsolution.escwebgis.cosapNew.bean.CosapTassa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class AcquaUtente extends EscObject implements Serializable {


	private static final long serialVersionUID = 1L;
	String id;
	String cognome;
	String nome;
	String sesso;
	String dtNascita;
	String comuneNascita;
	String prNascita;
	String codFiscale;
	String denominazione;
	String partIva;
	String viaResidenza;
	String civicoResidenza;
	String capResidenza;
	String comuneResidenza;
	String prResidenza;
	String telefono;
	String faxEmail;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getDtNascita() {
		return dtNascita;
	}
	public void setDtNascita(String dtNascita) {
		this.dtNascita = dtNascita;
	}
	public String getComuneNascita() {
		return comuneNascita;
	}
	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	public String getPrNascita() {
		return prNascita;
	}
	public void setPrNascita(String prNascita) {
		this.prNascita = prNascita;
	}
	public String getCodFiscale() {
		return codFiscale;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getPartIva() {
		return partIva;
	}
	public void setPartIva(String partIva) {
		this.partIva = partIva;
	}
	public String getViaResidenza() {
		return viaResidenza;
	}
	public void setViaResidenza(String viaResidenza) {
		this.viaResidenza = viaResidenza;
	}
	public String getCivicoResidenza() {
		return civicoResidenza;
	}
	public void setCivicoResidenza(String civicoResidenza) {
		this.civicoResidenza = civicoResidenza;
	}
	public String getCapResidenza() {
		return capResidenza;
	}
	public void setCapResidenza(String capResidenza) {
		this.capResidenza = capResidenza;
	}
	public String getComuneResidenza() {
		return comuneResidenza;
	}
	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}
	public String getPrResidenza() {
		return prResidenza;
	}
	public void setPrResidenza(String prResidenza) {
		this.prResidenza = prResidenza;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getFaxEmail() {
		return faxEmail;
	}
	public void setFaxEmail(String faxEmail) {
		this.faxEmail = faxEmail;
	}

}
