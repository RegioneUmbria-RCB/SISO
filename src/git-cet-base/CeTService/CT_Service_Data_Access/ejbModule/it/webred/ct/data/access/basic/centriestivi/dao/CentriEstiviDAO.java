package it.webred.ct.data.access.basic.centriestivi.dao;

import it.webred.ct.data.access.basic.centriestivi.CentriEstiviServiceException;
import it.webred.ct.data.model.centriestivi.SitCresCentro;
import it.webred.ct.data.model.centriestivi.SitCresFasciaEta;
import it.webred.ct.data.model.centriestivi.SitCresFermata;
import it.webred.ct.data.model.centriestivi.SitCresTurno;

import java.math.BigDecimal;
import java.util.List;

public interface CentriEstiviDAO {

	public List<SitCresFasciaEta> getListaFasciaEta()
			throws CentriEstiviServiceException;

	public List<SitCresCentro> getListaCentriByFascia(String idFascia)
			throws CentriEstiviServiceException;

	public List<SitCresTurno> getListaTurniByCentro(String idCentro)
			throws CentriEstiviServiceException;

	public List<SitCresFermata> getListaFermateByCentro(String idCentro)
			throws CentriEstiviServiceException;
	
}
