package it.webred.cs.csa.web.bean.timertask;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableParentiGitSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.AlertDTO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipiAlertCod;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsAFamigliaGruppoGit;
import it.webred.cs.data.model.CsAlertConfig;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.persist.CsAlert;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.interceptor.ExcludeDefaultInterceptors;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

@ExcludeDefaultInterceptors
public class ZsTimerTaskGiornaliero extends TimerTask {

	private static Logger logger;
	private String enteId;
	private AccessTableAlertSessionBeanRemote alertService;
	private AccessTableCasoSessionBeanRemote casoService;

	public ZsTimerTaskGiornaliero(String enteId) {
		super();
		this.enteId = enteId;
	}

	@Override
	public void run() {

		try {
			
			alertService = (AccessTableAlertSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB","AccessTableAlertSessionBean");
			casoService =   (AccessTableCasoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB","AccessTableCasoSessionBean");
				
	    	this.aggiornaAlertRelazioni();
	    	this.aggiornaAlertComponentiFamiliari();
	    	
			logger.debug("__ FINE Esecuzione ZsTimerTaskGiornaliero__");

		} catch (Exception e) {
			logger.error("__ ZsTimerTaskGiornaliero: Eccezione su : " + e.getMessage(), e);
		}
	}

	public void aggiornaAlertComponentiFamiliari() throws NamingException{
		
		logger.debug("__ INIZIO ZsTimerTaskGiornaliero: aggiornaAlertComponentiFamiliari_");
	
		BaseDTO bDto = new BaseDTO();
		IterDTO itDto = new IterDTO();
		OperatoreDTO opDto = new OperatoreDTO();

    	bDto.setEnteId(enteId);
    	itDto.setEnteId(enteId);
    	opDto.setEnteId(enteId);
		
    	try {
    		
    		AccessTableParentiGitSessionBeanRemote parentiService = (AccessTableParentiGitSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableParentiGitSessionBean");
    	
			List<CsAFamigliaGruppoGit> listaFamiglieAggiornate = parentiService.getFamigliaGruppoGitAggiornate(bDto);
			for(CsAFamigliaGruppoGit fam: listaFamiglieAggiornate) {
				
				CsACaso caso = fam.getCsASoggetto().getCsACaso();
				
				CsOOperatoreSettore opeTo = findOperatoreTo(caso);
				
				CsOSettore settTo = null ;
				CsOOrganizzazione orgTo = null;
				
				settTo = opeTo!=null ? opeTo.getCsOSettore() : null;
				// orgTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOSettore().getCsOOrganizzazione() : null;
				
				if(opeTo!=null || settTo!=null || orgTo!=null){
					boolean inserisco = this.validaInserimentoNuovoAlert(DataModelCostanti.TipiAlertCod.FAMIGLIA_GIT, caso.getId(), opeTo.getId());
					if(inserisco){
					
						//nuovo alert
						String nome = fam.getCsASoggetto().getCsAAnagrafica().getCognome() + " " + fam.getCsASoggetto().getCsAAnagrafica().getNome();
						String cf = fam.getCsASoggetto().getCsAAnagrafica().getCf();
						String descrizione = "Anagrafe comunale: sono state rilevate delle variazioni relative ai dati del nucleo familiare di "+ nome +" ("+ cf +").";
						String titDescrizione = "Notifica caso "+ nome + ": variazione situazione familiare";
						CsOOrganizzazione orgFrom = this.findOrganizzazioneDefault(TipiAlertCod.FAMIGLIA_GIT);
						logger.info(enteId + " __ Aggiungo Alert per aggiornamento famiglia: " + fam.getCsASoggetto().getCsAAnagrafica().getCf());
						addAlert(TipiAlertCod.FAMIGLIA_GIT, caso, titDescrizione, descrizione, null, null,null, orgFrom, opeTo, settTo, orgTo);
					}else
						logger.info(" __ Sospeso Inserimento Alert per aggiornamento famiglia: già presente [casoId:" + caso.getId()+"]");
				
					//creato l alert azzero la segnalazione
					fam.setFlgSegnalazione("0");
					
					bDto.setObj(fam);
					parentiService.updateFamigliaGruppoGit(bDto);
				}
			}
		
			logger.debug("__ FINE ZsTimerTaskGiornaliero: aggiornaAlertComponentiFamiliari_");
			
    	} catch (Exception e2) {
			logger.error("__ERRORE ZsTimerTaskGiornaliero: aggiornaAlertComponentiFamiliari : " + e2.getMessage(), e2);
		}
    	
	}
	
	public void aggiornaAlertRelazioni() throws NamingException{
		
		logger.debug("__ INIZIO ZsTimerTaskGiornaliero: aggiornaAlertRelazioni__");
			
		CeTBaseObject idto = new CeTBaseObject();
    	BaseDTO bDto = new BaseDTO();
		IterDTO itDto = new IterDTO();
		OperatoreDTO opDto = new OperatoreDTO();

		
    	bDto.setEnteId(enteId);
    	itDto.setEnteId(enteId);
    	opDto.setEnteId(enteId);
    	idto.setEnteId(enteId);
    
    	try {
    		AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableDiarioSessionBean");
    		
			List<CsDRelazione> listaRelazioni = diarioService.findRelazioniInScadenza(idto);
			if(listaRelazioni.size()>0 ) logger.info(" __ Trovate "+listaRelazioni.size()+" relazioni in scadenza.");
			for(CsDRelazione rel : listaRelazioni){
				
				CsACaso caso = rel.getCsDDiario().getCsACaso();
				CsOOperatoreSettore opeTo = findOperatoreTo(caso);
				
				CsOSettore settTo = null;
				CsOOrganizzazione orgTo = null;
				
				//Notifico al settore, solo se non trovo un operatore responsabile!
				if(opeTo==null) settTo = opeTo!=null ? opeTo.getCsOSettore() : null;
				//CsOOrganizzazione orgTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOSettore().getCsOOrganizzazione() : null;
			
				if(opeTo!=null || settTo!=null || orgTo!=null){
					boolean inserisco = this.validaInserimentoNuovoAlert(DataModelCostanti.TipiAlertCod.RELAZIONE, caso.getId(), opeTo.getId());
					if(inserisco){
						//nuovo alert
						String nome = caso.getCsASoggetto().getCsAAnagrafica().getCognome() + " " + caso.getCsASoggetto().getCsAAnagrafica().getNome();
						String cf = caso.getCsASoggetto().getCsAAnagrafica().getCf();
						String descrizione = "Inserire una nuova attivit&agrave; professionale nel fascicolo di "+ nome +" ("+ cf +"). <br/>";
						descrizione += rel.getDataProssimaRelazioneDal()!=null ? "Data prevista prossima attivit&agrave; dal:"+CsUiCompBaseBean.ddMMyyyy.format(rel.getDataProssimaRelazioneDal()) : "";
						descrizione += rel.getDataProssimaRelazioneAl()!=null  ? " al "+ CsUiCompBaseBean.ddMMyyyy.format(rel.getDataProssimaRelazioneAl()) : "";
						String titDescrizione = "Promemoria caso "+ nome + ": inserire nuova attività professionale";
						
						CsOOrganizzazione orgFrom = this.findOrganizzazioneDefault(TipiAlertCod.RELAZIONE);
									
						logger.info(enteId + " __ Aggiungo Alert per inserimento nuova relazione: [casoId:" + caso.getId()+"]");
						addAlert(TipiAlertCod.RELAZIONE, caso, titDescrizione, descrizione, null, null, null, orgFrom, opeTo, settTo, orgTo);
					}else
						logger.info(enteId + " __ Sospeso Inserimento Alert per richiesta nuova relazione: già presente [casoId:" + caso.getId()+"]");
						
				
				//creato l alert azzero la segnalazione
				//TODO: gestire flag su relazione!?
				}
			}
		
			logger.debug("__ FINE ZsTimerTaskGiornaliero: aggiornaAlertRelazioni__");
			
    	} catch (Exception e2) {
			logger.error("__ERRORE ZsTimerTaskGiornaliero: aggiornaAlertRelazioni : " + e2.getMessage(), e2);
		}
		
	}
	
	private CsOOrganizzazione findOrganizzazioneDefault(String tipo) throws Exception{
		
		BaseDTO bDto = new BaseDTO();
		bDto.setEnteId(enteId);
		bDto.setObj(tipo);
		CsAlertConfig aConfig = alertService.getAlertConfigByTipo(bDto);
		if(aConfig != null && aConfig.getCsOOrganizzazioneDefault() != null)
			return aConfig.getCsOOrganizzazioneDefault();
		else return null;
	}
	
	private CsOOperatoreSettore findOperatoreTo(CsACaso caso) throws Exception{
		
    	BaseDTO bDto = new BaseDTO();
		OperatoreDTO opDto = new OperatoreDTO();

    	bDto.setEnteId(enteId);
    	opDto.setEnteId(enteId);
    	
    	bDto.setObj(caso.getId());
		CsACasoOpeTipoOpe tipoOpe = casoService.findCasoOpeResponsabile(bDto);
    	
		CsOOperatoreSettore opeTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore(): null;
				
		//Responsabile altrimenti --> creatore
		if(opeTo == null) {		
			
			opeTo = casoService.findCreatoreCaso(bDto);
			
			/*//creatore
			opDto.setUsername(userIns);
			opeTo = operatoreService.findOperatoreByUsername(opDto);*/
		}
		
		return opeTo;
	}
	
	private boolean validaInserimentoNuovoAlert(String tipo, Long casoId, Long opeId ) throws Exception{
		//Cerco se è già presente un alert NON LETTO, altrimenti inserisco.
		boolean inserisco = true;
		IterDTO itDto = new IterDTO();
		
		itDto.setEnteId(enteId);
		itDto.setIdCaso(casoId);
		itDto.setTipo(tipo);
		itDto.setIdOpSettoreTo(opeId);
		List<CsAlert> listaAlert = alertService.findAlertVisibiliByIdCasoTipoOpeTo(itDto);
	    /*TODO: inserisco solo se tutti gli alert sono letti*/
		boolean tuttiLetti = true; 
	    //Se ci sono degli alert vecchi li rendo non visibili
		for(CsAlert al : listaAlert){
	    	if(al.getLetto()){
		    	al.setDtMod(new Date());
		    	al.setVisibile(false);
		    	
		    	BaseDTO b = new BaseDTO();
		    	b.setEnteId(enteId);
		    	b.setObj(al);
		    	alertService.updateAlert(b);
	    	}else tuttiLetti = false;
	    }
	    
	    inserisco = tuttiLetti;
	    return inserisco;
	}
	
	private void addAlert(String tipo, CsACaso caso, String oggetto, String descrizione, String url, 
			              CsOOperatore opFrom, CsOSettore settFrom, CsOOrganizzazione orgFrom,
						  CsOOperatoreSettore opTo,   CsOSettore settTo,   CsOOrganizzazione orgTo) throws Exception{
	
		AlertDTO newAlert = new AlertDTO();
		newAlert.setEnteId(enteId);
		
		OperatoreDTO opdto =  new OperatoreDTO();
		opdto.setEnteId(enteId);
		
		if(opFrom!=null)
			newAlert.setOperatoreFrom(opFrom);
		if(settFrom!=null){
			newAlert.setSettoreFrom(settFrom);
			newAlert.setOrganizzazioneFrom(settFrom.getCsOOrganizzazione());
		}else
			newAlert.setOrganizzazioneFrom(orgFrom);
		
		newAlert.setCaso(caso);
		newAlert.setTipo(tipo);
		
		newAlert.setOpSettoreTo(opTo);
		newAlert.setSettoreTo(settTo);
		newAlert.setOrganizzazioneTo(orgTo);
		
		newAlert.setDescrizione(descrizione);
		newAlert.setUrl(url);
		newAlert.setTitolo(oggetto);
		
		alertService.addAlert(newAlert);
	}

	
	
	public static void setLogger(Logger logger) {
		ZsTimerTaskGiornaliero.logger = logger;
	}

}
