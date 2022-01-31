package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RigaTypeDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected List<AttributoTypeDTO> attributo;
	
	public List<AttributoTypeDTO> getAttributo() {
        if (attributo == null) {
            attributo = new ArrayList<AttributoTypeDTO>();
        }
        return this.attributo;
    }

    public boolean isSetAttributo() {
        return ((this.attributo != null)&&(!this.attributo.isEmpty()));
    }

    public void unsetAttributo() {
        this.attributo = null;
    }

}
