package it.webred.cs.csa.web.bean.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author Andrea
 *
 */

@FacesConverter("stringTrimmer")
public class StringTrimmer implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value.toString().length() < 150 || !(value instanceof String)){
			return value.toString();
		}
		
		return value.toString().substring(0, 150) + "...";
	}

}
