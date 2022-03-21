package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class CoordinateDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String lat;
    protected String lon;
    
	public String getLat() {
		return lat;
	}
	
	public void setLat(String lat) {
		this.lat = lat;
	}

	public boolean isSetLat() {
        return (this.lat != null);
    }

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}
	
	public boolean isSetLon() {
        return (this.lon != null);
    }
	
}

