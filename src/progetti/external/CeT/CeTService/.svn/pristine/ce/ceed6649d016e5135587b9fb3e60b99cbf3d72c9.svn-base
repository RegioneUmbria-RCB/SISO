package it.webred.ct.data.model.processi;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SIT_SINTESI_PROCESSI database table.
 * 
 */
@Entity
@Table(name="SIT_SINTESI_PROCESSI")
public class SitSintesiProcessi implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SitSintesiProcessiPK id;

	private BigDecimal aggiornati;

	private BigDecimal inseriti;

	private BigDecimal sostituiti;

    public SitSintesiProcessi() {
    }

	public SitSintesiProcessiPK getId() {
		return this.id;
	}

	public void setId(SitSintesiProcessiPK id) {
		this.id = id;
	}
	
	public BigDecimal getAggiornati() {
		return this.aggiornati;
	}

	public void setAggiornati(BigDecimal aggiornati) {
		this.aggiornati = aggiornati;
	}

	public BigDecimal getInseriti() {
		return this.inseriti;
	}

	public void setInseriti(BigDecimal inseriti) {
		this.inseriti = inseriti;
	}

	public BigDecimal getSostituiti() {
		return this.sostituiti;
	}

	public void setSostituiti(BigDecimal sostituiti) {
		this.sostituiti = sostituiti;
	}

}