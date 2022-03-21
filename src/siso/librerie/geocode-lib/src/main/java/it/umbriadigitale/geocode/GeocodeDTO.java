package it.umbriadigitale.geocode;

public class GeocodeDTO {

	private double latitude = 0;
	private double longitudine = 0;
	String formattedAddress;
	boolean partialMatch;
	String placeId;
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(double longitudine) {
		this.longitudine = longitudine;
	}
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	public boolean isPartialMatch() {
		return partialMatch;
	}
	public void setPartialMatch(boolean partialMatch) {
		this.partialMatch = partialMatch;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	
	
	
}
