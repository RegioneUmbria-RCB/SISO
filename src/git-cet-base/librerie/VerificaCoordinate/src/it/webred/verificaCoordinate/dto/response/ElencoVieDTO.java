package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ElencoVieDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected List<ViaDTO> via;
	
	public List<ViaDTO> getVia() {
        if (via == null) {
            via = new ArrayList<ViaDTO>();
        }
        return this.via;
    }

    public boolean isSetVia() {
        return ((this.via!= null)&&(!this.via.isEmpty()));
    }

    public void unsetVia() {
        this.via = null;
    }

}
