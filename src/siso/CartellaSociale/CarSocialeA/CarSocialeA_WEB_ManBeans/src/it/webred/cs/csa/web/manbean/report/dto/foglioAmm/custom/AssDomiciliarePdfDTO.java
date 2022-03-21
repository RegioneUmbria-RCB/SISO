package it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom;

import it.webred.cs.csa.utils.bean.report.dto.BasePdfDTO;

public class AssDomiciliarePdfDTO extends BasePdfDTO {

	private String tipo = EMPTY_VALUE;
	private	String provvAG = EMPTY_VALUE;
	private	String anno = EMPTY_VALUE;
	private	String tipoOre = EMPTY_VALUE;
	private	String nOre = EMPTY_VALUE;
	private	String nAccessi = EMPTY_VALUE;
	private	String contrFamiglia = EMPTY_VALUE;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = format(tipo);
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
	public String getTipoOre() {
		return tipoOre;
	}
	public void setTipoOre(String tipoOre) {
		this.tipoOre = format(tipoOre);
	}
	public String getnOre() {
		return nOre;
	}
	public void setnOre(String nOre) {
		this.nOre = format(nOre);
	}
	public String getnAccessi() {
		return nAccessi;
	}
	public void setnAccessi(String nAccessi) {
		this.nAccessi = format(nAccessi);
	}
	public String getContrFamiglia() {
		return contrFamiglia;
	}
	public void setContrFamiglia(String contrFamiglia) {
		this.contrFamiglia = format(contrFamiglia);
	}
	
}
