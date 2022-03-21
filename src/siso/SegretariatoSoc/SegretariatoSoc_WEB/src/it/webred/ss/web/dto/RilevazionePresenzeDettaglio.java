package it.webred.ss.web.dto;

import java.io.Serializable;
import java.util.Date;

import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;

public class RilevazionePresenzeDettaglio extends PersonaDettaglio implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idTitoloDiStudio;
    private Long idVulnerabilita;
	private Long idStruttura;
    private Long idArea;
    private String unitaAbitativa;
    private Long idCondizioneLavorativa;
    private String messaggio;
    private Exception eccezione;
	
	public RilevazionePresenzeDettaglio(){}



	public Long getIdTitoloDiStudio() {
		return idTitoloDiStudio;
	} 



	public void setIdTitoloDiStudio(Long idTitoloDiStudio) {
		this.idTitoloDiStudio = idTitoloDiStudio;
	}



	public Long getIdVulnerabilita() {
		return idVulnerabilita;
	}



	public void setIdVulnerabilita(Long idVulnerabilita) {
		this.idVulnerabilita = idVulnerabilita;
	}



	public Long getIdStruttura() {
		return idStruttura;
	}



	public void setIdStruttura(Long idStruttura) {
		this.idStruttura = idStruttura;
	}



	public Long getIdArea() {
		return idArea;
	}



	public void setIdArea(Long idArea) {
		this.idArea = idArea;
	}



	public String getUnitaAbitativa() {
		return unitaAbitativa;
	}



	public void setUnitaAbitativa(String unitaAbitativa) {
		this.unitaAbitativa = unitaAbitativa;
	}



	public Long getIdCondizioneLavorativa() {
		return idCondizioneLavorativa;
	}



	public void setIdCondizioneLavorativa(Long idCondizioneLavorativa) {
		this.idCondizioneLavorativa = idCondizioneLavorativa;
	}
	
	
	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	
	public Exception getEccezione() {
		return eccezione;
	}

	public void setEccezione(Exception eccezione) {
		this.eccezione = eccezione;
	}
}
