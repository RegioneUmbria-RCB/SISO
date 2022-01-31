package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class ZonaOmogeneaDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private final static long serialVersionUID = 1L;
	
    protected String codice;
    protected String descrizione;
    protected String nota;
    
	public String getCodice() {
		return codice;
	}
	
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	public boolean isSetCodice() {
        return (this.codice != null);
    }
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public boolean isSetDescrizione() {
        return (this.descrizione!= null);
    }
	
	public String getNota() {
		return nota;
	}
	
	public void setNota(String nota) {
		this.nota = nota;
	}
	
	public boolean isSetNota() {
        return (this.nota!= null);
    }
	
}
