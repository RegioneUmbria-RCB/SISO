package it.webred.ss.data.model;

 
import java.io.Serializable;

import javax.persistence.*;

import java.util.List;
import java.util.Set;


/**
 * The persistent class for the CS_C_CATEGORIA_SOCIALE database table.
 * 
 */
@Entity
@Table(name="CS_C_CATEGORIA_SOCIALE")
@NamedQuery(name="SsCCategoriaSociale.findAll", query="SELECT c FROM SsCCategoriaSociale c order by c.nOrd, c.id")
public class SsCCategoriaSociale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_C_CATEGORIA_SOCIALE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_C_CATEGORIA_SOCIALE_ID_GENERATOR")
	private long id;

	private String abilitato;

	private String descrizione;

	private String tooltip;
	
	@Column(name="N_ORD")
	private Integer nOrd;

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SsCCategoriaSociale)) {
			return false;
		}
		SsCCategoriaSociale castOther = (SsCCategoriaSociale)other;
		return 
			this.id == castOther.id;
	}

	public SsCCategoriaSociale() {
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

	public Integer getnOrd() {
		return nOrd;
	}

	public void setnOrd(Integer nOrd) {
		this.nOrd = nOrd;
	}
	
}