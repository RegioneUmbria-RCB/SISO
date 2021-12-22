package it.webred.ss.web.bean.lista.soggetti;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.jsf.bean.UserSearchExtInput;
import it.webred.cs.jsf.manbean.ConsensoPrivacyMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean.CredenzialiWS;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagDTO;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.ws.ricerca.dto.FamiliareDettaglio;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaParams;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaResult;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.SintesiSchedeUfficioDTO;
import it.webred.ss.ejb.dto.SsSearchCriteria;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.util.PuntoContatto;
import it.webred.ss.web.bean.util.Soggetto;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import eu.smartpeg.rievazionepresenze.dto.AnagraficaDTO;
import eu.smartpeg.rilevazionepresenze.AnagraficaSessionBeanRemote;
import eu.smartpeg.rilevazionepresenze.data.dto.RpSearchCriteria;

@ManagedBean
@ViewScoped
public class SearchBean extends SegretariatoSocBaseBean{ 
  
	private UserSearchExtInput params=new UserSearchExtInput();
  
    private boolean searchAnagSanitaria = true;
    private boolean searchRilevazionePresenze = false; //#ROMACAPITALE
    
    List<Soggetto> soggetti;
	private Soggetto selectedSoggetto;
	private SoggettiTableDataModel soggettiDataModel;
	
	List<Soggetto> nucleo;
	private Soggetto selectedSoggettoNucleo;
	private SoggettiTableDataModel nucleoDataModel;
    
    private boolean searchButtonPressed;
    private boolean searchNucleoButtonPressed;
      
    private static final String SCHEDE_URL = "schede.faces";
	private static final String NUOVA_SCHEDA_URL = "editScheda.faces";
	
	@EJB(lookup = "java:global/RilevazionePresenze/RilevazionePresenze_EJB/AnagraficaSessionBean!eu.smartpeg.rilevazionepresenze.AnagraficaSessionBeanRemote") 
	private AnagraficaSessionBeanRemote rilevazionePresenzeService;
	
	
	
	public boolean isSearchButtonPressed() {
		return searchButtonPressed;
	}

	public void setSearchButtonPressed(boolean searchButtonPressed) {
		this.searchButtonPressed = searchButtonPressed;
	}

	public List<Soggetto> getSoggetti() {
		return soggetti;
	}


	public void setSoggetti(List<Soggetto> soggetti) {
		this.soggetti = soggetti;
	}


	public Soggetto getSelectedSoggetto() {
		return selectedSoggetto;
	}


	public void setSelectedSoggetto(Soggetto selectedSoggetto) {
		this.selectedSoggetto = selectedSoggetto;
	}


	public SoggettiTableDataModel getSoggettiDataModel() {
		return soggettiDataModel;
	}


	public void setSoggettiDataModel(SoggettiTableDataModel soggettiDataModel) {
		this.soggettiDataModel = soggettiDataModel;
	}


	public List<Soggetto> getNucleo() {
		return nucleo;
	}


	public void setNucleo(List<Soggetto> nucleo) {
		this.nucleo = nucleo;
	}


	public Soggetto getSelectedSoggettoNucleo() {
		return selectedSoggettoNucleo;
	}


	public void setSelectedSoggettoNucleo(Soggetto selectedSoggettoNucleo) {
		this.selectedSoggettoNucleo = selectedSoggettoNucleo;
	}


	public SoggettiTableDataModel getNucleoDataModel() {
		return nucleoDataModel;
	}


	public void setNucleoDataModel(SoggettiTableDataModel nucleoDataModel) {
		this.nucleoDataModel = nucleoDataModel;
	}
	

	public boolean isSearchNucleoButtonPressed() {
		return searchNucleoButtonPressed;
	}


	public void setSearchNucleoButtonPressed(boolean searchNucleoButtonPressed) {
		this.searchNucleoButtonPressed = searchNucleoButtonPressed;
	}


