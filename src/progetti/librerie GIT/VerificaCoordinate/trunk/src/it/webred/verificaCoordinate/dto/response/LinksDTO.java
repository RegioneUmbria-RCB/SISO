package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LinksDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private final static long serialVersionUID = 1L;
	
	protected List<LinkDTO> link;
	
	public List<LinkDTO> getLink() {
        if (link == null) {
            link = new ArrayList<LinkDTO>();
        }
        return this.link;
    }

    public boolean isSetLink() {
        return ((this.link != null)&&(!this.link.isEmpty()));
    }

    public void unsetLink() {
        this.link = null;
    }

}
