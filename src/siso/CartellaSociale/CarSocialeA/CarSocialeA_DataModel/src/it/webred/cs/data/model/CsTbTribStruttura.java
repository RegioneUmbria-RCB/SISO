package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="CS_TB_TRIB_STRUTTURA")
@NamedQuery(name="CsTbTribStruttura.findAll", query="SELECT c FROM CsTbTribStruttura c")
public class CsTbTribStruttura implements Serializable {
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
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}