package it.webred.cs.jsf.interfaces;

import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.dto.utility.KeyValuePairBean;

import java.util.List;

public interface IIterInfoStato {
	public long getIdCaso();
	public String getNomeStato();
	public String getDataCreazione();
	public String getResponsabile();
	public String getEnteSegnalante();
	public String getUfficioSegnalante();
	public String getOperatoreSegnalante();
	public String getEnteSegnalato();
	public String getUfficioSegnalato();
	public String getOperatoreSegnalato();
	public String getNota();
	public String getCssClassStato();
	public String getSegnalatoDaLabel();
	public String getSegnalatoALabel();
	public String getSezioneAttributiLabel();
	
	public boolean isEnteARendered();
	public boolean isUfficioARendered();
	public boolean isOperatoreARendered();
	public boolean isNotaRendered();
	public boolean isResponsabileRendered();
	public boolean isSezioneAttributiRendered();
	public boolean isOpPanelARendered();
	
	public List<KeyValuePairBean<String, String>> getListaAttrValues();
	void initialize(CsIterStepByCasoDTO itStep);
}