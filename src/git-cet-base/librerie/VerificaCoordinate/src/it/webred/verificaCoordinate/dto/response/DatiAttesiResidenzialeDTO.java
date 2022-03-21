package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class DatiAttesiResidenzialeDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected ClassiMaxCategoriaDTO classiMaxCategoria;
    protected ClassamentiDTO classamenti;
    protected String valoreCommercialeMq;
    protected String valoreCommerciale;
    protected String renditaMinima;
    protected String tariffaPerVano;


    public ClassiMaxCategoriaDTO getClassiMaxCategoria() {
        return classiMaxCategoria;
    }

    public void setClassiMaxCategoria(ClassiMaxCategoriaDTO value) {
        this.classiMaxCategoria = value;
    }

    public boolean isSetClassiMaxCategoria() {
        return (this.classiMaxCategoria != null);
    }

    public ClassamentiDTO getClassamenti() {
        return classamenti;
    }

    public void setClassamenti(ClassamentiDTO value) {
        this.classamenti = value;
    }

    public boolean isSetClassamenti() {
        return (this.classamenti != null);
    }
    
    public String getValoreCommercialeMq() {
        return valoreCommercialeMq;
    }

    public void setValoreCommercialeMq(String value) {
        this.valoreCommercialeMq = value;
    }

    public boolean isSetValoreCommercialeMq() {
        return (this.valoreCommercialeMq != null);
    }

    public String getValoreCommerciale() {
        return valoreCommerciale;
    }

    public void setValoreCommerciale(String value) {
        this.valoreCommerciale = value;
    }

    public boolean isSetValoreCommerciale() {
        return (this.valoreCommerciale != null);
    }

    public String getRenditaMinima() {
        return renditaMinima;
    }

    public void setRenditaMinima(String value) {
        this.renditaMinima = value;
    }

    public boolean isSetRenditaMinima() {
        return (this.renditaMinima != null);
    }

    public String getTariffaPerVano() {
        return tariffaPerVano;
    }

    public void setTariffaPerVano(String value) {
        this.tariffaPerVano = value;
    }

    public boolean isSetTariffaPerVano() {
        return (this.tariffaPerVano != null);
    }

}

