package it.webred.cs.csa.web.manbean.scheda.anagrafica;

import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.jsf.interfaces.IDatiValiditaGestione;
import it.webred.cs.jsf.manbean.DatiValGestioneMan;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.dto.utility.KeyValuePairBean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@ManagedBean
@NoneScoped
public class StatusGestBean extends DatiValGestioneMan implements IDatiValiditaGestione {

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<KeyValueDTO> loadListItems() {
		try {
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			return confService.getStatus(bo);
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	
	@Override
	public List<KeyValuePairBean> getDettaglioSelezione(Long id) {
		return null;
	}
}
