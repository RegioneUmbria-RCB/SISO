package it.webred.jsf.uicomp;

import it.webred.jsf.interfaces.IComune;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;


@FacesComponent(value="pnlComune")
public class pnlComuneComp extends UINamingContainer implements Serializable  {

	IComune com = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 @Override
	 public void encodeBegin(FacesContext context) throws IOException {
	 
	         Map<String, Object> value = getAttributes();
	         // com = (IComune) value.get("comuneManBean");

	        super.encodeBegin(context);
	 }
	 

	
}
