package it.webred.verificaCoordinate.dto.request;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class DatiToponomasticiDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String tipoArea;
    protected String nomeVia;
    protected String civico;
    protected String esponente;
    protected String codiceVia;
    
	public String getCivico() {
		return civico;
	}
	
	public void setCivico(String civico) {
		this.civico = civico;
	}

	public boolean isSetCivico() {
        return (this.civico != null);
    }
	
	public String getTipoArea() {
		return tipoArea;
	}

	public void setTipoArea(String tipoArea) {
		this.tipoArea = tipoArea;
	}
	
	public boolean isSetTipoArea() {
        return (this.tipoArea != null);
    }

	public String getNomeVia() {
		return nomeVia;
	}

	public void setNomeVia(String nomeVia) {
		this.nomeVia = nomeVia;
	}
	
	public boolean isSetNomeVia() {
        return (this.nomeVia != null);
    }

	public String getEsponente() {
		return esponente;
	}

	public void setEsponente(String esponente) {
		this.esponente = esponente;
	}
	
	public boolean isSetEsponente() {
        return (this.esponente != null);
    }

	public String getCodiceVia() {
		return codiceVia;
	}

	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}
	
	public boolean isSetCodiceVia() {
        return (this.codiceVia != null);
    }

}
