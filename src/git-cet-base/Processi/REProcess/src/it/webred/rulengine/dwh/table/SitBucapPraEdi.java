package it.webred.rulengine.dwh.table;


public class SitBucapPraEdi extends TabellaDwhMultiProv
{
	/*
	private String identificativoImpianto;
	private BigDecimal generatoriNumero;
	private DataDwh dtFileCsv;
	*/
	private String via;
	private String sede;
	private String descrizione_sede;
	private String periodo_archivio;
	private String descrizione_periodo_archivio;
	private String anno_protocollo;
	private String id_scatola;
	private String protocollo;
	private String numero_deposito;
	private String numero_civico;
	private String committente;
	private String impresa;
	private String nm_faldone;
	private String nm_pratica;
	private String magazzino;
	private String scaffale;
	private String ripiano;
	private String buca;
	private String fila;
	private String immagine_presente;
	private String data_inserimento;
	private String data_ultimo_rientro;
	private String tipo_pratica;
	private String richiedente;
	private String descrizione_richiedente;
	private String cod_via;
	private String status;
	
	@Override
	public String getValueForCtrHash(){
		return via +
		sede +
		descrizione_sede +
		periodo_archivio +
		descrizione_periodo_archivio +
		anno_protocollo +
		id_scatola +
		protocollo +
		numero_deposito +
		numero_civico +
		committente +
		impresa +
		nm_faldone +
		nm_pratica +
		magazzino +
		scaffale +
		ripiano +
		buca +
		fila +
		immagine_presente +
		data_inserimento +
		data_ultimo_rientro +
		tipo_pratica +
		richiedente +
		descrizione_richiedente + 
		cod_via +
		status;
	}


	public String getCod_via() {
		return cod_via;
	}


	public void setCod_via(String cod_via) {
		this.cod_via = cod_via;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getVia() {
		return via;
	}


	public void setVia(String via) {
		this.via = via;
	}


	public String getSede() {
		return sede;
	}


	public void setSede(String sede) {
		this.sede = sede;
	}


	public String getDescrizione_sede() {
		return descrizione_sede;
	}


	public void setDescrizione_sede(String descrizione_sede) {
		this.descrizione_sede = descrizione_sede;
	}


	public String getPeriodo_archivio() {
		return periodo_archivio;
	}


	public void setPeriodo_archivio(String periodo_archivio) {
		this.periodo_archivio = periodo_archivio;
	}


	public String getDescrizione_periodo_archivio() {
		return descrizione_periodo_archivio;
	}


	public void setDescrizione_periodo_archivio(String descrizione_periodo_archivio) {
		this.descrizione_periodo_archivio = descrizione_periodo_archivio;
	}


	public String getAnno_protocollo() {
		return anno_protocollo;
	}


	public void setAnno_protocollo(String anno_protocollo) {
		this.anno_protocollo = anno_protocollo;
	}


	public String getId_scatola() {
		return id_scatola;
	}


	public void setId_scatola(String id_scatola) {
		this.id_scatola = id_scatola;
	}


	public String getProtocollo() {
		return protocollo;
	}


	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}


	public String getNumero_deposito() {
		return numero_deposito;
	}


	public void setNumero_deposito(String numero_deposito) {
		this.numero_deposito = numero_deposito;
	}


	public String getNumero_civico() {
		return numero_civico;
	}


	public void setNumero_civico(String numero_civico) {
		this.numero_civico = numero_civico;
	}


	public String getCommittente() {
		return committente;
	}


	public void setCommittente(String committente) {
		this.committente = committente;
	}


	public String getImpresa() {
		return impresa;
	}


	public void setImpresa(String impresa) {
		this.impresa = impresa;
	}


	public String getNm_faldone() {
		return nm_faldone;
	}


	public void setNm_faldone(String nm_faldone) {
		this.nm_faldone = nm_faldone;
	}


	public String getNm_pratica() {
		return nm_pratica;
	}


	public void setNm_pratica(String nm_pratica) {
		this.nm_pratica = nm_pratica;
	}


	public String getMagazzino() {
		return magazzino;
	}


	public void setMagazzino(String magazzino) {
		this.magazzino = magazzino;
	}


	public String getScaffale() {
		return scaffale;
	}


	public void setScaffale(String scaffale) {
		this.scaffale = scaffale;
	}


	public String getRipiano() {
		return ripiano;
	}


	public void setRipiano(String ripiano) {
		this.ripiano = ripiano;
	}


	public String getBuca() {
		return buca;
	}


	public void setBuca(String buca) {
		this.buca = buca;
	}


	public String getFila() {
		return fila;
	}


	public void setFila(String fila) {
		this.fila = fila;
	}


	public String getImmagine_presente() {
		return immagine_presente;
	}


	public void setImmagine_presente(String immagine_presente) {
		this.immagine_presente = immagine_presente;
	}


	public String getData_inserimento() {
		return data_inserimento;
	}


	public void setData_inserimento(String data_inserimento) {
		this.data_inserimento = data_inserimento;
	}


	public String getData_ultimo_rientro() {
		return data_ultimo_rientro;
	}


	public void setData_ultimo_rientro(String data_ultimo_rientro) {
		this.data_ultimo_rientro = data_ultimo_rientro;
	}


	public String getTipo_pratica() {
		return tipo_pratica;
	}


	public void setTipo_pratica(String tipo_pratica) {
		this.tipo_pratica = tipo_pratica;
	}


	public String getRichiedente() {
		return richiedente;
	}


	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}


	public String getDescrizione_richiedente() {
		return descrizione_richiedente;
	}


	public void setDescrizione_richiedente(String descrizione_richiedente) {
		this.descrizione_richiedente = descrizione_richiedente;
	}


	

	
	
}