	public void searchSoggetto(ActionEvent actionEvent){
		List<SsAnagrafica> soggettiUdc = new ArrayList<SsAnagrafica>();
		String cf = params.getCodiceFiscale();
		String cognome = params.getCognome();
		String nome = params.getNome();
		String alias = params.getAlias();//SISO-948
		
		List<String> msg = new ArrayList<String>();
		if(params.isRicercaCf())
			msg.addAll(params.validaCodiceFiscale());
		else if(params.isRicercaDatiAnagrafici()){
			String validaTipo = null;
			
			if(isAnagrafeSigessAbilitata()) validaTipo = DataModelCostanti.TipoRicercaSoggetto.SIGESS;
			else if (isAnagrafeSanitariaUmbriaAbilitata()) validaTipo = DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA;
			else if (this.isAnagrafeSanitariaMarcheAbilitata()) validaTipo = DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE;

			msg.addAll(params.validaAnnoNascita(validaTipo));
			msg.addAll(params.validaCognome());					
			if(isAnagrafeSigessAbilitata())
				msg.addAll(params.validaSesso());
		}
		else if(params.isRicercaAlias()){ //SISO-948
			msg.addAll(params.validaAlias());
		}
	
		if(!msg.isEmpty()){
			addWarning("search.anagrafe.parametri", msg);
			return;
		}
						
		if((cognome == null || cognome.isEmpty()) && (nome == null||nome.isEmpty()) && (cf == null || cf.isEmpty() ) && (alias == null || alias.isEmpty() ))
			addError("compila.error");
		else {
			searchButtonPressed = true;
			searchNucleoButtonPressed = false;

			TreeMap<String, Soggetto> results = new TreeMap<String, Soggetto>();
			soggetti = new ArrayList<Soggetto>();
			
			if(this.isAnagrafeComunaleInternaAbilitata() && !params.isRicercaAlias()){
				List<Soggetto> lstAnag = ricercaInAnagrafeEsterna(DataModelCostanti.TipoRicercaSoggetto.DEFAULT);
				for(Soggetto s : lstAnag){
					String key = s.getSoggettoKey();
					if(!results.containsKey(key))
						results.put(key, s);
				}
			}
			
			if(this.isAnagrafeSigessAbilitata()&& !params.isRicercaAlias()){
				List<Soggetto> lstAnagSigess = ricercaInAnagrafeEsterna(DataModelCostanti.TipoRicercaSoggetto.SIGESS);
				for(Soggetto s : lstAnagSigess){
					String key = s.getSoggettoKey();
					if(!results.containsKey(key))
						results.put(key, s);
				}
			}
			
			if(this.isAbilitaSearchAnagSanitaria() && searchAnagSanitaria){
				List<Soggetto> lstAnagSanitaria = ricercaInAnagrafeEsterna(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA);
				for(Soggetto s : lstAnagSanitaria){
					String key = s.getSoggettoKey();
					if(!results.containsKey(key))
						results.put(key, s);
				}
				
			}
			
			if(this.isAnagrafeSanitariaMarcheAbilitata() && !params.isRicercaAlias()){
				List<Soggetto> lstAnagMarche = ricercaInAnagrafeEsterna(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE);
				for(Soggetto s : lstAnagMarche){
					String key = s.getSoggettoKey();
					if(!results.containsKey(key))
						results.put(key, s);
				}
			}
			
			
			//#ROMACAPITALE Se sono su ROMA isAnagrafeSigessAbilitata() ed è selezionato il check per la Ricerca su Rilevazione Presenze
//			if(this.isAbilitaSearchRilevazionePresenze() && !params.isRicercaAlias() ){
			if(this.searchRilevazionePresenze && !params.isRicercaAlias() ){
			  List<Soggetto> lstRilevazionePresenze = searchFromRilevazionePresenze();
				for(Soggetto s : lstRilevazionePresenze){
					String key = s.getSoggettoKey();
					if(!results.containsKey(key))
						results.put(key, s);
				}
			}
			
			//FINE #ROMACAPITALE
						
			//RICERCO I SOGGETTI PRESENTI IN UDC - LI FILTRO IN SEGUITO IN BASE AI RISULTATI TROVATI NELLA BANCHE DATI AGGIORNATE
			List<SsAnagrafica> schedeSegr = this.searchFromSchedaAnagrafica();
			if(schedeSegr != null)
				soggettiUdc.addAll(schedeSegr);
			
		
			//Aggiungo le anagrafiche presenti in SS solo per quei soggetti che non ho trovato nè in anagrafe sanitaria, nè in quella comunale
			for(SsAnagrafica ss : soggettiUdc)
				addSoggettoUdCLista(ss,results);
			
			SortedSet<String> keys = new TreeSet<String>(results.keySet());
			if (!params.isRicercaAlias()){
					
				//soggetti.addAll(results.values());
				for(String k: keys){
					Soggetto s = results.get(k);
					valorizzaPresenzaInSS(s);
					valorizzaPresenzaInCS(s);
					valorizzaPresenzaInRP(s);
					s.setBeneficiarioRdC(verificaPresenzaRdC(s.getCf()));	
					//SISO-1531
					s.setPresenzaDatiEsterni(verificaPresenzaDatiEsterni(s.getCf()));
					s.setEv(isInEnteEsterno(s.getCf()));
					soggetti.add(s);
					if(!s.getSsComplete().isEmpty() || !s.getSsIncomplete().isEmpty()) //Serve a recuperare la lista di schede aperte
						setAnagraficaId(s);
				}
			}else {
				for(String k: keys){
					Soggetto s = results.get(k);
					s.setEv(isInEnteEsterno(s.getCf()));
					valorizzaPresenzaInSS(s);
					soggetti.add(s);
				}
			}
			
			soggettiDataModel = new SoggettiTableDataModel(soggetti);
		}
	}
		
