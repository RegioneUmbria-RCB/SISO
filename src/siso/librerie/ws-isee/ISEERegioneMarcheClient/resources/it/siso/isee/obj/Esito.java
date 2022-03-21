package it.siso.isee.obj;

import it.marche.regione.pddnica.client.EccezioneClient;

public class Esito {

	private String consultazioneEsito;
	private String descErrore;
	private Attestazione attestazione;
	private Dichiarazione dichiarazione;
	private EccezioneClient eccezione;
	
	
	/**
	 * 
	 * @return in caso di errore applicativo "KO", altrimenti si tratta di errori provenienti dal server:
	 * "OK"
	   "RICHIESTA_INVALIDA"
	   "ERRORE_INTERNO"
	   "DATI_NON_TROVATI"
	 */
	public String getConsultazioneEsito() {
		return consultazioneEsito;
	}
	public void setConsultazioneEsito(String consultazioneEsito) {
		this.consultazioneEsito = consultazioneEsito;
	}
	public String getDescErrore() {
		return descErrore;
	}
	public void setDescErrore(String descErrore) {
		this.descErrore = descErrore;
	}
	public Attestazione getAttestazione() {
		return attestazione;
	}
	public void setAttestazione(Attestazione attestazione) {
		this.attestazione = attestazione;
	}
	public Dichiarazione getDichiarazione() {
		return dichiarazione;
	}
	public void setDichiarazione(Dichiarazione dichiarazione) {
		this.dichiarazione = dichiarazione;
	}
	
	public void setEccezioneClient(EccezioneClient eccezione) {
		
		if(eccezione != null) {
			this.setConsultazioneEsito("KO");
			this.setDescErrore("Errore processo di invio/ricezione dati ISEESoapClient");
			this.eccezione = eccezione;
		}
		
	}
	public EccezioneClient getEccezione() {
		return eccezione;
	}
	
	
}
