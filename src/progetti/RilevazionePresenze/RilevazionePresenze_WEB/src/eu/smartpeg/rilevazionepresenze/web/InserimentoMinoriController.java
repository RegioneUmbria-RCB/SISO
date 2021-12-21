package eu.smartpeg.rilevazionepresenze.web;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.json.JSONObject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiPTIDTO;
import eu.smartpeg.rilevazionepresenze.data.model.Anagrafica;
import eu.smartpeg.rilevazionepresenze.data.model.Struttura;
import eu.smartpeg.rilevazionepresenze.ejb.helpers.ComuniCacheHelperRemote;
import eu.smartpeg.rilevazionepresenze.ejb.helpers.NazioniCacheHelperRemote;
import eu.smartpeg.rilevazionepresenze.pai.ArCsPaiPTISessionBeanRemote;
import eu.smartpeg.utility.ejb.EjbClientUtility;
import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;


@ManagedBean(name = "inserimentoMinoriController")
@ViewScoped
public class InserimentoMinoriController extends RilevazionePresenzeBaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArCsPaiPTIDTO selectedMinore;
	private ArCsPaiPTIDTO nuovoInserimento;
	private DefaultStreamedContent paiFile;
	private DefaultStreamedContent ptiEquiFile;
	private boolean hasPai = false;
	private boolean hasPTIEqui = false;
	
	private List<ArCsPaiPTIDTO> minori;
	
	private AmTabComuni selectedMinoreComuneResidenza;
	private AmTabNazioni selectedMinoreStatoResidenza;
	private List<SelectItem> cittadinanze;
	
	@EJB
	private ArCsPaiPTISessionBeanRemote arCsPaiPtiEJB;
	
	@EJB
	private ComuniCacheHelperRemote comuniCacheHelper;
	
	@EJB
	private NazioniCacheHelperRemote nazioniCacheHelper;
	
	@PostConstruct
	public void init() {
		this.cittadinanze = new ArrayList<SelectItem>();
		/*BaseDTO bt = new BaseDTO();
		fillEnte(bt);
		readCurrentStruttura(bt.getEnteId());*/
		nuovoInserimento = new ArCsPaiPTIDTO();
		readMinori();
		
	}
	
	
	public void readMinori() {
		Struttura s = getCurrentStruttura();
		if(s!=null)
			setMinori(arCsPaiPtiEJB.findMinoriByIdStruttura(s.getId()));
		
	}


	public List<ArCsPaiPTIDTO> getMinori() {
		return minori;
	}


	public void setMinori(List<ArCsPaiPTIDTO> minori) {
		this.minori = minori;
	}


	public ArCsPaiPTIDTO getNuovoInserimento() {
		return nuovoInserimento;
	}


	public void setNuovoInserimento(ArCsPaiPTIDTO nuovoInserimento) {
		
		this.nuovoInserimento = nuovoInserimento;
		inizializzaGestioneAutocomplete();
	}
	
	
	
	public DefaultStreamedContent getPaiFile() {
		return paiFile;
	}


	public void setPaiFile(DefaultStreamedContent paiFile) {
		this.paiFile = paiFile;
	}

	

	public DefaultStreamedContent getPtiEquiFile() {
		return ptiEquiFile;
	}


	public void setPtiEquiFile(DefaultStreamedContent ptiEquiFile) {
		this.ptiEquiFile = ptiEquiFile;
	}


	public void handleUploadPai(FileUploadEvent event) {
		  UploadedFile selectedFile = event.getFile();
		  nuovoInserimento.setDocumentoPai(selectedFile.getContents());
		  nuovoInserimento.setNomeDocPai(selectedFile.getFileName());
	}
	
	public void preparePaiFile(){
		InputStream is = new ByteArrayInputStream(nuovoInserimento.getDocumentoPai());
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		setPaiFile(new DefaultStreamedContent(is, externalContext.getMimeType(nuovoInserimento.getNomeDocPai()),nuovoInserimento.getNomeDocPai()));
	}
	
	public void handleUploadPTIEqui(FileUploadEvent event) {
		  UploadedFile selectedFile = event.getFile();
		  nuovoInserimento.setDocumentoPtiEqui(selectedFile.getContents());
		  nuovoInserimento.setNomeDocPtiEqui(selectedFile.getFileName());
	}
	
	public void preparePTIEQUIFile(){
		InputStream is = new ByteArrayInputStream(nuovoInserimento.getDocumentoPtiEqui());
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		setPtiEquiFile(new DefaultStreamedContent(is, externalContext.getMimeType(nuovoInserimento.getNomeDocPtiEqui()),nuovoInserimento.getNomeDocPtiEqui()));
	}
	
	public void salva() {
		Struttura s = getCurrentStruttura();
		try {
			if(validaInserimento(nuovoInserimento).isEmpty() && s!=null) {
				arCsPaiPtiEJB.salvaInserimento(nuovoInserimento);
				setMinori(arCsPaiPtiEJB.findMinoriByIdStruttura(s.getId()));
				addMessage(FacesMessage.SEVERITY_INFO, "INFO ", "Salvataggio avvenuto con successo");
				nuovoInserimento = new ArCsPaiPTIDTO();
				PrimeFaces.current().executeScript("PF('insMinoreDialog').hide()");
				PrimeFaces.current().ajax().update("frmMessages:messages");
			} else {
				addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio", validaInserimento(nuovoInserimento));
			}
		} catch(Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio", e.getMessage());
		}

	}
	
	public void salvaNuovoInserimento() {
		Struttura s = this.getCurrentStruttura();
		if(s!=null){
			nuovoInserimento.setCodRouting(s.getCodBelfioreComune());
			nuovoInserimento.setStruttura(s);
			nuovoInserimento.setTipoStruttura(s.getTipoStruttura());
			nuovoInserimento.setStato("INS");
			salva();
			this.getMinori();
		}
	}


	public ArCsPaiPTIDTO getSelectedMinore() {
		return selectedMinore;
	}


	public void setSelectedMinore(ArCsPaiPTIDTO selectedMinore) {
		this.selectedMinore = selectedMinore;
		if(selectedMinore.getDocumentoPai() != null) {
			hasPai = true;
		}
		if(selectedMinore.getDocumentoPtiEqui() != null) {
			hasPTIEqui = true;
		}
	}
	
	public void onChangeCittadinanza() {
		nuovoInserimento.setComuneResidenza(null);
		nuovoInserimento.setViaResidenza(null);
		nuovoInserimento.setNazioneResidenza(null);
	}
	
	public void handleUploadPaiSecond(FileUploadEvent event) {
		  UploadedFile selectedFile = event.getFile();
		  selectedMinore.setDocumentoPai(selectedFile.getContents());
		  selectedMinore.setNomeDocPai(selectedFile.getFileName());
	}
	
	public void preparePaiFileSecond(){
		InputStream is = new ByteArrayInputStream(selectedMinore.getDocumentoPai());
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		setPaiFile(new DefaultStreamedContent(is, externalContext.getMimeType(selectedMinore.getNomeDocPai()),selectedMinore.getNomeDocPai()));
	}
	
	public void handleUploadPTIEquiSecond(FileUploadEvent event) {
		  UploadedFile selectedFile = event.getFile();
		  selectedMinore.setDocumentoPtiEqui(selectedFile.getContents());
		  selectedMinore.setNomeDocPtiEqui(selectedFile.getFileName());
	}
	
	public void preparePTIEQUIFileSecond(){
		InputStream is = new ByteArrayInputStream(selectedMinore.getDocumentoPtiEqui());
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		setPtiEquiFile(new DefaultStreamedContent(is, externalContext.getMimeType(selectedMinore.getNomeDocPtiEqui()),selectedMinore.getNomeDocPtiEqui()));
	}


	public boolean isHasPai() {
		return hasPai;
	}


	public void setHasPai(boolean hasPai) {
		this.hasPai = hasPai;
	}


	public boolean isHasPTIEqui() {
		return hasPTIEqui;
	}


	public void setHasPTIEqui(boolean hasPTIEqui) {
		this.hasPTIEqui = hasPTIEqui;
	}
	
	public void resetMinore() {
		this.selectedMinoreComuneResidenza = new AmTabComuni();
		this.selectedMinoreStatoResidenza= new AmTabNazioni();
		nuovoInserimento = new ArCsPaiPTIDTO();
	}
	
	
	public AmTabComuni getSelectedMinoreComuneResidenza() {
		return selectedMinoreComuneResidenza;
	}


	public void setSelectedMinoreComuneResidenza(AmTabComuni selectedMinoreComuneResidenza) {
		this.selectedMinoreComuneResidenza = selectedMinoreComuneResidenza;
	}


	public AmTabNazioni getSelectedMinoreStatoResidenza() {
		return selectedMinoreStatoResidenza;
	}


	public void setSelectedMinoreStatoResidenza(AmTabNazioni selectedMinoreStatoResidenza) {
		this.selectedMinoreStatoResidenza = selectedMinoreStatoResidenza;
	}


	public String validaInserimento(ArCsPaiPTIDTO minore) {
    	String msg = "";
		if(minore.getCittadinanza().equals("ITALIANA") && (minore.getComuneResidenza() == null || minore.getComuneResidenza().isEmpty() || minore.getViaResidenza() == null || minore.getViaResidenza().isEmpty())) {
			return "I dati sulla cittadinanza sono obbligatori";
		}
		if(minore.getCittadinanza().equals("ESTERA") && (minore.getNazioneResidenza() == null || minore.getNazioneResidenza().isEmpty())) {
			return "I dati sulla cittadinanza sono obbligatori";
		}
		if(minore.getDataFinePermanenza().getTime()<minore.getDataInizioPermamenza().getTime()) {
			return "La data di fine permanenza deve essere successiva alla data di inizio";
		}
		if(minore.getComuneResidenza() == null||minore.getComuneResidenza().isEmpty() ) {
			if(minore.getNazioneResidenza()== null) {
				return "Inserire Comune o Nazione Residenza";
			}
		
		}
		if(minore.getViaResidenza() == null||minore.getViaResidenza().isEmpty() ) {
				return "Inserire Indirizzo di Residenza";
		}
		
		//TODO Controlli sui files
		
		return msg;
    }
	
	/**
	 * Autocompletamento comune
	 * 
	 * @param query
	 * @return
	 */
	public List<AmTabComuni> completaLuogo(String query) {
		List<AmTabComuni> result = comuniCacheHelper.trovaComuniPerDenominazione(query);
		return result;
	}
	
	public void onComuneResidenzaSelect(javax.faces.event.AjaxBehaviorEvent event) {
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			nuovoInserimento.setComuneResidenza(mapper
					.writeValueAsString(selectedMinoreComuneResidenza));
		    AmTabNazioni nazItalia = new AmTabNazioni();
		    nazItalia = nazioniCacheHelper.getNazioneByCodiceAnagrafe("1");
			setSelectedMinoreStatoResidenza(nazItalia);
			
		} catch (Exception e) {
			logger.error(e);
		}
		
	}

	public void onStatoResidenzaSelect(javax.faces.event.AjaxBehaviorEvent event) {
		logger.debug("onStatoNascitaSelect - selectedMinoreStatoResidenza: "
				+ this.selectedMinoreStatoResidenza.getCodNazioneAnagrafe());
//		nuovoInserimento.setNazioneResidenza(selectedMinoreStatoResidenza.getCodNazioneAnagrafe());
		nuovoInserimento.setNazioneResidenza(selectedMinoreStatoResidenza.getCodIstatNazione());
		//SBIANCO IL COMUNE RESIDENZA
		if(!selectedMinoreStatoResidenza.getCodNazioneAnagrafe().equals("1")) {
			setSelectedMinoreComuneResidenza(null);
		}
		
		
		logger.debug("selected Nazione Residenza des: " + selectedMinoreStatoResidenza.getNazione());
	}
	
	private void inizializzaGestioneAutocomplete() {
		this.selectedMinoreComuneResidenza = null;
		this.selectedMinoreStatoResidenza=null;
		
		if (this.nuovoInserimento != null) {
			if (nuovoInserimento.getComuneResidenza()!=null) {
				JSONObject jsonObject = new JSONObject(nuovoInserimento.getComuneResidenza());
				if (nuovoInserimento.getComuneResidenza() != null) {
					this.selectedMinoreComuneResidenza = comuniCacheHelper
							.getComuneByCodiceIstat(jsonObject.getString("codIstatComune"));
				}
			}
			
			if (nuovoInserimento.getNazioneResidenza() != null && !nuovoInserimento.getNazioneResidenza().isEmpty()) {
//				this.selectedMinoreStatoResidenza = nazioniCacheHelper
//						.getNazioneByCodiceAnagrafe(nuovoInserimento.getNazioneResidenza());
				this.selectedMinoreStatoResidenza = nazioniCacheHelper
						.getNazioneByCodiceIstat(nuovoInserimento.getNazioneResidenza());
			}

		}
	}	
		
