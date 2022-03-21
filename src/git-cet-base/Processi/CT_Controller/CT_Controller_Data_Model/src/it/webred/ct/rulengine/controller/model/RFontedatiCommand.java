package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the R_FONTEDATI_COMMAND database table.
 * 
 */
@Entity
@Table(name="R_FONTEDATI_COMMAND")
public class RFontedatiCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RFontedatiCommandPK id;

	
    public RFontedatiCommand() {
    }


	public RFontedatiCommandPK getId() {
		return id;
	}


	public void setId(RFontedatiCommandPK id) {
		this.id = id;
	}



	
}