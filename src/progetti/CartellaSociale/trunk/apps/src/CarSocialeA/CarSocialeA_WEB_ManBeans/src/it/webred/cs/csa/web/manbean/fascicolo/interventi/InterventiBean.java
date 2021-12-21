package it.webred.cs.csa.web.manbean.fascicolo.interventi;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneDettaglioSintesiDTO;
import it.webred.cs.csa.ejb.dto.pai.CsPaiMastSoggDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.InserimentoConsuntivazioneDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.StrutturaDisponibilitaDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.csa.web.manbean.fascicolo.FglInterventoBean;
import it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi.ErogazioniInterventiBean;
import it.webred.cs.csa.web.manbean.fascicolo.initialize.InitInterventi;
import it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView.TipoInterventoManBean;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.jsf.bean.DatiInterventoBean;
import it.webred.cs.jsf.interfaces.IDatiInterventi;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

public class InterventiBean extends FascicoloCompBaseBean implements IDatiInterventi {

	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
	private final boolean DENTRO_FASCICOLO = true;
	private FglInterventoBean fglInterventoBean;
	private ErogazioniInterventiBean erogazioniInterventiBean;
	private TipoInterventoManBean tipoIntTreeView;
	private List<DatiInterventoBean> lstInterventi;
	private List<SelectItem> listTipoInterventos = null;
	private String filterCategorie="";
	protected boolean isTreeViewIntervento; //SISO-1110
	
	@PostConstruct
	public void PostConstruct() {
		fglInterventoBean = (FglInterventoBean)getBeanReference("fglInterventoBean");
	}

	public InterventiBean() {
		PostConstruct();
	}

	@Override
	protected void initializeData(Object data) {
		erogazioniInterventiBean = new ErogazioniInterventiBean();
		erogazioniInterventiBean.SetFromFascicolo(csASoggetto);

		fglInterventoBean.initialize(csASoggetto, catsocCorrenti, null);
		fglInterventoBean.initializeData(null);
		loadListaTipiIntervento();
		refreshListaInterventi(data);
		
		filterCategorie = this.getFilterCategorie(fglInterventoBean.getCatsocCorrenti());
		tipoIntTreeView = new TipoInterventoManBean(listTipoInterventos,filterCategorie);		
		erogazioniInterventiBean.setTipoIntTreeView(this.tipoIntTreeView);
	}
	
	public void initializeDataFromExtPai(CsPaiMastSoggDTO soggettoSelezionato) {
		erogazioniInterventiBean = new ErogazioniInterventiBean();
		erogazioniInterventiBean.SetFromPai(soggettoSelezionato);

		fglInterventoBean.initialize(soggettoSelezionato);
		fglInterventoBean.initializeData(null);
		loadListaTipiIntervento();
		refreshListaInterventi(null);
		//Manca il recupero di filterCategorie!?
		tipoIntTreeView = new TipoInterventoManBean(listTipoInterventos,filterCategorie);		
		erogazioniInterventiBean.setTipoIntTreeView(this.tipoIntTreeView);
	}
	
	public void initializeDataFromExtPai(CsASoggettoLAZY soggetto, List<CsCCategoriaSociale> catSoc) {
		erogazioniInterventiBean = new ErogazioniInterventiBean();
		erogazioniInterventiBean.SetFromPai(soggetto);

		fglInterventoBean.initialize(soggetto, catSoc, null);
		fglInterventoBean.initializeData(null);
		loadListaTipiIntervento();
		refreshListaInterventi(null);
		
		filterCategorie = this.getFilterCategorie(fglInterventoBean.getCatsocCorrenti());
		tipoIntTreeView = new TipoInterventoManBean(listTipoInterventos,filterCategorie);		
		erogazioniInterventiBean.setTipoIntTreeView(this.tipoIntTreeView);
	}

	public void refreshListaInterventi(Object data) {
		loadListaInterventi(data);
		fglInterventoBean.setListaInterventi(this.getListaInterventi());
	}

	private void loadListaTipiIntervento()
	{
		if (listTipoInterventos == null)
		{
			// filterCategorie="";
			listTipoInterventos = new LinkedList<SelectItem>();
			List<SelectItem> listTipoIntervento = fglInterventoBean.getLstTipoIntervento();
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
		}
	}

