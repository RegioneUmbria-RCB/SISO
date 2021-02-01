package it.webred.cs.csa.web.manbean.fascicolo.initialize;

import it.webred.cs.csa.ejb.client.AccessTableExportValutazioniSinbaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.InfoExportSinbaDTO;
import it.webred.cs.csa.ejb.dto.SinbaMinoriSearchResultDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

public class InitSchedeSinbaSoggetto extends ForkJoinTask {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger logger = Logger.getLogger("carsociale.log");
	BaseDTO dto;
	List<InfoExportSinbaDTO> csDSinbas;
	

	public static List<InfoExportSinbaDTO> loadLista(BaseDTO dto) throws Exception {

		AccessTableExportValutazioniSinbaSessionBeanRemote sinbaExportService = (AccessTableExportValutazioniSinbaSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB","AccessTableExportValutazioniSinbaSessionBean");

		List<InfoExportSinbaDTO> csDSinbas=new ArrayList<InfoExportSinbaDTO>();

		
		List<SinbaMinoriSearchResultDTO> sinbaMinoriSearchResultDTO= sinbaExportService.findSchedeSinbaPerMinore(dto);

		//se non è nullo dovrebbe trovarne solo uno
		if(sinbaMinoriSearchResultDTO!=null && !sinbaMinoriSearchResultDTO.isEmpty())
		{
			csDSinbas=sinbaMinoriSearchResultDTO.get(0).getCsDSinbas();
		}
		
		return csDSinbas;
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
	
	
	public InitSchedeSinbaSoggetto(BaseDTO dto) {
		this.dto =  dto;
		
	}
	
	@Override
	protected boolean exec() {

		try {
			csDSinbas =  loadLista(dto);
		} catch (Exception e) {
			logger.error("Errore caricamento lista InitAltriSoggetti id=" + dto.getObj(),e);
			return false;
		}

				
		return true;
	}

	@Override
	public Object getRawResult() {
		// TODO Auto-generated method stub
		return csDSinbas;
	}

	@Override
	protected void setRawResult(Object value) {
		// TODO Auto-generated method stub
		
	}

}
