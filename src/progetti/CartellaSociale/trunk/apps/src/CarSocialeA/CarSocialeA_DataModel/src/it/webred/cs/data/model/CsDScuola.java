package it.webred.cs.data.model;

import it.webred.cs.data.base.ICsDDiarioChild;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the CS_D_SCUOLA database table.
 * 
 */
@Entity
@Table(name="CS_D_SCUOLA")
@NamedQuery(name="CsDScuola.findAll", query="SELECT c FROM CsDScuola c")
public class CsDScuola implements ICsDDiarioChild {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DIARIO_ID")
	private Long diarioId;

	@Column(name="ANNO_ID")
	private Long annoScolasticoId;
	
	@Column(name="TIPO_SCUOLA_ID")
	private Long tipoScuolaId;

	@Column(name="SCUOLA_ID")
	private String scuolaId;

	private Boolean fermo;

	private String grado;

	private String nome;

	private String progetto;
	
	private String note;

	@ManyToOne
	@JoinColumn(name="TIPO_SCUOLA_ID", insertable=false, updatable=false)
	private CsTbTipoScuola csTbTipoScuola;
	
	@ManyToOne
	@JoinColumn(name="ANNO_ID", insertable=false, updatable=false)
	private CsTbAnnoScolastico csTbAnnoScolastico;

	@ManyToOne
	@JoinColumn(name="SCUOLA_ID", insertable=false, updatable=false)
	private CsTbScuola csTbScuola;
	
	//bi-directional one-to-one association to CsDDiario
	@OneToOne
	@JoinColumn(name="DIARIO_ID")
	private CsDDiario csDDiario;
	
	public CsDScuola() {
	}

	public Boolean getFermo() {
		return this.fermo;
	}

	public void setFermo(Boolean fermo) {
		this.fermo = fermo;
	}

	public String getGrado() {
		return this.grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProgetto() {
		return progetto;
	}

	public void setProgetto(String progetto) {
		this.progetto = progetto;
	}

	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public CsTbTipoScuola getCsTbTipoScuola() {
		return csTbTipoScuola;
	}

	public void setCsTbTipoScuola(CsTbTipoScuola csTbTipoScuola) {
		this.csTbTipoScuola = csTbTipoScuola;
	}

	public CsTbScuola getCsTbScuola() {
		return csTbScuola;
	}

	public void setCsTbScuola(CsTbScuola csTbScuola) {
		this.csTbScuola = csTbScuola;
	}

	public CsDDiario getCsDDiario() {
		return csDDiario = (csDDiario==null) ? new CsDDiario() : csDDiario;
	}

	public void setCsDDiario(CsDDiario csDDiario) {
		this.csDDiario = csDDiario;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getAnnoScolasticoId() {
		return annoScolasticoId;
	}

	public void setAnnoScolasticoId(Long annoScolasticoId) {
		this.annoScolasticoId = annoScolasticoId;
	}

	public Long getTipoScuolaId() {
		return tipoScuolaId;
	}

	public void setTipoScuolaId(Long tipoScuolaId) {
		this.tipoScuolaId = tipoScuolaId;
	}

	public String getScuolaId() {
		return scuolaId;
	}

	public void setScuolaId(String scuolaId) {
		this.scuolaId = scuolaId;
	}

	public CsTbAnnoScolastico getCsTbAnnoScolastico() {
		return csTbAnnoScolastico;
	}

	public void setCsTbAnnoScolastico(CsTbAnnoScolastico csTbAnnoScolastico) {
		this.csTbAnnoScolastico = csTbAnnoScolastico;
	}
	
}