package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.CsADatiEsterni;
import it.webred.cs.data.model.CsADatiEsterniSoggetto;
import it.webred.cs.data.model.CsASoggettoDatiEsterni;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.persistence.TypedQuery;

@Named
public class DatiEsterniSoggettoDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = -8939731743055501929L;

	public List<CsADatiEsterni> getDatiEsterniByCfSoggetto(String cf, String codiceEnte) {
		String jpql = "SELECT des.datiEsterni FROM CsADatiEsterniSoggetto des WHERE des.codiceEnte like :ente AND des.soggetto.cf = :cf";
		TypedQuery<CsADatiEsterni> qde = em.createQuery(jpql, CsADatiEsterni.class);
		qde.setParameter("ente", "%" + codiceEnte + "%");
		qde.setParameter("cf", cf);
		List<CsADatiEsterni> resultList = qde.getResultList();
		Set<CsADatiEsterni> uniqueItems = new HashSet<CsADatiEsterni>();
		for (CsADatiEsterni de : resultList) {
			uniqueItems.add(de);
		}
		resultList = new ArrayList<CsADatiEsterni>(uniqueItems);
		return resultList;
	}

	public CsASoggettoDatiEsterni getSoggettoByCf(String cf) {
		List<CsASoggettoDatiEsterni> result = em.createNamedQuery("CsASoggettoDatiEsterni.byCf", CsASoggettoDatiEsterni.class).setParameter("cf", cf)
				.getResultList();
		if (result.size() == 0)
			return null;
		else
			return result.get(0);
	}

	public void save(CsASoggettoDatiEsterni soggettoDaDatiEsterni, CsADatiEsterni datiEsterni, String codiceEnte) {
		CsADatiEsterniSoggetto relazioneDatiEsterniToSoggetto = new CsADatiEsterniSoggetto(soggettoDaDatiEsterni, datiEsterni, codiceEnte);
		em.persist(relazioneDatiEsterniToSoggetto);
	}

	public void save(CsADatiEsterni datiEsterni) {
		em.persist(datiEsterni);
	}

	public void save(CsASoggettoDatiEsterni soggetto) {
		em.persist(soggetto);
	}

}
