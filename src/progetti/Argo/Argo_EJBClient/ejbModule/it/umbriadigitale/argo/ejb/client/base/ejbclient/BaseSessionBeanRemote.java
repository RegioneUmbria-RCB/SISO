package it.umbriadigitale.argo.ejb.client.base.ejbclient;


import it.umbriadigitale.argo.ejb.client.base.dto.pass.SpazioDTO;
import it.umbriadigitale.argo.ejb.client.base.dto.retvalue.SpazioRetvalDTO;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface BaseSessionBeanRemote {
	public void addSpazio(SpazioDTO dto) throws Exception;
	
	public List<SpazioRetvalDTO> getSpazioByBelfiore(SpazioDTO dto)throws Exception;
	

}
