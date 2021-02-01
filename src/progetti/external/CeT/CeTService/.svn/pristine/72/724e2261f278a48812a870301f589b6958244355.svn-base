package it.webred.ct.data.access.basic.processi;

import java.util.List;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.common.CommonService;
import it.webred.ct.data.access.basic.common.dao.CommonDAO;
import it.webred.ct.data.access.basic.processi.dao.ProcessiDAO;
import it.webred.ct.data.model.common.SitEnte;
import it.webred.ct.data.model.processi.SitSintesiProcessi;
import it.webred.ct.support.datarouter.CeTBaseObject;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class ProcessiServiceBean extends CTServiceBaseBean implements ProcessiService {
	
	@Autowired
	private ProcessiDAO processiDAO;

	@Override
	public List<SitSintesiProcessi> getSintesiprocessiByProcessId(
			ProcessiDataIn dataIn) {
		return processiDAO.getSintesiprocessiByProcessId(dataIn.getProcessId());
	}
}
