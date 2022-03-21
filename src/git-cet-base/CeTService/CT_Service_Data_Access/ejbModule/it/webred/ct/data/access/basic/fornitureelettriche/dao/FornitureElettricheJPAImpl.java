package it.webred.ct.data.access.basic.fornitureelettriche.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;

import it.webred.ct.data.access.basic.fornitureelettriche.FornitureElettricheQueryBuilder;
import it.webred.ct.data.access.basic.fornitureelettriche.FornitureElettricheServiceException;
import it.webred.ct.data.access.basic.fornitureelettriche.dto.FornituraElettricaDTO;
import it.webred.ct.data.model.fornituraelettrica.SitEnelUtente;
import it.webred.ct.data.model.fornituraelettrica.SitEnelUtenza;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class FornitureElettricheJPAImpl extends CTServiceBaseDAO implements FornitureElettricheDAO {

	private static final long serialVersionUID = 6470081116054016812L;

	@Override
	public List<SitEnelUtente> getUtentiByDatiAnag(FornituraElettricaDTO fe) throws FornitureElettricheServiceException {
		List<SitEnelUtente> lstUtenti = new ArrayList<SitEnelUtente>();
		
		logger.debug("Forniture Elettriche - getUtentiByDatiAnag() [DENOMINAZIONE: "+fe.getDenominazione()+"];" + "[IDENTIFICATIVO: "+fe.getIdentificativo()+"]; ");
		try {
			String hql = new FornitureElettricheQueryBuilder().createQueryListaUtentiByCriteria(fe);
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
			throw new FornitureElettricheServiceException(t);
		}
		
		return lstUtenti;
	}//-------------------------------------------------------------------------

	@Override
	public List<SitEnelUtenza> getUtenzeByParams(FornituraElettricaDTO fe) throws FornitureElettricheServiceException {
		List<SitEnelUtenza> lstUtenze = new ArrayList<SitEnelUtenza>();
		
		logger.debug("Forniture Elettriche - getUtenzeByParams() [TIPO AREA: "+fe.getTipoArea()+"];" + "[NOME VIA: "+fe.getVia()+"]; " + "[CIVICO: "+fe.getCivico()+"]; " + "[ANNO UTENZA: "+fe.getAnnoUtenza()+"]; " + "[CODICE UTENZA: "+fe.getCodiceUtenza()+"]; ");
		try {
			String hql = new FornitureElettricheQueryBuilder().createQueryListaUtenzeByCriteria(fe);
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
			throw new FornitureElettricheServiceException(t);
		}
		
		return lstUtenze;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<Object[]> getFornitureElettricheByParams(FornituraElettricaDTO fe) throws FornitureElettricheServiceException {
		List<Object[]> lstFornitureElettriche = new ArrayList<Object[]>();
		
		logger.debug("Forniture Elettriche - getFornitureElettricheByParams() [TIPO AREA: "+fe.getTipoArea()+"];" + "[NOME VIA: "+fe.getVia()+"]; " + "[CIVICO: "+fe.getCivico()+"]; " + "[ANNO UTENZA: "+fe.getAnnoUtenza()+"]; " + "[CODICE UTENZA: "+fe.getCodiceUtenza()+"]; [DENOMINAZIONE: "+fe.getDenominazione()+"];" + "[IDENTIFICATIVO: "+fe.getIdentificativo()+"]; ");
		try {
			String hql = new FornitureElettricheQueryBuilder().createQueryListaFornitureElettricheByCriteria(fe);
			Query q = manager_diogene.createQuery(hql);
			if (fe.getLimit() > 0)
				q.setFirstResult(0).setMaxResults(fe.getLimit());

			lstFornitureElettriche = q.getResultList();	
			if (lstFornitureElettriche != null )
				logger.debug("Result size ["+lstFornitureElettriche.size()+"]");
			else
				logger.debug("Result size [0]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new FornitureElettricheServiceException(t);
		}
		
		return lstFornitureElettriche;
	}//-------------------------------------------------------------------------

}

