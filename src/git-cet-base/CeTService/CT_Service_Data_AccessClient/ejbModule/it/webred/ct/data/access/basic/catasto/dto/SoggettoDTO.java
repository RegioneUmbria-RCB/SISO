package it.webred.ct.data.access.basic.catasto.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SoggettoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String tipo;
	private String descTipo;
	
	private String titolo;
	
	private String tipoDocumento;
	
	private String nomeSoggetto;
	private String cognomeSoggetto;
	private String cfSoggetto;
	
	private String denominazioneSoggetto;
	private String pivaSoggetto;
	
	private Date dataInizioPoss;
	private Date dataFinePoss;
	private String dataInizioPossStr;
	private String dataFinePossStr;

	private BigDecimal quota;
	
	private boolean ultimoValido;
	
	private static final SimpleDateFormat sdf= new  SimpleDateFormat("dd/MM/yyyy");
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getNomeSoggetto() {
		return nomeSoggetto;
	}
	public void setNomeSoggetto(String nomeSoggetto) {
		this.nomeSoggetto = nomeSoggetto;
	}
	public String getCognomeSoggetto() {
		return cognomeSoggetto;
	}
	public void setCognomeSoggetto(String cognomeSoggetto) {
		this.cognomeSoggetto = cognomeSoggetto;
	}
	public String getCfSoggetto() {
		return cfSoggetto;
	}
	public void setCfSoggetto(String cfSoggetto) {
		this.cfSoggetto = cfSoggetto;
	}
	public String getDenominazioneSoggetto() {
		return denominazioneSoggetto;
	}
	public void setDenominazioneSoggetto(String denominazioneSoggetto) {
		this.denominazioneSoggetto = denominazioneSoggetto;
	}
	public String getPivaSoggetto() {
		return pivaSoggetto;
	}
	public void setPivaSoggetto(String pivaSoggetto) {
		this.pivaSoggetto = pivaSoggetto;
	}
	
	public boolean isUltimoValido() {
		return ultimoValido;
	}
	public void setUltimoValido(boolean ultimoValido) {
		this.ultimoValido = ultimoValido;
	}
	
	
	public String getNominativoVis() {
		String nomin = "-";
		if("P".equalsIgnoreCase(tipo) || (cfSoggetto != null && !"-".equals(cfSoggetto))) {
			nomin = cognomeSoggetto;
			if (nomeSoggetto != null && !"".equals(nomeSoggetto)) {
				if (!"".equals(nomin)) {
					nomin += " ";
				}
				nomin += nomeSoggetto;
			}
		} else {
			nomin = denominazioneSoggetto;
		}
		return nomin;
	}
	public Date getDataInizioPoss() {
		return dataInizioPoss;
	}
	public void setDataInizioPoss(Date dataInizioPoss) {
		this.dataInizioPoss = dataInizioPoss;
		if (dataInizioPoss !=null)	
			this.dataInizioPossStr = sdf.format(dataInizioPoss);
		else
			this.dataInizioPossStr ="";
	}
	public Date getDataFinePoss() {
		return dataFinePoss;
	}
	public void setDataFinePoss(Date dataFinePoss) {
		this.dataFinePoss = dataFinePoss;
		if (dataFinePoss !=null)	
			this.dataFinePossStr = sdf.format(dataFinePoss);
		else
			this.dataFinePossStr ="";
	}
	
	public String getDescTipo() {
		
		this.descTipo = (this.tipo!=null && this.tipo.equalsIgnoreCase("G")? "Persona Giuridica" : "Persona Fisica");
		
		return descTipo;
	}
	public String getDataInizioPossStr() {
		return dataInizioPossStr;
	}
	public void setDataInizioPossStr(String dataInizioPossStr) {
		this.dataInizioPossStr = dataInizioPossStr;
	}
	public String getDataFinePossStr() {
		return dataFinePossStr;
	}
	public void setDataFinePossStr(String dataFinePossStr) {
		this.dataFinePossStr = dataFinePossStr;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public BigDecimal getQuota() {
		return quota;
	}
	public void setQuota(BigDecimal quota) {
		this.quota = quota;
	}
}
