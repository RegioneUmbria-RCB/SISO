package it.webred.ct.data.access.basic.indice.dto;

import java.io.Serializable;

import it.webred.ct.data.model.indice.SitCivicoUnico;
import it.webred.ct.data.model.indice.SitOggettoUnico;
import it.webred.ct.data.model.indice.SitSoggettoUnico;
import it.webred.ct.data.model.indice.SitViaUnico;

public class SitNuovoDTO implements Serializable{

	private SitViaUnico sitViaUnico = new SitViaUnico();;
	private SitCivicoUnico sitCivicoUnico = new SitCivicoUnico();
	private SitSoggettoUnico sitSoggettoUnico = new SitSoggettoUnico();
	private SitOggettoUnico sitOggettoUnico = new SitOggettoUnico();
	
	public SitViaUnico getSitViaUnico() {
		return sitViaUnico;
	}
	public void setSitViaUnico(SitViaUnico sitViaUnico) {
		this.sitViaUnico = sitViaUnico;
	}
	public SitCivicoUnico getSitCivicoUnico() {
		return sitCivicoUnico;
	}
	public void setSitCivicoUnico(SitCivicoUnico sitCivicoUnico) {
		this.sitCivicoUnico = sitCivicoUnico;
	}
	public SitSoggettoUnico getSitSoggettoUnico() {
		return sitSoggettoUnico;
	}
	public void setSitSoggettoUnico(SitSoggettoUnico sitSoggettoUnico) {
		this.sitSoggettoUnico = sitSoggettoUnico;
	}
	public SitOggettoUnico getSitOggettoUnico() {
		return sitOggettoUnico;
	}
	public void setSitOggettoUnico(SitOggettoUnico sitOggettoUnico) {
		this.sitOggettoUnico = sitOggettoUnico;
	}
	
}
