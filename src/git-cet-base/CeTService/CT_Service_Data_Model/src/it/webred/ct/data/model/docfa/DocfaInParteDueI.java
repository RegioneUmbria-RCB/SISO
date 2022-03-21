package it.webred.ct.data.model.docfa;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DOCFA_IN_PARTE_DUE_I database table.
 * 
 */
@Entity
@Table(name="DOCFA_IN_PARTE_DUE_I")
public class DocfaInParteDueI implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocfaInPartePK id;
	
	@Column(name="CATEGORIA_APPARTENENZA")
	private String categoriaAppartenenza;

	@Column(name="DESTINAZIONE_USO")
	private String destinazioneUso;

	@Column(name="INFIS_INT_ALTRI_DESC")
	private String infisIntAltriDesc;

	@Column(name="INFIS_INT_PORTE_ING_ALTRO")
	private BigDecimal infisIntPorteIngAltro;

	@Column(name="INFIS_INT_PORTE_ING_LEGNO")
	private BigDecimal infisIntPorteIngLegno;

	@Column(name="INFIS_INT_PORTE_ING_LEGNO_TAMB")
	private BigDecimal infisIntPorteIngLegnoTamb;

	@Column(name="INFIS_INT_PORTE_ING_METALLO")
	private BigDecimal infisIntPorteIngMetallo;

	@Column(name="INFIS_INT_PORTE_INT_ALTRO")
	private BigDecimal infisIntPorteIntAltro;

	@Column(name="INFIS_INT_PORTE_INT_LEGNO")
	private BigDecimal infisIntPorteIntLegno;

	@Column(name="INFIS_INT_PORTE_INT_LEGNO_TAMB")
	private BigDecimal infisIntPorteIntLegnoTamb;

	@Column(name="INFIS_INT_PORTE_INT_METALLO")
	private BigDecimal infisIntPorteIntMetallo;

    public DocfaInParteDueI() {
    }

	public String getCategoriaAppartenenza() {
		return this.categoriaAppartenenza;
	}

	public void setCategoriaAppartenenza(String categoriaAppartenenza) {
		this.categoriaAppartenenza = categoriaAppartenenza;
	}

	public String getDestinazioneUso() {
		return this.destinazioneUso;
	}

	public void setDestinazioneUso(String destinazioneUso) {
		this.destinazioneUso = destinazioneUso;
	}

	public String getInfisIntAltriDesc() {
		return this.infisIntAltriDesc;
	}

	public void setInfisIntAltriDesc(String infisIntAltriDesc) {
		this.infisIntAltriDesc = infisIntAltriDesc;
	}

	public BigDecimal getInfisIntPorteIngAltro() {
		return this.infisIntPorteIngAltro;
	}

	public void setInfisIntPorteIngAltro(BigDecimal infisIntPorteIngAltro) {
		this.infisIntPorteIngAltro = infisIntPorteIngAltro;
	}

	public BigDecimal getInfisIntPorteIngLegno() {
		return this.infisIntPorteIngLegno;
	}

	public void setInfisIntPorteIngLegno(BigDecimal infisIntPorteIngLegno) {
		this.infisIntPorteIngLegno = infisIntPorteIngLegno;
	}

	public BigDecimal getInfisIntPorteIngLegnoTamb() {
		return this.infisIntPorteIngLegnoTamb;
	}

	public void setInfisIntPorteIngLegnoTamb(BigDecimal infisIntPorteIngLegnoTamb) {
		this.infisIntPorteIngLegnoTamb = infisIntPorteIngLegnoTamb;
	}

	public BigDecimal getInfisIntPorteIngMetallo() {
		return this.infisIntPorteIngMetallo;
	}

	public void setInfisIntPorteIngMetallo(BigDecimal infisIntPorteIngMetallo) {
		this.infisIntPorteIngMetallo = infisIntPorteIngMetallo;
	}

	public BigDecimal getInfisIntPorteIntAltro() {
		return this.infisIntPorteIntAltro;
	}

	public void setInfisIntPorteIntAltro(BigDecimal infisIntPorteIntAltro) {
		this.infisIntPorteIntAltro = infisIntPorteIntAltro;
	}

	public BigDecimal getInfisIntPorteIntLegno() {
		return this.infisIntPorteIntLegno;
	}

	public void setInfisIntPorteIntLegno(BigDecimal infisIntPorteIntLegno) {
		this.infisIntPorteIntLegno = infisIntPorteIntLegno;
	}

	public BigDecimal getInfisIntPorteIntLegnoTamb() {
		return this.infisIntPorteIntLegnoTamb;
	}

	public void setInfisIntPorteIntLegnoTamb(BigDecimal infisIntPorteIntLegnoTamb) {
		this.infisIntPorteIntLegnoTamb = infisIntPorteIntLegnoTamb;
	}

	public BigDecimal getInfisIntPorteIntMetallo() {
		return this.infisIntPorteIntMetallo;
	}

	public void setInfisIntPorteIntMetallo(BigDecimal infisIntPorteIntMetallo) {
		this.infisIntPorteIntMetallo = infisIntPorteIntMetallo;
	}

	public DocfaInPartePK getId() {
		return id;
	}

	public void setId(DocfaInPartePK id) {
		this.id = id;
	}

}