package it.webred.ct.data.model.f24;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="SIT_T_F24_COD_SOGG")
public class SitTF24CodSogg implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SitTF24CodSoggPK id;

	private String descrizione;

	public SitTF24CodSogg() {
	}

	public SitTF24CodSoggPK getId() {
		return this.id;
	}

	public void setId(SitTF24CodSoggPK id) {
		this.id = id;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}