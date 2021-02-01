package it.webred.cs.csa.web.manbean.interscambio;

import it.umbriadigitale.interscambio.utils.XMLReaderUtils;
import it.umbriadigitale.interscambio.utils.XMLValidationUtils;
import it.webred.cs.csa.ejb.dto.AlertDTO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.EventoDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.web.manbean.listacasi.LazyListaCasiModel;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hl7.v3.request.PRSSIN001004ZZ;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "interscambioBean")
@ViewScoped
public class InterscambioCartellaSocialeBean extends CsUiCompBaseBean {

	private InterscambioCartellaSociale ics = new InterscambioCartellaSociale();

	private LazyListaCasiModel cartelle = new LazyListaCasiModel();

	private LazyListaCasiModel cartellaSelezionata;

	private List<EventoDTO> listaEventi;
	
	private EventoDTO eventoSelezionato;

	private List<CsACaso> listaCasi = new ArrayList<CsACaso>();

	private CsACaso casoSelezionato;

	private String nome;
	private String cognome;
	
	private InterscambioDialogEventoBean modalBean;
	
	private List<SelectItem> tipiEvento = Arrays.asList(
			new SelectItem("", "TUTTI"), 
			new SelectItem("EXPORT", "EXPORT"), 
			new SelectItem("IMPORT", "IMPORT"));

	// Visualizzazione ESPORTAZIONE
	private boolean mostraTabellaEsportazione = false;
	private boolean mostraFormRicercaCartella = false;
	// Visualizzazione IMPORTAZIONE
	private boolean mostraFormImportazioneCartella = false;
	// Visualizzazione ELENCO EVENTI
	private boolean mostraElencoEventi = false;

	private Date oggi = new Date();

	private String eventCfFilter;
	private String eventTypeFilter;

	public InterscambioCartellaSocialeBean() {
		modalBean = new InterscambioDialogEventoBean();
	}

	@PostConstruct
	public void onInit() {
		eventCfFilter = (String) getSession().getAttribute("eventListFilter");
		eventTypeFilter =  (String) getSession().getAttribute("eventListFilterType");

		if (eventCfFilter != null) {
			getSession().removeAttribute("eventListFilter");
			this.visualizzaElencoEventi();
		} else {
			this.eventCfFilter = null;
		}
		
		// 2018-02-01 Smartpeg - spostata in PostConstruct per Maintenance
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);

