package it.webred.cs.csa.web.manbean.report.dto.cartella;

import it.webred.cs.csa.web.manbean.report.dto.BasePdfDTO;

public class NotePdfDTO extends BasePdfDTO {

	private String note = EMPTY_VALUE;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = format(note);
	}
	
}
