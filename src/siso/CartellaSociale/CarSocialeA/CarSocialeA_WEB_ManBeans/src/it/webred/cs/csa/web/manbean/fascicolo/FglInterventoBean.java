package it.webred.cs.csa.web.manbean.fascicolo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.naming.NamingException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.webred.cs.csa.ejb.client.configurazione.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.PaiDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.ejb.dto.pai.CsPaiMastSoggDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.InserimentoConsuntivazioneDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.StrutturaDisponibilitaDTO;
import it.webred.cs.csa.ejb.dto.siru.StampaFseDTO;
import it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi.ErogInterventoRowBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventi.DatiProgettoBean;
import it.webred.cs.csa.web.manbean.fascicolo.pai.ProgettiIndividualiExtBean;
import it.webred.cs.csa.web.manbean.report.ReportBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.FoglioAmministrativo.STATO;
import it.webred.cs.data.DataModelCostanti.PermessiErogazioneInterventi;
import it.webred.cs.data.DataModelCostanti.TariffeErogazioni.TIPO_FREQUENZA_SERVIZIO;
import it.webred.cs.data.DataModelCostanti.TariffeErogazioni.TIPO_RENDICONTO;
import it.webred.cs.data.DataModelCostanti.TipiCategoriaSociale;
import it.webred.cs.data.DataModelCostanti.TipoStatoErogazione;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIInterventoPr;
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsRelSettCsocTipoInterPK;
import it.webred.cs.data.model.CsRelSettoreStruttura;
import it.webred.cs.data.model.CsTbMotivoChiusuraInt;
import it.webred.cs.data.model.CsTbTipoContributo;
import it.webred.cs.data.model.CsTbTipoRetta;
import it.webred.cs.data.model.CsTbTipoRientriFami;
import it.webred.cs.data.model.CsTbTitoloStudio;
import it.webred.cs.jsf.bean.DatiFglInterventoBean;
import it.webred.cs.jsf.bean.DatiInterventoBean;
import it.webred.cs.jsf.bean.DatiTariffeInterventoBean;
import it.webred.cs.jsf.bean.erogazioneIntervento.ErogazioneInterventoBean;
import it.webred.cs.jsf.bean.erogazioneIntervento.InterventoErogazHistoryRowBean;
import it.webred.cs.jsf.interfaces.IDatiFglIntervento;
import it.webred.cs.jsf.manbean.ComponenteAltroMan;
import it.webred.cs.jsf.manbean.ComuneNascitaMan;
import it.webred.cs.jsf.manbean.ComuneResidenzaMan;
import it.webred.cs.jsf.manbean.SinaMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;

@ManagedBean(eager = true)
@SessionScoped
public class FglInterventoBean extends FascicoloCompSecondoLivello implements IDatiFglIntervento {

	private static final String DATA_EROG_LABEL_DEFAULT = "Data ";
	private String dataErogLabel = DATA_EROG_LABEL_DEFAULT;

	protected String headerDialogo = "";
	protected boolean abilitaSalvataggio = false;
	private boolean rendered = true;
	private boolean datiInterventoTabRendered = true;
	private boolean datiErogazioniTabRendered = true;
	private Long idTipoIntevento;
	private Long idTipoIntrCustom;
	private Long idErogazioneMaster;

	private String widgetVar;
	protected Long settoreId;
	protected Long interventoId;
	protected Long diarioId;

	private DatiFglInterventoBean datiFglIntBean;
	private DatiInterventoBean datiInterventoBean;
	private DatiTariffeInterventoBean datiTariffeInterventoBean;
	private DatiProgettoBean datiProgettoBean;
	private ErogazioneInterventoBean erogazioneInterventoBean = new ErogazioneInterventoBean();

	private CsASoggettoLAZY soggettoCorrente;

	private List<DatiInterventoBean> listaInterventi;

	private SoggettoErogazioneBean soggettoNuovaErogazione; //USARE SOLO PER INIZIALIZZARE
	private CsIIntervento csIIntervento = null;
	private Long idCatSociale;

	// SISO-760
	private PaiDTO paidto = null;

	private final String tipoErogazioneDisableTooltip = "Modifica disabilitata in quanto ci sono delle erogazioni comprese fra la data \"Dal\" e la data \"Al\"";

	// SISO-783
	private Boolean visualizzaSinaTab = false;
	private SinaMan sinaMan;
	
	//SISO 972
	private String FSEMessage = "";
	
	private Boolean fromPai=false;
	
	//Parametri Stampa POR
	private StampaFseDTO stampaFseDTO;
	
	//ComboBox
	private List<SelectItem> lstTipoIntervento;
	private List<SelectItem> lstMotiviChiusura;
	private List<SelectItem> lstCittadinanze;
	private List<SelectItem> lstTipoQuotaPasti;
	private List<SelectItem> lstTipoQuotaCentroD;
	private List<SelectItem> lstTipoRiscossione;
	private List<SelectItem> lstTipoOreVoucher;
	private List<SelectItem> lstTipoPeriodoErogazione;
	private List<SelectItem> lstTipoAttivita;
	private List<SelectItem> lstTipoSospensione;
	private List<SelectItem> lstTipoAttivazione;
	private List<SelectItem> lstModalitaIntervento;
	private List<SelectItem> lstTipoPeriodo;
	private List<SelectItem> lstTipoGestione;
	private List<SelectItem> lstTipoDeroghe;
	private List<SelectItem> lstTipoRientriFam;
	private List<SelectItem> lstTipoRetta;
	private List<SelectItem> lstTipoAffido;
	private List<SelectItem> lstSpeseExtra;
	private List<SelectItem> lstSpeseExtraSRM;
	private List<SelectItem> lstTipoAdmAdh;
	private List<SelectItem> lstEducatori;
	private List<SelectItem> lstTipoCirc4;
	private List<SelectItem> lstTipoInterventi;
	private List<SelectItem> lstTipoProgetto;
	private List<SelectItem> lstTipoContributo;
	private List<CsTbTipoContributo> lstTipoContributoTooltip;
	
	public String getFSEMessage() {
		return FSEMessage;
	}

	public void setFSEMessage(String fSEMessage) {
		FSEMessage = fSEMessage;
	}

	public void inizializzaErogazione(SoggettoErogazioneBean soggettoErogazione) {
		csASoggetto = null;
		if (soggettoErogazione != null)
			csASoggetto = soggettoErogazione.getCsASoggetto();
		soggettoNuovaErogazione = soggettoErogazione;
		if (csASoggetto != null)
			initialize(csASoggetto);
		else {
			setIdCaso(null);
		}
	}
	
	public void inizializzaErogazione(CsPaiMastSoggDTO soggettoPai) {
		this.csASoggetto = null;
		if (soggettoPai != null)
			csASoggetto = soggettoPai.getCsASoggetto();
		soggettoNuovaErogazione = new SoggettoErogazioneBean(soggettoPai);
		if (csASoggetto != null)
			initialize(csASoggetto);
		else {
			setIdCaso(null);
		}
	}

	public void inizializzaRiferimentoErogazione(ErogInterventoRowBean row){
		csASoggetto = null;
		if (row.getBeneficiarioRiferimento() != null)
			csASoggetto = row.getBeneficiarioRiferimento().getCsASoggetto();
		soggettoNuovaErogazione = new SoggettoErogazioneBean(true);
		try {
			BeanUtils.copyProperties(soggettoNuovaErogazione, row.getBeneficiarioRiferimento());
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		}
		if (csASoggetto != null)
			initialize(csASoggetto);
		else
			setIdCaso(null);
	}
	
	@Override
	public void initialize(CsASoggettoLAZY soggetto,List<CsCCategoriaSociale> catsocCorrenti, Object data) {
		super.initialize(soggetto, catsocCorrenti, data);
		
		//SISO-962 Inizio
		CsAAnaIndirizzo residenza = findIndirizzoResidenzaCaso(soggetto);
		String comune = getCasoComuneResidenza(residenza);
		String via = residenza!=null ? residenza.getLabelIndirizzo() : null;
		String statoEstero = residenza!=null ? residenza.getStatoCod() : null;
		String jsonComuneNascita = getJsonNascitaComuneBean(soggetto);
		
		String cfCaso = soggetto!=null && soggetto.getCsAAnagrafica()!=null ? soggetto.getCsAAnagrafica().getCf() : null;
		if(this.soggettoNuovaErogazione!=null && this.soggettoNuovaErogazione.getCf().equalsIgnoreCase(cfCaso))
			soggettoNuovaErogazione.integraDatiMancanti(soggetto, via, comune, residenza.getStatoCod(), jsonComuneNascita);
		else
			soggettoNuovaErogazione = new SoggettoErogazioneBean(soggetto, via , comune, statoEstero, jsonComuneNascita, true);
	
        /*else --> ci sono dei casi in cui i dati dell'erogazione non sono allineati a quelli della cartella. Devo poter comunque mostrare quelli registrati
         * I dati dovranno comunque essere allineati in qualche modo, si potrebbe utilizzare un flag da mostrare in maschera per poter sincronizzare il dato anche 
         * se l'indirizzo di residenza potrebbe cambiare e rimanere coerente alla data dell'erogazione.
         */
		//SISO-962 Fine
	}

	public void initialize(CsPaiMastSoggDTO soggettoPaiRiferimento) {
		if(soggettoNuovaErogazione==null)
		   soggettoNuovaErogazione = new SoggettoErogazioneBean(soggettoPaiRiferimento);
	}

	@Override
	public void initializeData(Object data) {
		CsOOperatoreSettore opSettore = getCurrentOpSettore();
		settoreId = opSettore.getCsOSettore().getId();
		lstEducatori = null;
		lstTipoIntervento = null;
	}

	protected void resetDialogo(boolean datiErogazioniTabRendered,
			Long interventoId, Long diarioId, Long tipoInterventoId,
			Long tipoInterventoCustomId, Long catSocialeId, boolean readOnly,
			boolean abilitaSalvataggio, String headerDialogo) {
		this.datiErogazioniTabRendered = datiErogazioniTabRendered;
		this.interventoId = interventoId;
		this.diarioId = diarioId;
		this.idTipoIntevento = tipoInterventoId;
		this.idTipoIntrCustom = tipoInterventoCustomId;
		this.idCatSociale = catSocialeId;
		this.readOnly = readOnly;
		this.abilitaSalvataggio = abilitaSalvataggio;
		this.headerDialogo = headerDialogo;
		if (this.idTipoIntevento != null && idTipoIntevento.equals(0L))
			this.idTipoIntevento = null;
		if (this.idTipoIntrCustom != null && idTipoIntrCustom.equals(0L))
			this.idTipoIntrCustom = null;

		// Reset Beans
		datiFglIntBean = null;
		datiInterventoBean = null;
		datiTariffeInterventoBean = null;
		datiProgettoBean = new DatiProgettoBean();
		erogazioneInterventoBean.resetFlagProvaMezzi();
		csIIntervento = null; // SISO-556 fix di un bug non censito evidenziatosi durante la SISO-556
	}
	
