package it.webred.cs.csa.ejb.dto.fascicolo.interventi;

import it.webred.cs.csa.ejb.dto.fascicolo.DatiInterceptorDTO;

import java.math.BigDecimal;
import java.util.Date;

public class ListaDatiInterventiDTO extends DatiInterceptorDTO{
	
	public ListaDatiInterventiDTO(Long secondoLivello, Long idSettoreDiario) {
		super(secondoLivello, idSettoreDiario);
	}

	private Long diarioId;
	
	private Date dataChiusuraDa;
	
	private Date dataUltimaModifica;
	private String opUltimaModifica;
	

	public Long getDiarioId() {
		return diarioId;
	}
	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public Date getDataUltimaModifica() {
		return dataUltimaModifica;
	}
	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}
	
	public Date getDataChiusuraDa() {
		return dataChiusuraDa;
	}
	public void setDataChiusuraDa(Date dataChiusuraDa) {
		this.dataChiusuraDa = dataChiusuraDa;
	}
	public String getOpUltimaModifica() {
		return opUltimaModifica;
	}
	public void setOpUltimaModifica(String opUltimaModifica) {
		this.opUltimaModifica = opUltimaModifica;
	}
	
}
