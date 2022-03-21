package it.webred.ct.data.access.basic.fornituregas.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.fornituregas.FornitureGasQueryBuilder;
import it.webred.ct.data.access.basic.fornituregas.FornitureGasServiceException;
import it.webred.ct.data.access.basic.fornituregas.dto.FornituraGasDTO;
import it.webred.ct.data.model.gas.SitUGas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class FornitureGasJPAImpl extends CTServiceBaseDAO implements FornitureGasDAO {

	private static final long serialVersionUID = 6470081116054016812L;

	@Override
	public List<SitUGas> getUtentiByDatiAnag(FornituraGasDTO fe) throws FornitureGasServiceException {
		List<SitUGas> lstUtenti = new ArrayList<SitUGas>();
		
		logger.debug("Forniture Gas - getUtentiByDatiAnag() [DENOMINAZIONE: "+fe.getDenominazione()+"];" + "[IDENTIFICATIVO: "+fe.getIdentificativo()+"]; ");
		try {
			String hql = new FornitureGasQueryBuilder().createQueryListaUtentiByCriteria(fe);
			Query q = manager_diogene.createQuery(hql);
			if (fe.getLimit() > 0)
				q.setFirstResult(0).setMaxResults(fe.getLimit());

			lstUtenti = q.getResultList();	
			if (lstUtenti != null )
				logger.debug("Result size ["+lstUtenti.size()+"]");
			else
				logger.debug("Result size [0]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new FornitureGasServiceException(t);
		}
		
		return lstUtenti;
	}//-------------------------------------------------------------------------

	@Override
	public List<SitUGas> getUtenzeByParams(FornituraGasDTO fe) throws FornitureGasServiceException {
		List<SitUGas> lstUtenze = new ArrayList<SitUGas>();
		
		logger.debug("Forniture Gas - getUtenzeByParams() [TIPO AREA: "+fe.getTipoArea()+"];" + "[NOME VIA: "+fe.getVia()+"]; " + "[CIVICO: "+fe.getCivico()+"]; " + "[ANNO UTENZA: "+fe.getAnnoUtenza()+"]; " + "[CODICE UTENZA: "+fe.getCodiceUtenza()+"]; ");
		try {
			String hql = new FornitureGasQueryBuilder().createQueryListaUtenzeByCriteria(fe);
			Query q = manager_diogene.createQuery(hql);
			if (fe.getLimit() > 0)
				q.setFirstResult(0).setMaxResults(fe.getLimit());

			lstUtenze = q.getResultList();	
			if (lstUtenze != null )
				logger.debug("Result size ["+lstUtenze.size()+"]");
			else
				logger.debug("Result size [0]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new FornitureGasServiceException(t);
		}
		
		return lstUtenze;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<SitUGas> getFornitureGasByParams(FornituraGasDTO fe) throws FornitureGasServiceException {
		List<SitUGas> lstFornitureGas = new ArrayList<SitUGas>();
		
		logger.debug("Forniture Gas - getFornitureGasByParams() [TIPO AREA: "+fe.getTipoArea()+"];" + "[NOME VIA: "+fe.getVia()+"]; " + "[CIVICO: "+fe.getCivico()+"]; " + "[ANNO UTENZA: "+fe.getAnnoUtenza()+"]; " + "[CODICE UTENZA: "+fe.getCodiceUtenza()+"]; [DENOMINAZIONE: "+fe.getDenominazione()+"];" + "[IDENTIFICATIVO: "+fe.getIdentificativo()+"]; ");
		try {
			String hql = new FornitureGasQueryBuilder().createQueryListaFornitureGasByCriteria(fe);
			Query q = manager_diogene.createQuery(hql);
			if (fe.getLimit() > 0)
				q.setFirstResult(0).setMaxResults(fe.getLimit());

			lstFornitureGas = q.getResultList();	
			if (lstFornitureGas != null )
				logger.debug("Result size ["+lstFornitureGas.size()+"]");
			else
				logger.debug("Result size [0]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new FornitureGasServiceException(t);
		}
		
		return lstFornitureGas;
	}//-------------------------------------------------------------------------

}


