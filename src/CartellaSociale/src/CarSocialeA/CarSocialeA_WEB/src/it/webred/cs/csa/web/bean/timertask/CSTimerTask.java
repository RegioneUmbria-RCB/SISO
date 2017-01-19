package it.webred.cs.csa.web.bean.timertask;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableParentiGitSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.RelazioneDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsAFamigliaGruppoGit;
import it.webred.cs.data.model.CsAlertConfig;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.persist.CsAlert;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;

import java.util.List;
import java.util.TimerTask;

import javax.naming.NamingException;

import org.jboss.logging.Logger;

public class CSTimerTask extends TimerTask {

	private static Logger logger;
	private String enteId;

	public CSTimerTask(String enteId) {
		super();
		this.enteId = enteId;
	}

	@Override
	public void run() {

		try {
			
			logger.debug("__ INIZIO Esecuzione CSTimerTask ENTEID " + enteId +" __");
			
			AccessTableAlertSessionBeanRemote alertService = (AccessTableAlertSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableAlertSessionBean");
			AccessTableParentiGitSessionBeanRemote parentiService = (AccessTableParentiGitSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableParentiGitSessionBean");
			AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
			
			BaseDTO bDto = new BaseDTO();
			IterDTO itDto = new IterDTO();
			OperatoreDTO opDto = new OperatoreDTO();

			
	    	bDto.setEnteId(enteId);
	    	itDto.setEnteId(enteId);
	    	opDto.setEnteId(enteId);
	    	
	    	this.aggiornaAlertRelazioni();
		
	    	try {
	    	
				List<CsAFamigliaGruppoGit> listaFamiglieAggiornate = parentiService.getFamigliaGruppoGitAggiornate(bDto);
				for(CsAFamigliaGruppoGit fam: listaFamiglieAggiornate) {
					
					CsACaso caso = fam.getCsASoggetto().getCsACaso();
					bDto.setObj(caso.getId());
					CsACasoOpeTipoOpe tipoOpe = casoService.findCasoOpeResponsabile(bDto);
					CsOOperatore opeTo = findOperatoreTo(tipoOpe, caso.getUserIns());
					
					CsOSettore settTo = null;
					CsOOrganizzazione orgTo = null;
					// settTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOSettore() : null;
					// orgTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOSettore().getCsOOrganizzazione() : null;
					
					//cerco se è già presente un alert
					itDto.setIdCaso(caso.getId());
					itDto.setTipo(DataModelCostanti.TipiAlert.FAMIGLIA_GIT);
					List<CsAlert> listaAlert = alertService.findAlertByIdCasoTipo(itDto);
					if(listaAlert != null && !listaAlert.isEmpty()) {
						
						CsAlert alert = listaAlert.get(0);
						
						if(alert.getVisibile() && !alert.getLetto() && alert.getCsOOperatore2().getId() == opeTo.getId())
							continue;
						
						
						alert.setCsOOperatore2(opeTo);
						alert.setCsOSettore2(settTo);
						alert.setCsOOrganizzazione2(orgTo);
						alert.setVisibile(true);
						alert.setLetto(false);
						bDto.setObj(alert);
						
						logger.info(enteId + " __ Aggiorno Alert per aggiornamento famiglia: " + fam.getCsASoggetto().getCsAAnagrafica().getCf());
						alertService.updateAlert(bDto);
						
					} else {
					
						//nuovo alert
						String nome = fam.getCsASoggetto().getCsAAnagrafica().getCognome() + " " + fam.getCsASoggetto().getCsAAnagrafica().getNome();
						String cf = fam.getCsASoggetto().getCsAAnagrafica().getCf();
						String descrizione = "Sono presenti aggiornamenti nei dati familiari di "+ nome +" ("+ cf +")";
						String titDescrizione = "Sono presenti aggiornamenti nei dati familiari di " + nome +" (" + cf + ")";
						
						/*itDto.setLabelTipo(DataModelCostanti.TipiAlert.FAMIGLIA_GIT_DESC);
						itDto.setDescrizione(descrizione);
						itDto.setUrl(null);
						itDto.setTitoloDescrizione(titDescrizione);*/
					
						CsOOrganizzazione orgFrom = this.findOrganizzazioneDefault(DataModelCostanti.TipiAlert.FAMIGLIA_GIT);
					
						logger.info(enteId + " __ Aggiungo Alert per aggiornamento famiglia: " + fam.getCsASoggetto().getCsAAnagrafica().getCf());
						addAlert(caso, null, null, orgFrom, DataModelCostanti.TipiAlert.FAMIGLIA_GIT,  DataModelCostanti.TipiAlert.FAMIGLIA_GIT_DESC, opeTo, settTo, orgTo, 
								descrizione, null, titDescrizione);

					}
					
					//creato l alert azzero la segnalazione
					fam.setFlgSegnalazione("0");
					bDto.setObj(fam);
					parentiService.updateFamigliaGruppoGit(bDto);
					
				}
			
	    	} catch (Exception e2) {
				logger.error("__ CSTimerTask: Eccezione su ENTEID " + enteId + ": " + e2.getMessage(), e2);
			}
			
			logger.debug("__ FINE Esecuzione CSTimerTask ENTEID " + enteId +" __");

		} catch (Exception e) {
			logger.error("__ CSTimerTask: Eccezione su ENTEID " + enteId + ": " + e.getMessage(), e);
		}
	}
	
	public void aggiornaAlertRelazioni() throws NamingException{
		AccessTableAlertSessionBeanRemote alertService = (AccessTableAlertSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableAlertSessionBean");
		AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
		AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");
		
		CeTBaseObject idto = new CeTBaseObject();
    	BaseDTO bDto = new BaseDTO();
		IterDTO itDto = new IterDTO();
		OperatoreDTO opDto = new OperatoreDTO();

		
    	bDto.setEnteId(enteId);
    	itDto.setEnteId(enteId);
    	opDto.setEnteId(enteId);
    	idto.setEnteId(enteId);
    
    	try {
	    	
			List<CsDRelazione> listaRelazioni = diarioService.findRelazioniEnteInScadenza(idto);
			if(listaRelazioni.size()>0 ) logger.info(enteId + " __ Trovate "+listaRelazioni.size()+" relazioni in scadenza per il comune.");
			for(CsDRelazione rel : listaRelazioni){
				
				CsACaso caso = rel.getCsDDiario().getCsACaso();
				bDto.setObj(caso.getId());
				CsACasoOpeTipoOpe tipoOpe = casoService.findCasoOpeResponsabile(bDto);
				CsOOperatore opeTo = findOperatoreTo(tipoOpe, caso.getUserIns());
				
				//Non valorizzo, altrimenti in fase di caricamento ignoro l'operatore.
				CsOSettore settTo = null;
				CsOOrganizzazione orgTo = null;
				//CsOSettore settTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOSettore() : null;
				//CsOOrganizzazione orgTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOSettore().getCsOOrganizzazione() : null;
				
				
				//cerco se è già presente un alert
				itDto.setIdCaso(caso.getId());
				itDto.setTipo(DataModelCostanti.TipiAlert.RELAZIONE);
				List<CsAlert> listaAlert = alertService.findAlertByIdCasoTipo(itDto);
				if(listaAlert != null && !listaAlert.isEmpty()) {
					
					CsAlert alert = listaAlert.get(0);
					
					if(alert.getVisibile().booleanValue() && !alert.getLetto().booleanValue() && alert.getCsOOperatore2().getId() == opeTo.getId())
						continue;
					
					alert.setCsOOperatore2(opeTo);
					alert.setCsOSettore2(settTo);
					alert.setCsOOrganizzazione2(orgTo);
					alert.setVisibile(true);
					alert.setLetto(false);
					bDto.setObj(alert);
					
					logger.info(enteId + " __ Aggiorno Alert per richiesta inserimento nuova relazione: caso " + caso.getId());
					alertService.updateAlert(bDto);
					
				} else {
				
					//nuovo alert
					String nome = caso.getCsASoggetto().getCsAAnagrafica().getCognome() + " " + caso.getCsASoggetto().getCsAAnagrafica().getNome();
					String cf = caso.getCsASoggetto().getCsAAnagrafica().getCf();
					String descrizione = "Inserire una nuova relazione nel fascicolo di "+ nome +" ("+ cf +")";
					String titDescrizione = "Inserire una nuova relazione nel fascicolo di "+ nome +" ("+ cf +")";
					
				/*	itDto.setLabelTipo(DataModelCostanti.TipiAlert.RELAZIONE_DESC);
					itDto.setDescrizione(descrizione);
					itDto.setUrl(null);
					itDto.setTitoloDescrizione("Inserire una nuova relazione nel fascicolo di " + nome +" (" + cf + ")");*/
					
					CsOOrganizzazione orgFrom = this.findOrganizzazioneDefault(DataModelCostanti.TipiAlert.RELAZIONE);
								
					logger.info(enteId + " __ Aggiungo Alert per inserimento nuova relazione: " + caso.getCsASoggetto().getCsAAnagrafica().getCf());
					addAlert(caso, null, null, orgFrom, DataModelCostanti.TipiAlert.RELAZIONE,  DataModelCostanti.TipiAlert.RELAZIONE_DESC, opeTo, settTo, orgTo, descrizione, null, titDescrizione);
				}
				
				//creato l alert azzero la segnalazione
				//TODO: gestire flag su relazione!?
			}
		
    	} catch (Exception e2) {
			logger.info("__ CSTimerTask: Eccezione su ENTEID " + enteId + ": " + e2.getMessage());
		}
		
	}
	
	private CsOOrganizzazione findOrganizzazioneDefault(String tipo) throws Exception{
		AccessTableAlertSessionBeanRemote alertService = (AccessTableAlertSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableAlertSessionBean");
		
		BaseDTO bDto = new BaseDTO();
		bDto.setEnteId(enteId);
		bDto.setObj(tipo);
		CsAlertConfig aConfig = alertService.getAlertConfigByTipo(bDto);
		if(aConfig != null && aConfig.getCsOOrganizzazioneDefault() != null)
			return aConfig.getCsOOrganizzazioneDefault();
		else return null;
	}
	
	private CsOOperatore findOperatoreTo(CsACasoOpeTipoOpe tipoOpe, String userIns) throws Exception{
		
		AccessTableOperatoreSessionBeanRemote operatoreService = (AccessTableOperatoreSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
		
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
	
	private void addAlert(CsACaso caso, CsOOperatore opFrom, CsOSettore settFrom, CsOOrganizzazione orgFrom,String tipo, String label,
							    CsOOperatore opTo, CsOSettore settTo, CsOOrganizzazione orgTo, String descrizione, String url, String titDescrizione) throws Exception{
		
		AccessTableAlertSessionBeanRemote alertService = (AccessTableAlertSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableAlertSessionBean");
		AccessTableOperatoreSessionBeanRemote operatoreService = (AccessTableOperatoreSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
		
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
		newAlert.setLabelTipo(label);
		newAlert.setVisibile(true);
		newAlert.setLetto(false);
		
		newAlert.setCsOOperatore2(opTo);
		newAlert.setCsOSettore2(settTo);
		newAlert.setCsOOrganizzazione2(orgTo);
		
		newAlert.setDescrizione(descrizione);
		newAlert.setUrl(url);
		newAlert.setTitoloDescrizione(titDescrizione);
		
		BaseDTO dto = new BaseDTO();
		dto.setEnteId(enteId);
		dto.setObj(newAlert);
		
		alertService.addAlert(dto);
	}

	public static void setLogger(Logger logger) {
		CSTimerTask.logger = logger;
	}

}
