package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.AlertDAO;
import it.webred.cs.csa.ejb.dto.AlertDTO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsAlertBASIC;
import it.webred.cs.data.model.CsAlertConfig;
import it.webred.cs.data.model.CsOOpsettoreAlertConfig;
import it.webred.cs.data.model.persist.CsAlert;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class AccessTableAlertSessionBean extends CarSocialeBaseSessionBean implements AccessTableAlertSessionBeanRemote {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AlertDAO alertDao;

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
	public void addAlertResponsabileCaso(BaseDTO dto) throws Exception {
		String cf = (String)dto.getObj();
		Long idOrg = (Long)dto.getObj2();
		String tipoAlert = (String)dto.getObj3();
		
		CsASoggettoLAZY s = soggettoSessionBean.getSoggettoByCF(dto);	
		
		CsACasoOpeTipoOpe ope = null;
		if(s!=null){
			dto.setObj(s.getCsACaso().getId());
		 	ope = casoSessionBean.findCasoOpeResponsabile(dto);
		}
		
		
		/*Inserisco alert solo se: 
		 * 		-   l'operatore che ha modificato la scheda UDC è diverso dal responsabile della CARTELLA SOCIALE
		 * 		- 	l'organizzazione della scheda è la stessa dell'operatore attualmente responsabile
		 * */
		if(ope!=null && ope.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOSettore().getCsOOrganizzazione().getId().longValue()==idOrg.longValue() 
					 && !ope.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOOperatore().getUsername().equals(dto.getUserId())){
			
			
			AlertDTO adto = new AlertDTO();
			adto.setEnteId(dto.getEnteId());
			adto.setUserId(dto.getUserId());
			adto.setSessionId(dto.getSessionId());
			
			adto.setCaso(s.getCsACaso());
			adto.setOpSettoreTo(ope.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore());
			adto.setTipo(tipoAlert);
			
			String casoDenom = s.getCsAAnagrafica().getCognome()+" "+s.getCsAAnagrafica().getNome()+"("+s.getCsAAnagrafica().getCf()+")";
			
			if(tipoAlert.equalsIgnoreCase(DataModelCostanti.TipiAlertCod.EROGAZIONE)){
				adto.setTitolo("Notifica  caso "+casoDenom+": nuova erogazione");
				adto.setDescrizione("Inserita una nuova erogazione per il caso "+casoDenom);
			}else if(tipoAlert.equalsIgnoreCase(DataModelCostanti.TipiAlertCod.UDC)){
				adto.setTitolo("Notifica  caso "+casoDenom+": nuova scheda ");
				adto.setDescrizione("Inserita una nuova scheda per il caso "+casoDenom);
			}
			
			this.addAlert(adto);
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
		return alertDao.findAlertByIdCasoTipo(dto.getIdCaso(), dto.getTipo());
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<CsAlert> findAlertVisibiliByIdCasoTipoOpeTo(IterDTO dto)throws Exception {
		return alertDao.findAlertVisibiliByIdCasoTipoOpeTo(dto.getIdCaso(), dto.getTipo(), dto.getIdOpSettoreTo());
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
