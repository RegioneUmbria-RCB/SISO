package it.siso.isee.obj;

import java.io.Serializable;
import java.util.List;

/***
 *  Veicoli e beni durevoli 
    (Quadro FC6) 
 * @author franc
 *
 */
public class Veicoli implements Serializable {

	private List<Veicolo> listaVeicoli;

	public List<Veicolo> getListaVeicoli() {
		return listaVeicoli;
	}

	public void setListaVeicoli(List<Veicolo> listaVeicoli) {
		this.listaVeicoli = listaVeicoli;
	}
	
	
}
