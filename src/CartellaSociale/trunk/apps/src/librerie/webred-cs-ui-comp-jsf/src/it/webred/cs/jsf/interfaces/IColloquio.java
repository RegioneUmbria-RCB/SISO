package it.webred.cs.jsf.interfaces;

import java.util.List;

import it.webred.cs.jsf.bean.colloquio.ColloquioRowBean;
import it.webred.cs.jsf.bean.colloquio.DatiColloquioBean;
import it.webred.dto.utility.KeyValuePairBean;


public interface IColloquio {
	
	public void saveColloquio() ;
	
	public DatiColloquioBean getDatiColloquio();
	
	public List<KeyValuePairBean<Long,String>> getNameTipoColloquios();
	
	public List<KeyValuePairBean<Long,String>> getNameDiarioDoves();
	
	public List<KeyValuePairBean<Long,String>> getNameDiarioConchis();
	
	public void OnNewColloquio() throws Exception;
	
	public List<ColloquioRowBean> getListaColloquios();
	
	public boolean isSoggetto();
	
	public boolean isReadOnly();

	public void onChangeDiarioConChi();
	
	public void esportaStampa(ColloquioRowBean row) throws Exception;

}
