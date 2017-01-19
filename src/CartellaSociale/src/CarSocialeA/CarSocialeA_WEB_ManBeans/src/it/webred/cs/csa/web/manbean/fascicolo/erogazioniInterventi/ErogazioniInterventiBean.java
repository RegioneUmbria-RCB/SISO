package it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.web.manbean.fascicolo.FglInterventoBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView.TipoInterventoManBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.manbean.UserSearchBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;
import it.webred.siso.ws.client.anag.exception.AnagrafeException;
import it.webred.siso.ws.client.anag.exception.AnagrafeSessionException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

@ManagedBean
@ViewScoped
public class ErogazioniInterventiBean extends CsUiCompBaseBean {

	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
	protected AnagrafeService anagrafeService = (AnagrafeService) getEjb("CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");

	@ManagedProperty(value = "#{lazyListaErogazioniModel}")
	private LazyListaErogazioniModel lazyListaErogazioniModel;

	@ManagedProperty( value = "#{fglInterventoBean}")
	private FglInterventoBean fglInterventoBean;
	private UserSearchBean userSearchBean = new UserSearchBean();
	
	private TipoInterventoManBean tipoIntTreeView;
	private Long idIntervento = 0L;
	private Long idDiario = 0L;
	private String tipoIntCustomName = "";
	private Long tipoInterventoId = 0L;
	private List<SelectItem> tipoInterventosAll = new LinkedList<SelectItem>();
	private List<SelectItem> tipoInterventosRecenti = new LinkedList<SelectItem>();
	private List<SelectItem> tipoInterventosCustom = new LinkedList<SelectItem>();
	private List<SelectItem> listaTipoFiltroInterventi = new LinkedList<SelectItem>();
	
	
	private String idRow;

	private SoggettoErogazioneBean soggettoErogazioneSelezionato;
	private Boolean provenienteDaFascicolo = false;

	public ErogazioniInterventiBean() {
		fglInterventoBean = (FglInterventoBean)getBeanReference("fglInterventoBean");
	}
	
	@PostConstruct
	public void init() {
		init( DataModelCostanti.ListaErogazioni.TUTTI );
	}

	protected void init(int defaultTipoFiltroIntervento ){
		listaTipoFiltroInterventi.add(new SelectItem(DataModelCostanti.ListaErogazioni.TUTTI, "Tutti"));
		listaTipoFiltroInterventi.add(new SelectItem(DataModelCostanti.ListaErogazioni.CON_RICHIESTA, "Richiesti"));
		listaTipoFiltroInterventi.add(new SelectItem(DataModelCostanti.ListaErogazioni.SENZA_RICHIESTA, "Erogati senza richiesta "));

		// tipoFiltroInterventiSelezionato =
		// DataModelCostanti.ListaErogazioni.TUTTI;
		lazyListaErogazioniModel = new LazyListaErogazioniModel();
		lazyListaErogazioniModel.Setup(defaultTipoFiltroIntervento, soggettoErogazioneSelezionato);

		// loadListaInterventi();
		loadTipoInterventi();
		this.tipoIntTreeView = new TipoInterventoManBean(tipoInterventosAll,"");
		tipoInterventoId = 0L;
		// rowIndex = null;
		idRow = null;
	}
	
	public void SetFromFascicolo(CsASoggettoLAZY soggetto)
	{
		provenienteDaFascicolo = true;
		soggettoErogazioneSelezionato = new SoggettoErogazioneBean(soggetto, true);

		init(DataModelCostanti.ListaErogazioni.SENZA_RICHIESTA);
	}

	protected void loadSoggettoErogazioneSelezionato() {
		DatiUserSearchBean sbean = userSearchBean.getSelSoggetto();
		String idPersonaSelezionata = sbean!=null ? sbean.getId() : null;
		if (StringUtils.isNotEmpty(idPersonaSelezionata)) {
			if (idPersonaSelezionata.trim().startsWith("SANITARIA")) {
				PersonaFindResult p = null;
				if(sbean.getSoggetto()!=null)
				  p = (PersonaFindResult)sbean.getSoggetto();
				if(p==null){
					try {
						p = this.getPersonaDaAnagSanitaria(idPersonaSelezionata.replace("SANITARIA", ""));
					} catch (AnagrafeException e) {
						logger.error(e.getMessage(), e);
					} catch (AnagrafeSessionException e) {
						logger.error(e.getMessage(), e);
					}
				}
				if (p != null){
					String cittadinanza = getCittadinanzaByIstat(p.getCodIstatCittadinanza());
					soggettoErogazioneSelezionato = new SoggettoErogazioneBean(p.getCognome(), p.getNome(), p.getCodfisc(), cittadinanza, p.getDataNascita(), true);
				}
			}
			else
			{
				RicercaSoggettoAnagrafeDTO ricercaDto = new RicercaSoggettoAnagrafeDTO();
				fillEnte(ricercaDto);
				ricercaDto.setIdVarSogg(idPersonaSelezionata);
				SitDPersona persona = anagrafeService.getPersonaById(ricercaDto);
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				String codFisPersona = null == persona ? "" : persona.getCodfisc();
				dto.setObj(codFisPersona);
				CsASoggettoLAZY soggetto = soggettoService.getSoggettoByCF(dto);

				if (soggetto != null)
					soggettoErogazioneSelezionato = new SoggettoErogazioneBean(soggetto, true);
				else if (persona != null)
					soggettoErogazioneSelezionato = new SoggettoErogazioneBean(persona, true);
			}
		}
		else
			soggettoErogazioneSelezionato = null;
	}

