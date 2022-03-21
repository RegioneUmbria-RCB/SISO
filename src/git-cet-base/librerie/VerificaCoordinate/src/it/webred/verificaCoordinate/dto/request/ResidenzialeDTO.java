package it.webred.verificaCoordinate.dto.request;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class ResidenzialeDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String categoriaEdilizia;
    protected String tipoIntervento;
    protected String ascensoreOrMin3MFT;
    protected String consistenza;
    protected String superficie;
    
    public String getCategoriaEdilizia() {
        return categoriaEdilizia;
    }

    public void setCategoriaEdilizia(String value) {
        this.categoriaEdilizia = value;
    }

    public boolean isSetCategoriaEdilizia() {
        return (this.categoriaEdilizia != null);
    }

    public String getTipoIntervento() {
        return tipoIntervento;
    }

    public void setTipoIntervento(String value) {
        this.tipoIntervento = value;
    }

    public boolean isSetTipoIntervento() {
        return (this.tipoIntervento != null);
    }

    public String getAscensoreOrMin3MFT() {
        return ascensoreOrMin3MFT;
    }

    public void setAscensoreOrMin3MFT(String value) {
        this.ascensoreOrMin3MFT = value;
    }

    public boolean isSetAscensoreOrMin3MFT() {
        return (this.ascensoreOrMin3MFT != null);
    }

    public String getConsistenza() {
        return consistenza;
    }
    
    public void setConsistenza(String value) {
        this.consistenza = value;
    }

    public boolean isSetConsistenza() {
        return (this.consistenza != null);
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String value) {
        this.superficie = value;
    }

    public boolean isSetSuperficie() {
        return (this.superficie != null);
    }

}
