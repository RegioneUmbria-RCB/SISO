package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.dto.siru.SiruDominioDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class DominioSiruDAO extends CarSocialeBaseDAO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	public List<SiruDominioDTO> loadSiruDimAzienda(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_DIMENSIONE_AZIENDA");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			String codice = (String)obs[0];
			String desc = (String)obs[1];
			String siso = (String)obs[2];
			toReturn.add(new SiruDominioDTO(codice, desc, siso));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruGruppoVulPart(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_GRUPPO_VUL_PART");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			String codice = (String)obs[0];
			String desc = (String)obs[1];
			String siso = (String)obs[2];
			toReturn.add(new SiruDominioDTO(codice, desc, siso));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruItComune(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT ID, CODICE_CATASTALE, DENOMINAZIONE FROM FSE_CHK_IT_COMUNE");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			String codice = (String)obs[0];
			String desc = (String)obs[1];
			String siso = (String)obs[2];
			toReturn.add(new SiruDominioDTO(codice, desc, siso));
		}
		
		return toReturn;
	}
	

	
	public List<SiruDominioDTO> loadSiruTipoOrarioLavoro(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_TIPO_ORARIO_LAVORO");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			String codice = (String)obs[0];
			String desc = (String)obs[1];
			String siso = (String)obs[3];
			toReturn.add(new SiruDominioDTO(codice, desc, siso));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruTitoloStudio(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_TITOLO_STUDIO");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			String codice = (String)obs[0];
			String desc = (String)obs[1];
			String siso = (String)obs[3];
			toReturn.add(new SiruDominioDTO(codice, desc, siso));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruTipologiaLavoro(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_TIPOLOGIA_LAVORO");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			String codice = (String)obs[0];
			String desc = (String)obs[1];
			String siso = (String)obs[3];
			toReturn.add(new SiruDominioDTO(codice, desc, siso));
		}
		
		return toReturn;
	}
		
	public List<SiruDominioDTO> loadSiruDurataRicerca(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_DURATA_RICERCA");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			String codice = (String)obs[0];
			String desc = (String)obs[1];
			String siso = (String)obs[2];
			toReturn.add(new SiruDominioDTO(codice, desc, siso));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadCondizioneMercato(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_CONDIZIONE_MERCATO");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			String codice = (String)obs[0];
			String desc = (String)obs[1];
			String siso = (String)obs[2];
			toReturn.add(new SiruDominioDTO(codice, desc, siso));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadResidenza(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_RESIDENZA");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			String codice = (String)obs[0];
			String desc = (String)obs[2];
			String siso = (String)obs[1];
			toReturn.add(new SiruDominioDTO(codice, desc, siso));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadAmNazionalita(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT DISTINCT COD_ISTAT_NAZIONE, NAZIONALITA, NAZIONALITA_PRINCIPALE FROM AM_TAB_NAZIONI ORDER BY NAZIONALITA, NAZIONALITA_PRINCIPALE DESC");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results)
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[1], null));
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruCittadinanza(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_CITTADINANZA");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0], (String)obs[1], null));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruNazioneIstatAgEntrate(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT DISTINCT IT.CODICE_AG_ENTRATE,  IT.DENOMINAZIONE, AM.COD_ISTAT_NAZIONE  "+
									   "FROM AM_TAB_NAZIONI am LEFT JOIN FSE_CHK_IT_NAZIONE it on AM.COD_AG_ENTRATE = IT.CODICE_FISCALE");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			String codice = (String)obs[0];
			String desc = (String)obs[1];
			String siso = (String)obs[2];
			toReturn.add(new SiruDominioDTO(codice, desc, siso));
		}
		return toReturn;
	}
	
/*	public List<SiruDominioDTO> loadSiruItNazione(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_IT_NAZIONE");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			String codice = (String)obs[0];
			String desc = (String)obs[2];
			String siso = (String)obs[1];
			toReturn.add(new SiruDominioDTO(codice, desc, siso));
		}
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadAmNazioneAgEntrate(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT DISTINCT COD_AG_ENTRATE,  NAZIONE, COD_ISTAT_NAZIONE  FROM AM_TAB_NAZIONI");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results)
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[1], (String)obs[2]));
		return toReturn;
	}*/
	
	public List<SiruDominioDTO> loadSiruLocalizzazioneGeog(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_LOCALIZZAZIONE_GEOG");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[6],null));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruStatoPartecipante(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_STATO_PARTECIPANTE");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0], (String)obs[1], null));
		}
		
		return toReturn;
	}
	
	public List<SiruDominioDTO> loadSiruAteco(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_ATECO");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[1], null));
		}
		
		return toReturn;
	}
	
	//SISO-850
	public List<SiruDominioDTO> loadSisoAteco(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM AR_TB_ATECO");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			BigDecimal bd = (BigDecimal)obs[2];
			toReturn.add(new SiruDominioDTO((String)obs[0],(String)obs[1],bd.toString(),null));
		}
		
		return toReturn;	
	}
	
	public List<SiruDominioDTO> loadSiruFormaGiuridica(){
		List<SiruDominioDTO> toReturn = new ArrayList<SiruDominioDTO>();
		Query q = em.createNativeQuery("SELECT * FROM FSE_CHK_FORMA_GIURIDICA_RNA");
		List<Object[]> results = q.getResultList();
		for(Object[] obs : results){
			String codice = (String)obs[0];
			String desc = (String)obs[1];
			toReturn.add(new SiruDominioDTO(codice, desc, null));
		}
		
		return toReturn;
	}
}
