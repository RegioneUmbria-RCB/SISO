/*
 * Created on 10-mag-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.escsolution.escwebgis.ecografico.bean;
import it.escsolution.escwebgis.common.EscObject;
import it.escsolution.escwebgis.toponomastica.bean.Strada;

import java.io.Serializable;
import java.util.List;
/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StradaEcografico extends EscObject  implements Serializable {

	private static final long serialVersionUID = 1L;

	 private String  id;
	 private String  pkSequStrada;
	 private String  codiceStrada;
	 private String  fkComuniBelfiore;
	 private String  ukStrada;
	 private String  specieStrada;
	 private String  nomeStrada;
	 private String  tipologia;
	 private String  ordinamento;
	 private String  fkLocalita;
	 private String  altraLocalita;
	 private String  fkStradaInizio;
	 private String  fkStradaFine;
	 private String  exStrada;
	 private String  descrStradaInizio;
	 private String  descrStradaFine;
	 private String  comune;
	 private String  note;
	 private String  descrLocalita;
	 private String  codiceLocalita;
	 
	 public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPkSequStrada() {
		return pkSequStrada;
	}
	public void setPkSequStrada(String pkSequStrada) {
		this.pkSequStrada = pkSequStrada;
	}
	public String getCodiceStrada() {
		return codiceStrada;
	}
	public void setCodiceStrada(String codiceStrada) {
		this.codiceStrada = codiceStrada;
	}
	public String getFkComuniBelfiore() {
		return fkComuniBelfiore;
	}
	public void setFkComuniBelfiore(String fkComuniBelfiore) {
		this.fkComuniBelfiore = fkComuniBelfiore;
	}
	public String getUkStrada() {
		return ukStrada;
	}
	public void setUkStrada(String ukStrada) {
		this.ukStrada = ukStrada;
	}
	public String getSpecieStrada() {
		return specieStrada;
	}
	public void setSpecieStrada(String specieStrada) {
		this.specieStrada = specieStrada;
	}
	public String getNomeStrada() {
		return nomeStrada;
	}
	public void setNomeStrada(String nomeStrada) {
		this.nomeStrada = nomeStrada;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getOrdinamento() {
		return ordinamento;
	}
	public void setOrdinamento(String ordinamento) {
		this.ordinamento = ordinamento;
	}
	public String getFkLocalita() {
		return fkLocalita;
	}
	public void setFkLocalita(String fkLocalita) {
		this.fkLocalita = fkLocalita;
	}
	public String getAltraLocalita() {
		return altraLocalita;
	}
	public void setAltraLocalita(String altraLocalita) {
		this.altraLocalita = altraLocalita;
	}
	public String getFkStradaInizio() {
		return fkStradaInizio;
	}
	public void setFkStradaInizio(String fkStradaInizio) {
		this.fkStradaInizio = fkStradaInizio;
	}
	public String getFkStradaFine() {
		return fkStradaFine;
	}
	public void setFkStradaFine(String fkStradaFine) {
		this.fkStradaFine = fkStradaFine;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getDescrLocalita() {
		return descrLocalita;
	}
	public void setDescrLocalita(String descrLocalita) {
		this.descrLocalita = descrLocalita;
	}
	public String getExStrada() {
		return exStrada;
	}
	public void setExStrada(String exStrada) {
		this.exStrada = exStrada;
	}
	public String getDescrStradaInizio() {
		return descrStradaInizio;
	}
	public void setDescrStradaInizio(String descrStradaInizio) {
		this.descrStradaInizio = descrStradaInizio;
	}
	public String getDescrStradaFine() {
		return descrStradaFine;
	}
	public void setDescrStradaFine(String descrStradaFine) {
		this.descrStradaFine = descrStradaFine;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getCodiceLocalita() {
		return codiceLocalita;
	}
	public void setCodiceLocalita(String codiceLocalita) {
		this.codiceLocalita = codiceLocalita;
	}
	
	public String getIdFonte() {
		return "29";
	}

	public String getTipoFonte() {
		return "TOPONOMASTICA";
	}

	public String getDiaKey() {
		if (diaKey != null && !diaKey.equals("")) {
			return diaKey;
		}
		diaKey = "";
		if (codiceStrada != null && !codiceStrada.equals("")) {
			diaKey += codiceStrada;
		}
		return diaKey;
	}
	
	
}
