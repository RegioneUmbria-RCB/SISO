package it.siso.isee.obj;

import java.io.Serializable;

public class DatiDelegato implements Serializable {

	private Anagrafica anagrafica;
	private Residenza indirizzo;
	public Anagrafica getAnagrafica() {
		return anagrafica;
	}
	public void setAnagrafica(Anagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}
	public Residenza getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(Residenza indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	
	
}
