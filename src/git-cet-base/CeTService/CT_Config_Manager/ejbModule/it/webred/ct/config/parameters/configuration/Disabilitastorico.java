package it.webred.ct.config.parameters.configuration;

import it.webred.ct.config.model.AmFonte;
import it.webred.ct.config.model.AmFonteTipoinfo;
import it.webred.ct.config.parameters.application.ApplicationServiceException;
import it.webred.ct.config.parameters.configuration.common.FonteTipoinfoValue;
import it.webred.ct.config.parameters.dto.AmKeyValueDTO;
import it.webred.ct.config.parameters.dto.AssistedValueDTO;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class Disabilitastorico {

	public List<AssistedValueDTO> getListaAssistedValue(EntityManager manager,
			String value) {

		List<AssistedValueDTO> listaAssistedValue = new ArrayList<AssistedValueDTO>();
		AssistedValueDTO av = new AssistedValueDTO();
		List<SelectItem> listaSelectItem = new ArrayList<SelectItem>();
		
		listaSelectItem.add(new SelectItem(""));
		listaSelectItem.add(new SelectItem("0", "NO"));
		listaSelectItem.add(new SelectItem("1", "SI"));
		
		av.setSelected(value);
		av.setListaItem(listaSelectItem);
		listaAssistedValue.add(av);
		
		return listaAssistedValue;
		
	}
	
	public void updateSelect(EntityManager manager,
			AmKeyValueDTO dto, int index) {
		//do nothing
	}
	
	public String setAssistedValue(List<AssistedValueDTO> listaValue) {

		return listaValue.get(0).getSelected();
		
	}
}
