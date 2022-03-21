package it.webred.ct.data.access.basic.ici.dto;

import it.webred.ct.data.model.ici.SitTIciOggetto;
import it.webred.ct.data.model.ici.VTIciSoggAll;

public class SoggOggIciDTO {
	
	private SitTIciOggetto oggetto;
	private VTIciSoggAll soggetto;
	
	public SitTIciOggetto getOggetto() {
		return oggetto;
	}
	public void setOggetto(SitTIciOggetto oggetto) {
		this.oggetto = oggetto;
	}
	public VTIciSoggAll getSoggetto() {
		return soggetto;
	}
	public void setSoggetto(VTIciSoggAll soggetto) {
		this.soggetto = soggetto;
	}

}
