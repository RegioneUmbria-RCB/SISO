package it.webred.ss.data.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the SS_TIPO_SCHEDA database table.
 * 
 */
@Entity
@Table(name="SS_TIPO_SCHEDA")
@NamedQuery(name="SsTipoScheda.findAll", query="SELECT c FROM SsTipoScheda c Where c.abilitato = true")
public class SsTipoScheda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String tipo;
	
	private String tooltip;
	
	private Boolean solo_comune;
	private Boolean presa_in_carico;
	
	private Boolean abilitato;
	
	public Boolean getSolo_comune() {
		return solo_comune;
	}

	public void setSolo_comune(Boolean solo_comune) {
		this.solo_comune = solo_comune;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getPresa_in_carico() {
		return presa_in_carico;
	}

	public void setPresa_in_carico(Boolean presa_in_carico) {
		this.presa_in_carico = presa_in_carico;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}	
	
	
	public Boolean getAbilitato() {
		return abilitato;
	}
	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}
}