package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class AttributoTypeDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String label;
    protected String valore;
    protected String nome;

    public String getLabel() {
        return label;
    }

    public void setLabel(String value) {
        this.label = value;
    }

    public boolean isSetLabel() {
        return (this.label != null);
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String value) {
        this.valore = value;
    }

    public boolean isSetValore() {
        return (this.valore != null);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String value) {
        this.nome = value;
    }

    public boolean isSetNome() {
        return (this.nome != null);
    }

}

