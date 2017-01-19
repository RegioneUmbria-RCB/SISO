package it.webred.ss.web.bean.lista.soggetti;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.SoggettoAnagrafeDTO;
import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.ws.client.anag.client.AnagrafeClient;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;
import it.webred.siso.ws.client.anag.client.RicercaAnagraficaBean;
import it.webred.siso.ws.client.anag.exception.AnagrafeException;
import it.webred.siso.ws.client.anag.exception.AnagrafeSessionException;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.util.PuntoContatto;
import it.webred.ss.web.bean.util.Soggetto;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.NamingException;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ViewScoped
public class SearchBean extends SegretariatoSocBaseBean{ 
  
    private String cognome;
    private String nome;
    private String cf;
    private boolean searchAnagSanitaria = true;
    
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
	private static final String REPORT_PATH = "/reports/modulo_scheda.pdf";
	private static final String PRIVACY_PATH = "/reports/modulo_privacy.pdf";
  	

	public boolean isSearchButtonPressed() {
		return searchButtonPressed;
	}

	public void setSearchButtonPressed(boolean searchButtonPressed) {
		this.searchButtonPressed = searchButtonPressed;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
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
		
		if(cf!=null && !cf.isEmpty() && cf.length()!=16)
			addWarning("search.cf.nonvalido","La ricerca verrà fatta per Cognome e Nome");
		
		if((cognome == null && nome == null && cf == null ) || (cognome.isEmpty() && nome.isEmpty() && cf.isEmpty()))
			addError("compila.error");
		else {
			searchButtonPressed = true;
			searchNucleoButtonPressed = false;

			TreeMap<String, Soggetto> results = new TreeMap<String, Soggetto>();
			soggetti = new ArrayList<Soggetto>();
			
			//Ricerco solo per codice fiscale
			if(cf !=null && !cf.isEmpty()){
				List<SitDPersona> list = readSoggettiFromAnagrafeByCf(cf);
				if(list != null)	
						for(SitDPersona sog: list)
							addSoggettoAnagComLista(sog,results);
						
				//se non trova il cf in ANAGRAFE COMUNALE cerca tra le SCHEDE SS CREATE e VALIDE (=soggetto NON RESIDENTE NEL COMUNE)
				if(list.isEmpty()){	
					List<SsAnagrafica> listaNnResidenti = readFromSchedaAnagraficaByCf(cf);
					if(listaNnResidenti != null)
						soggettiUdc.addAll(listaNnResidenti);
				}		
			}
			
			if(cognome != null && !cognome.isEmpty()){
				List<SitDPersona> salist = readSoggettiFromAnagrafeByName(cognome, nome);
				if(salist != null){
					for(SitDPersona s: salist)
						addSoggettoAnagComLista(s,results);
	
					if(salist.isEmpty()){
						salist = readSoggettiFromAnagrafeByName(cognome, "");
						for(SitDPersona s: salist){
							if(s.getNome().startsWith(nome.trim().toUpperCase()))
								addSoggettoAnagComLista(s,results);
						}
					}
				}
				List<SsAnagrafica> schedeSegr = readFromSchedaAnagraficaByName(cognome, nome);
				if(schedeSegr != null)
					soggettiUdc.addAll(schedeSegr);
			}
			
			
			if(searchAnagSanitaria){
				List<Soggetto> lstAnagSanitaria = this.ricercaInAnagrafeSanitaria(cf, cognome, nome);
				for(Soggetto s : lstAnagSanitaria){
					String key = s.getCf();
					if(!results.containsKey(key))
						results.put(key, s);
				}
			}
			
			//Aggiungo le anagrafiche presenti in SS solo per quei soggetti che non ho trovato nè in anagrafe sanitaria, nè in quella comunale
			for(SsAnagrafica ss : soggettiUdc)
				addSoggettoUdCLista(ss,results);
			
			
			SortedSet<String> keys = new TreeSet<String>(results.keySet());
			//soggetti.addAll(results.values());
			for(String k: keys){
				Soggetto s = results.get(k);
				valorizzaPresenzaInSS(s.getCf(), s);
				s.setEv(isInEnteEsterno(s.getCf()));
				s.setCs(isInCs(s.getCf()));
				s.setIntErogati(existInterventiErogati(s.getCf()));
				soggetti.add(s);
				if(!s.getSsComplete().isEmpty() || !s.getSsIncomplete().isEmpty()) //Serve a recuperare la lista di schede aperte
					setAnagraficaId(s);
			}
			
			soggettiDataModel = new SoggettiTableDataModel(soggetti);
		}
	}
	
