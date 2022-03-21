package it.umbriadigitale.soclav.managedbeans;

import java.util.List;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.umbriadigitale.soclav.service.dto.CartellaSocialeDTO;
import it.umbriadigitale.soclav.service.interfaccia.IAmbitiSocialeService;
import it.umbriadigitale.soclav.service.interfaccia.IGestioneLogAccessi;

@Controller
public class CarSocialeManBean extends BaseBean  {

	private List<CartellaSocialeDTO> lstCartelleSociali;
	private boolean render;
	
	@Autowired
	IGestioneLogAccessi accessiService;
	
	@Autowired
	IAmbitiSocialeService csocService;
	
	public void loadCartelleSociali(String cf, String ente, boolean logAccesso) {
		logger.debug("START loadCartelleSociali: carica");
		
		 if(!StringUtils.isEmpty(cf) && !StringUtils.isEmpty(ente)) {
			this.lstCartelleSociali = this.csocService.findCartelleSociali(cf, ente);
			if(!this.lstCartelleSociali.isEmpty()) {
				if(logAccesso) {
					AutenticazioneBean ab  = (AutenticazioneBean)getBeanReference("autenticazioneBean");
					String operatore = ab.getCetUser().getUsername();
					this.accessiService.salva("CARTELLA_SOCIALE", ente, cf, operatore);
				}
				this.render = true;
			}
		  } //else addWarningFromProperties("seleziona.warning");
		
		logger.debug("END loadCartelleSociali: carica");
	} 
	
	public ActionListener getCloseCarSocialeDialog() {
	    return new ActionListener() {
	        @Override
	        public void processAction(ActionEvent event) throws AbortProcessingException {
	        	render = false;
	        }
	    };
	}
	
	public void handleDialogCsClose(CloseEvent event) {
        this.render = false;
    }
     
	public List<CartellaSocialeDTO> getLstCartelleSociali() {
		return lstCartelleSociali;
	}

	public void setLstCartelleSociali(List<CartellaSocialeDTO> lstCartelleSociali) {
		this.lstCartelleSociali = lstCartelleSociali;
	}

	public boolean isRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

}
