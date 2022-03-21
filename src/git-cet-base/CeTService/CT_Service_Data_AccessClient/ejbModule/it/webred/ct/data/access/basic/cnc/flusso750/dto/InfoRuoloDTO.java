package it.webred.ct.data.access.basic.cnc.flusso750.dto;

import it.webred.ct.data.model.cnc.flusso750.VFrontespizioRuolo;
import it.webred.ct.data.model.cnc.flusso750.VInizioRuolo;

import java.io.Serializable;
import java.util.List;

// Verificare se è necessario utilizzarla per un dettaglio maggiore sul 750 (dati Ruolo)
public class InfoRuoloDTO implements Serializable {

	private VInizioRuolo infoRuolo;
	private List<VFrontespizioRuolo> infoFrontespizioList;
	private String descrAmbito;
	
	public InfoRuoloDTO() {}
	
	
	public InfoRuoloDTO(VInizioRuolo infoRuolo) {
		this (infoRuolo, null);
	}
	
	public InfoRuoloDTO(VInizioRuolo infoRuolo, List<VFrontespizioRuolo> infoFrontespizioList) {
		this.infoRuolo = infoRuolo;
		this.infoFrontespizioList = infoFrontespizioList;
	}
	
	public String getDescrAmbito() {
		return descrAmbito;
	}
	public void setDescrAmbito(String descrAmbito) {
		this.descrAmbito = descrAmbito;
	}
	public VInizioRuolo getInfoRuolo() {
		return infoRuolo;
	}
	public void setInfoRuolo(VInizioRuolo infoRuolo) {
		this.infoRuolo = infoRuolo;
	}
	public List<VFrontespizioRuolo> getInfoFrontespizioList() {
		return infoFrontespizioList;
	}
	public void setInfoFrontespizioList(
			List<VFrontespizioRuolo> infoFrontespizioList) {
		this.infoFrontespizioList = infoFrontespizioList;
	}
	
	
	
}
