package eu.smartpeg.rilevazionepresenze.web;

import it.webred.cs.csa.ejb.dto.pai.pti.PaiPTIFaseEnum;
import it.webred.ct.config.model.AmTabComuni;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.json.JSONObject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiInfoSinteticheDTO;
import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiPTIDocumentoDTO;
import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiPtiConsuntiDTO;
import eu.smartpeg.rievazionepresenze.dto.pai.DettaglioMinore;
import eu.smartpeg.rievazionepresenze.dto.pai.DettaglioPTI;
import eu.smartpeg.rievazionepresenze.dto.pai.MinoreInStruttura;
import eu.smartpeg.rievazionepresenze.dto.pai.RichiestaDisponibilitaPaiPtiDTO;
import eu.smartpeg.rilevazionepresenze.data.model.Struttura;
import eu.smartpeg.rilevazionepresenze.datautil.DataModelCostanti.StatiRichiesta.STATO_RICHIESTA;
import eu.smartpeg.rilevazionepresenze.ejb.helpers.ComuniCacheHelperRemote;
import eu.smartpeg.rilevazionepresenze.pai.ArCsPaiPTISessionBeanRemote;
import eu.smartpeg.rilevazionepresenze.pai.RichiestaDisponibilitaPaiPtiSessionBeanRemote;
import eu.smartpeg.rilevazionepresenze.web.util.Utils;

@ManagedBean(name = "richiesteController")
@ViewScoped
public class RichiesteController extends RilevazionePresenzeBaseController implements Serializable {

	private Struttura struttura;
	private List<RichiestaDisponibilitaPaiPtiDTO> richieste;
	private List<RichiestaDisponibilitaPaiPtiDTO> richiesteAccettateAperte;
	private List<RichiestaDisponibilitaPaiPtiDTO> richiesteAccettateChiuse;
	private RichiestaDisponibilitaPaiPtiDTO selectedRichiesta;
	private RichiestaDisponibilitaPaiPtiDTO selectedRichiestaMinore;
	private DefaultStreamedContent file;
	private List<DettaglioPTI> dettaglioPTISelected;
	private boolean prestazioneErogata = true;
	List<ArCsPaiPTIDocumentoDTO> listDocumentiPAI = new ArrayList<ArCsPaiPTIDocumentoDTO>();
	List<ArCsPaiPTIDocumentoDTO> listDocumentiPTI = new ArrayList<ArCsPaiPTIDocumentoDTO>();
	private ArCsPaiInfoSinteticheDTO infoSintetichePai = new ArCsPaiInfoSinteticheDTO();
	private ArCsPaiPTIDocumentoDTO selectedPai; // per la row expansion
	private MinoreInStruttura minoreStrutturaSelezionato;
	private List<MinoreInStruttura> lstMinoriInStruttura = new ArrayList<MinoreInStruttura>();
	private UploadedFile uploadedFile;
	private String uploadedFileType;
	public static Logger logger = Logger.getLogger("rilevazionepresenze.log");

	@EJB
	private RichiestaDisponibilitaPaiPtiSessionBeanRemote richiestaDisponibilitaEJB;

	@EJB
	private ComuniCacheHelperRemote comuniEJB;

	@EJB
	private ArCsPaiPTISessionBeanRemote arCsPaiPtiEJB;

	private static final long serialVersionUID = 1L;

	public RichiesteController() {
		this.struttura = new Struttura();
		this.richieste = new ArrayList<RichiestaDisponibilitaPaiPtiDTO>();
		this.richiesteAccettateAperte = new ArrayList<RichiestaDisponibilitaPaiPtiDTO>();
	}

	@PostConstruct
	public void init() {
		readRichieste();
		loadListaMinori();
	}

