package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsAComponenteGit;

public class ComponenteDTO {

	private CsAComponenteGit compGit;
	private boolean attiva;
	private String statoCartella;
	
	public CsAComponenteGit getCompGit() {
		return compGit;
	}
	
	public void setCompGit(CsAComponenteGit compGit) {
		this.compGit = compGit;
	}

	public boolean isAttiva() {
		return attiva;
	}

	public void setAttiva(boolean attiva) {
		this.attiva = attiva;
	}

	public String getStatoCartella() {
		return statoCartella;
	}

	public void setStatoCartella(String statoCartella) {
		this.statoCartella = statoCartella;
	}
		
}
