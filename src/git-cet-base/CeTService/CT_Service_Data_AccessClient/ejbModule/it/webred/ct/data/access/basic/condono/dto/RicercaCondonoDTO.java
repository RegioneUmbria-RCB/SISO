package it.webred.ct.data.access.basic.condono.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.util.Date;

public class RicercaCondonoDTO extends CeTBaseObject {

	private static final long serialVersionUID = 1L;

	private BigDecimal foglio;

	private String particella;

	private String sub;
	
	private Date dtRiferimento;

	private String codiceCondono;
	
	public BigDecimal getFoglio() {
		return foglio;
	}

	public void setFoglio(BigDecimal foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public void setDtRiferimento(Date dtRiferimento) {
		this.dtRiferimento = dtRiferimento;
	}

	public Date getDtRiferimento() {
		return dtRiferimento;
	}

	public String getCodiceCondono() {
		return codiceCondono;
	}

	public void setCodiceCondono(String codiceCondono) {
		this.codiceCondono = codiceCondono;
	}
	
	
	
}
