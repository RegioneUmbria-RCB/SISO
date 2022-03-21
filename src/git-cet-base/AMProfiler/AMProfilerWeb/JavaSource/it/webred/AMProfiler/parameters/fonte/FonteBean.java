package it.webred.AMProfiler.parameters.fonte;

import it.webred.ct.config.model.AmFonte;
import it.webred.ct.config.model.AmKeyValueExt;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class FonteBean extends FonteBaseBean {

	private String labelFonteSelected;
	private List<SelectItem> listaFonte = new ArrayList<SelectItem>();
	private List<AmKeyValueExt> listaKeyValue = new ArrayList<AmKeyValueExt>();

	public void fillItemsFonte() {

		listaFonte = new ArrayList<SelectItem>();
		String msg = "listafonte";

		try {

			List<AmFonte> lista = fonteService.getListaFonte();

			for (AmFonte fonte : lista) {
				listaFonte.add(new SelectItem(fonte.getId(), fonte
						.getDescrizione()));
			}

			if (lista.size() > 0) {
				labelFonteSelected = lista.get(0).getId().toString();
				doCaricaParametriFonte();
			}

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public void doCaricaParametriFonte() {

		String msg = "listaparametri";

		try {

			listaKeyValue = fonteService.getListaKeyValueExt99(new Integer(labelFonteSelected), null, null);

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	public String getLabelFonteSelected() {
		return labelFonteSelected;
	}

	public void setLabelFonteSelected(String labelFonteSelected) {
		this.labelFonteSelected = labelFonteSelected;
	}

	public List<SelectItem> getListaFonte() {
		if (listaFonte.size() == 0)
			fillItemsFonte();
		return listaFonte;
	}

	public void setListaFonte(List<SelectItem> listaFonte) {
		this.listaFonte = listaFonte;
	}

	public List<AmKeyValueExt> getListaKeyValue() {
		return listaKeyValue;
	}

	public void setListaKeyValue(List<AmKeyValueExt> listaKeyValue) {
		this.listaKeyValue = listaKeyValue;
	}

}
