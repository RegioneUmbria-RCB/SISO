package it.webred.cs.csa.web.manbean.fascicolo.initialize;

import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsCCategoriaSocialeBASIC;
import it.webred.cs.jsf.bean.colloquio.ColloquioRowBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

public class InitCategorieSocialiSoggetto extends ForkJoinTask {

	public static Logger logger = Logger.getLogger("carsociale.log");
	 BaseDTO dto;
	 List<CsCCategoriaSocialeBASIC> catsocCorrenti;
	

	public static List<CsCCategoriaSocialeBASIC> loadListaCategorieSociali(BaseDTO dto) throws Exception {
		
		ArrayList<ColloquioRowBean> listaColloquios = new ArrayList<ColloquioRowBean>();
				
		AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");

		List<CsCCategoriaSocialeBASIC> catsocCorrenti = soggettoService.getCatSocAttualiBySoggetto(dto);
	
		return catsocCorrenti;
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
	
	
	public InitCategorieSocialiSoggetto(BaseDTO dto) {
		this.dto =  dto;
		
	}
	
	@Override
	protected boolean exec() {

		try {
			catsocCorrenti =  loadListaCategorieSociali(dto);
		} catch (Exception e) {
			logger.error("Errore caricamento lista colloqui id=" + dto.getObj(),e);
			return false;
		}

				
		return true;
	}

	@Override
	public Object getRawResult() {
		// TODO Auto-generated method stub
		return catsocCorrenti;
	}

	@Override
	protected void setRawResult(Object value) {
		// TODO Auto-generated method stub
		
	}

}
