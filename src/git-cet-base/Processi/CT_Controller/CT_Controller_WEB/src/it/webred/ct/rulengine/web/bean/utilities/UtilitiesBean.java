package it.webred.ct.rulengine.web.bean.utilities;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.controller.ejbclient.utilities.dto.GestioneUtilitiesDTO;
import it.webred.ct.controller.ejbclient.utilities.dto.InputFunzioneDTO;
import it.webred.ct.controller.ejbclient.utilities.dto.LogFunzioniDTO;
import it.webred.ct.controller.ejbclient.utilities.dto.RicercaLogFunzioniDTO;
import it.webred.ct.rulengine.controller.model.RConnection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

public class UtilitiesBean extends UtilitiesBaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(UtilitiesBean.class.getName());

	
	private List<String> listaFunzioni;
	
	private final String F_RIC_HASH = "Ricalcolo Hash";
	private final String F_EMPTY_TABLE = "Svuotamento Tabella";
	
	private String utilitiesLogPage = "/jsp/protected/empty.xhtml";
	private String paramsPage = "/jsp/protected/empty.xhtml";
	

	@PostConstruct
	public void init() {
		
		listaFunzioni = new ArrayList<String>();
		listaFunzioni.add(this.F_RIC_HASH);
		listaFunzioni.add(this.F_EMPTY_TABLE);
		
	}
	

	public String goUtilities() {

		return "controller.utilities";
	}

	public void resetPage() {
		
		utilitiesLogPage = "/jsp/protected/empty.xhtml";
		paramsPage = "/jsp/protected/empty.xhtml";
		
	}

	public String getF_RIC_HASH() {
		return F_RIC_HASH;
	}

	public String getUtilitiesLogPage() {
		return utilitiesLogPage;
	}

	public void setUtilitiesLogPage(String utilitiesLogPage) {
		this.utilitiesLogPage = utilitiesLogPage;
	}

	public String getF_EMPTY_TABLE() {
		return F_EMPTY_TABLE;
	}

	public List<String> getListaFunzioni() {
		return listaFunzioni;
	}

	public void setListaFunzioni(List<String> listaFunzioni) {
		this.listaFunzioni = listaFunzioni;
	}

	public String getParamsPage() {
		return paramsPage;
	}

	public void setParamsPage(String paramsPage) {
		this.paramsPage = paramsPage;
	}
	
}
