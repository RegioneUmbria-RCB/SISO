package it.webred.ct.data.model.traffico;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SIT_TRFF_MULTE_TIPO database table.
 * 
 */
@Entity
@Table(name="SIT_TRFF_MULTE_TIPO")
public class SitTrffMulteTipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String tipo;

    public SitTrffMulteTipo() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}