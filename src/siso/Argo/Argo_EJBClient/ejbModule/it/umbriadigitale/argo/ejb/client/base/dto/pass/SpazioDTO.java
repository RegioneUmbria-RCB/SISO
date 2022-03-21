package it.umbriadigitale.argo.ejb.client.base.dto.pass;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class SpazioDTO extends CeTBaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String latitude;
	String longitude;
	String belfiore;

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	
	public String getLatitude() {
		return latitude;
		
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLongitude() {
		return longitude;
		
	}

	public String getBelfiore() {
		// TODO Auto-generated method stub
		return belfiore;
	}

}
