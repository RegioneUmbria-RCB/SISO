package it.webred.cs.csa.web.manbean.fascicolo.valMultidimensionale.sina;

import it.webred.cs.csa.ejb.client.AccessTableSinaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SinaDTO;
import it.webred.cs.csa.ejb.dto.SinaEsegDTO;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;

import java.util.List;

public class ValMultidimSinaBean extends CsUiCompBaseBean {
	
protected AccessTableSinaSessionBeanRemote sinaService = null;

protected List<SinaDTO> sinaDTO;

	public SinaEsegDTO getSinaById(Long id)
	{
		BaseDTO dto = new BaseDTO();
		dto.setObj(id);
		
		return this.getSinaService().getSinaById(dto);
	}
	
	protected AccessTableSinaSessionBeanRemote getSinaService() {
		if (this.sinaService == null) {
			try {
				this.sinaService = (AccessTableSinaSessionBeanRemote) ClientUtility
						.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
								"AccessTableSinaSessionBean");
			} catch (Exception e) {
				logger.error(e);
			}
		}

		return this.sinaService;
	}
}
