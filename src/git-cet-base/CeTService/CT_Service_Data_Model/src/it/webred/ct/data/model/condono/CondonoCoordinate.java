package it.webred.ct.data.model.condono;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the MI_CONDONO_COORDINATE database table.
 * 
 */
@Entity
@Table(name="MI_CONDONO_COORDINATE")
public class CondonoCoordinate implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CondonoCoordinatePK id;

	public void setId(CondonoCoordinatePK id) {
		this.id = id;
	}

	public CondonoCoordinatePK getId() {
		return id;
	}
}