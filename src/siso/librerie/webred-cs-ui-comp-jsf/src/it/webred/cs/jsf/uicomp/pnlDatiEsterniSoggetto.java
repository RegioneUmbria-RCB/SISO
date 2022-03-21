package it.webred.cs.jsf.uicomp;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

@FacesComponent(value = "pnlDatiEsterniSoggetto")
public class pnlDatiEsterniSoggetto extends UINamingContainer implements Serializable {

	private static final long serialVersionUID = 6899858320255055829L;

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		// Map<String, Object> value = getAttributes();
		super.encodeBegin(context);
	}

	@Override
	public String getId() {
		String id = super.getId();
		return id;
	}

}
