package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dao.AlertDAO;
import it.webred.cs.csa.ejb.dao.CasoDAO;
import it.webred.cs.csa.ejb.dao.DiarioDAO;
import it.webred.cs.csa.ejb.dao.IterDAO;
import it.webred.cs.csa.ejb.dao.SoggettoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.alert.AlertBean;
import it.webred.cs.csa.ejb.dto.alert.AlertDTO;
import it.webred.cs.csa.ejb.dto.alert.TipoAlertBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiNotifiche;
import it.webred.cs.data.DataModelCostanti.TipiAlertCod;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsAlertBASIC;
import it.webred.cs.data.model.CsAlertConfig;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOpsettoreAlertConfig;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsPaiMastSogg;
import it.webred.cs.data.model.persist.CsAlert;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class AccessTableAlertSessionBean extends CarSocialeBaseSessionBean implements AccessTableAlertSessionBeanRemote {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AlertDAO alertDao;
	
	@Autowired
	private IterDAO iterDao;

	@Autowired
	private DiarioDAO diarioDao;
	
	@Autowired
	private SoggettoDAO soggettoDao;
	
	@Autowired
	private CasoDAO casoDao;
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public void addAlert(AlertDTO dto) throws CarSocialeServiceException {
		try{
			CsAlert newAlert = new CsAlert();
			dto.fillAlertJPA(newAlert);
			
			newAlert.setVisibile(true);
			newAlert.setLetto(false);
			newAlert.setEmailInviata(false);
			
			if(newAlert.getCsOpSettore2()!=null || newAlert.getCsOSettore2()!=null || newAlert.getCsOOrganizzazione2()!=null){
				alertDao.createAlert(newAlert);
			}else
				logger.warn("Nessun destinatario specificato: impossibile inserire l'alert!");
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
	}
	
	@Override
	public void addAlertNuovoInserimentoToResponsabileCaso(BaseDTO dto) throws CarSocialeServiceException {
		try{
			//OBJ1
			CsASoggettoLAZY s = null;
			if(dto.getObj() instanceof CsASoggettoLAZY)
				s = (CsASoggettoLAZY) dto.getObj();
			else
				s = soggettoDao.getSoggettoByCF((String)dto.getObj());	
			
			if(s == null) return; //Soggetto senza CASO ASSOCIATO (può succedere per le erogazioni)
			
			//OBJ2
			Long idOrg = null;
			CsOOperatoreSettore os = null;
			if(dto.getObj2()!=null && dto.getObj2() instanceof CsOOperatoreSettore){
				os = (CsOOperatoreSettore)dto.getObj2();
				idOrg = os.getCsOSettore().getCsOOrganizzazione().getId();
			}else idOrg = (Long)dto.getObj2();
		
			//OBJ3
			String tipoAlert = (String)dto.getObj3();
			//OBJ4
			String labelTipo = (String)dto.getObj4();
			
			Boolean invioAncheSeResp = dto.getObj5() !=null ? (Boolean) dto.getObj5() : Boolean.FALSE;
			
			CsOOperatoreSettore opSettResponsabile = null; 
			if(s!=null){
				dto.setObj(s.getCsACaso().getId());
				opSettResponsabile = findDestinatarioAlertCaso(dto);
			}
			
			AlertDTO adto = new AlertDTO();
			adto.setEnteId(dto.getEnteId());
			adto.setUserId(dto.getUserId());
			adto.setSessionId(dto.getSessionId());
			adto.setCaso(s.getCsACaso());
			
			String denominazione = s.getCsAAnagrafica().getCognome()+" "+s.getCsAAnagrafica().getNome()+" (c.f. "+s.getCsAAnagrafica().getCf()+")";
			String descrizione ="E' stato inserito ";
			String today = ddMMyyyy.format(new Date());
			
			if(os!=null){
				adto.setOrganizzazioneFrom(os.getCsOSettore().getCsOOrganizzazione());
				adto.setSettoreFrom(os.getCsOSettore());
				adto.setOperatoreFrom(os.getCsOOperatore());
				descrizione = "L'operatore "
						+ os.getCsOOperatore().getCsOOperatoreAnagrafica().getCognome() + " "
						+ os.getCsOOperatore().getCsOOperatoreAnagrafica().getNome()
						+ " ha inserito ";
			}
			
			descrizione += labelTipo+" in data "+ today +", per il caso "+denominazione.toUpperCase();
			
			String titolo = "Notifica caso " + denominazione + ":" + labelTipo;
			
			adto.setTitolo(titolo);
			adto.setDescrizione(descrizione);
			adto.setOpSettoreTo(opSettResponsabile);
			adto.setTipo(tipoAlert);
			
			/*Inserisco alert solo se: 
			 * 		-   l'operatore che ha modificato la scheda UDC è diverso dal responsabile della CARTELLA SOCIALE (o creatore, se non è stata presa in carico)
			 * */
			//** SISO-1278 in caso di Valutazione Multidimensionale invio l'alert comunque attraverso il flag invioAncheSeResp
			if(opSettResponsabile!=null && (!opSettResponsabile.getCsOOperatore().getUsername().equals(dto.getUserId()) || invioAncheSeResp)){
				
				//Se, per alcuni tipi alert, l'operatore che inserisce appartiene ad una organizzazione diversa dal responsabile non aggiungo l'alert!
				if(tipoAlert.equalsIgnoreCase(DataModelCostanti.TipiAlertCod.UDC) 
						&& opSettResponsabile.getCsOSettore().getCsOOrganizzazione().getId().longValue()!=idOrg.longValue()) 
				   return;
						   
				this.addAlert(adto);
			}
			
			// notifica al responsabile settore (a cui è segnalato il caso (se esiste) o a quello di provenienza.
			// to
			CsItStep itStep = iterDao.getLastIterStepByCaso(s.getCsACaso().getId());
			adto.setOpSettoreTo(null);
			if (itStep.getCsOSettore2() != null) {
				adto.setOrganizzazioneTo(itStep.getCsOOrganizzazione2());
				adto.setSettoreTo(itStep.getCsOSettore2());
				addAlert(adto);
			} else if (opSettResponsabile==null && itStep.getCsOSettore1() != null) {
				adto.setOrganizzazioneTo(itStep.getCsOOrganizzazione1());
				adto.setSettoreTo(itStep.getCsOSettore1());
				addAlert(adto);
			}
		
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
	}
	
	@Override
	public LinkedList<TipoAlertBean> getNotificheVisibili(BaseDTO dto) throws Exception {
		LinkedList<TipoAlertBean> listTipoAlert = new LinkedList<TipoAlertBean>();
		try{
			
			CsOOperatoreSettore opSettore = (CsOOperatoreSettore)dto.getObj();
			Long idSettoreTo= opSettore.getCsOSettore().getId();
			
			boolean hasPermessoEnte = (Boolean)dto.getObj2();
			boolean hasPermessoSettore = (Boolean)dto.getObj3();
			List<Long> idSettoriAbilitatiOperatore = (List<Long>)dto.getObj4();
			HashMap<String, String> permessiGruppoSettore = (HashMap<String, String>) dto.getObj5();
			
			Long settoreRicerca = hasPermessoSettore ? idSettoreTo : null;
			Long enteRicerca = hasPermessoEnte ? opSettore.getCsOSettore().getCsOOrganizzazione().getId() : null;
			
			List<String> lstTipi = loadTipoNotificaAttiva(opSettore.getId(), permessiGruppoSettore);
			
			if(lstTipi!=null && !lstTipi.isEmpty()){
				/**
				 * Al momento non usato - in precedenza ricercavo comunque per operatore_id,
				 * ora ricerco per operatore_settore in modo tale che l'elenco delle notifiche venga caricato al variare del settore selezionato
				 * Se necessario, ripristinare la situazione precedente.*/
				
				List<CsAlertBASIC> listaAlert = alertDao.getAlerts(opSettore.getId(), settoreRicerca ,enteRicerca, lstTipi, true);	
				for( CsAlertBASIC csa : listaAlert ){
					if(csa.getVisibile()){
						TipoAlertBean curTipoAlert = null;
						for (TipoAlertBean item : listTipoAlert){
							if(item.getLabelTipo().equalsIgnoreCase(csa.getLabelTipo()))
								curTipoAlert = item;
						}
						
						if (curTipoAlert == null ) {
							curTipoAlert = new TipoAlertBean();
							curTipoAlert.setLabelTipo(csa.getLabelTipo());
							listTipoAlert.add(curTipoAlert);
						}
						AlertBean alBean = new AlertBean( csa, idSettoreTo, opSettore.getCsOOperatore().getId(), hasPermessoSettore, hasPermessoEnte, idSettoriAbilitatiOperatore);
						curTipoAlert.getListaAlert().add(alBean);
						
					}
				}
			}else{
				logger.warn("Permessi di visualizzazione notifiche (ITEM: Notifiche Cartella) non configurati per i gruppi: "+ opSettore.getAmGroup());
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw e;
		}
		return listTipoAlert;
	}
	
	@Override
	public CsAlert findAlertById(IterDTO dto)throws Exception {
		CsAlert alert = alertDao.findAlertById(dto.getIdAlert());
		return alert;
	}
	
	@Override
	public void updateLettoById(IterDTO dto)throws Exception {
		alertDao.setAlertLetto(dto.getIdAlert(), dto.getUserId());
	}

	@Override
	public void updatePulisciLista(IterDTO dto)throws Exception {
		alertDao.setAlertPulisciLista(dto.getIdAlertList());
	}

	@Override
	public void updateLeggiAll(IterDTO dto)throws Exception {
		alertDao.setAlertLeggiAll(dto.getIdAlertList(), dto.getUserId());
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<CsAlert> findAlertByIdCasoTipo(IterDTO dto)throws Exception {
		return alertDao.findAlertByIdCasoTipo(dto.getCsACaso().getId(), dto.getTipo());
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public boolean validaInserimentoNuovoAlert(BaseDTO dto) throws Exception{
		
		String tipo = (String)dto.getObj();
		Long casoId = (Long) dto.getObj2();
		Long opeId = (Long) dto.getObj3();
		
		boolean inserisco = false;
		
		//Verifico che il caso non sia in stato CHIUSO
		boolean validaIter = true;
		if(casoId!=null) {
			CsItStep it = iterDao.getLastIterStepByCaso(casoId);
			validaIter = it!=null && it.getCsCfgItStato().getId() != DataModelCostanti.IterStatoInfo.CHIUSO;
		}
		
		//Cerco se è già presente un alert NON LETTO, altrimenti inserisco.
		List<CsAlert> listaAlert = alertDao.findAlertVisibiliByIdCasoTipoOpeTo(casoId, tipo, opeId);
	    /*Inserisco solo se tutti gli alert sono letti*/
		boolean tuttiLetti = true; 
	    //Se ci sono degli alert vecchi li rendo non visibili
		for(CsAlert al : listaAlert){
	    	if(al.getLetto()){
		    	al.setDtMod(new Date());
		    	al.setVisibile(false);
		    	alertDao.updateAlert(al);
	    	}else tuttiLetti = false;
	    }
		    
		inserisco = tuttiLetti && validaIter;
		    
	    return inserisco;
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public void updateAlert(BaseDTO dto) {
		alertDao.updateAlert((CsAlert) dto.getObj());
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public CsOOrganizzazione findAlertOrganizzazioneDefault(BaseDTO bDto) throws Exception{
		CsAlertConfig aConfig = alertDao.getAlertConfigByTipo((String)bDto.getObj());
		if(aConfig != null && aConfig.getCsOOrganizzazioneDefault() != null)
			return aConfig.getCsOOrganizzazioneDefault();
		else return null;
	}

	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<CsAlert> findAlertEmail(BaseDTO bDto) {
		return alertDao.findAlertEmail();
	}

	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public boolean isRicEmailAttiva(BaseDTO bDto) {
		
		Long ops = (Long)bDto.getObj();
		String tipoCod = (String)bDto.getObj3();
		try {
			
			if(ops!=null){
				CsOOpsettoreAlertConfig c = alertDao.isRicezioneAttivaOpSettore(ops, tipoCod);
				return c!=null ? c.isFlgEmail() : false;
			}
			
		} catch (Exception e) {
			logger.error("isRicEmailAttiva :"+e.getMessage(),e);
		}
	
		return false;
	}

	private List<String> loadTipoNotificaAttiva(Long operatoreSettoreId, HashMap<String, String> permessiGruppoSettore){
		List<String> listaTipo = new LinkedList<String>();
		List<String> config = alertDao.loadTipoNotificaAttiva(operatoreSettoreId);
		if(permessiGruppoSettore != null) {
			for (String it : permessiGruppoSettore.values()) {
				int idx = it.lastIndexOf("@-@");
				String permesso = it.substring(idx+3);
				if( permesso.startsWith(PermessiNotifiche.VISUALIZZA_NOTIFICHE_TIPO) ){
					String tipo = permesso.replace(PermessiNotifiche.VISUALIZZA_NOTIFICHE_TIPO, "");
					if(config.contains(tipo)) 
						listaTipo.add( "'" + tipo + "'" );
				}
			}
		}
		return listaTipo;
	}

	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public void aggiornaAlertPAI(CeTBaseObject cet) throws Exception{
		BaseDTO dto = new BaseDTO();
		dto.setEnteId(cet.getEnteId());
		dto.setSessionId(cet.getSessionId());
		
		dto.setObj(TipiAlertCod.PAI);
		CsOOrganizzazione orgFrom = findAlertOrganizzazioneDefault(dto);
		
		List<CsDPai> listaPai = diarioDao.findPaiAperti();
		if(listaPai.size()>0 ) loggerTimertask.info(" __ Trovati "+listaPai.size()+" PAI aperti.");
		for(CsDPai p : listaPai){
			CsACaso caso = p.getCsDDiario().getCsACaso();
			String casoId = caso!=null ? caso.getId().toString() : "";
			
			try{
			
				CsPaiMastSogg beneficiarioRif = p.getBeneficiarioRiferimento();
				
				CsOSettore settTo = null;
				CsOOrganizzazione orgTo = null;
				CsOOperatoreSettore opeTo = null;
				String nome = null;
				String cf = null;
				
				if(caso!=null){
					dto.setObj(caso.getId());
					opeTo = findDestinatarioAlertCaso(dto);
				}else
					opeTo = p.getCsDDiario().getCsOOperatoreSettore();
				
				if(caso!=null){
					nome = caso.getCsASoggetto().getCsAAnagrafica().getDenominazione();
					cf = caso.getCsASoggetto().getCsAAnagrafica().getCf();
				}else if (beneficiarioRif!=null){
					nome = beneficiarioRif.getDenominazione();
					cf = beneficiarioRif.getCf();
				}
				//Notifico al settore, solo se non trovo un operatore responsabile!
				if(opeTo==null) settTo = opeTo!=null ? opeTo.getCsOSettore() : null;
				
				/**Gli alert sono riferiti al responsabile del caso:il sistema legge tutti i pai non chiusi
					-se data odierna > data chiusura prevista: il sistema registra un alert sul superamento della data chiusura prevista
					-altrimenti
						- se il pai ha data prossimo monitoraggio (calcolata da data ultima + verifica ogni) minore di data chiusura
							- se data odierna >= data prossimo monitoraggio - 1 giorno: il sistema invia un alert "Monitoraggio degli obiettivi necessario per <tipo pai> del <nome e cognome soggetto>"
						- altrimenti se data chiusura prevista < data odierna + 7 gg: il sistema registra un alert che avverte dell'avvicinarsi della data chiusura progetto*/
				
				ArrayList<String> msg= new ArrayList<String>();
				Date today = new Date();
				Calendar dataAlert7GG = Calendar.getInstance();
				dataAlert7GG.add(Calendar.DAY_OF_MONTH, 7);
				if(p.getDataChiusuraPrevista()!=null && today.after(p.getDataChiusuraPrevista())){
					msg.add("Superamento della data chiusura prevista per progetto PAI");
				}else{
					//Calcolo data prossimo monitoraggio (calcolata da data ultima + verifica ogni) 
					Calendar dataProssimoMonitoraggio = null;
					if(p.getVerificaOgni()!=null && p.getVerificaOgni().intValue()>0 && p.getDataMonitoraggio()!=null){
						dataProssimoMonitoraggio = Calendar.getInstance();
						dataProssimoMonitoraggio.setTime(p.getDataMonitoraggio());
						if(p.getVerificaUnitaMisura().equalsIgnoreCase(DataModelCostanti.Pai.PERIODO_TEMPORALE.GIORNI.getCodice()))
							dataProssimoMonitoraggio.add(Calendar.DAY_OF_MONTH, p.getVerificaOgni().intValue());
						else if(p.getVerificaUnitaMisura().equalsIgnoreCase(DataModelCostanti.Pai.PERIODO_TEMPORALE.SETTIMANE.getCodice()))
							dataProssimoMonitoraggio.add(Calendar.DAY_OF_MONTH, 7*p.getVerificaOgni().intValue());
						else if(p.getVerificaUnitaMisura().equalsIgnoreCase(DataModelCostanti.Pai.PERIODO_TEMPORALE.MESI.getCodice()))
							dataProssimoMonitoraggio.add(Calendar.MONTH, p.getVerificaOgni().intValue());
						else if(p.getVerificaUnitaMisura().equalsIgnoreCase(DataModelCostanti.Pai.PERIODO_TEMPORALE.ANNI.getCodice()))
								dataProssimoMonitoraggio.add(Calendar.YEAR, p.getVerificaOgni().intValue());
					}
					if(dataProssimoMonitoraggio!=null && 
							(p.getDataChiusuraPrevista()==null || dataProssimoMonitoraggio.getTime().before(p.getDataChiusuraPrevista()))){
						Calendar dataAlertMonitoraggio = Calendar.getInstance();
						dataAlertMonitoraggio.setTime(dataProssimoMonitoraggio.getTime());
						dataAlertMonitoraggio.add(Calendar.DAY_OF_MONTH, -1);
						if(!today.before(dataAlertMonitoraggio.getTime()))
							msg.add("Monitoraggio degli obiettivi necessario");
					}
					if(p.getDataChiusuraPrevista()!=null && p.getDataChiusuraPrevista().before(dataAlert7GG.getTime()))
						msg.add("Chiusura del progetto PAI prevista tra meno di una settimana");
				}
				
				if(!msg.isEmpty() && (opeTo!=null || settTo!=null || orgTo!=null)){
					dto.setObj(TipiAlertCod.PAI);
					dto.setObj2(caso!=null ? caso.getId() : null);
					dto.setObj3(opeTo.getId());
					boolean inserisco = alertSessionBean.validaInserimentoNuovoAlert(dto);
					if(inserisco){
						//nuovo alert
						String descrizione = "";
						for(String s: msg ) descrizione += s+ "<br/>";
						descrizione+="<br/>";
						
						if(caso!=null)
							descrizione+= "Caso: "+nome+" ("+cf+") - Identificativo:"+caso.getIdentificativo()+" <br/>";
						
						descrizione+= "Progetto PAI: "+p.getCsTbTipoPai().getDescrizione()+ " ID: "+p.getDiarioId()+"<br/>";
						descrizione+= "Data chiusura prevista: "+ (p.getDataChiusuraPrevista()!=null ?  ddMMyyyy.format(p.getDataChiusuraPrevista()) : "") + "<br/>";
						descrizione+= "Data ultimo monitoraggio: "+ (p.getDataMonitoraggio()!=null ? ddMMyyyy.format(p.getDataMonitoraggio()) : "") + "<br/>";
						descrizione+= "Periodo monitoraggio: "+ p.getVerificaOgni() + " " + p.getVerificaUnitaMisura() + "<br/>";
						
						String titDescrizione =  "Promemoria PAI num."+p.getDiarioId()+" "+  (caso!=null ? " caso " : "")+ nome + ": "+msg;
							
						loggerTimertask.info(cet.getEnteId() + " __ Aggiungo Alert per aggiornamento PAI: [paiId:"+p.getDiarioId()+"][casoId:" + casoId +"]");
						addAlert(dto.getEnteId(), TipiAlertCod.PAI, caso, titDescrizione, descrizione, null, null, null, orgFrom, opeTo, settTo, orgTo);
					}else
						loggerTimertask.info(cet.getEnteId() + " __ Sospeso Inserimento Alert per aggiornamento PAI: [paiId:"+p.getDiarioId()+"][casoId:" + casoId+"]");
						
				}
			}catch(Exception e){
				loggerTimertask.error("aggiornaAlertPAI [paiId:"+p.getDiarioId()+"][casoId:" + casoId +"]" + e.getMessage(), e);
				throw new CarSocialeServiceException(e);
			}
		}
	}
	
	private void addAlert(String enteId, String tipo, CsACaso caso, String oggetto, String descrizione, String url, 
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
		
		alertSessionBean.addAlert(newAlert);
  }
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public CsOOperatoreSettore findDestinatarioAlertCaso(BaseDTO dto){
		Long casoId = (Long) dto.getObj();
		CsOOperatoreSettore opeTo = casoDao.findOpSettoreResponsabileCaso(casoId);
				
		//Responsabile altrimenti --> creatore
		if(opeTo == null) {		
			CsItStep itStep = iterDao.getFirstIterStepByCaso(casoId);
			if(itStep!=null)
				opeTo = itStep.getCsDDiario().getCsOOperatoreSettore();
		}
		
		return opeTo;
	}
}
