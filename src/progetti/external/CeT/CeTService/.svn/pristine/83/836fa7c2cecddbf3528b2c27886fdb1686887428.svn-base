package it.webred.ct.data.access.basic.centriestivi;

import it.webred.ct.data.model.centriestivi.SitCresCentro;
import it.webred.ct.data.model.centriestivi.SitCresFasciaEta;
import it.webred.ct.data.model.centriestivi.SitCresFermata;
import it.webred.ct.data.model.centriestivi.SitCresTurno;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface CentriEstiviService {

	public List<SitCresFasciaEta> getListaFasciaEta(CentriEstiviDataIn dataIn)
			throws CentriEstiviServiceException;

	public List<SitCresCentro> getListaCentriByFascia(CentriEstiviDataIn dataIn)
			throws CentriEstiviServiceException;

	public List<SitCresTurno> getListaTurniByCentro(CentriEstiviDataIn dataIn)
			throws CentriEstiviServiceException;

	public List<SitCresFermata> getListaFermateByCentro(CentriEstiviDataIn dataIn)
			throws CentriEstiviServiceException;

}
