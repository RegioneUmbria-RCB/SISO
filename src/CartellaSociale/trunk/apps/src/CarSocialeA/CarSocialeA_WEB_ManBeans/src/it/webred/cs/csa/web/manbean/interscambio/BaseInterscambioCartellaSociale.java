package it.webred.cs.csa.web.manbean.interscambio;

import java.util.List;

import it.umbriadigitale.argo.ejb.client.cs.bean.ArInterscambioService;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableEventiSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.EventoDTO;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;

public abstract class BaseInterscambioCartellaSociale extends CsUiCompBaseBean {
	protected AccessTableEventiSessionBeanRemote eventoService =
		(AccessTableEventiSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableEventiSessionBean");

	protected AccessTableCasoSessionBeanRemote casoService =
		(AccessTableCasoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");

	protected AccessTableAlertSessionBeanRemote alertService =
		(AccessTableAlertSessionBeanRemote) getCarSocialeEjb("AccessTableAlertSessionBean");
	
	protected SsSchedaSessionBeanRemote schedaSessionService =
		(SsSchedaSessionBeanRemote) getEjb("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
	
	protected ArInterscambioService argoService =
		(ArInterscambioService) getEjb("Argo","Argo_EJB", "ArInterscambioServiceBean");
	
	
	
	public void creaEvento(EventoDTO evt) {
		this.eventoService.creaEvento(evt);
	}

	public void cancellaEvento(EventoDTO evt) {
		this.eventoService.cancellaEvento(evt);
	}

	public List<EventoDTO> getListaEventi(BaseDTO bdto) {
		return this.eventoService.findAllEvents(bdto);
	}
	
	public List<EventoDTO> getListaEventi(BaseDTO bdto, String cf, String type){
		return this.eventoService.findEventsByOpSettIdAndCF(cf, getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getId(), type);
	}

	public AccessTableEventiSessionBeanRemote getEventoService() {
		return eventoService;
	}

	public void setEventoService(AccessTableEventiSessionBeanRemote eventoService) {
		this.eventoService = eventoService;
	}

	public List<CsACaso> findCasoByCognomeAndNome(BaseDTO dto) {
		return this.casoService.findCasoByCognomeAndNome(dto);
	}

	public AccessTableCasoSessionBeanRemote getCasoService() {
		return casoService;
	}

	public void setCasoService(AccessTableCasoSessionBeanRemote casoService) {
		this.casoService = casoService;
	}

	public AccessTableAlertSessionBeanRemote getAlertService() {
		return alertService;
	}

	public ArInterscambioService getArgoService() {
		return argoService;
	}
}
