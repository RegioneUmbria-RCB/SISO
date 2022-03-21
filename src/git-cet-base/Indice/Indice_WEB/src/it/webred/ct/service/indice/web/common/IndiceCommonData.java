package it.webred.ct.service.indice.web.common;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;
import it.webred.ct.data.access.basic.indice.dto.SitNuovoDTO;
import it.webred.ct.data.access.basic.indice.dto.SitSorgenteDTO;
import it.webred.ct.service.indice.web.IndiceBaseBean;

public class IndiceCommonData extends IndiceBaseBean{

	protected IndiceSearchCriteria criteria = new IndiceSearchCriteria();
	
	protected int sorgenteIndex;
	
	protected List<SitSorgenteDTO> listaDTO;
	
	protected String pageOperationTable;
	
	protected String pageRisRicerca;
	
	protected String labelUnicoSelezionato;

	protected String labelUnicoSelezionato2;
	
	protected String hashTotaleSelezionato;

	protected String nuovoIdUnico;
	
	protected String labelTotaleNativo;
	
	protected boolean controlloTotaleNativo;

	protected List<SelectItem> listaEnteSorgente = new ArrayList<SelectItem>();

	protected SitNuovoDTO sitNuovoDTO = new SitNuovoDTO();

	public IndiceSearchCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(IndiceSearchCriteria criteria) {
		this.criteria = criteria;
	}

	public int getSorgenteIndex() {
		return sorgenteIndex;
	}

	public void setSorgenteIndex(int sorgenteIndex) {
		this.sorgenteIndex = sorgenteIndex;
	}

	public List<SitSorgenteDTO> getListaDTO() {
		return listaDTO;
	}

	public void setListaDTO(List<SitSorgenteDTO> listaDTO) {
		this.listaDTO = listaDTO;
	}

	public String getPageOperationTable() {
		return pageOperationTable;
	}

	public void setPageOperationTable(String pageOperationTable) {
		this.pageOperationTable = pageOperationTable;
	}

	public String getPageRisRicerca() {
		return pageRisRicerca;
	}

	public void setPageRisRicerca(String pageRisRicerca) {
		this.pageRisRicerca = pageRisRicerca;
	}

	public String getLabelUnicoSelezionato() {
		return labelUnicoSelezionato;
	}

	public void setLabelUnicoSelezionato(String labelUnicoSelezionato) {
		this.labelUnicoSelezionato = labelUnicoSelezionato;
	}

	public String getLabelUnicoSelezionato2() {
		return labelUnicoSelezionato2;
	}

	public void setLabelUnicoSelezionato2(String labelUnicoSelezionato2) {
		this.labelUnicoSelezionato2 = labelUnicoSelezionato2;
	}

	public String getHashTotaleSelezionato() {
		return hashTotaleSelezionato;
	}

	public void setHashTotaleSelezionato(String hashTotaleSelezionato) {
		this.hashTotaleSelezionato = hashTotaleSelezionato;
	}

	public String getNuovoIdUnico() {
		return nuovoIdUnico;
	}

	public void setNuovoIdUnico(String nuovoIdUnico) {
		this.nuovoIdUnico = nuovoIdUnico;
	}

	public String getLabelTotaleNativo() {
		return labelTotaleNativo;
	}

	public void setLabelTotaleNativo(String labelTotaleNativo) {
		this.labelTotaleNativo = labelTotaleNativo;
	}

	public boolean isControlloTotaleNativo() {
		return controlloTotaleNativo;
	}

	public void setControlloTotaleNativo(boolean controlloTotaleNativo) {
		this.controlloTotaleNativo = controlloTotaleNativo;
	}

	public List<SelectItem> getListaEnteSorgente() {
		return listaEnteSorgente;
	}

	public void setListaEnteSorgente(List<SelectItem> listaEnteSorgente) {
		this.listaEnteSorgente = listaEnteSorgente;
	}

	public SitNuovoDTO getSitNuovoDTO() {
		return sitNuovoDTO;
	}

	public void setSitNuovoDTO(SitNuovoDTO sitNuovoDTO) {
		this.sitNuovoDTO = sitNuovoDTO;
	}

	public void resetData(){
		
		criteria = new IndiceSearchCriteria();
		sorgenteIndex = 0;
		listaDTO = new ArrayList<SitSorgenteDTO>();
		pageOperationTable = "/jsp/protected/empty.xhtml";
		pageRisRicerca = "/jsp/protected/empty.xhtml";
		labelUnicoSelezionato = "";
		labelUnicoSelezionato2 = "";
		hashTotaleSelezionato = "";;
		nuovoIdUnico = "";
		labelTotaleNativo = "";
		controlloTotaleNativo = false;
		listaEnteSorgente = new ArrayList<SelectItem>();
		sitNuovoDTO = new SitNuovoDTO();
		
	}
	
}
