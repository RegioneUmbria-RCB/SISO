package it.webred.cs.csa.ejb.dto.fascicolo.isee;

import it.webred.cs.csa.ejb.dto.fascicolo.DatiInterceptorDTO;

import java.math.BigDecimal;
import java.util.Date;

public class ListaDatiIseeDTO extends DatiInterceptorDTO{
	
	public ListaDatiIseeDTO(Long secondoLivello, Long idSettoreDiario) {
		super(secondoLivello, idSettoreDiario);
	}

	private Long diarioId;
	private String annoRif;
	private BigDecimal ise;
	private BigDecimal isee;
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
	public String getAnnoRif() {
		return annoRif;
	}
	public void setAnnoRif(String annoRif) {
		this.annoRif = annoRif;
	}
	public BigDecimal getIse() {
		return ise;
	}
	public void setIse(BigDecimal ise) {
		this.ise = ise;
	}
	public BigDecimal getIsee() {
		return isee;
	}
	public void setIsee(BigDecimal isee) {
		this.isee = isee;
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
