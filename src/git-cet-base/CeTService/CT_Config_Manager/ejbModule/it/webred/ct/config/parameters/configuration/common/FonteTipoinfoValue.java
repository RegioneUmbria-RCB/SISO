package it.webred.ct.config.parameters.configuration.common;

import it.webred.ct.config.parameters.application.ApplicationServiceException;
import it.webred.ct.config.parameters.dto.AmKeyValueDTO;
import it.webred.ct.config.parameters.dto.AssistedValueDTO;
import it.webred.ct.config.model.AmFonte;
import it.webred.ct.config.model.AmFonteTipoinfo;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class FonteTipoinfoValue {

	public List<AssistedValueDTO> getListaAssistedValue(EntityManager manager,
			String value) {

		List<AssistedValueDTO> listaAssistedValue = new ArrayList<AssistedValueDTO>();
		AssistedValueDTO av = new AssistedValueDTO();
		AssistedValueDTO av2 = new AssistedValueDTO();
		List<SelectItem> listaSelectItem = new ArrayList<SelectItem>();
		listaSelectItem.add(new SelectItem(""));
		List<SelectItem> listaSelectItem2 = new ArrayList<SelectItem>();
		listaSelectItem2.add(new SelectItem(""));

		// recupero i valori del DB (es. CATASTO|IMMOBILI)
		String sub = "";
		String sub2 = "";
		int idSub = 0;
		if (value != null) {
			int pipe = value.indexOf("|");
			sub = value.substring(0, pipe);
			sub2 = value.substring(pipe + 1);
		}

		try {

			Query q = manager.createNamedQuery("AmFonte.getFonte");
			List<AmFonte> result = q.getResultList();
			for (AmFonte rs : result) {
				SelectItem item = new SelectItem(rs.getDescrizione());
				listaSelectItem.add(item);
				if(rs.getDescrizione().equals(sub))
					idSub = rs.getId();
			}
			av.setListaItem(listaSelectItem);
			av.setLabel("Fonte");
			av.setSelected(sub);

			if (result.size() > 0) {
				q = manager
						.createNamedQuery("AmFonteTipoinfo.getFonteTipoinfoByFonte");
				q.setParameter("fonte", idSub);
				List<AmFonteTipoinfo> result2 = q.getResultList();
				for (AmFonteTipoinfo rs : result2) {
					SelectItem item = new SelectItem(rs.getInformazione());
					listaSelectItem2.add(item);
				}
			}
			av2.setListaItem(listaSelectItem2);
			av2.setLabel("Informazione");
			av2.setSelected(sub2);

			listaAssistedValue.add(av);
			listaAssistedValue.add(av2);
			return listaAssistedValue;
		} catch (Throwable t) {
			throw new ApplicationServiceException(t);
		}
	}

	public void updateSelect(EntityManager manager,
			AmKeyValueDTO dto, int index) {

		if(index == 0 && !dto.getListaAssistedValue().get(0).getSelected().equals("")){
		
			List<SelectItem> listaSelectItem = new ArrayList<SelectItem>();
			listaSelectItem.add(new SelectItem(""));
	
			try{
			
				Query q = manager.createNamedQuery("AmFonte.getFonteByDescrizione");
				q.setParameter("descrizione", dto.getListaAssistedValue().get(0).getSelected());
				AmFonte result = (AmFonte) q.getSingleResult();
				
				q = manager
						.createNamedQuery("AmFonteTipoinfo.getFonteTipoinfoByFonte");
				q.setParameter("fonte", result.getId());
				List<AmFonteTipoinfo> result2 = q.getResultList();
				for (AmFonteTipoinfo rs : result2) {
					SelectItem item = new SelectItem(rs.getInformazione());
					listaSelectItem.add(item);
				}
				dto.getListaAssistedValue().get(1).setSelected("");
				dto.getListaAssistedValue().get(1).setListaItem(listaSelectItem);
	
			} catch (Throwable t) {
				throw new ApplicationServiceException(t);
			}
		}
		
	}
	
	public String setAssistedValue(List<AssistedValueDTO> listaValue){
		
		String value = "";
		
		//ricompongo le select nel formato originale (es. CATASTO|IMMOBILI)
		for(AssistedValueDTO dto: listaValue){
			if(!"".equals(dto.getSelected()))
				value += "|" + dto.getSelected();
		}
		
		return value.equals("")? value : value.substring(1);
		
	}
	
}
