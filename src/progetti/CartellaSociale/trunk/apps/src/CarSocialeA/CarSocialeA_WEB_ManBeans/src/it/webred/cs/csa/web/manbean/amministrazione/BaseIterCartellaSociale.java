package it.webred.cs.csa.web.manbean.amministrazione;

import java.util.ArrayList;
import java.util.List;

import it.umbriadigitale.argo.ejb.client.cs.bean.ArInterscambioService;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableEventiSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.EventoDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.StatoCartellaDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoCategoriaSocLAZY;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCfgItStato;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;

public abstract class BaseIterCartellaSociale extends CsUiCompBaseBean {
	protected AccessTableIterStepSessionBeanRemote iterService =
		(AccessTableIterStepSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");

	protected AccessTableConfigurazioneSessionBeanRemote configurazioneService = (AccessTableConfigurazioneSessionBeanRemote) 
			getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	
	protected AccessTableOperatoreSessionBeanRemote operatoreService = (AccessTableOperatoreSessionBeanRemote) 
			getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
	
	protected AccessTableSoggettoSessionBeanRemote  soggettoService = (AccessTableSoggettoSessionBeanRemote) 
			getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
	
 	public AccessTableConfigurazioneSessionBeanRemote getConfigurazioneService() {
		return configurazioneService;
	}
 	 public List<CsASoggettoCategoriaSocLAZY> getSoggettoCategorieAttuali(BaseDTO bDto){
 		 return soggettoService.getSoggettoCategorieAttualiBySoggetto(bDto);
 	 }
 	
 	
	public void setConfigurazioneService(
			AccessTableConfigurazioneSessionBeanRemote configurazioneService) {
		this.configurazioneService = configurazioneService;
	}

	public List<CsCfgItStato> getListaIterStati(CeTBaseObject cet) {
		return this.iterService.getListaIterStati(cet);
	}
	public boolean addIterStep(IterDTO dto) throws Exception{
		return this.iterService.addIterStep(dto);
	}
	public CsItStep getLastIterStepByCaso(IterDTO dto) {
		try {
			return this.iterService.getLastIterStepByCaso(dto); 
		}catch (Exception ex){
			return null;
		}
	}
	
	public List<CsOOperatoreSettore> getOperatoreSettore(OperatoreDTO dto) {
		try{
			return this.operatoreService.findOperatoreSettoreBySettore(dto);	
		}catch(Exception ex){
			return new ArrayList<CsOOperatoreSettore>();
		}
		
	}
	public CsASoggettoLAZY getSoggettoByCF(BaseDTO dto){
		 
			return this.soggettoService.getSoggettoByCF(dto);
	 
	}
	
	public List<CsASoggettoLAZY> getSoggettiByCF(BaseDTO dto){
		 
		return this.soggettoService.getSoggettiByCF(dto);
 
    }
	public List<CsOSettoreBASIC> findSettoreBASICByOrganizzazione(BaseDTO dto){
		return this.configurazioneService.findSettoreBASICByOrganizzazione(dto);
	}
	public List<CsOSettore> getListaSettori(CeTBaseObject cet) {
		return this.configurazioneService.getSettoreAll(cet);
	}
	public List<CsOOrganizzazione> getOrganizzazione(CeTBaseObject cet) {
		return this.configurazioneService.getOrganizzazioniAll(cet);
	}
	public List<CsOOperatore> getOperatore(CeTBaseObject cet) {
		return null;
	}
	
	public AccessTableIterStepSessionBeanRemote getIterService() {
		return iterService;
	}

	public void setIterService(AccessTableIterStepSessionBeanRemote iterService) {
		this.iterService = iterService;
	}
	
  
}

