package it.webred.ct.data.model.cnc.flusso750;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class VAnagrafica implements Serializable {

	// Surrogate Key
	@Id
	@Column(name="ID")
	private Long id;

	private ChiaveULPartita chiavePartita;

	@Column(name="ANNO_RUOLO_AVVISO")
	private String annoRuoloAvviso;

	@Column(name="CAP_AT")
	private String capAt;

	@Column(name="CAP_DEB")
	private String capDeb;

	@Column(name="CAP_SOC")
	private String capSoc;


	@Column(name="COD_AMBITO_AVVISO")
	private String codAmbitoAvviso;

	@Column(name="COD_BELFIORE_AT")
	private String codBelfioreAt;

	@Column(name="COD_BELFIORE_DEB")
	private String codBelfioreDeb;

	@Column(name="COD_BELFIORE_NASCITA")
	private String codBelfioreNascita;

	@Column(name="COD_BELFIORE_SOC")
	private String codBelfioreSoc;

	@Column(name="COD_CONGRUENZA")
	private String codCongruenza;

	@Column(name="COD_COOB")
	private String codCoob;

	@Column(name="COD_DEFUNTO")
	private String codDefunto;


	@Column(name="COD_FISCALE")
	private String codFiscale;

	@Column(name="COD_NAT_GIURID_INTEST")
	private String codNatGiuridIntest;

	@Column(name="COD_PART_TRASF_INFRARATA")
	private String codPartTrasfInfrarata;

	@Column(name="COD_PRESENZA_R7H")
	private String codPresenzaR7h;

	@Column(name="COD_PROVINCIA_AT")
	private String codProvinciaAt;

	@Column(name="COD_PROVINCIA_DEB")
	private String codProvinciaDeb;

	@Column(name="COD_PROVINCIA_NASCITA")
	private String codProvinciaNascita;

	@Column(name="COD_PROVINCIA_SOC")
	private String codProvinciaSoc;

	@Column(name="COD_REPARTO")
	private String codReparto;

	@Column(name="COD_SESSO")
	private String codSesso;


	@Column(name="COD_UFFICIO_MANDANTE")
	private String codUfficioMandante;

	@Column(name="COD_ULT_DATI_ANA_NOTIF")
	private String codUltDatiAnaNotif;


	private String cognome;

	private String denominazione;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	@Column(name="DT_INSINUAZIONE_FALLIMENTO")
	private String dtInsinuazioneFallimento;

	@Column(name="DT_NASCITA")
	private String dtNascita;

	@Column(name="DT_NOTIFICA_ATTO")
	private String dtNotificaAtto;

	@Column(name="DT_VALID_DOMIC_FISCALE")
	private String dtValidDomicFiscale;

	private String filler;

	private String filler1;

	private String filler2;

	private String filler3;

	@Column(name="INDIRIZZO_AT")
	private String indirizzoAt;

	@Column(name="INDIRIZZO_DEB")
	private String indirizzoDeb;

	@Column(name="INDIRIZZO_SOC")
	private String indirizzoSoc;

	private String interno;

	@Column(name="KM_DEB")
	private String kmDeb;

	@Column(name="KM_SOC")
	private String kmSoc;

	@Column(name="LETTERA_NUMERO_CIVICO_DEB")
	private String letteraNumeroCivicoDeb;

	@Column(name="LETTERA_NUMERO_CIVICO_SOC")
	private String letteraNumeroCivicoSoc;

	@Column(name="LOCALITA_FRAZIONE_DEB")
	private String localitaFrazioneDeb;

	@Column(name="LOCALITA_FRAZIONE_SOC")
	private String localitaFrazioneSoc;

	private String nome;

	@Column(name="NUMERO_CIVICO_DEB")
	private String numeroCivicoDeb;

	@Column(name="NUMERO_CIVICO_SOC")
	private String numeroCivicoSoc;

	private String palazzina;

	@Column(name="PARTITA_IVA")
	private String partitaIva;

	private String piano;

	private String presso;

	private String processid;

	@Column(name="PROGRESSIVO_RECORD")
	private String progressivoRecord;

	@Column(name="PROGRESSIVO_RUOLO_AVVISO")
	private String progressivoRuoloAvviso;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;
	
	private String scala;

	
	public String getAnnoRuoloAvviso() {
		return this.annoRuoloAvviso;
	}

	public void setAnnoRuoloAvviso(String annoRuoloAvviso) {
		this.annoRuoloAvviso = annoRuoloAvviso;
	}

	public String getCapAt() {
		return this.capAt;
	}

	public void setCapAt(String capAt) {
		this.capAt = capAt;
	}

	public String getCapDeb() {
		return this.capDeb;
	}

	public void setCapDeb(String capDeb) {
		this.capDeb = capDeb;
	}

	public String getCapSoc() {
		return this.capSoc;
	}

	public void setCapSoc(String capSoc) {
		this.capSoc = capSoc;
	}



	public String getCodAmbitoAvviso() {
		return this.codAmbitoAvviso;
	}

	public void setCodAmbitoAvviso(String codAmbitoAvviso) {
		this.codAmbitoAvviso = codAmbitoAvviso;
	}

	public String getCodBelfioreAt() {
		return this.codBelfioreAt;
	}

	public void setCodBelfioreAt(String codBelfioreAt) {
		this.codBelfioreAt = codBelfioreAt;
	}

	public String getCodBelfioreDeb() {
		return this.codBelfioreDeb;
	}

	public void setCodBelfioreDeb(String codBelfioreDeb) {
		this.codBelfioreDeb = codBelfioreDeb;
	}

	public String getCodBelfioreNascita() {
		return this.codBelfioreNascita;
	}

	public void setCodBelfioreNascita(String codBelfioreNascita) {
		this.codBelfioreNascita = codBelfioreNascita;
	}

	public String getCodBelfioreSoc() {
		return this.codBelfioreSoc;
	}

	public void setCodBelfioreSoc(String codBelfioreSoc) {
		this.codBelfioreSoc = codBelfioreSoc;
	}

	public String getCodCongruenza() {
		return this.codCongruenza;
	}

	public void setCodCongruenza(String codCongruenza) {
		this.codCongruenza = codCongruenza;
	}

	public String getCodCoob() {
		return this.codCoob;
	}

	public void setCodCoob(String codCoob) {
		this.codCoob = codCoob;
	}

	public String getCodDefunto() {
		return this.codDefunto;
	}

	public void setCodDefunto(String codDefunto) {
		this.codDefunto = codDefunto;
	}


	public String getCodFiscale() {
		return this.codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getCodNatGiuridIntest() {
		return this.codNatGiuridIntest;
	}

	public void setCodNatGiuridIntest(String codNatGiuridIntest) {
		this.codNatGiuridIntest = codNatGiuridIntest;
	}

	public String getCodPartTrasfInfrarata() {
		return this.codPartTrasfInfrarata;
	}

	public void setCodPartTrasfInfrarata(String codPartTrasfInfrarata) {
		this.codPartTrasfInfrarata = codPartTrasfInfrarata;
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

	public String getCodProvinciaDeb() {
		return this.codProvinciaDeb;
	}

	public void setCodProvinciaDeb(String codProvinciaDeb) {
		this.codProvinciaDeb = codProvinciaDeb;
	}

	public String getCodProvinciaNascita() {
		return this.codProvinciaNascita;
	}

	public void setCodProvinciaNascita(String codProvinciaNascita) {
		this.codProvinciaNascita = codProvinciaNascita;
	}

	public String getCodProvinciaSoc() {
		return this.codProvinciaSoc;
	}

	public void setCodProvinciaSoc(String codProvinciaSoc) {
		this.codProvinciaSoc = codProvinciaSoc;
	}

	public String getCodReparto() {
		return this.codReparto;
	}

	public void setCodReparto(String codReparto) {
		this.codReparto = codReparto;
	}

	public String getCodSesso() {
		return this.codSesso;
	}

	public void setCodSesso(String codSesso) {
		this.codSesso = codSesso;
	}


	public String getCodUfficioMandante() {
		return this.codUfficioMandante;
	}

	public void setCodUfficioMandante(String codUfficioMandante) {
		this.codUfficioMandante = codUfficioMandante;
	}

	public String getCodUltDatiAnaNotif() {
		return this.codUltDatiAnaNotif;
	}

	public void setCodUltDatiAnaNotif(String codUltDatiAnaNotif) {
		this.codUltDatiAnaNotif = codUltDatiAnaNotif;
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

	public String getDtInsinuazioneFallimento() {
		return this.dtInsinuazioneFallimento;
	}

	public void setDtInsinuazioneFallimento(String dtInsinuazioneFallimento) {
		this.dtInsinuazioneFallimento = dtInsinuazioneFallimento;
	}

	public String getDtNascita() {
		return this.dtNascita;
	}

	public void setDtNascita(String dtNascita) {
		this.dtNascita = dtNascita;
	}

	public String getDtNotificaAtto() {
		return this.dtNotificaAtto;
	}

	public void setDtNotificaAtto(String dtNotificaAtto) {
		this.dtNotificaAtto = dtNotificaAtto;
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

	public String getFiller2() {
		return this.filler2;
	}

	public void setFiller2(String filler2) {
		this.filler2 = filler2;
	}

	public String getFiller3() {
		return this.filler3;
	}

	public void setFiller3(String filler3) {
		this.filler3 = filler3;
	}

	public String getIndirizzoAt() {
		return this.indirizzoAt;
	}

	public void setIndirizzoAt(String indirizzoAt) {
		this.indirizzoAt = indirizzoAt;
	}

	public String getIndirizzoDeb() {
		return this.indirizzoDeb;
	}

	public void setIndirizzoDeb(String indirizzoDeb) {
		this.indirizzoDeb = indirizzoDeb;
	}

	public String getIndirizzoSoc() {
		return this.indirizzoSoc;
	}

	public void setIndirizzoSoc(String indirizzoSoc) {
		this.indirizzoSoc = indirizzoSoc;
	}

	public String getInterno() {
		return this.interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getKmDeb() {
		return this.kmDeb;
	}

	public void setKmDeb(String kmDeb) {
		this.kmDeb = kmDeb;
	}

	public String getKmSoc() {
		return this.kmSoc;
	}

	public void setKmSoc(String kmSoc) {
		this.kmSoc = kmSoc;
	}

	public String getLetteraNumeroCivicoDeb() {
		return this.letteraNumeroCivicoDeb;
	}

	public void setLetteraNumeroCivicoDeb(String letteraNumeroCivicoDeb) {
		this.letteraNumeroCivicoDeb = letteraNumeroCivicoDeb;
	}

	public String getLetteraNumeroCivicoSoc() {
		return this.letteraNumeroCivicoSoc;
	}

	public void setLetteraNumeroCivicoSoc(String letteraNumeroCivicoSoc) {
		this.letteraNumeroCivicoSoc = letteraNumeroCivicoSoc;
	}

	public String getLocalitaFrazioneDeb() {
		return this.localitaFrazioneDeb;
	}

	public void setLocalitaFrazioneDeb(String localitaFrazioneDeb) {
		this.localitaFrazioneDeb = localitaFrazioneDeb;
	}

	public String getLocalitaFrazioneSoc() {
		return this.localitaFrazioneSoc;
	}

	public void setLocalitaFrazioneSoc(String localitaFrazioneSoc) {
		this.localitaFrazioneSoc = localitaFrazioneSoc;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroCivicoDeb() {
		return this.numeroCivicoDeb;
	}

	public void setNumeroCivicoDeb(String numeroCivicoDeb) {
		this.numeroCivicoDeb = numeroCivicoDeb;
	}

	public String getNumeroCivicoSoc() {
		return this.numeroCivicoSoc;
	}

	public void setNumeroCivicoSoc(String numeroCivicoSoc) {
		this.numeroCivicoSoc = numeroCivicoSoc;
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

	public String getPresso() {
		return this.presso;
	}

	public void setPresso(String presso) {
		this.presso = presso;
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

	public String getProgressivoRuoloAvviso() {
		return this.progressivoRuoloAvviso;
	}

	public void setProgressivoRuoloAvviso(String progressivoRuoloAvviso) {
		this.progressivoRuoloAvviso = progressivoRuoloAvviso;
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
