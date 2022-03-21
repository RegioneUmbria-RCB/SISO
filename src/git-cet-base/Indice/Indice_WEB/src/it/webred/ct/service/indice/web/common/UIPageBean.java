package it.webred.ct.service.indice.web.common;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import it.webred.ct.service.indice.web.IndiceBaseBean;
import it.webred.ct.service.indice.web.common.pagination.DataProviderImpl;
import it.webred.ct.service.indice.web.common.pagination.TotaleDataModel;
import it.webred.ct.service.indice.web.common.pagination.UnicoDataModel;
import it.webred.ct.service.indice.web.via.ViaBean;

public class UIPageBean{

	private String tableName;

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Object getBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		Object bean = context.getApplication().getVariableResolver().resolveVariable(context,beanName);
		return bean;
	}
	
	public String doInit(){
		
		ServiceInitializer bean = (ServiceInitializer) getBean(tableName.toLowerCase() + "Bean");
		bean.initService();
		
		DataProviderImpl data = (DataProviderImpl) getBean("dataProviderImpl");
		data.resetData();
		
		return "ricerca." + tableName.toLowerCase();
		
	}
	
}
