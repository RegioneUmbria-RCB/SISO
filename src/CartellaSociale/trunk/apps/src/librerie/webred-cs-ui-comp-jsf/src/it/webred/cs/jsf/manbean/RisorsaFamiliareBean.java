package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.RisorsaFamDTO;
import it.webred.cs.jsf.interfaces.IRisorsaFamiliare;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;

@ManagedBean
public class RisorsaFamiliareBean extends CsUiCompBaseBean implements IRisorsaFamiliare{

	private String widgetVar = "risorsaFamVar";
	private String selCfRisorsa;
	private String cf;
	private Hashtable<String, RisorsaFamDTO> risorse;
	private List<SelectItem> risorseItem;
	private String tooltip;
	
	public RisorsaFamiliareBean(String cf){
		this.cf = cf;
		loadRisorse();
		this.tooltip = "Sono selezionabili sono i soggetti con cartella e dati sociali attivi";
	}
	
	private void loadRisorse(){
		risorseItem = new ArrayList<SelectItem>();
		
		if(cf!=null && !cf.isEmpty()){
			BaseDTO rsDto = new BaseDTO();
			fillEnte(rsDto);
			rsDto.setObj(cf);
			
			try {
				
				AccessTableSchedaSessionBeanRemote service = (AccessTableSchedaSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSessionBean");
				risorse = service.findRisorseFamiliariBySoggettoCf(rsDto);
			
				for(RisorsaFamDTO f : risorse.values()){
				
					SelectItem si2 = new SelectItem();
					si2.setValue(f.getCf());
					si2.setDisabled(!f.isHasDatiSociali());
					si2.setLabel(f.getItemLabel());
					risorseItem.add(si2);
				}
				
			} catch (NamingException e) {
				addError("general", "caricamento.error");
				logger.error(e.getMessage(), e);
			}
		}
	}

	public String getWidgetVar() {
		return widgetVar;
	}

	public List<SelectItem> getRisorseItem() {
		return risorseItem;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}

	public RisorsaFamDTO getSelRisorsa() {
		if(StringUtils.isNotEmpty(this.selCfRisorsa))
			return this.risorse.get(this.selCfRisorsa);
		else 
			return null;
	}

	public String getSelCfRisorsa() {
		return selCfRisorsa;
	}

	public void setSelCfRisorsa(String selCfRisorsa) {
		this.selCfRisorsa = selCfRisorsa;
	}

	public Hashtable<String, RisorsaFamDTO> getRisorse() {
		return risorse;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setRisorse(Hashtable<String, RisorsaFamDTO> risorse) {
		this.risorse = risorse;
	}

	public void setRisorseItem(List<SelectItem> risorseItem) {
		this.risorseItem = risorseItem;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	
}
