package it.webred.ct.data.model.bolliVeicoli;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="BOLLI_VEICOLI")
public class BolloVeicolo implements Serializable {
	
	private static final long serialVersionUID = 3162753164173327639L;
	
	@Id
	private Long id = null;
	
	@Column(name="ALIMENTAZIONE")
	private String alimentazione = "";
	@Column(name="CAP")
	private String cap = "";
	@Column(name="CILINDRATA")
	private BigDecimal cilindrata = null;
	@Column(name="CODICEFISCALE_PIVA")
	private String codiceFiscalePiva = "";
	@Column(name="CODICESESSO")
	private String codiceSesso = "";
	@Column(name="CODICE_CARROZZERIA")
	private String codiceCarrozzeria = "";
	@Column(name="CODICE_TELAIO")
	private String codiceTelaio = "";
	@Column(name="COD_SIGLA_EURO")
	private String codSiglaEuro = "";
	@Column(name="COGNOME")
	private String cognome = "";
	@Column(name="COMUNENASCITA")
	private String comuneNascita = "";	
	@Column(name="COMUNERESIDENZA")
	private String comuneResidenza = "";
	@Column(name="COMUNE_ISTATC")
	private String comuneIstatC = "";
	@Column(name="DATAFINEESENZIONE")
	@Temporal( TemporalType.DATE)
	private Date dataFineEsenzione = null;
	@Column(name="DATAFINEPROPRIETA")
	@Temporal( TemporalType.DATE)
	private Date dataFineProprieta = null;
	@Column(name="DATAINIZIOESENZIONE")
	@Temporal( TemporalType.DATE)
	private Date dataInizioEsenzione = null;
	@Column(name="DATAINIZIOPROPRIETA")
	@Temporal( TemporalType.DATE)
	private Date dataInizioProprieta = null;
	@Column(name="DATANASCITA")
	@Temporal( TemporalType.DATE)
	private Date dataNascita = null;
	@Column(name="DATA_INSERIMENTO")
	@Temporal( TemporalType.DATE)
	private Date dataInserimento = null;
	@Column(name="DESTINAZIONE")
	private String destinazione = "";
	@Column(name="DT_PRIMA_IMMATRICOLAZIONE")
	@Temporal( TemporalType.DATE)
	private Date dtPrimaImmatricolazione = null;
	@Column(name="EMISSIONI_CO2")
	private BigDecimal emissioniCo2 = null;
	@Column(name="ENTE")
	private String ente = "";
	@Column(name="ESENZIONE")
	private String esenzione = "";
	@Column(name="FLAG_ANN_MASSA_RIMORC")
	private String flagAnnMassaRimorc = "";
	@Column(name="INDIRIZZO")
	private String indirizzo = "";
	@Column(name="KW")
	private BigDecimal kw = null;
	@Column(name="MASSA_RIMORCHIABILE")
	private BigDecimal massaRimorchiabile = null;
	@Column(name="NOME")
	private String nome = "";
	@Column(name="NUMEROCIVICO")
	private String numeroCivico = "";
	@Column(name="NUMERO_ASSI")
	private BigDecimal numeroAssi = null;
	@Column(name="NUMERO_POSTI")
	private BigDecimal numeroPosti = null;
	@Column(name="PESO_COMPLESSIVO")
	private BigDecimal pesoComplessivo = null;
	@Column(name="PORTATA")
	private BigDecimal portata = null;
	@Column(name="POTENZA_FISCALE")
	private BigDecimal potenzaFiscale = null;
	@Column(name="PROVINCIANASCITA")
	private String provinciaNascita = "";
	@Column(name="PROVINCIARESIDENZA")
	private String provinciaResidenza = "";
	@Column(name="PROVINCIA_ISTATP")
	private String provinciaIstatP = "";
	@Column(name="RAGIONESOCIALE")
	private String ragioneSociale = "";
	@Column(name="REGIONERESIDENZA")
	private String regioneResidenza = "";
	@Column(name="SOSPENSIONE_PNEUMATICA")
	private String sospensionePneumatica = "";	
	@Column(name="STATOESENZIONE")
	private String statoEsenzione = "";
	@Column(name="TARGA")
	private String targa = "";
	@Column(name="TIPOHANDICAP")
	private String tipoHandicap = "";
	@Column(name="TIPOSOGGETTO")
	private String tipoSoggetto = "";
	@Column(name="TIPO_ALIMENTAZIONE_IMPIANTO")
	private String tipoAlimentazioneImpianto = "";
	@Column(name="USO")
	private String uso = "";
	@Transient
	private String chiave = "";

