package it.webred.cs.jsf.interfaces;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IDatiFglIntervento {

	public Long getDiarioId();
	
	public List<SelectItem> getLstTipoAttivita();
	public List<SelectItem> getLstTipoAttivazione();
	public List<SelectItem> getLstTipoSospensione();
	
	public List<SelectItem> getLstTipoPeriodo();
	//public List<SelectItem> getLstTipoIntervento();
	public List<SelectItem> getLstModalitaIntervento();
	public List<SelectItem> getLstMotiviChiusura();
	public List<SelectItem> getLstTipoQuotaPasti();
	public List<SelectItem> getLstTipoRiscossione();
	public List<SelectItem> getLstTipoOreVoucher();
	public List<SelectItem> getLstTipoPeriodoErogazione();
	public List<SelectItem> getLstTipoQuotaCentroD();
	
	public List<SelectItem> getLstTipoGestione();
	public List<SelectItem> getLstTipoDeroghe();
	
	public List<SelectItem> getLstTipoRientriFam();
	public List<SelectItem> getLstTipoRetta();

	public List<SelectItem> getLstRelazioni();

	public List<SelectItem> getLstTipoAffido();

	public List<SelectItem> getLstSpeseExtra();
	public List<SelectItem> getLstSpeseExtraSRM();

	public List<SelectItem> getLstTipoAdmAdh();
	public List<SelectItem> getLstTipoContributo();

	public boolean isDatiInterventoTabRendered();
	public boolean isDatiErogazioniTabRendered();
	
	
}
