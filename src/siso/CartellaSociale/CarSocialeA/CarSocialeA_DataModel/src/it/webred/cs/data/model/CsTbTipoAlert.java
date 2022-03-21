package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CS_TB_TIPO_DIARIO database table.
 * 
 */
@Entity
@Table(name="CS_TB_TIPO_ALERT")
@NamedQuery(name="CsTbTipoAlert.findAll", query="SELECT c FROM CsTbTipoAlert c")
public class CsTbTipoAlert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String abilitato;

	private String descrizione;

	private String tooltip;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public String getAbilitato() {
		return abilitato;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
}