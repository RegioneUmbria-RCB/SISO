package eu.smartpeg.gedclient;

public class NominativoGED {
	
	private String descrizione;
	private String codiceFiscale;

	/**
	 * Metodo Factory per creare oggetti di tipo NominativoGED
	 * @param descrizione
	 * @param codiceFiscale
	 * @return
	 */
	static public NominativoGED creaNominativoGED( String descrizione,String codiceFiscale) {
		NominativoGED nominativoGED = new NominativoGED();
		
		nominativoGED.setDescrizione(descrizione);
		nominativoGED.setCodiceFiscale(codiceFiscale);
		
		return nominativoGED;		
	}
	
	private NominativoGED () {
		
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	private  void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public  String getCodiceFiscale() {
		return codiceFiscale;
	}
	private void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	@Override
	public String toString() {
		return "NominativoGED [descrizione=" + descrizione + ", codiceFiscale=" + codiceFiscale + "]";
	}

}
