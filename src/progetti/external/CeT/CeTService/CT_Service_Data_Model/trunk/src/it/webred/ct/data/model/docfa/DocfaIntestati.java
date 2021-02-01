package it.webred.ct.data.model.docfa;

import java.io.Serializable;
import javax.persistence.*;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the DOCFA_INTESTATI database table.
 * 
 */
@Entity
@Table(name="DOCFA_INTESTATI")
public class DocfaIntestati implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected static Logger logger = Logger.getLogger("ctservice.log");

	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;

	private String cognome;

	@Column(name="COMUNE_NASCITA")
	private String comuneNascita;

	@Column(name="DATA_NASCITA")
	private String dataNascita;

	@Column(name="DENOMINATORE_POSSESSO")
	private BigDecimal denominatorePossesso;

	private String denominazione;

	private String nome;

	@Column(name="NR_ORDINE_INTESTATO")
	private BigDecimal nrOrdineIntestato;

	@Column(name="NUMERO_POSSESSO")
	private BigDecimal numeroPossesso;

	@Column(name="NUOVI_TITOLI")
	private String nuoviTitoli;

	@Column(name="PARTITA_IVA")
	private String partitaIva;

	@Id
	private DocfaIntestatiPK id;
	
	private String regime;

	private String sede;

	private String sesso;

	@Column(name="SPECIFICA_DIRITTO")
	private String specificaDiritto;

    public DocfaIntestati() {
    }

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getComuneNascita() {
		return this.comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getDataNascita() {
		return this.dataNascita;
	}
	
	public Date getDataNascitaToDate() {
		if (this.dataNascita != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
			try {
				return sdf.parse(this.dataNascita);
			} catch (ParseException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return null;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public BigDecimal getDenominatorePossesso() {
		return this.denominatorePossesso;
	}

	public void setDenominatorePossesso(BigDecimal denominatorePossesso) {
		this.denominatorePossesso = denominatorePossesso;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}


	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getNrOrdineIntestato() {
		return this.nrOrdineIntestato;
	}

	public void setNrOrdineIntestato(BigDecimal nrOrdineIntestato) {
		this.nrOrdineIntestato = nrOrdineIntestato;
	}


	public BigDecimal getNumeroPossesso() {
		return this.numeroPossesso;
	}

	public void setNumeroPossesso(BigDecimal numeroPossesso) {
		this.numeroPossesso = numeroPossesso;
	}

	public String getNuoviTitoli() {
		return this.nuoviTitoli;
	}

	public void setNuoviTitoli(String nuoviTitoli) {
		this.nuoviTitoli = nuoviTitoli;
	}

	public String getPartitaIva() {
		return this.partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}



	public String getRegime() {
		return this.regime;
	}

	public void setRegime(String regime) {
		this.regime = regime;
	}

	public String getSede() {
		return this.sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getSpecificaDiritto() {
		return this.specificaDiritto;
	}

	public void setSpecificaDiritto(String specificaDiritto) {
		this.specificaDiritto = specificaDiritto;
	}

	public DocfaIntestatiPK getId() {
		return id;
	}

	public void setId(DocfaIntestatiPK id) {
		this.id = id;
	}

}