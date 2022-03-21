package it.webred.ct.data.access.basic.indice.oggetto.dto;

import java.io.Serializable;

import it.webred.ct.data.access.basic.indice.dto.SitOperationDTO;
import it.webred.ct.data.model.indice.SitOggettoUnico;

public class SitOggettoDTO extends SitOperationDTO implements Serializable{

	private String foglio;
	private String particella;
	private String sub;
	private String fonteDati;
	private long fkEnteSorgente;
	private SitOggettoUnico sitOggettoUnico;
	
	public SitOggettoUnico getSitOggettoUnico() {
		return sitOggettoUnico;
	}
	public void setSitOggettoUnico(SitOggettoUnico sitOggettoUnico) {
		this.sitOggettoUnico = sitOggettoUnico;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getFonteDati() {
		return fonteDati;
	}
	public void setFonteDati(String fonteDati) {
		this.fonteDati = fonteDati;
	}
	public long getFkEnteSorgente() {
		return fkEnteSorgente;
	}
	public void setFkEnteSorgente(long fkEnteSorgente) {
		this.fkEnteSorgente = fkEnteSorgente;
	}
	
}
