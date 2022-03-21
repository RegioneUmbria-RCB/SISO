package it.webred.ct.service.indice.web.common.pagination;

import java.util.ArrayList;
import java.util.List;

import it.webred.ct.data.access.basic.indice.IndiceDataIn;
import it.webred.ct.data.access.basic.indice.civico.dto.ListaCiviciByVia;
import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;
import it.webred.ct.data.access.basic.indice.dto.ListaTotaleBySorgente;
import it.webred.ct.data.access.basic.indice.dto.ListaUnico;
import it.webred.ct.data.access.basic.indice.dto.SitOperationDTO;
import it.webred.ct.data.model.indice.SitUnico;
import it.webred.ct.service.indice.web.IndiceBaseBean;

public class DataProviderImpl extends IndiceBaseBean implements DataProvider{
	
	private IndiceSearchCriteria criteriaUnico = new IndiceSearchCriteria();
	private IndiceSearchCriteria criteriaUnicoTwo = new IndiceSearchCriteria();
	private IndiceSearchCriteria criteriaTotale = new IndiceSearchCriteria();
	//se true sono nel modalpanel per scegliere un secondo valore unico altrimenti nella ricerca a inizio pagina
	private boolean switchCriteriaUnico;
	private String pageModalPanel;

	public List<SitUnico> getUnicoByRange(int start, int rowNumber) {
		
		IndiceSearchCriteria crit = new IndiceSearchCriteria();
		
		if(switchCriteriaUnico)
			crit = criteriaUnicoTwo;
		else 
			crit = criteriaUnico;
		
		try{
			
			IndiceDataIn indDataIn = new IndiceDataIn();
			ListaUnico lu = new ListaUnico();
			lu.setCriteria(crit);
			lu.setStartm(start);
			lu.setNumberRecord(rowNumber);
			indDataIn.setListaUnico(lu);
			fillEnte(indDataIn);
			
			List<SitUnico> result = indiceService.getListaUnico(indDataIn);
			
			//List<SitUnico> result = indiceService.getListaUnico(crit, start, rowNumber);
			return result;
		}catch(Throwable t) {
			super.addErrorMessage("listaunici.error", t.getMessage());
			t.printStackTrace();
			return new ArrayList<SitUnico>();
		}
	}

	public long getUnicoCount() {
		
		IndiceSearchCriteria crit = new IndiceSearchCriteria();
		if(switchCriteriaUnico)
			crit = criteriaUnicoTwo;
		else crit = criteriaUnico;
		fillEnte(crit);
		
		Long result = indiceService.getListaUnicoRecordCount(crit);
		return result;
	}
	
	public List<SitOperationDTO> getTotaleByRange(int start, int rowNumber) {
		
		try{
			IndiceDataIn indDataIn = new IndiceDataIn();

			ListaTotaleBySorgente lts = new ListaTotaleBySorgente();
			lts.setCriteria(criteriaTotale);
			lts.setStartm(start);
			lts.setNumberRecord(rowNumber);			
			indDataIn.setListaTotaleBySorgente(lts);
			fillEnte(indDataIn);
			
			List<SitOperationDTO> result = indiceService.getListaTotaleBySorgente(indDataIn);
			//List<SitOperationDTO> result = indiceService.getListaTotaleBySorgente(criteriaTotale, start, rowNumber);
		
			return result;
		}catch(Throwable t) {
			super.addErrorMessage("listatotali.error", t.getMessage());
			t.printStackTrace();
			return new ArrayList<SitOperationDTO>();
		}
	}

	public long getTotaleCount() {
		fillEnte(criteriaTotale);
		Long result = indiceService.getListaTotaleBySorgenteRecordCount(criteriaTotale);
		return result;
	}

	public void resetData() {
		criteriaUnico = new IndiceSearchCriteria();
		criteriaUnicoTwo = new IndiceSearchCriteria();
		criteriaTotale = new IndiceSearchCriteria();
		switchCriteriaUnico = false;
		pageModalPanel = "/jsp/protected/empty.xhtml";
	}
	
	public void resetModalPanelPage(){
		
		pageModalPanel = "/jsp/protected/empty.xhtml";
		switchCriteriaUnico = false;
		
	}
	
	public IndiceSearchCriteria getCriteriaUnico() {
		return criteriaUnico;
	}

	public void setCriteriaUnico(IndiceSearchCriteria criteriaUnico) {
		this.criteriaUnico = criteriaUnico;
	}

	public IndiceSearchCriteria getCriteriaUnicoTwo() {
		return criteriaUnicoTwo;
	}

	public void setCriteriaUnicoTwo(IndiceSearchCriteria criteriaUnicoTwo) {
		this.criteriaUnicoTwo = criteriaUnicoTwo;
	}

	public boolean isSwitchCriteriaUnico() {
		return switchCriteriaUnico;
	}

	public void setSwitchCriteriaUnico(boolean switchCriteriaUnico) {
		this.switchCriteriaUnico = switchCriteriaUnico;
	}
	
	public void resetSwitchCriteriaUnico(){
		this.switchCriteriaUnico = false;
	}

	public IndiceSearchCriteria getCriteriaTotale() {
		return criteriaTotale;
	}

	public void setCriteriaTotale(IndiceSearchCriteria criteriaTotale) {
		this.criteriaTotale = criteriaTotale;
	}
	
	public String getPageModalPanel() {
		return pageModalPanel;
	}

	public void setPageModalPanel(String pageModalPanel) {
		this.pageModalPanel = pageModalPanel;
	}

}
