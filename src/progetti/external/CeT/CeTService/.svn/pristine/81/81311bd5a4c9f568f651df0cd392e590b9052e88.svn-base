package it.webred.ct.data.access.basic.centriestivi;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.centriestivi.dao.CentriEstiviDAO;
import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;
import it.webred.ct.data.access.basic.scuole.dao.ScuoleDAO;
import it.webred.ct.data.model.centriestivi.SitCresCentro;
import it.webred.ct.data.model.centriestivi.SitCresFasciaEta;
import it.webred.ct.data.model.centriestivi.SitCresFermata;
import it.webred.ct.data.model.centriestivi.SitCresTurno;
import it.webred.ct.data.model.scuole.SitSclClassi;
import it.webred.ct.data.model.scuole.SitSclIstituti;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class CentriEstiviServiceBean extends CTServiceBaseBean implements
		CentriEstiviService {

	@Autowired
	private CentriEstiviDAO centriEstiviDAO;

	@Override
	public List<SitCresFasciaEta> getListaFasciaEta(CentriEstiviDataIn dataIn)
			throws CentriEstiviServiceException {

		return centriEstiviDAO.getListaFasciaEta();
	}

	@Override
	public List<SitCresCentro> getListaCentriByFascia(CentriEstiviDataIn dataIn)
			throws CentriEstiviServiceException {

		return centriEstiviDAO.getListaCentriByFascia(dataIn.getIdFascia());
	}

	@Override
	public List<SitCresTurno> getListaTurniByCentro(CentriEstiviDataIn dataIn)
			throws CentriEstiviServiceException {
		
		return centriEstiviDAO.getListaTurniByCentro(dataIn.getIdCentro());
		
	}
	
	@Override
	public List<SitCresFermata> getListaFermateByCentro(CentriEstiviDataIn dataIn)
			throws CentriEstiviServiceException {
		
		return centriEstiviDAO.getListaFermateByCentro(dataIn.getIdCentro());
		
	}
}
