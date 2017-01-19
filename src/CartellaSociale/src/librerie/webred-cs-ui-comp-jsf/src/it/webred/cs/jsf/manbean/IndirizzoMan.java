package it.webred.cs.jsf.manbean;

import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaIndirizzoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDCivicoV;
import it.webred.ct.data.model.anagrafe.SitDVia;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.interfaces.IIndirizzo;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.naming.NamingException;

@ManagedBean
@NoneScoped
public class IndirizzoMan extends CsUiCompBaseBean implements IIndirizzo{

	private String widgetVar = "indirizzoVar";
	private String selectedIndirizzo;
	private String selectedCivico;
	private String selectedIdVia;
	private List<SitDCivicoV> lstCivici = new ArrayList<SitDCivicoV>();
	
	@Override
	public List<String> getLstIndirizzi(String query) {
		
		RicercaIndirizzoAnagrafeDTO ri = new RicercaIndirizzoAnagrafeDTO();
		fillEnte(ri);
		ri.setSitDCivicoViaDescrizione(query);
		List<String> result = new ArrayList<String>();
		try {
			AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface(
					"CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
			List<SitDVia> lista = anagrafeService.getIndirizziLike(ri);
			for(SitDVia via: lista){
				result.add(via.getDescrizione());
			}

		} catch (NamingException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public void handleChangeIndirizzo(AjaxBehaviorEvent event) {
		
		RicercaIndirizzoAnagrafeDTO ri = new RicercaIndirizzoAnagrafeDTO();
		fillEnte(ri);
		ri.setSitDCivicoViaDescrizione(selectedIndirizzo);
		try {
			
			AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface(
					"CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
			List<SitDVia> listaVie = anagrafeService.getIndirizziLike(ri);
			if(listaVie != null && listaVie.size() > 0){
				
				selectedIdVia = listaVie.get(0).getIdOrig();
				ri.setSitDViaIdExt(listaVie.get(0).getIdExt());
				lstCivici = anagrafeService.getCivicoByIdExtDVia(ri);
			}

		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}

	public String getWidgetVar() {
		return widgetVar;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}

	public String getSelectedIndirizzo() {
		return selectedIndirizzo;
	}

	public void setSelectedIndirizzo(String selectedIndirizzo) {
		this.selectedIndirizzo = selectedIndirizzo;
	}
	
	@Override
	public List<SitDCivicoV> getLstCivici() {
		return lstCivici;
	}

	public String getSelectedCivico() {
		return selectedCivico;
	}

	public void setSelectedCivico(String selectedCivico) {
		this.selectedCivico = selectedCivico;
	}

	public String getSelectedIdVia() {
		return selectedIdVia;
	}

	public void setSelectedIdVia(String selectedIdVia) {
		this.selectedIdVia = selectedIdVia;
	}
	
}
