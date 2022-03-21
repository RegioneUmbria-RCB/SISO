package it.webred.rulengine.brick.bolli.bean;

import java.io.Serializable;
import java.sql.Date;

public class BolloVeicoloBean implements Serializable{

	private static final long serialVersionUID = -1611403333091622999L;
	
	private Long id = null;
	private String targa = "";
	private String uso = "";
	private String destinazione = "";
	private String portata = null;
	private String flagAnnMassaRimorc = "";
	private String cilindrata = null;
	private String alimentazione = "";
	private String massaRimorchiabile = null;
	private String numeroPosti = null;
	private String tipoAlimentazioneImpianto = "";
	private String kw = null;
	private String dtPrimaImmatricolazione = null;
	private String numeroAssi = null;
	private String codSiglaEuro = "";
	private String emissioniCo2 = null;
	private String pesoComplessivo = null;
	private String codiceTelaio = "";
	private String codiceCarrozzeria = "";
	private String potenzaFiscale = null;
	private String sospensionePneumatica = "";
	private String comuneIstatC = "";
	private String provinciaIstatP = "";
	private String tipoSoggetto = "";
	private String codiceFiscalePiva = "";
	private String cognome = "";
	private String ragioneSociale = "";
	private String nome = "";
	private String codiceSesso = "";
	private String dataNascita = null;
	private String comuneNascita = "";
	private String provinciaNascita = "";
	private String comuneResidenza = "";
	private String provinciaResidenza = "";
	private String indirizzo = "";
	private String numeroCivico = "";
	private String cap = "";
	private String regioneResidenza = "";
	private String dataInizioProprieta = null;
	private String dataFineProprieta = null;
	private String esenzione = "";
	private String dataInizioEsenzione = null;
	private String dataFineEsenzione = null;
	private String statoEsenzione = "";
	private String tipoHandicap = "";
	private String ente = "";
	private Date dataInserimento = null;

	public BolloVeicoloBean() {
	}//-------------------------------------------------------------------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public String getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}

	public String getPortata() {
		return portata;
	}

	public void setPortata(String portata) {
		this.portata = portata;
	}

	public String getFlagAnnMassaRimorc() {
		return flagAnnMassaRimorc;
	}

	public void setFlagAnnMassaRimorc(String flagAnnMassaRimorc) {
		this.flagAnnMassaRimorc = flagAnnMassaRimorc;
	}

	public String getCilindrata() {
		return cilindrata;
	}

	public void setCilindrata(String cilindrata) {
		this.cilindrata = cilindrata;
	}

	public String getAlimentazione() {
		return alimentazione;
	}

	public void setAlimentazione(String alimentazione) {
		this.alimentazione = alimentazione;
	}

	public String getMassaRimorchiabile() {
		return massaRimorchiabile;
	}

	public void setMassaRimorchiabile(String massaRimorchiabile) {
		this.massaRimorchiabile = massaRimorchiabile;
	}

	public String getNumeroPosti() {
		return numeroPosti;
	}

	public void setNumeroPosti(String numeroPosti) {
		this.numeroPosti = numeroPosti;
	}

	public String getTipoAlimentazioneImpianto() {
		return tipoAlimentazioneImpianto;
	}

