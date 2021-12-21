package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the CS_C_CATEGORIA_SOCIALE database table.
 * 
 */
@Entity
@Table(name="CS_C_CATEGORIA_SOCIALE")
@NamedQuery(name="CsCCategoriaSociale.findAll", query="SELECT c FROM CsCCategoriaSociale c order by c.nOrd, c.id")
public class CsCCategoriaSociale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_C_CATEGORIA_SOCIALE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_C_CATEGORIA_SOCIALE_ID_GENERATOR")
	private long id;

	private Boolean abilitato;

	private String descrizione;

	private String tooltip;
	
	@Column(name="N_ORD")
	private Integer nOrd;

	public CsCCategoriaSociale() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
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