package it.webred.ct.service.indice.web.via;

import it.webred.ct.data.access.basic.indice.IndiceDataIn;
import it.webred.ct.data.access.basic.indice.dto.IndiceOperationCriteria;
import it.webred.ct.data.model.indice.SitViaUnico;
import it.webred.ct.service.indice.web.common.IndiceCommonBean;
import it.webred.ct.service.indice.web.common.ServiceInitializer;
import it.webred.ct.service.indice.web.common.pagination.DataProviderImpl;

public class ViaBean extends ViaBaseBean implements ServiceInitializer {

	private SitViaUnico nuovo = new SitViaUnico();

	public void initService() {

		IndiceCommonBean common = (IndiceCommonBean) getBeanReference("indiceCommonBean");
		common.setIndiceService(indiceService);
		common.resetData();

		DataProviderImpl dataProvider = (DataProviderImpl) getBeanReference("dataProviderImpl");
		dataProvider.setIndiceService(indiceService);

	}

	public void doNuovaVia() {

		String msg = "nuovo.via";

		try {
			
			IndiceDataIn indDataIn = new IndiceDataIn();
			indDataIn.setObj(nuovo);
			fillEnte(indDataIn);
			
			indiceService.creaNuovaVia(indDataIn);
			//indiceService.creaNuovaVia(nuovo);
			
			super.addInfoMessage(msg);
			
		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			t.printStackTrace();
		}
	}

	public SitViaUnico getNuovo() {
		return nuovo;
	}

	public void setNuovo(SitViaUnico nuovo) {
		this.nuovo = nuovo;
	}

}
