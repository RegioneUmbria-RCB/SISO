package it.webred.verificaCoordinate.dto.request;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class CredenzialiDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String sistema;
    protected String utente;
    protected String token;
    
	public String getSistema() {
		return sistema;
	}
	
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	
	public boolean isSetSistema() {
        return (this.sistema != null);
    }
	
	public String getUtente() {
		return utente;
	}
	
	public void setUtente(String utente) {
		this.utente = utente;
	}
	
	public boolean isSetUtente() {
        return (this.utente != null);
    }
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public boolean isSetToken() {
        return (this.token != null);
    }

}