	public void readRichieste() {
		List<RichiestaDisponibilitaPaiPtiDTO> richiestaNA = new ArrayList<RichiestaDisponibilitaPaiPtiDTO>();
		Struttura s = getCurrentStruttura();
		if(s!=null){
			List<RichiestaDisponibilitaPaiPtiDTO> listRichieste = 
				richiestaDisponibilitaEJB.findRichiestaByIdStruttura(s.getId());
			for (RichiestaDisponibilitaPaiPtiDTO richA : listRichieste) {
	//			if (!richA.getStatoRichiesta().equalsIgnoreCase("ACCETTATA")) {
				loadDettaglioMinore(richA);
				loadDettaglioPTI(richA);
				richiestaNA.add(richA);
	
	//			}
			}
			setRichieste(richiestaNA);
		}
	}

	public void loadListaMinori() {
		// carico tutti i minori in struttura, leggendo le richieste in struttura con
		// stato "ACCETTATA"
		// una richiesta "ACCETTATA" puo essere creata sia a partire da un inserimento
		// manuale della struttura che da un inserimento effettuato dal CS
		Struttura s = getCurrentStruttura();
		if(s!=null){
			List<RichiestaDisponibilitaPaiPtiDTO> listRichiesteAccettate = 
					richiestaDisponibilitaEJB.findRichiestaByStrutturaStato(s.getId(), STATO_RICHIESTA.ACCETTATA.getCodice());
	
			List<MinoreInStruttura> listMinStrutt = new ArrayList<>();
			MinoreInStruttura minoreInStruttura;
			for (RichiestaDisponibilitaPaiPtiDTO rich : listRichiesteAccettate) {
				minoreInStruttura = new MinoreInStruttura();
				loadDettaglioMinore(rich);
				loadDettaglioPTI(rich);
				insertNote(rich);
				minoreInStruttura.setRichDisp(rich);
	
				listMinStrutt.add(minoreInStruttura);
			}
	
			this.lstMinoriInStruttura = listMinStrutt;
		}
	}

	public void insertNote(RichiestaDisponibilitaPaiPtiDTO rich) {
		String codRouting = rich.getCodRouting();
		Long idPaiPTI = rich.getIdProgettoIndividuale();
		if (!arCsPaiPtiEJB.hasPaiDoc(codRouting, idPaiPTI)) {
			rich.setNote(tempoRimanentePAI(rich));
		} else {
			rich.setNote(null);
		}
	}

	public void readListConsuntivazione() {
		if (minoreStrutturaSelezionato != null) {
			loadConsuntivazioniMinoreSelezionato();
		}
	}

	public void loadDettaglioMinore(RichiestaDisponibilitaPaiPtiDTO richiestaSelezionata) {
		try {
			if (richiestaSelezionata != null) {
				JSONObject jsonObject = new JSONObject(richiestaSelezionata.getDettaglioMinore());
				DettaglioMinore dtMinore = new DettaglioMinore();
				dtMinore.setCodiceFiscale(jsonObject.getString("cf"));
				dtMinore.setCognome(jsonObject.getString("cognome"));
				dtMinore.setNome(jsonObject.getString("nome"));
				dtMinore.setIndirizzoResidenza(jsonObject.getString("viaResidenza"));
				dtMinore.setSesso(jsonObject.getString("sesso"));
				dtMinore.setAnnoNascita(jsonObject.getInt("annoNascita"));
				try {

					JSONObject datiComune = new JSONObject(jsonObject.getString("comuneResidenza").replace("\\", ""));
					dtMinore.setComuneResidenza(datiComune.getString("denominazione"));
				} catch (Exception e) {
					logger.error(
							"Si è verificato un errore durante il recupero dei dati relativi alla residenza per il pti: "
									+ richiestaSelezionata.getId() + "/" + richiestaSelezionata.getCodRouting());
				}
				richiestaSelezionata.setMinore(dtMinore);
			}
		} catch (Exception e) {
			logger.error("Si è verificato un errore durante il recupero dei dati relativi al minore per il pti: "
					+ richiestaSelezionata.getId() + "/" + richiestaSelezionata.getCodRouting());
		}
	}

