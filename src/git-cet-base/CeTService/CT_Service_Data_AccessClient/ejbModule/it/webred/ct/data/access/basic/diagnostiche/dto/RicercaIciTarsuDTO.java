package it.webred.ct.data.access.basic.diagnostiche.dto;

import java.io.Serializable;
import java.util.List;

import it.webred.ct.data.model.diagnostiche.*;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class RicercaIciTarsuDTO extends CeTBaseObject{
	
	private static final long serialVersionUID = 1L;
	private Long enteSorgenteOrigine;
	private Long progOrigine;
	
	//private String catPkid;
	private String idDwhOrigineSogg;
	private String idDwhOrigineCiv;
	//private String dataDocfa;
	private String foglio;
	private String particella;
	private String sub;
	private String dataRif;
	
	/*public String getCatPkid() {
		return catPkid;
	}
	public void setCatPkid(String catPkid) {
		this.catPkid = catPkid;
	}*/
	
	public String getDataRif() {
		return dataRif;
	}
	public void setDataRif(String dataRif) {
		this.dataRif = dataRif;
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
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	
	public String getIdDwhOrigineSogg() {
		return idDwhOrigineSogg;
	}
	public void setIdDwhOrigineSogg(String idDwhOrigineSogg) {
		this.idDwhOrigineSogg = idDwhOrigineSogg;
	}
	public String getIdDwhOrigineCiv() {
		return idDwhOrigineCiv;
	}
	public void setIdDwhOrigineCiv(String idDwhOrigineCiv) {
		this.idDwhOrigineCiv = idDwhOrigineCiv;
	}
	public void setEnteSorgenteOrigine(Long enteSorgenteOrigine) {
		this.enteSorgenteOrigine = enteSorgenteOrigine;
	}
	public void setProgOrigine(Long progOrigine) {
		this.progOrigine = progOrigine;
	}
	public Long getEnteSorgenteOrigine() {
		return enteSorgenteOrigine;
	}
	public Long getProgOrigine() {
		return progOrigine;
	}
	
}
