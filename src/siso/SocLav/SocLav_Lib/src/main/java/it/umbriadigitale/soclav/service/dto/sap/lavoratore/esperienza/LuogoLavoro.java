package it.umbriadigitale.soclav.service.dto.sap.lavoratore.esperienza;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class LuogoLavoro implements Serializable {

	/*	
	<luogolavoro>    
		<indirizzo>Località Cerrete Collicelli N° 8</indirizzo>    
		<codcomune>A000</codcomune>
	</luogolavoro>        
	*/
	
	private String indirizzo;
	private String codcomune;
	
	private String descomune;
	
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
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
		return this.indirizzo+", "+this.descomune;
	}
}
