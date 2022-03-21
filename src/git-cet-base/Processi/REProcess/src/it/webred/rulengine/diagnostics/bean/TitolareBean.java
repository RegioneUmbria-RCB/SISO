package it.webred.rulengine.diagnostics.bean;

import java.util.Date;

public class TitolareBean {
	private String tipoTitolo;
	private String descrTitolo;
	private double percPoss;
	private Date dataInizio;
	private Long pkidSogg;
	private String codFisc;
	private String pIva;
	private String ragSoc;
	private String nome;
	private Date dtNascita;
	private String sesso;
	private String codComuneNascita;
	private Long pkCuaa;
	private String flagPersonaFisica;
	private Boolean flagResidente;
	
		
	public String toString() {
		
		String titStr = (codFisc == null) ? "" : codFisc;
		if (codFisc!=null && pIva != null)
			titStr +="/";
		titStr += (pIva == null) ? "" : pIva;
		titStr += " ";
		titStr += (nome == null) ? "" : nome;
		titStr += (ragSoc == null) ? "" : " " + ragSoc;
		return titStr;
	}
	public String getTipoTitolo() {
		return tipoTitolo;
	}
	public void setTipoTitolo(String tipoTitolo) {
		this.tipoTitolo = tipoTitolo;
	}
	public String getDescrTitolo() {
		return descrTitolo;
	}
	public void setDescrTitolo(String descrTitolo) {
		this.descrTitolo = descrTitolo;
	}
	public double getPercPoss() {
		return percPoss;
	}
	public void setPercPoss(double percPoss) {
		this.percPoss = percPoss;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Long getPkidSogg() {
		return pkidSogg;
	}
	public void setPkidSogg(Long pkidSogg) {
		this.pkidSogg = pkidSogg;
	}
	public String getCodFisc() {
		return codFisc;
	}
	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}
	public String getPIva() {
		return pIva;
	}
	public void setPIva(String pIva) {
		this.pIva = pIva;
	}
	public String getRagSoc() {
		return ragSoc;
	}
	public void setRagSoc(String ragSoc) {
		this.ragSoc = ragSoc;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDtNascita() {
		return dtNascita;
	}
	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getCodComuneNascita() {
		return codComuneNascita;
	}
	public void setCodComuneNascita(String codComuneNascita) {
		this.codComuneNascita = codComuneNascita;
	}
	public Long getPkCuaa() {
		return pkCuaa;
	}
	public void setPkCuaa(Long pkCuaa) {
		this.pkCuaa = pkCuaa;
	}

	public String getFlagPersonaFisica() {
		return flagPersonaFisica;
	}

	public void setFlagPersonaFisica(String flagPersonaFisica) {
		this.flagPersonaFisica = flagPersonaFisica;
	}
	public Boolean getFlagResidente() {
		return flagResidente;
	}
	public void setFlagResidente(Boolean flagResidente) {
		this.flagResidente = flagResidente;
	}
	
	
	
	
}
