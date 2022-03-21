package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassamentiDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected List<ClassamentoDTO> classamento;
	
	public List<ClassamentoDTO> getClassamento() {
        if (classamento == null) {
            classamento = new ArrayList<ClassamentoDTO>();
        }
        return this.classamento;
    }

    public boolean isSetClassamento() {
        return ((this.classamento != null)&&(!this.classamento.isEmpty()));
    }

    public void unsetClassamento() {
        this.classamento = null;
    }

}
