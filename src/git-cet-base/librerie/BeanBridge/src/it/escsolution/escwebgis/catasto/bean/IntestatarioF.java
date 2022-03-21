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
public class IntestatarioF extends EscObject {
	private String codIntestatario;
	private String cognome;
	private String nome;
	private String comune;
	private String sesso;
	private String dataNascita;
	private String luogo;
	private String codFiscale;
	private String dataInizio;
	private String quota;
	private String titolo;
	private String dataFine;
	private String pkId;
	private String regime;
	private String descRegime;
	private String soggCollegato;
	private String quotaNum;
	private String quotaDenom;
	private String affidabilita;
	
	/**
	 * 
	 */
	public IntestatarioF() {
		codIntestatario = "";
		cognome = "";
		nome = "";
		comune = "";
		sesso = "";
		dataNascita = "";
		luogo = "";
		codFiscale = "";
		dataInizio= "";
		quota= "";
		titolo = "";
		dataFine ="";
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
	
	public String getCodFiscale() {
		return codFiscale;
	}

	/**
	 * @return
	 */
	public String getCodIntestatario() {
		return codIntestatario;
	}

	/**
	 * @return
	 */
	public String getCognome() {
		return cognome;
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
	public String getDataNascita() {
		return dataNascita;
	}

	/**
	 * @return
	 */
	public String getLuogo() {
		return luogo;
	}

	/**
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return
	 */
	public String getSesso() {
		return sesso;
	}

	/**
	 * @param string
	 */
	public void setCodFiscale(String string) {
		codFiscale = string;
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
	public void setCognome(String string) {
		cognome = string;
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
	public void setDataNascita(String string) {
		dataNascita = string;
	}

	/**
	 * @param string
	 */
	public void setLuogo(String string) {
		luogo = string;
	}

	/**
	 * @param string
	 */
	public void setNome(String string) {
		nome = string;
	}

	/**
	 * @param string
	 */
	public void setSesso(String string) {
		sesso = string;
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
	public String getRegime() {
		return regime;
	}
	public void setRegime(String regime) {
		this.regime = regime;
	}
	public String getDescRegime() {
		return descRegime;
	}
	public void setDescRegime(String descRegime) {
		this.descRegime = descRegime;
	}
	public String getSoggCollegato() {
		return soggCollegato;
	}
	public void setSoggCollegato(String soggCollegato) {
		this.soggCollegato = soggCollegato;
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
