package it.webred.cs.csa.web.manbean.report.dto;

public class HeaderPdfDTO extends BasePdfDTO {

	private String imagePath;
	private String nomeSettore;
	private String nomeUfficio;
	private String nomeEnte;
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getNomeUfficio() {
		return nomeUfficio;
	}
	public void setNomeUfficio(String nomeUfficio) {
		this.nomeUfficio = nomeUfficio;
	}
	public String getNomeEnte() {
		return nomeEnte;
	}
	public void setNomeEnte(String nomeEnte) {
		this.nomeEnte = nomeEnte;
	}
	public String getNomeSettore() {
		return nomeSettore;
	}
	public void setNomeSettore(String nomeSettore) {
		this.nomeSettore = nomeSettore;
	}
	
}
