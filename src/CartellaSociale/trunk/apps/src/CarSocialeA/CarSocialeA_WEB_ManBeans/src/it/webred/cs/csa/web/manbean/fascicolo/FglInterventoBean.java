package it.webred.cs.csa.web.manbean.fascicolo;


import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.PaiDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventi.DatiProgettoBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiErogazioneInterventi;
import it.webred.cs.data.DataModelCostanti.TipoStatoErogazione;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSocialeBASIC;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDDiarioDoc;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
import it.webred.cs.data.model.CsIInterventoPr;
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsRelSettCsocTipoInterPK;
import it.webred.cs.data.model.CsTbInterventiUOL;
import it.webred.cs.data.model.CsTbMotivoChiusuraInt;
import it.webred.cs.data.model.CsTbTipoCirc4;
import it.webred.cs.data.model.CsTbTipoContributo;
import it.webred.cs.data.model.CsTbTipoProgetto;
import it.webred.cs.data.model.CsTbTipoRetta;
import it.webred.cs.data.model.CsTbTipoRientriFami;
import it.webred.cs.jsf.bean.DatiFglInterventoBean;
import it.webred.cs.jsf.bean.DatiInterventoBean;
import it.webred.cs.jsf.bean.DatiTariffeInterventoBean;
import it.webred.cs.jsf.bean.erogazioneIntervento.ErogazioneInterventoBean;
import it.webred.cs.jsf.bean.erogazioneIntervento.ErogazioneInterventoBean.RuoloEnteGestoreSpesa;
import it.webred.cs.jsf.bean.erogazioneIntervento.InterventoErogazHistoryRowBean;
import it.webred.cs.jsf.interfaces.IDatiFglIntervento;
import it.webred.cs.jsf.manbean.ComponenteAltroMan;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;


//@formatter:off

@ManagedBean(eager = true)
@SessionScoped
public class FglInterventoBean extends FascicoloCompBaseBean implements IDatiFglIntervento {

	protected String datiPresaCaricoName = "datiPresaCarico";

	protected AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getCarSocialeEjb("AccessTableConfigurazioneSessionBean");
	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getCarSocialeEjb("AccessTableSchedaSessionBean");
	protected AccessTableIndirizzoSessionBeanRemote indirizzoService = (AccessTableIndirizzoSessionBeanRemote) getCarSocialeEjb("AccessTableIndirizzoSessionBean");
	protected AccessTableOperatoreSessionBeanRemote operatoreService = (AccessTableOperatoreSessionBeanRemote) getCarSocialeEjb("AccessTableOperatoreSessionBean");

	private SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

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
	private List<SelectItem> lstTipoIntervento;
	private List<DatiInterventoBean> listaInterventi;

	private SoggettoErogazioneBean soggettoNuovaErogazione;
	private List<SoggettoErogazioneBean> beneficiari;
	private CsIIntervento csIIntervento = null;
	private Long idCatSociale;
	
	//SISO-760
	private PaiDTO paidto=null;

	private final String tipoErogazioneDisableTooltip = "Modifica disabilitata in quanto ci sono delle erogazioni comprese fra la data \"Dal\" e la data \"Al\"";




	public void inizializzaErogazione(SoggettoErogazioneBean soggettoErogazione) {
		csASoggetto = null; if( soggettoErogazione != null ) csASoggetto = soggettoErogazione.getCsASoggetto();
		soggettoNuovaErogazione = soggettoErogazione;
		beneficiari = new ArrayList<SoggettoErogazioneBean>();
		beneficiari.add(this.soggettoNuovaErogazione);
		if (csASoggetto != null)
			initialize(csASoggetto);
		else {
			setIdCaso(null);
		}
	}




	public void inizializzaErogazione(List<SoggettoErogazioneBean> beneficiari, SoggettoErogazioneBean beneficiarioRif) {
		this.beneficiari = beneficiari;
		csASoggetto = null; 
		if( beneficiarioRif != null ) csASoggetto = beneficiarioRif.getCsASoggetto();
		soggettoNuovaErogazione = beneficiarioRif;
		if( csASoggetto != null )
			initialize( csASoggetto );
		else{
			setIdCaso(null);
		}
	}



	@Override
	public void initialize(CsASoggettoLAZY soggetto, List<CsCCategoriaSocialeBASIC> catsocCorrenti, Object data) {
		super.initialize(soggetto, catsocCorrenti, null);
		soggettoNuovaErogazione = new SoggettoErogazioneBean(soggetto, true);
		beneficiari = new ArrayList<SoggettoErogazioneBean>();
		beneficiari.add(this.soggettoNuovaErogazione);
	}



	@Override
	public void initializeData(Object data) {
		CsOOperatoreSettore opSettore = getCurrentOpSettore();
		settoreId = opSettore.getCsOSettore().getId();
		lstEducatori = null;
		lstTipoIntervento = null;
	}

