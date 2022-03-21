package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CS_TB_PROFESSIONE database table.
 * 
 */
@Entity
@Table(name="CS_TB_PROFESSIONE")
@NamedQuery(name="CsTbProfessione.findAll", query="SELECT c FROM CsTbProfessione c")
public class CsTbProfessione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_PROFESSIONE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_PROFESSIONE_ID_GENERATOR")
	private long id;

	private String abilitato;

	private String descrizione;

	private String tooltip;
	
	@Column(name="COD_CLASSE")
	private String codClasse;
	
	public CsTbProfessione() {
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

	public String getCodClasse() {
		return codClasse;
	}

	public void setCodClasse(String codClasse) {
		this.codClasse = codClasse;
	}
}