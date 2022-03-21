package it.webred.cs.csa.web.manbean.fascicolo.initialize;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.fascicolo.initialize.bean.InitAltriSoggettiBean;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.siso.ws.ricerca.dto.FamiliareDettaglio;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaParams;

import java.util.List;
import java.util.concurrent.ForkJoinTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

public class InitAltriSoggetti extends ForkJoinTask {

	public static Logger logger = Logger.getLogger("carsociale.log");
	private static AccessTableSchedaSessionBeanRemote schedaService;
	 BaseDTO dto;
	 InitAltriSoggettiBean altriSoggetti;
	 
	
	public static InitAltriSoggettiBean loadLista(BaseDTO dto) {
		Long anagraficaId = (Long)dto.getObj();
		String cf = (String)dto.getObj2();
		String tipo = (String)dto.getObj3();
		String idOrig = (String)dto.getObj4();
		
		if (schedaService==null)
			schedaService = (AccessTableSchedaSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSessionBean");

		// nel secondo oggetto del dto si mette il cf ma poi va cancellato 
		BaseDTO dtoc = new BaseDTO();
		dtoc.setEnteId(dto.getEnteId());
		dtoc.setUserId(dto.getUserId());
		dtoc.setSessionId(dto.getSessionId());
		dtoc.setObj(anagraficaId);
		List<CsAComponente> famigliaGruppo = schedaService.findComponentiFamigliaAllaDataBySoggettoId(dtoc);
		
		//Carico solo se è abilitata la fonte dati DIOGENE
		
		RicercaAnagraficaParams rs = new RicercaAnagraficaParams(tipo,true);
		rs.setEnteId(dto.getEnteId());
		rs.setUserId(dto.getUserId());
		rs.setSessionId(dto.getSessionId());
		rs.setCf(cf.toString());
		rs.setIdentificativo(idOrig);
		List<FamiliareDettaglio> listaFamiglia_anagrafe = CsUiCompBaseBean.getComposizioneFamiliareAllProvenienza(rs);
		
		InitAltriSoggettiBean altriSoggetti = new InitAltriSoggettiBean();
		altriSoggetti.setListaComponenti(famigliaGruppo);
		altriSoggetti.setListaFamiglia_anagrafe(listaFamiglia_anagrafe);
		
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
	protected boolean exec(){
		altriSoggetti =  loadLista(dto);	
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
