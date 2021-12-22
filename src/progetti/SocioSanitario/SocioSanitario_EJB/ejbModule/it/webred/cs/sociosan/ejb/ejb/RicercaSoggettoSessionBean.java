package it.webred.cs.sociosan.ejb.ejb;

import it.webred.cs.base.CsBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.sociosan.ejb.client.CTConfigClientSessionBeanRemote;
import it.webred.cs.sociosan.ejb.client.ricercaSoggetto.RicercaSoggettoSessionBeanRemote;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.siso.esb.Medico;
import it.webred.siso.esb.client.MedicoClient;
import it.webred.siso.ws.client.agag.marche.dto.RicercaAnagraficaDTO;
import it.webred.siso.ws.client.anag.client.AnagrafeClient;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;
import it.webred.siso.ws.client.anag.client.RicercaAnagraficaBean;
import it.webred.siso.ws.client.anag.exception.AnagrafeException;
import it.webred.siso.ws.client.anag.exception.AnagrafeSessionException;
import it.webred.siso.ws.client.anag.marche.client.PersonaResult;
import it.webred.siso.ws.client.anag.marche.client.RicercaAnagraficaClient;
import it.webred.siso.ws.client.anag.marche.client.RicercaPersonaResult;
import it.webred.siso.ws.ricerca.dto.FamiliareDettaglio;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaParams;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaResult;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;


@Stateless
public class RicercaSoggettoSessionBean extends CsBaseSessionBean implements RicercaSoggettoSessionBeanRemote  {

	private static final long serialVersionUID = 1L;
	private final String ITALIA = "ITALIA";

	@EJB
	private CTConfigClientSessionBeanRemote configService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/AnagrafeServiceBean")
	protected AnagrafeService anagrafeService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/LuoghiServiceBean")
	protected LuoghiService luoghiService;
	
	@EJB(mappedName = "java:global/CarSocialeA/CarSocialeA_EJB/AccessTableConfigurazioneSessionBean")
	protected AccessTableConfigurazioneSessionBeanRemote configurazioneService;
	
	@Override
	public RicercaAnagraficaResult ricercaPerDatiAnagrafici(RicercaAnagraficaParams params){
		logger.debug("ricercaPerDatiAnagrafici:"+params.stampaParametri()); 
		if(DataModelCostanti.TipoRicercaSoggetto.DEFAULT.equalsIgnoreCase(params.getProvenienza()))
			return this.ricercaAnaComunaleInterna(params);
		else if(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE.equalsIgnoreCase(params.getProvenienza()))
			return this.ricercaAnaSanRegionaleMarche(params);
		else if(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA.equalsIgnoreCase(params.getProvenienza()))
			return this.ricercaAnaSanRegionaleUmbria(params);
		//else if(DataModelCostanti.TipoRicercaSoggetto.SIGESS.equalsIgnoreCase(params.getProvenienza()))
		//	return this.ricercaAnaSigess(params);
		else return null;
	}
	
	@Override 
	public RicercaAnagraficaResult getDettaglioPersona(RicercaAnagraficaParams params){
		logger.debug(params.stampaParametri()); 
		if(DataModelCostanti.TipoRicercaSoggetto.DEFAULT.equalsIgnoreCase(params.getProvenienza()))
			return this.ricercaAnaComunaleInterna(params);
		else if(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE.equalsIgnoreCase(params.getProvenienza()))
			return this.ricercaAnaSanRegionaleMarche(params);
		else if(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA.equalsIgnoreCase(params.getProvenienza()))
			return this.getPersonaAnaSanRegionaleUmbria(params);
		//else if(DataModelCostanti.TipoRicercaSoggetto.SIGESS.equalsIgnoreCase(params.getProvenienza()))
		//	return this.getPersonaDaAnagSigess(params);
		else return null;
	}
	