		if (this.eventCfFilter == null) {
			this.listaEventi = this.ics.getListaEventi(bdto);
			this.eventCfFilter = null;
		} else {
			this.listaEventi = this.ics
					.getListaEventi(bdto, this.eventCfFilter, this.eventTypeFilter);
		}
	}

	/**
	 * Visualizza la maschera per avviare la ricerca tramite nome e cognome 
	 */
	public void esporta() {
		this.nascondiTutto();
		this.mostraFormRicercaCartella = true;

		this.getListaEventi();
		logger.info("Esportazione cartella avviata...");
	}

	/**
	 * Visualizza il componente per l'upload del file XML
	 */
	public void importa() {
		this.nascondiTutto();
		this.mostraFormImportazioneCartella = true;

		logger.info("Importazione cartella avviata...");
	}

	/**
	 * Visualizza una dataTable con la lista degli eventi IMPORT/EXPORT
	 */
	public void visualizzaElencoEventi() {
		this.nascondiTutto();
		this.mostraElencoEventi = true;
		logger.info("Visualizzazione eventi avviata...");
		// TODO: Gestire richiesta eventi (MP)
	}

	/**
	 * Nasconde tutte le viste qualI: FormRicercaCartella, TabellaEsportazione,
	 * FormImportazioneCartella, ElencoEventi
	 */
	private void nascondiTutto() {

		this.mostraFormRicercaCartella = false;
		this.mostraTabellaEsportazione = false;
		this.mostraFormImportazioneCartella = false;
		this.mostraElencoEventi = false;

	}

	/**
	 * Valorizza "eventoSelezionato" con l'evt cliccato dalla tabella
	 * 
	 * @param evt
	 */
	public void onClickEvento(EventoDTO evt) {
		this.eventoSelezionato = evt;
		// RequestContext rc = RequestContext.getCurrentInstance();
		// rc.execute("PF('dettaglioEvento').show();");
	}

	/**
	 * Effettua la cancellazione logica dell'evento selezionato
	 * 
	 * @param evt
	 */
	public void onClickDeleteEvento(EventoDTO evt) {
		logger.info("Evento cancellato " + evt.getCodiceFiscale());
		fillEnte(evt);
		this.ics.cancellaEvento(evt);
	}

	/***
	 * Ricarica la lista dei casi aggiungedo come filtro i campi Nome e Cognome
	 */
	public void search() {

		if (this.nome.isEmpty() || this.cognome.isEmpty()) {
			logger.info("Valorizzare nome e cognome.");
			this.growlMessage(FacesMessage.SEVERITY_WARN, "Nome e Cognome obbligatori! ");
			return;
		}

		BaseDTO dto = new BaseDTO();
		dto.setObj(this.cognome.trim());
		dto.setObj2(this.nome.trim());
		fillEnte(dto);

		this.listaCasi = this.ics.findCasoByCognomeAndNome(dto);
		
		//Warning all'utente in caso la ricerca non abbia prodotto risultati 
		if(listaCasi.isEmpty()){
			this.growlMessage(FacesMessage.SEVERITY_WARN, "La ricerca non ha prodotto risultati");
		}
		
		this.nascondiTutto();
		this.mostraFormRicercaCartella = true;
		this.mostraTabellaEsportazione = true;
	}

	/**
	 * Valorizza nel bean il caso selezionato che sarà mostrato nel MODAL
	 * 
	 * @param caso
	 */
	public void esportaCaso(CsACaso caso) {
		this.casoSelezionato = caso;
		logger.debug("Caso selezionato da esportare: " + caso.getId());
	}

	/**
	 * Conferma l'esportazione e salva l'evento EXPORT
	 */
	public void confermaEsportazione() {
		try {
			logger.debug("Caso selezionato in corso di esportazione: "+ this.casoSelezionato.getId());
			this.ics.esportaCartella(this.casoSelezionato, modalBean);
			this.nascondiTutto();
			
			this.growlMessage(FacesMessage.SEVERITY_INFO, "Esportazione avvenuta con successo");

			this.creaNotifica(getCasoSelezionato(), "ESPORTAZIONE");

			this.mostraTabellaEsportazione = false;
			this.casoSelezionato = null;
			this.listaCasi.clear();
			this.nascondiTutto();
			RequestContext.getCurrentInstance().update(
					"RicercaCartellaInterscambio");
		} catch (Exception ex) {
			logger.info("Errore nell'esportazione: \n" + ex.getMessage());

			this.growlMessage(FacesMessage.SEVERITY_ERROR,
					"Esportazione fallita");
		}
	}

	public String mittenteEsportazione() {
		return getCurrentBelfioreCS();
	}

	/**
	 * Gestisce il grow nella pagina di interscambioSociale.xhtml
	 * 
	 * @param severity
	 * @param text
	 */
	public void growlMessage(Severity severity, String text) {
		FacesContext context = FacesContext.getCurrentInstance();

		// TODO: Controllare problema su aggiunta dettaglio
		context.addMessage("notificaImportazione", new FacesMessage(severity,
				text, ""));

		RequestContext pfC = RequestContext.getCurrentInstance();
		pfC.update("@(.notificaGrowl)");
	}

	/**
	 * Gestisce l'importazione del file e salva l'esito dell'operazione IMPORT
	 * 
	 * @param event
	 */
	public void importaFile(FileUploadEvent event) {
		UploadedFile selectedFile = event.getFile();

		/*
		 * 2017-11-14
		 * 
		 * La javadoc di PrimeFaces non indica il charset utilizzato da
		 * getContents(). Facendo una prova con caratteri strani e ricostruendo
		 * la String con UTF-8 (vedi di seguito, nel salvataggio dell'evento),
		 * il salvataggio è avvenuto correttamente.
		 * 
		 * Si può supporre che getContents restituisca byte[] in UTF-8.
		 */
		byte[] content = selectedFile.getContents();

		try {
			// eseguo la validazione del file uploadato (se fallisce, lancia
			// eccezione)
			XMLValidationUtils.validateRequest(selectedFile.getInputstream());

			// TODO rivedere eccezioni lanciate!!!
			PRSSIN001004ZZ request = XMLReaderUtils
					.unmarshalRequestRoot(selectedFile.getInputstream());

			EventoImportDataWrapper importData = new EventoImportDataWrapper(
					request);

			// salvo il record su tabella Import/Export
			EventoDTO evt = new EventoDTO("IMPORT", // TIPO EVENTO
					importData.getMittente(), // MITTENTE
					importData.getDestinatario(), // DESTINATARIO
					importData.getNome(), // NOME SOGGETTO
					importData.getCognome(), // COGNOME SOGGETTO
					importData.getCodiceFiscale(), // CODICE FISCALE
					new String(content, "UTF-8"), // XML CARTELLA
					currentOperatoreDTO() // OPERATORE SETTORE
			);

			fillEnte(evt);
			this.ics.creaEvento(evt);

			// mostro l'ok a video
			this.growlMessage(FacesMessage.SEVERITY_INFO,
					"Importazione avvenuta con successo");

			// creo la notifica di importazione
			this.creaNotifica(getCasoSelezionato(), "IMPORTAZIONE");
		}
		// TODO catch unico per tutto?
		catch (Exception e) {
			// mostro l'errore a video
			this.growlMessage(FacesMessage.SEVERITY_ERROR,
					"Importazione fallita!");

			logger.error("Importazione XML di Interscambio fallita", e);
		}
	}

	/**
	 * Ritorna la lista degli eventi non cancellati
	 * 
	 * @return
	 */
	public List<EventoDTO> getListaEventi() {
		// 2018-02-01 Smartpeg - spostata in PostConstruct per Maintenance
//		BaseDTO bdto = new BaseDTO();
//		fillEnte(bdto);
//
//		if (this.eventCfFilter == null) {
//			this.listaEventi = this.ics.getListaEventi(bdto);
//			this.eventCfFilter = null;
//		} else {
//			this.listaEventi = this.ics
//					.getListaEventi(bdto, this.eventCfFilter, this.eventTypeFilter);
//		}

		return listaEventi;
	}

	public void creaNuovoEvento(EventoDTO evt) {
		fillEnte(evt);
		this.ics.creaEvento(evt);
	}

	private OperatoreDTO currentOperatoreDTO() {
		OperatoreDTO operatore = new OperatoreDTO();
		operatore.setUserId(getCurrentOpSettore().getCsOOperatore()
				.getUsername());
		operatore.setIdOperatoreSettore(getCurrentOpSettore().getId());
		return operatore;
	}

	private void creaNotifica(CsACaso caso, String operazione) {
		AlertDTO adto = new AlertDTO();
		fillEnte(adto);

		adto.setCaso(caso);

		CsOOperatoreSettore operatoreSettore = getCurrentOpSettore();

		try {
			this.ics.creaNotifica(adto, caso, operatoreSettore, operazione);
		} catch (Exception e) {
			logger.error("Errore nella creazione della notifica di "
					+ operazione);
		}
	}

	// GETTER & SETTER

	public LazyListaCasiModel getCartelle() {
		return cartelle;
	}

	public LazyListaCasiModel getCartellaSelezionata() {
		return cartellaSelezionata;
	}

	public void setCartellaSelezionata(LazyListaCasiModel cartellaSelezionata) {
		this.cartellaSelezionata = cartellaSelezionata;
	}

	public boolean isMostraTabellaEsportazione() {
		return mostraTabellaEsportazione;
	}

	public boolean isMostraFormRicercaCartella() {
		return mostraFormRicercaCartella;
	}

	public boolean isMostraFormImportazioneCartella() {
		return mostraFormImportazioneCartella;
	}

	public boolean isMostraElencoEventi() {
		return mostraElencoEventi;
	}

	public Date getOggi() {
		return oggi;
	}

	public EventoDTO getEventoSelezionato() {
		return eventoSelezionato;
	}

	public void setEventoSelezionato(EventoDTO eventoSelezionato) {
		this.eventoSelezionato = eventoSelezionato;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public List<CsACaso> getListaCasi() {
		return listaCasi;
	}

	public void setListaCasi(List<CsACaso> listaCasi) {
		this.listaCasi = listaCasi;
	}

	public CsACaso getCasoSelezionato() {
		return casoSelezionato;
	}

	public void setCasoSelezionato(CsACaso casoSelezionato) {
		this.casoSelezionato = casoSelezionato;
	}

	public List<SelectItem> getTipiEvento() {
		return tipiEvento;
	}

	public InterscambioDialogEventoBean getModalBean() {
		return modalBean;
	}

	public void setModalBean(InterscambioDialogEventoBean modalBean) {
		this.modalBean = modalBean;
	}
}
