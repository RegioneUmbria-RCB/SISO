package it.umbriadigitale.soclav.service.dto.sap.lavoratore.politicaattiva;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class Evento implements Serializable {
	
/*
 * 	<ultimo_evento>    
			<evento>02</evento>    
			<data_evento>2017-09-27</data_evento>
			<descrizione_evento>Proposta</descrizione_evento>
	</ultimo_evento>
 * */
	private String evento;
	private Date data_evento;
	private String descrizione_evento;
	
	private String desEvento;
	
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
	public Date getData_evento() {
		return data_evento;
	}
	public void setData_evento(Date data_evento) {
		this.data_evento = data_evento;
	}
	public String getDesEvento() {
		return desEvento;
	}
	public void setDesEvento(String desEvento) {
		this.desEvento = desEvento;
	}
	public String getDescrizione_evento() {
		return descrizione_evento;
	}
	public void setDescrizione_evento(String descrizione_evento) {
		this.descrizione_evento = descrizione_evento;
	}
	
}
