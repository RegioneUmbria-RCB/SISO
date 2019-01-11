package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsCMedico;
import it.webred.cs.data.model.CsVMedico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

@Named
public class MedicoDAO extends CarSocialeBaseDAO implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@SuppressWarnings("unchecked")
	public List<CsCMedico> getMedici() {
		
		try{
			
			Query q = em.createNamedQuery("CsCMedico.findAllAbil");
			return q.getResultList();
			
		} catch (Exception e) {	
			logger.error("Errore getMedici "+e.getMessage(),e);
		}
		return null;
	}
	
	public List<CsCMedico> searchMediciByDescrizione(String desc, Integer maxResults) {
		
		try{
			
			Query q = em.createNamedQuery("CsCMedico.findAllAbilByDenominazione");
			q.setParameter("descrizione", desc);
			if(maxResults!=null)
				q.setMaxResults(maxResults);
			return q.getResultList();
			
		} catch (Exception e) {	
			logger.error("Errore getMediciByDescrizione "+e.getMessage(),e);
		}
		return null;
	}
	
	public CsCMedico getMedicoByCodReg(BaseDTO dto) {
		
		try{
			Query q = em.createNamedQuery("CsCMedico.findByCodRegionale");
			q.setParameter("codRegionale", (String) dto.getObj());
			List<CsCMedico> lstMedici =  (List<CsCMedico>) q.getResultList();
			if(lstMedici.size()>0)
				return lstMedici.get(0);
		} catch (Exception e) {	
			logger.error("Errore getMedicoByCodReg "+e.getMessage(),e);
		}
		return null;
	}
	
	public CsCMedico findMedicoById(long id) throws Exception {
		CsCMedico medico = em.find(CsCMedico.class, id);
		return medico;
	}
	
	public CsCMedico addNewMedicoFromAnagReg(CsCMedico newMed){
		try{
			em.persist(newMed);
			em.flush();
			return newMed;
		}
		catch(EntityExistsException e){
			return null;
		}
		catch( IllegalArgumentException e){
			return null;
		}
		catch( TransactionRequiredException e){
			return null;
		}
		
	}
	
	public CsVMedico getMedicoRegioneByCodiceRegionale(BaseDTO dto){
		try{
			Query q = em.createNamedQuery("CsVMedico.findByCodReg");
			q.setParameter("codReg", (String) dto.getObj());
			return (CsVMedico) q.getSingleResult();
		} catch (Exception e) {	
			logger.error("Errore getMedicoByCodReg "+e.getMessage(),e);
		}
		return null;
	}

	public List<CsCMedico> findMediciByDescrizione(String desc) {
		List<CsCMedico> lstMedici = new ArrayList<CsCMedico>();
		try{
			Query q = em.createNamedQuery("CsCMedico.findByDescrizione");
			q.setParameter("descrizione", desc);
			lstMedici = (List<CsCMedico>) q.getResultList();
		} catch (Exception e) {	
			logger.error("Errore findMediciByDescrizione "+e.getMessage(),e);
		}
		return lstMedici;
	}
}
