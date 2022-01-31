package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class ClassamentoDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String categoria;
    protected String classe;
    protected String tariffa;
    protected String renditaComplessiva;
    
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
	
	public String getTariffa() {
		return tariffa;
	}
	
	public void setTariffa(String tariffa) {
		this.tariffa = tariffa;
	}
	
	public boolean isSetTariffa() {
        return (this.tariffa != null);
    }
	
	public String getRenditaComplessiva() {
		return renditaComplessiva;
	}
	
	public void setRenditaComplessiva(String renditaComplessiva) {
		this.renditaComplessiva = renditaComplessiva;
	}
	
	public boolean isSetRenditaComplessiva() {
        return (this.renditaComplessiva != null);
    }

}
