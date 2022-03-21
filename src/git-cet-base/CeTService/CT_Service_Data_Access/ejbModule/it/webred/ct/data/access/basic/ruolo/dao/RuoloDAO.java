package it.webred.ct.data.access.basic.ruolo.dao;

import it.webred.ct.data.access.basic.ruolo.RuoloServiceException;
import it.webred.ct.data.access.basic.ruolo.tarsu.RTarsuServiceException;
import it.webred.ct.data.model.ruolo.SitRuoloTarPdf;

import java.math.BigDecimal;
import java.util.List;

public interface RuoloDAO {
	
	List<SitRuoloTarPdf> getListaPdfByCfCu(String cf, BigDecimal cu)
			throws RuoloServiceException;

	List<SitRuoloTarPdf> getListaPdfByCf(String cf)
			throws RuoloServiceException;

}
