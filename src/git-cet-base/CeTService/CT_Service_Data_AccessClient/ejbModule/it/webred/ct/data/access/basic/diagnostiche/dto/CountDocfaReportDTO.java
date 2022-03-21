package it.webred.ct.data.access.basic.diagnostiche.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CountDocfaReportDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Date fornitura;
	
	private Long totali;
	
	private Long elaborati;
	
	private Long elabNoAnom;
	
	private Long elabSiAnom;

	public Date getFornitura() {
		return fornitura;
	}
	
	public String getFornituraToString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(fornitura);
	}

	public void setFornitura(Date fornitura) {
		this.fornitura = fornitura;
	}

	public Long getTotali() {
		return totali;
	}

	public void setTotali(Long totali) {
		this.totali = totali;
	}

	public Long getElaborati() {
		return elaborati;
	}

	public void setElaborati(Long elaborati) {
		this.elaborati = elaborati;
	}

	public Long getElabNoAnom() {
		return elabNoAnom;
	}

	public void setElabNoAnom(Long elabNoAnom) {
		this.elabNoAnom = elabNoAnom;
	}

	public Long getElabSiAnom() {
		return elabSiAnom;
	}

	public void setElabSiAnom(Long elabSiAnom) {
		this.elabSiAnom = elabSiAnom;
	}

}
