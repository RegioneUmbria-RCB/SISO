
package it.webred.ct.data.access.basic.f24.dto;

import it.webred.ct.data.model.f24.SitTF24Annullamento;
import it.webred.ct.data.model.f24.SitTF24Versamenti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class F24AnnullamentoDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private SitTF24Annullamento annullamento;
	private String descTipoTributo;
	private String descTipoImposta;
	private String descTipoEnteRd;
	private String descCodEnteRd;
	
	public void setAnnullamento(SitTF24Annullamento annullamento) {
		this.annullamento = annullamento;
	}
	
	public String getChiave(){
		return annullamento.getId();
	}

	public String getId() {
		return annullamento.getId();
	}
	
	public String getTipoOperazione(){
		return annullamento.getTipoOperazione();
	}
	
	public Date getDtOperazione(){
		return annullamento.getDtOperazione();
	}
	

	public Date getDtBonifico(){
		return annullamento.getDtBonificoOrig();
	}
	
	public BigDecimal getAnnoRif() {
		return annullamento.getAnnoRif();
	}

	public String getCf() {
		return annullamento.getCf();
	}

	public String getCodEnteCom() {
		return annullamento.getCodEnteCom();
	}

	public String getCodEnteRd() {
		return annullamento.getCodEnteRd();
	}

	public String getCodTributo() {
		return annullamento.getCodTributo();
	}

	public String getCodValuta() {
		return annullamento.getCodValuta();
	}

	public Date getDtFornitura() {
		return annullamento.getDtFornitura();
	}

	public Date getDtRipartizione() {
		return annullamento.getDtRipartOrig();
	}

	public Date getDtRiscossione() {
		return annullamento.getDtRiscossione();
	}

	
	public String getIdExtTestata() {
		return annullamento.getIdExtTestata();
	}

	public BigDecimal getImpCredito() {
		return annullamento.getImpCredito().divide(new BigDecimal(100));
	}

	public BigDecimal getImpDebito() {
		return annullamento.getImpDebito().divide(new BigDecimal(100));
	}

	public String getDescTipoTributo() {
		return descTipoTributo;
	}

	public void setDescTipoTributo(String descTipoTributo) {
		this.descTipoTributo = descTipoTributo;
	}

	public BigDecimal getProgFornitura() {
		return annullamento.getProgFornitura();
	}

	public BigDecimal getProgRipartizione() {
		return annullamento.getProgRipartOrig();
	}


	public String getTipoImposta() {
		return annullamento.getTipoImposta();
	}


	public void setDescTipoEnteRd(String descTipoEnteRd) {
		this.descTipoEnteRd = descTipoEnteRd;
	}

	public String getDescTipoImposta() {
		
		String imp = this.getTipoImposta();
		if("I".equalsIgnoreCase(imp))
			descTipoImposta = "ICI/IMU";
		else if("O".equalsIgnoreCase(imp))
			descTipoImposta = "TOSAP/COSAP";
		else if("T".equalsIgnoreCase(imp))
			descTipoImposta = "TARSU/TARIFFA";
		else if("S".equalsIgnoreCase(imp))
			descTipoImposta = "Tassa di scopo";
		else if("R".equalsIgnoreCase(imp))
			descTipoImposta = "Contributo/Imposta di soggiorno";
		else if("A".equalsIgnoreCase(imp))
			descTipoImposta = "TARES";
		else if("U".equalsIgnoreCase(imp))
			descTipoImposta = "TASI";
		else
			descTipoImposta= "";
		
		return descTipoImposta;
	}

	public void setDescTipoImposta(String descTipoImposta) {
		this.descTipoImposta = descTipoImposta;
	}

	public void setDescCodEnteRd(String descCodEnteRd) {
		this.descCodEnteRd = descCodEnteRd;
	}

}
