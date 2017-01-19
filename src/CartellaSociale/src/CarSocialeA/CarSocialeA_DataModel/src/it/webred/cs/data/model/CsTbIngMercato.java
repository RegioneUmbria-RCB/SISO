package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="CS_TB_ING_MERCATO")
@NamedQuery(name="CsTbIngMercato.findAll", query="SELECT c FROM CsTbIngMercato c")
public class CsTbIngMercato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String abilitato;

	private String descrizione;

	private String tooltip;
	
	public CsTbIngMercato() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(String abilitato) {
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

}