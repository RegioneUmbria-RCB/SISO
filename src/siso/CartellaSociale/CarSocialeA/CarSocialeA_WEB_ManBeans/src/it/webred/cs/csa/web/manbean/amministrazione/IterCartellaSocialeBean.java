package it.webred.cs.csa.web.manbean.amministrazione;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.csa.web.manbean.interscambio.InterscambioDialogEventoBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.IterStatoInfo;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;

@ManagedBean(name = "iterBean")
@ViewScoped
public class IterCartellaSocialeBean  extends CsUiCompBaseBean {

	private AccessTableCatSocialeSessionBeanRemote catSocService = (AccessTableCatSocialeSessionBeanRemote) getCarSocialeEjb( "AccessTableCatSocialeSessionBean");

	private IterCartellaSociale ics = new IterCartellaSociale();

	/*
	 * private LazyListaCasiModel cartelle = new LazyListaCasiModel();
	 * 
	 * private LazyListaCasiModel cartellaSelezionata;
	 * 
	 * private List<EventoDTO> listaEventi;
	 * 
	 * private EventoDTO eventoSelezionato;
	 * 
	 * private List<CsACaso> listaCasi = new ArrayList<CsACaso>(); private String
	 * username = null; private CsACaso casoSelezionato;
	 */
	private Date dataInserimento;
	private CsOOperatoreSettore csOpSettSel = null;

	private  List<CsOSettoreBASIC>  listaSettori;
	private List<SelectItem> listaStato;
	private List<CsOOrganizzazione> listaOrganizzazione;
	private List<CsOOperatore> listaOperatore;
	private List<SelectItem> listaOperatoreSettore;
	private List<IterSoggettoLAZY> listaIterSoggettoLAZY;
	
	/*
	 * private String nome; private String cognome;
	 */
	
	private String settTo;
	/*
	 * private Long settIdTo; private Long orgIdTo;
	 */
	//private Long operatoreSelezionato;   //ID OPERATORE_SETTORE
	private String  alertUrl; 
	private InterscambioDialogEventoBean modalBean;
	
