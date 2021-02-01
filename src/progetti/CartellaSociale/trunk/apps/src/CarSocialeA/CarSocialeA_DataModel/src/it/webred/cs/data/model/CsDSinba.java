package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the CS_D_VALUTAZIONE database table.
 * 
 */
@Entity
@Table(name = "CS_D_SINBA")
@NamedQuery(name = "CsDSinba.findAll", query = "SELECT c FROM CsDSinba c")
public class CsDSinba implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="DIARIO_ID")
	private Long diarioId;
	
	// bi-directional many-to-one association to csDExportSinba
	@ManyToOne
	@JoinColumn(name = "FILE_EXPORT_ID")
	private CsDExportSinba csDExportSinba;
	
	@OneToOne
	@JoinColumn(name = "DIARIO_ID")
	private CsDValutazione csDValutazione;
	
	@Column(name = "DATA_EXPORT")
	private Date dataExport;

	public CsDSinba() {
	}

	public CsDExportSinba getCsDExportSinba() {
		return csDExportSinba;
	}

	public void setCsDExportSinba(CsDExportSinba csDExportSinba) {
		this.csDExportSinba = csDExportSinba;
	}

	public Date getDataExport() {
		return dataExport;
	}

	public void setDataExport(Date dataExport) {
		this.dataExport = dataExport;
	}

	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public CsDValutazione getCsDValutazione() {
		return csDValutazione;
	}

	public void setCsDValutazione(CsDValutazione csDValutazione) {
		this.csDValutazione = csDValutazione;
	}
	
	public CsDDiario getCsDDiario(){
		return this.csDValutazione.getCsDDiario();
	}
}