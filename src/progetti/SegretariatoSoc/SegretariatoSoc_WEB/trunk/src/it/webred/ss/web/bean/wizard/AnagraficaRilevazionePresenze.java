package it.webred.ss.web.bean.wizard;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaParams;
import it.webred.ss.web.dto.RilevazionePresenzeDettaglio;

import javax.faces.bean.ManagedBean;

import org.apache.commons.lang3.StringUtils;

import eu.smartpeg.rievazionepresenze.dto.AnagraficaDTO;
import eu.smartpeg.rilevazionepresenze.AnagraficaSessionBeanRemote;

@ManagedBean
public class AnagraficaRilevazionePresenze extends CsUiCompBaseBean{
 	
	    private static AnagraficaSessionBeanRemote rilevazionePresenzeService = (AnagraficaSessionBeanRemote)getEjb("RilevazionePresenze","RilevazionePresenze_EJB","AnagraficaSessionBean!eu.smartpeg.rilevazionepresenze.AnagraficaSessionBeanRemote");	
     

		public static RilevazionePresenzeDettaglio getPersonaDaAnagRilevazionePresenze(String tipoRicerca, String identificativo, String cf) {
			RilevazionePresenzeDettaglio p=null;
		
			RicercaAnagraficaParams rab = new RicercaAnagraficaParams(tipoRicerca,true);
				//fillEnte(rab);
			rab.setIdentificativo(identificativo);
			
			RilevazionePresenzeDettaglio result = getPersonaDaAnaRP(rab);
			if(result.getMessaggio()==null && result != null){
				p = result;
			}else
				logger.error("Errore ricerca "+tipoRicerca+" id ["+result.getIdentificativo()+"]"+ result.getMessaggio(), result.getEccezione());
			return p;
		}

		
		//#ROMA CAPITALE 
	public static RilevazionePresenzeDettaglio getPersonaDaAnaRP(RicercaAnagraficaParams params) {
		RilevazionePresenzeDettaglio result = new RilevazionePresenzeDettaglio();
		AnagraficaDTO anagraficaRP = new AnagraficaDTO();
		// query di interrogazione Anagraferilevazione presenze
		RilevazionePresenzeDettaglio pd = new RilevazionePresenzeDettaglio();
		try {
			if (anagraficaRP != null) {
				if (params.getIdentificativo() != null) {
					if(params.getCf() == null) {
						anagraficaRP = rilevazionePresenzeService.findAnagraficaById(Long.parseLong(params.getIdentificativo()));
						
					}else if(params.getCf() != null) {
						anagraficaRP = rilevazionePresenzeService.findAnagraficaByCf(params.getCf());
					}
				
				}
			
				initFromAnagRilevazionePresenze(pd, anagraficaRP, params.getIdentificativo(), DataModelCostanti.TipoRicercaSoggetto.ANAG_RILEVAZIONE_PRESENZE);
				
				
              if (pd !=null && pd.getMessaggio() == null)
            	  result = pd;
              else
              {
            	  result.setMessaggio("Impossibile interrogare l'anagrafica Rilevazione Presenze: " + pd.getMessaggio());
              }
			}
			result = pd;
		} catch (Exception e) {
			logger.error(e);
			result.setEccezione(e);
		}

		return result;
	}
	
	private static void initFromAnagRilevazionePresenze(RilevazionePresenzeDettaglio pd, AnagraficaDTO anagraficaRP, String identificativo, String tipoRicerca){
		pd.setProvenienzaRicerca(tipoRicerca);
		pd.setIdentificativo(identificativo != null ? identificativo : String.valueOf(anagraficaRP.getId()));
		pd.setCodfisc(anagraficaRP.getCf());
		pd.setCognome(anagraficaRP.getCognome());
		pd.setNome(anagraficaRP.getNome());
		pd.setDataNascita(anagraficaRP.getDataNascita());
		pd.setDefunto(false);// non abbiamo questa info
		pd.setSesso(anagraficaRP.getSesso());

		// Cittadinanza
		pd.setCittadinanza(anagraficaRP.getCittadinanza());

		pd.setComuneNascita(anagraficaRP.getComuneNascita());
		pd.setNazioneNascita(anagraficaRP.getNazioneNascita());
		
		// Residenza
		pd.setIndirizzoResidenza(anagraficaRP.getIndirizzoResidenza());
		pd.setComuneResidenza(anagraficaRP.getComuneResidenza());
		
		// Domicilio
		pd.setIndirizzoDomicilio(anagraficaRP.getIndirizzoDomicilio());
		pd.setComuneDomicilio(anagraficaRP.getComuneDomicilio());

		pd.setIdTitoloDiStudio(anagraficaRP.getIdTitoloStudio());
		pd.setIdVulnerabilita(anagraficaRP.getIdVulnerabilita());
		pd.setIdCondizioneLavorativa(anagraficaRP.getIdCondizioneLavorativa());
		pd.setIdStruttura(anagraficaRP.getIdStruttura());
		pd.setIdArea(anagraficaRP.getIdAreaStruttura());
		pd.setUnitaAbitativa(anagraficaRP.getUnitaAbitativa());
	}
}
