package it.webred.AMProfiler.parameters.globale;

import it.webred.ct.config.parameters.dto.AmKeyValueDTO;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class GlobaleBean extends GlobaleBaseBean {


	private List<AmKeyValueDTO> listaKeyValue = new ArrayList<AmKeyValueDTO>();
	private String keySelected;
	private String keyIndex;

	public void doCaricaParametri() {

		String msg = "listaparametri";

		try {
			
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setType("0");
			listaKeyValue = amprofilerService
					.getListaKeyValue(criteria);
						
		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}
	
	public void doUpdateSelect() {
		
		String msg = "updateselect";
		
		try {

			listaKeyValue = amprofilerService.updateSelect(listaKeyValue, keySelected, new Integer(keyIndex).intValue());

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}
		
	}

	public void doSaveParam() {

		String msg = "salva";

		try {

			for (AmKeyValueDTO dto : listaKeyValue) {
				
				dto.setAmKeyValueExt(amprofilerService.saveParam(dto));
				if(dto.isShowDefault() && !"".equals(dto.getAmKeyValueExt().getValueConf()))
					dto.setShowDefault(false);
				if(dto.getAmKeyValueExt().getValueConf() == null && dto.getDefaultValue() != null){
					dto.setShowDefault(true);
					dto.getAmKeyValueExt().setValueConf(dto.getDefaultValue());
				}
			
			}

			super.addInfoMessage(msg);
		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}
	}

	public List<AmKeyValueDTO> getListaKeyValue() {
		doCaricaParametri();
		return listaKeyValue;
	}

	public void setListaKeyValue(List<AmKeyValueDTO> listaKeyValue) {
		this.listaKeyValue = listaKeyValue;
	}

	public String getKeySelected() {
		return keySelected;
	}

	public void setKeySelected(String keySelected) {
		this.keySelected = keySelected;
	}

	public String getKeyIndex() {
		return keyIndex;
	}

	public void setKeyIndex(String keyIndex) {
		this.keyIndex = keyIndex;
	}

}