	@Override
	public RicercaAnagraficaResult getComposizioneFamiliare(RicercaAnagraficaParams params) {
		if(DataModelCostanti.TipoRicercaSoggetto.DEFAULT.equalsIgnoreCase(params.getProvenienza()))
			return loadFamigliaGIT(params);
		else if(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE.equalsIgnoreCase(params.getProvenienza()))
			return loadFamigliaSIRPS(params);
		//else if(DataModelCostanti.TipoRicercaSoggetto.SIGESS.equalsIgnoreCase(params.getProvenienza()))
		//	return loadFamigliaSIGESS(params);
		else return null;
	}
		
	private RicercaAnagraficaResult ricercaAnaSanRegionaleUmbria(RicercaAnagraficaParams params){	
		RicercaAnagraficaResult result = new RicercaAnagraficaResult();
		List<PersonaDettaglio> lstout = new ArrayList<PersonaDettaglio>();
		
		try {
			CredenzialiWS anagReguser = this.getCredenzialiWS();
			URL wsdlLocation = getAnagrafeWSDLLocationURL();
			if(anagReguser!=null && wsdlLocation!=null){
				List <PersonaFindResult> listAnagReg=new ArrayList <PersonaFindResult>();
				
				AnagrafeClient anag = new AnagrafeClient(wsdlLocation);
				
				RicercaAnagraficaBean rb = new RicercaAnagraficaBean();
				rb.setUsername(anagReguser.getUsername());
				rb.setPassword(anagReguser.getPassword());
				anag.openSession(rb);
				
				RicercaAnagraficaBean rab= new RicercaAnagraficaBean();
				rab.setCognomePaziente(params.getCognome());
				rab.setNomePaziente(params.getNome());
				rab.setSesso(params.getSesso());
				rab.setCodiceFiscale(params.getCf());
				
				if(params.getAnnoNascitaDa()!=null && params.getAnnoNascitaDa()>0)
					rab.setDataNascitaDa("01/01/"+params.getAnnoNascitaDa());
				if(params.getAnnoNascitaA()!=null && params.getAnnoNascitaA()>0)
					rab.setDataNascitaA("31/12/"+params.getAnnoNascitaA());
				
				List <PersonaFindResult> l1=anag.findDatiAnagrafici(rab);
				if(l1!=null && !l1.isEmpty()){
					listAnagReg.addAll(l1);
				}
				
				for(PersonaFindResult p : listAnagReg){
					//Per recuperare dati di dettaglio
					if(params.isDettaglio()){
						String idPaziente = p.getIdPaziente();
						rab = new RicercaAnagraficaBean();
						rab.setIdPaziente(idPaziente);
					    p = anag.getDatiAnagraficiBaseByIdPaziente(rab);
					    if(p.getIdPaziente()==null) p.setIdPaziente(idPaziente); 
					}
						
					PersonaDettaglio pn = this.initFromAnaSanUmbria(p, params.isCaricaMedico());
					lstout.add(pn);			
				}
				result.setElencoAssistiti(lstout);
				anag.closeSession();
				
			}else
				result.setMessaggio("Non è stato possibile effettuare la ricerca in anagrafe sanitaria: parametri di connessione non impostati");
			
		}catch (AnagrafeException e) {
			logger.error(e.getMessage(), e);
			result.setEccezione(new SocioSanitarioException(e));
			result.setMessaggio(e.getMessage());
		} catch (AnagrafeSessionException e) {
			logger.error(e.getMessage(), e);
			result.setEccezione(new SocioSanitarioException(e));
			result.setMessaggio(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setEccezione(e);
			result.setMessaggio(e.getMessage());
		}
		return result;
	}
	
	private Medico getMedicoUmbriaByCodRegionale(String codRegMedico){
		Medico medicoRegione = null;
		if(!StringUtils.isBlank(codRegMedico)){
			URL url = getMediciWebServiceWSDLLocation();
			MedicoClient mc = null;
			if (url != null) {
				mc = new MedicoClient(url);
				medicoRegione = mc.getMedicoByCodiceRegionale(codRegMedico);
			}
		}
		return medicoRegione;
	}
			
	private URL getMediciWebServiceWSDLLocation() {
		String urlString = getGlobalParameter(DataModelCostanti.AmParameterKey.WS_MEDICI_URL);
		return stringToUrl(urlString);
	}
	
	private RicercaAnagraficaResult getPersonaAnaSanRegionaleUmbria(RicercaAnagraficaParams params){	
		RicercaAnagraficaResult result = new RicercaAnagraficaResult();
		List<PersonaDettaglio> lstout = new ArrayList<PersonaDettaglio>();
	
		try{
			PersonaFindResult p = null;
			CredenzialiWS anagRegUser = this.getCredenzialiWS();
			
			// precarico anagrafica
			URL wsdlLocation = getAnagrafeWSDLLocationURL();
			AnagrafeClient anag = new AnagrafeClient(wsdlLocation);
			if (anagRegUser != null && wsdlLocation != null) {
				RicercaAnagraficaBean rb = new RicercaAnagraficaBean();
				rb.setUsername(anagRegUser.getUsername());
				rb.setPassword(anagRegUser.getPassword());
				anag.openSession(rb);
				RicercaAnagraficaBean rab = new RicercaAnagraficaBean();
				
				if(params.getIdentificativo()!=null){
					rab.setIdPaziente(params.getIdentificativo());
					p = anag.getDatiAnagraficiBaseByIdPaziente(rab);
					
				}else if((params.getCognome()!=null && !params.getCognome().trim().isEmpty()) || (params.getCf()!=null && !params.getCf().trim().isEmpty())){
					
					rab.setCognomePaziente(params.getCognome());
					rab.setNomePaziente(params.getNome());
					rab.setCodiceFiscale(params.getCf());
					
					List<PersonaFindResult> listaPersone = anag.findDatiAnagrafici(rab);
					if (listaPersone != null && !listaPersone.isEmpty()) {
						PersonaFindResult persona = listaPersone.get(0);
						String idPaziente = persona.getIdPaziente();
						if (idPaziente != null){
							rab.setIdPaziente(idPaziente);
							p = anag.getDatiAnagraficiBaseByIdPaziente(rab);
							if(p.getIdPaziente()==null) p.setIdPaziente(idPaziente);
						}
					}
				}
				
				anag.closeSession();
			    
				if(p!=null){
					PersonaDettaglio pn = this.initFromAnaSanUmbria(p, params.isCaricaMedico());
					lstout.add(pn);
				}
				
				result.setElencoAssistiti(lstout);
			}else
				result.setMessaggio("Non è stato possibile effettuare la ricerca in anagrafe sanitaria: parametri di connessione non impostati");
					
		}catch (AnagrafeException e) {
			logger.error(e.getMessage(), e);
			result.setEccezione(e);
			result.setMessaggio(e.getMessage());
		} catch (AnagrafeSessionException e) {
			logger.error(e.getMessage(), e);
			result.setEccezione(e);
			result.setMessaggio(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setEccezione(e);
			result.setMessaggio(e.getMessage());
		}
		return result;
	}
		
	private RicercaAnagraficaResult ricercaAnaSanRegionaleMarche(RicercaAnagraficaParams params){	
		RicercaAnagraficaResult result = new RicercaAnagraficaResult();
		List<PersonaResult> lstTmp = new ArrayList<PersonaResult>();
		
		String path =configService.getGlobalParameter(DataModelCostanti.AmParameterKey.WS_RICERCA_JKS_PATH);
		String pwd = configService.getGlobalParameter(DataModelCostanti.AmParameterKey.WS_RICERCA_JKS_PWD);
		
		if(path!=null && pwd!=null){
			RicercaAnagraficaClient rc = new RicercaAnagraficaClient(path, pwd);
			
			RicercaAnagraficaDTO rab = new RicercaAnagraficaDTO();
			
			rab.setIdAssistito(params.getIdentificativo()!=null ? params.getIdentificativo().toUpperCase() : null);
			
			rab.setCognome(params.getCognome()!=null ? params.getCognome().toUpperCase() : null);
			rab.setNome(params.getNome()!=null ? params.getNome().toUpperCase()  : null);
			rab.setCf(params.getCf()!=null ? params.getCf().toUpperCase()  : null);
			rab.setSesso(params.getSesso()!=null ? params.getSesso().toUpperCase()  : null);
			
			Integer annoDa = null;
			Integer annoA = null;
			
			//Itero la ricerca per ogni anno dell'intervallo
			if(params.getAnnoNascitaDa()!=null && params.getAnnoNascitaDa()>0){
				annoDa = params.getAnnoNascitaDa();
				annoA = annoDa;
			}
		
			if(params.getAnnoNascitaA()!=null && params.getAnnoNascitaA()>0){
				annoA = params.getAnnoNascitaA();
				if(annoDa==null) annoDa = annoA;
			}
			
			String msg ="";
			if(annoDa!=null){
				Integer anno = annoDa;
				while(anno <= annoA){
					rab.setAnnoNascita(anno.toString());
					RicercaPersonaResult rpr = rc.ricercaPerDatiAnagrafici(rab);
					if(rpr.getCodice()==0){
						lstTmp.addAll(rpr.getElencoAssisiti());
					}else{
						if(rpr.getCodice()!=-1)
							msg += "Errore ricerca anagrafe regionale marche CODICE["+rpr.getCodice()+"]"+ rpr.getMessaggio();
							
						logger.error("Errore ricerca anagrafe regionale marche ANNO["+anno+"] - CODICE["+rpr.getCodice()+"]"+ rpr.getMessaggio(), rpr.getEccezione());
					}
					anno++;
				}
				result.setMessaggio(msg);
			}else{
				RicercaPersonaResult rpr = rc.ricercaPerDatiAnagrafici(rab);
				if(rpr.getCodice()==0){
					lstTmp.addAll(rpr.getElencoAssisiti());
				}else{
					if(rpr.getCodice()!=-1){
						msg += "Errore ricerca anagrafe regionale marche CODICE["+rpr.getCodice()+"]"+ rpr.getMessaggio();
						result.setCodice(rpr.getCodice());
						result.setMessaggio(msg);
						result.setEccezione(rpr.getEccezione());
					}
					logger.error(msg,result.getEccezione());
				}
			}
			
			//TODO: ordinare i nominativi
			List<PersonaDettaglio> lstout = new ArrayList<PersonaDettaglio>();
			for(PersonaResult p : lstTmp){
				if(!StringUtils.isEmpty(p.getAssistitoId()))
					lstout.add(this.initFromAnaSanMarche(p));
			}
			result.setElencoAssistiti(lstout);
		}else
			result.setMessaggio("Non è stato possibile effettuare la ricerca in anagrafe sanitaria: parametri di connessione non impostati");
		
		return result;
	}

	private PersonaDettaglio initFromAnaSanMarche(PersonaResult p){
		PersonaDettaglio pn = new PersonaDettaglio(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE);
		/*Campi necessari per creazione ID*/
		pn.setIdentificativo(p.getAssistitoId());
		
		pn.setCognome(p.getCognome());
		pn.setNome(p.getNome());
		pn.setCodfisc(p.getCodfisc());
		pn.setSesso(p.getSesso());
		pn.setStatoCivile(p.getStatoCivile());
		pn.setTelefono(null);
		//pn.setTelefono(p.getRecaptelefonico());
		
		//Dati Nascita
		pn.setDataNascita(p.getDataNascita());
		pn.setDataMorte(p.getDataMor());
		pn.setCittadinanza(this.getCittadinanzaByCodice(p.getCodIstatCittadinanza()));
		
		String istatComune = p.getIstatComNas(); // es.: CINA 999314
		if(istatComune!=null && istatComune.startsWith("999") && p.getCodStatoNas()==null){
			p.setIstatComNas(null);
			p.setCodStatoNas(istatComune.replaceFirst("999", ""));
		}
		pn.setComuneNascita(this.findComune(p.getIstatComNas()));
		pn.setNazioneNascita(this.findNazione(p.getCodStatoNas(), p.getDesStatoNas()));
		/*pn.setIstatComNas(p.getIstatComNas());
		pn.setDesComNas(p.getDesComNas());
		pn.setSiglaProvNas(p.getSiglaProvNas());
		
		pn.setCodStatoNas(p.getCodStatoNas());
		pn.setDesStatoNas(p.getDesStatoNas());*/
		
		//Dati Medico
		pn.setMedicoCodiceFiscale(p.getMedicoCodiceFiscale());
		
		String medicoCognome = null;
		String medicoNome = null;
		if(!StringUtils.isBlank(p.getMedicoCognomeNome())){
			String[] sMedico = p.getMedicoCognomeNome().split(" ");
			medicoCognome = sMedico[0];
			medicoNome = p.getMedicoCognomeNome().replace(sMedico[0], "").trim();
		}
		pn.setMedicoCognome(medicoCognome);
		pn.setMedicoNome(medicoNome);	
		
		Date dataScelta = parseddMMyyyy(p.getMedicoDataScelta(), "Data scelta medico per il soggetto" + pn.getCodfisc());
		Date dataRevoca = parseddMMyyyy(p.getMedicoDataRevoca(), "Data revoca medico per il soggetto" + pn.getCodfisc());;
		
		pn.setMedicoDataScelta(dataScelta);
		pn.setMedicoDataRevoca(dataRevoca);
		pn.setDocumentoSanitario(p.getDocumentoSanitario());
		pn.setDocumentoSanitarioScadenza(p.getDocumentoSanitarioScadenza());
		
		//Residenza
		String codIstatResidenza = p.getIstatComResidenza();
		String[] residenza = this.estraiCivicoMarche(p.getIndirizzoResidenza());
		pn.setIndirizzoResidenza(residenza!=null ? residenza[0].trim() : p.getIndirizzoResidenza());
		pn.setCivicoResidenza(residenza!=null ? residenza[1].trim() : null);
		pn.setComuneResidenza(this.findComune(codIstatResidenza));
		
		boolean domicilioComeResidenza = p.getIstatComResidenza()!=null && p.getIstatComResidenza().equalsIgnoreCase(p.getIstatComDomicilio()) &&
										 p.getIndirizzoResidenza()!=null && p.getIndirizzoResidenza().equalsIgnoreCase(p.getIndirizzoDomicilio());
		
		//Domicilio
		if(!domicilioComeResidenza){
			String[] domicilio = this.estraiCivicoMarche(p.getIndirizzoDomicilio());
			pn.setIndirizzoDomicilio(domicilio!=null ? domicilio[0].trim() : p.getIndirizzoDomicilio());
			pn.setCivicoDomicilio(domicilio!=null ? domicilio[1].trim() : null);
			pn.setComuneDomicilio(this.findComune(p.getIstatComDomicilio()));
		}
		//?
		pn.setIstatNazione(p.getIstatNazione());
		
		return pn;
	}
	
	private String[] estraiCivicoMarche(String indirizzo){
		String[] ind = null;
		if(!StringUtils.isBlank(indirizzo)){
			ind = indirizzo.split(",");
			if(ind.length==1){
				/*Tento lo split con " N "*/
				ind = indirizzo.split(" N ");
			}
			if(ind.length==2) return ind;
		}
		return null;
	}
	
	private PersonaDettaglio initFromAnaSanUmbria(PersonaFindResult p, boolean caricaMedico){
		PersonaDettaglio pn = new PersonaDettaglio(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA);
		
		pn.setIdentificativo(p.getIdPaziente());
		
		pn.setCognome(p.getCognome());
		pn.setNome(p.getNome());
		
		logger.debug("COGNOME["+p.getCognome()+"] NOME["+p.getNome()+"]");
		
		pn.setCodfisc(p.getCodfisc());
		pn.setSesso(p.getSesso());
		pn.setStatoCivile(p.getStatoCivile());
		pn.setTelefono(null);
		
		//Dati Nascita
		pn.setDataNascita(p.getDataNascita());
		pn.setDataMorte(p.getDataMor());
		pn.setCittadinanza(this.getCittadinanzaByCodice(p.getCodIstatCittadinanza()));
		
		pn.setComuneNascita(this.findComune(p.getIstatComNas()));
		pn.setNazioneNascita(this.findNazione(p.getCodStatoNas(), p.getDesStatoNas()));
		/*pn.setIstatComNas(p.getIstatComNas());
		pn.setDesComNas(p.getDesComNas());
		pn.setSiglaProvNas(p.getSiglaProvNas());
		
		pn.setCodStatoNas(p.getCodStatoNas());
		pn.setDesStatoNas(p.getDesStatoNas());
		*/
		//Dati Medico
		pn.setMedicoCodRegionale(p.getCodiceRegionaleMedico());
		
		if(caricaMedico){
			Medico medicoRegione = this.getMedicoUmbriaByCodRegionale(p.getCodiceRegionaleMedico());
			if (medicoRegione != null) {
				String cognomeNewMedico = medicoRegione.getCognome();
				String nomeNewMedico = medicoRegione.getNome();
				//String codiceFiscale = medicoRegione.getCodiceFiscale();
				pn.setMedicoCognome(cognomeNewMedico);
				pn.setMedicoNome(nomeNewMedico);
			}
		}else{
			//pn.setMedicoCognomeNome(null);
			pn.setMedicoCognome(null);
			pn.setMedicoNome(null);
		}
		
		pn.setMedicoCodiceFiscale(null);
		pn.setMedicoDataScelta(p.getMedicoDataScelta());
		pn.setMedicoDataRevoca(p.getMedicoDataRevoca());
		pn.setDocumentoSanitario(p.getNumeroTesseraSanitaria());
		pn.setDocumentoSanitarioScadenza(null);
		
		//Residenza
		pn.setIndirizzoResidenza(p.getIndirizzoResidenza());
		pn.setCivicoResidenza(p.getCivicoResidenza());
		AmTabComuni comRes = this.findComune(p.getIstatComResidenza());
		pn.setComuneResidenza(comRes);
		if(comRes==null) {
			AmTabNazioni nazRes = this.findNazione(p.getIstatComResidenza(), null);
			pn.setNazioneResidenza(nazRes);
		}
		
		boolean domicilioComeResidenza = p.getIndirizzoResidenza()!=null && p.getIndirizzoResidenza().equalsIgnoreCase(p.getIndirizzoDomicilio()) &&
										 p.getCivicoResidenza()!=null && p.getCivicoResidenza().equalsIgnoreCase(p.getCivicoDomicilio()) &&
										 p.getIstatComResidenza()!=null && p.getIstatComResidenza().equalsIgnoreCase(p.getIstatComDomicilio());
		
		//Domicilio
		if(!domicilioComeResidenza){
			pn.setIndirizzoDomicilio(p.getIndirizzoDomicilio());
			pn.setCivicoDomicilio(p.getCivicoDomicilio());
			AmTabComuni comDom = this.findComune(p.getIstatComDomicilio());
			pn.setComuneDomicilio(comDom);
			if(comDom==null){
				AmTabNazioni nazDom = this.findNazione(p.getIstatComDomicilio(), null);
				pn.setNazioneResidenza(nazDom);
			}
		}
		//?
		pn.setIstatNazione(null);
		
		return pn;
	}
	
	private AmTabComuni findComune(String codIstat){
		AmTabComuni comuneNascita = null;
		try{
			comuneNascita = luoghiService.getComuneItaByIstat(codIstat);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return comuneNascita;
	}
	
	private AmTabNazioni findNazione(String codice, String descrizione) {
		AmTabNazioni nazione = null;
		if(codice!=null && !codice.isEmpty()){
			codice = "100".equalsIgnoreCase(codice) ? "1" : codice;
			try{
				nazione = luoghiService.getNazioneByIstat(codice);
			}catch(Exception e){}
			if(nazione==null && descrizione!=null){
				logger.debug("Ricerco Nazione con cod.istat "+codice+ " --> NON TROVATA!");
			    nazione = new AmTabNazioni();
			    nazione.setCodIstatNazione(codice);
			    nazione.setNazione(descrizione);
			}
		}
		
		return nazione;
	}

	private RicercaAnagraficaResult loadFamigliaSIRPS(RicercaAnagraficaParams dto){
		RicercaAnagraficaResult result = new RicercaAnagraficaResult();
		//TODO:Implementare integrazione
		return result;
	}
	
	private PersonaDettaglio initFromDiogene(SitDPersona p, String enteId, String sessionId){
		PersonaDettaglio pd = new PersonaDettaglio(DataModelCostanti.TipoRicercaSoggetto.DEFAULT);
		/*Campi necessari per creazione ID*/
		if(p!=null){
			
			ComponenteFamigliaDTO info = new ComponenteFamigliaDTO();
			info.setEnteId(enteId);
			info.setSessionId(sessionId);
			info.setPersona(p);
			info = anagrafeService.fillInfoAggiuntiveComponente(info);
			
			pd.setIdentificativo(p.getIdExt());
			pd.setCognome(p.getCognome());
			pd.setNome(p.getNome());
			pd.setCodfisc(p.getCodfisc());
			pd.setCittadinanza(info.getCittadinanza());
			pd.setDataNascita(p.getDataNascita());
			pd.setDataMorte(p.getDataMor());
			pd.setSesso(p.getSesso());
			
			if("ITALIA".equalsIgnoreCase(info.getDesStatoNas())){
				AmTabComuni comNas = this.findComune(info.getCodComNas());
				pd.setComuneNascita(comNas);
			}else{
				pd.setNazioneNascita(findNazione(info.getCodStatoNas(), info.getDesStatoNas()));
			}
			
			if("ITALIA".equalsIgnoreCase(info.getDesStatoRes())){
				AmTabComuni comRes = this.findComune(info.getCodComRes());
				pd.setComuneResidenza(comRes);
			}else{
				pd.setNazioneResidenza(findNazione(info.getIstatStatoRes(), info.getDesStatoRes()));//Stato estero;
			}
			
			pd.setIndirizzoResidenza(info.getIndirizzoResidenza());
			pd.setCivicoResidenza(info.getCivicoResidenza());
			pd.setDataInizioResidenza(info.getDataInizioResidenza());
			
			boolean emigrato = p.getDataEmi()!=null && (p.getDataImm()==null || p.getDataImm().before(p.getDataEmi()));
			pd.setEmigrato(emigrato);
			
			pd.setStatoCivile(p.getStatoCivile());
			
		}
		return pd;
	}
	
	private RicercaAnagraficaResult ricercaAnaComunaleInterna(RicercaAnagraficaParams params){
		RicercaAnagraficaResult result = new RicercaAnagraficaResult();
		List<PersonaDettaglio> lstout = new ArrayList<PersonaDettaglio>();
		
		if(this.isAnagrafeComunaleInternaAbilitata()){
			RicercaSoggettoAnagrafeDTO rsDto = new RicercaSoggettoAnagrafeDTO();
			rsDto.setEnteId(params.getEnteId());
			rsDto.setSessionId(params.getSessionId());
			rsDto.setMaxResult(params.getMaxResult());
			
			List<SitDPersona> list = new ArrayList<SitDPersona>();
			if(!StringUtils.isBlank(params.getIdentificativo())){
				rsDto.setIdSogg(params.getIdentificativo());
				list = anagrafeService.searchSISOAnagrafiche(rsDto);
			}else{
				rsDto.setCognome(params.getCognome());
				rsDto.setNome(params.getNome());
				rsDto.setCodFis(params.getCf());
				rsDto.setSesso(params.getSesso());
				rsDto.setDtRif(new Date());
				rsDto.setAnnoNascitaDa(params.getAnnoNascitaDa());
				rsDto.setAnnoNascitaA(params.getAnnoNascitaA());
				list = anagrafeService.searchSISOAnagrafiche(rsDto);
			}
			
			for(SitDPersona s: list){
				PersonaDettaglio pd = this.initFromDiogene(s, params.getEnteId(), params.getSessionId());
				lstout.add(pd);
			}
		}
		
		result.setElencoAssistiti(lstout);
	    return result;
	}
	
	private RicercaAnagraficaResult loadFamigliaGIT(RicercaAnagraficaParams dto){
		RicercaAnagraficaResult result = new RicercaAnagraficaResult();
		List<FamiliareDettaglio> componenti = new ArrayList<FamiliareDettaglio>();
	  		RicercaSoggettoAnagrafeDTO rsaDto = new RicercaSoggettoAnagrafeDTO();
			rsaDto.setEnteId(dto.getEnteId());
			rsaDto.setSessionId(dto.getSessionId());
			rsaDto.setCodFis(dto.getCf());
			List<ComponenteFamigliaDTO> lista = anagrafeService.getListaCompFamigliaInfoAggiuntiveByCf(rsaDto);
	    	
			/* Ricerco il soggetto titolare della cartella e verifico se è intestatario della scheda anagrafica*/
			boolean parentelaValida = false;
			boolean trovato = false;
			int i=0;
			BaseDTO bo = new BaseDTO();
			bo.setEnteId(dto.getEnteId());
			while(i<lista.size() && !trovato){
				ComponenteFamigliaDTO componenteDto = lista.get(i);
				if(componenteDto.getPersona().getCodfisc() != null && componenteDto.getPersona().getCodfisc().equalsIgnoreCase(dto.getCf())){
					trovato = true;
					bo.setObj(componenteDto.getRelazPar());
					CsTbTipoRapportoCon rapp = configurazioneService.mappaRelazioneParentale(bo);
					if(rapp!=null && rapp.getId()==DataModelCostanti.INTESTATARIO_SCHEDA_REL_ID) parentelaValida = true;
				}
				i++;
			}
			
			/* Valorizzo i familiari (escludendo il soggetto stesso) */
	    	for(ComponenteFamigliaDTO f: lista) {
	    		
	    		if(f.getPersona().getCodfisc() != null && f.getPersona().getCodfisc().equals(dto.getCf()))
	    			continue;
	    		
	    		FamiliareDettaglio componenteGit = new FamiliareDettaglio();
	    		
	    		componenteGit.setCognome(f.getPersona().getCognome());
	    		componenteGit.setNome(f.getPersona().getNome());
	    		componenteGit.setSesso(f.getPersona().getSesso());
	    		componenteGit.setCodfisc(f.getPersona().getCodfisc());
	    		componenteGit.setDataNascita(f.getPersona().getDataNascita());
	    		componenteGit.setDataMorte(f.getPersona().getDataMor());
	    		
	    		// Cittadinanza
				componenteGit.setCittadinanza(f.getCittadinanza());
				
				AmTabComuni comuneNascita = this.findComune(f.getCodComNas());
	    		componenteGit.setComuneNascita(comuneNascita);
	    		if(!ITALIA.equalsIgnoreCase(f.getDesStatoNas())){
	    			componenteGit.setNazioneNascita(findNazione(f.getCodStatoNas(), f.getDesStatoNas()));
	    		}
	    		
	    		componenteGit.setIndirizzoResidenza(f.getIndirizzoResidenza());
	    		componenteGit.setCivicoResidenza(f.getCivicoResidenza());
	    		componenteGit.setComuneResidenza(this.findComune(f.getCodComRes()));
	    		
	    		bo.setObj(f.getRelazPar());
	    		componenteGit.setParentela(configurazioneService.mappaRelazioneParentale(bo));
				componenteGit.setParentelaValida(parentelaValida);
				
	    		componenti.add(componenteGit);
	    		
	    	}
	    	
	    	result.setElencoFamiliari(componenti);
	    	return result;
	}
	
	private  AmTabNazioni getNazioneByCodiceGenerico(String codice) {
		AmTabNazioni nazione = null;
		if(codice!=null && !codice.isEmpty()){
			try{
				nazione = luoghiService.getNazioneByCodiceGenerico(codice);
			}catch(Exception e){}
		}
		
		return nazione;
	}

	private  String getCittadinanzaByCodice(String codice){
		String cittadinanza = null;
		if("100".equals(codice)){
			cittadinanza = "ITALIANA";
		}else{
			AmTabNazioni nazione = getNazioneByCodiceGenerico(codice);
			if(nazione!=null)
				cittadinanza = nazione.getNazionalita();
		}
		
		return cittadinanza;
	}
	

}
