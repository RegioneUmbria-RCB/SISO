package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;

public class SitSclClassi extends TabellaDwh {

	private String codClasse;
	private String codIstituto;
	private BigDecimal nrSezione;
	private String sezioneClasse;
	private String tipoClasse;
	private String mensaLun;
	private String mensaMar;
	private String mensaMer;
	private String mensaGio;
	private String mensaVen;
	private String mensaSab;

	@Override
	public String getValueForCtrHash() {
		try {
			return codClasse
					+ codIstituto
					+ nrSezione.toString()
					+ sezioneClasse
					+ tipoClasse
					+ mensaLun
					+ mensaMar
					+ mensaMer
					+ mensaGio
					+ mensaVen
					+ mensaSab;
		} catch (Exception e) {
			return null;
		}

	}

	public String getCodClasse() {
		return codClasse;
	}

	public void setCodClasse(String codClasse) {
		this.codClasse = codClasse;
	}

	public String getCodIstituto() {
		return codIstituto;
	}

	public void setCodIstituto(String codIstituto) {
		this.codIstituto = codIstituto;
	}

	public BigDecimal getNrSezione() {
		return nrSezione;
	}

	public void setNrSezione(BigDecimal nrSezione) {
		this.nrSezione = nrSezione;
	}

	public String getSezioneClasse() {
		return sezioneClasse;
	}

	public void setSezioneClasse(String sezioneClasse) {
		this.sezioneClasse = sezioneClasse;
	}

	public String getTipoClasse() {
		return tipoClasse;
	}

	public void setTipoClasse(String tipoClasse) {
		this.tipoClasse = tipoClasse;
	}

	public String getMensaLun() {
		return mensaLun;
	}

	public void setMensaLun(String mensaLun) {
		this.mensaLun = mensaLun;
	}

	public String getMensaMar() {
		return mensaMar;
	}

	public void setMensaMar(String mensaMar) {
		this.mensaMar = mensaMar;
	}

	public String getMensaMer() {
		return mensaMer;
	}

	public void setMensaMer(String mensaMer) {
		this.mensaMer = mensaMer;
	}

	public String getMensaGio() {
		return mensaGio;
	}

	public void setMensaGio(String mensaGio) {
		this.mensaGio = mensaGio;
	}

	public String getMensaVen() {
		return mensaVen;
	}

	public void setMensaVen(String mensaVen) {
		this.mensaVen = mensaVen;
	}

	public String getMensaSab() {
		return mensaSab;
	}

	public void setMensaSab(String mensaSab) {
		this.mensaSab = mensaSab;
	}
	
}
