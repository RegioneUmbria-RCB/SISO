package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.persist.CsAlert;
import it.webred.cs.data.model.CsAlertBASIC;
import it.webred.cs.data.model.CsAlertConfig;
import it.webred.cs.data.model.CsOOpsettoreAlertConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

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
		List<CsAlert> lst = new ArrayList<CsAlert>();
		if(idCaso!=null && !StringUtils.isBlank(tipo) && opeTo!=null){
			try{
				Query q = em.createNamedQuery("CsAlert.getAlertVisibiliByIdCasoTipoOpeTo");
				q.setParameter("idCaso", idCaso);
				q.setParameter("tipo", tipo);
				q.setParameter("opSettoreTo", opeTo);
				lst = q.getResultList();
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
		}
		return lst;
	}
	
	public List<CsAlertBASIC> getAlerts(Long idOp, Long idSettore, Long idOrganizzazione, List<String> listaTipo, Boolean visibile) throws Exception {
		String select = "SELECT a FROM CsAlertBASIC a "
				+ " inner join fetch a.tipoAlert"
				+ " left join fetch a.csOpSettore2"
				+ " left join fetch a.csOOrganizzazione2"
				+ " left join fetch a.csOSettore2 ";
		String where = " WHERE 1 = 1 ";
		
		if(idOp!=null || idSettore!=null || idOrganizzazione!=null){
			where+= " AND ( 1=0 ";
			
			if(idOrganizzazione != null) 
				where += " OR (a.csOOrganizzazione2Id = :idOrganizzazione )"; //AND a.csOpSettore2 is null 
			if(idSettore != null) 
				where += " OR (a.csOSettore2Id = :idSettore )"; //AND a.csOpSettore2 is null
			if(idOp != null) 
				where += " OR (a.csOpSettore2.id = :idOperatore )";
			
			where+= " ) ";
		}
		
		if (listaTipo != null && listaTipo.size() > 0){
			where = where + " AND  UPPER(a.tipo) IN ( ";
			
			for(int i=0; i<listaTipo.size();i++){
				String lt = listaTipo.get(i);
				where += lt.toUpperCase();
						
				if(i<listaTipo.size()-1) where+=", ";
			}
			
			where +=") ";
		}
		
		if(visibile!=null)
			where += "AND a.visibile = " +(visibile ? "1" : "0");
		
		select += where;
		
		String query = select + " ORDER BY a.tipo, a.id DESC";
		
		logger.debug("getAlerts SQL["+query+"]");
		logger.debug("getAlerts idOperatoreSettore["+idOp+"] idSettore["+idSettore+"] idOrganizzazione["+idOrganizzazione+"] tipi["+listaTipo+"]" );
		
		
		try{
			Query q = em.createQuery(query);
			
			if(idOrganizzazione != null) 
				q.setParameter("idOrganizzazione", idOrganizzazione);
			if(idSettore != null) 
				q.setParameter("idSettore", idSettore);
			if(idOp != null) 
				q.setParameter("idOperatore", idOp);
			
			List<CsAlertBASIC> retList = (List<CsAlertBASIC>)q.getResultList();
			logger.debug("getAlerts RES["+retList.size()+"]");
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
		Query q = em.createNamedQuery("CsAlertConfig.getAlertConfigByTipo");
		q.setParameter("tipo", tipo);
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

	public List<String> loadTipoNotificaAttiva(Long opSettoreId) {
		Query q = em.createNamedQuery("CsOOpsettoreAlertConfig.findByOpSettore");
		q.setParameter("opSettoreId", opSettoreId);
		List<String> lista = q.getResultList();
		logger.debug("loadTipoNotificaAttiva idOpSet["+opSettoreId+"] result["+lista+"]");
		return lista;
	}

}
