package it.webred.ct.data.access.basic.praticheportale.dto;

import java.io.Serializable;
import java.util.List;

import it.webred.ct.data.model.praticheportale.PsAnagraficaView;
import it.webred.ct.data.model.praticheportale.PsPratica;

public class PraticaPortaleDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private PsPratica pratica;
	private PsAnagraficaView richiedente;
	private PsAnagraficaView fruitore;
	
	public PsPratica getPratica() {
		return pratica;
	}
	public void setPratica(PsPratica pratica) {
		this.pratica = pratica;
	}
	public PsAnagraficaView getRichiedente() {
		return richiedente;
	}
	public void setRichiedente(PsAnagraficaView richiedente) {
		this.richiedente = richiedente;
	}
	public PsAnagraficaView getFruitore() {
		return fruitore;
	}
	public void setFruitore(PsAnagraficaView fruitore) {
		this.fruitore = fruitore;
	}
}
