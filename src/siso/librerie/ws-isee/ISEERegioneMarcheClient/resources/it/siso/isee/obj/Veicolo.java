package it.siso.isee.obj;

import java.io.Serializable;

public class Veicolo implements Serializable {
 private String targa;
 /***
  * Può assumere uno dei seguenti valori:
	A – Autoveicolo
	M – Motoveicolo
	N – Nave
	I – Imbarcazione da diporto
  */
 private String tipoVeicolo;
	
 
 public String getTarga() {
		return targa;
	}
	public void setTarga(String targa) {
		this.targa = targa;
	}
	public String getTipoVeicolo() {
		return tipoVeicolo;
	}
	public void setTipoVeicolo(String tipoVeicolo) {
		this.tipoVeicolo = tipoVeicolo;
	}
 
 
}
