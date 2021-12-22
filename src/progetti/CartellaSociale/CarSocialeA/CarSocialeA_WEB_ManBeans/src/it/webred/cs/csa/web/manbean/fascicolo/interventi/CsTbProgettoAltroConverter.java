package it.webred.cs.csa.web.manbean.fascicolo.interventi;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.data.model.CsTbProgettoAltro;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class CsTbProgettoAltroConverter implements Converter {
	

public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {  
		
	CsTbProgettoAltro pa = null;
		if (submittedValue != null && !submittedValue.trim().equals("")) {
			try {
				
				AccessTableConfigurazioneSessionBeanRemote configService = 
						(AccessTableConfigurazioneSessionBeanRemote)CsUiCompBaseBean.getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
								
		    		
				CsTbProgettoAltro progettoAltro = configService.getProgettoAltroByDescrizione(submittedValue);
				
				if (progettoAltro!=null)
					pa = progettoAltro;
				else{
					pa = new CsTbProgettoAltro();
					pa.setDescrizione(submittedValue);
					pa.setAbilitato(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return pa;
	}

	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
		if (value == null || value.equals("")) {
			return "";  
		} else {
			return ((CsTbProgettoAltro)value).getDescrizione();
		}
	}  
	
}
