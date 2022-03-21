package it.webred.rulengine.dwh.table;

public class SitLicenzeCommercioVie extends TabellaDwhMultiProv {
	
	private String sedime;
	private String indirizzo;
	
	public String getSedime() {
		return sedime;
	}

	public void setSedime(String sedime) {
		this.sedime = sedime;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	@Override
	public String getValueForCtrHash()
	{
		return this.sedime +
		this.indirizzo +
		this.getProvenienza();
	}
	
}
