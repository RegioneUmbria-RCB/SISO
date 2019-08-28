package it.webred.cs.jsf.converter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean.AnagRegUser;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.DatiPersonaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.ws.client.anag.client.AnagrafeClient;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;
import it.webred.siso.ws.client.anag.client.RicercaAnagraficaBean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
 
@FacesConverter("userSearchConverter")
public class UserSearchConverter extends CsUiCompBaseBean implements Converter {
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
    	DatiUserSearchBean sDto = null;
    	if(value != null && value.trim().length() > 0) {
            try {
            	logger.debug("UserSearchConverter "+value);
            	
            	if(value.startsWith("SANITARIA")){
            		AnagRegUser anagReguser = getAnagRegUser();
        			URL wsdlLocation = getAnagRegWebServiceWSDLLocation();
        			if(anagReguser!=null && wsdlLocation!=null){
        				List <PersonaFindResult> listAnagReg=new ArrayList <PersonaFindResult>();
        				
        				AnagrafeClient anag = new AnagrafeClient(wsdlLocation);
        				
        				RicercaAnagraficaBean rb = new RicercaAnagraficaBean();
        				String idPaziente = value.replace("SANITARIA", "");
        				rb.setIdPaziente(idPaziente);
        				rb.setUsername(anagReguser.getUsername());
        				rb.setPassword(anagReguser.getPassword());
        				anag.openSession(rb);
        				
        				PersonaFindResult s = anag.getDatiAnagraficiBaseByIdPaziente(rb);
        				if(s!=null){
	        				sDto = new DatiUserSearchBean();
	    					sDto.setSoggetto(s);
	    					String itemLabel = s.getCognome().toUpperCase() + " "
	    							+ s.getNome().toUpperCase();
	    					if (s.getDataNascita() != null)
	    						itemLabel += " nato il: " + ddMMyyyy.format(s.getDataNascita())+". *Ricavato da anagrafe sanitaria regionale*";
	    					sDto.setItemLabel(itemLabel);
	    					sDto.setId("SANITARIA"+idPaziente);
        				}
    				}
            		
            	}else{
            		RicercaSoggettoAnagrafeDTO rsDto = new RicercaSoggettoAnagrafeDTO();
            		fillEnte(rsDto);
            		rsDto.setIdVarSogg(value);
            		
            		AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface("CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
            			
            		SitDPersona s = anagrafeService.getPersonaById(rsDto);
        			if(s!=null){
	            		sDto = new DatiUserSearchBean();
	    				sDto.setSoggetto(s);	
	    				String itemLabel = s.getCognome().toUpperCase() + " " + s.getNome().toUpperCase();
	    				if(s.getDataNascita() != null)
	    					itemLabel += " nato il: " + ddMMyyyy.format(s.getDataNascita());
	    				
	    				if(s.getDataMor()!=null)
	    					itemLabel += " morto il: " + ddMMyyyy.format(s.getDataMor());
	    				
	    				sDto.setItemLabel(itemLabel);
	    				sDto.setId(s.getId());
	    				
        			}
    				
            	}
            		
            	
                
            } catch(Exception e) {
            	logger.error(e);
            }
        }
           return sDto;
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((DatiUserSearchBean) object).getId());
        }
        else {
            return null;
        }
    }   
}     