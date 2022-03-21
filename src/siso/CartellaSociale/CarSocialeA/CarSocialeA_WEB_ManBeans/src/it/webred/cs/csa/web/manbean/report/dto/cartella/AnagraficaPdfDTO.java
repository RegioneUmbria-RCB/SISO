package it.webred.cs.csa.web.manbean.report.dto.cartella;


public class AnagraficaPdfDTO extends DatiAnagraficiPdfDTO {

	private String statoCivile = EMPTY_VALUE;
	private String status = EMPTY_VALUE;
	private String medico = EMPTY_VALUE;
	private String infoMedico = EMPTY_VALUE;
	
	private String catSociale = EMPTY_VALUE;
	private String assSociale = EMPTY_VALUE;
	private String dataPIC = EMPTY_VALUE;
	
	public String getStatoCivile() {
		return statoCivile;
	}
	public String getStatus() {
		return status;
	}
	public String getMedico() {
		return medico;
	}
	public String getCatSociale() {
		return catSociale;
	}
	public String getAssSociale() {
		return assSociale;
	}
	public String getDataPIC() {
		return dataPIC;
	}
	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setMedico(String medico) {
		this.medico = medico;
	}
	public void setCatSociale(String catSociale) {
		this.catSociale = catSociale;
	}
	public void setAssSociale(String assSociale) {
		this.assSociale = assSociale;
	}
	public void setDataPIC(String dataPIC) {
		this.dataPIC = dataPIC;
	}
	public String getInfoMedico() {
		return infoMedico;
	}
	public void setInfoMedico(String infoMedico) {
		this.infoMedico = infoMedico;
	}
}
