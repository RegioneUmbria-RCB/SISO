package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class ViaDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String prefisso;
    protected String nome;
    protected String codiceVia;
    
	public String getPrefisso() {
		return prefisso;
	}
	
	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}
	
	public boolean isSetPrefisso() {
        return (this.prefisso != null);
    }
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean isSetNome() {
        return (this.nome != null);
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
