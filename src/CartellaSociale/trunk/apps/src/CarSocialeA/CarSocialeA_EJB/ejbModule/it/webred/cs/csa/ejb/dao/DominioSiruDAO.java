package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.dto.SiruDominioDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class DominioSiruDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public List<SiruDominioDTO> loadSiruAteco(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_ATECO");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0]));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruDimAzienda(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_DIMENSIONE_AZIENDA");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0]));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruFormaGiuridica(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_FORMA_GIURIDICA_RNA");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0]));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruGruppoVulPart(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_GRUPPO_VUL_PART");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[2]));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruItComune(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_IT_COMUNE");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[3]));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruItNazione(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_IT_NAZIONE");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[1]));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSisoNazione(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM AM_TAB_NAZIONI");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[5],(String)obs[1]));
		}
		
		return toReturn;
	}
	
	//SISO-850
	public List<SiruDominioDTO> loadSisoAteco(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM AR_TB_ateco");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			BigDecimal bd = (BigDecimal)obs[2];
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[1],bd.toString()));
		}
		
		return toReturn;
		
	}
	public List<SiruDominioDTO> loadSiruLocalizzazioneGeog(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_LOCALIZZAZIONE_GEOG");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0]));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruStatoPartecipante(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_STATO_PARTECIPANTE");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0]));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruTipoOrarioLavoro(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_TIPO_ORARIO_LAVORO");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[3]));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruTitoloStudio(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_TITOLO_STUDIO");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[3]));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruTipologiaLavoro(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_TIPOLOGIA_LAVORO");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[1]));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruCittadinanza(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_CITTADINANZA");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0]));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruDurataRicerca(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_DURATA_RICERCA");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[2]));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadCondizioneMercato(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_CONDIZIONE_MERCATO");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[1]));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadResidenza(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_RESIDENZA");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[1]));
		}
		
		return toReturn;
	}
	
}
