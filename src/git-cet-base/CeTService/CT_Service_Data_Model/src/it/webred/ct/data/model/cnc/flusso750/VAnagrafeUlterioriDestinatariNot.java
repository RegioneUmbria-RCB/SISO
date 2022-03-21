package it.webred.ct.data.model.cnc.flusso750;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the RE_CNC_R7B_ANA_ULT_DEST_NOT database table.
 * 
 */
@Entity
@Table(name="RE_CNC_R7B_ANA_ULT_DEST_NOT")
public class VAnagrafeUlterioriDestinatariNot implements Serializable {
	private static final long serialVersionUID = 1L;

	// Surrogate Key
	@Id
	@Column(name="ID")
	private Long id;

	private ChiaveULPartita chiavePartita;

	@Column(name="CAP_AT")
	private String capAt;

	@Column(name="CAP_ULT_DEST")
	private String capUltDest;


	@Column(name="COD_BELFIORE_AT")
	private String codBelfioreAt;

	@Column(name="COD_BELFIORE_NASCITA")
	private String codBelfioreNascita;

	@Column(name="COD_BELFIORE_ULT_DEST")
	private String codBelfioreUltDest;

	@Column(name="COD_CONGRUENZA")
	private String codCongruenza;


	@Column(name="COD_FISCALE_INTEST_COOB")
	private String codFiscaleIntestCoob;

	@Column(name="COD_FISCALE_ULT_DEST")
	private String codFiscaleUltDest;

	@Column(name="COD_PRESENZA_R7H")
	private String codPresenzaR7h;

	@Column(name="COD_PROVINCIA_AT")
	private String codProvinciaAt;

	@Column(name="COD_PROVINCIA_NASCITA")
	private String codProvinciaNascita;

	@Column(name="COD_PROVINCIA_ULT_DEST")
	private String codProvinciaUltDest;

	@Column(name="COD_SESSO")
	private String codSesso;


	private String cognome;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	@Column(name="DT_NASCITA")
	private String dtNascita;

	@Column(name="DT_VALID_DOMIC_FISCALE")
	private String dtValidDomicFiscale;

	private String filler;

	private String filler1;

	@Column(name="INDIRIZZO_AT")
	private String indirizzoAt;

	@Column(name="INDIRIZZO_ULT_DEST")
	private String indirizzoUltDest;

	private String interno;

	@Column(name="KM_ULT_DEST")
	private String kmUltDest;

	@Column(name="LETTERA_CIVICO_ULT_DEST")
	private String letteraCivicoUltDest;

	@Column(name="LOCALITA_FRAZIONE_ULT_DEST")
	private String localitaFrazioneUltDest;

	private String nome;

	@Column(name="NUMERO_CIVICO_ULT_DEST")
	private String numeroCivicoUltDest;

	private String palazzina;

	@Column(name="PARTITA_IVA")
	private String partitaIva;

	private String piano;

	private String processid;

	@Column(name="PROGRESSIVO_RECORD")
	private String progressivoRecord;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

	private String scala;

    public VAnagrafeUlterioriDestinatariNot() {
    }




	public String getCapAt() {
		return this.capAt;
	}

	public void setCapAt(String capAt) {
		this.capAt = capAt;
	}

	public String getCapUltDest() {
		return this.capUltDest;
	}

	public void setCapUltDest(String capUltDest) {
		this.capUltDest = capUltDest;
	}

	

	public String getCodBelfioreAt() {
		return this.codBelfioreAt;
	}

	public void setCodBelfioreAt(String codBelfioreAt) {
		this.codBelfioreAt = codBelfioreAt;
	}

	public String getCodBelfioreNascita() {
		return this.codBelfioreNascita;
	}

	public void setCodBelfioreNascita(String codBelfioreNascita) {
		this.codBelfioreNascita = codBelfioreNascita;
	}

	public String getCodBelfioreUltDest() {
		return this.codBelfioreUltDest;
	}

	public void setCodBelfioreUltDest(String codBelfioreUltDest) {
		this.codBelfioreUltDest = codBelfioreUltDest;
	}

	public String getCodCongruenza() {
		return this.codCongruenza;
	}

	public void setCodCongruenza(String codCongruenza) {
		this.codCongruenza = codCongruenza;
	}

	

	public String getCodFiscaleIntestCoob() {
		return this.codFiscaleIntestCoob;
	}

	public void setCodFiscaleIntestCoob(String codFiscaleIntestCoob) {
		this.codFiscaleIntestCoob = codFiscaleIntestCoob;
	}

	public String getCodFiscaleUltDest() {
		return this.codFiscaleUltDest;
	}

	public void setCodFiscaleUltDest(String codFiscaleUltDest) {
		this.codFiscaleUltDest = codFiscaleUltDest;
	}

	public String getCodPresenzaR7h() {
		return this.codPresenzaR7h;
	}

	public void setCodPresenzaR7h(String codPresenzaR7h) {
		this.codPresenzaR7h = codPresenzaR7h;
	}

	public String getCodProvinciaAt() {
		return this.codProvinciaAt;
	}

	public void setCodProvinciaAt(String codProvinciaAt) {
		this.codProvinciaAt = codProvinciaAt;
	}

	public String getCodProvinciaNascita() {
		return this.codProvinciaNascita;
	}

	public void setCodProvinciaNascita(String codProvinciaNascita) {
		this.codProvinciaNascita = codProvinciaNascita;
	}

	public String getCodProvinciaUltDest() {
		return this.codProvinciaUltDest;
	}

	public void setCodProvinciaUltDest(String codProvinciaUltDest) {
		this.codProvinciaUltDest = codProvinciaUltDest;
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

	public String getDtValidDomicFiscale() {
		return this.dtValidDomicFiscale;
	}

	public void setDtValidDomicFiscale(String dtValidDomicFiscale) {
		this.dtValidDomicFiscale = dtValidDomicFiscale;
	}

	public String getFiller() {
		return this.filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getFiller1() {
		return this.filler1;
	}

	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}

	public String getIndirizzoAt() {
		return this.indirizzoAt;
	}

	public void setIndirizzoAt(String indirizzoAt) {
		this.indirizzoAt = indirizzoAt;
	}

	public String getIndirizzoUltDest() {
		return this.indirizzoUltDest;
	}

	public void setIndirizzoUltDest(String indirizzoUltDest) {
		this.indirizzoUltDest = indirizzoUltDest;
	}

	public String getInterno() {
		return this.interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getKmUltDest() {
		return this.kmUltDest;
	}

	public void setKmUltDest(String kmUltDest) {
		this.kmUltDest = kmUltDest;
	}

	public String getLetteraCivicoUltDest() {
		return this.letteraCivicoUltDest;
	}

	public void setLetteraCivicoUltDest(String letteraCivicoUltDest) {
		this.letteraCivicoUltDest = letteraCivicoUltDest;
	}

	public String getLocalitaFrazioneUltDest() {
		return this.localitaFrazioneUltDest;
	}

	public void setLocalitaFrazioneUltDest(String localitaFrazioneUltDest) {
		this.localitaFrazioneUltDest = localitaFrazioneUltDest;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroCivicoUltDest() {
		return this.numeroCivicoUltDest;
	}

	public void setNumeroCivicoUltDest(String numeroCivicoUltDest) {
		this.numeroCivicoUltDest = numeroCivicoUltDest;
	}

	public String getPalazzina() {
		return this.palazzina;
	}

	public void setPalazzina(String palazzina) {
		this.palazzina = palazzina;
	}

	public String getPartitaIva() {
		return this.partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
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