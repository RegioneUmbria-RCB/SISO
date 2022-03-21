package it.webred.ct.data.access.basic.rette.dto;

import java.io.Serializable;
import java.util.List;

import it.webred.ct.data.model.rette.SitRttBollette;
import it.webred.ct.data.model.rette.SitRttDettBollette;
import it.webred.ct.data.model.rette.SitRttRateBollette;

public class RetteDTO implements Serializable {

	private SitRttBollette sitRttBollette;
	private List<SitRttDettBollette> listaSitRttDettBollette;
	private List<SitRttRateBollette> listaSitRttRateBollette;
	
	public SitRttBollette getSitRttBollette() {
		return sitRttBollette;
	}
	public void setSitRttBollette(SitRttBollette sitRttBollette) {
		this.sitRttBollette = sitRttBollette;
	}
	public List<SitRttDettBollette> getListaSitRttDettBollette() {
		return listaSitRttDettBollette;
	}
	public void setListaSitRttDettBollette(
			List<SitRttDettBollette> listaSitRttDettBollette) {
		this.listaSitRttDettBollette = listaSitRttDettBollette;
	}
	public List<SitRttRateBollette> getListaSitRttRateBollette() {
		return listaSitRttRateBollette;
	}
	public void setListaSitRttRateBollette(
			List<SitRttRateBollette> listaSitRttRateBollette) {
		this.listaSitRttRateBollette = listaSitRttRateBollette;
	}
	
}
