package it.webred.ct.data.access.basic.acqua.dao;

import it.webred.ct.data.access.basic.acqua.AcquaBaseService;
import it.webred.ct.data.access.basic.acqua.AcquaServiceException;
import it.webred.ct.data.model.acqua.SitAcquaCatasto;
import it.webred.ct.data.model.acqua.SitAcquaUtente;
import it.webred.ct.data.model.acqua.SitAcquaUtenze;

import java.util.List;

import javax.persistence.Query;

public class AcquaJPAImpl extends AcquaBaseService implements AcquaDAO {

	private static final long serialVersionUID = 1L;

	@Override
	public List<SitAcquaUtente> getListaUtenteByCodFis(String codFiscale)
			throws AcquaServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitAcquaUtente.getUtenteByCF").setParameter(
					"codFisc", codFiscale);
			return q.getResultList();

		} catch (Throwable t) {
			throw new AcquaServiceException(t);
		}
	}
	
	@Override
	public List<SitAcquaUtente> getListaUtenteByPIva(String pIva)
			throws AcquaServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitAcquaUtente.getUtenteByPI").setParameter(
					"pIva", pIva);
			return q.getResultList();

		} catch (Throwable t) {
			throw new AcquaServiceException(t);
		}
	}
	
	@Override
	public List<SitAcquaUtenze> getListaUtenzeByCodFis(String codFiscale)
			throws AcquaServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitAcquaUtenze.getUtenzeByCF").setParameter(
					"codFisc", codFiscale);
			return q.getResultList();

		} catch (Throwable t) {
			throw new AcquaServiceException(t);
		}
	}
	
	@Override
	public List<SitAcquaUtenze> getListaUtenzeByPIva(String pIva)
			throws AcquaServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitAcquaUtenze.getUtenzeByPI").setParameter(
					"pIva", pIva);
			return q.getResultList();

		} catch (Throwable t) {
			throw new AcquaServiceException(t);
		}
	}

	@Override
	public List<SitAcquaCatasto> getListaCatastoByCodServizio(String codServizio)
			throws AcquaServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitAcquaCatasto.getCatastoByCodServizio").setParameter(
					"codServizio", codServizio);
			return q.getResultList();

		} catch (Throwable t) {
			throw new AcquaServiceException(t);
		}
	}

}
