package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VerificaCoordinateResponseDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected UserInfoDTO infoUtente;
    protected List<ErrorDTO> error;
    protected OkDTO ok;
    protected InfoDTO info;
    protected ElencoVieDTO elencoVie;
    protected ElencoUiuDTO elencoUiu;
    protected ElencoCivicoDTO elencoCivico;
    protected ElencoMappaleDTO elencoMappale;
    protected GrafDTO graf;
    protected String tipo;
    protected CoordinateDTO coordinate;
    
    public List<ErrorDTO> getError() {
        if (error == null) {
            error = new ArrayList<ErrorDTO>();
        }
        return this.error;
    }

    public boolean isSetError() {
        return ((this.error != null)&&(!this.error.isEmpty()));
    }

    public void unsetError() {
        this.error = null;
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
	
	public OkDTO getOk() {
		return ok;
	}
	
	public void setOk(OkDTO ok) {
		this.ok = ok;
	}
	
	public boolean isSetOk() {
        return (this.ok != null);
    }
	
	public InfoDTO getInfo() {
		return info;
	}
	
	public void setInfo(InfoDTO info) {
		this.info = info;
	}
	
	public boolean isSetInfo() {
        return (this.info != null);
    }
	
	public ElencoVieDTO getElencoVie() {
		return elencoVie;
	}
	
	public void setElencoVie(ElencoVieDTO elencoVie) {
		this.elencoVie = elencoVie;
	}
	
	public boolean isSetElencoVie() {
        return (this.elencoVie != null);
    }
	
	public ElencoUiuDTO getElencoUiu() {
		return elencoUiu;
	}
	
	public void setElencoUiu(ElencoUiuDTO elencoUiu) {
		this.elencoUiu = elencoUiu;
	}
	
	public boolean isSetElencoUiu() {
        return (this.elencoUiu != null);
    }
	
	public ElencoCivicoDTO getElencoCivico() {
		return elencoCivico;
	}
	
	public void setElencoCivico(ElencoCivicoDTO elencoCivico) {
		this.elencoCivico = elencoCivico;
	}
	
	public boolean isSetElencoCivico() {
        return (this.elencoCivico != null);
    }
	
	public ElencoMappaleDTO getElencoMappale() {
		return elencoMappale;
	}
	
	public void setElencoMappale(ElencoMappaleDTO elencoMappale) {
		this.elencoMappale = elencoMappale;
	}
	
	public boolean isSetElencoMappale() {
        return (this.elencoMappale != null);
    }
	
	public GrafDTO getGraf() {
		return graf;
	}
	
	public void setGraf(GrafDTO graf) {
		this.graf = graf;
	}
	
	public boolean isSetGraf() {
        return (this.graf != null);
    }
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public boolean isSetTipo() {
        return (this.tipo != null);
    }
	
	public CoordinateDTO getCoordinate() {
		return coordinate;
	}
	
	public void setCoordinate(CoordinateDTO coordinate) {
		this.coordinate = coordinate;
	}
	
	public boolean isSetCoordinate() {
        return (this.coordinate != null);
    }

}
