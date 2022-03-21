package it.webred.jsf.utils;

import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.naming.NamingException;


public class ComuneConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {  
		
		ComuneBean cb = null;
		if (submittedValue != null && !submittedValue.trim().equals("")) {
			try {
				LuoghiService luoghiService = (LuoghiService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");
		    	
				AmTabComuni comune = luoghiService.getComuneItaByIstat(submittedValue);
				
				if (comune!=null)
					cb = new ComuneBean(comune);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
		return cb;
	}

	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
		if (value == null || value.equals("")) {
			return "";  
		} else {
			return ((ComuneBean)value).getCodIstatComune();
		}
	}  

}  
