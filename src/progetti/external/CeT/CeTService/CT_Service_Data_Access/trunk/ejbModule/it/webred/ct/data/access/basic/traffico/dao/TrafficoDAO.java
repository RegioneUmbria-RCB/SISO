package it.webred.ct.data.access.basic.traffico.dao;

import it.webred.ct.data.access.basic.traffico.TrafficoServiceException;
import it.webred.ct.data.access.basic.traffico.dto.TrafficoSearchCriteria;
import it.webred.ct.data.model.traffico.SitTrffMulte;

import java.util.List;

public interface TrafficoDAO {

	public List<SitTrffMulte> getListaMulteByCriteria(TrafficoSearchCriteria criteria)
			throws TrafficoServiceException;
	
}
