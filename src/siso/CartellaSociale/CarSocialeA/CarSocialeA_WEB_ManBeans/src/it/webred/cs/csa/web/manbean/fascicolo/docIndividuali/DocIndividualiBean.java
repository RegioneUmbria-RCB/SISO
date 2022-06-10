package it.webred.cs.csa.web.manbean.fascicolo.docIndividuali;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.primefaces.context.RequestContext;

import eu.smartpeg.gedclient.AllegatoProtocolloGED;
import eu.smartpeg.gedclient.GedRomaClient;
import eu.smartpeg.gedclient.NominativoGED;
import eu.smartpeg.gedclient.NumeroProtocolloGED;
import eu.smartpeg.gedclient.exception.GedRomaException;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DatiOperatoreDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.docIndividuali.DocIndividualeBean;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompSecondoLivello;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiFascicolo;
import it.webred.cs.data.DataModelCostanti.TipoVisibilitaDocumento;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDDocIndividuale;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbSottocartellaDoc;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.jsf.interfaces.IDocIndividuali;
import it.webred.cs.jsf.manbean.DiarioDocsMan;
import it.webred.cs.jsf.manbean.TempCodFiscManager;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

@ManagedBean
@ViewScoped
public class DocIndividualiBean extends FascicoloCompSecondoLivello implements IDocIndividuali {

	private List<CsTbSottocartellaDoc> listaSottocartelle;
	private List<DocIndividualeBean> listaDocIndividualiPubblica;
	private List<DocIndividualeBean> listaDocIndividualiPrivata;
	private List<DocIndividualeBean> listaDocIndividualiRichiestaServizio; // SISO-438

	private int idxSelected;
	private String modalHeader;
	private boolean disableUpload;
	private CsDDocIndividuale docIndividuale;
	private DiarioDocsMan diarioDocsMan;
	private static Pattern NUMERIC_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");
	private static Pattern DATE_YEAR_PATTERN = Pattern.compile("^(19|20)\\d{2}$");
	private static Pattern LETTER_PATTERN = Pattern.compile("[a-zA-Z]+");
	
	// #ROMACAPITALE inizio
	private boolean pannelloProtocolloVisibile;
	private boolean pannelloMessaggioProtocollaDocVisibile;
	private boolean btnVerificaProtocolloVisibile;
	private boolean colonnaProtocolloVisibile;
	private String testoMessaggioProtocollo;
	private boolean solaLettura;
	// #ROMACAPITALE fine

	@PostConstruct
	public void init() {

		if (csASoggetto == null)
			csASoggetto = (CsASoggettoLAZY) getSession().getAttribute("soggetto");

		if (csASoggetto != null)
			this.initialize(csASoggetto);
	}

	public void initializeDocIndividuali(Long anagraficaId){
		CsASoggettoLAZY s = getSoggettoById(anagraficaId);
		initializeDocIndividuali(s);
	}
	
