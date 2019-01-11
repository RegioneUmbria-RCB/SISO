package it.webred.cs.csa.web.manbean.scheda.anagrafica;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.data.model.CsTbStatoCivile;
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
public class StatoCivileGestBean extends DatiValGestioneMan implements IDatiValiditaGestione {

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<KeyValuePairBean> getLstItems() {
		
		lstItems = new ArrayList<KeyValuePairBean>();
		try {
			AccessTableConfigurazioneSessionBeanRemote bean = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbStatoCivile> beanLstStato = bean.getStatoCivile(bo);
			if (beanLstStato != null) {
				for (CsTbStatoCivile stato : beanLstStato) {			
					lstItems.add(new KeyValuePairBean(stato.getCod(), stato.getDescrizione()));
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
