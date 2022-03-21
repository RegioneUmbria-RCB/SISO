package it.webred.cs.jsf.interfaces;

import it.webred.cs.jsf.bean.colloquio.ColloquioRowBean;
import it.webred.cs.jsf.bean.colloquio.DatiColloquioBean;

import java.util.List;

import javax.faces.model.SelectItem;


public interface IColloquio {
	
	public void salva() ;
	
	public DatiColloquioBean getDatiColloquio();
	
	public List<SelectItem> getNameTipoColloquios();
	
	public List<SelectItem> getNameDiarioDoves();
	
	public List<SelectItem> getNameDiarioConchis();
	
	public void OnNewColloquio() throws Exception;
	
	public List<ColloquioRowBean> getListaColloquios();
	
	public boolean isSoggetto();
	
	public boolean isReadOnly();

	public void onChangeDiarioConChi();
	
	public void esportaStampa(ColloquioRowBean row) throws Exception;

}
