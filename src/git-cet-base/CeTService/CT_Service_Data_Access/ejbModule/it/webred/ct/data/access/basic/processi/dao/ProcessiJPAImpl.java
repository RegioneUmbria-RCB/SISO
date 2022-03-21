package it.webred.ct.data.access.basic.processi.dao;

import java.util.List;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.processi.ProcessiServiceException;
import it.webred.ct.data.model.processi.SitSintesiProcessi;

import javax.persistence.Query;

public class ProcessiJPAImpl extends CTServiceBaseDAO implements ProcessiDAO {
	
	public List<SitSintesiProcessi> getSintesiprocessiByProcessId(String processId){
		
		try{
			logger.debug("ESTRAZIONE SINTESI PROCESSI PER PROCESSID: " + processId);
			Query q = manager_diogene.createNamedQuery("SitSintesiProcessi.getSintesiProcessiByProcessId");
			q.setParameter("processId", processId);
			return q.getResultList();

		} catch (Throwable t) {
			logger.error("", t);
			throw new ProcessiServiceException(t);
		}
				
	}

}