	public void initializeDocIndividuali(CsASoggettoLAZY soggetto) {

		if (soggetto != null) {
			getSession().setAttribute("soggetto", soggetto);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("docIndividuali.faces");
			} catch (IOException e) {
				addError("Errore", "Errore durante il reindirizzamento ai Documenti Individuali");
			}
		} else
			addWarningFromProperties("seleziona.warning");
	}

	@Override
	public void initializeData() {

		try {

			boolean permessoDown = checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_DOC_INDIVIDUALI_DOWN);
			CsOOperatoreSettore opSettore = getCurrentOpSettore();
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);

			listaSottocartelle = new ArrayList<CsTbSottocartellaDoc>();
			listaSottocartelle = confService.getTipoCartelle(dto);

			dto.setObj(idCaso);
			dto.setObj2(permessoDown);
			dto.setObj3(opSettore.getId());
			dto.setObj4(TipoVisibilitaDocumento.PRIVATO);
			listaDocIndividualiPrivata = diarioService.findDocIndividualiByCaso(dto);
			
			dto.setObj4(TipoVisibilitaDocumento.PUBBLICO);
			listaDocIndividualiPubblica = diarioService.findDocIndividualiByCaso(dto);
		
			// inizio SISO-438
			listaDocIndividualiRichiestaServizio = new ArrayList<DocIndividualeBean>();
			if (csASoggetto != null && csASoggetto.getCsAAnagrafica() != null && opSettore!=null) {
				dto.setObj(csASoggetto.getCsAAnagrafica().getCf());
				dto.setObj4(opSettore.getCsOSettore().getCsOOrganizzazione().getId());
				listaDocIndividualiRichiestaServizio = diarioService.findDocIndividualeByCfSchedaSegnalato(dto);
			}
			// fine SISO-438

			diarioDocsMan = new DiarioDocsMan();
			
			// #ROMACAPITALE
			colonnaProtocolloVisibile = getDocIndividualiProtocolloVisibile();
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	protected void initializeData(Object data) {
		this.initializeData();
	}

	@Override
	public void nuovo() {

		modalHeader = "Nuovo Documento Individuale";
		docIndividuale = new CsDDocIndividuale();
		docIndividuale.setPrivato(false);

		diarioDocsMan = new DiarioDocsMan();
		diarioDocsMan.getuFileMan().setExternalSave(true);
		disableUpload = false;

		// #ROMACAPITALE
		pannelloProtocolloVisibile = false;
		pannelloMessaggioProtocollaDocVisibile = false;
		solaLettura = false;
	}

	@Override
	public void caricaPubblico() {
		modalHeader = "Modifica Documento Individuale";
		docIndividuale = listaDocIndividualiPubblica.get(idxSelected).getCsDDocIndividuale();
		disableUpload = true;

		// #ROMACAPITALE: gestione tipologia del documento con "protollo" o "protocollodasigess" a true
		gestioneVisibilitaPannelliMessaggiProtocollo();
	}
	
	@Override
	public void caricaPrivato() {
		modalHeader = "Modifica Documento Individuale";
		docIndividuale = listaDocIndividualiPrivata.get(idxSelected).getCsDDocIndividuale();
		disableUpload = true;

		// #ROMACAPITALE: gestione tipologia del documento con "protollo" o "protocollodasigess" a true
		gestioneVisibilitaPannelliMessaggiProtocollo();
	}

	@Override
	public void indicaLettura() {

		try {

			docIndividuale = listaDocIndividualiPubblica.get(idxSelected).getCsDDocIndividuale();
			docIndividuale.setLetto(true);

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(docIndividuale);
			diarioService.updateDocIndividuale(dto);

			initializeData();

		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public String getDenominazioneOperatore(CsDDiario d) {

		try {
			String username = d.getUsrMod() != null ? d.getUsrMod() : d.getUserIns();
			return CsUiCompBaseBean.getCognomeNomeUtente(username);

		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
		return "";
	}
	
	@Override
	protected void save() {
		String errorMessageGED = null;
		try {

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(docIndividuale);
			
			if (docIndividuale.getDiarioId() != null) {

				// modifica
				
				// #ROMACAPITALE
				if (docIndividuale.getCsTbSottocartellaDoc().getProtocollo()) {
					List<String> msg = validaProtocollo(true);
					if (msg != null && msg.size() > 0) {
						return;
					}
				}

				docIndividuale.getCsDDiario().setUsrMod(dto.getUserId());
				docIndividuale.getCsDDiario().setDtMod(new Date());
				this.valorizzaSecondoLivello(docIndividuale.getCsDDiario());

				diarioService.updateDocIndividuale(dto);

				errorMessageGED = callGED(getDocumentFromDocIndividualeId(), false);
			} else {

				// nuovo
				if (!validaDocIndividuale()) {
					return;
				}

				BaseDTO casodto = new BaseDTO();
				fillEnte(casodto);
				casodto.setObj(idCaso);
				DatiOperatoreDTO responsabile = casoService.findResponsabileCaso(casodto);
				CsOOperatoreSettore opSettoreFrom = getCurrentOpSettore();
				CsACaso csACaso = casoService.findCasoById(casodto);

				CsTbTipoDiario cstd = new CsTbTipoDiario();
				cstd.setId(new Long(DataModelCostanti.TipoDiario.DOC_INDIVIDUALE_ID));

				docIndividuale.getCsDDiario().setCsACaso(csACaso);
				docIndividuale.getCsDDiario().setCsTbTipoDiario(cstd);
				docIndividuale.getCsDDiario().setDtIns(new Date());
				docIndividuale.getCsDDiario().setUserIns(dto.getUserId());
				docIndividuale.getCsDDiario().setResponsabileCaso(responsabile != null ? responsabile.getId(): null);
				docIndividuale.getCsDDiario().setCsOOperatoreSettore(opSettoreFrom);
				this.valorizzaSecondoLivello(docIndividuale.getCsDDiario());

				// Valorizzo le date Applicative

				docIndividuale.setLetto(false);
				CsDDiario csd = diarioService.saveDocIndividuale(dto);

				docIndividuale.setDiarioId(csd.getId());
				docIndividuale.setCsDDiario(csd);
				
				// salvo il documento
				diarioDocsMan.getuFileMan().setIdDiario(csd.getId());
				List<CsLoadDocumento> listaDocumenti = diarioDocsMan.getuFileMan().getDocumentiUploaded();
				for (CsLoadDocumento loadDoc : listaDocumenti)
					diarioDocsMan.getuFileMan().salvaDocumento(loadDoc);

				// notifica al responsabile caso
				if (!docIndividuale.getPrivato()) {

					BaseDTO bdto = new BaseDTO();
					fillEnte(bdto);
					bdto.setObj(csACaso.getCsASoggetto());
					bdto.setObj2(opSettoreFrom);
					bdto.setObj3(DataModelCostanti.TipiAlertCod.DOC_IND);
					bdto.setObj4("una nuovo documento individuale");
					
					alertService.addAlertNuovoInserimentoToResponsabileCaso(bdto);
				}
				
				errorMessageGED = callGED(listaDocumenti.get(0), false);
			}
			initializeData();
			
			addInfoFromProperties("salva.ok");
			if (errorMessageGED != null) {
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Attenzione", errorMessageGED));
			}
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}

	}

	@Override
	public void elimina() {

		try {

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(docIndividuale);
			diarioService.deleteDocIndividuale(dto);

			initializeData();

			addInfoFromProperties("elimina.ok");

		} catch (Exception e) {
			addErrorFromProperties("elimina.error");
			logger.error(e.getMessage(), e);
		}

	}

	private boolean validaDocIndividuale() {

		boolean ok = true;
		List<String> msg = new ArrayList<String>();
		List<CsLoadDocumento> lstDocs = diarioDocsMan.getuFileMan().getDocumentiUploaded();
		if (lstDocs.isEmpty()) {
			ok = false;
			msg.add("Aggiungere un documento");
		} else {
			// Verifico che siano stati correttamente caricati
			for (CsLoadDocumento cs : lstDocs) {
				if (cs.getDocumento() == null) {
					ok = false;
					msg.add("Documento non caricato o formato non valido");
				}
			}
		}

		if (docIndividuale.getDescrizione() == null || "".equals(docIndividuale.getDescrizione())) {
			ok = false;
			msg.add("Descrizione è un campo obbligatorio");
		}

		if (docIndividuale.getCsDDiario().getDtAmministrativa() == null) {
			ok = false;
			msg.add("Data è un campo obbligatorio");
		}

		if (docIndividuale.getCsTbSottocartellaDoc() == null) {
			ok = false;
			msg.add("Tipo documento è un campo obbligatorio");
		}

		// #ROMACAPITALE
		List<String> msgProtocollo = validaProtocollo(false);

		if (msgProtocollo != null && msgProtocollo.size() > 0) {
			ok = false;
			for (String m : msgProtocollo) {
				msg.add(m);
			}
		}

		this.addWarning("Documenti Individuali", msg);

		return ok;
	}

	@Override
	public Long getIdCaso() {
		return idCaso;
	}

	@Override
	public void setIdCaso(Long idCaso) {
		this.idCaso = idCaso;
	}

	@Override
	public int getIdxSelected() {
		return idxSelected;
	}

	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;
	}

	@Override
	public CsDDocIndividuale getDocIndividuale() {
		return docIndividuale;
	}

	public void setDocIndividuale(CsDDocIndividuale docIndividuale) {
		this.docIndividuale = docIndividuale;
	}

	@Override
	public DiarioDocsMan getDiarioDocsMan() {
		return diarioDocsMan;
	}

	public void setDiarioDocsMan(DiarioDocsMan diarioDocsMan) {
		this.diarioDocsMan = diarioDocsMan;
	}

	@Override
	public String getModalHeader() {
		return modalHeader;
	}

	public void setModalHeader(String modalHeader) {
		this.modalHeader = modalHeader;
	}

	@Override
	public List<DocIndividualeBean> getListaDocIndividualiPubblica() {
		return listaDocIndividualiPubblica;
	}

	public void setListaDocIndividualiPubblica(List<DocIndividualeBean> listaDocIndividualiPubblica) {
		this.listaDocIndividualiPubblica = listaDocIndividualiPubblica;
	}

	@Override
	public List<DocIndividualeBean> getListaDocIndividualiPrivata() {
		return listaDocIndividualiPrivata;
	}

	public void setListaDocIndividualiPrivata(List<DocIndividualeBean> listaDocIndividualiPrivata) {
		this.listaDocIndividualiPrivata = listaDocIndividualiPrivata;
	}

	public List<DocIndividualeBean> getListaDocIndividualiRichiestaServizio() {
		return listaDocIndividualiRichiestaServizio;
	}

	public void setListaDocIndividualiRichiestaServizio(List<DocIndividualeBean> listaDocIndividualiRichiestaServizio) {
		this.listaDocIndividualiRichiestaServizio = listaDocIndividualiRichiestaServizio;
	}

	@Override
	public boolean isDisableUpload() {
		return disableUpload;
	}

	public void setDisableUpload(boolean disableUpload) {
		this.disableUpload = disableUpload;
	}

	public List<CsTbSottocartellaDoc> getListaSottocartelle() {
		return listaSottocartelle;
	}

	@Override
	public List<SelectItem> getListaSottocartelleItem() {
		List<SelectItem> sil = new ArrayList<SelectItem>();
		for (CsTbSottocartellaDoc c : listaSottocartelle) {
			SelectItem si = new SelectItem();
			si.setLabel(c.getDescrizione());
			si.setValue(c.getId());
			si.setDisabled(!c.getAbilitato());
			sil.add(si);
		}
		return sil;
	}

	public Long getIdSottocartella() {		
		return (this.docIndividuale != null && this.docIndividuale.getCsTbSottocartellaDoc() != null) ? docIndividuale.getCsTbSottocartellaDoc().getId()
				: null;
	}

	public void setIdSottocartella(Long idSottocartella) {
		if (this.listaSottocartelle != null) {
			int i = 0;
			boolean trovato = false;
			while (i < listaSottocartelle.size() && !trovato) {
				CsTbSottocartellaDoc c = listaSottocartelle.get(i);
				if (c.getId().equals(idSottocartella)) {
					this.docIndividuale.setCsTbSottocartellaDoc(c);
					trovato = true;
				}
				i++;
			}
		}
	}

	// #ROMACAPITALE inizio
	@Override
	public boolean isPannelloProtocolloVisibile() {
		return pannelloProtocolloVisibile;
	}

	public void setPannelloProtocolloVisibile(boolean pannelloProtocolloVisibile) {
		this.pannelloProtocolloVisibile = pannelloProtocolloVisibile;
	}

	public boolean isPannelloMessaggioProtocollaDocVisibile() {
		return pannelloMessaggioProtocollaDocVisibile;
	}

	public void setPannelloMessaggioProtocollaDocVisibile(boolean pannelloMessaggioProtocollaDocVisibile) {
		this.pannelloMessaggioProtocollaDocVisibile = pannelloMessaggioProtocollaDocVisibile;
	}

	public boolean isBtnVerificaProtocolloVisibile() {
		return btnVerificaProtocolloVisibile;
	}

	public void setBtnVerificaProtocolloVisibile(boolean btnVerificaProtocolloVisibile) {
		this.btnVerificaProtocolloVisibile = btnVerificaProtocolloVisibile;
	}
	
	public boolean isColonnaProtocolloVisibile() {
		return colonnaProtocolloVisibile;		
	}

	public void setColonnaProtocolloVisibile(boolean colonnaProtocolloVisibile) {
		this.colonnaProtocolloVisibile = colonnaProtocolloVisibile;
	}
	
	public String getTestoMessaggioProtocollo() {
		return testoMessaggioProtocollo;
	}

	public void setTestoMessaggioProtocollo(String testoMessaggioProtocollo) {
		this.testoMessaggioProtocollo = testoMessaggioProtocollo;
	}

	public boolean isSolaLettura() {
		return solaLettura;
	}

	public void setSolaLettura(boolean solaLettura) {
		this.solaLettura = solaLettura;
	}

	public void onTipoDocumentiChange(AjaxBehaviorEvent event) {
		pannelloProtocolloVisibile = false;
		pannelloMessaggioProtocollaDocVisibile = false;
		solaLettura = false;
		
		if (this.docIndividuale.getCsTbSottocartellaDoc() != null)
		{
			//se la tipologia del documento ha il campo protollo a true si visualizza il pannello di dettaglio
			//se la tipologia del documento ha il campo protollo_da_sigess a true si visualizza il messaggio di alert
			if (this.docIndividuale.getCsTbSottocartellaDoc().getProtocollo() != null && this.docIndividuale.getCsTbSottocartellaDoc().getProtocollo())
			{
				pannelloProtocolloVisibile = true;
				pannelloMessaggioProtocollaDocVisibile = false;
			}
			else if (this.docIndividuale.getCsTbSottocartellaDoc().getProtocolloDaSigess() != null && this.docIndividuale.getCsTbSottocartellaDoc().getProtocolloDaSigess())
			{
				pannelloProtocolloVisibile = false;
				pannelloMessaggioProtocollaDocVisibile = true;
				testoMessaggioProtocollo = "ATTENZIONE: Per questa tipologia di documento, al salvataggio si procederà anche alla protocollazione del documento nel sistema GED.";
			}
			else
			{
				pannelloProtocolloVisibile = false;
				pannelloMessaggioProtocollaDocVisibile = false;
			}
		}
	}

	private void gestioneVisibilitaPannelliMessaggiProtocollo() {
		if ((this.docIndividuale.getCsTbSottocartellaDoc().getProtocollo() != null && this.docIndividuale.getCsTbSottocartellaDoc().getProtocollo())) {
			pannelloProtocolloVisibile = true;
			pannelloMessaggioProtocollaDocVisibile = false;
			solaLettura = false;
		}else if (this.docIndividuale.getCsTbSottocartellaDoc().getProtocolloDaSigess() != null && this.docIndividuale.getCsTbSottocartellaDoc().getProtocolloDaSigess()) {
			//nessuna chiamata al ged è andata a buon fine
			if("".equals(this.docIndividuale.getProtocolloStringaConcatenata()) && (this.docIndividuale.getNumeroAllegato() == null || !"".equals(this.docIndividuale.getNumeroAllegato()))) {
				pannelloProtocolloVisibile = false;
				pannelloMessaggioProtocollaDocVisibile = true;
				testoMessaggioProtocollo = "ATTENZIONE: Non è stato possibile protocollare questo documento per mancata risposta del GED. Cliccando Salva verrà effettuato un nuovo tentativo.";
				solaLettura = true;
			}
			//prima chiamata al ged è andata a buon fine ma la seconda no
			else if(!"".equals(this.docIndividuale.getProtocolloStringaConcatenata()) && (this.docIndividuale.getNumeroAllegato() == null || "".equals(this.docIndividuale.getNumeroAllegato()))) {
				pannelloProtocolloVisibile = true;
				pannelloMessaggioProtocollaDocVisibile = true;
				testoMessaggioProtocollo = "ATTENZIONE: Per questo documento è stato generato il protocollo ma non è stato possibile concludere la procedura di protocollazione per mancata risposta del GED. Cliccando Salva verrà effettuato un nuovo tentativo.";
				solaLettura = true;
			}
			else {
				pannelloProtocolloVisibile = true;
				pannelloMessaggioProtocollaDocVisibile = false;
				solaLettura = true;
			}
		}
		else {
			pannelloProtocolloVisibile = false;
			pannelloMessaggioProtocollaDocVisibile = false;
			solaLettura = false;
		}
	}
	
	private List<String> validaProtocollo(Boolean visualizzaErrori) {

		List<String> msg = new ArrayList<String>();

		if (pannelloProtocolloVisibile && docIndividuale.getCsTbSottocartellaDoc().getProtocollo()) {
			// se ho inserito qualcosa in uno dei tre campi proseguo con il controllo formale
			if ((docIndividuale.getDipartimentoProtocollo() != null && !"".equals(docIndividuale.getDipartimentoProtocollo()))
					|| (docIndividuale.getNumeroProtocollo() != null && !"".equals(docIndividuale.getNumeroProtocollo()))
					|| (docIndividuale.getAnnoProtocollo() != null && !"".equals(docIndividuale.getAnnoProtocollo()))) {

				// validazione Dipartimento/Struttura Protocollo
				if (docIndividuale.getDipartimentoProtocollo() == null
						|| "".equals(docIndividuale.getDipartimentoProtocollo())
						|| docIndividuale.getDipartimentoProtocollo().length() != 2) {
					msg.add("Il Protocollo non è corretto: il campo Struttura deve essere di 2 caratteri.");
				} else if(!isOnlyLetter(docIndividuale.getDipartimentoProtocollo())) {
					msg.add("Il Protocollo non è corretto: il campo Struttura deve essere composto solo da lettere.");
				} 
				
				// validazione anno protocollo
				if (docIndividuale.getAnnoProtocollo() == null || "".equals(docIndividuale.getAnnoProtocollo())
						|| docIndividuale.getAnnoProtocollo().length() != 4) {
					msg.add("Il Protocollo non è corretto: il campo Anno deve essere di 4 caratteri.");
				} else if(!isNumeric(docIndividuale.getAnnoProtocollo())) {
					msg.add("Il Protocollo non è corretto: il campo Anno protocollo deve essere composto solo da numeri.");
				} else if (!isValidYear(docIndividuale.getAnnoProtocollo())) {
					msg.add("Il Protocollo non è corretto: il campo Anno protocollo non è valido come anno.");
				}

				// validazione numero protocollo
				if (docIndividuale.getNumeroProtocollo() == null || "".equals(docIndividuale.getNumeroProtocollo())
						|| docIndividuale.getNumeroProtocollo().length() < 1
								&& docIndividuale.getNumeroProtocollo().length() > 6) {
					msg.add("Il Protocollo non è corretto: il campo Numero protocollo deve essere tra 1 e 6 caratteri.");
				} else if (!isNumeric(docIndividuale.getNumeroProtocollo())) {
					msg.add("Il Protocollo non è corretto: il campo Numero protocollo deve essere composto solo da numeri.");
				}

			} else {
				msg.add("Non è possibile verificare l'esistenza del protocollo in GED perchè tutti i campi devono essere valorizzati.");
			}
		} else {
			docIndividuale.setDipartimentoProtocollo(null);
			docIndividuale.setNumeroProtocollo(null);
			docIndividuale.setAnnoProtocollo(null);
		}

		if (visualizzaErrori && !msg.isEmpty()) {
			addMessage("pnlProtocollo:msgProtocollo", "Documenti Individuali", StringUtils.join(msg, "<br/>"),
					FacesMessage.SEVERITY_WARN);
		}

		return msg;
	}

	//Chiamata al GED per controllo esistenza del numero di protocollo inserito
	@Override
	public void verificaEsistenzaNumeroProtocollo() {
		logger.warn("Valido la correttezza del numero di protocollo inserito");

		List<String> msgProtocollo = validaProtocollo(true);

		if (msgProtocollo != null && msgProtocollo.size() > 0) {
			return;
		}
		
		try {			
			logger.warn("Instanzio GedRomaClient");
			GedRomaClient gedRomaClient = new GedRomaClient();
			
			String codiceDocumentoGED = getCodiceDocumentoGED();
			
			logger.warn("GedRomaClient: NumeroProtocolloGED.CreaNumeroProtocollo");
			NumeroProtocolloGED numProtocollo = NumeroProtocolloGED.CreaNumeroProtocollo(
					docIndividuale.getDipartimentoProtocollo(), Integer.parseInt(docIndividuale.getAnnoProtocollo()),
					Integer.parseInt(docIndividuale.getNumeroProtocollo()));
			
			//codice fiscale utente collegato
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			String cfUtenteCollegato = getCurrentOpSettore().getCsOOperatore().getCsOOperatoreAnagrafica().getCodiceFiscale();
			
			if(!StringUtils.isBlank(cfUtenteCollegato))
			{
				//if (gedRomaClient.ricercaProtocolloSingolo(codiceDocumentoGED, numProtocollo, cfUtenteCollegato)) {
				//	// found
				//	addMessage("pnlProtocollo:msgProtocollo", "Documenti Individuali",
				//			"Il numero di protocollo inserito risulta presente nel GED", FacesMessage.SEVERITY_INFO);
				//} else {
				//	// not found
				//	addMessage("pnlProtocollo:msgProtocollo", "Documenti Individuali",
				//			"Il numero di protocollo inserito non risulta presente nel GED", FacesMessage.SEVERITY_WARN);
				//}
				
				String msg = gedRomaClient.ricercaProtocolloSingolo(codiceDocumentoGED, numProtocollo, cfUtenteCollegato);
				addMessage("pnlProtocollo:msgProtocollo", "Documenti Individuali", msg, FacesMessage.SEVERITY_INFO);				
			}
			else
			{
				addMessage("pnlProtocollo:msgProtocollo", "Documenti Individuali",
						"Il codice fiscale dell'operatore non è censito correttamente", FacesMessage.SEVERITY_WARN);
			}
		} catch (GedRomaException grx) {
			addMessage("pnlProtocollo:msgProtocollo", "Documenti Individuali",
					"Non è possibile verificare l’esistenza del numero protocollo a causa del seguente errore: "+ grx.getMessage(),
					FacesMessage.SEVERITY_WARN);
		} catch (Exception e) {
			addMessage("pnlProtocollo:msgProtocollo", "Documenti Individuali",
					"Al momento non è possibile verificare l’esistenza del numero protocollo per mancata risposta del GED",
					FacesMessage.SEVERITY_WARN);
			logger.error(e.getMessage(), e);
		}
	}

	private String getCodiceDocumentoGED() {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);

		//EMAIL Del 15 febbraio 2021 di Progetti Distribuiti
		//Esiste un Codice GED per Municipio e non più legato alla tipologia di documento
		//quindi commentiamo "id tipologia documento" e passiamo solo il COD_ROUTING
		
		// id tipologia documento
//		if (this.docIndividuale.getCsTbSottocartellaDoc().getId() != null) {
//			dto.setObj(this.docIndividuale.getCsTbSottocartellaDoc().getId());
//		}
		
		// cod routing dell'organizzazione dell'utente in sessione
		if (dto.getEnteId() != null) {
			dto.setObj(dto.getEnteId());
		}

		String codiceGED = confService.findCodiceDocumentoGed(dto);
		logger.warn("GedRomaClient: findCodiceDocumentoGed " + codiceGED);
		return codiceGED;
	}

	public void staccaProtocollo_docPubblico() {
		try {

			docIndividuale = listaDocIndividualiPubblica.get(idxSelected).getCsDDocIndividuale();
			String errorMessageGED = callGED(getDocumentFromDocIndividualeId(), true);
			if (errorMessageGED == null) {
				addInfoFromProperties("salva.ok");
			} else {
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Attenzione", errorMessageGED));
			}
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
	}
	
	public void staccaProtocollo_docPrivato() {
		try {

			docIndividuale = listaDocIndividualiPrivata.get(idxSelected).getCsDDocIndividuale();
			String errorMessageGED = callGED(getDocumentFromDocIndividualeId(), true);
			if (errorMessageGED == null) {
				addInfoFromProperties("salva.ok");
			} else {
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Attenzione", errorMessageGED));
			}
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
	}

	private CsLoadDocumento getDocumentFromDocIndividualeId() {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(docIndividuale.getCsDDiario().getId());
		return diarioService.findDiarioDocById(dto).get(0).getCsLoadDocumento();
	}
	
	private void aggiornaDocumento() throws Exception {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(docIndividuale);
		diarioService.updateDocIndividuale(dto);
	}
	
	public String callGED(CsLoadDocumento csLoadDocumento, boolean toRefreshData) throws Exception {
		String errorMessageGED = null;

		/*
		 * dopo aver salvato il documento, si fanno le chiamate GED per staccare il
		 * protocollo e allegare l'allegato la prima chiamata viene fatta se campo
		 * ProtocolloDaSigess è true, protocollo null
		 */
		if (docIndividuale.getCsTbSottocartellaDoc().getProtocolloDaSigess() && docIndividuale.getNumeroProtocollo() == null) {
			errorMessageGED = richiediNumeroProtocollo();
			if (errorMessageGED == null) {
				logger.info("[GED ROMA] Protocollo creato correttamente");
				aggiornaDocumento();
			} else {
				return errorMessageGED;
			}

		}

		/*
		 * la seconda chiamata viene fatta se campo ProtocolloDaSigess è true,
		 * protocollo valorizzato e numero allegato null (ricevuta del ticket staccato
		 * dal GED)
		 */		
		if (docIndividuale.getCsTbSottocartellaDoc().getProtocolloDaSigess()
				&& docIndividuale.getNumeroProtocollo() != null
				&& (docIndividuale.getNumeroAllegato() == null || "".equals(docIndividuale.getNumeroAllegato()))) {
			errorMessageGED = allegaDocumentoProtocollo(csLoadDocumento);
			if (errorMessageGED == null) {
				aggiornaDocumento();
			}
		}
		
		if (toRefreshData && errorMessageGED == null) initializeData();

		return errorMessageGED;
	}
		
	public String richiediNumeroProtocollo() {
		logger.info("[GED ROMA] Richiedo un nuovo protocollo");

		String codiceDocumentoGED = getCodiceDocumentoGED();
		String testoOggetto = docIndividuale.getCsTbSottocartellaDoc().getDescrizione(); //tipologia documento
		String noteDocumento = docIndividuale.getDescrizione();		
		String descrizione = csASoggetto.getCsAAnagrafica().getCognome() + "/" + csASoggetto.getCsAAnagrafica().getNome(); 
		String codiceFiscale = csASoggetto.getCsAAnagrafica().getCf();
		
		NominativoGED nominativoMittente = NominativoGED.creaNominativoGED(descrizione, codiceFiscale);
		String result = null;
				
		TempCodFiscManager tempMan = (TempCodFiscManager) getReferencedBean("tempCodFiscManager");
		try {
			if (tempMan.isTemporaneo(codiceFiscale)) {
				result = "Per la protocollazione, il codice fiscale non deve essere temporaneo.";
			} else {
				try {
					GedRomaClient gedRomaClient = new GedRomaClient();

					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					Long timestampGetTime = timestamp.getTime();
					// usiamo timeStamp per creare un identificativo univoco
					String idUnivocoProceduraChiamante = "SIGESS-" + timestampGetTime.toString();

					NumeroProtocolloGED numeroProtocolloGED = gedRomaClient.protocollazioneMittenteEsterno(
							codiceDocumentoGED, testoOggetto, noteDocumento, nominativoMittente,
							idUnivocoProceduraChiamante);

					// set input fields
					docIndividuale.setAnnoProtocollo(numeroProtocolloGED.getAnnoProtocollo() + "");
					docIndividuale.setNumeroProtocollo(numeroProtocolloGED.getNumeroProgressivoProtocollo() + "");
					docIndividuale.setDipartimentoProtocollo(numeroProtocolloGED.getTipoProtocollo());
					docIndividuale.setIdUnivocoProceduraChiamante(idUnivocoProceduraChiamante);
				} catch (Exception e) {
					result = "Al momento non è possibile protocollare per mancata risposta del GED";
					logger.error("[GED ROMA] Al momento non è possibile protocollare per mancata risposta del GED", e);
				}
			}
		} catch (Exception e) {
			logger.error("Errore verifica codice fiscale temporaneo", e);
		}

		return result;
	}
	
	private String allegaDocumentoProtocollo(CsLoadDocumento csLoadDocumento) {

		NumeroProtocolloGED numeroProtocollo = NumeroProtocolloGED.CreaNumeroProtocollo(
				docIndividuale.getDipartimentoProtocollo(), Integer.parseInt(docIndividuale.getAnnoProtocollo()),
				Integer.parseInt(docIndividuale.getNumeroProtocollo()));
		String codiceDocumento = getCodiceDocumentoGED();
		AllegatoProtocolloGED allegato = AllegatoProtocolloGED.CreaAllegato(csLoadDocumento.getNome(), csLoadDocumento.getNome(), csLoadDocumento.getDocumento());
		String codiceFiscale_Cittadino = csASoggetto.getCsAAnagrafica().getCf();
		String result = null;
		try {
			String numeroAllegato = new GedRomaClient().inserisciAllegatoInProtocolloEsistente(numeroProtocollo,
					codiceDocumento, allegato, codiceFiscale_Cittadino);
			docIndividuale.setNumeroAllegato(numeroAllegato);
			logger.info("[GED ROMA] Protocollo creato correttamente");
		} catch (Exception e) {
			result = "Il protocollo è stato generato, ma al momento non è possibile allegare il documento per mancata risposta del GED";
			logger.error("[GED ROMA] Il protocollo è stato generato, ma al momento non è possibile allegare il documento per mancata risposta del GED", e);
		}
		return result;

	}

	public void stampaRicevutaGEDPubblico(int index) {
		try {
			
			docIndividuale = listaDocIndividualiPubblica.get(index).getCsDDocIndividuale();			
			
			creaRicevutaProtocollo_Download();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void stampaRicevutaGEDPrivato(int index) {

		try {

			docIndividuale = listaDocIndividualiPrivata.get(index).getCsDDocIndividuale();			
			
			creaRicevutaProtocollo_Download();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void creaRicevutaProtocollo_Download() throws IOException {
		
		String dirTemplate = "/template/siso/templateRicevutaProtocolloGED.txt";
		
		String dir = this.getDirDatiDiogene();
		if(!StringUtils.isBlank(dir)) {
			String pathCompleto = dir + dirTemplate;
			
			File fileTemplate = new File(pathCompleto);
			if(fileTemplate.exists())
			{
				// leggo il template della ricevuta
				String template = new String(Files.readAllBytes(Paths.get(pathCompleto)));
	
				Map<String, String> placeholders = new HashMap<String, String>();
				placeholders.put("cognome", csASoggetto.getCsAAnagrafica().getCognome());
				placeholders.put("nome", csASoggetto.getCsAAnagrafica().getNome());
				placeholders.put("codiceFiscale", csASoggetto.getCsAAnagrafica().getCf());
				placeholders.put("tipoDocumento", docIndividuale.getCsTbSottocartellaDoc().getDescrizione());
				placeholders.put("protocollo", docIndividuale.getProtocolloStringaConcatenata());
				placeholders.put("numeroAllegato", docIndividuale.getNumeroAllegato());				
				placeholders.put("data", new SimpleDateFormat("dd-MM-yyyy").format(docIndividuale.getCsDDiario().getDtAmministrativa()));
				placeholders.put("municipioDestinatario", docIndividuale.getCsDDiario().getCsOOperatoreSettore().getCsOSettore().getCsOOrganizzazione().getNome());
				
				String replaceTemplate = StrSubstitutor.replace(template, placeholders);			
				byte[] exportContent = replaceTemplate.getBytes();
				
				// scateno la response con il download del file creato
				FacesContext fc = FacesContext.getCurrentInstance();
				ExternalContext ec = fc.getExternalContext();
				
				String fileOutput = "ricevutaGED.txt";
				
				ec.responseReset();
				ec.setResponseContentType("text/plain");
				ec.setResponseContentLength(exportContent.length);
				ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileOutput + "\"");
				
				OutputStream output = null;
				try {
				    output = ec.getResponseOutputStream();
				    Streams.copy(new ByteArrayInputStream(exportContent), output, false);
				} catch (IOException ex) {
					logger.error(ex.getMessage(), ex);
				}
				
				fc.responseComplete();
			}
			else
			{
				String msg = "Attenzione: Il template della ricevuta di protocollo non è presente";
				logger.error(msg);
				addMessage("pnlProtocollo:msgProtocollo", "Documenti Individuali", StringUtils.join(msg, "<br/>"),
						FacesMessage.SEVERITY_WARN);
			}
		} else {
			String msg = "Attenzione: Il path per il recupero del template della ricevuta di protocollo non è impostato";
			logger.error(msg);
			addMessage("pnlProtocollo:msgProtocollo", "Documenti Individuali", StringUtils.join(msg, "<br/>"),
					FacesMessage.SEVERITY_WARN);
		}
	}
	
	private boolean isOnlyLetter(String str) {
		if (str == null) {
			return false;
		}
		return LETTER_PATTERN.matcher(str).matches();
	}
	
	private boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		return NUMERIC_PATTERN.matcher(strNum).matches();
	}
	
	private boolean isValidYear(String year) {
		if (year == null) {
			return false;
		}
		return DATE_YEAR_PATTERN.matcher(year).matches();
	}
	// #ROMACAPITALE fine
	
	@Override
	public String getIntestazione(){
		String denom = this.csASoggetto!=null ? this.csASoggetto.getCsAAnagrafica().getDenominazione() : "";
		String identificativo = this.csASoggetto!=null ? this.csASoggetto.getCsACaso().getIdentificativo().toString() : "";
		String tit = "Caso "+denom+ " [ID: "+identificativo+"]";
		return tit;
	}
}
