package it.webred.cs.csa.ejb.dto.siru;

import java.io.Serializable;

public class SiruDominioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codiceSiru;
	
	private String codiceSiso;
	
	private String descrizione;
	
	private String versione;  //SISO-850

	public SiruDominioDTO(String codice, String descrizione, String codiceSiso) {
		this.codiceSiru = codice;
		this.descrizione = descrizione;
		this.codiceSiso = codiceSiso;
	}
	public SiruDominioDTO(String codice, String descrizione,String versione, String codiceSiso) {
		this.codiceSiru = codice;
		this.descrizione = descrizione;
		this.versione = versione;
		this.codiceSiso = codiceSiso;
	}
		
	public String getCodiceSiru() {
		return codiceSiru;
	}
	public void setCodiceSiru(String codiceSiru) {
		this.codiceSiru = codiceSiru;
	}
	public String getCodiceSiso() {
		return codiceSiso;
	}
	public void setCodiceSiso(String codiceSiso) {
		this.codiceSiso = codiceSiso;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	//SISO-850
	public String getVersione() {
		return versione;
	}
	public void setVersione(String versione) {
		this.versione = versione;
	}
	//SISO-850
	
}
