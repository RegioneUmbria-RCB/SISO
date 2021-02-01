package it.webred.trainingprj.jsf.interfaces;

import it.webred.trainingprj.jsf.bean.Comune;

import java.util.ArrayList;

import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.validator.Validator;

public interface IDatiAna {
	
	public ArrayList<Comune> getLstComuni(String query);
	public ArrayList<SelectItem> getLstSessi();
	public ArrayList<SelectItem> getLstStatiCivili();
	public ArrayList<SelectItem> getLstCittadinanze();
	public ArrayList<SelectItem> getLstStatus();
	public ArrayList<SelectItem> getLstMediciCuranti();
	public ArrayList<SelectItem> getLstUffici();
	public ArrayList<SelectItem> getLstAssistentiSociali();
	public ArrayList<SelectItem> getLstPeriodiChiusura();
	public ArrayList<SelectItem> getLstMotiviChiusura();
	public void salvaDatiAna();
	public Converter getComuneConverter();
	public Validator getDateValidator();

}
