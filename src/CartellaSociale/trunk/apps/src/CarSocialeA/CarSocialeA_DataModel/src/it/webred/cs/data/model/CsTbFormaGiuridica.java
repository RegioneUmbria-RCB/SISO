package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the CS_TB_MACRO_ATTIVITA database table.
 * 
 */
@Entity
@Table(name="CS_TB_FORMA_GIURIDICA")
@NamedQuery(name="CsTbFormaGiuridica.findAll", query="SELECT c FROM CsTbFormaGiuridica c")
public class CsTbFormaGiuridica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private Boolean abilitato;

	private String descrizione;

	private String tooltip;

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Boolean getAbilitato() {
		return abilitato;
	}


	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getTooltip() {
		return tooltip;
	}


	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}


	@Override
	public boolean equals(Object other) {	
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof CsTbFormaGiuridica))return false;
	    CsTbFormaGiuridica otherMyClass = (CsTbFormaGiuridica)other;
	    
	    return (id==otherMyClass.id);			
	}

}