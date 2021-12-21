package eu.smartpeg.rilevazionepresenze.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import eu.smartpeg.rilevazionepresenze.AnagraficaSessionBeanRemote;
import eu.smartpeg.rilevazionepresenze.DominiSessionBeanRemote;
import eu.smartpeg.rilevazionepresenze.StruttureSessionBeanRemote;
import eu.smartpeg.rilevazionepresenze.data.model.Anagrafica;
import eu.smartpeg.rilevazionepresenze.data.model.Area;
import eu.smartpeg.rilevazionepresenze.data.model.Motivazione;
import eu.smartpeg.rilevazionepresenze.data.model.Struttura;
import eu.smartpeg.rilevazionepresenze.datautil.DataModelCostanti;
import eu.smartpeg.rilevazionepresenze.ejb.helpers.ComuniCacheHelperRemote;
import eu.smartpeg.rilevazionepresenze.ejb.helpers.NazioniCacheHelperRemote;
import eu.smartpeg.rilevazionepresenze.web.manbean.DocumentoController;
import eu.smartpeg.rilevazionipresenze.helper.ListeSelezioneHelper;
import eu.smartpeg.utility.ejb.EjbClientUtility;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.data.model.CsTbTipoRapportoCon;

import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.support.datarouter.CeTBaseObject;

