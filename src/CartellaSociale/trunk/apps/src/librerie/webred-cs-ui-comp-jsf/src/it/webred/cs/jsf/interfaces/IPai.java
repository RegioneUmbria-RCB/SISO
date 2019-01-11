package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.CsDPai;

import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.event.TabChangeEvent;

public interface IPai {

	public void onChangeTabView(TabChangeEvent tce);
	
	public void initializeData();

	public List<CsDPai> getPais();

	public void setPais(List<CsDPai> pais);

	public List<CsDPai> getFilteredPais();

	public void setFilteredPais(List<CsDPai> filteredPais);

	public void nuovo();

	public void carica();

	public void caricaChiudi();

	public void chiudi();

	public void salva();

	public String getModalHeader();

	public void setModalHeader(String modalHeader);

	public String getWidgetVar();

	public void setWidgetVar(String widgetVar);

	public int getIdxSelected();

	public void setIdxSelected(int idxSelected);

	public CsDPai getSelectedPai();

	public void setSelectedPai(CsDPai pai);

	public SelectItem[] getLstTipoPai();

	public List<SelectItem> getStatusOptions();
	
	//inizio evoluzione-pai
	public boolean isPaiDetailRendered();  
	public void aggiungiTipoInterventoButton();
	public void aggiungiTipoErogazioneButton(); //SISO-748
	//fine evoluzione-pai

}