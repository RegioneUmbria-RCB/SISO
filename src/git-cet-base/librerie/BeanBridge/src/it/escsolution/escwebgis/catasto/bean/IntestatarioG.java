/*
 * Created on 9-apr-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.escsolution.escwebgis.catasto.bean;

import it.escsolution.escwebgis.common.EscObject;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IntestatarioG extends EscObject {
	private String codIntestatario;
	private String denominazione;
	private String comune;
	private String partitaIva;
	private String sede;
	private String dataInizio;
	private String quota;
	private String titolo;
	private String dataFine;
	private String pkId;
	private String quotaNum;
	private String quotaDenom;
	private String affidabilita;
	
	/**
	 * 
	 */
	public IntestatarioG() {
		codIntestatario = "";
		denominazione = "";
		comune = "";
		partitaIva = "";
		sede = "";
		dataInizio="";
		quota="";
		titolo = "";
		dataFine = "";
		pkId="";
		quotaNum = "";
		quotaDenom = "";
		affidabilita = "";
	}
	/**
	 * @return
	 */
	public String getChiave() {
			return codIntestatario;
		}
	
	public String getCodIntestatario() {
		return codIntestatario;
	}

	/**
	 * @return
	 */
	public String getComune() {
		return comune;
	}

	/**
	 * @return
	 */
	public String getDenominazione() {
		return denominazione;
	}

	/**
	 * @return
	 */
	public String getPartitaIva() {
		return partitaIva;
	}

	/**
	 * @return
	 */
	public String getSede() {
		return sede;
	}

	/**
	 * @param string
	 */
	public void setCodIntestatario(String string) {
		codIntestatario = string;
	}

	/**
	 * @param string
	 */
	public void setComune(String string) {
		comune = string;
	}

	/**
	 * @param string
	 */
	public void setDenominazione(String string) {
		denominazione = string;
	}

	/**
	 * @param string
	 */
	public void setPartitaIva(String string) {
		partitaIva = string;
	}

	/**
	 * @param string
	 */
	public void setSede(String string) {
		sede = string;
	}

	/**
	 * @return
	 */
	public String getDataInizio() {
		return dataInizio;
	}

	/**
	 * @return
	 */
	public String getQuota() {
		return quota;
	}

	/**
	 * @param string
	 */
	public void setDataInizio(String string) {
		dataInizio = string;
	}

	/**
	 * @param string
	 */
	public void setQuota(String string) {
		quota = string;
	}

	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}
	public String getPkId() {
		return pkId;
	}
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
	public String getQuotaNum() {
		return quotaNum;
	}
	public void setQuotaNum(String quotaNum) {
		this.quotaNum = quotaNum;
	}
	public String getQuotaDenom() {
		return quotaDenom;
	}
	public void setQuotaDenom(String quotaDenom) {
		this.quotaDenom = quotaDenom;
	}
	public String getAffidabilita() {
		return affidabilita;
	}
	public void setAffidabilita(String affidabilita) {
		this.affidabilita = affidabilita;
	}
	
}
