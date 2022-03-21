package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassiMaxCategoriaDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected List<ClasseMaxCategoriaDTO> classeMaxCategoria;
	
	public List<ClasseMaxCategoriaDTO> getClasseMaxCategoria() {
        if (classeMaxCategoria == null) {
            classeMaxCategoria = new ArrayList<ClasseMaxCategoriaDTO>();
        }
        return this.classeMaxCategoria;
    }

    public boolean isSetClasseMaxCategoria() {
        return ((this.classeMaxCategoria != null)&&(!this.classeMaxCategoria.isEmpty()));
    }

    public void unsetClasseMaxCategoria() {
        this.classeMaxCategoria = null;
    }

}
