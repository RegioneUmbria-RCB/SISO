package it.webred.cs.csa.utils.bean.report.dto;


public class StoricoPdfDTO extends BasePdfDTO {

	private String dataInizio = EMPTY_VALUE;
	private String dataFine = EMPTY_VALUE;
	
	
	
	public String getDataInizio() {
		return dataInizio;
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}
	
	
}
