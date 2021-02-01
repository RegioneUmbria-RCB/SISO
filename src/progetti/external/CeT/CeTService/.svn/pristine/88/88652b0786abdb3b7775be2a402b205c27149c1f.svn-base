package it.webred.ct.config.parameters.comune;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmFonte;
import it.webred.ct.config.model.AmFonteComune;
import it.webred.ct.config.model.AmInstance;
import it.webred.ct.config.model.AmInstanceComune;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ComuneService extends ParameterService{
	
	public List<KeyValueDTO> getListaComuneByUsername(String username, boolean visMissingParam);
	
	public List<AmComune> getListaComuneByUsername(String user);

	public List<AmComune> getListaComune();
	
	public List<AmComune> getListaComuneByData(Date data);
	
	public List<KeyValueDTO> getListaInstanceByComune(String comune, boolean visMissingParam);
	
	public List<KeyValueDTO> getListaFonteByComune(String comune, boolean visMissingParam);
	
	public List<AmFonteComune> getListaFonteByComune(String comune);
	
	public List<AmInstance> getListaInstanceByUsername(String username);
	
	public AmInstance getInstanceByname(String instance);
	
	public AmComune getComune(String belfiore);
		
	public AmInstanceComune getInstanceComuneByComuneInstance(String comune, String instance);
	
	public AmFonteComune getFonteComuneByComuneFonte(String comune, Integer fonte);
	
	public AmFonteComune getFonteComuneByComuneCodiceFonte(String comune, String codFonte);
	
	public List<AmFonte> getListaFonte();
	
	public void saveComune(AmComune comune);
	
	public void saveInstanceComune(AmInstanceComune instanceComune);
		
	public void saveFonteComune(AmFonteComune fonteComune);
	
	public void deleteInstanceComune(AmInstanceComune instanceComune);
	
	public void deleteFonteComune(AmFonteComune fonteComune);
			
}
