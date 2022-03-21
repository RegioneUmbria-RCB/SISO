package it.webred.ct.service.indice.web.via;

import it.webred.ct.data.access.basic.indice.via.ViaService;
import it.webred.ct.service.indice.web.IndiceBaseBean;

import javax.ejb.EJB;

public class ViaBaseBean extends IndiceBaseBean {

	protected ViaService indiceService = (ViaService) getEjb("CT_Service",
			"CT_Service_Data_Access", "ViaServiceBean");

}
