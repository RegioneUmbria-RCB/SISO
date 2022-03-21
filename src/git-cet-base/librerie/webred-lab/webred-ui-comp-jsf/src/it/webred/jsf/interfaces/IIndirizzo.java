package it.webred.jsf.interfaces;



import java.util.List;



public interface IIndirizzo {

	
	public List<?> getLstIndirizzi(String query);
	
	public void handleChangeIndirizzo(javax.faces.event.AjaxBehaviorEvent event);

	public List<?> getLstCivici();
	
	public String getWidgetVar();
}
