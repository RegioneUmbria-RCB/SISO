package it.webred.ct.config.model;


import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_TAB_NAZIONI database table.
 * 
 */
@Entity
@Table(name="AM_TAB_REGIONI")
public class AmTabRegioni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COD_ISTAT_REGIONE")
	private String codIstatNazione;

	private String denominazione;
	
	@Column(name="COD_AGENZIA_ENTRATE")
	private String codAgenziaEntrate;

	public String getCodIstatNazione() {
		return codIstatNazione;
	}

	public void setCodIstatNazione(String codIstatNazione) {
		this.codIstatNazione = codIstatNazione;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getCodAgenziaEntrate() {
		return codAgenziaEntrate;
	}

	public void setCodAgenziaEntrate(String codAgenziaEntrate) {
		this.codAgenziaEntrate = codAgenziaEntrate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}