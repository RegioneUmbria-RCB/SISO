package it.webred.rulengine.dwh.table;


public class SitImpiantiFognari extends TabellaDwhMultiProv
{
	/*
	private String identificativoImpianto;
	private BigDecimal generatoriNumero;
	private DataDwh dtFileCsv;
	*/
	private String ds_serie_documentale;
	private String tipo_documento;
	private String descrizione_tipo_documento;
	private String numero_busta;
	private String tipo_busta;
	private String tipo_via;
	private String via;
	private String civico;
	private String barrato;
	private String codice_via;
	private String zona;
	private String annotazioni;
	private String nm_faldone;
	private String nm_pratica;
	private String nome_file;

	
	@Override
	public String getValueForCtrHash(){
		return ds_serie_documentale + 
				tipo_documento +
				descrizione_tipo_documento +
		numero_busta +
		tipo_busta +
		tipo_via +
		via +
		civico +
		barrato +
		codice_via +
		zona +
		annotazioni +
		nm_faldone +
		nm_pratica +
		nome_file;
	}//-------------------------------------------------------------------------


	public String getDs_serie_documentale() {
		return ds_serie_documentale;
	}


	public void setDs_serie_documentale(String ds_serie_documentale) {
		this.ds_serie_documentale = ds_serie_documentale;
	}


	public String getTipo_documento() {
		return tipo_documento;
	}


	public void setTipo_documento(String tipo_documento) {
		this.tipo_documento = tipo_documento;
	}


	public String getDescrizione_tipo_documento() {
		return descrizione_tipo_documento;
	}


	public void setDescrizione_tipo_documento(String descrizione_tipo_documento) {
		this.descrizione_tipo_documento = descrizione_tipo_documento;
	}


	public String getNumero_busta() {
		return numero_busta;
	}


	public void setNumero_busta(String numero_busta) {
		this.numero_busta = numero_busta;
	}


	public String getTipo_busta() {
		return tipo_busta;
	}


	public void setTipo_busta(String tipo_busta) {
		this.tipo_busta = tipo_busta;
	}


	public String getTipo_via() {
		return tipo_via;
	}


	public void setTipo_via(String tipo_via) {
		this.tipo_via = tipo_via;
	}


	public String getVia() {
		return via;
	}


	public void setVia(String via) {
		this.via = via;
	}


	public String getCivico() {
		return civico;
	}


	public void setCivico(String civico) {
		this.civico = civico;
	}


	public String getBarrato() {
		return barrato;
	}


	public void setBarrato(String barrato) {
		this.barrato = barrato;
	}


	public String getCodice_via() {
		return codice_via;
	}


	public void setCodice_via(String codice_via) {
		this.codice_via = codice_via;
	}


	public String getZona() {
		return zona;
	}


	public void setZona(String zona) {
		this.zona = zona;
	}


	public String getAnnotazioni() {
		return annotazioni;
	}


	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
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


	public String getNome_file() {
		return nome_file;
	}


	public void setNome_file(String nome_file) {
		this.nome_file = nome_file;
	}


	
}
