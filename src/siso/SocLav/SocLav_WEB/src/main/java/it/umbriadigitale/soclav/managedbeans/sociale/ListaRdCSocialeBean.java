package it.umbriadigitale.soclav.managedbeans.sociale;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.umbriadigitale.soclav.managedbeans.BaseBean;
import it.umbriadigitale.soclav.model.RdcAccessoLog;
import it.umbriadigitale.soclav.service.dto.DomandaRdCDTO;
import it.umbriadigitale.soclav.service.dto.gepi.GePiBeneficiarioDTO;
import it.umbriadigitale.soclav.service.dto.gepi.GePiDomandaDTO;
import it.umbriadigitale.soclav.service.interfaccia.IAmbitiSocialeService;
import it.umbriadigitale.soclav.service.interfaccia.IGestioneLogAccessi;

@Controller
public class ListaRdCSocialeBean extends BaseBean  {

	private String widgetVar = "listaRdCLavoroVar";
	@Autowired
	private LazyDataModel<GePiDomandaDTO> lazyListaSocialeModel;
	private DomandaRdCDTO selected;
	private String condividiCentriImpiego;
	private GePiBeneficiarioDTO famSelected;
	private List<RdcAccessoLog> lstAccessiLog;
	
	@Autowired
	IAmbitiSocialeService socService;
	
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
    	//lazyListaLavoroModel = new LazyListaRdCLavoro<GePiDomandaDTO>();
    		
    	
    }
	
	public ActionListener getCloseDialog() {
	    return new ActionListener() {
	        @Override
	        public void processAction(ActionEvent event) throws AbortProcessingException {
	        	//loadListaCasi();
	        }
	    };
	}
	
	public void rowDeselect() {
		this.selected=null;
	}


	public void clearFilters(){
		
	}

	public LazyDataModel<GePiDomandaDTO> getLazyListaSocialeModel() {
		return lazyListaSocialeModel;
	}

	public void setLazyListaSocialeModel(LazyDataModel<GePiDomandaDTO> lazyListaSocialeModel) {
		this.lazyListaSocialeModel = lazyListaSocialeModel;
	}

	public DomandaRdCDTO getSelected() {
		return selected;
	}

	public void setSelected(DomandaRdCDTO selected) {
		this.selected = selected;
	}	
	
	public void gestisciConsenso(GePiBeneficiarioDTO fam) {
		this.famSelected = fam;
		this.condividiCentriImpiego = null;
		if(fam.getConsensoRilasciato()!=null)
			this.condividiCentriImpiego =  fam.getConsensoRilasciato().booleanValue() ? "SI" : "NO";
	}
	
	public void load(GePiBeneficiarioDTO fam) {
		this.famSelected = fam;
	}
	
	public boolean salvaConsenso() {
		boolean esito = true;
		try {
			String cf = famSelected.getDatipersonali().getCodicefiscale();
			String ente = famSelected.getResidenza().getCodcomune();
			if(!StringUtils.isEmpty(cf) && !StringUtils.isEmpty(ente)) {
				if(condividiCentriImpiego!=null)
					this.socService.salvaConsenso(cf, "SI".equalsIgnoreCase(condividiCentriImpiego), ente);
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

	public String getCondividiCentriImpiego() {
		return condividiCentriImpiego;
	}

	public void setCondividiCentriImpiego(String condividiCentriImpiego) {
		this.condividiCentriImpiego = condividiCentriImpiego;
	}

	public GePiBeneficiarioDTO getFamSelected() {
		return famSelected;
	}

	public void setFamSelected(GePiBeneficiarioDTO famSelected) {
		this.famSelected = famSelected;
	}
	
	public List<RdcAccessoLog> getLstAccessiLog() {
		return lstAccessiLog;
	}

	public void setLstAccessiLog(List<RdcAccessoLog> lstAccessiLog) {
		this.lstAccessiLog = lstAccessiLog;
	}

	public void loadGestioneAccessiSAP(GePiBeneficiarioDTO fam) {
		logger.debug("START loadGestioneAccessiLavoro: carica");
		this.famSelected = fam;
		 if(fam!=null) {
			 String cf = fam.getDatipersonali().getCodicefiscale();
			 lstAccessiLog = accessiService.findAccessi("SAP", loadEntiAbilitati(), cf);
		  } //else addWarningFromProperties("seleziona.warning");
		
		logger.debug("END loadGestioneAccessiLavoro: carica");
	}
}
