package it.webred.ct.data.access.aggregator.isee.dao;

import it.webred.ct.data.access.aggregator.isee.IseeServiceException;
import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.model.isee.IseeAnnoDich;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;


public class IseeJPAImpl extends CTServiceBaseDAO implements IseeDAO  {

	@Override
	public List<IseeAnnoDich> getAnnoDich(){
		List<IseeAnnoDich> lista = new ArrayList<IseeAnnoDich>();
		
		try {			
			logger.debug("ESTRAZIONE LISTA ANNO DICHIARAZIONE ISEE");
			Query q;
			q = manager_diogene.createNamedQuery("Isee.getAnnoDich");
			lista = q.getResultList();
		
		} catch (Throwable t) {
			logger.error("", t);
			throw new IseeServiceException(t);
		}
		
		return lista;
	}

}
