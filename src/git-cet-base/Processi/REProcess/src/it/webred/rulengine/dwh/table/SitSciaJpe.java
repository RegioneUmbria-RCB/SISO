package it.webred.rulengine.dwh.table;


public class SitSciaJpe extends TabellaDwhMultiProv
{
	private String numero_pratica;
	private String data_apertura;
	private String data_chiusura;
	private String macrotipo;
	private String procedimento;
	private String oggetto;
	private String data_protocollo;
	private String numero_protocollo;
	private String referenti;
	private String ubicazioni;
	private String fascicolo;
	private String dati_catastali_terreni;
	private String dati_catastali_fabbricati;
	private String tipo_opera;
	private String responsabili;
	private String tecnici;
	private String situazione_pratica;
	private String gg_istruttoria;
	private String gg_prop_motiv;
	private String gg_interruzione;
	private String gg_sospensione;	
	private String imprese;
	private String dataAvvioProc;
	private String idPratica;
	private String statoPratica;
	private String note;

	
	@Override
	public String getValueForCtrHash(){
		return numero_pratica + 
		data_apertura +
		data_chiusura +
		macrotipo +
		procedimento +
		oggetto +
		data_protocollo +
		numero_protocollo +
		referenti +
		ubicazioni +
		fascicolo +
		dati_catastali_terreni +
		dati_catastali_fabbricati +
		tipo_opera +
		responsabili +
		tecnici +
		situazione_pratica +
		gg_istruttoria +
		gg_prop_motiv +
		gg_interruzione +
		gg_sospensione +
		imprese +
		dataAvvioProc +
		idPratica +
		statoPratica +
		note;
	}


	public String getNumero_pratica() {
		return numero_pratica;
	}


	public void setNumero_pratica(String numero_pratica) {
		this.numero_pratica = numero_pratica;
	}


	public String getData_apertura() {
		return data_apertura;
	}


	public void setData_apertura(String data_apertura) {
		this.data_apertura = data_apertura;
	}


	public String getData_chiusura() {
		return data_chiusura;
	}


	public void setData_chiusura(String data_chiusura) {
		this.data_chiusura = data_chiusura;
	}


	public String getMacrotipo() {
		return macrotipo;
	}


	public void setMacrotipo(String macrotipo) {
		this.macrotipo = macrotipo;
	}


	public String getProcedimento() {
		return procedimento;
	}


	public void setProcedimento(String procedimento) {
		this.procedimento = procedimento;
	}


	public String getOggetto() {
		return oggetto;
	}


	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}


	public String getData_protocollo() {
		return data_protocollo;
	}


	public void setData_protocollo(String data_protocollo) {
		this.data_protocollo = data_protocollo;
	}


	public String getNumero_protocollo() {
		return numero_protocollo;
	}


	public void setNumero_protocollo(String numero_protocollo) {
		this.numero_protocollo = numero_protocollo;
	}


	public String getReferenti() {
		return referenti;
	}


	public void setReferenti(String referenti) {
		this.referenti = referenti;
	}


	public String getUbicazioni() {
		return ubicazioni;
	}


	public void setUbicazioni(String ubicazioni) {
		this.ubicazioni = ubicazioni;
	}


	public String getFascicolo() {
		return fascicolo;
	}


	public void setFascicolo(String fascicolo) {
		this.fascicolo = fascicolo;
	}


	public String getDati_catastali_terreni() {
		return dati_catastali_terreni;
	}


	public void setDati_catastali_terreni(String dati_catastali_terreni) {
		this.dati_catastali_terreni = dati_catastali_terreni;
	}


	public String getDati_catastali_fabbricati() {
		return dati_catastali_fabbricati;
	}


	public void setDati_catastali_fabbricati(String dati_catastali_fabbricati) {
		this.dati_catastali_fabbricati = dati_catastali_fabbricati;
	}


	public String getTipo_opera() {
		return tipo_opera;
	}


	public void setTipo_opera(String tipo_opera) {
		this.tipo_opera = tipo_opera;
	}


	public String getResponsabili() {
		return responsabili;
	}


	public void setResponsabili(String responsabili) {
		this.responsabili = responsabili;
	}


	public String getTecnici() {
		return tecnici;
	}


	public void setTecnici(String tecnici) {
		this.tecnici = tecnici;
	}


	public String getSituazione_pratica() {
		return situazione_pratica;
	}


	public void setSituazione_pratica(String situazione_pratica) {
		this.situazione_pratica = situazione_pratica;
	}


	public String getGg_istruttoria() {
		return gg_istruttoria;
	}


	public void setGg_istruttoria(String gg_istruttoria) {
		this.gg_istruttoria = gg_istruttoria;
	}


	public String getGg_prop_motiv() {
		return gg_prop_motiv;
	}


	public void setGg_prop_motiv(String gg_prop_motiv) {
		this.gg_prop_motiv = gg_prop_motiv;
	}


	public String getGg_interruzione() {
		return gg_interruzione;
	}


	public void setGg_interruzione(String gg_interruzione) {
		this.gg_interruzione = gg_interruzione;
	}


	public String getGg_sospensione() {
		return gg_sospensione;
	}


	public void setGg_sospensione(String gg_sospensione) {
		this.gg_sospensione = gg_sospensione;
	}


	public String getImprese() {
		return imprese;
	}


	public void setImprese(String imprese) {
		this.imprese = imprese;
	}


	public String getDataAvvioProc() {
		return dataAvvioProc;
	}


	public void setDataAvvioProc(String dataAvvioProc) {
		this.dataAvvioProc = dataAvvioProc;
	}


	public String getIdPratica() {
		return idPratica;
	}


	public void setIdPratica(String idPratica) {
		this.idPratica = idPratica;
	}


	public String getStatoPratica() {
		return statoPratica;
	}


	public void setStatoPratica(String statoPratica) {
		this.statoPratica = statoPratica;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}
	
}
