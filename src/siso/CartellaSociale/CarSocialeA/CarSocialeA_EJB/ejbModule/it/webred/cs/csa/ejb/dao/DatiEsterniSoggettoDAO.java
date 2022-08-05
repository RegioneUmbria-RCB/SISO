package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.ParametriDatiEsterniSoggettoDTO;
import it.webred.cs.data.model.CsADatiEsterni;
import it.webred.cs.data.model.CsADatiEsterniSoggetto;
import it.webred.cs.data.model.CsASoggettoDatiEsterni;
import it.webred.cs.data.model.CsCfgBelfiore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Named
public class DatiEsterniSoggettoDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = -8939731743055501929L;

	public List<CsADatiEsterni> getDatiEsterniByCfSoggetto(String cf, String codiceEnte) {
		Set<CsADatiEsterni> uniqueItems = new HashSet<CsADatiEsterni>();
		List<CsADatiEsterni> resultList;
		try {
			String jpql = "SELECT des.datiEsterni FROM CsADatiEsterniSoggetto des WHERE des.codiceEnte like :ente AND des.soggetto.cf = :cf";
			TypedQuery<CsADatiEsterni> qde = em.createQuery(jpql, CsADatiEsterni.class);
			qde.setParameter("ente", "%" + codiceEnte + "%");
			qde.setParameter("cf", cf);
			resultList = qde.getResultList();
			for (CsADatiEsterni de : resultList) {
				uniqueItems.add(de);
			}
		}catch(Exception e) {
			logger.error("getDatiEsterniByCfSoggetto cf["+cf+"] ente["+codiceEnte+"] " + e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}finally{
			resultList = new ArrayList<CsADatiEsterni>(uniqueItems);
		}
		
		return resultList;
	}

	public CsASoggettoDatiEsterni getSoggettoByCf(String cf) {
		Query q = em.createNamedQuery("CsASoggettoDatiEsterni.byCf", CsASoggettoDatiEsterni.class);
		q.setParameter("cf", cf);
		List<CsASoggettoDatiEsterni> result = q.getResultList();
		if (result.size() == 0)
			return null;
		else
			return result.get(0);
	}

	//recupero codiceDocumentoGed
	@SuppressWarnings("unchecked")
	public CsCfgBelfiore findBelfioreByCodTerritorialeId(String codTerritorialeId) {
		try {
			Query q = em.createNamedQuery("CsCfgBelfiore.findByRipTerritorialeId");
			q.setParameter("codRipartizioneTerritoriale", codTerritorialeId);
			
			if(q.getResultList() != null)
			{
				return (CsCfgBelfiore)q.getSingleResult();
			}
		} 
		catch (NoResultException nre){
			//Ignore this because as per your logic this is ok!
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	public void save(CsASoggettoDatiEsterni soggettoDaDatiEsterni, CsADatiEsterni datiEsterni, String codiceEnte, String prestazione, String statoDomanda) {
		CsADatiEsterniSoggetto relazioneDatiEsterniToSoggetto = new CsADatiEsterniSoggetto(soggettoDaDatiEsterni, datiEsterni, codiceEnte);
		relazioneDatiEsterniToSoggetto.setCodicePrestazione(prestazione);
		relazioneDatiEsterniToSoggetto.setStatoDomanda(statoDomanda);
		 
		em.persist(relazioneDatiEsterniToSoggetto);
	}
	public void save(CsASoggettoDatiEsterni soggettoDaDatiEsterni, CsADatiEsterni datiEsterni, String codiceEnte, ParametriDatiEsterniSoggettoDTO parametriEsterni) {
		CsADatiEsterniSoggetto relazioneDatiEsterniToSoggetto = new CsADatiEsterniSoggetto(soggettoDaDatiEsterni, datiEsterni, codiceEnte);
		relazioneDatiEsterniToSoggetto.setCodicePrestazione(parametriEsterni.getCodicePrestazione());
		relazioneDatiEsterniToSoggetto.setTipoPrestazione(parametriEsterni.getTipoPrestazione());
		relazioneDatiEsterniToSoggetto.setStatoDomanda(parametriEsterni.getStatoDomanda());
		relazioneDatiEsterniToSoggetto.setDtAperturaDomanda(parametriEsterni.getDataApertura());
		relazioneDatiEsterniToSoggetto.setDtChiusuraDomanda(parametriEsterni.getDataChiusura());
		relazioneDatiEsterniToSoggetto.setEntitaServizio(parametriEsterni.getEntitaServizio());
		relazioneDatiEsterniToSoggetto.setIndirizzo(parametriEsterni.getIndirizzo());
		
		em.persist(relazioneDatiEsterniToSoggetto);
	}

	public void save(CsADatiEsterni datiEsterni) {
		em.persist(datiEsterni);
	}

	public void save(CsASoggettoDatiEsterni soggetto) {
		em.persist(soggetto);
	}

}
