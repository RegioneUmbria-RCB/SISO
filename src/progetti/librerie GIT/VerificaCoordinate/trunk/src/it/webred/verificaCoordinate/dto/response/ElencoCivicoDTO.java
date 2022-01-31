package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ElencoCivicoDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected List<CivicoDTO> civico;
	
	public List<CivicoDTO> getCivico() {
        if (civico == null) {
            civico = new ArrayList<CivicoDTO>();
        }
        return this.civico;
    }

    public boolean isSetCivico() {
        return ((this.civico != null)&&(!this.civico.isEmpty()));
    }

    public void unsetCivico() {
        this.civico = null;
    }

}
