package it.webred.ct.data.access.basic.segnalazionequalificata;

import it.webred.ct.data.access.basic.segnalazionequalificata.dto.RicercaPraticaSegnalazioneDTO;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class SegnalazioniDataIn extends CeTBaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Object obj1;
	private Object obj2;
	
	private RicercaPraticaSegnalazioneDTO ricercaPratica;
	
	public Object getObj1() {
		return obj1;
	}

	public void setObj1(Object obj1) {
		this.obj1 = obj1;
	}

	public Object getObj2() {
		return obj2;
	}

	public void setObj2(Object obj2) {
		this.obj2 = obj2;
	}

	public RicercaPraticaSegnalazioneDTO getRicercaPratica() {
		return ricercaPratica;
	}

	public void setRicercaPratica(RicercaPraticaSegnalazioneDTO ricercaPratica) {
		this.ricercaPratica = ricercaPratica;
	}
	
}
