package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.AlertDAO;
import it.webred.cs.csa.ejb.dao.IterDAO;
import it.webred.cs.csa.ejb.dto.AlertDTO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsAlertBASIC;
import it.webred.cs.data.model.CsAlertConfig;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOpsettoreAlertConfig;
import it.webred.cs.data.model.persist.CsAlert;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class AccessTableAlertSessionBean extends CarSocialeBaseSessionBean implements AccessTableAlertSessionBeanRemote {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AlertDAO alertDao;
	
	@Autowired
	private IterDAO iterDao;

	@EJB
	public AccessTableCasoSessionBeanRemote casoSessionBean;
	
	@EJB
	public AccessTableSoggettoSessionBeanRemote soggettoSessionBean;
	
	@EJB
	public AccessTableOperatoreSessionBeanRemote operatoreSessionBean;
		
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public void addAlert(AlertDTO dto) throws Exception {
		
		CsAlert newAlert = new CsAlert();
		dto.fillAlertJPA(newAlert);
		
		newAlert.setVisibile(true);
		newAlert.setLetto(false);
		newAlert.setEmailInviata(false);
		
		if(newAlert.getCsOpSettore2()!=null || newAlert.getCsOSettore2()!=null || newAlert.getCsOOrganizzazione2()!=null){
			alertDao.createAlert(newAlert);
		}else
			logger.warn("Nessun destinatario specificato: impossibile inserire l'alert!");
	}
	
	@Override
	public void addAlertNuovoInserimentoToResponsabileCaso(BaseDTO dto) throws Exception {
		//OBJ1
		CsASoggettoLAZY s = null;
		if(dto.getObj() instanceof CsASoggettoLAZY)
			s = (CsASoggettoLAZY) dto.getObj();
		else
			s = soggettoSessionBean.getSoggettoByCF(dto);	
		
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
		
		CsACasoOpeTipoOpe ope = null;
		CsOOperatoreSettore opSettResponsabile = null;
		if(s!=null){
			dto.setObj(s.getCsACaso().getId());
		 	ope = casoSessionBean.findCasoOpeResponsabile(dto);
		 	if(ope!=null)
		 		opSettResponsabile = ope.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore();
		 	else{
		 		//Se non esiste il responsabile invio l'alert al creatore del caso
		 		BaseDTO c = new BaseDTO();
		 		c.setEnteId(dto.getEnteId());
				c.setUserId(dto.getUserId());
				c.setSessionId(dto.getSessionId());
				c.setObj(s.getCsACaso().getId());
		 		opSettResponsabile = casoSessionBean.findCreatoreCaso(dto);
		 	}
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
		adto.setOpSettoreTo(ope.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore());
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
		
	}

	@Override
	public List<CsAlertBASIC> getNotificas(IterDTO dto) throws Exception {
		
		/*OperatoreDTO opDto = new OperatoreDTO();
		opDto.setUserId(dto.getUserId());
		opDto.setEnteId(dto.getEnteId());
		opDto.setSessionId(dto.getSessionId());
		opDto.setIdOperatoreSettore(dto.getIdOpSettoreTo());
		CsOOperatoreSettore os = operatoreSessionBean.findOperatoreSettoreById(opDto);*/
		
		/**
		 * Al momento non usato - in precedenza ricercavo comunque per operatore_id,
		 * ora ricerco per operatore_settore in modo tale che l'elenco delle notifiche venga caricato al variare del settore selezionato
		 * Se necessario, ripristinare la situazione precedente.*/
		
		String query = this.queryBuilder(dto.getIdOpSettoreTo(), dto.getIdSettTo(), dto.getIdOrgTo(), dto.getListaTipo());
		
		List<CsAlertBASIC> alert = alertDao.getAlerts(query);
      
		return alert;
		
	}
	

	protected String queryBuilder(Long idOp, Long idSettore, Long idOrganizzazione, List<String> listaTipo) {
		
		String select = "SELECT a FROM CsAlertBASIC a ";
		String where = " WHERE 1 = 1 ";
		
		if(idOp!=null || idSettore!=null || idOrganizzazione!=null){
			where+= " AND ( 1=0 ";
			
			if (idOrganizzazione != null) 
				where += " OR (a.csOOrganizzazione2Id = " + idOrganizzazione + " )"; //AND a.csOpSettore2 is null 
			if(idSettore != null) 
				where += " OR (a.csOSettore2Id = " + idSettore + " )"; //AND a.csOpSettore2 is null
			if(idOp != null) 
				where += " OR (a.csOpSettore2.id = " + idOp + ")";
			
			where+= " ) ";
		}
		
		
		if (listaTipo != null && listaTipo.size() > 0){
			where = where + " AND  UPPER(a.tipo) IN ( ";
			
			for(int i=0; i<listaTipo.size();i++){
				String lt = listaTipo.get(i);
				where += " UPPER("+lt+")";
						
				if(i<listaTipo.size()-1) where+=", ";
			}
			
			where +=") ";
		}
		select += where;
		
		select += " ORDER BY a.tipo, a.id DESC";
		
		return select;
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
	public List<CsAlert> findAlertVisibiliByIdCasoTipoOpeTo(IterDTO dto)throws Exception {
		return alertDao.findAlertVisibiliByIdCasoTipoOpeTo(dto.getCsACaso().getId(), dto.getTipo(), dto.getIdOpSettoreTo());
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
	public CsAlertConfig getAlertConfigByTipo(BaseDTO dto)throws Exception {
		return alertDao.getAlertConfigByTipo((String) dto.getObj());
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
	
	public boolean isRicNotificaAttiva(BaseDTO bDto) {
		
		Long ops = (Long)bDto.getObj();
		String tipoCod = (String)bDto.getObj3();
		try {
			
			if(ops!=null){
				CsOOpsettoreAlertConfig c = alertDao.isRicezioneAttivaOpSettore(ops, tipoCod);
				return c!=null ? c.isFlgNotifica() : false;
			}
			
		} catch (Exception e) {
			logger.error("isRicNotificaAttiva :"+e.getMessage(),e);
		}
	
		return false;
	}




}
