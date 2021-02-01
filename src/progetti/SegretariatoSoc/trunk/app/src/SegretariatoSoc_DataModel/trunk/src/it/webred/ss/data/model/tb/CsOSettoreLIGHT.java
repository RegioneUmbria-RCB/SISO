package it.webred.ss.data.model.tb;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CS_O_SETTORE database table.
 * 
 */
@Entity
@Table(name="CS_O_SETTORE")
public class CsOSettoreLIGHT implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Boolean abilitato;

	private String cell;

	private String email;

	private String fax;

	private String nome;
		
	private String nome2;
	
	private String tel;

	private String tooltip;
	
	private String orario;
	
	@Basic
	@Column(name= "ORGANIZZAZIONE_ID", insertable=false, updatable=false)
	protected Long csOOrganizzazioneId;
	

	public Long getCsOOrganizzazioneId() {
		return csOOrganizzazioneId;
	}

	public void setCsOOrganizzazioneId(Long csOOrganizzazioneId) {
		this.csOOrganizzazioneId = csOOrganizzazioneId;
	}

	public CsOSettoreLIGHT() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getCell() {
		return this.cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getNome2() {
		return nome2;
	}

	public void setNome2(String nome2) {
		this.nome2 = nome2;
	}

	public String getOrario() {
		return orario;
	}

	public void setOrario(String orario) {
		this.orario = orario;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}
	
}