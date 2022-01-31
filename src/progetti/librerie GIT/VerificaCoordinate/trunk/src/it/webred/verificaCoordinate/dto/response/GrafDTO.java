package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class GrafDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String foglio;
    protected String particella;
    protected String unimm;
    protected String sezione;
    
	public String getFoglio() {
		return foglio;
	}
	
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	
	public boolean isSetFoglio() {
        return (this.foglio != null);
    }
	
	public String getParticella() {
		return particella;
	}
	
	public void setParticella(String particella) {
		this.particella = particella;
	}
	
	public boolean isSetParticella() {
        return (this.particella != null);
    }
	
	public String getUnimm() {
		return unimm;
	}
	
	public void setUnimm(String unimm) {
		this.unimm = unimm;
	}
	
	public boolean isSetUnimm() {
        return (this.unimm != null);
    }
	
	public String getSezione() {
		return sezione;
	}
	
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	
	public boolean isSetSezione() {
        return (this.sezione != null);
    }

}
