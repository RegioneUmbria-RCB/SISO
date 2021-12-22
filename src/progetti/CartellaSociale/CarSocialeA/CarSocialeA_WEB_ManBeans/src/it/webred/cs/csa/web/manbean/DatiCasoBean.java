package it.webred.cs.csa.web.manbean;

import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class DatiCasoBean extends CsUiCompBaseBean {
	
	AccessTableIterStepSessionBeanRemote iterStepService = (AccessTableIterStepSessionBeanRemote) getCarSocialeEjb ("AccessTableIterStepSessionBean");
	AccessTableSoggettoSessionBeanRemote soggettiService = (AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb ("AccessTableSoggettoSessionBean");

	private CsIterStepByCasoDTO itStep;
	private List<CsASoggettoCategoriaSoc> listaSoggCat;
	private String catSociale;
	
	public void loadDatiCaso() {
	
		CsASoggettoLAZY soggetto = (CsASoggettoLAZY) getSession().getAttribute("soggetto");
		
		if(soggetto != null) {
		
			try {
				
				BaseDTO bDto = new BaseDTO();
				fillEnte(bDto);
				
				//last iter
				bDto.setObj(soggetto.getCsACaso().getId());
				itStep = iterStepService.getLastIterStepByCasoDTO(bDto);
				
				//cat sociali
				bDto.setObj(soggetto.getAnagraficaId());				
				String catSoc = "";
				listaSoggCat = soggettiService.getSoggettoCategorieBySoggetto(bDto);
				for(CsASoggettoCategoriaSoc soggCat: listaSoggCat) {
					if(soggCat.getId().getDataFineApp().after(new Date()))
						catSoc += ", " + soggCat.getCsCCategoriaSociale().getTooltip();
				}
				if(catSoc.length() > 1)
					catSociale = catSoc.substring(2);
				
			} catch (Exception e) {
				addError("general", "caricamento.error");
				logger.error(e.getMessage(), e);
			}
		}
		
	}

	public CsIterStepByCasoDTO getItStep() {
		return itStep;
	}

	public void setItStep(CsIterStepByCasoDTO itStep) {
		this.itStep = itStep;
	}

	public List<CsASoggettoCategoriaSoc> getListaSoggCat() {
		return listaSoggCat;
	}

	public void setListaSoggCat(List<CsASoggettoCategoriaSoc> listaSoggCat) {
		this.listaSoggCat = listaSoggCat;
	}

	public String getCatSociale() {
		return catSociale;
	}

	public void setCatSociale(String catSociale) {
		this.catSociale = catSociale;
	}
	
}
