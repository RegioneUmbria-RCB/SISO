package it.webred.ct.data.access.basic.bolliVeicoli.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.bolliVeicoli.BolliVeicoliQueryBuilder;
import it.webred.ct.data.access.basic.bolliVeicoli.BolliVeicoliServiceException;
import it.webred.ct.data.access.basic.bolliVeicoli.dto.RicercaBolliVeicoliDTO;
import it.webred.ct.data.model.bolliVeicoli.BolloVeicolo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class BolliVeicoliJPAImpl extends CTServiceBaseDAO implements BolliVeicoliDAO {

	private static final long serialVersionUID = 3503405803703978092L;

	@Override
	public List<BolloVeicolo> getBolliVeicoliByCFPI(RicercaBolliVeicoliDTO rbv)	throws BolliVeicoliServiceException {
		List<BolloVeicolo> lista = null;
		logger.debug("getBolliVeicoliByCFPI-parms["+"]" );
		try {
			Query q= null;
			
			if (rbv != null){
				q = manager_diogene.createNamedQuery("BolliVeicoliInfo.getBolliVeicoliByCFPI");
			}
			
			q.setParameter("cfpi", rbv.getCodiceFiscalePartitaIva());
			
			if (rbv.getLimit()!=null && rbv.getLimit()>0)
				q.setFirstResult(0).setMaxResults(rbv.getLimit());
			
			lista = q.getResultList();
			if (lista != null && lista.size()>0)
				logger.warn("getBolliVeicoliByCriteria TROVATO. N.ELE: "+ lista.size());
			else
				logger.warn("getBolliVeicoliByCriteria TROVATO. N.ELE: 0");
	
		} catch (Throwable t) {
			logger.error("", t);
			throw new BolliVeicoliServiceException(t);
		}
		
		return lista;
	}//-------------------------------------------------------------------------

	@Override
	public List<BolloVeicolo> getBolliVeicoliByCriteria(RicercaBolliVeicoliDTO rbv) throws BolliVeicoliServiceException {
		
		List<BolloVeicolo> lstBolliVeicoli = new ArrayList<BolloVeicolo>();
		try {
			String hql = new BolliVeicoliQueryBuilder().createQueryBolliVeicoliByCriteria(rbv);
			logger.debug("getBolliVeicoliByCriteria: " + hql );			
			Query q = manager_diogene.createQuery(hql);

			if (rbv.getLimit()>0)
				q.setFirstResult(0).setMaxResults(rbv.getLimit());
			
			lstBolliVeicoli = q.getResultList();
			if (lstBolliVeicoli != null && lstBolliVeicoli.size()>0)
				logger.warn("getBolliVeicoliByCriteria TROVATO. N.ELE: "+ lstBolliVeicoli.size());
			else
				logger.warn("getBolliVeicoliByCriteria TROVATO. N.ELE: 0");
		
		} catch (Throwable t) {
			logger.error("", t);
			throw new BolliVeicoliServiceException (t);
		}
		
		return lstBolliVeicoli;
	}//-------------------------------------------------------------------------
	

}

