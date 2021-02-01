package it.webred.cs.csa.web.bean.timertask;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableParentiGitSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.AlertDTO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ConfrontoSsCsDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.IProvvedimentiMinori;
import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.ProvvedimentiMinoriManBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipiAlertCod;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsAComponenteAnagCasoGit;
import it.webred.cs.data.model.CsAFamigliaGruppoGit;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsAlertConfig;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
import it.webred.cs.data.model.CsItStepLAZY;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.persist.CsAlert;
import it.webred.cs.data.model.view.VSsSchedeUdcDiff;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.abitazione.AbitazioneManBaseBean;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.cs.json.stranieri.StranieriManBaseBean;
import it.webred.cs.json.stranieri.ver1.StranieriBean;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.ws.ricerca.dto.FamiliareDettaglio;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaParams;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.interceptor.ExcludeDefaultInterceptors;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

@ExcludeDefaultInterceptors
public class ZsTimerTaskGiornaliero extends TimerTask {

	private static Logger logger = Logger.getLogger("carsociale_timertask.log");
	private String enteId;
	private AccessTableAlertSessionBeanRemote alertService;
	private AccessTableCasoSessionBeanRemote casoService;
	private AccessTableDiarioSessionBeanRemote diarioService;
	private LuoghiService luoghiService; 
	private AccessTableConfigurazioneSessionBeanRemote configurazioneService;
	private AnagrafeService anagrafeService;
	private AccessTableSoggettoSessionBeanRemote soggettoService;
	
	private int variazioniAnagraficheElaborate;
	private Integer MAX_VARIAZIONI_ANAGRAFICHE;
	
	 //SISO1297
	private AccessTableIterStepSessionBeanRemote iterStepService;
	public ZsTimerTaskGiornaliero(String enteId) {
		super();
		this.enteId = enteId;
	}

