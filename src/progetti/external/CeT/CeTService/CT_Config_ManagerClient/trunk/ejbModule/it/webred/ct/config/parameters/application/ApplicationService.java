package it.webred.ct.config.parameters.application;

import it.webred.ct.config.model.AmFonte;
import it.webred.ct.config.model.AmInstance;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ApplicationService extends ParameterService{
	
	public List<KeyValueDTO> getListaApplication();

	public List<KeyValueDTO> getListaInstanceByApplicationUsername(String application, String username);

	public List<AmFonte> getListaFonte(String application);
		
	public AmInstance getInstanceByApplicationComune(String application, String comune);
	
	public AmInstance getInstanceByName(String name);

	public void saveInstance(AmInstance instance);
	
	public void updateInstance(AmInstance instance);
	
	public String getUrlApplication(String username, String ente, String appName);
}
