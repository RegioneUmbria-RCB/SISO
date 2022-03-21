package it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom;

import it.webred.cs.csa.utils.bean.report.dto.BasePdfDTO;

public class SchedaUOLPdfDTO extends BasePdfDTO {

	private String codice = EMPTY_VALUE;
	private String anno = EMPTY_VALUE;
	private String educatore = EMPTY_VALUE;
	private String tipoCirc4 = EMPTY_VALUE;
	private	String intervento = EMPTY_VALUE;
	private	String tipoProgetto = EMPTY_VALUE;
	private	String note = EMPTY_VALUE;
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = format(codice);
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = format(anno);
	}
	public String getEducatore() {
		return educatore;
	}
	public void setEducatore(String educatore) {
		this.educatore = format(educatore);
	}
	public String getTipoCirc4() {
		return tipoCirc4;
	}
	public void setTipoCirc4(String tipoCirc4) {
		this.tipoCirc4 = format(tipoCirc4);
	}
	public String getIntervento() {
		return intervento;
	}
	public void setIntervento(String intervento) {
		this.intervento = format(intervento);
	}
	public String getTipoProgetto() {
		return tipoProgetto;
	}
	public void setTipoProgetto(String tipoProgetto) {
		this.tipoProgetto = format(tipoProgetto);
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = format(note);
	}
	
}
