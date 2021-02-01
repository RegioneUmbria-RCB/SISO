package it.webred.cs.csa.web.manbean.amministrazione;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.EventoDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.web.manbean.interscambio.InterscambioDialogEventoBean;
import it.webred.cs.csa.web.manbean.listacasi.LazyListaCasiModel;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoCategoriaSocLAZY;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCfgItStato;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.component.UIOutput;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.primefaces.context.RequestContext;

@ManagedBean(name = "iterBean")
@ViewScoped
public class IterCartellaSocialeBean  extends CsUiCompBaseBean {

			private AccessTableCatSocialeSessionBeanRemote catSocService = (AccessTableCatSocialeSessionBeanRemote) getCarSocialeEjb( "AccessTableCatSocialeSessionBean");

			private IterCartellaSociale ics = new IterCartellaSociale();

			private LazyListaCasiModel cartelle = new LazyListaCasiModel();

			private LazyListaCasiModel cartellaSelezionata;

			private List<EventoDTO> listaEventi;
			
			private EventoDTO eventoSelezionato;

			private List<CsACaso> listaCasi = new ArrayList<CsACaso>();
			private String username = null;
			private CsACaso casoSelezionato;
			private Date dataInserimento;
			private CsOOperatoreSettore csOpSettSel = null;
			public Date getDataInserimento() {
				return dataInserimento;
			}

			public void setDataInserimento(Date dataInserimento) {
				this.dataInserimento = dataInserimento;
			}
			private String nome;
			private String cognome;
			
			private String settTo;
			private Long settIdTo;
			private Long orgIdTo;
			//private Long operatoreSelezionato;   //ID OPERATORE_SETTORE
			private String  alertUrl; 
			private InterscambioDialogEventoBean modalBean;
			
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

			public List<CsCfgItStato> getListaStato() {
				return listaStato;
			}

			public void setListaStato(List<CsCfgItStato> listaStato) {
				this.listaStato = listaStato;
			}

			public List<CsOOrganizzazione> getListaOrganizzazione() {
				return listaOrganizzazione;
			}

			public void setListaOrganizzazione(List<CsOOrganizzazione> listaOrganizzazione) {
				this.listaOrganizzazione = listaOrganizzazione;
			}

			private  List<CsOSettoreBASIC>  listaSettori;
			private List<CsCfgItStato> listaStato;
			private List<CsOOrganizzazione> listaOrganizzazione;
			private List<CsOOperatore> listaOperatore;
			private List<CsOOperatoreSettore> listaOperatoreSettore;
			private List<IterSoggettoLAZY> listaIterSoggettoLAZY;
			
			public List<IterSoggettoLAZY> getListaIterSoggettoLAZY() {
				return listaIterSoggettoLAZY;
			}

			public void setListaIterSoggettoLAZY(
					List<IterSoggettoLAZY> listaIterSoggettoLAZY) {
				this.listaIterSoggettoLAZY = listaIterSoggettoLAZY;
			}

			public List<CsOOperatoreSettore> getListaOperatoreSettore() {
				return listaOperatoreSettore;
			}

			public void setListaOperatoreSettore(
					List<CsOOperatoreSettore> listaOperatoreSettore) {
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

			public String getSettore() {
				return settore;
			}

			public void setSettore(String settore) {
				this.settore = settore;
			}

			public String getOperatore() {
				return operatore;
			}

			public void setOperatore(String operatore) {
				this.operatore = operatore;
			}

			public String getOrganizzazione() {
				return organizzazione;
			}

			public void setOrganizzazione(String organizzazione) {
				this.organizzazione = organizzazione;
			}

			private String  inputCFTA;
			private String iterStato;
			private String settore;
			private String operatore;
			private String organizzazione;
			
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
				   listaStato = new ArrayList<CsCfgItStato>();
				   listaOrganizzazione = new ArrayList<CsOOrganizzazione>();
				   listaOperatore = new ArrayList<CsOOperatore>();
				   listaOperatoreSettore = new ArrayList<CsOOperatoreSettore>(); 
				   this.username = getCurrentUsername(); 
			}

			@PostConstruct
			public void onInit() {
				BaseDTO bdto = new BaseDTO();
				fillEnte(bdto);
				//listaSettori = ics.getListaSettori(bdto);
				listaStato  = ics.getListaIterStati(bdto); 
				listaOrganizzazione = ics.getOrganizzazione(bdto); 
				listaOperatore = new ArrayList<CsOOperatore>();
				  
				RequestContext.getCurrentInstance().update(
						"IterCartellaSocialeInput");
			}
			public void onOrganizzazioneChange(AjaxBehaviorEvent event) {

			    String organizzazioneSel = (String) ((UIOutput) event.getSource()).getValue();
			    		
			    System.out.print(organizzazioneSel);
			    BaseDTO bdto = new BaseDTO();
			    bdto.setObj( new Long(organizzazioneSel));
				fillEnte(bdto);
				this.listaSettori.clear();
				this.listaOperatoreSettore.clear();
			    this.listaSettori =  ics.findSettoreBASICByOrganizzazione(bdto);
				
			}

