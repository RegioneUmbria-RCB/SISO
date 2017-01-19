package it.webred.cs.csa.web.manbean.listacasi;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsCfgItStato;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbTitoloStudio;
import it.webred.cs.jsf.bean.DatiCasoBean;
import it.webred.cs.jsf.interfaces.IListaCasi;
import it.webred.cs.jsf.manbean.IterDialogMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.utilities.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.naming.NamingException;

import org.primefaces.model.LazyDataModel;

@ManagedBean
@ViewScoped
public class ListaCasiBean extends CsUiCompBaseBean implements IListaCasi{

	private String widgetVar = "listaCasiVar";
	private LazyListaCasiModel lazyListaCasiModel;
	private DatiCasoBean selectedCaso;
	private List<SelectItem> listaStati;
	private List<SelectItem> listaOperatori;
	private List<SelectItem> listaCondLavoro;
	private List<SelectItem> listaTitStudio;
	private boolean renderTipoOperatore=false;
	
		
	@ManagedProperty( value="#{iterDialogMan}")
	private IterDialogMan iterDialogMan;

	public ListaCasiBean() {
		lazyListaCasiModel = new LazyListaCasiModel();
	}
	
	@Override
	public IterDialogMan getIterDialogMan() {
		return iterDialogMan;
	}

	public void setIterDialogMan(IterDialogMan iterDialogMan) {
		this.iterDialogMan = iterDialogMan;
	}

	@Override
	public String getWidgetVar() {
		return widgetVar;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}
	
	public DatiCasoBean getSelectedCaso() {
		return selectedCaso;
	}

	public void setSelectedCaso(DatiCasoBean selectedCaso) {
		this.selectedCaso = selectedCaso;
	}

	public  LazyDataModel<DatiCasoBean> getLazyListaCasiModel() {
		return lazyListaCasiModel;
	}

	public void setLazyListaCasiModel( LazyDataModel<DatiCasoBean> lazyListaCasiModel) {
		this.lazyListaCasiModel = (LazyListaCasiModel)lazyListaCasiModel;
	}

	@PostConstruct
	public void onPostConstruct() throws NumberFormatException, NamingException {
		doLoadListaStati();
		doLoadListaOperatori();
		doLoadListaTitoliStudio();
		doLoadListaCondLavoro();
		String sIdCaso = getRequest().getParameter("IDCASO");
		if( CommonUtils.isNotEmptyString(sIdCaso) )
			iterDialogMan.openDialog(Long.parseLong(sIdCaso));
	}
	
	@Override
	public ActionListener getCloseDialog() {
	    return new ActionListener() {
	        @Override
	        public void processAction(ActionEvent event) throws AbortProcessingException {
	        	//loadListaCasi();
	        }
	    };
	}

	@Override
	public void rowDeselect() {
		lazyListaCasiModel = new LazyListaCasiModel();
	}

	public void setListaStati(List<SelectItem> listaStati) {
		this.listaStati = listaStati;
	}
	
		
	private void doLoadListaStati(){
		listaStati = new ArrayList<SelectItem>();
		try{
			AccessTableIterStepSessionBeanRemote service = 
					(AccessTableIterStepSessionBeanRemote)ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<CsCfgItStato> lst = service.getListaIterStati(cet);
			for(CsCfgItStato cfg : lst){
				SelectItem si = new SelectItem(cfg.getId(),cfg.getNome());
		        listaStati.add(si);
			}
		}catch(Exception e){
			logger.error("Errore caricamento lista stati ITER",e);
		}
	}
	