//		if (this.selectedMinore != null) {
//			JSONObject jsonObject = new JSONObject(selectedMinore.getComuneResidenza());
//			if (selectedMinore.getComuneResidenza() != null) {
//				this.selectedMinoreComuneResidenza = comuniCacheHelper
//						.getComuneByCodiceIstat(jsonObject.getString("codIstatComune"));
//			}
//			
//			if (selectedMinore.getNazioneResidenza() != null && !selectedMinore.getNazioneResidenza().isEmpty()) {
//				this.selectedMinoreStatoResidenza = nazioniCacheHelper
//						.getNazioneByCodiceAnagrafe(selectedMinore.getNazioneResidenza());
//			}
//
//		}
//	}	
	
	private Boolean inizializzaCacheHelpers() {
		// questa chiamata  serve per svegliare CDI e inizializzare il bean. 
		//  se possibile rifare in modo pi� elegante - non si pu� usare @startup
		return comuniCacheHelper.isListaComuniCaricata() && nazioniCacheHelper.isListaNazioniCaricata();
	} 
	

	/**
	 * Autocompletamento nazione
	 */
	public List<AmTabNazioni> completaLstNazioni(String query) {
		List<AmTabNazioni> result = nazioniCacheHelper.trovaNazioniPerDenominazione(query);
		return result;
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

	public void onCittadinanzaChange(ValueChangeEvent event) {
		String newValue = (String) event.getNewValue();

		nuovoInserimento.setCittadinanza(newValue);
		logger.debug("selected Cittadinanza: " + this.nuovoInserimento.getCittadinanza());

	}
}
