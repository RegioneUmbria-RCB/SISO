package it.webred.cs.csa.web.manbean.report.dto.fascicolo;

import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;

public class ErogazioniPaiPdfDTO extends ReportPdfDTO {
	
	private String tipoIntervento = EMPTY_VALUE;
	private String tipoInterventoCustom = EMPTY_VALUE;
	private String dataUltimaErogazione = EMPTY_VALUE;
	private String statoUltimaErog = EMPTY_VALUE;
	
	public String getTipoIntervento() {
		return tipoIntervento;
	}
	public void setTipoIntervento(String tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}
	public String getTipoInterventoCustom() {
		return tipoInterventoCustom;
	}
	public void setTipoInterventoCustom(String tipoInterventoCustom) {
		this.tipoInterventoCustom = tipoInterventoCustom;
	}
	public String getDataUltimaErogazione() {
		return dataUltimaErogazione;
	}
	public void setDataUltimaErogazione(String dataUltimaErogazione) {
		this.dataUltimaErogazione = dataUltimaErogazione;
	}
	public String getStatoUltimaErog() {
		return statoUltimaErog;
	}
	public void setStatoUltimaErog(String statoUltimaErog) {
		this.statoUltimaErog = statoUltimaErog;
	}
	



}