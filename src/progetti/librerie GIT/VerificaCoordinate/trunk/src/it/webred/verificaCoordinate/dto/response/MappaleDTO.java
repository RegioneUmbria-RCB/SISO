package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class MappaleDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String foglio;
    protected String particella;
    protected Long dataInizioVal;
    protected ZonaDecentramentoDTO zonaDecentramento;
    protected DatiPrgDTO datiPrg;
    protected VincoliTypeDTO vincoli;
    protected DatiAttesiDTO datiAttesi;
    protected String cartografia;
    protected String censTerreni;
    protected String censUrbano;

	public String getCartografia() {
		return cartografia;
	}
	
	public void setCartografia(String cartografia) {
		this.cartografia = cartografia;
	}
	
	public boolean isSetCartografia() {
        return (this.cartografia != null);
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

	public Long getDataInizioVal() {
		return dataInizioVal;
	}

	public void setDataInizioVal(Long dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}
	
	public boolean isSetDataInizioVal() {
        return (this.dataInizioVal != null);
    }

	public DatiPrgDTO getDatiPrg() {
		return datiPrg;
	}

	public void setDatiPrg(DatiPrgDTO datiPrg) {
		this.datiPrg = datiPrg;
	}
	
	public boolean isSetDatiPrg() {
        return (this.datiPrg != null);
    }

	public VincoliTypeDTO getVincoli() {
        return vincoli;
    }

    public void setVincoli(VincoliTypeDTO value) {
        this.vincoli = value;
    }

    public boolean isSetVincoli() {
        return (this.vincoli != null);
    }

	public DatiAttesiDTO getDatiAttesi() {
		return datiAttesi;
	}

	public void setDatiAttesi(DatiAttesiDTO datiAttesi) {
		this.datiAttesi = datiAttesi;
	}
	
	public boolean isSetDatiAttesi() {
        return (this.datiAttesi != null);
    }

	public String getCensTerreni() {
		return censTerreni;
	}

	public void setCensTerreni(String censTerreni) {
		this.censTerreni = censTerreni;
	}
	
	public boolean isSetCensTerreni() {
        return (this.censTerreni != null);
    }

	public String getCensUrbano() {
		return censUrbano;
	}

	public void setCensUrbano(String censUrbano) {
		this.censUrbano = censUrbano;
	}
	
	public boolean isSetCensUrbano() {
        return (this.censUrbano != null);
    }
	
	public ZonaDecentramentoDTO getZonaDecentramento() {
        return zonaDecentramento;
    }
	
    public void setZonaDecentramento(ZonaDecentramentoDTO value) {
        this.zonaDecentramento = value;
    }

    public boolean isSetZonaDecentramento() {
        return (this.zonaDecentramento != null);
    }
	
}