	public void inizializzaDialog(boolean datiIntTabRendered, boolean datiErogazioniTabRendered, 
								  Long interventoId, Long diarioId, Long tipoInterventoId, Long tipoInterventoCustomId, Long catSocId, 
								  boolean readOnly, boolean abilitaSalvataggio, String headerDialogo, CsIInterventoEsegMast master, boolean dentroFascicolo, 
								  StrutturaDisponibilitaDTO struttDispo, InserimentoConsuntivazioneDTO consuntivazione) {

		logger.info("Inizializzazione Dialog FglInterventoBean");
	       
		this.toSaveSecondoLivello=false;
		
		try {
			resetDialogo(datiErogazioniTabRendered, interventoId, diarioId, tipoInterventoId, tipoInterventoCustomId, catSocId, readOnly, abilitaSalvataggio, headerDialogo);
			
			//#ROMACAPITALE
			//carico i settori eroganti filtrati per interventoISTAT e interventoCUSTOM 
			erogazioneInterventoBean.caricaListaIdSettoriByIntervento(idTipoIntevento, idTipoIntrCustom);
			loadListaSettori();

			datiInterventoTabRendered = datiIntTabRendered && csASoggetto != null;
			if (datiInterventoTabRendered) {
				loadDatiFlgInterventiBean();
				loadDatiInterventoBean();

				idTipoIntevento = datiInterventoBean.getIdTipoIntervento();
				idTipoIntrCustom = datiInterventoBean.getIdTipoIntrCustom();

				if (idTipoIntevento == null || idTipoIntevento <= 0) {
					addErrorDialog("Dati mancanti", "Selezionare un tipo intervento");
					return;
				}
			} else csIIntervento = null;
			
			if (datiErogazioniTabRendered) {
				if (datiInterventoBean != null) {
					if (datiInterventoBean.getInizioDa() != null && diarioId != null && diarioId != 0)
						erogazioneInterventoBean.setDataAttivazioneDa(datiInterventoBean.getInizioDa());
					else erogazioneInterventoBean.setDataAttivazioneDa(new Date());
				}else erogazioneInterventoBean.setDataAttivazioneDa(new Date());


				// Se ho il master, inizializzo comunque da lì
				List<SoggettoErogazioneBean> beneficiari = new ArrayList<SoggettoErogazioneBean>();
				if (master != null) {
					erogazioneInterventoBean.inizializzaDaErogazioneMaster(idTipoIntevento, idTipoIntrCustom, master, dentroFascicolo);
				}else{
					boolean conFoglioAmministrativo = interventoId != null && interventoId > 0;
					if (conFoglioAmministrativo){
						CsAAnaIndirizzo residenza = findIndirizzoResidenzaCaso(csASoggetto);
						String via = residenza!=null ? residenza.getLabelIndirizzo() : null;
						String json = getCasoComuneResidenza(residenza);
						String statoEstero = residenza!=null ? residenza.getStatoCod() : null;
						String jsonComuneNascita = this.getJsonNascitaComuneBean(csASoggetto);
						soggettoNuovaErogazione = new SoggettoErogazioneBean(csASoggetto, via, json, statoEstero, jsonComuneNascita, true);
						erogazioneInterventoBean.inizializzaErogazione(idTipoIntevento, idTipoIntrCustom, this.interventoId, soggettoNuovaErogazione, beneficiari,dentroFascicolo);				
					}else
						erogazioneInterventoBean.inizializzaNuovaErogazione(idTipoIntevento, idTipoIntrCustom, soggettoNuovaErogazione, idCatSociale,dentroFascicolo);		
				}

				if (this.interventoId != null && this.interventoId > 0) {
					if(datiFglIntBean!=null && datiFglIntBean.getFlagAttivazione()!=null && !datiFglIntBean.isAttivazione() && !datiFglIntBean.getFlagRespinto())
						erogazioneInterventoBean.setErogazionePossibile(false);
					else erogazioneInterventoBean.setErogazionePossibile(true);
				} else erogazioneInterventoBean.setErogazionePossibile(true);
			}

			/*LOAD DATI PROGETTO*/
			CsIInterventoPr progetto = null;
			if (csIIntervento != null && csIIntervento.getCsIInterventoPr() != null)
				progetto = csIIntervento.getCsIInterventoPr();
			else if(erogazioneInterventoBean!=null && erogazioneInterventoBean.getNuovoIntEsegMast()!=null)
					progetto = erogazioneInterventoBean.getNuovoIntEsegMast().getCsIInterventoPr();

			progetto = progetto!=null ? progetto : new CsIInterventoPr();
			
			//Imposto al settore corrente, se non valorizzato
			if(progetto.getSettoreTitolare()==null)
				progetto.setSettoreTitolare(getCurrentOpSettore().getCsOSettore());
			
			boolean tabProgettoSolaLettura = false;
			String soggettoErogazioneBeanCF = erogazioneInterventoBean.getSoggettoErogazione().getCf();
			datiProgettoBean.loadDatiProgetto(progetto,tabProgettoSolaLettura,idTipoIntevento, idTipoIntrCustom, catSocId, soggettoErogazioneBeanCF, this.getZonaSociale());
			
			loadDatiTariffeInterventoBean(datiProgettoBean.getSettoreTitolare());
			cmbSettoreOnChange();
			
			loadDatiPai(master != null && master.getDiarioPaiId() != null ? master.getDiarioPaiId().longValue() : null);
			String cf = erogazioneInterventoBean.getSoggettoErogazione().getCf();
			initDatiSina(catSocId, master!=null ? master.getId() : null, cf);
			initStrutturaDisponibilita(struttDispo);
			initConsuntivazione(consuntivazione);
			
			logger.info("Fine Inizializzazione Dialog FglInterventoBean");

			RequestContext.getCurrentInstance().addCallbackParam("isShowDialog", true);

			Map<String, Object> options = new HashMap<String, Object>();
			options.put("resizable", false);
			RequestContext.getCurrentInstance().openDialog("isShowDialog", options, null);
			
        }catch (Exception e) {
			logger.error("Errore inizializza Interventi Dialog", e);
			addErrorFromProperties("caricamento.error");
			RequestContext.getCurrentInstance().addCallbackParam("isShowDialog", false);
		}
	}
	
	private void initStrutturaDisponibilita(StrutturaDisponibilitaDTO struttDispo){
		if (struttDispo != null) {
			erogazioneInterventoBean.setDataErog(struttDispo.getRichiesteDisponibilita().getDataInizioPermamenza());
			erogazioneInterventoBean.setDataErogA(struttDispo.getRichiesteDisponibilita().getDataFinePermanenza());
			
			CsRelSettoreStruttura csRelSettStrutt = new CsRelSettoreStruttura();
			BaseDTO dto = new BaseDTO();
			dto.setObj(struttDispo.getRichiesteDisponibilita().getIdStruttura());
			fillEnte(dto);
			csRelSettStrutt = confEnteService.findSettoreByIdStruttura(dto);	
            erogazioneInterventoBean.setSelSettoreEroganteId(csRelSettStrutt.getIdSettore());
            erogazioneInterventoBean.setIsStrutturaSelezionata(struttDispo.getRichiesteDisponibilita().getStatoRichiesta().equalsIgnoreCase("ACCETTATA"));
            datiTariffeInterventoBean.setFrequenzaAccessoAlServizio(TIPO_FREQUENZA_SERVIZIO.PERIODICO.getCodice());
            datiTariffeInterventoBean.selectedUnitaMisuraChange(erogazioneInterventoBean);
            datiTariffeInterventoBean.getCsIQuota().getCsIQuotaRipartiz().setFlagRendiconto(TIPO_RENDICONTO.PERIODICO.getCodice());
		}
	}
	
	private void initConsuntivazione(InserimentoConsuntivazioneDTO consuntivazione){
		if (consuntivazione != null) {
			Long selectedStato = 0L;
			erogazioneInterventoBean.setDataErog(consuntivazione.getDataDa());
			erogazioneInterventoBean.setDataErogA(consuntivazione.getDataA());
			erogazioneInterventoBean.getNuovocsIntEseg().getCsIValQuota().setValQuota(BigDecimal.valueOf(consuntivazione.getNumGiorni()));
			List<CsCfgIntEsegStato> listaStatiErogazione = erogazioneInterventoBean.getListaStatiErogazione();

			for (CsCfgIntEsegStato stato : listaStatiErogazione) {
				if (TipoStatoErogazione.EROGATIVO.equals(stato.getTipo())
						&& DataModelCostanti.CsCfgIntEsegStato.FLAG_CHIUDI_APERTO == stato.getFlagChiudi()
						&& stato.getNome().equalsIgnoreCase("EROGAZIONE")) {
					selectedStato = (Long) stato.getId();
				}
				
			}
			
			erogazioneInterventoBean.setStatoSelezionatoId(selectedStato);
			// SIMULO l'onchange del menu a tendina degli stati erogazione

			erogazioneInterventoBean.onStatoChanged();
			erogazioneInterventoBean.onUpdateTariffa(this.datiTariffeInterventoBean.getCsIQuota().getTariffa(),this.datiTariffeInterventoBean.getCsIQuota().isOreMinuti());
		}
	}
	
	private void initDatiSina(Long catSocId, Long masterId, String cf){
		//SISO-783
		//la tab sina è visualizzabile solo se cat sociale è anziani(4) o disabili(2)
		visualizzaSinaTab = (TipiCategoriaSociale.ANZIANI_ID.equals(catSocId) || TipiCategoriaSociale.DISABILI_ID.equals(catSocId));
		
		if(visualizzaSinaTab){
			Boolean esportata = false;
			if (masterId != null) {
				// ordinamento decrescente per data erogazione
				Collections.sort(erogazioneInterventoBean.getStoricoOperazioni(), new Comparator<InterventoErogazHistoryRowBean>() {

							@Override
							public int compare(
									InterventoErogazHistoryRowBean o1,
									InterventoErogazHistoryRowBean o2) {
								if (o1.getDataErogazione().after(o2.getDataErogazione())) {
									return -1;
								}
								if (o1.getDataErogazione().before(o2.getDataErogazione())) {
									return 1;
								}
								return 0;
							}
						});

				InterventoErogazHistoryRowBean ultimaErogE = null;
				for (InterventoErogazHistoryRowBean interventoErogazHistoryRowBean : erogazioneInterventoBean.getStoricoOperazioni()) {
					if (interventoErogazHistoryRowBean.getStato().getTipo().equals(DataModelCostanti.TipoStatoErogazione.EROGATIVO) ) {
						ultimaErogE = interventoErogazHistoryRowBean;
						break;
					}
				}
				
				//SISO-783 fix
				if(ultimaErogE != null){
					esportata = erogazioneInterventoBean.erogazioneEsportata(ultimaErogE);
				}

				sinaMan = new SinaMan(idCaso, masterId, cf, esportata, true);
				
			}else{
				sinaMan = new SinaMan(idCaso, new Long(0), cf, esportata, true);
			}
		}	
	}

	private void loadDatiPai(Long paiId){
		//SISO-760
		if(paiId!=null && paiId>0){
			BaseDTO bdto = new BaseDTO();
			bdto.setObj(paiId);
			fillEnte(bdto);
			paidto =  diarioService.findPaiFullById(bdto);
		}else{
			paidto=null;
		}
	}
	
	//TODO ML: il risultato di questo metodo dipende dalla posizione in cui è chiamato dal metodo chiamante,
	//cioè il risultato prodotto dipende dalle proprietà che sono state popolate in precedenza.
	//forse sarebbe meglio chiamarlo in questa maniera loadDatiTariffeInterventoBean(interventoId) e popolare gli oggetti in base all'intevento
	//letto da database, ma a questo punto si potrebbero avere problemi di performance
	private void loadDatiTariffeInterventoBean(CsOSettore settoreTitolare) {
		CsIQuota quotaIn = null;

		Long currentOperatoreId = getCurrentOpSettore().getCsOOperatore().getId();
		if (csIIntervento != null && csIIntervento.getCsIQuota() != null)
			quotaIn = csIIntervento.getCsIQuota();
		else if(erogazioneInterventoBean!=null && erogazioneInterventoBean.getNuovoIntEsegMast()!=null)
				quotaIn = erogazioneInterventoBean.getNuovoIntEsegMast().getCsIQuota();

		/*Dal momento che la visualizzazione in sola lettura vale solo per EROGANTI, 
		 * che si trovano in lista casi la cui prima riga di storico (AVVIO) è stata fatta dall'ente
		 * verifico solo l'operatore che ha inserito l'erogazione master */
		boolean tabQuoteSolaLettura = false;
		if(isPermessoErogativo() && erogazioneInterventoBean!=null && erogazioneInterventoBean.getNuovoIntEsegMast()!=null){
			CsOOperatoreSettore ops = erogazioneInterventoBean.getNuovoIntEsegMast().getCsOOperatoreSettore();
			if (ops != null)
				tabQuoteSolaLettura = !currentOperatoreId.equals(ops.getCsOOperatore().getId());
		}

		datiTariffeInterventoBean = new DatiTariffeInterventoBean(quotaIn, tabQuoteSolaLettura, this.idTipoIntevento,this.idTipoIntrCustom, settoreTitolare);

	}

	private void loadDatiFlgInterventiBean() {
		datiFglIntBean = new DatiFglInterventoBean();
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);

