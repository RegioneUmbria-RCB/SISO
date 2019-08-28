package it.webred.ss.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CS_O_ORGANIZZAZIONE database table.
 * 
 */
@Entity
@Table(name="AR_O_ORGANIZZAZIONE")
@NamedQuery(name="ArOOrganizzazione.findAll", query="SELECT c FROM ArOOrganizzazione c")
public class ArOOrganizzazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String abilitato;

	private String email;

	private String nome;

	private String tooltip;
	
	private String belfiore;
	
	private String piva_cf;
	
	private String zona_nome;

	public ArOOrganizzazione() {
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

	public String getPiva_cf() {
		return piva_cf;
	}

	public void setPiva_cf(String piva_cf) {
		this.piva_cf = piva_cf;
	}

	public String getZona_nome() {
		return zona_nome;
	}

	public void setZona_nome(String zona_nome) {
		this.zona_nome = zona_nome;
	}

}