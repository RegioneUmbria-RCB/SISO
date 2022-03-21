package it.webred.ct.data.access.basic.indice;

import it.webred.ct.data.access.basic.indice.civico.dto.ListaCiviciByVia;
import it.webred.ct.data.access.basic.indice.dto.AggregaUnici;
import it.webred.ct.data.access.basic.indice.dto.CambiaUnico;
import it.webred.ct.data.access.basic.indice.dto.ListaTotale;
import it.webred.ct.data.access.basic.indice.dto.ListaTotaleBySorgente;
import it.webred.ct.data.access.basic.indice.dto.ListaUnico;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class IndiceDataIn extends CeTBaseObject implements Serializable{

	private Object obj;	
	private ListaCiviciByVia listaCiviciByVia;
	private ListaUnico listaUnico;
	private ListaTotale listaTotale;
	private ListaTotaleBySorgente listaTotaleBySorgente;
	private CambiaUnico cambiaUnico;
	private AggregaUnici aggregaUnici;
	
	
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public ListaCiviciByVia getListaCiviciByVia() {
		return listaCiviciByVia;
	}

	public void setListaCiviciByVia(ListaCiviciByVia listaCiviciByVia) {
		this.listaCiviciByVia = listaCiviciByVia;
	}

	public ListaUnico getListaUnico() {
		return listaUnico;
	}

	public void setListaUnico(ListaUnico listaUnico) {
		this.listaUnico = listaUnico;
	}

	public ListaTotale getListaTotale() {
		return listaTotale;
	}

	public void setListaTotale(ListaTotale listaTotale) {
		this.listaTotale = listaTotale;
	}

	public ListaTotaleBySorgente getListaTotaleBySorgente() {
		return listaTotaleBySorgente;
	}

	public void setListaTotaleBySorgente(ListaTotaleBySorgente listaTotaleBySorgente) {
		this.listaTotaleBySorgente = listaTotaleBySorgente;
	}

	public CambiaUnico getCambiaUnico() {
		return cambiaUnico;
	}

	public void setCambiaUnico(CambiaUnico cambiaUnico) {
		this.cambiaUnico = cambiaUnico;
	}

	public AggregaUnici getAggregaUnici() {
		return aggregaUnici;
	}

	public void setAggregaUnici(AggregaUnici aggregaUnici) {
		this.aggregaUnici = aggregaUnici;
	}
	
	
	
	
	
	
	
}
