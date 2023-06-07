package it.webred.cs.json.provvedimentiMinori.tabs;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IInterventiTutela {

	public List<SelectItem> getLstParenti();
	
	public String getTabName();
	
	public List<SelectItem> getLstTipoColocamento();

	public List<SelectItem> getLstIncontriProtetti();

	public List<String> getLstInterventiTutelaCheck();

	public List<String> getLstSospensioneRapporti();
	
	public  String getCmbxForRadioAffido();

	public  String getCmbxForRadioIncontri();

}
