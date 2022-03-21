package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class CivicoDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected ViaDTO via;
    protected String numero;
    
	public ViaDTO getVia() {
		return via;
	}
	
	public void setVia(ViaDTO via) {
		this.via = via;
	}
	
	public boolean isSetVia() {
        return (this.via != null);
    }
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public boolean isSetNumero() {
        return (this.numero != null);
    }

}
