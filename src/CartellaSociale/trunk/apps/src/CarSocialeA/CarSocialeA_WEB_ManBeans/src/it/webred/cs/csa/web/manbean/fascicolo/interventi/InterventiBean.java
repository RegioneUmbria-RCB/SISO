package it.webred.cs.csa.web.manbean.fascicolo.interventi;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.csa.web.manbean.fascicolo.FglInterventoBean;
import it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi.ErogazioniInterventiBean;
import it.webred.cs.csa.web.manbean.fascicolo.initialize.InitInterventi;
import it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView.TipoInterventoManBean;
import it.webred.cs.data.model.CsCCategoriaSocialeBASIC;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.jsf.Costanti.TipoPermessoErogazioneInterventi;
import it.webred.cs.jsf.bean.DatiInterventoBean;
import it.webred.cs.jsf.interfaces.IDatiInterventi;

import java.util.ArrayList;
import java.util.Date;
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
//evoluzione-pai		String filterCategorie = "";
		if (fglInterventoBean.getCatsocCorrenti() != null) {
			for (CsCCategoriaSocialeBASIC item : fglInterventoBean.getCatsocCorrenti()) {
				filterCategorie =filterCategorie+ item.getDescrizione() + ",";
			}
		}
		
		tipoIntTreeView = new TipoInterventoManBean(listTipoInterventos,filterCategorie);
		erogazioniInterventiBean.setTipoIntTreeView(this.tipoIntTreeView);
	};

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
			logger.info("InterventiBean - Recupero lista Interventi");
			if (idCaso != null && idCaso > 0) {
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(idCaso);
				List<CsIIntervento> lsti;
				if (data == null)
					lsti = InitInterventi.loadLista(dto);
				else
					lsti = (List<CsIIntervento>) data;
				for (CsIIntervento i : lsti) {
					DatiInterventoBean dib = new DatiInterventoBean(i, idSoggetto);
					dib.setListaErogazioni(getListaSintesiErogazione(i.getCsIInterventoEsegMast()!=null ? i.getCsIInterventoEsegMast().getId() : null));
					this.lstInterventi.add(dib);
				}
			}

		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}
	
	private List<CsIInterventoEseg> getListaSintesiErogazione(Long masterId){
		if(masterId!=null && masterId>0){
			BaseDTO b = new BaseDTO();
			fillEnte(b);
			b.setObj(masterId);
			b.setObj2(false); //Non caricare i dettagli
			return interventoService.getInterventoEsegByMasterId(b);
		}
		return null;
	}

	public void inizializzaNuovaErogazione() {
		erogazioniInterventiBean.inizializzaDialogo(null);
		fglInterventoBean.setDatiErogazioniTabRendered(true);
		fglInterventoBean.setDatiInterventoTabRendered(false);
		
		tipoIntTreeView.reset();   
	}
	
	//SISO-748
	public void inizializzaNuovaErogazione(TipoInterventoManBean tipoIntTreeView, Boolean cambiaBeneficiario){
		erogazioniInterventiBean.inizializzaDialogoByTreeView(tipoIntTreeView);
		fglInterventoBean.setDatiErogazioniTabRendered(true);
		fglInterventoBean.setDatiInterventoTabRendered(false);
		fglInterventoBean.getErogazioneInterventoBean().setCambiaBeneficiarioRiferimento(cambiaBeneficiario);
	}

	//inizio evoluzione-pai
	public boolean inizializzaNuovoIntervento(TipoInterventoManBean tipoIntTreeView) { 
		boolean datiErogazioniTabRendered = isPermessoAutorizzativo();
		//fglInterventoBean.inizializzaDialog(tipoIntTreeView.getSelTipoInterventoId(), tipoIntTreeView.getSelTipoInterventoCutomId());
		fglInterventoBean.inizializzaDialog(true,datiErogazioniTabRendered, 0L, 0L, tipoIntTreeView.getSelTipoInterventoId(), tipoIntTreeView.getSelTipoInterventoCutomId(), tipoIntTreeView.getSelCatSocialeId(), readOnly, true, "Nuovo Intervento",null,true);
		 
		boolean interventoSelezionato;
		if (tipoIntTreeView.getSelTipoInterventoId() == null || tipoIntTreeView.getSelTipoInterventoId() <= 0) {
			interventoSelezionato = false; 
		} else { 
			interventoSelezionato = true; 
		}
		tipoIntTreeView.reset();
		return interventoSelezionato;
	}
	//fine evoluzione-pai
	
	
	public void inizializzaNuovoIntervento() { 
		boolean datiErogazioniTabRendered = isPermessoAutorizzativo();
		//fglInterventoBean.inizializzaDialog(tipoIntTreeView.getSelTipoInterventoId(), tipoIntTreeView.getSelTipoInterventoCutomId());
		fglInterventoBean.inizializzaDialog(true,datiErogazioniTabRendered, 0L, 0L, tipoIntTreeView.getSelTipoInterventoId(), tipoIntTreeView.getSelTipoInterventoCutomId(), tipoIntTreeView.getSelCatSocialeId(), readOnly, true, "Nuovo Intervento",null,DENTRO_FASCICOLO);
		tipoIntTreeView.reset();   
	}
	public void inizializzaNuovoFoglioAmministrativo( DatiInterventoBean intervento ) {
		boolean datiErogazioniTabRendered = isPermessoAutorizzativo();
		//fglInterventoBean.inizializzaDialog(tipoIntTreeView.getSelTipoInterventoId(), tipoIntTreeView.getSelTipoInterventoCutomId());
		fglInterventoBean.inizializzaDialog(true,datiErogazioniTabRendered, intervento.getIdIntervento(), 0L, intervento.getIdTipoIntervento(), intervento.getIdTipoIntrCustom(), intervento.getIdCatSociale(), readOnly, true, "Nuovo Foglio Amministrativo", null,DENTRO_FASCICOLO);
		tipoIntTreeView.reset();   
	}
	public void inizializzaModificaFoglioAmministrativo( CsFlgIntervento fog ) {
		boolean datiErogazioniTabRendered = isPermessoAutorizzativo();
		//fglInterventoBean.inizializzaDialog(tipoIntTreeView.getSelTipoInterventoId(), tipoIntTreeView.getSelTipoInterventoCutomId());
		
		//inizio SISO-500 
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(fog.getCsIIntervento().getId());
		CsIInterventoEsegMast csIInterventoEsegMast = interventoService.getCsIInterventoEsegMastByByInterventoId(dto);
		//Long idErogazioneMaster = csIInterventoEsegMast!=null?csIInterventoEsegMast.getId():null;
		//fine SISO-500 
		
		Long idTipoIntervento = fog.getCsIIntervento().getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento().getId();
		Long idTipoInterventoCustom = fog.getCsIIntervento().getCsIInterventoCustom()!=null ? fog.getCsIIntervento().getCsIInterventoCustom().getId() : null;
		Long idCatSoc = fog.getCsIIntervento().getCsRelSettCsocTipoInter().getCsRelSettoreCatsoc().getCsCCategoriaSociale().getId();
		
		fglInterventoBean.inizializzaDialog
		        (true,datiErogazioniTabRendered, fog.getCsIIntervento().getId(), fog.getDiarioId(), idTipoIntervento, idTipoInterventoCustom , idCatSoc,  readOnly, true, "Modifica Foglio Amministrativo", 
		        		csIInterventoEsegMast,  //SISO-500 
		        		DENTRO_FASCICOLO
					);
		tipoIntTreeView.reset();   
	}

	@Override
	public List<DatiInterventoBean> getListaInterventi() {
		/*if (lstInterventi == null || this.lstInterventi.size() == 0)
			this.loadListaInterventi(null);*/
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
}
