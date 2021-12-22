package it.webred.cs.csa.utils.bean.report.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ValoriPdfDTO extends BasePdfDTO {

	private String descrizione  = EMPTY_VALUE;
	private String tooltip  = EMPTY_VALUE;
	private String dallaData  = EMPTY_VALUE;
	
	protected SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	
	public ValoriPdfDTO(String descrizione, String tooltip, Date dallaData){
		this.descrizione = descrizione; 
		this.tooltip = tooltip;
		this.dallaData = dallaData!=null ? "dalla data: "+ ddMMyyyy.format(dallaData) : "";
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	public String getTooltip() {
		return tooltip;
	}
	public String getDallaData() {
		return dallaData;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public void setDallaData(String dallaData) {
		this.dallaData = dallaData;
	}
}
