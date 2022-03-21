package it.webred.cs.jsf.converter;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
 
 
@FacesConverter("userSearchConverter")
public class UserSearchConverter extends CsUiCompBaseBean implements Converter {
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
    	DatiUserSearchBean sDto = null;
    	if(value != null && value.trim().length() > 0) {
            try {
            	logger.debug("UserSearchConverter "+value);
 
            		String codIndividuale = null;
            		String tipoRicerca = null;
            		if(value.startsWith(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA)){
            			tipoRicerca = DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA;
            			codIndividuale = value.replace(tipoRicerca, "");
            		}else if(value.startsWith(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE)){
            			tipoRicerca = DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE;
            			codIndividuale = value.replace(tipoRicerca, "");
            		}else if(value.startsWith(DataModelCostanti.TipoRicercaSoggetto.SIGESS)){
            			tipoRicerca = DataModelCostanti.TipoRicercaSoggetto.SIGESS;
            			codIndividuale = value.replace(tipoRicerca, "");
            		}else if(value.startsWith(DataModelCostanti.TipoRicercaSoggetto.DEFAULT)){
            			tipoRicerca = DataModelCostanti.TipoRicercaSoggetto.DEFAULT;
            			codIndividuale = value.replace(tipoRicerca, "");
            		}
            		
            		PersonaDettaglio s = null;
            		String id =         !StringUtils.isBlank(codIndividuale) && !codIndividuale.startsWith("@") ? codIndividuale : null;
            		String codFiscale = !StringUtils.isBlank(codIndividuale) &&  codIndividuale.startsWith("@") ? codIndividuale.replace("@","") : null;
            		
            		if(id!=null)
            			s = super.getPersonaDaAnagEsterna(tipoRicerca, codIndividuale);
            		else
            			s = super.getPersonaDaAnagEsterna(tipoRicerca, null, null, codFiscale);
            		
            		if(s!=null){
        				sDto = new DatiUserSearchBean();
    					sDto.setSoggetto(s);
    					String itemLabel = s.getCognome().toUpperCase() + " " + s.getNome().toUpperCase();
    					if (s.getDataNascita() != null)
    						itemLabel += " nato il: " + ddMMyyyy.format(s.getDataNascita());
    					
    					if(s.isDefunto()){
    						itemLabel += " morto";
	    					if(s.getDataMorte()!=null)
	    						itemLabel  += " il: " + ddMMyyyy.format(s.getDataMorte());
    					}
    					
    					sDto.setItemLabel(itemLabel);
    					String identificativo = s.getIdentificativo()!=null ? s.getIdentificativo() : "@" +s.getCodfisc();
    					sDto.setId(tipoRicerca+identificativo);
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