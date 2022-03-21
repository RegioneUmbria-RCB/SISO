package it.webred.ct.data.model.traffico;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SIT_TRFF_MULTE_STATO database table.
 * 
 */
@Entity
@Table(name="SIT_TRFF_MULTE_STATO")
public class SitTrffMulteStato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String descr;

	private String stato;

    public SitTrffMulteStato() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

}