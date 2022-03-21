package it.webred.ct.service.indice.web.civico;

import it.webred.ct.data.access.basic.indice.civico.CivicoService;
import it.webred.ct.data.access.basic.indice.via.ViaService;
import it.webred.ct.service.indice.web.IndiceBaseBean;

import javax.ejb.EJB;

public class CivicoBaseBean extends IndiceBaseBean {

	protected CivicoService civicoService = (CivicoService) getEjb(
			"CT_Service", "CT_Service_Aggregator_EJB", "DatiCivicoServiceBean");

}
