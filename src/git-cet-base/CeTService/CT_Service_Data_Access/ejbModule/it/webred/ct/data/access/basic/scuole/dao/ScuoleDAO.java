package it.webred.ct.data.access.basic.scuole.dao;

import it.webred.ct.data.access.basic.scuole.ScuoleDataIn;
import it.webred.ct.data.access.basic.scuole.ScuoleServiceException;
import it.webred.ct.data.access.basic.traffico.TrafficoServiceException;
import it.webred.ct.data.access.basic.traffico.dto.TrafficoSearchCriteria;
import it.webred.ct.data.model.scuole.SitSclClassi;
import it.webred.ct.data.model.scuole.SitSclIstituti;
import it.webred.ct.data.model.traffico.SitTrffMulte;

import java.math.BigDecimal;
import java.util.List;

public interface ScuoleDAO {

	public List<SitSclIstituti> getListaIstitutiByTipo(String tipoIstituto)
			throws ScuoleServiceException;

	public List<SitSclClassi> getListaClassiByIstituto(String codIstituto)
			throws ScuoleServiceException;

	public List<String> getListaAnniByIstituto(String codIstituto)
			throws ScuoleServiceException;

	public List<String> getListaSezioniByIstitutoAnno(String codIstituto, BigDecimal anno)
			throws ScuoleServiceException;

	public List<SitSclIstituti> getListaIstitutiByIscrizione(String tipoIscrizione)
			throws ScuoleServiceException;

	public List<String> getListaTipiIstitutoByIscrizione(String tipoiscrizione)
			throws ScuoleServiceException;

	public List<String> getListaTipiIstituto()
			throws ScuoleServiceException;
	
}
