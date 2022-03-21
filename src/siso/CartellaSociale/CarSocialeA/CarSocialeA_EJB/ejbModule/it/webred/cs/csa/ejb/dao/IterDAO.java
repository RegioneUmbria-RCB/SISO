package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.model.CsCfgAttr;
import it.webred.cs.data.model.CsCfgAttrOption;
import it.webred.cs.data.model.CsCfgItStato;
import it.webred.cs.data.model.CsCfgItStatoAttr;
import it.webred.cs.data.model.CsCfgItTransizione;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.data.model.CsItStepAttrValueBASIC;
import it.webred.cs.data.model.CsItStepLAZY;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * @author Andrea
 *
 */
@Named
public class IterDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;


	//SISO-1297
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	protected void loadRelatedLAZYEntities( CsItStepLAZY itStep ){
		if( itStep != null )
		{
			
			Query q = em.createNamedQuery("CsItStepAttrValueBASIC.findByCsItStepId");
			q.setParameter("csItStepId", itStep.getId());
			
			List<CsItStepAttrValueBASIC> csItStepAttrValues =  q.getResultList();
			
			itStep.setCsItStepAttrValues(csItStepAttrValues);
			//itStep.getCsItStepAttrValues().size();
			//loadRelatedEntities(itStep.getCsCfgItStato());
		}
	}
	
	public CsCfgItStato findStatoById(long idStato) throws Exception {
		CsCfgItStato itStato = em.find(CsCfgItStato.class, idStato);
		loadRelatedEntities(itStato);
		return itStato;
	}
	
	public void loadRelatedEntities( CsCfgItStato itStato ) throws Exception
	{
		if( itStato != null )
		{
			itStato.getCsCfgItStatoAttrs().size();
			for (CsCfgItStatoAttr itAttrs : itStato.getCsCfgItStatoAttrs()){
				CsCfgAttr itAttr = itAttrs.getCsCfgAttr();
				
				if( itAttr.getCsCfgAttrOptions() != null ) 
					itAttr.setCsCfgAttrOptions(getCfgAttrOptionByCfgAttr(itAttr.getId()));
			}
		}
	}
	
	protected void loadRelatedEntities(CsItStep itStep) throws Exception
	{
		if( itStep != null ){
			itStep.getCsItStepAttrValues().size();
			loadRelatedEntities(itStep.getCsCfgItStato());
		}
	}
	
	private List<CsCfgAttrOption> getCfgAttrOptionByCfgAttr(long idAttr)  throws Exception {
		Query q = em.createNamedQuery("CsCfgAttrOption.findAttrOptionByCfgAttr");
		q.setParameter("idCfgAttr", idAttr);
		return q.getResultList();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsItStepLAZY> getIterStepLAZYListByCaso(long idCaso)  throws Exception {
		Query q = em.createNamedQuery("CsItStepLAZY.getIterStepLAZYListByCaso");
		q.setParameter("csACasoId", idCaso);
		List retList = q.getResultList();

		if( retList != null )
			for (Object itStep : retList)
				loadRelatedLAZYEntities((CsItStepLAZY)itStep);
		
		return retList;
	}
	
	//SISO-1297
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	@SuppressWarnings("rawtypes")
	public CsItStepLAZY getLastIterStepLAZYByCaso(long idCaso) throws Exception {
		Query q = em.createNamedQuery("CsItStepLAZY.getLastIterStepLAZYByCaso");
		q.setParameter("idCaso", idCaso);
		CsItStepLAZY itStep = null;
		try{
			itStep = (CsItStepLAZY)q.getSingleResult();
			
			loadRelatedLAZYEntities(itStep);
			return itStep;
			
		}catch(NoResultException nre){
			logger.warn("Nessun IterStep per "+idCaso);
		}

		return null;
	}
    
	public CsItStepLAZY getLastIterStepLAZYByTipoCaso(long idCaso, long idStato, boolean loadRelated) {
		CsItStepLAZY itStep = null;
		try{
			Query q = em.createNamedQuery("CsItStepLAZY.getLastIterStepLAZYByTipoCaso");
			q.setParameter("idCaso", idCaso);
			q.setParameter("idStato", idStato);
			itStep = (CsItStepLAZY)q.getSingleResult();
			
			if(loadRelated)
				loadRelatedLAZYEntities(itStep);
			return itStep;
			
		}catch(NoResultException nre){
			logger.warn("Nessun IterStep per "+idCaso);
		}catch(Throwable e){
			logger.error(e.getMessage());
		}

		return null;
	}
    
   
	@SuppressWarnings("rawtypes")
	public CsItStep getFirstIterStepByCaso(long idCaso) {
		Query q = em.createNamedQuery("CsItStep.getFirstIterStepByCaso");
		q.setParameter("idCaso", idCaso);
		CsItStep itStep = null;
		try{
			itStep = (CsItStep)q.getSingleResult();
			//loadRelatedEntities(itStep);
			return itStep;
			
		}catch(NoResultException nre){
			logger.warn("Nessun IterStep per "+idCaso);
		}catch(Exception e){
			logger.error("getFirstIterStepByCaso "+idCaso + " "+e.getMessage(), e);
		}

		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public CsItStep getLastIterStepByCaso(long idCaso) {
		Query q = em.createNamedQuery("CsItStep.getLastIterStepByCaso");
		q.setParameter("idCaso", idCaso);
		CsItStep itStep = null;
		try{
			itStep = (CsItStep)q.getSingleResult();
			loadRelatedEntities(itStep);
			return itStep;
			
		}catch(NoResultException nre){
			logger.warn("Nessun IterStep per "+idCaso);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}

		return null;
	}
	
	public boolean addIterStep(CsItStep iterStep) throws Exception{
		try{
			if (em.contains(iterStep))
				em.merge(iterStep);
			else
			  em.persist(iterStep);
		}catch(Exception e){
			logger.error("addIterStep",e);
			return false;
		}
		
		return true;
	}	
}
