package it.webred.ct.data.access.basic.condono.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.condono.CondonoServiceException;
import it.webred.ct.data.access.basic.condono.dto.RicercaCondonoDTO;
import it.webred.ct.data.model.condono.CondonoPratica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CondonoJPAImpl extends CTServiceBaseDAO implements CondonoDAO{
	
	private static final long serialVersionUID = 1L;

	@Override
	public CondonoPratica getPraticaCondono(RicercaCondonoDTO rc)
	{
		try {
			/*
			Query q= manager_diogene.createNamedQuery("Condono.getInfoCondono");
			q.setParameter("foglio",codiceCondono);
			
			condono = (CondonoPratica)q.getSingleResult();
			*/
			return (CondonoPratica)manager_diogene.find(CondonoPratica.class, new Long(rc.getCodiceCondono()));

		} catch (Throwable t) {
			throw new CondonoServiceException(t);
		}
	}
	
	
	@Override
	public List<CondonoPratica> getCondoni(RicercaCondonoDTO rc)
	{
		List<CondonoPratica> lista = new ArrayList<CondonoPratica> ();
		try {
	
			Query q=manager_diogene.createNamedQuery("Condono.getInfoCondono");
			
			if(rc.getDtRiferimento()!=null)
				q.setParameter("pgData",rc.getDtRiferimento());
			else
				q.setParameter("pgData",new Date());

			q.setParameter("foglio",rc.getFoglio());
			q.setParameter("particella",rc.getParticella());
			
			lista = (List<CondonoPratica>) q.getResultList();

		} catch (Throwable t) {
			throw new CondonoServiceException(t);
		}
		return lista;
	}
} 
