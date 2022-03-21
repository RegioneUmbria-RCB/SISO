package it.webred.cs.jsf.interfaces;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IContributi {
	
	public boolean isPrimaErogazione();
	
	public boolean isInterventoPre();
	
	public boolean isRinnovo();
	
	public boolean isRichRespinta();
	
	public boolean isSospensione();
	
	public Long getIdBuono();
	
	public Long getIdEsenzione();
	
	public Long getIdRiduzione();
	
	public List<SelectItem> getLstEsenzioniRiduzioni();
	
	public List<SelectItem> getLstBuoni();

}