	public void loadDettaglioPTI(RichiestaDisponibilitaPaiPtiDTO rich) {
		try {
			if (rich != null) {
				JSONObject jsonObject = new JSONObject(rich.getDettaglioPTI());
				DettaglioPTI dtPTI = new DettaglioPTI();
				dtPTI.setDescTipoMinore(jsonObject.getString("descTipoMinore"));
				dtPTI.setDataRedazione(new Date(jsonObject.getLong("dataRedazione")));
				dtPTI.setPeriodoInsPianificatoDa(new Date(jsonObject.getLong("periodoInsPianificazioneDa")));
				dtPTI.setPeriodoInsPianificatoA(new Date(jsonObject.getLong("periodoInsPianificazioneA")));
				dtPTI.setFlgEmergenza(jsonObject.getBoolean("flgEmergenza"));
				dtPTI.setFlgDisabilita(jsonObject.getBoolean("flgDisabilita"));
				dtPTI.setFlgInterventiDisabili(jsonObject.getBoolean("flgInterventiDisabili"));
				dtPTI.setFlgCondVerifPresenzaAdulti(jsonObject.getBoolean("flgCondVerifPresenzaAdulti"));
				dtPTI.setFlgGravidanza(jsonObject.getBoolean("flgGravidanza"));
				dtPTI.setFlgNeonato(jsonObject.getBoolean("flgNeonato"));
				dtPTI.setFlgAreaPenale(jsonObject.getBoolean("flgAreaPenale"));
				if (jsonObject.get("caseManager") != null) {
					dtPTI.setCaseManager(jsonObject.get("caseManager").toString());
				} else {
					dtPTI.setCaseManager(null);
				}

				dtPTI.setFlgCoinvFamiglia(jsonObject.getBoolean("flgCoinvFamiglia"));
				dtPTI.setFlgProrRichMagg(jsonObject.getBoolean("flgProrRichMagg"));
				dtPTI.setFlgProrLimiteEta(jsonObject.getBoolean("flgProrLimiteEta"));
				dtPTI.setFlgEsisteEduPeda(jsonObject.getBoolean("flgEsisteEduPeda"));
				dtPTI.setFlgInvioSegnTM(jsonObject.getBoolean("flgInvioSegnTM"));
				if (jsonObject.get("faseAttuale") != null) {
					JSONObject fase = (JSONObject) jsonObject.get("faseAttuale");

					dtPTI.setFaseAttuale(fase.getLong("idStato"));
					if (dtPTI.getFaseAttuale().equals(PaiPTIFaseEnum.EROG_OK.getId())) {
						prestazioneErogata = true;
					}
				}

			
				rich.setPti(dtPTI);
			}
		} catch (Exception e) {
			logger.error("Si è verificato un errore durante il recupero dei dati relativi al dettaglio PTI per il pti: "
					+ rich.getId() + "/" + rich.getCodRouting(), e);
		}

	}

	public RichiestaDisponibilitaPaiPtiDTO getSelectedRichiesta() {
		return selectedRichiesta;
	}

	public void setSelectedRichiesta(RichiestaDisponibilitaPaiPtiDTO selectedRichiesta) {
		this.selectedRichiesta = selectedRichiesta;
	}

	public void fileUploadListener(FileUploadEvent event) {

		this.uploadedFile = event.getFile();
		this.uploadedFileType = (String) event.getComponent().getAttributes().get("tipoFile");
	}

	public void handleAllegato(FileUploadEvent event) {
		UploadedFile selectedFile = event.getFile();
		selectedRichiesta.setDocumento(selectedFile.getContents());
		selectedRichiesta.setNomeDocumento(selectedFile.getFileName());
	}

