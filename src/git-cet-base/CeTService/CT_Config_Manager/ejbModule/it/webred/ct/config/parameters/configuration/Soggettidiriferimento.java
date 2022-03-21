package it.webred.ct.config.parameters.configuration;

import it.webred.ct.config.parameters.configuration.common.FonteTipoinfoValue;
import it.webred.ct.config.parameters.dto.AmKeyValueDTO;
import it.webred.ct.config.parameters.dto.AssistedValueDTO;

import java.util.List;

import javax.persistence.EntityManager;

public class Soggettidiriferimento {

	public List<AssistedValueDTO> getListaAssistedValue(EntityManager manager,
			String value) {

		FonteTipoinfoValue classeMappatura = new FonteTipoinfoValue();
		return classeMappatura.getListaAssistedValue(manager, value);
		
	}

	public void updateSelect(EntityManager manager,
			AmKeyValueDTO dto, int index) {

		FonteTipoinfoValue classeMappatura = new FonteTipoinfoValue();
		classeMappatura.updateSelect(manager, dto, index);
		
	}
	
	public String setAssistedValue(List<AssistedValueDTO> listaValue) {

		FonteTipoinfoValue classeMappatura = new FonteTipoinfoValue();
		return classeMappatura.setAssistedValue(listaValue);
		
	}
}
