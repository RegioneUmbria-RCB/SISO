package it.webred.ct.data.access.basic.ici.dto;

import java.io.Serializable;
import java.util.List;

import it.webred.ct.data.model.ici.*;

public class IciDTO implements Serializable{
	
	private SitTIciOggetto oggetto;
	private List<VTIciSoggAll> soggetti;
	public void setSoggetti(List<VTIciSoggAll> soggetti) {
		this.soggetti = soggetti;
	}
	public List<VTIciSoggAll> getSoggetti() {
		return soggetti;
	}
	public void setOggetto(SitTIciOggetto oggetto) {
		this.oggetto = oggetto;
	}
	public SitTIciOggetto getOggetto() {
		return oggetto;
	}

}
