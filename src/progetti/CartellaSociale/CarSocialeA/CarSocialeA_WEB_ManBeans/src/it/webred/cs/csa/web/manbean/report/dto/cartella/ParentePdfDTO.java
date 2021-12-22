package it.webred.cs.csa.web.manbean.report.dto.cartella;


public class ParentePdfDTO extends DatiAnagraficiPdfDTO {

	private String parentela = EMPTY_VALUE;
	private String decesso = EMPTY_VALUE;
	
	public String getParentela() {
		return parentela;
	}
	public String getDecesso() {
		return decesso;
	}
	public void setParentela(String parentela) {
		this.parentela = parentela;
	}
	public void setDecesso(String decesso) {
		this.decesso = decesso;
	}
	
	
	
}