	@Override
	public void run() {

		try {
			configurazioneService = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
			alertService = (AccessTableAlertSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB","AccessTableAlertSessionBean");
			casoService =  (AccessTableCasoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB","AccessTableCasoSessionBean");
			diarioService =  (AccessTableDiarioSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB","AccessTableDiarioSessionBean");
			soggettoService = (AccessTableSoggettoSessionBeanRemote)ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB","AccessTableSoggettoSessionBean");
			//SISO-1297
			iterStepService  = (AccessTableIterStepSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");
			luoghiService  = (LuoghiService)  ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");
			anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface("CT_Service", "CT_Service_Data_Access","AnagrafeServiceBean");
			
			this.aggiornaFamiglieSIGESS();
	    	this.aggiornaAlertRelazioni();
	    	this.aggiornaAlertComponentiFamiliari();
	    	this.aggiornaAlertProvvedimentiTribunale();
	    	this.aggiornaAlertPAI();
	    	this.segnalaModificheSchedaCompletaUDC();
	    	// SISO 1115
	    	this.importaDatiEsterniSoggettoDaFile();
	    	

	    	this.elaboraVariazioniAnagrafiche();
			
			//SISO-1127 Fine
	    	verificaCasiSoggettoErogazione();
	    	
			logger.debug("__ FINE Esecuzione ZsTimerTaskGiornaliero__");

		} catch (Exception e) {
			logger.error("__ ZsTimerTaskGiornaliero: Eccezione su : " + e.getMessage(), e);
		}
	}
	
	private void elaboraVariazioniAnagrafiche() throws NamingException{
		variazioniAnagraficheElaborate = 0;
		MAX_VARIAZIONI_ANAGRAFICHE = CsUiCompBaseBean.getMaxVariazioniAnagrafiche();
		
		logger.debug("elaboraVariazioniAnagrafiche - il numero massimo di variazioni anagrafiche verificabili giornalmente è: "+ this.MAX_VARIAZIONI_ANAGRAFICHE);
		
    	//SISO-1127
		this.aggiornaAnagraficaCasoSIGESS();
		this.aggiornaAnagraficaCasoSIRPS();
		this.aggiornaAnagraficaCasoAnagrafeSanitariaUmbria();
		this.aggiornaAnagraficaInterna();
		
		// SISO-1266
		this.aggiornaAnagraficaCasoSenzaIdProvenienza();
	   
		this.aggiornaAlertAnagraficaCaso();
	}
	
	//SISO-1127 Inizio
		public void aggiornaAlertAnagraficaCaso(){
				
				logger.debug("__ INIZIO ZsTimerTaskGiornaliero: aggiornaAlertAnagraficaCaso_");
			
				BaseDTO bDto = new BaseDTO();
				IterDTO itDto = new IterDTO();
				OperatoreDTO opDto = new OperatoreDTO();
		
		    	bDto.setEnteId(enteId);
		    	itDto.setEnteId(enteId);
		    	opDto.setEnteId(enteId);
				
		    	try {
		    		
					List<CsAComponenteAnagCasoGit> listaAnagraficheCasiAggiornate = soggettoService.getAnagraficheCasiGitAggiornate(bDto);
					for(CsAComponenteAnagCasoGit componenteGIT: listaAnagraficheCasiAggiornate) {
						
						CsACaso caso = componenteGIT .getCsASoggetto().getCsACaso();
						
						CsOOperatoreSettore opeTo = findOperatoreTo(caso);
						
						CsOSettore settTo = null ;
						CsOOrganizzazione orgTo = null;
						
						settTo = opeTo!=null ? opeTo.getCsOSettore() : null;
						// orgTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOSettore().getCsOOrganizzazione() : null;
						
						if(opeTo!=null || settTo!=null || orgTo!=null){
							boolean inserisco = this.validaInserimentoNuovoAlert(DataModelCostanti.TipiAlertCod.ANAGRAFICA_CASO_GIT, caso, opeTo.getId());
							if(inserisco){
							
								//nuovo alert
								String nome = componenteGIT.getCsASoggetto().getCsAAnagrafica().getCognome() + " " + componenteGIT.getCsASoggetto().getCsAAnagrafica().getNome();
								String cf = componenteGIT.getCsASoggetto().getCsAAnagrafica().getCf();
								String descrizione = "Dati Anagrafici: sono state rilevate delle variazioni relative al Soggetto  "+ nome +" ("+ cf +") .";
								String titDescrizione = "Notifica caso "+ nome + ": variazione dati anagrafici";
								CsOOrganizzazione orgFrom = this.findOrganizzazioneDefault(TipiAlertCod.ANAGRAFICA_CASO_GIT);
								logger.info(enteId + " __ Aggiungo Alert per aggiornamento anagrafica caso: " + componenteGIT.getCsASoggetto().getCsAAnagrafica().getCf());
								addAlert(TipiAlertCod.ANAGRAFICA_CASO_GIT, caso, titDescrizione, descrizione, null, null,null, orgFrom, opeTo, settTo, orgTo);
							}else
								logger.info(" __ Sospeso Inserimento Alert per aggiornamento anagrafica caso: già presente [casoId:" + caso.getId()+"]");
						
							//creato l alert azzero la segnalazione
							componenteGIT.setFlgSegnalazione(false);
							
							bDto.setObj(componenteGIT);
							soggettoService.updateAnagraficaComponenteCaso(bDto);
						}
					}
				
					logger.debug("__ FINE ZsTimerTaskGiornaliero: aggiornaAlertComponentiFamiliari_");
					
		    	} catch (Exception e2) {
					logger.error("__ERRORE ZsTimerTaskGiornaliero: aggiornaAlertComponentiFamiliari : " + e2.getMessage(), e2);
				}
		    	
			}
	 
		 private boolean maxVarAnagraficheElaborate(){
			 boolean superato =  MAX_VARIAZIONI_ANAGRAFICHE!=null && this.variazioniAnagraficheElaborate > MAX_VARIAZIONI_ANAGRAFICHE;
			 if(superato) logger.warn("Superato il limite massimo delle variazioni anagrafiche elaborabili giornalmente ["+MAX_VARIAZIONI_ANAGRAFICHE+"]");
			 return superato;
		 }
		
		 private void aggiornaAnagraficaCasoSIGESS(){	
				
			if(CsUiCompBaseBean.isAnagrafeSigessAbilitata()){
			    logger.debug("__ INIZIO ZsTimerTaskGiornaliero: aggiornaAnagraficaCasoSIGESS");
				BaseDTO bDto = new BaseDTO();
		    	bDto.setEnteId(enteId);
		    	bDto.setUserId("aggiornaAnagraficaCasoSIGESS"); //da valutare se cambiare Utente
				String tipoRicerca = DataModelCostanti.TipoRicercaSoggetto.SIGESS;
		    	try {
		    		
		    		bDto.setObj(tipoRicerca);
		    		List<CsAComponenteAnagCasoGit> listaSoggettiSigess = soggettoService.getAnagraficaComponenteCasoProvenienza(bDto);
		    		Date oggi = new Date();
		    		logger.debug("aggiornaAnagraficaCasoSIGESS - elaborazione di "+listaSoggettiSigess.size()+" soggetti in corso...");
					for(CsAComponenteAnagCasoGit anagraficaCaso: listaSoggettiSigess) {
						try {
							if(maxVarAnagraficheElaborate()) break;
							Date ultimaModifica = anagraficaCaso.getDtMod()!=null ? anagraficaCaso.getDtMod() : anagraficaCaso.getDtIns();
							long millisDiff =    oggi.getTime() - ultimaModifica.getTime();
							//1 Giorni = 86400000 Millisecondi
							int days = (int) (millisDiff / 86400000);
							if(days>=7){
								String cf = anagraficaCaso.getCsASoggetto().getCsAAnagrafica().getCf();
								String idSigess = anagraficaCaso.getCsASoggetto().getCsAAnagrafica().getIdOrigWsId();
								
								RicercaAnagraficaParams params = new RicercaAnagraficaParams(tipoRicerca, true);
								params.setEnteId(enteId);
								params.setIdentificativo(idSigess);
								params.setCf(cf);
								RicercaAnagraficaResult res = CsUiCompBaseBean.getPersonaDaAnagSigess(params);
								
								List<PersonaDettaglio> lista = new ArrayList<PersonaDettaglio>();
								if(res!=null && !res.getElencoAssisiti().isEmpty()){
									if(res.getMessaggio()==null){
										lista = res.getElencoAssisiti();
										elaboraVariazioniCaso(anagraficaCaso, lista, tipoRicerca);
									}else
										logger.error("aggiornaAnagraficaCasoSIGESS : Errore ricerca soggetto ["+cf+"]["+anagraficaCaso.getCsASoggetto().getCsAAnagrafica().getIdOrigWs()+"] "+tipoRicerca+" CODICE["+res.getCodice()+"]"+ res.getMessaggio(), res.getEccezione());
								}
							}
						} catch (Exception e2) {
							logger.error("__ERRORE ZsTimerTaskGiornaliero: aggiornaAnagraficaCasoSIGESS :  per anagraficaCaso ID [" + anagraficaCaso.getId() + "]" + e2.getMessage(), e2);
						}
					}
				
					logger.debug("__ FINE ZsTimerTaskGiornaliero: aggiornaAnagraficaCasoSIGESS");
					
		    	} catch (Exception e2) {
					logger.error("__ERRORE ZsTimerTaskGiornaliero: aggiornaAnagraficaCasoSIGESS : " + e2.getMessage(), e2);
				}
		    	
			}
		}
		 //SISO-1266
		 private List<CsAComponenteAnagCasoGit> getAnagraficheSenzaIdProvenienza(){
			 List<CsAComponenteAnagCasoGit> listCsAComponenteAnagCasoGit = new ArrayList<CsAComponenteAnagCasoGit>();   
			 BaseDTO bDto = new BaseDTO();
		    	bDto.setEnteId(enteId);
		    	bDto.setUserId("getAnagraficheSenzaIdProvenienza"); //da valutare se cambiare Utente
			  	try {
		    		
		    	     List<CsAComponenteAnagCasoGit> listCsAComponenteAnagCasoGitDB = soggettoService.getAnagraficaComponenteCasoNoIdProvenienza(bDto);
//			  		 List<CsAComponenteAnagCasoGit> listCsAComponenteAnagCasoGitDBDEBUG = soggettoService.getAnagraficaComponenteCasoNoIdProvenienza(bDto);
//		    	      List<CsAComponenteAnagCasoGit> listCsAComponenteAnagCasoGitDB = listCsAComponenteAnagCasoGitDBDEBUG.subList(0, 1);
		    		Date oggi = new Date();
		    		logger.debug("getAnagraficheSenzaIdProvenienza - trovati "+ listCsAComponenteAnagCasoGitDB.size()+" soggetti");
					for(CsAComponenteAnagCasoGit anagraficaCaso: listCsAComponenteAnagCasoGitDB) {
						try {
							Date ultimaModifica = anagraficaCaso.getDtMod()!=null ? anagraficaCaso.getDtMod() : anagraficaCaso.getDtIns();
							long millisDiff = oggi.getTime() - ultimaModifica.getTime();
							//1 Giorni = 86400000 Millisecondi
							int days = (int) (millisDiff / 86400000);
							if(days>= 7){
								listCsAComponenteAnagCasoGit.add(anagraficaCaso);
							}
						}catch(Exception ex) {
					  		logger.error("getAnagraficheSenzaIdProvenienza - Attenzione si è verificato un errore durante la procedura di estrazione dei soggetti privi di 'IdProvenienza' per anagraficaCaso ID" + anagraficaCaso.getId(), ex );
					  	}
					}
			  	}catch(Exception ex) {
			  		logger.error("getAnagraficheSenzaIdProvenienza - Attenzione si è verificato un errore durante la procedura di estrazione dei soggetti privi di 'IdProvenienza'", ex );
			  	}
			  	logger.debug("getAnagraficheSenzaIdProvenienza - elaborazione di "+ listCsAComponenteAnagCasoGit.size()+" soggetti in corso (ultima modifica > 7 gg)");
			  
			  	return listCsAComponenteAnagCasoGit;
		 }
		 
		 private List<CsAComponenteAnagCasoGit> getAnagraficheByTipoRicerca(String tipoRicerca){
			 List<CsAComponenteAnagCasoGit> listCsAComponenteAnagCasoGit = new ArrayList<CsAComponenteAnagCasoGit>() ;   
			 BaseDTO bDto = new BaseDTO();
		    	bDto.setEnteId(enteId);
		    	bDto.setUserId("getAnagraficheByTipoRicerca"); //da valutare se cambiare Utente
			  	try {
		    		
			  		  bDto.setObj(tipoRicerca);
					  List<CsAComponenteAnagCasoGit> listCsAComponenteAnagCasoGitDB  = soggettoService.getAnagraficaComponenteCasoProvenienza(bDto);
			    	  Date oggi = new Date();
			    	 
		    		
		    		logger.debug("getAnagraficheByTipoRicerca["+tipoRicerca+"] - trovati "+ listCsAComponenteAnagCasoGitDB.size()+" soggetti");
					for(CsAComponenteAnagCasoGit anagraficaCaso: listCsAComponenteAnagCasoGitDB) {
						Date ultimaModifica = anagraficaCaso.getDtMod()!=null ? anagraficaCaso.getDtMod() : anagraficaCaso.getDtIns();
						long millisDiff =  oggi.getTime() - ultimaModifica.getTime() ;
						int days = (int) (millisDiff / 86400000);
						if(days>= 7){
							listCsAComponenteAnagCasoGit.add(anagraficaCaso);
						}
					}
			  	}catch(Exception ex) {
			  		logger.error("getAnagraficheByTipoRicerca - Attenzione si è verificato un errore durante la procedura di estrazione dei soggetti tipo["+tipoRicerca+"]", ex );
			  	}
			  	
			  	logger.debug("getAnagraficheByTipoRicerca["+tipoRicerca+"] - elaborazione di "+ listCsAComponenteAnagCasoGit.size()+" soggetti in corso (ultima modifica > 7 gg)");
			  	
			  	return listCsAComponenteAnagCasoGit;
		 }
		 private String getEnteId(long idCaso) throws Exception {
		    	BaseDTO bDto = new BaseDTO();
		    	bDto.setObj(idCaso);
		    	bDto.setEnteId(enteId);
		    	CsIterStepByCasoDTO  iterStepDTO = iterStepService.getLastIterStepByCasoDTOAnonimo(bDto);
		     return	iterStepDTO.getCsItStep()!=null ? iterStepDTO.getCsItStep().getCsOOrganizzazione1().getCodRouting() : null;
		 }
		 
		 //PREVEDERE L'AGGIORNAMENTO DELLE ANAGRAFICHE SENZA idProvenienza
		 private void aggiornaAnagraficaCasoSenzaIdProvenienza() {
			 logger.debug("__ INIZIO ZsTimerTaskGiornaliero: aggiornaAnagraficaCasoSenzaIdProvenienza");
			    
			 String tipoRicerca = "";
			 List<CsAComponenteAnagCasoGit> listCsAComponenteAnagCasoGit = getAnagraficheSenzaIdProvenienza();
			 
		    for(CsAComponenteAnagCasoGit anagraficaCaso: listCsAComponenteAnagCasoGit) {
				
		    	if(maxVarAnagraficheElaborate()) break; 
		    	
		    	String cf = anagraficaCaso.getCsASoggetto().getCsAAnagrafica().getCf();
				
				RicercaAnagraficaResult res = null;
				if((res == null || res.getElencoAssisiti().size() ==0) && CsUiCompBaseBean.isAnagrafeComunaleInternaAbilitata() ){
					try{
						String iterEnteId = getEnteId(anagraficaCaso.getCsASoggetto().getCsACaso().getId());
						res = this.ricercaSoggettoDiogene(iterEnteId, cf);
						if(res.getElencoAssisiti().size() > 0) {
							tipoRicerca = DataModelCostanti.TipoRicercaSoggetto.DEFAULT;
						}
						
					}catch(Exception ex) {
				  		logger.error("aggiornaAnagraficaCasoSenzaIdProvenienza - Attenzione si è verificato un errore durante la ricerca del soggetto in ANAGRAFE COMUNALE [ID CS_A_COMPONENTE_ANAG_CASO_GIT:"+ anagraficaCaso.getId() +"]", ex );
				  	}
			
				}
				if((res == null || res.getElencoAssisiti().size() ==0) && CsUiCompBaseBean.isAnagrafeSigessAbilitata()){
					try{
						RicercaAnagraficaParams params = new RicercaAnagraficaParams( DataModelCostanti.TipoRicercaSoggetto.SIGESS, true);
						params.setEnteId(enteId);
						params.setCf(cf);
						res = CsUiCompBaseBean.getPersonaDaAnagSigess(params);
						if(res.getElencoAssisiti().size() > 0) {
							tipoRicerca = DataModelCostanti.TipoRicercaSoggetto.SIGESS;
						}
					}catch(Exception ex) {
				  		logger.error("aggiornaAnagraficaCasoSenzaIdProvenienza - Attenzione si è verificato un errore durante la ricerca del soggetto in ANAGRAFE SIGESS [ID CS_A_COMPONENTE_ANAG_CASO_GIT:"+ anagraficaCaso.getId() +"]", ex );
				  	}
				}
				
				if((res == null || res.getElencoAssisiti().size() ==0) && CsUiCompBaseBean.isAnagrafeSanitariaUmbriaAbilitata()) {
					try{
						RicercaAnagraficaParams params = new RicercaAnagraficaParams( DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA, true);
						params.setEnteId(enteId);
						params.setCf(cf);
						params.setCaricaMedico(true);
						res = CsUiCompBaseBean.getDettaglioPersona(params);
						
						if(res.getElencoAssisiti().size() > 0) {
							tipoRicerca = DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA;
						}
					}catch(Exception ex) {
				  		logger.error("aggiornaAnagraficaCasoSenzaIdProvenienza - Attenzione si è verificato un errore durante la ricerca del soggetto in ANASAN UMBRIA [ID CS_A_COMPONENTE_ANAG_CASO_GIT:"+ anagraficaCaso.getId() +"]", ex );
				  	}
				}
			
				if((res == null || res.getElencoAssisiti().size() ==0) && CsUiCompBaseBean.isAnagrafeSanitariaMarcheAbilitata()){
					try{
						RicercaAnagraficaParams params = new RicercaAnagraficaParams( DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE, true);
						params.setEnteId(enteId);
						params.setCf(cf);
						res = CsUiCompBaseBean.getDettaglioPersona(params);
						if(res.getElencoAssisiti().size() > 0) {
							tipoRicerca = DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE;
						}
					}catch(Exception ex) {
				  		logger.error("aggiornaAnagraficaCasoSenzaIdProvenienza - Attenzione si è verificato un errore durante la ricerca del soggetto in ANASAN MARCHE [ID CS_A_COMPONENTE_ANAG_CASO_GIT:"+ anagraficaCaso.getId() +"]", ex );
				  	}
				}
			
				try{
					List<PersonaDettaglio> lista = new ArrayList<PersonaDettaglio>();
					if(res!=null && !res.getElencoAssisiti().isEmpty()){
						if(res.getMessaggio()==null)
							elaboraVariazioniCaso(anagraficaCaso, res.getElencoAssisiti(), tipoRicerca);
						else
							logger.error("aggiornaAnagraficaCasoSenzaIdProvenienza : Errore ricerca soggetto ["+cf+"]["+anagraficaCaso.getCsASoggetto().getCsAAnagrafica().getIdOrigWs()+"] "+tipoRicerca+" CODICE["+res.getCodice()+"]"+ res.getMessaggio(), res.getEccezione());
					}
			    }catch(Exception ex) {
			  		logger.error("aggiornaAnagraficaCasoSenzaIdProvenienza - Attenzione si è verificato un errore durante la procedura di elaborazione delle variazioni [ID CS_A_COMPONENTE_ANAG_CASO_GIT : "+ anagraficaCaso.getId() +"]", ex );
			  	}
		    }
		    logger.debug("__ FINE ZsTimerTaskGiornaliero: aggiornaAnagraficaCasoSenzaIdProvenienza");
		 }
		 
		 
		 private PersonaDettaglio initFromSitDPersona(SitDPersona soggetto, String enteRouting) {
			 PersonaDettaglio pDett  = new PersonaDettaglio();	
			 
			 pDett.setEmigrato(soggetto.getDataEmi()!=null && (soggetto.getDataImm()==null || soggetto.getDataImm().before(soggetto.getDataEmi())));
			 Date dataFineValidita = null;
			 Date dataOdierna  = new Date();
			 if(soggetto.getDtFineDato() != null && soggetto.getDtFineDato().compareTo(DataModelCostanti.END_DATE) != 0) {
				 dataFineValidita =soggetto.getDtFineDato(); 
			 }
			 else if(soggetto.getDtFineVal()  != null && soggetto.getDtFineVal().compareTo(DataModelCostanti.END_DATE) != 0) {
				 dataFineValidita =soggetto.getDtFineVal(); 
			 }
			 
			 if(dataFineValidita == null || (dataFineValidita != null && dataFineValidita.compareTo(dataOdierna) > 0)) {
				   String cf = soggetto.getCodfisc();
					IndirizzoAnagDTO indirizzo = null; //getResidenzaFromAnagrafe(cf);
					
					RicercaSoggettoAnagrafeDTO dto = new RicercaSoggettoAnagrafeDTO();
					dto.setEnteId(enteRouting);
					dto.setCodFis(cf);
					List<IndirizzoAnagDTO> lst = anagrafeService.getIndirizzoResidenzaByCodFisc(dto);
					if(lst!=null && !lst.isEmpty())
						indirizzo = lst.get(0);
						
					
					if (indirizzo!=null) {
						 
						pDett.setIndirizzoResidenza(indirizzo.getIndirizzo());
						String civico = indirizzo.getCivicoNumero() != null ? indirizzo.getCivicoNumero() : "";
						civico+= indirizzo.getCivicoAltro() !=null ?  " "+ indirizzo.getCivicoAltro() : "";
						pDett.setCivicoResidenza(civico.trim());
						
						AmTabComuni comuneResidenza = luoghiService.getComuneItaByIstat(indirizzo.getComCod());
						pDett.setComuneResidenza(comuneResidenza);
								
					}else{
							logger.debug("initFromSitDPersona : Implementare recupero stato estero di residenza");
							//TODO:Implementare recupero 
					}
					
					pDett.setCognome(soggetto.getCognome());
					pDett.setNome(soggetto.getNome());
					pDett.setDataNascita(soggetto.getDataNascita());
					pDett.setDefunto(soggetto.getDataMor()!= null ? true :false);
					
					pDett.setSesso(soggetto.getSesso());

				
					ComponenteFamigliaDTO compDto = new ComponenteFamigliaDTO();
					compDto.setEnteId(enteRouting);
					compDto.setPersona(soggetto);
					compDto = anagrafeService.fillInfoAggiuntiveComponente(compDto);

					pDett.setStatoCivile(soggetto.getStatoCivile());
					 
					//Cittadinanza
					pDett.setCittadinanza( compDto.getCittadinanza());
					pDett.setCodfisc(cf);
					pDett.setIdentificativo(soggetto.getIdExt());
					//GESTIONE COMUNE
					if("ITALIA".equals(compDto.getDesStatoNas())) {
						if(compDto.getCodComNas()!=null){
						 	pDett.setComuneNascita(luoghiService.getComuneItaByIstat(compDto.getCodComNas()));
						} 
					} else {
						if(compDto.getIstatStatoNas()!=null){
							if(!"ITALIA".equalsIgnoreCase(compDto.getDesStatoNas())){
							  
								pDett.setNazioneNascita( findNazione(compDto.getCodStatoNas(), compDto.getDesStatoNas()));
				    		}	 
						} 
					}

			 }
			return pDett;	
		 }
		 private AmTabNazioni findNazione(String codice, String descrizione) {
				AmTabNazioni nazione = null;
				if(codice!=null && !codice.isEmpty()){
					codice = "100".equalsIgnoreCase(codice) ? "1" : codice;
					try{
						nazione = luoghiService.getNazioneByIstat(codice);
					}catch(Exception e){}
					if(nazione==null && descrizione!=null){
						logger.debug("Ricerco Nazione con cod.istat "+codice+ " --> NON TROVATA!");
					    nazione = new AmTabNazioni();
					    nazione.setCodIstatNazione(codice);
					    nazione.setNazione(descrizione);
					}
				}
				
				return nazione;
			}
		 //FINE
		 private void aggiornaAnagraficaCasoGIT(RicercaAnagraficaResult ricercaAnagraficaResult, CsAComponenteAnagCasoGit anagraficaCaso, String tipoRicerca) {
			 try {
	   	       
	   	      	List<PersonaDettaglio> lista = new ArrayList<PersonaDettaglio>();
					if(ricercaAnagraficaResult!=null){
						if(ricercaAnagraficaResult.getMessaggio()==null)
							elaboraVariazioniCaso(anagraficaCaso, ricercaAnagraficaResult.getElencoAssisiti(), tipoRicerca);
						else
							logger.error("aggiornaAnagaggiornaAnagraficaCasoGIT  :"+tipoRicerca+" CODICE["+ricercaAnagraficaResult.getCodice()+"]"+ ricercaAnagraficaResult.getMessaggio(), ricercaAnagraficaResult.getEccezione());
					}
				 }catch(Exception e){
					logger.error(e);
				 }
		 }
		 
		 private void elaboraVariazioniCaso(CsAComponenteAnagCasoGit temp, List<PersonaDettaglio> lista, String tipoRicerca){
			if(!lista.isEmpty()){
			    BaseDTO bDto = new BaseDTO();
		    	bDto.setEnteId(enteId);
		    	bDto.setUserId("aggiornaAnagraficaCasoGIT"); //da valutare se cambiare Utente   
		    	bDto.setObj(temp);
				bDto.setObj2(lista);
				bDto.setObj3(tipoRicerca);
				soggettoService.elaboraVariazioniSoggettoCasoGit(bDto);
				variazioniAnagraficheElaborate++;
			}
		 }
		 
		 private void aggiornaAnagraficaCasoSIRPS(){	
				
				if(CsUiCompBaseBean.isAnagrafeSanitariaMarcheAbilitata()){
					logger.debug("__ INIZIO ZsTimerTaskGiornaliero: aggiornaAnagraficaCasoSIRPS");
				    String tipoRicerca = DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE;
				    List<CsAComponenteAnagCasoGit>  listaCasiSIRPS =	this.getAnagraficheByTipoRicerca(tipoRicerca);
			    	try {
			    		
			    	 	for(CsAComponenteAnagCasoGit anagraficaCaso: listaCasiSIRPS) {
							try {
								if(maxVarAnagraficheElaborate()) break;
			    	 		    String cf = anagraficaCaso.getCsASoggetto().getCsAAnagrafica().getCf();
								String idSirps = anagraficaCaso.getCsASoggetto().getCsAAnagrafica().getIdOrigWsId();
								
								RicercaAnagraficaParams params = new RicercaAnagraficaParams(tipoRicerca, true);
								params.setEnteId(enteId);
								params.setIdentificativo(idSirps);
								params.setCf(cf);
								RicercaAnagraficaResult res = CsUiCompBaseBean.getDettaglioPersona(params);
								
								this.aggiornaAnagraficaCasoGIT(res, anagraficaCaso, tipoRicerca);
							}catch(Exception ex) {
								
								logger.error("__ERRORE ZsTimerTaskGiornaliero: aggiornaAnagraficaCasoSIRPS  per anagraficaCaso ID [" + anagraficaCaso.getId() + "]"  + ex.getMessage(), ex);
							}
						}
					
						logger.debug("__ FINE ZsTimerTaskGiornaliero: aggiornaAnagraficaCasoSIRPS");
						
			    	} catch (Exception e2) {
						logger.error("__ERRORE ZsTimerTaskGiornaliero: aggiornaAnagraficaCasoSIRPS : " + e2.getMessage(), e2);
					}
			    	
				}
			}
		 private void aggiornaAnagraficaCasoAnagrafeSanitariaUmbria(){	
				
				if(CsUiCompBaseBean.isAnagrafeSanitariaUmbriaAbilitata()){
					logger.debug("__ INIZIO ZsTimerTaskGiornaliero: aggiornaAnagraficaCasoAnagrafeSanitariaUmbria");
				   	String tipoRicerca = DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA;
				    List<CsAComponenteAnagCasoGit>  listaCasiSANITA_UMBRIA = this.getAnagraficheByTipoRicerca(tipoRicerca);
			    	try {
			    		
			    	    for(CsAComponenteAnagCasoGit anagraficaCaso: listaCasiSANITA_UMBRIA) {
							try {
								if(maxVarAnagraficheElaborate()) break;
				    	    	String cf = anagraficaCaso.getCsASoggetto().getCsAAnagrafica().getCf();
								String idSirps = anagraficaCaso.getCsASoggetto().getCsAAnagrafica().getIdOrigWsId();
								
								RicercaAnagraficaParams params = new RicercaAnagraficaParams(tipoRicerca, true);
								params.setEnteId(enteId);
								params.setIdentificativo(idSirps);
								params.setCf(cf);
								RicercaAnagraficaResult res = CsUiCompBaseBean.getDettaglioPersona(params);
								
								this.aggiornaAnagraficaCasoGIT(res, anagraficaCaso, tipoRicerca);
								
							   }
								catch(Exception ex) {
									logger.error("__ERRORE ZsTimerTaskGiornaliero: aggiornaAnagraficaCasoAnagrafeSanitariaUmbria  per anagraficaCaso ID [" + anagraficaCaso.getId() + "] " + ex.getMessage(), ex);
								}
							}
					
						logger.debug("__ FINE ZsTimerTaskGiornaliero: aggiornaAnagraficaCasoAnagrafeSanitariaUmbria");
						
			    	} catch (Exception e2) {
						logger.error("__ERRORE ZsTimerTaskGiornaliero: aggiornaAnagraficaCasoAnagrafeSanitariaUmbria : " + e2.getMessage(), e2);
					}
			    	
				}
			}
		 private void aggiornaAnagraficaInterna(){	
				
				if(CsUiCompBaseBean.isAnagrafeComunaleInternaAbilitata()) {
				   logger.debug("__ INIZIO ZsTimerTaskGiornaliero: aggiornaAnagraficaInterna");
				   String tipoRicerca = DataModelCostanti.TipoRicercaSoggetto.DEFAULT;
				   List<CsAComponenteAnagCasoGit>  listaCasiANAG_DIOGENE = this.getAnagraficheByTipoRicerca(tipoRicerca);
			    	
			    		for(CsAComponenteAnagCasoGit anagraficaCaso: listaCasiANAG_DIOGENE) {
			    			try {
			    				if(maxVarAnagraficheElaborate()) break;
						 		String cf = anagraficaCaso.getCsASoggetto().getCsAAnagrafica().getCf();
								String idOrigWs = anagraficaCaso.getCsASoggetto().getCsAAnagrafica().getIdOrigWsId();
								String iterEnteId = getEnteId(anagraficaCaso.getCsASoggetto().getCsACaso().getId());
								RicercaAnagraficaResult res = ricercaSoggettoDiogene(iterEnteId, cf);
								this.aggiornaAnagraficaCasoGIT(res, anagraficaCaso, tipoRicerca);
								
			    			} catch (Exception e2) {
								logger.error("__ERRORE ZsTimerTaskGiornaliero: aggiornaAnagraficaInterna :  per anagraficaCaso ID [" + anagraficaCaso.getId() + "]" + e2.getMessage(), e2);
							} 
						}
			    	 
					
						logger.debug("__ FINE ZsTimerTaskGiornaliero: aggiornaAnagraficaInterna fine.");
						
			    
			    	
				}
			}
		//SISO-1127 Fine
	private RicercaAnagraficaResult ricercaSoggettoDiogene(String enteId, String cf){
		RicercaAnagraficaResult res = new RicercaAnagraficaResult(); 
		if(!StringUtils.isEmpty(enteId)){
			RicercaSoggettoAnagrafeDTO rsDto = new RicercaSoggettoAnagrafeDTO();
			rsDto.setEnteId(enteId);
			rsDto.setCodFis(cf);
	    	rsDto.setDtRif(new Date());
	    	 	
			List<SitDPersona> list = anagrafeService.searchSISOAnagrafiche(rsDto);
			//if(list != null ) res = new RicercaAnagraficaResult(); 
			for(SitDPersona s: list){
				 PersonaDettaglio pDettaglio =  this.initFromSitDPersona(s, enteId);
				 res.addAssistito(pDettaglio);
			}
		}
		
		return res;
	}
		 
	private void aggiornaFamiglieSIGESS(){	
			
		if(CsUiCompBaseBean.isAnagrafeSigessAbilitata()){
		    logger.debug("__ INIZIO ZsTimerTaskGiornaliero: aggiornaFamiglieGIT");
			BaseDTO bDto = new BaseDTO();
	    	bDto.setEnteId(enteId);
	    	bDto.setUserId("aggiornaFamiglieSIGESS");
			String tipoRicerca = DataModelCostanti.TipoRicercaSoggetto.SIGESS;
	    	try {
	    		
	    		AccessTableParentiGitSessionBeanRemote parentiService = (AccessTableParentiGitSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableParentiGitSessionBean");
	    	    
	    		bDto.setObj(tipoRicerca);
	    		List<CsAFamigliaGruppoGit> listaFamiglieSigess = parentiService.getFamigliaGruppoProvenienza(bDto);
	    		Date oggi = new Date();
	    		logger.debug("aggiornaFamiglieSIGESS - elaborazione di "+listaFamiglieSigess.size()+" famiglie in corso...");
				
	    		for(CsAFamigliaGruppoGit fam: listaFamiglieSigess) {
					Date ultimaModifica = fam.getDtMod()!=null ? fam.getDtMod() : fam.getDtIns();
					long millisDiff =  oggi.getTime() - ultimaModifica.getTime();
					int days = (int) (millisDiff /  86400000);
					 if(days>=7){
						String cf = fam.getCsASoggetto().getCsAAnagrafica().getCf();
						String idSigess = fam.getCsASoggetto().getCsAAnagrafica().getIdOrigWsId();
						
						RicercaAnagraficaParams params = new RicercaAnagraficaParams(tipoRicerca, true);
						params.setEnteId(enteId);
						params.setIdentificativo(idSigess);
						params.setCf(cf);
						RicercaAnagraficaResult res = CsUiCompBaseBean.getComposizioneFamiliare(params);
						
						List<FamiliareDettaglio> lista = new ArrayList<FamiliareDettaglio>();
						if(res!=null){
							if(res.getMessaggio()==null){
								lista = res.getElencoFamiliari();
								bDto.setObj(fam);
								bDto.setObj2(lista);
								parentiService.elaboraVariazioniFamigliaGruppoGit(bDto);
							}else
								logger.error("Errore ricerca componenti familiari per il soggetto["+cf+"]["+fam.getCsASoggetto().getCsAAnagrafica().getIdOrigWs()+"] "+tipoRicerca+" CODICE["+res.getCodice()+"]"+ res.getMessaggio(), res.getEccezione());
						}
					}
				}
			
				logger.debug("__ FINE ZsTimerTaskGiornaliero: aggiornaFamiglieGIT");
				
	    	} catch (Exception e2) {
				logger.error("__ERRORE ZsTimerTaskGiornaliero: aggiornaFamiglieGIT : " + e2.getMessage(), e2);
			}
	    	
		}
	}

	private void segnalaModificheSchedaCompletaUDC() {
		BaseDTO dto = new BaseDTO();
    	
    	//fillEnte(dto);
		dto.setEnteId(enteId);
		try{
			List<VSsSchedeUdcDiff> result = casoService.controllaModificheSchedaCompletaUDC(dto);
			if(result!=null&&result.size()>0) {
				logger.info("\n\n Ho trovato "+ result.size() +" schede da controllare \n\n");
				for(VSsSchedeUdcDiff o : result){
		
					String denominazione = o.getCognome()+" "+o.getNome()+" (cod.fiscale:"+o.getCf()+")";
					
					dto.setObj(o.getIdScheda());
					ConfrontoSsCsDTO resSS = diarioService.estraiDatiSchedaSS(dto); //1
					
					dto.setObj(o.getCf());
					dto.setObj2(o.getDt());
					ConfrontoSsCsDTO resCS = diarioService.estraiDatiSchedaCS(dto); //2
		
					boolean differenze = false;
					//controllo tutte le differenze
					StringBuilder msg = new StringBuilder();
					StringBuilder titolo = new StringBuilder();
						
					List<String> campiVariati = new ArrayList<String>();
					
					if(resSS!=null && resCS!=null){
						titolo.append("Aggiornamento dati schede "+CsUiCompBaseBean.getNomeIstanzaSS()+" per il soggetto "+denominazione);
						msg.append("Nella scheda "+CsUiCompBaseBean.getNomeIstanzaSS()+" (Identificativo:"+resSS.getIdentificativo()+") per il soggetto "+denominazione+
								" sono state individuate delle differenze rispetto ai dati della cartella sociale (Identificativo:"+resCS.getIdentificativo()+"). "
							  + "Le variazioni interressano i seguenti campi: ");
							
						if(isCampoVariato(resSS.getNome(), resCS.getNome())){
							campiVariati.add("Nome");
							differenze = true;
						 }
						if(isCampoVariato(resSS.getCognome(), resCS.getCognome())){
							campiVariati.add("Cognome");
							differenze = true;
						}if(isCampoVariato(resSS.getCf(), resCS.getCf())){
							campiVariati.add("CodiceFiscale");
							differenze = true;
						}
						if(isCampoVariato(resSS.getCittadinanza(), resCS.getCittadinanza())){
							campiVariati.add("Cittadinanza");
							differenze = true;
						}
						if(isCampoVariato(resSS.getStatoCivile(),resCS.getStatoCivile())){
							campiVariati.add("StatoCivile");
							 differenze = true;
						}
						if(isCampoVariato(resSS.getSenzaFissaDimora(), resCS.getSenzaFissaDimora())){
							campiVariati.add("SenzaFissaDimora");
							 differenze = true;
						}
						if(isCampoVariato(resSS.getResidenzaIndirizzo(),resCS.getResidenzaIndirizzo())){
							campiVariati.add("Indirizzo di residenza");
							 differenze = true;
						}
						if(isCampoVariato(resSS.getResidenzaComune(),resCS.getResidenzaComune())){
							 campiVariati.add("Comune di residenza");
							 differenze = true;
						}
		/*				if(isCampoVariato(codCom1, codCom2)){
							 campiVariati.add("CodiceComune");
							 differenze = true;
						}
						if(isCampoVariato(prov1,prov2)){
							campiVariati.add("Provincia");
							 differenze = true;
						}*/
						if(isCampoVariato(resSS.getResidenzaNazione(), resCS.getResidenzaNazione())){
							 campiVariati.add("Nazione di residenza");
							 differenze = true;
						}
						if(isCampoVariato(resSS.getLavoro(), resCS.getLavoro())){
							 campiVariati.add("Lavoro");
							 differenze = true;
						}
						if(isCampoVariato(resSS.getProfessione(), resCS.getProfessione())){
							 campiVariati.add("Professione");
							 differenze = true;
						}
						
						try {
							if(resSS.getAbitazione()!=null&&resCS.getAbitazione()!=null) {
							IAbitazione abi1 = AbitazioneManBaseBean.initByModel(resSS.getAbitazione());
							IAbitazione abi2 = AbitazioneManBaseBean.initByModel(resCS.getAbitazione());
							
							String tipoAbitazione1 = (abi1!=null?abi1.getTipoAbitazione():null);
							String tipoAbitazione2 = (abi2!=null?abi2.getTipoAbitazione():null);
			
							String titoloGodimento1 = (abi1!=null?abi1.getTitoloGodimento():null);
							String titoloGodimento2 = (abi1!=null?abi1.getTitoloGodimento():null);
						
							if(isCampoVariato(tipoAbitazione1, tipoAbitazione2)){
								 campiVariati.add("TipologiaAbitazione");
								 differenze = true;
							}
							if(isCampoVariato(titoloGodimento1, titoloGodimento2)){
								campiVariati.add("TitoloGodimento");
								 differenze = true;
							}
							}
						} catch (Exception e) {
							logger.error("Errore nell'estrazione dati abitazione", e);			
							}
						
						try {
							
							if(resSS.getStranieri()!=null && resCS.getStranieri()!=null) {
							IStranieri stra1 = StranieriManBaseBean.initByModel(resSS.getStranieri());
							IStranieri stra2 = StranieriManBaseBean.initByModel(resCS.getStranieri());
							StranieriBean s1 = stra1.getSelected();
							String codiceP1 = s1.getPermesso().getCodice();
							String statoP1 = s1.getStatoPermessoSogg();
							
							StranieriBean s2 = stra2.getSelected();
							String codiceP2 = s2.getPermesso().getCodice();
							String statoP2 = s2.getStatoPermessoSogg();
							
							if(isCampoVariato(codiceP1, codiceP2)){
								campiVariati.add("CodicePermessoSoggiorno");
								 differenze = true;
							}
							if(isCampoVariato(statoP1, statoP2)){
								campiVariati.add("StatoPermessoSoggiorno");
								 differenze = true;
							}
							}
						} catch (Exception e) {
							logger.error("Errore nell'estrazione dat permesso di soggiorno", e);
						}
									
						if (differenze){
							
							for(int i=0; i<campiVariati.size(); i++){
								String c = campiVariati.get(i);
								msg.append(c);
								if(i<campiVariati.size()-1) msg.append(", ");
							}
							
							String titDescrizione = titolo.toString();
				    		String descrizione = msg.toString();
							CsACaso caso = resCS.getCaso();
					
			    	
							logger.info(titDescrizione);
							logger.info(descrizione+"\n\n");
			    	
			    		
				    		if(caso!=null) {
					    		
				    			BaseDTO bDto = new BaseDTO();
					        	bDto.setEnteId(dto.getEnteId()); 
					        	bDto.setObj(caso.getId());
					        	
					    		CsACasoOpeTipoOpe tipoOpe = casoService.findCasoOpeResponsabile(bDto);
					        	
					    		CsOOperatoreSettore opeTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore(): null;
					    				
					    		//Responsabile altrimenti --> creatore
					    		if(opeTo == null) {		
					    			opeTo = casoService.findCreatoreCaso(bDto);
					    		}
					    		
					    		boolean inserisco = this.validaInserimentoNuovoAlert(DataModelCostanti.TipiAlertCod.MODUDC, caso, opeTo.getId());
								if(inserisco)
									addAlert(TipiAlertCod.MODUDC, caso, titDescrizione, descrizione, null, null,null, null, opeTo, null, null);
								else 
									logger.info(" __ Sospeso Inserimento Alert per segnalaModificheSchedaCompletaUDC: già presente [casoId:" + caso.getId()+"]");
					    		
				    		} else
				    			logger.error("Attenzione!!! impossibile determinare il CasoID per la segnalazione:"+titDescrizione+" - "+descrizione);
					
						}
					}
					    	
		    	}
			} else {
				logger.info("segnalaModificheSchedaCompletaUDC: Non ci sono differenze di schede da controllare");
			}
		} catch (Exception e2) {
			logger.error("__ERRORE ZsTimerTaskGiornaliero: segnalaModificheSchedaCompletaUDC : " + e2.getMessage(), e2);
		}
	}
	
	private boolean isCampoVariato(Object campo1, Object campo2){
		boolean variato =  
			   (campo1!=null && campo2==null)||
			   (campo1==null && campo2!=null)||
			   (campo1!=null && campo2!=null && !campo1.toString().trim().equalsIgnoreCase(campo2.toString().trim()));
		if(variato)
			logger.info("[SS:"+campo1+"][CS:"+campo2+"]");
		
		return variato;
	}

	public void aggiornaAlertComponentiFamiliari() throws NamingException{
		
		logger.debug("__ INIZIO ZsTimerTaskGiornaliero: aggiornaAlertComponentiFamiliari_");
	
		BaseDTO bDto = new BaseDTO();
		IterDTO itDto = new IterDTO();
		OperatoreDTO opDto = new OperatoreDTO();

    	bDto.setEnteId(enteId);
    	itDto.setEnteId(enteId);
    	opDto.setEnteId(enteId);
		
    	try {
    		
    		AccessTableParentiGitSessionBeanRemote parentiService = (AccessTableParentiGitSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableParentiGitSessionBean");
    	
			List<CsAFamigliaGruppoGit> listaFamiglieAggiornate = parentiService.getFamigliaGruppoGitAggiornate(bDto);
			for(CsAFamigliaGruppoGit fam: listaFamiglieAggiornate) {
				
				CsACaso caso = fam.getCsASoggetto().getCsACaso();
				
				CsOOperatoreSettore opeTo = findOperatoreTo(caso);
				
				CsOSettore settTo = null ;
				CsOOrganizzazione orgTo = null;
				
				settTo = opeTo!=null ? opeTo.getCsOSettore() : null;
				// orgTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOSettore().getCsOOrganizzazione() : null;
				
				if(opeTo!=null || settTo!=null || orgTo!=null){
					boolean inserisco = this.validaInserimentoNuovoAlert(DataModelCostanti.TipiAlertCod.FAMIGLIA_GIT, caso, opeTo.getId());
					if(inserisco){
					
						//nuovo alert
						String nome = fam.getCsASoggetto().getCsAAnagrafica().getCognome() + " " + fam.getCsASoggetto().getCsAAnagrafica().getNome();
						String cf = fam.getCsASoggetto().getCsAAnagrafica().getCf();
						String descrizione = "Anagrafe comunale: sono state rilevate delle variazioni relative ai dati del nucleo familiare di "+ nome +" ("+ cf +").";
						String titDescrizione = "Notifica caso "+ nome + ": variazione situazione familiare";
						CsOOrganizzazione orgFrom = this.findOrganizzazioneDefault(TipiAlertCod.FAMIGLIA_GIT);
						logger.info(enteId + " __ Aggiungo Alert per aggiornamento famiglia: " + fam.getCsASoggetto().getCsAAnagrafica().getCf());
						addAlert(TipiAlertCod.FAMIGLIA_GIT, caso, titDescrizione, descrizione, null, null,null, orgFrom, opeTo, settTo, orgTo);
					}else
						logger.info(" __ Sospeso Inserimento Alert per aggiornamento famiglia: già presente [casoId:" + caso.getId()+"]");
				
					//creato l alert azzero la segnalazione
					fam.setFlgSegnalazione(false);
					
					bDto.setObj(fam);
					parentiService.updateFamigliaGruppoGit(bDto);
				}
			}
		
			logger.debug("__ FINE ZsTimerTaskGiornaliero: aggiornaAlertComponentiFamiliari_");
			
    	} catch (Exception e2) {
			logger.error("__ERRORE ZsTimerTaskGiornaliero: aggiornaAlertComponentiFamiliari : " + e2.getMessage(), e2);
		}
    	
	}
	
	public void aggiornaAlertRelazioni() throws NamingException{
		
		logger.debug("__ INIZIO ZsTimerTaskGiornaliero: aggiornaAlertRelazioni__");
			
		CeTBaseObject idto = new CeTBaseObject();
    	BaseDTO bDto = new BaseDTO();
		IterDTO itDto = new IterDTO();
		OperatoreDTO opDto = new OperatoreDTO();

		
    	bDto.setEnteId(enteId);
    	itDto.setEnteId(enteId);
    	opDto.setEnteId(enteId);
    	idto.setEnteId(enteId);
    
    	try {
    		AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableDiarioSessionBean");
    		
			List<CsDRelazione> listaRelazioni = diarioService.findRelazioniInScadenza(idto);
			if(listaRelazioni.size()>0 ) logger.info(" __ Trovate "+listaRelazioni.size()+" relazioni in scadenza.");
			for(CsDRelazione rel : listaRelazioni){
				
				CsACaso caso = rel.getCsDDiario().getCsACaso();
				CsOOperatoreSettore opeTo = findOperatoreTo(caso);
				
				CsOSettore settTo = null;
				CsOOrganizzazione orgTo = null;
				
				//Notifico al settore, solo se non trovo un operatore responsabile!
				if(opeTo==null) settTo = opeTo!=null ? opeTo.getCsOSettore() : null;
				//CsOOrganizzazione orgTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOSettore().getCsOOrganizzazione() : null;
			
				if(opeTo!=null || settTo!=null || orgTo!=null){
					boolean inserisco = this.validaInserimentoNuovoAlert(DataModelCostanti.TipiAlertCod.RELAZIONE, caso, opeTo.getId());
					if(inserisco){
						//nuovo alert
						String nome = caso.getCsASoggetto().getCsAAnagrafica().getCognome() + " " + caso.getCsASoggetto().getCsAAnagrafica().getNome();
						String cf = caso.getCsASoggetto().getCsAAnagrafica().getCf();
						String descrizione = "Inserire una nuova attivit&agrave; professionale nel fascicolo di "+ nome +" ("+ cf +"). <br/>";
						descrizione += rel.getDataProssimaRelazioneDal()!=null ? "Data prevista prossima attivit&agrave; dal:"+CsUiCompBaseBean.ddMMyyyy.format(rel.getDataProssimaRelazioneDal()) : "";
						descrizione += rel.getDataProssimaRelazioneAl()!=null  ? " al "+ CsUiCompBaseBean.ddMMyyyy.format(rel.getDataProssimaRelazioneAl()) : "";
						String titDescrizione = "Promemoria caso "+ nome + ": inserire nuova attività professionale";
						
						CsOOrganizzazione orgFrom = this.findOrganizzazioneDefault(TipiAlertCod.RELAZIONE);
									
						logger.info(enteId + " __ Aggiungo Alert per inserimento nuova relazione: [casoId:" + caso.getId()+"]");
						addAlert(TipiAlertCod.RELAZIONE, caso, titDescrizione, descrizione, null, null, null, orgFrom, opeTo, settTo, orgTo);
					}else
						logger.info(enteId + " __ Sospeso Inserimento Alert per richiesta nuova relazione: già presente [casoId:" + caso.getId()+"]");
						
				
				//creato l alert azzero la segnalazione
				//TODO: gestire flag su relazione!?
				}
			}
		
			logger.debug("__ FINE ZsTimerTaskGiornaliero: aggiornaAlertRelazioni__");
			
    	} catch (Exception e2) {
			logger.error("__ERRORE ZsTimerTaskGiornaliero: aggiornaAlertRelazioni : " + e2.getMessage(), e2);
		}
		
	}
	
	public void aggiornaAlertProvvedimentiTribunale() throws NamingException{
		
		AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableDiarioSessionBean");
		logger.debug("__ INIZIO ZsTimerTaskGiornaliero: aggiornaAlertProvvedimentiTribunale__");
		BaseDTO bDto = new BaseDTO();
		IterDTO itDto = new IterDTO();
		OperatoreDTO opDto = new OperatoreDTO();
		
		itDto.setEnteId(enteId);
    	opDto.setEnteId(enteId);
    	bDto.setEnteId(enteId);
    	bDto.setObj2(DataModelCostanti.TipoDiario.PROVVEDIMENTI_TRIBUNALE);
    	
    	SimpleDateFormat formatta = new SimpleDateFormat("dd/MM/yyyy");
    	Date now = new Date();
    	
    	try{	
    		List<CsDValutazione> schede = diarioService.getSchedeValutazionebyTipo(bDto);
    		Date oggi = pulisciData(now);
    		
    		for (CsDValutazione csDValutazione : schede) {
    			
    			IProvvedimentiMinori bean;
    			String descrizione = "";
    			
    			bean = ProvvedimentiMinoriManBean.initIProvvedimentiMinoriTask(csDValutazione, csDValutazione.getCsDDiario().getCsACaso().getCsASoggetto());
				Date dtAdempimento = bean.getScadenzaAdempimento();
				Date dtAggiornamento = bean.getScadenzaAggiornamento();
				
				Date scadenzaAd = dtAdempimento != null ? pulisciData(dtAdempimento) : null;
				Date scadenzaAgg = dtAggiornamento != null ? pulisciData(dtAggiornamento) : null;
				
				if(scadenzaAd !=null && oggi.compareTo(scadenzaAd)>=0){
					String scadenzaAdempimento = formatta.format(scadenzaAd);
					
					descrizione += "Il provvedimento ha raggiunto la data scadenza adempimento  " + scadenzaAdempimento;
					
				}
				if(scadenzaAgg !=null && oggi.compareTo(scadenzaAgg)>=0){
					String scadenzaAggiorn = formatta.format(scadenzaAgg);
					
					if(descrizione.isEmpty()){
						descrizione += "Il provvedimento ha raggiunto la data scadenza aggiornamento " + scadenzaAggiorn;
					}else{
						descrizione += "   e la data scadenza aggiornamento " + scadenzaAggiorn;
					}
					
				}
				/*CONTROLLO DATE POI CREAZIONE ALERT**/
				
    			CsACaso caso = csDValutazione.getCsDDiario().getCsACaso();
    			CsOOperatoreSettore opeTo = findOperatoreTo(caso);
				
    			CsOSettore settTo = null;
				CsOOrganizzazione orgTo = null;
				
				//Notifico al settore, solo se non trovo un operatore responsabile!
				if(opeTo==null) settTo = opeTo!=null ? opeTo.getCsOSettore() : null;
			
				if((opeTo!=null || settTo!=null || orgTo!=null) && !descrizione.isEmpty()){
					boolean inserisco = this.validaInserimentoNuovoAlert(DataModelCostanti.TipiAlertCod.PROTRIB, caso, opeTo.getId());
					if(inserisco){
					String nome = caso.getCsASoggetto().getCsAAnagrafica().getCognome() + " " + caso.getCsASoggetto().getCsAAnagrafica().getNome();
					
					String titDescrizione = "Promemoria caso "+ nome + ": Scadenza Provvedimenti";
					
					CsOOrganizzazione orgFrom = this.findOrganizzazioneDefault(TipiAlertCod.PROTRIB);
								
					logger.info(enteId + " __ Aggiungo Alert per Provvedimento Tribunale : [casoId:" + caso.getId()+"]");
					addAlert(TipiAlertCod.PROTRIB, caso, titDescrizione, descrizione, null, null, null, orgFrom, opeTo, settTo, orgTo);
					}else
					logger.info(enteId + " __ Sospeso Inserimento Alert per scadenze provvedimento: già presente [casoId:" + caso.getId()+"]");
				}
			}
    		logger.debug("__ FINE ZsTimerTaskGiornaliero: aggiornaAlertProvvedimentiTribunale__");
    	}catch(Exception e){
    		logger.error("Errore ZsTimerTaskGiornaliero: aggiornaAlertProvvedimenti Tribunale " + e.getMessage(),e);
		}
	}
	
	private void aggiornaAlertPAI() {
		logger.debug("__ INIZIO ZsTimerTaskGiornaliero: aggiornaAlertPAI_");
		
		CeTBaseObject idto = new CeTBaseObject();
    	idto.setEnteId(enteId);
    
    	try {
    		AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableDiarioSessionBean");
    		
			List<CsDPai> listaPai = diarioService.findPaiAperti(idto);
			if(listaPai.size()>0 ) logger.info(" __ Trovati "+listaPai.size()+" PAI aperti.");
			for(CsDPai p : listaPai){
				
				CsACaso caso = p.getCsDDiario().getCsACaso();
				CsOOperatoreSettore opeTo = findOperatoreTo(caso);
				
				CsOSettore settTo = null;
				CsOOrganizzazione orgTo = null;
				
				String nome = caso.getCsASoggetto().getCsAAnagrafica().getCognome() + " " + caso.getCsASoggetto().getCsAAnagrafica().getNome();
				String cf = caso.getCsASoggetto().getCsAAnagrafica().getCf();
				
				//Notifico al settore, solo se non trovo un operatore responsabile!
				if(opeTo==null) settTo = opeTo!=null ? opeTo.getCsOSettore() : null;
				
				/**Gli alert sono riferiti al responsabile del caso:il sistema legge tutti i pai non chiusi
					-se data odierna > data chiusura prevista: il sistema registra un alert sul superamento della data chiusura prevista
					-altrimenti
						- se il pai ha data prossimo monitoraggio (calcolata da data ultima + verifica ogni) minore di data chiusura
							- se data odierna >= data prossimo monitoraggio - 1 giorno: il sistema invia un alert "Monitoraggio degli obiettivi necessario per <tipo pai> del <nome e cognome soggetto>"
						- altrimenti se data chiusura prevista < data odierna + 7 gg: il sistema registra un alert che avverte dell'avvicinarsi della data chiusura progetto*/
				
				ArrayList<String> msg= new ArrayList<String>();
				Date today = new Date();
				Calendar dataAlert7GG = Calendar.getInstance();
				dataAlert7GG.add(Calendar.DAY_OF_MONTH, 7);
				if(p.getDataChiusuraPrevista()!=null && today.after(p.getDataChiusuraPrevista())){
					msg.add("Superamento della data chiusura prevista per progetto PAI");
				}else{
					//Calcolo data prossimo monitoraggio (calcolata da data ultima + verifica ogni) 
					Calendar dataProssimoMonitoraggio = null;
					if(p.getVerificaOgni()!=null && p.getVerificaOgni().intValue()>0 && p.getDataMonitoraggio()!=null){
						dataProssimoMonitoraggio = Calendar.getInstance();
						dataProssimoMonitoraggio.setTime(p.getDataMonitoraggio());
						if(p.getVerificaUnitaMisura().equalsIgnoreCase(DataModelCostanti.Pai.PERIODO_TEMPORALE.GIORNI.getCodice()))
							dataProssimoMonitoraggio.add(Calendar.DAY_OF_MONTH, p.getVerificaOgni().intValue());
						else if(p.getVerificaUnitaMisura().equalsIgnoreCase(DataModelCostanti.Pai.PERIODO_TEMPORALE.SETTIMANE.getCodice()))
							dataProssimoMonitoraggio.add(Calendar.DAY_OF_MONTH, 7*p.getVerificaOgni().intValue());
						else if(p.getVerificaUnitaMisura().equalsIgnoreCase(DataModelCostanti.Pai.PERIODO_TEMPORALE.MESI.getCodice()))
							dataProssimoMonitoraggio.add(Calendar.MONTH, p.getVerificaOgni().intValue());
						else if(p.getVerificaUnitaMisura().equalsIgnoreCase(DataModelCostanti.Pai.PERIODO_TEMPORALE.ANNI.getCodice()))
								dataProssimoMonitoraggio.add(Calendar.YEAR, p.getVerificaOgni().intValue());
					}
					if(dataProssimoMonitoraggio!=null && 
							(p.getDataChiusuraPrevista()==null || dataProssimoMonitoraggio.getTime().before(p.getDataChiusuraPrevista()))){
						Calendar dataAlertMonitoraggio = Calendar.getInstance();
						dataAlertMonitoraggio.setTime(dataProssimoMonitoraggio.getTime());
						dataAlertMonitoraggio.add(Calendar.DAY_OF_MONTH, -1);
						if(!today.before(dataAlertMonitoraggio.getTime()))
							msg.add("Monitoraggio degli obiettivi necessario");
					}
					if(p.getDataChiusuraPrevista()!=null && p.getDataChiusuraPrevista().before(dataAlert7GG.getTime()))
						msg.add("Chiusura del progetto PAI prevista tra meno di una settimana");
				}
				
				if(!msg.isEmpty() && (opeTo!=null || settTo!=null || orgTo!=null)){
					boolean inserisco = this.validaInserimentoNuovoAlert(DataModelCostanti.TipiAlertCod.PAI, caso, opeTo.getId());
					if(inserisco){
						//nuovo alert
						String descrizione = "";
						for(String s: msg ) descrizione += s+ "<br/>";
						descrizione+="<br/>";
						descrizione+= "Caso: "+nome+" ("+cf+") - Identificativo:"+caso.getIdentificativo()+" <br/>";
						descrizione+= "Progetto PAI: "+p.getCsTbTipoPai().getDescrizione()+ "<br/>";
						descrizione+= "Data chiusura prevista: "+ (p.getDataChiusuraPrevista()!=null ?  CsUiCompBaseBean.ddMMyyyy.format(p.getDataChiusuraPrevista()) : "") + "<br/>";
						descrizione+= "Data ultimo monitoraggio: "+ (p.getDataMonitoraggio()!=null ? CsUiCompBaseBean.ddMMyyyy.format(p.getDataMonitoraggio()) : "") + "<br/>";
						descrizione+= "Periodo monitoraggio: "+ p.getVerificaOgni() + " " + p.getVerificaUnitaMisura() + "<br/>";
						
						String titDescrizione = "Promemoria caso "+ nome + ": "+msg;
						
						CsOOrganizzazione orgFrom = this.findOrganizzazioneDefault(TipiAlertCod.PAI);
									
						logger.info(enteId + " __ Aggiungo Alert per aggiornamento PAI: [casoId:" + caso.getId()+"]");
						addAlert(TipiAlertCod.PAI, caso, titDescrizione, descrizione, null, null, null, orgFrom, opeTo, settTo, orgTo);
					}else
						logger.info(enteId + " __ Sospeso Inserimento Alert per aggiornamento PAI: già presente [casoId:" + caso.getId()+"]");
						
				}
			}
		
			logger.debug("__ FINE ZsTimerTaskGiornaliero: aggiornaAlertPAI_");
			
    	} catch (Exception e2) {
			logger.error("__ERRORE ZsTimerTaskGiornaliero: aggiornaAlertPAI : " + e2.getMessage(), e2);
		}
	}

    	
	private CsOOrganizzazione findOrganizzazioneDefault(String tipo) throws Exception{
		
		BaseDTO bDto = new BaseDTO();
		bDto.setEnteId(enteId);
		bDto.setObj(tipo);
		CsAlertConfig aConfig = alertService.getAlertConfigByTipo(bDto);
		if(aConfig != null && aConfig.getCsOOrganizzazioneDefault() != null)
			return aConfig.getCsOOrganizzazioneDefault();
		else return null;
	}
	
	private CsOOperatoreSettore findOperatoreTo(CsACaso caso) throws Exception{
		
    	BaseDTO bDto = new BaseDTO();
		OperatoreDTO opDto = new OperatoreDTO();

    	bDto.setEnteId(enteId);
    	opDto.setEnteId(enteId);
    	
    	bDto.setObj(caso.getId());
		CsACasoOpeTipoOpe tipoOpe = casoService.findCasoOpeResponsabile(bDto);
    	
		CsOOperatoreSettore opeTo = tipoOpe!=null ? tipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore(): null;
				
		//Responsabile altrimenti --> creatore
		if(opeTo == null) {		
			
			opeTo = casoService.findCreatoreCaso(bDto);
			
			/*//creatore
			opDto.setUsername(userIns);
			opeTo = operatoreService.findOperatoreByUsername(opDto);*/
		}
		
		return opeTo;
	}
	
	private boolean validaInserimentoNuovoAlert(String tipo, CsACaso caso, Long opeId ) throws Exception{
		//Cerco se è già presente un alert NON LETTO, altrimenti inserisco.
		boolean inserisco = true;
		IterDTO itDto = new IterDTO();
		
		itDto.setEnteId(enteId);
		itDto.setCsACaso(caso);
		itDto.setTipo(tipo);
		itDto.setIdOpSettoreTo(opeId);
		List<CsAlert> listaAlert = alertService.findAlertVisibiliByIdCasoTipoOpeTo(itDto);
	    /*TODO: inserisco solo se tutti gli alert sono letti*/
		boolean tuttiLetti = true; 
	    //Se ci sono degli alert vecchi li rendo non visibili
		for(CsAlert al : listaAlert){
	    	if(al.getLetto()){
		    	al.setDtMod(new Date());
		    	al.setVisibile(false);
		    	
		    	BaseDTO b = new BaseDTO();
		    	b.setEnteId(enteId);
		    	b.setObj(al);
		    	alertService.updateAlert(b);
	    	}else tuttiLetti = false;
	    }
	    
	    inserisco = tuttiLetti;
	    return inserisco;
	}
	
	private void addAlert(String tipo, CsACaso caso, String oggetto, String descrizione, String url, 
			              CsOOperatore opFrom, CsOSettore settFrom, CsOOrganizzazione orgFrom,
						  CsOOperatoreSettore opTo,   CsOSettore settTo,   CsOOrganizzazione orgTo) throws Exception{
	
		AlertDTO newAlert = new AlertDTO();
		newAlert.setEnteId(enteId);
		
		OperatoreDTO opdto =  new OperatoreDTO();
		opdto.setEnteId(enteId);
		
		if(opFrom!=null)
			newAlert.setOperatoreFrom(opFrom);
		if(settFrom!=null){
			newAlert.setSettoreFrom(settFrom);
			newAlert.setOrganizzazioneFrom(settFrom.getCsOOrganizzazione());
		}else
			newAlert.setOrganizzazioneFrom(orgFrom);
		
		newAlert.setCaso(caso);
		newAlert.setTipo(tipo);
		
		newAlert.setOpSettoreTo(opTo);
		newAlert.setSettoreTo(settTo);
		newAlert.setOrganizzazioneTo(orgTo);
		
		newAlert.setDescrizione(descrizione);
		newAlert.setUrl(url);
		newAlert.setTitolo(oggetto);
		
		alertService.addAlert(newAlert);
	}

	
	
	public static void setLogger(Logger logger) {
		ZsTimerTaskGiornaliero.logger = logger;
	}
    
	private Date pulisciData(Date data){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String togliOre = sdf.format(data);
			Date dataPulita = sdf.parse(togliOre);
			
			return dataPulita;
		} catch (ParseException e) {
			
		}
		return null;
	}

	// SISO 1115
	private void importaDatiEsterniSoggettoDaFile() {
		logger.debug("__ INIZIO ZsTimerTaskGiornaliero: importaDatiEsterniSoggettoDaFile");
		DatiEsterniSoggettoTimerTaskHelper helper = null;
		try {
			helper = new DatiEsterniSoggettoTimerTaskHelper(this.enteId);
		} catch (Exception e) {
			logger.warn("errore nell'inizializzazione della logica di importazione dei dati esterni", e);
		}
		if (helper == null) {
			logger.warn("logica di importazione dei dati esterni non correttamente inizializzata");
		} else {
			helper.importaDatiEsterni();
		}
		logger.debug("__ FINE   ZsTimerTaskGiornaliero: importaDatiEsterniSoggettoDaFile");
	}
	// -!- SISO 1115

	private void verificaCasiSoggettoErogazione(){
		logger.debug("__ INIZIO ZsTimerTask: verificaCasiSoggettoErogazione_");
		
		try {
		AccessTableInterventoSessionBeanRemote intService = (AccessTableInterventoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
		AccessTableIndirizzoSessionBeanRemote indirizzoService = (AccessTableIndirizzoSessionBeanRemote)  ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIndirizzoSessionBean");
		BaseDTO bDto = new BaseDTO();
    	bDto.setEnteId(enteId);
		
	    List<CsIInterventoEsegMastSogg> lst = intService.findSoggettiErogazioneConCaso(bDto);
	    
	    for(CsIInterventoEsegMastSogg se : lst){
	    	String userMod = "ZsTimerTask-";
	    	if(!StringUtils.isBlank(se.getCf())){
	    		String cf = se.getCf().trim();
	    		CsASoggettoLAZY soggetto = se.getCaso().getCsASoggetto();
	    			
	    		String cfCaso = soggetto.getCsAAnagrafica().getCf().trim();
	    		
	    		//Sgancio i soggetti per cui il codice fiscale non corrisponde più ad un caso (potrebbe essere stato modificato)
	    		boolean aggiorna = false;
	    		if(!StringUtils.equalsIgnoreCase(cf, cfCaso)){
	    			se.setCaso(null);
	    			aggiorna = true;
	    			userMod+= "NoCaso";
	    			logger.info("Il soggetto master con cf "+cf+"ID_MASTER["+se.getId().getMasterId()+"] verrà sganciato dal caso: i codici fiscali non coincidono.");
	    		}else{
	    			boolean datiValidi = se.getDatiValidi()!=null ? se.getDatiValidi().booleanValue() : false;
	    			//Verifico la validità dei dati anagrafici
	    			SoggettoErogazioneBean bean = new SoggettoErogazioneBean(se);
	    			bean.setCsASoggetto(se.getCaso().getCsASoggetto());
	    			
	    			boolean anagrafeUfficiale = !StringUtils.isBlank(se.getCaso().getCsASoggetto().getCsAAnagrafica().getIdOrigWs());
	    			
	    			CsAAnaIndirizzo residenza = null; 
	    			Long idSoggetto = (soggetto != null ? soggetto.getAnagraficaId() :  null);
	    			 	if(idSoggetto != null){
	    					bDto.setObj(idSoggetto);
	    				    residenza  =   indirizzoService.getIndirizzoResidenza(bDto);
	    			 	}
	    			
	    			String via = residenza!=null ? residenza.getLabelIndirizzo() : null;
	    		    
	    			boolean datiCompleti = bean.isValorizzato();
	    			boolean allineatoAnagrafica = bean.isAllineatoAnagrafica();
	    			boolean allineatoResidenza = bean.isAllineatoResidenza(via, CsUiCompBaseBean.getCasoComuneResidenza(residenza));
	    			
	    		//	if(anagrafeUfficiale){
		    			if(!datiValidi && datiCompleti && allineatoAnagrafica && allineatoResidenza){
		    				aggiorna=true;
			    			se.setDatiValidi(true);
			    			userMod+= "Valido";
			    			logger.info("Il soggetto master con cf "+cf+" ID_MASTER["+se.getId().getMasterId()+"] ha dati coincidenti con quelli del caso");
		    			}else if(datiValidi && (!allineatoAnagrafica || !allineatoResidenza || !datiCompleti)){
		    				aggiorna=true;
			    			se.setDatiValidi(false);
			    			if(!datiCompleti){
			    				userMod+= "Incompleti";
			    				logger.info("Il soggetto master con cf "+cf+" ID_MASTER["+se.getId().getMasterId()+"] ha dati anagrafici/residenza INCOMPLETI");
			    			}if(!allineatoAnagrafica){
			    				userMod+= "DatiAna";
			    				logger.info("Il soggetto master con cf "+cf+" ID_MASTER["+se.getId().getMasterId()+"] NON ha dati coincidenti con quelli del caso");
			    			}if(!allineatoResidenza){
			    				userMod+= "Res";
			    				logger.info("Il soggetto master con cf "+cf+" ID_MASTER["+se.getId().getMasterId()+"] NON ha indirizzo di residenza coincidente con quello del caso");
			    			}
			    		}
	    		//	}
	    		}
	    		
	    		if(aggiorna){
	    			se.setDtMod(new Date());
	    			se.setUserMod(userMod);
	    			bDto.setObj(se);
	    			intService.updateSoggettoErogazione(bDto);
	    		}
	    		
	    	}
	    }
	
	} catch (Exception e2) {
		logger.error("__ ECCEZIONE ZsTimerTask: verificaCasiSoggettoErogazione_ " + e2.getMessage(), e2);
	}
		logger.debug("__ FINE ZsTimerTask: verificaCasiSoggettoErogazione_");
	}

}
