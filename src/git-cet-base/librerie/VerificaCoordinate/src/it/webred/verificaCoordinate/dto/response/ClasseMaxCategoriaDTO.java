package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class ClasseMaxCategoriaDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String categoria;
    protected String classe;
    
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String value) {
        this.categoria = value;
    }

    public boolean isSetCategoria() {
        return (this.categoria != null);
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String value) {
        this.classe = value;
    }

    public boolean isSetClasse() {
        return (this.classe != null);
    }

}

