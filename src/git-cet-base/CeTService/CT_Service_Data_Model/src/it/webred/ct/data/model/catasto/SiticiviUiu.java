package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SITICIVI_UIU database table.
 * 
 */
@Entity
@Table(name="SITICIVI_UIU")
public class SiticiviUiu implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public SiticiviUiuPK getId() {
		return id;
	}

	public void setId(SiticiviUiuPK id) {
		this.id = id;
	}

	@EmbeddedId
	private SiticiviUiuPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO_VAL")
	private Date dataInizioVal;

	private String interno;

	private String piano;

	private String principale;

	private String scala;

	private String utente;

	@Column(name="UTENTE_FINE")
	private String utenteFine;

    public SiticiviUiu() {
    }

	public Date getDataInizioVal() {
		return this.dataInizioVal;
	}

	public void setDataInizioVal(Date dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}

	public String getInterno() {
		return this.interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getPiano() {
		return this.piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getPrincipale() {
		return this.principale;
	}

	public void setPrincipale(String principale) {
		this.principale = principale;
	}

	public String getScala() {
		return this.scala;
	}

	public void setScala(String scala) {
		this.scala = scala;
	}

	public String getUtente() {
		return this.utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public String getUtenteFine() {
		return this.utenteFine;
	}

	public void setUtenteFine(String utenteFine) {
		this.utenteFine = utenteFine;
	}

}