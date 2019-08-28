package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.InfoRecapitiDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
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
	
	public  InfoRecapitiDTO findInfoRecapiti(String cf){
		try{
				
			String sql = 
					  "SELECT tel, cell, email, indirizzo, civico, prov, com_cod, com_des, stato_cod, stato_des "
					+ "FROM V_CS_SS_INFO_SOGGETTI WHERE CF = ? ORDER BY DATA DESC";
			Query q = em.createNativeQuery(sql);
			q.setParameter(1, cf.toUpperCase());
			List<Object[]> lst = q.getResultList();
			if(!lst.isEmpty()){
				Object[] arr = new Object[lst.get(0).length];
				boolean completo = false;
				int j=0;
				while(!completo && j<lst.size()){
					Object[] oarr = lst.get(j);
					completo = true;
					for(int i = 0; i<oarr.length; i++){
						if(arr[i]==null && oarr[i]!=null) arr[i]=oarr[i];
						if(arr[i]==null) completo = false;
					}
					j++;
				}
				
				InfoRecapitiDTO iDTO = new InfoRecapitiDTO();
				int index = -1;
				iDTO.setTelefono((String)arr[++index]);
				iDTO.setCellulare((String)arr[++index]);
				iDTO.setEmail((String)arr[++index]);
				iDTO.setResIndirizzo((String)arr[++index]);
				iDTO.setResCivico((String)arr[++index]);
				iDTO.setResProv((String)arr[++index]);
				iDTO.setResComune(new KeyValueDTO(arr[++index],(String)arr[++index]));
				iDTO.setResStato(new KeyValueDTO(arr[++index],(String)arr[++index]));
				
				return iDTO;
			}
			
			
		}catch(Exception e){
			logger.error("findInfoRecapiti "+e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
		
		return null;
	}
	
}
