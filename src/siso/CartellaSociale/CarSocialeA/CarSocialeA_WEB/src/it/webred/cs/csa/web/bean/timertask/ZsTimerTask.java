package it.webred.cs.csa.web.bean.timertask;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoErogazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
import it.webred.cs.data.model.CsPaiMastSogg;
import it.webred.cs.data.model.persist.CsAlert;
import it.webred.cs.sociosan.ejb.client.CTConfigClientSessionBeanRemote;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.mailing.MailUtils;
import it.webred.mailing.MailUtils.MailAddressList;
import it.webred.mailing.MailUtils.MailParamBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import javax.activation.FileDataSource;
import javax.interceptor.ExcludeDefaultInterceptors;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

@ExcludeDefaultInterceptors
public class ZsTimerTask extends TimerTask {

	private static Logger logger = Logger.getLogger("carsociale_timertask.log");
	private String enteId;

	public ZsTimerTask(String enteId) {
		super();
		this.enteId = enteId;
	}

	@Override
	public void run() {

		try {
			
			logger.debug("__ INIZIO Esecuzione ZsTimerTask ENTEID " + enteId +" __");
			
			associaCasoASoggettiErogazione();
			associaCasoASoggettiPai();
			gestisciInvioMail();

			// caricaDatiStagingMobile();

			TimerTaskPTI taskPTI = new TimerTaskPTI(enteId);
			
			taskPTI.gestisciInsMinoriStruttura();
			taskPTI.gestisciAcquisizionePai();
			taskPTI.gestisciAcquisizioneConsuntivazioni();

			logger.debug("__ FINE Esecuzione ZsTimerTask __");

		} catch (Exception e) {
			logger.error("__ ZsTimerTask: Eccezione su " + e.getMessage(), e);
		}
	}
	
