package it.webred.cs.csa.web.manbean.fascicolo.initialize;

import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.fascicolo.colloquio.ColloquioBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventi.InterventiBean;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSocialeBASIC;
import it.webred.cs.data.model.CsDColloquioBASIC;
import it.webred.cs.jsf.bean.colloquio.ColloquioRowBean;
import it.webred.ejb.utility.ClientUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

public class InitColloquio extends ForkJoinTask {

	public static Logger logger = Logger.getLogger("carsociale.log");
	 BaseDTO dto;
	 List<CsDColloquioBASIC> listaColloquios;
	

	public static List<CsDColloquioBASIC> loadListaColloqui(BaseDTO dto) throws Exception {
		
		ArrayList<ColloquioRowBean> listaColloquios = new ArrayList<ColloquioRowBean>();
				
		AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote)  getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");

		List<CsDColloquioBASIC> listaColl = diarioService.getColloquios(dto);
	
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

		try {
			listaColloquios =  loadListaColloqui(dto);
		} catch (Exception e) {
			logger.error("Errore caricamento lista colloqui id=" + dto.getObj(),e);
			return false;
		}

				
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
