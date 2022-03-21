package it.webred.ct.data.model.c336;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the S_C336_DECOD_IRREG database table.
 * 
 */
@Entity
@Table(name="S_C336_DECOD_IRREG")
public class C336DecodIrreg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COD_IRREG")
	private String codIrreg;

	@Column(name="DES_IRREG")
	private String desIrreg;

	@Column(name="FL_ATTIVO")
	private String flAttivo;

    public C336DecodIrreg() {
    }

	public String getCodIrreg() {
		return this.codIrreg;
	}

	public void setCodIrreg(String codIrreg) {
		this.codIrreg = codIrreg;
	}

	public String getDesIrreg() {
		return this.desIrreg;
	}

	public void setDesIrreg(String desIrreg) {
		this.desIrreg = desIrreg;
	}

	public String getFlAttivo() {
		return this.flAttivo;
	}

	public void setFlAttivo(String flAttivo) {
		this.flAttivo = flAttivo;
	}

}