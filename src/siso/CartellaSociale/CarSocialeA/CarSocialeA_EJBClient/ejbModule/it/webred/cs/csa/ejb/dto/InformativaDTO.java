package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;
import java.util.List;

public class InformativaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long   idClasse;
	private String codiceMemo;
	private String descClasse;
	private String descClasseISTAT;
	
	private List<CodificaINPS> lstCodINPS;

	public Long getIdClasse() {
		return idClasse;
	}

	public String getCodiceMemo() {
		return codiceMemo;
	}

	public String getDescClasse() {
		return descClasse;
	}

	public String getDescClasseISTAT() {
		return descClasseISTAT;
	}

	public List<CodificaINPS> getLstCodINPS() {
		return lstCodINPS;
	}

	public void setIdClasse(Long idClasse) {
		this.idClasse = idClasse;
	}

	public void setCodiceMemo(String codiceMemo) {
		this.codiceMemo = codiceMemo;
	}

	public void setDescClasse(String descClasse) {
		this.descClasse = descClasse;
	}

	public void setDescClasseISTAT(String descClasseISTAT) {
		this.descClasseISTAT = descClasseISTAT;
	}

	public void setLstCodINPS(List<CodificaINPS> lstCodINPS) {
		this.lstCodINPS = lstCodINPS;
	}
	
}
