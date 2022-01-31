package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class ZonaDecentramentoDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private final static long serialVersionUID = 1L;
	
    protected String zona;
    
    public String getZona() {
        return zona;
    }

    public void setZona(String value) {
        this.zona = value;
    }

    public boolean isSetZona() {
        return (this.zona != null);
    }

}