@javax.faces.bean.ManagedBean
@ViewScoped
public class AnagraficaController extends RilevazionePresenzeBaseController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<Anagrafica> anagrafiche;
	private Anagrafica selectedAnagrafica;
	private String modalHeader;
	public int idxSelected;
	private List<SelectItem> cittadinanze;
	private Map<String, Struttura> strutture;
	private List<SelectItem> lstParentela;
	private String selezioneTipoLuogoNascita;
	private Map<String, Area> aree;
	private List<SelectItem> lstTitoliStudio;
	private List<SelectItem> lstConLavorativa;
	private List<SelectItem> listaTipiScuola;
	private List<SelectItem> lstGruppiVulnerabili;
	private List<SelectItem> lstMunicipi;
	private List<SelectItem> lstTipoDocumento;
	private Map<String, Anagrafica> referenti;
	private Map<String, Motivazione> listaMotivazioni;


	private Boolean abilitaMunicipioRes;
	private Boolean abilitaMunicipioDom;

	// variabili di appoggio per autocompletamento
	private AmTabNazioni selectedAnagraficaStatoNascita;
	private AmTabComuni selectedAnagraficaComuneNascita;
	private AmTabComuni selectedAnagraficaComuneResidenza;
	private AmTabComuni selectedAnagraficaComuneDomicilio;
	private AmTabNazioni selectedAnagraficaStatoTitoloConseguito;

	private String tipoFunzione;
	 
	@EJB
	private AnagraficaSessionBeanRemote anagraficaEjb;
	@EJB
	private StruttureSessionBeanRemote struttureEjb;
	@EJB
	private ComuniCacheHelperRemote comuniCacheHelper;
	@EJB
	private NazioniCacheHelperRemote nazioniCacheHelper;
	@EJB
	private DominiSessionBeanRemote dominiEjb;
	
	@ManagedProperty(value ="#{documentoController}" ) 
	private DocumentoController documentoController;
	
	public AnagraficaController() {
		this.anagrafiche = new ArrayList<>();
		setTipoFunzione(HomeController.getGlobalParameter(DataModelCostanti.AmParameterKey.TIPO_FUNZIONE_STRUTTURA));
	}

	@PostConstruct
	private void init() {
		logger.info(" Anagrafica Controller @PostConstruct INIT ");
		this.cittadinanze = new ArrayList<SelectItem>();
		this.strutture = new HashMap<>();
		this.referenti = new HashMap<>();
		this.aree = new HashMap<>();
		this.setAbilitaMunicipioRes(false);
		this.setAbilitaMunicipioDom(false);
		this.listaMotivazioni = new HashMap<>();
		readAnagrafiche();
		readStrutture();
		readReferenti();
		inizializzaCacheHelpers();
		readMotivazioni();
	}


	// ======================================================================================
	// METODI PUBBLICI BEAN
	//
	/**
	 * nuova anagrafica
	 */
	public void nuova() {
		setModalHeader("Nuova Anagrafica");
		Anagrafica nuovaAnagrafica = new Anagrafica();
		setSelectedAnagrafica(nuovaAnagrafica);
		readStruttureByTipoFunzione(Long.valueOf(this.getTipoFunzione()));
		readReferenti();
	
	}

	public void readStrutture() {
		List<Struttura> listaStrutture = struttureEjb.findAll();
		
		if(strutture != null) {
			strutture.clear();
		}
		
		for (Struttura struttura : listaStrutture) {
			strutture.put(String.valueOf(struttura.getId()), struttura);
		}
	}	
	
	public void readStruttureByTipoFunzione(Long idTipoFunzione) {	
		
        List<Struttura> listaStrutture = struttureEjb.findStruttureByTipoFunzione(idTipoFunzione);
		
		if(strutture != null) {
			strutture.clear();
		}
		
		for (Struttura struttura : listaStrutture) {
			strutture.put(String.valueOf(struttura.getId()), struttura);
		}
	}
	
	
	//TODO: questo metodo è strano ... 
	public String readTipoDocumento(Long idTipologiaDocumento) {
		if (this.getlstTipoDocumento().size() > 0) {
			for (SelectItem itm : this.lstTipoDocumento) {

				if (itm.getValue() == idTipologiaDocumento) {
					return itm.getLabel();
				}
			}

			return null;
		}

		return null;
	}

	/**
	 * Autocompletamento comune
	 * 
	 * @param query
	 * @return
	 */
	public List<AmTabComuni> completaLuogo(String query) {
		List<AmTabComuni> result = new ArrayList<AmTabComuni>();
		if (query.length()>2)
			result = comuniCacheHelper.trovaComuniPerDenominazione(query);
		return result;
	}

	/**
	 * Autocompletamento nazione
	 */
	public List<AmTabNazioni> completaLstNazioni(String query) {
		List<AmTabNazioni> result = nazioniCacheHelper.trovaNazioniPerDenominazione(query);
		return result;
	}

	/**
	 * Salva anagrafica selezionata
	 * 
	 * @throws Exception
	 */
	public void salva() throws Exception {
		logger.info("salvataggio anagrafica...");
		try {
			
			selectedAnagrafica.setComuneNascita(selectedAnagraficaComuneNascita);
			selectedAnagrafica.setComuneResidenza(selectedAnagraficaComuneResidenza);
			selectedAnagrafica.setComuneDomicilio(selectedAnagraficaComuneDomicilio);

			if (anagraficaEjb.validaAnagrafica(this.getSelectedAnagrafica()).isEmpty()) {
				
				Long idAnagraficaSalvata = anagraficaEjb.salva(this.getSelectedAnagrafica());

				if (idAnagraficaSalvata != null) {
					chiudiDettaglioEtornaAgriglia();
					logger.info("salvataggio angrafica completato: anagrafica ID: " + idAnagraficaSalvata);
					addMessage(FacesMessage.SEVERITY_INFO, "Salvataggio completato", "Anagrafica salvata");
					readAnagrafiche();

				} else {
					addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio",
							anagraficaEjb.validaAnagrafica(this.getSelectedAnagrafica()));

				}
			} else {
				addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio",
						anagraficaEjb.validaAnagrafica(this.getSelectedAnagrafica()));
			}

		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio", e.getMessage());
		}
	}


//	private void aggiornaElencoDocumenti(Anagrafica anagrafica) {
//		anagrafica.getArRpDocumentiAnags().clear();
//		
//		for( DocumentoAnagraficaDTO dto: getDocumentoController().getElencoDocumenti()) {
//			DocumentiAnag documento = aggiornaAnagraficaDaDTO(anagrafica, dto);			
//			anagrafica.getArRpDocumentiAnags().add(documento);		
//		}
//	}

