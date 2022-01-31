package it.escsolution.escwebgis.pubblicita.bean;

import it.escsolution.escwebgis.common.EscObject;
import it.escsolution.escwebgis.cosapNew.bean.CosapTassa;

import java.io.Serializable;
import java.util.ArrayList;

public class PubblicitaTestata extends EscObject implements Serializable {


	private static final long serialVersionUID = 1L;
	String id;
	String tipoPratica;
	Integer numPratica;
	String annoPratica;
	String descPratica;
	String dtInizio;
	String dtDecTermini;
	String provvedimento;
	
	ArrayList<PubblicitaDettaglio> lstDettaglio;
	

	public String getChiave() {
		return id;
	}
	
	public ArrayList<PubblicitaDettaglio> getLstDettaglio() {
		return lstDettaglio;
	}
	public void setLstDettaglio(ArrayList<PubblicitaDettaglio> lstDettaglio) {
		this.lstDettaglio = lstDettaglio;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipoPratica() {
		return tipoPratica;
	}
	public void setTipoPratica(String tipoPratica) {
		this.tipoPratica = tipoPratica;
	}
	
	public String getAnnoPratica() {
		return annoPratica;
	}
	public void setAnnoPratica(String annoPratica) {
		this.annoPratica = annoPratica;
	}
	public String getDescPratica() {
		return descPratica;
	}
	public void setDescPratica(String descPratica) {
		this.descPratica = descPratica;
	}
	public String getDtInizio() {
		return dtInizio;
	}
	public void setDtInizio(String dtInizio) {
		this.dtInizio = dtInizio;
	}
	public String getDtDecTermini() {
		return dtDecTermini;
	}
	public void setDtDecTermini(String dtDecTermini) {
		this.dtDecTermini = dtDecTermini;
	}
	public String getProvvedimento() {
		return provvedimento;
	}
	public void setProvvedimento(String provvedimento) {
		this.provvedimento = provvedimento;
	}
	
	public Integer getNumPratica() {
		return numPratica;
	}
	public void setNumPratica(Integer numPratica) {
		this.numPratica = numPratica;
	}
}
