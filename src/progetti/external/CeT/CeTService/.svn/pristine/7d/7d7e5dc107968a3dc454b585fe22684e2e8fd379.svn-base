package it.webred.ct.data.access.basic.scuole.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.rette.RetteServiceException;
import it.webred.ct.data.access.basic.scuole.ScuoleServiceException;
import it.webred.ct.data.model.scuole.SitSclClassi;
import it.webred.ct.data.model.scuole.SitSclIstituti;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class ScuoleJPAImpl extends CTServiceBaseDAO implements ScuoleDAO {
	
	@Override
	public List<SitSclIstituti> getListaIstitutiByTipo(String tipoIstituto)
			throws ScuoleServiceException {

		try {

			Query q = manager_diogene.createNamedQuery(
					"SitSclIstituti.getIstitutiByTipo").setParameter("tipo", tipoIstituto);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RetteServiceException(t);
		}

	}
	
	@Override
	public List<String> getListaTipiIstitutoByIscrizione(String tipoiscrizione)
			throws ScuoleServiceException {
		
		try {

			String sql = (new ScuoleQueryBuilder()).createQueryTipiIstitutiByTipoIscrizione(tipoiscrizione);
			Query q = manager_diogene.createQuery(sql);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RetteServiceException(t);
		}
	}
	
	@Override
	public List<String> getListaTipiIstituto()
			throws ScuoleServiceException {
		
		try {

			String sql = (new ScuoleQueryBuilder()).createQueryTipiIstituti();
			Query q = manager_diogene.createQuery(sql);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RetteServiceException(t);
		}
	}
	
	@Override
	public List<SitSclIstituti> getListaIstitutiByIscrizione(String tipoiscrizione)
			throws ScuoleServiceException {
		
		try {

			String sql = (new ScuoleQueryBuilder()).createQueryIstitutiByTipoIscrizione(tipoiscrizione);
			Query q = manager_diogene.createQuery(sql);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RetteServiceException(t);
		}
	}
	
	@Override
	public List<SitSclClassi> getListaClassiByIstituto(String codIstituto)
			throws ScuoleServiceException {

		try {

			Query q = manager_diogene.createNamedQuery(
					"SitSclClassi.getClassiByIstituto").setParameter("istituto", codIstituto);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RetteServiceException(t);
		}

	}
	
	@Override
	public List<String> getListaAnniByIstituto(String codIstituto)
			throws ScuoleServiceException {

		List<String> listaAnni = new ArrayList<String>();
		
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitSclClassi.getAnniByIstituto").setParameter("istituto", codIstituto);
			List<Object> lista = q.getResultList();
			for(Object anno: lista){
				listaAnni.add(((BigDecimal) anno).toString());
			}

			return listaAnni;
			
		} catch (Throwable t) {
			throw new RetteServiceException(t);
		}

	}
	
	@Override
	public List<String> getListaSezioniByIstitutoAnno(String codIstituto, BigDecimal anno)
			throws ScuoleServiceException {

		try {

			Query q = manager_diogene.createNamedQuery(
					"SitSclClassi.getSezioniByIstitutoAnno");
			q.setParameter("istituto", codIstituto);
			q.setParameter("anno", anno);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RetteServiceException(t);
		}

	}

}