	private String  inputCFTA;
	private String iterStato;
	private Long settore;
	private Long opSettore;
	private Long organizzazione;
	
	
	protected AccessTableCasoSessionBeanRemote getCasoSessioBean() throws NamingException {
		AccessTableCasoSessionBeanRemote sessionBean = (AccessTableCasoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
		return sessionBean;
	}
	private List<SelectItem> tipiEvento = Arrays.asList(
			new SelectItem("", "TUTTI"), 
			new SelectItem("EXPORT", "EXPORT"), 
			new SelectItem("IMPORT", "IMPORT"));

	// Visualizzazione ESPORTAZIONE
	private boolean mostraTabellaUtenti = false;
	public boolean isMostraTabellaUtenti() {
		return mostraTabellaUtenti;
	}

	public void setMostraTabellaUtenti(boolean mostraTabellaUtenti) {
		this.mostraTabellaUtenti = mostraTabellaUtenti;
	}

	private boolean mostraFormRicercaCartella = true;
	// Visualizzazione IMPORTAZIONE
	private boolean mostraFormImportazioneCartella = false;
	// Visualizzazione ELENCO EVENTI
	private boolean mostraElencoEventi = false;

	private Date oggi = new Date();

	public List<CsOSettoreBASIC> getListaSettori() {
		return listaSettori;
	}

	public void setListaSettori(List<CsOSettoreBASIC> listaSettori) {
		this.listaSettori = listaSettori;
	}

	public List<SelectItem> getListaStato() {
		return listaStato;
	}

	public void setListaStato(List<SelectItem> listaStato) {
		this.listaStato = listaStato;
	}

	public List<CsOOrganizzazione> getListaOrganizzazione() {
		return listaOrganizzazione;
	}

	public void setListaOrganizzazione(List<CsOOrganizzazione> listaOrganizzazione) {
		this.listaOrganizzazione = listaOrganizzazione;
	}
	
	public List<IterSoggettoLAZY> getListaIterSoggettoLAZY() {
		return listaIterSoggettoLAZY;
	}

	public void setListaIterSoggettoLAZY(
			List<IterSoggettoLAZY> listaIterSoggettoLAZY) {
		this.listaIterSoggettoLAZY = listaIterSoggettoLAZY;
	}

	public List<SelectItem> getListaOperatoreSettore() {
		return listaOperatoreSettore;
	}

	public void setListaOperatoreSettore(List<SelectItem> listaOperatoreSettore) {
		this.listaOperatoreSettore = listaOperatoreSettore;
	}

	public List<SelectItem> getTipiEvento() {
		return tipiEvento;
	}

	public void setTipiEvento(List<SelectItem> tipiEvento) {
		this.tipiEvento = tipiEvento;
	}

	public boolean isMostraFormRicercaCartella() {
		return mostraFormRicercaCartella;
	}

	public void setMostraFormRicercaCartella(boolean mostraFormRicercaCartella) {
		this.mostraFormRicercaCartella = mostraFormRicercaCartella;
	}

	public boolean isMostraFormImportazioneCartella() {
		return mostraFormImportazioneCartella;
	}

	public void setMostraFormImportazioneCartella(
			boolean mostraFormImportazioneCartella) {
		this.mostraFormImportazioneCartella = mostraFormImportazioneCartella;
	}

	public List<CsOOperatore> getListaOperatore() {
		return listaOperatore;
	}

	public void setListaOperatore(List<CsOOperatore> listaOperatore) {
		this.listaOperatore = listaOperatore;
	}

	public Long getSettore() {
		return settore;
	}

	public void setSettore(Long settore) {
		this.settore = settore;
	}

	public Long getOpSettore() {
		return opSettore;
	}

	public void setOpSettore(Long opSettore) {
		this.opSettore = opSettore;
	}

	public Long getOrganizzazione() {
		return organizzazione;
	}

	public void setOrganizzazione(Long organizzazione) {
		this.organizzazione = organizzazione;
	}

	public String getInputCFTA() {
		return inputCFTA;
	}

	public void setInputCFTA(String inputCFTA) {
		this.inputCFTA = inputCFTA;
	}

	public String getIterStato() {
		return iterStato;
	}

	public void setIterStato(String iterStato) {
		this.iterStato = iterStato;
	}

	public IterCartellaSocialeBean() {
	   listaSettori = new ArrayList<CsOSettoreBASIC>();
	   listaStato = new ArrayList<SelectItem>();
	   listaOrganizzazione = new ArrayList<CsOOrganizzazione>();
	   listaOperatore = new ArrayList<CsOOperatore>();
	   listaOperatoreSettore = new ArrayList<SelectItem>(); 
	}

	@PostConstruct
	public void onInit() {
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		//listaSettori = ics.getListaSettori(bdto);
		listaStato  = ics.getListaIterStati(bdto); 
		listaOrganizzazione = confEnteService.getOrganizzazioniAccesso(bdto);
		listaOperatore = new ArrayList<CsOOperatore>();
		  
		RequestContext.getCurrentInstance().update("IterCartellaSocialeInput");
	}
			
	public void onOrganizzazioneChange(AjaxBehaviorEvent event) {

	    Long organizzazioneSel = (Long) ((UIOutput) event.getSource()).getValue();
	    		
	    BaseDTO bdto = new BaseDTO();
	    bdto.setObj(organizzazioneSel);
		fillEnte(bdto);
		this.listaSettori.clear();
		this.listaOperatoreSettore.clear();
	    this.listaSettori =  confEnteService.findSettoreBASICByOrganizzazione(bdto);
		
	}

	private CsOSettoreBASIC getCsOSettoreById(long id){
		
		for(CsOSettoreBASIC csOSettore : listaSettori){
			if(csOSettore.getId() == id)
				return csOSettore;
		}
		return new CsOSettoreBASIC();
	}
 
			
	public void onSettoreChange(AjaxBehaviorEvent event) {

	    Long settoreSel = (Long) ((UIOutput) event.getSource()).getValue();
	    	
	    OperatoreDTO bdto = new OperatoreDTO();
	    bdto.setIdSettore(settoreSel);
		fillEnte(bdto);
		this.listaOperatoreSettore.clear();
	    this.listaOperatoreSettore =  ics.findOperatoriSettore(bdto);
		
	}
	public void onOperatoreChange(AjaxBehaviorEvent event) {

	    Long operatoreSel = (Long) ((UIOutput) event.getSource()).getValue();
	    		
	    logger.debug("onOperatoreChange:"+operatoreSel);
	    
	    OperatoreDTO bdto = new OperatoreDTO();
	    fillEnte(bdto);
	    bdto.setIdOperatoreSettore(operatoreSel);
	    this.csOpSettSel = confEnteService.findOperatoreSettoreById(bdto);
	}
 
	public boolean isStatoSelected() {
		return !StringUtils.isBlank(iterStato);
	}
	
	public boolean isSettoreSelected() {
		return settore!=null && settore>0;
	}
	
	public boolean isEnteSelected() {
		return organizzazione!=null && organizzazione>0;
	}
	
	public boolean isOpSettoreSelected() {
		return opSettore!=null && opSettore>0;
	}
	
	public boolean isProvenienzaIncompleta(){
		boolean completa = (!this.isEnteSelected() && !this.isSettoreSelected() && !this.isOpSettoreSelected()) ||
						   (this.isEnteSelected() && this.isSettoreSelected() && this.isOpSettoreSelected());
		return !completa;
	}
	
	public void onClickCambiaStatoIter(){
		
		List<String> messages= new ArrayList<String>();
	
		if(!this.isStatoSelected()) {
			this.addError("Attenzione", "Selezionare un nuovo stato iter");
			return;
		}
		Long statoL = Long.parseLong(iterStato);
		
		if((statoL==IterStatoInfo.SEGNALATO_OP || statoL==DataModelCostanti.IterStatoInfo.PRESO_IN_CARICO) && !this.isOpSettoreSelected()) {
			this.addError("Attenzione", "Per lo stato selezionato è obbligatorio impostare l'operatore di destinazione");
			return;
		}else if(statoL==IterStatoInfo.SEGNALATO && !this.isSettoreSelected()) {
			this.addError("Attenzione", "Per lo stato selezionato è obbligatorio impostare il settore di destinazione");
			return;
		}else if(statoL==IterStatoInfo.SEGNALATO_ENTE && !this.isEnteSelected()) {
			this.addError("Attenzione", "Per lo stato selezionato è obbligatorio impostare l'organizzazione di destinazione");
			return;
		}else{
			if(this.isProvenienzaIncompleta()){
				this.addError("Attenzione", "Per lo stato selezionato è obbligatorio impostare ente, ufficio e operatore; non selezionando nessuno verranno utilizzati i valori dell'ultimo iter step del caso.");
				return;
			}
		}
		
		boolean ok = true;
		 
		try{
			for(IterSoggettoLAZY iterIesimo: listaIterSoggettoLAZY){
				dataInserimento = new Date();	
				boolean bSaved = true;
				String msgCaso = "Caso ID: "+iterIesimo.getCasoId()+" [cf: "+iterIesimo.getCf()+"]";
				Long idEnteCorrente = iterIesimo.getItStep().getEnteSegnalante().getId();
				Long idSettoreCorrente = iterIesimo.getItStep().getSettoreSegnalante().getId();
				Long idOpCorrente = (Long) iterIesimo.getItStep().getOperatoreSegnalante().getCodice();
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				
				//devo verificare che il settore di destinazione gestisca almeno una delle categorie del caso
				if(this.isSettoreSelected() && idSettoreCorrente!=settore) {
					
		   			List<CsASoggettoCategoriaSoc> listaCatSocLazy = iterIesimo.getListaCatSociali();
		   			boolean catSocManaged = false;
		   			int i = 0;
		   			while(!catSocManaged && i<listaCatSocLazy.size()){
		   				CsASoggettoCategoriaSoc catSocLazy =  listaCatSocLazy.get(i);		
						dto.setObj(catSocLazy.getId().getCategoriaSocialeId());
						dto.setObj2(settore);
						catSocManaged = catSocService.existsRelSettoreCatsoc(dto);
						i++;
		   			}
		   			
		   			if(!catSocManaged){
						messages.add(msgCaso + " non può essere associato al settore " + getCsOSettoreById(this.settore).getTooltip() + " perché questo non gestisce le categorie sociali del caso ");
						bSaved = false;
					}	
				}
				
				/*
				 * //Verifico che la transizione sia ammessa
				 * dto.setObj(iterIesimo.getItStep().getIdStatoIter()); dto.setObj2(statoL);
				 * boolean transAmmessa = confService.existsTransizioneTraStati(dto);
				 * if(!transAmmessa) { messages.add(msgCaso +
				 * " transizione non prevista dal sistema"); bSaved = false; }
				 */
				if(bSaved){
				
					IterDTO iterDto = new IterDTO();
					fillEnte(iterDto);
					iterDto.setCsACaso(iterIesimo.getCaso()); 
					alertUrl = getRequest().getRequestURL().toString() + "?IDCASO=" + iterDto.getCsACaso().getId().toString();
					
					if(statoL==IterStatoInfo.SEGNALATO || statoL==IterStatoInfo.SEGNALATO_ENTE || statoL==IterStatoInfo.SEGNALATO_OP) {
						//Utilizzo l'ente/settore/operatore impostato come finale (TO)
						
						iterDto.setIdOrganizzazione(idEnteCorrente);
						iterDto.setIdSettore(idSettoreCorrente); //iterDto.setIdSettTo(new Long(this.settore));
						iterDto.setIdOperatore(idOpCorrente);
						
						if(this.isEnteSelected() && statoL==IterStatoInfo.SEGNALATO_ENTE)
							iterDto.setIdOrgTo(this.organizzazione);
						
						if(this.isSettoreSelected() && statoL==IterStatoInfo.SEGNALATO)
							iterDto.setIdSettTo(this.settore);
						
						if(this.isOpSettoreSelected() && statoL==IterStatoInfo.SEGNALATO_OP)
							iterDto.setIdOpSettoreTo(opSettore); 
						
					}else{
						
						if(!this.isEnteSelected() && !this.isSettoreSelected() && this.csOpSettSel==null) {
							iterDto.setIdOrganizzazione(idEnteCorrente);
							iterDto.setIdSettore(idSettoreCorrente); 
							iterDto.setIdOperatore(idOpCorrente);
						}else{
							//Utilizzo l'ente/settore/operatore impostato come corrente dello stato
							if(this.isEnteSelected())
								iterDto.setIdOrganizzazione(this.organizzazione);
							
							if(this.isSettoreSelected())
								iterDto.setIdSettore(this.settore);
							
							if(this.csOpSettSel!=null){
								iterDto.setIdSettore(this.csOpSettSel.getCsOSettore().getId()); 
								iterDto.setIdOperatore(this.csOpSettSel.getCsOOperatore().getId()); 
							}
						}
					}
					
					iterDto.setIdStato(statoL);
					iterDto.setNota("Cambio stato effettuato da "+getCurrentUsername()+ " mediante ITER UTILITY");
					iterDto.setNotificaSettoreSegnalante(true);
					
					iterDto.setAttrNewStato(null);
					iterDto.setAlertUrl(null);
					iterDto.setAlertUrl(statoL == DataModelCostanti.IterStatoInfo.PRESO_IN_CARICO  ? alertUrl : null);
					iterDto.setDataInserimento(dataInserimento);
					
					bSaved = iterService.addIterStep(iterDto);
					addInfoFromProperties("salva.ok");
				}
			}
			onClickValidaCF();
			if(!messages.isEmpty())
				addWarning("Attenzione", messages);
			RequestContext.getCurrentInstance().addCallbackParam("saved", ok);
		}catch (Exception e) {
			ok=false;
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	public void onClickValidaCF() {
		StringTokenizer st2 = new StringTokenizer(this.inputCFTA.trim(), ";");
				listaIterSoggettoLAZY = new ArrayList<IterSoggettoLAZY>();
				while (st2.hasMoreElements()) {
					String CF = st2.nextElement().toString();
					BaseDTO dto = new BaseDTO();
					dto.setObj(CF);
					fillEnte(dto);
					List<CsASoggettoLAZY> listSoggettiCFLazy =   soggettoService.getSoggettiByCF(dto);
					
					
					for(CsASoggettoLAZY soggetto : listSoggettiCFLazy){
						IterSoggettoLAZY iterSoggLAZY = new IterSoggettoLAZY(soggetto);
						iterSoggLAZY.setInputCF(CF);
						
						dto.setObj(soggetto.getAnagraficaId());
						iterSoggLAZY.setListaCatSociali(soggettoService.getSoggettoCategorieAttualiBySoggetto(dto));
						
						if(soggetto!= null){
							dto.setObj(iterSoggLAZY.getCasoId());
							
							
							IterInfoStatoMan casoInfo = new IterInfoStatoMan();
							CsIterStepByCasoDTO itStep = iterService.getLastIterStepByCasoDTO(dto);
							iterSoggLAZY.setItStep(itStep);
							if( itStep != null )
								casoInfo.initialize( itStep);
							iterSoggLAZY.setItStepInfo(casoInfo);
							
						}
						
						listaIterSoggettoLAZY.add(iterSoggLAZY);
					}

					
				}
				this.mostraTabellaUtenti= true;
 
			}
			
			public Date getDataInserimento() {
				return dataInserimento;
			}

			public void setDataInserimento(Date dataInserimento) {
				this.dataInserimento = dataInserimento;
			}
			
			/**
	 * Visualizza la maschera per avviare la ricerca tramite nome e cognome 
	 */

}
