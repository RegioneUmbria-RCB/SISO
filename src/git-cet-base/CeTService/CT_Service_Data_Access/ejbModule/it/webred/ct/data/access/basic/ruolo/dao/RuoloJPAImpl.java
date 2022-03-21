package it.webred.ct.data.access.basic.ruolo.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.ruolo.RuoloServiceException;
import it.webred.ct.data.model.ruolo.SitRuoloTarPdf;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

public class RuoloJPAImpl extends CTServiceBaseDAO implements RuoloDAO {


	@Override
	public List<SitRuoloTarPdf> getListaPdfByCfCu(String cf, BigDecimal cu )
			throws RuoloServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRuoloTarPdf.getListaPdfByCfCu");
			q.setParameter("codfisc",cf);
			q.setParameter("cu", cu);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RuoloServiceException(t);
		}
	}
	
	@Override
	public List<SitRuoloTarPdf> getListaPdfByCf(String cf)
			throws RuoloServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRuoloTarPdf.getListaPdfByCf");
			q.setParameter("codfisc",cf);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RuoloServiceException(t);
		}
	}
	
	
	
}
