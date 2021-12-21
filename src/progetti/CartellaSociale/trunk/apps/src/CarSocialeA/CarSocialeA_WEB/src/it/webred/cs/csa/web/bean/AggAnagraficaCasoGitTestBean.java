package it.webred.cs.csa.web.bean;

import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti.TipoAggiornamentoAnagrafica;
import it.webred.cs.data.model.CsAComponenteAnagCasoGit;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean
@ViewScoped
public class AggAnagraficaCasoGitTestBean extends CsUiCompBaseBean {
	
	HashMap<String, String> mappaVariazioniAnag = new HashMap<String, String>();	
    AccessTableSoggettoSessionBeanRemote soggettoService =  (AccessTableSoggettoSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableSoggettoSessionBean");
     
	@PostConstruct
	public void init() {
		
		mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.ANAGRAFICA_COGNOME , "ROSSI");
		mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.ANAGRAFICA_CODICE_FISCALE , "CFXDRT71A04L117U");
		mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.INDIRIZZO_RESIDENZA, "VIA TORRE GRANDE");
		mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.NUM_CIVICO_RESIDENZA, "22");
		mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.COD_COM_RESIDENZA, "055032" );
		mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.COMUNE_DI_RESIDENZA, "TERNI" );
		mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.COMUNE_DOMICILIO, "MONZA" );
		mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.CODICE_COMUNE_DOMICILIO, "108033" );
		mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.COGNOME_NUOVO_MEDICO, "MANCINI" );
		mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.NOME_NUOVO_MEDICO, "DANIELA" );
		mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.COD_REGIONALE_NUOVO_MEDICO, "26220" );
	}

	public void salvaAggiornamento() {
		String enteId = "";
		BaseDTO bDto = new BaseDTO();
		fillEnte(bDto);
		bDto.setUserId("aggiornaFamiglieSIGESS"); //da valutare se cambiare Utente
    	bDto.setObj(Long.parseLong("364106")); //ajani gianluigia
		CsAComponenteAnagCasoGit anagraficaCaso = soggettoService.getAnagraficaComponenteCasoSoggettoId(bDto);
	 	
		anagraficaCaso.setDtMod(new Date());
		anagraficaCaso.setFlgSegnalazione(true);
		anagraficaCaso.setSegnalazione(serializzaJsonHashMap(mappaVariazioniAnag));
		bDto.setObj(anagraficaCaso);
		soggettoService.saveAggiornamentoAnagraficaCasoGit(bDto);
	}
	
	private String serializzaJsonHashMap(HashMap<String, String> hashmap ) {
		String result ="";
		
		if(hashmap != null && hashmap.size() > 0) {
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				result = mapper.writerWithDefaultPrettyPrinter()
				  .writeValueAsString(hashmap);
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage(), e);
			}
		}
			
		return result;
	}
}
