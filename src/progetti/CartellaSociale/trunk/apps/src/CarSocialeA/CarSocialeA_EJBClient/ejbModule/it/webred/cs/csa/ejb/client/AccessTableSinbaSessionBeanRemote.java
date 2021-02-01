package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsDSinba;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableSinbaSessionBeanRemote {

	
	public CsDSinba getSchedaSinbaById(BaseDTO dto) throws Exception;

	public CsDSinba salvaSchedaSinba(BaseDTO dto) throws Exception;
	
	public List<CsDSinba> getListaSchedaSinbaByCaso(BaseDTO dto) throws Exception;

	public void deleteSchedaSinba(BaseDTO b) throws Exception;
	
	public List<String> findCodiciPrestazione(BaseDTO b);
	

}