	public void prepareFile() {
		InputStream is = new ByteArrayInputStream(selectedRichiesta.getDocumento());
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		setFile(new DefaultStreamedContent(is, externalContext.getMimeType(selectedRichiesta.getNomeDocumento()),
				selectedRichiesta.getNomeDocumento()));
	}

	public void prepareFile(ArCsPaiPTIDocumentoDTO rowDoc) {
		try {
			InputStream is = new ByteArrayInputStream(rowDoc.getDocumento());
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

			String mimeType;

			mimeType = URLConnection.guessContentTypeFromStream(is);

			is.close();

			setFile(new DefaultStreamedContent(is, externalContext.getMimeType(mimeType), rowDoc.getNome()));

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}

	public String validaUploadPAI(RichiestaDisponibilitaPaiPtiDTO richiesta) {
		if (richiesta.getDocumento() == null) {
			return "Seleziona un documento da caricare";
		}

		return null;
	}

	public String validaRifiutaRichiesta(RichiestaDisponibilitaPaiPtiDTO richiesta) {
		if (richiesta.getMotivoRifiuto().isEmpty() || richiesta.getMotivoRifiuto() == null) {
			return "Motivo del rifiuto obbligatorio";
		}

		return null;
	}

	public String validaAccettaRichiesta(RichiestaDisponibilitaPaiPtiDTO richiesta) {
		if (richiesta.getDataInizioPermanenza() == null || richiesta.getDataFinePermanenza() == null) {
			return "Data inizio permanenza e Data fine permanenza sono dati obbligatori";
		}
		if (richiesta.getDataFinePermanenza().getTime() - richiesta.getDataInizioPermanenza().getTime() <= 0) {
			return "La data di fine permanenza deve essere maggiore della data di inizio";
		}
		return null;
	}

	public void salvaAccettaRichiesta() {
		try {
			String msg = validaAccettaRichiesta(selectedRichiesta);
			if (msg == null) {
				selectedRichiesta.setStatoRichiesta(STATO_RICHIESTA.ACCETTATA_STRUTTURA.getCodice());
				selectedRichiesta.setDataAccStruttura(new Date());
				richiestaDisponibilitaEJB.salva(selectedRichiesta);
				addMessage(FacesMessage.SEVERITY_INFO, "INFO ", "Salvataggio avvenuto con successo");
			} else {
				addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio", msg);
			}
		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio", e.getMessage());
		}
		readRichieste();

	}

	public void salvaRifiutaRichiesta() {
		try {
			String msg = validaRifiutaRichiesta(selectedRichiesta);
			if (msg == null) {
				selectedRichiesta.setStatoRichiesta(STATO_RICHIESTA.RIFIUTATA_STRUTTURA.getCodice());
				richiestaDisponibilitaEJB.salva(selectedRichiesta);
				addMessage(FacesMessage.SEVERITY_INFO, "INFO ", "Salvataggio avvenuto con successo");
			} else {
				addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio", msg);
			}
		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio", e.getMessage());
		}
		readRichieste();

	}

	public void salvaFile() {

		try {
			if (this.uploadedFile != null && this.uploadedFileType != null) {

				ArCsPaiPTIDocumentoDTO documentoPAI = new ArCsPaiPTIDocumentoDTO();
				documentoPAI.setIdPaiPTI(selectedRichiestaMinore.getIdProgettoIndividuale());
				documentoPAI.setCodRouting(selectedRichiestaMinore.getCodRouting());
				documentoPAI.setTipo(this.uploadedFile.getContentType());
				documentoPAI.setDtIns(new Date());
				documentoPAI.setNome(this.uploadedFile.getFileName());
				documentoPAI.setDocumento(this.uploadedFile.getContents());
				documentoPAI.setTipoDocumento(this.uploadedFileType);
				documentoPAI.setValidoDa(new Date());
				documentoPAI.setValidoA(null);
				Long idDocPaiSalvato = arCsPaiPtiEJB.salvaDocumento(documentoPAI);

				if (this.uploadedFileType.equalsIgnoreCase("PAI_DOC")) {
					infoSintetichePai.setIdDocPai(idDocPaiSalvato);
					infoSintetichePai.setIdPaiPTI(selectedRichiestaMinore.getIdProgettoIndividuale());
					infoSintetichePai.setCodRouting(selectedRichiestaMinore.getCodRouting());

					richiestaDisponibilitaEJB.salvaInfoSintetiche(infoSintetichePai);
				}

			}

			addMessage(FacesMessage.SEVERITY_INFO, "INFO ", "Salvataggio avvenuto con successo");
			caricaSelectedRichiestaDocumenti();
			
		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio", e.getMessage());
		} finally {
			resetDocumenti();
		}
	}

	public void resetDocumenti() {
		this.uploadedFile = null;
		this.uploadedFileType = null;
	}

	public void aggiornaInfoSintetiche(ArCsPaiPTIDocumentoDTO doc) {
		try {

			doc.setFlgNotifica(false);
			arCsPaiPtiEJB.salvaDocumento(doc);
			
			addMessage(FacesMessage.SEVERITY_INFO, "INFO ", "Aggionramento avvenuto con successo");

		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante l'aggiornamento", e.getMessage());
		}
	}

	public String tempoRimanentePAI(RichiestaDisponibilitaPaiPtiDTO rich) {
		try {
			int giorniMax = rich.getPti().isFlgEmergenza() ? 30 : 10;

			Date dt1;
			Date oggi = new Date();
			if (rich.getDataAccStruttura() == null) {
				dt1 = new Date();
			} else {
				dt1 = rich.getDataAccStruttura();
			}
			int tempoRimanente = giorniMax - (int) ((oggi.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24));
			if (tempoRimanente <= 0) {
				return "Sei in ritardo per il caricamento del PAI";
			}
			return "Attenzione! Hai ancora " + tempoRimanente + " giorni per caricare il PAI";
		} catch (Exception ex) {
			logger.error("Errore durante il controllo PAI: " + ex.getMessage());
		}
		return "Attenzione! Non è stato possibile recuperare il PTI.";
	}

	public String returnStato(RichiestaDisponibilitaPaiPtiDTO richiesta) {
		if (richiesta.getRichAttiva() != null && !richiesta.getRichAttiva()
				&& !richiesta.getStatoRichiesta().equals(STATO_RICHIESTA.ACCETTATA.getCodice())) {
			return "accettata_altra_struttura";
		}

		return richiesta.getStatoRichiesta().toLowerCase();
	}

	public String retrieveComuneProvenienza(String codRouting) {
		AmTabComuni comune = comuniEJB.getComuneByCodBelfiore(codRouting);
		return comune.getDenominazione();

	}

	public void caricaSelectedRichiestaDocumenti() {
		infoSintetichePai = new ArCsPaiInfoSinteticheDTO();
		listDocumentiPAI = new ArrayList<ArCsPaiPTIDocumentoDTO>();
		listDocumentiPTI = new ArrayList<ArCsPaiPTIDocumentoDTO>();

		if (this.selectedRichiestaMinore != null) {
			String codRouting = selectedRichiestaMinore.getCodRouting();
			Long idPaiPTI = selectedRichiestaMinore.getIdProgettoIndividuale();
			List<ArCsPaiPTIDocumentoDTO> listDocumentiRichiesta = 
					arCsPaiPtiEJB.findDocumentiRichiestaSelezionata(codRouting, idPaiPTI);

			for (ArCsPaiPTIDocumentoDTO doc : listDocumentiRichiesta) {
				if (doc.getTipoDocumento().equalsIgnoreCase("PAI_DOC")) {
					doc.initInfoSintetiche(doc.getId(), doc.getCodRouting(), doc.getIdPaiPTI());

					if (doc.getValidoA() == null) {
						// recupero le info sintetiche relative al documento valido
						infoSintetichePai = doc.getArCsPaiInfoSinteticheDTO();
					}
					listDocumentiPAI.add(doc);
				} else if (doc.getTipoDocumento().equalsIgnoreCase("PTI_DOC")) {
					listDocumentiPTI.add(doc);
				}
			}
			setListDocumentiPAI(listDocumentiPAI);
			setListDocumentiPTI(listDocumentiPTI);
		}

	}

	public void onRowEdit(RowEditEvent event) {
		ArCsPaiPtiConsuntiDTO ob = (ArCsPaiPtiConsuntiDTO) event.getObject();
		if (checkDate(ob))
			salvaConsuntivazione(ob, ob.getInviatoEnte());
	}

	public void onRowCancel(RowEditEvent event) {
		ArCsPaiPtiConsuntiDTO ob = (ArCsPaiPtiConsuntiDTO) event.getObject();
		if (ob.getId() != null) {
//			minoreStrutturaSelezionato.getListaConsuntivazioni().remove(ob);
			eliminaConsuntivazione(ob);
		}
		minoreStrutturaSelezionato.getListaConsuntivazioni();
	}

	public void onAddNew() {

		for (ArCsPaiPtiConsuntiDTO cons : minoreStrutturaSelezionato.getListaConsuntivazioni()) {
			if (cons == null) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Attenzione", "È gia presente un inserimento da salvare");
			}
		}

		ArCsPaiPtiConsuntiDTO nuovaConsuntivaz = new ArCsPaiPtiConsuntiDTO(
				minoreStrutturaSelezionato.getRichDisp().getIdProgettoIndividuale(),
				minoreStrutturaSelezionato.getRichDisp().getCodRouting());
		nuovaConsuntivaz.setFlagErogato(false);
		nuovaConsuntivaz.setInviatoEnte(false);

		Calendar da = Calendar.getInstance();
		da.set(Calendar.DAY_OF_MONTH, 1);
		da.set(Calendar.HOUR_OF_DAY, 0);
		da.set(Calendar.MINUTE, 0);
		da.set(Calendar.SECOND, 0);
		da.set(Calendar.MILLISECOND, 0);
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
		a.set(Calendar.HOUR_OF_DAY, 0);
		a.set(Calendar.MINUTE, 0);
		a.set(Calendar.SECOND, 0);
		a.set(Calendar.MILLISECOND, 0);

		nuovaConsuntivaz.setDataDa(da.getTime());
		nuovaConsuntivaz.setDataA(a.getTime());
		nuovaConsuntivaz.setNumGiorni(new Long(Utils.daysBetween(da.getTime(), a.getTime())));

		minoreStrutturaSelezionato.getListaConsuntivazioni().add(nuovaConsuntivaz);

	}

