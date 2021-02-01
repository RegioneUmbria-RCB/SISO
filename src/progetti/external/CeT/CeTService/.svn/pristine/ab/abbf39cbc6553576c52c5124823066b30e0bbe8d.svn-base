package it.webred.ct.data.model.docfa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the DOCFA_VALORI database table.
 * 
 */
@Entity
@Table(name="DOCFA_VALORI")
public class DocfaValori implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocfaValoriPK id;
	
	private String fascia;

	@Column(name="VAL_MAX")
	private BigDecimal valMax;

	@Column(name="VAL_MED")
	private BigDecimal valMed;

	@Column(name="VAL_MIN")
	private BigDecimal valMin;

	private String zona;

    public DocfaValori() {
    }

	public String getFascia() {
		return this.fascia;
	}

	public void setFascia(String fascia) {
		this.fascia = fascia;
	}

	public BigDecimal getValMax() {
		return this.valMax;
	}

	public void setValMax(BigDecimal valMax) {
		this.valMax = valMax;
	}

	public BigDecimal getValMed() {
		return this.valMed;
	}

	public void setValMed(BigDecimal valMed) {
		this.valMed = valMed;
	}

	public BigDecimal getValMin() {
		return this.valMin;
	}

	public void setValMin(BigDecimal valMin) {
		this.valMin = valMin;
	}

	public String getZona() {
		return this.zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public void setId(DocfaValoriPK id) {
		this.id = id;
	}

	public DocfaValoriPK getId() {
		return id;
	}

}