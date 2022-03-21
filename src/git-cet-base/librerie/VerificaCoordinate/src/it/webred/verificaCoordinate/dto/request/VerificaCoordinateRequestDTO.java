package it.webred.verificaCoordinate.dto.request;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class VerificaCoordinateRequestDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected UserInfoDTO infoUtente;
	protected CredenzialiDTO credenziali;
    protected DatiToponomasticiDTO datiToponomastici;
    protected DatiCatastaliDTO datiCatastali;
    protected ResidenzialeDTO residenziale;
    protected NonResidenzialeDTO nonResidenziale;
    
	public CredenzialiDTO getCredenziali() {
		return credenziali;
	}

	public void setCredenziali(CredenzialiDTO credenziali) {
		this.credenziali = credenziali;
	}
	
	public boolean isSetCredenziali() {
        return (this.credenziali != null);
    }
    
    public DatiCatastaliDTO getDatiCatastali() {
		return datiCatastali;
	}
	
	public void setDatiCatastali(DatiCatastaliDTO datiCatastali) {
		this.datiCatastali = datiCatastali;
	}
	
	public boolean isSetDatiCatastali() {
        return (this.datiCatastali != null);
    }

	public UserInfoDTO getInfoUtente() {
		return infoUtente;
	}

	public void setInfoUtente(UserInfoDTO infoUtente) {
		this.infoUtente = infoUtente;
	}
	
	public boolean isSetInfoUtente() {
        return (this.infoUtente != null);
    }

	public DatiToponomasticiDTO getDatiToponomastici() {
		return datiToponomastici;
	}

	public void setDatiToponomastici(DatiToponomasticiDTO datiToponomastici) {
		this.datiToponomastici = datiToponomastici;
	}
	
	public boolean isSetDatiToponomastici() {
        return (this.datiToponomastici != null);
    }
	
	public ResidenzialeDTO getResidenziale() {
		return residenziale;
	}

	public void setResidenziale(ResidenzialeDTO residenziale) {
		this.residenziale = residenziale;
	}
	
	public boolean isSetResidenziale() {
        return (this.residenziale != null);
    }
	
	public NonResidenzialeDTO getNonResidenziale() {
		return nonResidenziale;
	}

	public void setNonResidenziale(NonResidenzialeDTO nonResidenziale) {
		this.nonResidenziale = nonResidenziale;
	}
	
	public boolean isSetNonResidenziale() {
        return (this.nonResidenziale != null);
    }
	
}
