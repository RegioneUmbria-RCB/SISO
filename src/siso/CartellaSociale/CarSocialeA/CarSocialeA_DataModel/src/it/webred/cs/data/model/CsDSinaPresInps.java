package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CS_D_SINA_PRES_INPS")
public class CsDSinaPresInps implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private CsDSinaPresInpsPK id;
	
	@ManyToOne
	@JoinColumn(name = "PRESTAZIONE_INPS_ID", insertable = false, updatable = false)
	private ArTbPrestazioniInps arTbPrestazioniInps;

	
	public CsDSinaPresInpsPK getId() {
		return id;
	}

	public void setId(CsDSinaPresInpsPK id) {
		this.id = id;
	}

	public ArTbPrestazioniInps getArTbPrestazioniInps() {
		return arTbPrestazioniInps;
	}

	public void setArTbPrestazioniInps(ArTbPrestazioniInps arTbPrestazioniInps) {
		this.arTbPrestazioniInps = arTbPrestazioniInps;
	}

}
