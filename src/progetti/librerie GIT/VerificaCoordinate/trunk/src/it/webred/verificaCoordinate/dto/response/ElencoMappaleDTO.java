package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ElencoMappaleDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected List<MappaleDTO> mappale;
	
	public List<MappaleDTO> getMappale() {
        if (mappale == null) {
            mappale = new ArrayList<MappaleDTO>();
        }
        return this.mappale;
    }

    public boolean isSetMappale() {
        return ((this.mappale != null)&&(!this.mappale.isEmpty()));
    }

    public void unsetMappale() {
        this.mappale = null;
    }

}
