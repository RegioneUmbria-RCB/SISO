package it.webred.AMProfiler.parameters.application;

import it.webred.ct.config.model.AmFonte;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.dto.AmKeyValueDTO;
import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class ApplicationBean extends ApplicationBaseBean {

	private String labelApplicationSelected;
	private List<SelectItem> listaApplication = new ArrayList<SelectItem>();
	private String labelFonteSelected;
	private List<SelectItem> listaFonte = new ArrayList<SelectItem>();
	private List<AmKeyValueExt> listaKeyValueFonte = new ArrayList<AmKeyValueExt>();
	private List<AmKeyValueDTO> listaKeyValueApplication = new ArrayList<AmKeyValueDTO>();
	private String keySelected;
	private String keyIndex;

	public void fillItemsApplication() {

		listaApplication = new ArrayList<SelectItem>();
		String msg = "listaapplication";

		try {

			List<KeyValueDTO> lista = applicationService.getListaApplication();
			for(KeyValueDTO kv: lista)
				listaApplication.add(new SelectItem(kv.getKey(), kv.getValue()));

			if (listaApplication.size() > 0) {
				labelApplicationSelected = (String) listaApplication.get(0)
						.getValue();
				doCaricaParametriApplication();
				fillItemsFonte();
			}

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			t.printStackTrace();
		}

	}

	public void fillItemsFonte() {

		listaFonte = new ArrayList<SelectItem>();
		String msg = "listafonte";

		try {

			List<AmFonte> lista = applicationService
					.getListaFonte(labelApplicationSelected);

			for (AmFonte fonte : lista) {
				listaFonte.add(new SelectItem(fonte.getId(), fonte
						.getDescrizione()));
			}

			if (listaFonte.size() > 0) {
				labelFonteSelected = ((Integer) listaFonte.get(0).getValue())
						.toString();
				doCaricaParametriFonte();
			} else
				listaKeyValueFonte = new ArrayList<AmKeyValueExt>();

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void doCaricaParametriApplication() {

		String msg = "listaparametri";

		try {

			/*listaKeyValueApplication = applicationService
					.getListaToOverwrite(labelApplicationSelected);*/
			
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setType("0");
			criteria.setApplicationKv(labelApplicationSelected);
			listaKeyValueApplication = applicationService
					.getListaKeyValue(criteria);
			
			fillItemsFonte();
			
		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void doCaricaParametriFonte() {

		String msg = "listaparametri";

		try {

			listaKeyValueFonte = applicationService.getListaKeyValueExt99(
					new Integer(labelFonteSelected), labelApplicationSelected,
					null);

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public String getLabelApplicationSelected() {
		return labelApplicationSelected;
	}

	public void setLabelApplicationSelected(String labelApplicationSelected) {
		this.labelApplicationSelected = labelApplicationSelected;
	}

	public List<SelectItem> getListaApplication() {
		if (listaApplication.size() == 0)
			fillItemsApplication();
		return listaApplication;
	}

	public void setListaApplication(List<SelectItem> listaApplication) {
		this.listaApplication = listaApplication;
	}

	public String getLabelFonteSelected() {
		return labelFonteSelected;
	}

	public void setLabelFonteSelected(String labelFonteSelected) {
		this.labelFonteSelected = labelFonteSelected;
	}

	public List<SelectItem> getListaFonte() {
		return listaFonte;
	}

	public void setListaFonte(List<SelectItem> listaFonte) {
		this.listaFonte = listaFonte;
	}

	public List<AmKeyValueExt> getListaKeyValueFonte() {
		return listaKeyValueFonte;
	}

	public void setListaKeyValueFonte(List<AmKeyValueExt> listaKeyValueFonte) {
		this.listaKeyValueFonte = listaKeyValueFonte;
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

	public List<AmKeyValueDTO> getListaKeyValueApplication() {
		return listaKeyValueApplication;
	}

	public void setListaKeyValueApplication(
			List<AmKeyValueDTO> listaKeyValueApplication) {
		this.listaKeyValueApplication = listaKeyValueApplication;
	}

}