	private void addSoggettoAnagComLista(SitDPersona s, TreeMap<String,Soggetto> results){
		
		boolean emigrato = s.getDataEmi()!=null && (s.getDataImm()==null || s.getDataImm().before(s.getDataEmi()));
		if(!emigrato){
			String key = s.getCodfisc();
			if(key==null|| key.trim().isEmpty()){
				String descrizione = s.getCognome()+" "+s.getNome()+" "+s.getDataNascita();
				key=Integer.toString(descrizione.hashCode());
				logger.debug(s.getCognome()+" "+s.getNome()+" non ha cod.fiscale: registrato con id "+key);
			}else
				key = key.toUpperCase();
				
			Soggetto val = null;
			if(!results.containsKey(key))
				val = new Soggetto(null, s.getId(), null, s.getCognome(), s.getNome(), s.getCodfisc(), s.getDataNascita(), s.getDataMor(), s.getSesso());
			else
				val = results.get(key);
				
			val.setIdExt(s.getIdExt());
			if(s.getDataMor()==null){
				IndirizzoAnagrafeDTO i = getResidenzaFromAnagrafe(s);
				if(i!=null){
					Date oggi = new Date();
					if((i.getDtIniVal()==null || oggi.compareTo(i.getDtIniVal())>=0) && (i.getDtFinVal()==null || oggi.compareTo(i.getDtFinVal())<=0))
					val.setIndirizzo(i);
				}
			}
			results.put(key, val);
		}

	}
	
	private void addSoggettoUdCLista(SsAnagrafica s, TreeMap<String,Soggetto> results){
		String key = s.getCf()!=null ? s.getCf().toUpperCase() : null;
		Soggetto val = null;
		if(!results.containsKey(key)){
			val = new Soggetto(null, null, s.getId(), s.getCognome(), s.getNome(), key, s.getData_nascita(), null, s.getSesso());
			val.setAnagEsterna(true);
			results.put(key, val);
		}
	}
	
