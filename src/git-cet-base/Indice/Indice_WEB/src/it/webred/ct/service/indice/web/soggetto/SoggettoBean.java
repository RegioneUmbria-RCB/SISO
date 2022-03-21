package it.webred.ct.service.indice.web.soggetto;

import it.webred.ct.service.indice.web.common.IndiceCommonBean;
import it.webred.ct.service.indice.web.common.ServiceInitializer;
import it.webred.ct.service.indice.web.common.pagination.DataProviderImpl;

public class SoggettoBean extends SoggettoBaseBean implements ServiceInitializer{

	public void initService(){
		IndiceCommonBean common = (IndiceCommonBean) getBeanReference("indiceCommonBean");
		common.setIndiceService(indiceService);
		common.resetData();
		
		DataProviderImpl dataProvider = (DataProviderImpl) getBeanReference("dataProviderImpl");
		dataProvider.setIndiceService(indiceService);
	}
	
}