			private CsOSettoreBASIC getCsOSettoreById(long id){
				
				for(CsOSettoreBASIC csOSettore : listaSettori){
					if(csOSettore.getId() == id)
						return csOSettore;
				}
				return new CsOSettoreBASIC();
			}
 
			
			public void onSettoreChange(AjaxBehaviorEvent event) {

			    String settoreSel = (String) ((UIOutput) event.getSource()).getValue();
			    		
			    System.out.print(settoreSel);
			    OperatoreDTO bdto = new OperatoreDTO();
			    bdto.setIdSettore(new Long(settoreSel));
				fillEnte(bdto);
				this.listaOperatoreSettore.clear();
			    this.listaOperatoreSettore =  ics.getOperatoreSettore(bdto);
				
			}
			public void onOperatoreChange(AjaxBehaviorEvent event) {

			    String operatoreSel = (String) ((UIOutput) event.getSource()).getValue();
			    		
			    System.out.print(operatoreSel);
			   
			    for(CsOOperatoreSettore csOpSett : listaOperatoreSettore){
			    	if(csOpSett.getId() == Long.parseLong(operatoreSel)){
			    		this.csOpSettSel = csOpSett;
			    		break;
			    	}
			    }
			 
			}
		 
			public String getSettTo() {
				settTo = null;
				settIdTo = null;
				orgIdTo = null;
				
				
					CsOOperatoreSettore currOpSett = getCurrentOpSettore();
					settTo = currOpSett.getCsOSettore().getNome();
					settIdTo = currOpSett.getCsOSettore().getId();
					orgIdTo = currOpSett.getCsOSettore().getCsOOrganizzazione().getId();
			
				return settTo;
			}
			public void onClickCambiaStatoIter(){
				boolean ok = true;
				 
				 
				try{
					for(IterSoggettoLAZY iterIesimo: listaIterSoggettoLAZY){
						dataInserimento = new Date();	
						boolean bSaved = true;	
						// STEP 1:  devo impostare lo stato segnalato:
						IterDTO iterDto = new IterDTO();
						fillEnte(iterDto);
						iterDto.setCsACaso(iterIesimo.getSoggettoLAZY().getCsACaso()); 
						alertUrl = getRequest().getRequestURL().toString() + "?IDCASO=" + iterDto.getCsACaso().getId().toString();
 
						
						
						if(organizzazione != null && ! organizzazione.equals("")){
							
							iterDto.setIdOrgTo(new Long(this.organizzazione));
						}
						iterDto.setIdOrganizzazione(iterIesimo.getItStep().getCsOOrganizzazione1().getId());
						
						if(settore != null && ! settore.equals("")){
							
							iterDto.setIdSettTo(new Long(this.settore));
						}
				 
						Long statoL = Long.parseLong(iterStato);
						
						iterDto.setIdSettore(iterIesimo.getItStep().getCsOSettore1().getId()); //iterDto.setIdSettTo(new Long(this.settore));
						iterDto.setIdOperatore( iterIesimo.getItStep().getCsOOperatore1().getId() );
						
						if(statoL == DataModelCostanti.IterStatoInfo.PRESO_IN_CARICO){
							
							if(this.operatore != null && !"".equals(this.operatore.trim())){
								
								iterDto.setIdStato(DataModelCostanti.IterStatoInfo.SEGNALATO_OP);
								iterDto.setIdOpSettoreTo(new Long(this.operatore)); //iterDto.setIdOpSettoreTo(null);
								iterDto.setNota("Segnalato ad operatore ITER UTILITY");
							}
							
							else{
								iterDto.setIdStato(DataModelCostanti.IterStatoInfo.SEGNALATO);
								iterDto.setNota("Segnalato a settore ITER UTILITY");
							}
							
//							iterDto.setNomeOperatore(username);
							iterDto.setNomeOperatore(iterIesimo.getItStep().getCsOOperatore1().getUsername());
							iterDto.setNotificaSettoreSegnalante(true);
						
							
							
							iterDto.setAttrNewStato(null);
							iterDto.setAlertUrl(null);
						 	
						    bSaved = ics.addIterStep(iterDto);
							addInfoFromProperties("salva.ok");
							if(bSaved){
								iterDto.setIdStato(statoL);
								
								
								
								//iterDto.setIdOpSettoreTo( new Long(this.operatore)); //iterDto.setIdOperatore(new Long(this.operatore)); // il TO non serve in quanto è l'operatore stesso a prenderlo in carico.
								iterDto.setIdOpSettoreTo(null);
								iterDto.setNomeOperatore(this.csOpSettSel.getCsOOperatore().getUsername()); // Va inserito il destinatario in caso di Presa in carico 
								iterDto.setIdSettore(this.csOpSettSel.getCsOSettore().getId()); //iterDto.setIdSettTo(new Long(this.settore));
								
								//iterDto.setIdOrgTo(new Long(this.organizzazione));
								iterDto.setIdOrgTo(null);
								iterDto.setCsACaso(iterIesimo.getSoggettoLAZY().getCsACaso());
								//iterDto.setIdSettTo( new Long(this.settore) ); //iterDto.setIdSettore( new Long(this.settore) );//iterIesimo.getItStep().getCsOSettore1().getId());
								iterDto.setIdSettTo(null ); //iterDto.setIdSettore( new Long(this.settore) );//iterIesimo.getItStep().getCsOSettore1().getId());
								iterDto.setNota("Presa in carico dall'operatore mediante ITER UTILITY");
							 	iterDto.setAlertUrl(alertUrl);
								iterDto.setDataInserimento(dataInserimento);
								bSaved = ics.addIterStep(iterDto);
								if( !bSaved ) {
									addErrorDialog("Attenzione", "Errore durante il salvataggio della presa in carico.");
								}
							}
						}else{
							iterDto.setIdStato(statoL);
//							iterDto.setNomeOperatore(username);
							iterDto.setNomeOperatore(iterIesimo.getItStep().getCsOOperatore1().getUsername());
							iterDto.setNotificaSettoreSegnalante(true);
							if(operatore != null && ! operatore.equals("")){
						 		iterDto.setIdOpSettoreTo(new Long(this.operatore)); //iterDto.setIdOpSettoreTo(null);
							}
							//devo verificare che l'ORGANIZZAZIONE  abbia il SETTORE abilitato.
							BaseDTO dto = new BaseDTO();
				   			List<CsASoggettoCategoriaSocLAZY> listaCatSocLazy = iterIesimo.getListaCsSoggettoCatSocLazy();
				   			
				   			for(CsASoggettoCategoriaSocLAZY catSocLazy : listaCatSocLazy){
				   				dto.setObj(catSocLazy.getId().getCategoriaSocialeId() );
								fillEnte(dto);
								List<CsRelSettoreCatsoc> listaRelSettoreCatSoc = catSocService.findRelSettoreCatsocByCatSoc(dto);
								if(listaRelSettoreCatSoc != null | listaRelSettoreCatSoc.size() > 0){
									boolean found = false;
									for(CsRelSettoreCatsoc relSettoreCatSoc : listaRelSettoreCatSoc){
										
										if(relSettoreCatSoc.getId().getSettoreId() == Integer.parseInt(this.settore)){
											found = true;
										}
												
									}
									if(found == false){
										 
										addWarning("Attenzione", "Il caso associato al Codice Fiscale " + iterIesimo.getInputCF() + " non può essere associato al settore " + getCsOSettoreById(Integer.parseInt(this.settore.trim())).getTooltip() + " perché questo non ha associata la categoria " + catSocLazy.getCsCCategoriaSociale().getDescrizione());
										bSaved = false;
									}
								}
				   			}
							
							iterDto.setNota("Segnalato a " + (this.settore != null && !this.settore.equals("") ? "Settore" : "Ente") + " ITER UTILITY");
							iterDto.setAttrNewStato(null);
							iterDto.setAlertUrl(null);
							iterDto.setNotificaSettoreSegnalante(true);
							
							if(bSaved)
								bSaved = ics.addIterStep(iterDto);
							if( !bSaved ) {
								addErrorDialog("Attenzione", "Errore durante il salvataggio del cambio stato.");
							 }
						}
					}
					onClickValidaCF();
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
					//IterSoggettoLAZY iterSoggLAZY = new IterSoggettoLAZY( );
					String CF = st2.nextElement().toString();
					BaseDTO dto = new BaseDTO();
					dto.setObj(CF);
					fillEnte(dto);
					List<CsASoggettoLAZY> listSoggettiCFLazy =   ics.getSoggettiByCF(dto);
					
					for(CsASoggettoLAZY soggettoLazy : listSoggettiCFLazy){
						IterSoggettoLAZY iterSoggLAZY = new IterSoggettoLAZY( );
						iterSoggLAZY.setInputCF(CF);
						
						dto.setObj(soggettoLazy.getAnagraficaId());
						iterSoggLAZY.setListaCsSoggettoCatSocLazy(  ics.getSoggettoCategorieAttuali(dto));
						iterSoggLAZY.setSoggettoLAZY(soggettoLazy); //(ics.getSoggettoByCF(dto));
						
						if(iterSoggLAZY.getSoggettoLAZY()  != null){
							IterDTO iterDto = new IterDTO();
							iterDto.setCsACaso(iterSoggLAZY.getSoggettoLAZY().getCsACaso()); 
							fillEnte(iterDto);
							iterSoggLAZY.setItStep(   ics.getLastIterStepByCaso( iterDto));
						}
						
						listaIterSoggettoLAZY.add(iterSoggLAZY);
					}

					
				}
				this.mostraTabellaUtenti= true;
 
			}
			
			/**
			 * Visualizza la maschera per avviare la ricerca tramite nome e cognome 
			 */

}
