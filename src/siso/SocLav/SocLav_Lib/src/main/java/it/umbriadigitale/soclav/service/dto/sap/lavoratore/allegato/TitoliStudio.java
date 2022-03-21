package it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class TitoliStudio implements Serializable {

	/*
	 *     <titolostudio>    
				<codlivelloistruzione>20</codlivelloistruzione>    
				<corsostudio>20000000</corsostudio>    
				<descrizionecorsostudio>LICENZA MEDIA</descrizionecorsostudio>    
				<riconosciutoin>SI</riconosciutoin>
			</titolostudio>        
	 * */
	private String codlivelloistruzione;
	private String corsostudio;
	private String descrizionecorsostudio;
	private String riconosciutoin;
	
	private String deslivelloistruzione;
	private String descorsostudio;
	
	public String getCodlivelloistruzione() {
		return codlivelloistruzione;
	}
	public void setCodlivelloistruzione(String codlivelloistruzione) {
		this.codlivelloistruzione = codlivelloistruzione;
	}
	public String getCorsostudio() {
		return corsostudio;
	}
	public void setCorsostudio(String corsostudio) {
		this.corsostudio = corsostudio;
	}
	public String getDescrizionecorsostudio() {
		return descrizionecorsostudio;
	}
	public void setDescrizionecorsostudio(String descrizionecorsostudio) {
		this.descrizionecorsostudio = descrizionecorsostudio;
	}
	public String getRiconosciutoin() {
		return riconosciutoin;
	}
	public void setRiconosciutoin(String riconosciutoin) {
		this.riconosciutoin = riconosciutoin;
	}
	public String getDeslivelloistruzione() {
		return deslivelloistruzione;
	}
	public void setDeslivelloistruzione(String deslivelloistruzione) {
		this.deslivelloistruzione = deslivelloistruzione;
	}
	public String getDescorsostudio() {
		return descorsostudio;
	}
	public void setDescorsostudio(String descorsostudio) {
		this.descorsostudio = descorsostudio;
	}	
}
