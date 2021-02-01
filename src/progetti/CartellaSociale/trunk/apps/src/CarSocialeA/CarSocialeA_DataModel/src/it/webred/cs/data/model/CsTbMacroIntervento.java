package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CS_TB_MACRO_INTERVENTO database table.
 * 
 */
@Entity
@Table(name="CS_TB_MACRO_INTERVENTO")
@NamedQuery(name="CsTbMacroIntervento.findAll", query="SELECT c FROM CsTbMacroIntervento c")
public class CsTbMacroIntervento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String pdsmacro;
	
	private Boolean abilitato;
	
	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	public CsTbMacroIntervento(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPdsmacro() {
		return pdsmacro;
	}

	public void setPdsmacro(String pdsmacro) {
		this.pdsmacro = pdsmacro;
	}
	
		
}
