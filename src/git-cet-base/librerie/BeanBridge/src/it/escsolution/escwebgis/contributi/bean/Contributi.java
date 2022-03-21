package it.escsolution.escwebgis.contributi.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Contributi extends EscObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private LinkedHashMap<String, String> datiTestata;
	
	private static final ArrayList<String> campiLista = new ArrayList<String>();
	static {
		campiLista.add("ANNO_MANDATO");
		campiLista.add("CENTRO_RESPONSABILITA");
		campiLista.add("DESCRIZIONE_CDR");
		campiLista.add("OGGETTO_CAPITOLO");
		campiLista.add("IMPORTO_SU_IMPEGNO");
		campiLista.add("CODICE_FISCALE");
		campiLista.add("RAGIONE_SOCIALE");
	}
	
	private ArrayList<ContributiSib> contributiSib;

	
	public LinkedHashMap<String, String> getDatiTestata() {
		return datiTestata;
	}

	public void setDatiTestata(LinkedHashMap<String, String> datiTestata) {
		this.datiTestata = datiTestata;
	}

	public static ArrayList<String> getCampiLista() {
		return campiLista;
	}

	public ArrayList<ContributiSib> getContributiSib() {
		return contributiSib;
	}

	public void setContributiSib(ArrayList<ContributiSib> contributiSib) {
		this.contributiSib = contributiSib;
	}

}
