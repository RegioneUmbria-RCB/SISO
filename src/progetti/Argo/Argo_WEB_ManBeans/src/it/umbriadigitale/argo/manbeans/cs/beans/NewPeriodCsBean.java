package it.umbriadigitale.argo.manbeans.cs.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import it.umbriadigitale.argo.ejb.client.cs.bean.ArCsSoggettoCsService;
import it.umbriadigitale.argo.ejb.client.cs.dto.ArCsSoggettoCsDTO;
import it.umbriadigitale.argo.manbeans.BaseBean;
import it.umbriadigitale.argo.manbeans.util.Constants;

/**
 * 
 * @author andrea.niccolini
 *
 */
@ManagedBean
@ViewScoped
public class NewPeriodCsBean extends BaseBean {

	
	// Current subject
	private ArCsSoggettoCsDTO currentSubject;
	
	
	@EJB(mappedName = "java:global/Argo/Argo_EJB/ArCsSoggettoCsServiceBean")
	protected ArCsSoggettoCsService arCsSoggettoCsServiceBean;
	
	
	@PostConstruct
	public void init()
	{
		List<ArCsSoggettoCsDTO> soggetti = arCsSoggettoCsServiceBean.getSoggetti();
	}
	
	
	
	public String goHome()
	{
		return Constants.NAV_GO_HOME;
	}

	
	public void savePeriod()
	{
		// save current period
	}

	
	
	public ArCsSoggettoCsDTO getCurrentSubject() {
		return currentSubject;
	}

	public void setCurrentSubject(ArCsSoggettoCsDTO currentSubject) {
		this.currentSubject = currentSubject;
	}

}
