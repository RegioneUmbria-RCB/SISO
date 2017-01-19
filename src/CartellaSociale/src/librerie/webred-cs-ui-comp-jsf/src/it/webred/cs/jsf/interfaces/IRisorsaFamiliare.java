package it.webred.cs.jsf.interfaces;

import it.webred.cs.csa.ejb.dto.RisorsaFamDTO;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IRisorsaFamiliare {

	public String getWidgetVar();
	
	public RisorsaFamDTO getSelRisorsa();

	public String getSelCfRisorsa();
	
	public List<SelectItem> getRisorseItem();

	public String getTooltip();
	

}
