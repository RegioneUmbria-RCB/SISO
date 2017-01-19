package it.webred.cs.csa.web.manbean.scheda;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableMediciSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTablePersonaCiviciSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.fascicolo.presaincarico.PresaInCaricoBean;
import it.webred.cs.csa.web.manbean.fascicolo.schedeSegr.SchedaSegrBean;
import it.webred.cs.csa.web.manbean.scheda.anagrafica.AnagraficaBean;
import it.webred.cs.csa.web.manbean.scheda.disabilita.DatiDisabilitaBean;
import it.webred.cs.csa.web.manbean.scheda.invalidita.DatiInvaliditaBean;
import it.webred.cs.csa.web.manbean.scheda.note.NoteBean;
import it.webred.cs.csa.web.manbean.scheda.operatori.OperatoriBean;
import it.webred.cs.csa.web.manbean.scheda.parenti.ParentiBean;
import it.webred.cs.csa.web.manbean.scheda.sociali.DatiSocialiBean;
import it.webred.cs.csa.web.manbean.scheda.tribunale.DatiTribunaleBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCMedico;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.jsf.bean.DatiAnaBean;
import it.webred.cs.jsf.bean.DatiCasoBean;
import it.webred.cs.jsf.manbean.exception.CsUiCompException;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;
import it.webred.siso.ws.client.anag.client.AnagrafeClient;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;
import it.webred.siso.ws.client.anag.client.RicercaAnagraficaBean;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsIndirizzo;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import javax.naming.NamingException;

