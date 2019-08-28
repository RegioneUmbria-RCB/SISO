package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.persist.CsAlert;
import it.webred.cs.data.model.CsAlertBASIC;
import it.webred.cs.data.model.CsAlertConfig;
import it.webred.cs.data.model.CsOOpsettoreAlertConfig;
import it.webred.cs.data.model.CsTbTipoAlert;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

/**
 * @author alessandro.feriani
 *
 */
@Named
public class AlertDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public void createAlert(it.webred.cs.data.model.persist.CsAlert newAlert) throws Exception {
		newAlert.setDtIns(new Date());
		em.persist(newAlert);
	}
	
	public void updateAlert(CsAlert alert) {
		alert.setDtMod(new Date());
		em.merge(alert);
	}

	public CsAlert findAlertById(long idAlert) throws Exception {
		CsAlert alert= em.find(CsAlert.class,  idAlert);
		return alert;
	}
	
	public CsAlertBASIC findAlertBASICById(long idAlert) throws Exception {
		CsAlertBASIC alert= em.find(CsAlertBASIC.class,  idAlert);
		return alert;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsAlert> findAlertByIdCasoTipo(long idCaso, String tipo) throws Exception {
		try{
			Query q = em.createNamedQuery("CsAlert.getAlertByIdCasoTipo")
					.setParameter("idCaso", idCaso)
					.setParameter("tipo", tipo);
			return q.getResultList();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	
	public List<CsAlert> findAlertVisibiliByIdCasoTipoOpeTo(Long idCaso, String tipo, Long opeTo) {
		
		try{
			Query q = em.createNamedQuery("CsAlert.getAlertVisibiliByIdCasoTipoOpeTo");
			q.setParameter("idCaso", idCaso);
			q.setParameter("tipo", tipo);
			q.setParameter("opSettoreTo", opeTo);
			return q.getResultList();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<CsAlertBASIC> getAlerts(String query) throws Exception {
		try{
			Query q = em.createQuery(query);
			List<CsAlertBASIC> retList = q.getResultList();
			return retList;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	
	public void setAlertLetto(Long idAlert, String username) throws Exception {
			CsAlert alert = em.find(CsAlert.class, idAlert);
			alert.setLetto(true);
			alert.setLettoUser(username);
			em.merge(alert);
	}

	public void setAlertVisibile(Long idAlert) throws Exception {
			CsAlert alert = em.find(CsAlert.class, idAlert);
			alert.setVisibile(false);
			em.merge(alert);
	}

	public void setAlertPulisciLista(List<Long> idAlertList) throws Exception {
			for(Long id : idAlertList )
				setAlertVisibile(id);		
	}

	public void setAlertLeggiAll(List<Long> idAlertList, String username) throws Exception {
			for(Long id : idAlertList )
				setAlertLetto(id, username);	
	}
	
	@SuppressWarnings("unchecked")
	public CsAlertConfig getAlertConfigByTipo(String tipo) throws Exception {
		Query q = em.createNamedQuery("CsAlertConfig.getAlertConfigByTipo")
				.setParameter("tipo", tipo);
		List<CsAlertConfig> lista = q.getResultList();
		if(lista.size() > 0)
			return lista.get(0);
		return null;
	}

	public List<CsAlert> findAlertEmail() {
		Query q = em.createNamedQuery("CsAlert.findAlertEmail");
		List<CsAlert> lista = q.getResultList();
		return lista;
	}

	public CsOOpsettoreAlertConfig isRicezioneAttivaOpSettore(Long opSettoreId, String tipoCod) {
		Query q = em.createNamedQuery("CsOOpsettoreAlertConfig.find");
		q.setParameter("opSettoreId", opSettoreId);
		q.setParameter("tipoCod", tipoCod);
		List<CsOOpsettoreAlertConfig> lista = q.getResultList();
		if(!lista.isEmpty()) return lista.get(0);
		else logger.warn("isRicezioneAttivaOpSettore  opSettoreId["+opSettoreId+"], tipoCod["+opSettoreId+"] - NON DEFINITO");
		return null;
	}

}
