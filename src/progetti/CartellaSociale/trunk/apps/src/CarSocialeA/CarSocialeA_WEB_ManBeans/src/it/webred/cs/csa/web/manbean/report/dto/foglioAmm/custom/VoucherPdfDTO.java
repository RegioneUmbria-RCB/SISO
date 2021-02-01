package it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom;

import it.webred.cs.csa.utils.bean.report.dto.BasePdfDTO;

public class VoucherPdfDTO extends BasePdfDTO {

	private String aumentoOre = EMPTY_VALUE;
	private String diminuzioneOre = EMPTY_VALUE;
	private String dal = EMPTY_VALUE;
	private	String tipoOre = EMPTY_VALUE;
	private	String orePreviste = EMPTY_VALUE;
	private	String nAccessi = EMPTY_VALUE;
	private	String contributoUtente = EMPTY_VALUE;
	
	public String getAumentoOre() {
		return aumentoOre;
	}
	public void setAumentoOre(String aumentoOre) {
		this.aumentoOre = format(aumentoOre);
	}
	public String getDiminuzioneOre() {
		return diminuzioneOre;
	}
	public void setDiminuzioneOre(String diminuzioneOre) {
		this.diminuzioneOre = format(diminuzioneOre);
	}
	public String getDal() {
		return dal;
	}
	public void setDal(String dal) {
		this.dal = format(dal);
	}
	public String getTipoOre() {
		return tipoOre;
	}
	public void setTipoOre(String tipoOre) {
		this.tipoOre = format(tipoOre);
	}
	public String getOrePreviste() {
		return orePreviste;
	}
	public void setOrePreviste(String orePreviste) {
		this.orePreviste = format(orePreviste);
	}
	public String getnAccessi() {
		return nAccessi;
	}
	public void setnAccessi(String nAccessi) {
		this.nAccessi = format(nAccessi);
	}
	public String getContributoUtente() {
		return contributoUtente;
	}
	public void setContributoUtente(String contributoUtente) {
		this.contributoUtente = format(contributoUtente);
	}
	
}