	protected void loadTipoInterventi() {

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		List<CsCTipoIntervento> lstTipoInterventi = interventoService.findAllTipiIntervento(dto);
		List<KeyValueDTO> lstTipoInterventiRecenti = interventoService.findTipiInterventoRecenti(dto);
		List<KeyValueDTO> lstTipoInterventiCustom = interventoService.findTipiInterventoCustomRecenti(dto);
		this.tipoInterventosAll = new LinkedList<SelectItem>();
		this.tipoInterventosRecenti = new LinkedList<SelectItem>();
		this.tipoInterventosCustom = new LinkedList<SelectItem>();
		for (CsCTipoIntervento i : lstTipoInterventi) 
			this.tipoInterventosAll.add(new SelectItem((Long)i.getId(), i.getDescrizione()));
	
		for (KeyValueDTO i : lstTipoInterventiRecenti) 
			this.tipoInterventosRecenti.add(new SelectItem((Long)i.getCodice(), i.getDescrizione()));
		
		for (KeyValueDTO i : lstTipoInterventiCustom) 
			this.tipoInterventosCustom.add(new SelectItem((Long)i.getCodice(), i.getDescrizione()));
	}
	
	/*
	 * protected void loadListaInterventi() { this.listaInterventi=null;
	 * this.listaInterventiFiltro=null;
	 * 
	 * CsOOperatoreSettore opSettore = getCurrentOpSettore(); String permesso =
	 * getPermessoErogazioneInterventi(); ErogazioniSearchCriteria bDto = new
	 * ErogazioniSearchCriteria(); fillEnte(bDto);
	 * bDto.setSettoreId(opSettore.getCsOSettore().getId());
	 * bDto.setOrganizzazioneId
	 * (opSettore.getCsOSettore().getCsOOrganizzazione().getId());
	 * bDto.setPermessoAutorizzativo
	 * (TipoPermessoErogazioneInterventi.PERMESSO_AUTORIZZATIVO
	 * .equals(getPermessoErogazioneInterventi()));
	 * 
	 * listaInterventiAll = new LinkedList<ErogInterventoRowBean>();
	 *//**
	 * Recupero gli interventi eseguiti, privi di foglio ammnistrativo che
	 * abbiano una delle seguenti caratteristiche: 1. settore dell'operatore che
	 * l'ha inserito = corrente 2. organizzazione dell'erogazione = corrente 3.
	 * organizzazione del caso (recuperabile mediante CF) = corrente
	 */
	/*
	 * 
	 * List<ErogazioneDTO> lstInterventi =
	 * interventoService.searchListaErogInterventiBySettore(bDto); for
	 * (ErogazioneDTO csIIntervento : lstInterventi) { ErogInterventoRowBean row
	 * = new ErogInterventoRowBean(csIIntervento, permesso);
	 * this.listaInterventiAll.add(row); }
	 * 
	 * this.listaInterventiRichiesti = new LinkedList<ErogInterventoRowBean>();
	 * this.listaInterventiSenzaRichiesta = new
	 * LinkedList<ErogInterventoRowBean>(); for (ErogInterventoRowBean row :
	 * this.listaInterventiAll) { if (row.isRenderBtnAvviaErog())
	 * this.listaInterventiRichiesti.add(row); else if
	 * (row.isRenderBtnEliminaErog())
	 * this.listaInterventiSenzaRichiesta.add(row); }
	 * 
	 * }
	 */

