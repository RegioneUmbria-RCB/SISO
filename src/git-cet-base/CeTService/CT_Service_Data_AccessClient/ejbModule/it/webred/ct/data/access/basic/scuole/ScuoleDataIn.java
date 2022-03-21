package it.webred.ct.data.access.basic.scuole;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.math.BigDecimal;

public class ScuoleDataIn extends CeTBaseObject implements Serializable{

	private String tipoIscrizione;
	private String tipoIstituto;
	private String codIstituto;
	private BigDecimal anno;
	private String idCentro;
	private String idFascia;
	
	public String getTipoIstituto() {
		return tipoIstituto;
	}
	public void setTipoIstituto(String tipoIstituto) {
		this.tipoIstituto = tipoIstituto;
	}
	public String getCodIstituto() {
		return codIstituto;
	}
	public void setCodIstituto(String codIstituto) {
		this.codIstituto = codIstituto;
	}
	public BigDecimal getAnno() {
		return anno;
	}
	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}
	public String getTipoIscrizione() {
    	return tipoIscrizione;
    }
	public void setTipoIscrizione(String tipoIscrizione) {
    	this.tipoIscrizione = tipoIscrizione;
    }
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
