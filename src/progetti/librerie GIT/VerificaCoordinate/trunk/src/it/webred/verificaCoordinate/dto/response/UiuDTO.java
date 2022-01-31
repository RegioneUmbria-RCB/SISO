package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class UiuDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String foglio;
    protected String particella;
    protected String subalterno;
    protected String sezione;
    protected String classe;
    protected String categoria;
    protected String rendita;
    protected Long dataInizioVal;
    
    public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public boolean isSetCategoria() {
        return (this.categoria != null);
    }
    
    public String getClasse() {
		return classe;
	}
	
	public void setClasse(String classe) {
		this.classe = classe;
	}
	
	public boolean isSetClasse() {
        return (this.classe != null);
    }

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

	public String getRendita() {
		return rendita;
	}

	public void setRendita(String rendita) {
		this.rendita = rendita;
	}
	
	public boolean isSetRendita() {
        return (this.rendita != null);
    }

	public Long getDataInizioVal() {
		return dataInizioVal;
	}

	public void setDataInizioVal(Long dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}
	
	public boolean isSetDataInizioVal() {
        return (this.dataInizioVal != null);
    }
	
}
