package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.CsTbDisabTipologia;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.model.SelectItem;

public interface IDatiDisabilita {
	
	public boolean isPDF();
	
	public boolean isDF();
	
	public boolean isConsulenzaCOP();

	public String getAnno();
	
	public String getCertificatore();
	
	public String getTerapie();
	
	public BigDecimal getIdGravita();
	
	public BigDecimal getIdTipologia();
	
	public BigDecimal getIdEnte();
	
	public boolean isNisValutazione();
	
	public boolean isNisConsulenza();
	
	public boolean isNisOrientamento();
	
	public List<SelectItem> getLstTipologia();
	
	public List<SelectItem> getLstEnte();
		
	public List<SelectItem> getLstGravita();
	
	public List<CsTbDisabTipologia> getLstCsTbDisabTipologia();

}
