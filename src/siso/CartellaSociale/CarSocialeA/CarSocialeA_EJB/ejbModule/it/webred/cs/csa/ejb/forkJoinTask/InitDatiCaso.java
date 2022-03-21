package it.webred.cs.csa.ejb.forkJoinTask;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDatiEsterniSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.CasoDAO;
import it.webred.cs.csa.ejb.dao.InterventoDAO;
import it.webred.cs.csa.ejb.dao.InterventoErogazioneDAO;
import it.webred.cs.csa.ejb.dao.IterDAO;
import it.webred.cs.csa.ejb.dao.SoggettoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.listaCasi.OperatoreListaCasiDTO;
import it.webred.cs.csa.ejb.dto.listaCasi.UnitaOrganizzativaDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.csa.ejb.dto.retvalue.DatiCasoListaDTO;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

public class InitDatiCaso extends ForkJoinTask {

	private static Logger logger = Logger.getLogger("carsociale.log");
	
	private static AccessTableDatiEsterniSoggettoSessionBeanRemote datiEsterniService;
	private static AccessTableCasoSessionBeanRemote casoService;
	private static AccessTableIterStepSessionBeanRemote iterService;
	private static AccessTableInterventoSessionBeanRemote interventoService;
	private static AccessTableSoggettoSessionBeanRemote soggettoService;
	
	private CeTBaseObject cet;
    private DatiCasoListaDTO bean;

	
	public static void load(DatiCasoListaDTO dc, CeTBaseObject cet ) {

		if(datiEsterniService == null)
			datiEsterniService=  (AccessTableDatiEsterniSoggettoSessionBeanRemote)getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDatiEsterniSoggettoSessionBean");

		if(casoService == null)
			casoService = (AccessTableCasoSessionBeanRemote)getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
		
		if(iterService == null)
			iterService = (AccessTableIterStepSessionBeanRemote)getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");

		if(interventoService == null)
			interventoService = (AccessTableInterventoSessionBeanRemote)getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
		
		if(soggettoService == null)
			soggettoService = (AccessTableSoggettoSessionBeanRemote)getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");

		
		Long casoId = dc.getCasoId();
		
		BaseDTO dto = new BaseDTO();
		dto.setEnteId(cet.getEnteId());
		dto.setSessionId(cet.getSessionId());
		dto.setUserId(cet.getUserId());
		dto.setObj(casoId);
		
		logger.debug("INIZIO InitDatiCaso caso["+casoId+"]");
		List<CsACasoOpeTipoOpe> lstOperatori = casoService.getListaOperatoreTipoOpByCasoId(dto);
		List<OperatoreListaCasiDTO> lstOperatoriDTO = new ArrayList<OperatoreListaCasiDTO>();
		for(CsACasoOpeTipoOpe op : lstOperatori){
			OperatoreListaCasiDTO o = new OperatoreListaCasiDTO();
			o.setDenominazione(op.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOOperatore().getDenominazione());
			o.setDataInizioApp(op.getDataInizioApp());
			o.setDataFineApp(op.getDataFineApp());
			o.setResponsabile(op.getFlagResponsabile()!=null ? op.getFlagResponsabile() : Boolean.FALSE);
			o.setTipo(op.getCsOOperatoreTipoOperatore().getCsTbTipoOperatore().getDescrizione());
			lstOperatoriDTO.add(o);
		}
		dc.setOperatori(lstOperatoriDTO);
		
		List<UnitaOrganizzativaDTO> lstAccessoFascicolo = casoService.getListaUnitaOrganizzativeByCasoId(dto);
		dc.setListaAccessoFascicolo(lstAccessoFascicolo);
		
		CsIterStepByCasoDTO csItStep = iterService.getLastIterStepByCasoDTO(dto);
		dc.setLastIterStep(csItStep);
		
		List<InterventoBaseDTO> lstInterventi = new ArrayList<InterventoBaseDTO>();
		if(dc.getExistsInterventi())
			lstInterventi = interventoService.getListaInfoInterventiByCaso(dto);
		
		dc.setInterventiProgrammati(lstInterventi);
	
		dto.setObj(dc.getCf());
		dto.setObj2(Boolean.TRUE);
		List<ErogazioneBaseDTO> lstErogati = interventoService.getListaInterventiErogatiByCF(dto);
		dc.setErogazioni(lstErogati);
		
		//SISO-1532
		dto.setObj(dc.getCf());
		dto.setObj2(null);
		Boolean isPrestazioneDatiEsterni = datiEsterniService.existsPrestazione(dto);
		dc.setDatiEsterniFound(isPrestazioneDatiEsterni);
		
		dto.setObj(dc.getAnagraficaId());
		List<CsASoggettoCategoriaSoc> lstCatSoc = soggettoService.getSoggettoCategorieAttualiBySoggetto(dto); 
		dc.setListaCatSociale(lstCatSoc);
		
		Boolean existsRdC = false;
		if(dc.getAnagraficaId()!=null && !StringUtils.isBlank(dc.getCf())){
			dto.setObj(dc.getAnagraficaId());
			dto.setObj2(dc.getCf());
			dto.setObj3(null);
			existsRdC = soggettoService.hasNucleoBeneficiarioRdC(dto);
		}
		dc.setNucleoBeneficiarioRdC(existsRdC);
		
		logger.debug("FINE InitDatiCaso caso["+casoId+"]");
		
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
	
	
	public InitDatiCaso(DatiCasoListaDTO dc, CeTBaseObject cet) {
		this.cet = cet;
		this.bean =  dc;
		
	}
	
	@Override
	protected boolean exec() {
		load(bean, cet);
		return true;
	}

	@Override
	public Object getRawResult() {
		// TODO Auto-generated method stub
		return bean;
	}

	@Override
	protected void setRawResult(Object value) {
		// TODO Auto-generated method stub
		
	}

	public DatiCasoListaDTO getBean() {
		return bean;
	}

	public void setBean(DatiCasoListaDTO bean) {
		this.bean = bean;
	}

}
