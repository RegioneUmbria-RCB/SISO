package it.webred.ct.data.access.basic.ruolo.tares.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.rette.RetteServiceException;
import it.webred.ct.data.access.basic.ruolo.tares.RTaresServiceException;
import it.webred.ct.data.model.ruolo.SitRuoloTarPdf;
import it.webred.ct.data.model.ruolo.tares.SitRuoloTares;
import it.webred.ct.data.model.ruolo.tares.SitRuoloTaresImm;
import it.webred.ct.data.model.ruolo.tares.SitRuoloTaresRata;
import it.webred.ct.data.model.ruolo.tares.SitRuoloTaresSt;
import it.webred.ct.data.model.ruolo.tares.SitRuoloTaresStSg;

import java.util.List;

import javax.persistence.Query;

public class RTaresJPAImpl extends CTServiceBaseDAO implements RTaresDAO {

	@Override
	public List<SitRuoloTares> getListaRuoliByCodFis(String codFiscale)
			throws RTaresServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRuoloTares.getListaRuoliByCF");
			q.setParameter("codfisc",codFiscale);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RTaresServiceException(t);
		}
	}

	@Override
	public List<SitRuoloTaresImm> getListaImmByCodRuolo(String idExtRuolo)
			throws RTaresServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRuoloTaresImm.getListaImmByCodRuolo");
			q.setParameter("idExtRuolo",idExtRuolo);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RTaresServiceException(t);
		}
	}

	@Override
	public List<SitRuoloTaresRata> getListaRateByCodRuolo(String idExtRuolo)
			throws RetteServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRuoloTaresRata.getListaRateByCodRuolo");
			q.setParameter("idExtRuolo",idExtRuolo);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RTaresServiceException(t);
		}
	}

	@Override
	public List<SitRuoloTaresSt> getListaStByCodRuolo(String idExtRuolo)
			throws RTaresServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRuoloTaresSt.getListaStByCodRuolo");
			q.setParameter("idExtRuolo",idExtRuolo);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RTaresServiceException(t);
		}
	}

	
	@Override
	public List<SitRuoloTaresStSg> getListaStSgByCodRuolo(String idExtRuolo)
			throws RTaresServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRuoloTaresStSg.getListaSgraviByCodRuolo");
			q.setParameter("idExtRuolo",idExtRuolo);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RTaresServiceException(t);
		}
	}
	
	@Override
	public List<SitRuoloTarPdf> getListaPdfByCfCu(String cf, Integer cu )
			throws RTaresServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRuoloTarPdf.getListaPdfByCfCu");
			q.setParameter("codfisc",cf);
			q.setParameter("cu", cu);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RTaresServiceException(t);
		}
	}

	@Override
	public SitRuoloTares getRuoloById(String id) throws RTaresServiceException {
		try {

			return manager_diogene.find(SitRuoloTares.class, id);

		} catch (Throwable t) {
			throw new RTaresServiceException(t);
		}
	}
	
	
	
}