	public void setTipoAlimentazioneImpianto(String tipoAlimentazioneImpianto) {
		this.tipoAlimentazioneImpianto = tipoAlimentazioneImpianto;
	}

	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}

	public String getDtPrimaImmatricolazione() {
		return dtPrimaImmatricolazione;
	}

	public void setDtPrimaImmatricolazione(String dtPrimaImmatricolazione) {
		this.dtPrimaImmatricolazione = dtPrimaImmatricolazione;
	}

	public String getNumeroAssi() {
		return numeroAssi;
	}

	public void setNumeroAssi(String numeroAssi) {
		this.numeroAssi = numeroAssi;
	}

	public String getCodSiglaEuro() {
		return codSiglaEuro;
	}

	public void setCodSiglaEuro(String codSiglaEuro) {
		this.codSiglaEuro = codSiglaEuro;
	}

	public String getEmissioniCo2() {
		return emissioniCo2;
	}

	public void setEmissioniCo2(String emissioniCo2) {
		this.emissioniCo2 = emissioniCo2;
	}

	public String getPesoComplessivo() {
		return pesoComplessivo;
	}

	public void setPesoComplessivo(String pesoComplessivo) {
		this.pesoComplessivo = pesoComplessivo;
	}

	public String getCodiceTelaio() {
		return codiceTelaio;
	}

	public void setCodiceTelaio(String codiceTelaio) {
		this.codiceTelaio = codiceTelaio;
	}

	public String getCodiceCarrozzeria() {
		return codiceCarrozzeria;
	}

	public void setCodiceCarrozzeria(String codiceCarrozzeria) {
		this.codiceCarrozzeria = codiceCarrozzeria;
	}

	public String getPotenzaFiscale() {
		return potenzaFiscale;
	}

	public void setPotenzaFiscale(String potenzaFiscale) {
		this.potenzaFiscale = potenzaFiscale;
	}

	public String getSospensionePneumatica() {
		return sospensionePneumatica;
	}

	public void setSospensionePneumatica(String sospensionePneumatica) {
		this.sospensionePneumatica = sospensionePneumatica;
	}

	public String getComuneIstatC() {
		return comuneIstatC;
	}

	public void setComuneIstatC(String comuneIstatC) {
		this.comuneIstatC = comuneIstatC;
	}

	public String getProvinciaIstatP() {
		return provinciaIstatP;
	}

	public void setProvinciaIstatP(String provinciaIstatP) {
		this.provinciaIstatP = provinciaIstatP;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public String getCodiceFiscalePiva() {
		return codiceFiscalePiva;
	}

	public void setCodiceFiscalePiva(String codiceFiscalePiva) {
		this.codiceFiscalePiva = codiceFiscalePiva;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodiceSesso() {
		return codiceSesso;
	}

	public void setCodiceSesso(String codiceSesso) {
		this.codiceSesso = codiceSesso;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getProvinciaNascita() {
		return provinciaNascita;
	}

	public void setProvinciaNascita(String provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getProvinciaResidenza() {
		return provinciaResidenza;
	}

	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNumeroCivico() {
		return numeroCivico;
	}

	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getRegioneResidenza() {
		return regioneResidenza;
	}

	public void setRegioneResidenza(String regioneResidenza) {
		this.regioneResidenza = regioneResidenza;
	}

	public String getDataInizioProprieta() {
		return dataInizioProprieta;
	}

	public void setDataInizioProprieta(String dataInizioProprieta) {
		this.dataInizioProprieta = dataInizioProprieta;
	}

	public String getDataFineProprieta() {
		return dataFineProprieta;
	}

	public void setDataFineProprieta(String dataFineProprieta) {
		this.dataFineProprieta = dataFineProprieta;
	}

	public String getEsenzione() {
		return esenzione;
	}

	public void setEsenzione(String esenzione) {
		this.esenzione = esenzione;
	}

	public String getDataInizioEsenzione() {
		return dataInizioEsenzione;
	}

	public void setDataInizioEsenzione(String dataInizioEsenzione) {
		this.dataInizioEsenzione = dataInizioEsenzione;
	}

	public String getDataFineEsenzione() {
		return dataFineEsenzione;
	}

	public void setDataFineEsenzione(String dataFineEsenzione) {
		this.dataFineEsenzione = dataFineEsenzione;
	}

	public String getStatoEsenzione() {
		return statoEsenzione;
	}

	public void setStatoEsenzione(String statoEsenzione) {
		this.statoEsenzione = statoEsenzione;
	}

	public String getTipoHandicap() {
		return tipoHandicap;
	}

	public void setTipoHandicap(String tipoHandicap) {
		this.tipoHandicap = tipoHandicap;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	

}
