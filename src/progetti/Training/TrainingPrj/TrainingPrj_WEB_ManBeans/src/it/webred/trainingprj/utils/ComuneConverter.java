package it.webred.trainingprj.utils;

import it.webred.cs.sample.ejb.AccessTableDataComuniSessionBeanRemote;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.trainingprj.jsf.bean.Comune;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.naming.NamingException;

public class ComuneConverter implements Converter {  

	public static ArrayList<Comune> getLstComuni() {
		ArrayList<Comune> lstComuni = new ArrayList<Comune>();
		try {
			AccessTableDataComuniSessionBeanRemote bean = (AccessTableDataComuniSessionBeanRemote) ClientUtility.getEjbInterface("TrainingPrj", "TrainingPrj_EJB", "AccessTableDataComuniSessionBean");
			CeTBaseObject dataIn = new CeTBaseObject();
			dataIn.setEnteId("F704");
			List<String> comuni = bean.getComuni(dataIn);
			for (String comune : comuni) {
				Comune comuneToAdd = new Comune();
				comuneToAdd.setCodice(comune.substring(0, 4).toUpperCase()); //TODO
				comuneToAdd.setDescrizione(comune);
				lstComuni.add(comuneToAdd);
			}		
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return lstComuni;
	}
	
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {  
		if (submittedValue.trim().equals("")) {  
			return null;  
		} else {  
			for (Comune comune : getLstComuni()) {  
				if (comune.getCodice().equals(submittedValue)) {  
					return comune;  
				}
			}  
			return null;  
		} 
	}

	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
		if (value == null || value.equals("")) {
			return "";  
		} else {
			return ((Comune)value).getCodice();
		}
	}  

}  
