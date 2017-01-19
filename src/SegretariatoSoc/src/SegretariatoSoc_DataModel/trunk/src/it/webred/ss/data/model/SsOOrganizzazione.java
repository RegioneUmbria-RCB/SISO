package it.webred.ss.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CS_O_ORGANIZZAZIONE database table.
 * 
 */
@Entity
@Table(name="CS_O_ORGANIZZAZIONE")
@NamedQuery(name="SsOOrganizzazione.findAll", query="SELECT c FROM SsOOrganizzazione c")
public class SsOOrganizzazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String abilitato;

	private String email;

	private String nome;

	private String tooltip;
	
	private String belfiore;

	public SsOOrganizzazione() {
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getBelfiore() {
		return belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

}