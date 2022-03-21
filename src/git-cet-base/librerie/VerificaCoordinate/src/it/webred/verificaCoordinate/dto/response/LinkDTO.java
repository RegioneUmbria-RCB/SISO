package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class LinkDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String descrizione;
    protected String url;
    
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public boolean isSetDescrizione() {
        return (this.descrizione != null);
    }
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean isSetUrl() {
        return (this.url!= null);
    }
	
}
