package it.webred.verificaCoordinate.dto.request;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class NonResidenzialeDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String categoriaEdilizia;
    protected C6DTO c6;
    
    public String getCategoriaEdilizia() {
        return categoriaEdilizia;
    }

    public void setCategoriaEdilizia(String value) {
        this.categoriaEdilizia = value;
    }

    public boolean isSetCategoriaEdilizia() {
        return (this.categoriaEdilizia != null);
    }
    
    public C6DTO getC6() {
        return c6;
    }

    public void setC6(C6DTO value) {
        this.c6 = value;
    }

    public boolean isSetC6() {
        return (this.c6 != null);
    }
    
}
