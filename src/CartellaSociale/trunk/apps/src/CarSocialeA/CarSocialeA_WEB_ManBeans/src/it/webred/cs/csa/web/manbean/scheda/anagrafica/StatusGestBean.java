package it.webred.cs.csa.web.manbean.scheda.anagrafica;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.data.model.CsTbStatus;
import it.webred.cs.jsf.interfaces.IDatiValiditaGestione;
import it.webred.cs.jsf.manbean.DatiValGestioneMan;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.dto.utility.KeyValuePairBean;
import it.webred.ejb.utility.ClientUtility;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.naming.NamingException;

@ManagedBean
@NoneScoped
public class StatusGestBean extends DatiValGestioneMan implements IDatiValiditaGestione {

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<KeyValuePairBean> getLstItems() {
		
		lstItems = new ArrayList<KeyValuePairBean>();
		try {
			AccessTableConfigurazioneSessionBeanRemote bean = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbStatus> beanLstStatus = bean.getStatus(bo);
			if (beanLstStatus != null) {
				for (CsTbStatus status : beanLstStatus) {
					KeyValuePairBean kv = new KeyValuePairBean(status.getId(), status.getDescrizione());
					if("0".equals(status.getAbilitato()))
						lstItems.add(kv);
				}
			}
		} catch (NamingException e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
		return lstItems;
	}

	@Override
	public List<KeyValuePairBean> getDettaglioSelezione(Long id) {
		return null;
	}
	
}
