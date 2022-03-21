package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the CS_D_COLLOQUIO database table.
 * 
 */
@Entity
@Table(name="CS_D_COLLOQUIO")
public class CsDColloquioBASIC implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DIARIO_ID")
	private Long diarioId;

	//bi-directional many-to-one association to CsDRelazione
	@ManyToOne
	@JoinColumn(name="DIARIO_CONCHI_ID")
	private CsCDiarioConchi csCDiarioConchi;

	//bi-directional many-to-one association to CsCDiarioDove
	@ManyToOne
	@JoinColumn(name="DIARIO_DOVE_ID")
	private CsCDiarioDove csCDiarioDove;
	
	//bi-directional many-to-one association to CsCDiarioDove
	@ManyToOne
	@JoinColumn(name="TIPO_COLLOQUIO_ID")
	private CsCTipoColloquio csCTipoColloquio;
	
	@Column(name="TESTO_DIARIO")
	private String testoDiario;

	@Column(name="RISERVATO")
	private Boolean riservato;
	
	//bi-directional one-to-one association to CsDDiario
	@OneToOne
	@JoinColumn(name="DIARIO_ID" )
	private CsDDiarioBASIC csDDiario;
	
	@Column(name="DIARIO_CONCHI_ALTRO")
	private String diarioConChiAltro;
	
	public CsDColloquioBASIC() {
	}

	public CsCTipoColloquio getCsCTipoColloquio() {
		return csCTipoColloquio;
	}

	public void setCsCTipoColloquio(CsCTipoColloquio csCTipoColloquio) {
		this.csCTipoColloquio = csCTipoColloquio;
	}

	public CsCDiarioDove getCsCDiarioDove() {
		return this.csCDiarioDove;
	}

	public void setCsCDiarioDove(CsCDiarioDove csCDiarioDove) {
		this.csCDiarioDove = csCDiarioDove;
	}
	
	public Long getDiarioId() {
		return this.diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public CsCDiarioConchi getCsCDiarioConchi() {
		return this.csCDiarioConchi;
	}

	public void setCsCDiarioConchi(CsCDiarioConchi csCDiarioConchi) {
		this.csCDiarioConchi = csCDiarioConchi;
	}

	public String getTestoDiario() {
		return this.testoDiario;
	}

	public void setTestoDiario(String testoDiario) {
		this.testoDiario = testoDiario;
	}

	public CsDDiarioBASIC getCsDDiario() {
		return csDDiario = (csDDiario==null) ? new CsDDiarioBASIC() : csDDiario;
	}

	public void setCsDDiario(CsDDiarioBASIC csDDiario) {
		this.csDDiario = csDDiario;
	}
	
	public Boolean getRiservato() {
		return riservato;
	}

	public void setRiservato(Boolean riservato) {
		this.riservato = riservato;
	}

	public String getDiarioConChiAltro() {
		return diarioConChiAltro;
	}

	public void setDiarioConChiAltro(String diarioConChiAltro) {
		this.diarioConChiAltro = diarioConChiAltro;
	}
}