	protected List<Soggetto> ricercaInAnagrafeEsterna(String tipoRicerca){
		List<Soggetto> listAutocomplete = new ArrayList<Soggetto>();
		
		if(CsUiCompBaseBean.isAnagrafeAbilitata(tipoRicerca)){
			try {
				
				List <PersonaDettaglio> listAnagReg=new ArrayList <PersonaDettaglio>();
				
				RicercaAnagraficaParams rab= new RicercaAnagraficaParams(tipoRicerca,true);
				fillEnte(rab);
				rab.setCognome(params.getCognome());
				rab.setNome(params.getNome());
				rab.setSesso(params.getDatiSesso().getSesso());
				rab.setCf(params.getCodiceFiscale());
				rab.setAnnoNascitaDa(params.getAnnoNascitaDa());
				rab.setAnnoNascitaA(params.getAnnoNascitaA());
				
				RicercaAnagraficaResult result = CsUiCompBaseBean.ricercaPerDatiAnagrafici(rab);
				List<PersonaDettaglio> elenco = result.getElencoAssistiti();
				if(StringUtils.isBlank(result.getMessaggio()) && elenco!=null)
					listAnagReg.addAll(elenco);
				
				if(!StringUtils.isBlank(result.getMessaggio()))
					logger.error(result.getMessaggio(), result.getEccezione());
				
				for(int i=0; i<listAnagReg.size(); i++){
					PersonaDettaglio s=listAnagReg.get(i);
					
					Soggetto sogg = new Soggetto(s.getProvenienzaRicerca(), s.getIdentificativo(), null, s.getCognome(), s.getNome(), s.getCodfisc(), s.getDataNascita(), s.getDataMorte(), s.getSesso());
					
					if(s.getIndirizzoResidenza()!=null){
						IndirizzoAnagDTO ind = new IndirizzoAnagDTO();
						ind.setIndirizzo(s.getIndirizzoResidenza());
						ind.setCivicoNumero(s.getCivicoResidenza());
						if(s.getComuneResidenza()!=null){
							ind.setComCod(s.getComuneResidenza().getCodIstatComune());
							ind.setComDes(s.getComuneResidenza().getDenominazione());
							ind.setProv(s.getComuneResidenza().getSiglaProv());
						}
						if(s.getNazioneResidenza()!=null){
							ind.setStatoCod(s.getNazioneResidenza().getCodIstatNazione());
							ind.setStatoDes(s.getNazioneResidenza().getNazione());
						}
						sogg.setIndirizzo(ind);
					}
					verificaPrivacy(sogg);
					
					listAutocomplete.add(sogg);
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return listAutocomplete;
	} 

	private void addSoggettoUdCLista(SsAnagrafica s, TreeMap<String,Soggetto> results){
		Soggetto val = new Soggetto(null, null, s.getId(), s.getCognome(), s.getNome(), s.getCf(), s.getData_nascita(), null, s.getSesso());
		String key = val.getSoggettoKey();
		if(!results.containsKey(key)){
			val.setAnagEsistenteUDC(true);
			val.setAlias(s.getAlias());
			
			verificaPrivacy(val);
			
			results.put(key, val);
		}
	}
	
	
	private void verificaPrivacy(Soggetto val){
		try {
			
			if(!StringUtils.isBlank(val.getCf()) && !val.isAnonimo()){
				String org = this.getPreselectedOrganizzazione();
				Long idOrganizzazione = new Long(org.split("\\|")[1]);
				ConsensoPrivacyMan consensoMan = new ConsensoPrivacyMan(val.getCf(), idOrganizzazione, val.isAnonimo(), val.isBeneficiarioRdC());
				val.setPrivacy(consensoMan.getPrivacy());			
			}else{
				logger.warn("Il soggetto ["+val.getCognome()+" "+val.getNome()+"("+val.getAlias()+")] è ANONIMO o il CF non è valorizzato: impossibile verificare lo stato della scheda privacy!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public boolean isAbilitaSearchAnagSanitaria(){
		boolean abilita = false;
		try {
			CredenzialiWS anagReguser = this.getCredenzialiWS();
			URL wsdlLocation = CsUiCompBaseBean.getAnagrafeWSDLLocationURL();
			abilita = (isAnagrafeSanitariaUmbriaAbilitata() && anagReguser!=null && wsdlLocation!=null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return abilita;
	}
	
	//#ROMACAPITALE
	public boolean isAbilitaSearchRilevazionePresenze(){
		boolean abilita = false;
		try {
			abilita = CsUiCompBaseBean.isRilevazionePresenzeAbilita();
    	} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return abilita;
	}
	
	private List<Soggetto> searchFromRilevazionePresenze() {
		List<Soggetto> listAutocomplete = new ArrayList<Soggetto>();
		RpSearchCriteria dto = new RpSearchCriteria();
//		fillUserData(dto);
		dto.setCf(params.getCodiceFiscale());
		dto.setCognome(params.getCognome());
		dto.setNome(params.getNome());
		dto.setSesso(params.getDatiSesso()!=null ? params.getDatiSesso().getSesso() : null);
		
		try{
		
		Date dataNascitaDa = (params.getAnnoNascitaDa()!=null && params.getAnnoNascitaDa()>0 ? ddMMyyyy.parse("01/01/"+params.getAnnoNascitaDa()) : null);
		Date dataNascitaA = (params.getAnnoNascitaA()!=null && params.getAnnoNascitaA()>0 ? ddMMyyyy.parse("31/12/"+params.getAnnoNascitaA()):null);
		dto.setDataNascitaDa(dataNascitaDa);
		dto.setDataNascitaA(dataNascitaA);
		}catch(Exception pe){
			logger.error(pe);
		}
		
		try {


    		List<AnagraficaDTO> lstAnagraficaRP = rilevazionePresenzeService.searchAnagraficaRPBySoggetto(dto);
    		
    		if  (lstAnagraficaRP != null && lstAnagraficaRP.size()>0)
    		{
    			for (AnagraficaDTO anagRp :  lstAnagraficaRP) {
                  Soggetto sogg = new Soggetto(DataModelCostanti.TipoRicercaSoggetto.ANAG_RILEVAZIONE_PRESENZE, String.valueOf(anagRp.getId()), null, anagRp.getCognome(), anagRp.getNome(), anagRp.getCf(), anagRp.getDataNascita(), null, anagRp.getSesso());
					
					if(anagRp.getIndirizzoResidenza()!=null){
						IndirizzoAnagDTO ind = new IndirizzoAnagDTO();
						ind.setIndirizzo(anagRp.getIndirizzoResidenza());
						
						AmTabComuni comuneRes = anagRp.getComuneResidenza();
						if(comuneRes!=null){
							ind.setComCod(comuneRes.getCodIstatComune());
							ind.setComDes(comuneRes.getDenominazione());
							ind.setProv(comuneRes.getSiglaProv());
						}
						
						sogg.setIndirizzo(ind);
					}
					verificaPrivacy(sogg);
					listAutocomplete.add(sogg);
    			}
    		}
    		//Qui devo trasformare l'anagrafica Rp in soggetto
			
//    		for(SsSchedaSegnalato sc: schedeCF)
//    			listaSchede.add(sc.getAnagrafica());
    		
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.rilevazione.presenze.soggetto.error");
		}
		
		return listAutocomplete;
	}
	//FINE  #ROMACAPITALE
	
	private List<SsAnagrafica> searchFromSchedaAnagrafica() {
		List<SsAnagrafica> listaSchede =  new ArrayList<SsAnagrafica>();
		SsSearchCriteria dto = new SsSearchCriteria();
		fillUserData(dto);
		dto.setCf(params.getCodiceFiscale());
		dto.setCognome(params.getCognome());
		dto.setNome(params.getNome());
		dto.setSesso(params.getDatiSesso()!=null ? params.getDatiSesso().getSesso() : null);
		dto.setAlias(params.getAlias());
		
		try{
		
		Date dataNascitaDa = (params.getAnnoNascitaDa()!=null && params.getAnnoNascitaDa()>0 ? ddMMyyyy.parse("01/01/"+params.getAnnoNascitaDa()) : null);
		Date dataNascitaA = (params.getAnnoNascitaA()!=null && params.getAnnoNascitaA()>0 ? ddMMyyyy.parse("31/12/"+params.getAnnoNascitaA()):null);
		dto.setDataNascitaDa(dataNascitaDa);
		dto.setDataNascitaA(dataNascitaA);
		}catch(Exception pe){
			logger.error(pe);
		}
		
		try {
    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
        			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

    		List<SsSchedaSegnalato> schedeCF = schedaService.searchSchedeBySoggetto(dto);
    		
    		for(SsSchedaSegnalato sc: schedeCF)
    			listaSchede.add(sc.getAnagrafica());
    		
    	} catch(NamingException e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.soggetto.error");
		}
		
		return listaSchede;
	}
	
/*	//modifica
	private List<SsAnagrafica> readFromSchedaAnagraficaByName(String cognome2, String nome2) {
		List<SsAnagrafica> listaSchede =  new ArrayList<SsAnagrafica>();
		it.webred.ss.ejb.dto.BaseDTO dto = new it.webred.ss.ejb.dto.BaseDTO();
		fillUserData(dto);
		String[] denom = {cognome2, nome2};
		dto.setObj(denom);
		
		try {
    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
        			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

    		List<SsSchedaSegnalato> schedeCF = schedaService.readSchedeByDenominazione(dto);
    		
    		for(SsSchedaSegnalato sc: schedeCF)
    			listaSchede.add(sc.getAnagrafica());
    		
    	} catch(NamingException e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.soggetto.error");
		}
		
		return listaSchede;
	}

	private List<SsAnagrafica> readFromSchedaAnagraficaByCf(String cf2){
		List<SsAnagrafica> listaSchede =  new ArrayList<SsAnagrafica>();
		it.webred.ss.ejb.dto.BaseDTO dto = new it.webred.ss.ejb.dto.BaseDTO();
		fillUserData(dto);
		dto.setObj(cf2);
		
		try {
    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
        			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

    		List<SsSchedaSegnalato> schedeCF = schedaService.readSchedeSegnalatoByCF(dto);
    		
    		for(SsSchedaSegnalato sc: schedeCF)
    			listaSchede.add(sc.getAnagrafica());
    		
    	} catch(NamingException e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.soggetto.error");
		}
		
		return listaSchede;
	}
		*/
///fine implementazione

	private void setAnagraficaId(Soggetto s){
		it.webred.ss.ejb.dto.BaseDTO dto = new it.webred.ss.ejb.dto.BaseDTO();
    	fillUserData(dto);
    	dto.setObj(s.getCf());  
    	try {
    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
        			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
    		SsAnagrafica anagrafica = schedaService.readAnagraficaByCf(dto);
    		if(anagrafica != null)
    			s.setIdSsAnag(anagrafica.getId());
    		
    	} catch(NamingException e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.error");
		}
	}
	
	public void valorizzaPresenzaInSS(Soggetto s){
		
		it.webred.ss.ejb.dto.BaseDTO dto = new it.webred.ss.ejb.dto.BaseDTO();
    	fillUserData(dto);
    	
    	if(!StringUtils.isBlank(s.getCf()) || !StringUtils.isBlank(s.getAlias())){
	    	String cfAlias = !StringUtils.isBlank(s.getCf())? s.getCf() : "";
	    	cfAlias+= !StringUtils.isBlank(s.getAlias()) ?  "_" +s.getAlias() : "";
		    dto.setObj(cfAlias);
		    	try {
		    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
		        			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
		    		HashMap<SsOOrganizzazione, List<SintesiSchedeUfficioDTO>> complete = schedaService.countAnagraficaInSsComplete(dto);
		    		s.setSsComplete(mapToList(complete));
		    		
		    		HashMap<SsOOrganizzazione, List<SintesiSchedeUfficioDTO>> incomplete = schedaService.countAnagraficaInSsInComplete(dto);
		    		s.setSsIncomplete(mapToList(incomplete));
		    		
		    	} catch(NamingException e) {
		    		logger.error(e.getMessage(), e);
		    		addError("caricamento.error");
				}
    	}else{
    		s.setSsComplete(new ArrayList<KeyValueDTO>());
    		s.setSsIncomplete(new ArrayList<KeyValueDTO>());
    	}
	}
	
	public void valorizzaPresenzaInCS(Soggetto s){
		
		if(!StringUtils.isBlank(s.getCf())){
			s.setIntErogati(existInterventiErogati(s.getCf()));
			
			BaseDTO dto = new BaseDTO();
	    	fillUserData(dto);
	    	dto.setObj(s.getCf());  
	    	try {
	    		AccessTableSoggettoSessionBeanRemote soggettiService = 
	    				(AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
	    		CsOSettore settore = soggettiService.getLastItStepSettoreSoggetto(dto);
	    		s.setCs(settore!=null);
	    		
	    		if(settore!=null){
	    			CsOOrganizzazione o = settore.getCsOOrganizzazione();
	    			String nomeOrg = o!=null && o.getCodCatastale()!=null ? getLabelOrganizzazione()+" di "+o.getNome() : o.getNome();
	    			
	    			String settore1 = settore!=null ? settore.getNome() : "";
	    			
	    			s.setCsOrganizzazione(nomeOrg);
	    			s.setCsOSettore(settore1);
	    		}
	    	} catch(Exception e) {
	    		logger.error(e.getMessage(), e);
	    		addError("caricamento.error");
			}
		}
	}
     public void valorizzaPresenzaInRP(Soggetto s){
	     s.setRp( s.isAnagRilevazionePresenze());
	}
	private List<KeyValueDTO> mapToList(HashMap<it.webred.ss.data.model.SsOOrganizzazione,List<SintesiSchedeUfficioDTO>> map) throws NamingException{
		PuntoContatto pc = getPreselectedPContatto();
		Long idOrg = pc.getOrganizzazione().getId();
		List<KeyValueDTO> lst = new ArrayList<KeyValueDTO>();
		Iterator<SsOOrganizzazione> it = map.keySet().iterator();
		while(it.hasNext()){
			SsOOrganizzazione o =it.next();
	
			String nome = o.getCodCatastale()!=null ? getLabelOrganizzazione()+" di "+o.getNome() : o.getNome();
			List<SintesiSchedeUfficioDTO> uffs = (List<SintesiSchedeUfficioDTO>)map.get(o);
			for(SintesiSchedeUfficioDTO ss : uffs){
				KeyValueDTO kv = new KeyValueDTO(nome+" - "+ss.getUfficio().getNome(),ss.getTotale().toString());
				
				if(this.canAccessUfficio(ss.getUfficio().getId())){
					if(o.getId()==idOrg)
						lst.add(0, kv);
					else
						lst.add(kv);
				}
			}
		}
	
		
		return lst;
	}
	
	public boolean isInEnteEsterno(String cf){
		it.webred.ss.ejb.dto.BaseDTO dto = new it.webred.ss.ejb.dto.BaseDTO();
    	fillUserData(dto);
    	dto.setObj(cf);  
    	try {
    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
        			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
    		return schedaService.isAnagraficaEsterna(dto);
    		
    	} catch(NamingException e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.error");
		}
    	return false;
	}
	
	public Boolean isInCs(String cf){
		BaseDTO dto = new BaseDTO();
    	fillUserData(dto);
    	dto.setObj(cf);  
    	try {
    		AccessTableSoggettoSessionBeanRemote soggettiService = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface(
    				"CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
    		return soggettiService.esisteSchedaSoggettoByCF(dto);
    		
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.error");
		}
    	return false;
	}
	
	public boolean existInterventiErogati(String cf){
		BaseDTO dto = new BaseDTO();
    	fillUserData(dto);
    	dto.setObj(cf);  
		try {
    		AccessTableInterventoSessionBeanRemote intService = (AccessTableInterventoSessionBeanRemote) ClientUtility.getEjbInterface(
    				"CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
    		return intService.esisteInterventoErogatoByCF(dto);
    		
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.error");
		}
		return false;
	}
	
	public void onViewSchedeClick(){
		
    	if(selectedSoggetto == null){
    		addError("seleziona.soggetto.error");
    	} else if((selectedSoggetto.getSsComplete().isEmpty() && !isEnteEsterno()) || (!selectedSoggetto.isEv() && isEnteEsterno())) {
    		addError("seleziona.altro.soggetto.error");
    	} else if(selectedSoggetto.getSsComplete().isEmpty()){
    		addWarning("avvertenza.soggetto.incompleto");	
    	}else {
    		String url = addParameter(SCHEDE_URL, "UdcSoggetto", selectedSoggetto.getIdSsAnag()+"");
    		try {
    			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    		} catch (IOException e) {
    			logger.error(e);
    		}
    	}
    }
	
	public void onViewSchedeNucleoClick(){
    	if(selectedSoggettoNucleo == null){
    		addError("seleziona.soggetto.error");
    	} else if(selectedSoggetto.getSsComplete().isEmpty()) {
    		addError("seleziona.altro.soggetto.error");
    	} else {
    		String url = addParameter(SCHEDE_URL, "UdcSoggetto", selectedSoggettoNucleo.getIdSsAnag()+"");
    		try {
    			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    		} catch (IOException e) {
    			logger.error(e);
    		}
    	}
    }
	
	
    
    public void onNewSchedaClick(){
		PuntoContatto pCont = this.getPreselectedPContatto();
		if (pCont != null && pCont.getIdPContatto()==null){
			addWarning("no.puntoContatto.error");
			return;
		}
			
    	if(selectedSoggetto == null){
    		addError("seleziona.soggetto.error");
    	}else{
            if(selectedSoggetto.getDataMorte()!=null){
            	addWarning("policy.error","Il soggetto selezionato è deceduto il "+ddMMyyyy.format(selectedSoggetto.getDataMorte()));
				return;
            }
    		
    		if(selectedSoggetto.getCf()==null) addWarning("seleziona.soggetto.noCF");
    		
    		String url = addParameter(NUOVA_SCHEDA_URL, CF_KEY, selectedSoggetto.getCf()+"");
    		if(selectedSoggetto.isAnagEsistenteUDC())
    			url = addParameter(NUOVA_SCHEDA_URL, SOGGETTO_KEY, selectedSoggetto.getIdSsAnag()+"");
    		else //if(selectedSoggetto.isAnagSanitariaUmbria() || selectedSoggetto.isAnagSigess() || selectedSoggetto.isAnagSanitariaMarche() || selectedSoggetto.isAnagRilevazionePresenze())
    		{	String identificativo = selectedSoggetto.getId();
    			if(StringUtils.isBlank(identificativo)){
    				String message = "Il soggetto selezionato "
    						+ "["+selectedSoggetto.getCognome()+" "+selectedSoggetto.getNome()+" c.f.:"+selectedSoggetto.getCf()+"] "
    						+ "non ha un identificativo valido nell'anagrafe di riferimento";
    				logger.warn(message);
    				addWarning("policy.error","Il soggetto selezionato [] non ha un identificativo valido nell'anagrafe di riferimento");
    				if(selectedSoggetto.isAnagSanitariaMarche()){
    					identificativo = selectedSoggetto.getCf().toUpperCase();
    				}else return;
    			}else
    				url = addParameter(NUOVA_SCHEDA_URL, ANAG_WS_KEY, identificativo +"");
    				
    			url = addParameter(url, ANAG_WS_TIPO, selectedSoggetto.getTipoRicercaSoggetto()+"");	
    		}
    		//Matteo Leandri 30/06/2016
    		//recupero il parametro currentLocation. La pagina sulla quale viene fatto il redirect lo utilizza per creare il nome del pulsante "indietro"
    		String clParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentLocation");
    		url = addParameter(url, "currentLocation", clParam != null ? clParam : "");
    		    		
    		try {
    			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    		} catch (IOException e) {
    			logger.error("onNewSchedaClick",e);
    		}
    	}
    }
    
    public void onNucleoFamiliareClick(){
    	if(selectedSoggetto == null){
    		addError("seleziona.soggetto.error");
    	} else if(selectedSoggetto.getDataMorte()!=null){
        	addWarning("policy.error","Il soggetto selezionato è deceduto il "+ddMMyyyy.format(selectedSoggetto.getDataMorte()));
        } else {
    		searchNucleoButtonPressed = true;
    		nucleo = new ArrayList<Soggetto>();
    		
    		try{
    			String tipoRicerca = selectedSoggetto.getTipoRicercaSoggetto();
    				
				String idWs = selectedSoggetto.getId();
				String cf = selectedSoggetto.getCf();
				RicercaAnagraficaParams params = new RicercaAnagraficaParams(!StringUtils.isBlank(tipoRicerca) ? tipoRicerca : DataModelCostanti.TipoRicercaSoggetto.DEFAULT, true);
				fillEnte(params);
				params.setIdentificativo(idWs);
				params.setCf(cf);
				RicercaAnagraficaResult result = CsUiCompBaseBean.getComposizioneFamiliare(params);
				
				List<FamiliareDettaglio> lista = new ArrayList<FamiliareDettaglio>();
				if(result!=null) {
					if(result.getMessaggio()==null && !result.getElencoFamiliari().isEmpty())
						lista = result.getElencoFamiliari();
					else
						logger.error("Componenti familiari non trovati per il soggetto["+cf+"]["+idWs+"] "+tipoRicerca+" CODICE["+result.getCodice()+"]"+ result.getMessaggio(), result.getEccezione());
				}
				
				for(FamiliareDettaglio f : lista){
					Soggetto s = new Soggetto(tipoRicerca, f.getIdentificativo(), null, f.getCognome(), f.getNome(), f.getCodfisc(), f.getDataNascita(), f.getDataMorte(), f.getSesso());
					s.setParentela(f.getParentela()!=null ? f.getParentela().getDescrizione() : "(codice non mappato)");
					nucleo.add(s);
				}
    			
	    		for(Soggetto s: nucleo){
	    			valorizzaPresenzaInSS(s);
					valorizzaPresenzaInCS(s);
	    			s.setEv(isInEnteEsterno(s.getCf()));
					
					if(!s.getSsComplete().isEmpty() || !s.getSsIncomplete().isEmpty())
						setAnagraficaId(s);
				}
	    		
	    		
			
    		}catch(Exception e){
    			addError("load.nucleoFamiliare.error");
    			logger.error("onNucleoFamiliareClick",e);
    		}
    		
			nucleoDataModel = new SoggettiTableDataModel(nucleo);
    	}
    }
    
	public boolean isSearchAnagSanitaria() {
		return searchAnagSanitaria;
	}

	public void setSearchAnagSanitaria(boolean searchAnagSanitaria) {
		this.searchAnagSanitaria = searchAnagSanitaria;
	}

	//#ROMACAPITALE
	public boolean isSearchRilevazionePresenze() {
		return searchRilevazionePresenze;
	}

	public void setSearchRilevazionePresenze(boolean searchRilevazionePresenze) {
		this.searchRilevazionePresenze = searchRilevazionePresenze;
	}

	//FINE #ROMACAPITALE
	public UserSearchExtInput getParams() {
		return params;
	}

	public void setParams(UserSearchExtInput params) {
		this.params = params;
	}
	
	public void onChangeTipoRicerca(){
		this.params.clearCampi();
		soggetti = new ArrayList<Soggetto>();
		selectedSoggetto=null;
		this.setSearchAnagSanitaria(!this.params.isRicercaAlias()); 
	
	}
	
	public void clearParameters(){
		this.params=new UserSearchExtInput();
		soggetti = new ArrayList<Soggetto>();
		nucleo = new ArrayList<Soggetto>();
		selectedSoggetto=null;
		this.soggettiDataModel = new SoggettiTableDataModel(soggetti);
		this.nucleoDataModel = new SoggettiTableDataModel(nucleo);
		searchButtonPressed = false;
		searchNucleoButtonPressed = false;
		RequestContext.getCurrentInstance().update("inputSearch");
		RequestContext.getCurrentInstance().update("table_search_form");
	}

}
