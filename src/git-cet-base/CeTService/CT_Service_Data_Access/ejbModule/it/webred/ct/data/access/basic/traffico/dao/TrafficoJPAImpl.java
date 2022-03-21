package it.webred.ct.data.access.basic.traffico.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.traffico.TrafficoServiceException;
import it.webred.ct.data.access.basic.traffico.dto.TrafficoSearchCriteria;
import it.webred.ct.data.model.traffico.SitTrffMulte;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class TrafficoJPAImpl extends CTServiceBaseDAO implements TrafficoDAO {

	@Override
	public List<SitTrffMulte> getListaMulteByCriteria(TrafficoSearchCriteria criteria)
			throws TrafficoServiceException {

		List<SitTrffMulte> lista = new ArrayList<SitTrffMulte>();
		try {

			String sql = (new TrafficoQueryBuilder(criteria)).createQuery();

			Query q = manager_diogene.createQuery(sql);
			lista.addAll(q.getResultList());
			
			//aggiungo multe che hanno nome e cognome ma non cod fisc
			if(criteria.getCodFiscale() != null && !criteria.getCodFiscale().trim().equals("") &&
					criteria.getNome() != null && !criteria.getNome().trim().equals("") &&
							criteria.getCognome() != null && !criteria.getCognome().trim().equals("")){
				String nome = criteria.getNome();
				String cognome = criteria.getCognome();
				criteria = new TrafficoSearchCriteria();
				criteria.setNullCodFisc(true);
				criteria.setNome(nome);
				criteria.setCognome(cognome);
				sql = (new TrafficoQueryBuilder(criteria)).createQuery();
				q = manager_diogene.createQuery(sql);
				lista.addAll(q.getResultList());
			}
			
			return lista;

		} catch (Throwable t) {
			throw new TrafficoServiceException(t);
		}

	}

}
