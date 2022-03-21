package it.webred.cs.csa.web.manbean;

import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.jsf.interfaces.ISoggettoGit;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.data.access.aggregator.isee.IseeService;
import it.webred.ct.data.access.aggregator.isee.dto.InfoIseeDTO;
import it.webred.ct.data.access.aggregator.isee.dto.IseeDataIn;

import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class SoggettoGitBean extends CsUiCompBaseBean implements ISoggettoGit{

	private InfoIseeDTO datiSoggettoGit;
	
	public void loadDatiSoggettoGit() {
		CsASoggettoLAZY soggetto = (CsASoggettoLAZY) getSession().getAttribute("soggetto");
		
		if(soggetto != null) {
		
			IseeDataIn dataIn = new IseeDataIn();
			dataIn.setCodiceFiscale(soggetto.getCsAAnagrafica().getCf());
			int year = Calendar.getInstance().get(Calendar.YEAR);
			dataIn.setAnno(year -1);
			dataIn.setRichiediUltimiRedditiDisp(true);
			fillEnte(dataIn);
			try {
				
				IseeService iseeService = (IseeService) getEjb("CT_Service", "CT_Service_Data_Access", "IseeServiceBean");
				datiSoggettoGit = iseeService.getInfoIsee(dataIn);
				RequestContext.getCurrentInstance().addCallbackParam("canOpen", true);
				
			} catch (Exception e) {
				addError("general", "caricamento.error");
				logger.error(e.getMessage(), e);
			}
		}
		
	}
	
	@Override
	public InfoIseeDTO getDatiSoggettoGit() {		
		return datiSoggettoGit;
	}

	public void setDatiSoggettoGit(InfoIseeDTO datiSoggettoGit) {
		this.datiSoggettoGit = datiSoggettoGit;
	}
	
}