	public boolean checkDate(ArCsPaiPtiConsuntiDTO consModificata) {
		if (consModificata.getDataDa() == null) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Campo 'Data Da' obbligatorio ", null);
			return false;
		}

		if (consModificata.getDataA() == null) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Campo 'Data A' obbligatorio ", null);
			return false;
		}

		if (consModificata.getDataA().before(consModificata.getDataDa())) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Campo 'Data Da' deve essere antecedente al campo 'Data A' ", null);
			return false;
		}

		int maxConsentito = Utils.daysBetween(consModificata.getDataDa(), consModificata.getDataA());
		if (maxConsentito < consModificata.getNumGiorni()) {
			addMessage(FacesMessage.SEVERITY_ERROR,
					"'Numero giorni' indicati superano il massimo consentito (" + maxConsentito + ")", null);
			return false;
		}

		for (ArCsPaiPtiConsuntiDTO cons : minoreStrutturaSelezionato.getListaConsuntivazioni()) {
			if (cons.getId() == null || cons.getId().equals(consModificata.getId())) {
				continue;
			}

			if (Utils.isBeetween(consModificata.getDataA(), cons.getDataDa(), cons.getDataA())) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Sovrapposizione date",
						"La data " + Utils.formatDate("yyyy-MM-dd", consModificata.getDataA())
								+ " già inclusa nel periodo: " + Utils.formatDate("yyyy-MM-dd", cons.getDataDa())
								+ " - " + Utils.formatDate("yyyy-MM-dd", cons.getDataA()));
				return false;
			}

			if (Utils.isBeetween(consModificata.getDataDa(), cons.getDataDa(), cons.getDataA())) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Sovrapposizione date",
						"La data " + Utils.formatDate("yyyy-MM-dd", consModificata.getDataDa())
								+ " già inclusa nel periodo: " + Utils.formatDate("yyyy-MM-dd", cons.getDataDa())
								+ " - " + Utils.formatDate("yyyy-MM-dd", cons.getDataA()));
				return false;
			}

			if (consModificata.getDataDa().before(cons.getDataDa())
					&& consModificata.getDataA().after(cons.getDataA())) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Sovrapposizione date",
						"Il periodo " + Utils.formatDate("yyyy-MM-dd", consModificata.getDataDa()) + " - "
								+ Utils.formatDate("yyyy-MM-dd", consModificata.getDataA())
								+ " si sovrappone col periodo: " + Utils.formatDate("yyyy-MM-dd", cons.getDataDa())
								+ " - " + Utils.formatDate("yyyy-MM-dd", cons.getDataA()));
				return false;
			}
		}
		return true;
	}

	public void updateConsuntivazione(ArCsPaiPtiConsuntiDTO consuntivazione) {

		try {
			if (consuntivazione != null) {
				consuntivazione.setInviatoEnte(true);
				richiestaDisponibilitaEJB.salvaConsuntivazione(consuntivazione);
			}

			readListConsuntivazione();

		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio", e.getMessage());
		}
	}

	public void salvaConsuntivazione(ArCsPaiPtiConsuntiDTO consuntivazione, Boolean invioEnte) {

		try {
			if (consuntivazione != null) {
				consuntivazione.setInviatoEnte(invioEnte);
				consuntivazione = richiestaDisponibilitaEJB.salvaConsuntivazione(consuntivazione);
			}
//			readListConsuntivazione();

			// aggiorno la lista consuntivazioni
			for (ArCsPaiPtiConsuntiDTO cons : minoreStrutturaSelezionato.getListaConsuntivazioni()) {
				if (cons.getId() == null) {
					cons.setId(consuntivazione.getId());
					break;
				}
			}
			FacesMessage msg = new FacesMessage("Consuntivazione modificata", consuntivazione.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio", e.getMessage());
		}
	}

	public void eliminaConsuntivazione(ArCsPaiPtiConsuntiDTO consuntivazione) {

		try {
			if (consuntivazione != null) {
				richiestaDisponibilitaEJB.eliminaConsuntivazione(consuntivazione);
			}

			// aggiorno la lista consuntivazioni
			readListConsuntivazione();

			
			FacesMessage msg = new FacesMessage("Consuntivazione eliminata", consuntivazione.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante la cancellazione", e.getMessage());
		}
	}
	public void loadConsuntivazioniMinoreSelezionato() {
		String codRouting = minoreStrutturaSelezionato.getRichDisp().getCodRouting();
		Long idPaiPTI = minoreStrutturaSelezionato.getRichDisp().getIdProgettoIndividuale();
		// recupero lista consuntivazioni
		List<ArCsPaiPtiConsuntiDTO> lista = richiestaDisponibilitaEJB.findListaConsuntivazione(codRouting, idPaiPTI);
		minoreStrutturaSelezionato.setListaConsuntivazioni(lista);
	}

	public List<MinoreInStruttura> getLstMinoriInStruttura() {
		return lstMinoriInStruttura;
	}

	public void setLstMinoriInStruttura(List<MinoreInStruttura> lstMinoriInStruttura) {
		this.lstMinoriInStruttura = lstMinoriInStruttura;
	}

	public Struttura getStruttura() {
		return struttura;
	}

	public List<RichiestaDisponibilitaPaiPtiDTO> getRichieste() {
		return richieste;
	}

	public void setStruttura(Struttura struttura) {
		this.struttura = struttura;
	}

	public void setRichieste(List<RichiestaDisponibilitaPaiPtiDTO> richieste) {
		this.richieste = richieste;
	}

	public MinoreInStruttura getMinoreStrutturaSelezionato() {
		return minoreStrutturaSelezionato;
	}

	public void setMinoreStrutturaSelezionato(MinoreInStruttura minoreStrutturaSelezionato) {
		this.minoreStrutturaSelezionato = minoreStrutturaSelezionato;
	}

	public ArCsPaiPTIDocumentoDTO getSelectedPai() {
		return selectedPai;
	}

	public void setSelectedPai(ArCsPaiPTIDocumentoDTO selectedPai) {
		this.selectedPai = selectedPai;
	}

	public ArCsPaiInfoSinteticheDTO getInfoSintetichePai() {
		return infoSintetichePai;
	}

	public void setInfoSintetichePai(ArCsPaiInfoSinteticheDTO infoSintetichePai) {
		this.infoSintetichePai = infoSintetichePai;
	}

	public RichiestaDisponibilitaPaiPtiDTO getSelectedRichiestaMinore() {
		return selectedRichiestaMinore;
	}

	public void setSelectedRichiestaMinore(RichiestaDisponibilitaPaiPtiDTO selectedRichiestaMinore) {
		this.selectedRichiestaMinore = selectedRichiestaMinore;
	}

	public boolean isPrestazioneErogata() {
		return prestazioneErogata;
	}

	public void setPrestazioneErogata(boolean prestazioneErogata) {
		this.prestazioneErogata = prestazioneErogata;
	}

	public DefaultStreamedContent getFile() {
		return file;
	}

	public void setFile(DefaultStreamedContent file) {
		this.file = file;
	}

	public List<DettaglioPTI> getDettaglioPTISelected() {
		return dettaglioPTISelected;
	}

	public void setDettaglioPTISelected(List<DettaglioPTI> dettaglioPTISelected) {
		this.dettaglioPTISelected = dettaglioPTISelected;
	}

	public List<ArCsPaiPTIDocumentoDTO> getListDocumentiPAI() {
		return listDocumentiPAI;
	}

	public void setListDocumentiPAI(List<ArCsPaiPTIDocumentoDTO> listDocumentiPAI) {
		this.listDocumentiPAI = listDocumentiPAI;
	}

	public List<ArCsPaiPTIDocumentoDTO> getListDocumentiPTI() {
		return listDocumentiPTI;
	}

	public void setListDocumentiPTI(List<ArCsPaiPTIDocumentoDTO> listDocumentiPTI) {
		this.listDocumentiPTI = listDocumentiPTI;
	}

	public List<RichiestaDisponibilitaPaiPtiDTO> getRichiesteAccettateAperte() {
		return richiesteAccettateAperte;
	}

	public void setRichiesteAccettateAperte(List<RichiestaDisponibilitaPaiPtiDTO> richiesteAccettateAperte) {
		this.richiesteAccettateAperte = richiesteAccettateAperte;
	}

	public List<RichiestaDisponibilitaPaiPtiDTO> getRichiesteAccettateChiuse() {
		return richiesteAccettateChiuse;
	}

	public void setRichiesteAccettateChiuse(List<RichiestaDisponibilitaPaiPtiDTO> richiesteAccettateChiuse) {
		this.richiesteAccettateChiuse = richiesteAccettateChiuse;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getUploadedFileType() {
		return uploadedFileType;
	}

	public void setUploadedFileType(String uploadedFileType) {
		this.uploadedFileType = uploadedFileType;
	}

}
