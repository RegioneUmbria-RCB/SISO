package it.webred.ct.config.parameters;

import java.math.BigDecimal;
import java.util.List;

import it.webred.ct.config.model.AmFonteTipoinfo;
import it.webred.ct.config.model.AmKeyValue;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.model.AmSection;
import it.webred.ct.config.parameters.dto.AmKeyValueDTO;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;

import javax.ejb.Remote;
import javax.persistence.EntityManager;

@Remote
public interface ParameterService {

	public List<AmKeyValueDTO> getListaKeyValue(ParameterSearchCriteria criteria);

	public List<AmKeyValueExt> getListaKeyValueExt99(Integer idFonte, String applicationName, EntityManager manager);
	
	public List<AmKeyValue> getListaToOverwrite(String application);
	
	public AmKeyValueExt saveParam(AmKeyValueDTO dto);

	public List<AmKeyValueDTO> updateSelect(List<AmKeyValueDTO> listaDTO,
			String selected, int index);
	
	public AmFonteTipoinfo getFonteTipoInfo(Integer idFonte, BigDecimal prog);
	
	public AmSection getAmSection(String name, String application);
	
	public AmKeyValueExt getAmKeyValueExtByKeyFonteComune(String key, String comune,
			String fonte);
	
	public List<AmKeyValueExt> getAmKeyValueExtByComune(String comune);
	
	public AmKeyValueExt getAmKeyValueExt (ParameterSearchCriteria criteria);
	
	public String getVersionePiattaforma();
	
	public String getVersioneSmartWelfare();
}
