package it.umbriadigitale.soclav.service.dto.sap.lavoratore.esperienza;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class TipoRapporto implements Serializable {

	/*
	 * 	<tiporapporto>    
		<tipocontratto>A.02.00</tipocontratto>    
		<assunzioneLegge68>NO</assunzioneLegge68>    
		<lavInMobilita>NO</lavInMobilita>    
		<lavoroStagionale>SI</lavoroStagionale>    
		<lavoroInAgricoltura>NO</lavoroInAgricoltura>
		</tiporapporto>
	 * */
	private String tipocontratto;
	private String assunzioneLegge68;
	private String lavInMobilita;
	private String lavoroStagionale;
	private String lavoroInAgricoltura;
	
	private String desCatInquadramento;
	private String destipocontratto;
	
	public String getTipocontratto() {
		return tipocontratto;
	}
	public void setTipocontratto(String tipocontratto) {
		this.tipocontratto = tipocontratto;
	}
	public String getAssunzioneLegge68() {
		return assunzioneLegge68;
	}
	public void setAssunzioneLegge68(String assunzioneLegge68) {
		this.assunzioneLegge68 = assunzioneLegge68;
	}
	public String getLavInMobilita() {
		return lavInMobilita;
	}
	public void setLavInMobilita(String lavInMobilita) {
		this.lavInMobilita = lavInMobilita;
	}
	public String getLavoroStagionale() {
		return lavoroStagionale;
	}
	public void setLavoroStagionale(String lavoroStagionale) {
		this.lavoroStagionale = lavoroStagionale;
	}
	public String getLavoroInAgricoltura() {
		return lavoroInAgricoltura;
	}
	public void setLavoroInAgricoltura(String lavoroInAgricoltura) {
		this.lavoroInAgricoltura = lavoroInAgricoltura;
	}
	public String getDesCatInquadramento() {
		return desCatInquadramento;
	}
	public void setDesCatInquadramento(String desCatInquadramento) {
		this.desCatInquadramento = desCatInquadramento;
	}
	public String getDestipocontratto() {
		return destipocontratto;
	}
	public void setDestipocontratto(String destipocontratto) {
		this.destipocontratto = destipocontratto;
	}
	
}
