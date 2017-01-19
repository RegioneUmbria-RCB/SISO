package it.webred.cs.csa.web.manbean.fascicolo.interventi;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.csa.web.manbean.fascicolo.FglInterventoBean;
import it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi.ErogazioniInterventiBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView.TipoInterventoManBean;
import it.webred.cs.data.model.CsCCategoriaSocialeBASIC;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.jsf.Costanti.TipoPermessoErogazioneInterventi;
import it.webred.cs.jsf.bean.DatiInterventoBean;
import it.webred.cs.jsf.interfaces.IDatiInterventi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

public class InterventiBean extends FascicoloCompBaseBean implements IDatiInterventi {

	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
	
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
	protected void initializeData() {
		erogazioniInterventiBean = new ErogazioniInterventiBean();
		erogazioniInterventiBean.SetFromFascicolo(csASoggetto);

		fglInterventoBean.initialize(csASoggetto, catsocCorrenti);
		fglInterventoBean.initializeData();
		loadListaTipiIntervento();
		refreshListaInterventi();
//evoluzione-pai		String filterCategorie = "";
		if (fglInterventoBean.getCatsocCorrenti() != null) {
			for (CsCCategoriaSocialeBASIC item : fglInterventoBean.getCatsocCorrenti()) {
				filterCategorie =filterCategorie+ item.getDescrizione() + ",";
			}
		}
		
		tipoIntTreeView = new TipoInterventoManBean(listTipoInterventos,filterCategorie);
		erogazioniInterventiBean.setTipoIntTreeView(this.tipoIntTreeView);
	};

	public void refreshListaInterventi() {
		loadListaInterventi();
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

	protected void loadListaInterventi() {
		lstInterventi = new ArrayList<DatiInterventoBean>();
		try {
			logger.info("InterventiBean - Recupero lista Interventi");
			if (idCaso != null && idCaso > 0) {
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(idCaso);
				List<CsIIntervento> lsti = interventoService.getListaInterventiByCaso(dto);
				for (CsIIntervento i : lsti) {
					DatiInterventoBean dib = new DatiInterventoBean(i, idSoggetto);
					this.lstInterventi.add(dib);
				}
			}

		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	public void inizializzaNuovaErogazione() {
		erogazioniInterventiBean.inizializzaDialogo(null);
		fglInterventoBean.setDatiErogazioniTabRendered(true);
		fglInterventoBean.setDatiInterventoTabRendered(false);
		
		tipoIntTreeView.reset();   
	}
	

	//inizio evoluzione-pai
	public boolean inizializzaNuovoIntervento(TipoInterventoManBean tipoIntTreeView) { 
		boolean datiErogazioniTabRendered = TipoPermessoErogazioneInterventi.PERMESSO_AUTORIZZATIVO.equals(getPermessoErogazioneInterventi());
		//fglInterventoBean.inizializzaDialog(tipoIntTreeView.getSelTipoInterventoId(), tipoIntTreeView.getSelTipoInterventoCutomId());
		fglInterventoBean.inizializzaDialog(true,datiErogazioniTabRendered, 0L, 0L, tipoIntTreeView.getSelTipoInterventoId(), tipoIntTreeView.getSelTipoInterventoCutomId(), tipoIntTreeView.getSelCatSocialeId(), readOnly, true, "Nuovo Intervento",null);
		 
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
		boolean datiErogazioniTabRendered = TipoPermessoErogazioneInterventi.PERMESSO_AUTORIZZATIVO.equals(getPermessoErogazioneInterventi());
		//fglInterventoBean.inizializzaDialog(tipoIntTreeView.getSelTipoInterventoId(), tipoIntTreeView.getSelTipoInterventoCutomId());
		fglInterventoBean.inizializzaDialog(true,datiErogazioniTabRendered, 0L, 0L, tipoIntTreeView.getSelTipoInterventoId(), tipoIntTreeView.getSelTipoInterventoCutomId(), tipoIntTreeView.getSelCatSocialeId(), readOnly, true, "Nuovo Intervento",null);
		tipoIntTreeView.reset();   
	}
	public void inizializzaNuovoFoglioAmministrativo( DatiInterventoBean intervento ) {
		boolean datiErogazioniTabRendered = TipoPermessoErogazioneInterventi.PERMESSO_AUTORIZZATIVO.equals(getPermessoErogazioneInterventi());
		//fglInterventoBean.inizializzaDialog(tipoIntTreeView.getSelTipoInterventoId(), tipoIntTreeView.getSelTipoInterventoCutomId());
		fglInterventoBean.inizializzaDialog(true,datiErogazioniTabRendered, intervento.getIdIntervento(), 0L, null, null, null, readOnly, true, "Nuovo Foglio Amministrativo", null);
		tipoIntTreeView.reset();   
	}
	public void inizializzaModificaFoglioAmministrativo( CsFlgIntervento fog ) {
		boolean datiErogazioniTabRendered = TipoPermessoErogazioneInterventi.PERMESSO_AUTORIZZATIVO.equals(getPermessoErogazioneInterventi());
		//fglInterventoBean.inizializzaDialog(tipoIntTreeView.getSelTipoInterventoId(), tipoIntTreeView.getSelTipoInterventoCutomId());
		
		//inizio SISO-500 
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(fog.getCsIIntervento().getId());
		CsIInterventoEsegMast csIInterventoEsegMast = interventoService.getCsIInterventoEsegMastByByInterventoId(dto);
		Long idErogazioneMaster = csIInterventoEsegMast!=null?csIInterventoEsegMast.getId():null;
		//fine SISO-500 
		
		fglInterventoBean.inizializzaDialog(true,datiErogazioniTabRendered, fog.getCsIIntervento().getId(), fog.getDiarioId(), null, null, null, readOnly, true, "Modifica Foglio Amministrativo", 
					idErogazioneMaster  //SISO-500 
					);
		tipoIntTreeView.reset();   
	}

	@Override
	public List<DatiInterventoBean> getListaInterventi() {
		if (lstInterventi == null || this.lstInterventi.size() == 0)
			this.loadListaInterventi();
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
