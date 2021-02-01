package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the LOAD_CAT_UIU_ID database table.
 * 
 */
@Entity
@Table(name="LOAD_CAT_UIU_ID")
public class LoadCatUiuId implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LoadCatUiuIdPK id;

	private BigDecimal denom;

	private String edificialita;

	@Column(name="ERROR_CODE")
	private BigDecimal errorCode;

	private String foglio;

	private String graffato;

	@Column(name="LOAD_ID")
	private BigDecimal loadId;

	private String mappale;

	private String sez;

	@Column(name="STATO_SITI")
	private String statoSiti;

	private String sub;

    public LoadCatUiuId() {
    }

	public LoadCatUiuIdPK getId() {
		return this.id;
	}

	public void setId(LoadCatUiuIdPK id) {
		this.id = id;
	}
	
	public BigDecimal getDenom() {
		return this.denom;
	}

	public void setDenom(BigDecimal denom) {
		this.denom = denom;
	}

	public String getEdificialita() {
		return this.edificialita;
	}

	public void setEdificialita(String edificialita) {
		this.edificialita = edificialita;
	}

	public BigDecimal getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(BigDecimal errorCode) {
		this.errorCode = errorCode;
	}

	public String getFoglio() {
		return this.foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getGraffato() {
		return this.graffato;
	}

	public void setGraffato(String graffato) {
		this.graffato = graffato;
	}

	public BigDecimal getLoadId() {
		return this.loadId;
	}

	public void setLoadId(BigDecimal loadId) {
		this.loadId = loadId;
	}

	public String getMappale() {
		return this.mappale;
	}

	public void setMappale(String mappale) {
		this.mappale = mappale;
	}

	public String getSez() {
		return this.sez;
	}

	public void setSez(String sez) {
		this.sez = sez;
	}

	public String getStatoSiti() {
		return this.statoSiti;
	}

	public void setStatoSiti(String statoSiti) {
		this.statoSiti = statoSiti;
	}

	public String getSub() {
		return this.sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

}