	protected void loadListaInterventi(Object data) {
		lstInterventi = new ArrayList<DatiInterventoBean>();
		try {
			logger.info("INIT InterventiBean - Recupero lista Interventi");
			if (idCaso != null && idCaso > 0) {
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(idCaso);
				List<CsIIntervento> lsti;
				if (data == null)
					lsti = InitInterventi.loadLista(dto);
				else
					lsti = (List<CsIIntervento>) data;
				
				List<CsAComponente> listaParenti = CsUiCompBaseBean.caricaParenti(idSoggetto, null);
				for (CsIIntervento i : lsti) {
					DatiInterventoBean dib = new DatiInterventoBean(i, idSoggetto, listaParenti);
					
					//Recupero le informazioni di sintesi delle erogazioni effettuate 
					dto.setObj(i.getId());
					List<ErogazioneDettaglioSintesiDTO> lstErogazioni = interventoService.getSintesiErogazioniByInterventoId(dto);
					dib.setListaSintesiErogazioni(lstErogazioni);
					this.lstInterventi.add(dib);
				}
			}
			logger.info("END InterventiBean - Recupero lista Interventi");
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}
	
	public void inizializzaNuovaErogazione() {
		fglInterventoBean.setFromPai(false);
		erogazioniInterventiBean.inizializzaDialogo(null);
		fglInterventoBean.setDatiErogazioniTabRendered(true);
		fglInterventoBean.setDatiInterventoTabRendered(false);
		
		if (isTreeViewTipoIntervento())// SISO-1110
			tipoIntTreeView.reset();
		else
			tipoIntTreeView.resetCustomIstat();
	}
	
	//SISO-748
	public void inizializzaNuovaErogazione(TipoInterventoManBean tipoIntTreeView, Boolean cambiaBeneficiario){
		fglInterventoBean.setFromPai(true);
		erogazioniInterventiBean.inizializzaDialogoByTreeView(tipoIntTreeView);
		fglInterventoBean.setDatiErogazioniTabRendered(true);
		fglInterventoBean.setDatiInterventoTabRendered(false);
		fglInterventoBean.getErogazioneInterventoBean().setCambiaBeneficiarioRiferimento(cambiaBeneficiario);
	}
	public void inizializzaNuovaErogazione(TipoInterventoManBean tipoIntTreeView, Boolean cambiaBeneficiario, StrutturaDisponibilitaDTO struttDisp){
		fglInterventoBean.setFromPai(true);
		erogazioniInterventiBean.inizializzaDialogoByTreeView(tipoIntTreeView,struttDisp);
		fglInterventoBean.setDatiErogazioniTabRendered(true);
		fglInterventoBean.setDatiInterventoTabRendered(false);
		fglInterventoBean.getErogazioneInterventoBean().setCambiaBeneficiarioRiferimento(cambiaBeneficiario);
	}
	public void inizializzaNuovaErogazione(TipoInterventoManBean tipoIntTreeView, Boolean cambiaBeneficiario, StrutturaDisponibilitaDTO struttDisp, InserimentoConsuntivazioneDTO consuntivazione){
		erogazioniInterventiBean.inizializzaDialogoByTreeView(tipoIntTreeView,struttDisp);
		fglInterventoBean.setDatiErogazioniTabRendered(true);
		fglInterventoBean.setDatiInterventoTabRendered(false);
		fglInterventoBean.getErogazioneInterventoBean().setCambiaBeneficiarioRiferimento(cambiaBeneficiario);
	}
	
	//inizio evoluzione-pai
	public boolean inizializzaNuovoIntervento(TipoInterventoManBean tipoIntTreeView, CsPaiMastSoggDTO soggettoPai, StrutturaDisponibilitaDTO  strutturaDispoDTO) { 
		fglInterventoBean.setFromPai(true);
		boolean datiErogazioniTabRendered = isPermessoAutorizzativo();
		boolean readOnly = this.readOnly;
		
		Long tipoInterventoId = tipoIntTreeView.getSelTipoInterventoId();
		Long tipoInterventoCustomId = tipoIntTreeView.getSelTipoInterventoCutomId();
		Long catSoc = tipoIntTreeView.getSelCatSocialeId();
		
		if (soggettoPai != null) {
			fglInterventoBean.inizializzaErogazione(soggettoPai);
			readOnly = true;
		}
		
		fglInterventoBean.inizializzaDialog(true,datiErogazioniTabRendered, 0L, 0L, tipoInterventoId, tipoInterventoCustomId, catSoc, readOnly, true, "Nuovo Intervento",null,DENTRO_FASCICOLO, strutturaDispoDTO, null);
		
		boolean interventoSelezionato;
		if (tipoIntTreeView.getSelTipoInterventoId() == null || tipoIntTreeView.getSelTipoInterventoId() <= 0) {
			interventoSelezionato = false; 
		} else { 
			interventoSelezionato = true; 
		}
		if (isTreeViewTipoIntervento())	// SISO-1110
			tipoIntTreeView.reset();
		else
			tipoIntTreeView.resetCustomIstat();
		return interventoSelezionato;
	}
	//fine evoluzione-pai
	
	
	public void inizializzaNuovoIntervento() { 
		boolean datiErogazioniTabRendered = isPermessoAutorizzativo();
		Long tipoInterventoId = tipoIntTreeView.getSelTipoInterventoId();
		Long tipoInterventoCustomId = tipoIntTreeView.getSelTipoInterventoCutomId();
		Long catSoc = tipoIntTreeView.getSelCatSocialeId();
		
		if(catSoc!=null){
			boolean categoriaCaso = false;
			for(CsCCategoriaSociale cat : this.getCatsocCorrenti()){
				if(cat.getId()==catSoc.longValue()) categoriaCaso = true;
			}
			if(!categoriaCaso){
				this.addWarning("Operazione non consentita", "E' possibile programmare interventi solo per le categorie sociali del caso corrente");
				return;
			}
		}
			
		fglInterventoBean.inizializzaDialog(true,datiErogazioniTabRendered, 0L, 0L, tipoInterventoId, tipoInterventoCustomId, catSoc, readOnly, true, "Nuovo Intervento",null,DENTRO_FASCICOLO, null, null);
		if (isTreeViewTipoIntervento())	// SISO-1110
			tipoIntTreeView.reset();
		else
			tipoIntTreeView.resetCustomIstat();
	}
	public void inizializzaNuovoFoglioAmministrativo( DatiInterventoBean intervento ) {
		boolean datiErogazioniTabRendered = isPermessoAutorizzativo();
		
		Long interventoId = intervento.getIdIntervento();
		Long tipoInterventoId = intervento.getIdTipoIntervento();
		Long tipoInterventoCustomId = intervento.getIdTipoIntrCustom();
		Long catSoc = intervento.getIdCatSociale();
		
		CsIInterventoEsegMast master = null;
		if(datiErogazioniTabRendered && intervento.getIdMasterErogazione()!=null && intervento.getIdMasterErogazione().longValue()>0) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(intervento.getIdMasterErogazione());
			master = interventoService.getCsIInterventoEsegMastById(dto);
		}
			
		fglInterventoBean.inizializzaDialog(true,datiErogazioniTabRendered, interventoId, 0L, tipoInterventoId, tipoInterventoCustomId, catSoc, readOnly, true, "Nuovo Foglio Amministrativo", master, DENTRO_FASCICOLO, null, null);
		
		if (isTreeViewTipoIntervento())// SISO-1110
			tipoIntTreeView.reset();
		else
			tipoIntTreeView.resetCustomIstat();
	}
	public void inizializzaModificaFoglioAmministrativo( CsFlgIntervento fog ) {
		boolean datiErogazioniTabRendered = isPermessoAutorizzativo();
		boolean datiIntTabRendered = true;
		
		//inizio SISO-500 
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(fog.getCsIIntervento().getId());
		CsIInterventoEsegMast csIInterventoEsegMast = interventoService.getCsIInterventoEsegMastByInterventoId(dto);
		//fine SISO-500 
		
		Long idTipoIntervento = fog.getCsIIntervento().getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento().getId();
		Long idTipoInterventoCustom = fog.getCsIIntervento().getCsIInterventoCustom()!=null ? fog.getCsIIntervento().getCsIInterventoCustom().getId() : null;
		Long idCatSoc = fog.getCsIIntervento().getCsRelSettCsocTipoInter().getCsRelSettoreCatsoc().getCsCCategoriaSociale().getId();
		Long idDiario = fog.getDiarioId();
		Long idIntervento = fog.getCsIIntervento().getId();
		fglInterventoBean.inizializzaDialog(datiIntTabRendered,datiErogazioniTabRendered, idIntervento, idDiario, 
		        		idTipoIntervento, idTipoInterventoCustom , idCatSoc,  readOnly, true, "Modifica Foglio Amministrativo", csIInterventoEsegMast, DENTRO_FASCICOLO, null, null);
		
		if (isTreeViewTipoIntervento())// SISO-1110
			tipoIntTreeView.reset();
		else
			tipoIntTreeView.resetCustomIstat();
	}

