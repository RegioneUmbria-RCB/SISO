package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.data.model.CsAComponenteGit;
import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.cs.data.model.CsAFamigliaGruppoGit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

@Named
public class ParentiDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public CsAFamigliaGruppoGit getFamigliaGruppoGit(Long idSoggetto) {
		try{	
			Query q = em.createNamedQuery("CsAFamigliaGruppoGit.getFamigliaGruppoGitBySoggettoId")
					.setParameter("idSoggetto", idSoggetto);
			List<CsAFamigliaGruppoGit> lista = q.getResultList();
			if(lista != null && lista.size() > 0)
				return lista.get(0);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}	
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public CsAFamigliaGruppo getFamigliaGruppo(Long idSoggetto) {
		try{
		Query q = em.createNamedQuery("CsAFamigliaGruppo.getFamigliaGruppoBySoggettoId")
				.setParameter("idSoggetto", idSoggetto);
		List<CsAFamigliaGruppo> lista = q.getResultList();
		if(lista != null && lista.size() > 0)
			return lista.get(0);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}	
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsAFamigliaGruppoGit> getFamiglieGruppoGitAggiornate() {
		List<CsAFamigliaGruppoGit> s = new ArrayList<CsAFamigliaGruppoGit>();
		Query q;
		try{
			q = em.createNamedQuery("CsAFamigliaGruppoGit.getFamigliaGruppoGitAggiornate");
			s = q.getResultList();
		
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
		return s;
	}
	
	public void saveComponenteGit(CsAComponenteGit comp) {
		try{
			em.persist(comp);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
	}
	
	public void updateComponenteGit(CsAComponenteGit comp) {
		try{
			em.merge(comp);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
	}
	
	public CsAFamigliaGruppoGit saveFamigliaGruppoGit(CsAFamigliaGruppoGit fam) {
		try{
			em.persist(fam);
		}catch(Exception sqle){return null;}
		return fam;

	}
	
	public void updateFamigliaGruppoGit(CsAFamigliaGruppoGit fam) {
		try{
			em.merge(fam);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
	}
	
}
