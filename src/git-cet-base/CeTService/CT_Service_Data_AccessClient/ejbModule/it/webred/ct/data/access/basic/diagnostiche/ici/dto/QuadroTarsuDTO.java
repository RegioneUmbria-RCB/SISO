package it.webred.ct.data.access.basic.diagnostiche.ici.dto;

import it.webred.ct.data.access.basic.diagnostiche.tarsu.dto.SitTTarOggettoDTO;
import it.webred.ct.data.model.diagnostiche.DocfaTarReport;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class QuadroTarsuDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private DocfaTarReport report;
	private List<SitTTarOggettoDTO> supDichTarsu;
	
	public DocfaTarReport getReport() {
		return report;
	}
	public void setReport(DocfaTarReport report) {
		this.report = report;
	}
	public void setSupDichTarsu(List<SitTTarOggettoDTO> supDichTarsu) {
		this.supDichTarsu = supDichTarsu;
	}
	public List<SitTTarOggettoDTO> getSupDichTarsu() {
		return supDichTarsu;
	}
	
}
