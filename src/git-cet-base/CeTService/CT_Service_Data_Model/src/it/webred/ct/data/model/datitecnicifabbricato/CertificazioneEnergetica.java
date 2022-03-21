package it.webred.ct.data.model.datitecnicifabbricato;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the DATI_TEC_FABBR_CERT_ENER database table.
 * 
 */
@Entity
@Table(name="DATI_TEC_FABBR_CERT_ENER")
public class CertificazioneEnergetica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="CF_PIVA_CERTIFICATORE")
	private String cfPivaCertificatore;

	@Column(name="CF_PIVA_PROPRIETARIO")
	private String cfPivaProprietario;

	@Column(name="CLASSE_ENERGETICA")
	private String classeEnergetica;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_PROT")
	private Date dataProt;
    
    @Transient
    private String dataProtStr;

	@Column(name="DENOM_CERTIFICATORE")
	private String denomCertificatore;

	@Column(name="DENOM_PROPRIETARIO")
	private String denomProprietario;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_SCA_VALIDITA")
	private Date dtScaValidita;
    
    @Transient
    private String dtScaValiditaStr;

    @Column(name="EMISSIONI")
	private BigDecimal emissioni;

    @Column(name="FOGLIO")
	private String foglio;

	@Column(name="NUM_PROT")
	private BigDecimal numProt;

	@Column(name="PARTICELLA")
	private String particella;

	@Column(name="SEZIONE")
	private String sezione;

	@Column(name="SUB_TUTTI")
	private String subTutti;

	@Column(name="UBIC_INTERNO")
	private String ubicInterno;

	@Column(name="UBIC_PIANO")
	private String ubicPiano;

	@Column(name="UBIC_SCALA")
	private String ubicScala;

	@Column(name="UBIC_SITICIVI_PKID_CIVI")
	private BigDecimal ubicSiticiviPkidCivi;
	
	@Column(name="CODICE_IDENTIFICATIVO_PRATICA")
	private String codiceIdentificativoPratica = "";
	
	@Column(name="INDIRIZZO")
	private String indirizzo = "";
	
	@Column(name="PROVINCIA")
	private String provincia = "";
	
	@Column(name="COMUNE")
	private String comune = "";
	
	@Column(name="EDIFICIO_PUBBLICO")
	private String edificioPubblico = "";
	
	@Column(name="DESTINAZIONE_DI_USO")
	private String destinazioneDiUso = "";
	
	@Column(name="ANNO_COSTRUZIONE")
	private String annoCostruzione = "";
	
	@Column(name="MOTIVAZIONE_ACE")
	private String motivazioneAce = "";
	
	@Column(name="SUPERFICIE_LORDA")
	private BigDecimal superficieLorda = null;
	
	@Column(name="SUPERFICIE_NETTA")
	private BigDecimal superficieNetta = null;
	
	@Column(name="VOLUME_LORDO")
	private BigDecimal volumeLordo = null;
	
	@Column(name="VOLUME_NETTO")
	private BigDecimal volumeNetto = null;
	
	@Column(name="SUPERFICIE_DISPERDENTE")
	private BigDecimal superficieDisperdente = null;
	
	@Column(name="SUPERFICIE_VETRATA_OPACA")
	private BigDecimal superficieVetrataOpaca = null;
	
	@Column(name="TRASMITTANZA_MEDIA_INVOLUCRO")
	private BigDecimal trasmittanzaMediaInvolucro = null;
	
	@Column(name="TRASMITTANZA_MEDIA_COPERTURA")
	private BigDecimal trasmittanzaMediaCopertura = null;
	
	@Column(name="TRASMITTANZA_MEDIA_BASAMENTO")
	private BigDecimal trasmittanzaMediaBasamento = null;
	
	@Column(name="TRASMITTANZA_MEDIA_SERRAMENTO")
	private BigDecimal trasmittanzaMediaSerramento = null;
	
	@Column(name="EPH")
	private BigDecimal eph = null;
	
	@Column(name="ETH")
	private BigDecimal eth = null;
	
	@Column(name="ETC")
	private BigDecimal etc = null;
	
	@Column(name="EFER")
	private BigDecimal efer = null;
	
	@Column(name="EPW")
	private BigDecimal epw = null;
	
	@Column(name="EPT")
	private BigDecimal ept = null;
	
	@Column(name="EF_GLOB_MEDIA_RISCALDAMENTO")
	private BigDecimal efGlobMediaRiscaldamento = null;
	
	@Column(name="EF_GLOB_MEDIA_ACQUA_CALDA_SAN")
	private BigDecimal efGlobMediaAcquaCaldaSan = null;
	
	@Column(name="EGHW")
	private BigDecimal eghw = null;
	
	@Column(name="TIPOLOGIA_VENTILAZIONE")
	private String tipologiaVentilazione = "";
	
	@Column(name="NUMERO_RICAMBI_ORARI")
	private String numeroRicambiOrari = "";
	
	@Column(name="TIPOLOGIA_PANNELLO_ST")
	private String tipologiaPannelloSt = "";
	
	@Column(name="TIPOLOGIA_PANNELLO_FV")
	private String tipologiaPannelloFv = "";
	
	@Column(name="SUPERFICIE_CAPTANTE_FV")
	private String superficieCaptanteFv = "";
	
	@Column(name="SUPERFICIE_APERTURA_ST")
	private String superficieAperturaSt = "";
	
	@Column(name="SUP_PAN_FV_SUP_UTILE")
	private String supPanFvSupUtile = "";
	
	@Column(name="SUP_PAN_ST_SUP_UTILE")
	private String supPanStSupUtile = "";
	
	@Column(name="TIPOLOGIA_COMBUSTIBILE")
	private String tipologiaCombustibile = "";
	
	@Column(name="TIPOLOGIA_GENERATORE")
	private String tipologiaGeneratore = "";
	
	@Column(name="POTENZA_GENERATORE")
	private String potenzaGeneratore = "";
	
	@Column(name="BELFIORE")
	private String belfiore = "";
	
	@Transient
	private String chiave = "";
	
    public CertificazioneEnergetica() {
    }//-------------------------------------------------------------------------

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCfPivaCertificatore() {
		return this.cfPivaCertificatore;
	}

	public void setCfPivaCertificatore(String cfPivaCertificatore) {
		this.cfPivaCertificatore = cfPivaCertificatore;
	}

	public String getCfPivaProprietario() {
		return this.cfPivaProprietario;
	}

	public void setCfPivaProprietario(String cfPivaProprietario) {
		this.cfPivaProprietario = cfPivaProprietario;
	}

	public String getClasseEnergetica() {
		return this.classeEnergetica;
	}

	public void setClasseEnergetica(String classeEnergetica) {
		this.classeEnergetica = classeEnergetica;
	}

	public Date getDataProt() {
		return this.dataProt;
	}

	public void setDataProt(Date dataProt) {
		this.dataProt = dataProt;
	}

	public String getDataProtStr() {
		
		SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy");
		return sdf.format(dataProt);		
	}

	public String getDenomCertificatore() {
		return this.denomCertificatore;
	}

	public void setDenomCertificatore(String denomCertificatore) {
		this.denomCertificatore = denomCertificatore;
	}

	public String getDenomProprietario() {
		return this.denomProprietario;
	}

	public void setDenomProprietario(String denomProprietario) {
		this.denomProprietario = denomProprietario;
	}

	public Date getDtScaValidita() {
		return this.dtScaValidita;
	}

	public void setDtScaValidita(Date dtScaValidita) {
		this.dtScaValidita = dtScaValidita;
	}

	public String getDtScaValiditaStr() {
		SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy");
		return sdf.format(dtScaValidita);
	}

	public BigDecimal getEmissioni() {
		return this.emissioni;
	}

	public void setEmissioni(BigDecimal emissioni) {
		this.emissioni = emissioni;
	}

	public String getFoglio() {
		return this.foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public BigDecimal getNumProt() {
		return this.numProt;
	}

	public void setNumProt(BigDecimal numProt) {
		this.numProt = numProt;
	}

	public String getParticella() {
		return this.particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getSezione() {
		return this.sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getSubTutti() {
		return this.subTutti;
	}

	public void setSubTutti(String subTutti) {
		this.subTutti = subTutti;
	}

	public String getUbicInterno() {
		return this.ubicInterno;
	}

	public void setUbicInterno(String ubicInterno) {
		this.ubicInterno = ubicInterno;
	}

	public String getUbicPiano() {
		return this.ubicPiano;
	}

	public void setUbicPiano(String ubicPiano) {
		this.ubicPiano = ubicPiano;
	}

	public String getUbicScala() {
		return this.ubicScala;
	}

	public void setUbicScala(String ubicScala) {
		this.ubicScala = ubicScala;
	}

	public BigDecimal getUbicSiticiviPkidCivi() {
		return this.ubicSiticiviPkidCivi;
	}

	public void setUbicSiticiviPkidCivi(BigDecimal ubicSiticiviPkidCivi) {
		this.ubicSiticiviPkidCivi = ubicSiticiviPkidCivi;
	}

	public String getCodiceIdentificativoPratica() {
		return codiceIdentificativoPratica;
	}

	public void setCodiceIdentificativoPratica(String codiceIdentificativoPratica) {
		this.codiceIdentificativoPratica = codiceIdentificativoPratica;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getEdificioPubblico() {
		return edificioPubblico;
	}

	public void setEdificioPubblico(String edificioPubblico) {
		this.edificioPubblico = edificioPubblico;
	}

	public String getDestinazioneDiUso() {
		return destinazioneDiUso;
	}

	public void setDestinazioneDiUso(String destinazioneDiUso) {
		this.destinazioneDiUso = destinazioneDiUso;
	}

	public String getAnnoCostruzione() {
		return annoCostruzione;
	}

	public void setAnnoCostruzione(String annoCostruzione) {
		this.annoCostruzione = annoCostruzione;
	}

	public String getMotivazioneAce() {
		return motivazioneAce;
	}

	public void setMotivazioneAce(String motivazioneAce) {
		this.motivazioneAce = motivazioneAce;
	}

	public BigDecimal getSuperficieLorda() {
		return superficieLorda;
	}

	public void setSuperficieLorda(BigDecimal superficieLorda) {
		this.superficieLorda = superficieLorda;
	}

	public BigDecimal getSuperficieNetta() {
		return superficieNetta;
	}

	public void setSuperficieNetta(BigDecimal superficieNetta) {
		this.superficieNetta = superficieNetta;
	}

	public BigDecimal getVolumeLordo() {
		return volumeLordo;
	}

	public void setVolumeLordo(BigDecimal volumeLordo) {
		this.volumeLordo = volumeLordo;
	}

	public BigDecimal getVolumeNetto() {
		return volumeNetto;
	}

	public void setVolumeNetto(BigDecimal volumeNetto) {
		this.volumeNetto = volumeNetto;
	}

	public BigDecimal getSuperficieDisperdente() {
		return superficieDisperdente;
	}

	public void setSuperficieDisperdente(BigDecimal superficieDisperdente) {
		this.superficieDisperdente = superficieDisperdente;
	}

	public BigDecimal getSuperficieVetrataOpaca() {
		return superficieVetrataOpaca;
	}

	public void setSuperficieVetrataOpaca(BigDecimal superficieVetrataOpaca) {
		this.superficieVetrataOpaca = superficieVetrataOpaca;
	}

	public BigDecimal getTrasmittanzaMediaInvolucro() {
		return trasmittanzaMediaInvolucro;
	}

	public void setTrasmittanzaMediaInvolucro(BigDecimal trasmittanzaMediaInvolucro) {
		this.trasmittanzaMediaInvolucro = trasmittanzaMediaInvolucro;
	}

	public BigDecimal getTrasmittanzaMediaCopertura() {
		return trasmittanzaMediaCopertura;
	}

	public void setTrasmittanzaMediaCopertura(BigDecimal trasmittanzaMediaCopertura) {
		this.trasmittanzaMediaCopertura = trasmittanzaMediaCopertura;
	}

	public BigDecimal getTrasmittanzaMediaBasamento() {
		return trasmittanzaMediaBasamento;
	}

	public void setTrasmittanzaMediaBasamento(BigDecimal trasmittanzaMediaBasamento) {
		this.trasmittanzaMediaBasamento = trasmittanzaMediaBasamento;
	}

	public BigDecimal getTrasmittanzaMediaSerramento() {
		return trasmittanzaMediaSerramento;
	}

	public void setTrasmittanzaMediaSerramento(
			BigDecimal trasmittanzaMediaSerramento) {
		this.trasmittanzaMediaSerramento = trasmittanzaMediaSerramento;
	}

	public BigDecimal getEph() {
		return eph;
	}

	public void setEph(BigDecimal eph) {
		this.eph = eph;
	}

	public BigDecimal getEth() {
		return eth;
	}

	public void setEth(BigDecimal eth) {
		this.eth = eth;
	}

	public BigDecimal getEtc() {
		return etc;
	}

	public void setEtc(BigDecimal etc) {
		this.etc = etc;
	}

	public BigDecimal getEfer() {
		return efer;
	}

	public void setEfer(BigDecimal efer) {
		this.efer = efer;
	}

	public BigDecimal getEpw() {
		return epw;
	}

	public void setEpw(BigDecimal epw) {
		this.epw = epw;
	}

	public BigDecimal getEpt() {
		return ept;
	}

	public void setEpt(BigDecimal ept) {
		this.ept = ept;
	}

	public BigDecimal getEfGlobMediaRiscaldamento() {
		return efGlobMediaRiscaldamento;
	}

	public void setEfGlobMediaRiscaldamento(BigDecimal efGlobalMediaRiscaldamento) {
		this.efGlobMediaRiscaldamento = efGlobalMediaRiscaldamento;
	}

	public BigDecimal getEfGlobMediaAcquaCaldaSan() {
		return efGlobMediaAcquaCaldaSan;
	}

	public void setEfGlobMediaAcquaCaldaSan(BigDecimal efGlobalMediaAcquaCaldaSan) {
		this.efGlobMediaAcquaCaldaSan = efGlobalMediaAcquaCaldaSan;
	}

	public BigDecimal getEghw() {
		return eghw;
	}

	public void setEghw(BigDecimal eghw) {
		this.eghw = eghw;
	}

	public String getTipologiaVentilazione() {
		return tipologiaVentilazione;
	}

	public void setTipologiaVentilazione(String tipologiaVentilazione) {
		this.tipologiaVentilazione = tipologiaVentilazione;
	}

	public String getNumeroRicambiOrari() {
		return numeroRicambiOrari;
	}

	public void setNumeroRicambiOrari(String numeroRicambiOrari) {
		this.numeroRicambiOrari = numeroRicambiOrari;
	}

	public String getTipologiaPannelloSt() {
		return tipologiaPannelloSt;
	}

	public void setTipologiaPannelloSt(String tipologiaPannelloSt) {
		this.tipologiaPannelloSt = tipologiaPannelloSt;
	}

	public String getTipologiaPannelloFv() {
		return tipologiaPannelloFv;
	}

	public void setTipologiaPannelloFv(String tipologiaPannelloFv) {
		this.tipologiaPannelloFv = tipologiaPannelloFv;
	}

	public String getSuperficieCaptanteFv() {
		return superficieCaptanteFv;
	}

	public void setSuperficieCaptanteFv(String superficieCaptanteFv) {
		this.superficieCaptanteFv = superficieCaptanteFv;
	}

	public String getSuperficieAperturaSt() {
		return superficieAperturaSt;
	}

	public void setSuperficieAperturaSt(String superficieAperturaSt) {
		this.superficieAperturaSt = superficieAperturaSt;
	}

	public String getSupPanFvSupUtile() {
		return supPanFvSupUtile;
	}

	public void setSupPanFvSupUtile(String supPanFvSupUtile) {
		this.supPanFvSupUtile = supPanFvSupUtile;
	}

	public String getSupPanStSupUtile() {
		return supPanStSupUtile;
	}

	public void setSupPanStSupUtile(String supPanStSupUtile) {
		this.supPanStSupUtile = supPanStSupUtile;
	}

	public String getTipologiaCombustibile() {
		return tipologiaCombustibile;
	}

	public void setTipologiaCombustibile(String tipologiaCombustibile) {
		this.tipologiaCombustibile = tipologiaCombustibile;
	}

	public String getTipologiaGeneratore() {
		return tipologiaGeneratore;
	}

	public void setTipologiaGeneratore(String tipologiaGeneratore) {
		this.tipologiaGeneratore = tipologiaGeneratore;
	}

	public String getPotenzaGeneratore() {
		return potenzaGeneratore;
	}

	public void setPotenzaGeneratore(String potenzaGeneratore) {
		this.potenzaGeneratore = potenzaGeneratore;
	}

	public String getBelfiore() {
		return belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setDataProtStr(String dataProtStr) {
		this.dataProtStr = dataProtStr;
	}

	public void setDtScaValiditaStr(String dtScaValiditaStr) {
		this.dtScaValiditaStr = dtScaValiditaStr;
	}

	public String getChiave() {
		return "" + this.getId() ;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

}


/*
private String sezione = "";
	private String foglio = "";
	private String particella = "";
	private String subTutti = "";
	private Date dataProt = null;
	private String numProt = "";
	private Date dtScaValidita = null;
	private String denomProprietario = "";
	private String cfPivaProprietario = "";
	private String classeEnergetica = "";
	private String emissione = "";
	private String denomCertificatore = "";
	private String cfPivaCertificatore = "";
	private String codiceIdentificativoPratica = "";
	private String indirizzo = "";
	private String provincia = "";
	private String comune = "";
	private String edificioPubblico = "";
	private String destinazioneDiUso = "";
	private String annoCostruzione = "";
	private String motivazioneAce = "";
	private BigDecimal superficieLorda = null;
	private BigDecimal superficieNetta = null;
	private BigDecimal volumeLordo = null;
	private BigDecimal volumeNetto = null;
	private BigDecimal superficieDisperdente = null;
	private BigDecimal superficieVetrataOpaca = null;
	private BigDecimal trasmittanzaMediaInvolucro = null;
	private BigDecimal trasmittanzaMediaCopertura = null;
	private BigDecimal trasmittanzaMediaBasamento = null;
	private BigDecimal trasmittanzaMediaSerramento = null;
	private BigDecimal eph = null;
	private BigDecimal eth = null;
	private BigDecimal etc = null;
	private BigDecimal efer = null;
	private BigDecimal epw = null;
	private BigDecimal ept = null;
	private BigDecimal efGlobalMediaRiscaldamento = null;
	private BigDecimal efGlobalMediaAcquaCaldaSan = null;
	private BigDecimal eghw = null;
	private String tipologiaVentilazione = "";
	private String numeroRicambiOrari = "";
	private String tipologiaPannelloSt = "";
	private String tipologiaPannelloFv = "";
	private String superficieCaptanteFv = "";
	private String superficieAperturaSt = "";
	private String supPanFvSupUtile = "";
	private String supPanStSupUtile = "";
	private String tipologiaCombustibile = "";
	private String tipologiaGeneratore = "";
	private String potenzaGeneratore = "";
	private String belfiore = "";
 */
