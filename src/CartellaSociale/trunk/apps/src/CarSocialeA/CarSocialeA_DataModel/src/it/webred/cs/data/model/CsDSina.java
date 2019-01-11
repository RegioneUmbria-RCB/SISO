package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CS_D_SINA")
@NamedQuery(name = "CsDSina.findAll", query = "SELECT c FROM CsDSina c")
public class CsDSina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CS_D_SINA_GENERATOR", sequenceName = "SQ_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_D_SINA_GENERATOR")
	private Long id;

	// bi-directional many-to-one association to CsACaso
	@ManyToOne
	@JoinColumn(name = "DIARIO_ID")
	private CsDDiario csDDiario;

	// bi-directional many-to-one association to CsACaso
	@ManyToOne
	@JoinColumn(name = "INTERVENTO_ESEG_MAST_ID")
	private CsIInterventoEsegMast csIInterventoEsegMast;

	// bi-directional one-to-many association to CsRelSinaEseg
	 @OneToMany(mappedBy = "csDSina", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval=true)
	private List<CsDSinaEseg> csDSinaEseg;

	// bi-directional many-to-many association to ArTbPrestazioniInps
	 @ManyToMany
		@JoinTable(
				name="CS_D_SINA_PRES_INPS", joinColumns={@JoinColumn(name="SINA_ID") }, inverseJoinColumns={@JoinColumn(name="PRESTAZIONE_INPS_ID")}
				)
	 private List<ArTbPrestazioniInps> arTbPrestazioniInps;
		
	
	public CsDSina() {
		csDSinaEseg = new ArrayList<CsDSinaEseg>();
		arTbPrestazioniInps = new ArrayList<ArTbPrestazioniInps>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<CsDSinaEseg> getCsDSinaEseg() {
		return csDSinaEseg;
	}

	public void setCsDSinaEseg(List<CsDSinaEseg> csDSinaEseg) {
		this.csDSinaEseg = csDSinaEseg;
	}

	public CsDDiario getCsDDiario() {
		return csDDiario;
	}

	public void setCsDDiario(CsDDiario csDDiario) {
		this.csDDiario = csDDiario;
	}

	public CsIInterventoEsegMast getCsIInterventoEsegMast() {
		return csIInterventoEsegMast;
	}

	public void setCsIInterventoEsegMast(
			CsIInterventoEsegMast csIInterventoEsegMast) {
		this.csIInterventoEsegMast = csIInterventoEsegMast;
	}
	
	public void setArTbPrestazioniInps(List<ArTbPrestazioniInps> arTbPrestazioniInps) {
		this.arTbPrestazioniInps = arTbPrestazioniInps;
	}

	public List<ArTbPrestazioniInps> getArTbPrestazioniInps() {
		return this.arTbPrestazioniInps;
	}

	public void addArTbPrestazioniInps(ArTbPrestazioniInps arTbPrestazioniInps) {
		if (this.arTbPrestazioniInps == null)
			this.arTbPrestazioniInps = new ArrayList<ArTbPrestazioniInps>();

		this.arTbPrestazioniInps.add(arTbPrestazioniInps);
	}
}
