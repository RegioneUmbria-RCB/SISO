package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CS_C_TIPO_INTERVENTO_CUSTOM database table.
 * 
 */
@Entity
@Table(name="CS_C_TIPO_INTERVENTO_CUSTOM")
@NamedQuery(name="CsCTipoInterventoCustom.findAll", query="SELECT ic FROM CsCTipoInterventoCustom ic")
public class CsCTipoInterventoCustom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_C_TIPO_INTERVENTO_CUSTOM_ID_GENERATOR", sequenceName="SQ_ID_ARGO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_C_TIPO_INTERVENTO_CUSTOM_ID_GENERATOR")
	private Long id;

	private char abilitato;

	private String descrizione;

	private String tooltip;

	
	
	public CsCTipoInterventoCustom() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public char getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(char abilitato) {
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