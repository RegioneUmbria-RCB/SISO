package it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAnagrafici;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class Indirizzo implements Serializable {
	
	private String indirizzo;
	private String cap;
	private String codcomune;
	private String descomune;
	
	public Indirizzo() {
		
	}
	
	public Indirizzo(String indirizzo, String cap, String comuneCod, String comuneDes) {
		this.indirizzo = indirizzo;
		this.cap = cap;
		this.codcomune = comuneCod;
		this.descomune = comuneDes;
	}
	
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCodcomune() {
		return codcomune;
	}

	public void setCodcomune(String codcomune) {
		this.codcomune = codcomune;
	}

	public String getDescomune() {
		return descomune;
	}

	public void setDescomune(String descomune) {
		this.descomune = descomune;
	}
	
	public String getIndirizzoCompleto() {
		String s = "";
		s+= !StringUtils.isBlank(this.indirizzo) ? this.indirizzo+", " : "";
		s+= !StringUtils.isBlank(this.cap) ? this.cap+" " : "";
		s+= !StringUtils.isBlank(this.descomune) ? this.descomune : this.codcomune;
		return s;
	}
}
