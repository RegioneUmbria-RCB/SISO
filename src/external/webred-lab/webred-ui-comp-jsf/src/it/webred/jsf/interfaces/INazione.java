package it.webred.jsf.interfaces;



import java.util.List;

import javax.faces.convert.Converter;



public interface INazione {

	
	public List<?> getLstNazioni(String query) ;
	
	public void handleChangeNazione(javax.faces.event.AjaxBehaviorEvent event);

	public Converter getNazioneConverter();
	
	public String getTipoNazione();

}
