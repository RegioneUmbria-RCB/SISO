package it.webred.cs.csa.web.manbean.listacasi.initDatiCaso;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.csa.ejb.dto.retvalue.DatiCasoListaDTO;
import it.webred.cs.csa.web.manbean.fascicolo.colloquio.ColloquioBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventi.InterventiBean;
import it.webred.cs.data.DataModelCostanti.IterStatoInfo;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsASoggettoCategoriaSocLAZY;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSocialeBASIC;
import it.webred.cs.data.model.CsDColloquioBASIC;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.jsf.bean.DatiCasoBean;
import it.webred.cs.jsf.bean.colloquio.ColloquioRowBean;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
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

public class InitDatiCaso extends ForkJoinTask {

	public static Logger logger = Logger.getLogger("carsociale.log");
	
		public static AccessTableIterStepSessionBeanRemote iterSessionBean ;
		public static AccessTableCasoSessionBeanRemote casoService ;
		public static AccessTableInterventoSessionBeanRemote interventoService ;
		public static AccessTableSoggettoSessionBeanRemote soggettiService;
        BaseDTO bDto;
        DatiCasoBean bean;
        DatiCasoListaDTO dc;
        
        

        
	public static DatiCasoBean load(DatiCasoListaDTO dc, BaseDTO bDto ) throws Exception {
		CsASoggettoLAZY sogg = dc.getSoggetto();
		CsACaso caso = sogg.getCsACaso();

		logger.debug("INIT - InitDatiCaso " + caso.getId() );



		if (iterSessionBean==null)
			iterSessionBean = (AccessTableIterStepSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");
		if (casoService==null)
			casoService = (AccessTableCasoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
		if (interventoService==null)
			interventoService = (AccessTableInterventoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
		if (soggettiService==null)
			soggettiService = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");

		AmAnagrafica operatoreAnagrafica = null;
		CsUiCompBaseBean.logger.debug("*** recupero anagrafica operatore");

		List<CsACasoOpeTipoOpe> lstOperatori = casoService.getListaOperatoreTipoOpByCasoId(bDto);
		
    	//Carico gli altri dati da mostrare in lista
		IterInfoStatoMan casoInfo = new IterInfoStatoMan();
		
		IterDTO itDto = new IterDTO();
		//CsUiCompBaseBean.fillEnte(itDto); --> nno funziona in thread
		itDto.setSessionId(bDto.getSessionId());
		itDto.setUserId(bDto.getUserId());
		itDto.setEnteId(bDto.getEnteId());
		
		itDto.setIdCaso(caso.getId());
		CsUiCompBaseBean.logger.debug("*** getLastIterStepByCasoDTO");

		CsIterStepByCasoDTO lastItStep = iterSessionBean.getLastIterStepByCasoDTO(itDto);
		
		CsUiCompBaseBean.logger.debug("*** FINE getLastIterStepByCasoDTO");
		
		if( lastItStep != null ) {
			casoInfo.initialize( lastItStep);
		}

	
		CsUiCompBaseBean.logger.debug("*** countInterventiByCaso");
		DatiCasoBean bean = new DatiCasoBean( sogg, lstOperatori, casoInfo);
		if(lastItStep.getCsItStep().getCsCfgItStato().getId()!=IterStatoInfo.APERTO)
			bean.setDataApertura(dc.getDataApertura());
		
		List<InterventoBaseDTO> lstInterventi = interventoService.getListaInfoInterventiByCaso(bDto);
		
		BaseDTO b2 = new BaseDTO();
		//CsUiCompBaseBean.fillEnte(b2); --> nno funziona in thread
		b2.setSessionId(bDto.getSessionId());
		b2.setUserId(bDto.getUserId());
		b2.setEnteId(bDto.getEnteId());

		
		b2.setObj(sogg.getCsAAnagrafica().getCf());
		b2.setObj2(true);
		List<ErogazioneBaseDTO> lstErogati = interventoService.getListaInterventiErogatiByCF(b2);
		bean.setnInterventi(Integer.toString(lstInterventi.size()));
		bean.setListaInterventi(lstInterventi);
		bean.setListaErogazioni(lstErogati);
		CsUiCompBaseBean.logger.debug("*** FINE countInterventiByCaso");
		
		CsUiCompBaseBean.logger.debug("*** getSoggettoCategorieAttualiBySoggetto");
		bDto.setObj(sogg.getAnagraficaId());
		List<CsASoggettoCategoriaSocLAZY> lstCatSoc = soggettiService.getSoggettoCategorieAttualiBySoggetto(bDto);
		bean.setListaCatSociale(lstCatSoc);
		CsUiCompBaseBean.logger.debug("*** FINE getSoggettoCategorieAttualiBySoggetto");
    	
		logger.debug("FINE - InitDatiCaso " + caso.getId());
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
