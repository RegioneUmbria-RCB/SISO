package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the CS_TB_TIPO_PAI database table.
 */
@Entity
@Table(name = "CS_TB_TIPO_PAI")
@NamedQuery(name = "CsTbTipoPai.findAll", query = "SELECT c FROM CsTbTipoPai c")
public class CsTbTipoPai implements Serializable {
	private static final long serialVersionUID = -7832348981104285862L;

	@Id
	@SequenceGenerator(name = "CS_TB_TIPO_PAI_ID_GENERATOR", sequenceName = "SQ_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_TB_TIPO_PAI_ID_GENERATOR")
	private long id;

	private Boolean abilitato;

	private String descrizione;

	private String tooltip;
	
	private String progetto;

	public CsTbTipoPai() {
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

/*	@Override
	public String toString() {
		return descrizione;
	}*/
	
	
	@Override
	public boolean equals(Object obj) {
		return (obj!=null && obj instanceof CsTbTipoPai) && id==((CsTbTipoPai)obj).getId();
	}

	public String getProgetto() {
		return progetto;
	}

	public void setProgetto(String progetto) {
		this.progetto = progetto;
	}
}