package it.webred.AMProfiler.parameters.comune;

import it.webred.ct.config.model.AmInstance;
import it.webred.ct.config.model.AmKeyValue;
import it.webred.ct.config.parameters.dto.AmKeyValueDTO;
import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class ComuneBean extends ComuneBaseBean {

	private String labelComuneSelected;
	private List<SelectItem> listaComune = new ArrayList<SelectItem>();
	private String labelInstanceComuneSelected;
	private List<SelectItem> listaInstanceComune = new ArrayList<SelectItem>();
	private String labelFonteComuneSelected;
	private List<SelectItem> listaInstanceOverw = new ArrayList<SelectItem>();
	private AmKeyValueDTO paramOverw = new AmKeyValueDTO();
	private String labelParamOverw;
	private List<SelectItem> listaFonteComune = new ArrayList<SelectItem>();
	private List<AmKeyValueDTO> listaKeyValueComune = new ArrayList<AmKeyValueDTO>();
	private List<AmKeyValueDTO> listaKeyValueInstanceComune = new ArrayList<AmKeyValueDTO>();
	private List<AmKeyValueDTO> listaKeyValueFonteComune = new ArrayList<AmKeyValueDTO>();
	private String keySelected;
	private String keyIndex;

	public void fillItemsComune() {
		
		listaComune = new ArrayList<SelectItem>();
		String msg = "listacomune";

		try {

			List<KeyValueDTO> lista = comuneService.getListaComuneByUsername(getRequest().getUserPrincipal().getName(), true);
			for (KeyValueDTO kv : lista)
				listaComune.add(new SelectItem(kv.getKey(), kv.getValue()));

			if (listaComune.size() > 0) {
				labelComuneSelected = (String) listaComune.get(0).getValue();
				fillItemsInstanceComune();
			}

			if (listaComune.size() > 0) {
				labelComuneSelected = (String) listaComune.get(0).getValue();
				fillItemsFonteComune();
			}

			if (listaComune.size() > 0) {
				labelComuneSelected = (String) listaComune.get(0).getValue();
				doCaricaParametriComune();
			}

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void fillItemsInstanceComune() {

		listaInstanceComune = new ArrayList<SelectItem>();
		String msg = "listainstance";

		try {

			List<KeyValueDTO> lista = comuneService.getListaInstanceByComune(
					labelComuneSelected, true);
			for (KeyValueDTO kv : lista)
				listaInstanceComune.add(new SelectItem(kv.getKey(), kv
						.getValue()));

			if (listaInstanceComune.size() > 0) {
				labelInstanceComuneSelected = (String) listaInstanceComune.get(
						0).getValue();
				doCaricaParametriInstanceComune();
			} else
				listaKeyValueInstanceComune = new ArrayList<AmKeyValueDTO>();

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void fillItemsFonteComune() {

		listaFonteComune = new ArrayList<SelectItem>();
		String msg = "listafonte";

		try {

			List<KeyValueDTO> lista = comuneService.getListaFonteByComune(
					labelComuneSelected, true);
			for(KeyValueDTO kv: lista)
				listaFonteComune.add(new SelectItem(kv.getKey(), kv.getValue()));

			if (listaFonteComune.size() > 0) {
				labelFonteComuneSelected = (String) listaFonteComune.get(0)
						.getValue();
				doCaricaParametriFonteComune();
			} else
				listaKeyValueFonteComune = new ArrayList<AmKeyValueDTO>();

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void fillItemsInstanceOverw() {

		listaInstanceOverw = new ArrayList<SelectItem>();
		String msg = "listainstance";

		try {

			AmInstance instance = comuneService
					.getInstanceByname(labelInstanceComuneSelected);

			for (AmKeyValue kv : comuneService.getListaToOverwrite(instance
					.getFkAmApplication())) {

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

	public void doCaricaParametriComune() {

		String msg = "listaparametri";

		try {

			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setType("2");
			criteria.setComune(labelComuneSelected);
			listaKeyValueComune = comuneService.getListaKeyValue(criteria);

			fillItemsInstanceComune();
			fillItemsFonteComune();

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void doCaricaParametriInstanceComune() {

		String msg = "listaparametri";

		try {

			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setType("3");
			criteria.setComune(labelComuneSelected);
			criteria.setInstance(labelInstanceComuneSelected);
			listaKeyValueInstanceComune = comuneService
					.getListaKeyValue(criteria);
			fillItemsInstanceOverw();

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void doCaricaParametriFonteComune() {

		String msg = "listaparametri";

		try {

			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setType("4");
			criteria.setComune(labelComuneSelected);
			criteria.setFonte(labelFonteComuneSelected);
			listaKeyValueFonteComune = comuneService.getListaKeyValue(criteria);

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void doCaricaParametroOverw() {

		String msg = "listaparametri";

		try {

			AmInstance instance = comuneService
					.getInstanceByname(labelInstanceComuneSelected);

			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setType("0");
			criteria.setApplicationKv(instance.getFkAmApplication());
			criteria.setKey(labelParamOverw);
			List<AmKeyValueDTO> lista = comuneService
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

			listaKeyValueComune = comuneService.updateSelect(
					listaKeyValueComune, keySelected, new Integer(keyIndex)
							.intValue());

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void doSaveParamComune() {

		String msg = "salva";

		try {

			for (AmKeyValueDTO dto : listaKeyValueComune) {

				dto.getAmKeyValueExt().getAmComune().setBelfiore(
						labelComuneSelected);
				dto.setAmKeyValueExt(comuneService.saveParam(dto));
				if (dto.isShowDefault()
						&& !"".equals(dto.getAmKeyValueExt().getValueConf()))
					dto.setShowDefault(false);
				if (dto.getAmKeyValueExt().getValueConf() == null
						&& dto.getDefaultValue() != null) {
					dto.setShowDefault(true);
					dto.getAmKeyValueExt().setValueConf(dto.getDefaultValue());
				}

			}

			// update lista select
			listaComune = new ArrayList<SelectItem>();
			List<KeyValueDTO> lista = comuneService.getListaComuneByUsername(getRequest().getUserPrincipal().getName(), true);
			for (KeyValueDTO kv : lista)
				listaComune.add(new SelectItem(kv.getKey(), kv.getValue()));

			super.addInfoMessage(msg);
		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}
	}

	public void doSaveParamInstanceComune() {

		String msg = "salva";

		try {

			for (AmKeyValueDTO dto : listaKeyValueInstanceComune) {

				dto.getAmKeyValueExt().getAmComune().setBelfiore(
						labelComuneSelected);
				dto.getAmKeyValueExt().getAmInstance().setName(
						labelInstanceComuneSelected);
				dto.setAmKeyValueExt(comuneService.saveParam(dto));
				if (dto.isShowDefault()
						&& !"".equals(dto.getAmKeyValueExt().getValueConf()))
					dto.setShowDefault(false);
				if (dto.getAmKeyValueExt().getValueConf() == null
						&& dto.getDefaultValue() != null) {
					dto.setShowDefault(true);
					dto.getAmKeyValueExt().setValueConf(dto.getDefaultValue());
				}

			}

			// update lista select
			listaComune = new ArrayList<SelectItem>();
			List<KeyValueDTO> lista = comuneService.getListaComuneByUsername(getRequest().getUserPrincipal().getName(), true);
			for (KeyValueDTO kv : lista)
				listaComune.add(new SelectItem(kv.getKey(), kv.getValue()));
			listaInstanceComune = new ArrayList<SelectItem>();
			lista = comuneService.getListaInstanceByComune(
					labelComuneSelected, true);
			for (KeyValueDTO kv : lista)
				listaInstanceComune.add(new SelectItem(kv.getKey(), kv
						.getValue()));

			super.addInfoMessage(msg);
		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}
	}

	public void doSaveParamFonteComune() {

		String msg = "salva";

		try {

			for (AmKeyValueDTO dto : listaKeyValueFonteComune) {

				dto.getAmKeyValueExt().getAmComune().setBelfiore(
						labelComuneSelected);
				dto.getAmKeyValueExt().setFkAmFonte(
						new Integer(labelFonteComuneSelected));
				dto.setAmKeyValueExt(comuneService.saveParam(dto));
				if (dto.isShowDefault()
						&& !"".equals(dto.getAmKeyValueExt().getValueConf()))
					dto.setShowDefault(false);
				if (dto.getAmKeyValueExt().getValueConf() == null
						&& dto.getDefaultValue() != null) {
					dto.setShowDefault(true);
					dto.getAmKeyValueExt().setValueConf(dto.getDefaultValue());
				}

			}

			// update lista select
			listaComune = new ArrayList<SelectItem>();
			List<KeyValueDTO> lista = comuneService.getListaComuneByUsername(getRequest().getUserPrincipal().getName(), true);
			for (KeyValueDTO kv : lista)
				listaComune.add(new SelectItem(kv.getKey(), kv.getValue()));
			listaFonteComune = new ArrayList<SelectItem>();
			lista = comuneService.getListaFonteByComune(
					labelComuneSelected, true);
			for(KeyValueDTO kv: lista)
				listaFonteComune.add(new SelectItem(kv.getKey(), kv.getValue()));

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
					labelInstanceComuneSelected);
			paramOverw.getAmKeyValueExt().getAmComune().setBelfiore(
					labelComuneSelected);
			paramOverw.setAmKeyValueExt(comuneService.saveParam(paramOverw));

			super.addInfoMessage(msg);
		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}
	}

	public String getLabelComuneSelected() {
		return labelComuneSelected;
	}

	public void setLabelComuneSelected(String labelComuneSelected) {
		this.labelComuneSelected = labelComuneSelected;
	}

	public List<SelectItem> getListaComune() {
		if (listaComune.size() == 0)
			fillItemsComune();
		return listaComune;
	}

	public void setListaComune(List<SelectItem> listaComune) {
		this.listaComune = listaComune;
	}

	public String getLabelInstanceComuneSelected() {
		return labelInstanceComuneSelected;
	}

	public void setLabelInstanceComuneSelected(
			String labelInstanceComuneSelected) {
		this.labelInstanceComuneSelected = labelInstanceComuneSelected;
	}

	public List<SelectItem> getListaInstanceComune() {
		return listaInstanceComune;
	}

	public void setListaInstanceComune(List<SelectItem> listaInstanceComune) {
		this.listaInstanceComune = listaInstanceComune;
	}

	public String getLabelFonteComuneSelected() {
		return labelFonteComuneSelected;
	}

	public void setLabelFonteComuneSelected(String labelFonteComuneSelected) {
		this.labelFonteComuneSelected = labelFonteComuneSelected;
	}

	public List<SelectItem> getListaFonteComune() {
		return listaFonteComune;
	}

	public void setListaFonteComune(List<SelectItem> listaFonteComune) {
		this.listaFonteComune = listaFonteComune;
	}

	public List<AmKeyValueDTO> getListaKeyValueComune() {
		return listaKeyValueComune;
	}

	public void setListaKeyValueComune(List<AmKeyValueDTO> listaKeyValueComune) {
		this.listaKeyValueComune = listaKeyValueComune;
	}

	public List<AmKeyValueDTO> getListaKeyValueInstanceComune() {
		return listaKeyValueInstanceComune;
	}

	public void setListaKeyValueInstanceComune(
			List<AmKeyValueDTO> listaKeyValueInstanceComune) {
		this.listaKeyValueInstanceComune = listaKeyValueInstanceComune;
	}

	public List<AmKeyValueDTO> getListaKeyValueFonteComune() {
		return listaKeyValueFonteComune;
	}

	public void setListaKeyValueFonteComune(
			List<AmKeyValueDTO> listaKeyValueFonteComune) {
		this.listaKeyValueFonteComune = listaKeyValueFonteComune;
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
		this.paramOverw = paramOverw;
	}

	public String getLabelParamOverw() {
		return labelParamOverw;
	}

	public void setLabelParamOverw(String labelParamOverw) {
		this.labelParamOverw = labelParamOverw;
	}

}