	public boolean isAbilitaSearchAnagSanitaria(){
		boolean abilita = false;
		try {
			AnagRegUser anagReguser = getAnagRegUser();
			URL wsdlLocation = getAnagRegWebServiceWSDLLocation();
			abilita = (anagReguser!=null && wsdlLocation!=null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return abilita;
	}
	
	private List<Soggetto> ricercaInAnagrafeSanitaria(String codFisc, String cognome,String nome){
		List<Soggetto> listAutocomplete = new ArrayList<Soggetto>();
		
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
				if((cognome!=null && !cognome.isEmpty()) || (nome!=null && !nome.isEmpty())){
					anag.openSession(rb);
					RicercaAnagraficaBean rab= new RicercaAnagraficaBean();
					rab.setCodiceFiscale(codFisc!=null ? codFisc.toUpperCase() : "");
					rab.setCognomePaziente(cognome!=null ? cognome.trim() : "");
					rab.setNomePaziente(nome!=null ? nome.trim() : "");
					List <PersonaFindResult> l1=anag.findCognomeNome(rab);
					if(l1!=null && !l1.isEmpty()){
						listAnagReg.addAll(l1);
					}
					
					listAnagReg = filtraByCodFiscale(codFisc,listAnagReg);
					
					if(listAnagReg.size()>=100)
						addWarning("search.anag.sanitaria.risultati.limitati","verrano mostrati solo i primi "+50+" record");
					
					int maxResultAnagReg=listAnagReg.size()>50 ? 51 : listAnagReg.size();
					for(int i=0; i<maxResultAnagReg; i++){
						PersonaFindResult s=listAnagReg.get(i);
						String cf = s.getCodfisc();
						Soggetto sogg = new Soggetto(new Long(s.getIdPaziente()),null,null, s.getCognome(), s.getNome(), cf, s.getDataNascita(), s.getDataMor(), s.getSesso());
						sogg.setAnagSanitaria(true); // *Ricavato da anagrafe sanitaria regionale*
						//sDto.setItemLabel(itemLabel);
						//sDto.setId("SANITARIA"+s.getIdPaziente());
						listAutocomplete.add(sogg);
	
					}
				anag.closeSession();
			  }else
				addWarning("search.anag.sanitaria.parametri");
			}
			}catch (AnagrafeException e) {
				logger.error(e.getMessage(), e);
			} catch (AnagrafeSessionException e) {
				logger.error(e.getMessage(), e);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		return listAutocomplete;
	}
	
	private List <PersonaFindResult> filtraByCodFiscale(String cfSearch, List<PersonaFindResult> all){
		if(cfSearch!=null && !cfSearch.isEmpty() && cfSearch.length()==16){
			List <PersonaFindResult> lstcf = new ArrayList<PersonaFindResult>();
			for(PersonaFindResult p : all){
				if(cfSearch.equalsIgnoreCase(p.getCodfisc()))
					lstcf.add(p);
			}
			return lstcf;
		}else
			return all;
	}
	
	//modifica
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
	
	public void valorizzaPresenzaInSS(String cf, Soggetto s){
		it.webred.ss.ejb.dto.BaseDTO dto = new it.webred.ss.ejb.dto.BaseDTO();
    	fillUserData(dto);
    	dto.setObj(cf);
    	try {
    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
        			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
    		HashMap<it.webred.ss.data.model.SsOOrganizzazione,Integer> complete = schedaService.countAnagraficaInSsComplete(dto);
    		s.setSsComplete(mapToList(complete));
    		
    		HashMap<it.webred.ss.data.model.SsOOrganizzazione,Integer> incomplete = schedaService.countAnagraficaInSsInComplete(dto);
    		s.setSsIncomplete(mapToList(incomplete));
    		
    	} catch(NamingException e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.error");
		}
	}
	
	private List<KeyValueDTO> mapToList(HashMap<it.webred.ss.data.model.SsOOrganizzazione,Integer> map) throws NamingException{
		PuntoContatto pc = getPreselectedPContatto();
		Long idOrg = pc.getOrganizzazione().getId();
		List<KeyValueDTO> lst = new ArrayList<KeyValueDTO>();
		Iterator<SsOOrganizzazione> it = map.keySet().iterator();
		while(it.hasNext()){
			SsOOrganizzazione o =it.next();
	
			String nome = o.getBelfiore()!=null ? "Comune di "+o.getNome() : o.getNome();
			KeyValueDTO kv = new KeyValueDTO(nome,map.get(o).toString());
			
			if(o.getId()==idOrg)
				lst.add(0, kv);
			else
				lst.add(kv);
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
	
	private List<SitDPersona> readSoggettiFromAnagrafeByName(String cognome, String nome){
		RicercaSoggettoAnagrafeDTO rsDto = new RicercaSoggettoAnagrafeDTO();
		fillUserData(rsDto);
		rsDto.setDenom((cognome + " " + nome).trim());
    		
    	try {
    		AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface(
    				"CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");

    		return anagrafeService.getListaPersoneByDenominazione(rsDto);
    		
    	} catch(NamingException e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.soggetto.error");
		}
    	return null;
	}
	
	private List<SitDPersona> readSoggettiFromAnagrafeByCf(String cf){
		RicercaSoggettoAnagrafeDTO dto = new RicercaSoggettoAnagrafeDTO();
    	fillUserData(dto);
    	dto.setCodFis(cf);
    	dto.setDtRif(new Date());
    	
    	try {
    		AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface(
    				"CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
    		return anagrafeService.getListaPersoneByCF(dto);
    		
    	} catch(NamingException e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.soggetto.error");
		}
    	return null;
	}
	
	private List<SoggettoAnagrafeDTO> searchSoggettiFromAnagrafe(String cognome, String nome, String cf){
		RicercaSoggettoAnagrafeDTO dto = new RicercaSoggettoAnagrafeDTO();
    	fillUserData(dto);
    	dto.setCodFis(cf);
    	dto.setCognome(cognome);
    	dto.setNome(nome);
    	
    	try {
    		AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface(
    				"CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
    		return anagrafeService.searchSoggetto(dto);
    		
    	} catch(NamingException e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.soggetto.error");
		}
    	return null;
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
    		
    		if(selectedSoggetto.getCf()==null)
    			addWarning("seleziona.soggetto.noCF");
    		
    		String url = addParameter(NUOVA_SCHEDA_URL, CF_KEY, selectedSoggetto.getCf()+"");
    		
    		if(selectedSoggetto.isAnagSanitaria())
    			url = addParameter(NUOVA_SCHEDA_URL, ANAG_SAN_KEY, selectedSoggetto.getIdAnagSanitaria()+"");
    		
    		if(selectedSoggetto.isAnagEsterna())
    			url = addParameter(NUOVA_SCHEDA_URL, SOGGETTO_KEY, selectedSoggetto.getIdSsAnag()+"");
    		
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
    		
	    		List<ComponenteFamigliaDTO> famiglia = getNucleoFamiliare(selectedSoggetto.getIdExt(), selectedSoggetto.getCf());
	    		
	    		for(ComponenteFamigliaDTO c: famiglia){
	    			SitDPersona p = c.getPersona();
	    			Soggetto s = new Soggetto(new Long(-1), p.getId(), null, p.getCognome(), p.getNome(), p.getCodfisc(), p.getDataNascita(), p.getDataMor(),p.getSesso());
	    			s.setParentela(getRelazioneParentaleCs(c.getRelazPar()));
	    			
	    			nucleo.add(s);
	    		}
	    		
	    		for(Soggetto s: nucleo){
	    			valorizzaPresenzaInSS(s.getCf(), s);
					s.setEv(isInEnteEsterno(s.getCf()));
					s.setCs(isInCs(s.getCf()));
					s.setIntErogati(existInterventiErogati(s.getCf()));
					
					if(!s.getSsComplete().isEmpty() || !s.getSsIncomplete().isEmpty())
						setAnagraficaId(s);
				}
			
    		}catch(Exception e){
    			addError("load.nucleoFamiliare.error");
    		}
    		
			nucleoDataModel = new SoggettiTableDataModel(nucleo);
    	}
    }
    
    public StreamedContent getReport(){
		InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(REPORT_PATH);
		return new DefaultStreamedContent(stream, "application/pdf", "modulo_report.pdf");
    }
    
    public StreamedContent getPrivacy(){
		InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(PRIVACY_PATH);
		return new DefaultStreamedContent(stream, "application/pdf", "modulo_privacy.pdf");
    }

	public boolean isSearchAnagSanitaria() {
		return searchAnagSanitaria;
	}

	public void setSearchAnagSanitaria(boolean searchAnagSanitaria) {
		this.searchAnagSanitaria = searchAnagSanitaria;
	}
    
}