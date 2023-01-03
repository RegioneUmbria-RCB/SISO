package it.webred.rulengine.dwh.table;


public class SitSchedeMartelli extends TabellaDwhMultiProv
{
	/*
	private String identificativoImpianto;
	private BigDecimal generatoriNumero;
	private DataDwh dtFileCsv;
	*/
	private String ds_serie_documentale;
	private String fascicolo;
	private String tipo;
	private String toponimo;
	private String civico;
	private String descrizione;
	private String classifica;
	private String sequenziale;
	private String multiplo;
	private String nm_faldone;
	private String nm_pratica;
	private String nome_file;

	
	@Override
	public String getValueForCtrHash(){
		return ds_serie_documentale + 
				fascicolo +
				tipo +
		toponimo +
		civico +
		descrizione +
		classifica +
		sequenziale +
		multiplo +
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


	public String getFascicolo() {
		return fascicolo;
	}


	public void setFascicolo(String fascicolo) {
		this.fascicolo = fascicolo;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getToponimo() {
		return toponimo;
	}


	public void setToponimo(String toponimo) {
		this.toponimo = toponimo;
	}


	public String getCivico() {
		return civico;
	}


	public void setCivico(String civico) {
		this.civico = civico;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getClassifica() {
		return classifica;
	}


	public void setClassifica(String classifica) {
		this.classifica = classifica;
	}


	public String getSequenziale() {
		return sequenziale;
	}


	public void setSequenziale(String sequenziale) {
		this.sequenziale = sequenziale;
	}


	public String getMultiplo() {
		return multiplo;
	}


	public void setMultiplo(String multiplo) {
		this.multiplo = multiplo;
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
