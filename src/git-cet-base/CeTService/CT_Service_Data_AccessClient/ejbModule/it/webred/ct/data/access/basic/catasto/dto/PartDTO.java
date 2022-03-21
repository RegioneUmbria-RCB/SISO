package it.webred.ct.data.access.basic.catasto.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.Date;

public class PartDTO extends CeTBaseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long foglio;
	private String particella;
	private String codiFiscLuna;
	private Date maxdate;  //31/12/9999
	
	public Long getFoglio() {
		return foglio;
	}
	public void setFoglio(Long foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getCodiFiscLuna() {
		return codiFiscLuna;
	}
	public void setCodiFiscLuna(String codiFiscLuna) {
		this.codiFiscLuna = codiFiscLuna;
	}
	public Date getMaxdate() {
		return maxdate;
	}
	public void setMaxdate(Date maxdate) {
		this.maxdate = maxdate;
	}
	
}
