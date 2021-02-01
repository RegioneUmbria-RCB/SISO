package it.webred.amprofiler.ejb.anagrafica;

import it.webred.amprofiler.ejb.AmProfilerBaseService;
import it.webred.amprofiler.ejb.anagrafica.dto.AnagraficaSearchCriteria;
import it.webred.amprofiler.ejb.anagrafica.dto.QueryBuilder;
import it.webred.amprofiler.model.AmAnagrafica;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
public class AnagraficaServiceBean extends AmProfilerBaseService implements
		AnagraficaService {

	@Override
	public List<AmAnagrafica> getListaAnagrafica(
			AnagraficaSearchCriteria criteria) {

		try {

			String sql = (new QueryBuilder(criteria)).createQuery();

			if (sql == null)
				return new ArrayList<AmAnagrafica>();

			Query q = em.createQuery(sql);
			q.setMaxResults(100);

			return q.getResultList();

		} catch (Throwable t) {
			throw new AnagraficaServiceException(t);
		}

	}

	@Override
	public AmAnagrafica findAnagraficaByUserName(String userName) {
		AnagraficaSearchCriteria criteria = new AnagraficaSearchCriteria();
		criteria.setUserName(userName);
		criteria.setUserNameEquals(true);
		List<AmAnagrafica> lista = getListaAnagrafica(criteria);
		if (lista.size() > 0)
			return lista.get(0);
		else
			return null;

	}

	@Override
	public AmAnagrafica findAnagraficaById(Integer id) {
        AmAnagrafica anag = em.find(AmAnagrafica.class, id);
        if(anag==null)
        	anag = new AmAnagrafica();
        return anag;
	}

	@Override
	public List<AmAnagrafica> findAnagraficaByCodiceFiscale(String userName,
			String codiceFiscale) {

		AnagraficaSearchCriteria criteria = new AnagraficaSearchCriteria();
		criteria.setUserName(userName);
		criteria.setCodiceFiscale(codiceFiscale);
		return getListaAnagrafica(criteria);

	}
	
	@Override
	public List<AmAnagrafica> findAnagraficaByCodiceFiscaleCognomeNome(String codiceFiscale, String cognome, String nome) {

		AnagraficaSearchCriteria criteria = new AnagraficaSearchCriteria();
		criteria.setCodiceFiscale(codiceFiscale);
		criteria.setCognome(cognome);
		criteria.setNome(nome);
		return getListaAnagrafica(criteria);

	}

	@Override
	public boolean createAnagrafica(AmAnagrafica anagrafica){

		boolean esito = false;
		
		try {

			em.persist(anagrafica);
			esito = true;
			
		} catch (Throwable t) {
			throw new AnagraficaServiceException(t);
		}
		return esito;
	}
	
	@Override
	public boolean updateAnagrafica(AmAnagrafica anagrafica){

		boolean esito = false;
		
		try {

			em.merge(anagrafica);
			esito = true;
			
		} catch (Throwable t) {
			throw new AnagraficaServiceException(t);
		}
		return esito;
	}

}
