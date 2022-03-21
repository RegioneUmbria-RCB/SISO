package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ElencoUiuDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected List<UiuDTO> uiu;
	
	public List<UiuDTO> getUiu() {
        if (uiu == null) {
            uiu = new ArrayList<UiuDTO>();
        }
        return this.uiu;
    }

    public boolean isSetUiu() {
        return ((this.uiu!= null)&&(!this.uiu.isEmpty()));
    }

    public void unsetUiu() {
        this.uiu = null;
    }

}
