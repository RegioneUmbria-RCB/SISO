package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="CS_TB_G_VULNERABILE")
@NamedQuery(name="CsTbGVulnearabile.findAll", query="SELECT c FROM CsTbGVulnerabile c")
public class CsTbGVulnerabile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String abilitato;

	private String descrizione;

	private String tooltip;
	
	@Column(name="DATO_SENSIBILE")
	private String datoSensibile;
	
	@Column(name="COD_CLASSE")
	private String codClasse;
	
	public CsTbGVulnerabile() {
	}

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
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

	public String getDatoSensibile() {
		return datoSensibile;
	}

	public void setDatoSensibile(String datoSensibile) {
		this.datoSensibile = datoSensibile;
	}


	public String getCodClasse() {
		return codClasse;
	}


	public void setCodClasse(String codClasse) {
		this.codClasse = codClasse;
	}
}