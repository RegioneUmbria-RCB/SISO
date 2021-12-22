package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;
import java.util.Date;

public class PresaInCaricoDTO implements Serializable {
	
	public Date dataAmministrativa;
	public DatiOperatoreDTO responsabile;
	public KeyValueDTO settore;
	public KeyValueDTO organizzazione;
	
	
	public Date getDataAmministrativa() {
		return dataAmministrativa;
	}
	public void setDataAmministrativa(Date dataAmministrativa) {
		this.dataAmministrativa = dataAmministrativa;
	}
	public DatiOperatoreDTO getResponsabile() {
		return responsabile;
	}
	public void setResponsabile(DatiOperatoreDTO responsabile) {
		this.responsabile = responsabile;
	}
	public KeyValueDTO getSettore() {
		return settore;
	}
	public void setSettore(KeyValueDTO settore) {
		this.settore = settore;
	}
	public KeyValueDTO getOrganizzazione() {
		return organizzazione;
	}
	public void setOrganizzazione(KeyValueDTO organizzazione) {
		this.organizzazione = organizzazione;
	}
	
}
