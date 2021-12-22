package it.umbriadigitale.soclav.service.dto.sap.lavoratore;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class AltreNotizie implements Serializable {

	//TODO:IMPLEMENTARE nel file XML di prova non sono presenti questi tag occorre verificare i nomi (questi sono temporanei)
	private String codcatprotette;
	private String indiceisee;
	
	private String descatprotette;
	
	public String getCodcatprotette() {
		return codcatprotette;
	}
	public void setCodcatprotette(String codcatprotette) {
		this.codcatprotette = codcatprotette;
	}
	public String getDescatprotette() {
		return descatprotette;
	}
	public void setDescatprotette(String descatprotette) {
		this.descatprotette = descatprotette;
	}
	public String getIndiceisee() {
		return indiceisee;
	}
	public void setIndiceisee(String indiceisee) {
		this.indiceisee = indiceisee;
	}
	
	
}
