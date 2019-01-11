package it.webred.ss.web.bean.util;

import it.webred.cs.csa.ejb.client.AccessTablePersonaCiviciSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.interfaces.IUserSearch;
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
import it.webred.siso.ws.client.anag.exception.AnagrafeException;
import it.webred.siso.ws.client.anag.exception.AnagrafeSessionException;
import it.webred.ss.web.bean.wizard.NuovaSchedaWizard;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.naming.NamingException;

import org.primefaces.event.SelectEvent;

public class UserSearchBean extends CsUiCompBaseBean implements IUserSearch{

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
	
	private String widgetVar = "userSearchVar";
	//private String idSoggetto;
	private DatiUserSearchBean selSoggetto;
	private Integer maxResult = 15;
	
	private NuovaSchedaWizard nuovaSchedaWizard;
	
	private UserSearchBean() {}
	
	public UserSearchBean(NuovaSchedaWizard nuovaSchedaWizard) {
		this.nuovaSchedaWizard = nuovaSchedaWizard;
	}

	@Override
	public List<DatiUserSearchBean> getLstSoggetti(String query) {
		
		List<DatiUserSearchBean> listAutocomplete = new ArrayList<DatiUserSearchBean>();
		RicercaSoggettoAnagrafeDTO rsDto = new RicercaSoggettoAnagrafeDTO();
		fillEnte(rsDto);
		rsDto.setDenom(query);
		//rsDto.setMaxResult(maxResult);
		try {
			
			AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface("CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
			
			List<SitDPersona> list = anagrafeService.getListaPersoneByDenominazione(rsDto);
			int contatore = 0;
			for(SitDPersona s: list){
				boolean emigrato = s.getDataEmi()!=null && (s.getDataImm()==null || s.getDataImm().before(s.getDataEmi()));
				if((!emigrato) && s.getDataMor()==null){
					DatiUserSearchBean sDto = new DatiUserSearchBean();
					sDto.setSoggetto(s);	
					String itemLabel = (s.getCognome()!=null ? s.getCognome().toUpperCase() : "" )+ " " + (s.getNome()!=null ? s.getNome().toUpperCase() : "");
					if(s.getDataNascita() != null)
						itemLabel += " nato il: " + sdf.format(s.getDataNascita());
					
				/*	if(s.getDataMor()!=null)
						itemLabel += " morto il: " + sdf.format(s.getDataMor());
					*/
					
					sDto.setItemLabel(itemLabel);
					sDto.setId(s.getId());
					listAutocomplete.add(sDto);
					contatore++;
				}
				if(contatore>=maxResult) break;
			}

			if (list.size() < maxResult)
				this.ricercaInAnagrafeSanitaria(query, list.size(), listAutocomplete);
			
		} catch (NamingException e) {
			addError("general", "caricamento.error");
			logger.error(e.getMessage(), e);
		}
		
		return listAutocomplete;
	}

	private void ricercaInAnagrafeSanitaria(String query, int listSize, List<DatiUserSearchBean> listAutocomplete){
		
		try {
			AnagRegUser anagReguser = getAnagRegUser();
			URL wsdlLocation = getAnagRegWebServiceWSDLLocation();
			if(anagReguser!=null && wsdlLocation!=null){
				List <PersonaFindResult> listAnagReg=new ArrayList <PersonaFindResult>();
				
				AnagrafeClient anag = new AnagrafeClient(wsdlLocation);
				
				RicercaAnagraficaBean rb = new RicercaAnagraficaBean();
				rb.setUsername(anagReguser.getUsername());
				rb.setPassword(anagReguser.getPassword());
				anag.openSession(rb);
				String[] queryArray=query.split(" ");
				for(int i=0;i<queryArray.length;i++){
					String subQuery1="";
					String subQuery2="";
					for(int j=0; j<=i;j++){
						subQuery1=(subQuery1+" "+queryArray[j]).trim();
					}
					for(int k=i+1; k< queryArray.length;k++){
						subQuery2=(subQuery2+" "+queryArray[k]).trim();
					}
					RicercaAnagraficaBean rab= new RicercaAnagraficaBean();
					rab.setCognomePaziente(subQuery1);
					rab.setNomePaziente(subQuery2);
					List <PersonaFindResult> l1=anag.findCognomeNome(rab);
					if(l1!=null && !l1.isEmpty()){
						listAnagReg.addAll(l1);
					}
					rab.setCognomePaziente(subQuery2);
					rab.setNomePaziente(subQuery1);
					List <PersonaFindResult> l2=anag.findCognomeNome(rab);
					if(l2!=null && !l2.isEmpty()){
						listAnagReg.addAll(l2);
					}
					
					
				}
				int maxResultAnagReg=maxResult-listSize;
				for(int i=0; i<maxResultAnagReg;i++){
					if(i>=listAnagReg.size()){
						break;
					}
					PersonaFindResult s=listAnagReg.get(i);
					DatiUserSearchBean sDto = new DatiUserSearchBean();
					sDto.setSoggetto(s);
					String itemLabel = s.getCognome().toUpperCase() + " "
							+ s.getNome().toUpperCase();
					if (s.getDataNascita() != null)
						itemLabel += " nato il: " + sdf.format(s.getDataNascita())+". *Ricavato da anagrafe sanitaria regionale*";
					sDto.setItemLabel(itemLabel);
					sDto.setId("SANITARIA"+s.getIdPaziente());
					listAutocomplete.add(sDto);
					
				}
				anag.closeSession();
			}
			
		} 
		catch (AnagrafeException e) {
			logger.error(e.getMessage(), e);
		} catch (AnagrafeSessionException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	@Override
	public void handleChangeUser(AjaxBehaviorEvent event) {
		SelectEvent se = (SelectEvent) event;
		String id = ((DatiUserSearchBean)se.getObject()).getId();
		
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
		
		try {
			//precarico anagrafica
			AnagrafeService anagrafeService = (AnagrafeService) getEjb("CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
			RicercaSoggettoAnagrafeDTO ricercaDto = new RicercaSoggettoAnagrafeDTO();
			fillEnte(ricercaDto);
			ricercaDto.setIdVarSogg(id);
			SitDPersona p = anagrafeService.getPersonaById(ricercaDto);
			
			if(p != null) {
				
				if(p.getDataMor()!=null && p.getDataMor().before(new Date())){
					addWarning("Non è possibile creare una nuova scheda","Il soggetto selezionato è deceduto il "+ddMMyyyy.format(p.getDataMor()));
					return;
				}
				
				this.nuovaSchedaWizard.getSegnalante().setCognome(p.getCognome());
				this.nuovaSchedaWizard.getSegnalante().setNome(p.getNome());
				this.nuovaSchedaWizard.getSegnalante().setCf(p.getCodfisc());
				this.nuovaSchedaWizard.getSegnalante().setDataNascita(p.getDataNascita());
				SessoBean sb = new SessoBean(p.getSesso());
				this.nuovaSchedaWizard.getSegnalante().setDatiSesso(sb);
				
				ComponenteFamigliaDTO compDto = new ComponenteFamigliaDTO();
				compDto.setPersona(p);
				fillEnte(compDto);
				compDto = anagrafeService.fillInfoAggiuntiveComponente(compDto);
				
				//nascita
				if("ITALIA".equals(compDto.getDesStatoNas())) {
					ComuneBean comuneBean = new ComuneBean(compDto.getCodComNas(), compDto.getDesComNas(), compDto.getSiglaProvNas());
					this.nuovaSchedaWizard.getSegnalante().getComuneNazioneNascitaMan().getComuneNascitaMan().setComune(comuneBean);
				} else {
					AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(compDto.getIstatStatoNas(), compDto.getDesStatoNas());
					this.nuovaSchedaWizard.getSegnalante().getComuneNazioneNascitaMan().setValue(this.nuovaSchedaWizard.getSegnalante().getComuneNazioneNascitaMan().getNazioneValue());
					this.nuovaSchedaWizard.getSegnalante().getComuneNazioneNascitaMan().getNazioneMan().setNazione(amTabNazioni);
				}
				
				//indirizzo res
				if(p.getCodfisc() != null) {
					AccessTablePersonaCiviciSessionBeanRemote personaCiviciService = (AccessTablePersonaCiviciSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTablePersonaCiviciSessionBean");
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(p.getCodfisc());
					CsAIndirizzo indResidenza = personaCiviciService.getIndirizzoResidenzaByCodFisc(dto);
					if(indResidenza != null) {
						this.nuovaSchedaWizard.getSegnalante().setIndirizzo(indResidenza.getCsAAnaIndirizzo().getIndirizzo() +
								", " + indResidenza.getCsAAnaIndirizzo().getComDes() + " (" + indResidenza.getCsAAnaIndirizzo().getProv() + ") " +
								", " + indResidenza.getCsAAnaIndirizzo().getStatoDes());
						try {
							this.nuovaSchedaWizard.getSegnalante().setNumero(Integer.valueOf(indResidenza.getCsAAnaIndirizzo().getCivicoNumero()));
						} catch (NumberFormatException e) { }
						ComuneBean comune = new ComuneBean(indResidenza.getCsAAnaIndirizzo().getComCod(), indResidenza.getCsAAnaIndirizzo().getComDes(), indResidenza.getCsAAnaIndirizzo().getProv());
						this.nuovaSchedaWizard.getSegnalante().setComune(comune);
					}
				}
				
				//stato civile
				CsTbStatoCivile csTbStatoCivile = this.nuovaSchedaWizard.getStatoCivileByIdExtCeT(p.getStatoCivile());
				this.nuovaSchedaWizard.getSegnalante().setCodStatoCivile(csTbStatoCivile!=null ? csTbStatoCivile.getCod() : null);
			}
		} catch (Exception e) {
			addError("Errore", "Errore durante il caricamento dell'anagrafica");
			logger.error("", e);
		}
	}
	
	public void nuovaDaAnagrafeSanitaria(String id) {
		if(id == null || "".equals(id)) {
			addWarning("Scegliere un soggetto o creare una cartella vuota", "");
			return;
		}
		
		try{
			LuoghiService luoghiService = (LuoghiService) getEjb("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");
	
			PersonaFindResult p = this.getPersonaDaAnagSanitaria(id);
			
			if(p != null) {
				
				if(p.getDataMor()!=null && p.getDataMor().before(new Date())){
					addWarning("Non è possibile creare una nuova scheda","Il soggetto selezionato è deceduto il "+ddMMyyyy.format(p.getDataMor()));
					return;
				}
				
				this.nuovaSchedaWizard.getSegnalante().setCognome(p.getCognome());
				this.nuovaSchedaWizard.getSegnalante().setNome(p.getNome());
				this.nuovaSchedaWizard.getSegnalante().setCf(p.getCodfisc());
				this.nuovaSchedaWizard.getSegnalante().setDataNascita(p.getDataNascita());
				SessoBean sb = new SessoBean(p.getSesso());
				this.nuovaSchedaWizard.getSegnalante().setDatiSesso(sb);
				
				//nascita
				AmTabComuni comuneNascita = luoghiService.getComuneItaByIstat(p.getIstatComNas());
				if(comuneNascita!=null) {
					p.setDesStatoNas("ITALIA");
					p.setIstatComNas(comuneNascita.getCodIstatComune());
					p.setDesComNas(comuneNascita.getDenominazione());
					p.setSiglaProvNas(comuneNascita.getSiglaProv());
					ComuneBean comuneBean = new ComuneBean(comuneNascita);
					this.nuovaSchedaWizard.getSegnalante().getComuneNazioneNascitaMan().getComuneNascitaMan().setComune(comuneBean);
				} else {
					AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(p.getIstatComNas(), p.getDesStatoNas());
					this.nuovaSchedaWizard.getSegnalante().getComuneNazioneNascitaMan().setValue(this.nuovaSchedaWizard.getSegnalante().getComuneNazioneNascitaMan().getNazioneValue());
					this.nuovaSchedaWizard.getSegnalante().getComuneNazioneNascitaMan().getNazioneMan().setNazione(amTabNazioni);
				}
				
				//indirizzo res	
				if(p.getCodfisc() != null) {
					AccessTablePersonaCiviciSessionBeanRemote personaCiviciService = (AccessTablePersonaCiviciSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTablePersonaCiviciSessionBean");
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(p.getCodfisc());
					CsAIndirizzo indResidenza = personaCiviciService.getIndirizzoResidenzaByCodFisc(dto);
					if(indResidenza != null) {
						this.nuovaSchedaWizard.getSegnalante().setIndirizzo(indResidenza.getCsAAnaIndirizzo().getIndirizzo());
						try {
							this.nuovaSchedaWizard.getSegnalante().setNumero(Integer.valueOf(indResidenza.getCsAAnaIndirizzo().getCivicoNumero()));
						} catch (NumberFormatException e) { }
						ComuneBean comune = new ComuneBean(indResidenza.getCsAAnaIndirizzo().getComCod(), indResidenza.getCsAAnaIndirizzo().getComDes(), indResidenza.getCsAAnaIndirizzo().getProv());
						this.nuovaSchedaWizard.getSegnalante().setComune(comune);
					}
				} else {
					this.nuovaSchedaWizard.getSegnalante().setIndirizzo(p.getIndirizzoResidenza() + ", " + p.getCivicoResidenza());
				}
				
				//stato civile
				//l'inizializzazione fatta in NuovaSchedaWizard da anagrafica sanitaria non fa l'inizilizzazione dello stato civile. 
				
			}
		} catch (Exception e) {
			addError("Errore", "Errore durante il caricamento dell'anagrafica");
			logger.error("", e);
		}
	}


	@Override
	public String getWidgetVar() {
		return widgetVar;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}

	@Override
	public Integer getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}

	public DatiUserSearchBean getSelSoggetto() {
		return selSoggetto;
	}

	public void setSelSoggetto(DatiUserSearchBean selSoggetto) {
		this.selSoggetto = selSoggetto;
	}
	
}
