package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_FONTE database table.
 * 
 */
@Entity
@Table(name="AM_FONTE")
public class AmFonte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String codice;

	private String descrizione;

	@Column(name="N_ORDINE")
	private int nOrdine;

	@Column(name="TIPO_FONTE")
	private String tipoFonte;

    public AmFonte() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getNOrdine() {
		return this.nOrdine;
	}

	public void setNOrdine(int nOrdine) {
		this.nOrdine = nOrdine;
	}

	public String getTipoFonte() {
		return this.tipoFonte;
	}

	public void setTipoFonte(String tipoFonte) {
		this.tipoFonte = tipoFonte;
	}

}