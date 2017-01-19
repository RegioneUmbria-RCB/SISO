package it.webred.cs.jsf.interfaces;

import java.util.ArrayList;

import javax.faces.model.SelectItem;
import javax.faces.validator.Validator;

public interface IDatiSocApe {
	
	public ArrayList<SelectItem> getLstUffici();
	public ArrayList<SelectItem> getLstAssistentiSociali();
	public Validator getDateValidator();

}
