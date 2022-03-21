package it.webred.cs.sociosan.ejb.dto.isee;

import java.io.Serializable;
import java.util.List;

public class RicercaIseeResult implements Serializable{

    private List<DatiIsee> elencoAttestazioni;
	private String messaggio;
    private Integer codice;
    private Exception eccezione;
    
    
	public Exception getEccezione() {
		return eccezione;
	}

	public void setEccezione(Exception eccezione) {
		this.eccezione = eccezione;
	}

	public Integer getCodice() {
		return codice;
	}

	public void setCodice(Integer codice) {
		this.codice = codice;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public List<DatiIsee> getElencoAttestazioni() {
		return elencoAttestazioni;
	}

	public void setElencoAttestazioni(List<DatiIsee> elencoAttestazioni) {
		this.elencoAttestazioni = elencoAttestazioni;
	}

}
