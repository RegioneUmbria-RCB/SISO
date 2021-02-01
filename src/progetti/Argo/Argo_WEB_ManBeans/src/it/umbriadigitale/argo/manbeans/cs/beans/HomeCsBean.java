package it.umbriadigitale.argo.manbeans.cs.beans;

import it.umbriadigitale.argo.manbeans.util.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;


/**
 * 
 * @author andrea.niccolini
 *
 */
@ManagedBean
@ViewScoped
public class HomeCsBean extends BaseCsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3838128707915669034L;

	
	// Authorities list
	private List<SelectItem> selectableAuthorities;
	private String selectedAuthority;
	
	// Periods selectable list
	private List<SelectItem> selectablePeriods;
	private String selectedPeriod;
	
	// Periods data list
	private List<Object> periodDataList;
	
	
	
	public void onSelectionAuthority(AjaxBehaviorEvent event)
	{	
		selectablePeriods = new ArrayList<SelectItem>();
		
		SelectItemGroup g1 = new SelectItemGroup("2014");
		g1.setSelectItems(new SelectItem[] {new SelectItem("1", "01/01/2014 - 31/03/2014"), new SelectItem("2", "01/04/2014 - 30/06/2014"), 
        		new SelectItem("3", "01/07/2014 - 30/09/2014"), new SelectItem("4", "01/10/2014 - 31/12/2014")});
		
		selectablePeriods.add(g1);
					
		SelectItemGroup g2 = new SelectItemGroup("2015");
		g2.setSelectItems(new SelectItem[] {new SelectItem("1", "01/01/2015 - 30/04/2015"), new SelectItem("2", "01/05/2015 - 31/08/2015"), 
        		new SelectItem("3", "01/09/2015 - 31/12/2015")});
		
		selectablePeriods.add(g2);
	}
	
	
	
	public void onSelectionPeriod()
	{
		
	}
	
	
	
	public List<SelectItem> getSelectableAuthorities() {
		if(selectableAuthorities == null)
		{
			selectableAuthorities = new ArrayList<SelectItem>();
			
			SelectItemGroup g1 = new SelectItemGroup("Regione Sociale");
	        g1.setSelectItems(new SelectItem[] {new SelectItem("1", "Regione Sociale 1"), new SelectItem("2", "Regione Sociale 2"), 
	        		new SelectItem("3", "Regione Sociale 3")});
			
			selectableAuthorities.add(g1);
			
			SelectItemGroup g2 = new SelectItemGroup("Comune");
	        g2.setSelectItems(new SelectItem[] {new SelectItem("1", "Perugia"), new SelectItem("2", "Gubbio"), 
	        		new SelectItem("3", "Città di Castello")});
	        
	        selectableAuthorities.add(g2);
		}
		return selectableAuthorities;
	}
	
	
	
	
	public String goNewPeriod()
	{
		return Constants.NAV_GO_NEW_PERIOD;
	}
	
	
	
	public void setSelectableAuthorities(List<SelectItem> selectableAuthorities) {
		this.selectableAuthorities = selectableAuthorities;
	}
	
	public String getSelectedAuthority() {
		return selectedAuthority;
	}
	public void setSelectedAuthority(String selectedAuthority) {
		this.selectedAuthority = selectedAuthority;
	}
	
	public List<SelectItem> getSelectablePeriods() {
		return selectablePeriods;
	}
	public void setSelectablePeriods(List<SelectItem> selectablePeriods) {
		this.selectablePeriods = selectablePeriods;
	}
	
	public String getSelectedPeriod() {
		return selectedPeriod;
	}
	public void setSelectedPeriod(String selectedPeriod) {
		this.selectedPeriod = selectedPeriod;
	}

	public List<Object> getPeriodDataList() {
		return periodDataList;
	}

	public void setPeriodDataList(List<Object> periodDataList) {
		this.periodDataList = periodDataList;
	}
	
}
