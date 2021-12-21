package it.umbriadigitale.soclav.managedbeans;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.umbriadigitale.soclav.service.dto.sap.Lavoratore;
import it.umbriadigitale.soclav.service.interfaccia.ICentriImpiegoService;
import it.umbriadigitale.soclav.service.interfaccia.IGestioneLogAccessi;

@Controller
public class SapManBean extends BaseBean  {

	private boolean render;
	private Lavoratore sap;
	
	@Autowired
	IGestioneLogAccessi accessiService;
	
	@Autowired
	ICentriImpiegoService cpiService; 
	
	
	public void load(String codSAP, String ente, boolean logAccesso) {
		logger.debug("START loadSAP: carica");
		
		 if(!StringUtils.isBlank(codSAP)) {
			 sap = cpiService.find(codSAP);
		    if(sap==null) {
				 logger.error("SAP non presente");
				 return;
		    }
			if(this.sap!=null && logAccesso) {
				AutenticazioneBean ab  = (AutenticazioneBean)getBeanReference("autenticazioneBean");
				String operatore = ab.getCetUser().getUsername();
				String cf = sap.getDatianagrafici().getDatipersonali().getCodicefiscale();
				this.accessiService.salva("SAP", ente, cf, operatore);
				this.render = true;
			}
		  } //else addWarningFromProperties("seleziona.warning");
		
		logger.debug("END loadSAP: carica");
	} 
	
	public ActionListener getCloseCarSocialeDialog() {
	    return new ActionListener() {
	        @Override
	        public void processAction(ActionEvent event) throws AbortProcessingException {
	        	render = false;
	        }
	    };
	}
	
	public void handleDialogSAPClose(CloseEvent event) {
        this.render = false;
    }
     
	public boolean isRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

	public Lavoratore getSap() {
		return sap;
	}

	public void setSap(Lavoratore sap) {
		this.sap = sap;
	}
     
}
