package it.webred.cs.jsf.interfaces;

import java.util.ArrayList;
import javax.faces.model.SelectItem;
import javax.faces.validator.Validator;

public interface IDatiChi {
	
	public ArrayList<SelectItem> getLstPeriodiChiusura();
	public ArrayList<SelectItem> getLstMotiviChiusura();
	public Validator getDateValidator();

}
