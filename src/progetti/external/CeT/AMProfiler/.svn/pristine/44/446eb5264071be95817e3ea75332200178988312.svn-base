package it.webred.AMProfiler.parameters.instance;

import it.webred.ct.config.model.AmKeyValue;
import it.webred.ct.config.parameters.dto.AmKeyValueDTO;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class InstanceBean extends InstanceBaseBean {

	private String labelApplicationSelected;
	private List<SelectItem> listaApplication = new ArrayList<SelectItem>();
	private String labelInstanceSelected;
	private List<SelectItem> listaInstance = new ArrayList<SelectItem>();
	private List<SelectItem> listaInstanceOverw = new ArrayList<SelectItem>();
	private AmKeyValueDTO paramOverw = new AmKeyValueDTO();
	private String labelParamOverw;
	private List<AmKeyValueDTO> listaKeyValue = new ArrayList<AmKeyValueDTO>();
	private String keySelected;
	private String keyIndex;

	public void fillItemsApplication() {

		listaApplication = new ArrayList<SelectItem>();
		String msg = "listaapplication";

		try {

			List<KeyValueDTO> lista = applicationService.getListaApplication();
			for (KeyValueDTO kv : lista)
				listaApplication
						.add(new SelectItem(kv.getKey(), kv.getValue()));

			if (listaApplication.size() > 0) {
				labelApplicationSelected = (String) listaApplication.get(0)
						.getValue();
				fillItemsInstance();
			}

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void fillItemsInstance() {

		listaInstance = new ArrayList<SelectItem>();
		String msg = "listainstance";

		try {

			List<KeyValueDTO> lista = applicationService
					.getListaInstanceByApplicationUsername(labelApplicationSelected, getRequest().getUserPrincipal().getName());
			for (KeyValueDTO kv : lista)
				listaInstance.add(new SelectItem(kv.getKey(), kv.getValue()));

			if (listaInstance.size() > 0) {
				labelInstanceSelected = (String) listaInstance.get(0)
						.getValue();
				doCaricaParametri();
				fillItemsInstanceOverw();
			} else
				listaKeyValue = new ArrayList<AmKeyValueDTO>();

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void fillItemsInstanceOverw() {

		listaInstanceOverw = new ArrayList<SelectItem>();
		String msg = "listainstance";

		try {

			for (AmKeyValue kv : applicationService
					.getListaToOverwrite(labelApplicationSelected)) {

				listaInstanceOverw.add(new SelectItem(kv.getKey()));

			}

			if (listaInstanceOverw.size() > 0) {
				labelParamOverw = (String) listaInstanceOverw.get(0).getValue();
				doCaricaParametroOverw();
			}

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void doCaricaParametri() {

		String msg = "listaparametri";

		try {

			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setType("1");
			criteria.setInstance(labelInstanceSelected);
			listaKeyValue = applicationService.getListaKeyValue(criteria);

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void doCaricaParametroOverw() {

		String msg = "listaparametri";

		try {

			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setType("0");
			criteria.setApplicationKv(labelApplicationSelected);
			criteria.setKey(labelParamOverw);
			List<AmKeyValueDTO> lista = applicationService
					.getListaKeyValue(criteria);

			if (lista.size() > 0) {
				paramOverw = lista.get(0);
			}

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void doUpdateSelect() {

		String msg = "updateselect";

		try {

			listaKeyValue = applicationService.updateSelect(listaKeyValue,
					keySelected, new Integer(keyIndex).intValue());

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void doSaveParam() {

		String msg = "salva";

		try {

			for (AmKeyValueDTO dto : listaKeyValue) {

				dto.getAmKeyValueExt().getAmInstance().setName(
						labelInstanceSelected);
				dto.setAmKeyValueExt(applicationService.saveParam(dto));
				if (dto.isShowDefault()
						&& !"".equals(dto.getAmKeyValueExt().getValueConf()))
					dto.setShowDefault(false);
				if (dto.getAmKeyValueExt().getValueConf() == null
						&& dto.getDefaultValue() != null) {
					dto.setShowDefault(true);
					dto.getAmKeyValueExt().setValueConf(dto.getDefaultValue());
				}

			}

			// update liste
			List<KeyValueDTO> lista = applicationService.getListaApplication();
			for (KeyValueDTO kv : lista)
				listaApplication
						.add(new SelectItem(kv.getKey(), kv.getValue()));
			lista = applicationService
					.getListaInstanceByApplicationUsername(labelApplicationSelected, getRequest().getUserPrincipal().getName());
			for (KeyValueDTO kv : lista)
				listaInstance.add(new SelectItem(kv.getKey(), kv.getValue()));

			super.addInfoMessage(msg);
		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}
	}

	public void doSaveParamOverw() {

		String msg = "salva";

		try {

			paramOverw.getAmKeyValueExt().getAmInstance().setName(
					labelInstanceSelected);
			paramOverw.getAmKeyValueExt().getAmComune().setBelfiore(null);
			paramOverw.setAmKeyValueExt(applicationService
					.saveParam(paramOverw));

			super.addInfoMessage(msg);
		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}
	}

	public List<AmKeyValueDTO> getListaKeyValue() {
		return listaKeyValue;
	}

	public void setListaKeyValue(List<AmKeyValueDTO> listaKeyValue) {
		this.listaKeyValue = listaKeyValue;
	}

	public void setListaInstance(List<SelectItem> listaInstance) {
		this.listaInstance = listaInstance;
	}

	public List<SelectItem> getListaInstance() {
		return listaInstance;
	}

	public String getLabelInstanceSelected() {
		return labelInstanceSelected;
	}

	public void setLabelInstanceSelected(String labelInstanceSelected) {
		this.labelInstanceSelected = labelInstanceSelected;
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

	public List<SelectItem> getListaInstanceOverw() {
		return listaInstanceOverw;
	}

	public void setListaInstanceOverw(List<SelectItem> listaInstanceOverw) {
		this.listaInstanceOverw = listaInstanceOverw;
	}

	public AmKeyValueDTO getParamOverw() {
		return paramOverw;
	}

	public void setParamOverw(AmKeyValueDTO paramOverw) {
		this.paramOverw = paramOverw == null ? new AmKeyValueDTO() : paramOverw;
	}

	public String getLabelParamOverw() {
		return labelParamOverw;
	}

	public void setLabelParamOverw(String labelParamOverw) {
		this.labelParamOverw = labelParamOverw;
	}

}
