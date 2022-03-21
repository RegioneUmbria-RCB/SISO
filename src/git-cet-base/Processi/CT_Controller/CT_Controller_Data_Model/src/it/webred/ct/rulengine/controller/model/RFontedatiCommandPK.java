package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the R_FONTEDATI_COMMAND database table.
 * 
 */
@Embeddable
public class RFontedatiCommandPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name="FK_COMMAND")
	private Long fkCommand;

	@Column(name="ID_FONTE")
	private Long idFonte;

	public RFontedatiCommandPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getFkCommand() {
		return fkCommand;
	}

	public void setFkCommand(Long fkCommand) {
		this.fkCommand = fkCommand;
	}

	public Long getIdFonte() {
		return idFonte;
	}

	public void setIdFonte(Long idFonte) {
		this.idFonte = idFonte;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RFontedatiCommandPK)) {
			return false;
		}
		RFontedatiCommandPK castOther = (RFontedatiCommandPK)other;
		return 
			this.fkCommand.equals(castOther.fkCommand)
			&& (this.idFonte == castOther.idFonte);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fkCommand.hashCode();
		hash = hash * prime + ((int) (this.idFonte ^ (this.idFonte >>> 32)));
		
		return hash;
    }
}
