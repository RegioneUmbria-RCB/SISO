package it.webred.ct.data.model.cnc.flusso750;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the RE_CNC_R7H_ULT_REC_ANA database table.
 * 
 */
@Entity
@Table(name="RE_CNC_R7H_ULT_REC_ANA")
public class VUlterioreRecordAnagrafico implements Serializable {
	private static final long serialVersionUID = 1L;

	// Surrogate Key
	@Id
	@Column(name="ID")
	private Long id;

	private ChiaveULPartita chiavePartita;
	
	private String cap;


	@Column(name="COD_BELFIORE")
	private String codBelfiore;

	@Column(name="COD_BELFIORE_NASCITA")
	private String codBelfioreNascita;

	@Column(name="COD_FISCALE")
	private String codFiscale;

	@Column(name="COD_FISCALE_COMUNIC_ENTE")
	private String codFiscaleComunicEnte;

	@Column(name="COD_NAT_GIURID_INTEST")
	private String codNatGiuridIntest;

	@Column(name="COD_PROVINCIA")
	private String codProvincia;

	@Column(name="COD_PROVINCIA_NASCITA")
	private String codProvinciaNascita;

	@Column(name="COD_SESSO")
	private String codSesso;


	private String cognome;

	private String denominazione;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	@Column(name="DT_NASCITA")
	private String dtNascita;

	private String indirizzo;

	private String interno;

	private String km;

	@Column(name="LETTERA_NUMERO_CIVICO")
	private String letteraNumeroCivico;

	@Column(name="LOCALITA_FRAZIONE")
	private String localitaFrazione;

	private String nome;

	@Column(name="NUMERO_CIVICO")
	private String numeroCivico;

	private String palazzina;

	private String piano;

	private String processid;

	@Column(name="PROGRESSIVO_RECORD")
	private String progressivoRecord;


	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

	private String scala;

    public VUlterioreRecordAnagrafico() {
    }



	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}



	public String getCodBelfiore() {
		return this.codBelfiore;
	}

	public void setCodBelfiore(String codBelfiore) {
		this.codBelfiore = codBelfiore;
	}

	public String getCodBelfioreNascita() {
		return this.codBelfioreNascita;
	}

	public void setCodBelfioreNascita(String codBelfioreNascita) {
		this.codBelfioreNascita = codBelfioreNascita;
	}


	public String getCodFiscale() {
		return this.codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getCodFiscaleComunicEnte() {
		return this.codFiscaleComunicEnte;
	}

	public void setCodFiscaleComunicEnte(String codFiscaleComunicEnte) {
		this.codFiscaleComunicEnte = codFiscaleComunicEnte;
	}

	public String getCodNatGiuridIntest() {
		return this.codNatGiuridIntest;
	}

	public void setCodNatGiuridIntest(String codNatGiuridIntest) {
		this.codNatGiuridIntest = codNatGiuridIntest;
	}

	public String getCodProvincia() {
		return this.codProvincia;
	}

	public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
	}

	public String getCodProvinciaNascita() {
		return this.codProvinciaNascita;
	}

	public void setCodProvinciaNascita(String codProvinciaNascita) {
		this.codProvinciaNascita = codProvinciaNascita;
	}

	public String getCodSesso() {
		return this.codSesso;
	}

	public void setCodSesso(String codSesso) {
		this.codSesso = codSesso;
	}


	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getDtNascita() {
		return this.dtNascita;
	}

	public void setDtNascita(String dtNascita) {
		this.dtNascita = dtNascita;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getInterno() {
		return this.interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getKm() {
		return this.km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public String getLetteraNumeroCivico() {
		return this.letteraNumeroCivico;
	}

	public void setLetteraNumeroCivico(String letteraNumeroCivico) {
		this.letteraNumeroCivico = letteraNumeroCivico;
	}

	public String getLocalitaFrazione() {
		return this.localitaFrazione;
	}

	public void setLocalitaFrazione(String localitaFrazione) {
		this.localitaFrazione = localitaFrazione;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroCivico() {
		return this.numeroCivico;
	}

	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

	public String getPalazzina() {
		return this.palazzina;
	}

	public void setPalazzina(String palazzina) {
		this.palazzina = palazzina;
	}

	public String getPiano() {
		return this.piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProgressivoRecord() {
		return this.progressivoRecord;
	}

	public void setProgressivoRecord(String progressivoRecord) {
		this.progressivoRecord = progressivoRecord;
	}



	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

	public String getScala() {
		return this.scala;
	}

	public void setScala(String scala) {
		this.scala = scala;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public ChiaveULPartita getChiavePartita() {
		return chiavePartita;
	}



	public void setChiavePartita(ChiaveULPartita chiavePartita) {
		this.chiavePartita = chiavePartita;
	}

	
}