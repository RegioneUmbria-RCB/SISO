package it.webred.cs.csa.web.manbean.scheda.anagrafica;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IDatiValiditaGestione;
import it.webred.cs.jsf.manbean.DatiValGestioneMan;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.dto.utility.KeyValuePairBean;
import it.webred.ejb.utility.ClientUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.naming.NamingException;

@ManagedBean
@NoneScoped
public class StatoCivileGestBean extends DatiValGestioneMan implements IDatiValiditaGestione {
	protected static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
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
	
	//SISO-1127 Inizio
	protected static long nullDateTime;
	static {
		try {
			nullDateTime = SDF.parse("31/12/9999").getTime();
		} catch (Exception e) {}		
	}
	
	public long getNullDateTime() {
		return nullDateTime;
	}
	public ValiditaCompBaseBean getComponenteAttivo() {
		
		
		List<ValiditaCompBaseBean>  listComponents = super.getLstComponents();
		
		if (listComponents == null || listComponents.size() == 0) {
			return null;
		}
		 
		for (ValiditaCompBaseBean comp : listComponents) {
			if(comp.getDataFine() == null || (comp.getDataFine().getTime() == getNullDateTime())) {
				 return comp;
			}
		}
		 return null;
	}
   public int getIndexComponenteAttivo() {
		
		
		List<ValiditaCompBaseBean>  listComponents = super.getLstComponents();
		
		if (listComponents == null || listComponents.size() == 0) {
			return -1;
		}
		int index = 0; 
		for (ValiditaCompBaseBean comp : listComponents) {
			if(comp.getDataFine() == null || (comp.getDataFine().getTime() == getNullDateTime())) {
				 return index;
			}
			index++;
		}
		 return -1;
	}
	
   //SISO-1127 Fine
}
