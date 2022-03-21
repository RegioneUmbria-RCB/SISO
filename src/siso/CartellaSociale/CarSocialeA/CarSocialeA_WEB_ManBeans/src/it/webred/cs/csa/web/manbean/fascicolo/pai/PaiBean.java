package it.webred.cs.csa.web.manbean.fascicolo.pai;

import it.webred.cs.csa.ejb.client.AccessTablePaiPTISessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableComuniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.RelazioneDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneDettaglioDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.pai.CsPaiMastSoggDTO;
import it.webred.cs.csa.ejb.dto.pai.PaiSearchCriteria;
import it.webred.cs.csa.ejb.dto.pai.PaiSintesiDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.PaiAffidoStatoEnum;
import it.webred.cs.csa.ejb.dto.pai.base.CsPaiFaseChiusuraDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.CsPaiPTIDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.CsPaiPtiRevisioniDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.DettaglioMinore;
import it.webred.cs.csa.ejb.dto.pai.pti.DettaglioPTI;
import it.webred.cs.csa.ejb.dto.pai.pti.InserimentoConsuntivazioneDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.PaiPTIFaseEnum;
import it.webred.cs.csa.ejb.dto.pai.pti.PaiPTIMotivoRevisioneEnum;
import it.webred.cs.csa.ejb.dto.pai.pti.PaiPTITipoProrogaEnum;
import it.webred.cs.csa.ejb.dto.pai.pti.RichiestaDisponibilitaPaiPtiDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.StrutturaDisponibilitaDTO;
import it.webred.cs.csa.ejb.dto.relazione.RelazioneSintesiDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloBean;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompSecondoLivello;
import it.webred.cs.csa.web.manbean.fascicolo.FglInterventoBean;
import it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi.ErogInterventoRowBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventi.InterventiBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView.TipoInterventoManBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.Pai.PERIODO_TEMPORALE;
import it.webred.cs.data.DataModelCostanti.TipoRicercaSoggetto;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsPaiFaseChiusuraPK;
import it.webred.cs.data.model.CsPaiMastSogg;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.cs.data.model.CsTbMotivoChiusuraPai;
import it.webred.cs.data.model.CsTbProgettoAltro;
import it.webred.cs.data.model.CsTbSchedaMultidim;
import it.webred.cs.data.model.CsTbTipoPai;
import it.webred.cs.jsf.bean.DatiInterventoBean;
import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.interfaces.IPai;
import it.webred.cs.jsf.manbean.ComuneNazioneNascitaMan;
import it.webred.cs.jsf.manbean.ComuneNazioneResidenzaMan;
import it.webred.cs.jsf.manbean.RisorsaFamiliareBean;
import it.webred.cs.jsf.manbean.UserSearchBeanExt;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.json.JSONObject;
import org.primefaces.model.DualListModel;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaiBean extends FascicoloCompSecondoLivello implements IPai, Serializable {

	private static final long serialVersionUID = 6070604949953337370L;

	private static final String MESSAGES_ID = "messagesPai";

	private LazyListaPaiModel lazyListaPaiModel;
	private List<SelectItem> lstTipoPai;
	private List<SelectItem> lstMotivoChiusuraByTipoPai;
	private Long idxSelected;
	private CsDPai selectedPai;
	private String modalHeader;
	private String widgetVar;
	private DualListModel<RelazioneSintesiDTO> picklistRelazioni;
	private DualListModel<DatiInterventoBean> picklistInterventi;
	private List<RelazioneDTO> listaRelazioniDTO;

	// SISO-748
	private DualListModel<ErogInterventoRowBean> picklistErogazioni;
	private List<ErogInterventoRowBean> listaErogazioniByCaso;
	private List<String> listaOpzioniBen;
	private boolean onUpdateErogazioni = false;

	private boolean onUpdateRelazioni = false;
	private boolean onUpdateInterventi = false;
	private boolean onClosing = false;
	private boolean validaChiusura;
	private boolean fromFascicoloCartellaUtente = true;
	private boolean soggettoSelectedFromAnagrafe = false;
	private List<String> erogazioniNuoveAssociate;

	private List<SelectItem> lstRadioOptions;
	private List<SelectItem> lstArFfProgetti;

	// SISO-1131
	private CsTbProgettoAltro selectedProgettoAltro;
	private boolean abilitaMenuProgettiAltro = false;

	// inizio evoluzione-pai
	private TipoInterventoManBean tipoInterventoManBean;
	private List<SelectItem> listTipoInterventos = null;
	// fine evoluzione-pai

	// SISO-155
	private CsTbTipoPai tipoPai = null;
	private PaiAffidoBean paiAffidoBean = new PaiAffidoBean();
	private PaiSalBean paiSalBean = new PaiSalBean();
	private PaiPTIBean paiPTIBean = new PaiPTIBean();
	
	private int tabViewIndex = 0;

	// SISO-520
	private Date dataNuovoMonitoraggio;

	// SISO-1280
	private CsPaiMastSoggDTO soggRiferimentoPai;
	private List<CsPaiMastSoggDTO> altriSoggetti;
	private SessoBean sessoBeneficiario;
	private List<String> listaCittadinanze;
	private ComuneNazioneResidenzaMan comuneNazioneResidenzaMan = null;
	private ComuneNazioneNascitaMan comuneNazioneNascitaMan = new ComuneNazioneNascitaMan();
	private RisorsaFamiliareBean risorsaFamBean;
	private Boolean testataDisabled = false;
	private boolean nuovoInsSoggManuale = false;
	private CsPaiMastSoggDTO altroSoggettoTmp;
	private CsPaiMastSoggDTO soggettoSelezionato;
	private Boolean cambiaBeneficiarioRiferimento = false;
	private Long idCasoSoggEsterno = null;
	private boolean visualizzaConsuntivazione = false;
	public boolean provenienzaConsuntivazione = false;
	public InserimentoConsuntivazioneDTO selectedConsuntivazione;
	private String messaggioConsuntivazione;
	private String messConsuntivazioneChiusura;
	private List<RichiestaDisponibilitaPaiPtiDTO> lstProgettiAltriEntiNonEmergenza;
	private List<RichiestaDisponibilitaPaiPtiDTO> lstProgettiAltriEnti;

	private StrutturaDisponibilitaDTO strutturaAccettataDaEnte;
	private CsPaiPtiRevisioniDTO ptiRevisione;
	public Long motivoRevisione;
	public Long tipoProroga;
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private boolean isProgettoPTI = false;
	Date dataUltimaConsuntivazioneErog = new Date();
	private boolean procediSalvataggio = false;
	
	
	// FINE SISO_1280
	/* SISO-738 */
	protected AccessTableCatSocialeSessionBeanRemote catSocService = (AccessTableCatSocialeSessionBeanRemote) getCarSocialeEjb("AccessTableCatSocialeSessionBean");
	protected AccessTablePaiPTISessionBeanRemote paiPTIService = (AccessTablePaiPTISessionBeanRemote) getCarSocialeEjb("AccessTablePaiPTISessionBean");
	
	public PaiBean() {
		super();
	}
	// SISO 1280 fine

	// SISO 724
	public void onChangeTabView(TabChangeEvent tce) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Map<String, String> params = context.getExternalContext().getRequestParameterMap();
			TabView tabView = (TabView) tce.getComponent();
			String activeIndexValue = params.get(tabView.getClientId(context) + "_tabindex");

			this.tabViewIndex = Integer.parseInt(activeIndexValue);
		} catch (Exception ex) {
			this.tabViewIndex = 0;
		}
	}

	// SISO-155
	public void onChangeTipoProgetto(AjaxBehaviorEvent event) throws Exception {
		this.setReadOnly(false);
		long tipoProgettoId = (Long) ((javax.faces.component.UIInput) event.getComponent()).getValue();

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(tipoProgettoId);
		tipoPai = confService.findTipoPaiById(dto);
		selectedPai.setCsTbTipoPai(tipoPai);
		
		if (isClasseProgetto(PaiProgettiEnum.SAL)) {
			paiSalBean.nuovo();
		} else if (isClasseProgetto(PaiProgettiEnum.AFFIDO)) {
			if (fromFascicoloCartellaUtente) {
				paiAffidoBean.nuovo(idSoggetto);
			} else {
				if (soggRiferimentoPai != null) {
					CsASoggettoLAZY sogg = recuperaSoggetto(soggRiferimentoPai.getCf());
					if (sogg != null)
						paiAffidoBean.nuovo(sogg.getAnagraficaId());
				}
			}

		} else if (isClasseProgetto(PaiProgettiEnum.PTI)) {
			if (fromFascicoloCartellaUtente) {
				paiPTIBean.nuovo(csASoggetto);

			} else {
				
				if (soggRiferimentoPai != null) {
					CsASoggettoLAZY sogg = recuperaSoggetto(soggRiferimentoPai.getCf());
					if (sogg != null){
						paiPTIBean.nuovo(sogg);
				}else{
					if(getSoggRiferimentoPai().getAnnoNascita()!= null && getSoggRiferimentoPai().getAnnoNascita()!=0) {
						paiPTIBean.nuovo(soggRiferimentoPai.getAnnoNascita());
					}else {
						tipoPai = null;
						lstTipoPai = getLstTipoPai();
						this.selectedPai.getCsTbTipoPai().setId(-1);
						RequestContext.getCurrentInstance().update("frmProgettiIndividuali:paiExtComponent:tipoProgetto");
						addError("Errore", "Inserire la data di nascita del beneficiario");
					}
				}
			}
		}

		}
		RequestContext.getCurrentInstance().update("frmProgettiIndividuali:paiExtComponent:paiTabViewContainer");
		
	}

	@Override
	public void initializeData() {
		try {
			tabViewIndex = 0;
			onUpdateRelazioni = false;
			onUpdateInterventi = false;
			picklistRelazioni = null;
			picklistInterventi = null;
			onClosing = false;
			idCasoSoggEsterno = null;
			soggRiferimentoPai = null;
			// SISO-748
			picklistErogazioni = null;
			setOnUpdateErogazioni(false);
			
			lazyListaPaiModel = new LazyListaPaiModel();
			lazyListaPaiModel.setAccessoEsternoDatiCartella(isAccessoEsternoDatiCartella());
			lazyListaPaiModel.setFromFascicoloCartellaUtente(fromFascicoloCartellaUtente);
			if (fromFascicoloCartellaUtente) {
				lazyListaPaiModel.setCf(csASoggetto.getCsAAnagrafica().getCf());
				lazyListaPaiModel.setIdCaso(idCaso);
			}

			selectedPai = null;

			loadListaTipiIntervento();
			if (fromFascicoloCartellaUtente) {
				tipoInterventoManBean = new TipoInterventoManBean(listTipoInterventos,getFilterCategorie(catsocCorrenti));
			} else {
				tipoInterventoManBean = new TipoInterventoManBean(listTipoInterventos, "");
			}
			// fine evoluzione-pai

			// SISO-155
			tipoPai = null;

//			//SISO-520
			this.dataNuovoMonitoraggio = null;

			this.erogazioniNuoveAssociate = new ArrayList<String>();

			// SISO-1131
			this.setSelectedProgettoAltro(new CsTbProgettoAltro());
			comuneNazioneResidenzaMan = new ComuneNazioneResidenzaMan();
			testataDisabled = false;
			this.setReadOnly(false);
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	public CsTbTipoPai getTipoPai() {
		return tipoPai;
	}

	public void setTipoPai(CsTbTipoPai tipoPai) {
		this.tipoPai = tipoPai;
	}

	@Override
	protected void initializeData(Object data) {
		this.initializeData();
	}

	@Override
	public void nuovo() {
		picklistRelazioni = null;
		picklistInterventi = null;
		onUpdateRelazioni = false;
		onUpdateInterventi = false;
		onClosing = false;
		modalHeader = "Nuovo progetto";
		selectedPai = new CsDPai();
		selectedPai.setTipoProgettoId(0L);
		selectedPai.setTipoBeneficiario(DataModelCostanti.ListaBeneficiari.INDIVIDUALE);
		selectedPai.setTotBeneficiari(Integer.valueOf(1));
		// SISO-748
		picklistErogazioni = null;
		setOnUpdateErogazioni(false);
		this.setReadOnly(false);
		// LD
		progettiIndividualiExtBean = (ProgettiIndividualiExtBean)getBeanReference("progettiInvidualiExt");

		// SISO-1280 Inizio
		loadListaTipiIntervento();
		if (fromFascicoloCartellaUtente) {
			getPicklistRelazioni();
			getPicklistInterventi();
			getPicklistErogazioni();

			CsAAnaIndirizzo residenza = findIndirizzoResidenzaCaso(csASoggetto);
			String via = residenza != null ? residenza.getLabelIndirizzo() : null;
			soggRiferimentoPai = new CsPaiMastSoggDTO(csASoggetto, via, getCasoComuneResidenza(residenza), residenza.getStatoCod(), true);

			this.valorizzaResidenzaMan();

			this.sessoBeneficiario = new SessoBean();
			this.sessoBeneficiario.setSesso(soggRiferimentoPai.getSesso());

			tipoInterventoManBean = new TipoInterventoManBean(listTipoInterventos, getFilterCategorie(this.catsocCorrenti));

		} else {
			if (soggRiferimentoPai != null) {
				CsASoggettoLAZY sogg = this.recuperaSoggetto(soggRiferimentoPai.getCf());

				idCasoSoggEsterno = sogg != null && sogg.getCsACaso() != null ? sogg.getCsACaso().getId() : null;

				this.sessoBeneficiario = new SessoBean();
				this.sessoBeneficiario.setSesso(soggRiferimentoPai.getSesso());
				// se l'anagrafica selezionata e' presente nella cartella sociale
				if (idCasoSoggEsterno != null) {
					soggRiferimentoPai.setCasoId(idCasoSoggEsterno);
					getPicklistRelazioni();
					getPicklistInterventi();
					getPicklistErogazioni();
					
					List<CsCCategoriaSociale> lstCatSoc = loadCatSocialiAttuali(sogg.getAnagraficaId());
					tipoInterventoManBean = new TipoInterventoManBean(listTipoInterventos, getFilterCategorie(lstCatSoc));
					progettiIndividualiExtBean.initializeData(sogg, lstCatSoc);

				} else {
					getPicklistRelazioniEsterneFromCF(soggRiferimentoPai.getCf());
					getPicklistInterventiEsterniFromCF(soggRiferimentoPai.getCf());
					getPicklistErogazioniEsterniFromCF(soggRiferimentoPai.getCf());

					tipoInterventoManBean = new TipoInterventoManBean(listTipoInterventos, "");
					progettiIndividualiExtBean.initializeData(soggRiferimentoPai);
				}
			} else {
				// inizializzazione delle varie picklist
				initPickList();
				soggRiferimentoPai = new CsPaiMastSoggDTO();
				soggRiferimentoPai.setIntestatario(true);
				this.sessoBeneficiario = new SessoBean();
				this.altroSoggettoTmp = new CsPaiMastSoggDTO(false);
				tipoInterventoManBean = new TipoInterventoManBean(listTipoInterventos, "");
			}

			selectedPai.setTipoBeneficiario(DataModelCostanti.ListaBeneficiari.INDIVIDUALE);
			selectedPai.setTotBeneficiari(Integer.valueOf(1));
			this.setCambiaBeneficiarioRiferimento(false);

		}
		// SISO-1280 Fine

		// SISO-155
		tipoPai = null;

		// SISO_1034
		selectedPai.setMonitoraggioObiettivi(false);
		this.setSelectedProgettoAltro(new CsTbProgettoAltro());
	}

	@Override
	public void carica() {
		// per la modifica
		try {
			picklistRelazioni = null;
			picklistInterventi = null;
			onUpdateRelazioni = false;
			onUpdateInterventi = false;
			onClosing = false;
			this.lstMotivoChiusuraByTipoPai = null;

			this.motivoRevisione = null;
			this.tipoProroga = null;

			//ListaDatiPaiDTO sel  = filteredPais.get(idxSelected);
			Long diarioId = idxSelected;
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(diarioId);
			selectedPai = diarioService.getPaiById(dto);
			selectedPai.getCsDDiario().setVisSecondoLivello(null); // SISO-812

			tipoPai = selectedPai.getCsTbTipoPai();
			soggRiferimentoPai = toDto(selectedPai.getBeneficiari().get(0));
			this.sessoBeneficiario = new SessoBean();
			this.sessoBeneficiario.setSesso(soggRiferimentoPai.getSesso());

			this.valorizzaResidenzaMan();

			if (soggRiferimentoPai.getCasoId() != null)
				idCasoSoggEsterno = soggRiferimentoPai.getCasoId();
			altriSoggetti = new ArrayList<CsPaiMastSoggDTO>();

			if (selectedPai.getBeneficiari().size() > 1) {
				CsPaiMastSoggDTO temp;
				for (int i = 1; i <= selectedPai.getBeneficiari().size() - 1; i++) {
					temp = toDto(selectedPai.getBeneficiari().get(i));
					altriSoggetti.add(temp);
				}
			}

			/// LD
			progettiIndividualiExtBean = (ProgettiIndividualiExtBean) this.getBeanReference("progettiInvidualiExt");

			loadListaTipiIntervento();
			if (fromFascicoloCartellaUtente) {
				tipoInterventoManBean = new TipoInterventoManBean(listTipoInterventos,getFilterCategorie(this.catsocCorrenti));

			} else {
				dto.setObj(soggRiferimentoPai.getCf());
				CsASoggettoLAZY sogg = soggettoService.getSoggettoByCF(dto);
				if (sogg != null) {
					List<CsCCategoriaSociale> lstCatSoc = loadCatSocialiAttuali(sogg.getAnagraficaId());
					tipoInterventoManBean = new TipoInterventoManBean(listTipoInterventos,getFilterCategorie(lstCatSoc));
					progettiIndividualiExtBean.initializeData(sogg, lstCatSoc);

				} else {
					String filteredCatSoc = "";
					if (isClasseProgetto(PaiProgettiEnum.PTI)) {
						BaseDTO dto2 = new BaseDTO();
						fillEnte(dto2);
						dto2.setObj(DataModelCostanti.TipiCategoriaSociale.FAMIGLIA_MINORI_ID);
						filteredCatSoc = catSocService.getCategoriaSocialeById(dto2);
					}
					tipoInterventoManBean = new TipoInterventoManBean(listTipoInterventos, filteredCatSoc);
					progettiIndividualiExtBean.initializeData(soggRiferimentoPai);
				}
			}

			// SISO-748
			picklistErogazioni = null;
			setOnUpdateErogazioni(false);

			// SISO-1280 Inizio
			if (fromFascicoloCartellaUtente) {
				getPicklistRelazioni();
				getPicklistInterventi();
				getPicklistErogazioni();
			} else {
				if (idCasoSoggEsterno != null) {
					getPicklistRelazioni();
					getPicklistInterventi();
					getPicklistErogazioni();
				} else {
					getPicklistRelazioniEsterneFromCF(soggRiferimentoPai.getCf());
					getPicklistInterventiEsterniFromCF(soggRiferimentoPai.getCf());
					getPicklistErogazioniEsterniFromCF(soggRiferimentoPai.getCf());
				}
			}
			this.dataNuovoMonitoraggio = null; // SISO-520

			// SISO-155
			
			if (isClasseProgetto(PaiProgettiEnum.AFFIDO)) {
				paiAffidoBean.findAffidoByPai(selectedPai.getDiarioId(), idSoggetto);
			} else if (isClasseProgetto(PaiProgettiEnum.SAL)) {
				paiSalBean.findSALByPai(selectedPai.getDiarioId(), idSoggetto);
			} else if (isClasseProgetto(PaiProgettiEnum.PTI)) {
				this.ptiRevisione = null;
				paiPTIBean.caricaPTI(selectedPai.getDiarioId(), soggRiferimentoPai);
				this.setStrutturaAccettataDaEnte(paiPTIBean.getStrutturaSelezionata());
			}
			
			modalHeader = selectedPai.isClosed() ? "Progetto chiuso:" : "Modifica progetto:";
			modalHeader += "	[" + selectedPai.getDiarioId() + "]";

			// SISO-1034: per gestire il monitoraggio degli obiettivi prima della modifica
			if (selectedPai.getMonitoraggioObiettivi() == null)
				selectedPai.setMonitoraggioObiettivi(false);

			// SISO-1131
			CsTbProgettoAltro altro = this.selectedPai.getCsTbProgettoAltro() != null ? this.selectedPai.getCsTbProgettoAltro() : new CsTbProgettoAltro();
			this.setSelectedProgettoAltro(altro);

//			RequestContext.getCurrentInstance().update("frmProgettiIndividuali:paiExtComponent:paitabview:panelIntervento:interventoTree");	
			RequestContext.getCurrentInstance().update("frmProgettiIndividuali:paiExtComponent:paitabview");

		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	private CsPaiMastSoggDTO toDto(CsPaiMastSogg source) {
		if (source == null) {
			return null;
		}

		CsPaiMastSoggDTO target = new CsPaiMastSoggDTO();
		String[] ignore = { "pai" };
		BeanUtils.copyProperties(source, target, ignore);
		return target;
	}

	private CsPaiMastSogg toEntity(CsPaiMastSoggDTO source) {
		if (source == null) {
			return null;
		}

		CsPaiMastSogg target = new CsPaiMastSogg();
		String[] ignore = {};
		BeanUtils.copyProperties(source, target, ignore);
		return target;
	}

	@Override
	public void caricaChiudi() {
		// chiudi();
		carica();
		modalHeader = "Chiusura progetto: [" + selectedPai.getDiarioId() + "]";
		onClosing = true;
	}

	public void inizializeNuovoSoggetto(DatiUserSearchBean datiUserSearchBean) {
		loadSoggettoPaiSelezionato(datiUserSearchBean);
		nuovo();
	}

	public boolean isClosed() {
		boolean closed = false;
		if (this.onClosing)
			closed = this.selectedPai.isClosed() && this.validaChiusura;
		else
			closed = this.selectedPai.isClosed();
		return closed;
	}

	@Override
	public void chiudi() {
		if (!validaChiusuraPai()) {
			this.validaChiusura = false;
			return;
		}
		this.validaChiusura = true;
		verificaEsistenzaConsuntivazioni();
		  if(procediSalvataggio) {
				salva();
		  }
		
		
	
	}

	@Override
	protected void save() {

		if (!validaTestata()) {
			return;
		}

		if (!validaPai())
			return;

		// SISO-155
		if (isClasseProgetto(PaiProgettiEnum.AFFIDO)) {
			String valida = paiAffidoBean.validaAffidoPai(idSoggetto, selectedPai.getCsDDiario().getDtAttivazioneDa());
			if (valida != null) {
				addError("Errore", valida);
				return;
			}
		}
		// SISO-1257
		if (isClasseProgetto(PaiProgettiEnum.SAL)) {
			// devo verificare se ci sono le attività salvate in funzione della fase
			String valida = "";
			valida = paiSalBean.validaSALPai(idSoggetto, selectedPai.getCsDDiario().getDtAttivazioneDa(), picklistRelazioni);

			if (valida != null) {
				addError("Errore", valida);
				return;
			}
		}

		if (isClasseProgetto(PaiProgettiEnum.PTI)) {
			
			String valida = paiPTIBean.validaPTIPai(soggRiferimentoPai.getAnnoNascita());
			
			if (valida != null) {
				addError("Errore", valida);
				return;
			}
		}

		try {
			// SISO-1131
			if (this.selectedProgettoAltro != null) {
				// SISO-1131 devo salvare nuovo progetto altro.
				this.selectedPai.setCsTbProgettoAltro(null);
				if (selectedProgettoAltro.getId() == 0 && selectedProgettoAltro.getDescrizione() != null) {
					// salvataggio
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(this.selectedProgettoAltro);
					selectedProgettoAltro = interventoService.salvaProgettoAltro(dto);

				}
				if (selectedProgettoAltro.getId() > 0) {
					this.selectedPai.setCsTbProgettoAltro(selectedProgettoAltro);
				}
			}

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);

			// SISO-520
			selectedPai.setDataMonitoraggio(this.dataNuovoMonitoraggio);

			// SISO-1275
			if (selectedPai.getCsTbMotivoChiusuraPai() != null && selectedPai.getCsTbMotivoChiusuraPai().getId() > 0) {
				selectedPai.setMotivoChiusura(this.selectedPai.getCsTbMotivoChiusuraPai().getDescrizione());
			} else {
				selectedPai.setCsTbMotivoChiusuraPai(null);
			}

			if (selectedPai.getDiarioId() != null) {

				Set<CsPaiMastSogg> soggSet = new HashSet<CsPaiMastSogg>();
				soggSet.add(toEntity(soggRiferimentoPai));
//				selectedPai.addBeneficiario(toEntity(soggRiferimentoPai));

				if (altriSoggetti != null && altriSoggetti.size() > 0) {
					for (CsPaiMastSoggDTO soggAltro : altriSoggetti) {

						soggAltro.setDiarioId(selectedPai.getDiarioId());
						soggSet.add(toEntity(soggAltro));
//						selectedPai.addBeneficiario(toEntity(soggAltro));
					}
				}
				selectedPai.setCsPaiBeneficiari(soggSet);

				dto.setObj(selectedPai);

				if (this.selectedPai.getMonitoraggioObiettivi())
					this.pulisciObiettivi();
				this.valorizzaSecondoLivello(selectedPai.getCsDDiario());

				diarioService.updatePai(dto);

			} else {

				CsOOperatoreSettore opSettore = getCurrentOpSettore();
				BaseDTO dtoBeneficiari = new BaseDTO();
				fillEnte(dtoBeneficiari);

				dto.setObj(selectedPai);
				if (fromFascicoloCartellaUtente) {
					dto.setObj2(idCaso);
				} else {
					dto.setObj2(idCasoSoggEsterno);
				}
				dto.setObj3(opSettore);

				if (this.selectedPai.getMonitoraggioObiettivi())
					this.pulisciObiettivi();
				this.valorizzaSecondoLivello(selectedPai.getCsDDiario());

				selectedPai = diarioService.savePai(dto);

				// SISO 1280: Aggiungo i beneficiari all'entita CsDPai
				List<CsPaiMastSogg> lstBeneficiari = new ArrayList<CsPaiMastSogg>();

				soggRiferimentoPai.setDiarioId(selectedPai.getDiarioId());
//				
				if (this.comuneNazioneResidenzaMan != null
						&& this.comuneNazioneResidenzaMan.getComuneResidenzaMan().getComune() != null) {
					soggRiferimentoPai.setNazioneResidenzaNonDefinita(false);
					ObjectMapper mapper = new ObjectMapper();
					try {
						soggRiferimentoPai.setComuneResidenza(mapper
								.writeValueAsString(comuneNazioneResidenzaMan.getComuneResidenzaMan().getComune()));
					} catch (Exception e) {
						logger.error(e);
					}
				}

				if (fromFascicoloCartellaUtente) {
					soggRiferimentoPai.setCasoId(idCaso);
				} else {
					soggRiferimentoPai.setCasoId(idCasoSoggEsterno);
				}
				soggRiferimentoPai.setSesso(this.sessoBeneficiario.getSesso());
				lstBeneficiari.add(toEntity(soggRiferimentoPai));

				if (altriSoggetti != null && altriSoggetti.size() > 0) {
					Long idCasoSoggAltro = null;
					for (CsPaiMastSoggDTO soggAltro : altriSoggetti) {
						soggAltro.setDiarioId(selectedPai.getDiarioId());

						CsASoggettoLAZY sogg = this.recuperaSoggetto(soggAltro.getCf());
						idCasoSoggAltro = sogg != null && sogg.getCsACaso() != null ? sogg.getCsACaso().getId() : null;
						soggAltro.setCasoId(idCasoSoggAltro);

						lstBeneficiari.add(toEntity(soggAltro));

					}
				}
				dtoBeneficiari.setObj(lstBeneficiari);
				diarioService.saveBeneficiariPai(dtoBeneficiari);

			}

			/*
			 * //SISO-1280 Devo impostare la cittadinza del beneficiario di riferimento nel
			 * Pai (CsDPAI) non è stata eliminata perchè //probabilmente necessaria per
			 * l'esportazione if (this.selectedPai.getCittadinanza() != null &&
			 * "".equals(selectedPai.getCittadinanza().trim())) {
			 * selectedPai.setCittadinanza(soggRiferimentoPai.getCittadinanza()); }
			 */

			// SISO-155
			if (isClasseProgetto(PaiProgettiEnum.AFFIDO)) {
				paiAffidoBean.salva(selectedPai.getDiarioId());
			} else if (isClasseProgetto(PaiProgettiEnum.SAL)) {
				paiSalBean.salva(selectedPai.getDiarioId());
			} else if (isClasseProgetto(PaiProgettiEnum.PTI)) {
//				paiPTIBean.salva(selectedPai.getDiarioId(), soggRiferimentoPai);
				// Recupero le date del Diario
				Date da = this.selectedPai.getCsDDiario().getDtAttivazioneDa();
				Date a = this.selectedPai.getCsDDiario().getDtChiusuraDa();

				if (ptiRevisione != null) {
					ptiRevisione.setMotivoRevisione(
							PaiPTIMotivoRevisioneEnum.getDescrizioneFromValore(this.motivoRevisione));
					if (this.tipoProroga != null)
						ptiRevisione.setProroga(PaiPTITipoProrogaEnum.getDescrizioneFromValore(this.tipoProroga));
				}
				paiPTIBean.salva(selectedPai.getDiarioId(), soggRiferimentoPai, this.picklistInterventi,
						this.picklistErogazioni, da, a, ptiRevisione, isClosed());

			}

			if (paiSalBean.getWarnigSalvataggio() != null && !paiSalBean.getWarnigSalvataggio().isEmpty()) {
				addWarning("Salvataggio", paiSalBean.getWarnigSalvataggio());

			}

			this.updateInterventi();
			this.updateRelazioni();
			this.updateErogazioni(); // SISO-748

			FascicoloBean fbean = (FascicoloBean) getReferencedBean("fascicoloBean");
			// inizio modifica evoluzione-pai
			if (fbean != null && fbean.getInterventiBean() != null) {
				fbean.getInterventiBean().refreshListaInterventi(null);
			}

			addInfoFromProperties("salva.ok");
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);

			initializeData();

		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
	}

	private boolean validaPai() {
		boolean ok = true;
		List<String> errors = this.valida();
		for (String err : errors) {
			ok = false;
			addError("Problemi salvataggio Progetto individuale", err);
		}
		return ok;
	}

	public boolean validaTestata() {
		boolean valido = true;

		List<String> lstcf = new ArrayList<String>();
		if (!soggRiferimentoPai.isValorizzato()) {
			addWarning("Beneficiari", "Anagrafica incompleta del soggetto beneficiario di riferimento");
			valido = false;
		} else if (!validaCittadinanza(soggRiferimentoPai.getCittadinanza())) {
			addWarning("Beneficiari", "La cittadinanza del soggetto beneficiario di riferimento non è più valida");
			valido = false;
		} else if (!StringUtils.isEmpty(soggRiferimentoPai.getSecondaCittadinanza())
				&& !validaCittadinanza(soggRiferimentoPai.getSecondaCittadinanza())) {
			addWarning("Beneficiari",
					"La seconda cittadinanza del soggetto beneficiario di riferimento non è più valida");
			valido = false;
		} else
			lstcf.add(soggRiferimentoPai.getCf().toUpperCase());

		if (!this.isUnicoBeneficiario()) {

			if (selectedPai.getTotBeneficiari() == null || selectedPai.getTotBeneficiari().intValue() == 0) {
				addWarning("Beneficiari", "Il totale dei soggetti beneficiari è un campo obbligatorio");
				valido = false;
			}

			if (!getAltriSoggetti().isEmpty()) {
				for (CsPaiMastSoggDTO seb : this.getAltriSoggetti()) {
					if (!seb.isValorizzato()) {
						addWarning("Beneficiari",
								"Dati incompleti per il soggetto: " + seb.getCognome() + " " + seb.getNome());
						valido = false;
					}

					// Verifica che non ci siano soggetti uguali
					if (!lstcf.contains(seb.getCf().toUpperCase()))
						lstcf.add(seb.getCf().toUpperCase());
					else {
						addWarning("Beneficiari", "Beneficiario duplicato: " + seb.getCf());
						valido = false;
					}
				}

				Integer countAnagrafiche = this.getAltriSoggetti().size() + 1;
				if (countAnagrafiche.compareTo(selectedPai.getTotBeneficiari()) > 0) {
					addWarning("Beneficiari",
							"Il totale di beneficiari elencati, compreso quello di riferimento, non può superare il valore dichiarato nel campo 'num.totale soggetti beneficiari' ");
					valido = false;
				}
			}
		}

		return valido;

	}

	private boolean validaCittadinanza(String cittadinanza) {
		AmTabNazioni nazione = luoghiService.getNazioneByNazionalita(cittadinanza);
		return nazione != null && nazione.getAttivo();
	}

	private void pulisciObiettivi() {

		this.selectedPai.setVerificaOgni(BigDecimal.ZERO);
		this.selectedPai.setVerificaUnitaMisura("Giorni");
		this.selectedPai.setObiettiviBreve("");
		this.selectedPai.setObiettiviMedio("");
		this.selectedPai.setObiettiviLungo("");
		this.selectedPai.setDataMonitoraggio(null);
		this.selectedPai.setMotivazioniProgetto("");// SISO-1172
	}

	private boolean validaChiusuraPai() {
		boolean ok = true;
		List<String> errors = this.validaChiusura();
		for (String err : errors) {
			ok = false;
			addError(err, "");
		}
		return ok;
	}
	private void verificaEsistenzaConsuntivazioni() {
		 this.procediSalvataggio =false;
		dataUltimaConsuntivazioneErog= this.paiPTIBean.getDataUltimaConsuntivazione();
		//DEVO RECUPERARE, se Esiste l'ultima data di consuntivazione e confronto la data di chiusura 
		// se la data di consuntivazione è antecedente alla data di chiusura: avvertimento che la data di chiusura verrà allineata alla data di ultima
		// Nel caso di progetto PTI
		if (dataUltimaConsuntivazioneErog != null) {
			if (dataUltimaConsuntivazioneErog.before(this.selectedPai.getCsDDiario().getDtChiusuraDa())) {
				messConsuntivazioneChiusura = "L'ultima data di consuntivazione è antecedente la data attuale : " + formatter.format(dataUltimaConsuntivazioneErog)
				     + ". Sei sicuro di voler impostare la data di chiusura progetto alla data di ultima consuntivazione ?";
				
				RequestContext.getCurrentInstance().execute("PF('confermaDataChiusura').show();");
				
			}else {
				 this.procediSalvataggio =true;
			}
		}
		else {
			 this.procediSalvataggio =true;
		}
			
		
	}
	

	// SISO-520
	public void salvataggioConMonitoraggio() {
		if (this.dataNuovoMonitoraggio == null) {
			addError("Errore PAI", "Indicare la data di nuovo monitoraggio");
		} else {
			salva();
		}
	}

	public void salvataggioSenzaMonitoraggio() {
		if (this.dataNuovoMonitoraggio != null)
			addError("Errore PAI",
					"Impossibile inserire la data di nuovo monitoraggio. E' necessario monitorare prima gli obiettivi");
		else
			salva();
	}

	@Override
	public void salvaGestioneMonitoraggioObiettivi() {
		aggiornaDatiInseriti();

		if (this.selectedPai.getMonitoraggioObiettivi())
			this.salvataggioSenzaMonitoraggio();
		else
			RequestContext.getCurrentInstance().execute("PF('confermaSalvataggioDettaglioPai').show();");
	}

	public void aggiornaDatiInseriti() {

		soggRiferimentoPai.setSesso(this.sessoBeneficiario.getSesso());

		if (this.comuneNazioneResidenzaMan != null && this.comuneNazioneResidenzaMan.isComune()
				&& this.comuneNazioneResidenzaMan.getComuneMan().getComune() != null) {
			ObjectMapper om = new ObjectMapper();
			try {
				this.soggRiferimentoPai.setNazioneResidenzaNonDefinita(false);
				this.soggRiferimentoPai.setNazioneResidenza(null);
				this.soggRiferimentoPai.setComuneResidenza(
						om.writeValueAsString(this.comuneNazioneResidenzaMan.getComuneMan().getComune()));
			} catch (Exception ex) {

			}
		} else if (this.comuneNazioneResidenzaMan != null && this.comuneNazioneResidenzaMan.isNazione()
				&& this.comuneNazioneResidenzaMan.getNazioneMan().getNazione() != null) {

			this.soggRiferimentoPai.setNazioneResidenza(
					this.comuneNazioneResidenzaMan.getNazioneMan().getNazione().getCodIstatNazione());
			this.soggRiferimentoPai.setComuneResidenza(null);

		}

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
	public String getModalHeader() {
		return modalHeader;
	}

	@Override
	public void setModalHeader(String modalHeader) {
		this.modalHeader = modalHeader;
	}

	@Override
	public String getWidgetVar() {
		return widgetVar;
	}

	@Override
	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}

	@Override
	public CsDPai getSelectedPai() {
		return selectedPai;
	}

	@Override
	public void setSelectedPai(CsDPai pai) {
		this.selectedPai = pai;
	}

	private List<SelectItem> generateListTipoPai() {
		lstTipoPai = new ArrayList<SelectItem>();
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		List<KeyValueDTO> lst = confService.getTipoPai(bo);
		lstTipoPai = this.convertiLista(lst);
		return lstTipoPai;
	}

	@Override
	public List<SelectItem> getLstTipoPai() {
		if (lstTipoPai == null || lstTipoPai.isEmpty())
			lstTipoPai = generateListTipoPai();
		List<SelectItem> out = new ArrayList<SelectItem>();
		out.addAll(lstTipoPai);
		return out;
	}

	@Override
	public List<SelectItem> getLstTipoPaiFiltro() {
		if (lstTipoPai == null || lstTipoPai.isEmpty())
			lstTipoPai = generateListTipoPai();
		List<SelectItem> out = new ArrayList<SelectItem>();
		out.add(new SelectItem(null, ""));
		out.addAll(lstTipoPai);
		return out;
	}

	@Override
	public List<SelectItem> getLstMotivoChiusuraByTipoPai() {
		Long tipoPaiId = this.selectedPai.getCsTbTipoPai().getId();
		if ( tipoPaiId != 0) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(tipoPaiId);
			List<KeyValueDTO> lst = confService.getLstMotivoChiusuraPai(dto);
			lstMotivoChiusuraByTipoPai = this.convertiLista(lst);
		}else lstMotivoChiusuraByTipoPai = new ArrayList<SelectItem>();
		return lstMotivoChiusuraByTipoPai;
	}

	@Override
	public List<SelectItem> getStatusOptions() {
		List<SelectItem> options = new ArrayList<SelectItem>();

		options.add(new SelectItem("", ""));
		options.add(new SelectItem("true", "sì"));
		options.add(new SelectItem("false", "no"));

		return options;
	}

	@Override
	public Long getIdxSelected() {
		return this.idxSelected;
	}

	@Override
	public void setIdxSelected(Long idxSelected) {
		this.idxSelected = idxSelected;

	}

	public Long getMotivoRevisione() {
		return motivoRevisione;
	}

	public void setMotivoRevisione(Long motivoRevisione) {
		this.motivoRevisione = motivoRevisione;
	}

	public Long getTipoProroga() {
		return tipoProroga;
	}

	public void setTipoProroga(Long tipoProroga) {
		this.tipoProroga = tipoProroga;
	}

	private List<String> validaChiusura() {
		ArrayList<String> errors = new ArrayList<String>();

		if (StringUtils.isNotEmpty(this.selectedPai.getObiettiviBreve()) && this.selectedPai.getRaggiuntiBreve() == 0) {
			errors.add("Inserire il raggiungimento degli obiettivi a breve termine");
		}
		if (StringUtils.isNotEmpty(this.selectedPai.getObiettiviMedio()) && this.selectedPai.getRaggiuntiMedio() == 0) {
			errors.add("Inserire il raggiungimento degli obiettivi a medio termine");
		}
		if (StringUtils.isNotEmpty(this.selectedPai.getObiettiviLungo()) && this.selectedPai.getRaggiuntiLungo() == 0) {
			errors.add("Inserire il raggiungimento degli obiettivi a lungo termine");
		}
		// SISO-520
		if (this.selectedPai.getCsDDiario().getDtChiusuraDa() != null) {
			long diff = this.selectedPai.getCsDDiario().getDtChiusuraDa().getTime()
					- this.selectedPai.getCsDDiario().getDtAttivazioneDa().getTime();
			if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 7) {
				errors.add("La differenza tra la data di chiusura e la data di attivazione deve essere maggiore o uguale a sette giorni.");
			}
		}

		if (this.selectedPai.getCsTbMotivoChiusuraPai().getId() <= 0) {
			errors.add("Indicare il motivo della chiusura");
		} else if (StringUtils.equalsIgnoreCase("Altro", this.selectedPai.getMotivoChiusura())
				&& StringUtils.isEmpty(this.selectedPai.getMotivoChiusuraSpec())) {
			errors.add("Specificare la descrizione del motivo della chiusura (avendo indicato 'Altro')");
		}

		if (this.selectedPai.getCsDDiario().getDtChiusuraDa() == null
				|| this.selectedPai.getCsDDiario().getDtChiusuraDa().equals(DataModelCostanti.END_DATE)) {
			errors.add("Indicare la data di chiusura");
		}

		// verificare se ci sono interventi associati ancora aperti e che la relativa
		// data di chiuera sia compresa nella data del progetto
		if (this.picklistInterventi != null && this.picklistInterventi.getTarget() != null) {
			boolean aperti = false;
			Date maxDataChiusuraFA = null;

			for (DatiInterventoBean i : this.picklistInterventi.getTarget()) {
				for (CsFlgIntervento f : i.getListaFogli()) {
					if (f.getCsDDiario().getDtChiusuraDa() == null) {
						aperti = true;
					}
					if (!aperti) {
						if (f.getCsDDiario().getDtChiusuraDa().after(maxDataChiusuraFA)) {
							maxDataChiusuraFA = f.getCsDDiario().getDtChiusuraDa();
						}
					}
				}
			}
			if (aperti) {
				errors.add("Ci sono interventi associati ancora aperti: è necessario chiuderli ovvero scollegarli dal progetto per poter chiudere il PAI");
			} else if (maxDataChiusuraFA != null && maxDataChiusuraFA.after(this.selectedPai.getCsDDiario().getDtChiusuraDa())) {
				errors.add("Ci sono interventi associati con fogli amministrativi la cui data di chiusura è successiva a quella di chiusura del progetto");
			}
		}

		// verificare per ogni mast se ci sono erogazioni ancora aperte e che la data di
		// chiusura sia compresa nella data del progetto
		if (this.picklistErogazioni != null && this.picklistErogazioni.getTarget() != null) {
			boolean aperti = true;
			boolean dopoDataChiusura = false;

			for (ErogInterventoRowBean eirb : picklistErogazioni.getTarget()) {
				for (ErogazioneDettaglioDTO d : eirb.getLstInterventiEseguiti()) {
					if (d.getStatoErogazione().getFlagChiudi()) {
						aperti = false;
						break;
					}
				}
				
				for (ErogazioneDettaglioDTO d2 : eirb.getLstInterventiEseguiti()) {
					Date dataCheck = d2.getDataErogazioneA() == null ? d2.getDataErogazione() : d2.getDataErogazioneA();

					if (dataCheck.after(this.selectedPai.getCsDDiario().getDtChiusuraDa())) {
						dopoDataChiusura = true;
						break;
					}
				}

				
			}

			if (aperti)
				errors.add("Ci sono erogazioni associate ancora aperte: è necessario chiuderle per poter chiudere il Progetto Individuale");
			if(dopoDataChiusura)
				errors.add("Ci sono erogazioni associate con fogli amministrativi la cui data di chiusura è successiva a quella di chiusura del progetto");
		}

		/// SISO-1275 - CHiusura condizionata alla fase
		if (this.selectedPai.getCsTbTipoPai().getId() != 0
				&& this.selectedPai.getCsTbMotivoChiusuraPai().getId() != 0) {
			Integer faseProgetto = 0;
			String descrizioneFaseProgetto = "";
			if (this.getClassePai() != null) {
				if (isClasseProgetto(PaiProgettiEnum.AFFIDO)) {
					faseProgetto = this.paiAffidoBean.getStatoAffido();
					descrizioneFaseProgetto = PaiAffidoStatoEnum.getDescrizioneFromValore(faseProgetto);
				}
				if (faseProgetto != 0) {
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
	
					CsPaiFaseChiusuraPK pk = new CsPaiFaseChiusuraPK();
	
					pk.setTipoPai(this.selectedPai.getCsTbTipoPai().getId());
					pk.setMotivoChiusura(this.selectedPai.getCsTbMotivoChiusuraPai().getId());
					pk.setFase(faseProgetto);
	
					if (esisteMotivoChiusuraFaseByTipoPai(pk)) {
						if (!this.esisteMotivoChiusura(pk)) {
							Long id = Long.valueOf(this.selectedPai.getCsTbMotivoChiusuraPai().getId());
							dto = new BaseDTO();
							fillEnte(dto);
							dto.setObj(id);
							CsTbMotivoChiusuraPai motChiusura = confService.getMotivoChiusuraPaiById(dto);
							errors.add("Non è possibile chiudere il progetto nella fase " + descrizioneFaseProgetto
									+ " con motivazione " + motChiusura.getDescrizione());
						}
					}
				}
	
				if (isClasseProgetto(PaiProgettiEnum.PTI)) {
					dataUltimaConsuntivazioneErog= null;
					Date dataAttuale = new Date();
					faseProgetto = this.paiPTIBean.getIdFase().intValue();
					descrizioneFaseProgetto = PaiPTIFaseEnum.getDescrizioneFromValore(faseProgetto);
					if (faseProgetto < PaiPTIFaseEnum.EROG_OK.getId()) {
						if(faseProgetto.equals(PaiPTIFaseEnum.STRUTT_OK.getId())) {//NON HO ANCORA EROGATO E HO PERO ACCETTATO LA STRUTTURA
							errors.add("Non è possibile chiudere il progetto di tipo " + this.getClassePai().toUpperCase()
							+ " poichè non è presente l'erogazione del servizio ");
						}
					
					}
				
					//CONTROLLO SE ESISTE ALMENO UNA RELAZIONE
					if (this.picklistRelazioni != null && this.picklistRelazioni.getTarget() != null
							&& this.picklistRelazioni.getTarget().size() <= 0) {
						errors.add("Non è possibile chiudere il progetto di tipo " + this.getClassePai().toUpperCase()
						+ " poichè non è presente alcuna relazione ");
						
					}
					
					
					
				}
	
				if (faseProgetto != 0) {
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
	
					CsPaiFaseChiusuraPK pk = new CsPaiFaseChiusuraPK();
	
					pk.setTipoPai(this.selectedPai.getCsTbTipoPai().getId());
					pk.setMotivoChiusura(this.selectedPai.getCsTbMotivoChiusuraPai().getId());
					pk.setFase(faseProgetto);
	
					if (esisteMotivoChiusuraFaseByTipoPai(pk)) {
						if (!this.esisteMotivoChiusura(pk)) {
							Long id = Long.valueOf(this.selectedPai.getCsTbMotivoChiusuraPai().getId());
							dto = new BaseDTO();
							fillEnte(dto);
							dto.setObj(id);
							CsTbMotivoChiusuraPai motChiusura = confService.getMotivoChiusuraPaiById(dto);
							errors.add("Non è possibile chiudere il progetto nella fase " + descrizioneFaseProgetto
									+ " con motivazione " + motChiusura.getDescrizione());
						}
					}
				}
			}

		}
		return errors;
	}

	public List<String> valida() {
		ArrayList<String> errors = new ArrayList<String>();

		if (this.selectedPai.getCsTbTipoPai() == null || this.selectedPai.getCsTbTipoPai().getId() == 0) {
			errors.add("Indicare il tipo di progetto");
		}

		// SISO-1280 non serv e più il controllo sulla cittadinanza perchè è stata
		// eliminata dal pai ma
		// perchè già presente nel panel beneficiari
//		if (this.selectedPai.getCittadinanza() == null || "".equals(selectedPai.getCittadinanza().trim())) {
//			errors.add("Indicare la cittadinanza al tempo del PAI");
//		}

		if (this.selectedPai.getCsDDiario().getDtAttivazioneDa() == null) {
			errors.add("Indicare la data di attivazione");
		}
//		if (this.selectedPai.getCsDDiario().getDtChiusuraDa() == null) {
//			errors.add("Indicare la data di chiusura anche se presunta");
//		}
		// SISO-1131
		// se ho selezionato il Progetto
		if (this.selectedPai.getTipoProgettoId() > 0) {
			String nomeProgettoSelezionato = "";
			for (SelectItem itm : lstArFfProgetti) {
				if (itm.getValue().equals(this.selectedPai.getTipoProgettoId())) {
					nomeProgettoSelezionato = itm.getLabel();
					break;
				}

			}
			if (!nomeProgettoSelezionato.isEmpty() && nomeProgettoSelezionato.equalsIgnoreCase("ALTRO")) {
				if (this.selectedProgettoAltro != null) {
					if (this.selectedProgettoAltro.getDescrizione() == null
							|| this.selectedProgettoAltro.getDescrizione().isEmpty()) {
						errors.add("Il campo Specificare è obbligatorio");
					}
				} else {
					errors.add("Il campo Specificare è obbligatorio");
				}

			}

		}

		// SISO-1034
		if (!this.selectedPai.getMonitoraggioObiettivi()) {

			// SISO-1172
			if (StringUtils.isEmpty(this.selectedPai.getMotivazioniProgetto())) {
				errors.add("Inserire le motivazioni del progetto");
			}
			// FINE SISO-1172
			if (this.selectedPai.getVerificaOgni() == null
					|| StringUtils.isEmpty(this.selectedPai.getVerificaUnitaMisura())) {
				errors.add("Indicare la frequenza di verifica");
			}
			if (StringUtils.isEmpty(this.selectedPai.getObiettiviBreve())
					&& StringUtils.isEmpty(this.selectedPai.getObiettiviMedio())
					&& StringUtils.isEmpty(this.selectedPai.getObiettiviLungo())) {
				errors.add("Inserire almeno un obiettivo");
			}

			// ISSUE-520
			if (this.dataNuovoMonitoraggio != null && !this.dataNuovoMonitoraggio.before((new Date()))) {
				errors.add("La data ultimo monitoraggio non può essere superiore alla data odierna.");
			}
			// if(this.selectedPai.getDataMonitoraggio()==null){
			// errors.add("Indicare la data ultimo monitoraggio.");
			// }
			if (this.selectedPai.getCsDDiario().getDtChiusuraDa() != null && this.selectedPai.getCsDDiario()
					.getDtChiusuraDa().before(this.selectedPai.getCsDDiario().getDtAttivazioneDa())) {
				errors.add("La data di chiusura deve essere successiva alla data di attivazione.");
			}
		}

		if (lazyListaPaiModel.getRowCount()>0) {
			// verificare esistenza di altri progetti dello stesso tipo nello stesso periodo
			CsTbTipoPai tipo = this.selectedPai.getCsTbTipoPai();
			
			PaiSearchCriteria psc = new PaiSearchCriteria();
			fillEnte(psc);
			psc.setDiarioId(this.selectedPai.getDiarioId());
			psc.setCodiceFiscale(soggRiferimentoPai.getCf());
			psc.setTipoPaiId(tipo.getId());
			
			List<PaiSintesiDTO> lstDate = diarioService.findDatePai(psc);
			
			Date da = this.selectedPai.getCsDDiario().getDtAttivazioneDa();
			Date a = this.selectedPai.getCsDDiario().getDtChiusuraDa();
			for (PaiSintesiDTO p : lstDate) {
/*				if (p.getDiarioId().equals(this.selectedPai.getDiarioId())) {
					continue;
				} else if (tipo.getId() != (Long)p.getTipoPai().getCodice()) {
					continue;
				} else if (!p.hasBeneficiarioCF(soggRiferimentoPai.getCf())) {
					continue;
				} else {*/
					try {
						if (VerificaSovrapposizioneRangeDate(da, a, p.getDtAttivazione(),p.getDtChiusura())) {
							errors.add("Esiste già un progetto dello stesso tipo nello stesso periodo (ID: "+ p.getDiarioId()+ ").\n "
									+  "Le data del progetto corrente si sovrappongono al periodo di apertura dell'altro progetto esistente");
						}
					} catch (Exception e) {
						// errors.add(e.getLocalizedMessage());
						logger.error(e.getMessage(), e);
					}
			}
		}

		// verificare coerenza delle date di tutte le entità (interventi, relazioni)
		// collegate al progetto
		{
			Date da = this.selectedPai.getCsDDiario().getDtAttivazioneDa();
			Date a = this.selectedPai.getCsDDiario().getDtChiusuraDa();
			if (this.picklistInterventi != null && this.picklistInterventi.getTarget() != null) {
				for (DatiInterventoBean i : this.picklistInterventi.getTarget()) {
					if (!between(i.getFineDa(), da, a) || !between(i.getFineA(), da, a) || !between(i.getInizioA(), da, a) || !between(i.getInizioDa(), da, a)) {
						// ERROR: la durata del progetto corrente non comprende le date di inizio e fine di tutti gli interventi associati
						errors.add("Esiste un intervento associato al progetto (ID: " + i.getIdIntervento()+ ") le cui date di inizio e fine non sono comprese nella durata del progetto corrente.");
					}
				}
			}
			if (this.picklistRelazioni != null && this.picklistRelazioni.getTarget() != null) {
				for (RelazioneSintesiDTO r : this.picklistRelazioni.getTarget()) {
					Date dAmm = r.getDtAmministrativa();
					if (!between(dAmm, da, a)) {
						// ERROR: la durata del progetto corrente non comprende le date di tutte le relazioni associate
						errors.add("Esiste un'attività professionale associata al progetto (ID: " + r.getDiarioId()+ ") la cui data non è compresa nella durata del progetto corrente.");
					} else {
						if (isClasseProgetto(PaiProgettiEnum.PTI)) {
							// DEVO VERIFICARE SE sono passati i mesi e la relazione non esiste
							Date dtPTI = this.selectedPai.getCsDDiario().getDtAmministrativa();
						}
					}
				}
			}
			if (this.picklistErogazioni != null && this.picklistErogazioni.getTarget() != null) {
				// per ogni mast controllo le erogazioni associate
				for (ErogInterventoRowBean erb : this.picklistErogazioni.getTarget()) {
					for (ErogazioneDettaglioDTO ed : erb.getLstInterventiEseguiti()) {
						if (ed.getStatoErogazione().getTipo().equalsIgnoreCase(DataModelCostanti.TipoStatoErogazione.EROGATIVO) && !between(ed.getDataErogazione(), da, a)) {
							// ERROR: la durata del progetto corrente non comprende le date di tutte le relazioni associate
							errors.add("Esiste una erogazione associata al progetto (ID: " + ed.getIdInterventoEseg()+ ") la cui data non è compresa nella durata del progetto corrente.");
						}
					}
				}
			}
		}

		if (this.selectedPai.getDiarioId() == null) { // nuovo progetto
		}

		return errors;
	}

	private List<RelazioneSintesiDTO> loadListaRelazioni(){
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		if (fromFascicoloCartellaUtente)
			dto.setObj(idCaso);
		else
			dto.setObj(idCasoSoggEsterno);
		List<RelazioneSintesiDTO> lst = diarioService.findRelazioniSintesiByCaso(dto);
		// caso in cui provengo dal fascicolo
		String cf = csASoggetto != null && csASoggetto.getCsAAnagrafica() != null ? csASoggetto.getCsAAnagrafica().getCf(): null;

		// caso in cui ho selezionato un'anagrafica esterna
		if (cf == null) {
			cf = soggRiferimentoPai != null ? soggRiferimentoPai.getCf() : null;
		}

		// cerco eventuali relazioni create esternamente alla cartella sociale
		if (cf != null) {
			dto.setObj(cf);
			lst.addAll(diarioService.findRelazioniSintesiPaiEsterniByCF(dto));
		}
		
		return lst;
		
	}
	
	public List<RelazioneSintesiDTO> getLstRelazioniAssociatePai(List<RelazioneSintesiDTO> lst) {
		Long diarioPaiId = selectedPai!=null ? selectedPai.getDiarioId() : null;
		for (Iterator<RelazioneSintesiDTO> it = lst.iterator(); it.hasNext();) {
				RelazioneSintesiDTO r = it.next();
				if (!r.isRelatedToPai(diarioPaiId)) {
					it.remove();
				}
			}
		return lst;
	}

	public List<DatiInterventoBean> getLstInterventi() {
		List<CsAComponente> listaParenti = CsUiCompBaseBean.caricaParenti(idSoggetto, null);
		return elaboraLstInterventi(loadLstInterventi(), false, true, idSoggetto, listaParenti);
	}

	private List<CsIIntervento> loadLstInterventi() {
		List<CsIIntervento> lsti = new ArrayList<CsIIntervento>();

		try {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			if (fromFascicoloCartellaUtente)
				dto.setObj(idCaso);
			else
				dto.setObj(idCasoSoggEsterno);
			lsti = interventoService.getListaInterventiByCaso(dto);
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
		return lsti;
	}

	public List<DatiInterventoBean> elaboraLstInterventi(List<CsIIntervento> lsti, boolean onlyAssociatedToPai, boolean onlyAssociabiliToPai, Long idSoggetto, List<CsAComponente> listaParenti) {
		List<DatiInterventoBean> lst = new ArrayList<DatiInterventoBean>();
		logger.info("PaiBean - Elaborazione lista Interventi "
				+ "totale[" + lsti.size() + "] "
				+ "onlyAssociatedToPai["+ onlyAssociatedToPai + "], "
				+ "onlyAssociabiliToPai[" + onlyAssociabiliToPai + "]");

		if (onlyAssociatedToPai) {
			for (Iterator<CsIIntervento> it = lsti.iterator(); it.hasNext();) {
				CsIIntervento i = it.next();
				Long idPaiIntervento = i.getDiarioPaiId();
				if (idPaiIntervento != null && selectedPai != null && idPaiIntervento.equals(selectedPai.getDiarioId())) {
					DatiInterventoBean dib = new DatiInterventoBean(i, idSoggetto, listaParenti);
					lst.add(dib);
				}
			}
		}

		if (onlyAssociabiliToPai) {
			for (Iterator<CsIIntervento> it = lsti.iterator(); it.hasNext();) {
				CsIIntervento i = it.next();
				Long idPaiIntervento = i.getDiarioPaiId();
				if (!(idPaiIntervento != null && selectedPai != null && !idPaiIntervento.equals(selectedPai.getDiarioId()))) {
					DatiInterventoBean dib = new DatiInterventoBean(i, idSoggetto, listaParenti);
					lst.add(dib);
				}
			}
		}

		return lst;
	}

	private List<ErogInterventoRowBean> elaboraLstErogazioni(boolean onlyAssociatedToPai,boolean onlyAssociabiliToPai) {

		List<ErogInterventoRowBean> toReturn = new LinkedList<ErogInterventoRowBean>();
		logger.info("PaiBean - Elaborazione lista Erogazioni totale[" + listaErogazioniByCaso.size()+ "] "
				+ "onlyAssociatedToPai[" + onlyAssociatedToPai + "], "
				+ "onlyAssociabiliToPai[" + onlyAssociabiliToPai + "]");

		if (onlyAssociatedToPai) {
			for (ErogInterventoRowBean eirb : listaErogazioniByCaso) {
				if ((eirb.getDiarioPaiId() != null && selectedPai != null && eirb.getDiarioPaiId().equals(selectedPai.getDiarioId())))
					toReturn.add(eirb);
			}
		}

		if (onlyAssociabiliToPai) {
			for (ErogInterventoRowBean eirb : listaErogazioniByCaso) {
				if (!(eirb.getDiarioPaiId() != null && selectedPai != null && eirb.getDiarioPaiId().equals(selectedPai.getDiarioId())))
					toReturn.add(eirb);
			}
		}

		return toReturn;
	}

	// SISO-748
	public List<ErogInterventoRowBean> getLstErogazioni(boolean searchBycaso, String cf) {

		List<ErogInterventoRowBean> toReturn = new LinkedList<ErogInterventoRowBean>();
		CsOOperatoreSettore opSettore = CsUiCompBaseBean.getCurrentOpSettore();
		ErogazioniSearchCriteria bDto = new ErogazioniSearchCriteria();
		CsUiCompBaseBean.fillEnte(bDto);
		bDto.setSettoreId(opSettore.getCsOSettore().getId());
		bDto.setOrganizzazioneId(opSettore.getCsOSettore().getCsOOrganizzazione().getId());
		bDto.setFirst(0);
		bDto.setPageSize(0);
		bDto.setPermessoAutorizzativo(CsUiCompBaseBean.isPermessoAutorizzativo());
		bDto.setSearchErogatiNoIntervento(true); // Con questo parametro attivo non carica le erogazioni collegate alle
													// richieste di intervento
		bDto.setSearchByCaso(searchBycaso);
		if (searchBycaso) {
			if (fromFascicoloCartellaUtente)
				bDto.setCasoId(idCaso);
			else
				bDto.setCasoId(idCasoSoggEsterno);
		} else {
			if (cf != null) {
				bDto.setCodiceFiscale(cf);
			}
		}
		logger.debug("Caricamento erogazioni PAI settore[" + bDto.getSettoreId() + "], " + "organizzazione["
				+ bDto.getOrganizzazioneId() + "], " + "autorizzativo [" + bDto.isPermessoAutorizzativo() + "], "
				+ "erogatiNoIntervento [" + bDto.isSearchErogatiNoIntervento() + "], " + "searchByCaso ["
				+ bDto.isSearchByCaso() + "], " + "idCaso [" + bDto.getCasoId() + "]");

		boolean loadDettaglioErogazione = false;
		bDto.setLoadDettaglioErogazione(loadDettaglioErogazione);
		
		List<ErogazioneMasterDTO> lst = interventoService.searchListaErogInterventi(bDto);
		for (ErogazioneMasterDTO dae : lst) {
			//Mettendo false - non carico il dettaglio di configurazione delle spese
			ErogInterventoRowBean row = new ErogInterventoRowBean(dae, false);
			toReturn.add(row);
		}

		return toReturn;

	}

	public DualListModel<RelazioneSintesiDTO> getPicklistRelazioni() {
		if (picklistRelazioni == null) {
			refreshPicklistRelazioni();
		}

		return picklistRelazioni;
	}

	public void refreshPicklistRelazioni() {
		logger.debug("Init refreshPicklistRelazioni");
		List<RelazioneSintesiDTO> relazioniCaso = this.loadListaRelazioni();
		List<RelazioneSintesiDTO> source = new ArrayList<RelazioneSintesiDTO>();
		source.addAll(relazioniCaso);
		
		List<RelazioneSintesiDTO> target = getLstRelazioniAssociatePai(relazioniCaso);
		
		if (source != null)
			source.removeAll(target);
		picklistRelazioni = new DualListModel<RelazioneSintesiDTO>(source, target);
		aggiornaListaRelazioni();
		
		logger.debug("End refreshPicklistRelazioni");
	}
	
	public void refreshPicklistRelazioni(RelazioneSintesiDTO relazioneDto) {
		if (picklistRelazioni != null) { // è null quando il salvataggio viene richiamato dalla tab "Attività
											// professionali"; è valorizzato quando viene richiamato nella tab PAI

			Iterator<RelazioneSintesiDTO> it = picklistRelazioni.getTarget().iterator();

			while (it.hasNext()) {
				RelazioneSintesiDTO rdto = it.next();
				if (rdto.getDiarioId().equals(relazioneDto.getDiarioId())) {
					it.remove();
					break;
				}
			}

			picklistRelazioni.getTarget().add(relazioneDto);
			aggiornaListaRelazioni();
			
			if (fromFascicoloCartellaUtente)
				RequestContext.getCurrentInstance().update("fascicoloTab:paiForm:paiComponent:paitabview:dataTableRelazioni_panel");
			else
				RequestContext.getCurrentInstance().update("frmProgettiIndividuali:paiExtComponent:paitabview:dataTableRelazioni_panel");
		}
	}

	/**
	 * Metodo che recupera tutte le relazioni create contestualmente ad un PAI
	 * esterno
	 * 
	 * @param CF
	 */
	public void getPicklistRelazioniEsterneFromCF(String CF) {
		BaseDTO baseDTO = new BaseDTO();
		fillEnte(baseDTO);
		baseDTO.setObj(CF);
		List<RelazioneSintesiDTO> source = diarioService.findRelazioniSintesiPaiEsterniByCF(baseDTO);
		List<RelazioneSintesiDTO> target = new ArrayList<RelazioneSintesiDTO>();
		target.addAll(source);
		
		Long diarioPaiId = selectedPai!=null ? selectedPai.getDiarioId() : null;
		for (Iterator<RelazioneSintesiDTO> it = target.iterator(); it.hasNext();) {
			RelazioneSintesiDTO r = it.next();
			if (!r.isRelatedToPai(diarioPaiId)) {
				it.remove();
			}
		}
		if (source != null)
			source.removeAll(target);

		picklistRelazioni = new DualListModel<RelazioneSintesiDTO>(source, target);
	}

	public void getPicklistInterventiEsterniFromCF(String CF) {
		BaseDTO baseDTO = new BaseDTO();
		fillEnte(baseDTO);
		baseDTO.setObj(CF);
		List<CsIIntervento> listInterventi = diarioService.findInterventiPaiEsterniByCF(baseDTO);
		
		Long idSoggetto = soggRiferimentoPai.getCaso() != null && soggRiferimentoPai.getCaso().getCsASoggetto() != null
				? soggRiferimentoPai.getCaso().getCsASoggetto().getAnagraficaId(): null;

		List<CsAComponente> listaParenti = CsUiCompBaseBean.caricaParenti(idSoggetto, null);
		List<DatiInterventoBean> target = elaboraLstInterventi(listInterventi, true, false, idSoggetto, listaParenti);
		List<DatiInterventoBean> source = elaboraLstInterventi(listInterventi, false, true, idSoggetto, listaParenti);

		if (source != null)
			source.removeAll(target);

		picklistInterventi = new DualListModel<DatiInterventoBean>(source, target);
	}

	public DualListModel<ErogInterventoRowBean> getPicklistErogazioniEsterniFromCF(String CF) {

		listaErogazioniByCaso = getLstErogazioni(false, CF);
		// List<ErogInterventoRowBean> toReturn = new
		// LinkedList<ErogInterventoRowBean>();
		List<ErogInterventoRowBean> source = elaboraLstErogazioni(false, true);
		List<ErogInterventoRowBean> target = elaboraLstErogazioni(true, false);
		source.removeAll(target);
		picklistErogazioni = new DualListModel<ErogInterventoRowBean>(source, target);

		return picklistErogazioni;
	}

	public void initPickList() {
		picklistRelazioni = new DualListModel<RelazioneSintesiDTO>(new ArrayList<RelazioneSintesiDTO>(),new ArrayList<RelazioneSintesiDTO>());
		picklistInterventi = new DualListModel<DatiInterventoBean>(new ArrayList<DatiInterventoBean>(),new ArrayList<DatiInterventoBean>());
		picklistErogazioni = new DualListModel<ErogInterventoRowBean>(new ArrayList<ErogInterventoRowBean>(),new ArrayList<ErogInterventoRowBean>());
	}

	public void setPicklistRelazioni(DualListModel<RelazioneSintesiDTO> picklistRelazioni) {
		this.picklistRelazioni = picklistRelazioni;
	}
	
	public List<DatiInterventoBean> getTargetDatiInterventoBean() {
		return picklistInterventi.getTarget();
	}

	// SISO-748
	public List<ErogInterventoRowBean> getTargetErogazione() {
		return picklistErogazioni.getTarget();
	}
	// FINEresiduo-evoluzione-pai

	public DualListModel<DatiInterventoBean> getPicklistInterventi() {
		if (picklistInterventi == null) {
			refreshPicklistInterventi();
		}

		return picklistInterventi;
	}

	public void refreshPicklistInterventi() {
		logger.debug("INIT refreshPicklistInterventi");
		List<CsIIntervento> lstInterventi = loadLstInterventi();
		List<CsAComponente> listaParenti = CsUiCompBaseBean.caricaParenti(idSoggetto, null);
		List<DatiInterventoBean> target = elaboraLstInterventi(lstInterventi, true, false, idSoggetto, listaParenti);
		List<DatiInterventoBean> source = elaboraLstInterventi(lstInterventi, false, true, idSoggetto, listaParenti);
		source.removeAll(target);
		picklistInterventi = new DualListModel<DatiInterventoBean>(source, target);
		logger.debug("END refreshPicklistInterventi");
	}

	public void setPicklistInterventi(DualListModel<DatiInterventoBean> picklistInterventi) {
		this.picklistInterventi = picklistInterventi;
	}

	// SISO-748
	public DualListModel<ErogInterventoRowBean> getPicklistErogazioni() {
		if (picklistErogazioni == null) {
			refreshPicklistErogazioni();
		}
		return picklistErogazioni;
	}

	public void refreshPicklistErogazioni() {
		logger.debug("INIT refreshPicklistErogazioni");
		listaErogazioniByCaso = getLstErogazioni(true, null);
		List<ErogInterventoRowBean> source = elaboraLstErogazioni(false, true);
		List<ErogInterventoRowBean> target = elaboraLstErogazioni(true, false);
		source.removeAll(target);
		picklistErogazioni = new DualListModel<ErogInterventoRowBean>(source, target);
		logger.debug("END refreshPicklistErogazioni");
	}

	public Converter getPicklistRelazioniConverter() {
		return new Converter() {
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
				RelazioneSintesiDTO ret = null;

				if (submittedValue != null && !submittedValue.trim().equals("")) {
					try {
						BaseDTO dto = new BaseDTO();
						fillEnte(dto);
						dto.setObj(Long.valueOf(submittedValue));
						ret = diarioService.findRelazioneSintesiById(dto);
					} catch (Exception e) {
						logger.error(e);
					}
				}

				return ret;
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if (value != null && value instanceof RelazioneSintesiDTO)
					return ((RelazioneSintesiDTO) value).getDiarioId().toString();
				else
					return "";
			}
		};
	}

	public Converter getPicklistInterventiConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
				DatiInterventoBean ret = null;

				if (submittedValue != null && !submittedValue.trim().equals("")) {
					try {
						BaseDTO dto = new BaseDTO();
						fillEnte(dto);
						dto.setObj(Long.valueOf(submittedValue));
						CsIIntervento csiinterv = interventoService.getInterventoById(dto);
						List<CsAComponente> listaParenti = CsUiCompBaseBean.caricaParenti(idSoggetto, null);
						ret = new DatiInterventoBean(csiinterv, idSoggetto, listaParenti);
					} catch (Exception e) {
						logger.error(e);
					}
				}

				return ret;
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if (value != null && value instanceof DatiInterventoBean)
					return ((DatiInterventoBean) value).getIdIntervento().toString();
				else
					return "";
			}

		};
	}

	// SISO-748
	public Converter getPicklistErogazioniConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
				ErogInterventoRowBean ret = null;

				if (submittedValue != null && !submittedValue.trim().equals("")) {
					try {
						String idRow = submittedValue;

						for (ErogInterventoRowBean eir : listaErogazioniByCaso) {
							if (eir.getIdRow().equalsIgnoreCase(idRow)) {
								ret = eir;
								break;
							}
						}
					} catch (Exception e) {
						logger.error(e);
					}
				}

				return ret;
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if (value != null && value instanceof ErogInterventoRowBean)
					return ((ErogInterventoRowBean) value).getIdRow().toString();
				else
					return "";
			}

		};
	}

	public boolean isOnUpdateRelazioni() {
		return onUpdateRelazioni;
	}

	public void setOnUpdateRelazioni(boolean onUpdateRelazioni) {
		this.onUpdateRelazioni = onUpdateRelazioni;
		this.setTabViewIndex(1);
	}

	public int getTabViewIndex() {
		return tabViewIndex;
	}

	public void setTabViewIndex(int tabViewIndex) {
		this.tabViewIndex = tabViewIndex;
	}

	public boolean isOnUpdateInterventi() {
		return onUpdateInterventi;
	}

	public void setOnUpdateInterventi(boolean onUpdateInterventi) {
		this.onUpdateInterventi = onUpdateInterventi;
		this.setTabViewIndex(0);
	}

	private void updateInterventi() throws Exception {
		if (picklistInterventi != null && selectedPai != null) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);

			if (picklistInterventi.getTarget() != null) {
				for (DatiInterventoBean di : picklistInterventi.getTarget()) {
					dto.setObj(di.getIdIntervento());
					dto.setObj2(selectedPai);
					interventoService.salvaRifInterventoToPai(dto);
				}
			}
			if (picklistInterventi.getSource() != null) {
				for (DatiInterventoBean di : picklistInterventi.getSource()) {
					dto.setObj(di.getIdIntervento());
					dto.setObj2(null);
					dto.setObj3(selectedPai.getDiarioId());
					interventoService.salvaRifInterventoToPai(dto);
				}
			}
		}
	}

	private void updateRelazioni() throws Exception {
		if (picklistRelazioni != null && selectedPai != null) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);

			if (picklistRelazioni.getTarget() != null) {
				for (RelazioneSintesiDTO r : picklistRelazioni.getTarget()) {
					dto.setObj(r.getDiarioId());
					dto.setObj2(selectedPai.getDiarioId());
					diarioService.salvaRifRelazioneToPai(dto);
				}
			}
			if (picklistRelazioni.getSource() != null) {
				for (RelazioneSintesiDTO r : picklistRelazioni.getSource()) {
					dto.setObj(r.getDiarioId());
					dto.setObj2(null);
					dto.setObj3(selectedPai.getDiarioId());
					diarioService.salvaRifRelazioneToPai(dto);
				}
			}
		}
	}

	// SISO-748
	private void updateErogazioni() throws Exception {
		if (picklistErogazioni != null && selectedPai != null) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);

			if (picklistErogazioni.getTarget() != null) {
				for (ErogInterventoRowBean di : picklistErogazioni.getTarget()) {
					dto.setObj(di.getMaster().getIdInterventoEsegMaster());
					dto.setObj2(selectedPai.getDiarioId());
					interventoService.salvaRifErogazioneToPai(dto);
				}
			}
			if (picklistErogazioni.getSource() != null) {
				for (ErogInterventoRowBean di : picklistErogazioni.getSource()) {
					dto.setObj(di.getMaster().getIdInterventoEsegMaster());
					dto.setObj2(null);
					interventoService.salvaRifErogazioneToPai(dto);
				}
			}

		}
	}

	/**
	 * @return the onClosing
	 */
	public final boolean isOnClosing() {
		return onClosing;
	}

	/**
	 * @param onClosing the onClosing to set
	 */
	public final void setOnClosing(boolean onClosing) {
		this.onClosing = onClosing;
	}

	@Override
	protected final void addError(String summary, String descrizione) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary,
				descrizione != null ? descrizione : "");
		FacesContext.getCurrentInstance().addMessage(MESSAGES_ID, message);
	}

	public void addMessage(FacesMessage.Severity tipoMessaggio, String summary) {
		FacesMessage message = new FacesMessage(tipoMessaggio, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	private static boolean between(Date d, Date min, Date max) {
		if (d == null)
			return true;

		if (max != null && min == null)
			return !d.after(max);
		if (min != null && max == null)
			return !d.before(min);
		if (min != null && max != null)
			return (!d.before(min) && !d.after(max));

		return false;
	}

	/// <summary>
	/// Verifica se due range di date si sovrappongono
	/// </summary>
	/// <param name="ev1Begin">inizio primo range</param>
	/// <param name="ev1End">fine primo range</param>
	/// <param name="ev2Begin">inizio secondo range</param>
	/// <param name="ev2End">fine secondo range</param>
	/// <returns>true se sovrapposti</returns>
	///
	// casi possibili:
	// 1) ev2 contenuto in ev1
	// ev1: +---------------+
	// ev2: +---+
	// 2) ev1 contenuto in ev2
	// ev1: +--------+
	// ev2: +-----------------+
	// 3) parzialmente contenuto, ev1 inizia prima di ev2
	// ev1: +--------+
	// ev2: +---------------+
	// 4) parzialmente contenuto, ev1 inizia dopo ev2
	// ev1: +--------+
	// ev2: +--------+
	// 5) eventi esattamente sovrapposti
	// ev1: +--------+
	// ev2: +--------+
	// 6) ev1 contenuto in ev2 con data inizio uguale
	// ev1: +--------+
	// ev2: +---------------+
	// 7) ev1 contenuto in ev2 con data fine uguale
	// ev1: +---+
	// ev2: +--------+
	public static boolean VerificaSovrapposizioneRangeDate(Date ev1Begin, Date ev1End, Date ev2Begin, Date ev2End)
			throws Exception {
		if (ev1Begin == null || ev2Begin == null) // || ev1End==null
		{
			throw new Exception("Date null");
		}

		// esiste un pai aperto dello stesso tipo, non posso salvare
		if (ev2End == null) {
			return true;
		}

		// la data del pai selezionato deve essere almeno maggiore della data di
		// chiusura del pai analizzato
		if (!ev1Begin.after(ev2End)) {
			return true;
		} else {
			return false;
		}

//	  if ((ev1End != null && ev1Begin.after(ev1End)) || (ev2End != null && ev2Begin.after(ev2End)))
//	  {
//	    //return true;  // se le date sono invertite indico come periodo sovrapposto
//	    // in laternativa
//	    throw new Exception("Date invertite");
//	  }  
//	 
//	  if ((!ev1Begin.after(ev2Begin) && !ev1End.before(ev2End))  // 1 e 5
//	    || (!ev1Begin.before(ev2Begin) && !ev1End.after(ev2End)) // 2, 6 e 7
//	    || (ev1Begin.before(ev2Begin) && ev1End.after(ev2Begin)) // 3
//	    || (ev1Begin.before(ev2End) && ev1End.after(ev2End))    // 4
//	    )
//	    return true;
//	  else
//	    return false;
	}

	// inizio evoluzione-pai
	@Override
	public boolean isPaiDetailRendered() {
		return selectedPai != null;
	}

	public TipoInterventoManBean getTipoInterventoManBean() {
		return tipoInterventoManBean;
	}

	public void setTipoInterventoManBean(TipoInterventoManBean tipoInterventoManBean) {
		this.tipoInterventoManBean = tipoInterventoManBean;
	}

	@ManagedProperty(value = "#{fascicoloBean}")
	private FascicoloBean fascicoloBean;

	@ManagedProperty(value = "#{progettiInvidualiExt}")
	private ProgettiIndividualiExtBean progettiIndividualiExtBean;

	public FascicoloBean getFascicoloBean() {
		return fascicoloBean;
	}

	public void setFascicoloBean(FascicoloBean fascicoloBean) {
		this.fascicoloBean = fascicoloBean;
	}

	public ProgettiIndividualiExtBean getProgettiIndividualiExtBean() {
		return progettiIndividualiExtBean;
	}

	public void setProgettiIndividualiExtBean(ProgettiIndividualiExtBean progettiIndividualiExtBean) {
		this.progettiIndividualiExtBean = progettiIndividualiExtBean;
	}

	public void refreshPicklistInterventi(CsIIntervento inter) {
		if (inter != null) {
			if (picklistInterventi != null) { // è null quando il salvataggio viene richiamato dalla tab "Interventi"; è
												// valorizzato quando viene richiamato nella tab PAI
				List<CsAComponente> listaParenti = CsUiCompBaseBean.caricaParenti(idSoggetto, null);
				picklistInterventi.getTarget().add(new DatiInterventoBean(inter, idSoggetto, listaParenti));
				// UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
				// UIComponent component =
				// viewRoot.findComponent("fascicoloTab:paiForm:paiComponent:paitabview:dataTableInterventi_panel");
				// logger.debug(component.getClientId());
				if (fromFascicoloCartellaUtente)
					RequestContext.getCurrentInstance()
							.update("fascicoloTab:paiForm:paiComponent:paitabview:dataTableInterventi_panel");
				else
					RequestContext.getCurrentInstance()
							.update("frmProgettiIndividuali:paiExtComponent:paitabview:dataTableInterventi_panel");
				this.setTabViewIndex(1);
			}
		}
	}


	// SISO-748
	public void refreshPicklistErogazioni(Long idMast) {
		picklistErogazioni = null;
		if (fromFascicoloCartellaUtente) {
			getPicklistErogazioni();
		} else {
			if (soggRiferimentoPai != null && soggRiferimentoPai.getCf() != null) {
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(soggRiferimentoPai.getCf());
				CsASoggettoLAZY sogg = soggettoService.getSoggettoByCF(dto);
				idCasoSoggEsterno = sogg != null && sogg.getCsACaso() != null ? sogg.getCsACaso().getId() : null;

				// se l'anagrafica selezionata e' presente nella cartella sociale
				if (idCasoSoggEsterno != null) {
					getPicklistErogazioni();
					tipoInterventoManBean = new TipoInterventoManBean(listTipoInterventos, getFilterCategorie(loadCatSocialiAttuali(sogg.getAnagraficaId())));
				} else {
					getPicklistErogazioniEsterniFromCF(soggRiferimentoPai.getCf());
					tipoInterventoManBean = new TipoInterventoManBean(listTipoInterventos, "");
				}
			}
		}

		if (idMast != null) {
			String id = "E" + idMast;
			if (!this.erogazioniNuoveAssociate.contains(id))
				this.erogazioniNuoveAssociate.add(id);
		}

		if (picklistErogazioni != null) {
			List<ErogInterventoRowBean> tmp = new ArrayList<ErogInterventoRowBean>();
			tmp.addAll(picklistErogazioni.getSource());
			Iterator it = tmp.iterator(); // Utilizzo una lista di appoggio per evitare
											// java.util.ConcurrentModificationException
			while (it.hasNext()) {
				ErogInterventoRowBean row = (ErogInterventoRowBean) it.next();
				if (erogazioniNuoveAssociate.contains(row.getIdRow())) {
					this.picklistErogazioni.getTarget().add(row);
					this.picklistErogazioni.getSource().remove(row);
				}
			}
			RequestContext.getCurrentInstance().update("frmProgettiIndividuali:paiExtComponent:paitabview");

			this.setTabViewIndex(0);
			if (fromFascicoloCartellaUtente) {
				RequestContext.getCurrentInstance().update("fascicoloTab:paiForm:paiComponent:paitabview:dataTableInterventi_panel");
			} else {
				RequestContext.getCurrentInstance().update("frmProgettiIndividuali:paiExtComponent:paitabview:dataTableInterventi_panel");
			}

		}
	}

	// DEVO CONTROLLARE SE E' stata salvata una riga di erogazione con le date della
	// consuntivazione
	public void refreshConsuntivazione(Long idMast) throws Exception {
		// prendo il target conll'erogazione che ha idMast

		boolean found = false;
		for (ErogInterventoRowBean rowM : this.picklistErogazioni.getTarget()) {
			if (rowM.getIdRow().equalsIgnoreCase("E" + idMast)) {
				// ho trovato l'erogazione appena salvata.
				for (ErogazioneDettaglioDTO d : rowM.getLstInterventiEseguiti()) {
					if (d.getStatoErogazione().getTipo().equalsIgnoreCase("E")) {
						found = foundAndUpdateCons(d);

						if (found) {
							break;
						}
					}
				}
				break;
			}
		}
		this.setTabViewIndex(0);
		// aggiornamento view
		if (fromFascicoloCartellaUtente) {
			RequestContext.getCurrentInstance().update("fascicoloTab:paiForm:paiComponent:pnlDaconsuntivare");
		} else {
			RequestContext.getCurrentInstance().update("frmProgettiIndividuali:paiExtComponent:pnlDaconsuntivare");
		}

	}

	public boolean foundAndUpdateCons(ErogazioneDettaglioDTO d) throws ParseException {
		Date dataErogazioneDa = formatter.parse(formatter.format(d.getDataErogazione()));
		Date dataErogazioneA = formatter.parse(formatter.format(d.getDataErogazioneA()));

		for (InserimentoConsuntivazioneDTO cons : this.getPaiPTIBean().getLstConsuntivazioni()) {
			if (!cons.getFlagErogato()) {
				Date consDa = formatter.parse(formatter.format(cons.getDataDa()));
				Date consA = formatter.parse(formatter.format(cons.getDataA()));
				if (consDa.equals(dataErogazioneDa) && consA.equals(dataErogazioneA)) {

					cons.setFlagErogato(true);
					// update
					paiPTIBean.aggiornaConsuntivazione(cons);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void aggiungiTipoInterventoButton() {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean interventoSelezionato = false;
		if (fromFascicoloCartellaUtente) {
			fascicoloBean = (FascicoloBean) this.getBeanReference("fascicoloBean");
					// context.getApplication().evaluateExpressionGet(context, "#{fascicoloBean}",FascicoloBean.class);

			fascicoloBean.getInterventiBean().getErogazioniInterventiBean().SetFromFascicolo(getCsASoggetto());
			interventoSelezionato = fascicoloBean.getInterventiBean().inizializzaNuovoIntervento(tipoInterventoManBean, null, strutturaAccettataDaEnte);
		} else {
			progettiIndividualiExtBean = (ProgettiIndividualiExtBean) context.getApplication()
					.evaluateExpressionGet(context, "#{progettiInvidualiExt}", ProgettiIndividualiExtBean.class);

			interventoSelezionato = progettiIndividualiExtBean.getInterventiBean()
					.inizializzaNuovoIntervento(tipoInterventoManBean, soggRiferimentoPai, strutturaAccettataDaEnte);
		}
		if (!interventoSelezionato) {
			RequestContext.getCurrentInstance().addCallbackParam("interventoSelezionato", false);
		} else {
			RequestContext.getCurrentInstance().addCallbackParam("interventoSelezionato", true);
		}
	}

	@Override
	public void aggiungiTipoErogazioneButton() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(tipoPai!=null){
          if  (isClasseProgetto(PaiProgettiEnum.PTI)) {
        	  if(this.getPaiPTIBean().getPti().getId()!=null) {
        		  if(this.getPaiPTIBean().getPti().getFaseAttuale().getIdStato() < PaiPTIFaseEnum.STRUTT_OK.getId()) {
        			  addError("Non è possibile erogare il servizio se non viene scelta una struttura", "");
        			  return;
        		  }
        	  }
          }
		}else{
			addError("Selezionare un tipo progetto", "");
			return;
		}
		
		
		if (fromFascicoloCartellaUtente) {
			fascicoloBean = (FascicoloBean) getBeanReference("fascicoloBean");
			//fascicoloBean = (FascicoloBean) context.getApplication().evaluateExpressionGet(context, "#{fascicoloBean}",FascicoloBean.class);

			fascicoloBean.getInterventiBean().getErogazioniInterventiBean().SetFromFascicolo(getCsASoggetto());
			if (strutturaAccettataDaEnte != null) {
				fascicoloBean.getInterventiBean().inizializzaNuovaErogazione(tipoInterventoManBean, true, strutturaAccettataDaEnte);
			} else {
				fascicoloBean.getInterventiBean().inizializzaNuovaErogazione(tipoInterventoManBean, true);
			}

		} else {
			progettiIndividualiExtBean = (ProgettiIndividualiExtBean) context.getApplication()
					.evaluateExpressionGet(context, "#{progettiInvidualiExt}", ProgettiIndividualiExtBean.class);

			CsASoggettoLAZY soggetto = this.recuperaSoggetto(soggRiferimentoPai.getCf());

			if (soggetto != null)
				progettiIndividualiExtBean.getInterventiBean().getErogazioniInterventiBean().SetFromPai(soggetto);
			else
				progettiIndividualiExtBean.getInterventiBean().getErogazioniInterventiBean().SetFromPai(soggRiferimentoPai);

			if (strutturaAccettataDaEnte != null) {
				progettiIndividualiExtBean.getInterventiBean().inizializzaNuovaErogazione(tipoInterventoManBean, true,strutturaAccettataDaEnte);
			} else {
				progettiIndividualiExtBean.getInterventiBean().inizializzaNuovaErogazione(tipoInterventoManBean, true);
			}

		}

	}

	@Override
	public void apriErogazioneButton(Object obj) {
		setProvenienzaConsuntivazione(false);

		if (fromFascicoloCartellaUtente) {
			fascicoloBean = (FascicoloBean) this.getBeanReference("fascicoloBean");

			fascicoloBean.getInterventiBean().getErogazioniInterventiBean().SetFromFascicolo(getCsASoggetto());
			fascicoloBean.getInterventiBean().getErogazioniInterventiBean().inizializzaDialogo(obj);

		} else {
			progettiIndividualiExtBean = (ProgettiIndividualiExtBean) this.getBeanReference("progettiInvidualiExt");
			CsASoggettoLAZY soggetto = this.recuperaSoggetto(soggRiferimentoPai.getCf());

			if (soggetto != null) {
				progettiIndividualiExtBean.getInterventiBean().getErogazioniInterventiBean().SetFromPai(soggetto);
			} else
				progettiIndividualiExtBean.getInterventiBean().getErogazioniInterventiBean().SetFromPai(soggRiferimentoPai);

			progettiIndividualiExtBean.getInterventiBean().getErogazioniInterventiBean().inizializzaDialogo(obj);
		}

	}

	private void loadListaTipiIntervento() {
		List<SelectItem> listTipoIntervento = new ArrayList<SelectItem>();
		listTipoInterventos = new LinkedList<SelectItem>();

		if (fromFascicoloCartellaUtente) {
			FglInterventoBean fglInterventoBean = (FglInterventoBean) getReferencedBean("fglInterventoBean");

			listTipoIntervento = fglInterventoBean.getLstTipoIntervento();
			

			for (SelectItem item : listTipoIntervento) {

				SelectItemGroup itemGroup = (SelectItemGroup) item;
				for (SelectItem selectItem : itemGroup.getSelectItems()) {

					String values = (String) selectItem.getValue();
					String vals[] = values.split("@");
					Long id = new Long(vals[1]);
					String label = (String) selectItem.getLabel();
					this.listTipoInterventos.add(new SelectItem(id, label));
				}
			}
		} else {
				if (isClasseProgetto(PaiProgettiEnum.PTI)) {
					listTipoIntervento = this.getLstTipoInterventoPTI();
					for (SelectItem item : listTipoIntervento) {
						SelectItemGroup itemGroup = (SelectItemGroup) item;
						for (SelectItem selectItem : itemGroup.getSelectItems()) {
							String values = (String) selectItem.getValue();
							String vals[] = values.split("@");
							Long id = new Long(vals[1]);
							String label = (String) selectItem.getLabel();
							this.listTipoInterventos.add(new SelectItem(id, label));
						}
					}
				}else{
					BaseDTO b = new BaseDTO();
					fillEnte(b);
					List<KeyValueDTO> lst = confService.findTipiInterventoAbilitati(b);
					listTipoInterventos = this.convertiLista(lst);
				}
		}

	}

	public List<SelectItem> getLstRadioOptions() {
		if (lstRadioOptions == null) {
			lstRadioOptions = new ArrayList<SelectItem>();
			lstRadioOptions.add(new SelectItem(1, "No"));
			lstRadioOptions.add(new SelectItem(2, "Parzialmente"));
			lstRadioOptions.add(new SelectItem(3, "Si"));

		}

		return lstRadioOptions;
	}

	public List<SelectItem> getLstTipoInterventoPTI() {
		List<SelectItem> listTipoIntervento = new ArrayList<SelectItem>();
		try {
			CsCCategoriaSociale catSoc = new CsCCategoriaSociale();
			CsOOperatoreSettore opSettore = getCurrentOpSettore();
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(opSettore.getCsOSettore().getId());
			List<CsRelSettoreCatsoc> listaCatSociali = catSocService.findRelSettoreCatsocBySettore(dto);

			for (CsRelSettoreCatsoc cSoc : listaCatSociali) {
				if (DataModelCostanti.TipiCategoriaSociale.FAMIGLIA_MINORI_ID.equals(cSoc.getCsCCategoriaSociale().getId())) {
					catSoc = cSoc.getCsCCategoriaSociale();
				}

				if (catSoc.getId() != 0) {

					InterventoDTO dtoI = new InterventoDTO();
					fillEnte(dtoI);
					dtoI.setIdSettore(opSettore.getCsOSettore().getId());
					dtoI.setIdCatsoc(catSoc.getId());
					List<CsCTipoIntervento> beanlst = confService.findTipiInterventoSettoreCatSoc(dtoI);

					if (beanlst != null) {
						SelectItemGroup gr = new SelectItemGroup(catSoc.getDescrizione());
						List<SelectItem> grItems = new ArrayList<SelectItem>();
						for (CsCTipoIntervento tipo : beanlst) {
							String chiaveInterventoTB = catSoc.getId() + "@" + tipo.getId(); 
							grItems.add(new SelectItem(chiaveInterventoTB, tipo.getDescrizione()));
						}
						if (!grItems.isEmpty()) {
							gr.setSelectItems(grItems.toArray(new SelectItem[grItems.size()]));
							listTipoIntervento.add(gr);
						}
					}
				}
			}

		} catch (Exception e) {
			addErrorDialogFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
		return listTipoIntervento;
	}

	public List<SelectItem> getLstArFfProgetti() {
		if (this.lstArFfProgetti == null)
			lstArFfProgetti = this.loadLstArFfProgetti();
		return lstArFfProgetti;
	}

	public void setLstArFfProgetti(List<SelectItem> lstArFfProgetti) {
		this.lstArFfProgetti = lstArFfProgetti;
	}

	public void setPicklistErogazioni(DualListModel<ErogInterventoRowBean> picklistErogazioni) {
		this.picklistErogazioni = picklistErogazioni;
	}

	public List<ErogInterventoRowBean> getListaErogazioniByCaso() {
		return listaErogazioniByCaso;
	}

	public void setListaErogazioniByCaso(List<ErogInterventoRowBean> listaErogazioniByCaso) {
		this.listaErogazioniByCaso = listaErogazioniByCaso;
	}

	public boolean isOnUpdateErogazioni() {
		return onUpdateErogazioni;
	}

	public void setOnUpdateErogazioni(boolean onUpdateErogazioni) {
		this.onUpdateErogazioni = onUpdateErogazioni;
		this.setTabViewIndex(0);
	}

	public PaiAffidoBean getPaiAffidoBean() {
		return paiAffidoBean;
	}

	public void setPaiAffidoBean(PaiAffidoBean paiAffidoBean) {
		this.paiAffidoBean = paiAffidoBean;
	}


	// fine evoluzione-pai
	public Date getDataNuovoMonitoraggio() {
		return dataNuovoMonitoraggio;
	}

	public void setDataNuovoMonitoraggio(Date dataNuovoMonitoraggio) {
		this.dataNuovoMonitoraggio = dataNuovoMonitoraggio;
	}

	public PaiSalBean getPaiSalBean() {
		return paiSalBean;
	}

	public void setPaiSalBean(PaiSalBean paiSalBean) {
		this.paiSalBean = paiSalBean;
	}

	public PaiPTIBean getPaiPTIBean() {
		return paiPTIBean;
	}

	public void setPaiPTIBean(PaiPTIBean paiPTIBean) {
		this.paiPTIBean = paiPTIBean;
	}

	@Override
	public PERIODO_TEMPORALE[] getListaPeriodi() {
		return DataModelCostanti.Pai.PERIODO_TEMPORALE.values();
	}

	// Inizio SISO-1110
	public String getViewIntervento() {
		if (!isTreeViewTipoIntervento())
			return "/jsp/protected/treeTipoIntervento/tipoInterventoSelect.xhtml";
		else
			return "/jsp/protected/treeTipoIntervento/tipoInterventoTreePai.xhtml";
	}
	// Fine SISO-1110

	// SISO-1131
	public List<CsTbProgettoAltro> loadListaProgettoAltro(String query) {
		List<CsTbProgettoAltro> result = new ArrayList<CsTbProgettoAltro>();

		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
		fillEnte(d);
		if (!query.isEmpty()) {
			d.setObj(query);
			result = interventoService.findProgettiAltroPerDesc(d);
		}
		return result;
	}

	public void onProgettoAltroSelect(javax.faces.event.AjaxBehaviorEvent event) {
		this.selectedPai.setCsTbProgettoAltro(selectedProgettoAltro);
	}

	@Override
	public boolean isAbilitaMenuProgettiAltro() {
		String tipoProgettoAltro = "";
		if (this.selectedPai.getTipoProgettoId() != null) {
			if (this.selectedPai.getTipoProgettoId() >= 0) {

				for (SelectItem itm : lstArFfProgetti) {
					if (itm.getValue().equals(this.selectedPai.getTipoProgettoId())) {
						tipoProgettoAltro = itm.getLabel();
						this.abilitaMenuProgettiAltro = (!tipoProgettoAltro.isEmpty()
								&& tipoProgettoAltro.equalsIgnoreCase("ALTRO"));
						break;
					}
				}

			} else {
				this.abilitaMenuProgettiAltro = false;
			}
		} else {
			this.abilitaMenuProgettiAltro = false;
		}
		return this.abilitaMenuProgettiAltro;
	}

	public void setAbilitaMenuProgettiAltro(boolean abilitaMenuProgettiAltro) {
		this.abilitaMenuProgettiAltro = abilitaMenuProgettiAltro;
	}

	/**
	 * @return the selectedProgettoAltro
	 */
	public CsTbProgettoAltro getSelectedProgettoAltro() {
		return selectedProgettoAltro;
	}

	/**
	 * @param selectedProgettoAltro the selectedProgettoAltro to set
	 */
	public void setSelectedProgettoAltro(CsTbProgettoAltro selectedProgettoAltro) {
		this.selectedProgettoAltro = selectedProgettoAltro;
	}

	public void onChangeProgetto(AjaxBehaviorEvent event) {

		long tipoProgettoId = (Long) ((javax.faces.component.UIInput) event.getComponent()).getValue();

		String progetto = "";

		for (SelectItem itm : lstArFfProgetti) {
			if (itm.getValue().equals(this.selectedPai.getTipoProgettoId())) {
				progetto = itm.getLabel();
				break;
			}
		}

		if (!progetto.isEmpty() && progetto.equalsIgnoreCase("ALTRO")) {
			this.setSelectedProgettoAltro(new CsTbProgettoAltro());
			this.selectedPai.setCsTbProgettoAltro(new CsTbProgettoAltro());
		}

	}
	// FINE SISO-1131

	public List<SelectItem> getLstTipoBeneficiario() {
		List<SelectItem> lstTipoBeneficiario = new ArrayList<SelectItem>();
		lstTipoBeneficiario.add(new SelectItem(null, ""));
		lstTipoBeneficiario.add(new SelectItem(DataModelCostanti.ListaBeneficiari.INDIVIDUALE,
				DataModelCostanti.ListaBeneficiari.INDIVIDUALE, DataModelCostanti.ListaBeneficiari.INDIVIDUALE));
		lstTipoBeneficiario.add(new SelectItem(DataModelCostanti.ListaBeneficiari.NUCLEO,
				DataModelCostanti.ListaBeneficiari.NUCLEO, DataModelCostanti.ListaBeneficiari.NUCLEO));
		lstTipoBeneficiario.add(new SelectItem(DataModelCostanti.ListaBeneficiari.GRUPPO,
				DataModelCostanti.ListaBeneficiari.GRUPPO, DataModelCostanti.ListaBeneficiari.GRUPPO));
		return lstTipoBeneficiario;

	}

	public Long getCasoFromCF(String codiceFiscale) {

		return null;
	}

	public List<String> getListaOpzioniBen() {
		if (listaOpzioniBen == null) {
			listaOpzioniBen = new ArrayList<String>();
			listaOpzioniBen.add(DataModelCostanti.ListaBeneficiari.INDIVIDUALE);
			listaOpzioniBen.add(DataModelCostanti.ListaBeneficiari.NUCLEO);
			listaOpzioniBen.add(DataModelCostanti.ListaBeneficiari.GRUPPO);
		}
		return listaOpzioniBen;
	}

	public boolean isFromFascicoloCartellaUtente() {
		return fromFascicoloCartellaUtente;
	}

	public void setFromFascicoloCartellaUtente(boolean fromFascicoloCartellaUtente) {
		this.fromFascicoloCartellaUtente = fromFascicoloCartellaUtente;
	}

	/////// BENEFICIARIIIIIIIIIIIIIIIIII

	public boolean isSoggettoSelectedFromAnagrafe() {
		return soggettoSelectedFromAnagrafe;
	}

	public void setSoggettoSelectedFromAnagrafe(boolean soggettoSelectedFromAnagrafe) {
		this.soggettoSelectedFromAnagrafe = soggettoSelectedFromAnagrafe;
	}

	public boolean isDisabilitaDatiBeneficiario() {
		// TODO--> disabilitato se il PAI è stato salvato e se l'operatore non è il
		// proprietario
		return false; // !this.getErogazionePossibile() || this.getTestataDisabled();
	}

	public CsPaiMastSoggDTO getSoggRiferimentoPai() {
		return soggRiferimentoPai;
	}

	public void setSoggRiferimentoPai(CsPaiMastSoggDTO soggRiferimentoPai) {
		this.soggRiferimentoPai = soggRiferimentoPai;
	}

	public SessoBean getSessoBeneficiario() {
		return sessoBeneficiario;
	}

	public void setSessoBeneficiario(SessoBean sessoBeneficiario) {
		this.sessoBeneficiario = sessoBeneficiario;
	}

	public boolean isOperatoreProprietario() {
		Long currentOperatoreID = getCurrentOpSettore().getCsOOperatore().getId();
		boolean proprietario = false;
//		if(nuovoIntEsegMast!=null && nuovoIntEsegMast.getCsOOperatoreSettore()!=null)
//			proprietario = currentOperatoreID.equals(this.nuovoIntEsegMast.getCsOOperatoreSettore().getCsOOperatore().getId());
		return proprietario;
	}

	public void onChangeTipoBeneficiario(AjaxBehaviorEvent event) {
		if (this.isUnicoBeneficiario()) {
//			altriSoggetti = new HashSet<CsPaiMastSoggDTO>();
			altriSoggetti = new ArrayList<CsPaiMastSoggDTO>();
			soggRiferimentoPai.setIntestatario(true);
			selectedPai.setTotBeneficiari(Integer.valueOf(1));
		} else if (selectedPai.getTotBeneficiari() == null) {
			selectedPai.setTotBeneficiari(Integer.valueOf(1));
		}

	}

	public boolean isUnicoBeneficiario() {
		return selectedPai != null
				? DataModelCostanti.ListaBeneficiari.INDIVIDUALE.equalsIgnoreCase(selectedPai.getTipoBeneficiario())
				: true;
	}

	public boolean isNucleoBeneficiario() {
		return selectedPai != null
				? DataModelCostanti.ListaBeneficiari.NUCLEO.equalsIgnoreCase(selectedPai.getTipoBeneficiario())
				: false;
	}

	public boolean isGruppoBeneficiario() {
		return selectedPai != null
				? DataModelCostanti.ListaBeneficiari.GRUPPO.equalsIgnoreCase(selectedPai.getTipoBeneficiario())
				: false;
	}

	/**
	 * la funzione ritorna true se il soggetto è stato selezionato dall'anagrafe o
	 * se sono in modifica
	 * 
	 * @return
	 */
	public boolean isDisabilitaBeneficiarioRif() {

		return soggRiferimentoPai.isValorizzato() || selectedPai.getDiarioId() != null || fromFascicoloCartellaUtente;
	}

	public List<String> getListaCittadinanze() {
		if (listaCittadinanze == null) {
			listaCittadinanze = new ArrayList<String>();
			try {
				AccessTableNazioniSessionBeanRemote bean = (AccessTableNazioniSessionBeanRemote) ClientUtility
						.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableNazioniSessionBean");
				listaCittadinanze = bean.getCittadinanze();

			} catch (NamingException e) {
				addErrorFromProperties("caricamento.error");
				logger.error(e.getMessage(), e);
			}
		}

		return listaCittadinanze;
	}

	public List<CsPaiMastSoggDTO> getAltriSoggetti() {
		if (this.altriSoggetti == null)
			altriSoggetti = new ArrayList<CsPaiMastSoggDTO>();
		return altriSoggetti;
	}

	public void setAltriSoggetti(List<CsPaiMastSoggDTO> altriSoggetti) {
		this.altriSoggetti = altriSoggetti;
	}

	public ComuneNazioneResidenzaMan getComuneNazioneResidenzaMan() {
		return comuneNazioneResidenzaMan;
	}

	public void setComuneNazioneResidenzaMan(ComuneNazioneResidenzaMan comuneNazioneResidenzaMan) {
		this.comuneNazioneResidenzaMan = comuneNazioneResidenzaMan;
	}

	public RisorsaFamiliareBean getRisorsaFamBean() {
		return risorsaFamBean;
	}

	public void setRisorsaFamBean(RisorsaFamiliareBean risorsaFamBean) {
		this.risorsaFamBean = risorsaFamBean;
	}

	public boolean isNuovoInsSoggManuale() {
		return nuovoInsSoggManuale;
	}

	public void setNuovoInsSoggManuale(boolean nuovoInsSoggManuale) {
		this.nuovoInsSoggManuale = nuovoInsSoggManuale;
	}

	public CsPaiMastSoggDTO getAltroSoggettoTmp() {
		if (altroSoggettoTmp == null)
			altroSoggettoTmp = new CsPaiMastSoggDTO(false);
		return altroSoggettoTmp;
	}

	public void setAltroSoggettoTmp(CsPaiMastSoggDTO altroSoggettoTmp) {
		this.altroSoggettoTmp = altroSoggettoTmp;
	}

	public CsPaiMastSoggDTO getSoggettoSelezionato() {
		return soggettoSelezionato;
	}

	public void setSoggettoSelezionato(CsPaiMastSoggDTO soggettoSelezionato) {
		this.soggettoSelezionato = soggettoSelezionato;
	}

	public Boolean getCambiaBeneficiarioRiferimento() {
		return cambiaBeneficiarioRiferimento;
	}

	public void setCambiaBeneficiarioRiferimento(Boolean cambiaBeneficiarioRiferimento) {
		this.cambiaBeneficiarioRiferimento = cambiaBeneficiarioRiferimento;
	}

	public Boolean getTestataDisabled() {
		return testataDisabled;
	}

	public void setTestataDisabled(Boolean testataDisabled) {
		this.testataDisabled = testataDisabled;
	}

	public InserimentoConsuntivazioneDTO getSelectedConsuntivazione() {
		return selectedConsuntivazione;
	}

	public void setSelectedConsuntivazione(InserimentoConsuntivazioneDTO selectedConsuntivazione) {
		this.selectedConsuntivazione = selectedConsuntivazione;
	}

	public String getMessaggioConsuntivazione() {
		return messaggioConsuntivazione;
	}

	public void setMessaggioConsuntivazione(String messaggioConsuntivazione) {
		this.messaggioConsuntivazione = messaggioConsuntivazione;
	}
	
	public String getMessConsuntivazioneChiusura() {
		return messConsuntivazioneChiusura;
	}

	public void setMessConsuntivazioneChiusura(String messConsuntivazioneChiusura) {
		this.messConsuntivazioneChiusura = messConsuntivazioneChiusura;
	}

	public List<CsTbSchedaMultidim> getNazioneResidenzaList() {
		List<CsTbSchedaMultidim> result = new ArrayList<CsTbSchedaMultidim>();

		CsTbSchedaMultidim prova = new CsTbSchedaMultidim();
		prova.setDescrizione("Stato estero di residenza");
		prova.setTooltip(
				"Lo stato estero di residenza è un dato obbligatorio. Qualora non si conosca lo stato estero di residenza del beneficiario selezionare il check Non disponibile");
		result.add(prova);

		return result;
	}

	public void sincronizzaResidenza(CsPaiMastSoggDTO soggDTO) {
		this.csASoggetto = soggDTO.getCaso() != null ? soggDTO.getCaso().getCsASoggetto() : null;
		if (this.csASoggetto != null) {
			CsAAnaIndirizzo residenza = findIndirizzoResidenzaCaso(this.csASoggetto);
			String via = residenza != null ? residenza.getLabelIndirizzo() : null;
			soggDTO.sincronizzaResidenza(via, getCasoComuneResidenza(residenza), residenza.getStatoCod());
			this.valorizzaResidenzaMan();
		}
	}

	public void valorizzaResidenzaMan() {
		// Estraggo i dati e deserializzo
		ObjectMapper om = new ObjectMapper();
		try {

			if (!StringUtils.isBlank(soggRiferimentoPai.getNazioneResidenza())) {
				this.comuneNazioneResidenzaMan.setNazioneValue();
				AmTabNazioni naz = luoghiService.getNazioneByIstat(soggRiferimentoPai.getNazioneResidenza());
				this.comuneNazioneResidenzaMan.getNazioneResidenzaMan().setNazione(naz);
			} else if (StringUtils.isBlank(soggRiferimentoPai.getNazioneResidenza()) && soggRiferimentoPai.isNazioneResidenzaNonDefinita()) {
				this.comuneNazioneResidenzaMan.setNazioneValue();
			} else {
				this.comuneNazioneResidenzaMan.setComuneValue();
				if (!StringUtils.isBlank(soggRiferimentoPai.getComuneResidenza()))
					this.comuneNazioneResidenzaMan.getComuneMan().setComune(om.readValue(soggRiferimentoPai.getComuneResidenza(), ComuneBean.class));
			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

	protected void loadSoggettoPaiSelezionato(DatiUserSearchBean sbean) {
		ObjectMapper om = new ObjectMapper();
		String idPersonaSelezionata = sbean != null ? sbean.getId() : null;
		this.comuneNazioneNascitaMan = new ComuneNazioneNascitaMan();
		if (StringUtils.isNotEmpty(idPersonaSelezionata)) {

			this.soggettoSelectedFromAnagrafe = true;
			PersonaDettaglio p = null;
			if (sbean.getSoggetto() != null)
				p = (PersonaDettaglio) sbean.getSoggetto();

			// Provenendo dall'anagrafe marche i dati sono già completi, non serve richiamare il WS
			if (idPersonaSelezionata.trim().startsWith(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA))
				p = getPersonaDaAnagEsterna(TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA, idPersonaSelezionata.replace(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA, ""));
			if (idPersonaSelezionata.trim().startsWith(DataModelCostanti.TipoRicercaSoggetto.SIGESS))
				p = getPersonaDaAnagEsterna(TipoRicercaSoggetto.SIGESS,idPersonaSelezionata.replace(DataModelCostanti.TipoRicercaSoggetto.SIGESS, ""));

			if (p != null) {
				comuneNazioneNascitaMan.init(p.getComuneNascita(), p.getNazioneNascita());

				String jsonResidenza = null;
				if (p.getComuneResidenza() != null) {
					ComuneBean comuneResidenza = new ComuneBean(p.getComuneResidenza());
					try {
						jsonResidenza = om.writeValueAsString(comuneResidenza);
					} catch (JsonProcessingException e1) {}
				}
				soggRiferimentoPai = new CsPaiMastSoggDTO(p.getCognome(), p.getNome(), p.getCodfisc(),
						p.getCittadinanza(), p.getDataNascita(), p.getIndirizzoCivicoResidenza(), jsonResidenza,
						p.getSesso(), true);

	
				/*Verifico la  corrispondenza dei dati anagrafici con quelli del caso*/		
				String codFisPersona = p!=null ? p.getCodfisc() : null;
				CsASoggettoLAZY soggetto = this.getSchedaCSByCF(codFisPersona);
				if (soggetto != null){
					CsAAnaIndirizzo residenza = findIndirizzoResidenzaCaso(soggetto);
					String via = residenza!=null ? residenza.getLabelIndirizzo() : null;
					soggRiferimentoPai.integraDatiMancanti(soggetto, via, getCasoComuneResidenza(residenza));
					
				    if(p.getComuneNascita()==null && p.getNazioneNascita()==null){
				    	CsAAnagrafica ana =  soggetto.getCsAAnagrafica();
				    	comuneNazioneNascitaMan = getComuneNazioneNascitaMan(
				    			 ana.getComuneNascitaCod(), ana.getComuneNascitaDes(), ana.getProvNascitaCod(), 
								 ana.getStatoNascitaCod(), ana.getStatoNascitaDes());
				    	
				    }
				}
				
				valorizzaResidenzaMan();	
			}
		} else
			soggRiferimentoPai = null;

		UserSearchBeanExt ubean = (UserSearchBeanExt) getReferencedBean("userSearchBeanExt");
		ubean.clearParameters();
	}

	// Altri beneficiari
	public void initRisorsaFamiliareBean(String cf) {
		risorsaFamBean = null;
		if (this.isNucleoBeneficiario())
			this.risorsaFamBean = new RisorsaFamiliareBean(cf);
	}

	public void onChangeTipoNuovoInsSogg() {
		if (nuovoInsSoggManuale) {
			// beneficiarioSearchBean= new UserSearchBean();
		} else {
			altroSoggettoTmp = new CsPaiMastSoggDTO(false);
		}
	}

	public void addSoggettoManuale() {

		boolean valido = true;
		if (this.altroSoggettoTmp.getCf() == null || altroSoggettoTmp.getCf().isEmpty()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Inserire il codice fiscale del nuovo beneficiario");
			valido = false;
		}

		if (this.altroSoggettoTmp.getCognome() == null || altroSoggettoTmp.getCognome().isEmpty()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Inserire il cognome del nuovo beneficiario");
			valido = false;
		}

		if (this.altroSoggettoTmp.getNome() == null || altroSoggettoTmp.getNome().isEmpty()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Inserire il nome del nuovo beneficiario");
			valido = false;
		}

		if (this.altroSoggettoTmp.getCittadinanza() == null || altroSoggettoTmp.getCittadinanza().isEmpty()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Inserire la cittadinanza del nuovo beneficiario");
			valido = false;
		}

		if (this.altroSoggettoTmp.getAnnoNascita() == null || altroSoggettoTmp.getAnnoNascita() < 1900) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Inserire l'anno di nascita del nuovo beneficiario");
			valido = false;
		}

		if (valido && !isSoggettoPresente(this.altroSoggettoTmp.getCf())) {
			this.getAltriSoggetti().add(this.altroSoggettoTmp);
			this.altroSoggettoTmp = new CsPaiMastSoggDTO(false);
		} else
			addWarning("Non è possibile inserire il soggetto come beneficiario",
					"Il codice fiscale del soggetto selezionato è già presente");

	}

	private boolean isSoggettoPresente(String cf) {
		boolean trovato = false;
		if (!StringUtils.isBlank(cf)) {
			// Verifico se coincide con quello di riferimento
			if (this.getSoggRiferimentoPai().getCf().equalsIgnoreCase(cf))
				trovato = true;

			// Verifico se esiste nella lista dei soggetti
			for (Iterator<CsPaiMastSoggDTO> it = this.getAltriSoggetti().iterator(); it.hasNext();) {
				CsPaiMastSoggDTO r = it.next();
				if (r.getCf().equalsIgnoreCase(cf)) {
					trovato = true;
				}
			}
		}
		return trovato;
	}

	public void addDaAnagrafeWrapper(DatiUserSearchBean s) {
		ObjectMapper om = new ObjectMapper();
		if (s == null) {
			addWarning("Scegliere un soggetto o inserirlo manualmente", "");
			return;
		}

		CsPaiMastSoggDTO soggPai = null;
		if (s != null) {

			PersonaDettaglio p = (PersonaDettaglio) s.getSoggetto();
			/* Devo comunque interrogare il WS per recuperare i dati estesi */
			if (p.getProvenienzaRicerca().equalsIgnoreCase(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA)
					|| p.getProvenienzaRicerca().equalsIgnoreCase(DataModelCostanti.TipoRicercaSoggetto.SIGESS))
				p = CsUiCompBaseBean.getPersonaDaAnagEsterna(p.getProvenienzaRicerca(), p.getIdentificativo());

			String json = null;
			if (p.getComuneResidenza() != null) {
				ComuneBean cb = new ComuneBean(p.getComuneResidenza());
				try {
					json = om.writeValueAsString(cb);
				} catch (JsonProcessingException e) {}
			}
			soggPai = new CsPaiMastSoggDTO(p.getCognome(), p.getNome(), p.getCodfisc(), p.getCittadinanza(),
					p.getDataNascita(), p.getIndirizzoCivicoResidenza(), json, p.getSesso(), false);

			UserSearchBeanExt ubean = (UserSearchBeanExt) getReferencedBean("userSearchBeanExt");
			ubean.clearParameters();

			if (soggPai != null) {
				if (!isSoggettoPresente(soggPai.getCf())) {
					CsASoggettoLAZY caso = getSchedaCSByCF(soggPai.getCf());
					soggPai.setCsASoggetto(caso);

					this.getAltriSoggetti().add(soggPai);
				} else {
					addWarning("Non è possibile inserire il soggetto come beneficiario",
							"Il codice fiscale del soggetto selezionato è già presente");
					return;
				}
			}
		}
	}

	public CsASoggettoLAZY getSchedaCSByCF(String cf) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(cf);
		try {
			AccessTableSoggettoSessionBeanRemote soggettiService = (AccessTableSoggettoSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
			return soggettiService.getSoggettoByCF(dto);

		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public void aggiornaSoggettoRiferimento() {
		this.soggRiferimentoPai.setIntestatario(false);
		this.altriSoggetti.add(this.soggRiferimentoPai);

		this.soggettoSelezionato.setIntestatario(true);
		this.soggRiferimentoPai = this.soggettoSelezionato;
		this.altriSoggetti.remove(this.soggettoSelezionato);
		this.soggettoSelezionato = null;

		this.valorizzaResidenzaMan();

	}

	public void onUpdateDatiRif(CsPaiMastSoggDTO soggPai) {
		if (soggPai.getIntestatario()) {
			soggRiferimentoPai.setCittadinanza(soggPai.getCittadinanza());
			soggRiferimentoPai.setAnnoNascita(soggPai.getAnnoNascita());
		}
	}

	public void onUpdateDatiPrinc() {
		for (CsPaiMastSoggDTO s : this.getAltriSoggetti()) {
			if (s.getIntestatario()) {
				s.setCittadinanza(soggRiferimentoPai.getCittadinanza());
				s.setAnnoNascita(soggRiferimentoPai.getAnnoNascita());
				s.setSecondaCittadinanza(soggRiferimentoPai.getSecondaCittadinanza());
			}
		}
	}

	public String getLabelLuogoResidenza(CsPaiMastSoggDTO soggPai) {
		String value = "-";
		try {

			if (!StringUtils.isBlank(soggPai.getComuneResidenza())) {
				ObjectMapper om = new ObjectMapper();
				ComuneBean bean = om.readValue(soggPai.getComuneResidenza(), ComuneBean.class);
				value = bean.getDenominazione() + " (" + bean.getSiglaProv() + ")";
			} else {
				if (!StringUtils.isBlank(soggPai.getNazioneResidenza())) {
					AmTabNazioni naz = luoghiService.getNazioneByIstat(soggPai.getNazioneResidenza());
					value = naz.getNazione();
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return value;
	}

	public void removeBeneficiario(CsPaiMastSoggDTO soggPai) {
		this.getAltriSoggetti().remove(soggPai);
	}

	public Long getIdCasoSoggEsterno() {
		return idCasoSoggEsterno;
	}

	public void setIdCasoSoggEsterno(Long idCasoSoggEsterno) {
		this.idCasoSoggEsterno = idCasoSoggEsterno;
	}

	public StrutturaDisponibilitaDTO getStrutturaAccettataDaEnte() {
		return strutturaAccettataDaEnte;
	}

	public void setStrutturaAccettataDaEnte(StrutturaDisponibilitaDTO strutturaAccettataDaEnte) {
		this.strutturaAccettataDaEnte = strutturaAccettataDaEnte;
	}

	public void onCFBlurAction() {
		progettiIndividualiExtBean = (ProgettiIndividualiExtBean) this.getBeanReference("progettiInvidualiExt");
	
		if (!StringUtils.isBlank(soggRiferimentoPai.getCf())) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(soggRiferimentoPai.getCf());
			CsASoggettoLAZY sogg = soggettoService.getSoggettoByCF(dto);
			idCasoSoggEsterno = sogg != null && sogg.getCsACaso() != null ? sogg.getCsACaso().getId() : null;

			if (sogg != null) {
				if (isClasseProgetto(PaiProgettiEnum.SAL))
					paiSalBean.nuovo();
				else if (isClasseProgetto(PaiProgettiEnum.AFFIDO))
					paiAffidoBean.nuovo(sogg.getAnagraficaId());
			}
			// se l'anagrafica selezionata e' presente nella cartella sociale
			if (idCasoSoggEsterno != null) {
				soggRiferimentoPai.setCasoId(idCasoSoggEsterno);
				refreshPicklistRelazioni();
				refreshPicklistInterventi();
				refreshPicklistErogazioni();
				List<CsCCategoriaSociale> lstCatSoc = loadCatSocialiAttuali(sogg.getAnagraficaId());
				tipoInterventoManBean = new TipoInterventoManBean(listTipoInterventos, getFilterCategorie(lstCatSoc));
				progettiIndividualiExtBean.initializeData(sogg, lstCatSoc);

			} else {

				getPicklistRelazioniEsterneFromCF(soggRiferimentoPai.getCf());
				getPicklistInterventiEsterniFromCF(soggRiferimentoPai.getCf());
				getPicklistErogazioniEsterniFromCF(soggRiferimentoPai.getCf());

				progettiIndividualiExtBean.initializeData(soggRiferimentoPai);
				tipoInterventoManBean = new TipoInterventoManBean(listTipoInterventos, "");

			}

		}

	}

	public void onAnnoNascitaChange() {
		FacesContext context = FacesContext.getCurrentInstance();
		progettiIndividualiExtBean = (ProgettiIndividualiExtBean) context.getApplication()
				.evaluateExpressionGet(context, "#{progettiInvidualiExt}", ProgettiIndividualiExtBean.class);

		//controllare se sono stateselezionate delle strutture e controllare
		if (isClasseProgetto(PaiProgettiEnum.PTI)) {
			if(soggRiferimentoPai.getAnnoNascita()!=null){
			this.paiPTIBean.setAnnoNascitaMinore(soggRiferimentoPai.getAnnoNascita());
			this.paiPTIBean.refreshListaStrutture();
			RequestContext.getCurrentInstance().update("frmProgettiIndividuali:paiExtComponent:pnlRichiestaDisp");
			}else {
				addError("Errore", "Inserire la data di nascita del beneficiario");
				return;
			}
		}

	}

	public CsASoggettoLAZY recuperaSoggetto(String cf) {

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(cf);
		return soggettoService.getSoggettoByCF(dto);

	}

	// SISO-1275
	public boolean esisteMotivoChiusura(CsPaiFaseChiusuraPK pk) {
		CsPaiFaseChiusuraDTO csPaiFaseChiusuraDTO = new CsPaiFaseChiusuraDTO();
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(pk);
		csPaiFaseChiusuraDTO = confService.getPaiFaseChiusuraById(dto);
		return csPaiFaseChiusuraDTO != null;
	}

	// SISO-1275
	public boolean esisteMotivoChiusuraFaseByTipoPai(CsPaiFaseChiusuraPK pk) {
		Boolean esisteMo = false;
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(pk.getTipoPai());
		esisteMo = confService.esisteAlmenoUnMotivoChiusura(dto);
		return esisteMo;
	}

	public void setVisualizzaConsuntivazione(boolean visualizzaConsuntivazione) {
		this.visualizzaConsuntivazione = visualizzaConsuntivazione;
	}

	@Override
	public boolean getProvenienzaConsuntivazione() {
		return provenienzaConsuntivazione;
	}

	public void setProvenienzaConsuntivazione(boolean provenienzaConsuntivazione) {
		this.provenienzaConsuntivazione = provenienzaConsuntivazione;
	}

	@Override
	public void erogaServizio(InserimentoConsuntivazioneDTO consuntivazione) {
		setProvenienzaConsuntivazione(true);
		this.getPaiPTIBean().refreshFLagErogatoConsuntivazione();// rileggo le consuntivazioni

		// controlla esistenza di almeno una erogazione con stato di avvio
		ErogInterventoRowBean masterA = masterAvvio();

		if (masterA != null) {
			Object obj = masterA;
			InterventiBean interventiBean = null;
			if (fromFascicoloCartellaUtente) {
				fascicoloBean = (FascicoloBean) this.getBeanReference("fascicoloBean");
				interventiBean = fascicoloBean.getInterventiBean();
			} else {
				progettiIndividualiExtBean = (ProgettiIndividualiExtBean) this.getBeanReference("progettiInvidualiExt");
				interventiBean = progettiIndividualiExtBean.getInterventiBean();
			}
			initializeInterventiDialog(interventiBean, consuntivazione, obj);

		} else {
			addWarning("Non è possibile erogare il serivizio in quanto non esistono erogazioni aperte", "");
			return;
		}

	}

	public void initializeInterventiDialog(InterventiBean interventiBean, InserimentoConsuntivazioneDTO consuntivazione, Object rowMasterErog) {
		CsASoggettoLAZY soggetto = this.recuperaSoggetto(soggRiferimentoPai.getCf());

		if (soggetto != null)
			interventiBean.getErogazioniInterventiBean().SetFromPai(soggetto);
		else
			interventiBean.getErogazioniInterventiBean().SetFromPai(soggRiferimentoPai);

		interventiBean.getErogazioniInterventiBean().inizializzaDialogo(rowMasterErog, consuntivazione);
	}

	public ErogInterventoRowBean masterAvvio() {
		Boolean esisteRigaErogazione = false;
		Boolean esisteChiusura = false;
		ErogInterventoRowBean master = null;
		if (this.picklistErogazioni != null && this.picklistErogazioni.getTarget() != null) {
			for (ErogInterventoRowBean eirb : picklistErogazioni.getTarget()) {
				for (ErogazioneDettaglioDTO d : eirb.getLstInterventiEseguiti()) {
					esisteRigaErogazione = true;

					if (d.getStatoErogazione().getFlagChiudi()) {
						esisteChiusura = true;

					}

				}
				if (esisteRigaErogazione && !esisteChiusura) {
					master = eirb;
					break;
				}
			}

		}
		return master;
	}

	@Override
	public void verificaPeriodi(InserimentoConsuntivazioneDTO consuntivazione) throws Throwable {
		String msgSicuro = "Sei sicuro di voler impostare questa consuntivazione come 'erogata'?";
		boolean rigaTrovata = false;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		setSelectedConsuntivazione(consuntivazione);

		Date consDa = formatter.parse(formatter.format(consuntivazione.getDataDa()));
		Date consA = formatter.parse(formatter.format(consuntivazione.getDataA()));

		if (this.picklistErogazioni != null && this.picklistErogazioni.getTarget() != null) {
			for (ErogInterventoRowBean eirb : picklistErogazioni.getTarget()) {

				for (ErogazioneDettaglioDTO d : eirb.getLstInterventiEseguiti()) {

					if (!d.getStatoErogazione().getFlagChiudi() && d.getStatoErogazione().getTipo().equalsIgnoreCase(DataModelCostanti.TipoStatoErogazione.EROGATIVO)) {

						Date dataErogazioneDa = formatter.parse(formatter.format(d.getDataErogazione()));
						Date dataErogazioneA = formatter.parse(formatter.format(d.getDataErogazioneA()));

						// ciclo la consuntivazione
						if (consDa.equals(dataErogazioneDa) && consA.equals(dataErogazioneA)) {
							// esiste periodo di erogazione preciso
							setMessaggioConsuntivazione(msgSicuro);
							rigaTrovata = true;
							break;
						}
						if (between(consDa, dataErogazioneDa, dataErogazioneA) || between(consA, dataErogazioneDa, dataErogazioneA)) {
							setMessaggioConsuntivazione(msgSicuro+ " Esiste una erogazione che va dal "
											+ formatter.format(dataErogazioneDa) + " al "
											+ formatter.format(dataErogazioneA));
							rigaTrovata = true;
							break;

						}

					}

				}
				if (rigaTrovata)
					break;
			}

		}
		if (!rigaTrovata)
			// se sono qui non esiste alcun intervallo
			setMessaggioConsuntivazione(msgSicuro+" Non Esiste una erogazione che comprende le date di consuntivazione ");
	}

	@Override
	public void aggiornaFLagErogatoConsuntivazione() throws Throwable {
		if (this.selectedConsuntivazione != null) {

			this.getPaiPTIBean().aggiornaConsuntivazione(selectedConsuntivazione);
		}

	}

	@Override
	public void refreshFLagErogatoConsuntivazione() throws Throwable {
		// TODO Auto-generated method stub
		this.getPaiPTIBean().refreshFLagErogatoConsuntivazione();
	}

	@Override
	public void avviaModificaPTI(CsPaiPTIDTO pti) throws Exception {
		CsOOperatore op = null;
		this.getPaiPTIBean().setVisualizzaMotiviRevisione(true);

		ptiRevisione = new CsPaiPtiRevisioniDTO();
//		BeanUtils.copyProperties(pti, ptiRevisione);
		ptiRevisione.setIdPaiPTI(pti.getId());
		ptiRevisione.setCodRouting(pti.getCodRouting());
		ptiRevisione.setDataRedazione(pti.getDataRedazione());
		ptiRevisione.setDiarioSinbaId(pti.getDiarioSinbaId());
		ptiRevisione.setFlgAreaPenale(pti.getFlgAreaPenale());
		ptiRevisione.setFlgCoinvFamiglia(pti.getFlgCoinvFamiglia());
		ptiRevisione.setFlgCondVerifPresenzaAdulti(pti.getFlgCondVerifPresenzaAdulti());
		ptiRevisione.setFlgDisabilita(pti.getFlgDisabilita());
		ptiRevisione.setFlgEmergenza(pti.getFlgEmergenza());
		ptiRevisione.setFlgEsisteEduPeda(pti.getFlgEsisteEduPeda());
		ptiRevisione.setFlgGravidanza(pti.getFlgGravidanza());
		ptiRevisione.setFlgInterventiDisabili(pti.getFlgInterventiDisabili());
		ptiRevisione.setFlgInvioSegnTM(pti.getFlgInvioSegnTM());
		ptiRevisione.setFlgNeonato(pti.getFlgNeonato());
		ptiRevisione.setFlgProrLimiteEta(pti.getFlgProrLimiteEta());
		ptiRevisione.setFlgProrRichMagg(pti.getFlgProrRichMagg());
		if ((pti.getIdCaseManager() != null && (pti.getCaseManager() == null || pti.getCaseManager().isEmpty()))) {
			OperatoreDTO odto = new OperatoreDTO();
			fillEnte(odto);
			odto.setIdOperatore(pti.getIdCaseManager());
			op = confEnteService.findOperatoreById(odto);
		}
		if (op != null)
			ptiRevisione.setCaseManager(op.getDenominazione());
		ptiRevisione.setIdCaseManager(pti.getIdCaseManager());
		ptiRevisione.setPeriodoInsPianificazioneDa(pti.getPeriodoInsPianificazioneDa());
		ptiRevisione.setPeriodoInsPianificazioneA(pti.getPeriodoInsPianificazioneA());

		ptiRevisione.setDataMonitoraggio(selectedPai.getDataMonitoraggio());
		ptiRevisione.setMonitoraggioObiettivi(selectedPai.getMonitoraggioObiettivi());
		ptiRevisione.setObiettiviBreve(selectedPai.getObiettiviBreve());
		ptiRevisione.setObiettiviMedio(selectedPai.getObiettiviMedio());
		ptiRevisione.setObiettiviLungo(selectedPai.getObiettiviLungo());
		ptiRevisione.setRaggiuntiBreve(selectedPai.getRaggiuntiBreve());
		ptiRevisione.setRaggiuntiMedio(selectedPai.getRaggiuntiMedio());
		ptiRevisione.setRaggiuntiLungo(selectedPai.getRaggiuntiLungo());
		ptiRevisione.setVerificaOgni(selectedPai.getVerificaOgni());
		ptiRevisione.setVerificaUnitaMisura(selectedPai.getVerificaUnitaMisura());

//		ptiRevisione.setMotivoRevisione(this.motivoRevisione);

	}

	@Override
	public void verificaSelezioneMotivo() {
		if (this.motivoRevisione > 0) {
			this.getPaiPTIBean().setModifica(true);
		} else {
			addError("Revisione PTI", "e' necessairio selezionare il motivo della revisione");
			logger.error("Errore Aggiornamento Consuntivazioni");
		}
	}

	/**
	 * controllo sulla data di completamento per tutti i PTI inseriti in "EMERGENZA"
	 * l'operatore ha a disposizione 30 giorni per inserire tutti i dati richiesti
	 * 
	 * @return
	 */
	public String checkCompletamentoPTI() {
		if (this.selectedPai == null || this.getPaiPTIBean().getPti() == null
				|| this.getPaiPTIBean().getPti().getId() == null
				|| Boolean.FALSE.equals(this.getPaiPTIBean().getPti().getFlgEmergenza())) {
			return null;
		}
		int giorniMax = 30;
		Date dataIns = this.selectedPai.getCsDDiario().getDtIns();
		Date oggi = new Date();
		int tempoRimanente = giorniMax - (int) ((oggi.getTime() - dataIns.getTime()) / (1000 * 60 * 60 * 24));
		if (tempoRimanente <= 0) {
			return "Sei in ritardo per il completamento del PTI";
		}

		return "Attenzione! Hai ancora " + tempoRimanente + " giorni per completare l'inserimento del PTI";
	}

	/**
	 * controlli scadenze equipe: Deve essere effettuata una relazione ogni sei mesi
	 * dal PTI - Nel caso di SERVIZIO RESIDENZIALE A BASSA INTENSITÀ TERAPEUTICO
	 * RIABILITATIVA ART. 31 la verifica deve essere effettuata ogni 2 mesi - Nel
	 * caso di SERVIZIO RESIDENZIALE A MEDIA INTENSITÀ TERAPEUTICO RIABILITATIVA
	 * ART. 32 la verifica deve essere effettuata ogni 3 mesi
	 * 
	 * @return
	 * @throws Exception
	 */
	public String checkScadenzeEquipe() throws Exception {
		int giorniMax = 0;
		int idTipoMacro = 0;
		Integer periodo = 0;

		if (this.selectedPai == null || this.getPaiPTIBean().getPti() == null
				|| this.getPaiPTIBean().getPti().getId() == null) {
			return null;
		}

		if (this.getPaiPTIBean().getPti() != null && this.getPaiPTIBean().getPti().getIdStruttura() != null
				&& !this.selectedPai.isClosed()) {
			Long idStruttura = this.getPaiPTIBean().getPti().getIdStruttura();
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idStruttura);
			StrutturaDisponibilitaDTO struttura = paiPTIService.findStrutturaById(dto);
			Long idTipoMacroTipologiaStruttura = struttura.getIdMacroTipologiaServizio();
			idTipoMacro = idTipoMacroTipologiaStruttura.intValue();

			if (idTipoMacro == 1) {
				giorniMax = 180;// 6 mesi
				periodo = 6;
			} else if (idTipoMacro == 2) {
				giorniMax = 60; // 2 mesi
				periodo = 2;
			} else if (idTipoMacro == 3) {
				giorniMax = 90; // 3 mesi
				periodo = 3;
			}

			Date oggi = new Date();
			if (this.picklistRelazioni != null && this.picklistRelazioni.getTarget() != null
					&& this.picklistRelazioni.getTarget().size() > 0) {

				RelazioneSintesiDTO r = this.picklistRelazioni.getTarget().get(0);
				Date dataRelazione = r.getDtAmministrativa();// per la relazione vale la
																							// data ammnistrativa

				// se non è stata fatta nessuna relazione dopo 6 mesi scrivo messaggio
				int i = 1;
				int tempoRimanente = giorniMax
						- (int) ((oggi.getTime() - dataRelazione.getTime()) / (1000 * 60 * 60 * 24));
				if (tempoRimanente <= 0) {
					return "E' necessario inserire una relazione. Sono passati  " + periodo.toString()
							+ " mesi dall'ultima relazione inserita";
				}

				return "Attenzione! Hai ancora " + tempoRimanente + " giorni per inserire la prossima relazione";

			} else {
				Date dataIns = this.selectedPai.getCsDDiario().getDtAttivazioneDa();// data inizio PTI vale la data
																					// attivazione da

				int tempoRimanente = giorniMax - (int) ((oggi.getTime() - dataIns.getTime()) / (1000 * 60 * 60 * 24));
				if (tempoRimanente <= 0) {
					return "Sei in ritardo per l'inserimento della relazione. Sono passati " + periodo
							+ " mesi dalla creazione del progetto PTI ";
				}

				return "Attenzione! Hai ancora " + tempoRimanente + " giorni per inserire la relazione";

			}

		}
		return null;
	}

	@Override
	public List<RichiestaDisponibilitaPaiPtiDTO> getLstProgettiAltriEnti() {
		// TODO Auto-generated method stub
		return this.lstProgettiAltriEnti;
	}

	@Override
	public void setLstProgettiAltriEnti(List<RichiestaDisponibilitaPaiPtiDTO> lstProgettiAltriEnti) {
		this.lstProgettiAltriEnti = lstProgettiAltriEnti;

	}

	public List<RichiestaDisponibilitaPaiPtiDTO> getLstProgettiAltriEntiNonEmergenza() {
		return lstProgettiAltriEntiNonEmergenza;
	}

	public void setLstProgettiAltriEntiNonEmergenza(
			List<RichiestaDisponibilitaPaiPtiDTO> lstProgettiAltriEntiNonEmergenza) {
		this.lstProgettiAltriEntiNonEmergenza = lstProgettiAltriEntiNonEmergenza;
	}

	@Override
	public void caricaListaProgettiAltriEnti() throws Exception {
		List<RichiestaDisponibilitaPaiPtiDTO> lstAltriProgetti = new ArrayList<RichiestaDisponibilitaPaiPtiDTO>();
		List<RichiestaDisponibilitaPaiPtiDTO> lstAltriProgettiNE = new ArrayList<RichiestaDisponibilitaPaiPtiDTO>();
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getCodRouting());
		lstAltriProgetti = paiPTIService.findProgettiAltriEnti(dto);

		for (RichiestaDisponibilitaPaiPtiDTO rich : lstAltriProgetti) {

			loadDettaglioMinore(rich);
			loadDettaglioPTI(rich);
			if (rich.getPti() != null) {
				if (rich.getPti().isFlgEmergenza()) {
					lstAltriProgetti.add(rich);
				} else {
					lstAltriProgettiNE.add(rich);
				}

			}
		}

		this.lstProgettiAltriEnti = lstAltriProgetti;
		this.lstProgettiAltriEntiNonEmergenza = lstAltriProgettiNE;
		
		if (!(lstProgettiAltriEnti.size() >0))
		   addMessage(FacesMessage.SEVERITY_INFO, "Nessun progetto da altro ente da visualizzare");
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
					e.printStackTrace();
				}
				richiestaSelezionata.setMinore(dtMinore);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
//				if (jsonObject.get("faseAttuale") != null) {
//					JSONObject fase = (JSONObject) jsonObject.get("faseAttuale");
//					
//					dtPTI.setFaseAttuale(fase.getLong("idStato"));
//					if (dtPTI.getFaseAttuale().equals(PaiPTIFaseEnum.EROG_OK.getId())) {
//						prestazioneErogata = true;
//					}
//				}

				rich.setPti(dtPTI);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String retrieveComuneProvenienza(String codRouting) {
		AmTabComuni comune = luoghiService.getComuneItaByBelfiore(codRouting);
		return comune.getDenominazione();
	}
	
     public void gestisciCampiMonitoraggio() { 
		
		if(this.selectedPai.getMonitoraggioObiettivi()) {
			pulisciObiettivi();
		}
		
		
	}

	public boolean isProgettoPTI() {

		if (tipoPai!=null) {
			if(this.selectedPai !=null && this.selectedPai.getDiarioId()!=null) {
				return isClasseProgetto(PaiProgettiEnum.PTI);
			}
			return false;
		} else {
			return false;
		}

	}

	 public String decodificaObiettiviRaggiunti(Integer idRaggiunti) {
			
		 switch (idRaggiunti) {
		 case 1:
				return  "non raggiunti";
				
		 case 2:
				return  "raggiunti parzialmente";
		 case 3:
				return  "raggiunti";
		 }
		return "";
		
	 }
	 @Override
		public void salvaDataChiusuraAggiornata() throws Throwable {
		 	//Devo aggiornare la data di chiusura alla data di ultima consuntivazione
		 
		 this.selectedPai.getCsDDiario().setDtChiusuraDa(this.getDataUltimaConsuntivazioneErog());
		 this.procediSalvataggio =true;
		 salva();
		}

	
	public Date getDataUltimaConsuntivazioneErog() {
		return dataUltimaConsuntivazioneErog;
	}

	public void setDataUltimaConsuntivazioneErog(Date dataUltimaConsuntivazioneErog) {
		this.dataUltimaConsuntivazioneErog = dataUltimaConsuntivazioneErog;
	}

	public boolean isProcediSalvataggio() {
		return procediSalvataggio;
	}

	public void setProcediSalvataggio(boolean procediSalvataggio) {
		this.procediSalvataggio = procediSalvataggio;
	}

	public LazyListaPaiModel getLazyListaPaiModel() {
		return lazyListaPaiModel;
	}

	public void setLazyListaPaiModel(LazyListaPaiModel lazyListaPaiModel) {
		this.lazyListaPaiModel = lazyListaPaiModel;
	}
	
	private void aggiornaListaRelazioni(){
		List<RelazioneSintesiDTO> target =  picklistRelazioni.getTarget();
		List<Long> relIds = new ArrayList<Long>();
		for(RelazioneSintesiDTO t : target)
			relIds.add(t.getDiarioId());
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(relIds);
		listaRelazioniDTO = diarioService.findRelazioniByIds(dto);	
	}
	
	public void confermaCollegaAttivita(){
		this.setOnUpdateRelazioni(false);
		this.aggiornaListaRelazioni();
	}
	
	public void annullaCollegaAttivita(){
		this.setOnUpdateRelazioni(false);
	}
	
/*	public List<RelazioneDTO> getTargetRelazioneDTO(){
		List<RelazioneSintesiDTO> target =  picklistRelazioni.getTarget();
		List<Long> relIds = new ArrayList<Long>();
		for(RelazioneSintesiDTO t : target)
			relIds.add(t.getDiarioId());
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(relIds);
		List<RelazioneDTO> targetDTO = diarioService.findRelazioniByIds(dto);	
		return targetDTO;
	}*/
	
	public List<RelazioneDTO> getListaRelazioniDTO(){
		return this.listaRelazioniDTO;
	}
	
	private boolean isClasseProgetto(PaiProgettiEnum tipo){
		return tipo.getProgetto().equalsIgnoreCase(this.getClassePai());
	}
	
	public String getClassePai(){
		return tipoPai!=null ? tipoPai.getProgetto() : null;
	}
}
