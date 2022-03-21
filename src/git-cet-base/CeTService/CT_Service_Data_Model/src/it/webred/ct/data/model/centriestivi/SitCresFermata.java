package it.webred.ct.data.model.centriestivi;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SIT_CRES_FERMATA database table.
 * 
 */
@Entity
@Table(name="SIT_CRES_FERMATA")
public class SitCresFermata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_CRES_FERMATA")
	private String idCresFermata;

	private String descrizione;

    public SitCresFermata() {
    }

	public String getIdCresFermata() {
		return this.idCresFermata;
	}

	public void setIdCresFermata(String idCresFermata) {
		this.idCresFermata = idCresFermata;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}