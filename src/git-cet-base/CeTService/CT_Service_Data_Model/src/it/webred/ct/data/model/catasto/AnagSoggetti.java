package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ANAG_SOGGETTI database table.
 * 
 */
@Entity
@Table(name="ANAG_SOGGETTI")
public class AnagSoggetti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long pkid;

	@Column(name="ATTO_FINE")
	private BigDecimal attoFine;

	@Column(name="ATTO_INIZIO")
	private BigDecimal attoInizio;

	private String cap;

	@Column(name="CAP_RECAP")
	private String capRecap;

	@Column(name="COD_FISCALE")
	private String codFiscale;

	@Column(name="COD_SOGGETTO")
	private BigDecimal codSoggetto;

	private String cognome;

    @Temporal( TemporalType.DATE)
	@Column(name="D_COSTITUZIONE")
	private Date dCostituzione;

    @Temporal( TemporalType.DATE)
	@Column(name="D_MORTE")
	private Date dMorte;

    @Temporal( TemporalType.DATE)
	@Column(name="D_NASCITA")
	private Date dNascita;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_FINE_VAL")
	private Date dataFineVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO_VAL")
	private Date dataInizioVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_LAV")
	private Date dataLav;

	private String denominazione;

	private String fax;

	@Column(name="FKCIVICO_RECAPITO")
	private BigDecimal fkcivicoRecapito;

	@Column(name="FKCIVICO_RESI")
	private BigDecimal fkcivicoResi;

	@Column(name="FKCOD_COMU_NASC")
	private String fkcodComuNasc;

	@Column(name="FKCOD_COMU_RECAPITO")
	private String fkcodComuRecapito;

	@Column(name="FKCOD_COMU_RESI")
	private String fkcodComuResi;

	@Column(name="FKRAPP_LEGALE")
	private BigDecimal fkrappLegale;

	@Column(name="FL_PART_IVA")
	private String flPartIva;

	@Column(name="FLAG_PERS_FISICA")
	private String flagPersFisica;

	@Column(name="FONTE_FINE")
	private BigDecimal fonteFine;

	@Column(name="FONTE_INIZIO")
	private BigDecimal fonteInizio;

	@Column(name="INDIRIZZO_EST")
	private String indirizzoEst;

	@Column(name="INDIRIZZO_EST_RECAP")
	private String indirizzoEstRecap;

	private String localita;

	private String mail;

	@Column(name="NATURA_GIURIDICA")
	private String naturaGiuridica;

	private String nome;

	@Column(name="PART_IVA")
	private String partIva;

	private String sesso;

	private String telefono;

	@Column(name="UTENTE_FINE")
	private String utenteFine;

	@Column(name="UTENTE_INIZIO")
	private String utenteInizio;

	@Column(name="VALIDO_FINE")
	private String validoFine;

	@Column(name="VALIDO_INIZIO")
	private String validoInizio;

    public AnagSoggetti() {
    }

	public long getPkid() {
		return this.pkid;
	}

	public void setPkid(long pkid) {
		this.pkid = pkid;
	}

	public BigDecimal getAttoFine() {
		return this.attoFine;
	}

	public void setAttoFine(BigDecimal attoFine) {
		this.attoFine = attoFine;
	}

	public BigDecimal getAttoInizio() {
		return this.attoInizio;
	}

	public void setAttoInizio(BigDecimal attoInizio) {
		this.attoInizio = attoInizio;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCapRecap() {
		return this.capRecap;
	}

	public void setCapRecap(String capRecap) {
		this.capRecap = capRecap;
	}

	public String getCodFiscale() {
		return this.codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public BigDecimal getCodSoggetto() {
		return this.codSoggetto;
	}

	public void setCodSoggetto(BigDecimal codSoggetto) {
		this.codSoggetto = codSoggetto;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDCostituzione() {
		return this.dCostituzione;
	}

	public void setDCostituzione(Date dCostituzione) {
		this.dCostituzione = dCostituzione;
	}

	public Date getDMorte() {
		return this.dMorte;
	}

	public void setDMorte(Date dMorte) {
		this.dMorte = dMorte;
	}

	public Date getDNascita() {
		return this.dNascita;
	}

	public void setDNascita(Date dNascita) {
		this.dNascita = dNascita;
	}

	public Date getDataFineVal() {
		return this.dataFineVal;
	}

	public void setDataFineVal(Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}

	public Date getDataInizioVal() {
		return this.dataInizioVal;
	}

	public void setDataInizioVal(Date dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}

	public Date getDataLav() {
		return this.dataLav;
	}

	public void setDataLav(Date dataLav) {
		this.dataLav = dataLav;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public BigDecimal getFkcivicoRecapito() {
		return this.fkcivicoRecapito;
	}

	public void setFkcivicoRecapito(BigDecimal fkcivicoRecapito) {
		this.fkcivicoRecapito = fkcivicoRecapito;
	}

	public BigDecimal getFkcivicoResi() {
		return this.fkcivicoResi;
	}

	public void setFkcivicoResi(BigDecimal fkcivicoResi) {
		this.fkcivicoResi = fkcivicoResi;
	}

	public String getFkcodComuNasc() {
		return this.fkcodComuNasc;
	}

	public void setFkcodComuNasc(String fkcodComuNasc) {
		this.fkcodComuNasc = fkcodComuNasc;
	}

	public String getFkcodComuRecapito() {
		return this.fkcodComuRecapito;
	}

	public void setFkcodComuRecapito(String fkcodComuRecapito) {
		this.fkcodComuRecapito = fkcodComuRecapito;
	}

	public String getFkcodComuResi() {
		return this.fkcodComuResi;
	}

	public void setFkcodComuResi(String fkcodComuResi) {
		this.fkcodComuResi = fkcodComuResi;
	}

	public BigDecimal getFkrappLegale() {
		return this.fkrappLegale;
	}

	public void setFkrappLegale(BigDecimal fkrappLegale) {
		this.fkrappLegale = fkrappLegale;
	}

	public String getFlPartIva() {
		return this.flPartIva;
	}

	public void setFlPartIva(String flPartIva) {
		this.flPartIva = flPartIva;
	}

	public String getFlagPersFisica() {
		return this.flagPersFisica;
	}

	public void setFlagPersFisica(String flagPersFisica) {
		this.flagPersFisica = flagPersFisica;
	}

	public BigDecimal getFonteFine() {
		return this.fonteFine;
	}

	public void setFonteFine(BigDecimal fonteFine) {
		this.fonteFine = fonteFine;
	}

	public BigDecimal getFonteInizio() {
		return this.fonteInizio;
	}

	public void setFonteInizio(BigDecimal fonteInizio) {
		this.fonteInizio = fonteInizio;
	}

	public String getIndirizzoEst() {
		return this.indirizzoEst;
	}

	public void setIndirizzoEst(String indirizzoEst) {
		this.indirizzoEst = indirizzoEst;
	}

	public String getIndirizzoEstRecap() {
		return this.indirizzoEstRecap;
	}

	public void setIndirizzoEstRecap(String indirizzoEstRecap) {
		this.indirizzoEstRecap = indirizzoEstRecap;
	}

	public String getLocalita() {
		return this.localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNaturaGiuridica() {
		return this.naturaGiuridica;
	}

	public void setNaturaGiuridica(String naturaGiuridica) {
		this.naturaGiuridica = naturaGiuridica;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPartIva() {
		return this.partIva;
	}

	public void setPartIva(String partIva) {
		this.partIva = partIva;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUtenteFine() {
		return this.utenteFine;
	}

	public void setUtenteFine(String utenteFine) {
		this.utenteFine = utenteFine;
	}

	public String getUtenteInizio() {
		return this.utenteInizio;
	}

	public void setUtenteInizio(String utenteInizio) {
		this.utenteInizio = utenteInizio;
	}

	public String getValidoFine() {
		return this.validoFine;
	}

	public void setValidoFine(String validoFine) {
		this.validoFine = validoFine;
	}

	public String getValidoInizio() {
		return this.validoInizio;
	}

	public void setValidoInizio(String validoInizio) {
		this.validoInizio = validoInizio;
	}

}