package it.webred.cs.csa.web.manbean.report.dto.cartella;

import it.webred.cs.csa.utils.bean.report.dto.StoricoPdfDTO;

public class DatiSocialiAbitazionePdfDTO extends StoricoPdfDTO {
	
	private String tipologiaAbitazione = EMPTY_VALUE;
	private String numeroVani = EMPTY_VALUE;
	private String titoloDiGodimento = EMPTY_VALUE;
	private String proprietarioOGestore = EMPTY_VALUE;
	private String note = EMPTY_VALUE;



	public String getTipologiaAbitazione() {
		return tipologiaAbitazione;
	}



	public void setTipologiaAbitazione(String tipologiaAbitazione) {
		this.tipologiaAbitazione = tipologiaAbitazione;
	}



	public String getNumeroVani() {
		return numeroVani;
	}



	public void setNumeroVani(String numeroVani) {
		this.numeroVani = numeroVani;
	}



	public String getTitoloDiGodimento() {
		return titoloDiGodimento;
	}



	public void setTitoloDiGodimento(String titoloDiGodimento) {
		this.titoloDiGodimento = titoloDiGodimento;
	}



	public String getProprietarioOGestore() {
		return proprietarioOGestore;
	}



	public void setProprietarioOGestore(String proprietarioOGestore) {
		this.proprietarioOGestore = proprietarioOGestore;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}

}
