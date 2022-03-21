package it.webred.ct.data.access.basic.anagrafe.dto;

import it.webred.ct.data.model.anagrafe.SitComune;
import it.webred.ct.data.model.anagrafe.SitDCivicoV;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.data.model.anagrafe.SitDVia;
import it.webred.ct.data.model.anagrafe.SitProvincia;
import it.webred.ct.data.model.common.SitEnte;

import java.io.Serializable;

public class AnagraficaDTO extends AnagrafeBaseDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SitDPersona persona;
	private SitDCivicoV civico;
	private SitDVia via;
	private SitComune comuneNascita;
	private SitEnte comuneResidenza;
	private String provinciaResidenza;
	private String capResidenza;
	private SitProvincia provNascita;
	private SitComune comuneResidenzaEmig;
	private SitProvincia provResidenzaEmig;
	
	public SitDPersona getPersona() {
		return persona;
	}
	public void setPersona(SitDPersona persona) {
		this.persona = persona;
	}
	public SitDCivicoV getCivico() {
		return civico;
	}
	public void setCivico(SitDCivicoV civico) {
		this.civico = civico;
	}
	public SitDVia getVia() {
		return via;
	}
	public void setVia(SitDVia via) {
		this.via = via;
	}
	public SitComune getComuneNascita() {
		return comuneNascita;
	}
	public void setComuneNascita(SitComune comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	public SitProvincia getProvNascita() {
		return provNascita;
	}
	public void setProvNascita(SitProvincia provNascita) {
		this.provNascita = provNascita;
	}
	public SitEnte getComuneResidenza() {
		return comuneResidenza;
	}
	public void setComuneResidenza(SitEnte comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}
	public SitComune getComuneResidenzaEmig() {
		return comuneResidenzaEmig;
	}
	public void setComuneResidenzaEmig(SitComune comuneResidenzaEmig) {
		this.comuneResidenzaEmig = comuneResidenzaEmig;
	}
	public SitProvincia getProvResidenzaEmig() {
		return provResidenzaEmig;
	}
	public void setProvResidenzaEmig(SitProvincia provResidenzaEmig) {
		this.provResidenzaEmig = provResidenzaEmig;
	}
	public String getProvinciaResidenza() {
		return provinciaResidenza;
	}
	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}
	public String getCapResidenza() {
		return capResidenza;
	}
	public void setCapResidenza(String capResidenza) {
		this.capResidenza = capResidenza;
	}
}
