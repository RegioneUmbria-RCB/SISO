package it.webred.cs.jsf.interfaces;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.model.SelectItem;

public interface IFormazioneLavoro {
	
	public List<SelectItem> getLstTitoliStudio();
	
	public List<SelectItem> getLstProfessioni();
	
	public List<SelectItem> getLstConLavorativa();
	
	public List<SelectItem> getLstSettoreImpiego();
	
	public BigDecimal getIdTitoloStudio();
	
	public BigDecimal getIdProfessione();

	public BigDecimal getIdSettoreImpiego();
	
	public BigDecimal getIdCondLavorativa();
	
	public void onChangeProfessione();

	public boolean validaData();

	public String getTitoloStudioIstat();

	public String getTitoloStudio();
}
