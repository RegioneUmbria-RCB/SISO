package it.webred.cs.csa.web.manbean.scheda;

import it.webred.cs.csa.ejb.client.*;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.EventoDTO;
import it.webred.cs.csa.web.manbean.fascicolo.presaincarico.PresaInCaricoBean;
import it.webred.cs.csa.web.manbean.fascicolo.schedeSegr.SchedaSegrBean;
import it.webred.cs.csa.web.manbean.interscambio.InterscambioCartellaSociale;
import it.webred.cs.csa.web.manbean.interscambio.InterscambioDialogEventoBean;
import it.webred.cs.csa.web.manbean.report.ReportBean;
import it.webred.cs.csa.web.manbean.scheda.anagrafica.AnagraficaBean;
import it.webred.cs.csa.web.manbean.scheda.disabilita.DatiDisabilitaBean;
import it.webred.cs.csa.web.manbean.scheda.invalidita.DatiInvaliditaBean;
import it.webred.cs.csa.web.manbean.scheda.note.NoteBean;
import it.webred.cs.csa.web.manbean.scheda.operatori.SchedaPermessiBean;
import it.webred.cs.csa.web.manbean.scheda.parenti.ParentiBean;
import it.webred.cs.csa.web.manbean.scheda.sociali.DatiSocialiBean;
import it.webred.cs.csa.web.manbean.scheda.sociali.DatiSocialiComp;
import it.webred.cs.csa.web.manbean.scheda.tribunale.DatiTribunaleBean;
import it.webred.cs.csa.web.manbean.schedaSegr.SchedaSegr;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiCartella;
import it.webred.cs.data.DataModelCostanti.TipoRicercaSoggetto;
import it.webred.cs.data.model.*;
import it.webred.cs.data.model.view.CsRdcAnagraficaGepi;
import it.webred.cs.jsf.bean.DatiAnaBean;
import it.webred.cs.jsf.bean.DatiCasoBean;
import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.manbean.ConsensoPrivacyMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsIndirizzo;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.DatiPrivacyDTO;
import it.webred.ss.ejb.dto.report.DatiPrivacyPdfDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class SchedaBean extends CsUiCompBaseBean {

	private boolean disableModificaCartella;
	private String idTabScheda = "tabScheda";

	@ManagedProperty(value = "#{anagraficaBean}")
	private AnagraficaBean anagraficaBean;

	@ManagedProperty(value = "#{datiDisabilitaBean}")
	private DatiDisabilitaBean datiDisabilitaBean;

	@ManagedProperty(value = "#{datiInvaliditaBean}")
	private DatiInvaliditaBean datiInvaliditaBean;

	@ManagedProperty(value = "#{parentiBean}")
	private ParentiBean parentiBean;

	@ManagedProperty(value = "#{datiSocialiBean}")
	private DatiSocialiBean datiSocialiBean;

	@ManagedProperty(value = "#{datiTribunaleBean}")
	private DatiTribunaleBean datiTribunaleBean;

	@ManagedProperty(value = "#{schedaPermessiBean}")
	private SchedaPermessiBean schedaPermessiBean;

	@ManagedProperty(value = "#{schedaSegrBean}")
	private SchedaSegrBean schedaSegrBean;

	@ManagedProperty(value = "#{noteBean}")
	private NoteBean noteBean;
	
	// SISO-1115
	@ManagedProperty(value = "#{schedaDatiEsterniSoggettoBean}")
	private SchedaDatiEsterniSoggettoBean schedaDatiEsterniSoggettoBean;
	// -!-

	private PresaInCaricoBean presaInCaricoBean;
	private ConsensoPrivacyMan consensoPrivacyMan;

	private boolean renderTabAnagrafica;
	private boolean renderTabDatiSociali;
	private boolean renderTabInvalidita;
	private boolean renderTabParenti;
	private boolean renderTabDisabilita;
	private boolean renderTabTribunale;
	private boolean renderTabServizi;
	private boolean renderTabContributi;
	private boolean renderTabOperatori;
	private boolean renderTabNote;
	
	//SISO-1526
	private boolean checkDatiEsterni = false;
	
	// 2018-02-01 Smartpeg - Maintenance
	private boolean renderedCasoEsportatoInterscambio;
	private boolean renderedCasoImportatoInterscambio;

	private boolean disableHead;
	private boolean modificaButt;
	private boolean attivaSalvataggio;
	private Long identificativoScheda;
	private String dataModifica;
	private String usrModifica;
	
	public String msgBeneficiarioRdC;
	
	//Per esportazione Cartella Sociale 
	private InterscambioCartellaSociale ics = new InterscambioCartellaSociale();
	private CsACaso casoDaEsportare;
	private InterscambioDialogEventoBean modalBean;
	private static String MSG_DATE="Se possibile impostare correttamente le date";
	
	//SISO 724
	private int tabViewIndex = 0;

	//SISO 724
	public void onChangeTabView(TabChangeEvent tce){
	   try{
		    FacesContext context = FacesContext.getCurrentInstance();
		    Map<String,String> params = context.getExternalContext().getRequestParameterMap();
		    TabView tabView = (TabView) tce.getComponent();
		    String activeIndexValue = params.get(tabView.getClientId(context) + "_tabindex");
		    this.tabViewIndex = Integer.parseInt(activeIndexValue);
		}catch(Exception ex){
			this.tabViewIndex = 0;
		}
	}
	
	/**
	 * Apre il dialog per la conferma dell'esportazione della cartella
	 */
	public void dialogEsportaCartella(CsACaso caso) {
		this.casoDaEsportare = caso;
		logger.info("Mostra dialog dettaglio esportazione...");
	}

	/**
	 * Avvia l'esportazione del caso selezionato
	 */
	public void esportaCartella() {
		logger.info("Esportazione cartella sociale avviata...");

		try {
			
			//TODO: passare valori organizzazioni mittente/destinataria
			this.ics.esportaCartella(this.casoDaEsportare, modalBean);
			logger.info("Esportazione avvenuta con successo");
			
			FacesContext context = FacesContext.getCurrentInstance();

			// TODO: Controllare problema su aggiunta dettaglio
			context.addMessage("notificaImportazione", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Esportazione avvenuta con successo", ""));

			RequestContext pfC = RequestContext.getCurrentInstance();
			pfC.update("@(.notificaGrowl)");

		} catch (Exception ex) {
			logger.error("Errore nell'esportazione della cartella relativa al caso ID: "
					+ this.casoDaEsportare.getId());
		}
	}

	/**
	 * Gestione visualizzazione icona di notifica per Caso Importato
	 * 
	 * @return
	 */
	public boolean isRenderedCasoImportatoInterscambio() {
		// 2018-02-01 Smartpeg - valorizzazione spostata in checkPermessi per Maintenance
//		return esisteEventoDiTipo("IMPORT");
		return renderedCasoImportatoInterscambio;
	}

	
	public boolean isCheckDatiEsterni() {
		return checkDatiEsterni;
	}

	public void setCheckDatiEsterni(boolean checkDatiEsterni) {
		this.checkDatiEsterni = checkDatiEsterni;
	}

	/**
	 * Gestione visualizzazione icona di notifica per Caso Esportato
	 * 
	 * @return
	 */
	public boolean isRenderedCasoEsportatoInterscambio() {
		// 2018-02-01 Smartpeg - valorizzazione spostata in checkPermessi per Maintenance
//		return this.esisteEventoDiTipo("EXPORT");
		return renderedCasoEsportatoInterscambio;
	}

	/**
	 * Controlla l'esistenza di un evento relativo al caso selezionato
	 * e di tipo uguale al tipo specificato da parametro 
	 * @param tipo  
	 * @return true se presente 
	 */
	private boolean esisteEventoDiTipo(String tipo) {
		boolean esiste =false;
		CsASoggettoLAZY soggetto = (CsASoggettoLAZY) getSession().getAttribute("soggetto");
		
		if(soggetto!=null){
			// Operatore_Settore_ID
			// Codice Fiscale del Caso
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(soggetto.getCsAAnagrafica().getCf());
			dto.setObj2(getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getId());
			dto.setObj3(tipo);
			// Ottengo la lista di eventi legata al caso selezionato
			List<EventoDTO> eventiCaso = this.ics.getEventoService().findEventsByOpSettIdAndCF(dto);
	
			if (!eventiCaso.isEmpty()) esiste = true;
		}
		return esiste;
	}

	public void nuova() {

		initialize(null);
		modifica();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("scheda.faces");
		} catch (IOException e) {
			addError("Errore",
					"Errore durante il reindirizzamento alla scheda Caso");
		}
	}

	public void nuovaDaAnagrafeEsterna(String tipoRicerca, String idSearch, PersonaDettaglio pIn) {
		if (StringUtils.isBlank(idSearch)) {
			addWarning("Scegliere un soggetto o creare una cartella vuota", "");
			return;
		}

		initialize(null);
		modifica();

		try {
			
			String id =         !StringUtils.isBlank(idSearch) && !idSearch.startsWith("@") ? idSearch : null;
    		String codFiscale = !StringUtils.isBlank(idSearch) &&  idSearch.startsWith("@") ? idSearch.replace("@","") : null;
    		
    		PersonaDettaglio p = pIn;
    		if(!DataModelCostanti.TipoRicercaSoggetto.DEFAULT.equals(tipoRicerca)){
				if(!StringUtils.isBlank(id)) 
					p = getPersonaDaAnagEsterna(tipoRicerca, id);
				else if(!DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE.equals(tipoRicerca))
					p = CsUiCompBaseBean.getPersonaDaAnagEsterna(tipoRicerca, null, null, codFiscale);
    		}

			if (p != null) {

				if (this.isCodFiscaleInCs(p.getCodfisc().trim().toUpperCase())){
					disableHead = false;
					return;
				}	
				
				String titleBlocco = "Non è possibile creare una nuova scheda di accesso";
				String msgBlocco = "Il soggetto selezionato è";
				if (p.isDefunto()) {
					addWarning(titleBlocco,msgBlocco +" deceduto" + (p.getDataMorte()!=null ? "il "+ddMMyyyy.format(p.getDataMorte()) : ""));
					disableHead = false;
					return;
				}
				
				if(p.isEmigrato()){
					addWarning(titleBlocco, msgBlocco +" emigrato");
					disableHead = false;
					return;
				}

				// anagrafica
				DatiAnaBean anaBean = new DatiAnaBean();
				anaBean.setIdOrigWs(tipoRicerca+"@"+(id!=null ? id : ""));
				anaBean.setCodiceFiscale(p.getCodfisc());
				anaBean.setCognome(p.getCognome());
				anaBean.setNome(p.getNome());
				anaBean.setDataNascita(p.getDataNascita());
				SessoBean sb = new SessoBean(p.getSesso());
				anaBean.setDatiSesso(sb);

				// Cittadinanza
				anaBean.setCittadinanza(p.getCittadinanza());

				anagraficaBean.setDatiAnaBean(anaBean);

				// nascita
				anagraficaBean.getComuneNazioneNascitaMan().init(p.getComuneNascita(), p.getNazioneNascita());

				List<CsAIndirizzo> listaIndirizzi = new ArrayList<CsAIndirizzo>();
				
				// indirizzo residenza
				if(!StringUtils.isBlank(p.getIndirizzoResidenza())){
					CsAIndirizzo indirizzoRes = new CsAIndirizzo();
					CsAAnaIndirizzo indirizzoAna = new CsAAnaIndirizzo();
					indirizzoAna.setIndirizzo(p.getIndirizzoResidenza());
					indirizzoAna.setCivicoNumero(p.getCivicoResidenza());
					indirizzoAna.setCivicoAltro(null);
					if(p.getComuneResidenza()!=null){
						indirizzoAna.setProv(p.getComuneResidenza().getSiglaProv());
						indirizzoAna.setComCod(p.getComuneResidenza().getCodIstatComune());
						indirizzoAna.setComDes(p.getComuneResidenza().getDenominazione());
					}else if(p.getNazioneResidenza()!=null){
						indirizzoAna.setStatoCod(p.getNazioneResidenza().getCodIstatNazione());
						indirizzoAna.setStatoDes(p.getNazioneResidenza().getNazione());
					}
						
					indirizzoRes.setDataInizioApp(new Date());
					indirizzoRes.setCsAAnaIndirizzo(indirizzoAna);
					indirizzoRes.setCsTbTipoIndirizzo(anagraficaBean.getResidenzaCsaMan().getTipoIndirizzoResidenza());
					listaIndirizzi.add(indirizzoRes);
				}
				
				// indirizzo domicilio
				if(!StringUtils.isBlank(p.getIndirizzoDomicilio())){
					CsAIndirizzo indirizzoDom = new CsAIndirizzo();
					CsAAnaIndirizzo indirizzoAnaDom = new CsAAnaIndirizzo();
					indirizzoAnaDom.setIndirizzo(p.getIndirizzoResidenza());
					indirizzoAnaDom.setCivicoNumero(p.getCivicoDomicilio());
					indirizzoAnaDom.setCivicoAltro(null);
					if(p.getComuneDomicilio()!=null){
						indirizzoAnaDom.setProv(p.getComuneDomicilio().getSiglaProv());
						indirizzoAnaDom.setComCod(p.getComuneDomicilio().getCodIstatComune());
						indirizzoAnaDom.setComDes(p.getComuneDomicilio().getDenominazione());
					}else if(p.getNazioneDomicilio()!=null){
						indirizzoAnaDom.setStatoCod(p.getNazioneDomicilio().getCodIstatNazione());
						indirizzoAnaDom.setStatoDes(p.getNazioneDomicilio().getNazione());
					}
					
					indirizzoDom.setDataInizioApp(new Date());
					indirizzoDom.setCsAAnaIndirizzo(indirizzoAnaDom);
					indirizzoDom.setCsTbTipoIndirizzo(anagraficaBean.getResidenzaCsaMan().getTipoIndirizzoDomicilio());
					listaIndirizzi.add(indirizzoDom);
				}
				anagraficaBean.getResidenzaCsaMan().setLstIndirizzi(listaIndirizzi);
				anagraficaBean.getResidenzaCsaMan().setLstIndirizziOld(listaIndirizzi);
				anagraficaBean.getResidenzaCsaMan().setWarningMessage(MSG_DATE);

				// stato civile
				this.impostaStatoCivile(p.getStatoCivile());
			
				//medico
				if(TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE.equalsIgnoreCase(tipoRicerca))
					anagraficaBean.impostaMedicoPersonaMarche(p);
				if(TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA.equalsIgnoreCase(tipoRicerca))
					anagraficaBean.impostaMedicoPersonaUmbria(p.getMedicoCodRegionale(), p.getMedicoDataScelta(), p.getMedicoDataRevoca());
				if(TipoRicercaSoggetto.DEFAULT.equalsIgnoreCase(tipoRicerca)){
					try {
						// Cerca medico del soggetto in anagrafe regionale
						PersonaDettaglio pMedicoUmbria = getPersonaDaAnagEsterna(TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA, p.getCognome(), p.getNome(), p.getCodfisc());
						if (pMedicoUmbria != null) 
							anagraficaBean.impostaMedicoPersonaUmbria(pMedicoUmbria.getMedicoCodRegionale(), pMedicoUmbria.getMedicoDataScelta(), pMedicoUmbria.getMedicoDataRevoca());
						
						PersonaDettaglio pMedicoMarche = getPersonaDaAnagEsterna(TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE, p.getCognome(), p.getNome(), p.getCodfisc());
						if(pMedicoMarche!=null)
							anagraficaBean.impostaMedicoPersonaMarche(pMedicoMarche);
						
					} catch (Exception e) {
						addError("Errore","Errore recupero medico da anagrafe regionale");
						logger.error("Errore recupero medico da anagrafe regionale" + e.getMessage(), e);
					}
				
				}
			}

			FacesContext.getCurrentInstance().getExternalContext().redirect("scheda.faces");

		} catch (Exception e) {
			addError("Errore", "Errore durante il caricamento dell'anagrafica");
			logger.error("", e);
		}
	}
	
	public void nuovaDaAnagrafeWrapper(DatiUserSearchBean sel) {
		String id = sel.getId();
		if (id != null) {
			if (id.trim().startsWith(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA))
				nuovaDaAnagrafeEsterna(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA, id.replace(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA, ""), (PersonaDettaglio)sel.getSoggetto() );
			else if(id.trim().startsWith(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE))
				nuovaDaAnagrafeEsterna(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE, id.replace(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE, ""), (PersonaDettaglio)sel.getSoggetto());
			else if(id.trim().startsWith(DataModelCostanti.TipoRicercaSoggetto.SIGESS))
				nuovaDaAnagrafeEsterna(DataModelCostanti.TipoRicercaSoggetto.SIGESS, id.replace(DataModelCostanti.TipoRicercaSoggetto.SIGESS, ""), (PersonaDettaglio)sel.getSoggetto());
			else
				nuovaDaAnagrafeEsterna(DataModelCostanti.TipoRicercaSoggetto.DEFAULT, id.replace(DataModelCostanti.TipoRicercaSoggetto.DEFAULT, ""), (PersonaDettaglio)sel.getSoggetto());
		} else {
			this.addWarning("Attenzione","Selezionare un soggetto per poter creare una nuova cartella");
		}
	}
	
	private void impostaStatoCivile(String sc){
		if (sc != null && !sc.trim().isEmpty()) {
			
			AccessTableConfigurazioneSessionBeanRemote configurazioneService = (AccessTableConfigurazioneSessionBeanRemote) 
					getEjb("CarSocialeA", "CarSocialeA_EJB","AccessTableConfigurazioneSessionBean");
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(sc);
			CsTbStatoCivile statoCivile = configurazioneService.getStatoCivileByIdExtCet(dto);
			if (statoCivile != null) {
				anagraficaBean.getStatoCivileGestBean().setItemSelezionato(statoCivile.getCod() + "|" + statoCivile.getDescrizione());
				anagraficaBean.getStatoCivileGestBean().aggiungiSelezionato();
				anagraficaBean.getStatoCivileGestBean().setMaxActiveComponents(1);
				anagraficaBean.getStatoCivileGestBean().salva();
				anagraficaBean.getStatoCivileGestBean().setWarningMessage(MSG_DATE);
			} else if (statoCivile == null && sc != null) {
				addWarning("Attenzione", "Non è stata configurata la corrisponenza dei codici di stato civile");
			}
		}
	}
	
	public void nuovaDaSocLav(CsRdcAnagraficaGepi rdc){
		
		if (rdc == null) {
			addWarning("Scegliere una soggetto", "");
			return;
		}
		
		initialize(null);
		modifica();
		
		addWarning("", "Funzione creazione cartella da SOCLAV non implementata");
		
		try{
			
			// anagrafica
			DatiAnaBean anaBean = new DatiAnaBean();
			
			anaBean.setCodiceFiscale(rdc.getCf());
			anaBean.setCognome(rdc.getCognome());
			anaBean.setNome(rdc.getNome());
			anaBean.setDataNascita(rdc.getDataNascita());
			SessoBean sb = new SessoBean(rdc.getSesso());
			anaBean.setDatiSesso(sb);
			anaBean.setCittadinanza(rdc.getNazionalita());

			anaBean.setTelefono(rdc.getTelefono());
			anaBean.setCellulare(rdc.getCellulare());
			anaBean.setEmail(rdc.getEmail());

			anagraficaBean.setDatiAnaBean(anaBean);
			
			// nascita
			if (rdc.getNasComuneCod() != null) {
				AmTabComuni comuneNasc = luoghiService.getComuneItaByBelfiore(rdc.getNasComuneCod());
				if(comuneNasc!=null) {
					ComuneBean comuneBean= new ComuneBean(comuneNasc);
					anagraficaBean.getComuneNazioneNascitaMan().getComuneNascitaMan().setComune(comuneBean);
				}else {
					AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByCodiceGenerico(rdc.getNasComuneCod());
					anagraficaBean.getComuneNazioneNascitaMan().setNazioneValue();
					anagraficaBean.getComuneNazioneNascitaMan().getNazioneMan().setNazione(amTabNazioni);
				}
			}
			
			CsAIndirizzo indResidenza = null;
			if(rdc.getResIndirizzo() != null){
				indResidenza = new CsAIndirizzo();
				CsAAnaIndirizzo indirizzoAna = new CsAAnaIndirizzo();
				
				indirizzoAna.setIndirizzo(rdc.getResIndirizzo());
				
				AmTabComuni comune = luoghiService.getComuneItaByBelfiore(rdc.getResComuneCod());
				if(comune!=null) {
					indirizzoAna.setProv(comune.getSiglaProv());
					indirizzoAna.setComCod(comune.getCodIstatComune());
					indirizzoAna.setComDes(comune.getDenominazione());
				}
				
				indResidenza.setDataInizioApp(new Date());
				indResidenza.setCsAAnaIndirizzo(indirizzoAna);
			
				indResidenza.setCsTbTipoIndirizzo(anagraficaBean.getResidenzaCsaMan().getTipoIndirizzoResidenza());
				List<CsAIndirizzo> listaIndirizzi = new ArrayList<CsAIndirizzo>();
				listaIndirizzi.add(indResidenza);
				anagraficaBean.getResidenzaCsaMan().setLstIndirizzi(listaIndirizzi);
				anagraficaBean.getResidenzaCsaMan().setLstIndirizziOld(listaIndirizzi);
				anagraficaBean.getResidenzaCsaMan().setWarningMessage(MSG_DATE);
			}
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("scheda.faces");

		} catch (Exception e) {
			addError("Errore", "Errore durante il caricamento dell'anagrafica");
			logger.error("", e);
		}	
	}

	// SISO-938 la creazione da "Schede Ufficio della Cittadinanza" viene smistata a seconda del tipoScheda
	public void nuovaDaSchedeUDC(SchedaSegr schedaSegr) {
		if (schedaSegr.isProvenienzaUDC()) {
			nuovaDaSegretariatoSoc(schedaSegr.getId());
		}
		else {
			// SISO-938 2019-02-21 per ora l'unico caso di ALTRI è Serena e ricade tutto lì in automatico
			nuovaDaAltriSerena(schedaSegr);
		}
	}
	
	// SISO-938 reso private, in quanto il punto di ingresso della view è diventato nuovaDaSchedeUDC(SchedaSegr)
	private void nuovaDaSegretariatoSoc(Long id) {

		if (id == null) {
			addWarning("Scegliere una scheda", "");
			return;
		}

		initialize(null);
		modifica();

		try {
			// precarico anagrafica
			SsSchedaSessionBeanRemote ssSchedaSegrService = (SsSchedaSessionBeanRemote) getEjb(
					"SegretariatoSoc", "SegretariatoSoc_EJB",
					"SsSchedaSessionBean");

			it.webred.ss.ejb.dto.BaseDTO bDto = new it.webred.ss.ejb.dto.BaseDTO();
			CsUiCompBaseBean.fillEnte(bDto);
			bDto.setObj(id);
			SsScheda ssScheda = ssSchedaSegrService.readScheda(bDto);
			schedaSegrBean.setSsScheda(ssScheda);
			bDto.setObj(ssScheda.getSegnalato());
			SsSchedaSegnalato segnalato = ssSchedaSegrService.readSegnalatoById(bDto);

			if (segnalato != null) {

				schedaSegrBean.setSsSchedaSegnalato(segnalato);
				SsAnagrafica ana = segnalato.getAnagrafica();
				
				// anagrafica
				DatiAnaBean anaBean = new DatiAnaBean();
				anaBean.setIdOrigWs(ana.getIdOrigWs());
				anaBean.setCodiceFiscale(ana.getCf());
				anaBean.setCognome(ana.getCognome());
				anaBean.setNome(ana.getNome());
				anaBean.setDataNascita(ana.getData_nascita());
				SessoBean sb = new SessoBean(ana.getSesso());
				anaBean.setDatiSesso(sb);
				anaBean.setCittadinanza(ana.getCittadinanza());
				anaBean.setCittadinanza2(ana.getCittadinanza2());
				anaBean.setCittadinanzaAcq(ana.getCittadinanzaAcq());

				anaBean.setTelefono(segnalato.getTelefono());
				anaBean.setTitTelefono(segnalato.getTitolareTelefono());

				anaBean.setCellulare(segnalato.getCel());
				anaBean.setTitCellulare(segnalato.getTitolareCellulare());

				anaBean.setEmail(segnalato.getEmail());
				anaBean.setTitEmail(segnalato.getTitolareEmail());

				anagraficaBean.setDatiAnaBean(anaBean);

				// nascita
				if (ana.getComuneNascitaCod() != null || ana.getStatoNascitaCod() != null) {

					if (ana.getComuneNascitaCod() != null) {
						ComuneBean comuneBean = new ComuneBean(
								ana.getComuneNascitaCod(),
								ana.getComuneNascitaDes(),
								ana.getProvNascitaCod());
						anagraficaBean.getComuneNazioneNascitaMan().getComuneNascitaMan().setComune(comuneBean);
					}
					if (ana.getStatoNascitaCod() != null) {
						AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(ana.getStatoNascitaCod(),ana.getStatoNascitaDes());
						anagraficaBean.getComuneNazioneNascitaMan().setNazioneValue();
						anagraficaBean.getComuneNazioneNascitaMan().getNazioneMan().setNazione(amTabNazioni);
					}

				} else { // Gestione Precendente

					String comuneNascita = ana.getLuogoDiNascita();
					if (comuneNascita != null) {
						AccessTableComuniSessionBeanRemote comuniService = (AccessTableComuniSessionBeanRemote) getEjb(
								"CarSocialeA", "CarSocialeA_EJB","AccessTableComuniSessionBean");
						List<AmTabComuni> lstComuni = comuniService.getComuniByDenomContains(comuneNascita, false);
						if (lstComuni != null && !lstComuni.isEmpty() && lstComuni.size() == 1) {
							AmTabComuni comune = lstComuni.get(0);
							ComuneBean comuneBean = new ComuneBean(comune);
							anagraficaBean.getComuneNazioneNascitaMan().getComuneNascitaMan().setComune(comuneBean);
						} else {
							AccessTableNazioniSessionBeanRemote nazioniService = (AccessTableNazioniSessionBeanRemote) getEjb(
									"CarSocialeA", "CarSocialeA_EJB","AccessTableNazioniSessionBean");
							List<AmTabNazioni> lstNazioni = nazioniService.getNazioniByDenomContains(comuneNascita);
							if (lstComuni != null && !lstNazioni.isEmpty() && lstNazioni.size() == 1) {
								AmTabNazioni amTabNazioni = lstNazioni.get(0);
								anagraficaBean.getComuneNazioneNascitaMan().setNazioneValue();
								anagraficaBean.getComuneNazioneNascitaMan().getNazioneMan().setNazione(amTabNazioni);
							}
						}
					}
				}

				// indirizzo res
				CsAIndirizzo indirizzoRes = null;
				if (segnalato.getSenzaFissaDimora() != null && segnalato.getSenzaFissaDimora()) {
					indirizzoRes = new CsAIndirizzo();
					indirizzoRes.setDataInizioApp(new Date());
					CsAAnaIndirizzo indirizzoAna = new CsAAnaIndirizzo();
					indirizzoAna.setIndirizzo(DataModelCostanti.SENZA_FISSA_DIMORA);
					indirizzoRes.setCsAAnaIndirizzo(indirizzoAna);

				} else {
					SsIndirizzo residenzaSegnalato = segnalato.getResidenza();
					if (residenzaSegnalato != null) {
						String viaCompleta = residenzaSegnalato.getVia();
						// Provo a fare split per gli indirizzi generati
						// automaticamente
						String viaResSegnalato = viaCompleta != null
								&& viaCompleta.contains(", ") ? viaCompleta
								.substring(0, viaCompleta.lastIndexOf(","))
								: viaCompleta;
						String civicoResSegnalato = viaCompleta != null
								&& viaCompleta.contains(", ") ? viaCompleta
								.substring(viaCompleta.lastIndexOf(",") + 1)
								.trim() : null;
						// residenzaSegnalato.getNumero();
						boolean datiResSegnPresenti = viaResSegnalato != null && civicoResSegnalato != null;

						if (datiResSegnPresenti) {
							AccessTablePersonaCiviciSessionBeanRemote personaCiviciService = 
									(AccessTablePersonaCiviciSessionBeanRemote)getCarSocialeEjb("AccessTablePersonaCiviciSessionBean");
							BaseDTO dto = new BaseDTO();
							fillEnte(dto);
							dto.setObj(viaResSegnalato);
							dto.setObj2(civicoResSegnalato);
							indirizzoRes = personaCiviciService.getIndirizzoResidenzaByNomeCiv(dto);
						}

						if (indirizzoRes == null && (viaResSegnalato != null || civicoResSegnalato != null)) {
							indirizzoRes = new CsAIndirizzo();
							CsAAnaIndirizzo indirizzoAna = new CsAAnaIndirizzo();
							indirizzoAna.setIndirizzo(viaResSegnalato);
							if (civicoResSegnalato != null)
								indirizzoAna.setCivicoNumero(civicoResSegnalato.toString());
							
							if(!StringUtils.isBlank(residenzaSegnalato.getComuneCod())){
								indirizzoAna.setProv(residenzaSegnalato.getProvCod());
								indirizzoAna.setComCod(residenzaSegnalato.getComuneCod());
								indirizzoAna.setComDes(residenzaSegnalato.getComuneDes());
							}else{
								indirizzoAna.setStatoCod(residenzaSegnalato.getStatoCod());
								indirizzoAna.setStatoDes(residenzaSegnalato.getStatoDes());
							}
							indirizzoRes.setDataInizioApp(new Date());
							indirizzoRes.setCsAAnaIndirizzo(indirizzoAna);
						}
					}
				}

				if (indirizzoRes != null) {
					indirizzoRes.setCsTbTipoIndirizzo(anagraficaBean.getResidenzaCsaMan().getTipoIndirizzoResidenza());
					List<CsAIndirizzo> listaIndirizzi = new ArrayList<CsAIndirizzo>();
					listaIndirizzi.add(indirizzoRes);
					anagraficaBean.getResidenzaCsaMan().setLstIndirizzi(listaIndirizzi);
					anagraficaBean.getResidenzaCsaMan().setLstIndirizziOld(listaIndirizzi);
				}

				// stato civile
				if (segnalato.getAnagrafica().getStato_civile() != null) {
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(segnalato.getAnagrafica().getStato_civile());
					CsTbStatoCivile statoCivile = confService.getStatoCivileByDescrizione(dto);
					if (statoCivile != null) {
						anagraficaBean.getStatoCivileGestBean().setItemSelezionato(statoCivile.getCod() + "|"+ statoCivile.getDescrizione());
						anagraficaBean.getStatoCivileGestBean().aggiungiSelezionato();
						anagraficaBean.getStatoCivileGestBean().salva();
					}
				}

				// medico
				String sMedico = segnalato.getMedico();
				CsCMedico medico = null;
				if (sMedico != null) {
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					try {
						Long idMedico = new Long(sMedico);
						dto.setObj(idMedico);
						medico = mediciSessionBean.findMedicoById(dto);

					} catch (Exception e) {
						logger.error("SOGGETTO SCHEDA SEGR: "
								+ ana.getCf() + " dato medico("+ sMedico + ") non è un ID, si procede a ricercare tramite descrizione");
					}

					// Se non trovo per id, lo ricerco per descrizione
					if (medico == null) {
						dto.setObj(sMedico.toUpperCase());
						medico = mediciSessionBean.findMedicoByDescrizione(dto);
					}

					anagraficaBean.addToListaMediciSoggetto(medico, null, null);

				}
			}
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			
			//Controllo privacy SISO 961
			DatiPrivacyDTO in = new DatiPrivacyDTO();
			fillEnte(in);
			in.setCf(segnalato.getAnagrafica().getCf());
			in.setOrganizzazioneId(ssScheda.getAccesso().getSsRelUffPcontOrg().getId().getOrganizzazioneId());
			DatiPrivacyDTO out = ssSchedaSegrService.findSchedaPrivacyByCfEnte(in);
			
			if( out == null)
				this.addMessage("Attenzione,", "Modulo privacy non sottoscritto", FacesMessage.SEVERITY_WARN);
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("scheda.faces");

		} catch (Exception e) {
			addError("Errore", "Errore durante il caricamento dell'anagrafica");
			logger.error("", e);
		}
	}

	private void nuovaDaAltriSerena(SchedaSegr schedaSegr) {
		Long id = schedaSegr.getId();
		String tipoScheda = schedaSegr.getProvenienza();
		
		if (id == null || "".equals(id)) {
			addWarning("Scegliere un soggetto o creare una cartella vuota", "");
			return;
		}

		initialize(null);
		modifica();
		
		try {
			AccessTableSchedaSegrSessionBeanRemote schedaSegrService = (AccessTableSchedaSegrSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");

			BaseDTO csDto = new BaseDTO();
			fillEnte(csDto);

			csDto.setObj(id);
			csDto.setObj2(tipoScheda);
			
			CsSchedeAltraProvenienza vistaCasiAltri = schedaSegrService.findVistaCasiAltriBySchedaIdProvenienza(csDto);
			schedaSegrBean.setVistaCasiAltri(vistaCasiAltri);

			// anagrafica
			DatiAnaBean anaBean = new DatiAnaBean();
			anaBean.setCodiceFiscale(vistaCasiAltri.getSegnalatoCodiceFiscale());
			anaBean.setCognome(vistaCasiAltri.getSegnalatoCognome());
			anaBean.setNome(vistaCasiAltri.getSegnalatoNome());

			anagraficaBean.setDatiAnaBean(anaBean);
			
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("scheda.faces");
		}
		catch (Exception e) {
			addError("Errore", "Errore durante il caricamento dell'anagrafica");
			logger.error("", e);
		}
	}
	
	public void carica(DatiCasoBean selectedCaso) {
		initializePresaInCarico(selectedCaso);

		logger.debug("*** INIZIO SchedaBean.carica ....");
		
		this.tabViewIndex = 0;

		BaseDTO b = new BaseDTO();
		fillEnte(b);
		b.setObj(selectedCaso.getAnagraficaId());

		CsASoggettoLAZY soggetto = soggettoService.getSoggettoById(b);

		if (soggetto != null) {
			initialize(soggetto);

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("scheda.faces");
			} catch (IOException e) {
				addError("Errore","Errore durante il reindirizzamento alla scheda Caso");
			}
		} else
			addWarningFromProperties("seleziona.warning");

		logger.debug("*** FINE SchedaBean.carica ....");

	}

	public void carica(CsASoggettoLAZY soggetto) {
		if (soggetto != null) {
			initialize(soggetto);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("scheda.faces");
			} catch (IOException e) {
				addError("Errore","Errore durante il reindirizzamento alla scheda Caso");
			}
		} else
			addWarningFromProperties("seleziona.warning");
	}
	
	public void carica(Long soggettoId) {
		if (soggettoId != null) {
			CsASoggettoLAZY soggetto = this.getSoggettoById(soggettoId);
			this.carica(soggetto);
		}
	}

	public void initializePresaInCarico(DatiCasoBean selectedCaso) {
		logger.info("Inizializza PresaInCarico per il caso:"+ selectedCaso.getAnagraficaId());
		presaInCaricoBean = new PresaInCaricoBean();
		presaInCaricoBean.initialize(selectedCaso);
		presaInCaricoBean.setReadOnly(isDisableModificaCartella());
		presaInCaricoBean.setAggiornaCartella(true);
	}

	public void initializePresaInCarico(CsASoggettoLAZY s) {
		presaInCaricoBean = null;
		if (s != null) {
			logger.info("Inizializza PresaInCarico per il caso:"+ s.getAnagraficaId());
			presaInCaricoBean = new PresaInCaricoBean();
			presaInCaricoBean.initialize(s);
			presaInCaricoBean.setReadOnly(isDisableModificaCartella());
			presaInCaricoBean.setAggiornaCartella(true);
			RequestContext.getCurrentInstance().update("schedaPICForm");
		}
	}

	
	
	public void initialize(CsASoggettoLAZY soggetto) {
		logger.debug("*** INIZIO SchedaBean.initialize ....");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
		if (soggetto != null && soggetto.getAnagraficaId() != null && soggetto.getAnagraficaId() != 0) {
			//SISO-1526 Caricare l'elenco di eventuali dati esterni per tab0
			String etichetta = schedaDatiEsterniSoggettoBean.getDatiEsterniFilterString(String.valueOf(this.tabViewIndex));
			if(etichetta != null)
				this.checkDatiEsterni = schedaDatiEsterniSoggettoBean.controllaPresenzaDatiEsterniSoggetto(etichetta);
			
			Long soggettoId = soggetto.getAnagraficaId();
			getSession().setAttribute("soggettoId", soggettoId);
			getSession().setAttribute("soggetto", soggetto);

			Long casoId = soggetto.getCsACaso().getId();
			
			initializePresaInCarico(soggetto);
			initializeProperties(soggettoId, casoId);
			noteBean.initialize(soggetto);
			
			 //SISO-1060
			if (soggetto.getCsACaso().getCsASoggetto().getDtMod() != null && soggetto.getCsACaso().getCsASoggetto().getUsrMod()!= null )
			{
				this.setDataModifica(sdf.format(soggetto.getCsACaso().getCsASoggetto().getDtMod()));
				this.setUsrModifica(super.getCognomeNomeUtente(soggetto.getCsACaso().getCsASoggetto().getUsrMod()));
			}else{
				this.setDataModifica(sdf.format(soggetto.getCsACaso().getCsASoggetto().getDtIns()));
				this.setUsrModifica(super.getCognomeNomeUtente(soggetto.getCsACaso().getCsASoggetto().getUserIns()));
			}		
			
			loadMessaggioRdC(soggetto);
			boolean beneficiarioRdC= getMsgBeneficiarioRdC()!=null ;
			consensoPrivacyMan = new ConsensoPrivacyMan(soggetto.getCsAAnagrafica().getCf(), getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getId(), soggetto.getCsAAnagrafica().isAnonimo(), beneficiarioRdC);
			
		} else {

			getSession().setAttribute("soggettoId", null);
			getSession().setAttribute("soggetto", null);
			
			initializePresaInCarico((CsASoggettoLAZY) null);
			initializeProperties(null, null);
			noteBean.initialize(null);
		
			consensoPrivacyMan = null;
		}

		checkPermessi(soggetto);

		disabilitaModifiche();
		
		identificativoScheda = soggetto != null ? soggetto.getCsACaso().getIdentificativo() : null;

		logger.debug("*** FINE SchedaBean.initialize ....");
		modalBean = new InterscambioDialogEventoBean();

	}

	private void initializeProperties(Long soggettoId, Long casoId) {
		anagraficaBean.initialize(soggettoId);
		datiDisabilitaBean.initialize(soggettoId);
		datiInvaliditaBean.initialize(soggettoId);
		parentiBean.initialize(soggettoId);
		datiSocialiBean.initialize(soggettoId, casoId);
		datiTribunaleBean.initialize(soggettoId);
		schedaPermessiBean.initialize(soggettoId);
		schedaSegrBean.initialize(soggettoId);
		// SISO-1115
		schedaDatiEsterniSoggettoBean.initialize(soggettoId);
		// -!-
	}

	public boolean isRenderNuovaCartella() {
		return checkPermesso(PermessiCartella.ITEM, PermessiCartella.CREAZIONE_CASO);
	}

	private void checkPermessi(CsASoggettoLAZY soggetto) {

		setDisableModificaCartella(false);
		if (soggetto != null) {
			setDisableModificaCartella(true);
			CsOOperatoreSettore opSettore = getCurrentOpSettore();
			AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getEjb(
					"CarSocialeA", "CarSocialeA_EJB",
					"AccessTableCasoSessionBean");
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(soggetto.getCsACaso().getId());
			List<CsACasoOpeTipoOpe> listaCasoOpeTipoOpe = casoService.getListaOperatoreTipoOpByCasoId(dto);
			boolean isCasoOperatore = false;
			boolean respExist = false;
			for (CsACasoOpeTipoOpe casoOpeTipoOpe : listaCasoOpeTipoOpe) {
				if (casoOpeTipoOpe.getDataFineApp().after(new Date())) {
					if (casoOpeTipoOpe.getFlagResponsabile() != null && casoOpeTipoOpe.getFlagResponsabile().booleanValue())
						respExist = true;
					if (casoOpeTipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getId() == opSettore.getId())
						isCasoOperatore = true;
				}
			}

			/*
			 * il caso è in modifica se: ho il permesso modifica tutti i casi
			 * settore se il caso non ha responsabile e l'operatore è il
			 * creatore del caso se sono presente nella lista degli operatori
			 * per quel caso
			 */
			if (checkPermesso(DataModelCostanti.PermessiCartella.ITEM, DataModelCostanti.PermessiCartella.MODIFICA_CASI_SETTORE)
					|| (!respExist && soggetto.getCsACaso().getUserIns().equals(opSettore.getCsOOperatore().getUsername()) || isCasoOperatore))
				setDisableModificaCartella(false);
		}

		if (soggetto == null) {
			renderTabAnagrafica = true;
			renderTabDatiSociali = false;
			renderTabInvalidita = false;
			renderTabParenti = false;
			renderTabDisabilita = false;
			renderTabTribunale = false;
			renderTabServizi = false;
			renderTabContributi = false;
			renderTabOperatori = false;
			renderTabNote = false;
			
			// 2018-02-01 Smartpeg - Maintenance
			renderedCasoEsportatoInterscambio = false;
			renderedCasoImportatoInterscambio = false;
		} else if (checkPermesso(PermessiCartella.ITEM,PermessiCartella.VISUALIZZAZIONE_DATI_RISERV)) {
			renderTabAnagrafica = true;
			renderTabDatiSociali = true;
			renderTabInvalidita = true;
			renderTabParenti = true;
			renderTabDisabilita = true;
			renderTabTribunale = true;
			renderTabServizi = false;
			renderTabContributi = false;
			renderTabOperatori = true;
			renderTabNote = true;
			
			// 2018-02-01 Smartpeg - Maintenance
			renderedCasoEsportatoInterscambio = esisteEventoDiTipo("EXPORT");
			renderedCasoImportatoInterscambio = esisteEventoDiTipo("IMPORT");
		} else {
			logger.info("Utente non abilitato alla visualizzazione dei dati riservati (permesso CarSociale-Accedi ai Dati Riservati): "
					+ "attivati solo i TAB ANAGRAFICA e OPERATORI");
			renderTabAnagrafica = true; /**/
			renderTabDatiSociali = false;
			renderTabInvalidita = false;
			renderTabParenti = false; /* SISO-609-DISATTIVARE?! */
			renderTabDisabilita = false;
			renderTabTribunale = false;
			renderTabServizi = false;
			renderTabContributi = false;
			renderTabOperatori = true; /**/
			renderTabNote = false;
			
			// 2018-02-01 Smartpeg - Maintenance
			renderedCasoEsportatoInterscambio = false;
			renderedCasoImportatoInterscambio = false;
		}
	}
	
	private void loadMessaggioRdC(CsASoggettoLAZY soggetto) {
		msgBeneficiarioRdC = null;
		if(soggetto!=null) {
			String cf = soggetto.getCsAAnagrafica().getCf();
			if(verificaBeneficiarioRdC(cf)) {
				msgBeneficiarioRdC = "Beneficiario di Reddito di Cittadinanza (Patto per il sociale)";
			}else {
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(soggetto.getAnagraficaId());
				dto.setObj2(cf);
				if(soggettoService.hasNucleoBeneficiarioRdC(dto))
					msgBeneficiarioRdC = "Nucleo familiare beneficiario di Reddito di Cittadinanza (Patto per il sociale)";
			}
		}
	}
	
	public void salva() {
		boolean salvato;	
		boolean validaCross = true;
		boolean nuovoInserimento = anagraficaBean.getSoggettoId()==null;
		// salvo tutti i bean
		if (nuovoInserimento) {
			if (this.isCodFiscaleInCs(anagraficaBean.getDatiAnaBean().getCodiceFiscale().trim().toUpperCase())){
				disableHead = false;
				return;
			}
		}else{
			
/*			//TODO:Validazione INCROCIATA DATI SOCIALI FAMILIARI/RISORSE ATTIVE!!! Solo per salvataggi successivi a quello dell'anagrafica
			completa = far vedere "Tipologia nucleo" ma rendere obbligatorio in inserimento cartella anche una situazione valida per le risorse
			sintetica = far vedere "Tipologia nucleo" 
			dettaglio = non far vedere tipologia nucleo e rendere obbligatorio l'inserimento di una situazione attiva per le risorse, esattamente come avviene ora per i dati sociali
			
		    if(renderTabParenti){	
				String gestioneParam = getGestioneTipoFamiglia();
				if(gestioneParam!=null && !gestioneParam.equalsIgnoreCase(DataModelCostanti.GestioneTipoFamiglia.SINTETICA)){
					ParentiComp p  = parentiBean.getFirstActiveComponent();
					if(p==null){
						this.addWarning(parentiBean.getNomeTab(), "Inserire una situazione valida");
						validaCross = false;
					}
				}
		    }*/
			
			if(renderTabTribunale && renderTabDatiSociali){
				int situazioniTrib = datiTribunaleBean.listaComponenti.size();
				for(ValiditaCompBaseBean comp: datiSocialiBean.listaComponenti) {
					DatiSocialiComp disComp = (DatiSocialiComp) comp;
					if(disComp.getIdInviante() != null){
						for(SelectItem se : disComp.getLstInviante()){
							if(se.getLabel().contains("Tribunale")){
							 SelectItemGroup sg = (SelectItemGroup) se;
								for(SelectItem si : sg.getSelectItems()){
									BigDecimal temp =	new BigDecimal(si.getValue().toString());
									if(situazioniTrib == 0 && disComp.getIdInviante().equals(temp)){
										this.addWarning("Tribunale", "Nei dati sociali è stato selezionata la voce 'Tribunale' come inviante: inserire almeno una situazione nel tab apposito.");
										validaCross = false;
									}
								}
							}
						}
					}	
				}
			}
		}
		
		
		if(validaCross){
			 salvato = (this.renderTabAnagrafica ? anagraficaBean.salva() : true)
					&& (this.renderTabParenti ? parentiBean.salva() : true)
					&& (this.renderTabDatiSociali ? datiSocialiBean.salva() : true)
					&& (this.renderTabInvalidita ? datiInvaliditaBean.salva() : true)
					&& (this.renderTabDisabilita ? datiDisabilitaBean.salva() : true)
					&& (this.renderTabTribunale ? datiTribunaleBean.salva() : true)
					&& (this.renderTabOperatori ? schedaPermessiBean.salva() : true)
					&& (this.renderTabNote ? noteBean.salva() : true);
		}else
			salvato = false;
			
		if (salvato) {
			// riinizializzo la scheda
			CsASoggettoLAZY sogg = anagraficaBean.getSoggetto();
			this.initialize(sogg);
			this.identificativoScheda = sogg.getCsACaso().getIdentificativo();

			if(nuovoInserimento && this.schedaSegrBean.getSsScheda()!=null)
				this.datiSocialiBean.nuovo();
			
			// refresh dei tab per farli renderizzare
			RequestContext.getCurrentInstance().update(this.getIdTabScheda());

			addInfoFromProperties("salva.ok");
			if(datiSocialiBean.isModificheNonSalvate()){
				modifica();
				addInfo("Dati Sociali", "Sono stati importati i dati sociali da "+this.getLabelSegrSociale()+": verificare eventuali informazioni mancanti e salvare.");
			}else
				disabilitaModifiche();
		}
	}

	public void refreshSchedaContent(){
		BaseDTO b = new BaseDTO();
		fillEnte(b);
		b.setObj(anagraficaBean.getSoggettoId());
	
		CsASoggettoLAZY soggetto = soggettoService.getSoggettoById(b);
	
		if (soggetto != null) {
			initialize(soggetto);
	
			// refresh dei tab per farli renderizzare
			RequestContext.getCurrentInstance().update("schedaContent");
	
		}
		//presaInCaricoBean.getCloseDialog();
	}

	public void modifica() {
		attivaSalvataggio = true;
		modificaButt = false;
		disableHead = true;
	}

	public void disabilitaModifiche() {
		// esco da modalità modifica
		disableHead = false;
		attivaSalvataggio = false;
		modificaButt = true;
	}

	public AnagraficaBean getAnagraficaBean() {
		return anagraficaBean;
	}

	public void setAnagraficaBean(AnagraficaBean anagraficaBean) {
		this.anagraficaBean = anagraficaBean;
	}

	public PresaInCaricoBean getPresaInCaricoBean() {
		return presaInCaricoBean;
	}

	public void setPresaInCaricoBean(PresaInCaricoBean presaInCaricoBean) {
		this.presaInCaricoBean = presaInCaricoBean;
	}

	public DatiDisabilitaBean getDatiDisabilitaBean() {
		return datiDisabilitaBean;
	}

	public void setDatiDisabilitaBean(DatiDisabilitaBean datiDisabilitaBean) {
		this.datiDisabilitaBean = datiDisabilitaBean;
	}

	public DatiInvaliditaBean getDatiInvaliditaBean() {
		return datiInvaliditaBean;
	}

	public void setDatiInvaliditaBean(DatiInvaliditaBean datiInvaliditaBean) {
		this.datiInvaliditaBean = datiInvaliditaBean;
	}

	public ParentiBean getParentiBean() {
		return parentiBean;
	}

	public void setParentiBean(ParentiBean parentiBean) {
		this.parentiBean = parentiBean;
	}

	public DatiSocialiBean getDatiSocialiBean() {
		return datiSocialiBean;
	}

	public void setDatiSocialiBean(DatiSocialiBean datiSocialiBean) {
		this.datiSocialiBean = datiSocialiBean;
	}

	public DatiTribunaleBean getDatiTribunaleBean() {
		return datiTribunaleBean;
	}

	public void setDatiTribunaleBean(DatiTribunaleBean datiTribunaleBean) {
		this.datiTribunaleBean = datiTribunaleBean;
	}

	public boolean isDisableModificaCartella() {
		return disableModificaCartella;
	}

	public void setDisableModificaCartella(boolean disableModifica) {
		this.disableModificaCartella = disableModifica;
	}

	

	public SchedaSegrBean getSchedaSegrBean() {
		return schedaSegrBean;
	}

	public void setSchedaSegrBean(SchedaSegrBean schedaSegrBean) {
		this.schedaSegrBean = schedaSegrBean;
	}

	public NoteBean getNoteBean() {
		return noteBean;
	}

	public void setNoteBean(NoteBean noteBean) {
		this.noteBean = noteBean;
	}

	public boolean isRenderTabAnagrafica() {
		return renderTabAnagrafica;
	}

	public boolean isRenderTabDatiSociali() {
		return renderTabDatiSociali;
	}

	public boolean isRenderTabInvalidita() {
		return renderTabInvalidita;
	}

	public boolean isRenderTabParenti() {
		return renderTabParenti;
	}

	public boolean isRenderTabDisabilita() {
		return renderTabDisabilita;
	}

	public boolean isRenderTabTribunale() {
		return renderTabTribunale;
	}

	public boolean isRenderTabServizi() {
		return renderTabServizi;
	}

	public boolean isRenderTabContributi() {
		return renderTabContributi;
	}

	public boolean isRenderTabOperatori() {
		return renderTabOperatori;
	}

	public boolean isRenderTabNote() {
		return renderTabNote;
	}

	public String getIdTabScheda() {
		return idTabScheda;
	}

	public void setIdTabScheda(String idTabScheda) {
		this.idTabScheda = idTabScheda;
	}

	public boolean isDisableHead() {
		return disableHead;
	}

	public void setDisableHead(boolean disableHead) {
		this.disableHead = disableHead;
	}

	public boolean isModificaButt() {
		return modificaButt;
	}

	public void setModificaButt(boolean modificaButt) {
		this.modificaButt = modificaButt;
	}

	public boolean isAttivaSalvataggio() {
		return attivaSalvataggio;
	}

	public void setAttivaSalvataggio(boolean attivaSalvataggio) {
		this.attivaSalvataggio = attivaSalvataggio;
	}

	protected CsTbCittadinanzaAcq getCittadinanzaAcq(Long id) {
		try {
			AccessTableConfigurazioneSessionBeanRemote service = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
							"AccessTableConfigurazioneSessionBean");
			if (id != null) {
				it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillEnte(d);
				d.setObj(new Long(id));
				CsTbCittadinanzaAcq fa = service.getCittadinanzaAcqById(d);
				return fa;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error",
					"Impossibile recuperare la tipologia di Cittadinanza Acquisita");
		}
		return null;
	}

	public Long getIdentificativoScheda() {
		return identificativoScheda;
	}

	public void setIdentificativoScheda(Long identificativoScheda) {
		this.identificativoScheda = identificativoScheda;
	}

	public InterscambioDialogEventoBean getModalBean() {
		return modalBean;
	}

	public void setModalBean(InterscambioDialogEventoBean modalBean) {
		this.modalBean = modalBean;
	}
	
	public int getTabViewIndex() {
		return tabViewIndex;
	}

	public void setTabViewIndex(int tabViewIndex) {
		this.tabViewIndex = tabViewIndex;
	}

	public SchedaPermessiBean getSchedaPermessiBean() {
		return schedaPermessiBean;
	}

	public void setSchedaPermessiBean(SchedaPermessiBean schedaPermessiBean) {
		this.schedaPermessiBean = schedaPermessiBean;
	}
    //SISO-1060
	
	public String getUsrModifica() {
		return usrModifica;
	}

	public String getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(String dataModifica) {
		this.dataModifica = dataModifica;
	}

	public void setUsrModifica(String usrModifica) {
		this.usrModifica = usrModifica;
	}

	// SISO-1115
	public SchedaDatiEsterniSoggettoBean getSchedaDatiEsterniSoggettoBean() {
		return schedaDatiEsterniSoggettoBean;
	}

	public void setSchedaDatiEsterniSoggettoBean(SchedaDatiEsterniSoggettoBean schedaDatiEsterniSoggettoBean) {
		this.schedaDatiEsterniSoggettoBean = schedaDatiEsterniSoggettoBean;
	}
	// -!-

	public String getMsgBeneficiarioRdC() {
		return msgBeneficiarioRdC;
	}

	public void setMsgBeneficiarioRdC(String msgBeneficiarioRdC) {
		this.msgBeneficiarioRdC = msgBeneficiarioRdC;
	}
	
	public StreamedContent getFilePrivacy(){
		DatiPrivacyPdfDTO dati = null;
		if(this.anagraficaBean!=null) {
			dati = anagraficaBean.getDatiReportPrivacy();
			dati.setRenderAccesso(false);
			dati.setRenderRiferimenti(false);
			dati.setRenderSegnalante(false);
		}
		ReportBean bean = (ReportBean)CsUiCompBaseBean.getReferencedBean("ReportBean");
		if(bean==null) bean = new ReportBean(); 
		
		return bean.getStampaPrivacy(dati, null, this.getSchedaSegrBean().getLabelSegnalato(), null);
	}

	public ConsensoPrivacyMan getConsensoPrivacyMan() {
		return consensoPrivacyMan;
	}

	public void setConsensoPrivacyMan(ConsensoPrivacyMan consensoPrivacyMan) {
		this.consensoPrivacyMan = consensoPrivacyMan;
	}
}
