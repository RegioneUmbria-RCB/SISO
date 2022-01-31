package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatiAttesiDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected List<DatiAttesiResidenzialeDTO> datiAttesiResidenziale;
    protected List<DatiAttesiNonResidenzialeDTO> datiAttesiNonResidenziale;

    public List<DatiAttesiResidenzialeDTO> getDatiAttesiResidenziale() {
        if (datiAttesiResidenziale == null) {
            datiAttesiResidenziale = new ArrayList<DatiAttesiResidenzialeDTO>();
        }
        return this.datiAttesiResidenziale;
    }

    public boolean isSetDatiAttesiResidenziale() {
        return ((this.datiAttesiResidenziale != null)&&(!this.datiAttesiResidenziale.isEmpty()));
    }

    public void unsetDatiAttesiResidenziale() {
        this.datiAttesiResidenziale = null;
    }

    public List<DatiAttesiNonResidenzialeDTO> getDatiAttesiNonResidenziale() {
        if (datiAttesiNonResidenziale == null) {
            datiAttesiNonResidenziale = new ArrayList<DatiAttesiNonResidenzialeDTO>();
        }
        return this.datiAttesiNonResidenziale;
    }

    public boolean isSetDatiAttesiNonResidenziale() {
        return ((this.datiAttesiNonResidenziale != null)&&(!this.datiAttesiNonResidenziale.isEmpty()));
    }

    public void unsetDatiAttesiNonResidenziale() {
        this.datiAttesiNonResidenziale = null;
    }


}