//	private DocumentiAnag aggiornaAnagraficaDaDTO(Anagrafica anagrafica, DocumentoAnagraficaDTO dto) {
//		//TODO: spostare logica fuori dal controler
//		DocumentiAnag documento = new DocumentiAnag();
//		documento.setDataScadenza(dto.getDataScadenza());
//		documento.setId(dto.getId());
//		documento.setIdTipologiaDocumento(dto.getIdTipologiaDocumento());
//		documento.setNote(dto.getNote());
//		documento.setNumeroDocumento(dto.getNumeroDocumento());
//		documento.setArRpAnagrafica(anagrafica);
//		return documento;
//	}

	/**
	 * Elimina anagrafica selezionata
	 */
	public void eliminaAnagrafica() {
		try {
			logger.info("eliminaAnagrafica. SelectedAnagrafica ID : " + this.getSelectedAnagrafica().getId());
			// TODO: fare controllo se è referente e se ha persone collegate
			// TODO: perchè chiamo due volte anagraficaEjb?
			// TODO: validazione dovrebbe restituire dettaglio errori da visualizzare
			if (anagraficaEjb.validaEliminazioneAnagrafica(this.getSelectedAnagrafica()).isEmpty()) {
				anagraficaEjb.eliminaAnagrafica(this.getSelectedAnagrafica());
				addMessage(FacesMessage.SEVERITY_INFO, "INFO ", "Eliminazione avvenuta con successo");
				setSelectedAnagrafica(null);
				readAnagrafiche();
			} else {
				addMessage(FacesMessage.SEVERITY_WARN, "WARN",
						"Non è possibile eliminare l'anagrafica in quanto è un referente ed esistono altre anagrafiche collegate a questa.");
			}
		} catch (Exception e) {
			// TODO: gestione errore e visualizzazione messaggio
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore nell'eliminazione dell'anagrafica", e.getMessage());
		}

	}

	/**
	 * verifica coerenza tra unita abitativa di una anagrafica e del suo referente
	 * 
	 * @param anagraficaNonReferente 
	 *                               
	 * @return
	 */
	public Boolean isCongruenteUnitaAbitativa(Anagrafica anagraficaNonReferente) {
		
		if(anagraficaNonReferente==null) {
			return false;
		}
		
		// TODO: questa è logica di business oppure deve stare nel controller? forse meglio in EJB.
		if (!anagraficaNonReferente.getFlgReferente()) {
			Long idReferente = anagraficaNonReferente.getIdReferente();

			Anagrafica anagraficaReferente = anagraficaEjb.findReferenteByID(idReferente);
			// Non dovrebbe mai succedere, ma nei test si è verificato
			if (anagraficaReferente == null) {
				logger.error("ID Referente non valido in anagrafica ID REFERENTE:  " + idReferente);
				logger.error("ID Referente non valido in anagrafica. Referent enon trovato.  ID Anagrafica:  "
						+ anagraficaNonReferente.getId());
				addMessage(FacesMessage.SEVERITY_ERROR, "Referente non valido",
						"Referente non trovato. ID referente non valido in anagrafica ID: "
								+ anagraficaNonReferente.getId());
				return false;
			}

			String moduliReferente = anagraficaReferente.getUnitaAbitativa();
			String moduliNonReferente = anagraficaNonReferente.getUnitaAbitativa();
			if (moduliReferente != null && moduliNonReferente != null) {

				String[] xx = moduliReferente.split(",");
				List<String> listaUnitaAbitativeRef = new ArrayList<String>(Arrays.asList(xx));
				String[] yy = moduliNonReferente.split(",");
				List<String> listaUnitaAbitativeNoRef = new ArrayList<String>(Arrays.asList(yy));

				for (String unitaAb : listaUnitaAbitativeNoRef) {
					if (!listaUnitaAbitativeRef.contains(unitaAb)) {
						return true;
						
					}

				}
			}
		}

		return false;

	}
	
	// 
	// METODI PUBBLICI BEAN
	// ======================================================================================
	
	// ======================================================================================	
	// METODI PRIVATI 
	// 

	// TODO: da portare su Helper Esterno
	private void inizializzaGestioneAutocomplete(Anagrafica selectedAnagrafica) {
		this.selectedAnagraficaComuneDomicilio = null;
		this.selectedAnagraficaComuneResidenza = null;
		this.selectedAnagraficaComuneNascita = null;
		this.selectedAnagraficaStatoNascita = null;
		this.selectedAnagraficaStatoTitoloConseguito = null;

		if (this.selectedAnagrafica != null) {
			if (selectedAnagrafica.getComuneDomicilioCod() != null) {
				this.selectedAnagraficaComuneDomicilio = comuniCacheHelper
						.getComuneByCodiceIstat(selectedAnagrafica.getComuneDomicilioCod());
			}

			if (selectedAnagrafica.getComuneResidenzaCod() != null) {
				this.selectedAnagraficaComuneResidenza = comuniCacheHelper
						.getComuneByCodiceIstat(selectedAnagrafica.getComuneResidenzaCod());
			}

			if (selectedAnagrafica.getComuneNascitaCod() != null) {
				this.selectedAnagraficaComuneNascita = comuniCacheHelper
						.getComuneByCodiceIstat(selectedAnagrafica.getComuneNascitaCod());
			}

			if (selectedAnagrafica.getStatoNascitaCod() != null) {
				this.selectedAnagraficaStatoNascita = nazioniCacheHelper
						.getNazioneByCodiceAnagrafe(selectedAnagrafica.getStatoNascitaCod());
			}

			if (selectedAnagrafica.getStatoTitConseguitoCod() != null) {
				// TODO: stato nascita � un campo STRING. Stato titolo conseguito � un campo
				// LONG quale dei due � corretto?
				this.selectedAnagraficaStatoTitoloConseguito = nazioniCacheHelper
						.getNazioneByCodiceAnagrafe(String.valueOf(selectedAnagrafica.getStatoTitConseguitoCod()));
			}
		}
	}	
	
	private Boolean inizializzaCacheHelpers() {
		// questa chiamata  serve per svegliare CDI e inizializzare il bean. 
		//  se possibile rifare in modo pi� elegante - non si pu� usare @startup
		return comuniCacheHelper.isListaComuniCaricata() && nazioniCacheHelper.isListaNazioniCaricata();
	} 
	
	private void readReferenti() {
		List<Anagrafica> listaReferenti = anagraficaEjb.findAllReferenti();
		
		if(referenti != null) {
			referenti.clear();
		}
		for (Anagrafica anagReferente : listaReferenti) {
			referenti.put(String.valueOf(anagReferente.getId()), anagReferente);
		}
	}
	
	private void readAnagrafiche() {
		// TODO: questa deve essere rifatta per supportare paginazione!
		List<Anagrafica> anagrafiche = anagraficaEjb.findAll();
		if (getAnagrafiche() == null) {
			logger.info("inizializzo lista anagrafiche. SIZE lista: " + anagrafiche.size());
			setAnagrafiche(anagrafiche);
		} else {
			logger.info("aggiorno lista anagrafiche. SIZE lista: " + anagrafiche.size());
			getAnagrafiche().clear();
			getAnagrafiche().addAll(anagrafiche);
		}
	}	


	public void chiudiDettaglioEtornaAgriglia() {
		this.setSelectedAnagrafica(null); // TODO: mettendo questa a NULL si resetta il FORM e dovrebbe tornare sulla
											// master ... DA RIVEDERE
	}

	
	//carica la lista delle motivazioni di uscita dal campo
	private void readMotivazioni() {
		List<Motivazione> motivazioni = dominiEjb.findAllMotivazioni();
		
		if(listaMotivazioni != null) {
			listaMotivazioni.clear();
		}
		
		for (Motivazione m : motivazioni) {
			listaMotivazioni.put(String.valueOf(m.getId()), m);
		}
	}

	// 
	// METODI PRIVATI
	// ======================================================================================
	
	// ======================================================================================	
	// EVENT HANDLER  
	//	

	public void onComuneNascitaSelect(javax.faces.event.AjaxBehaviorEvent event) {
		//selectedAnagrafica.setComuneNascita(selectedAnagraficaComuneNascita);
	}

	public void onComuneResidenzaSelect(javax.faces.event.AjaxBehaviorEvent event) {
		//selectedAnagrafica.setComuneResidenza(selectedAnagraficaComuneResidenza);
		if(selectedAnagraficaComuneResidenza!=null) {
			this.setAbilitaMunicipioRes(selectedAnagraficaComuneResidenza.getSiglaProv().equalsIgnoreCase("RM"));
		}
	}

	public void onComuneDomicilioSelect(javax.faces.event.AjaxBehaviorEvent event) {		
		//selectedAnagrafica.setComuneDomicilio(selectedAnagraficaComuneDomicilio);
		if(selectedAnagraficaComuneDomicilio!=null) {
			this.setAbilitaMunicipioDom(selectedAnagraficaComuneDomicilio.getSiglaProv().equalsIgnoreCase("RM"));
		}		
	}

	public void onStatoTitoloStudioChange(javax.faces.event.AjaxBehaviorEvent event) {
		logger.debug("onStatoTitoloStudioChange - event: " + event);
		logger.debug("onStatoTitoloStudioChange - selectedAnagraficaStatoTitoloConseguito: "
				+ this.selectedAnagraficaStatoTitoloConseguito.getNazione());
		this.selectedAnagrafica.setStatoTitConseguitoCod(
				Long.parseLong(selectedAnagraficaStatoTitoloConseguito.getCodNazioneAnagrafe()));
	}	

	public void onDataInsediamentoChange(ValueChangeEvent event) {
		Date newValue = (Date) event.getNewValue();

		selectedAnagrafica.setDataInsediamento(newValue);
		logger.debug("selected  Data Insediamento: " + this.selectedAnagrafica.getDataInsediamento());

	}

	public void onDataUscitaChange(ValueChangeEvent event) {
		Date newValue = (Date) event.getNewValue();

		selectedAnagrafica.setDataUscita(newValue);
		logger.debug("selected  Data Uscita: " + this.selectedAnagrafica.getDataUscita());

	}

	public void onDataNascitaChange(ValueChangeEvent event) {
		Date newValue = (Date) event.getNewValue();

		selectedAnagrafica.setDataNascita(newValue);
		logger.debug("selected  Data Uscita: " + this.selectedAnagrafica.getDataNascita());

	}

	public void onCognomeChange(ValueChangeEvent event) {
		String newValue = (String) event.getNewValue();

		selectedAnagrafica.setCognome(newValue);
		logger.debug("selected  Cognome: " + this.selectedAnagrafica.getCognome());

	}

	public void onNomeChange(ValueChangeEvent event) {
		String newValue = (String) event.getNewValue();

		selectedAnagrafica.setNome(newValue);
		logger.debug("selected  Nome: " + this.selectedAnagrafica.getNome());

	}

	public void onCfChange(ValueChangeEvent event) {
		String newValue = (String) event.getNewValue();

		selectedAnagrafica.setCf(newValue);
		logger.debug("selected  Nome: " + this.selectedAnagrafica.getCf());

	}

	public void onGenereChange(ValueChangeEvent event) {
		String newValue = (String) event.getNewValue();

		selectedAnagrafica.setGenere(newValue);
		logger.debug("selected Genere: " + this.selectedAnagrafica.getGenere());

	}

	public void onCittadinanzaChange(ValueChangeEvent event) {
		String newValue = (String) event.getNewValue();

		selectedAnagrafica.setCittadinanza(newValue);
		logger.debug("selected Cittadinanza: " + this.selectedAnagrafica.getCittadinanza());

	}

	public void onIndirizzoResChange(ValueChangeEvent event) {
		String newValue = (String) event.getNewValue();

		selectedAnagrafica.setIndirizzoResidenza(newValue);
		logger.debug("selected Indirizzo Residenza: " + this.selectedAnagrafica.getIndirizzoResidenza());

	}

	public void onMunicipioResChange(ValueChangeEvent event) {
		Long newValue = (Long) event.getNewValue();

		selectedAnagrafica.setIdMunicipioRes(newValue);
		logger.debug("selected Municipio Residenza: " + this.selectedAnagrafica.getIdMunicipioRes());

	}

	public void onMunicipioDomChange(ValueChangeEvent event) {
		Long newValue = (Long) event.getNewValue();

		selectedAnagrafica.setIdMunicipioDom(newValue);
		logger.debug("selected Municipio Domicilio: " + this.selectedAnagrafica.getIdMunicipioDom());

	}

	public void onIndirizzoDomChange(ValueChangeEvent event) {
		String newValue = (String) event.getNewValue();

		selectedAnagrafica.setIndirizzoDomicilio(newValue);
		logger.debug("selected Indirizzo Domicilio: " + this.selectedAnagrafica.getIndirizzoDomicilio());

	}

	public void onReferenteChange(ValueChangeEvent event) {
		Long newValue = (Long) event.getNewValue();

		selectedAnagrafica.setIdReferente(newValue);
		logger.debug("selected Id Referente: " + this.selectedAnagrafica.getIdReferente());

	}

	public void onGradoParentelaChange(ValueChangeEvent event) {
		Long newValue = (Long) event.getNewValue();

		selectedAnagrafica.setIdParentela(newValue);
		logger.debug("selected Id Parentela: " + this.selectedAnagrafica.getIdParentela());

	}

	public void onUnitaAbitativaChange(ValueChangeEvent event) {
		String newValue = (String) event.getNewValue();

		selectedAnagrafica.setUnitaAbitativa(newValue);
		logger.debug("selected Unita Abitativa: " + this.selectedAnagrafica.getUnitaAbitativa());

	}

	public void onTitoloStudioChange(ValueChangeEvent event) {
		Long newValue = (Long) event.getNewValue();

		selectedAnagrafica.setIdTitoloStudio(newValue);
		logger.debug("selected TitoloStudio: " + this.selectedAnagrafica.getIdTitoloStudio());

	}

	public void onStatoNascitaSelect(javax.faces.event.AjaxBehaviorEvent event) {
		logger.debug("onStatoNascitaSelect - selectedAnagraficaStatoNascita: "+ this.selectedAnagraficaStatoNascita.getCodNazioneAnagrafe());
		selectedAnagrafica.setStatoNascitaCod(selectedAnagraficaStatoNascita.getCodNazioneAnagrafe());
		selectedAnagrafica.setStatoNascitaDes(selectedAnagraficaStatoNascita.getNazione());
		logger.debug("selected Stato Nascita des: " + this.selectedAnagrafica.getStatoNascitaDes());
	}

	public void onCondizioneLavorativaChange(ValueChangeEvent event) {
		Long newValue = (Long) event.getNewValue();

		selectedAnagrafica.setIdCondizioneLavorativa(newValue);
		logger.debug("selected Condizione Lavorativa: " + this.selectedAnagrafica.getIdCondizioneLavorativa());

	}

	public void onOrdineScuolaChange(ValueChangeEvent event) {
		Long newValue = (Long) event.getNewValue();

		selectedAnagrafica.setIdOrdineScuola(newValue);
		logger.debug("selected Ordine Scuola: " + this.selectedAnagrafica.getIdOrdineScuola());

	}

	public void onGruppoVulnerabileChange(ValueChangeEvent event) {
		Long newValue = (Long) event.getNewValue();

		selectedAnagrafica.setIdVulnerabilita(newValue);
		logger.debug("selected Gruppo Vulnerabile: " + this.selectedAnagrafica.getIdVulnerabilita());

	}
	
	public void onChangeCmbxVillaggio(AjaxBehaviorEvent e) {

		try {
			Long idStruttura = (Long) ((javax.faces.component.UIInput) (e.getSource())).getValue();
			
			caricaAreePerVillaggio(idStruttura);
			
		} catch (Exception es) {
			// TODO: gestione errore e visualizzazione messaggio

			addMessage(FacesMessage.SEVERITY_ERROR, "Errore nel caricamento delle aree", es.getMessage());
		}

	}

	private void caricaAreePerVillaggio(Long idStruttura) {
		List<Area> listaAree = new ArrayList<Area>();
		Struttura struttura = struttureEjb.findStrutturaById(idStruttura);

		if (struttura != null && struttura.getAreas() != null) {
			listaAree = struttura.getAreas();
		}
		
		if(aree != null) {
			aree.clear();
		}
		for (Area area : listaAree) {
			aree.put(String.valueOf(area.getId()), area);
		}
	}  	
	// 
	// EVENT HANDLERS 
	//  ================================================================================

	// ================================================================================
	// GETTERS AND SETTERS
	//	
	
	public String getModalHeader() {
		return modalHeader;
	}

	public void setModalHeader(String modalHeader) {
		this.modalHeader = modalHeader;
	}

	public int getIdxSelected() {
		return idxSelected;
	}

	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;
	}

	public String getSelezioneTipoLuogoNascita() {
		return selezioneTipoLuogoNascita;
	}

	public void setSelezioneTipoLuogoNascita(String selezioneTipoLuogoNascita) {
		this.selezioneTipoLuogoNascita = selezioneTipoLuogoNascita;
	}

	public Map<String, Struttura> getStrutture() {
		return strutture;
	}

	public void setStrutture(Map<String, Struttura> strutture) {
		this.strutture = strutture;
	}

	public Map<String, Anagrafica> getReferenti() {
		return referenti;
	}

	public void setReferenti(Map<String, Anagrafica> referenti) {
		this.referenti = referenti;
	}

	public List<SelectItem> getCittadinanze() {
		if (cittadinanze.isEmpty()) {

			// TODO: con @EJB non funziona. Utilizzo lookup EJB
			String remoteName = AccessTableNazioniSessionBeanRemote.class.getCanonicalName();
			AccessTableNazioniSessionBeanRemote accessTableNazioniSessionBeanRemote = (AccessTableNazioniSessionBeanRemote) EjbClientUtility
					.getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableNazioniSessionBean", remoteName); //

			List<String> beanLstCittadinanze = accessTableNazioniSessionBeanRemote.getCittadinanze();
			if (beanLstCittadinanze != null) {
				for (String cittadinanza : beanLstCittadinanze) {
					// in AM_TAB_NAZIONI il campo NAZIONALITA ha lunghezza
					// 500, in CS_A_SOGGETTO il campo CITTADINANZA ha
					// lunghezza 255
					if (cittadinanza.length() > 255) {
						cittadinanza = cittadinanza.substring(0, 252) + "...";
					}
					cittadinanze.add(new SelectItem(cittadinanza, cittadinanza));
				}
			}

		}

		return cittadinanze;
	}

	public void setCittadinanze(List<SelectItem> cittadinanze) {
		this.cittadinanze = cittadinanze;
	}

	public List<SelectItem> getLstParentela() {
		try {
			// TODO: con @EJB non funziona. Utilizzo lookup EJB
			String remoteName = AccessTableConfigurazioneSessionBeanRemote.class.getCanonicalName();
			AccessTableConfigurazioneSessionBeanRemote accessTableConfigurazioneSessionBeanRemote = (AccessTableConfigurazioneSessionBeanRemote) EjbClientUtility
					.getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean", remoteName); //

			if (lstParentela == null) {
				lstParentela = new ArrayList<SelectItem>();
				CeTBaseObject bo = new CeTBaseObject();
				fillEnte(bo);
				List<CsTbTipoRapportoCon> lstParentelaModel = accessTableConfigurazioneSessionBeanRemote
						.getTipoRapportoConParenti(bo);
				if (lstParentelaModel != null) {
					for (CsTbTipoRapportoCon obj : lstParentelaModel) {
						lstParentela.add(new SelectItem(obj.getId().longValue(), obj.getDescrizione()));
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return lstParentela;
	}

	public void setLstParentela(List<SelectItem> lstParentela) {
		this.lstParentela = lstParentela;
	}

	public Map<String, Area> getAree() {
		return aree;
	}

	public void setAree(Map<String, Area> aree) {
		this.aree = aree;
	}

	public Boolean getAbilitaMunicipioRes() {
		return abilitaMunicipioRes;
	}

	public void setAbilitaMunicipioRes(Boolean abilitaMunicipioRes) {
		this.abilitaMunicipioRes = abilitaMunicipioRes;
	}

	public Boolean getAbilitaMunicipioDom() {
		return abilitaMunicipioDom;
	}

	public void setAbilitaMunicipioDom(Boolean abilitaMunicipioDom) {
		this.abilitaMunicipioDom = abilitaMunicipioDom;
	}

	public Anagrafica getSelectedAnagrafica() {
		return selectedAnagrafica;
	}

	public void setSelectedAnagrafica(Anagrafica anagrafica) {
		selectedAnagrafica = anagrafica;
		documentoController.caricaDocumentiDaAngrafica(anagrafica);
		inizializzaGestioneAutocomplete(anagrafica);
		if(anagrafica != null && anagrafica.getId() != null)
		{
			caricaAreePerVillaggio(anagrafica.getStruttura().getId());
		}
	}
	
	public List<Anagrafica> getAnagrafiche() {
		return anagrafiche;
	}

	public void setAnagrafiche(List<Anagrafica> anagrafiche) {
		this.anagrafiche = anagrafiche;
	}	
	
	public List<SelectItem> getLstTitoliStudio() {
		if (lstTitoliStudio == null)
			lstTitoliStudio = ListeSelezioneHelper.costruisciListaTitoliDiStudio();
		return lstTitoliStudio;
	}

	public List<SelectItem> getLstConLavorativa() {
		if (lstConLavorativa == null) {
			lstConLavorativa = ListeSelezioneHelper.costruisciListaCondizioneLavorativa();
		}
		return lstConLavorativa;
	}
	
	public List<SelectItem> getListaGruppoVulnerabile() {
		if (lstGruppiVulnerabili == null) {
			lstGruppiVulnerabili = ListeSelezioneHelper.costruisciListaGruppoVulnerabile();
		}
		return lstGruppiVulnerabili;
	}	
	
	public List<SelectItem> getlstTipoDocumento() {
		if(lstTipoDocumento == null) {
			lstTipoDocumento =ListeSelezioneHelper.costruisciListaTipoDocumento();
		}

		return lstTipoDocumento;
	}	

	public List<SelectItem> getlstMunicipi() {
		if (lstMunicipi == null) {
			lstMunicipi= ListeSelezioneHelper.costruisciListaMunicipi();
		}
		return lstMunicipi;
	}

	public List<SelectItem> getlistaTipiScuola() {
		if (listaTipiScuola == null) {
			listaTipiScuola = ListeSelezioneHelper.costruisciListaTipoScuola();
		}
		return listaTipiScuola;
	}

	public AmTabComuni getSelectedAnagraficaComuneNascita() {
		return selectedAnagraficaComuneNascita;
	}

	public void setSelectedAnagraficaComuneNascita(AmTabComuni selectedAnagraficaComuneNascita) {
		this.selectedAnagraficaComuneNascita = selectedAnagraficaComuneNascita;
	}

	public AmTabNazioni getSelectedAnagraficaStatoNascita() {
		return selectedAnagraficaStatoNascita;
	}

	public void setSelectedAnagraficaStatoNascita(AmTabNazioni selectedAnagraficaStatoNascita) {
		this.selectedAnagraficaStatoNascita = selectedAnagraficaStatoNascita;
	}

	public AmTabComuni getSelectedAnagraficaComuneResidenza() {
		return selectedAnagraficaComuneResidenza;
	}

	public void setSelectedAnagraficaComuneResidenza(AmTabComuni selectedAnagraficaComuneResidenza) {
		this.selectedAnagraficaComuneResidenza = selectedAnagraficaComuneResidenza;
	}

	public AmTabComuni getSelectedAnagraficaComuneDomicilio() {
		return selectedAnagraficaComuneDomicilio;
	}

	public void setSelectedAnagraficaComuneDomicilio(AmTabComuni selectedAnagraficaComuneDomicilio) {
		this.selectedAnagraficaComuneDomicilio = selectedAnagraficaComuneDomicilio;
	}

	public AmTabNazioni getSelectedAnagraficaStatoTitoloConseguito() {
		return selectedAnagraficaStatoTitoloConseguito;
	}

	public void setSelectedAnagraficaStatoTitoloConseguito(AmTabNazioni selectedAnagraficaStatoTitoloConseguito) {
		this.selectedAnagraficaStatoTitoloConseguito = selectedAnagraficaStatoTitoloConseguito;
	}	
	
	public DocumentoController getDocumentoController() {
		return documentoController;
	}

	public void setDocumentoController(DocumentoController documentoController) {
		this.documentoController = documentoController;
	}

	public Map<String, Motivazione> getListaMotivazioni() {
		return listaMotivazioni;
	}

	public void setListaMotivazioni(Map<String, Motivazione> listaMotivazioni) {
		this.listaMotivazioni = listaMotivazioni;
	}

	public String getTipoFunzione() {
		return tipoFunzione;
	}

	public void setTipoFunzione(String tipoFunzione) {
		this.tipoFunzione = tipoFunzione;
	}
	
	// 
	// GETTERS AND SETTERS
	//  ================================================================================
}
