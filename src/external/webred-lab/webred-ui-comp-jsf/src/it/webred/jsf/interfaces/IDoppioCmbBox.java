package it.webred.jsf.interfaces;

import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

public interface IDoppioCmbBox {
	
	public void codiceValueChangeListener(AjaxBehaviorEvent event);
	
	public void codInizialeValueChangeListener(AjaxBehaviorEvent event);
	
	public void salva();

	public void reset();
	
	public String getCodIniziale();
	
	public String getIdScelta();
	
	public String getDescrScelta();
	
	public String getTextIfEmpty();
	
	public String getWidgetVar();
	
	public String getDialogHeader();
	
	public String getLabelComboBox();
		
	public String getIdSceltaSalvata();

	public String getDescrSceltaSalvata();
	
	public String getTestoIniziale();
	
	public List<SelectItem> getLstCodIniziali();
	
	public List<SelectItem> getLstCodici();

	
}