	// /******PUBLIC METHODS*********

	
	public void inizializzaDialogo(Object obj) {

		if (obj == null) {
			
			this.tipoInterventoId = tipoIntTreeView.getSelTipoInterventoId();
			if (tipoInterventoId == null || tipoInterventoId <= 0) {
				addError("Selezionare un tipo di intervento", "Selezionare un tipo di intervento");
				return;
			}

			if( !provenienteDaFascicolo )
				loadSoggettoErogazioneSelezionato();

			//boolean datiErogazioniTabRendered = !provenienteDaFascicolo;
			fglInterventoBean.inizializzaErogazione(soggettoErogazioneSelezionato);
			fglInterventoBean.inizializzaDialog(false, true, 0L, 0L, tipoInterventoId, tipoIntTreeView.getSelTipoInterventoCutomId(), tipoIntTreeView.getSelCatSocialeId(), true, true, "Nuova Erogazione", null);
			fglInterventoBean.setDatiInterventoTabRendered(false);
			
		}else{

			//Carico un'erogazione esistente per modificarla
			ErogInterventoRowBean row = (ErogInterventoRowBean) obj;
			boolean datiErogazioniTabRendered = row.isRenderBtnEroga() || row.isRenderBtnAvviaErog();
			fglInterventoBean.inizializzaErogazione(row.getBeneficiari(), row.getBeneficiarioRiferimento());
			fglInterventoBean.inizializzaDialog(false, datiErogazioniTabRendered, row.getIdIntervento(), row.getDiarioId(), row.getIdTipoIntervento(), row.getIdTipoInterventoCustom(), tipoIntTreeView.getSelCatSocialeId(), true, true, "Modifica Erogazione", row.getMaster().getIdInterventoEsegMaster());
			fglInterventoBean.setDatiInterventoTabRendered(false);
			
		}
		
		userSearchBean = new UserSearchBean();
		//overwrite header
		fglInterventoBean.setHeaderDialogo( "Erogazione - " + fglInterventoBean.getErogazioneInterventoBean().getNomeTipoErogazione());
		
		this.tipoInterventoId=0L;
		this.tipoIntCustomName="";
		this.tipoIntTreeView.reset();
	}

	public void rimuoviInterventoSenzaRichiesta(ErogInterventoRowBean rowBean) {
		if(rowBean==null){
			addError("Non è stato selezionato alcune elemento da eliminare", "");
			return;
			
		}else{
			idRow = rowBean.getIdRow();
			if(!idRow.startsWith("E")){
				addError("Non è possibile eliminare erogazioni associate a richieste intervento", "");
				return;
			}
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
		    Long idErogazione = new Long(idRow.replaceFirst("E", ""));
			dto.setObj(idErogazione);
			interventoService.rimuoviInterventoEseguitoMast(dto);
		}
	}

	public void chiamataUpdate() {
		logger.debug("Aggiorno la lista!");
	}

	public void onTipoListaInterventiChanged(AjaxBehaviorEvent event) {
		// System.out.println("onchange");
	}
	
	// ********GETTERS AND SETTERS

	public TipoInterventoManBean getTipoIntTreeView() {
		return tipoIntTreeView;
	}

	public void setTipoIntTreeView(TipoInterventoManBean tipoIntTreeView) {
		this.tipoIntTreeView = tipoIntTreeView;
	}

	public UserSearchBean getUserSearchBean() {
		return userSearchBean;
	}

	public Long getIdIntervento() {
		return idIntervento;
	}

	public void setIdIntervento(Long idIntervento) {
		this.idIntervento = idIntervento;
	}

	public Long getIdDiario() {
		return idDiario;
	}

	public void setIdDiario(Long idDiario) {
		this.idDiario = idDiario;
	}


	public List<SelectItem> getTipoInterventosAll() {
		return tipoInterventosAll;
	}

	public List<SelectItem> getTipoInterventosRecenti() {
		return tipoInterventosRecenti;
	}

	public List<SelectItem> getTipoInterventosCustom() {
		return tipoInterventosCustom;
	}
	
	public void setTipoInterventoId(Long tipoInterventoId) {
		this.tipoInterventoId = tipoInterventoId;
	}

	public Long getTipoInterventoId() {
		return tipoIntTreeView.getSelTipoInterventoId();
		//return tipoInterventoId;
	}
	                
	public String getTipoIntCustomName() {
		this.tipoIntCustomName= tipoIntTreeView.getSelTipoInterventoCustom().getDescrizione();
		return this.tipoIntCustomName;
	}

	public void setTipoIntCustomName(String tipoIntCustomName) {
		this.tipoIntCustomName = tipoIntCustomName;
	}

	public void setInterventoService(AccessTableInterventoSessionBeanRemote interventoService) {
		this.interventoService = interventoService;
	}

	public List<SelectItem> getListaTipoFiltroInterventi() {
		return listaTipoFiltroInterventi;
	}

	public LazyListaErogazioniModel getLazyListaErogazioniModel() {
		return lazyListaErogazioniModel;
	}

	public void setLazyListaErogazioniModel(LazyListaErogazioniModel lazyListaErogazioniModel) {
		this.lazyListaErogazioniModel = lazyListaErogazioniModel;
	}

	public String getIdRow() {
		return idRow;
	}

	public void setIdRow(String idRow) {
		this.idRow = idRow;
	}

	public FglInterventoBean getFglInterventoBean() {
		return fglInterventoBean;
	}

	public void setFglInterventoBean(FglInterventoBean fglInterventoBean) {
		this.fglInterventoBean = fglInterventoBean;
	}

	public List<String> getTipoBeneficiarios() {
		return new ArrayList<String>(){{add(DataModelCostanti.ListaBeneficiari.INDIVIDUALE);add(DataModelCostanti.ListaBeneficiari.NUCLEO);add(DataModelCostanti.ListaBeneficiari.GRUPPO);}};
		
	}

}