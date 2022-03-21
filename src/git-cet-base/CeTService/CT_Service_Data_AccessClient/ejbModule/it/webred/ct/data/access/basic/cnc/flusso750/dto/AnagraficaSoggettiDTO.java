package it.webred.ct.data.access.basic.cnc.flusso750.dto;

import it.webred.ct.data.model.cnc.flusso750.VAnagrafeCointestatario;
import it.webred.ct.data.model.cnc.flusso750.VAnagrafeDebitore;
import it.webred.ct.data.model.cnc.flusso750.VAnagrafeUlterioriDestinatariNot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AnagraficaSoggettiDTO extends InfoRuoloDTO implements Serializable {
	
	private List<VAnagrafeDebitore> anagraficaIntestatarioList ;
	
	private List<VAnagrafeCointestatario> anagraficaCointestatarioList;
	
	private List<VAnagrafeUlterioriDestinatariNot> anagraficaUlterioriDestinatariNotList;

	public List<VAnagrafeDebitore> getAnagraficaIntestatarioList() {
		return anagraficaIntestatarioList;
	}

	public void setAnagraficaIntestatarioList(
			List<VAnagrafeDebitore> anagraficaIntestatarioList) {
		this.anagraficaIntestatarioList = anagraficaIntestatarioList;
	}
	

	public List<VAnagrafeCointestatario> getAnagraficaCointestatarioList() {
		return anagraficaCointestatarioList;
	}

	public void setAnagraficaCointestatarioList(
			List<VAnagrafeCointestatario> anagraficaCointestatarioList) {
		this.anagraficaCointestatarioList = anagraficaCointestatarioList;
	}

	public List<VAnagrafeUlterioriDestinatariNot> getAnagraficaUlterioriDestinatariNotList() {
		return anagraficaUlterioriDestinatariNotList;
	}

	public void setAnagraficaUlterioriDestinatariNotList(
			List<VAnagrafeUlterioriDestinatariNot> anagraficaUlterioriDestinatariNotList) {
		this.anagraficaUlterioriDestinatariNotList = anagraficaUlterioriDestinatariNotList;
	}

	public void addAnagDebitore(VAnagrafeDebitore anagDeb) {
		if (anagraficaIntestatarioList == null)
			anagraficaIntestatarioList = new ArrayList<VAnagrafeDebitore>();
		
		anagraficaIntestatarioList.add(anagDeb);
	}
}
