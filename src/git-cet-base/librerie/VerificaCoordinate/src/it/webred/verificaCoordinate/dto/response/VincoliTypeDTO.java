package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VincoliTypeDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected List<VincoloTypeDTO> vincolo;
	
	public List<VincoloTypeDTO> getVincolo() {
        if (vincolo == null) {
            vincolo = new ArrayList<VincoloTypeDTO>();
        }
        return this.vincolo;
    }

    public boolean isSetVincolo() {
        return ((this.vincolo != null)&&(!this.vincolo.isEmpty()));
    }

    public void unsetVincolo() {
        this.vincolo = null;
    }

}
