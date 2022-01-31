package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatiPrgDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected List<PrgDTO> prg;
	
	public List<PrgDTO> getPrg() {
        if (prg == null) {
            prg = new ArrayList<PrgDTO>();
        }
        return this.prg;
    }

    public boolean isSetPrg() {
        return ((this.prg != null)&&(!this.prg.isEmpty()));
    }

    public void unsetPrg() {
        this.prg = null;
    }

}
