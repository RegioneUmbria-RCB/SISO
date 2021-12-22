package it.umbriadigitale.soclav.model.anpal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="RDC_TB_CPI_COMPETENZA")
public class RdCTbCpiCompetenza implements Serializable {
	
	@EmbeddedId
	private RdCTbCpiCompetenzaPK id;
		
	@Column(name="CPI_SEDE_COMUNE")
	private String cpiSedeComune;
	
	@Column(name="CPI_SEDE_PROV")
	private String cpiSedeProv;
	
	@Column(name="CPI_SEDE_REGIONE")
	private String cpiSedeRegione;
		
	@Column(name="COMUNE_DES")
	private String comuneDes;
	
	@Column(name="COMUNE_COD_ISTAT")
	private String comuneCodIstat;
	
	@OneToOne
	@JoinColumn(name ="COD_CPI", insertable = false, updatable = false)
	private RdCTbCpi cpi;

	public String getCpiSedeComune() {
		return cpiSedeComune;
	}

	public void setCpiSedeComune(String cpiSedeComune) {
		this.cpiSedeComune = cpiSedeComune;
	}

	public String getCpiSedeProv() {
		return cpiSedeProv;
	}

	public void setCpiSedeProv(String cpiSedeProv) {
		this.cpiSedeProv = cpiSedeProv;
	}

	public String getCpiSedeRegione() {
		return cpiSedeRegione;
	}

	public void setCpiSedeRegione(String cpiSedeRegione) {
		this.cpiSedeRegione = cpiSedeRegione;
	}

	public String getComuneDes() {
		return comuneDes;
	}

	public void setComuneDes(String comuneDes) {
		this.comuneDes = comuneDes;
	}

	public String getComuneCodIstat() {
		return comuneCodIstat;
	}

	public void setComuneCodIstat(String comuneCodIstat) {
		this.comuneCodIstat = comuneCodIstat;
	}

	public RdCTbCpi getCpi() {
		return cpi;
	}

	public void setCpi(RdCTbCpi cpi) {
		this.cpi = cpi;
	}

	public RdCTbCpiCompetenzaPK getId() {
		return id;
	}

	public void setId(RdCTbCpiCompetenzaPK id) {
		this.id = id;
	}
}
