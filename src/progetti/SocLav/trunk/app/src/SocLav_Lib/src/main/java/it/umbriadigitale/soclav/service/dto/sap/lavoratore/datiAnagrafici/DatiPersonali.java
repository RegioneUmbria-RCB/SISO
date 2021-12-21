package it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAnagrafici;

import java.io.Serializable;
import java.util.Date;

public class DatiPersonali implements Serializable {

/*
 * <datipersonali>
			<codicefiscale>BTTCLD87L62E690I</codicefiscale>
			<cognome>BATTISTONI</cognome>
			<nome>CLAUDIA</nome>
			<sesso>F</sesso>
			<datanascita>1992-12-23</datanascita>
			<codcomune>A944</codcomune>
			<codcittadinanza>000</codcittadinanza> 
	</datipersonali>        
 * */
	
	  private String codicefiscale;
	  private String cognome;
	  private String nome;
	  private String sesso;
	  private Date datanascita;
	  private String codcomune;
	  private String codcittadinanza;
	  
	  private String desLuogoNascita;
	  private String desCittadinanza;
	  
	public String getCodicefiscale() {
		return codicefiscale;
	}
	public void setCodicefiscale(String codicefiscale) {
		this.codicefiscale = codicefiscale;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public Date getDatanascita() {
		return datanascita;
	}
	public void setDatanascita(Date datanascita) {
		this.datanascita = datanascita;
	}
	public String getCodcomune() {
		return codcomune;
	}
	public void setCodcomune(String codcomune) {
		this.codcomune = codcomune;
	}
	public String getCodcittadinanza() {
		return codcittadinanza;
	}
	public void setCodcittadinanza(String codcittadinanza) {
		this.codcittadinanza = codcittadinanza;
	}
	public String getDesLuogoNascita() {
		return desLuogoNascita;
	}
	public void setDesLuogoNascita(String desLuogoNascita) {
		this.desLuogoNascita = desLuogoNascita;
	}
	public String getDesCittadinanza() {
		return desCittadinanza;
	}
	public void setDesCittadinanza(String desCittadinanza) {
		this.desCittadinanza = desCittadinanza;
	}
}
