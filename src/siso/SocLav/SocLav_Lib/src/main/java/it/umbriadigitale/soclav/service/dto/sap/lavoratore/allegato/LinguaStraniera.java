package it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
@XmlAccessorType(XmlAccessType.PROPERTY)
public class LinguaStraniera implements Serializable {
	
/*	<codlingua>001</codlingua>    
	<codlivelloletto>B1</codlivelloletto>    
	<codlivelloscritto>B1</codlivelloscritto>    
	<codlivelloparlato>B1</codlivelloparlato> */
	
	private String codlingua;
	private String codlivelloletto;
	private String codlivelloscritto;
	private String codlivelloparlato;
	
	private String deslingua;
	private String deslivelloletto;
	private String deslivelloparlato;
	private String deslivelloscritto;
	
	public String getCodlingua() {
		return codlingua;
	}
	public void setCodlingua(String codlingua) {
		this.codlingua = codlingua;
	}
	public String getCodlivelloletto() {
		return codlivelloletto;
	}
	public void setCodlivelloletto(String codlivelloletto) {
		this.codlivelloletto = codlivelloletto;
	}
	public String getCodlivelloscritto() {
		return codlivelloscritto;
	}
	public void setCodlivelloscritto(String codlivelloscritto) {
		this.codlivelloscritto = codlivelloscritto;
	}
	public String getCodlivelloparlato() {
		return codlivelloparlato;
	}
	public void setCodlivelloparlato(String codlivelloparlato) {
		this.codlivelloparlato = codlivelloparlato;
	}
	public String getDeslingua() {
		return deslingua;
	}
	public void setDeslingua(String deslingua) {
		this.deslingua = deslingua;
	}
	public String getDeslivelloletto() {
		return deslivelloletto;
	}
	public void setDeslivelloletto(String deslivelloletto) {
		this.deslivelloletto = deslivelloletto;
	}
	public String getDeslivelloparlato() {
		return deslivelloparlato;
	}
	public void setDeslivelloparlato(String deslivelloparlato) {
		this.deslivelloparlato = deslivelloparlato;
	}
	public String getDeslivelloscritto() {
		return deslivelloscritto;
	}
	public void setDeslivelloscritto(String deslivelloscritto) {
		this.deslivelloscritto = deslivelloscritto;
	}
	
}
