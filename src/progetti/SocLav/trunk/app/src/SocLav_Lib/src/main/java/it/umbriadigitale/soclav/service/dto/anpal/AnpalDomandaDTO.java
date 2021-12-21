package it.umbriadigitale.soclav.service.dto.anpal;

import java.util.List;

import it.umbriadigitale.soclav.service.dto.DomandaRdCDTO;

@SuppressWarnings("serial")
public class AnpalDomandaDTO extends DomandaRdCDTO{

	private AnpalBeneficiarioDTO richiedente;
	
	private List<AnpalBeneficiarioDTO> familiari;
	
	private String statoDomandaINPS;

	public AnpalBeneficiarioDTO getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(AnpalBeneficiarioDTO richiedente) {
		this.richiedente = richiedente;
	}

	public List<AnpalBeneficiarioDTO> getFamiliari() {
		return familiari;
	}

	public void setFamiliari(List<AnpalBeneficiarioDTO> familiari) {
		this.familiari = familiari;
	}
	
	public String getStatoDomandaINPS() {
		return statoDomandaINPS;
	}

	public void setStatoDomandaINPS(String statoDomandaINPS) {
		this.statoDomandaINPS = statoDomandaINPS;
	}

}
