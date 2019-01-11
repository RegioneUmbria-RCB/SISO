package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the CS_C_DIARIO_CONCHI database table.
 * 
 */
@Entity
@Table(name="CS_C_DIARIO_CONCHI")
@NamedQuery(name="CsCDiarioConchi.findAll", query="SELECT c FROM CsCDiarioConchi c")
public class CsCDiarioConchi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_C_DIARIO_CONCHI_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_C_DIARIO_CONCHI_ID_GENERATOR")
	private long id;

	private String abilitato;

	private String descrizione;

	private String tooltip;

//	// bi-directional many-to-many association to CsDRelazione
	/*	@ManyToMany(mappedBy = "lstConChi", fetch=FetchType.LAZY)
	 
		private List<CsDRelazione> csDRelazione;*/
	
	public CsCDiarioConchi() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	/*
	public List<CsDRelazione> getCsDRelazione() {
		return csDRelazione;
	}

	public void setCsDRelazione(List<CsDRelazione> csDRelazione) {
		this.csDRelazione = csDRelazione;
	}*/

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsCDiarioConchi)) {
			return false;
		}
		CsCDiarioConchi castOther = (CsCDiarioConchi)other;
		return 
			this.id == castOther.id;
	}
	
}