package it.webred.cs.csa.web.manbean.scheda;

import it.webred.cs.csa.ejb.client.AccessTableDatiEsterniSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DatiEsterniSoggettoViewDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class SchedaDatiEsterniSoggettoBean extends CsUiCompBaseBean {

	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb("AccessTableSoggettoSessionBean");

	private AccessTableDatiEsterniSoggettoSessionBeanRemote ejbDatiEsterni = (AccessTableDatiEsterniSoggettoSessionBeanRemote) getCarSocialeEjb("AccessTableDatiEsterniSoggettoSessionBean");

	private String codiceFiscaleSoggetto;
	private List<DatiEsterniSoggettoViewDTO> datiEsterniSoggetto;
	private List<DatiEsterniSoggettoViewDTO> datiEsterniFiltered = null;
	
	 
	private  final String ANAGRAFICA_GENERICA = "Anagrafica generica";
	private  final String PRESTAZIONE_SOCIALE = "Prestazione sociale";
	private  final String PRESTAZIONI = "Prestazione";
	
	private  final String DISABILITA_INVALIDITA = "Disabilita e Invalidita";
	private  final String TRIBUNALE = "Tribunale";
	private  final String ATTIVITA_PROFESSIONALI = "Attività Professionali";
	private  final String DISABILITA = "Disabilità";
	private  final String INVALIDITA = "Invalidità";
	
	private Map<String, String> mapLabelTab = new HashMap<String,String>();
	

	public String getPRESTAZIONI() {
		return PRESTAZIONI;
	}

	public String getDISABILITA() {
		return DISABILITA;
	}

	public String getINVALIDITA() {
		return INVALIDITA;
	}

	public String getANAGRAFICA_GENERICA() {
		return ANAGRAFICA_GENERICA;
	}

	public String getPRESTAZIONE_SOCIALE() {
		return PRESTAZIONE_SOCIALE;
	}

	public String getDISABILITA_INVALIDITA() {
		return DISABILITA_INVALIDITA;
	}

	public String getTRIBUNALE() {
		return TRIBUNALE;
	}

	public String getATTIVITA_PROFESSIONALI() {
		return ATTIVITA_PROFESSIONALI;
	}

	public void initialize(String cfSoggetto) {
		if(cfSoggetto!=null){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			codiceFiscaleSoggetto = cfSoggetto;
			
			dto.setObj(codiceFiscaleSoggetto);
			datiEsterniSoggetto = ejbDatiEsterni.getDatiEsterniSoggetto(dto);	
		}
	}

	public String getDatiEsterniFilterString(String key) {
		if(mapLabelTab.containsKey(key))
			return (String)mapLabelTab.get(key);
		return null;
	}
	public List<DatiEsterniSoggettoViewDTO> getDatiEsterniSoggetto() {
		return datiEsterniSoggetto;
	}

	public boolean controllaPresenzaDatiEsterniSoggetto(String etichetta) {
		boolean result = false;
		datiEsterniFiltered = new ArrayList<DatiEsterniSoggettoViewDTO>();
		if(this.datiEsterniSoggetto!=null){
			for(DatiEsterniSoggettoViewDTO datoEsternoDTO : this.datiEsterniSoggetto) {
				if(datoEsternoDTO.getTipologia()!=null && datoEsternoDTO.getTipologia().contains(etichetta)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public List<DatiEsterniSoggettoViewDTO> getDatiEsterniFiltered() {
		return datiEsterniFiltered;
	}

	public void setDatiEsterniFiltered(List<DatiEsterniSoggettoViewDTO> datiEsterniFiltered) {
		this.datiEsterniFiltered = datiEsterniFiltered;
	}
	
}
