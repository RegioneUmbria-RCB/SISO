package it.webred.cs.csa.web.manbean.scheda;

import it.webred.cs.csa.ejb.client.AccessTableDatiEsterniSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DatiEsterniSoggettoViewDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class SchedaDatiEsterniSoggettoBean extends CsUiCompBaseBean {

	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb("AccessTableSoggettoSessionBean");

	private AccessTableDatiEsterniSoggettoSessionBeanRemote ejbDatiEsterni = (AccessTableDatiEsterniSoggettoSessionBeanRemote) getCarSocialeEjb("AccessTableDatiEsterniSoggettoSessionBean");

	private String codiceFiscaleSoggetto;
	private List<DatiEsterniSoggettoViewDTO> datiEsterniSoggetto;

	public void initialize(Long soggettoId) {
		if(soggettoId!=null){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(soggettoId);
			CsASoggettoLAZY soggetto = soggettoService.getSoggettoById(dto);
			codiceFiscaleSoggetto = soggetto.getCsAAnagrafica().getCf();
			
			dto.setObj(codiceFiscaleSoggetto);
			datiEsterniSoggetto = ejbDatiEsterni.getDatiEsterniSoggetto(dto);
		}
	}

	public List<DatiEsterniSoggettoViewDTO> getDatiEsterniSoggetto() {
		return datiEsterniSoggetto;
	}

}
