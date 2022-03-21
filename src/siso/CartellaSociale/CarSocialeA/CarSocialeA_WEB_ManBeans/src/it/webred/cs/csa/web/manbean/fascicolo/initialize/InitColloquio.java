package it.webred.cs.csa.web.manbean.fascicolo.initialize;

import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.colloquio.ListaDatiColloquioDTO;

import java.util.List;
import java.util.concurrent.ForkJoinTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

public class InitColloquio extends ForkJoinTask {

	private static Logger logger = Logger.getLogger("carsociale.log");
	private static AccessTableDiarioSessionBeanRemote diarioService;
	BaseDTO dto;
	 List<ListaDatiColloquioDTO> listaColloquios;
	

	public static List<ListaDatiColloquioDTO> loadListaColloqui(BaseDTO dto) {
		if(diarioService==null) 
			diarioService = (AccessTableDiarioSessionBeanRemote)  getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");
		List<ListaDatiColloquioDTO> listaColl = diarioService.findColloquiByCaso(dto);
	
		return listaColl;
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
	
	
	public InitColloquio(BaseDTO dto) {
		this.dto =  dto;
		
	}
	
	@Override
	protected boolean exec() {
		listaColloquios =  loadListaColloqui(dto);
		return true;
	}

	@Override
	public Object getRawResult() {
		// TODO Auto-generated method stub
		return listaColloquios;
	}

	@Override
	protected void setRawResult(Object value) {
		// TODO Auto-generated method stub
		
	}

}
