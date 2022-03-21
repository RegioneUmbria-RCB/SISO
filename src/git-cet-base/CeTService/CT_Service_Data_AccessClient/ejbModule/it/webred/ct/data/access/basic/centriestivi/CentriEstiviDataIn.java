package it.webred.ct.data.access.basic.centriestivi;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.math.BigDecimal;

public class CentriEstiviDataIn extends CeTBaseObject implements Serializable{

	private String idCentro;
	private String idFascia;
	
	public String getIdCentro() {
		return idCentro;
	}
	public void setIdCentro(String idCentro) {
		this.idCentro = idCentro;
	}
	public String getIdFascia() {
		return idFascia;
	}
	public void setIdFascia(String idFascia) {
		this.idFascia = idFascia;
	}
	
}
