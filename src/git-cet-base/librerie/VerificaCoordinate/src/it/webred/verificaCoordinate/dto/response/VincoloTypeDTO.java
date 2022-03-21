package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VincoloTypeDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected List<RigaTypeDTO> riga;
    protected String tipo;    
    
    public List<RigaTypeDTO> getRiga() {
        if (riga == null) {
            riga = new ArrayList<RigaTypeDTO>();
        }
        return this.riga;
    }

    public boolean isSetRiga() {
        return ((this.riga != null)&&(!this.riga.isEmpty()));
    }

    public void unsetRiga() {
        this.riga = null;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String value) {
        this.tipo = value;
    }

    public boolean isSetTipo() {
        return (this.tipo != null);
    }
    
}
