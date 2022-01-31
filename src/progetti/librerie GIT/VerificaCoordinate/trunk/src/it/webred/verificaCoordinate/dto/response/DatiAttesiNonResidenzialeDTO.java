package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class DatiAttesiNonResidenzialeDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String classeMediaRiferimento;

	
    public String getClasseMediaRiferimento() {
        return classeMediaRiferimento;
    }

    public void setClasseMediaRiferimento(String value) {
        this.classeMediaRiferimento = value;
    }

    public boolean isSetClasseMediaRiferimento() {
        return (this.classeMediaRiferimento != null);
    }

}
