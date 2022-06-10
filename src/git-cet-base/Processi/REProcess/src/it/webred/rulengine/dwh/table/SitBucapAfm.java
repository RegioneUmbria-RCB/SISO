package it.webred.rulengine.dwh.table;


public class SitBucapAfm extends TabellaDwhMultiProv
{
	/*
	private String identificativoImpianto;
	private BigDecimal generatoriNumero;
	private DataDwh dtFileCsv;
	*/
	private String numero_work_flow__verifica;
	private String via;
	private String sede;
	private String anno_workflow;
	private String descrizione_sede;
	private String numero_pg_documento;
	private String esibente;
	private String codice_classificazione;
	private String anno_work_flow__verifica;
	private String ds_codice_classificazione;
	private String numero_civico;
	private String id_scatola;
	private String piano_di_intervento;
	private String numero_workflow;
	private String anno_pg_documento;
	private String dati_catastali;
	private String periodo_archivio;
	private String descrizione_periodo_archivio;
	private String codice_settore;
	private String descrizione_codice_settore;
	private String oggetto_visura;
	private String nm_faldone;
	private String nm_pratica;
	private String magazzino;
	private String scaffale;
	private String ripiano;
	private String buca;
	private String data_inserimento;
	private String fila;	
	private String immagine_presente;
	private String data_ultimo_rientro;
	private String tipo_pratica;
	private String richiedente;
	private String descrizione_richiedente;
	private String cod_via;

	
	@Override
	public String getValueForCtrHash(){
		return numero_work_flow__verifica + via +
		sede +
		anno_workflow +
		descrizione_sede +
		numero_pg_documento +
		esibente +
		codice_classificazione +
		anno_work_flow__verifica +
		ds_codice_classificazione +
		numero_civico +
		id_scatola +
		piano_di_intervento +
		numero_workflow +
		anno_pg_documento +
		dati_catastali +
		periodo_archivio +
		descrizione_periodo_archivio +
		codice_settore +
		descrizione_codice_settore +
		oggetto_visura +
		nm_faldone +
		nm_pratica +
		magazzino +
		scaffale +
		ripiano + 
		buca +
		data_inserimento +
		fila + immagine_presente + data_ultimo_rientro + tipo_pratica + richiedente + descrizione_richiedente + cod_via;
	}


	public String getNumero_work_flow__verifica() {
		return numero_work_flow__verifica;
	}


	public void setNumero_work_flow__verifica(String numero_work_flow__verifica) {
		this.numero_work_flow__verifica = numero_work_flow__verifica;
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


	public String getAnno_workflow() {
		return anno_workflow;
	}


	public void setAnno_workflow(String anno_workflow) {
		this.anno_workflow = anno_workflow;
	}


	public String getDescrizione_sede() {
		return descrizione_sede;
	}


	public void setDescrizione_sede(String descrizione_sede) {
		this.descrizione_sede = descrizione_sede;
	}


	public String getNumero_pg_documento() {
		return numero_pg_documento;
	}


	public void setNumero_pg_documento(String numero_pg_documento) {
		this.numero_pg_documento = numero_pg_documento;
	}


	public String getEsibente() {
		return esibente;
	}


	public void setEsibente(String esibente) {
		this.esibente = esibente;
	}


	public String getCodice_classificazione() {
		return codice_classificazione;
	}


	public void setCodice_classificazione(String codice_classificazione) {
		this.codice_classificazione = codice_classificazione;
	}


	public String getAnno_work_flow__verifica() {
		return anno_work_flow__verifica;
	}


	public void setAnno_work_flow__verifica(String anno_work_flow__verifica) {
		this.anno_work_flow__verifica = anno_work_flow__verifica;
	}


	public String getDs_codice_classificazione() {
		return ds_codice_classificazione;
	}


	public void setDs_codice_classificazione(String ds_codice_classificazione) {
		this.ds_codice_classificazione = ds_codice_classificazione;
	}


	public String getNumero_civico() {
		return numero_civico;
	}


	public void setNumero_civico(String numero_civico) {
		this.numero_civico = numero_civico;
	}


	public String getId_scatola() {
		return id_scatola;
	}


	public void setId_scatola(String id_scatola) {
		this.id_scatola = id_scatola;
	}


	public String getPiano_di_intervento() {
		return piano_di_intervento;
	}


	public void setPiano_di_intervento(String piano_di_intervento) {
		this.piano_di_intervento = piano_di_intervento;
	}


	public String getNumero_workflow() {
		return numero_workflow;
	}


	public void setNumero_workflow(String numero_workflow) {
		this.numero_workflow = numero_workflow;
	}


	public String getAnno_pg_documento() {
		return anno_pg_documento;
	}


	public void setAnno_pg_documento(String anno_pg_documento) {
		this.anno_pg_documento = anno_pg_documento;
	}


	public String getDati_catastali() {
		return dati_catastali;
	}


	public void setDati_catastali(String dati_catastali) {
		this.dati_catastali = dati_catastali;
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


	public String getCodice_settore() {
		return codice_settore;
	}


	public void setCodice_settore(String codice_settore) {
		this.codice_settore = codice_settore;
	}


	public String getDescrizione_codice_settore() {
		return descrizione_codice_settore;
	}


	public void setDescrizione_codice_settore(String descrizione_codice_settore) {
		this.descrizione_codice_settore = descrizione_codice_settore;
	}


	public String getOggetto_visura() {
		return oggetto_visura;
	}


	public void setOggetto_visura(String oggetto_visura) {
		this.oggetto_visura = oggetto_visura;
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


	public String getData_inserimento() {
		return data_inserimento;
	}


	public void setData_inserimento(String data_inserimento) {
		this.data_inserimento = data_inserimento;
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


	public String getCod_via() {
		return cod_via;
	}


	public void setCod_via(String cod_via) {
		this.cod_via = cod_via;
	}


	

	
	
}
