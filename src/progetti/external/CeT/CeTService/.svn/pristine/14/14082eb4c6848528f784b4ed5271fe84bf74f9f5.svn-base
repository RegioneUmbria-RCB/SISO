package it.webred.ct.data.model.redditi;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RED_REDDITI_DICHIARATI database table.
 * 
 */
@Entity
@Table(name="RED_REDDITI_DICHIARATI")
public class RedRedditiDichiarati implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RedRedditiDichiaratiPK id;
		
	@Column(name="VALORE_CONTABILE")
	private String valoreContabile;
	
    public RedRedditiDichiarati() {
    }

	public String getValoreContabile() {
		return this.valoreContabile;
	}

	public void setValoreContabile(String valoreContabile) {
		this.valoreContabile = valoreContabile;
	}

	public RedRedditiDichiaratiPK getId() {
		return id;
	}

	public void setId(RedRedditiDichiaratiPK id) {
		this.id = id;
	}

}