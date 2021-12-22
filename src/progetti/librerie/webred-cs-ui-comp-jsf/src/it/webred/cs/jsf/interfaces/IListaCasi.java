package it.webred.cs.jsf.interfaces;

import java.util.List;

import it.webred.cs.jsf.bean.DatiCasoBean;
import it.webred.cs.jsf.manbean.IterDialogMan;

import javax.faces.event.ActionListener;
import javax.faces.model.SelectItem;

import org.primefaces.model.LazyDataModel;

public interface IListaCasi {
	
	public String getWidgetVar();
	
	public ActionListener getCloseDialog();
	
	public IterDialogMan getIterDialogMan();
		
	public void rowDeselect();

	public void clearFilters();

	public boolean isRenderTipoOperatore();
	public boolean isRenderStatoOperatore();
	
	public List<SelectItem> getListaOperatori();
	public List<SelectItem> getListaStati(); 
	public List<SelectItem> getListaTitStudio();
	public List<SelectItem> getListaCondLavoro();

}
