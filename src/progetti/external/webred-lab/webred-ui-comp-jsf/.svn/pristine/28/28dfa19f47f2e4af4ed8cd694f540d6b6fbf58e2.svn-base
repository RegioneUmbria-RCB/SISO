package it.webred.jsf.utils;

import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ejb.utility.ClientUtility;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.naming.NamingException;


public class NazioneConverter implements Converter {
	
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {  
		AmTabNazioni nazione = null;
		if (submittedValue != null && !submittedValue.trim().equals("")) {
			try {
				LuoghiService luoghiService = (LuoghiService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");
		    	
				nazione = luoghiService.getNazioneByIstat(submittedValue);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return nazione;
	}

	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
		if (value == null || value.equals("")) {
			return "";  
		} else {
			return ((AmTabNazioni)value).getCodIstatNazione();
		}
	}  

}  

