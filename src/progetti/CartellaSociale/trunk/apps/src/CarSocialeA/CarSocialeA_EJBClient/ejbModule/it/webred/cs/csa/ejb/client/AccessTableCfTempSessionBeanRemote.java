package it.webred.cs.csa.ejb.client;

import java.util.List;

import javax.ejb.Remote;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsCfTemp;

@Remote
public interface AccessTableCfTempSessionBeanRemote {
	
		
	public CsCfTemp findCfTempById(BaseDTO dto)throws Exception;
	
	public List<CsCfTemp> findCfTempByCf(BaseDTO dto)throws Exception;		
	
	public CsCfTemp generateNewCfTemp(BaseDTO dto) throws Exception;
	
	public CsCfTemp saveCfTemp(BaseDTO dto) throws Exception;

	//public String generateCfTempStringByAttribs(BaseDTO dto);

	public List<CsCfTemp> findCfTempByCfTemp(BaseDTO dto) throws Exception;
	
	public List<CsCfTemp> findCfTempByCfTempNotReplaced(BaseDTO dto) throws Exception;

	public List<CsCfTemp> findCfTempByAttribs(BaseDTO dto) throws Exception;

	

}
