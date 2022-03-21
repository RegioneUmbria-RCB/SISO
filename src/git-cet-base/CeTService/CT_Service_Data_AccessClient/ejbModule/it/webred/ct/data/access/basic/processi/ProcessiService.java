package it.webred.ct.data.access.basic.processi;

import java.util.List;

import it.webred.ct.data.model.processi.SitSintesiProcessi;

import javax.ejb.Remote;

@Remote
public interface ProcessiService {
	
	public List<SitSintesiProcessi> getSintesiprocessiByProcessId(ProcessiDataIn dataIn);

}
