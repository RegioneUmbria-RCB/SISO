package it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom;

import it.webred.cs.csa.utils.bean.report.dto.BasePdfDTO;

public class SemiResMinoriPdfDTO extends BasePdfDTO {

	private String speseExtra = EMPTY_VALUE;
	private	String provvAG = EMPTY_VALUE;
	private String pernottamento = EMPTY_VALUE;
	private String cene = EMPTY_VALUE;
	private String pranzi = EMPTY_VALUE;
	private	String interventoEducativo = EMPTY_VALUE;
	private	String altro = EMPTY_VALUE;
	private	String anno = EMPTY_VALUE;
	private	String trasporto = EMPTY_VALUE;
	private	String nGiorni = EMPTY_VALUE;
	private	String tipoRetta = EMPTY_VALUE;
	private	String valoreRetta = EMPTY_VALUE;
	private	String comunita = EMPTY_VALUE;
	private	String contrFamOrigine = EMPTY_VALUE;
	
	public String getSpeseExtra() {
		return speseExtra;
	}
	public void setSpeseExtra(String speseExtra) {
		this.speseExtra = format(speseExtra);
	}
	public String getInterventoEducativo() {
		return interventoEducativo;
	}
	public void setInterventoEducativo(String interventoEducativo) {
		this.interventoEducativo = format(interventoEducativo);
	}
	public String getAltro() {
		return altro;
	}
	public void setAltro(String altro) {
		this.altro = format(altro);
	}
	public String getProvvAG() {
		return provvAG;
	}
	public void setProvvAG(String provvAG) {
		this.provvAG = format(provvAG);
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = format(anno);
	}
	public String getnGiorni() {
		return nGiorni;
	}
	public void setnGiorni(String nGiorni) {
		this.nGiorni = format(nGiorni);
	}
	public String getContrFamOrigine() {
		return contrFamOrigine;
	}
	public void setContrFamOrigine(String contrFamOrigine) {
		this.contrFamOrigine = format(contrFamOrigine);
	}
	public String getPernottamento() {
		return pernottamento;
	}
	public void setPernottamento(String pernottamento) {
		this.pernottamento = format(pernottamento);
	}
	public String getCene() {
		return cene;
	}
	public void setCene(String cene) {
		this.cene = format(cene);
	}
	public String getPranzi() {
		return pranzi;
	}
	public void setPranzi(String pranzi) {
		this.pranzi = format(pranzi);
	}
	public String getTrasporto() {
		return trasporto;
	}
	public void setTrasporto(String trasporto) {
		this.trasporto = format(trasporto);
	}
	public String getTipoRetta() {
		return tipoRetta;
	}
	public void setTipoRetta(String tipoRetta) {
		this.tipoRetta = format(tipoRetta);
	}
	public String getValoreRetta() {
		return valoreRetta;
	}
	public void setValoreRetta(String valoreRetta) {
		this.valoreRetta = format(valoreRetta);
	}
	public String getComunita() {
		return comunita;
	}
	public void setComunita(String comunita) {
		this.comunita = format(comunita);
	}

}