	private void associaCasoASoggettiErogazione(){
	
    	try {
    		
    		AccessTableInterventoSessionBeanRemote intService = (AccessTableInterventoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
    		AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
    		
    		BaseDTO bDto = new BaseDTO();
        	bDto.setEnteId(enteId);
    		
    	    List<CsIInterventoEsegMastSogg> lst = intService.findSoggettiErogazioneSenzaCaso(bDto);
    	    logger.debug("Trovate "+lst.size()+" erogazioni da collegare al caso (esistente)");
    	    //Il metodo restituisce già i record senza casoId, il cui CF è presente nelle anagrafiche dei casi esistenti.
    	    
    	    for(CsIInterventoEsegMastSogg se : lst){
    	    	if(se.getCf()!=null && !se.getCf().trim().isEmpty()){
    	    		String cf = se.getCf()!=null ? se.getCf().toUpperCase() : null;
    	    		bDto.setObj(cf.toUpperCase());
    	    		CsASoggettoLAZY s = soggettoService.getSoggettoByCF(bDto);
    	    		if(s!=null && s.getCsACaso()!=null){
    	    			logger.debug("ZsTimerTask_Individuato caso per il soggetto: "+cf);
    	    			se.setCaso(s.getCsACaso());
    	    			se.setDtMod(new Date());
    	    			se.setUserMod("ZsTimerTask");
    	    			bDto.setObj(se);
    	    			intService.updateSoggettoErogazione(bDto);
    	    		}
    	    	}
    	    }
		
    	} catch (Exception e2) {
			logger.error("__ ZsTimerTask: Eccezione " + e2.getMessage(), e2);
		}
	}
	
	private void associaCasoASoggettiPai(){
		
    	try {
    		
    		AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");
    		AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
    		
    		BaseDTO bDto = new BaseDTO();
        	bDto.setEnteId(enteId);
    		
    	    List<CsPaiMastSogg> lst = diarioService.findSoggettiPaiSenzaCaso(bDto);
    	    logger.debug("Trovati "+lst.size()+" soggetti (pai) da collegare al caso (esistente)");
    	    
    	    for(CsPaiMastSogg sogg : lst){
    	    	if(sogg.getCf()!=null && !sogg.getCf().trim().isEmpty()){
    	    		String cf = sogg.getCf()!=null ? sogg.getCf().toUpperCase() : null;
    	    		bDto.setObj(cf.toUpperCase());
    	    		CsASoggettoLAZY s = soggettoService.getSoggettoByCF(bDto);
    	    		if(s!=null && s.getCsACaso()!=null){
    	    			logger.debug("ZsTimerTask_Individuato caso per il soggetto: "+cf);
    	    			sogg.setCaso(s.getCsACaso());
    	    			sogg.setDtMod(new Date());
    	    			sogg.setUserMod("ZsTimerTask");
    	    			bDto.setObj(sogg);
    	    			diarioService.updateSoggettoPai(bDto);
    	    		}
    	    	}
    	    }
		
    	} catch (Exception e2) {
			logger.error("__ ZsTimerTask: Eccezione " + e2.getMessage(), e2);
		}
	}

	public static void setLogger(Logger logger) {
		ZsTimerTask.logger = logger;
	}
	
	private void gestisciInvioMail() throws NamingException {

		logger.debug("__ INIZIO ZsTimerTask: gestisciInvioMail__");
		
		CTConfigClientSessionBeanRemote mailConf = (CTConfigClientSessionBeanRemote)   ClientUtility.getEjbInterface("SocioSanitario","SocioSanitario_EJB", "CTConfigClientSessionBean");
		AccessTableAlertSessionBeanRemote alertService = (AccessTableAlertSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableAlertSessionBean");

		BaseDTO bDto = new BaseDTO();
		bDto.setEnteId(enteId);

		List<CsAlert> lstAlert = alertService.findAlertEmail(bDto);
		logger.debug("__ Trovati "+lstAlert.size()+" alert, per i quali deve ancora essere inviata la notifica e-mail");
		HashMap<String, Boolean> mappaEmail = new HashMap<String, Boolean>();
		try{
			MailParamBean params = mailConf.getSISOMailParametres();
			
			for(CsAlert a : lstAlert){
				
				List<String> emailSegnalatoA = new ArrayList<String>();
				String email;
				/* Solo se attivato il flag (eredito dal salvataggio IT_STEP) */
				String emailSettoreSegnalante = null;
				boolean notificaSegnalante = a.getNotificaSegnalante()!=null && a.getNotificaSegnalante();
				if(notificaSegnalante && a.getCsOSettore1()!=null)
					emailSettoreSegnalante = a.getCsOSettore1().getEmail();
				
				if(a.getCsOpSettore2()!=null && a.getCsOpSettore2().getCsOOperatore().getCsOOperatoreAnagrafica().getEmail()!=null){ 
					boolean ricAttiva = this.isRicezioneAttiva(alertService, a, mappaEmail);
					email = a.getCsOpSettore2().getCsOOperatore().getCsOOperatoreAnagrafica().getEmail();
					if(ricAttiva && !emailSegnalatoA.contains(email))
						emailSegnalatoA.add(email);
				}
					
				if(a.getCsOSettore2()!=null && a.getCsOSettore2().getEmail()!=null){
					email = a.getCsOSettore2().getEmail();
					if(!emailSegnalatoA.contains(email)) emailSegnalatoA.add(email);
				} 
				
				if(a.getCsOOrganizzazione2()!=null && a.getCsOOrganizzazione2().getEmail()!=null){
					email = a.getCsOOrganizzazione2().getEmail();
					if(!emailSegnalatoA.contains(email))  emailSegnalatoA.add(email);
				}
			
				
				if(!emailSegnalatoA.isEmpty()){
					try{
						// Now try to send email
						MailAddressList addressTO = new MailAddressList(emailSegnalatoA);
						MailAddressList addressCC = new MailAddressList();
						MailAddressList addressBCC = new MailAddressList(emailSettoreSegnalante);

						// Segnalibri
						String subject = a.getTitoloDescrizione();
						String messageBody = a.getDescrizione();
			
						MailUtils.sendEmail(params, addressTO, addressCC, addressBCC, subject,messageBody, (FileDataSource[]) null);
						
						/*Aggiorno alert*/
						a.setEmailInviata(true);
						a.setDtInvioEmail(new Date());
						bDto.setObj(a);
						alertService.updateAlert(bDto);
						
					}catch(Exception e){
						logger.error("__ Errore in invio email per alert "+a.getId()+":"+e.getMessage(), e);
					}
				}
			}
		
		}catch(Exception ex){
			logger.error("__ Errore gestisciInvioMail", ex);
		}
		
		logger.debug(enteId+"__ FINE ZsTimerTask: gestisciInvioMail__");
	}
	
	private boolean isRicezioneAttiva(AccessTableAlertSessionBeanRemote alertService, CsAlert alert, HashMap<String, Boolean> mappaEmail){
				
		String tipo = alert.getTipo();
		Long opSettId = alert.getCsOpSettore2().getId();
		
		String key = opSettId+"|"+tipo;
		
		Boolean attiva = mappaEmail.get(key);
		
		if(attiva==null){
			attiva = false;
			BaseDTO bDto = new BaseDTO();
			bDto.setEnteId(enteId);
			
			if(alert.getCsOpSettore2().getId()>0){
				bDto.setObj(alert.getCsOpSettore2().getId());
				bDto.setObj3(tipo);
				attiva =  alertService.isRicEmailAttiva(bDto);
			}
			logger.debug("isRicezioneAttiva key["+key+"] attiva["+attiva+"]");
			mappaEmail.put(key, attiva);
		}
		return attiva;
	}

	
	private void caricaDatiStagingMobile() throws Exception{
	logger.debug("__ INIZIO ZsTimerTask: caricaDatiStagingMobile_");
		AccessTableInterventoErogazioneSessionBeanRemote sb =  (AccessTableInterventoErogazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoErogazioneSessionBean");
		 
			CeTBaseObject bDto = new CeTBaseObject();
			bDto.setEnteId(enteId);
			
			sb.verificaLoadingMobileStaging(bDto);
		logger.debug(enteId+"__ FINE ZsTimerTask: caricaDatiStagingMobile_");
	}

}
