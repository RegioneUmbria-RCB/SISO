package it.umbriadigitale.interscambio.data.wrapper;

public class FaseWrapper {
	private String idFaseText;
	private String codiceFase;
	private String annotazioni;
	private String statoFaseCode;
	private String dataInizioText;
	private String dataFineText;
	private String codiceEsito;
	
	
	public FaseWrapper(String idFaseText, String codiceFase, String annotazioni, String statoFaseCode,
			String dataInizioText, String dataFineText, String codiceEsito) {
		
		this.idFaseText = idFaseText;
		this.codiceFase = codiceFase;
		this.annotazioni = annotazioni;
		this.statoFaseCode = statoFaseCode;
		this.dataInizioText = dataInizioText;
		this.dataFineText = dataFineText;
		this.codiceEsito = codiceEsito;
	}
	
	
	public String getIdFaseText() {
		return idFaseText;
	}

	public String getCodiceFase() {
		return codiceFase;
	}

	public String getAnnotazioni() {
		return annotazioni;
	}

	public String getStatoFaseCode() {
		return statoFaseCode;
	}

	public String getDataInizioText() {
		return dataInizioText;
	}

	public String getDataFineText() {
		return dataFineText;
	}

	public String getCodiceEsito() {
		return codiceEsito;
	}
}
