package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name="CS_TB_DURATA_RIC_LAVORO")
@NamedQuery(name="CsTbDurataRicLavoro.findAll", query="SELECT c FROM CsTbDurataRicLavoro c")
public class CsTbDurataRicLavoro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_DURATA_RIC_LAVORO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_DURATA_RIC_LAVORO_ID_GENERATOR")
	private long id;
	
	private Boolean abilitato;

	private String descrizione;

	private String tooltip;

	public CsTbDurataRicLavoro() {
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

}