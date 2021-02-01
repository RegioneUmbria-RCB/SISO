package it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventi.DatiProgettoBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.Costanti.TipoPermessoErogazioneInterventi;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


public class LazyListaErogazioniModel extends LazyDataModel<ErogInterventoRowBean> {
     
	private int tipoFiltroInterventiSelezionato;
	private Date dataUltimaErogazione;
	private Long[] selectedTipoIntervento;
	private Long[] selectedTipoInterventoCustom;
	private String[] selectedTipoBeneficiario;
	private String[] selectedTipoInterventoInps; //SISO-1162
	//SISO-381
	String sortField;
	SortOrder sortOrder; 
	Map filters;
	
	
	public String[] getSelectedTipoBeneficiario() {
		return selectedTipoBeneficiario;
	}

	public void setSelectedTipoBeneficiario(String[] selectedTipoBeneficiario) {
		this.selectedTipoBeneficiario = selectedTipoBeneficiario;
	}

	private SoggettoErogazioneBean soggettoErogazioneSelezionato;
	
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat ddMMyyyy= new SimpleDateFormat("dd/MM/yyyy");
	
	public LazyListaErogazioniModel(){
	}
	
	public void Setup(int preselected, SoggettoErogazioneBean soggetto){
		tipoFiltroInterventiSelezionato = preselected;
		soggettoErogazioneSelezionato = soggetto;
	}

	@Override
    public ErogInterventoRowBean getRowData(String rowKey) { 
		//TODO
        return null;
    }
 
    @Override
    public Object getRowKey(ErogInterventoRowBean erogazione) {
        return erogazione.getIdRow();
    }
 
    @Override
    public List<ErogInterventoRowBean> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
    	
    	//SISO-381
    	this.sortField = sortField;
    	this.sortOrder = sortOrder;
    	this.filters = filters;
    	
        List<ErogInterventoRowBean> listaInterventi = new LinkedList<ErogInterventoRowBean>();
 
    	CsOOperatoreSettore opSettore = CsUiCompBaseBean.getCurrentOpSettore();
		ErogazioniSearchCriteria bDto = new ErogazioniSearchCriteria();
		CsUiCompBaseBean.fillEnte(bDto);
		bDto.setSettoreId(opSettore.getCsOSettore().getId());
		bDto.setOrganizzazioneId(opSettore.getCsOSettore().getCsOOrganizzazione().getId());
		bDto.setFirst(first);
		bDto.setPageSize(pageSize);
		bDto.setPermessoAutorizzativo(CsUiCompBaseBean.isPermessoAutorizzativo());
		bDto.setSearchConRichiesta(tipoFiltroInterventiSelezionato == DataModelCostanti.ListaErogazioni.CON_RICHIESTA);
		bDto.setSearchErogatiNoIntervento(tipoFiltroInterventiSelezionato == DataModelCostanti.ListaErogazioni.SENZA_RICHIESTA);
		
		List<ErogInterventoRowBean> listaInterventiAll = new LinkedList<ErogInterventoRowBean>();
		
		elaboraCriteriFiltro(filters, bDto);
		
		if( soggettoErogazioneSelezionato != null)
			bDto.setCodiceFiscale( soggettoErogazioneSelezionato.getCodiceFiscale() );
		
		if(this.dataUltimaErogazione!=null)
			bDto.setDataErogazione(ddMMyyyy.format(this.dataUltimaErogazione));
			
		if(this.selectedTipoIntervento!=null && this.selectedTipoIntervento.length>0)
			bDto.setLstTipoIntervento(this.selectedTipoIntervento);
		if(this.selectedTipoInterventoCustom!=null && this.selectedTipoInterventoCustom.length>0)
			bDto.setLstTipoInterventoCustom(this.selectedTipoInterventoCustom);
		//SISO-1162
		if(this.selectedTipoInterventoInps!=null && this.selectedTipoInterventoInps.length>0)
			bDto.setLstTipoInterventoInps(this.selectedTipoInterventoInps);
		if(this.selectedTipoBeneficiario!=null && this.selectedTipoBeneficiario.length>0)
			bDto.setLstTipoBeneficiario(this.selectedTipoBeneficiario);
				
		try {
				AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
				/**Recupero gli interventi eseguiti, privi di foglio ammnistrativo che abbiano una delle seguenti caratteristiche:
				1. settore dell'operatore che l'ha inserito = corrente
				2. organizzazione dell'erogazione = corrente
				3. organizzazione del caso (recuperabile mediante CF) = corrente
				*/
				
				List<ErogazioneMasterDTO> lst = interventoService.searchListaErogInterventi(bDto);
				for (ErogazioneMasterDTO datiAggregatiErogazioneDTO : lst) {
					ErogInterventoRowBean row = new ErogInterventoRowBean(datiAggregatiErogazioneDTO);
					listaInterventiAll.add(row);
				}
			
			listaInterventi=listaInterventiAll;
		   
	        //rowCount
			Integer dataSize = interventoService.countListaErogInterventiBySettore(bDto); //commentato mk_osmosit
			this.setRowCount(dataSize);
		
		} catch (Exception e) {
			CsUiCompBaseBean.addErrorFromProperties("caricamento.error");
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
		
		return listaInterventi;
    }
    
    protected void elaboraCriteriFiltro( Map filters, ErogazioniSearchCriteria searchCriteria){
    	String denominazione = (String) filters.get("denominazione");
		String statoErogazione = (String) filters.get("statoErogazione");
		String lineaFinanziamento = (String)filters.get("lineaFinanziamento");
		String catSociale = (String)filters.get("descCategoriaSociale");
		
		searchCriteria.setDenominazione(denominazione!=null ? denominazione : null);
		searchCriteria.setStatoErogazione(statoErogazione!=null ? statoErogazione : null);
		searchCriteria.setLineaFinanziamento(lineaFinanziamento!=null ? lineaFinanziamento : null);
		searchCriteria.setDescCatSociale(catSociale);
    }

	public int getTipoFiltroInterventiSelezionato() {
		return tipoFiltroInterventiSelezionato;
	}

	public void setTipoFiltroInterventiSelezionato(
			int tipoFiltroInterventiSelezionato) {
		this.tipoFiltroInterventiSelezionato = tipoFiltroInterventiSelezionato;
	}

	public Long[] getSelectedTipoIntervento() {
		return selectedTipoIntervento;
	}

	public void setSelectedTipoIntervento(Long[] selectedTipoIntervento) {
		this.selectedTipoIntervento = selectedTipoIntervento;
	}

	public Long[] getSelectedTipoInterventoCustom() {
		return selectedTipoInterventoCustom;
	}

	public void setSelectedTipoInterventoCustom(Long[] selectedTipoInterventoCustom) {
		this.selectedTipoInterventoCustom = selectedTipoInterventoCustom;
	}

	public Date getDataUltimaErogazione() {
		return dataUltimaErogazione;
	}

	public void setDataUltimaErogazione(Date dataUltimaErogazione) {
		this.dataUltimaErogazione = dataUltimaErogazione;
	}
	//SISO-1162
	public String[] getSelectedTipoInterventoInps() {
		return selectedTipoInterventoInps;
	}

	public void setSelectedTipoInterventoInps(String[] selectedTipoInterventoInps) {
		this.selectedTipoInterventoInps = selectedTipoInterventoInps;
	}

	
}