		if (diarioId != null && diarioId.intValue() > 0) {
			CsFlgIntervento cs;
			try {
				dto.setObj(diarioId);
				cs = interventoService.getFoglioInterventoById(dto);
				datiFglIntBean.valorizzaBean(cs);
			} catch (Exception e) {
				addErrorDialogFromProperties("caricamento.error");
				logger.error(e.getMessage(), e);
			}
		}

	}

	private void loadDatiInterventoBean() {
		List<CsAComponente> listaParenti = CsUiCompBaseBean.caricaParenti(idSoggetto, null);
		datiInterventoBean = new DatiInterventoBean(settoreId, idSoggetto, listaParenti);
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);

		ComponenteAltroMan componente = new ComponenteAltroMan(idSoggetto, listaParenti);
		datiInterventoBean.setComponente(componente);

		try {
			
			if (diarioId != null && diarioId.intValue() > 0) {
				CsFlgIntervento cs;
				// try {

				dto.setObj(diarioId);
				cs = interventoService.getFoglioInterventoById(dto);
				csIIntervento = cs.getCsIIntervento();
				datiInterventoBean = new DatiInterventoBean(csIIntervento, idSoggetto, listaParenti);
				idTipoIntevento = datiInterventoBean.getIdTipoIntervento();
				idTipoIntrCustom = datiInterventoBean.getIdTipoIntrCustom();

				interventoId = csIIntervento.getId();
			} else {
				if (interventoId == null || interventoId.intValue() <= 0) {
					datiInterventoBean.inizializzaNuovoTipoIntervento(idTipoIntevento, idTipoIntrCustom, idCatSociale);
//					List<SelectItem> lstTipoInt = this.getLstTipoIntervento();
//					if (lstTipoInt.size() > 0) {
//						SelectItem[] si = ((SelectItemGroup) lstTipoInt.get(0)).getSelectItems();
//						datiInterventoBean.setTipoInterventoCatSoc((String) si[0].getValue());
//					}
				}
				else {
					dto.setObj(interventoId);
					csIIntervento = interventoService.getInterventoById(dto);
					datiInterventoBean = new DatiInterventoBean(csIIntervento, idSoggetto, listaParenti);
					idTipoIntevento = datiInterventoBean.getIdTipoIntervento();
					idTipoIntrCustom = datiInterventoBean.getIdTipoIntrCustom();
				}
			}

		} catch (Exception e) {
			addErrorDialogFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}

	}

	private boolean checkIfErogazionePossibile() {
		if(this.erogazioneInterventoBean.isDentroFascicolo() && this.datiFglIntBean!=null){ //siamo dentro l'intervento
			// TODO OK Impossibile erogare se l'intervento è stato respinto
			if (datiFglIntBean.getFlagRespinto() != null) {
				if (datiFglIntBean.getFlagRespinto()) {
					addMessage(FacesMessage.SEVERITY_WARN, "Impossibile erogare se l'intervento e' stato respinto");
					return false;
				}
			}

			if (listaInterventi != null) {
				for (DatiInterventoBean dataBeanIntervento : listaInterventi) {
					if (dataBeanIntervento.getIdIntervento().compareTo(interventoId) == 0) {
						if (dataBeanIntervento.getInterventoRespinto()) {// c'è un foglio respinto
							addMessage(FacesMessage.SEVERITY_WARN, "Impossibile erogare se l'intervento e' stato respinto");
							return false;
						}
					}
				}
			}
			// TODO OK Non è consentito erogare: il foglio amministrativo non lo consente
			if (this.datiFglIntBean.getFlagAttivazione() != null) {
				if(!datiFglIntBean.isAttivazione()){
					addMessage(FacesMessage.SEVERITY_WARN,"Non e' consentito erogare: il foglio amministrativo non lo consente");
					return false;
				}
			}
		}
		return true;
	}

	public void eliminaFoglio() {
		try {
			BaseDTO b = new BaseDTO();
			fillEnte(b);
			b.setObj(this.diarioId);

			logger.debug("Rimozione in corso del foglio amministrativo e relativo diario (id=" + this.diarioId + ")");

			interventoService.deleteFoglioAmministrativo(b);
			diarioService.deleteDiario(b);

			b.setObj(this.interventoId);
			CsIIntervento inter = interventoService.getInterventoById(b);
			if (inter.getCsFlgInterventos() == null || inter.getCsFlgInterventos().size() == 0) {
				logger.debug("Rimozione dell'intervento (id=" + this.interventoId + ")");
				interventoService.deleteIntervento(b);
			}

			RequestContext.getCurrentInstance().addCallbackParam("removed", true);

			addInfoDialogFromProperties("elimina.ok");

		} catch (Exception e) {
			addErrorDialogFromProperties("elimina.error");
			logger.error(e.getMessage(), e);
		}
	}

	public void changeTipoIntervento() {
		datiInterventoBean.resetOnChangeTipoIntervento();
		datiFglIntBean.setIdRelazione(null);
	}

	public void reset() {
		datiFglIntBean = new DatiFglInterventoBean();
		List<CsAComponente> listaParenti = CsUiCompBaseBean.caricaParenti(idSoggetto, null);
		datiInterventoBean = new DatiInterventoBean(settoreId, idSoggetto,listaParenti);
		datiProgettoBean = new DatiProgettoBean();
		interventoId = null;
		diarioId = null;
		this.setFromPai(Boolean.FALSE);
	}

	private boolean validaArchiTemporaliFogli(){
		boolean valido = true;
		
		/**
		 * http://progetti.asc.webred.it/browse/SISO-451 i range di date non si devono sovrapporre 
		 */
		for (CsFlgIntervento foglio : this.datiInterventoBean.getListaFogli()) {
			CsDDiario csDDiario = foglio.getCsDDiario();
			try {
				if (diarioId != null && diarioId.intValue() > 0 && diarioId.intValue() == csDDiario.getId())
					continue;
					
				if (csDDiario.getDtAttivazioneDa().compareTo(datiFglIntBean.getDtTipoAttA())<=0 && datiFglIntBean.getDtTipoAttDa().compareTo(csDDiario.getDtAttivazioneA())<=0) {
					valido =  false;
				}
			} catch(NullPointerException npe) {
				continue;
			}
		}
		return valido;
	}
	
	protected boolean salvaDatiIntervento(CsIQuota quota, CsIInterventoPr progetto) {
		boolean bSaved = false;

		try {
			boolean archiTemporaliValidi = this.validaArchiTemporaliFogli();
			if(!archiTemporaliValidi){
				addWarningDialogFromProperties("archi.temporali.sovrapposti");
				return archiTemporaliValidi;
			}

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(diarioId);
			// dati anagrafici
			CsFlgIntervento cs = new CsFlgIntervento();
			if (diarioId != null && diarioId.intValue() > 0)
				cs = interventoService.getFoglioInterventoById(dto);
		
			datiFglIntBean.setVisSecondoLivello(this.toSaveSecondoLivello ? getCurrentOpSettore().getCsOSettore().getId() : null); //SISO-812
			datiFglIntBean.valorizzaJpa(cs);

			CsIIntervento inter = new CsIIntervento();

			BaseDTO b = new BaseDTO();
			fillEnte(b);
			
			if (idTipoIntrCustom != null) {
				BaseDTO dto1 = new BaseDTO();
				fillEnte(dto1);
				dto1.setObj(idTipoIntrCustom);

				// SALVA TIPO INTERVENTI CUSTOM
				CsCTipoInterventoCustom interventoCustom = confService.findTipoInterventoCustomById(dto1);
				inter.setCsIInterventoCustom(interventoCustom);
			}
			if (interventoId == null || interventoId.intValue() <= 0) {	// si sta inserendo un nuovo intervento

				datiInterventoBean.valorizzaDettaglioIntervento(inter);

				if (datiInterventoBean.getSettore() != null && datiInterventoBean.getIdTipoIntervento() != null) {

					CsRelSettCsocTipoInterPK pk = new CsRelSettCsocTipoInterPK();

					pk.setScsSettoreId(datiInterventoBean.getSettore());
					pk.setCstiTipoInterventoId(datiInterventoBean.getIdTipoIntervento());
					pk.setScsCategoriaSocialeId(datiInterventoBean.getIdCatSociale());

					b.setObj(pk);
					inter.setCsRelSettCsocTipoInter(confEnteService.findRelSettCsocTipoInterById(b));

				}

				dto.setObj(inter);
				inter.setCsIQuota(quota);
				inter.setCsIInterventoPr(progetto);
				inter = interventoService.salvaIntervento(dto);

			} else { // si sta modificando un nuovo intervento esistente

				b.setObj(interventoId);
				inter = interventoService.getInterventoById(b);
				// inizio SISO-391
				if (datiInterventoBean.getDettaglioInterventoModifica()) {
					datiInterventoBean.valorizzaDettaglioIntervento(inter);
				}
				// fine SISO-391

				if (inter.getCsIQuota() == null
						|| datiInterventoBean.getDettaglioInterventoModifica()) { // SISO-391
					inter.setCsIQuota(quota);
					dto.setObj(inter);
					interventoService.salvaIntervento(dto);
				}

			}

			cs.setCsIIntervento(inter);
			InterventoDTO foglioDTO = new InterventoDTO();
			fillEnte(foglioDTO);
			foglioDTO.setFoglio(cs);
			foglioDTO.setCasoId(idCaso);
			foglioDTO.setIdRelazione(datiFglIntBean.getIdRelazione());
			interventoService.salvaFoglioAmministrativo(foglioDTO);

			lstTipoIntervento = null;

			// frida
			csIIntervento = inter;

			bSaved = true;

		} catch (Exception e) {
			addErrorDialogFromProperties("salva.error");
			logger.error(e.getMessage(), e);
			bSaved = false;
		}

		return bSaved;
	}

	protected boolean salvaDatiErogazione(CsIQuota quota, CsIInterventoPr progetto) {

		if(this.erogazioneInterventoBean.getNuovoIntEsegMast() != null)
			this.erogazioneInterventoBean.getNuovoIntEsegMast().setSettoreSecondoLivello(toSaveSecondoLivello ? getCurrentOpSettore().getCsOSettore().getId() : null);	
	
	 	boolean bSaved = this.erogazioneInterventoBean.salva(csIIntervento, quota, idCaso!=null, 
					csASoggetto, datiTariffeInterventoBean.getFrequenzaAccessoAlServizio(), progetto );

		return bSaved;
	}

	public void onCheckRespinto(AjaxBehaviorEvent event) {
		// se esiste almeno un foglio attivo non si può respingere
		if (datiFglIntBean.getFlagRespinto() != null) {
			if (this.datiFglIntBean.getFlagRespinto()) {
				for (DatiInterventoBean dataBeanIntervento : listaInterventi) {
					if (dataBeanIntervento.getIdIntervento().compareTo(interventoId) == 0) {
						if (dataBeanIntervento.getInterventoAperto()) {
							this.datiFglIntBean.setFlagRespinto(false);
							this.erogazioneInterventoBean.setErogazionePossibile(true);
							addMessage(FacesMessage.SEVERITY_WARN,"Impossibile respingere: per questo intervento esiste un foglio amministrativo con stato='attivazione'");
							return;
						}
					}
				}
				this.erogazioneInterventoBean.setErogazionePossibile(false);
				this.datiFglIntBean.setFlagAttivazione(null);
				this.datiFglIntBean.setDescrSospensione(null);
				this.datiFglIntBean.setTipoAttivazione(null);
				this.datiFglIntBean.setMotivoChiusura(null);
				this.datiFglIntBean.setDtTipoAttA(null);
				this.datiFglIntBean.setDtTipoAttDa(null);
				addMessage(FacesMessage.SEVERITY_INFO,"ATTENZIONE: L'intervento e' stato respinto. Al salvataggio delle impostazioni sara' impossibile erogare");
			}else{
				this.erogazioneInterventoBean.setErogazionePossibile(true);
				this.datiFglIntBean.setMotivoRespinto(null);
			}
		}
	}

	public void addMessage(FacesMessage.Severity tipoMessaggio, String summary) {
		FacesMessage message = new FacesMessage(tipoMessaggio, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void onStatoChanged(AjaxBehaviorEvent event) {
		if (checkIfErogazionePossibile()) {
			this.erogazioneInterventoBean.onStatoChanged();
			erogazioneInterventoBean.onUpdateTariffa(this.datiTariffeInterventoBean.getCsIQuota().getTariffa(),this.datiTariffeInterventoBean.getCsIQuota().isOreMinuti());
			//QUi vanno caricate le date
		}
	}

	public void aggiungiAttributiFglIntervento() {

		// SISO-720 SM
		// in ogni caso si dovrebbe aggiornare il valore corrente della spesa
//		BigDecimal tariffa = datiTariffeInterventoBean.getCsIQuota().getTariffa();
		//SISO-1602
		BigDecimal tariffa = erogazioneInterventoBean.getNuovocsIntEseg().getCsIValQuota().getTariffaCustom();
		erogazioneInterventoBean.onUpdateQuotaDettaglio(tariffa,datiTariffeInterventoBean.getCsIQuota().isOreMinuti());

		// FINE SISO-720 SM
		if(erogazioneInterventoBean.getErogazioneHistory().isVisualizzaStorico() && !erogazioneInterventoBean.getStoricoOperazioni().isEmpty()){
			if(	erogazioneInterventoBean.getStatoSelezionato().getOrdineDescFase() < erogazioneInterventoBean.getStoricoOperazioni().get(0).getStato().getOrdineDescFase()){
				addMessage(FacesMessage.SEVERITY_ERROR, "Non è possibile aggiungere erogazioni con stato in una fase precedente all'ultima erogazione");
				return;
			}
		}
		
		if(this.erogazioneInterventoBean.isDentroFascicolo() && this.datiFglIntBean!=null){ //siamo dentro l'intervento
			// TODO OK Impossibile erogare se l'intervento è stato respinto
			if (datiFglIntBean.getFlagRespinto() != null) {
				if (datiFglIntBean.getFlagRespinto()) {
					addMessage(FacesMessage.SEVERITY_WARN, "Impossibile erogare se l'intervento e' stato respinto");
					return;
				}
			}
			if (listaInterventi != null) {
				for (DatiInterventoBean dataBeanIntervento : listaInterventi) {
					if (dataBeanIntervento.getIdIntervento().compareTo(interventoId) == 0) {
						if (dataBeanIntervento.getInterventoRespinto()) {// c'è un foglio respinto
							addMessage(FacesMessage.SEVERITY_WARN, "Impossibile erogare se l'intervento e' stato respinto");
							return;
						}
					}
				}
			}
			// TODO OK Non è consentito erogare: il foglio amministrativo non lo consente
			if (this.datiFglIntBean.getFlagAttivazione() != null) {
				if(!this.datiFglIntBean.isAttivazione()){
					addMessage(FacesMessage.SEVERITY_WARN,"Non e' consentito erogare: il foglio amministrativo non lo consente");
					return;
				}else{ //controllo su dataErogazione
					if (this.erogazioneInterventoBean.getDataErog().before(this.datiFglIntBean.getDtTipoAttDa())) {
						addMessage(FacesMessage.SEVERITY_WARN,"Data erogazione errata: non puo' essere precedente alla data di inizio validita' del foglio amministrativo");				
						return;
					}
					if (this.erogazioneInterventoBean.getDataErog().after(this.datiFglIntBean.getDtTipoAttA())) {
						addMessage(FacesMessage.SEVERITY_WARN,"Data erogazione errata: non puo' essere successiva alla data di fine validita' del foglio amministrativo");				
						return;
					}
				}
			}
		}

		// INIZIO SISO-556
		if (erogazioneInterventoBean.getNuovocsIntEseg().getValoreGestitaEnte() != null) {
			if (erogazioneInterventoBean.getNuovocsIntEseg().getSpesa() == null) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Valorizzare il campo Spesa");
				return;
			} else if ( erogazioneInterventoBean.getNuovocsIntEseg().getValoreGestitaEnte().compareTo(erogazioneInterventoBean.getNuovocsIntEseg().getSpesa() ) > 0  ) { 
				addMessage(FacesMessage.SEVERITY_ERROR,"Valore Spesa Gestita Direttamente in dettaglio non corretto:inserire valore numerico intero o decimale (es.12,34), minore o uguale alla Spesa");
					return;
				}
		}
		// FINE SISO-556

/*		//Verifica che tutti i campi dinamici siano stati valorizzati
		boolean validi=true;
		for(InterventoErogazAttrBean erog : this.erogazioneInterventoBean.getErogaziones()){
			if(erog.getValue()==null){
				addMessage(FacesMessage.SEVERITY_WARN, "Il campo '"+ erog.getLabel() +"' non è stato valorizzato.");	
				validi=false;
			}
		}if(!validi) return;
 */

		this.erogazioneInterventoBean.onChangeCalcolaSpeseTotali();
		
		BigDecimal valQuota = this.erogazioneInterventoBean.getNuovocsIntEseg().getCsIValQuota().getValQuota();
		BigDecimal valQuotaEnte = this.erogazioneInterventoBean.getNuovocsIntEseg().getValoreGestitaEnte();
		BigDecimal percQuotaEnte = this.erogazioneInterventoBean.getNuovocsIntEseg().getPercGestitaEnte();
		BigDecimal compUt = this.erogazioneInterventoBean.getNuovocsIntEseg().getCompartUtenti();
		BigDecimal compSSN = this.erogazioneInterventoBean.getNuovocsIntEseg().getCompartSsn();
		BigDecimal altreComp = this.erogazioneInterventoBean.getNuovocsIntEseg().getCompartAltre();
		BigDecimal spesa = this.erogazioneInterventoBean.getNuovocsIntEseg().getSpesa();
		
		
		if(valQuota!=null && valQuota.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) != 0 
				&& !datiTariffeInterventoBean.getCsIQuota().isKm() 
				&& !datiTariffeInterventoBean.getCsIQuota().isOreMinuti()
				&& !datiTariffeInterventoBean.getCsIQuota().isValuta()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Il valore inserito deve essere intero");
			return;
		}
		
		
		
		
		
		if (valQuotaEnte != null && valQuotaEnte.compareTo(valQuota) != 0){
			Double ctrlSomma = 0.0;
			ctrlSomma += valQuotaEnte.doubleValue();
			ctrlSomma = compUt != null ? (ctrlSomma + compUt.doubleValue()) : ctrlSomma; 
			ctrlSomma = compSSN != null ? (ctrlSomma + compSSN.doubleValue()) : ctrlSomma; 
			ctrlSomma = altreComp != null ? (ctrlSomma + altreComp.doubleValue()) : ctrlSomma; 
		
			BigDecimal ctrlSommaBd = new BigDecimal(ctrlSomma).setScale(2, RoundingMode.HALF_UP);
			ctrlSomma = ctrlSommaBd.doubleValue();
			
			if(ctrlSomma.compareTo(spesa.doubleValue())!= 0 ){
				addMessage(FacesMessage.SEVERITY_ERROR, "La somma dei parametri non corrisponde all'importo prestazione,\r\n somma = " + ctrlSomma.toString() + " mentre l'importo prestazione = " + spesa.toString());
				return;
				}
		}
		else if(percQuotaEnte != null && percQuotaEnte.compareTo(new BigDecimal(100))!= 0){
			Double ctrlSomma = 0.0;
			Double	valorePerc = spesa.doubleValue()*percQuotaEnte.doubleValue()/100;
			ctrlSomma += valorePerc.doubleValue();
			ctrlSomma = compUt != null ? (ctrlSomma + compUt.doubleValue()) : ctrlSomma; 
			ctrlSomma = compSSN != null ? (ctrlSomma + compSSN.doubleValue()) : ctrlSomma; 
			ctrlSomma = altreComp != null ? (ctrlSomma + altreComp.doubleValue()) : ctrlSomma; 
			
			if(ctrlSomma.compareTo(spesa.doubleValue())!= 0 ){
				addMessage(FacesMessage.SEVERITY_ERROR, "La somma dei parametri non corrisponde all'importo prestazione,\r\n somma = " + ctrlSomma.toString() + " mentre l'importo prestazione = " + spesa.toString());
				return;
				}
		}
		
		boolean rendicontoPeriodico = this.datiTariffeInterventoBean.isRendicontoPeriodico();
		this.erogazioneInterventoBean.aggiungiAttributi(rendicontoPeriodico,
				this.getDatiTariffeInterventoBean().getCsIQuota().getCsIQuotaRipartiz().getFlagPeriodoRipartiz(),  // SISO-556
				isVisualizzaIntervalloDateErogazione(), this.getDatiTariffeInterventoBean().getCsIQuota().getCsTbUnitaMisura().getValore()); //SISO-958
	}

    @Override
	protected void save(){	
		boolean bSaved = true;
		try {

			this.erogazioneInterventoBean.valorizzaResidenza();
			this.erogazioneInterventoBean.valorizzaLuogoDiNascita();
			this.erogazioneInterventoBean.valorizzaSesso();
			
			// TODO OK foglio attivazione controlli
			if (this.datiFglIntBean != null) { // controlli solo dentro il foglio amministrativo
				if(this.datiFglIntBean.isAttivazione()){
					List<InterventoErogazHistoryRowBean> listaErogazioni=this.erogazioneInterventoBean.getStoricoOperazioni();
					if (listaErogazioni.size() == 0) {
						addMessage(FacesMessage.SEVERITY_WARN, "Inserire almeno una riga di erogazione");
						bSaved = false;
						return;
					} else {
						InterventoErogazHistoryRowBean ultimaErog = listaErogazioni.get(0);
						for (InterventoErogazHistoryRowBean iEhR : listaErogazioni) {
							if (ultimaErog.getIntEseg().getDtIns().before(iEhR.getIntEseg().getDtIns()))
								ultimaErog = iEhR;
						}	
					}
				}
			}else{ //erogazione fuori foglio amministrativo: almeno una riga dovrebbe esserci
				List<InterventoErogazHistoryRowBean> listaErogazioni=this.erogazioneInterventoBean.getStoricoOperazioni();
				if (listaErogazioni.size() == 0) {
					addMessage(FacesMessage.SEVERITY_WARN, "Inserire almeno una riga di erogazione");
					bSaved = false;
					return;
				}
			}
			
			 //SISO-556 la validazione della testata, delle erogazioni e in generale qualsiasi validazione, viene messa prima del salvataggio
			//datiProgettoBean.setSoggettoErogazione(soggettoNuovaErogazione); //setto il soggetto erogazione di riferimento
			if (validaSalvataggio()) {
				//SISO-972
				if(!isValidaFSE() && abilitaControlloFSEPor){
					RequestContext.getCurrentInstance().addCallbackParam("porFSE", true);
					setFSEMessage("Il salvataggio aggiornerà i dati della scheda intervento fse " + (this.datiProgettoBean.getNumFSE() == 1 ? " dell'intervento presente " :   " dei "  + this.getDatiProgettoBean().getNumFSE() + " interventi presenti") + " nel sistema, relativi allo stesso soggetto, progetto e sottocorso/attività. \n Se si è sicuri si aver valorizzato interamente gli attributi della scheda intervento procedere al salvataggio.");
					bSaved = false;
					return;
				}
				//SISO-972 fine
				
				boolean savedQuota = this.datiTariffeInterventoBean.salva(isUnitaMisuraRequired());
				CsIQuota quotaCorrente = datiTariffeInterventoBean.getCsIQuota();
				if (quotaCorrente != null && (quotaCorrente.getId() == null || quotaCorrente.getId() == 0))
					quotaCorrente = null;
				bSaved &= savedQuota;
				
				SoggettoErogazioneBean soggettoErogazione = this.erogazioneInterventoBean!=null ? this.erogazioneInterventoBean.getSoggettoErogazione() : null;
				boolean savedProgetto = this.datiProgettoBean.salva(soggettoErogazione);
				CsIInterventoPr progettoCorrente = datiProgettoBean.getCsIInterventoPr();
				if (progettoCorrente != null && (progettoCorrente.getId() == null || progettoCorrente.getId() == 0))
					progettoCorrente = null;

				bSaved &= savedProgetto;
				
				if (!readOnly)
					bSaved &= salvaDatiIntervento(quotaCorrente, progettoCorrente);

				if (datiErogazioniTabRendered)
					bSaved &= salvaDatiErogazione(quotaCorrente, progettoCorrente);
				
				//SISO 929
				if (isSinaRendered()) {
					List<String> msgErog = sinaMan.validaSinaErogazione(erogazioneInterventoBean.getCsIPs().getFlagInCarico(), erogazioneInterventoBean.getErogazioneHistory().getRows());
					// SISO-783
					// ** mod. SISO-886 **//
					if (msgErog==null || msgErog.size() ==0)
						bSaved &= sinaMan.salvaDaFglIntervento(this.erogazioneInterventoBean.getNuovoIntEsegMast().getId());
				}
			} else
				bSaved = false;

			if (bSaved) {

				try {
					// Refresh dei dati della lista
					FascicoloBean fbean = (FascicoloBean) getReferencedBean("fascicoloBean");
				
					// inizio modifica evoluzione-pai
					if (fbean != null) {
						if (fbean.getInterventiBean() != null) {
							fbean.getInterventiBean().refreshListaInterventi(null);
						}
						if (csIIntervento != null && fbean.getPaiBean() != null) {
							if (fbean.isPaiTabSelected())
								fbean.getPaiBean().refreshPicklistInterventi(csIIntervento);
							else
								fbean.getPaiBean().refreshPicklistInterventi(null);
						} else if (erogazioneInterventoBean != null && fbean.getPaiBean() != null) {
							if (fbean.isPaiTabSelected())
								fbean.getPaiBean().refreshPicklistErogazioni(this.erogazioneInterventoBean.getNuovoIntEsegMast().getId());
							else if (fbean.getPaiBean().getProvenienzaConsuntivazione())
							    fbean.getPaiBean().refreshConsuntivazione(this.erogazioneInterventoBean.getNuovoIntEsegMast().getId());
							else
								fbean.getPaiBean().refreshPicklistErogazioni(null);
						}
					}
				}catch (Exception e) {
				
					logger.error(e.getMessage(), e);
				}

				try {

					if (this.getFromPai()) {
						ProgettiIndividualiExtBean paiExtBean = (ProgettiIndividualiExtBean) getReferencedBean("progettiInvidualiExt");
						if (paiExtBean != null && paiExtBean.getPaiBean() != null) {
							if (paiExtBean.getInterventiBean() != null) {
								paiExtBean.getInterventiBean().refreshListaInterventi(null);
							}
							if (csIIntervento != null) {
								paiExtBean.getPaiBean().refreshPicklistInterventi(csIIntervento);
							} else if (erogazioneInterventoBean != null) {
								paiExtBean.getPaiBean().refreshPicklistErogazioni(this.erogazioneInterventoBean.getNuovoIntEsegMast().getId());
								if (paiExtBean.getPaiBean().getProvenienzaConsuntivazione())
									paiExtBean.getPaiBean().refreshConsuntivazione(this.erogazioneInterventoBean.getNuovoIntEsegMast().getId());
							}
						}
					}
				} catch (Exception e) {

					logger.error(e.getMessage(), e);
				}
				// fine modifica evoluzione-pai
				setFromPai(Boolean.FALSE);
				RequestContext.getCurrentInstance().addCallbackParam("saved", true);
				addInfoDialogFromProperties("salva.ok");
			}

		} catch (Exception e) {
			bSaved = false;
			addErrorDialogFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
	}

	// INZIO SISO-556
	private boolean validaSalvataggio() {
		SoggettoErogazioneBean soggettoErogazione = this.erogazioneInterventoBean!=null ? this.erogazioneInterventoBean.getSoggettoErogazione() : null;
		if (!erogazioneInterventoBean.validaTestata()
				|| !datiTariffeInterventoBean.valida()
				|| !erogazioneInterventoBean.validaErogazioni(datiTariffeInterventoBean.getCsIQuota())
				|| !datiProgettoBean.validaDatiProgetto(soggettoErogazione)
				|| !validaSina()
				){
			
			// || !erogazioneInterventoBean.validaDatiCsIPs()) // SISO-657
			return false;
		}
		else {
			return true;
		}
	}
	// FINE SISO-556

	public void onChangeProgetto(){
		SoggettoErogazioneBean soggetto = this.erogazioneInterventoBean!=null ? this.erogazioneInterventoBean.getSoggettoErogazione() : null;
		String cf = soggetto!=null ? soggetto.getCf() : null;
		datiProgettoBean.onChangeProgetto(cf);
	}
	
	//SISO -972
	public void onChangeAttivita(){
		SoggettoErogazioneBean soggetto = this.erogazioneInterventoBean!=null ? this.erogazioneInterventoBean.getSoggettoErogazione() : null;
		String cf = soggetto!=null ? soggetto.getCf() : null;
		datiProgettoBean.onChangeAttivita(cf);
	}
	//FINE SISO-972

	public void onChangeOrigineFinanziamento(AjaxBehaviorEvent event){ 
		SoggettoErogazioneBean soggetto = this.erogazioneInterventoBean!=null ? this.erogazioneInterventoBean.getSoggettoErogazione() : null;
		String cf = soggetto!=null ? soggetto.getCf() : null;
		datiProgettoBean.onChangeOrigineFinanziamento(cf);
	}

	public void resetDatiAttivita() {
		
		this.erogazioneInterventoBean.setErogazionePossibile(datiFglIntBean.isAttivazione());
		if(this.datiFglIntBean.isAttivazione())
			addMessage(FacesMessage.SEVERITY_INFO, "ATTENZIONE: Tipo foglio='Attivazione'. Occorre inserire almeno una riga di erogazione");
		else
			addMessage(FacesMessage.SEVERITY_INFO, "ATTENZIONE: Tipo foglio diverso da 'Attivazione'. Non sara' possibile inserire erogazioni");

		this.datiFglIntBean.resetDatiAttivita();
	}

	public DatiFglInterventoBean getDatiFglIntBean() {
		return datiFglIntBean;
	}

	public void setDatiFglIntBean(DatiFglInterventoBean datiFglIntBean) {
		this.datiFglIntBean = datiFglIntBean;
	}

	public List<SelectItem> getLstCittadinanze() {
		if (lstCittadinanze == null) {
			lstCittadinanze = new ArrayList<SelectItem>();
			lstCittadinanze.add(new SelectItem(null, "- seleziona -"));
			try {
				AccessTableNazioniSessionBeanRemote bean = (AccessTableNazioniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableNazioniSessionBean");
				List<String> beanLstCittadinanze = bean.getCittadinanze();
				if (beanLstCittadinanze != null) {
					for (String cittadinanza : beanLstCittadinanze) {
						// in AM_TAB_NAZIONI il campo NAZIONALITA ha lunghezza
						// 500, in CS_A_SOGGETTO il campo CITTADINANZA ha
						// lunghezza 255
						if (cittadinanza.length() > 255) {
							cittadinanza = cittadinanza.substring(0, 252) + "...";
						}
						lstCittadinanze.add(new SelectItem(cittadinanza, cittadinanza));
					}
				}
			} catch (NamingException e) {
				addErrorDialogFromProperties("caricamento.error");
				logger.error(e.getMessage(), e);
			}
		}
		return lstCittadinanze;
	}

	@Override
	public List<SelectItem> getLstMotiviChiusura() {
		if (lstMotiviChiusura == null) {
			lstMotiviChiusura = new ArrayList<SelectItem>();
			lstMotiviChiusura.add(new SelectItem(null, "- seleziona -"));
			try {
				CeTBaseObject cet = new CeTBaseObject();
				fillEnte(cet);
				List<CsTbMotivoChiusuraInt> beanLst = confService.getMotiviChiusuraIntervento(cet);
				if (beanLst != null) {
					for (CsTbMotivoChiusuraInt s : beanLst) {
						lstMotiviChiusura.add(new SelectItem(s.getId(), s.getDescrizione()));
					}
				}
			} catch (Exception e) {
				addErrorDialogFromProperties("caricamento.error");
				logger.error(e.getMessage(), e);
			}
		}
		return lstMotiviChiusura;
	}

	public List<SelectItem> getLstTipoIntervento() {
		if (lstTipoIntervento == null || lstTipoIntervento.size() == 0) {
			lstTipoIntervento = new ArrayList<SelectItem>();
			try {

				if (settoreId != null && catsocCorrenti != null && !catsocCorrenti.isEmpty()) {
					for (CsCCategoriaSociale cs : catsocCorrenti) {
						InterventoDTO dto = new InterventoDTO();
						fillEnte(dto);
						dto.setIdSettore(settoreId);
						dto.setIdCatsoc(cs.getId());
						List<CsCTipoIntervento> beanlst = confService.findTipiInterventoSettoreCatSoc(dto);

						if (beanlst != null) {
							SelectItemGroup gr = new SelectItemGroup(cs.getDescrizione());
							List<SelectItem> grItems = new ArrayList<SelectItem>();
							for (CsCTipoIntervento tipo : beanlst) {
								String chiaveInterventoTB = cs.getId() + "@" + tipo.getId(); 
								grItems.add(new SelectItem(chiaveInterventoTB, tipo.getDescrizione()));
							}
							if (!grItems.isEmpty()) {
								gr.setSelectItems(grItems.toArray(new SelectItem[grItems.size()]));
								this.lstTipoIntervento.add(gr);
							}

						}
					}
				} else {
					logger.warn("Impossibile attivare interventi per il soggetto: settore o categoria sociale non specificata.");
				}
			} catch (Exception e) {
				addErrorDialogFromProperties("caricamento.error");
				logger.error(e.getMessage(), e);
			}
		}
		return lstTipoIntervento;
	}

	public DatiInterventoBean getDatiInterventoBean() {
		return datiInterventoBean;
	}

	public void setDatiInterventoBean(DatiInterventoBean datiInterventoBean) {
		this.datiInterventoBean = datiInterventoBean;
	}

	public Long getInterventoId() {
		return interventoId;
	}

	public void setInterventoId(Long interventoId) {
		this.interventoId = interventoId;
	}

	@Override
	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public String getWidgetVar() {
		widgetVar = "fglAmmDialog";
		return widgetVar;
	}

	public CsASoggettoLAZY getSoggettoCorrente() {
		return soggettoCorrente;
	}

	public void setSoggettoCorrente(CsASoggettoLAZY soggettoCorrente) {
		this.soggettoCorrente = soggettoCorrente;
	}

	@Override
	public List<SelectItem> getLstTipoQuotaPasti() {
		if (lstTipoQuotaPasti == null) {
			lstTipoQuotaPasti = new ArrayList<SelectItem>();
			lstTipoQuotaPasti.add(new SelectItem("Nessuna", "Nessuna"));
			lstTipoQuotaPasti.add(new SelectItem("Totale", "Totale"));
			lstTipoQuotaPasti.add(new SelectItem("Parziale", "Parziale"));
		}
		return lstTipoQuotaPasti;
	}

	@Override
	public List<SelectItem> getLstTipoQuotaCentroD() {
		if (lstTipoQuotaCentroD == null) {
			lstTipoQuotaCentroD = new ArrayList<SelectItem>();
			lstTipoQuotaCentroD.add(new SelectItem("Nessuna", "Nessuna"));
			lstTipoQuotaCentroD.add(new SelectItem("Totale", "Totale"));
			lstTipoQuotaCentroD.add(new SelectItem("Parziale", "Parziale"));
		}
		return lstTipoQuotaCentroD;
	}

	@Override
	public List<SelectItem> getLstTipoRiscossione() {
		if (lstTipoRiscossione == null) {
			lstTipoRiscossione = new ArrayList<SelectItem>();
			lstTipoRiscossione.add(new SelectItem("Riscossione diretta", "Riscossione diretta"));
			lstTipoRiscossione.add(new SelectItem("Delegato", "Delegato"));
			lstTipoRiscossione.add(new SelectItem("Accredito", "Accredito"));
		}
		return lstTipoRiscossione;
	}

	@Override
	public List<SelectItem> getLstTipoOreVoucher() {
		if (lstTipoOreVoucher == null) {
			lstTipoOreVoucher = new ArrayList<SelectItem>();
			lstTipoOreVoucher.add(new SelectItem("Giornaliere", "Giornaliere"));
			lstTipoOreVoucher.add(new SelectItem("Settimanali", "Settimanali"));
			lstTipoOreVoucher.add(new SelectItem("Mensili", "Mensili"));
			lstTipoOreVoucher.add(new SelectItem("Annuali", "Annuali"));
		}
		return lstTipoOreVoucher;
	}

	@Override
	public List<SelectItem> getLstTipoPeriodoErogazione() {
		if (lstTipoPeriodoErogazione == null) {
			lstTipoPeriodoErogazione = new ArrayList<SelectItem>();
			lstTipoPeriodoErogazione.add(new SelectItem("Una tantum", "Una tantum"));
			lstTipoPeriodoErogazione.add(new SelectItem("Giornaliero", "Giornaliero"));
			lstTipoPeriodoErogazione.add(new SelectItem("Mensile", "Mensile"));
			lstTipoPeriodoErogazione.add(new SelectItem("Annuale", "Annuale"));
		}
		return lstTipoPeriodoErogazione;
	}

	@Override
	public List<SelectItem> getLstTipoAttivita() {
		if (lstTipoAttivita == null) {
			lstTipoAttivita = new ArrayList<SelectItem>();
			lstTipoAttivita.add(new SelectItem(null, "- seleziona -"));
			for(STATO s : DataModelCostanti.FoglioAmministrativo.STATO.values())
				this.lstTipoAttivita.add(new SelectItem(s.getCodice(),s.getDescrizione()));
		}
		return lstTipoAttivita;
	}

	@Override
	public List<SelectItem> getLstTipoSospensione() {
		if (lstTipoSospensione == null) {
			lstTipoSospensione = new ArrayList<SelectItem>();
			lstTipoSospensione.add(new SelectItem(null, "- seleziona -"));
			lstTipoSospensione.add(new SelectItem("Temporanea", "Temporanea"));
			lstTipoSospensione.add(new SelectItem("Definitiva", "Definitiva"));
		}
		return lstTipoSospensione;
	}

	@Override
	public List<SelectItem> getLstTipoAttivazione() {
		if (lstTipoAttivazione == null) {
			lstTipoAttivazione = new ArrayList<SelectItem>();
			lstTipoAttivazione.add(new SelectItem(null, "- seleziona -"));
			lstTipoAttivazione.add(new SelectItem("Immediata", "Immediata"));
			lstTipoAttivazione.add(new SelectItem("Riattivazione", "Riattivazione"));
			lstTipoAttivazione.add(new SelectItem("Attivazione", "Attivazione"));
		}
		return lstTipoAttivazione;
	}

	@Override
	public List<SelectItem> getLstModalitaIntervento() {
		if (lstModalitaIntervento == null) {
			lstModalitaIntervento = new ArrayList<SelectItem>();
			lstModalitaIntervento.add(new SelectItem("P", "Prima Erogazione"));
			lstModalitaIntervento.add(new SelectItem("R", "Rinnovo"));
		}
		return lstModalitaIntervento;
	}

	@Override
	public List<SelectItem> getLstTipoPeriodo() {
		if (lstTipoPeriodo == null) {
			lstTipoPeriodo = new ArrayList<SelectItem>();
			lstTipoPeriodo.add(new SelectItem("1", "Presunta"));
			lstTipoPeriodo.add(new SelectItem("2", "Certa"));
		}
		return lstTipoPeriodo;
	}

	@Override
	public List<SelectItem> getLstTipoGestione() {
		if (lstTipoGestione == null) {
			lstTipoGestione = new ArrayList<SelectItem>();
			lstTipoGestione.add(new SelectItem("Badante", "Badante"));
			lstTipoGestione.add(new SelectItem("Cooperativa", "Cooperativa"));
			lstTipoGestione.add(new SelectItem("Altro", "Altro"));
		}
		return lstTipoGestione;
	}

	@Override
	public List<SelectItem> getLstTipoDeroghe() {
		if (lstTipoDeroghe == null) {
			lstTipoDeroghe = new ArrayList<SelectItem>();
			lstTipoDeroghe.add(new SelectItem("Su Importo", "Su Importo"));
			lstTipoDeroghe.add(new SelectItem("Sulle Applicazioni", "Sulle Applicazioni"));
		}
		return lstTipoDeroghe;
	}

	@Override
	public List<SelectItem> getLstTipoRientriFam() {
		if (lstTipoRientriFam == null) {
			lstTipoRientriFam = new ArrayList<SelectItem>();
			try {
				CeTBaseObject cet = new CeTBaseObject();
				fillEnte(cet);
				List<CsTbTipoRientriFami> beanLst = confService.getTipiRientriFami(cet);
				if (beanLst != null) {
					for (CsTbTipoRientriFami s : beanLst) {
						lstTipoRientriFam.add(new SelectItem(s.getId(), s.getDescrizione()));
					}
				}
			} catch (Exception e) {
				addErrorDialogFromProperties("caricamento.error");
				logger.error(e.getMessage(), e);
			}
		}
		return lstTipoRientriFam;
	}

	@Override
	public List<SelectItem> getLstTipoRetta() {
		if (lstTipoRetta == null) {
			lstTipoRetta = new ArrayList<SelectItem>();
			try {
				CeTBaseObject cet = new CeTBaseObject();
				fillEnte(cet);
				List<CsTbTipoRetta> beanLst = confService.getTipiRetta(cet);
				if (beanLst != null) {
					for (CsTbTipoRetta s : beanLst) {
						lstTipoRetta.add(new SelectItem(s.getId(), s.getDescrizione()));
					}
				}
			} catch (Exception e) {
				addErrorDialogFromProperties("caricamento.error");
				logger.error(e.getMessage(), e);
			}
		}
		return lstTipoRetta;
	}

	@Override
	public List<SelectItem> getLstRelazioni() {
		List<SelectItem> lst = new ArrayList<SelectItem>();
		lst.add(new SelectItem(null, "- seleziona -"));
		try {
			Long idTipoIntervento = this.datiInterventoBean != null ? this.datiInterventoBean.getIdTipoIntervento() : null;
			if (idTipoIntervento != null && this.idCaso != null) {
				InterventoDTO i = new InterventoDTO();
				fillEnte(i);
				i.setCasoId(this.idCaso);
				i.setIdTipoIntervento(idTipoIntervento);
				List<KeyValueDTO> beanLst = diarioService.findRelazioniByCasoTipoIntervento(i);
				lst = this.convertiLista(beanLst);
				
			}
		} catch (Exception e) {
			addErrorDialogFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
		return lst;
	}

	@Override
	public List<SelectItem> getLstTipoAffido() {
		if (lstTipoAffido == null) {
			lstTipoAffido = new ArrayList<SelectItem>();
			lstTipoAffido.add(new SelectItem("ETERO_FAMILIARE", "Etero Familiare"));
			lstTipoAffido.add(new SelectItem("PARENTI", "A parenti"));
			lstTipoAffido.add(new SelectItem("DIURNO_MESE", "Diurno Mese"));
			lstTipoAffido.add(new SelectItem("DIURNO_GIORNALIERO", "Giornaliero diurno"));
		}
		return lstTipoAffido;
	}

	@Override
	public List<SelectItem> getLstSpeseExtra() {
		if (lstSpeseExtra == null) {
			lstSpeseExtra = new ArrayList<SelectItem>();
			lstSpeseExtra.add(new SelectItem("SANITARIE", "Spese Sanitarie"));
			lstSpeseExtra.add(new SelectItem("VACANZE", "Spese vacanze"));
			lstSpeseExtra.add(new SelectItem("INCONTRI", "Incontri Protetti"));
			lstSpeseExtra.add(new SelectItem("INT_EDU", "Intervento Educativo"));
			lstSpeseExtra.add(new SelectItem("TESTI", "Rimborso testi scuola"));
			lstSpeseExtra.add(new SelectItem("ALTRO", "Altro"));
		}
		return lstSpeseExtra;
	}

	@Override
	public List<SelectItem> getLstSpeseExtraSRM() {
		if (lstTipoQuotaCentroD == null) {
			lstSpeseExtraSRM = new ArrayList<SelectItem>();
			lstSpeseExtraSRM.add(new SelectItem("PERNOTTAMENTO", "Pernottamento"));
			lstSpeseExtraSRM.add(new SelectItem("CENE", "Cene"));
			lstSpeseExtraSRM.add(new SelectItem("INT_EDU", "Intervento Educativo"));
			lstSpeseExtraSRM.add(new SelectItem("ALTRO", "Altro"));
		}
		return lstSpeseExtraSRM;
	}

	@Override
	public List<SelectItem> getLstTipoAdmAdh() {
		if (lstTipoAdmAdh == null) {
			lstTipoAdmAdh = new ArrayList<SelectItem>();
			lstTipoAdmAdh.add(new SelectItem("ADM", "ADM"));
			lstTipoAdmAdh.add(new SelectItem("ADH", "ADH"));
			lstTipoAdmAdh.add(new SelectItem("POLO", "POLO"));
		}
		return lstTipoAdmAdh;
	}

	public List<SelectItem> getLstEducatori() {
		Date dataRif = new Date();
		if (lstEducatori == null) {
			// Filtro per tipo = Educatore UOL e Organizzazione = utente loggato
			lstEducatori = new ArrayList<SelectItem>();
			lstEducatori.add(new SelectItem(null, "- seleziona -"));
			Map<Long, Long> mapEducatori = new HashMap<Long, Long>();

			try {
				CsOOperatoreSettore opSettore = getCurrentOpSettore();
				CsOOrganizzazione org = opSettore.getCsOSettore().getCsOOrganizzazione();

				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(DataModelCostanti.Operatori.EDUCATORE_UOL_ID);
				List<CsOOperatoreTipoOperatore> lista = confEnteService.getOperatoriByTipoId(dto);
				for (CsOOperatoreTipoOperatore cs : lista) {
					CsOOperatoreSettore opSett = cs.getCsOOperatoreSettore();
					boolean isAttivo = !dataRif.after(cs.getDataFineApp()) && !dataRif.before(cs.getDataInizioApp());
					if (isAttivo && opSett.getCsOSettore().getCsOOrganizzazione().getId().equals(org.getId())
							&& !mapEducatori.containsKey(opSett.getCsOOperatore().getId())) {
						String anagrafica = opSett.getCsOOperatore().getDenominazione();
						if (anagrafica != null && !anagrafica.isEmpty()) {
							mapEducatori.put(opSett.getCsOOperatore().getId(), opSett.getCsOOperatore().getId());
							SelectItem si = new SelectItem(opSett.getCsOOperatore().getId(), anagrafica);
							lstEducatori.add(si);
						}
					}
				}
			} catch (Exception e) {
				addErrorDialogFromProperties("caricamento.error");
				logger.error(e.getMessage(), e);
			}
		}

		return lstEducatori;
	}

	public List<SelectItem> getLstTipoCirc4() {
		if (lstTipoCirc4 == null) {
			lstTipoCirc4 = new ArrayList<SelectItem>();
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<KeyValueDTO> lista = confService.getTipiCirc4(cet);
			lstTipoCirc4 = this.convertiLista(lista);
		}
		return lstTipoCirc4;
	}

	public List<SelectItem> getLstTipoInterventi() {
		if (lstTipoInterventi == null) {
			lstTipoInterventi = new ArrayList<SelectItem>();

			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<KeyValueDTO> lista = confService.getinterventiUOL(cet);
			lstTipoInterventi = convertiLista(lista);
		}
		return lstTipoInterventi;
	}

	public List<SelectItem> getLstTipoProgetto() {
		if (lstTipoProgetto == null) {
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			lstTipoProgetto = convertiLista(confService.getTipiProgetto(cet));
		}
		return lstTipoProgetto;
	}

	@Override
	public List<SelectItem> getLstTipoContributo() {
		if (lstTipoContributo == null) {
			lstTipoContributo = new ArrayList<SelectItem>();

			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			lstTipoContributoTooltip = confService.getTipoContributo(cet);
			for (CsTbTipoContributo cs : lstTipoContributoTooltip) {
				lstTipoContributo.add(new SelectItem(cs.getId(), cs.getDescrizione()));
			}
		}
		return lstTipoContributo;
	}

	public List<CsTbTipoContributo> getLstTipoContributoTooltip() {
		if (lstTipoContributoTooltip == null) {
			getLstTipoContributo();
		}
		return lstTipoContributoTooltip;
	}

	/* Erogazione's Methods */
	public ErogazioneInterventoBean getErogazioneInterventoBean() {
		return erogazioneInterventoBean;
	}

	@Override
	public boolean isDatiInterventoTabRendered() {
		return datiInterventoTabRendered;
	}

	@Override
	public boolean isDatiErogazioniTabRendered() {
		return datiErogazioniTabRendered;
	}

	public void setDatiInterventoTabRendered(boolean datiInterventoTabRendered) {
		this.datiInterventoTabRendered = datiInterventoTabRendered;
	}

	public void setDatiErogazioniTabRendered(boolean datiErogazioniTabRenderd) {
		this.datiErogazioniTabRendered = datiErogazioniTabRenderd;
	}

	public void setSettoreId(Long settoreId) {
		this.settoreId = settoreId;
	}

	public List<DatiInterventoBean> getListaInterventi() {
		return listaInterventi;
	}

	public void setListaInterventi(List<DatiInterventoBean> listaInterventi) {
		this.listaInterventi = listaInterventi;
	}

	@Override
	public boolean isReadOnly() {
		// Abilito permesso di salvataggio per chi ha i privilegi sulla visione
		// complessiva
		return super.isReadOnly() || !checkPermesso(PermessiErogazioneInterventi.ITEM, PermessiErogazioneInterventi.ABILITA);
	}

	public String getHeaderDialogo() {
		return headerDialogo;
	}

	public void setHeaderDialogo(String headerDialogo) {
		this.headerDialogo = headerDialogo;
	}

	public boolean isAbilitaSalvataggio() {
		return abilitaSalvataggio;
	}

	public void setAbilitaSalvataggio(boolean abilitaSalvataggio) {
		this.abilitaSalvataggio = abilitaSalvataggio;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getTipoErogazioneDisableTooltip() {
		return tipoErogazioneDisableTooltip;
	}

	public DatiTariffeInterventoBean getDatiTariffeInterventoBean() {
		return datiTariffeInterventoBean;
	}

	public void setDatiTariffeInterventoBean(
			DatiTariffeInterventoBean datiTariffeInterventoBean) {
		this.datiTariffeInterventoBean = datiTariffeInterventoBean;
	}

	public boolean isAnyErogazioniBetweenDataInizioDataFine() {
		if (erogazioneInterventoBean != null && erogazioneInterventoBean.getInterEsegHistory() != null)
			for (CsIInterventoEseg csi : erogazioneInterventoBean.getInterEsegHistory())
				try {
					if (datiFglIntBean.getDtTipoAttDa().compareTo(csi.getDataEsecuzione())<=0 && csi.getDataEsecuzione().compareTo(datiFglIntBean.getDtTipoAttA())<=0)
						return true;
				} catch (NullPointerException npe) {
				}
		return false;
	}
	
	public Boolean getDisableIntervalloDateFgl(){
		Boolean disable = 
				this.datiFglIntBean.getFlagAttivazione() == null ||
				this.datiFglIntBean.isValutazione() || 
				this.datiFglIntBean.isGraduatoria() ||
				this.isReadOnly() || 
				(this.datiFglIntBean.getFlagRespinto()!=null ? this.datiFglIntBean.getFlagRespinto() : false) || 
				(this.isAnyErogazioniBetweenDataInizioDataFine() && this.validaArchiTemporaliFogli()); //Se ci sono fogli con stesse date, poi non me li fa più cambiare
		return disable;
	}

	public Long getIdErogazioneMaster() {
		return idErogazioneMaster;
	}

	public void setIdErogazioneMaster(Long idErogazioneMaster) {
		this.idErogazioneMaster = idErogazioneMaster;
	}

	// inizio SISO-556
	public boolean isVisualizzaIntervalloDateErogazione() {
		// Il range di date deve essere proposto SOLO se lo stato selezionato è di tipo "E" e il rendiconto è periodico
		boolean res = erogazioneInterventoBean.isStatoErogativo() && this.datiTariffeInterventoBean.isRendicontoPeriodico();
		return res;
	}
	
	
	public String getDataErogLabel() {
		if(erogazioneInterventoBean.isStatoErogativo()){ 
			if(datiTariffeInterventoBean.isRendicontoPeriodico())
				dataErogLabel = "Data inizio erogazione";
			else dataErogLabel = "Data erogazione";
		}else 
			dataErogLabel = DATA_EROG_LABEL_DEFAULT;
		return dataErogLabel;
	}

	public boolean isUnitaMisuraRequired() {

		// in assenza dell'intervento è sempre obbligatoria
		if (!datiInterventoTabRendered) {
			return true;
		} else{ // in presenza dell'intervento è obbligatoria solo quando l'intervento è in attivazione
			return (datiFglIntBean!=null && datiFglIntBean.isAttivazione());
		}
	}

	// sempre visualizzato tranne quando non è valorizzata l'unità di misura
	public boolean isVisualizzaPanelValQuota() {
		//prima del SISO-556 era  #{fglInterventoBean.datiTariffeInterventoBean.csIQuota.csTbUnitaMisura.valore!=null 
		// and fglInterventoBean.datiTariffeInterventoBean.csIQuota.tariffa>0}
		boolean res = false;

//		if ( datiTariffeInterventoBean.getSelUnitaMisura() == null 
//				|| datiTariffeInterventoBean.getSelUnitaMisura() != DataModelCostanti.CsTbUnitaMisura.ID_EURO) {
//			res = true;
//		} 

		if (datiTariffeInterventoBean.getCsIQuota().getCsTbUnitaMisura().getValore() != null
//				&& datiTariffeInterventoBean.getCsIQuota().getTariffa().compareTo(BigDecimal.ZERO) > 0  //se tariffa maggiore di zero 
		) {
			res = true;
		}

		return res;
	}

	// spostato da qui da ErogazioneInterventoBean
	public boolean isAbilitaCheckCalcolo() {
		// Abilito anche quando non è vuoto, ma non sono state inserite erogazioni!
		boolean storicoVuoto = true; // erogazioneInterventoBean.getErogazioneHistory().getRows().isEmpty();

		for (InterventoErogazHistoryRowBean interventoErogazHistoryRowBean : erogazioneInterventoBean.getStoricoOperazioni()) {
			if (interventoErogazHistoryRowBean.getStato().getTipo().equals(DataModelCostanti.TipoStatoErogazione.EROGATIVO)) {
				storicoVuoto = false;
			}
		}

		boolean unitaMisuraValida;
		if (datiTariffeInterventoBean.getSelUnitaMisura() == null || datiTariffeInterventoBean.getSelUnitaMisura() == DataModelCostanti.CsTbUnitaMisura.ID_EURO) {
			unitaMisuraValida = true;
		} else {
			unitaMisuraValida = false;
		}

		return storicoVuoto && unitaMisuraValida;
	}

	public boolean isSelectUnitaDiMisuraDisabled() {
		boolean res = false;
		if (datiTariffeInterventoBean.isSolaLettura()) {
			res = true;
		}

		//Disabilitare il campo “unità di misura” se ci sono righe di erogazione con TIPO E.
		res = erogazioneInterventoBean.isErogazioniPresenti();

		return res;
	}

//	public boolean righeDiErogazionePresenti(){
//		boolean res = false;
//		List<InterventoErogazHistoryRowBean> interventoErogazHistoryRowBeanList = erogazioneInterventoBean.getErogazioneHistory().getRows(); 
//		for (InterventoErogazHistoryRowBean interventoErogazHistoryRowBean : interventoErogazHistoryRowBeanList) {
//			if (interventoErogazHistoryRowBean.getStato().getTipo().equals(DataModelCostanti.TipoStatoErogazione.EROGATIVO)) {
//				res = true;
//			}
//		}
//		return res;
//	}

	// fine SISO-556

	// INIZIO SISO-524
	public boolean isTariffaDisabled() {
		return isTariffaDettaglioDisabled() || erogazioneInterventoBean.isErogazioniPresenti();
	}
	public boolean isTariffaDettaglioDisabled() {
		return datiTariffeInterventoBean.isSolaLettura() || datiTariffeInterventoBean.isValuta();
	}

	public boolean isFrequenzaAccessoAlServizioDisabled() {
		return datiTariffeInterventoBean.isSolaLettura() || erogazioneInterventoBean.isErogazioniPresenti();
	}

	public boolean isValQuotaPeriodoDisabled() {
		return datiTariffeInterventoBean.isSolaLettura() 
					|| datiTariffeInterventoBean.isDisableInputFrequenzaDelServizio()
				|| erogazioneInterventoBean.isErogazioniPresenti();
	}

	public boolean isFlagPeriodoRipartizDisabled() {
		return datiTariffeInterventoBean.isSolaLettura() 
					|| datiTariffeInterventoBean.isDisableInputFrequenzaDelServizio()
				|| erogazioneInterventoBean.isErogazioniPresenti();
	}

	public boolean isNumeroGiorniSettimanaliDisabled() {
		return datiTariffeInterventoBean.isSolaLettura() 
					|| datiTariffeInterventoBean.flagPeriodoRipartizEnabled()
				|| datiTariffeInterventoBean.isDisableInputFrequenzaDelServizio()
				|| erogazioneInterventoBean.isErogazioniPresenti()
				|| datiTariffeInterventoBean.isValuta();//SISO-1134
	}

	public boolean isFlagRendicontoDisabled() {
		return datiTariffeInterventoBean.isSolaLettura()  
					|| datiTariffeInterventoBean.isDisableInputFrequenzaDelServizio()
				|| erogazioneInterventoBean.isErogazioniPresenti();
	}

	public DatiProgettoBean getDatiProgettoBean() {
		return datiProgettoBean;
	}

	public void setDatiProgettoBean(DatiProgettoBean datiProgettoBean) {
		this.datiProgettoBean = datiProgettoBean;
	}

	private void loadListaSettori() {
		CeTBaseObject opDto = new CeTBaseObject();
		fillEnte(opDto);
		List<CsOSettore> listaSettori = confEnteService.getSettoreAll(opDto);

		HashMap<Long, List<CsOSettore>> mapEntiTitolariDiSettore = new HashMap<Long, List<CsOSettore>>();
		HashMap<Long, List<CsOSettore>> mappaSettoriEr = new HashMap<Long, List<CsOSettore>>();
		/* SISO-663 SM */
		HashMap<Long, List<CsOSettore>> mapEntiGestoriDiSettore = new HashMap<Long, List<CsOSettore>>();
		/* -=- */
		if (!listaSettori.isEmpty()) {
			for (CsOSettore s : listaSettori) {
				List<CsOSettore> lstTit = mapEntiTitolariDiSettore.get(s.getCsOOrganizzazione().getId());
				List<CsOSettore> lstEr = mappaSettoriEr.get(s.getCsOOrganizzazione().getId());
				List<CsOSettore> lstGest = mapEntiGestoriDiSettore.get(s.getCsOOrganizzazione().getId());
				if (lstTit == null) 	lstTit = new ArrayList<CsOSettore>();
				if (lstEr == null)		lstEr = new ArrayList<CsOSettore>();
				if (lstGest == null)	lstGest = new ArrayList<CsOSettore>();

				if (s.getFlgIntTitolare()!=null && s.getFlgIntTitolare().booleanValue()) {
					lstTit.add(s);
					mapEntiTitolariDiSettore.put(s.getCsOOrganizzazione().getId(), lstTit);
				}
				if (s.getFlgIntEroga()!=null && s.getFlgIntEroga().booleanValue()) {
					
					// #ROMACAPITALE
					List<Long> lstSettoriIntervento = erogazioneInterventoBean.getListaIdSettoriByIntervervento();
					if( lstSettoriIntervento!= null && lstSettoriIntervento.size() > 0){
						if (lstSettoriIntervento.contains(s.getId())) {
							lstEr.add(s);
							mappaSettoriEr.put(s.getCsOOrganizzazione().getId(), lstEr);
						}
					}// fine #ROMACAPITALE
					else{
						// vecchio metodo senza filtro romacapitale
						lstEr.add(s);
						mappaSettoriEr.put(s.getCsOOrganizzazione().getId(), lstEr);
					}
				}
				if (s.getFlgIntGestore()!=null && s.getFlgIntGestore().booleanValue()) {
					lstGest.add(s);
					mapEntiGestoriDiSettore.put(s.getCsOOrganizzazione().getId(), lstGest);
				}

			}

			List<SelectItem> selectItemListGestoriSettore = loadListaSettoriGroup(mapEntiGestoriDiSettore);

			datiProgettoBean.setListaSettoriTitGroup(loadListaSettoriGroup(mapEntiTitolariDiSettore));
			datiProgettoBean.setListaSettoriGestGroup(selectItemListGestoriSettore);

			erogazioneInterventoBean.setListaSettoriErGroup(loadListaSettoriGroup(mappaSettoriEr));
		}
	}

	// SISO-663 SM

	private List<SelectItem> loadListaSettoriGroup(HashMap<Long, List<CsOSettore>> mappaSettori) {
		ArrayList<SelectItem> listaSettoriGroup = new ArrayList<SelectItem>();
		for (Long ids : mappaSettori.keySet()) {
			List<CsOSettore> lst = mappaSettori.get(ids);
			List<SelectItem> settoriGruppo = new ArrayList<SelectItem>();
			if ("1".equals(lst.get(0).getCsOOrganizzazione().getAbilitato())) {
				SelectItemGroup gr = new SelectItemGroup(lst.get(0).getCsOOrganizzazione().getNome());
				for (CsOSettore s : lst) {
					SelectItem si = new SelectItem(s.getId(), s.getNome());
					si.setDisabled(si.isDisabled());
					settoriGruppo.add(si);
				}
				gr.setSelectItems(settoriGruppo.toArray(new SelectItem[settoriGruppo.size()]));
				listaSettoriGroup.add(gr);
			}
		}
		return listaSettoriGroup;
	}

	// @formatter:off
	public Long getSelSettoreTitolareId() {
		return datiProgettoBean.getSelSettoreTitolareId();
	}

	public void setSelSettoreTitolareId(Long selSettoreTitolareId) {
		datiProgettoBean.setSelSettoreTitolareId(selSettoreTitolareId);
		erogazioneInterventoBean.setSettoreTitolare(datiProgettoBean.getSettoreTitolare());
	}

	public Long getSelSettoreGestoreId() {
		Long idSettoreGestoreSelezionato = datiProgettoBean.getSelSettoreGestoreId();
		if (idSettoreGestoreSelezionato == null) idSettoreGestoreSelezionato = -1L;
		return idSettoreGestoreSelezionato;
	}

	public void setSelSettoreGestoreId(Long selSettoreGestoreId) {
		datiProgettoBean.setSelSettoreGestoreId(selSettoreGestoreId);
		erogazioneInterventoBean.setSettoreGestore(datiProgettoBean.getSettoreGestore());
	}

	public void settoreGestoreComeSettoreTitolare() {
		datiProgettoBean.setIdSettoreGestoreComeIdSettoreTitolare();
		erogazioneInterventoBean.setSettoreGestore(datiProgettoBean.getSettoreGestore());
	}

	public void cmbSettoreOnChange() {
		logger.info(">>> cmbSettoreOnChange start");
		datiProgettoBean.onSettoreChange();

		CsOSettore settoreTitolare = datiProgettoBean.getSettoreTitolare();
		
		// imposta i settori titolare e gestore dell'erogazione
		erogazioneInterventoBean.setSettoreTitolare(settoreTitolare);
		datiTariffeInterventoBean.loadListaTariffe(settoreTitolare);
		
		//SISO_1136 Non esiste più il menu a tendina per la selezione del Settore Gestore quindi settiamo Gestore = Titolare
		erogazioneInterventoBean.setGestoreComeTitolare(true);
		erogazioneInterventoBean.setSettoreGestore(datiProgettoBean.getSettoreGestore());
		
		logger.info(">>> cmbSettoreOnChange end");

	}
	
	// SISO-972
	public boolean isValidaFSE(){
		if (this.datiProgettoBean.getNumFSE() > 0){
			return false;
		}
		return true;
	}
	
	public void stampaModelloPOR(Object obj){
		SoggettoErogazioneBean beneficRif = null;
		List<String> msg = new ArrayList<String>();
		if(obj != null){//quando è null provengo dal tab progetti e non c'è bisogno di inizializzare
			ErogInterventoRowBean row = (ErogInterventoRowBean) obj;
			beneficRif = row.getBeneficiarioRiferimento();
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(row.getMaster().getIdInterventoEsegMaster());
			CsIInterventoPr progetto = interventoService.getProgettoByMasterId(dto);
			
			if(progetto == null){ 
				msg.add("Impossibile stampare il modello, progetto non presente");
				return;
			}
			
			datiProgettoBean = new DatiProgettoBean();
			datiProgettoBean.loadDatiProgetto(progetto,false,row.getIdTipoIntervento(),row.getIdTipoInterventoCustom(), row.getIdCatSociale(), beneficRif.getCf(), null);
		}else{
			this.erogazioneInterventoBean.valorizzaResidenza();
			this.erogazioneInterventoBean.valorizzaLuogoDiNascita();
			beneficRif = erogazioneInterventoBean.getSoggettoErogazione();// se mi arriva dal tab progetti lui è beneficiario
		}
			
		if(beneficRif == null){
			msg.add("Impossibile stampare il modello, nessun beneficiario presente");
		}
		
		if(beneficRif.getCsASoggetto() == null){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(beneficRif.getCf());
			beneficRif.setCsASoggetto(soggettoService.getSoggettoByCF(dto));
		}
		
	   if(datiProgettoBean.getCsIInterventoPr().getCsTbTitoloStudio()!=null &&
		  datiProgettoBean.getCsIInterventoPr().getCsTbTitoloStudio().getDescrizione() == null){
		   BaseDTO dto = new BaseDTO();
		   fillEnte(dto);
		   dto.setObj(datiProgettoBean.getTitoloStudioId());
		   CsTbTitoloStudio tb = confService.getTitoloStudioById(dto);
		   datiProgettoBean.getCsIInterventoPr().setCsTbTitoloStudio(tb);
		}
		
		inizializzaStampa(beneficRif);
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(stampaFseDTO);
		dto.setObj2(datiProgettoBean.getMappaCampiFse());
		
		
		List<String> msgRes = datiProgettoBean.validaResidenzaDomicilioFSE(beneficRif);
		msg.addAll(msgRes);
		
		List<String> msgPor = datiPorService.validaStampa(dto);
		msg.addAll(msgPor);
					
		if(!msg.isEmpty()){
			String s  = "<ul>";
			for(String sm : msg) s+= "<li>"+ sm.replace("'", "&#39;") +"</li>";
			s+="</ul>";	
			this.addWarningDialog("Validazione campi stampa POR", s);
			return;
		}
		
		////FINE SISO-1010 
		RequestContext.getCurrentInstance().execute("PF('wVdlgStampaPor').show()");
		//chiamare la stampa
	
	}

	private void inizializzaStampa(SoggettoErogazioneBean beneficRif){
		stampaFseDTO = new StampaFseDTO();

		//Dati beneficiario
		stampaFseDTO.setCognome(beneficRif.getCognome());
		stampaFseDTO.setNome(beneficRif.getNome());
		stampaFseDTO.setCodiceFiscale(beneficRif.getCf());
		stampaFseDTO.setCittadinanza(beneficRif.getCittadinanza());
		stampaFseDTO.setAnnoNascita(beneficRif.getAnnoNascita()!=null ? beneficRif.getAnnoNascita().toString() : null);
		/**
		 * Prendendo i dati dal codice fiscale va considerato se prevedere i casi di omocodia!!?
		 * */
		if(beneficRif.getCf().length() <= 16 ){//se > 16 è temporaneo e lo ignoro
				String beneficGiornoNascita = beneficRif.getCf().substring(9, 11);
				String letteraMeseNascita = beneficRif.getCf().substring(8,9).toUpperCase();
				String meseNascita= "";
				String beneficAnnoNascita = beneficRif.getAnnoNascita().toString(); 
				int benefSessoGiorno = Integer.parseInt(beneficGiornoNascita);
				
				stampaFseDTO.setSesso(benefSessoGiorno > 31 ? "F" : "M");
				
				if(letteraMeseNascita.equals("A")){
			           meseNascita="01";
			    }else if(letteraMeseNascita.equals("B")){
			           meseNascita="02";
			    }else if(letteraMeseNascita.equals("C")){
			           meseNascita="03";
			    }else if(letteraMeseNascita.equals("D")){
			           meseNascita="04";
			    }else if(letteraMeseNascita.equals("E")){
			           meseNascita="05";
			    }else if(letteraMeseNascita.equals("H")){
			           meseNascita="06";
			    }else if(letteraMeseNascita.equals("L")){
			           meseNascita="07";
			    }else if(letteraMeseNascita.equals("M")){
			           meseNascita="08";
			    }else if(letteraMeseNascita.equals("P")){
			           meseNascita="09";
			    }else if(letteraMeseNascita.equals("R")){
			           meseNascita="10";
			    }else if(letteraMeseNascita.equals("S")){
			           meseNascita="11";
			    }else if(letteraMeseNascita.equals("T")){
			           meseNascita="12";
			    }
				
				benefSessoGiorno = benefSessoGiorno > 31 ? benefSessoGiorno - 40 : benefSessoGiorno ;
				stampaFseDTO.setDataNascita(String.valueOf(benefSessoGiorno).concat("/").concat(meseNascita).concat("/").concat(beneficAnnoNascita));
				
		}
		
		ComuneResidenzaMan residenzaComuneMan = new ComuneResidenzaMan();
		ObjectMapper mapper = new ObjectMapper();
		if(!StringUtils.isBlank(beneficRif.getComuneResidenza())){
			try {
				residenzaComuneMan.setComune(mapper.readValue(beneficRif.getComuneResidenza(), ComuneBean.class));
			} catch (JsonParseException e) {
				logger.error(e.getMessage(), e);
			} catch (JsonMappingException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			
			stampaFseDTO.setCapResidenza(residenzaComuneMan.getComune().getCap());
			stampaFseDTO.setSiglaProvResidenza(residenzaComuneMan.getComune().getSiglaProv());
			stampaFseDTO.setComuneResidenza(residenzaComuneMan.getComune().getDenominazione());
		}
		stampaFseDTO.setViaResidenza(beneficRif.getViaResidenza());
		
		ComuneNascitaMan nascitaComuneMan = new ComuneNascitaMan();
		
		if(!StringUtils.isBlank(beneficRif.getComuneNascita())){
			try {
				nascitaComuneMan.setComune(mapper.readValue(beneficRif.getComuneNascita(), ComuneBean.class));
			} catch (JsonParseException e) {
				logger.error(e.getMessage(), e);
			} catch (JsonMappingException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			
			stampaFseDTO.setLuogoNascita(nascitaComuneMan.getDescrizioneCompleta());
		}else{
			AmTabNazioni nazione = getNazioneByIstat(beneficRif.getNazioneNascita(), null);
			stampaFseDTO.setLuogoNascita(nazione!=null ? nazione.getNazione() : null);
		}
		
		this.datiProgettoBean.valorizzaStampaFse(stampaFseDTO);
		
	}
	

	public void chiamaStampa(){
 		if(StringUtils.isBlank(stampaFseDTO.getSoggettoAttuatore())){
			addMessage(FacesMessage.SEVERITY_ERROR, "Inserire il soggetto Attuatore");
			return;
		}
		
		ReportBean bean = (ReportBean)CsUiCompBaseBean.getReferencedBean("ReportBean");
		if(bean == null)//Se non è gia stato chiamato lo inizializzo
			bean = new ReportBean(); 
		
		bean.esportaModelloPor(stampaFseDTO);
	}
	
	
	private boolean isSinaRendered() {
		return visualizzaSinaTab && sinaMan != null;
	}
	
	//SISO-783
	private Boolean validaSina(){
		
		//se il sina non è gestito è sempre valido
		if(!isSinaRendered())
			return true;

		Boolean valido = true;
		List<String> lst = new ArrayList<String>();
        //SISO-985
		List<String> s = this.sinaMan.validaSinaErogazione(erogazioneInterventoBean.getCsIPs().getFlagInCarico(),
														   erogazioneInterventoBean.getStoricoOperazioni());
		if(s!=null && s.size()>0){
			valido = false;
			lst.addAll(s);
	} 
		
		if(!valido)
		{
			String errObbl=""; 
			errObbl += StringUtils.join(lst, "<br/>");
			this.addError("Validazione dati SINA", errObbl);
		}
		return valido;
	}

	public PaiDTO getPaidto() {
		return paidto;
	}

	public void setPaidto(PaiDTO paidto) {
		this.paidto = paidto;
	}

	public Boolean getVisualizzaSinaTab() {
		return visualizzaSinaTab;
	}

	public void setVisualizzaSinaTab(Boolean visualizzaSinaTab) {
		this.visualizzaSinaTab = visualizzaSinaTab;
	}

	public SinaMan getSinaMan() {
		return sinaMan;
	}

	public void setSinaMan(SinaMan sinaMan) {
		this.sinaMan = sinaMan;
	}
	
	public void onChangeBeneficiario(){
	    if(this.erogazioneInterventoBean.getBeneficiarioSel()==null){
	    	this.addWarning("Beneficiari", "Nessun soggetto selezionato.");
	    	return;
	    }
		this.erogazioneInterventoBean.aggiornaSoggettoRiferimento();
		
		String cfRiferimento = this.erogazioneInterventoBean.getSoggettoErogazione()!=null ? this.erogazioneInterventoBean.getSoggettoErogazione().getCf() : null;
		datiProgettoBean.onChangeBeneficiarioRiferimento(cfRiferimento);
	}
    //fine-SISO-722

	//SISO-1133
	public String flowSpese(FlowEvent event){
		BigDecimal tariffa = erogazioneInterventoBean.getNuovocsIntEseg().getCsIValQuota().getTariffaCustom();
			try{
				if(event.getNewStep().equals("detta_tbCompart")){
					if(datiTariffeInterventoBean.getCsIQuota().isOreMinuti() && erogazioneInterventoBean.getMinutiErogazione() >= 60L) {
						addMessage(FacesMessage.SEVERITY_ERROR, "Il campo Minuti non è corretto:inserire valore numerico intero compreso tra 0 e 59");
						return event.getOldStep();
					}else {
						erogazioneInterventoBean.onUpdateQuotaDettaglio(tariffa,datiTariffeInterventoBean.getCsIQuota().isOreMinuti());
					}
				}
				else if(event.getNewStep().equals("detta_tbCaricoEnte")){
					//calcolo importo a carico dell'ente
					erogazioneInterventoBean.onUpdateQuotaDettaglio(tariffa,datiTariffeInterventoBean.getCsIQuota().isOreMinuti());
					erogazioneInterventoBean.onCalcoloValoreGestitaEnte();
				}
				
			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);
			}
			return event.getNewStep();
	}

	public Boolean getFromPai() {
		return fromPai;
	}
	
	public void setFromPai(Boolean fromPai) {
		this.fromPai = fromPai;
	}

}
