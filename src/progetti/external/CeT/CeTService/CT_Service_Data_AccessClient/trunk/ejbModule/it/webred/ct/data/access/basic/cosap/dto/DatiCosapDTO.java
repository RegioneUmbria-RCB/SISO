package it.webred.ct.data.access.basic.cosap.dto;

import it.webred.ct.data.model.cosap.SitTCosapContrib;
import it.webred.ct.data.model.cosap.SitTCosapTassa;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class DatiCosapDTO extends CeTBaseObject implements Serializable{
	
	private SitTCosapTassa oggetto;
	private SitTCosapContrib soggetto;
	
	
	public SitTCosapTassa getOggetto() {
		return oggetto;
	}
	public void setOggetto(SitTCosapTassa oggetto) {
		this.oggetto = oggetto;
	}
	public SitTCosapContrib getSoggetto() {
		return soggetto;
	}
	public void setSoggetto(SitTCosapContrib soggetto) {
		this.soggetto = soggetto;
	}
	

}
