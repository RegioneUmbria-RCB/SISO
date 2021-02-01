package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CS_TB_TIPO_PROGETTO database table.
 * 
 */
@Entity
@Table(name="CS_TB_TIPO_PROGETTO")
@NamedQuery(name="CsTbTipoProgetto.findAll", query="SELECT c FROM CsTbTipoProgetto c")
public class CsTbTipoProgetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_TIPO_PROGETTO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_TIPO_PROGETTO_ID_GENERATOR")
	private long id;

	private boolean abilitato;

	private String descrizione;

	private String tooltip;
	
	private Long ufficio;

	public CsTbTipoProgetto() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isAbilitato() {
		return abilitato;
	}

	public void setAbilitato(boolean abilitato) {
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

	public Long getUfficio() {
		return ufficio;
	}

	public void setUfficio(Long ufficio) {
		this.ufficio = ufficio;
	}
	
}