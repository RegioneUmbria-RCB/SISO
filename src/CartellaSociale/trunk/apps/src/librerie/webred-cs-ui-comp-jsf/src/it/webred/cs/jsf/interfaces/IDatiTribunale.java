package it.webred.cs.jsf.interfaces;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IDatiTribunale {
	
	public boolean isTMCivile();
	
	public boolean isTMAmministrativo();
	
	public boolean isPenaleMinorile();
	
	public boolean isTO();
	
	public boolean isNIS();
	
	public boolean isProcuraMinorile();
	
	public Long getIdMacroSegnalazione();
	
	public Long getIdMicroSegnalazione();
	
	public Long getIdMotivoSegnalazione();
	
	public String getNumeroProtocollo();
	
	public List<SelectItem> getLstMacroSegnalazioni();
	
	public List<SelectItem> getLstMicroSegnalazioni();
	
	public List<SelectItem> getLstMotiviSegnalazioni();

}
