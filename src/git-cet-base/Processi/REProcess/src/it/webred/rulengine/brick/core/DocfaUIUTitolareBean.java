package it.webred.rulengine.brick.core;

public class DocfaUIUTitolareBean {
	
	private String protocolloReg;
	private String dataFornitura;
	private String foglio;
	private String particella;
	private String subalterno;
	private String denominazione;
	private String nome;
	private String codiceFiscale;
	private String partitaIva;
	private String dataNascita;
	private String comuneNascita;
	private String provinciaNascita;
	private String sesso;
	private String comuneResidenza;
	private String indirizzoResidenza;
	private String civicoResidenza;
	private String tipoPossesso;
	private String percentualePossesso;
	private String flagPersona;
	private String capResidenza;
	private String provinciaResidenza;
	private String flagProvenienzaInfo; // A = anagrafe T = tributi
	//rappresentante legale (dati da SIATEL)
	private String denominazioneRL;
	private String nomeRL;
	private String codiceFiscaleRL;
	private String partitaIvaRL;
	private String dataNascitaRL;
	private String comuneNascitaRL;
	private String provinciaNascitaRL;
	private String sessoRL;
	private String capResidenzaRL;
	private String comuneResidenzaRL;
	private String indirizzoResidenzaRL;
	private String civicoResidenzaRL;
	private String provinciaResidenzaRL;
	
	
	public DocfaUIUTitolareBean() {
		
		protocolloReg = "";
		dataFornitura = "";
		foglio = "";
		particella = "";
		subalterno = "";
		denominazione = "";
		nome = "";
		codiceFiscale = "";
		partitaIva = "";
		dataNascita = "";
		comuneNascita = "";
		provinciaNascita = "";
		sesso = "";
		comuneResidenza = "";
		indirizzoResidenza = "";
		civicoResidenza = "";
		tipoPossesso = "";
		percentualePossesso = ""; 
		flagPersona = "";
		capResidenza = "";
		provinciaResidenza = "";
		flagProvenienzaInfo = "";
		denominazioneRL = "";
		nomeRL = "";
		codiceFiscaleRL = "";
		partitaIvaRL = "";
		dataNascitaRL = "";
		comuneNascitaRL = "";
		provinciaNascitaRL = "";
		sessoRL = "";
		capResidenzaRL = "";
		comuneResidenzaRL = "";
		indirizzoResidenzaRL = "";
		civicoResidenzaRL = "";
		provinciaResidenzaRL = "";
	}
	
	public String getDataFornitura() {
		return dataFornitura;
	}

	public void setDataFornitura(String dataFornitura) {
		this.dataFornitura = dataFornitura;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getProtocolloReg() {
		return protocolloReg;
	}

	public void setProtocolloReg(String protocolloReg) {
		this.protocolloReg = protocolloReg;
	}

	public String getSubalterno() {
		return subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getCivicoResidenza() {
		return civicoResidenza;
	}

	public void setCivicoResidenza(String civicoResidenza) {
		this.civicoResidenza = civicoResidenza;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
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

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}

	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getPercentualePossesso() {
		return percentualePossesso;
	}

	public void setPercentualePossesso(String percentualePossesso) {
		this.percentualePossesso = percentualePossesso;
	}

	public String getProvinciaNascita() {
		return provinciaNascita;
	}

	public void setProvinciaNascita(String provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getTipoPossesso() {
		return tipoPossesso;
	}

	public void setTipoPossesso(String tipoPossesso) {
		this.tipoPossesso = tipoPossesso;
	}

	public String getFlagPersona() {
		return flagPersona;
	}

	public void setFlagPersona(String flagPersona) {
		this.flagPersona = flagPersona;
	}

	public String getCapResidenza() {
		return capResidenza;
	}

	public void setCapResidenza(String capResidenza) {
		this.capResidenza = capResidenza;
	}

	public String getProvinciaResidenza() {
		return provinciaResidenza;
	}

	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}

	public String getFlagProvenienzaInfo()
	{
		return flagProvenienzaInfo;
	}

	public void setFlagProvenienzaInfo(String flagProvenienzaInfo)
	{
		this.flagProvenienzaInfo = flagProvenienzaInfo;
	}

	public String getCivicoResidenzaRL()
	{
		return civicoResidenzaRL;
	}

	public void setCivicoResidenzaRL(String civicoResidenzaRL)
	{
		this.civicoResidenzaRL = civicoResidenzaRL;
	}

	public String getCodiceFiscaleRL()
	{
		return codiceFiscaleRL;
	}

	public void setCodiceFiscaleRL(String codiceFiscaleRL)
	{
		this.codiceFiscaleRL = codiceFiscaleRL;
	}

	public String getComuneNascitaRL()
	{
		return comuneNascitaRL;
	}

	public void setComuneNascitaRL(String comuneNascitaRL)
	{
		this.comuneNascitaRL = comuneNascitaRL;
	}

	public String getComuneResidenzaRL()
	{
		return comuneResidenzaRL;
	}

	public void setComuneResidenzaRL(String comuneResidenzaRL)
	{
		this.comuneResidenzaRL = comuneResidenzaRL;
	}

	public String getDataNascitaRL()
	{
		return dataNascitaRL;
	}

	public void setDataNascitaRL(String dataNascitaRL)
	{
		this.dataNascitaRL = dataNascitaRL;
	}

	public String getDenominazioneRL()
	{
		return denominazioneRL;
	}

	public void setDenominazioneRL(String denominazioneRL)
	{
		this.denominazioneRL = denominazioneRL;
	}

	public String getIndirizzoResidenzaRL()
	{
		return indirizzoResidenzaRL;
	}

	public void setIndirizzoResidenzaRL(String indirizzoResidenzaRL)
	{
		this.indirizzoResidenzaRL = indirizzoResidenzaRL;
	}

	public String getNomeRL()
	{
		return nomeRL;
	}

	public void setNomeRL(String nomeRL)
	{
		this.nomeRL = nomeRL;
	}

	public String getPartitaIvaRL()
	{
		return partitaIvaRL;
	}

	public void setPartitaIvaRL(String partitaIvaRL)
	{
		this.partitaIvaRL = partitaIvaRL;
	}

	public String getProvinciaNascitaRL()
	{
		return provinciaNascitaRL;
	}

	public void setProvinciaNascitaRL(String provinciaNascitaRL)
	{
		this.provinciaNascitaRL = provinciaNascitaRL;
	}

	public String getSessoRL()
	{
		return sessoRL;
	}

	public void setSessoRL(String sessoRL)
	{
		this.sessoRL = sessoRL;
	}

	public String getCapResidenzaRL()
	{
		return capResidenzaRL;
	}

	public void setCapResidenzaRL(String capResidenzaRL)
	{
		this.capResidenzaRL = capResidenzaRL;
	}

	public String getProvinciaResidenzaRL()
	{
		return provinciaResidenzaRL;
	}

	public void setProvinciaResidenzaRL(String provinciaResidenzaRL)
	{
		this.provinciaResidenzaRL = provinciaResidenzaRL;
	}

}
