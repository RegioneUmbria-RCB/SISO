package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CS_PAI_FASE_CHIUSURA database table.
 * 
 */
@Entity
@Table(name="CS_PAI_FASE_CHIUSURA")
@NamedQuery(name="CsPaiFaseChiusura.findAll", query="SELECT c FROM CsPaiFaseChiusura c")
public class CsPaiFaseChiusura implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsPaiFaseChiusuraPK id;

	public CsPaiFaseChiusura() {
	}

	public CsPaiFaseChiusuraPK getId() {
		return this.id;
	}

	public void setId(CsPaiFaseChiusuraPK id) {
		this.id = id;
	}

}

