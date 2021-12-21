package it.umbriadigitale.soclav.managedbeans.lavoro;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.umbriadigitale.soclav.managedbeans.AutenticazioneBean;
import it.umbriadigitale.soclav.managedbeans.BaseBean;
import it.umbriadigitale.soclav.model.RdcAccessoLog;
import it.umbriadigitale.soclav.service.dto.anpal.AnpalBeneficiarioDTO;
import it.umbriadigitale.soclav.service.dto.anpal.AnpalDomandaDTO;
import it.umbriadigitale.soclav.service.interfaccia.IAmbitiSocialeService;
import it.umbriadigitale.soclav.service.interfaccia.ICentriImpiegoService;
import it.umbriadigitale.soclav.service.interfaccia.IGestioneLogAccessi;

@Controller
public class ListaRdCLavoroBean extends BaseBean  {

	private String widgetVar = "listaRdCLavoroVar";
	@Autowired
	private LazyDataModel<AnpalDomandaDTO> lazyListaLavoroModel;
	private AnpalDomandaDTO selected;
	
	//private List<CartellaSocialeDTO> lstCartelleSociali;
	private List<RdcAccessoLog> lstAccessiLog;
	//private boolean renderLstCartelleSociali;
	private String condividiServiziSociali;
	
	private AnpalBeneficiarioDTO famSelected;
	
	@Autowired
	ICentriImpiegoService cpiService; 
	
	@Autowired
	IAmbitiSocialeService csocService;
	
	@Autowired
	IGestioneLogAccessi accessiService;
	
	public String getWidgetVar() {
		return widgetVar;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}
	
    @PostConstruct
    public void initDataModel() {
    	//lazyListaLavoroModel = new LazyListaRdCLavoro<RdCAnpalBeneficiario>() {
    		
    	
    }
	
	/*
	 * public void loadSAP(String codSAP) { logger.debug("START loadSAP: carica");
	 * 
	 * if(!StringUtils.isBlank(codSAP)) { sap = cpiService.find(codSAP);
	 * if(sap==null) { logger.error("SAP non presente"); return; }
	 * 
	 * } //else addWarningFromProperties("seleziona.warning");
	 * 
	 * logger.debug("END loadSAP: carica");
	 * 
	 * }
	 */
	
	public void loadGestioneAccessiSociale(AnpalBeneficiarioDTO fam) {
		logger.debug("START loadGestioneAccessiSociale: carica");
		this.famSelected = fam;
		 if(fam!=null) {
			 String cf = fam.getDatipersonali().getCodicefiscale();
			 this.lstAccessiLog = accessiService.findAccessi("CARTELLA_SOCIALE", loadEntiAbilitati(), cf);
		  } //else addWarningFromProperties("seleziona.warning");
		
		logger.debug("END loadGestioneAccessiSociale: carica");
	}
	
	/*
	 * public void loadCartelleSociali() {
	 * logger.debug("START loadCartelleSociali: carica");
	 * 
	 * if(famSelected!=null) { String cf =
	 * famSelected.getDatipersonali().getCodicefiscale(); String ente =
	 * famSelected.getResidenza().getCodcomune(); this.lstCartelleSociali =
	 * this.csocService.findCartelleSociali(cf, ente);
	 * if(!this.lstCartelleSociali.isEmpty()) { AutenticazioneBean ab =
	 * (AutenticazioneBean)getBeanReference("autenticazioneBean"); String operatore
	 * = ab.getCetUser().getUsername();
	 * this.accessiService.salva("CARTELLA_SOCIALE", ente, cf, operatore);
	 * this.renderLstCartelleSociali = true; } } //else
	 * addWarningFromProperties("seleziona.warning");
	 * 
	 * logger.debug("END loadCartelleSociali: carica"); }
	 */
	
	public void rowDeselect() {
		this.selected=null;
	}

	public void clearFilters(){
		
	}

	public LazyDataModel<AnpalDomandaDTO> getLazyListaLavoroModel() {
		return lazyListaLavoroModel;
	}

	public void setLazyListaLavoroModel(LazyDataModel<AnpalDomandaDTO> lazyListaLavoroModel) {
		this.lazyListaLavoroModel = lazyListaLavoroModel;
	}

	public AnpalDomandaDTO getSelected() {
		return selected;
	}

	public void setSelected(AnpalDomandaDTO selected) {
		this.selected = selected;
	}

	public ICentriImpiegoService getCpiService() {
		return cpiService;
	}

	public void setCpiService(ICentriImpiegoService cpiService) {
		this.cpiService = cpiService;
	}

	public List<RdcAccessoLog> getLstAccessiLog() {
		return lstAccessiLog;
	}

	public void setLstAccessiLog(List<RdcAccessoLog> lstAccessiLog) {
		this.lstAccessiLog = lstAccessiLog;
	}

	public String getCondividiServiziSociali() {
		return condividiServiziSociali;
	}

	public void setCondividiServiziSociali(String condividiServiziSociali) {
		this.condividiServiziSociali = condividiServiziSociali;
	}
	
	public boolean salvaConsenso() {
		boolean esito = true;
		try {
			String cf = famSelected.getDatipersonali().getCodicefiscale();
			String ente = famSelected.getResidenza().getCodcomune();
			if(!StringUtils.isEmpty(cf) && !StringUtils.isEmpty(ente)) {
				if(condividiServiziSociali!=null)
					this.cpiService.salvaConsenso(cf, "SI".equalsIgnoreCase(condividiServiziSociali), ente);
				//RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			}else {
				//RequestContext.getCurrentInstance().addCallbackParam("saved", false);
			}
			
			this.famSelected = null;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			esito = false;
		}
			
		return esito;
	}
	
	public void gestisciConsenso(AnpalBeneficiarioDTO fam) {
		this.famSelected = fam;
		this.condividiServiziSociali = null;
		if(fam.getConsensoRilasciato()!=null)
			this.condividiServiziSociali =  fam.getConsensoRilasciato().booleanValue() ? "SI" : "NO";
	}

	public AnpalBeneficiarioDTO getFamSelected() {
		return famSelected;
	}

	public void setFamSelected(AnpalBeneficiarioDTO famSelected) {
		this.famSelected = famSelected;
	}

}
