package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="CS_TB_SS_PROVENIENZA")
@NamedQuery(name="CsTbSsProvenienza.findAll", query="SELECT c FROM CsTbSsProvenienza c order by to_number(c.abilitato) desc, c.descrizione")
public class CsTbSsProvenienza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private Boolean abilitato;

	private String descrizione;

	private String tooltip;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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