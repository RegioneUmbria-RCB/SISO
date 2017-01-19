package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.AlertDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsAlertBASIC;
import it.webred.cs.data.model.CsAlertConfig;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
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
	public AccessTableOperatoreSessionBeanRemote operatoreSessionBean;
		
	@Override
	public void addAlert(IterDTO dto) throws Exception {
		
		CsACaso caso = casoSessionBean.findCasoById(dto);
		CsAlert newAlert = new CsAlert();
		
		OperatoreDTO opDto = new OperatoreDTO();
		opDto.setUserId(dto.getUserId());
		opDto.setEnteId(dto.getEnteId());
		opDto.setSessionId(dto.getSessionId());
		opDto.setUsername(dto.getNomeOperatore());
		
		if(dto.getNomeOperatore() != null) {
			CsOOperatore operatore = operatoreSessionBean.findOperatoreByUsername(opDto);
			newAlert.setCsOOperatore1(operatore);
		}
		
		if(dto.getIdSettore() != null) {
			opDto.setIdSettore(dto.getIdSettore());
			CsOSettore settore = operatoreSessionBean.findSettoreById(opDto);
			newAlert.setCsOSettore1(settore);
			newAlert.setCsOOrganizzazione1(settore.getCsOOrganizzazione());
		} else if (dto.getIdOrganizzazione() != null) {
			opDto.setIdOrganizzazione(dto.getIdOrganizzazione());
			CsOOrganizzazione organizzazione = operatoreSessionBean.findOrganizzazioneById(opDto);
			newAlert.setCsOOrganizzazione1(organizzazione);
		}
		
		newAlert.setCsACaso(caso);
		newAlert.setTipo(dto.getTipo());
		newAlert.setLabelTipo(dto.getLabelTipo());
		newAlert.setVisibile(true);
		newAlert.setLetto(false);
		
		opDto.setIdOperatore(dto.getIdOpTo());
		opDto.setIdSettore(dto.getIdSettTo());
		opDto.setIdOrganizzazione(dto.getIdOrgTo());
		if(dto.getIdOpTo() != null && dto.getIdOpTo() > 0)
		{
			CsOOperatore operatore = operatoreSessionBean.findOperatoreById(opDto);
			newAlert.setCsOOperatore2(operatore);
		}
		
		if(dto.getIdSettTo() != null && dto.getIdSettTo() > 0)
		{
			CsOSettore settoreTo = operatoreSessionBean.findSettoreById(opDto);
			newAlert.setCsOSettore2(settoreTo);
		}
		
		if(dto.getIdOrgTo() != null && dto.getIdOrgTo() > 0)
		{
			CsOOrganizzazione ente = operatoreSessionBean.findOrganizzazioneById(opDto);
			newAlert.setCsOOrganizzazione2(ente);
		}
		
		newAlert.setDescrizione(dto.getDescrizione());
		newAlert.setUrl(dto.getUrl());
		newAlert.setTitoloDescrizione(dto.getTitoloDescrizione());
		
		alertDao.createAlert(newAlert);
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public void addAlert(BaseDTO dto) throws Exception {
		
		CsAlert newAlert = (CsAlert)dto.getObj();		
		alertDao.createAlert(newAlert);
	}

	@Override
	public List<CsAlertBASIC> getNotificas(IterDTO dto) throws Exception {
		
		OperatoreDTO opDto = new OperatoreDTO();
		opDto.setUserId(dto.getUserId());
		opDto.setEnteId(dto.getEnteId());
		opDto.setSessionId(dto.getSessionId());
		opDto.setUsername(dto.getNomeOperatore());
		CsOOperatoreBASIC operatore = operatoreSessionBean.findOperatoreBASICByUsername(opDto);
										
		String query = queryBuilder(operatore.getId(), dto.getIdSettore(), dto.getIdOrganizzazione(), dto.getListaTipo());
		 
		List<CsAlertBASIC> alert = alertDao.getAlerts(query);

		return alert;
	}
	
	
	

	protected String queryBuilder(Long idOp, Long idSettore, Long idOrganizzazione, List<String> listaTipo) {
		
		String where = " WHERE 1 = 1 ";
		String select = "SELECT a FROM CsAlertBASIC a INNER join fetch a.csOOrganizzazione2 b INNER join fetch a.csOSettore2 c  INNER join fetch a.csOOperatore2 d ";

	
		if (idOrganizzazione != null) {
			where += " AND (a.csOOrganizzazione2.id = " + idOrganizzazione + " OR a.csOOperatore2.id = " + idOp + ")";
		} else if(idSettore != null) {
			where += " AND (a.csOSettore2.id = " + idSettore + " OR a.csOOperatore2.id = " + idOp + ")";
		} else {
			where += " AND (a.csOOperatore2.id = " + idOp + ")";
		}

		if (listaTipo != null && listaTipo.size() > 0){
			where = where + " AND  UPPER(a.tipo) IN ( ";
			
			for(int i=0; i<listaTipo.size();i++){
				String lt = listaTipo.get(i);
				where += " UPPER("+lt+")";
						
				if(i<listaTipo.size()-1)
					where+=", ";
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
		alertDao.setAlertLetto(dto.getIdAlert());
	}

	@Override
	public void updatePulisciLista(IterDTO dto)throws Exception {
		alertDao.setAlertPulisciLista(dto.getIdAlertList());
	}

	@Override
	public void updateLeggiAll(IterDTO dto)throws Exception {
		alertDao.setAlertLeggiAll(dto.getIdAlertList());
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
	public void updateAlert(BaseDTO dto) {
		alertDao.updateAlert((CsAlert) dto.getObj());
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public CsAlertConfig getAlertConfigByTipo(BaseDTO dto)throws Exception {
		return alertDao.getAlertConfigByTipo((String) dto.getObj());
	}
}