	protected void resetDialogo(boolean datiErogazioniTabRendered, Long interventoId, Long diarioId, Long tipoInterventoId, Long tipoInterventoCustomId, Long catSocialeId, boolean readOnly, boolean abilitaSalvataggio, String headerDialogo) {
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
	public void inizializzaDialog(boolean datiIntTabRendered, boolean datiErogazioniTabRendered, Long interventoId, Long diarioId, Long tipoInterventoId, Long tipoInterventoCustomId, Long catSocId, boolean readOnly, boolean abilitaSalvataggio, String headerDialogo, CsIInterventoEsegMast master, boolean dentroFascicolo) {

		logger.info("Inizializzazione Dialog FglInterventoBean");

		try {
			resetDialogo(datiErogazioniTabRendered, interventoId, diarioId, tipoInterventoId, tipoInterventoCustomId, catSocId, readOnly, abilitaSalvataggio, headerDialogo);
			loadListaSettori();

			datiInterventoTabRendered = datiIntTabRendered && csASoggetto != null;
			if (datiInterventoTabRendered) {
				loadDatiFlgInterventiBean();
				loadDatiInterventoBean();

				idTipoIntevento = datiInterventoBean.getIdTipoIntervento();
				idTipoIntrCustom = datiInterventoBean.getIdTipoIntrCustom();

				if (idTipoIntevento == null || idTipoIntevento <= 0) {
					addErrorDialog("Selezionare un tipo di intervento", "");
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
				if (master != null) {
					if (!master.getBeneficiari().isEmpty() && master.getBeneficiari().size() != beneficiari.size()) {
						beneficiari = new ArrayList<SoggettoErogazioneBean>();
						for (CsIInterventoEsegMastSogg sm : master.getBeneficiari()) {
							SoggettoErogazioneBean sb = new SoggettoErogazioneBean(sm);
							beneficiari.add(sb);
						}
					}
					erogazioneInterventoBean.inizializzaDaErogazioneMaster(idTipoIntevento, idTipoIntrCustom, master, soggettoNuovaErogazione, beneficiari, dentroFascicolo);

				}else{
					boolean conFoglioAmministrativo = interventoId != null && interventoId > 0;
					if (conFoglioAmministrativo)
					  erogazioneInterventoBean.inizializzaErogazione(idTipoIntevento, idTipoIntrCustom, this.interventoId, new SoggettoErogazioneBean(csASoggetto,true), beneficiari,dentroFascicolo);				
					else
					  erogazioneInterventoBean.inizializzaNuovaErogazione(idTipoIntevento, idTipoIntrCustom, soggettoNuovaErogazione, idCatSociale,dentroFascicolo);		
				}

				if (this.interventoId != null && this.interventoId > 0) {
					if(datiFglIntBean!=null && datiFglIntBean.getFlagAttivazione()!=null && 
							!DataModelCostanti.FoglioAmministrativo.STATO_ATTIVAZIONE.equals(datiFglIntBean.getFlagAttivazione()) && 
							!datiFglIntBean.getFlagRespinto())
						erogazioneInterventoBean.setErogazionePossibile(false);
					else erogazioneInterventoBean.setErogazionePossibile(true);
				} else erogazioneInterventoBean.setErogazionePossibile(true);
			}

			loadDatiTariffeInterventoBean();
			loadDatiProgettoBean(catSocId);
			
			//SISO-760
			if(master != null && master.getDiarioPaiId() != null){
				BaseDTO bdto = new BaseDTO();
				bdto.setObj(master.getDiarioPaiId().longValue());
				fillEnte(bdto);
				paidto =  diarioService.findPaiFullById(bdto);
			}else{
				paidto=null;
			}

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

	//TODO ML: il risultato di questo metodo dipende dalla posizione in cui è chiamato dal metodo chiamante,
	//cioè il risultato prodotto dipende dalle proprietà che sono state popolate in precedenza.
	//forse sarebbe meglio chiamarlo in questa maniera loadDatiTariffeInterventoBean(interventoId) e popolare gli oggetti in base all'intevento
	//letto da database, ma a questo punto si potrebbero avere problemi di performance
	private void loadDatiTariffeInterventoBean() {
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
		if(this.isPermessoErogativo() && erogazioneInterventoBean!=null && erogazioneInterventoBean.getNuovoIntEsegMast()!=null){
			CsOOperatoreSettore ops = erogazioneInterventoBean.getNuovoIntEsegMast().getCsOOperatoreSettore();
			if (ops != null)
				tabQuoteSolaLettura = !currentOperatoreId.equals(ops.getCsOOperatore().getId());
		}

		datiTariffeInterventoBean = new DatiTariffeInterventoBean(quotaIn, tabQuoteSolaLettura);

	}




	private void loadDatiProgettoBean(Long catSocialeID) {

		CsIInterventoPr progetto = null;
		if (csIIntervento != null && csIIntervento.getCsIInterventoPr() != null)
			progetto = csIIntervento.getCsIInterventoPr();
		else if(erogazioneInterventoBean!=null && erogazioneInterventoBean.getNuovoIntEsegMast()!=null)
				progetto = erogazioneInterventoBean.getNuovoIntEsegMast().getCsIInterventoPr();

		CsADatiSociali datiSociali = null;
		if (getSoggettoCorrente() != null) {
			String cf = getSoggettoCorrente().getCsAAnagrafica().getCf();
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(cf);
			datiSociali = schedaService.findDatiSocialiAttiviBySoggettoCf(dto);
		}

		// TODO: valorizzare
		boolean tabProgettoSolaLettura = false;

		datiProgettoBean.loadDatiProgetto(progetto,tabProgettoSolaLettura,datiSociali,this.idTipoIntevento, this.idTipoIntrCustom, catSocialeID);
		/* SISO-663 SM */
		erogazioneInterventoBean.setSettoreTitolare(datiProgettoBean.getSettoreTitolare());
		erogazioneInterventoBean.setSettoreGestore(datiProgettoBean.getSettoreGestore());
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




	private CsFlgIntervento getPrimoFoglioAmministrativo(Long interventoId) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(interventoId);
		return interventoService.getPrimoFoglioAmministrativo(dto);
	}




	private void loadDatiInterventoBean() {
		datiInterventoBean = new DatiInterventoBean(settoreId, idSoggetto);
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);

		ComponenteAltroMan componente = new ComponenteAltroMan(idSoggetto);
		datiInterventoBean.setComponente(componente);

		try {
			if (diarioId != null && diarioId.intValue() > 0) {
				CsFlgIntervento cs;
				// try {

				dto.setObj(diarioId);
				cs = interventoService.getFoglioInterventoById(dto);
				csIIntervento = cs.getCsIIntervento();
				datiInterventoBean = new DatiInterventoBean(csIIntervento, idSoggetto);
				idTipoIntevento = datiInterventoBean.getIdTipoIntervento();
				idTipoIntrCustom = datiInterventoBean.getIdTipoIntrCustom();

				interventoId = csIIntervento.getId();
			}
			else {
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
					CsIIntervento ci = interventoService.getInterventoById(dto);
					datiInterventoBean = new DatiInterventoBean(ci, idSoggetto);
					idTipoIntevento = datiInterventoBean.getIdTipoIntervento();
					idTipoIntrCustom = datiInterventoBean.getIdTipoIntrCustom();
				}
			}


		}
		catch (Exception e) {
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
				if(!DataModelCostanti.FoglioAmministrativo.STATO_ATTIVAZIONE.equalsIgnoreCase(this.datiFglIntBean.getFlagAttivazione())){
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
		datiInterventoBean = new DatiInterventoBean(settoreId, idSoggetto);
		datiProgettoBean = new DatiProgettoBean();
		interventoId = null;
		diarioId = null;
	}




	protected boolean salvaDatiIntervento(CsIQuota quota, CsIInterventoPr progetto) {
		boolean bSaved = false;

		try {
			/**
			 * http://progetti.asc.webred.it/browse/SISO-451
			 * i range di date non si devono sovrapporre 
			 */
			for (CsFlgIntervento foglio : this.datiInterventoBean.getListaFogli()) {
				CsDDiario csDDiario = foglio.getCsDDiario();
				try {
					if (diarioId != null && diarioId.intValue() > 0 && diarioId.intValue() == csDDiario.getId())
						continue;

					if (csDDiario.getDtAttivazioneDa().compareTo(datiFglIntBean.getDtTipoAttA())<=0 && datiFglIntBean.getDtTipoAttDa().compareTo(csDDiario.getDtAttivazioneA())<=0) {
						addWarningDialogFromProperties("archi.temporali.sovrapposti");
						return false;
					}
				} catch(NullPointerException npe) {
					continue;
				}
			}

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(diarioId);
			// dati anagrafici
			CsFlgIntervento cs = new CsFlgIntervento();
			if (diarioId != null && diarioId.intValue() > 0) {
				cs = interventoService.getFoglioInterventoById(dto);
			}
			BaseDTO b = new BaseDTO();
			fillEnte(b);

			datiFglIntBean.valorizzaJpa(cs);
			cs.getCsDDiario().setResponsabileCaso(getOperResponsabileCaso().getId()); // Valorizzo
			// Responsabile

			CsIIntervento inter = new CsIIntervento();

			if (idTipoIntrCustom != null)
			{
				BaseDTO dto1 = new BaseDTO();
				fillEnte(dto1);
				dto1.setObj(idTipoIntrCustom);

				// SALVA TIPO INTERVENTI CUSTOM
				CsCTipoInterventoCustom interventoCustom = interventoService.findTipiInterventoCustomById(dto1);
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
					inter.setCsRelSettCsocTipoInter(interventoService.findRelSettCsocTipoInterById(b));

				}

				dto.setObj(inter);
				inter.setCsIQuota(quota);
				inter.setCsIInterventoPr(progetto);
				inter = interventoService.salvaIntervento(dto);

			}else{		//si sta modificando un nuovo intervento esistente

				b.setObj(interventoId);
				inter = interventoService.getInterventoById(b);
				// inizio SISO-391
				if (datiInterventoBean.getDettaglioInterventoModifica()) {
					datiInterventoBean.valorizzaDettaglioIntervento(inter);
				}
				// fine SISO-391

				if(inter.getCsIQuota()==null 
						|| datiInterventoBean.getDettaglioInterventoModifica() ){	//SISO-391
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

		/*	// Refresh dei dati della lista
			FascicoloBean fbean = (FascicoloBean) this.getBean("fascicoloBean");
			//inizio modifica evoluzione-pai
			if (fbean != null) {
				fbean.getInterventiBean().refreshListaInterventi(null);
				fbean.getPaiBean().refreshPicklistInterventi(inter);
			}*/
			// fine modifica evoluzione-pai
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
		// INIZIO MOD-RL

//		fglInterventoBean.erogazioneInterventoBean.nuovoIntEsegMast.tipoBeneficiario  
		boolean bSaved = this.erogazioneInterventoBean.salva(csIIntervento, quota, idCaso!=null, 
					csASoggetto, datiTariffeInterventoBean.getFrequenzaAccessoAlServizio(), progetto );

		// INIZIO MOD-RL
		return bSaved;
	}




//	public void onCheckIfErogazionePossibile(AjaxBehaviorEvent event){
//		checkIfErogazionePossibile();
//	}

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
		if (checkIfErogazionePossibile()){
			this.erogazioneInterventoBean.onStatoChanged();
			erogazioneInterventoBean.onUpdateTariffa(this.datiTariffeInterventoBean.getCsIQuota().getTariffa());
		}
	}




	public void aggiungiAttributiFglIntervento() {

		// SISO-720 SM 
		// in ogni caso si dovrebbe aggiornare il valore corrente della spesa
		{
		BigDecimal tariffa = datiTariffeInterventoBean.getCsIQuota().getTariffa();
		erogazioneInterventoBean.onUpdateQuotaDettaglio(tariffa);
		}
		// FINE SISO-720 SM
		
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
				if(!DataModelCostanti.FoglioAmministrativo.STATO_ATTIVAZIONE.equalsIgnoreCase(this.datiFglIntBean.getFlagAttivazione())){
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
		this.erogazioneInterventoBean.onChangeCalcolaCompartecipazioniTot();



		boolean rendicontoPeriodico = DataModelCostanti.CsIQuotaRipartiz.FLAG_RENDICONTO_PERIODICO.
								equals(this.getDatiTariffeInterventoBean().getCsIQuota().getCsIQuotaRipartiz().getFlagRendiconto());
		this.erogazioneInterventoBean.aggiungiAttributi(rendicontoPeriodico,
				this.getDatiTariffeInterventoBean().getCsIQuota().getCsIQuotaRipartiz().getFlagPeriodoRipartiz(),  // SISO-556
				isVisualizzaIntervalloDateErogazione()); // SISO-556
	}




	public boolean salva() {
		boolean bSaved = true;
		try { // TODO OK foglio attivazione controlli
			if (this.datiFglIntBean != null) { // controlli solo dentro il foglio amministrativo
				if(DataModelCostanti.FoglioAmministrativo.STATO_ATTIVAZIONE.equalsIgnoreCase(this.datiFglIntBean.getFlagAttivazione())){
					List<InterventoErogazHistoryRowBean> listaErogazioni=this.erogazioneInterventoBean.getErogazioneHistory().getRows();
					if (listaErogazioni.size() == 0) {
						addMessage(FacesMessage.SEVERITY_WARN, "Inserire almeno una riga di erogazione");
						bSaved = false;
						return bSaved;
					}
					else {
						InterventoErogazHistoryRowBean ultimaErog = listaErogazioni.get(0);
						for (InterventoErogazHistoryRowBean iEhR : listaErogazioni) {
							if (ultimaErog.getIntEseg().getDtIns().before(iEhR.getIntEseg().getDtIns())) {
								ultimaErog = iEhR;
							}
						}
				/*		#SISO-370 - è logicamente errato. Anche se lo stato del foglio è "Attivazione" io dovrei poter immettere qualsiasi stato fra le righe di erogazione! Eliminare il controllo.
				 * 		if(!ultimaErog.getStato().getErogazionePossibile()){
							addMessage(FacesMessage.SEVERITY_WARN,"Lo stato dell'ultima riga di erogazione non è compatibile con un foglio amministrativo"+ 
						" di 'Attivazione': inserire una riga che permetta l'erogazione dell'intervento.");
							bSaved=false;
							return bSaved;
						}		*/			
					}

				}

			}else{ //erogazione fuori foglio amministrativo: almeno una riga dovrebbe esserci
				List<InterventoErogazHistoryRowBean> listaErogazioni=this.erogazioneInterventoBean.getErogazioneHistory().getRows();
				if (listaErogazioni.size() == 0) {
					addMessage(FacesMessage.SEVERITY_WARN, "Inserire almeno una riga di erogazione");
					bSaved = false;
					return bSaved;
				}
			}

			 //SISO-556 la validazione della testata, delle erogazioni e in generale qualsiasi validazione, viene messa prima del salvataggio
			datiProgettoBean.setSoggettoErogazione(erogazioneInterventoBean.getSoggettoErogazione()); //setto il soggetto erogazione di riferimento
			if (validaSalvataggio()) {
				boolean savedQuota = this.datiTariffeInterventoBean.salva();
				CsIQuota quotaCorrente = datiTariffeInterventoBean.getCsIQuota();
				if (quotaCorrente != null && (quotaCorrente.getId() == null || quotaCorrente.getId() == 0))
					quotaCorrente = null;

				
				
				boolean savedProgetto = this.datiProgettoBean.salva();
				CsIInterventoPr progettoCorrente = datiProgettoBean.getCsIInterventoPr();
				if (progettoCorrente != null && (progettoCorrente.getId() == null || progettoCorrente.getId() == 0))
					progettoCorrente = null;

				if (!readOnly)
					bSaved &= salvaDatiIntervento(quotaCorrente, progettoCorrente);

				if (datiErogazioniTabRendered)
					bSaved &= salvaDatiErogazione(quotaCorrente, progettoCorrente);
			} else{
				bSaved = false;
			}


			if (bSaved)
			{

				// Refresh dei dati della lista
				FascicoloBean fbean = (FascicoloBean) getBean("fascicoloBean");
				// inizio modifica evoluzione-pai
				if (fbean != null) {
					if (fbean.getInterventiBean() != null){
						fbean.getInterventiBean().refreshListaInterventi(null);
					}
					if (fbean.getPaiBean() != null && csIIntervento != null){
						fbean.getPaiBean().refreshPicklistInterventi(csIIntervento);
					}else if(fbean.getPaiBean()!=null && erogazioneInterventoBean!=null){
						fbean.getPaiBean().refreshPicklistErogazioni(this.erogazioneInterventoBean.getNuovoIntEsegMast().getId());
					}
						
				}
				// fine modifica evoluzione-pai

				RequestContext.getCurrentInstance().addCallbackParam("saved", true);
				addInfoDialogFromProperties("salva.ok");
			}

		} catch (Exception e) {
			bSaved = false;
			addErrorDialogFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
		return bSaved;
	}




	// INZIO SISO-556
	private boolean validaSalvataggio() {
		if (!erogazioneInterventoBean.validaTestata()
				|| !erogazioneInterventoBean.validaErogazioni(datiTariffeInterventoBean.getCsIQuota())
				|| !datiProgettoBean.validaDatiProgetto()
				)
			// || !erogazioneInterventoBean.validaDatiCsIPs()) // SISO-657
			return false;
		else {
			return true;
		}
	}
	// FINE SISO-556




	public void resetDatiAttivita() {
		if (DataModelCostanti.FoglioAmministrativo.STATO_ATTIVAZIONE.equals(this.datiFglIntBean.getFlagAttivazione())) {
			this.datiFglIntBean.setDescrSospensione(null);
			this.datiFglIntBean.setMotivoChiusura(null);
			this.erogazioneInterventoBean.setErogazionePossibile(true);
			addMessage(FacesMessage.SEVERITY_INFO, "ATTENZIONE: Tipo foglio='Attivazione'. Occorre inserire almeno una riga di erogazione");
		}
		if (DataModelCostanti.FoglioAmministrativo.STATO_CHIUSURA.equals(this.datiFglIntBean.getFlagAttivazione())) {
			this.datiFglIntBean.setDtTipoAttA(null);
			this.datiFglIntBean.setTipoAttivazione(null);
			this.datiFglIntBean.setDescrSospensione(null);
			this.erogazioneInterventoBean.setErogazionePossibile(false);
			addMessage(FacesMessage.SEVERITY_INFO, "ATTENZIONE: Tipo foglio diverso da 'Attivazione'. Non sara' possibile inserire erogazioni");
		}
		if (DataModelCostanti.FoglioAmministrativo.STATO_SOSPENSIONE.equals(this.datiFglIntBean.getFlagAttivazione())) {
			this.datiFglIntBean.setTipoAttivazione(null);
			this.datiFglIntBean.setMotivoChiusura(null);
			this.erogazioneInterventoBean.setErogazionePossibile(false);
			addMessage(FacesMessage.SEVERITY_INFO, "ATTENZIONE: Tipo foglio diverso da'Attivazione'. Non sara' possibile inserire erogazioni");
		}
		if (DataModelCostanti.FoglioAmministrativo.STATO_VALUTAZIONE.equals(this.datiFglIntBean.getFlagAttivazione())) {
			this.datiFglIntBean.setDescrSospensione(null);
			this.datiFglIntBean.setTipoAttivazione(null);
			this.datiFglIntBean.setMotivoChiusura(null);
			this.datiFglIntBean.setDtTipoAttA(null);
			this.datiFglIntBean.setDtTipoAttDa(null);
			this.erogazioneInterventoBean.setErogazionePossibile(false);
			addMessage(FacesMessage.SEVERITY_INFO, "ATTENZIONE: Tipo foglio diverso da 'Attivazione'. Non sara' possibile inserire erogazioni");
		}
	}




	public DatiFglInterventoBean getDatiFglIntBean() {
		return datiFglIntBean;
	}




	public void setDatiFglIntBean(DatiFglInterventoBean datiFglIntBean) {
		this.datiFglIntBean = datiFglIntBean;
	}

	private List<SelectItem> lstCittadinanze;




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

	private List<SelectItem> lstMotiviChiusura;




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

				// HashMap<String, DatiInterventoBean> mappaIAperti = new
				// HashMap<String, DatiInterventoBean>();
				// Creo la mappa degli interventi aperti
				// categoria@tipointervento per omettere i tipi intervento
				// corrispondenti dalla lista
				// for (DatiInterventoBean dib : this.listaInterventi) {
				// if (!dib.getInterventoChiuso().booleanValue()) {
				// // mappaIAperti.put(dib.getTipoInterventoCatSoc(), dib);
				// // //Posso erogare lo creare lo stesso tipo di
				// // intervento per più categorie
				// mappaIAperti.put(dib.getTipoIntervento().toString(), dib);
				// }
				// }

				if (settoreId != null && catsocCorrenti != null && !catsocCorrenti.isEmpty()) {
					for (CsCCategoriaSocialeBASIC cs : catsocCorrenti) {
						InterventoDTO dto = new InterventoDTO();
						fillEnte(dto);
						dto.setIdSettore(settoreId);
						dto.setIdCatsoc(cs.getId());
						List<CsCTipoIntervento> beanlst = interventoService.findTipiInterventoSettoreCatSoc(dto);

						if (beanlst != null) {
							SelectItemGroup gr = new SelectItemGroup(cs.getDescrizione());
							List<SelectItem> grItems = new ArrayList<SelectItem>();
							for (CsCTipoIntervento tipo : beanlst) {
								String chiaveInterventoTB = cs.getId() + "@" + tipo.getId(); // Posso
								// erogare
								// lo
								// creare
								// lo
								// stesso
								// tipo
								// di
								// intervento
								// per
								// più
								// categorie
								String chiaveIntervento = tipo.getId().toString();
								// if
								// (!mappaIAperti.containsKey(chiaveIntervento))
								grItems.add(new SelectItem(chiaveInterventoTB, tipo.getDescrizione()));
							}
							if (!grItems.isEmpty()) {
								gr.setSelectItems(grItems.toArray(new SelectItem[grItems.size()]));
								this.lstTipoIntervento.add(gr);
							}

						}
					}
				}
				else {
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

	private List<SelectItem> lstTipoQuotaPasti;




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

	private List<SelectItem> lstTipoQuotaCentroD;




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

	private List<SelectItem> lstTipoRiscossione;




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

	private List<SelectItem> lstTipoOreVoucher;




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

	private List<SelectItem> lstTipoPeriodoErogazione;




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

	private List<SelectItem> lstTipoAttivita;




	@Override
	public List<SelectItem> getLstTipoAttivita() {
		if (lstTipoAttivita == null) {
			lstTipoAttivita = new ArrayList<SelectItem>();
			lstTipoAttivita.add(new SelectItem(null, "- seleziona -"));
			lstTipoAttivita.add(new SelectItem(DataModelCostanti.FoglioAmministrativo.STATO_VALUTAZIONE, "Valutazione"));
			lstTipoAttivita.add(new SelectItem(DataModelCostanti.FoglioAmministrativo.STATO_ATTIVAZIONE, "Attivazione"));
			lstTipoAttivita.add(new SelectItem(DataModelCostanti.FoglioAmministrativo.STATO_SOSPENSIONE, "Sospensione"));
			lstTipoAttivita.add(new SelectItem(DataModelCostanti.FoglioAmministrativo.STATO_CHIUSURA, "Chiusura"));
		}
		return lstTipoAttivita;
	}

	private List<SelectItem> lstTipoSospensione;




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

	private List<SelectItem> lstTipoAttivazione;




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

	private List<SelectItem> lstModalitaIntervento;




	@Override
	public List<SelectItem> getLstModalitaIntervento() {
		if (lstModalitaIntervento == null) {
			lstModalitaIntervento = new ArrayList<SelectItem>();
			lstModalitaIntervento.add(new SelectItem("P", "Prima Erogazione"));
			lstModalitaIntervento.add(new SelectItem("R", "Rinnovo"));
		}
		return lstModalitaIntervento;
	}

	private List<SelectItem> lstTipoPeriodo;




	@Override
	public List<SelectItem> getLstTipoPeriodo() {
		if (lstTipoPeriodo == null) {
			lstTipoPeriodo = new ArrayList<SelectItem>();
			lstTipoPeriodo.add(new SelectItem("1", "Presunta"));
			lstTipoPeriodo.add(new SelectItem("2", "Certa"));
		}
		return lstTipoPeriodo;
	}

	private List<SelectItem> lstTipoGestione;




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

	private List<SelectItem> lstTipoDeroghe;




	@Override
	public List<SelectItem> getLstTipoDeroghe() {
		if (lstTipoDeroghe == null) {
			lstTipoDeroghe = new ArrayList<SelectItem>();
			lstTipoDeroghe.add(new SelectItem("Su Importo", "Su Importo"));
			lstTipoDeroghe.add(new SelectItem("Sulle Applicazioni", "Sulle Applicazioni"));
		}
		return lstTipoDeroghe;
	}

	private List<SelectItem> lstTipoRientriFam;




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

	private List<SelectItem> lstTipoRetta;




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
				List<CsDRelazione> beanLst = diarioService.findRelazioniByCasoTipoIntervento(i);
				if (beanLst != null) {
					for (CsDRelazione s : beanLst) {
						Date dataRelazione = s.getCsDDiario().getDtAmministrativa();
						String descrRelazione = "";

						BaseDTO bd = new BaseDTO();
						fillEnte(bd);
						bd.setObj(s.getCsDDiario().getId());
						List<CsDDiarioDoc> lstDocumenti = diarioService.findDiarioDocById(bd);
						if (lstDocumenti != null && !lstDocumenti.isEmpty())
							descrRelazione = lstDocumenti.iterator().next().getCsLoadDocumento().getNome();
						else
							descrRelazione = s.getProposta() != null ? (s.getProposta().length() > 20 ? s.getProposta().substring(0, 20) + "..." : s.getProposta()) : "Nessuna proposta";

						String descrizione = (dataRelazione != null ? SDF.format(s.getCsDDiario().getDtAmministrativa()) : "") + " - " + descrRelazione;
						lst.add(new SelectItem(s.getDiarioId(), descrizione));
					}
				}
			}
		} catch (Exception e) {
			addErrorDialogFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
		return lst;
	}

	private List<SelectItem> lstTipoAffido;




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

	private List<SelectItem> lstSpeseExtra;




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

	private List<SelectItem> lstSpeseExtraSRM;




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

	private List<SelectItem> lstTipoAdmAdh;




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

	private List<SelectItem> lstEducatori;




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
				List<CsOOperatoreTipoOperatore> lista = operatoreService.getOperatoriByTipoId(dto);
				for (CsOOperatoreTipoOperatore cs : lista) {
					CsOOperatoreSettore opSett = cs.getCsOOperatoreSettore();
					boolean isAttivo = !dataRif.after(cs.getDataFineApp()) && !dataRif.before(cs.getDataInizioApp());
					if (isAttivo && opSett.getCsOSettore().getCsOOrganizzazione().getId().equals(org.getId())
							&& !mapEducatori.containsKey(opSett.getCsOOperatore().getId())) {
						String anagrafica = this.getDenominazioneOperatore(opSett.getCsOOperatore());
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

	private List<SelectItem> lstTipoCirc4;




	public List<SelectItem> getLstTipoCirc4() {
		if (lstTipoCirc4 == null) {
			lstTipoCirc4 = new ArrayList<SelectItem>();
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<CsTbTipoCirc4> lista = confService.getTipiCirc4(cet);
			for (CsTbTipoCirc4 cs : lista) {
				lstTipoCirc4.add(new SelectItem(cs.getId(), cs.getDescrizione()));
			}
		}
		return lstTipoCirc4;
	}

	private List<SelectItem> lstTipoInterventi;




	public List<SelectItem> getLstTipoInterventi() {
		if (lstTipoInterventi == null) {
			lstTipoInterventi = new ArrayList<SelectItem>();

			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<CsTbInterventiUOL> lista = confService.getinterventiUOL(cet);
			for (CsTbInterventiUOL cs : lista) {
				lstTipoInterventi.add(new SelectItem(cs.getId(), cs.getDescrizione()));
			}
		}
		return lstTipoInterventi;
	}

	private List<SelectItem> lstTipoProgetto;




	public List<SelectItem> getLstTipoProgetto() {
		if (lstTipoProgetto == null) {
			lstTipoProgetto = new ArrayList<SelectItem>();

			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<CsTbTipoProgetto> lista = confService.getTipiProgetto(cet);
			for (CsTbTipoProgetto cs : lista) {
				lstTipoProgetto.add(new SelectItem(cs.getId(), cs.getDescrizione()));
			}
		}
		return lstTipoProgetto;
	}

	private List<SelectItem> lstTipoContributo;
	private List<CsTbTipoContributo> lstTipoContributoTooltip;




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




	public Long getIdErogazioneMaster() {
		return idErogazioneMaster;
	}




	public void setIdErogazioneMaster(Long idErogazioneMaster) {
		this.idErogazioneMaster = idErogazioneMaster;
	}




	// inizio SISO-556
	public boolean isVisualizzaIntervalloDateErogazione() {

		boolean res = false;
		if (erogazioneInterventoBean.getStatoSelezionato() != null) {
				// Il range di date deve essere proposto SOLO se lo stato selezionato è di tipo "E" e il rendiconto è periodico
			if (TipoStatoErogazione.EROGATIVO.equals(erogazioneInterventoBean.getStatoSelezionato().getTipo())
						&&  getDatiTariffeInterventoBean().getCsIQuota().getCsIQuotaRipartiz().getFlagRendiconto().
								equals(DataModelCostanti.CsIQuotaRipartiz.FLAG_RENDICONTO_PERIODICO)  ) {
				res = true;
			}
		}

		return res;
	}




	public boolean isUnitaMisuraRequired() {

		// in assenza dell'intervento è sempre obbligatoria
		if (!datiInterventoTabRendered) {
			return true;
		} else{ // in presenza dell'intervento è obbligatoria solo quando l'intervento è in attivazione
			if ( datiFglIntBean!=null &&
					DataModelCostanti.FoglioAmministrativo.STATO_ATTIVAZIONE.equals( datiFglIntBean.getFlagAttivazione() )) {
				return true;
			} else { 
				return false;
			}
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

		for (InterventoErogazHistoryRowBean interventoErogazHistoryRowBean : erogazioneInterventoBean.getErogazioneHistory().getRows()) {
			if (interventoErogazHistoryRowBean.getStato().getTipo().equals(DataModelCostanti.TipoStatoErogazione.EROGATIVO)) {
				storicoVuoto = false;
			}
		}

		boolean unitaMisuraValida;
		if (datiTariffeInterventoBean.getSelUnitaMisura() == null
				|| datiTariffeInterventoBean.getSelUnitaMisura() == DataModelCostanti.CsTbUnitaMisura.ID_EURO) {
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
		return datiTariffeInterventoBean.isSolaLettura() || erogazioneInterventoBean.isErogazioniPresenti();
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
				|| erogazioneInterventoBean.isErogazioniPresenti();
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
		List<CsOSettore> listaSettori = confService.getSettoreAll(opDto);

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
				if (lstTit == null)
					lstTit = new ArrayList<CsOSettore>();
				if (lstEr == null)
					lstEr = new ArrayList<CsOSettore>();
				if (lstGest == null)
					lstGest = new ArrayList<CsOSettore>();

				if (s.getFlagIntTitolare()!=null && s.getFlagIntTitolare().booleanValue()) {
					lstTit.add(s);
					mapEntiTitolariDiSettore.put(s.getCsOOrganizzazione().getId(), lstTit);
				}
				if (s.getFlgIntEroga()!=null && s.getFlgIntEroga().booleanValue()) {
					lstEr.add(s);
					mappaSettoriEr.put(s.getCsOOrganizzazione().getId(), lstEr);
				}
				if (s.getFlgIntGestore()!=null && s.getFlgIntGestore().booleanValue()) {
					lstGest.add(s);
					mapEntiGestoriDiSettore.put(s.getCsOOrganizzazione().getId(), lstGest);
				}

			}

			List<SelectItem> selectItemListGestoriSettore = loadListaSettoriGroup(mapEntiGestoriDiSettore);

			datiProgettoBean.setListaSettori(listaSettori);
			datiProgettoBean.setListaSettoriTitGroup(loadListaSettoriGroup(mapEntiTitolariDiSettore));
			datiProgettoBean.setListaSettoriGestGroup(selectItemListGestoriSettore);

			erogazioneInterventoBean.setListaSettori(listaSettori);
			erogazioneInterventoBean.setListaSettoriErGroup(loadListaSettoriGroup(mappaSettoriEr));
			erogazioneInterventoBean.setListaSettoriGestGroup(selectItemListGestoriSettore);
			erogazioneInterventoBean.setMapEntiTitolariDiSettore(mapEntiTitolariDiSettore);
			erogazioneInterventoBean.setMapEntiGestoriDiSettore(mapEntiGestoriDiSettore);
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


//@formatter:off
	public Long getSelSettoreTitolareId() { return datiProgettoBean.getSelSettoreTitolareId(); }

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
		
		Long selSettoreTitolareId = datiProgettoBean.getSelSettoreTitolareId();
		Long selSettoreGestoreId = datiProgettoBean.getSelSettoreGestoreId();
		
		logger.info("settoreTitolareId: " + selSettoreTitolareId);
		logger.info("settoreGestoreId : " + selSettoreGestoreId);
		
		if (	selSettoreTitolareId != null 	&& 
				selSettoreGestoreId != null 	&& 
				selSettoreTitolareId.equals(selSettoreGestoreId)) {
			erogazioneInterventoBean.setGestoreComeTitolare(selSettoreTitolareId.equals(selSettoreGestoreId));
		}
		else {
			erogazioneInterventoBean.setGestoreComeTitolare(false);
		}
		
		logger.info("gestoreComeTitolare : " + erogazioneInterventoBean.isGestoreComeTitolare());
		
		erogazioneInterventoBean.setSettoreTitolare(datiProgettoBean.getSettoreTitolare());
		erogazioneInterventoBean.setSettoreGestore(datiProgettoBean.getSettoreGestore());
		
		RuoloEnteGestoreSpesa ruoloEnteGestoreSpesa = erogazioneInterventoBean.getRuoloEnteGestoreSpesa();
		if (selSettoreTitolareId == null && ruoloEnteGestoreSpesa == RuoloEnteGestoreSpesa.TITOLARE) {
			erogazioneInterventoBean.setRuoloEnteGestoreSpesa(RuoloEnteGestoreSpesa.NON_SELEZIONATO);
		}
		if (selSettoreGestoreId == null && ruoloEnteGestoreSpesa == RuoloEnteGestoreSpesa.GESTORE) {
			erogazioneInterventoBean.setRuoloEnteGestoreSpesa(RuoloEnteGestoreSpesa.NON_SELEZIONATO);
		}
		logger.info("ruoloEnteGestoreSpesa : " + erogazioneInterventoBean.getRuoloEnteGestoreSpesa());
		
		erogazioneInterventoBean.validaEnteGestoreSpesa();
		
		logger.info(">>> cmbSettoreOnChange end");
		
	}
	

	public void impostaSettoreGestoreUgualeASettoreTitolare() {
		Long selSettoreTitolareId = datiProgettoBean.getSelSettoreTitolareId();
		if (selSettoreTitolareId != null) {
			
			RuoloEnteGestoreSpesa ruoloEnteGestoreSpesa = erogazioneInterventoBean.getRuoloEnteGestoreSpesa();
			if (ruoloEnteGestoreSpesa == RuoloEnteGestoreSpesa.GESTORE)
				erogazioneInterventoBean.setRuoloEnteGestoreSpesa(RuoloEnteGestoreSpesa.TITOLARE);
			
			//erogazioneInterventoBean.sobGestioneSpesaOnChange();
			datiProgettoBean.setIdSettoreGestoreComeIdSettoreTitolare();
			
			erogazioneInterventoBean.setSettoreGestore(datiProgettoBean.getSettoreGestore());
			erogazioneInterventoBean.setSettoreTitolare(datiProgettoBean.getSettoreTitolare());
			
			erogazioneInterventoBean.validaEnteGestoreSpesa();
			
			
		}
	}
//@formatter:on


	public PaiDTO getPaidto() {
		return paidto;
	}

	public void setPaidto(PaiDTO paidto) {
		this.paidto = paidto;
	}
	
	

}
