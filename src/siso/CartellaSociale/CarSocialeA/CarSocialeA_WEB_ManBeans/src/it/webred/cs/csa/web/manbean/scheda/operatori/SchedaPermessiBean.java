package it.webred.cs.csa.web.manbean.scheda.operatori;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.web.manbean.scheda.SchedaBean;
import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaCompUtils;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoAccessoFascicolo;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsACasoOpeTipoOpe2;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbTipoOperatore;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.ISchedaPermessi;
import it.webred.cs.jsf.manbean.DatiValGestioneMan;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.dto.utility.KeyValuePairBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class SchedaPermessiBean extends DatiValGestioneMan  implements ISchedaPermessi {

	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
	protected AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
	
	private final String EREDITA = "EREDITA";
	private final String ABILITA = "ABILITA";
	private final String DISABILITA = "DISABILITA";
    //SISO-812
	private final String OPERATORI="OPERATORI";
	private final String SECONDOLIVELLO="SECONDOLIVELLO";
	private final String ORGANIZZAZIONE="ORGANIZZAZIONE"; 
	
	private Long idTipoOperatore;
	private Long idEnte;
	private Long idSettore;
	private String descrizioneTipoOperatore; //SISO-812
	protected Long soggettoId;
	protected Long casoId;
	private String insOperatore;
	private String contattiOperatore;
	private String gestisciFascicolo;
	private String gestisciPermessi; //SISO-812
	private boolean disableFlagFasc= true;
	//private boolean disableFlagFasc= false; //SISO-812
	private List<SelectItem> lstOrganizzazioni;
	private List<SelectItem> lstSettori;
	private List<SelectItem> lstGestFascicolo; 
	private List<SelectItem> lstGestPermessi; 
	private boolean renderedFiltriOperatore;
	private boolean renderedFiltriSecondoLivello;
	private boolean renderedFiltriOrganizzazione;
	private boolean nascondiInformazioni=true;
	private String nascondiInformazioniHelp="Specifica se i contenuti del fascicolo inseriti dal settore assegnante devono essere nascosti (consigliato) al settore assegnatario";
	//SISO-812
	
	public String getInsOperatore() {
		return insOperatore;
	}

	public void setInsOperatore(String insOperatore) {
		this.insOperatore = insOperatore;
	}

	public void initialize(Long sId, Long cId) {
		
		logger.debug("*** INIZIO chiamata SchedaPermessiBean.initialize");

		insOperatore=null;
		contattiOperatore = null;
		gestisciFascicolo = this.EREDITA;
		disableFlagFasc = true;
        soggettoId = sId;
        casoId = cId;
		
		if(soggettoId != null) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(soggettoId);
			
			//soggetto = soggettoService.getSoggettoById(dto);
		
			List<CsTbTipoOperatore> lst = getTipiOperatore();
			if(lst!=null && lst.size()>0){
				idTipoOperatore = lst.get(0).getId();
				descrizioneTipoOperatore=lst.get(0).getDescrizione();
			}
				
			
			dto.setObj(casoId);
			
			List<CsACasoOpeTipoOpe> lstCasoOper = casoService.getListaOperatoreTipoOpByCasoId(dto);
			
			List<CsACasoOpeTipoOpe2> lstCasoOper2 = casoService.getListaOperatoreTipoOp2ByCasoId(dto);
			
			List<CsACasoAccessoFascicolo> lstSecondoLivello= casoService.getListaAccessoFascicoloByCasoId(dto);
			
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
				b.setTipologia(this.OPERATORI);//SISO-812
				b.setDataInserimento(co.getDtIns());//SISO-1060
				b.setUsrInserimento(super.getCognomeNomeUtente(co.getUserIns()));
				
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
				b.setTipologia(this.OPERATORI);//SISO-812
				b.setDataInserimento(co2.getDtIns());//SISO-1060
				b.setUsrInserimento(super.getCognomeNomeUtente(co2.getUserIns()));
				
				lstComponents.add(b);
			}
			
			for(CsACasoAccessoFascicolo ls : lstSecondoLivello){
				String descrizione = ls.getCsOOrganizzazione()!=null ? ls.getCsOOrganizzazione().getNome() : " ";
				descrizione += ls.getCsOSettore()!=null ? ls.getCsOSettore().getDescrizione() : "";
				if(ls.getTipologia().equals(this.SECONDOLIVELLO)){
					SecondoLivelloComp b = new SecondoLivelloComp();
					b.setDataFine (ls.getDataFineApp());
					b.setDataInizio(ls.getDataInizioApp());
					b.setTipologia(this.SECONDOLIVELLO);
					b.setDescrizione(descrizione);
					b.setIdOrganizzazione(ls.getIdOrganizzazione());
					b.setIdSettore(ls.getIdSettore());
					b.setNascondiInformazioni(ls.getFlagNascondiInformazioni());
					b.setDataInserimento(ls.getDtIns());//SISO-1060
					b.setUsrInserimento(super.getCognomeNomeUtente(ls.getUserIns()));
					
					lstComponents.add(b);
				}
				if(ls.getTipologia().equals(this.ORGANIZZAZIONE)){
					OrganizzazioneComp o = new OrganizzazioneComp();
					o.setDataFine (ls.getDataFineApp());
					o.setDataInizio(ls.getDataInizioApp());
					o.setTipologia(this.ORGANIZZAZIONE);
					o.setDescrizione(descrizione);
					o.setIdOrganizzazione(ls.getIdOrganizzazione());
					o.setIdSettore(ls.getIdSettore());
					o.setNascondiInformazioni(ls.getFlagNascondiInformazioni());
					o.setDataInserimento(ls.getDtIns());//SISO-1060
					o.setUsrInserimento(super.getCognomeNomeUtente(ls.getUserIns()));
					
					lstComponents.add(o);
				}
			}
		}
		
		//Imposto al valore corrente
		CsOOperatoreSettore opSettore = getCurrentOpSettore();
		if(opSettore != null && opSettore.getCsOSettore().getCsOOrganizzazione().getId() != null){
			this.idEnte = opSettore.getCsOSettore().getCsOOrganizzazione().getId();
			this.onChangeOrganizzazione();
		}
		
		//SISO-812
		this.gestisciPermessi=this.OPERATORI;
		this.renderedFiltriOperatore=true;
		this.renderedFiltriOrganizzazione=true;
		this.renderedFiltriSecondoLivello=true;
		  
		logger.debug("*** FINE chiamata SchedaPermessiBean.initialize");

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
				List<CsOOrganizzazione> beanLst = confEnteService.getOrganizzazioni(dto);
				if (beanLst != null) {
					for (CsOOrganizzazione s : beanLst){
						SelectItem si = new SelectItem(s.getId(), s.getNome());
						si.setDisabled(!s.getAbilitato().equals("1"));
						lstOrganizzazioni.add(si);
					}
				}			} catch (Exception e) {
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
			List<CsOOperatoreTipoOperatore> lstOper = confEnteService.getOperatoriByTipoIdSettore(bo);
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
		String today = ddMMyyyy.format(new Date());
		boolean inserisciSelezione = !StringUtils.isEmpty(itemSelezionato); //inserito operatore da selectOneMenu
		boolean inserisciTesto = !StringUtils.isEmpty(insOperatore); //inserito operatore manualmente
		
		boolean inserisciOrganizzazione = idEnte != null && idEnte.longValue()>0;  //SISO-812
		boolean inserisciSettore = idSettore != null && idSettore.longValue()>0;
		boolean inserisciTipo =idTipoOperatore != null && idTipoOperatore.longValue()>0;
		boolean inserisciSecondoLivello= false;
		String nomeOrganizzazione="";
		String nomeSettore="";
		String nomeTipo="";
		
		try{
			
				if(inserisciSelezione && inserisciTesto && this.gestisciPermessi.equals(this.OPERATORI)){
					addWarning("Inserisci o Seleziona un Operatore alla volta", "");
			        return;
			       }
		  
		        if(!(inserisciOrganizzazione && inserisciSettore && inserisciTipo) && gestisciPermessi.equals(SECONDOLIVELLO)){
			       addWarning("Inserisci Organizzazione, Tipo e Settore", "");
				   return;
		           }else{
		        	   inserisciSecondoLivello=true;
		  }
			 
		if(inserisciSelezione && this.gestisciPermessi.equals(this.OPERATORI)){ 

			String[] str = itemSelezionato.split("\\|");
			OperatoriComp comp = new OperatoriComp();
			comp.setId(new Long(str[0]));
			comp.setTipo(this.getDescrizioneTipo());
			comp.setDescrizione(str[1]);
			comp.setDataInizio(ddMMyyyy.parse(today));
			comp.setDataFine(DataModelCostanti.END_DATE);
			comp.setOperTipo2(false);
			comp.setGestisciFascicolo(gestisciFascicolo);
			comp.setTipologia(gestisciPermessi); //SISO-812
			if(checkExists(comp))
				addWarning("L'elemento selezionato è già presente", "");
			else lstComponents.add(comp);
			
		 }else if(inserisciTesto && this.gestisciPermessi.equals(this.OPERATORI)){
			
			OperatoriComp comp = new OperatoriComp();
			comp.setTipo(this.getDescrizioneTipo());
			comp.setDescrizione(insOperatore);
			comp.setContatti(contattiOperatore);
			comp.setDataInizio(ddMMyyyy.parse(today));
			comp.setDataFine(DataModelCostanti.END_DATE);
			comp.setOperTipo2(true);
			comp.setTipologia(gestisciPermessi); //SISO-812
			lstComponents.add(comp);
			
		 }else if(gestisciPermessi.equals(this.OPERATORI)){
			addWarning("Inserisci o Seleziona un Operatore", "");
			return;
		}
		
		 if(inserisciSecondoLivello){
			 
			 try {
				   //trovo denominazione organizzazione selezionata
				   BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(this.idEnte);
					CsOOrganizzazione org = confEnteService.getOrganizzazioneById(dto);
					nomeOrganizzazione = org!=null ? org.getNome() : "";		
					
					
					//trovo denominazione settore selezionato
					dto.setObj(this.idSettore);
					CsOSettore sett = confEnteService.getSettoreById(dto);
					nomeSettore= sett!=null ? sett.getNome() : "";
					
					//trovo denominazione tipo selezionato
					dto.setObj(this.idSettore);
					CsTbTipoOperatore tipoOp = confService.getTipoOperatoreById(dto);
					nomeTipo=tipoOp!=null ? tipoOp.getDescrizione() : "";
								
					//procedo col salvataggio nella lista provvisoria
					if(gestisciPermessi.equals(this.SECONDOLIVELLO)){
						SecondoLivelloComp compSecondoLivello = new SecondoLivelloComp();
						//campo descrizione secondo livello
						String descrizione=nomeOrganizzazione+"-"+nomeSettore;
						compSecondoLivello.setDescrizione(descrizione);
						compSecondoLivello.setDataInizio(ddMMyyyy.parse(today));
						compSecondoLivello.setDataFine(DataModelCostanti.END_DATE);
						compSecondoLivello.setTipologia(gestisciPermessi); //SISO-812
						compSecondoLivello.setIdOrganizzazione(idEnte);
						compSecondoLivello.setIdSettore(idSettore);
						compSecondoLivello.setOrganizzazione(nomeOrganizzazione);
						compSecondoLivello.setSettore(nomeSettore);
						compSecondoLivello.setNascondiInformazioni(nascondiInformazioni);
					    
						if(checkExistsSecondoLivello(compSecondoLivello))
							addWarning("L'elemento selezionato è già presente", "");
						else 
						lstComponents.add(compSecondoLivello);
					}
					
					if(gestisciPermessi.equals(this.ORGANIZZAZIONE)){
						OrganizzazioneComp compOrganizzazione = new OrganizzazioneComp();
						//campo descrizione organizzazione
						String descrizione=nomeOrganizzazione+"-"+nomeSettore;
						compOrganizzazione.setId(idEnte);
						compOrganizzazione.setDescrizione(descrizione);
						compOrganizzazione.setDataInizio(ddMMyyyy.parse(today));
						compOrganizzazione.setDataFine(DataModelCostanti.END_DATE);
						compOrganizzazione.setTipologia(gestisciPermessi); //SISO-812
						compOrganizzazione.setOrganizzazione(nomeOrganizzazione);
						compOrganizzazione.setSettore(nomeSettore);
						compOrganizzazione.setIdOrganizzazione(idEnte);
						compOrganizzazione.setIdSettore(idSettore);
						compOrganizzazione.setNascondiInformazioni(nascondiInformazioni);
						if(checkExistsOrganizzazione(compOrganizzazione))
							addWarning("L'elemento selezionato è già presente", "");
						else 
						lstComponents.add(compOrganizzazione);
					}
					
					
					
				} catch (Exception e) {
					addErrorFromProperties("caricamento.error");
					logger.error(e.getMessage(),e);
				}
         
		 
		 }
		} catch (Exception e) {
			addError("Errore durante l'inserimento dell'elemento selezionato", (String)null);
			logger.error(e.getMessage(),e);
		}
	}
	
	protected boolean checkExistsSecondoLivello(SecondoLivelloComp comp) {
		for(ValiditaCompBaseBean el: lstComponents) {
			if(el.getTipologia().equals(SECONDOLIVELLO)){
				SecondoLivelloComp s= (SecondoLivelloComp)el;
				if(s.getIdOrganizzazione() != null && s.getIdOrganizzazione().equals(idEnte)
					&& s.getIdSettore() != null && s.getIdSettore().equals(idSettore) 
					//TODO:in questo modo non è possibile inserire secondi livelli con settore vuoto. Come procedo?
					)
					return true;
			}
		}
		return false;
	}
	
		
	protected boolean checkExistsOrganizzazione(OrganizzazioneComp comp) {
		for(ValiditaCompBaseBean el: lstComponents) {
			if(el.getTipologia().equals(ORGANIZZAZIONE)){
				OrganizzazioneComp o= (OrganizzazioneComp)el;
				if(o.getIdOrganizzazione()!= null && o.getIdOrganizzazione().equals(idEnte)
					&& o.getIdSettore() != null && o.getIdSettore().equals(idSettore)
					//TODO:in questo modo non è possibile inserire organizzazioni con settore vuoto.
					)
					return true;
				}
			}
		return false;
	}
	public String tabellaContatti(SchedaValiditaCompUtils svcu){
		
		String contatti = "";
		
		if(svcu.getTipologia().equals(this.OPERATORI)){
			OperatoriComp c=  (OperatoriComp)svcu;
			contatti=c.getContatti();
			}
		return contatti;
	}
	
	public Boolean tabellaResponsabile(SchedaValiditaCompUtils svcu){
		Boolean responsabile = false;
		
		if(svcu.getTipologia().equals(this.OPERATORI)){
			OperatoriComp c=  (OperatoriComp)svcu;
			responsabile=c.getResponsabile();
			}
		return responsabile;
	}
	
	public Boolean tabellaOperTipo2(SchedaValiditaCompUtils svcu){
		Boolean operTipo2 = false;
		
		if(svcu.getTipologia().equals(this.OPERATORI)){
			OperatoriComp c=  (OperatoriComp)svcu;
			operTipo2=c.isOperTipo2();
			}
		return operTipo2;
	}
	
	public boolean valida(){
		boolean valida = true;
		
		List<OperatoriComp> lstOperatori1 = new ArrayList<OperatoriComp>();
		for(ValiditaCompBaseBean baseComp: getLstComponents()) {
			if(baseComp.getTipologia().equals(this.OPERATORI) && !((OperatoriComp)baseComp).isOperTipo2())
				lstOperatori1.add((OperatoriComp)baseComp);
		}
		
		int i = 0;
		while(valida && i<lstOperatori1.size()) {
			OperatoriComp comp1 = lstOperatori1.get(i);
			for(OperatoriComp comp2: lstOperatori1) {
					if(lstOperatori1.indexOf(comp2)!=i 
							&& comp1.getId().equals(comp2.getId()) 
							&& comp1.getDataInizio().equals(comp2.getDataInizio()) 
							&& comp1.getDataFine().equals(comp2.getDataFine()) 
							&& comp1.getResponsabile().equals(comp2.getResponsabile())){
						valida = false;
						
					}
				}
			i++;
		}
		if(!valida)
			addWarning("Permessi-Operatori: Verificare gli intervalli di validità, sono presenti record identici", "");
		return valida;
	}
	
	@Override
	public boolean salva() {
		String today = ddMMyyyy.format(new Date());
		boolean salvato = true;
		itemSelezionato = null;
		List<ValiditaCompBaseBean> lstTempActive = getActiveList();
		try {
			
			lstComponentsActive = lstTempActive;
			
			if(!valida())
				return false;
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);	
			
			if(casoId != null && casoId>0) {
				
				dto.setObj(casoId);
				CsACaso caso = casoService.findCasoById(dto);
				
				casoService.eliminaOperatoreTipoOpByCasoId(dto);
				casoService.eliminaOperatoreTipoOp2ByCasoId(dto);
	
				casoService.eliminaAccessoFascicoloByCasoId(dto);
				
				for(ValiditaCompBaseBean baseComp: getLstComponents()) {
					//salvataggio operatori attivi
					if(baseComp.getTipologia().equals(this.OPERATORI)){
					OperatoriComp comp = (OperatoriComp)baseComp;
					if(comp.isOperTipo2()){
						CsACasoOpeTipoOpe2 cs = new CsACasoOpeTipoOpe2();

						cs.setCasoId(casoId);
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
					
						//cs.setCsACaso(soggetto.getCsACaso());
						cs.setCasoId (casoId);
						cs.setOperatoreTipoOperatoreId(comp.getId());
						cs.setDataInizioApp(comp.getDataInizio());
						cs.setDataFineApp(comp.getDataFine());
						cs.setDataInizioSys(new Date());
						cs.setFlagResponsabile(comp.getResponsabile());
						cs.setFlagGestioneFascicolo(decodeFlagGestione(comp.getGestisciFascicolo()));
						
						dto.setObj(comp.getId());
						cs.setCsOOperatoreTipoOperatore(confEnteService.getOperatoreTipoOpById(dto));
						
						dto.setObj(cs);
						iterService.salvaOperatoreCaso(dto);
					}	
					}
					if(baseComp.getTipologia().equals(this.SECONDOLIVELLO)){
						SecondoLivelloComp comp = (SecondoLivelloComp)baseComp;
                        CsACasoAccessoFascicolo cs = new CsACasoAccessoFascicolo();
                     
						cs.setCasoId (casoId);
						cs.setIdSettore(comp.getIdSettore()!=null && comp.getIdSettore()>0 ? comp.getIdSettore() : null);
						cs.setIdOrganizzazione(comp.getIdOrganizzazione()!=null && comp.getIdOrganizzazione()>0 ? comp.getIdOrganizzazione() : null);
						cs.setTipologia(SECONDOLIVELLO);
					    cs.setDtIns(comp.getDataInizio());
						cs.setDataFineApp(comp.getDataFine());
						cs.setDataInizioSys(new Date());
						cs.setDtMod(new Date());
						cs.setDataInizioApp(comp.getDataInizio());
						
						cs.setFlagNascondiInformazioni(comp.getNascondiInformazioni());
						dto.setObj(cs);
						casoService.salvaAccessoFascicoloCaso(dto);
						

					}else if(baseComp.getTipologia().equals(this.ORGANIZZAZIONE)){
						OrganizzazioneComp comp = (OrganizzazioneComp)baseComp;
                        CsACasoAccessoFascicolo cs = new CsACasoAccessoFascicolo();
                        cs.setCasoId (casoId);
                        cs.setIdSettore(comp.getIdSettore()!=null && comp.getIdSettore()>0 ? comp.getIdSettore() : null);
						cs.setIdOrganizzazione(comp.getIdOrganizzazione()!=null && comp.getIdOrganizzazione()>0 ? comp.getIdOrganizzazione() : null);
						cs.setTipologia(ORGANIZZAZIONE);
					    if(comp.getDataInizio() == null) {
					    	cs.setDtIns(new Date());
					    	cs.setDataInizioApp(ddMMyyyy.parse(today));
					    }else{
					    	cs.setDataInizioApp(comp.getDataInizio());	
					    	cs.setDtIns(comp.getDataInizio());
					    }
					    if(comp.getDataFine() == null) {
					    	cs.setDataFineApp(DataModelCostanti.END_DATE);
					    }else{
					    	cs.setDataFineApp(comp.getDataFine());
					    }
					    
						cs.setDataInizioSys(new Date());
						cs.setDtMod(new Date());
						//cs.setDataInizioApp(new Date());
						cs.setFlagNascondiInformazioni(comp.getNascondiInformazioni());
						dto.setObj(cs);
						casoService.salvaAccessoFascicoloCaso(dto);
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
	
	//metodo che carica settori solo per operatore (sia di secondo livello che non)
	private void loadListaSettori(){
		lstSettori = new ArrayList<SelectItem>();
		try {
			OperatoreDTO dto = new OperatoreDTO();
			fillEnte(dto);
			if(this.idEnte!=null){
				dto.setIdOrganizzazione(idEnte);
				
				//List<CsOSettoreBASIC> beanLst = confEnteService.findSettoreBASICByOrganizzazione(dto);
				List<KeyValueDTO> beanLst = new ArrayList<KeyValueDTO>();
				if(this.gestisciPermessi == null || this.gestisciPermessi.equals(OPERATORI)){
					beanLst=confEnteService.findSettoriByOrganizzazione(dto);
				}else if(this.gestisciPermessi != null && this.gestisciPermessi.equals(SECONDOLIVELLO)){
					beanLst=confEnteService.findSettoriSecondoLivelloByOrganizzazione(dto);
				}else //gestisciPermessi uguale a ORGANIZZAZIONE
					beanLst=confEnteService.findSettoriSenzaSecondoLivelloByOrganizzazione(dto);
				
				lstSettori = convertiLista(beanLst);
				if(!lstSettori.isEmpty())
					idSettore=(Long)lstSettori.get(0).getValue();
				else
					idSettore=null;
			
				
			}
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
   
	//SISO-812
	public void renderedFiltri(AjaxBehaviorEvent event){
		if(!gestisciPermessi.isEmpty()){
			if(gestisciPermessi.equals(OPERATORI)) {
				this.renderedFiltriOperatore=true;
				this.renderedFiltriSecondoLivello=true;
				this.renderedFiltriOrganizzazione=true;
				}
		    if(gestisciPermessi.equals(SECONDOLIVELLO)) {
		    	this.renderedFiltriOperatore=false;
		    	this.renderedFiltriSecondoLivello=true;
		    	this.renderedFiltriOrganizzazione=false;
		    	}
		    if(gestisciPermessi.equals(ORGANIZZAZIONE)) {
		    	this.renderedFiltriOperatore=false;
		    	this.renderedFiltriSecondoLivello=false;
		    	this.renderedFiltriOrganizzazione=true;
		    	}
		}
		this.loadListaSettori();
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
	// inizio SISO-812

	public List<SelectItem> getLstGestPermessi() {
		if(lstGestPermessi==null){
			lstGestPermessi = new ArrayList<SelectItem>();
			lstGestPermessi.add(new SelectItem(this.OPERATORI, "Operatori"));
			lstGestPermessi.add(new SelectItem(this.SECONDOLIVELLO, "Secondo livello"));
			lstGestPermessi.add(new SelectItem(this.ORGANIZZAZIONE, "Organizzazione"));
		}
		return lstGestPermessi;
	}

	public void setLstGestPermessi(List<SelectItem> lstGestPermessi) {
		this.lstGestPermessi = lstGestPermessi;
	}

	public String getGestisciPermessi() {
		return gestisciPermessi;
	}

	public void setGestisciPermessi(String gestisciPermessi) {
		this.gestisciPermessi = gestisciPermessi;
	}

	public boolean isRenderedFiltriOperatore() {
		return renderedFiltriOperatore;
	}

	public boolean isRenderedFiltriSecondoLivello() {
		return renderedFiltriSecondoLivello;
	}

	public boolean isRenderedFiltriOrganizzazione() {
		return renderedFiltriOrganizzazione;
	}

	public void setRenderedFiltriOperatore(boolean renderedFiltriOperatore) {
		this.renderedFiltriOperatore = renderedFiltriOperatore;
	}

	public void setRenderedFiltriSecondoLivello(boolean renderedFiltriSecondoLivello) {
		this.renderedFiltriSecondoLivello = renderedFiltriSecondoLivello;
	}

	public void setRenderedFiltriOrganizzazione(boolean renderedFiltriOrganizzazione) {
		this.renderedFiltriOrganizzazione = renderedFiltriOrganizzazione;
	}

	public String getOPERATORI() {
		return OPERATORI;
	}

	public String getSECONDOLIVELLO() {
		return SECONDOLIVELLO;
	}

	public String getORGANIZZAZIONE() {
		return ORGANIZZAZIONE;
	}

	public String getDescrizioneTipoOperatore() {
		return descrizioneTipoOperatore;
	}

	public void setDescrizioneTipoOperatore(String descrizioneTipoOperatore) {
		this.descrizioneTipoOperatore = descrizioneTipoOperatore;
	}
	
	public boolean isNascondiInformazioni() {
		return nascondiInformazioni;
	}

	public void setNascondiInformazioni(boolean nascondiInformazioni) {
		this.nascondiInformazioni = nascondiInformazioni;
	}

	public String getNascondiInformazioniHelp() {
		return nascondiInformazioniHelp;
	}

	public void setNascondiInformazioniHelp(String nascondiInformazioniHelp) {
		this.nascondiInformazioniHelp = nascondiInformazioniHelp;
	}

	@Override
	protected List<KeyValueDTO> loadListItems() {
		return null;
	}

	// fine SISO-812	
}
