package it.webred.ss.web.bean.wizard;

import javax.faces.bean.ManagedBean;

import eu.smartpeg.rilevazionepresenze.AnagraficaSessionBeanRemote;
import eu.smartpeg.rilevazionepresenze.data.model.Anagrafica;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaParams;
import it.webred.ss.web.dto.RilevazionePresenzeDettaglio;

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
		Anagrafica anagraficaRP = new Anagrafica();
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
		private static void initFromAnagRilevazionePresenze(RilevazionePresenzeDettaglio rilevazionePresenzeBean, Anagrafica anagraficaRP, String identificativo, String tipoRicerca){
			
		try {
			rilevazionePresenzeBean
					.setIdentificativo(identificativo != null ? identificativo : String.valueOf(anagraficaRP.getId()));
			rilevazionePresenzeBean.setCodfisc(anagraficaRP.getCf());
			rilevazionePresenzeBean.setCognome(anagraficaRP.getCognome());
			rilevazionePresenzeBean.setNome(anagraficaRP.getNome());
			rilevazionePresenzeBean.setDataNascita(anagraficaRP.getDataNascita());
			rilevazionePresenzeBean.setDefunto(false);// non abbiamo questa info
			rilevazionePresenzeBean.setSesso(anagraficaRP.getGenere());

			// Cittadinanza
			rilevazionePresenzeBean.setCittadinanza(anagraficaRP.getCittadinanza());

			// nascita
			String codIstat = anagraficaRP.getComuneNascitaCod() != null ? anagraficaRP.getComuneNascitaCod() : null;
			AmTabComuni comuneNascita = luoghiService.getComuneItaByIstat(codIstat);
			if (comuneNascita != null)
				rilevazionePresenzeBean.setComuneNascita(comuneNascita);
			else
				rilevazionePresenzeBean.setNazioneNascita(
						getNazioneByIstat(anagraficaRP.getStatoNascitaCod(), anagraficaRP.getStatoNascitaDes()));

			// Residenza
			rilevazionePresenzeBean.setIndirizzoResidenza(anagraficaRP.getIndirizzoResidenza());
			if (anagraficaRP.getComuneResidenzaCod() != null && !anagraficaRP.getComuneResidenzaCod().isEmpty()) {
				AmTabComuni comuneResidenza = luoghiService.getComuneItaByIstat(anagraficaRP.getComuneResidenzaCod());
				rilevazionePresenzeBean.setComuneResidenza(comuneResidenza);

			}
			// Domicilio
			rilevazionePresenzeBean.setIndirizzoDomicilio(anagraficaRP.getIndirizzoDomicilio());
			if (anagraficaRP.getComuneDomicilioCod() != null && !anagraficaRP.getComuneDomicilioCod().isEmpty()) {
				AmTabComuni comuneDomicilio = luoghiService.getComuneItaByIstat(anagraficaRP.getComuneDomicilioCod());
				rilevazionePresenzeBean.setComuneDomicilio(comuneDomicilio);
			}
			else if (anagraficaRP.getComuneResidenzaCod() != null  && !anagraficaRP.getComuneResidenzaCod().isEmpty()) {
				rilevazionePresenzeBean.setIndirizzoDomicilio(anagraficaRP.getIndirizzoResidenza());
				//imposto il comuneDOmicilio uguale alla residenza
				AmTabComuni comuneDomicilio = luoghiService.getComuneItaByIstat(anagraficaRP.getComuneResidenzaCod());
				rilevazionePresenzeBean.setComuneDomicilio(comuneDomicilio);
			}

			rilevazionePresenzeBean.setIdTitoloDiStudio(anagraficaRP.getIdTitoloStudio());
			rilevazionePresenzeBean.setIdVulnerabilita(anagraficaRP.getIdVulnerabilita());
			rilevazionePresenzeBean.setIdCondizioneLavorativa(anagraficaRP.getIdCondizioneLavorativa());
			rilevazionePresenzeBean.setIdStruttura(anagraficaRP.getStruttura().getId());
			rilevazionePresenzeBean.setIdArea(anagraficaRP.getIdAreaStruttura());
			rilevazionePresenzeBean.setUnitaAbitativa(anagraficaRP.getUnitaAbitativa());

		} catch (Exception e) {
			logger.error(e);
			rilevazionePresenzeBean.setEccezione(e);
		}
		}
}
