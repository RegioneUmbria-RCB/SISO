package it.umbriadigitale.interscambio.data.wrapper;

public class ProgettoIndividualeWrapper {
	private String codiceProgetto;
	private String oggetto;
	private String dataInizioText;
	private String dataFineText;
	
	
	public ProgettoIndividualeWrapper(String codiceProgetto, String oggetto, String dataInizioText,	String dataFineText) {
		this.codiceProgetto = codiceProgetto;
		this.oggetto = oggetto;
		this.dataInizioText = dataInizioText;
		this.dataFineText = dataFineText;
	}
	
	
	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public String getOggetto() {
		return oggetto;
	}

	public String getDataInizioText() {
		return dataInizioText;
	}

	public String getDataFineText() {
		return dataFineText;
	}
}
