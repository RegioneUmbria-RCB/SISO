package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the CS_TB_MICRO_INTERVENTO database table.
 * 
 */
@Entity
@Table(name="CS_TB_MICRO_INTERVENTO")
@NamedQuery(name="CsTbMicroIntervento.findAll", query="SELECT c FROM CsTbMicroIntervento c")
public class CsTbMicroIntervento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String pdsMicro;
	
	private Boolean abilitato;
	
	@Column(name="ID_PDSMACRO")
	private Long idMacro;

	public CsTbMicroIntervento() {
	}

	public String getPdsMicro() {
		return this.pdsMicro;
	}

	public void setPdsMicro(String pdsMicro) {
		this.pdsMicro = pdsMicro;
	}
	
	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdMacro() {
		return idMacro;
	}

	public void setIdMacro(Long idMacro) {
		this.idMacro = idMacro;
	}

}