package eu.smartpeg.gedclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import eng.protocollo.protocollazione.data.NumeroAllegato;
import eng.protocollo.protocollazione.data.ged.ArrayOfNumeroProtocollo;
import eng.protocollo.protocollazione.data.ged.Autenticazione;
import eng.protocollo.protocollazione.data.ged.DatiProtocollazione;
import eng.protocollo.protocollazione.data.ged.Nominativo;
import eng.protocollo.protocollazione.data.ged.NumeroProtocollo;
import eng.protocollo.protocollazione.data.ged.ResultProtocollazione;
import eng.protocollo.protocollazione.data.ged.TipologiaDocumenti;
import eng.protocollo.protocollazione.data.v2.ProtocolloConAllegati;
import eng.protocollo.ws.ArrayOf186797045598279129NillableNominativo;
import eng.protocollo.ws.ArrayOf186797045598279129NillableStrutturaDestinataria;
import eng.protocollo.ws.DettaglioProtocollo;
import eng.protocollo.ws.GestoreAllegato;
import eng.protocollo.ws.GestoreAllegatoService;
import eng.protocollo.ws.GestoreDettaglioProtocolloV2;
import eng.protocollo.ws.GestoreDettaglioProtocolloV2Service;
import eng.protocollo.ws.GestoreGEDPRT;
import eng.protocollo.ws.GestoreGEDPRTService;
import eu.smartpeg.gedclient.exception.ConfigurationException;
import eu.smartpeg.gedclient.exception.GedRomaException;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;

public class GedRomaClient {

	private final static Logger logger = Logger.getLogger("gedroma.log");
//	private URL webServiceUrlGestioneDettaglio;
//	private URL webServiceUrlGestioneAllegato;
//	private URL webServiceUrlGestioneProtocollo;
	private String webServiceUrlGestioneDettaglio;
	private String webServiceUrlGestioneAllegato;
	private String webServiceUrlGestioneProtocollo;
	
	private String codiceApplicazioneChiamante;
	private String passwordApplicazioneChiamante;
	private String codiceProceduraChiamante;

	private static String ILLEGAL_WS_URL_MSG = "URL Web Service GED non valida";
	private static String CODICE_ERRORE_PROTOCOLLO_NON_TROVATO = "20046";

