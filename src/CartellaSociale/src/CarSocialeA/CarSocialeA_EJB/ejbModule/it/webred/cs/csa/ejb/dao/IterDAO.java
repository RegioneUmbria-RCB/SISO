package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.CsCfgAttr;
import it.webred.cs.data.model.CsCfgAttrOption;
import it.webred.cs.data.model.CsCfgItStato;
import it.webred.cs.data.model.CsCfgItStatoAttr;
import it.webred.cs.data.model.CsCfgItStatoLAZY;
import it.webred.cs.data.model.CsCfgItTransizione;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.data.model.CsItStepAttrValueBASIC;
import it.webred.cs.data.model.CsItStepLAZY;

import java.io.Serializable;
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
	
	public List<CsCfgItStato> getListaIterStati(){
		Query q = em.createNamedQuery("CsCfgItStato.findAll");
		return (List<CsCfgItStato>)q.getResultList();
	}

	protected void loadRelatedEntities( CsCfgItStato itStato ) throws Exception
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
	
	protected void loadRelatedLAZYEntities( CsItStepLAZY itStep ) throws Exception
	{
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
	
	
	protected void loadRelatedEntities( CsItStep itStep ) throws Exception
	{
		if( itStep != null )
		{
			itStep.getCsItStepAttrValues().size();
			loadRelatedEntities(itStep.getCsCfgItStato());
		}
	}
	
	public List<CsCfgAttrOption> getCfgAttrOptionByCfgAttr(long idAttr)  throws Exception {
		Query q = em.createNamedQuery("CsCfgAttrOption.findAttrOptionByCfgAttr").setParameter("idCfgAttr", idAttr);
		return q.getResultList();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsItStep> getIterStepListByCaso(long idCaso)  throws Exception {
		Query q = em.createNamedQuery("CsItStep.getIterStepListByCaso").setParameter("idCaso", idCaso);
		List retList = q.getResultList();
		if( retList != null )
			for (Object itStep : retList)
				loadRelatedEntities((CsItStep)itStep);
		return retList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsItStepLAZY> getIterStepLAZYListByCaso(long idCaso)  throws Exception {
		Query q = em.createNamedQuery("CsItStepLAZY.getIterStepLAZYListByCaso").setParameter("csACasoId", idCaso);
		List retList = q.getResultList();

		if( retList != null )
			for (Object itStep : retList)
				loadRelatedLAZYEntities((CsItStepLAZY)itStep);
		
		return retList;
	}
	
	
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
	
	@SuppressWarnings("rawtypes")
	public CsItStep getLastIterStepByCaso(long idCaso) throws Exception {
		Query q = em.createNamedQuery("CsItStep.getLastIterStepByCaso");
		q.setParameter("idCaso", idCaso);
		CsItStep itStep = null;
		try{
			itStep = (CsItStep)q.getSingleResult();
			loadRelatedEntities(itStep);
			return itStep;
			
		}catch(NoResultException nre){
			logger.warn("Nessun IterStep per "+idCaso);
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

	public CsCfgItStato findStatoById(long idStato) throws Exception {
		CsCfgItStato itStato = em.find(CsCfgItStato.class, idStato);
		loadRelatedEntities(itStato);
		return itStato;
	}
	
	public CsCfgAttr findStatoAttrById(long idStatoAttr) throws Exception{
		CsCfgAttr itStatoAttr = em.find(CsCfgAttr.class, idStatoAttr);
		return itStatoAttr;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsCfgItTransizione> findTransizionesByStatoRuolo(Long idStato, String opRuolo) throws Exception {
		Query q = em.createNamedQuery("CsCfgItTransizione.findTransizionesByStatoRuolo").setParameter("idStato", idStato).setParameter("nomeRuolo", opRuolo);
		List results = q.getResultList();
		return results;
	}
	
}
