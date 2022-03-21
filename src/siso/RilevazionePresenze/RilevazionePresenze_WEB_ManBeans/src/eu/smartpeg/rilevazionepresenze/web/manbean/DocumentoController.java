package eu.smartpeg.rilevazionepresenze.web.manbean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.jboss.logging.Logger;

import eu.smartpeg.rilevazionepresenze.AnagraficaSessionBeanRemote;
import eu.smartpeg.rilevazionepresenze.data.model.Anagrafica;
import eu.smartpeg.rilevazionepresenze.data.model.DocumentiAnag;
import eu.smartpeg.rilevazionepresenze.web.dto.DocumentoAnagraficaDTO;

@javax.faces.bean.ManagedBean
@ViewScoped
public class DocumentoController {

	public static Logger logger = Logger.getLogger("rilevazionepresenze.log");

	private DocumentiAnag selectedDocumento;
	private Anagrafica selectedAngrafica;
	private Date nuovoDocumentoDataScadenza;
	private String nuovoDocumentoIdTipologiaDocumento;
	private String nuovoDocumentoNumeroDocumento;

	@EJB
	private AnagraficaSessionBeanRemote anagraficaEjb;

	public DocumentoController() {
		super();
		logger.debug("COSTRUTTORE CHIAMATO");
	}

	@PostConstruct
	private void init() {
		logger.debug("@PostConstruct init CHIAMATO");
		selectedDocumento = new DocumentiAnag();
	}

	public void caricaDocumentiDaAngrafica(Anagrafica anagrafica) {
		init();
		if (anagrafica == null || anagrafica.getArRpDocumentiAnags() == null) {
			return;
		}
		setSelectedAngrafica(anagrafica);
	}

	private void addMessage(FacesMessage.Severity tipoMessaggio, String summary, String messaggio) {
		FacesMessage message = new FacesMessage(tipoMessaggio, summary, messaggio);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public DocumentiAnag getSelectedDocumento() {
		return this.selectedDocumento;
	}

	public void setSelectedDocumento(DocumentiAnag documento) {
		this.selectedDocumento = documento;
	}

	public void onNumeroDocumentoChange(ValueChangeEvent event) {

		String newValue = (String) event.getNewValue();

		this.selectedDocumento.setNumeroDocumento(newValue);
		logger.debug("selected Documento Numero : " + this.selectedDocumento.getNumeroDocumento());
	}

	public void onDataScadenzaChange(ValueChangeEvent event) {
		Date newValue = (Date) event.getNewValue();
		this.selectedDocumento.setDataScadenza(newValue);
		logger.info("onDataScadenzaChange: data scad. documento " + this.selectedDocumento.getDataScadenza());
	}

	public void onTipoDocumentoChange(ValueChangeEvent event) {

		long newValue = Long.valueOf((String) event.getNewValue());
		logger.debug("ValueChangeEvent:  " + newValue);

		this.selectedDocumento.setIdTipologiaDocumento(newValue);

		logger.debug("selected Tipo Documento: ");
	}

	/**
	 * Aggiungi documento all'anagrafica
	 */
	public void aggiungiDocumento() {
		// TODO: verifica duplicato
		logger.info("aggiungiDocumento");
		try {

			DocumentiAnag nuovoDocumento = DocumentiAnag.createDocument(getNuovoDocumentoDataScadenza(), 
					getNuovoDocumentoIdTipologiaDocumento(), 
					getNuovoDocumentoNumeroDocumento());

			if (nuovoDocumento.getValidationMessages().isEmpty()) {
				getSelectedAngrafica().addArRpDocumentiAnag(nuovoDocumento);
				logger.debug("documento aggiunto (non ancora salvato su DB) ad entity anagrafica");
			} else {
				for (String msg : nuovoDocumento.getValidationMessages()) {
					addMessage(FacesMessage.SEVERITY_WARN, "Documento non valido", msg);
				}
			}
			
		} catch (Exception e) {
			logger.error("Errore nell'inserimento del documento", e);
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore nell'aggiunta del documento", e.getMessage());
		}
	}

	public void eliminaDocumento() {
		DocumentiAnag documento = this.getSelectedDocumento();
		// anagraficaEjb.eliminaDocumento(documento.getId());
		this.getElencoDocumenti().remove(documento);
	}

	public Set<DocumentiAnag> getElencoDocumenti() {
		return getSelectedAngrafica().getArRpDocumentiAnags();
	}

	private Anagrafica getSelectedAngrafica() {
		return selectedAngrafica;
	}

	private void setSelectedAngrafica(Anagrafica selectedAngrafica) {
		this.selectedAngrafica = selectedAngrafica;
	}

	public Date getNuovoDocumentoDataScadenza() {
		return nuovoDocumentoDataScadenza;
	}

	public void setNuovoDocumentoDataScadenza(Date nuovoDocumentoDataScadenza) {
		this.nuovoDocumentoDataScadenza = nuovoDocumentoDataScadenza;
	}

	public String getNuovoDocumentoIdTipologiaDocumento() {
		return nuovoDocumentoIdTipologiaDocumento;
	}

	public void setNuovoDocumentoIdTipologiaDocumento(String nuovoDocumentoIdTipologiaDocumento) {
		this.nuovoDocumentoIdTipologiaDocumento = nuovoDocumentoIdTipologiaDocumento;
	}

	public String getNuovoDocumentoNumeroDocumento() {
		return nuovoDocumentoNumeroDocumento;
	}

	public void setNuovoDocumentoNumeroDocumento(String nuovoDocumentoNumeroDocumento) {
		this.nuovoDocumentoNumeroDocumento = nuovoDocumentoNumeroDocumento;
	}

}