	public GedRomaClient() throws ConfigurationException {
		try {
			initFromConfiguration();
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			throw new IllegalArgumentException(ILLEGAL_WS_URL_MSG, e);
		} catch (ConfigurationException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Carica parametri di default per il client da configurazione
	 * 
	 * @throws MalformedURLException
	 * @throws ConfigurationException
	 */
	private void initFromConfiguration() throws MalformedURLException, ConfigurationException {

//		URL endpointGestioneDettaglio = new URL(getAttributeKey("gedRoma.dettaglioProtocollo.ws.url"));
//		URL endpointGestioneAllegato = new URL(getAttributeKey("gedRoma.gestioneAllegato.ws.url"));
//		URL endpointGestioneProtocollo = new URL(getAttributeKey("gedRoma.gestioneProtocollo.ws.url"));
		String endpointGestioneDettaglio = getAttributeKey("gedRoma.dettaglioProtocollo.ws.url");
		String endpointGestioneAllegato = getAttributeKey("gedRoma.gestioneAllegato.ws.url");
		String endpointGestioneProtocollo = getAttributeKey("gedRoma.gestioneProtocollo.ws.url");
		
		String codiceApplicazioneChiamante = getAttributeKey("gedRoma.common.applicazioneChiamante");
		String passwordApplicazioneChiamante = getAttributeKey("gedRoma.common.passwordApplicazioneChiamante");
		String codiceProceduraChiamante = getAttributeKey("gedRoma.common.codiceProceduraChiamante");

		if (endpointGestioneDettaglio == null || codiceApplicazioneChiamante == null
				|| passwordApplicazioneChiamante == null || codiceProceduraChiamante == null) {
			throw new ConfigurationException();
		}
		setCodiceApplicazioneChiamante(codiceApplicazioneChiamante);
		setPasswordApplicazioneChiamante(passwordApplicazioneChiamante);
		setCodiceProceduraChiamante(codiceProceduraChiamante);

		setWebServiceUrlGestioneAllegato(endpointGestioneAllegato);
		setWebServiceUrlGestioneDettaglio(endpointGestioneDettaglio);
		setWebServiceUrlGestioneProtocollo(endpointGestioneProtocollo);
	}

	// =========================================================================

	// =========================================================================
	// METODI PUBBLICI

	/**
	 * Inserisce file allegato in protocollo esistente
	 * 
	 * @param numeroProtocollo
	 * @param codiceDocumento
	 * @param allegato
	 * @return numero allegato assegnato dal GED. NULL se inserimento fallito
	 * @throws Exception
	 */
	public String inserisciAllegatoInProtocolloEsistente(NumeroProtocolloGED numeroProtocollo, String codiceDocumento,
			AllegatoProtocolloGED allegato, String utenteCollegato) throws Exception {
				
		logger.debug("[GED - inserisciAllegatoInProtocolloEsistente] PARAMETRI IN INPUT 4:");
		logger.debug("[GED - inserisciAllegatoInProtocolloEsistente] " + numeroProtocollo.toString());
		logger.debug("[GED - inserisciAllegatoInProtocolloEsistente] codiceDocumento: " + codiceDocumento);
		logger.debug("[GED - inserisciAllegatoInProtocolloEsistente] " + allegato.toString());
		logger.debug("[GED - inserisciAllegatoInProtocolloEsistente] utenteCollegato: " + utenteCollegato);

		NumeroAllegato numeroAllegato = chiamaWsInserimentoAllegato(numeroProtocollo, codiceDocumento, allegato, utenteCollegato);

		if (numeroAllegato.getErrore() != null) {
			logger.error("Si è verificato il seguente errore: '" + numeroAllegato.getErrore().getCodiceErrore() + " - "
					+ numeroAllegato.getErrore().getDescrizioneErrore() + "'");
			throw new Exception(numeroAllegato.getErrore().getDescrizioneErrore());
		}
		
		logger.debug("[GED - inserisciAllegatoInProtocolloEsistente] OUTPUT: numeroAllegato: " + numeroAllegato.getNumeroAllegato());

		return numeroAllegato.getNumeroAllegato();
	}

	/**
	 * Protocollazione documento mittente esterno su GED. Tipo DocumentoGED da
	 * scegliere in base tipo di documento ed al municipio che gestisce la pratica
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	public NumeroProtocolloGED protocollazioneMittenteEsterno(String tipoDocumentoGED, String testoOggetto,
			String noteDocumento, NominativoGED nominativoMittente, String idUnivocoProceduraChiamante)
			throws MalformedURLException {
		
		Autenticazione autenticazione = creaAutenticazioneGED();
		logger.debug("[GED - protocollazioneMittenteEsterno] autenticazione OK");

		TipologiaDocumenti tipologiaDocumenti = creaTipologiaDocumenti(tipoDocumentoGED);
		logger.debug("[GED - protocollazioneMittenteEsterno] tipologiaDocumenti OK");

		DatiProtocollazione datiProtocollazione = creaDatiProtocollazione(testoOggetto, noteDocumento, idUnivocoProceduraChiamante);
		logger.debug("[GED - protocollazioneMittenteEsterno] datiProtocollazione OK");

		Nominativo nominativo = creaNominativo(nominativoMittente);
		logger.debug("[GED - protocollazioneMittenteEsterno] nominativo OK");

		ArrayOf186797045598279129NillableStrutturaDestinataria destinatari = new ArrayOf186797045598279129NillableStrutturaDestinataria();

		ArrayOf186797045598279129NillableNominativo interessati = new ArrayOf186797045598279129NillableNominativo();
		
		logger.debug("[GED - protocollazioneMittenteEsterno] PARAMETRI IN INPUT 5:");
		logger.debug("[GED - protocollazioneMittenteEsterno] tipoDocumentoGED: " + tipoDocumentoGED);
		logger.debug("[GED - protocollazioneMittenteEsterno] testoOggetto: " + testoOggetto);
		logger.debug("[GED - protocollazioneMittenteEsterno] noteDocumento: " + noteDocumento);
		logger.debug("[GED - protocollazioneMittenteEsterno] " + nominativoMittente.toString());
		logger.debug("[GED - protocollazioneMittenteEsterno] idUnivocoProceduraChiamante: " + idUnivocoProceduraChiamante);
		logger.debug("[GED - protocollazioneMittenteEsterno] datiProtocollazione --> isIsEsistente: " + datiProtocollazione.isIsEsistente() + ", tipoCodificaStruttura: " + datiProtocollazione.getTipoCodificaStruttura()  + " isIsDocumentoRiservato: " + datiProtocollazione.isIsDocumentoRiservato() );

		ResultProtocollazione result = chiamaWsProtocollazione(autenticazione, tipologiaDocumenti, datiProtocollazione,
				nominativo, destinatari, interessati);
		logger.debug("[GED - protocollazioneMittenteEsterno] chiamaWsProtocollazione OK");
		
		logger.debug("[GED - protocollazioneMittenteEsterno] OUTPUT: codice: " + result.getCodice() + ", descrizione: " + result.getDescrizione() + ", tipo: " + result.getTipo());

		return decodificaRispostaWebService(result);
	}

	/**
	 * Verifica esistenza di uno specifico numero protocollo
	 * 
	 * @param codiceDocumentoGED
	 * @param numeroProtocollo
	 * @return
	 * @throws Exception
	 */
	public boolean ricercaProtocolloSingolo(String codiceDocumentoGED, NumeroProtocolloGED numeroProtocollo,
			String utenteCollegato) throws Exception {

		logger.debug("[GED - ricercaProtocolloSingolo] PARAMETRI IN INPUT 3:");
		logger.debug("[GED - ricercaProtocolloSingolo] codiceDocumentoGED: " + codiceDocumentoGED);
		logger.debug("[GED - ricercaProtocolloSingolo] " + numeroProtocollo.toString());
		logger.debug("[GED - ricercaProtocolloSingolo] utenteCollegato: " + utenteCollegato);

		ProtocolloConAllegati protocolloConAllegati = chiamaWsDettaglioProtocollo(codiceDocumentoGED, numeroProtocollo,
				utenteCollegato);

		logger.debug("[GED - ricercaProtocolloSingolo] OUTPUT: getAnnoProtocollo: " + protocolloConAllegati.getAnnoProtocollo());
		
		if(protocolloConAllegati.getErrore() != null)
		{
			logger.debug("[GED - ricercaProtocolloSingolo] ERRORE: codice: " + protocolloConAllegati.getErrore().getCodiceErrore() + " , descrizione: " + protocolloConAllegati.getErrore().getDescrizioneErrore());
		}
		
		return decodificaRispostaWsRicercaDettaglioProtocollo(protocolloConAllegati);
	}

	// ====================================================================================

	// ====================================================================================
	// METODI PRIVATI

	private ProtocolloConAllegati chiamaWsDettaglioProtocollo(String codiceDocumentoGED,
			NumeroProtocolloGED numeroProtocollo, String utenteCollegato) {
		DettaglioProtocollo dettaglioProtocollo = new DettaglioProtocollo();		
		dettaglioProtocollo.setCodiceApplicazioneChiamante(getCodiceApplicazioneChiamante());
		dettaglioProtocollo.setPasswordApplicazioneChiamante(getPasswordApplicazioneChiamante());
		dettaglioProtocollo.setCodiceProcedura(getCodiceProceduraChiamante());
		dettaglioProtocollo.setCodiceDocumento(codiceDocumentoGED);
		dettaglioProtocollo.setTipoProtocollo(numeroProtocollo.getTipoProtocollo());
		dettaglioProtocollo.setAnnoProtocollo(numeroProtocollo.getAnnoProtocollo());
		dettaglioProtocollo.setNumeroProgressivoProtocollo(numeroProtocollo.getNumeroProgressivoProtocollo());
		dettaglioProtocollo.setUtenteCollegato(utenteCollegato);        
		
		URL url = getClass().getClassLoader().getResource(webServiceUrlGestioneDettaglio);
		System.out.println("url: "+  url.toString());
		
		GestoreDettaglioProtocolloV2Service gestoreDettaglioProtocolloV2service = new GestoreDettaglioProtocolloV2Service(url);
		GestoreDettaglioProtocolloV2 gestoreDettaglioProtocolloV2proxy = gestoreDettaglioProtocolloV2service.getGestoreDettaglioProtocolloV2();
		
		ProtocolloConAllegati protocolloConAllegati = gestoreDettaglioProtocolloV2proxy.dettaglioProtocollo(
				dettaglioProtocollo.getCodiceApplicazioneChiamante(),
				dettaglioProtocollo.getPasswordApplicazioneChiamante(), dettaglioProtocollo.getCodiceProcedura(),
				dettaglioProtocollo.getCodiceDocumento(), dettaglioProtocollo.getTipoProtocollo(),
				dettaglioProtocollo.getAnnoProtocollo(), dettaglioProtocollo.getNumeroProgressivoProtocollo(),
				dettaglioProtocollo.getUtenteCollegato(), dettaglioProtocollo.getStrutturaUtenteCollegato());
		
		
		
		return protocolloConAllegati;
	}

	private boolean decodificaRispostaWsRicercaDettaglioProtocollo(ProtocolloConAllegati protocolloConAllegati)
			throws Exception {
		if (protocolloConAllegati.getErrore() != null) {
			if (protocolloConAllegati.getErrore().getCodiceErrore()
					.equalsIgnoreCase(CODICE_ERRORE_PROTOCOLLO_NON_TROVATO)) {
				logger.error("[GED - ricercaProtocolloSingolo/decodificaRispostaWsRicercaDettaglioProtocollo] OUTPUT: false - Protocollo non trovato");
				return false;
			} else {
				throw new GedRomaException(protocolloConAllegati.getErrore().getCodiceErrore(),
						(protocolloConAllegati.getErrore().getDescrizioneErrore()));
			}
		}
		
		logger.debug("[GED - ricercaProtocolloSingolo/decodificaRispostaWsRicercaDettaglioProtocollo] OUTPUT: true");

		return true;
	}

	private NumeroAllegato chiamaWsInserimentoAllegato(NumeroProtocolloGED numeroProtocollo, String codiceDocumento,
			AllegatoProtocolloGED allegato, String utenteCollegato) {
		
		URL url = getClass().getClassLoader().getResource(webServiceUrlGestioneAllegato);
		System.out.println("url: "+  url.toString());
		
		GestoreAllegatoService gestoreAlegatoService = new GestoreAllegatoService(url);
		GestoreAllegato gestoreAllegatoProxy = gestoreAlegatoService.getGestoreAllegato();

		Integer DIMENSIONE_FILE = new Integer(0); // probabilmente non serve
		String TIPO_FILE = null; // nell'esempio questo parametro è NULL
		NumeroAllegato numeroAllegato = gestoreAllegatoProxy.inserimentoAllegato(getCodiceApplicazioneChiamante(),
				getPasswordApplicazioneChiamante(), getCodiceProceduraChiamante(), codiceDocumento, utenteCollegato,
				numeroProtocollo.getTipoProtocollo(), numeroProtocollo.getAnnoProtocollo(),
				numeroProtocollo.getNumeroProgressivoProtocollo(), allegato.getDescrizione(), allegato.getNomeFile(),
				DIMENSIONE_FILE, TIPO_FILE, allegato.getAllegato());
		return numeroAllegato;
	}

	private NumeroProtocolloGED decodificaRispostaWebService(ResultProtocollazione result) {
		NumeroProtocolloGED numeroProtocolloGED = null;

		ArrayOfNumeroProtocollo protocolli = result.getNumeroProtocolli();
		
		if(protocolli != null)
		{
			List<NumeroProtocollo> listaProtocolli = protocolli.getNumeroProtocollo();
	
			if (listaProtocolli.isEmpty() == false) {
				NumeroProtocollo numeroProtocolo = listaProtocolli.get(0);
	
				numeroProtocolloGED = NumeroProtocolloGED.CreaNumeroProtocollo(numeroProtocolo.getTipoProtocollo(),
						numeroProtocolo.getAnnoProtocollo(), numeroProtocolo.getProgressivoProtocollo());
			}
			
			logger.debug("[GED - protocollazioneMittenteEsterno/decodificaRispostaWebService] OUTPUT: numeroProtocolloGED: " + numeroProtocolloGED.toString());
		}
		else
		{
			logger.debug("[GED - protocollazioneMittenteEsterno/decodificaRispostaWebService] result.getNumeroProtocolli() è null ");		
		}
		
		return numeroProtocolloGED;
	}

	private ResultProtocollazione chiamaWsProtocollazione(Autenticazione autenticazione,
			TipologiaDocumenti tipologiaDocumenti, DatiProtocollazione datiProtocollazione, Nominativo nominativo,
			ArrayOf186797045598279129NillableStrutturaDestinataria destinatari,
			ArrayOf186797045598279129NillableNominativo interessati) {
		
		URL url = getClass().getClassLoader().getResource(webServiceUrlGestioneProtocollo);
		System.out.println("url: "+  url.toString());
		
		GestoreGEDPRTService gestoreGEDPRTService = new GestoreGEDPRTService(url);
		GestoreGEDPRT gestoreGEDPRTProxy = gestoreGEDPRTService.getGestoreGEDPRT();

		ResultProtocollazione result = gestoreGEDPRTProxy.protocollazioneMittenteEsterno(autenticazione,
				tipologiaDocumenti, datiProtocollazione, nominativo, destinatari, interessati);
		return result;
	}

	private Nominativo creaNominativo(NominativoGED nominativoMittente) {
		Nominativo nominativo = new Nominativo();
		nominativo.setTipoNominativo(getAttributeKey("gedRoma.gestioneProtocollo.nominativo.tipo"));
		// TODO: verificare se questo parametro è fisso (per ora codiceGED è fisso)
		nominativo.setCodiceGED(getAttributeKey("gedRoma.gestioneProtocollo.nominativo.codiceGED"));
		nominativo.setDescrizioneNominativo(nominativoMittente.getDescrizione());
		nominativo.setCodiceFiscale(nominativoMittente.getCodiceFiscale());
		return nominativo;
	}

	private DatiProtocollazione creaDatiProtocollazione(String testoOggetto, String noteDocumento,
			String idUnivocoProceduraChiamante) {
		DatiProtocollazione datiProtocollazione = new DatiProtocollazione();
		datiProtocollazione.setTestoOggetto(testoOggetto);
		datiProtocollazione.setNoteDocumento(noteDocumento);
		datiProtocollazione.setIdUnivocoProceduraChiamante(idUnivocoProceduraChiamante);
		datiProtocollazione.setIsEsistente(new Boolean(getAttributeKey("gedRoma.gestioneProtocollo.datiProtocollazione.isEsistente")));
		datiProtocollazione.setTipoCodificaStruttura(getAttributeKey("gedRoma.gestioneProtocollo.datiProtocollazione.tipoCodStrutt"));
		datiProtocollazione.setIsDocumentoRiservato(false);
		
		return datiProtocollazione;
	}

	private TipologiaDocumenti creaTipologiaDocumenti(String tipoDocumentoGED) {
		TipologiaDocumenti tipologiaDocumenti = new TipologiaDocumenti();
		tipologiaDocumenti.setCodiceDocumento(tipoDocumentoGED);
		tipologiaDocumenti.setCodiceProceduraChiamante(getCodiceProceduraChiamante());
		return tipologiaDocumenti;
	}

	private Autenticazione creaAutenticazioneGED() {
		Autenticazione autenticazione = new Autenticazione();
		autenticazione.setCodiceApplicazioneChiamante(getCodiceApplicazioneChiamante());
		autenticazione.setPasswordApplicazioneChiamante(getPasswordApplicazioneChiamante());
		return autenticazione;
	}

	public String getWebServiceUrlGestioneDettaglio() {
		return webServiceUrlGestioneDettaglio;
	}

	public void setWebServiceUrlGestioneDettaglio(String webServiceUrlGestioneDettaglio) {
		this.webServiceUrlGestioneDettaglio = webServiceUrlGestioneDettaglio;
	}

	public String getWebServiceUrlGestioneAllegato() {
		return webServiceUrlGestioneAllegato;
	}

	public void setWebServiceUrlGestioneAllegato(String webServiceUrlGestioneAllegato) {
		this.webServiceUrlGestioneAllegato = webServiceUrlGestioneAllegato;
	}

	public String getWebServiceUrlGestioneProtocollo() {
		return webServiceUrlGestioneProtocollo;
	}

	public void setWebServiceUrlGestioneProtocollo(String webServiceUrlGestioneProtocollo) {
		this.webServiceUrlGestioneProtocollo = webServiceUrlGestioneProtocollo;
	}

	public String getCodiceApplicazioneChiamante() {
		return codiceApplicazioneChiamante;
	}

	public void setCodiceApplicazioneChiamante(String codice_applicazione_chiamante) {
		this.codiceApplicazioneChiamante = codice_applicazione_chiamante;
	}

	public String getPasswordApplicazioneChiamante() {
		return passwordApplicazioneChiamante;
	}

	public void setPasswordApplicazioneChiamante(String password_applicazione_chiamante) {
		this.passwordApplicazioneChiamante = password_applicazione_chiamante;
	}

	public String getCodiceProceduraChiamante() {
		return codiceProceduraChiamante;
	}

	public void setCodiceProceduraChiamante(String codiceProceduraChiamante) {
		this.codiceProceduraChiamante = codiceProceduraChiamante;
	}

	private String getAttributeKey(String keyConf) {
		ParameterService paramService = (ParameterService) getEjb("CT_Service", "CT_Config_Manager",
				"ParameterBaseService");
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey(keyConf);
		return paramService.getAmKeyValueExt(criteria).getValueConf();
	}

	public static Object getEjb(String ear, String module, String ejbName) {
		Context cont;
		try {
			cont = new InitialContext();
			return cont.lookup("java:global/" + ear + "/" + module + "/" + ejbName);
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
