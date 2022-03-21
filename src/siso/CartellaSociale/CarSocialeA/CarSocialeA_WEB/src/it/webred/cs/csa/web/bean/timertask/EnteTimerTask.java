package it.webred.cs.csa.web.bean.timertask;

import java.util.TimerTask;

import javax.interceptor.ExcludeDefaultInterceptors;

import org.jboss.logging.Logger;

@ExcludeDefaultInterceptors
public class EnteTimerTask extends TimerTask {

	private static Logger logger = Logger.getLogger("carsociale_timertask.log");
	private String enteId;	

	public EnteTimerTask(String enteId) {
		super();
		this.enteId = enteId;
	}

	@Override
	public void run() {

		try {
				
	    	//this.aggiornaAlertRelazioni();
	    	//this.aggiornaAlertComponentiFamiliari();
	    	
			logger.debug("__ FINE Esecuzione EnteTimerTask ENTEID " + enteId +" __");

		} catch (Exception e) {
			logger.error("__ EnteTimerTask: Eccezione su ENTEID " + enteId + ": " + e.getMessage(), e);
		}
	}

/*	public void aggiornaAlertComponentiFamiliari() throws NamingException{
		
		logger.debug(enteId+"__ INIZIO EnteTimerTask: aggiornaAlertComponentiFamiliari_");
		
		AccessTableParentiGitSessionBeanRemote parentiService = (AccessTableParentiGitSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableParentiGitSessionBean");
		
		BaseDTO bDto = new BaseDTO();
		IterDTO itDto = new IterDTO();
		OperatoreDTO opDto = new OperatoreDTO();

    	bDto.setEnteId(enteId);
    	itDto.setEnteId(enteId);
    	opDto.setEnteId(enteId);
		
    	try {
	    	
			List<CsAFamigliaGruppoGit> listaFamiglieAggiornate = parentiService.getFamigliaGruppoGitAggiornate(bDto);
			for(CsAFamigliaGruppoGit fam: listaFamiglieAggiornate) {
				
				CsACaso caso = fam.getCsASoggetto().getCsACaso();
				bDto.setObj(caso.getId());
				CsACasoOpeTipoOpe tipoOpe = casoService.findCasoOpeResponsabile(bDto);
				CsOOperatore opeTo = findOperatoreTo(tipoOpe, caso.getUserIns());
				
				CsOSettore settTo = null ;
				CsOOrganizzazione orgTo = null;
				
				settTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOSettore() : null;
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
						logger.info(enteId + " __ Sospeso Inserimento Alert per aggiornamento famiglia: già presente [casoId:" + caso.getId()+"]");
				
					//creato l alert azzero la segnalazione
					fam.setFlgSegnalazione("0");
					
					bDto.setObj(fam);
					parentiService.updateFamigliaGruppoGit(bDto);
				}
			}
		
			logger.debug(enteId+"__ FINE EnteTimerTask: aggiornaAlertComponentiFamiliari_");
			
    	} catch (Exception e2) {
			logger.error(enteId+"__ERRORE EnteTimerTask: aggiornaAlertComponentiFamiliari : " + e2.getMessage(), e2);
		}
    	
	}
	
	public void aggiornaAlertRelazioni() throws NamingException{
		
		logger.debug(enteId+"__ INIZIO EnteTimerTask: aggiornaAlertRelazioni__");
		
		AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableDiarioSessionBean");
		
		CeTBaseObject idto = new CeTBaseObject();
    	BaseDTO bDto = new BaseDTO();
		IterDTO itDto = new IterDTO();
		OperatoreDTO opDto = new OperatoreDTO();

		
    	bDto.setEnteId(enteId);
    	itDto.setEnteId(enteId);
    	opDto.setEnteId(enteId);
    	idto.setEnteId(enteId);
    
    	try {
	    	
			List<CsDRelazione> listaRelazioni = diarioService.findRelazioniInScadenza(idto);
			if(listaRelazioni.size()>0 ) logger.info(" __ Trovate "+listaRelazioni.size()+" relazioni in scadenza.");
			for(CsDRelazione rel : listaRelazioni){
				
				CsACaso caso = rel.getCsDDiario().getCsACaso();
				bDto.setObj(caso.getId());
				CsACasoOpeTipoOpe tipoOpe = casoService.findCasoOpeResponsabile(bDto);
				CsOOperatore opeTo = findOperatoreTo(tipoOpe, caso.getUserIns());
				
				CsOSettore settTo = null;
				CsOOrganizzazione orgTo = null;
				settTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOSettore() : null;
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
		
			logger.debug(enteId+"__ FINE EnteTimerTask: aggiornaAlertRelazioni__");
			
    	} catch (Exception e2) {
			logger.error(enteId+"__ERRORE EnteTimerTask: aggiornaAlertRelazioni : " + e2.getMessage(), e2);
		}
		
	}
	
	private CsOOperatore findOperatoreTo(CsACasoOpeTipoOpe tipoOpe, String userIns) throws Exception{
		
    	BaseDTO bDto = new BaseDTO();
		OperatoreDTO opDto = new OperatoreDTO();

    	bDto.setEnteId(enteId);
    	opDto.setEnteId(enteId);
    	
		CsOOperatore opeTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOOperatore() : null;
		//Responsabile altrimenti --> creatore
		if(opeTo == null) {		
			//creatore
			opDto.setUsername(userIns);
			opeTo = operatoreService.findOperatoreByUsername(opDto);
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
		itDto.setIdOpTo(opeId);
		List<CsAlert> listaAlert = alertService.findAlertVisibiliByIdCasoTipoOpeTo(itDto);
	    TODO: inserisco solo se tutti gli alert sono letti
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
						  CsOOperatore opTo,   CsOSettore settTo,   CsOOrganizzazione orgTo) throws Exception{
		
		
		CsAlert newAlert = new CsAlert();
		
		OperatoreDTO opdto =  new OperatoreDTO();
		opdto.setEnteId(enteId);
		
		if(opFrom!=null){
			opdto.setIdOperatore(opFrom.getId());
			CsOOperatore op1 = operatoreService.findOperatoreById(opdto);
			newAlert.setCsOOperatore1(op1);
		}
		if(settFrom!=null){
			newAlert.setCsOSettore1(settFrom);
			newAlert.setCsOOrganizzazione1(settFrom.getCsOOrganizzazione());
		}else
			newAlert.setCsOOrganizzazione1(orgFrom);
		
		newAlert.setCsACaso(caso);
		newAlert.setTipo(tipo);
		newAlert.setVisibile(true);
		newAlert.setLetto(false);
		
		newAlert.setCsOOperatore2(opTo);
		newAlert.setCsOSettore2(settTo);
		newAlert.setCsOOrganizzazione2(orgTo);
		
		newAlert.setDescrizione(descrizione);
		newAlert.setUrl(url);
		newAlert.setTitoloDescrizione(oggetto);
		
		BaseDTO dto = new BaseDTO();
		dto.setEnteId(enteId);
		dto.setObj(newAlert);
		
		alertService.addAlert(dto);
	}

	
	*/
	public static void setLogger(Logger logger) {
		EnteTimerTask.logger = logger;
	}

}
