package it.webred.cs.csa.web.manbean.report.dto;

public class BasePdfDTO {

	public final String EMPTY_VALUE = "-";
	public final String EMPTY_VALUES = "Non Presente";
	private Boolean showCasellaContributo = false;
	
	protected String format(String value) {
		if(value == null || "".equals(value.trim()))
			return EMPTY_VALUE;
		return value;
	}
	protected String format2(String value) {
		if(value == null || "".equals(value.trim()))
			return EMPTY_VALUES;
		return value;
	}

	public Boolean getShowCasellaContributo() {
		return showCasellaContributo;
	}

	public void setShowCasellaContributo(Boolean showCasellaContributo) {
		this.showCasellaContributo = showCasellaContributo;
	}
	
}
