package it.webred.ct.data.access.basic.traffico;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.traffico.dao.TrafficoDAO;
import it.webred.ct.data.access.basic.traffico.dto.TrafficoSearchCriteria;
import it.webred.ct.data.model.traffico.SitTrffMulte;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class TrafficoServiceBean extends CTServiceBaseBean implements
		TrafficoService {

	@Autowired
	private TrafficoDAO trafficoDAO;

	@Override
	public List<SitTrffMulte> getListaMulteByCriteria(TrafficoDataIn dataIn)
			throws TrafficoServiceException {
		// se non c'è filtro torno vuoto
		if (dataIn.getObj() == null && dataIn.getObj2() == null
				&& dataIn.getObj3() == null && dataIn.getObj4() == null
				&& dataIn.getObj5() == null && dataIn.getObj6() == null)
			return new ArrayList<SitTrffMulte>();
		TrafficoSearchCriteria criteria = new TrafficoSearchCriteria();
		if (dataIn.getObj() != null)
			criteria.setCodFiscale((String) dataIn.getObj());
		if (dataIn.getObj2() != null)
			criteria.setNumVerbale((String) dataIn.getObj2());
		if (dataIn.getObj3() != null)
			criteria.setTarga((String) dataIn.getObj3());
		if (dataIn.getObj4() != null)
			criteria.setNome((String) dataIn.getObj4());
		if (dataIn.getObj5() != null)
			criteria.setCognome((String) dataIn.getObj5());
		if (dataIn.getObj6() != null)
			criteria.setIdOrig((String) dataIn.getObj6());
		return trafficoDAO.getListaMulteByCriteria(criteria);
	}

	@Override
	public boolean checkMulta(TrafficoDataIn dataIn)
			throws TrafficoServiceException {
		return true;
	}


}
