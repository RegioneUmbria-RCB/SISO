package it.webred.rulengine.brick.loadDwh.load.docfa.tablebean;

import java.io.Serializable;
import java.util.Date;

public class DocfaTableDC10 extends DocfaTable implements Serializable {
	
	private Date data;
	private String codiceEnte;
	private String sezione;
	private Long identificativoImmo;
	private String protocolloReg;
	private Date dataReg;
	private String zona;
	private String categoria;
	private String classe;
	private String consistenza;
	private Long superficie;
	private String renditaEuro;
	private String partitaSpeciale;
	private String lotto;
	private String edificio;
	private String scala;
	private String internoUno;
	private String internoDue;
	private String pianoUno;
	private String pianoDue;
	private String pianoTre;
	private String pianoQuattro;
	private String sezioneUrbana;
	private String foglio;
	private String numero;
	private Long denominatore;
	private String subalterno;
	private String edificabilita;
	private String presenzaGraffati;
	private String toponimo;
	private String indirizzo;
	private String civicoUno;
	private String civicoDue;
	private String civicoTre;
	private String ulterioriIndirizzi;
	
	
	//query di inserimento record
	public static final String INSERT_RECORD = "INSERT INTO DC_DOCFA_1_0 VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	public DocfaTableDC10(Date reDataInizioVal, Date reDataFineVal,
			Long reFlagElaborato) {
		
		super(reDataInizioVal, reDataFineVal, reFlagElaborato);
		// TODO Auto-generated constructor stub
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public String getCodiceEnte() {
		return codiceEnte;
	}


	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}


	public String getSezione() {
		return sezione;
	}


	public void setSezione(String sezione) {
		this.sezione = sezione;
	}


	public Long getIdentificativoImmo() {
		return identificativoImmo;
	}


	public void setIdentificativoImmo(Long identificativoImmo) {
		this.identificativoImmo = identificativoImmo;
	}


	public String getProtocolloReg() {
		return protocolloReg;
	}


	public void setProtocolloReg(String protocolloReg) {
		this.protocolloReg = protocolloReg;
	}


	public Date getDataReg() {
		return dataReg;
	}


	public void setDataReg(Date dataReg) {
		this.dataReg = dataReg;
	}


	public String getZona() {
		return zona;
	}


	public void setZona(String zona) {
		this.zona = zona;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	public String getClasse() {
		return classe;
	}


	public void setClasse(String classe) {
		this.classe = classe;
	}


	public String getConsistenza() {
		return consistenza;
	}


	public void setConsistenza(String consistenza) {
		this.consistenza = consistenza;
	}


	public Long getSuperficie() {
		return superficie;
	}


	public void setSuperficie(Long superficie) {
		this.superficie = superficie;
	}


	public String getRenditaEuro() {
		return renditaEuro;
	}


	public void setRenditaEuro(String renditaEuro) {
		this.renditaEuro = renditaEuro;
	}


	public String getPartitaSpeciale() {
		return partitaSpeciale;
	}


	public void setPartitaSpeciale(String partitaSpeciale) {
		this.partitaSpeciale = partitaSpeciale;
	}


	public String getLotto() {
		return lotto;
	}


	public void setLotto(String lotto) {
		this.lotto = lotto;
	}


	public String getEdificio() {
		return edificio;
	}


	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}


	public String getScala() {
		return scala;
	}


	public void setScala(String scala) {
		this.scala = scala;
	}


	public String getInternoUno() {
		return internoUno;
	}


	public void setInternoUno(String internoUno) {
		this.internoUno = internoUno;
	}


	public String getInternoDue() {
		return internoDue;
	}


	public void setInternoDue(String internoDue) {
		this.internoDue = internoDue;
	}


	public String getPianoUno() {
		return pianoUno;
	}


	public void setPianoUno(String pianoUno) {
		this.pianoUno = pianoUno;
	}


	public String getPianoDue() {
		return pianoDue;
	}


	public void setPianoDue(String pianoDue) {
		this.pianoDue = pianoDue;
	}


	public String getPianoTre() {
		return pianoTre;
	}


	public void setPianoTre(String pianoTre) {
		this.pianoTre = pianoTre;
	}


	public String getPianoQuattro() {
		return pianoQuattro;
	}


	public void setPianoQuattro(String pianoQuattro) {
		this.pianoQuattro = pianoQuattro;
	}


	public String getSezioneUrbana() {
		return sezioneUrbana;
	}


	public void setSezioneUrbana(String sezioneUrbana) {
		this.sezioneUrbana = sezioneUrbana;
	}


	public String getFoglio() {
		return foglio;
	}


	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public Long getDenominatore() {
		return denominatore;
	}


	public void setDenominatore(Long denominatore) {
		this.denominatore = denominatore;
	}


	public String getSubalterno() {
		return subalterno;
	}


	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}


	public String getEdificabilita() {
		return edificabilita;
	}


	public void setEdificabilita(String edificabilita) {
		this.edificabilita = edificabilita;
	}


	public String getPresenzaGraffati() {
		return presenzaGraffati;
	}


	public void setPresenzaGraffati(String presenzaGraffati) {
		this.presenzaGraffati = presenzaGraffati;
	}


	public String getToponimo() {
		return toponimo;
	}


	public void setToponimo(String toponimo) {
		this.toponimo = toponimo;
	}


	public String getIndirizzo() {
		return indirizzo;
	}


	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}


	public String getCivicoUno() {
		return civicoUno;
	}


	public void setCivicoUno(String civicoUno) {
		this.civicoUno = civicoUno;
	}


	public String getCivicoDue() {
		return civicoDue;
	}


	public void setCivicoDue(String civicoDue) {
		this.civicoDue = civicoDue;
	}


	public String getCivicoTre() {
		return civicoTre;
	}


	public void setCivicoTre(String civicoTre) {
		this.civicoTre = civicoTre;
	}


	public String getUlterioriIndirizzi() {
		return ulterioriIndirizzi;
	}


	public void setUlterioriIndirizzi(String ulterioriIndirizzi) {
		this.ulterioriIndirizzi = ulterioriIndirizzi;
	}
}
