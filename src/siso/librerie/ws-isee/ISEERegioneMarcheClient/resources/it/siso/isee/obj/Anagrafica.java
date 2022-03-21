package it.siso.isee.obj;

import java.io.Serializable;

public class Anagrafica extends AnagraficaBase implements Serializable {

	/***
	 * Dati disponibili nella dichiarazione
	 * @return
	 */
	private String dataNascita;
	private String sesso;
	private String cittadinanza;
	private String provinciaNascita;
	private String comuneNascita;
	private String codiceComuneNascita;
	
	
	
	
	
	public String getCodiceComuneNascita() {
		return codiceComuneNascita;
	}
	public void setCodiceComuneNascita(String codiceComuneNascita) {
		this.codiceComuneNascita = codiceComuneNascita;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getCittadinanza() {
		return cittadinanza;
	}
	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
	public String getProvinciaNascita() {
		return provinciaNascita;
	}
	public void setProvinciaNascita(String provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}
	public String getComuneNascita() {
		return comuneNascita;
	}
	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	
	
	  
}
