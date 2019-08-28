package it.webred.cs.csa.web.manbean.fascicolo.initialize;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.fascicolo.initialize.bean.InitAltriSoggettiBean;
import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;

import java.util.List;
import java.util.concurrent.ForkJoinTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

public class InitAltriSoggetti extends ForkJoinTask {

	public static Logger logger = Logger.getLogger("carsociale.log");
	 BaseDTO dto;
	 InitAltriSoggettiBean altriSoggetti;
	

	public static InitAltriSoggettiBean loadLista(BaseDTO dto) throws Exception {

		AnagrafeService anagrafeService = (AnagrafeService) getEjb(
				"CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
		AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getEjb(
				"CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSessionBean");


		// nel secondo oggetto del dto si mette il cf ma poi va cancellato 
		RicercaSoggettoAnagrafeDTO rs = new RicercaSoggettoAnagrafeDTO();
		rs.setEnteId(dto.getEnteId());
		rs.setUserId(dto.getUserId());
		rs.setSessionId(dto.getSessionId());
		rs.setCodFis((String)dto.getObj2());
		dto.setObj2(null);

		CsAFamigliaGruppo famigliaGruppo = schedaService.findFamigliaAllaDataBySoggettoId(dto);
		List<SitDPersona> listaFamiglia_anagrafe = anagrafeService.getFamigliaByCF(rs);

		InitAltriSoggettiBean altriSoggetti = new InitAltriSoggettiBean();
		altriSoggetti.setFamigliaGruppo(famigliaGruppo);
		altriSoggetti.setListaFamiglia_anagrafe(listaFamiglia_anagrafe);;
		
		return altriSoggetti;
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
	
	
	public InitAltriSoggetti(BaseDTO dto) {
		this.dto =  dto;
		
	}
	
	@Override
	protected boolean exec() {

		try {
			altriSoggetti =  loadLista(dto);
		} catch (Exception e) {
			logger.error("Errore caricamento lista InitAltriSoggetti id=" + dto.getObj(),e);
			return false;
		}

				
		return true;
	}

	@Override
	public Object getRawResult() {
		// TODO Auto-generated method stub
		return altriSoggetti;
	}

	@Override
	protected void setRawResult(Object value) {
		// TODO Auto-generated method stub
		
	}

}
