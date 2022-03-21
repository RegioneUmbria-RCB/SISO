package it.webred.ct.data.access.basic.compravendite.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class RicercaCompravenditeDTO extends CeTBaseObject implements Serializable{

	private static final long serialVersionUID = 1045055194850865024L;
	
	private String idNota;
	private BigDecimal iidNota;
	private BigDecimal iidFornitura;
	private String sezione;
	private String foglio;
	private String particella;
	private String sub;
	private Date dtRif;
	private String identificativoSoggetto = "";
	private String tipoSoggetto = "";	//P = Privato; G = Giuridico
	private Integer limit = 0;
	
	public String getIdNota() {
		return idNota;
	}

	public void setIdNota(String idNota) {
		this.idNota = idNota;
	}

	public BigDecimal getIidNota() {
		return iidNota;
	}

	public void setIidNota(BigDecimal iidNota) {
		this.iidNota = iidNota;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
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

	public Date getDtRif() {
		return dtRif;
	}

	public void setDtRif(Date dtRif) {
		this.dtRif = dtRif;
	}

	public BigDecimal getIidFornitura() {
		return iidFornitura;
	}

	public void setIidFornitura(BigDecimal iidFornitura) {
		this.iidFornitura = iidFornitura;
	}

	public String getIdentificativoSoggetto() {
		return identificativoSoggetto;
	}

	public void setIdentificativoSoggetto(String identificativoSoggetto) {
		this.identificativoSoggetto = identificativoSoggetto;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}


	
	
}
