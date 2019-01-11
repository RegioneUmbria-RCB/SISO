package it.webred.cs.jsf.interfaces;

import it.webred.cs.jsf.bean.DatiUserSearchBean;

import java.util.List;

public interface IUserSearch {

	
	public List<?> getLstSoggetti(String query) ;
	
	public void handleChangeUser(javax.faces.event.AjaxBehaviorEvent event);
	
	public String getWidgetVar();
	
	//public String getIdSoggetto();
	public DatiUserSearchBean getSelSoggetto();
	
	public Integer getMaxResult();

}
