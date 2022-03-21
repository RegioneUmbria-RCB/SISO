package it.webred.ct.data.access.basic.traffico.dto;

public class TrafficoSearchCriteria {
	
	private String codFiscale;
	private String numVerbale;
	private String targa;
	private String nome;
	private String cognome;
	private String idOrig;
	private String data;
	private String importo;
	private boolean nullCodFisc;
	
	public String getCodFiscale() {
		return codFiscale;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	public String getNumVerbale() {
		return numVerbale;
	}
	public void setNumVerbale(String numVerbale) {
		this.numVerbale = numVerbale;
	}
	public String getTarga() {
		return targa;
	}
	public void setTarga(String targa) {
		this.targa = targa;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public boolean isNullCodFisc() {
		return nullCodFisc;
	}
	public void setNullCodFisc(boolean nullCodFisc) {
		this.nullCodFisc = nullCodFisc;
	}
	public String getIdOrig() {
		return idOrig;
	}
	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getImporto() {
		return importo;
	}
	public void setImporto(String importo) {
		this.importo = importo;
	}
	
}
