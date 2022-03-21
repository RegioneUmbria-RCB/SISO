package it.webred.ct.data.model.common;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SIT_ENTE database table.
 * 
 */
@Entity
@Table(name="SIT_ENTE")
public class SitEnte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name="COD_ISTAT")
	private String codIstat;

	private String codent;

	private String descrizione;

    public SitEnte() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodIstat() {
		return this.codIstat;
	}

	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}

	public String getCodent() {
		return this.codent;
	}

	public void setCodent(String codent) {
		this.codent = codent;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}