	@Override
	public List<DatiInterventoBean> getListaInterventi() {
		return lstInterventi;
	}

	@Override
	public Long getIdCaso() {
		return idCaso;
	}

	@Override
	public void setIdCaso(Long idCaso) {
		this.idCaso = idCaso;
	}

	public FglInterventoBean getFglInterventoBean() {
		return fglInterventoBean;
	}

	public ErogazioniInterventiBean getErogazioniInterventiBean() {
		return erogazioniInterventiBean;
	}

	public TipoInterventoManBean getTipoIntTreeView() {
		return tipoIntTreeView;
	}

	public void setTipoIntTreeView(TipoInterventoManBean tipoIntTreeView) {
		this.tipoIntTreeView = tipoIntTreeView;
	}

	//inizio evoluzione-pai
	public List<SelectItem> getListTipoInterventos() {
		return listTipoInterventos;
	}

	public void setListTipoInterventos(List<SelectItem> listTipoInterventos) {
		this.listTipoInterventos = listTipoInterventos;
	}

	public String getFilterCategorie() {
		return filterCategorie;
	}

	public void setFilterCategorie(String filterCategorie) {
		this.filterCategorie = filterCategorie;
	} 	
	//fine evoluzione-pai
	//Inizio SISO-1110
		public boolean isTreeViewIntervento() {
			return isTreeViewTipoIntervento();
		}

		public void setTreeViewIntervento(boolean isTreeViewIntervento) {
			this.isTreeViewIntervento = isTreeViewIntervento;
		}
			//Fine SISO-1110
	
}
