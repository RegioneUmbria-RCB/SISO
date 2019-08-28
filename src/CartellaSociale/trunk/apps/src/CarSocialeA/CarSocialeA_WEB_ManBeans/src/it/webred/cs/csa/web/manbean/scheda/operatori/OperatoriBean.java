package it.webred.cs.csa.web.manbean.scheda.operatori;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.web.manbean.scheda.SchedaBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsACasoOpeTipoOpe2;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsTbTipoOperatore;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IOperatori;
import it.webred.cs.jsf.manbean.DatiValGestioneMan;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.dto.utility.KeyValuePairBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class OperatoriBean extends DatiValGestioneMan  implements IOperatori {

	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
	protected AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");	
	protected AccessTableOperatoreSessionBeanRemote operatoreService = (AccessTableOperatoreSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
	protected AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
	
	private final String EREDITA = "EREDITA";
	private final String ABILITA = "ABILITA";
	private final String DISABILITA = "DISABILITA";
	
	private Long idTipoOperatore;
	private Long idEnte;
	private Long idSettore;
	protected CsASoggettoLAZY soggetto;
	protected Long soggettoId;
	private String insOperatore;
	private String contattiOperatore;
	private String gestisciFascicolo;
	private boolean disableFlagFasc= true;
	
	private List<SelectItem> lstOrganizzazioni;
	private List<SelectItem> lstSettori;
	private List<SelectItem> lstGestFascicolo; 
	
	public String getInsOperatore() {
		return insOperatore;
	}

	public void setInsOperatore(String insOperatore) {
		this.insOperatore = insOperatore;
	}

	public void initialize(Long sId) {
		
		logger.debug("*** INIZIO chiamata OperatoriBean.initialize");

		insOperatore=null;
		contattiOperatore = null;
		gestisciFascicolo = this.EREDITA;
		disableFlagFasc = true;
		soggettoId = sId;

		
		if(soggettoId != null) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(soggettoId);
			
			soggetto = soggettoService.getSoggettoById(dto);
		
			List<CsTbTipoOperatore> lst = getTipiOperatore();
			if(lst!=null && lst.size()>0)
				idTipoOperatore = lst.get(0).getId();
			
			dto.setObj(soggetto.getCsACaso().getId());
			
			List<CsACasoOpeTipoOpe> lstCasoOper = casoService.getListaOperatoreTipoOpByCasoId(dto);
			
			List<CsACasoOpeTipoOpe2> lstCasoOper2 = casoService.getListaOperatoreTipoOp2ByCasoId(dto);
			
			lstComponents = new ArrayList<ValiditaCompBaseBean>();
			
			for(CsACasoOpeTipoOpe co : lstCasoOper){
				OperatoriComp b = new OperatoriComp();
				b.setDataFine(co.getDataFineApp());
				b.setDataInizio(co.getDataInizioApp());
				
				CsOOperatore oper = co.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOOperatore();
				
				b.setDescrizione(getDenominazioneOperatore(oper));
				b.setId(co.getCsOOperatoreTipoOperatore().getId());
				b.setTipo(co.getCsOOperatoreTipoOperatore().getCsTbTipoOperatore().getDescrizione());
				b.setSettore(co.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOSettore().getNome());
				b.setResponsabile(co.getFlagResponsabile()!=null ? co.getFlagResponsabile() : false);
				b.setGestisciFascicolo(encodeFlagGestione(co.getFlagGestioneFascicolo()));
				b.setOperTipo2(false);
				lstComponents.add(b);
				
			}
			for(CsACasoOpeTipoOpe2 co2 : lstCasoOper2){
				OperatoriComp b = new OperatoriComp();
				b.setDataFine(co2.getDataFineApp());
				b.setDataInizio(co2.getDataInizioApp());
				b.setDescrizione(co2.getNomeOperatore());
				b.setContatti(co2.getContatti());
				b.setTipo(co2.getCsTbTipoOperatore().getDescrizione());
				b.setOperTipo2(true);
				lstComponents.add(b);
			}
			
		}
		
		//Imposto al valore corrente
		CsOOperatoreSettore opSettore = getCurrentOpSettore();
		if(opSettore != null && opSettore.getCsOSettore().getCsOOrganizzazione().getId() != null){
			this.idEnte = opSettore.getCsOSettore().getCsOOrganizzazione().getId();
			this.onChangeOrganizzazione();
		}
		  
		logger.debug("*** FINE chiamata OperatoriBean.initialize");

	}
	
	private Boolean decodeFlagGestione(String val){
		if(val.equalsIgnoreCase(this.ABILITA)) return true;
		else if(val.equalsIgnoreCase(this.DISABILITA)) return false;
		else return null;
	}
	
	private String encodeFlagGestione(Boolean val){
		if(val!=null && val.booleanValue()) return this.ABILITA;
		else if(val!=null && !val.booleanValue()) return this.DISABILITA;
		else return this.EREDITA;
	}
	
	@Override
	protected String getDescrizioneTipo(){
		List<CsTbTipoOperatore> lst = getTipiOperatore();
		if(idTipoOperatore!=null){
			int i=0;
			boolean trovato = false;
			while(i<lst.size() && !trovato){
				CsTbTipoOperatore to = lst.get(i);
				if(to.getId()==this.idTipoOperatore.longValue())
				   return to.getDescrizione();
				
				i++;
			}
		}
		return "";
	}
	
	private long searchTipoId(String descrizione){
		int i=0;
		long tipoId=0;
		boolean trovato = false;
		List<CsTbTipoOperatore> lst = getTipiOperatore();
		while(i<lst.size() && !trovato){
			CsTbTipoOperatore to = lst.get(i);
			if(to.getDescrizione().equals(descrizione))
			   return to.getId();
			
			i++;
		}
		return tipoId;
	}
	
	
	
	private List<CsTbTipoOperatore> getTipiOperatore(){
		List<CsTbTipoOperatore> beanLst = new ArrayList<CsTbTipoOperatore>();
		try {
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			beanLst = confService.getTipiOperatore(cet);
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		return beanLst;
	}
	
	@Override
	public List<SelectItem> getLstTipoOperatore() {
		ArrayList<SelectItem> lst = new ArrayList<SelectItem>();
		try {
			List<CsTbTipoOperatore> beanLst = this.getTipiOperatore();
			if (beanLst != null) {
				for (CsTbTipoOperatore s : beanLst) 
					lst.add(new SelectItem(s.getId(), s.getDescrizione()));
			}
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		return lst;
	}
	
	@Override
	public List<SelectItem> getLstOrganizzazioni() {
		if(lstOrganizzazioni==null || lstOrganizzazioni.isEmpty()){
			lstOrganizzazioni = new ArrayList<SelectItem>();
			try {
				CeTBaseObject dto = new CeTBaseObject();
				fillEnte(dto);
				List<CsOOrganizzazione> beanLst = confService.getOrganizzazioni(dto);
				if (beanLst != null) {
					for (CsOOrganizzazione s : beanLst){
						SelectItem si = new SelectItem(s.getId(), s.getNome());
						si.setDisabled(!s.getAbilitato().equals("1"));
						lstOrganizzazioni.add(si);
					}
				}
			} catch (Exception e) {
				addErrorFromProperties("caricamento.error");
				logger.error(e.getMessage(),e);
			}
		}
		return lstOrganizzazioni;
	}
	
	@Override
	public List<SelectItem> getLstSettori() {
		return this.lstSettori;
	}
	
	

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<KeyValuePairBean> getLstItems() {
		Date dataRif = new Date();
		lstItems = new ArrayList<KeyValuePairBean>();
		try {
			//filtro per tipo e settore
			BaseDTO bo = new BaseDTO();
			bo.setObj(this.idTipoOperatore);
			bo.setObj2(this.idSettore);
			fillEnte(bo);
			List<CsOOperatoreTipoOperatore> lstOper = operatoreService.getOperatoriByTipoIdSettore(bo);
			if (lstOper != null) {
				for (CsOOperatoreTipoOperatore obj : lstOper) {
					CsOOperatoreSettore operSett = obj.getCsOOperatoreSettore();
					if(operSett!=null){
						boolean isAttivo = !dataRif.after(obj.getDataFineApp()) && !dataRif.before(obj.getDataInizioApp());
						//boolean altroSettore = !operSett.getCsOSettore().getId().equals(this.idSettore);
						if(isAttivo){
							CsOOperatoreAnagrafica operatoreAnagrafica  = operSett.getCsOOperatore().getCsOOperatoreAnagrafica();
							String descrizione = operSett.getCsOOperatore().getUsername();
								if(operatoreAnagrafica!=null && operatoreAnagrafica.getCognome()!=null && operatoreAnagrafica.getNome()!=null)
									descrizione = operatoreAnagrafica.getCognome()+" "+operatoreAnagrafica.getNome();
							lstItems.add(new KeyValuePairBean(obj.getId(), descrizione));
						}
					}
				}
			}		
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
		return lstItems;
	}
	
	public void resetInsOperatore(){
		if(this.itemSelezionato==null){
			this.gestisciFascicolo=null;
			this.disableFlagFasc = true;
		}else{
			this.insOperatore="";
			this.contattiOperatore="";
			this.disableFlagFasc = false;
		}
	}
	
	@Override
	public void aggiungiSelezionato() {
		boolean inserisciSelezione = itemSelezionato != null && !"".equals(itemSelezionato);
		boolean inserisciTesto = insOperatore!=null && !insOperatore.equals("");
		
		try{
		  if(inserisciSelezione && inserisciTesto){
			addWarning("Inserisci o Seleziona un Operatore alla volta", "");
			return;
		  }	 
			 
		if(inserisciSelezione){

			String[] str = itemSelezionato.split("\\|");
			OperatoriComp comp = new OperatoriComp();
			comp.setId(new Long(str[0]));
			comp.setTipo(this.getDescrizioneTipo());
			comp.setDescrizione(str[1]);
			comp.setDataInizio(new Date());
			comp.setDataFine(DataModelCostanti.END_DATE);
			comp.setOperTipo2(false);
			comp.setGestisciFascicolo(gestisciFascicolo);
			if(checkExists(comp))
				addWarning("L'elemento selezionato è già presente", "");
			else lstComponents.add(comp);
			
		 }else if(inserisciTesto){
			
			OperatoriComp comp = new OperatoriComp();
			comp.setTipo(this.getDescrizioneTipo());
			comp.setDescrizione(insOperatore);
			comp.setContatti(contattiOperatore);
			comp.setDataInizio(new Date());
			comp.setDataFine(DataModelCostanti.END_DATE);
			comp.setOperTipo2(true);
			lstComponents.add(comp);
			
		 }else{
			addWarning("Inserisci o Seleziona un Operatore", "");
			return;
		}
		  
		 	
		} catch (Exception e) {
			addError("Errore durante l'inserimento dell'elemento selezionato", null);
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public boolean salva() {
		boolean salvato = true;
		itemSelezionato = null;
		List<ValiditaCompBaseBean> lstTempActive = getActiveList();
		try {
			
			lstComponentsActive = lstTempActive;
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);	
			
			if(soggetto != null) {
				
				dto.setObj(soggetto.getCsACaso().getId());
				casoService.eliminaOperatoreTipoOpByCasoId(dto);
				casoService.eliminaOperatoreTipoOp2ByCasoId(dto);
				
				for(ValiditaCompBaseBean baseComp: getLstComponents()) {
					OperatoriComp comp = (OperatoriComp)baseComp;
					if(comp.isOperTipo2()){
						CsACasoOpeTipoOpe2 cs = new CsACasoOpeTipoOpe2();

						cs.setCsACaso(soggetto.getCsACaso());
						cs.setNomeOperatore(comp.getDescrizione());
						cs.setContatti(comp.getContatti());
						cs.setDataInizioApp(comp.getDataInizio());
						cs.setDataInizioSys(new Date());
						cs.setDataFineApp(comp.getDataFine());
						
						CsTbTipoOperatore to = new CsTbTipoOperatore();
						to.setId(searchTipoId(comp.getTipo()));
						cs.setCsTbTipoOperatore(to);
						
						dto.setObj(cs);
						casoService.salvaOperatoreCaso2(dto);
						
					}else{
						
						CsACasoOpeTipoOpe cs = new CsACasoOpeTipoOpe();
					
						cs.setCsACaso(soggetto.getCsACaso());
						cs.setCasoId (soggetto.getCsACaso().getId());
						cs.setOperatoreTipoOperatoreId(comp.getId());
						cs.setDataInizioApp(comp.getDataInizio());
						cs.setDataFineApp(comp.getDataFine());
						cs.setDataInizioSys(new Date());
						cs.setFlagResponsabile(comp.getResponsabile());
						cs.setFlagGestioneFascicolo(decodeFlagGestione(comp.getGestisciFascicolo()));
						
						dto.setObj(comp.getId());
						cs.setCsOOperatoreTipoOperatore(operatoreService.getOperatoreTipoOpById(dto));
						
						dto.setObj(cs);
						casoService.salvaOperatoreCaso(dto);
					}	
				}
				RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			}
			
			
			//addInfoFromProperties("salva.ok");
		
		} catch (Exception e) {
			salvato = false;
			addErrorFromProperties("salva.errorO");
			logger.error(e.getMessage(),e);
		}	
		return salvato;
	}
	

	public Long getIdTipoOperatore() {
		return idTipoOperatore;
	}

	public void setIdTipoOperatore(Long idTipoOperatore) {
		this.idTipoOperatore = idTipoOperatore;
	}

	public Long getIdSettore() {
		return idSettore;
	}

	public void setIdSettore(Long idSettore) {
		this.idSettore = idSettore;
	}

	public Long getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Long idEnte) {
		this.idEnte = idEnte;
	}
	
	@Override
	public void onChangeOrganizzazione(){
		this.idSettore=null;
		this.soggettoId=null;
		this.loadListaSettori();
		this.resetOperatore();
	}
	
	@Override
	public void resetOperatore(){
		this.soggettoId=null;
		this.itemSelezionato=null;
	}
	
	private void loadListaSettori(){
		lstSettori = new ArrayList<SelectItem>();
		try {
			OperatoreDTO dto = new OperatoreDTO();
			fillEnte(dto);
			if(this.idEnte!=null){
				dto.setIdOrganizzazione(idEnte);
				List<CsOSettoreBASIC> beanLst = operatoreService.findSettoreBASICByOrganizzazione(dto);
				if (beanLst != null) {
					for (CsOSettoreBASIC s : beanLst) 
						lstSettori.add(new SelectItem(s.getId(), s.getNome()));
					
					if(!lstSettori.isEmpty())
						idSettore=(Long)lstSettori.get(0).getValue();
					else
						idSettore=null;
				}
				
				
			}
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public List<KeyValuePairBean> getDettaglioSelezione(Long id) {
		return null;
	}

	public String getContattiOperatore() {
		return contattiOperatore;
	}

	public void setContattiOperatore(String contattiOperatore) {
		this.contattiOperatore = contattiOperatore;
	}

	public String getGestisciFascicolo() {
		return gestisciFascicolo;
	}

	public void setGestisciFascicolo(String gestisciFascicolo) {
		this.gestisciFascicolo = gestisciFascicolo;
	}

	public boolean isDisabilitaForm(){
		SchedaBean bean = (SchedaBean)getBeanReference("schedaBean");
		return !bean.isAttivaSalvataggio();
	}

	public boolean isDisableFlagFasc() {
		return disableFlagFasc;
	}

	public void setDisableFlagFasc(boolean disableFlagFasc) {
		this.disableFlagFasc = disableFlagFasc;
	}

	public List<SelectItem> getLstGestFascicolo() {
		if(lstGestFascicolo==null){
			lstGestFascicolo = new ArrayList<SelectItem>();
			lstGestFascicolo.add(new SelectItem(this.ABILITA, "Si"));
			lstGestFascicolo.add(new SelectItem(this.DISABILITA, "No"));
			lstGestFascicolo.add(new SelectItem(this.EREDITA, "Eredita permessi profilo"));
		}
		return lstGestFascicolo;
	}

	public void setLstGestFascicolo(List<SelectItem> lstGestFascicolo) {
		this.lstGestFascicolo = lstGestFascicolo;
	}
	
}
