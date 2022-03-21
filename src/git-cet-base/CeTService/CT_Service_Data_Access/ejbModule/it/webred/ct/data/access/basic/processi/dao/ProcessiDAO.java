package it.webred.ct.data.access.basic.processi.dao;

import java.util.List;

import it.webred.ct.data.model.processi.SitSintesiProcessi;

public interface ProcessiDAO {
	
	public List<SitSintesiProcessi> getSintesiprocessiByProcessId(String processId);

}
