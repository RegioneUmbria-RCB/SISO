package it.webred.ct.data.access.basic.cnc.flusso290.dto;

import it.webred.ct.data.model.cnc.flusso290.RAnagraficaIntestatarioRuolo;
import it.webred.ct.data.model.cnc.flusso290.RDatiContabili;

import java.io.Serializable;
import java.util.List;

public class AnagraficaImpostaDTO implements Serializable {

	private RAnagraficaIntestatarioRuolo anagraficaIntestatario;
	private List<RDatiContabili> datiContabili;
	
	
	public RAnagraficaIntestatarioRuolo getAnagraficaIntestatario() {
		return anagraficaIntestatario;
	}
	public void setAnagraficaIntestatario(
			RAnagraficaIntestatarioRuolo anagraficaIntestatario) {
		this.anagraficaIntestatario = anagraficaIntestatario;
	}
	public List<RDatiContabili> getDatiContabili() {
		return datiContabili;
	}
	public void setDatiContabili(List<RDatiContabili> datiContabili) {
		this.datiContabili = datiContabili;
	}
	
	
}
