package it.webred.verificaCoordinate.dto.request;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class C6DTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String postoAutoCoperto;
	
	public String getPostoAutoCoperto() {
        return postoAutoCoperto;
    }

    public void setPostoAutoCoperto(String value) {
        this.postoAutoCoperto = value;
    }

    public boolean isSetPostoAutoCoperto() {
        return (this.postoAutoCoperto != null);
    }

}
