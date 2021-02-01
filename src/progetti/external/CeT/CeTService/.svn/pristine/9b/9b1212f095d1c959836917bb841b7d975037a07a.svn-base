package it.webred.ct.data.access.basic.indice.ricerca;

import java.util.Date;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class KeyFabbricatoDTO extends CeTBaseObject {
	protected String codNazionale ;
	protected String sezione ;
	protected String foglio ;
	protected String  particella ;
	protected Date dtVal ;
	public KeyFabbricatoDTO()  {
		super();
	}
	public KeyFabbricatoDTO(CeTBaseObject cet , String codNazionale, String sezione ,String foglio ,String  particella ,Date dtVal)  {
		super();
		this.setEnteId(cet.getEnteId());
		this.setUserId(cet.getUserId());
		this.setSessionId(cet.getSessionId());
		this.codNazionale = codNazionale;
		this.sezione = sezione;
		this.foglio = foglio;
		this.particella = particella;
		this.dtVal = dtVal;
	}
	
	public String getCodNazionale() {
		return codNazionale;
	}
	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public Date getDtVal() {
		return dtVal;
	}
	public void setDtVal(Date dtVal) {
		this.dtVal = dtVal;
	}
	public String stringValue() {
		String retVal = "";
		retVal = this.codNazionale!=null ? this.codNazionale: "";
		retVal += "-";
		retVal += this.sezione!=null ? this.sezione: "";
		retVal += "-";
		retVal += this.foglio!=null ? this.foglio : "";
		retVal += "-";
		retVal += this.particella!=null ? this.particella : "";
		retVal += "-";
		retVal += this.dtVal!=null ? this.dtVal.toString() : "null";
		return retVal;	
	}
}
