package it.umbriadigitale.soclav.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import it.umbriadigitale.soclav.model.gepi.RdCAnagraficaGepi;
import it.umbriadigitale.soclav.service.IRdCGepiAnagraficaService;
 
@Named
@org.springframework.context.annotation.Scope("session")
@Component
public class GepiAnpalBean extends BaseBean {

	public final Logger logger = Logger.getLogger(this.getClass());

	@ManagedProperty("#{rdcGepiAnagraficaService}")
	protected IRdCGepiAnagraficaService rdcGepiDomandaService;

	 
	
	
	public IRdCGepiAnagraficaService getRdcGepiDomandaService() {
		return rdcGepiDomandaService;
	}




	public void setRdcGepiDomandaService(IRdCGepiAnagraficaService rdcGepiDomandaService) {
		this.rdcGepiDomandaService = rdcGepiDomandaService;
	}




	public List<RdCAnagraficaGepi> getRdCAnagraficaGepi(String codEnte){
		return rdcGepiDomandaService.findRdcAnagraficaByCodEnte(codEnte);
	}  
}