    public BolloVeicolo() {
    }//-------------------------------------------------------------------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlimentazione() {
		return alimentazione;
	}

	public void setAlimentazione(String alimentazione) {
		this.alimentazione = alimentazione;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public BigDecimal getCilindrata() {
		return cilindrata;
	}

	public void setCilindrata(BigDecimal cilindrata) {
		this.cilindrata = cilindrata;
	}

	public String getCodiceFiscalePiva() {
		return codiceFiscalePiva;
	}

	public void setCodiceFiscalePiva(String codiceFiscalePiva) {
		this.codiceFiscalePiva = codiceFiscalePiva;
	}

	public String getCodiceSesso() {
		return codiceSesso;
	}

	public void setCodiceSesso(String codiceSesso) {
		this.codiceSesso = codiceSesso;
	}

	public String getCodiceCarrozzeria() {
		return codiceCarrozzeria;
	}

	public void setCodiceCarrozzeria(String codiceCarrozzeria) {
		this.codiceCarrozzeria = codiceCarrozzeria;
	}

	public String getCodiceTelaio() {
		return codiceTelaio;
	}

	public void setCodiceTelaio(String codiceTelaio) {
		this.codiceTelaio = codiceTelaio;
	}

	public String getCodSiglaEuro() {
		return codSiglaEuro;
	}

	public void setCodSiglaEuro(String codSiglaEuro) {
		this.codSiglaEuro = codSiglaEuro;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getComuneIstatC() {
		return comuneIstatC;
	}

	public void setComuneIstatC(String comuneIstatC) {
		this.comuneIstatC = comuneIstatC;
	}

	public Date getDataFineEsenzione() {
		return dataFineEsenzione;
	}

	public void setDataFineEsenzione(Date dataFineEsenzione) {
		this.dataFineEsenzione = dataFineEsenzione;
	}

	public Date getDataFineProprieta() {
		return dataFineProprieta;
	}

	public void setDataFineProprieta(Date dataFineProprieta) {
		this.dataFineProprieta = dataFineProprieta;
	}

	public Date getDataInizioEsenzione() {
		return dataInizioEsenzione;
	}

	public void setDataInizioEsenzione(Date dataInizioEsenzione) {
		this.dataInizioEsenzione = dataInizioEsenzione;
	}

	public Date getDataInizioProprieta() {
		return dataInizioProprieta;
	}

	public void setDataInizioProprieta(Date dataInizioProprieta) {
		this.dataInizioProprieta = dataInizioProprieta;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}

	public Date getDtPrimaImmatricolazione() {
		return dtPrimaImmatricolazione;
	}

	public void setDtPrimaImmatricolazione(Date dtPrimaImmatricolazione) {
		this.dtPrimaImmatricolazione = dtPrimaImmatricolazione;
	}

	public BigDecimal getEmissioniCo2() {
		return emissioniCo2;
	}

	public void setEmissioniCo2(BigDecimal emissioniCo2) {
		this.emissioniCo2 = emissioniCo2;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public String getEsenzione() {
		return esenzione;
	}

	public void setEsenzione(String esenzione) {
		this.esenzione = esenzione;
	}

	public String getFlagAnnMassaRimorc() {
		return flagAnnMassaRimorc;
	}

	public void setFlagAnnMassaRimorc(String flagAnnMassaRimorc) {
		this.flagAnnMassaRimorc = flagAnnMassaRimorc;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public BigDecimal getKw() {
		return kw;
	}

	public void setKw(BigDecimal kw) {
		this.kw = kw;
	}

	public BigDecimal getMassaRimorchiabile() {
		return massaRimorchiabile;
	}

	public void setMassaRimorchiabile(BigDecimal massaRimorchiabile) {
		this.massaRimorchiabile = massaRimorchiabile;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroCivico() {
		return numeroCivico;
	}

	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

	public BigDecimal getNumeroAssi() {
		return numeroAssi;
	}

	public void setNumeroAssi(BigDecimal numeroAssi) {
		this.numeroAssi = numeroAssi;
	}

	public BigDecimal getNumeroPosti() {
		return numeroPosti;
	}

	public void setNumeroPosti(BigDecimal numeroPosti) {
		this.numeroPosti = numeroPosti;
	}

	public BigDecimal getPesoComplessivo() {
		return pesoComplessivo;
	}

	public void setPesoComplessivo(BigDecimal pesoComplessivo) {
		this.pesoComplessivo = pesoComplessivo;
	}

	public BigDecimal getPortata() {
		return portata;
	}

	public void setPortata(BigDecimal portata) {
		this.portata = portata;
	}

	public BigDecimal getPotenzaFiscale() {
		return potenzaFiscale;
	}

	public void setPotenzaFiscale(BigDecimal potenzaFiscale) {
		this.potenzaFiscale = potenzaFiscale;
	}

	public String getProvinciaNascita() {
		return provinciaNascita;
	}

	public void setProvinciaNascita(String provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}

	public String getProvinciaResidenza() {
		return provinciaResidenza;
	}

	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}

	public String getProvinciaIstatP() {
		return provinciaIstatP;
	}

	public void setProvinciaIstatP(String provinciaIstatP) {
		this.provinciaIstatP = provinciaIstatP;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getRegioneResidenza() {
		return regioneResidenza;
	}

	public void setRegioneResidenza(String regioneResidenza) {
		this.regioneResidenza = regioneResidenza;
	}

	public String getSospensionePneumatica() {
		return sospensionePneumatica;
	}

	public void setSospensionePneumatica(String sospensionePneumatica) {
		this.sospensionePneumatica = sospensionePneumatica;
	}

	public String getStatoEsenzione() {
		return statoEsenzione;
	}

	public void setStatoEsenzione(String statoEsenzione) {
		this.statoEsenzione = statoEsenzione;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public String getTipoHandicap() {
		return tipoHandicap;
	}

	public void setTipoHandicap(String tipoHandicap) {
		this.tipoHandicap = tipoHandicap;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public String getTipoAlimentazioneImpianto() {
		return tipoAlimentazioneImpianto;
	}

	public void setTipoAlimentazioneImpianto(String tipoAlimentazioneImpianto) {
		this.tipoAlimentazioneImpianto = tipoAlimentazioneImpianto;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public String getChiave() {
		return "" + this.getId() ;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}

