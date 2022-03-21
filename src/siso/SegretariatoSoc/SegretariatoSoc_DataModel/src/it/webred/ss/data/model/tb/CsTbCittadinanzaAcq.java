package it.webred.ss.data.model.tb;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CS_TB_PERMESSO database table.
 * 
 */
@Entity
@Table(name="CS_TB_CITTADINANZA_ACQ")
@NamedQuery(name="CsTbCittadinanzaAcq.findAll", query="SELECT c FROM CsTbCittadinanzaAcq c")
public class CsTbCittadinanzaAcq implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private Boolean abilitato;

	private String descrizione;

	private String tooltip;

	public CsTbCittadinanzaAcq() {
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