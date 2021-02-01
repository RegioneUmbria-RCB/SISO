package it.webred.ct.data.model.catasto;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the LOAD_CAT_UIU_IND database table.
 * 
 */
@Entity
@Table(name="LOAD_CAT_UIU_IND")
@IdClass(LoadCatUiuIndPK.class)
@IndiceEntity(sorgente="4")
public class LoadCatUiuInd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CODI_FISC_LUNA")
	@IndiceKey(pos="4")
	@Id
	private String codiFiscLuna;


	@IndiceKey(pos="5")
	@Id
	private String sezione;

	@Column(name="ID_IMM")
	@IndiceKey(pos="1")
	@Id
	private BigDecimal idImm;

	@IndiceKey(pos="2")
	@Id
	private BigDecimal progressivo;

	@IndiceKey(pos="3")
	@Id
	private BigDecimal seq;

	
	private String civ1;

	private String civ2;

	private String civ3;

	@Column(name="COD_STRADA")
	private BigDecimal codStrada;

	@Column(name="ERROR_CODE")
	private BigDecimal errorCode;

	private String ind;

	@Column(name="LOAD_ID")
	private BigDecimal loadId;

	@Column(name="STATO_SITI")
	private String statoSiti;

	private BigDecimal toponimo;

    public LoadCatUiuInd() {
    }


	public String getCiv1() {
		return this.civ1;
	}

	public void setCiv1(String civ1) {
		this.civ1 = civ1;
	}

	public String getCiv2() {
		return this.civ2;
	}

	public void setCiv2(String civ2) {
		this.civ2 = civ2;
	}

	public String getCiv3() {
		return this.civ3;
	}

	public void setCiv3(String civ3) {
		this.civ3 = civ3;
	}

	public BigDecimal getCodStrada() {
		return this.codStrada;
	}

	public void setCodStrada(BigDecimal codStrada) {
		this.codStrada = codStrada;
	}

	public BigDecimal getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(BigDecimal errorCode) {
		this.errorCode = errorCode;
	}

	public String getInd() {
		return this.ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}

	public BigDecimal getLoadId() {
		return this.loadId;
	}

	public void setLoadId(BigDecimal loadId) {
		this.loadId = loadId;
	}

	public String getStatoSiti() {
		return this.statoSiti;
	}

	public void setStatoSiti(String statoSiti) {
		this.statoSiti = statoSiti;
	}

	public BigDecimal getToponimo() {
		return this.toponimo;
	}

	public void setToponimo(BigDecimal toponimo) {
		this.toponimo = toponimo;
	}


	public String getCodiFiscLuna() {
		return codiFiscLuna;
	}


	public void setCodiFiscLuna(String codiFiscLuna) {
		this.codiFiscLuna = codiFiscLuna;
	}


	public String getSezione() {
		return sezione;
	}


	public void setSezione(String sezione) {
		this.sezione = sezione;
	}


	public BigDecimal getIdImm() {
		return idImm;
	}


	public void setIdImm(BigDecimal idImm) {
		this.idImm = idImm;
	}


	public BigDecimal getProgressivo() {
		return progressivo;
	}


	public void setProgressivo(BigDecimal progressivo) {
		this.progressivo = progressivo;
	}


	public BigDecimal getSeq() {
		return seq;
	}


	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}
	
	

}