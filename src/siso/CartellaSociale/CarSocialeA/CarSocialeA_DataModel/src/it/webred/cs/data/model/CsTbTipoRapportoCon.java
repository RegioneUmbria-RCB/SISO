package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CS_TB_TIPO_RAPPORTO_CON database table.
 * 
 */
@Entity
@Table(name="CS_TB_TIPO_RAPPORTO_CON")
@NamedQuery(name="CsTbTipoRapportoCon.findAll", query="SELECT c FROM CsTbTipoRapportoCon c")
public class CsTbTipoRapportoCon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_TIPO_RAPPORTO_CON_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_TIPO_RAPPORTO_CON_ID_GENERATOR")
	private Long id;

	private String abilitato;

	private String descrizione;

	private String tooltip;
	
	private Boolean parente;

	public CsTbTipoRapportoCon() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	public Boolean getParente() {
		return parente;
	}

	public void setParente(Boolean parente) {
		this.parente = parente;
	}

}