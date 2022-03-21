package it.webred.cs.jsf.bean;

import it.webred.ct.data.model.anagrafe.SitDVia;

public class DatiIndirizzoBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SitDVia sitDVia;
	private String itemLabel;

	public SitDVia getSitDVia() {
		return sitDVia;
	}

	public void setSitDVia(SitDVia sitDVia) {
		this.sitDVia = sitDVia;
	}

	public String getItemLabel() {
		return itemLabel;
	}

	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

}
