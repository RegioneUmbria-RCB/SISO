package it.umbriadigitale.argo.data.cs.data;

// Generated 26-ott-2015 13.12.17 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ArFfTbOrigine generated by hbm2java
 */
@Entity
@Table(name = "AR_FF_TB_ORIGINE")
public class ArFfTbOrigine implements java.io.Serializable {

	@Id
	private long id;
	private String descrizione;
	private String tooltip;
	private Character abilitato;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arFfTbOrigine")
	private Set<ArFfFondo> arFfFondos = new HashSet<ArFfFondo>(0);

	public ArFfTbOrigine() {
	}

	public ArFfTbOrigine(long id) {
		this.id = id;
	}

	public ArFfTbOrigine(long id, String descrizione, String tooltip,
			Character abilitato, Set<ArFfFondo> arFfFondos) {
		this.id = id;
		this.descrizione = descrizione;
		this.tooltip = tooltip;
		this.abilitato = abilitato;
		this.arFfFondos = arFfFondos;
	}


	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Character getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(Character abilitato) {
		this.abilitato = abilitato;
	}

	public Set<ArFfFondo> getArFfFondos() {
		return this.arFfFondos;
	}

	public void setArFfFondos(Set<ArFfFondo> arFfFondos) {
		this.arFfFondos = arFfFondos;
	}

}
