package it.umbriadigitale.interscambio.data.wrapper;

public class ObiettivoWrapper {
	private String codiceObiettivo;
	private String indicatoreEsitoCode;
	private String tempisticheMonitoraggioText;
	
	
	public ObiettivoWrapper(String codiceObiettivo, String indicatoreEsitoCode, String tempisticheMonitoraggioText) {
		this.codiceObiettivo = codiceObiettivo;
		this.indicatoreEsitoCode = indicatoreEsitoCode;
		this.tempisticheMonitoraggioText = tempisticheMonitoraggioText;
	}
	
	
	public String getCodiceObiettivo() {
		return codiceObiettivo;
	}

	public String getIndicatoreEsitoCode() {
		return indicatoreEsitoCode;
	}

	public String getTempisticheMonitoraggioText() {
		return tempisticheMonitoraggioText;
	}
}