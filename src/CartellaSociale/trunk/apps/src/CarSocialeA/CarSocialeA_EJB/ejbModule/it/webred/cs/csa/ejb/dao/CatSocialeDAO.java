package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsRelCatsocTipoInter;
import it.webred.cs.data.model.CsRelCatsocTipoInterPK;
import it.webred.cs.data.model.CsRelSettCatsocEsclusiva;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.CsRelSettCsocTipoInterPK;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.cs.data.model.CsRelSettoreCatsocPK;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

/**
 * @author Andrea
 *
 */
@Named
public class CatSocialeDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public CsCCategoriaSociale getCategoriaSocialeById(Long id) {

		CsCCategoriaSociale cs = em.find(CsCCategoriaSociale.class, id);
		return cs;
		
	}
	
	public List<CsCCategoriaSociale> getCategorieSocialiByDesc(String desc){
	try{
			
			Query q = em.createNamedQuery("CsCCategoriaSociale.findAbilByDesc");
			q.setParameter("desc", "%"+desc.toUpperCase()+"%");
			return q.getResultList();
			
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsCCategoriaSociale> getCategorieSociali() {
		
		try{
			
			Query q = em.createNamedQuery("CsCCategoriaSociale.findAllAbil");
			return q.getResultList();
			
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsCCategoriaSociale> getCategorieSocialiAll() {
		
		try{
			
			Query q = em.createNamedQuery("CsCCategoriaSociale.findAll");
			return q.getResultList();
			
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public void salvaCategoriaSociale(CsCCategoriaSociale catSoc) {
		em.persist(catSoc);
	}
	
	public void updateCategoriaSociale(CsCCategoriaSociale catSoc) {
		em.merge(catSoc);
	}
	
	public void eliminaCategoriaSociale(Long id) {
		Query q = em.createNamedQuery("CsCCategoriaSociale.eliminaCategoriaSocialeById");
		q.setParameter("id", id);
		q.executeUpdate();
	}
	
	public CsRelCatsocTipoInter getRelCatsocTipoInterById(CsRelCatsocTipoInterPK id) {
		CsRelCatsocTipoInter cs = em.find(CsRelCatsocTipoInter.class, id);
		return cs;	
	}
	
	@SuppressWarnings("unchecked")
	public List<CsRelCatsocTipoInter> findRelCatsocTipoInterByCatSoc(Long idCatSoc) {
		Query q = em.createNamedQuery("CsRelCatsocTipoInter.findRelCatsocTipointerByCatSoc");
		q.setParameter("idCatsoc", idCatSoc);
		return q.getResultList();
	}

	public void salvaRelCatsocTipoInter(CsRelCatsocTipoInter rel) {
		em.persist(rel);
	}
	
	public void updateRelCatsocTipoInter(CsRelCatsocTipoInter rel) {
		em.merge(rel);
	}

	public void eliminaRelCatsocTipoInter(Long idCatSoc, Long idTipoInter) {
		Query q = em.createNamedQuery("CsRelCatsocTipoInter.eliminaRelCatsocTipointerByIds");
		q.setParameter("idCatsoc", idCatSoc);
		q.setParameter("idTipointer", idTipoInter);
		q.executeUpdate();
	}
	
	public CsRelSettoreCatsoc getRelSettoreCatsocById(CsRelSettoreCatsocPK id) {
		CsRelSettoreCatsoc cs = em.find(CsRelSettoreCatsoc.class, id);
		return cs;	
	}
	
	@SuppressWarnings("unchecked")
	public List<CsRelSettoreCatsoc> findRelSettoreCatsocByCatSoc(Long idCatSoc) {
		Query q = em.createNamedQuery("CsRelSettoreCatsoc.findRelSettoreCatsocByCatSoc");
		q.setParameter("idCatsoc", idCatSoc);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<CsRelSettoreCatsoc> findRelSettoreCatsocBySettore(Long idSettore) {
		Query q = em.createNamedQuery("CsRelSettoreCatsoc.findRelSettoreCatsocBySettore");
		q.setParameter("idSettore", idSettore);
		List<Object[]> lst= q.getResultList();
		List<CsRelSettoreCatsoc> lstend = new ArrayList<CsRelSettoreCatsoc>();
		for(Object[] o : lst)
			lstend.add((CsRelSettoreCatsoc)o[0]);
		return lstend;
	}

	public void salvaRelSettoreCatsoc(CsRelSettoreCatsoc rel) {
		em.persist(rel);
	}
	
	public void updateRelSettoreCatsoc(CsRelSettoreCatsoc rel) {
		em.merge(rel);
	}

	public void eliminaRelSettoreCatsoc(Long idCatSoc, Long idSettore) {
		Query q = em.createNamedQuery("CsRelSettoreCatsoc.eliminaRelSettoreCatsocByIds");
		q.setParameter("idCatsoc", idCatSoc);
		q.setParameter("idSettore", idSettore);
		q.executeUpdate();
	}
	
	public CsRelSettCsocTipoInter getRelSettCsocTipoInterById(CsRelSettCsocTipoInterPK id) {
		CsRelSettCsocTipoInter cs = em.find(CsRelSettCsocTipoInter.class, id);
		return cs;
	}
	
	public void salvaRelSettCsocTipoInter(CsRelSettCsocTipoInter rel) {
		em.persist(rel);
	}
	
	public void updateRelSettCsocTipoInter(CsRelSettCsocTipoInter rel) {
		em.merge(rel);
	}
	
	@SuppressWarnings("unchecked")
	public List<CsRelSettCsocTipoInter> findRelSettCatsocTipoInterByCatSoc(Long idCatSoc) {
		Query q = em.createNamedQuery("CsRelSettCsocTipoInter.findRelSettCatsocTipoInterByCatSoc");
		q.setParameter("idCatsoc", idCatSoc);
		return q.getResultList();
	}
	
	public void eliminaRelSettCsocTipoInter(Long idCatSoc, Long idSettore, Long idTipoInter) {
		Query q = em.createNamedQuery("CsRelSettCsocTipoInter.eliminaRelSettoreCsocTipointerByIds");
		q.setParameter("idCatsoc", idCatSoc);
		q.setParameter("idSettore", idSettore);
		q.setParameter("idTipointer", idTipoInter);
		q.executeUpdate();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CsRelSettCatsocEsclusiva> findRelSettCatsocEsclusivaByCatSoc(Long idCatSoc) {
		Query q = em.createNamedQuery("CsRelSettCatsocEsclusiva.findRelSettCatsocEsclusivaByCatSoc");
		q.setParameter("catSocId", idCatSoc);
		return q.getResultList();
	}
	
	public void salvaRelSettCatsocEsclusiva(CsRelSettCatsocEsclusiva rel) {
		em.persist(rel);
	}
	
	public void updateRelSettCatsocEsclusiva(CsRelSettCatsocEsclusiva rel) {
		em.merge(rel);
	}
	
	public void eliminaRelSettCatsocEsclusiva(Long idCatSoc, Long idSettore, Long idTipoDiario) {
		Query q = em.createNamedQuery("CsRelSettCatsocEsclusiva.deleteRelSettCatsocEsclusivaByIds");
		q.setParameter("tipoDiarioId", idTipoDiario);
		q.setParameter("categoriaSocialeId", idCatSoc);
		q.setParameter("settoreId", idSettore);
		q.executeUpdate();
	}
}
