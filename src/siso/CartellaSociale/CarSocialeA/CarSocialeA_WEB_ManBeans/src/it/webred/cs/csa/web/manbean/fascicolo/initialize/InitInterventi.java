package it.webred.cs.csa.web.manbean.fascicolo.initialize;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsIIntervento;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

public class InitInterventi extends ForkJoinTask {

	private static Logger logger = Logger.getLogger("carsociale.log");
	private static AccessTableInterventoSessionBeanRemote interventoService; 
	 BaseDTO dto;
	 List<CsIIntervento> lsti;
	

	public static List<CsIIntervento> loadLista(BaseDTO dto) {
		List<CsIIntervento> lsti = new ArrayList<CsIIntervento>();
		if(interventoService==null) 
			interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
		
		lsti = interventoService.getListaInterventiByCaso(dto);
	
		return lsti;
	}
	
	public static Object getEjb(String ear, String module, String ejbName) {
		Context cont;
		try {
			cont = new InitialContext();
			return cont.lookup("java:global/" + ear + "/" + module + "/" + ejbName);
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	public InitInterventi(BaseDTO dto) {
		this.dto =  dto;
		
	}
	
	@Override
	protected boolean exec() {
		lsti =  loadLista(dto);
		return true;
	}

	@Override
	public Object getRawResult() {
		// TODO Auto-generated method stub
		return lsti;
	}

	@Override
	protected void setRawResult(Object value) {
		// TODO Auto-generated method stub
		
	}

}
