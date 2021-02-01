package it.webred.ct.data.model.diagnostiche;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DOCFA_ANOMALIE database table.
 * 
 */
@Entity
@Table(name="DOCFA_ANOMALIE")
public class DocfaAnomalie implements Serializable {
	private static final long serialVersionUID = 1L;

	private String descrizione;

	@Id
	private String id;
	
	private String tipo;

    public DocfaAnomalie() {
    }

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}