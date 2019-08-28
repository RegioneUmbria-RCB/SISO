package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CS_TB_STATO_CIVILE database table.
 * 
 */
@Entity
@Table(name="CS_TB_STATO_CIVILE")
@NamedQuery(name="CsTbStatoCivile.findAll", query="SELECT c FROM CsTbStatoCivile c")
public class CsTbStatoCivile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_STATO_CIVILE_COD_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_STATO_CIVILE_COD_GENERATOR")
	private String cod;

	private String abilitato;

	private String descrizione;

/*	@Column(name="ID_ORIG_CET")
	private String idOrigCet;
*/
	private String tooltip;

	public CsTbStatoCivile() {
	}

	public String getCod() {
		return this.cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
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
}