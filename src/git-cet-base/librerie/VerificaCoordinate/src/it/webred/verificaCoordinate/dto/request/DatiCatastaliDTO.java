package it.webred.verificaCoordinate.dto.request;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class DatiCatastaliDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String foglio;
    protected String mappale;
    protected String subalterno;
    protected String sezione;
    protected TipoCatastoDTO tipoCatasto;
    
	public String getFoglio() {
		return foglio;
	}
	
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	
	public boolean isSetFoglio() {
        return (this.foglio != null);
    }

	public String getMappale() {
		return mappale;
	}

	public void setMappale(String mappale) {
		this.mappale = mappale;
	}

	public boolean isSetMappale() {
        return (this.mappale != null);
    }
	
	public String getSubalterno() {
		return subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public boolean isSetSubalterno() {
        return (this.subalterno != null);
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
	
	public TipoCatastoDTO getTipoCatasto() {
        return tipoCatasto;
    }

    public void setTipoCatasto(TipoCatastoDTO value) {
        this.tipoCatasto = value;
    }

    public boolean isSetTipoCatasto() {
        return (this.tipoCatasto!= null);
    }
    
}