	private void doLoadListaTitoliStudio(){
		listaTitStudio = new ArrayList<SelectItem>();
		try{
			AccessTableConfigurazioneSessionBeanRemote confService = 
					(AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<CsTbTitoloStudio> lst = confService.getTitoliStudio(cet);
			for (CsTbTitoloStudio obj : lst) 
				listaTitStudio.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				
		}catch(Exception e){
			logger.error("Errore caricamento lista TITOLI STUDIO",e);
		}
	}
	
	private void doLoadListaCondLavoro(){
		listaCondLavoro = new ArrayList<SelectItem>();
		try{
			AccessTableConfigurazioneSessionBeanRemote confService = 
					(AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			TreeMap<String, List<CsTbCondLavoro>> tree = confService.getMappaCondLavoro(cet);
			for(String str : tree.keySet()){
				List<CsTbCondLavoro> lst = tree.get(str);
				if (lst != null && !lst.isEmpty()) {
					String labelGroup = lst.get(0).getCsTbIngMercato().getDescrizione();
					SelectItemGroup gr = new SelectItemGroup(labelGroup);
					List<SelectItem> siList = new ArrayList<SelectItem>();
					for (CsTbCondLavoro obj : lst) {
						SelectItem si = new SelectItem(obj.getId(), obj.getDescrizione());
						if(labelGroup==null || labelGroup.trim().isEmpty())
							siList.add(si);
						else
							listaCondLavoro.add(si);
					}
					if(labelGroup==null || labelGroup.trim().isEmpty()){
						gr.setSelectItems(siList.toArray(new SelectItem[siList.size()]));
						listaCondLavoro.add(gr);
					}
				}		
			}		
		}catch(Exception e){
			logger.error("Errore caricamento lista CONDIZIONE LAVORO ",e);
		}
	}
	
	private void doLoadListaOperatori(){
		listaOperatori = new ArrayList<SelectItem>();
		CsOOperatore operatore = getCurrentOpSettore().getCsOOperatore();
		Long settoreId = getCurrentOpSettore().getCsOSettore().getId();
		if(!CsUiCompBaseBean.checkPermesso(DataModelCostanti.PermessiCaso.VISUALIZZAZIONE_CASI_SETTORE)){
			listaOperatori.add(new SelectItem(operatore.getId(),this.getDenominazioneOperatore(operatore)));
		}else{
			try{
				if (settoreId != 0L) {
					AccessTableOperatoreSessionBeanRemote operatoreSessionBean = (AccessTableOperatoreSessionBeanRemote) 
							ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
			
					OperatoreDTO opDto = new OperatoreDTO();
					fillEnte(opDto);
					opDto.setIdSettore(settoreId);
					for (CsOOperatoreSettore it : operatoreSessionBean.findOperatoreSettoreBySettore(opDto) ) {
					    CsOOperatore op = it.getCsOOperatore();
					    //OpSettoreID-Cognome e Nome Operatore
					    String cognome = op.getCsOOperatoreAnagrafica().getCognome();
					    String nome = op.getCsOOperatoreAnagrafica().getNome();
					    String descrizione = op.getCsOOperatoreAnagrafica().getUsername();
					    if(cognome!=null && nome!=null)
					    	descrizione = cognome+" "+nome;
					    SelectItem si = new SelectItem(op.getId(), descrizione);
						listaOperatori.add(si);
					}
				}
			}catch(Exception e){
				logger.error("Errore caricamento lista operatori Settore",e);
			}
	  }
	}
	
	@Override
	public List<SelectItem> getListaStati() {
		if(listaStati.isEmpty())
			doLoadListaStati();
		return listaStati;
	}
	
	@Override
	public List<SelectItem> getListaOperatori(){
		if(listaOperatori.isEmpty())
			doLoadListaOperatori();
		return listaOperatori;	
	}
	
	@Override
	public List<SelectItem> getListaTitStudio(){
		if(listaTitStudio.isEmpty())
			doLoadListaTitoliStudio();
		return listaTitStudio;	
	}
	
	@Override
	public List<SelectItem> getListaCondLavoro(){
		if(listaCondLavoro.isEmpty())
			doLoadListaCondLavoro();
		return listaCondLavoro;	
	}
	
	public boolean getSelNR() {
		Boolean nr = (Boolean)getSession().getAttribute(filtroCasi_NON_RESPONSABILE);
		if(nr!=null)
			return nr;
		else
			return false;
	}
	
	public void setSelNR(boolean nr){
		getSession().setAttribute(filtroCasi_NON_RESPONSABILE, nr);
	}

	public String getSelStato() {
		return (String)getSession().getAttribute(filtroCasi_STATO);
	}

	public void setSelStato(String selStato) {
		getSession().setAttribute(filtroCasi_STATO, selStato);
	}
	
	public Long getSelTitStudio() {
		return (Long)getSession().getAttribute(filtroCasi_STUDIO);
	}

	public void setSelTitStudio(Long sel) {
		getSession().setAttribute(filtroCasi_STUDIO, sel);
	}
	
	public Long getSelCondLavoro() {
		return (Long)getSession().getAttribute(filtroCasi_LAVORO);
	}

	public void setSelCondLavoro(Long sel) {
		getSession().setAttribute(filtroCasi_LAVORO, sel);
	}
	
	public String getSelTutela() {
		return (String)getSession().getAttribute(filtroCasi_TUTELA);
	}

	public void setSelTutela(String sel) {
		getSession().setAttribute(filtroCasi_TUTELA, sel);
	}

	public String getSelOperatore() {
		return (String)getSession().getAttribute(filtroCasi_OPERATORE);
	}

	public void setSelOperatore(String selOperatore) {
		getSession().setAttribute(filtroCasi_OPERATORE, selOperatore);
		if(selOperatore!=null && !selOperatore.isEmpty())
			this.renderTipoOperatore=true;
		else{
			this.renderTipoOperatore=false;
			this.setSelNR(false);
		}
	}
	
	@Override
	public void clearFilters(){
		this.setSelOperatore(null);
		this.setSelStato(null);
		this.setSelCondLavoro(null);
		this.setSelTitStudio(null);
		this.setSelTutela(null);	
	}

	@Override
	public boolean isRenderTipoOperatore() {
		return renderTipoOperatore;
	}

	
	
}
