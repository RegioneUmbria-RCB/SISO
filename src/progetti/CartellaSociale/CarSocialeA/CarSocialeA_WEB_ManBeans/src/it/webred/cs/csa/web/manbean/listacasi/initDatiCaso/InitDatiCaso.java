package it.webred.cs.csa.web.manbean.listacasi.initDatiCaso;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDatiEsterniSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DatiEsterniSoggettoViewDTO;
import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.csa.ejb.dto.retvalue.DatiCasoListaDTO;
import it.webred.cs.data.DataModelCostanti.IterStatoInfo;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoAccessoFascicolo;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.jsf.bean.DatiCasoBean;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;
import it.webred.ejb.utility.ClientUtility;

import java.util.List;
import java.util.concurrent.ForkJoinTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

public class InitDatiCaso extends ForkJoinTask {

	public static Logger logger = Logger.getLogger("carsociale.log");
	
		public static AccessTableIterStepSessionBeanRemote iterSessionBean ;
		public static AccessTableCasoSessionBeanRemote casoService ;
		public static AccessTableInterventoSessionBeanRemote interventoService ;
		public static AccessTableSoggettoSessionBeanRemote soggettiService;
		//SISO-1532
		public static AccessTableDatiEsterniSoggettoSessionBeanRemote datiEsterniEJBRemote;
        BaseDTO bDto;
        DatiCasoBean bean;
        DatiCasoListaDTO dc;
        
        

        
	public static DatiCasoBean load(DatiCasoListaDTO dc, BaseDTO bDto ) throws Exception {

		if (iterSessionBean==null)
			iterSessionBean = (AccessTableIterStepSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");
		if (casoService==null)
			casoService = (AccessTableCasoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
		if (interventoService==null)
			interventoService = (AccessTableInterventoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
		if (soggettiService==null)
			soggettiService = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");

		if(datiEsterniEJBRemote == null)
			datiEsterniEJBRemote=  (AccessTableDatiEsterniSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableDatiEsterniSoggettoSessionBean");

			
		List<CsACasoOpeTipoOpe> lstOperatori = casoService.getListaOperatoreTipoOpByCasoId(bDto);
		List<CsACasoAccessoFascicolo> lstAccessoFascicolo = casoService.getListaAccessoFascicoloByCasoId(bDto);
		
    	//Carico gli altri dati da mostrare in lista
		IterInfoStatoMan casoInfo = new IterInfoStatoMan();
		
		BaseDTO itDto = new BaseDTO();
		//CsUiCompBaseBean.fillEnte(itDto); --> nno funziona in thread
		itDto.setSessionId(bDto.getSessionId());
		itDto.setUserId(bDto.getUserId());
		itDto.setEnteId(bDto.getEnteId());
		itDto.setObj(dc.getCasoId());
		
		CsIterStepByCasoDTO lastItStep = iterSessionBean.getLastIterStepByCasoDTO(itDto);
		
		if( lastItStep != null ) {
			casoInfo.initialize( lastItStep);
		}

		DatiCasoBean bean = new DatiCasoBean( dc, lstOperatori, casoInfo, lstAccessoFascicolo, dc.getResidenza());
		if(lastItStep.getCsItStep().getCsCfgItStato().getId()!=IterStatoInfo.APERTO)
			bean.setDataApertura(dc.getDataApertura());
		
		List<InterventoBaseDTO> lstInterventi = interventoService.getListaInfoInterventiByCaso(bDto);
		bean.setnInterventi(Integer.toString(lstInterventi.size()));
		bean.setListaInterventi(lstInterventi);
		
		BaseDTO b2 = new BaseDTO();
		//CsUiCompBaseBean.fillEnte(b2); --> nno funziona in thread
		b2.setSessionId(bDto.getSessionId());
		b2.setUserId(bDto.getUserId());
		b2.setEnteId(bDto.getEnteId());

		
		b2.setObj(dc.getCf());
		b2.setObj2(true);
		List<ErogazioneBaseDTO> lstErogati = interventoService.getListaInterventiErogatiByCF(b2);
		bean.setListaErogazioni(lstErogati);
		
		b2.setObj(dc.getAnagraficaId());
		b2.setObj2(dc.getCf());
		b2.setObj3(null);
		bean.setNucleoBeneficiarioRdC(soggettiService.hasNucleoBeneficiarioRdC(b2));
		
		bDto.setObj(dc.getAnagraficaId());
		List<CsASoggettoCategoriaSoc> lstCatSoc = soggettiService.getSoggettoCategorieAttualiBySoggetto(bDto);
		bean.setListaCatSociale(lstCatSoc);
		
		//SISO-1532
		bDto.setObj(dc.getCf());
		Boolean isPrestazioneDatiEsterni = datiEsterniEJBRemote.existsPrestazione(bDto);
		bean.setDatiEsterniFound(isPrestazioneDatiEsterni);
		
        return bean;
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
	
	
	public InitDatiCaso(DatiCasoListaDTO dc,  BaseDTO bDto) {
		this.bDto = bDto;
		this.dc =  dc;
		
	}
	
	@Override
	protected boolean exec() {

		try {
			bean =  load(dc,   bDto);
		} catch (Exception e) {
			logger.error("Errore caricamento lista casi ",e);
			return false;
		}

				
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

}