import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class SchedaBean extends CsUiCompBaseBean {
	
	private boolean renderNuovaCartella;
	private boolean disableModificaCartella;
	private String idTabScheda = "tabScheda";
	
	@ManagedProperty(value="#{anagraficaBean}")
	private AnagraficaBean anagraficaBean;
	
	@ManagedProperty(value="#{datiDisabilitaBean}")
	private DatiDisabilitaBean datiDisabilitaBean;
	
	@ManagedProperty(value="#{datiInvaliditaBean}")
	private DatiInvaliditaBean datiInvaliditaBean;
	
	@ManagedProperty(value="#{parentiBean}")
	private ParentiBean parentiBean;
	
	@ManagedProperty(value="#{datiSocialiBean}")
	private DatiSocialiBean datiSocialiBean;
	
	@ManagedProperty(value="#{datiTribunaleBean}")
	private DatiTribunaleBean datiTribunaleBean;
	
	@ManagedProperty(value="#{operatoriBean}")
	private OperatoriBean operatoriBean;
	
	@ManagedProperty(value="#{schedaSegrBean}")
	private SchedaSegrBean schedaSegrBean;
	
	@ManagedProperty(value="#{noteBean}")
	private NoteBean noteBean;
	
	private PresaInCaricoBean presaInCaricoBean;
	
	private boolean renderTabAnagrafica;
	private boolean renderTabDatiSociali;
	private boolean renderTabInvalidita;
	private boolean renderTabParenti;
	private boolean renderTabDisabilita;
	private boolean renderTabTribunale;
	private boolean renderTabServizi;
	private boolean renderTabContributi;
	private boolean renderTabOperatori;
	private boolean renderTabNote;
	
	private boolean disableHead;
	private boolean modificaButt;
	private boolean attivaSalvataggio;
	private Long identificativoScheda;
	
	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");

	
	
	public void nuova() {
			
		initialize(null);
		modifica();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("scheda.faces");
		} catch (IOException e) {
			addError("Errore", "Errore durante il reindirizzamento alla scheda Caso");
		}
	}
	
	public void nuovaDaAnagrafeSanitaria(String id) {
		if(id == null || "".equals(id)) {
			addWarning("Scegliere un soggetto o creare una cartella vuota", "");
			return;
		}
		
		initialize(null);
		modifica();
		
		try{
			LuoghiService luoghiService = (LuoghiService) getEjb("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");

			PersonaFindResult p = this.getPersonaDaAnagSanitaria(id);
			
			if(p != null) {
				
				if(isCodFiscaleInCs(p.getCodfisc().trim().toUpperCase())){
					addWarning("Operazione non consentita","Esiste già una cartella associata al Cod.Fiscale "+p.getCodfisc());
					disableHead=false;
					return;
				}
				
				if(p.getDataMor()!=null && p.getDataMor().before(new Date())){
					addWarning("Non è possibile creare una nuova cartella","Il soggetto selezionato è deceduto il "+ddMMyyyy.format(p.getDataMor()));
					disableHead=false;
					return;
				}
				
				
				//anagrafica
				DatiAnaBean anaBean = new DatiAnaBean();
				anaBean.setCodiceFiscale(p.getCodfisc());
				anaBean.setCognome(p.getCognome());
				anaBean.setNome(p.getNome());
				anaBean.setDataNascita(p.getDataNascita());
				SessoBean sb = new SessoBean(p.getSesso());
				anaBean.setDatiSesso(sb);
				anagraficaBean.setDatiAnaBean(anaBean);
				
				//Cittadinanza
				String nazionalita = getCittadinanzaByIstat(p.getCodIstatCittadinanza());
				anaBean.setCittadinanza((nazionalita!=null && nazionalita.length() > 255) ? nazionalita.substring(0, 252) + "..." : nazionalita);
				
				//nascita
				AmTabComuni comuneNascita = luoghiService.getComuneItaByIstat(p.getIstatComNas());
				if(comuneNascita!=null) {
					p.setDesStatoNas("ITALIA");
					p.setIstatComNas(comuneNascita.getCodIstatComune());
					p.setDesComNas(comuneNascita.getDenominazione());
					p.setSiglaProvNas(comuneNascita.getSiglaProv());
					ComuneBean comuneBean = new ComuneBean(comuneNascita);
					anagraficaBean.getComuneNazioneNascitaMan().getComuneNascitaMan().setComune(comuneBean);
				} else {
					AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(p.getIstatComNas(), p.getDesStatoNas());
					anagraficaBean.getComuneNazioneNascitaMan().setValue(anagraficaBean.getComuneNazioneNascitaMan().getNazioneValue());
					anagraficaBean.getComuneNazioneNascitaMan().getNazioneMan().setNazione(amTabNazioni);
				}
				
				//indirizzo res	
				if(p.getCodfisc() != null) {
					AccessTablePersonaCiviciSessionBeanRemote personaCiviciService = (AccessTablePersonaCiviciSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTablePersonaCiviciSessionBean");
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(p.getCodfisc());
					CsAIndirizzo indResidenza = personaCiviciService.getIndirizzoResidenzaByCodFisc(dto);
					if(indResidenza != null) {
						indResidenza.setCsTbTipoIndirizzo(anagraficaBean.getResidenzaCsaMan().getTipoIndirizzoResidenza());
						List<CsAIndirizzo> listaIndirizzi = new ArrayList<CsAIndirizzo>();
						listaIndirizzi.add(indResidenza);
						anagraficaBean.getResidenzaCsaMan().setLstIndirizzi(listaIndirizzi);
						anagraficaBean.getResidenzaCsaMan().setLstIndirizziOld(listaIndirizzi);
						anagraficaBean.getResidenzaCsaMan().setWarningMessage("Se possibile impostare correttamente le date");
					}
				}
				
				//stato civile
				if(p.getStatoCivile() != null) {
					AccessTableConfigurazioneSessionBeanRemote configurazioneService = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(p.getStatoCivile());
					CsTbStatoCivile statoCivile = configurazioneService.getStatoCivileByIdOrigCet(dto);
					if(statoCivile != null) {
						anagraficaBean.getStatoCivileGestBean().setItemSelezionato(statoCivile.getCod() + "|" + statoCivile.getDescrizione());
						anagraficaBean.getStatoCivileGestBean().aggiungiSelezionato();
						anagraficaBean.getStatoCivileGestBean().setMaxActiveComponents(1);
						anagraficaBean.getStatoCivileGestBean().salva();
						anagraficaBean.getStatoCivileGestBean().setWarningMessage("Se possibile impostare correttamente le date");
					}
				}
				
				impostaMedicoPersona(p.getCodiceRegionaleMedico());
				
				
			}
		
		
			FacesContext.getCurrentInstance().getExternalContext().redirect("scheda.faces");
		
		} catch (Exception e) {
			addError("Errore", "Errore durante il caricamento dell'anagrafica");
			logger.error("", e);
		}
	}
	
	private void impostaMedicoPersona(String codRegMedico) throws  CsUiCompException{
		if(codRegMedico!= null && !codRegMedico.trim().isEmpty()){
			CsCMedico medico = getMedicoByCodRegionale(codRegMedico);
			addToListaMediciSoggetto(medico);
		}
	}
	

	public void addToListaMediciSoggetto(CsCMedico medico){
		
		if(medico!=null){//se ho trovato il medico sulla vista e l'ho aggiunto a CsCMedici, lo setto sulla scheda 
			anagraficaBean.getMediciGestBean().getLstItems();
			String denominazione = (medico.getCognome() == null ? "" : medico.getCognome()).trim();
			String nome = (medico.getNome() == null ? "" : medico.getNome()).trim();
			if (!nome.equals("")) {
				if (!denominazione.equals("")) {
					denominazione += " ";
				}
				denominazione += nome;
			}		
			anagraficaBean.getMediciGestBean().setItemSelezionato(String.valueOf(medico.getId())+"|"+denominazione);
			anagraficaBean.getMediciGestBean().aggiungiSelezionato();
			anagraficaBean.getMediciGestBean().salva();
		}
	
	}
	
	public boolean isCodFiscaleInCs(String cf){
		BaseDTO dto = new BaseDTO();
    	fillEnte(dto);
    	dto.setObj(cf);  
    	try {
    		AccessTableSoggettoSessionBeanRemote soggettiService = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface(
    				"CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
    		return soggettiService.esisteSchedaSoggettoByCF(dto);
    		
    	} catch(NamingException e) {
    		logger.error(e.getMessage(), e);
		}
    	return false;
	}
	
	public void nuovaDaAnagrafeWrapper(String id) {
		if(id.trim().startsWith("SANITARIA")){
			nuovaDaAnagrafeSanitaria(id.replace("SANITARIA", ""));
		}
		else{
			nuovaDaAnagrafe(id);
		}
	}
	
	public void nuovaDaAnagrafe(String id) {
		
		if(id == null || "".equals(id)) {
			addWarning("Scegliere un soggetto o creare una cartella vuota", "");
			return;
		}
		
		initialize(null);
		modifica();
		
		try {
			//precarico anagrafica
			AnagrafeService anagrafeService = (AnagrafeService) getEjb("CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
			RicercaSoggettoAnagrafeDTO ricercaDto = new RicercaSoggettoAnagrafeDTO();
			fillEnte(ricercaDto);
			ricercaDto.setIdVarSogg(id);
			SitDPersona p = anagrafeService.getPersonaById(ricercaDto);
			
			if(p != null) {
				
				if(isCodFiscaleInCs(p.getCodfisc())){
					addWarning("Operazione non consentita","Esiste già una cartella associata al Cod.Fiscale "+p.getCodfisc());
					disableHead=false;
					return;
				}
				
				if(p.getDataMor()!=null && p.getDataMor().before(new Date())){
					addWarning("Non è possibile creare una nuova cartella","Il soggetto selezionato è deceduto il "+ddMMyyyy.format(p.getDataMor()));
					disableHead=false;
					return;
				}
				
				ComponenteFamigliaDTO compDto = new ComponenteFamigliaDTO();
				compDto.setPersona(p);
				fillEnte(compDto);
				compDto = anagrafeService.fillInfoAggiuntiveComponente(compDto);
				
				//anagrafica
				DatiAnaBean anaBean = new DatiAnaBean();
				anaBean.setCodiceFiscale(p.getCodfisc());
				anaBean.setCognome(p.getCognome());
				anaBean.setNome(p.getNome());
				anaBean.setDataNascita(p.getDataNascita());
				SessoBean sb = new SessoBean(p.getSesso());
				anaBean.setDatiSesso(sb);
				anagraficaBean.setDatiAnaBean(anaBean);
				
				//nascita
				if("ITALIA".equals(compDto.getDesStatoNas())) {
					ComuneBean comuneBean = new ComuneBean(compDto.getCodComNas(), compDto.getDesComNas(), compDto.getSiglaProvNas());
					anagraficaBean.getComuneNazioneNascitaMan().getComuneNascitaMan().setComune(comuneBean);
				} else {
					AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(compDto.getIstatStatoNas(), compDto.getDesStatoNas());
					anagraficaBean.getComuneNazioneNascitaMan().setValue(anagraficaBean.getComuneNazioneNascitaMan().getNazioneValue());
					anagraficaBean.getComuneNazioneNascitaMan().getNazioneMan().setNazione(amTabNazioni);
				}
				
				//indirizzo res
				if(p.getCodfisc() != null) {
					AccessTablePersonaCiviciSessionBeanRemote personaCiviciService = (AccessTablePersonaCiviciSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTablePersonaCiviciSessionBean");
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(p.getCodfisc());
					CsAIndirizzo indResidenza = personaCiviciService.getIndirizzoResidenzaByCodFisc(dto);
					if(indResidenza != null) {
						indResidenza.setCsTbTipoIndirizzo(anagraficaBean.getResidenzaCsaMan().getTipoIndirizzoResidenza());
						List<CsAIndirizzo> listaIndirizzi = new ArrayList<CsAIndirizzo>();
						listaIndirizzi.add(indResidenza);
						anagraficaBean.getResidenzaCsaMan().setLstIndirizzi(listaIndirizzi);
						anagraficaBean.getResidenzaCsaMan().setLstIndirizziOld(listaIndirizzi);
						anagraficaBean.getResidenzaCsaMan().setWarningMessage("Se possibile impostare correttamente le date");
					}
				}
				
				//stato civile
				if(p.getStatoCivile() != null) {
					AccessTableConfigurazioneSessionBeanRemote configurazioneService = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(p.getStatoCivile());
					CsTbStatoCivile statoCivile = configurazioneService.getStatoCivileByIdOrigCet(dto);
					if(statoCivile != null) {
						anagraficaBean.getStatoCivileGestBean().setItemSelezionato(statoCivile.getCod() + "|" + statoCivile.getDescrizione());
						anagraficaBean.getStatoCivileGestBean().aggiungiSelezionato();
						anagraficaBean.getStatoCivileGestBean().setMaxActiveComponents(1);
						anagraficaBean.getStatoCivileGestBean().salva();
						anagraficaBean.getStatoCivileGestBean().setWarningMessage("Se possibile impostare correttamente le date");
					}
				}
				
				try{
					//Cerca medico del soggetto in anagrafe regionale
					AnagRegUser anagRegUser = getAnagRegUser();
					URL wsdlLocation = getAnagRegWebServiceWSDLLocation();
					if(anagRegUser!=null && wsdlLocation!=null){
						AnagrafeClient anag = new AnagrafeClient(wsdlLocation);
						RicercaAnagraficaBean rb = new RicercaAnagraficaBean();
						rb.setUsername(anagRegUser.getUsername());
						rb.setPassword(anagRegUser.getPassword());
						anag.openSession(rb);
						RicercaAnagraficaBean rab= new RicercaAnagraficaBean();
						rab.setCognomePaziente(p.getCognome());
						rab.setNomePaziente(p.getNome());
						List<PersonaFindResult> listaPersoneCognomeNome=anag.findCognomeNome(rab);
						if(listaPersoneCognomeNome!=null){
							for(PersonaFindResult persona : listaPersoneCognomeNome){
								if(persona.getCodfisc().trim().toUpperCase().equals(p.getCodfisc().trim().toUpperCase())){
									String idPaziente=persona.getIdPaziente();
									if(idPaziente!=null){
										rab.setIdPaziente(idPaziente);
									}
									persona=anag.getDatiAnagraficiBaseByIdPaziente(rab);
									if(persona!=null){
										impostaMedicoPersona(persona.getCodiceRegionaleMedico());
									}
								}
							}
						}
						anag.closeSession();
					}
				}catch (Exception e) {
					addError("Errore", "Errore recupero medico da anagrafe regionale");
					logger.error("Errore recupero medico da anagrafe regionale"+e.getMessage(), e);
				}
			}
		
			FacesContext.getCurrentInstance().getExternalContext().redirect("scheda.faces");
		
		} catch (Exception e) {
			addError("Errore", "Errore durante il caricamento dell'anagrafica");
			logger.error("", e);
		}
	}
	
	public void nuovaDaSegretariatoSoc(Long id) {
		
		if(id == null) {
			addWarning("Scegliere una scheda", "");
			return;
		}
		
		initialize(null);
		modifica();
		
		try {
			//precarico anagrafica
			SsSchedaSessionBeanRemote ssSchedaSegrService = (SsSchedaSessionBeanRemote) getEjb("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			
			it.webred.ss.ejb.dto.BaseDTO bDto = new it.webred.ss.ejb.dto.BaseDTO();
			CsUiCompBaseBean.fillEnte(bDto);
			bDto.setObj(id);
			SsScheda ssScheda = ssSchedaSegrService.readScheda(bDto);
			schedaSegrBean.setSsScheda(ssScheda);
			bDto.setObj(ssScheda.getSegnalato());
    		SsSchedaSegnalato segnalato = ssSchedaSegrService.readSegnalatoById(bDto);
			
			if(segnalato != null) {
				
				schedaSegrBean.setSsSchedaSegnalato(segnalato);
				SsAnagrafica ana = segnalato.getAnagrafica();
				
				//anagrafica
				DatiAnaBean anaBean = new DatiAnaBean();
				anaBean.setCodiceFiscale(ana.getCf());
				anaBean.setCognome(ana.getCognome());
				anaBean.setNome(ana.getNome());
				anaBean.setDataNascita(ana.getData_nascita());
				SessoBean sb = new SessoBean(ana.getSesso());
				anaBean.setDatiSesso(sb);
				anaBean.setCittadinanza(ana.getCittadinanza());
				anaBean.setCittadinanza2(ana.getCittadinanza2());
				anaBean.setCittadinanzaAcq(ana.getCittadinanzaAcq());
				
				anaBean.setCellulare(segnalato.getCel());
				anaBean.setEmail(segnalato.getEmail());
				anaBean.setTelefono(segnalato.getTelefono());
				anagraficaBean.setDatiAnaBean(anaBean);
				
				//nascita
				if(ana.getComuneNascitaCod()!=null||ana.getStatoNascitaCod()!=null){
				
					if(ana.getComuneNascitaCod()!=null){
						ComuneBean comuneBean = new ComuneBean(ana.getComuneNascitaCod(), ana.getComuneNascitaDes(), ana.getProvNascitaCod());
						anagraficaBean.getComuneNazioneNascitaMan().getComuneNascitaMan().setComune(comuneBean);
					}
					if(ana.getStatoNascitaCod()!=null){
						AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(ana.getStatoNascitaCod(), ana.getStatoNascitaDes());
						anagraficaBean.getComuneNazioneNascitaMan().setValue(anagraficaBean.getComuneNazioneNascitaMan().getNazioneValue());
						anagraficaBean.getComuneNazioneNascitaMan().getNazioneMan().setNazione(amTabNazioni);
					}
					
				}else{ //Gestione Precendente
					
					String comuneNascita = ana.getLuogoDiNascita();			
					if(comuneNascita!=null){
						AccessTableComuniSessionBeanRemote comuniService = (AccessTableComuniSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableComuniSessionBean");
						List<AmTabComuni> lstComuni = comuniService.getComuniByDenomContains(comuneNascita,false);
						if(lstComuni!=null && !lstComuni.isEmpty() && lstComuni.size()==1) {
							AmTabComuni comune = lstComuni.get(0);
							ComuneBean comuneBean = new ComuneBean(comune);
							anagraficaBean.getComuneNazioneNascitaMan().getComuneNascitaMan().setComune(comuneBean);
						} else {
							AccessTableNazioniSessionBeanRemote nazioniService = (AccessTableNazioniSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableNazioniSessionBean");
							List<AmTabNazioni> lstNazioni = nazioniService.getNazioniByDenomContains(comuneNascita);
							if(lstComuni!=null && !lstNazioni.isEmpty() && lstNazioni.size()==1) {
								AmTabNazioni amTabNazioni = lstNazioni.get(0);
								anagraficaBean.getComuneNazioneNascitaMan().setValue(anagraficaBean.getComuneNazioneNascitaMan().getNazioneValue());
								anagraficaBean.getComuneNazioneNascitaMan().getNazioneMan().setNazione(amTabNazioni);
							}
						}
					}
				}
				
				//indirizzo res
				SsIndirizzo residenzaSegnalato = segnalato.getResidenza();
				if(residenzaSegnalato != null) {
					String viaCompleta = residenzaSegnalato.getVia();
					//Provo a fare split per gli indirizzi generati automaticamente
					String viaResSegnalato = viaCompleta!=null && viaCompleta.contains(", ") ? viaCompleta.substring(0, viaCompleta.lastIndexOf(",")) : viaCompleta;
					String civicoResSegnalato = viaCompleta!=null && viaCompleta.contains(", ") ? viaCompleta.substring(viaCompleta.lastIndexOf(",")+1).trim() : null; 
					//residenzaSegnalato.getNumero();
					boolean datiResSegnPresenti = viaResSegnalato != null && civicoResSegnalato!=null;
					CsAIndirizzo indirizzoRes = null;
					if(datiResSegnPresenti) {
						AccessTablePersonaCiviciSessionBeanRemote personaCiviciService = (AccessTablePersonaCiviciSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTablePersonaCiviciSessionBean");
						BaseDTO dto = new BaseDTO();
						fillEnte(dto);
						dto.setObj(viaResSegnalato);
						dto.setObj2(civicoResSegnalato);
						indirizzoRes = personaCiviciService.getIndirizzoResidenzaByNomeCiv(dto);
					} 
					
					if(indirizzoRes == null && (viaResSegnalato != null || civicoResSegnalato != null)) {
						indirizzoRes = new CsAIndirizzo();
						CsAAnaIndirizzo indirizzoAna = new CsAAnaIndirizzo();
						indirizzoAna.setIndirizzo(viaResSegnalato);
						if(civicoResSegnalato != null)
							indirizzoAna.setCivicoNumero(civicoResSegnalato.toString());
						if(residenzaSegnalato.getComune() != null) {
							LuoghiService luoghiService = (LuoghiService) getEjb("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");
							AmTabComuni comune = luoghiService.getComuneItaByIstat(residenzaSegnalato.getComune());
							if(comune != null) {
							indirizzoAna.setProv(comune.getSiglaProv());
							indirizzoAna.setComCod(comune.getCodIstatComune());
							indirizzoAna.setComDes(comune.getDenominazione());
							indirizzoAna.setStatoCod("1");
							indirizzoAna.setStatoDes("ITALIA");
							}
						}
						indirizzoRes.setDataInizioApp(new Date());
						indirizzoRes.setCsAAnaIndirizzo(indirizzoAna);
					}
	
					if(indirizzoRes != null) {
						indirizzoRes.setCsTbTipoIndirizzo(anagraficaBean.getResidenzaCsaMan().getTipoIndirizzoResidenza());
						List<CsAIndirizzo> listaIndirizzi = new ArrayList<CsAIndirizzo>();
						listaIndirizzi.add(indirizzoRes);
						anagraficaBean.getResidenzaCsaMan().setLstIndirizzi(listaIndirizzi);
						anagraficaBean.getResidenzaCsaMan().setLstIndirizziOld(listaIndirizzi);
					}
				}
				
				//stato civile
				if(segnalato.getAnagrafica().getStato_civile() != null) {
					AccessTableConfigurazioneSessionBeanRemote configurazioneService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(segnalato.getAnagrafica().getStato_civile());
					CsTbStatoCivile statoCivile = configurazioneService.getStatoCivileByDescrizione(dto);
					if(statoCivile != null) {
						anagraficaBean.getStatoCivileGestBean().setItemSelezionato(statoCivile.getCod() + "|" + statoCivile.getDescrizione());
						anagraficaBean.getStatoCivileGestBean().aggiungiSelezionato();
						anagraficaBean.getStatoCivileGestBean().salva();
					}
				}
				
				//medico
				String sMedico = segnalato.getMedico();
				CsCMedico medico = null;
				if(sMedico!= null) {
					AccessTableMediciSessionBeanRemote medicoService = (AccessTableMediciSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableMediciSessionBean");
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					try {
						Long idMedico = new Long(sMedico);
						dto.setObj(idMedico);
						medico = medicoService.findMedicoById(dto);
						
					} catch (Exception e) {
						logger.error("SOGGETTO SCHEDA SEGR: " + ana.getCf() + " dato medico("+sMedico+") non è un ID, si procede a ricercare tramite descrizione");
					}
						
					//Se non trovo per id, lo ricerco per descrizione
					if(medico==null){
						dto.setObj(sMedico.toUpperCase());
						medico = medicoService.findMedicoByDescrizione(dto);
					}
						
					addToListaMediciSoggetto(medico);
		
				}
			}
		
			FacesContext.getCurrentInstance().getExternalContext().redirect("scheda.faces");
		
		} catch (Exception e) {
			addError("Errore", "Errore durante il caricamento dell'anagrafica");
			logger.error("", e);
		}
	}
	
	public void carica(DatiCasoBean selectedCaso) {
		initializePresaInCarico(selectedCaso);

		logger.debug("*** INIZIO SchedaBean.carica ....");
		
		BaseDTO b = new BaseDTO();
		fillEnte(b);
		b.setObj(selectedCaso.getSoggetto().getAnagraficaId());
		
		CsASoggettoLAZY soggetto = soggettoService.getSoggettoById(b);

		
		//CsASoggetto soggetto = selectedCaso.getSoggetto();
		
		if(soggetto != null) {
			initialize(soggetto);
			
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("scheda.faces");
			} catch (IOException e) {
				addError("Errore", "Errore durante il reindirizzamento alla scheda Caso");
			}
		} else addWarningFromProperties("seleziona.warning");
		
		logger.debug("*** FINE SchedaBean.carica ....");

		
	}
	
	public void carica(CsASoggettoLAZY soggetto) {
		if(soggetto != null) {
			initialize(soggetto); 
			initializePresaInCarico(soggetto);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("scheda.faces");
			} catch (IOException e) {
				addError("Errore", "Errore durante il reindirizzamento alla scheda Caso");
			}
		} else addWarningFromProperties("seleziona.warning");
	}
	
	public void initializePresaInCarico(DatiCasoBean selectedCaso){
		logger.info("Inizializza PresaInCarico per il caso:"+selectedCaso.getSoggetto().getAnagraficaId());
		presaInCaricoBean = new PresaInCaricoBean();
		presaInCaricoBean.initialize(selectedCaso);
		presaInCaricoBean.setReadOnly(isDisableModificaCartella());
	}
	
	public void initializePresaInCarico(CsASoggettoLAZY s) {
		presaInCaricoBean = null;
		if(s!=null){
			logger.info("Inizializza PresaInCarico per il caso:"+s.getAnagraficaId());
			presaInCaricoBean = new PresaInCaricoBean();
			presaInCaricoBean.initialize(s);
			presaInCaricoBean.setReadOnly(isDisableModificaCartella());
			RequestContext.getCurrentInstance().update("schedaPICForm");
		}
	}
	
	
	public void initialize(CsASoggettoLAZY soggetto) {
		logger.debug("*** INIZIO SchedaBean.initialize ....");

		if(soggetto != null && soggetto.getAnagraficaId() != null && soggetto.getAnagraficaId() != 0) {
			
			Long soggettoId = soggetto.getAnagraficaId();
			getSession().setAttribute("soggettoId", soggettoId);
			getSession().setAttribute("soggetto", soggetto);

			Long casoId = soggetto.getCsACaso().getId();
			
			initializeProperties(soggettoId,casoId);
			noteBean.initialize(soggetto);
			
		} else {
			
			getSession().setAttribute("soggettoId", null);
			getSession().setAttribute("soggetto", null);
			
			initializeProperties(null,null);
			noteBean.initialize(null);
			initializePresaInCarico((CsASoggettoLAZY)null);
		}
		
		checkPermessi(soggetto);
		
		disableHead=false;
		modificaButt=true;
		attivaSalvataggio=false;
		identificativoScheda = soggetto!=null ? soggetto.getCsACaso().getIdentificativo() : null;
		
		logger.debug("*** FINE SchedaBean.initialize ....");

	}
	
	private void initializeProperties(Long soggettoId, Long casoId){
		anagraficaBean.initialize(soggettoId);
		datiDisabilitaBean.initialize(soggettoId);
		datiInvaliditaBean.initialize(soggettoId);
		parentiBean.initialize(soggettoId);
		datiSocialiBean.initialize(soggettoId, casoId);
		datiTribunaleBean.initialize(soggettoId);
		operatoriBean.initialize(soggettoId);
		schedaSegrBean.initialize(soggettoId);
	}
	

	public boolean isRenderNuovaCartella() {
		return checkPermesso(DataModelCostanti.PermessiCaso.CREAZIONE_CASO);
	}
	
	private void checkPermessi(CsASoggettoLAZY soggetto) {
		
		setDisableModificaCartella(false);
		if(soggetto != null) {
			setDisableModificaCartella(true);
			CsOOperatoreSettore opSettore = (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
			AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(soggetto.getCsACaso().getId());
			List<CsACasoOpeTipoOpe> listaCasoOpeTipoOpe = casoService.getListaOperatoreTipoOpByCasoId(dto);
			boolean isCasoOperatore = false;
			boolean respExist = false;
			for(CsACasoOpeTipoOpe casoOpeTipoOpe: listaCasoOpeTipoOpe) {
				if(casoOpeTipoOpe.getDataFineApp().after(new Date())) {
					if(casoOpeTipoOpe.getFlagResponsabile() != null && casoOpeTipoOpe.getFlagResponsabile().booleanValue())
						respExist = true;
					if(casoOpeTipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getId() == opSettore.getId())
						isCasoOperatore = true;
				}
			}
			
			/*il caso è in modifica se:
			*	ho il permesso modifica tutti i casi settore
			*	se il caso non ha responsabile e l'operatore è il creatore del caso
			*	se sono presente nella lista degli operatori per quel caso
			*/
			if(checkPermesso(DataModelCostanti.PermessiCaso.MODIFICA_CASI_SETTORE) 
					|| (!respExist && soggetto.getCsACaso().getUserIns().equals(opSettore.getCsOOperatore().getUsername())
					|| isCasoOperatore))
				setDisableModificaCartella(false); 
		}
		
		if(soggetto == null) {
			renderTabAnagrafica = true;
			renderTabDatiSociali = false;
			renderTabInvalidita = false;
			renderTabParenti = false;
			renderTabDisabilita = false;
			renderTabTribunale = false;
			renderTabServizi = false;
			renderTabContributi = false;
			renderTabOperatori = false;
			renderTabNote = false;
		} else if(checkPermesso(DataModelCostanti.PermessiCaso.VISUALIZZAZIONE_DATI_RISERV)) {
			renderTabAnagrafica = true;
			renderTabDatiSociali = true;
			renderTabInvalidita = true;
			renderTabParenti = true;
			renderTabDisabilita = true;
			renderTabTribunale = true;
			renderTabServizi = false;
			renderTabContributi = false;
			renderTabOperatori = true;
			renderTabNote = true;
		} else {
			renderTabAnagrafica = true;
			renderTabDatiSociali = false;
			renderTabInvalidita = false;
			renderTabParenti = true;
			renderTabDisabilita = false;
			renderTabTribunale = false;
			renderTabServizi = false;
			renderTabContributi = false;
			renderTabOperatori = true;
			renderTabNote = false;
		}
	}

	public void salva(){
		//salvo tutti i bean
		if(anagraficaBean.getSoggettoId()==null){
			if(isCodFiscaleInCs(anagraficaBean.getDatiAnaBean().getCodiceFiscale().trim().toUpperCase())){
					addWarning("Operazione non consentita","Esiste già una cartella associata al Cod.Fiscale "+anagraficaBean.getDatiAnaBean().getCodiceFiscale());
					disableHead=false;
					return;
			}
		}
		
		boolean salvato = (this.renderTabAnagrafica ? anagraficaBean.salva() : true) &&
				          (this.renderTabParenti ? parentiBean.salva() : true) &&
				          (this.renderTabDatiSociali ? datiSocialiBean.salva() : true) &&
				          (this.renderTabInvalidita ? datiInvaliditaBean.salva() : true) &&
				          (this.renderTabDisabilita ? datiDisabilitaBean.salva() :true) &&
				          (this.renderTabTribunale ? datiTribunaleBean.salva() : true) &&
				          (this.renderTabOperatori ? operatoriBean.salva() : true) &&
				          (this.renderTabNote ? noteBean.salva() : true);
		if(salvato){
			//riinizializzo la scheda
			CsASoggettoLAZY sogg = anagraficaBean.getSoggettoSalvato();
			this.initialize(sogg);
			this.initializePresaInCarico(sogg);
			this.identificativoScheda=sogg.getCsACaso().getIdentificativo();
			
			//refresh dei tab per farli renderizzare  
			RequestContext.getCurrentInstance().update(this.getIdTabScheda());
					
			addInfoFromProperties("salva.ok");
			disabilitaModifiche();
		}
	}
	
	public ActionListener getCloseDialog() {
		BaseDTO b = new BaseDTO();
		fillEnte(b);
		b.setObj(anagraficaBean.getSoggettoId());

		CsASoggettoLAZY soggetto = soggettoService.getSoggettoById(b);

		if(soggetto != null) {
			initialize(soggetto);
			
			this.initializePresaInCarico(soggetto);
			//refresh dei tab per farli renderizzare
			RequestContext.getCurrentInstance().update("frmSalva");

		}
		return this.presaInCaricoBean.getCloseDialog();
	}
	
	public void modifica(){
		attivaSalvataggio=true;
		modificaButt=false;
		disableHead=true;
	 }
	
	public void disabilitaModifiche(){
		//esco da modalità modifica
		disableHead=false;
		attivaSalvataggio=false;
		modificaButt=true;
	}
	
	public AnagraficaBean getAnagraficaBean() {
		return anagraficaBean;
	}

	public void setAnagraficaBean(AnagraficaBean anagraficaBean) {
		this.anagraficaBean = anagraficaBean;
	}

	public PresaInCaricoBean getPresaInCaricoBean() {
		return presaInCaricoBean;
	}

	public void setPresaInCaricoBean(PresaInCaricoBean presaInCaricoBean) {
		this.presaInCaricoBean = presaInCaricoBean;
	}

	public DatiDisabilitaBean getDatiDisabilitaBean() {
		return datiDisabilitaBean;
	}

	public void setDatiDisabilitaBean(DatiDisabilitaBean datiDisabilitaBean) {
		this.datiDisabilitaBean = datiDisabilitaBean;
	}

	public DatiInvaliditaBean getDatiInvaliditaBean() {
		return datiInvaliditaBean;
	}

	public void setDatiInvaliditaBean(DatiInvaliditaBean datiInvaliditaBean) {
		this.datiInvaliditaBean = datiInvaliditaBean;
	}

	public ParentiBean getParentiBean() {
		return parentiBean;
	}

	public void setParentiBean(ParentiBean parentiBean) {
		this.parentiBean = parentiBean;
	}

	public DatiSocialiBean getDatiSocialiBean() {
		return datiSocialiBean;
	}

	public void setDatiSocialiBean(DatiSocialiBean datiSocialiBean) {
		this.datiSocialiBean = datiSocialiBean;
	}

	public DatiTribunaleBean getDatiTribunaleBean() {
		return datiTribunaleBean;
	}

	public void setDatiTribunaleBean(DatiTribunaleBean datiTribunaleBean) {
		this.datiTribunaleBean = datiTribunaleBean;
	}

	public boolean isDisableModificaCartella() {
		return disableModificaCartella;
	}

	public void setDisableModificaCartella(boolean disableModifica) {
		this.disableModificaCartella = disableModifica;
	}

	public OperatoriBean getOperatoriBean() {
		return operatoriBean;
	}

	public void setOperatoriBean(OperatoriBean operatoriBean) {
		this.operatoriBean = operatoriBean;
	}

	public SchedaSegrBean getSchedaSegrBean() {
		return schedaSegrBean;
	}

	public void setSchedaSegrBean(SchedaSegrBean schedaSegrBean) {
		this.schedaSegrBean = schedaSegrBean;
	}

	public NoteBean getNoteBean() {
		return noteBean;
	}

	public void setNoteBean(NoteBean noteBean) {
		this.noteBean = noteBean;
	}

	public boolean isRenderTabAnagrafica() {
		return renderTabAnagrafica;
	}

	public boolean isRenderTabDatiSociali() {
		return renderTabDatiSociali;
	}

	public boolean isRenderTabInvalidita() {
		return renderTabInvalidita;
	}

	public boolean isRenderTabParenti() {
		return renderTabParenti;
	}

	public boolean isRenderTabDisabilita() {
		return renderTabDisabilita;
	}

	public boolean isRenderTabTribunale() {
		return renderTabTribunale;
	}

	public boolean isRenderTabServizi() {
		return renderTabServizi;
	}

	public boolean isRenderTabContributi() {
		return renderTabContributi;
	}

	public boolean isRenderTabOperatori() {
		return renderTabOperatori;
	}

	public boolean isRenderTabNote() {
		return renderTabNote;
	}

	public String getIdTabScheda() {
		return idTabScheda;
	}

	public void setIdTabScheda(String idTabScheda) {
		this.idTabScheda = idTabScheda;
	}

	public boolean isDisableHead() {
		return disableHead;
	}

	public void setDisableHead(boolean disableHead) {
		this.disableHead = disableHead;
	}

	public boolean isModificaButt() {
		return modificaButt;
	}

	public void setModificaButt(boolean modificaButt) {
		this.modificaButt = modificaButt;
	}

	public boolean isAttivaSalvataggio() {
		return attivaSalvataggio;
	}

	public void setAttivaSalvataggio(boolean attivaSalvataggio) {
		this.attivaSalvataggio = attivaSalvataggio;
	}

	protected CsTbCittadinanzaAcq getCittadinanzaAcq(Long id){
    	try{
    	AccessTableConfigurazioneSessionBeanRemote service =
    		(AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");				
    	if(id!=null){
    		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
        	fillEnte(d);
    		d.setObj(new Long(id));
    		CsTbCittadinanzaAcq fa = service.getCittadinanzaAcqById(d);
    		return fa;
    	}
		
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		addError("lettura.error","Impossibile recuperare la tipologia di Cittadinanza Acquisita");
		}
    	return null;
   }

	public Long getIdentificativoScheda() {
		return identificativoScheda;
	}

	public void setIdentificativoScheda(Long identificativoScheda) {
		this.identificativoScheda = identificativoScheda;
	}

}
