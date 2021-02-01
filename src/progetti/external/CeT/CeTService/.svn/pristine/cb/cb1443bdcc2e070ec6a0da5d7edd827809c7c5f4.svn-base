package it.webred.ct.data.access.basic.centriestivi.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.centriestivi.CentriEstiviServiceException;
import it.webred.ct.data.model.centriestivi.SitCresCentro;
import it.webred.ct.data.model.centriestivi.SitCresFasciaEta;
import it.webred.ct.data.model.centriestivi.SitCresFermata;
import it.webred.ct.data.model.centriestivi.SitCresTurno;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

public class CentriEstiviJPAImpl extends CTServiceBaseDAO implements
		CentriEstiviDAO {

	@Override
	public List<SitCresFasciaEta> getListaFasciaEta()
			throws CentriEstiviServiceException {

		try {

			Query q = manager_diogene
					.createNamedQuery("SitCresFasciaEta.getFasce");
			return q.getResultList();

		} catch (Throwable t) {
			throw new CentriEstiviServiceException(t);
		}

	}

	@Override
	public List<SitCresCentro> getListaCentriByFascia(String idFascia)
			throws CentriEstiviServiceException {

		try {

			Query q = manager_diogene
					.createNamedQuery("SitCresCentro.getCentriByFascia")
					.setParameter("fascia", idFascia)
					.setParameter("data", new Date());
			List<SitCresCentro> list = q.getResultList();
			if(list != null){
				for(SitCresCentro centro: list){
					if(centro.getSitCresTurnos() != null)
						centro.getSitCresTurnos().size();
				}
			}
				
			return list;

		} catch (Throwable t) {
			throw new CentriEstiviServiceException(t);
		}

	}

	@Override
	public List<SitCresTurno> getListaTurniByCentro(String idCentro)
			throws CentriEstiviServiceException {

		try {

			Query q = manager_diogene.createNamedQuery(
					"SitCresTurno.getTurniByCentro").setParameter("centro",
					idCentro);
			return q.getResultList();

		} catch (Throwable t) {
			throw new CentriEstiviServiceException(t);
		}

	}

	@Override
	public List<SitCresFermata> getListaFermateByCentro(String idCentro)
			throws CentriEstiviServiceException {

		try {
			if(idCentro == null){
				Query q = manager_diogene.createNamedQuery(
						"SitCresFermata.getFermate");
				return q.getResultList();
			}
			
			Query q = manager_diogene.createNamedQuery(
					"SitCresFermata.getFermateByCentro").setParameter("centro",
					idCentro);
			return q.getResultList();

		} catch (Throwable t) {
			throw new CentriEstiviServiceException(t);
		}

	}

}
