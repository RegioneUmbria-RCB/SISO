package it.webred.ct.data.model.versamenti.iciDM;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SIT_T_ICI_DM_DECODE database table.
 * 
 */
@Entity
@Table(name="SIT_T_ICI_DM_DECODE")
public class SitTIciDmDecode implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SitTIciDmDecodePK id;

	private String descrizione;

	public SitTIciDmDecodePK getId() {
		return id;
	}

	public void setId(SitTIciDmDecodePK id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


}