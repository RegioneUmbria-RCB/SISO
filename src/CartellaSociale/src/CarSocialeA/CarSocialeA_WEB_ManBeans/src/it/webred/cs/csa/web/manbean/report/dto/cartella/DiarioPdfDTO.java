package it.webred.cs.csa.web.manbean.report.dto.cartella;



import it.webred.cs.csa.web.manbean.report.dto.BasePdfDTO;

public class DiarioPdfDTO extends BasePdfDTO {
	
	
	private String dataColloquio;
	private String dove = EMPTY_VALUE;
	private String conChi = EMPTY_VALUE;
	private String tipoColloquio = EMPTY_VALUE;
	private String riservato = EMPTY_VALUE;
	private String testoDiario = EMPTY_VALUE;
	
	
	
	
	public String getDove() {
		return dove;
	}
	public void setDove(String dove) {
		this.dove = format(dove);
	}
	public String getConChi() {
		return conChi;
	}
	public void setConChi(String conChi) {
		this.conChi = format(conChi);
	}
	public String getTipoColloquio() {
		return tipoColloquio;
	}
	public void setTipoColloquio(String tipoColloquio) {
		this.tipoColloquio = format(tipoColloquio);
	}

	public String getTestoDiario() {
		return testoDiario;
	}
	public void setTestoDiario(String testoDiario) {
		this.testoDiario = format(testoDiario);
	}
	public String getDataColloquio() {
		return dataColloquio;
	}
	public void setDataColloquio(String dataColloquio) {
		this.dataColloquio = format(dataColloquio);
	}
	public String getRiservato() {
		return riservato;
	}
	public void setRiservato(String riservato) {
		this.riservato = riservato;
	}
	

	
	
	

}
