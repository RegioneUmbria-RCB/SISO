package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CS_TB_ICD9 database table.
 * 
 */
@Entity
@Table(name="CS_TB_ICD9")
@NamedQuery(name="CsTbIcd9.findAll", query="SELECT c FROM CsTbIcd9 c")
public class CsTbIcd9 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_ICD9_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_ICD9_ID_GENERATOR")
	private long id;

	private String abilitato;

	@Column(name="COD_INIZIALE")
	private String codIniziale;

	private String codice;

	private String descrizione;

	private String tooltip;

	public CsTbIcd9() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public String getCodIniziale() {
		return this.codIniziale;
	}

	public void setCodIniziale(String codIniziale) {
		this.codIniziale = codIniziale;
	}

	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
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