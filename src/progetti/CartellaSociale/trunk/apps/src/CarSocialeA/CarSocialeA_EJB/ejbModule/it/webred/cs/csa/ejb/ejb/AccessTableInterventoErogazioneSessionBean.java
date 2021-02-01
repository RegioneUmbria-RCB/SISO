package it.webred.cs.csa.ejb.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoErogazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.InterventoDAO;
import it.webred.cs.csa.ejb.dao.InterventoErogazioneDAO;
import it.webred.cs.csa.ejb.dao.IterDAO;
import it.webred.cs.csa.ejb.dao.OperatoreDAO;
import it.webred.cs.csa.ejb.dao.VMobiDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ColloquioDTO;
import it.webred.cs.csa.ejb.dto.CsRelRelazioneProblDTO;
import it.webred.cs.csa.ejb.dto.DiarioAnagraficaDTO;
import it.webred.cs.csa.ejb.dto.DocIndividualeDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.RelazioneDTO;
import it.webred.cs.csa.ejb.dto.TriageItemDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrBean;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogSettoreTipoIntCfgDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindCasiByOperatoreBySoggettoRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindCasiByUsernameOperatoreRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO;
import it.webred.cs.csa.ejb.dto.mobi.VmobiCasiDTO;
import it.webred.cs.csa.ejb.dto.mobi.upload.CsRelRelazioneProblRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.upload.UploadMobileAttivitaProfessionaliDTO;
import it.webred.cs.csa.ejb.dto.mobi.upload.UploadMobileDTO;
import it.webred.cs.csa.ejb.dto.mobi.upload.UploadMobileDocumentiDTO;
import it.webred.cs.csa.ejb.dto.mobi.upload.UploadMobileErogazioniDTO;
import it.webred.cs.csa.ejb.dto.mobi.upload.UploadMobileValoreDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiErogazioneInterventi;
import it.webred.cs.data.DataModelCostanti.TipoDatoMobile;
import it.webred.cs.data.DataModelCostanti.TipoDocumentoMobile;
import it.webred.cs.data.DataModelCostanti.TipoStatoErogazione;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCDiarioConchi;
import it.webred.cs.data.model.CsCDiarioDove;
import it.webred.cs.data.model.CsCTipoColloquio;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsDColloquio;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIInterventoEsegValore;
import it.webred.cs.data.model.CsIInterventoPr;
import it.webred.cs.data.model.CsIValQuota;
import it.webred.cs.data.model.CsMobileStaging;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsRelRelazioneProbl;
import it.webred.cs.data.model.CsTbMicroAttivita;
import it.webred.cs.data.model.CsTbProbl;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.data.model.view.VMobiCasi;
import it.webred.cs.data.model.view.VMobiIntErog;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

@Stateless
public class AccessTableInterventoErogazioneSessionBean extends CarSocialeBaseSessionBean implements AccessTableInterventoErogazioneSessionBeanRemote {

	private static final long serialVersionUID = 1L;
	@Autowired
	private VMobiDAO vMobiDao;
	
	@Autowired
	private InterventoDAO interventoDao;
	
	@Autowired
	private ConfigurazioneDAO configurazioneDao;
	
	@Autowired
	private IterDAO iterDao;
	
	@Autowired
	private InterventoErogazioneDAO interventoErogDao;
	
	@EJB
	private AccessTableInterventoSessionBeanRemote interventoService;
	
	@EJB
	private AccessTableSoggettoSessionBeanRemote soggettoService;
	
	@EJB
	private AccessTableDiarioSessionBeanRemote diarioService;
	
	@EJB
	private AccessTableCasoSessionBeanRemote casoService; 
	
	
	
	@Autowired 
	private OperatoreDAO operatoreDao;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/LoginBean")
	private LoginBeanService loginService;
	
	
    private List<VMobiIntErog> findListaErogazioniByIdSettore(FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO dto) {
    	
    	List<VMobiIntErog> lst = vMobiDao.findVMobiIntErogByIdSettoreErogante(dto) ;
    	return lst;
    }
    
    private List<VMobiIntErog> findIntEsegMastIdByCasoId(FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO request, BigDecimal casoId)
    {
    	
    	//implementa native query per recuperare gli INT_ESEG_MAST_ID su CS_I_INTERVENTO_ESEG_MAST_SOGG a partire da caso_id
    	List<VMobiIntErog> esegMastList=vMobiDao.findIntEsegMastIdByCasoId(request,casoId);
    	return esegMastList;
    }
    
//    private List<VMobiIntErog> findListaErogazioniByCsInterventoEsegMastSoggList(List<BigDecimal> Er_IdList)
//    {
//    	//implemeta accesso alla VMobiIntErog con ER_ID in lista id passati
//    	List<VMobiIntErog> lst = vMobiDao.findListaErogazioniByCsInterventoEsegMastSoggList(Er_IdList);
//    	return lst;
//    	
//    }
	
	@Override
    public FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO findErogazioniOrganizzazione(BaseDTO dto) {
		FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO request = (FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO)dto.getObj();
		FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO response = new FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO();
	
		CeTBaseObject o = new CeTBaseObject();
		o.setEnteId(dto.getEnteId());
		o.setUserId(dto.getUserId());
		o.setSessionId(dto.getSessionId());
		
		logger.debug("Sto caricando la configurazione dei permessi per l'utente "+dto.getUserId()+" nell'organizzazione "+dto.getEnteId());
	   	//1.Per ciascun settore (operatore/Settore) recupero il gruppo di appartenenza ed i permessi (autorizzativi/erogativi) associati
		Hashtable<BigDecimal, List<String>> mappaTipiStato = getPermessiErogazioneGruppoSettore(o);
    	
    	List<BigDecimal> lstSettori = new ArrayList<BigDecimal>();
    	if(!mappaTipiStato.isEmpty())
    	   lstSettori.addAll(mappaTipiStato.keySet());
  
		request.setIdSettori(lstSettori);
		
		logger.debug("Sto caricando la lista erogazioni");
		List<VMobiIntErog> lst = this.findListaErogazioniByIdSettore(request);
		response.setvMobiIntErog(lst);
		
	    //Carica la configurazione dei tipi intervento
		// momentaneamente commentata
		logger.debug("Sto caricando la configurazione erogazioni");
		List<ErogSettoreTipoIntCfgDTO> mappa = loadConfigurazioneTipiIntervento(o, mappaTipiStato);
		response.setConfigInterventi(mappa);
		
		
		logger.debug("Sto caricando la configurazione dei diari");
		
		List<KeyValueDTO> lstConChis = new ArrayList<KeyValueDTO>();
		for(CsCDiarioConchi c : configurazioneDao.findAllDiarioConchis()){
			if("1".equalsIgnoreCase(c.getAbilitato())) lstConChis.add(new KeyValueDTO(c.getId(), c.getDescrizione()));
		}
		response.setLstDiarioConChi(lstConChis);
		
		List<KeyValueDTO> lstDiarioDove = new ArrayList<KeyValueDTO>();
		for(CsCDiarioDove c : configurazioneDao.findAllDiarioDoves()){
			if("1".equalsIgnoreCase(c.getAbilitato())) lstDiarioDove.add(new KeyValueDTO(c.getId(), c.getDescrizione()));
		}
		response.setLstDiarioDove(lstDiarioDove);
		
		List<KeyValueDTO> lstTipi = new ArrayList<KeyValueDTO>();
		for(CsCTipoColloquio c : configurazioneDao.findAllTipoColloquios()){
			if("1".equalsIgnoreCase(c.getAbilitato())) lstTipi.add(new KeyValueDTO(c.getId(), c.getDescrizione()));
		}
		response.setLstTipoColloquio(lstTipi);
		
		//Specifica di SISO-1044
		
				//devo accedere per CASO con erogazione anche non attiva o CASO senza erogazioni ma almeno preso in carico...
				List<VmobiCasiDTO> lstCasi = new ArrayList<VmobiCasiDTO>();
				int i=0;
					if(lst != null && !lst.isEmpty())	
					{
						for (VMobiIntErog vMobiIntErog : lst) {
//							if(i>10)
//							{
//								
//								break;
//							}
							FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO requestCasi = new FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO();
							try {
								if (vMobiIntErog.getErSoggCasoId()!=null && vMobiIntErog.getErSoggCasoId().longValue()>0)
								{
									requestCasi.setCasoId(vMobiIntErog.getErSoggCasoId());
									
									VmobiCasiDTO casoFound = null;
									casoFound=this.findCasoById(requestCasi);
									if(!isPresente(lstCasi,casoFound.getCasoId()))
									{
										if(casoFound!=null && casoFound.getCasoId()!=null && casoFound.getCasoId().longValue()>0)
										{
											//implementa chiamate per caricare configurazioni aggiuntive. Le stesse configurazioni vanno caricate anche per la vecchia ricerca
											try {
												List<DiarioAnagraficaDTO> anagraficheSoggettiInteressati = configurazioneDao.getUlterioriSoggettiInteressati(casoFound.getCasoId().longValue());
												
												casoFound.setAnagraficheSoggettiInteressati(anagraficheSoggettiInteressati);
												
											} catch (Exception e) {
												logger.debug("Errore recupero anagraficheSoggettiInteressati  per il caso");
											}
											
											try {
												List<CsRelRelazioneProblDTO> problematichePrecedenti = configurazioneDao.getProblematichePrecedenti(casoFound.getCasoId().longValue());
												casoFound.setProblematichePrecedenti(problematichePrecedenti);
												
											} catch (Exception e) {
												logger.debug("Errore recupero problematichePrecedenti il caso");
											}
											
											lstCasi.add(casoFound);
												
											
										}
										
									}
									
										
										
									
									
									
								}
							} catch (Exception e) {
								logger.debug("Errore recupero SoggettiInteressati e ProblematichePrecedenti per il caso");
							}
							i++;
							
						}
						
						
					}
						
				response.setvMobiCasiDTO(lstCasi);
				
				//SISO-1044********
		//TASK SISO-1044
		//Carica la configurazione dei tipi intervento
		
				
				
				List<SelectItem> catalogoattivita = configurazioneDao.getCatalogoattivita();
				response.setCatalogoattivita(catalogoattivita);
				List<SelectItem> catalogoProblematiche = configurazioneDao.getCatalogoProblematiche();
				response.setCatalogoProblematiche(catalogoProblematiche);
				ArrayList<CsCDiarioConchi> listaConChi = configurazioneDao.getListaConChi();
				response.setListaConChi(listaConChi);
				List<SelectItem> listaRiunioneCon = configurazioneDao.getListaRiunioneCon(true);
				response.setListaRiunioneCon(listaRiunioneCon);
				List<SelectItem> listaRichiestaIndagine= configurazioneDao.getListaRichiestaIndagine();
				response.setListaRichiestaIndagine(listaRichiestaIndagine);
				HashMap<String, List<TriageItemDTO>> triageValueMap = configurazioneDao.getTriageValueMap();
				response.setTriageValueMap(triageValueMap);
				
		//************ TASK-1044
		
		return response;
	}
	
private boolean isPresente(List<VmobiCasiDTO> lstCasi,BigDecimal casoId)
{
	boolean isPresente=false;
	
	for (VmobiCasiDTO vmobiCasiDTO : lstCasi) {
		if(vmobiCasiDTO.getCasoId().equals(casoId))
		{
			isPresente=true;
			break;
		}
			
	}
	
	return isPresente;
	
}
    private VmobiCasiDTO findCasoById(
			FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO requestCasi) {

    	VMobiCasi caso = vMobiDao.findVMobiCasiById(requestCasi) ;
    	VmobiCasiDTO casoDto =null;
    	
    	if(caso!=null&& caso.getCasoId()!=null){
    		casoDto = new VmobiCasiDTO();
    		casoDto.setByVmobiCaso(caso);

    		
    	}
    	
				
    	return casoDto;
	}



	private List<ErogSettoreTipoIntCfgDTO> loadConfigurazioneTipiIntervento(CeTBaseObject dto, Hashtable<BigDecimal, List<String>> mappaTipiStato) {
    	
    	List<BigDecimal> lstSettori = new ArrayList<BigDecimal>();
    	if(!mappaTipiStato.isEmpty())
    		lstSettori.addAll(mappaTipiStato.keySet());     //Settori per cui l'utente è abilitato
    	
    	String username = dto.getUserId();
    	List<ErogSettoreTipoIntCfgDTO> config = new ArrayList<ErogSettoreTipoIntCfgDTO>();
    	
    	//2.A questo punto recupero la lista di tipi intervento
    	//Carico la configurazione per tutti i Tipi Intervento (li filtro in un secondo momento)
    	List<CsCTipoIntervento> lstTipiIntervento = interventoDao.findTipiIntervento(false);
    	
    	if(username!=null){
	    	BaseDTO bdto = new BaseDTO();
			bdto.setEnteId(dto.getEnteId());
			bdto.setUserId(username);
			bdto.setSessionId(dto.getSessionId());
	    	
			Hashtable<Long,HashMap<Long,ErogStatoCfgDTO>> mappaCfgTipoInt = new Hashtable<Long,HashMap<Long,ErogStatoCfgDTO>>();
	    	for(CsCTipoIntervento c : lstTipiIntervento){
	    		
	    		bdto.setObj(c.getId()); 
	    		
	    		//3.Per ciascun tipo intervento carico la lista di configurazione per stato
	    		HashMap<Long,ErogStatoCfgDTO> mappaStati =  interventoService.findConfigIntEsegByTipoIntervento(bdto);
	    		if(mappaStati!=null && !mappaStati.isEmpty())
	    			mappaCfgTipoInt.put(c.getId(), mappaStati);
	    	}
	    	
	    	//Filtro gli stati
	    	for(BigDecimal idSettore : lstSettori){
	    		
	    		List<String> tipiStato = mappaTipiStato.get(idSettore);
	    		if(tipiStato!=null && !tipiStato.isEmpty()){
			    	for(CsCTipoIntervento c : lstTipiIntervento){
		
			    		List<CsCfgIntEsegStato> lstStatiAbil = interventoErogDao.getListaIntEsegStatoByTipiStato(tipiStato, c.getId());
			    		HashMap<Long,ErogStatoCfgDTO> mappaStati = mappaCfgTipoInt.get(c.getId());
			    		
			    		List<ErogStatoCfgDTO> lstStatiAbilitati = new ArrayList<ErogStatoCfgDTO>();
			    		for(CsCfgIntEsegStato s : lstStatiAbil){
			    			lstStatiAbilitati.add(mappaStati.get(s.getId()));
			    		}
			    		
			    		ErogSettoreTipoIntCfgDTO cfg = new ErogSettoreTipoIntCfgDTO(c.getId(), idSettore.longValue());
			    		cfg.setLstStati(lstStatiAbilitati);
			    		
			    		config.add(cfg);
			    	}
	    		}else
	    			logger.debug("Impossibile recuperare i permesssi per EROGAZIONE INTERVENTI per il settore [id:"+idSettore+"]");
	    	}	
		    
    	}else logger.debug("Impossibile recuperare la configurazione per EROGAZIONE INTERVENTI: Identificativo utente non specificato.");
	    	
    	return config;
    }

    @Override
	public List<VMobiCasi> findCasiByUsernameOperatore(BaseDTO dto) {
    	List<VMobiCasi> lst = vMobiDao.findVMobiCasiByUsernameOperatore((FindCasiByUsernameOperatoreRequestDTO)dto.getObj()) ;
    	return lst;
	}
    
    //TODO TASK 1044
    @Override
    public FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO findCasiRicercaSoggetto(BaseDTO dto) {
    	//devo restituire lo stesso DTO restituito dalla vecchia ricerca
    	FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO response = new FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO();
		FindCasiByOperatoreBySoggettoRequestDTO request = (FindCasiByOperatoreBySoggettoRequestDTO)dto.getObj();
		
		
				
		CeTBaseObject o = new CeTBaseObject();
		o.setEnteId(dto.getEnteId());
		o.setUserId(dto.getUserId());
		o.setSessionId(dto.getSessionId());
		
		logger.debug("Sto caricando i risultati della ricerca CASI l'utente "+dto.getUserId()+" nell'organizzazione "+dto.getEnteId());
	   	//1.Per ciascun settore (operatore/Settore) recupero il gruppo di appartenenza ed i permessi (autorizzativi/erogativi) associati
		Hashtable<BigDecimal, List<String>> mappaTipiStato = getPermessiErogazioneGruppoSettore(o);
    	
    	List<BigDecimal> lstSettori = new ArrayList<BigDecimal>();
    	if(!mappaTipiStato.isEmpty())
    	   lstSettori.addAll(mappaTipiStato.keySet());
  
		request.setIdSettori(lstSettori);
		request.setUsername(dto.getUserId());
		
		logger.debug("Sto caricando la lista dei casi trovati");
		//devo accedere per CASO con erogazione anche non attiva o CASO senza erogazioni ma almeno preso in carico...
		List<VmobiCasiDTO> lstCasi = this.findCasiByOperatoreBySoggetto(request);
		
		
		
		
		response.setvMobiCasiDTO(lstCasi);
		
		
		return response;
    }
    
    //TODO scelto il CASO provo a ricavare le erogazioni e tutte le configurazioni
    @Override
    public FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO findErogazioniSoggettoSelezionato(BaseDTO dto) {
    	//devo restituire lo stesso DTO restituito dalla vecchia ricerca
    	//i parametri sono gli stessi della vecchia ricerca in più gli passo il casoId su Obj2
    	FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO request = (FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO)dto.getObj();
		FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO response = new FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO();
    	//Obj2 è semplicemente un BigDecimal contenente casoId; 
		//in realta casoId è anche un parametro di Obj
    	BigDecimal casoId = (BigDecimal)dto.getObj2();
		
		List<CsIInterventoEsegMast> requestErogazioni = new ArrayList<CsIInterventoEsegMast>();
		
		
				
		CeTBaseObject o = new CeTBaseObject();
		o.setEnteId(dto.getEnteId());
		o.setUserId(dto.getUserId());
		o.setSessionId(dto.getSessionId());
		
		
		logger.debug("Sto caricando la configurazione dei permessi per l'utente "+dto.getUserId()+" nell'organizzazione "+dto.getEnteId());
	   	//1.Per ciascun settore (operatore/Settore) recupero il gruppo di appartenenza ed i permessi (autorizzativi/erogativi) associati
		Hashtable<BigDecimal, List<String>> mappaTipiStato = getPermessiErogazioneGruppoSettore(o);
    	
    	List<BigDecimal> lstSettori = new ArrayList<BigDecimal>();
    	if(!mappaTipiStato.isEmpty())
    	   lstSettori.addAll(mappaTipiStato.keySet());
  
		request.setIdSettori(lstSettori);
		
		
		//vanno inseriti all'interno del nodo vmobicasi. Questo perche in fase iniziale leggo tutti i casi dell'organizzazione e quindi avra un elenco di casi
		//implementa chiamate per caricare configurazioni aggiuntive. Le stesse configurazioni vanno caricate anche per la vecchia ricerca
		List<DiarioAnagraficaDTO> anagraficheSoggettiInteressati = configurazioneDao.getUlterioriSoggettiInteressati(casoId.longValue());
		//vanno inseriti all'interno del nodo vmobicasi. Questo perche in fase iniziale leggo tutti i casi dell'organizzazione e quindi avra un elenco di casi
		List<CsRelRelazioneProblDTO> problematichePrecedenti = configurazioneDao.getProblematichePrecedenti(casoId.longValue());
				
		//E' SEMPRE UN SOLO CASO ID	- quindi scorro una lista di un unico elemnto e gli assegno sempre le stesse liste soggettiInteressati e problematiche precedenti
		//Specifica di SISO-1044
		//devo accedere per CASO con erogazione anche non attiva o CASO senza erogazioni ma almeno preso in carico...
		VmobiCasiDTO casoDto = this.findCasiByOperatoreBySoggetto(request);
		List<VmobiCasiDTO> lstCasiCompleto = new ArrayList<VmobiCasiDTO>();
	    casoDto.setAnagraficheSoggettiInteressati(anagraficheSoggettiInteressati);
		casoDto.setProblematichePrecedenti(problematichePrecedenti);
		lstCasiCompleto.add(casoDto);
		
		response.setvMobiCasiDTO(lstCasiCompleto);
		//SISO-1044********
		
		logger.debug("Sto caricando la lista erogazioni");
		//dal caso recupero la lista degli id delle erogazioni (INT_ESEG_MAST_ID)
		
		//recupero la lista  List<VMobiIntErog> dalla lista di CS_I_INTERVENTO_ESEG_MAST.ID
		//IN TEORIA SARA? SEMPRE VUOTA... ALTRIMENTI IL SOGGETTO LO TROVAVO CON L'ALTRA RICERCA
		//ATTENZIONE: potrei non avere erogazioni per quel caso (caso preso in carico ma senza erogazioni)
		List<VMobiIntErog> lstVMobiIntErog = this.findIntEsegMastIdByCasoId(request,casoId);
		response.setvMobiIntErog(lstVMobiIntErog);
		
	    //Carica la configurazione dei tipi intervento
		
		//TODO togli i commenti loadConfigurazioneTipiIntervento e setConfigInterventi
		logger.debug("Sto caricando la configurazione erogazioni");
		List<ErogSettoreTipoIntCfgDTO> mappa = loadConfigurazioneTipiIntervento(o, mappaTipiStato);
		response.setConfigInterventi(mappa);
		
		
		logger.debug("Sto caricando la configurazione dei diari");
		//li prende gia dalla sincronizzazione iniziale e non dipendono dal soggetto/caso_Id
//		List<KeyValueDTO> lstConChis = new ArrayList<KeyValueDTO>();
//		for(CsCDiarioConchi c : configurazioneDao.findAllDiarioConchis()){
//			if("1".equalsIgnoreCase(c.getAbilitato())) lstConChis.add(new KeyValueDTO(c.getId(), c.getDescrizione()));
//		}
//		response.setLstDiarioConChi(lstConChis);
//		
//		List<KeyValueDTO> lstDiarioDove = new ArrayList<KeyValueDTO>();
//		for(CsCDiarioDove c : configurazioneDao.findAllDiarioDoves()){
//			if("1".equalsIgnoreCase(c.getAbilitato())) lstDiarioDove.add(new KeyValueDTO(c.getId(), c.getDescrizione()));
//		}
//		response.setLstDiarioDove(lstDiarioDove);
//		
//		List<KeyValueDTO> lstTipi = new ArrayList<KeyValueDTO>();
//		for(CsCTipoColloquio c : configurazioneDao.findAllTipoColloquios()){
//			if("1".equalsIgnoreCase(c.getAbilitato())) lstTipi.add(new KeyValueDTO(c.getId(), c.getDescrizione()));
//		}
//		response.setLstTipoColloquio(lstTipi);
//		
		
				
		List<SelectItem> catalogoattivita = configurazioneDao.getCatalogoattivita();
		response.setCatalogoattivita(catalogoattivita);
		List<SelectItem> catalogoProblematiche = configurazioneDao.getCatalogoProblematiche();
		response.setCatalogoProblematiche(catalogoProblematiche);
		ArrayList<CsCDiarioConchi> listaConChi = configurazioneDao.getListaConChi();
		response.setListaConChi(listaConChi);
		List<SelectItem> listaRiunioneCon = configurazioneDao.getListaRiunioneCon(true);
		response.setListaRiunioneCon(listaRiunioneCon);
		List<SelectItem> listaRichiestaIndagine= configurazioneDao.getListaRichiestaIndagine();
		response.setListaRichiestaIndagine(listaRichiestaIndagine);
		HashMap<String, List<TriageItemDTO>> triageValueMap = configurazioneDao.getTriageValueMap();
		response.setTriageValueMap(triageValueMap);
		
		return response;
    }
    
    
protected List<VmobiCasiDTO> findCasiByOperatoreBySoggetto(FindCasiByOperatoreBySoggettoRequestDTO dto) {
    	
    	
    	List<VMobiCasi> lst = vMobiDao.findVMobiCasiByOperatoreBySoggetto(dto) ;
    	
    	List<VmobiCasiDTO> lstCasiDTO = new ArrayList<VmobiCasiDTO>();
		for (VMobiCasi vMobiCasi : lst) {
			VmobiCasiDTO casoDtoTMP = new VmobiCasiDTO();
			casoDtoTMP.setByVmobiCaso(vMobiCasi);
			lstCasiDTO.add(casoDtoTMP);
		}
		
		
    	return lstCasiDTO;
	}
protected VmobiCasiDTO findCasiByOperatoreBySoggetto(FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO dto) {
	
	
	VMobiCasi caso = vMobiDao.findVMobiCasiById(dto) ;
	
	VmobiCasiDTO casoDTO = new VmobiCasiDTO();
	
	casoDTO.setByVmobiCaso(caso);
		
	
	
	
	return casoDTO;
}

	
	
	protected List<String> getListaTipoStatoErogazioneByPermessoErogazione(Hashtable<String,String> map) {
		List<String> lstStati = new LinkedList<String>();
		
		boolean autorizzativo = (map != null && map.get("permission@-@CarSociale@-@CarSociale@-@" + PermessiErogazioneInterventi.AUTORIZZA) != null);
		boolean erogativo =     (map != null && map.get("permission@-@CarSociale@-@CarSociale@-@" + PermessiErogazioneInterventi.EROGA) != null);
		
		if (autorizzativo) {
			lstStati.add(TipoStatoErogazione.PRELIMINARE);
			lstStati.add(TipoStatoErogazione.EROGATIVO);
		} else if(erogativo)
			lstStati.add(TipoStatoErogazione.EROGATIVO);

		return lstStati;
	}
	
	
	
	
	public Hashtable<BigDecimal, List<String>> getPermessiErogazioneGruppoSettore(CeTBaseObject o) {
		Hashtable<BigDecimal, List<String>> mappaTipiStato = new Hashtable<BigDecimal, List<String>>();
	
		Hashtable<Long, Hashtable> mappaSettorePermessi = new Hashtable<Long, Hashtable>();
		Hashtable<String, String> permessiGruppoSettore;
		
		try{
			//Recupero OperatoreSettore per utente
			List<CsOOperatoreSettore> operSetts = operatoreDao.findOperatoreSettoreByUsername(o.getUserId(), new Date());
				for(CsOOperatoreSettore opsettore : operSetts){
					if(o.getEnteId().equals(opsettore.getCsOSettore().getCsOOrganizzazione().getCodRouting())){ //filtro in base ai settori richiesti
						Long idSettore = opsettore.getCsOSettore().getId();
						String gruppi = opsettore.getAmGroup();
						if(gruppi != null) {
							String[] arrGruppi = gruppi.split("\\|");
							List<String> listaGruppi = new ArrayList<String>(Arrays.asList(arrGruppi));
							permessiGruppoSettore = mappaSettorePermessi.get(idSettore); 
							if(permessiGruppoSettore==null) permessiGruppoSettore= new Hashtable<String, String>();
					
							for(String gruppo: listaGruppi) {
								HashMap<String, String> permessi = loginService.getPermissionsByGroup(gruppo, o.getEnteId());
								permessiGruppoSettore.putAll(permessi);
							}
							
							mappaSettorePermessi.put(idSettore, permessiGruppoSettore);
					}
				}
			}
				
			
			
			for(Long idSettore : mappaSettorePermessi.keySet()){
				List<String> lstStati =  getListaTipoStatoErogazioneByPermessoErogazione(mappaSettorePermessi.get(idSettore));
				if(!lstStati.isEmpty())
					mappaTipiStato.put(new BigDecimal(idSettore), lstStati);
			}
		
		}catch(Exception e){logger.error("Configurazione non disponibile",e);}
		
		
		return mappaTipiStato;
	}

	@Override
	@AuditSaltaValidazioneSessionID
	@AuditConsentiAccessoAnonimo
	public void verificaLoadingMobileStaging(CeTBaseObject cet){
		elaboraMobileStaging();
	}
	
	@Override
	public void uploadDatiMobile(BaseDTO dto) throws Exception {
		CsMobileStaging ms = new CsMobileStaging();
		
		String json = (String)dto.getObj();
		
		if(json!=null){
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature. ACCEPT_SINGLE_VALUE_AS_ARRAY);
			UploadMobileDTO up = mapper.readValue(json, UploadMobileDTO.class);
			
			
			if(up.getDiari().length>0){
				for(UploadMobileDocumentiDTO m : up.getDiari()){
					salvaMobileJson(mapper.writeValueAsString(m), TipoDatoMobile.MOBILE_DIARIO, dto, up.getSessionId());
				}
			}
			
			if(up.getErogazioni().length>0){
				for(UploadMobileErogazioniDTO m : up.getErogazioni()){
					salvaMobileJson(mapper.writeValueAsString(m), TipoDatoMobile.MOBILE_EROGAZIONE, dto, up.getSessionId());
				}
			}
			
			if(up.getScanners().length>0){
				for(UploadMobileDocumentiDTO m : up.getScanners()){
					salvaMobileJson(mapper.writeValueAsString(m), TipoDatoMobile.MOBILE_SCANNER, dto, up.getSessionId());
				}
			}
			
			//TASK SISO-777
			//Aggiunta gestione dell'inserimenti (solo inserimento) di nuove attività professionali tramite APP
			if(up.getAttivitaProfessionali().length>0){
				for(UploadMobileAttivitaProfessionaliDTO m : up.getAttivitaProfessionali()){
					salvaMobileJson(mapper.writeValueAsString(m), TipoDatoMobile.MOBILE_ATTIVITA_PROFESSIONALE, dto, up.getSessionId());
				}
			}
			
			//********************
		}
	}
	
	private void salvaMobileJson(String json, String tipo, BaseDTO dto, String sessionId){
		CsMobileStaging ms = new CsMobileStaging();
		
		ms.setDtSincro(new Date());
		ms.setJson(json);
		ms.setUsrSincro(dto.getUserId());
		ms.setEnteSincro(dto.getEnteId());
		ms.setFlgElab(Boolean.FALSE);
		ms.setSessionId(sessionId);
		
		ms.setJson(json);
		ms.setTipo(tipo);
		this.vMobiDao.salvaMobileStaging(ms);
	}
	
	@Async
	public void elaboraMobileStaging(){
		
		List<CsMobileStaging> lstStaging = vMobiDao.findVariazioniStaging();
		logger.debug("elaboraMobileStaging - Trovate "+lstStaging.size()+" record da elaborare.");
		
		HashMap<Long,CsIInterventoEsegMast> mappaMaster = new HashMap<Long,CsIInterventoEsegMast>();
		HashMap<Long,List<CsIInterventoEseg>> mappaAlert = new HashMap<Long,List<CsIInterventoEseg>>();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature. ACCEPT_SINGLE_VALUE_AS_ARRAY);
		
		for(CsMobileStaging m : lstStaging){
			try{
				
				BaseDTO dto = new BaseDTO();
				dto.setEnteId(m.getEnteSincro());
				dto.setUserId(m.getUsrSincro());
				dto.setSessionId(m.getSessionId());
				
				CsOOperatore opCorrente = operatoreDao.findOperatoreByUsername(dto.getUserId());
				String msg = "";
				
				if(TipoDatoMobile.MOBILE_DIARIO.equalsIgnoreCase(m.getTipo())){
					UploadMobileDocumentiDTO json = mapper.readValue(m.getJson(), UploadMobileDocumentiDTO.class);
					msg = this.elaboraDiarioMobileStaging(json, dto, opCorrente);
				}
				
				if(TipoDatoMobile.MOBILE_EROGAZIONE.equalsIgnoreCase(m.getTipo())){
					UploadMobileErogazioniDTO json = mapper.readValue(m.getJson(), UploadMobileErogazioniDTO.class);
					msg = this.elaboraErogazioniMobileStaging(json, dto, opCorrente, mappaAlert, mappaMaster);					
				}	
				
				if(TipoDatoMobile.MOBILE_SCANNER.equalsIgnoreCase(m.getTipo())){
					UploadMobileDocumentiDTO json = mapper.readValue(m.getJson(), UploadMobileDocumentiDTO.class);
					msg+= this.elaboraDocIndividualeStaging(json, dto, opCorrente);
				}
				
				//TASK SISO-777
				//Aggiunta gestione dell'inserimenti (solo inserimento) di nuove attività professionali tramite APP
				if(TipoDatoMobile.MOBILE_ATTIVITA_PROFESSIONALE.equalsIgnoreCase(m.getTipo())){
					UploadMobileAttivitaProfessionaliDTO json = mapper.readValue(m.getJson(), UploadMobileAttivitaProfessionaliDTO.class);
					msg+= this.elaboraAttivitaProfessionaliMobileStaging(json, dto, opCorrente);
				
				}
				
				m.setFlgElab(msg.isEmpty());
				m.setMsgElab(!msg.isEmpty() ? msg : "Salvataggio completato correttamente");
			
			}catch(Exception e){
				m.setMsgElab(e.getMessage());
				m.setFlgElab(Boolean.FALSE);
				logger.error(e.getMessage(), e);
			}
			m.setDtElab(new Date());
			vMobiDao.salvaMobileStaging(m);
		}
			
	}
	
	private String elaboraDiarioMobileStaging(UploadMobileDocumentiDTO m, BaseDTO dto, CsOOperatore opCorrente){
		String messaggioErrore = "";
			
		dto.setObj(m.getErSoggCf());
		CsASoggettoLAZY soggetto = soggettoService.getSoggettoByCF(dto);
			
		if(soggetto!=null){
			try{	
				
				CsOOperatoreSettore opSettore = operatoreDao.findOperatoreSettoreById(opCorrente.getId(), m.getErSettoreErogId(),  m.getCreatedAt());
				
				if(TipoDocumentoMobile.DIARIO_TESTO==m.getTipo()){
					ColloquioDTO cdto = new ColloquioDTO();
					cdto.setDiarioTipoSelected(m.getTipo());
				
					cdto.setDiarioConChiSelected(m.getConChi());
					cdto.setDiarioDoveSelected(m.getDove());
					cdto.setDiarioTipoSelected(m.getTipoColloquio());
					
					CsDColloquio c = new CsDColloquio();
					c.setTestoDiario(m.getTitolo()+"<br/>"+m.getTesto());
					c.setRiservato("0"); //DEFAULT PER I DATI DA MOBILE   
					
					CsDDiario diario = new CsDDiario();
					diario.setDtAmministrativa(m.getCreatedAt());
					diario.setDtIns(new Date());
					diario.setUserIns(dto.getUserId());
					c.setCsDDiario(diario);
					
					cdto.setColloquio(c);
					
					dto.setObj(cdto);
					dto.setObj2(soggetto);
					dto.setObj3(opSettore);
					
					if(cdto.getDiarioConChiSelected()==null)      messaggioErrore+="\n Il campo del colloquio DIARIO_CONCHI_ID è obbligatorio.";
					//else if(cdto.getDiarioDoveSelected()==null)   messaggioErrore+="\n Il campo del colloquio DIARIO_DOVE_ID è obbligatorio.";
					else if(cdto.getDiarioTipoSelected()==null)	  messaggioErrore+="\n Il campo del colloquio TIPO_COLLOQUIO_ID è obbligatorio.";
					else
						diarioService.salvaColloquio(dto);
				}else{
					salvaDocumento(dto, soggetto, m.getTitolo(), m.getData(), m.getTipo());
				}
			}catch(Exception e){
				messaggioErrore+="\n Errore elaborazione diario "+soggetto.getCsAAnagrafica().getCf()+" "+e.getMessage();
			}
		}else
			messaggioErrore+="\n Errore elaborazione diario "+m.getErSoggCf()+" Impossibile inserire il diario, non esiste alcun caso associato al soggetto.";
		return messaggioErrore;
	}
	
	private String elaboraErogazioniMobileStaging(UploadMobileErogazioniDTO m, BaseDTO dto,CsOOperatore opCorrente, HashMap<Long,List<CsIInterventoEseg>> mappaAlert, HashMap<Long,CsIInterventoEsegMast> mappaMaster) throws Exception{
		String messaggioErrore = "";
			
		dto.setObj(m.getErSoggCf());
		
		VMobiIntErog d =  m.getProgrammazione();
	
		long key = d.getErId().longValue();
		CsIInterventoEsegMast master = mappaMaster.get(key);
		if(master==null)
			master = interventoErogDao.getErogazioneMasterById(d.getErId().longValue());
		
		List<CsIInterventoEseg> lstErogAlert = mappaAlert.get(key);
		if(lstErogAlert==null)
			lstErogAlert = new ArrayList<CsIInterventoEseg>();
	
		CsIInterventoEseg eseg = valorizzaInterventoEseg(m, opCorrente, master);
	//	eseg = master.addCsIInterventoEseg(eseg);
		valorizzaIntEsegValori(m, dto, eseg);
		
		/*Devo salvare da qui, altrimenti con la merge duplica i valori*/
		BaseDTO udto = new BaseDTO();
		udto.setEnteId(dto.getEnteId());
		udto.setUserId(dto.getUserId());
		udto.setSessionId(dto.getSessionId());
		udto.setObj(eseg);
		eseg = interventoService.aggiungiInterventoEseguito(udto);

		/*Verifica invio alert*/
		 if(eseg.getStato().getTipo().equalsIgnoreCase(DataModelCostanti.TipoStatoErogazione.EROGATIVO)){ 
			 	
		    	//Invio alert al (responsabile) di settore titolare, se quello gestore è diverso
		    	CsOOperatoreSettore opSett = eseg.getCsOOperatoreSettore();
		    	
		    	CsIInterventoPr csIInterventoPr = master.getCsIInterventoPr();
				CsOSettore settoreTitolare = csIInterventoPr.getSettoreTitolare();
				CsOSettore settoreGestore = csIInterventoPr.getSettoreGestore();
				/* --===-- 
		    	if(settore.getId().equals(settoreTitolare.getId()))
		    		erogAlertTitolare.add(nuovoIntEseg);*/
		    	
				/*Inserisco alert (al titolare) solo se il settore titolare è diverso dal gestore*/
		    	if(settoreGestore!=null && settoreGestore.getId().longValue()!=settoreTitolare.getId().longValue())
		    		lstErogAlert.add(eseg);
		  }
		
		mappaMaster.put(key,master);
		//mappaAlert.put(key,lstErogAlert);
		
	/*	Iterator<Long> it = mappaMaster.keySet().iterator();
		while(it.hasNext()){
			Long key = it.next();
			dto.setObj(mappaMaster.get(key));
			//interventoService.salvaInterventoEseguitoMast(dto);
			
			List<CsIInterventoEseg> lstErogAlert = mappaAlert.get(key);*/
			if(lstErogAlert!=null){
				BaseDTO adto = new BaseDTO();
				adto.setSessionId(dto.getSessionId());
				adto.setEnteId(dto.getEnteId());
				adto.setUserId(dto.getUserId());
				//adto.setObj(mappaMaster.get(key));
				adto.setObj(master);
				adto.setObj2(!lstErogAlert.isEmpty());
				adto.setObj3(lstErogAlert);
				interventoService.gestisciAlertErogazioni(adto);
		}
		
		return messaggioErrore;
	}
	
	private String elaboraDocIndividualeStaging(UploadMobileDocumentiDTO d, BaseDTO dto,CsOOperatore opCorrente) throws Exception{
		String messaggioErrore = "";
		
		dto.setObj(d.getErSoggCf());
		CsASoggettoLAZY soggetto = soggettoService.getSoggettoByCF(dto);
		
		if(soggetto!=null)
			salvaDocumento(dto,soggetto,d.getTitolo(), d.getData(), d.getTipo());
		else
			throw new Exception("Impossibile inserire il documento, nessun caso esistente per il soggetto con codice fiscale: "+d.getErSoggCf());
	
		return messaggioErrore;
	}
	
	
	
	//TASK SISO-777
	//Aggiunta gestione dell'inserimenti (solo inserimento) di nuove attività professionali tramite APP
	private String elaboraAttivitaProfessionaliMobileStaging(UploadMobileAttivitaProfessionaliDTO d, BaseDTO dto,CsOOperatore opCorrente) throws Exception{
		String messaggioErrore = "";
		//ricostruire RelazioneDTO a partire dai dati presenti su UploadMobileAttivitaProfessionaliDTO
		RelazioneDTO relDtoToSave = new RelazioneDTO();
		
		//relDtoToSave.setFlagRilevazioneProblematiche(d.getFlagRilevazioneProblematiche());
	
		CsTbMicroAttivita microAttivita=configurazioneDao.getMicroAttivitaById(d.getId_microAttivita());
		relDtoToSave.setMicroAttivita(microAttivita);
		
		//setMicroAttivita setta anche la macroattività
		//CsTbMacroAttivita macroAttivita=diarioService.findMacroAttivitaById(d.getId_macroAttivita());
		//relDtoToSave.getRelazione().setMacroAttivita(macroAttivita);
		
		relDtoToSave.setTriage(d.getTriage());
		
		dto.setObj(d.getErSoggCf());
		CsASoggettoLAZY soggetto = soggettoService.getSoggettoByCF(dto);
		
		if(soggetto!=null)
			salvaAttivitaProfessionale(dto,soggetto,relDtoToSave, d,opCorrente);
		else
			throw new Exception("Impossibile inserire il documento, nessun caso esistente per il soggetto con codice fiscale: "+d.getErSoggCf());
	
		return messaggioErrore;
	}
	
	private CsIInterventoEseg valorizzaInterventoEseg(UploadMobileErogazioniDTO row, CsOOperatore opCorrente, CsIInterventoEsegMast master) throws Exception {
		
		CsIInterventoEseg nuovoIntEseg = new CsIInterventoEseg();
		
		nuovoIntEseg.setUserIns(opCorrente.getUsername());
		nuovoIntEseg.setDtIns(new Date());
		
		//Assumo che il settore corrente dell'operatore sia quello EROGANTE del master
		BigDecimal idSettore = row.getProgrammazione().getErSettoreErogId();
		CsOOperatoreSettore opSettore = operatoreDao.findOperatoreSettoreById(opCorrente.getId(), idSettore.longValue(), row.getCreatedAt());
		nuovoIntEseg.setCsOOperatoreSettore(opSettore);					
	
		nuovoIntEseg.setEnteOperatoreErogante(row.getErOrganizzazioneErId().toString());
		
		nuovoIntEseg.setDataEsecuzione(row.getCreatedAt());
		nuovoIntEseg.setNote(row.getTesto());
		
		CsCfgIntEsegStato stato = configurazioneDao.findCsCfgIntEsegStato(row.getStato().getId());
		nuovoIntEseg.setStato(stato);
			
	/*	nuovoIntEseg.setSpesa(row.getSpesa());		
		nuovoIntEseg.setPercGestitaEnte(row.getPercGestitaEnte());
		nuovoIntEseg.setValoreGestitaEnte(row.getValoreGestitaEnte());
		nuovoIntEseg.setCompartUtenti(row.getCompartUtenti());
		nuovoIntEseg.setCompartAltre(row.getCompartAltre());
		nuovoIntEseg.setCompartSsn(row.getCompartSsn());
		nuovoIntEseg.setNoteAltreCompart(row.getNoteAltreCompart());				*/
					
		nuovoIntEseg.setNote(row.getTesto());	
		
		if(row.getUnimis()!=null && row.getValore()!=null){
			nuovoIntEseg.setCsIValQuota(new CsIValQuota());
			nuovoIntEseg.getCsIValQuota().setValQuota(row.getValore());
			nuovoIntEseg.getCsIValQuota().setUserIns(nuovoIntEseg.getUserIns());
			nuovoIntEseg.getCsIValQuota().setDtIns(nuovoIntEseg.getDtIns());
			
			//Calcolo la spesa, utilizzando la tariffa
			BigDecimal tariffa = master.getCsIQuota().getTariffa();
			nuovoIntEseg.setSpesa(tariffa.multiply(row.getValore()));
			
			if(master.getFlagSpesaCalc().booleanValue()){
				BigDecimal spesaMaster = master.getSpesa()!=null ? master.getSpesa().add(nuovoIntEseg.getSpesa()) : nuovoIntEseg.getSpesa();
				master.setSpesa(spesaMaster);
				if(master.getPercGestitaEnte()!=null)
					master.setValoreGestitaEnte(master.getSpesa().multiply(master.getPercGestitaEnte()));
			}
			if(master.getFlagCompartCalc()){
				//todo?! -- Non arriva nessun dato dal mobile attualmente
			}
			
			master.setDtMod(new Date());
			master.setUsrMod(row.getErSoggCf());
			
		}else 
			nuovoIntEseg.setCsIValQuota(null);
		
		nuovoIntEseg.setCsIInterventoEsegMast(master);
				
		return nuovoIntEseg;
	}

	
	private void valorizzaIntEsegValori(UploadMobileErogazioniDTO row, BaseDTO dto, CsIInterventoEseg nuovoIntEseg) {
		List<CsIInterventoEsegValore> listaIntEsegValore = new LinkedList<CsIInterventoEsegValore>();
		
		List<IntEsegAttrBean> attributi = row.getStato().getListaAttributi();
			
		for (UploadMobileValoreDTO cell : row.getValori()) {
			
			CsIInterventoEsegValore intEsegValore = new CsIInterventoEsegValore();
			
			dto.setObj(cell.getId_attr());
			dto.setObj2(cell.getUniMis()!=null && cell.getUniMis()>0  ? cell.getUniMis() : null);
			CsCfgAttrUnitaMisura attUm = interventoService.findAttrUnitaMisura(dto);
			
			intEsegValore.setCsAttributoUnitaMisura(attUm);
			intEsegValore.setCsIInterventoEseg(nuovoIntEseg);
			intEsegValore.setValore(cell.getValore()!=null ? cell.getValore().toString(): null);
			listaIntEsegValore.add(intEsegValore);
		}

		nuovoIntEseg.setDataEsecuzione(row.getCreatedAt());
		//nuovoIntEseg.setDataEsecuzioneA(row.getDataErogazioneA());
		nuovoIntEseg.setCsIInterventoEsegValores(listaIntEsegValore);
	}

	private void salvaDocumento(BaseDTO dto, CsASoggettoLAZY soggetto, String titolo, byte[] data, Long tipoDocumento) throws Exception{
		
	/*	CsDDocIndividuale docIndividuale = new CsDDocIndividuale();
		docIndividuale.setCsTbSottocartellaDoc(configurazioneDao.getTipoCartellaById(CsTbSottocartellaDoc.ID_DIARIO));
		docIndividuale.setDescrizione(titolo);
		docIndividuale.setLetto(false);
		docIndividuale.setPrivato(false);
		
		dto.setObj(docIndividuale);
		diarioService.saveDocIndividuale(dto);*/
		
		DocIndividualeDTO dataIn = new DocIndividualeDTO(); 
		dataIn.setEnteId(dto.getEnteId());
		dataIn.setUserId(dto.getUserId());
		dataIn.setSessionId(dto.getSessionId());
		
		dataIn.setNome(titolo);

        String contentType=null;
        switch (tipoDocumento.intValue()) {
            case TipoDocumentoMobile.DIARIO_FOTO:   contentType = "image/jpeg";
                     break;
            case TipoDocumentoMobile.DIARIO_AUDIO:  contentType = "audio/mpeg";
                     break;
        }
		dataIn.setTipo(contentType);  //TODO: verificare che il content type coincida con quello effettivo o funzioni con uno a caso.
		
		dataIn.setDocumento(data);
		dataIn.setCasoId(soggetto.getCsACaso().getId());
		dataIn.setSottoCartella(DataModelCostanti.CsTbSottocartellaDoc.ID_DIARIO);
		dataIn.setLetto(false);
		dataIn.setPrivato(false);
		diarioService.createAndsaveDocIndividuale(dataIn);
	}
	
	//TASK SISO-777
	private List<CsOOperatoreSettore> loadListaOpSettoreAttivi(List<CsOOperatoreSettore> lst){
		List<CsOOperatoreSettore> lstOut = new ArrayList<CsOOperatoreSettore>();
		
		for(CsOOperatoreSettore os: lst){
			Date oggi = new Date();
			if(!os.getDataInizioApp().after(oggi) && !os.getDataFineApp().before(oggi))
				lstOut.add(os);
		}
		
		return lstOut;
	}
	private void aggiungiSettore(List<CsOSettore> lst , CsOSettore s){
		boolean trovato = false;
		int i = 0;
		while(!trovato && i<lst.size()){
			if(lst.get(i).getId().longValue()==s.getId().longValue()) trovato = true;
			i++;
		}
		
		if(!trovato) lst.add(s);
	}
	private List<CsOSettore> ordinaListaSettori(List<CsOSettore> lst){
		List<CsOSettore> lstNoOrd = new ArrayList<CsOSettore>();
		List<CsOSettore> lstNull = new ArrayList<CsOSettore>();
		List<Integer> ord = new ArrayList<Integer>();
		
		for(CsOSettore s : lst){
			if(s.getnOrd()==null) lstNull.add(s);
			else{ 
				lstNoOrd.add(s);
				ord.add(s.getnOrd());
			}
		}
		
		if(!lstNoOrd.isEmpty()){
			CsOSettore[] lstOrd = new CsOSettore[lstNoOrd.size()];
			
			Collections.sort(ord);
			for(CsOSettore s : lstNoOrd){
				int index  = getFirstIndexLibero(ord, lstOrd, s.getnOrd());
				lstOrd[index] = s;
			}
				
			lstNull.addAll(0,Arrays.asList(lstOrd));
		}
		
		return lstNull;
	}
	private int getFirstIndexLibero(List<Integer> ord, CsOSettore[] dest, int nOrd){
		int fstIndex = ord.indexOf(nOrd);
		int lstIndex = ord.lastIndexOf(nOrd);
		
		boolean trovato = false;
		while(!trovato && fstIndex<=lstIndex){
			if(dest[fstIndex]==null) trovato = true;
			else fstIndex++;
		}
		return fstIndex;
	}
	//Aggiunta gestione dell'inserimenti (solo inserimento) di nuove attività professionali tramite APP
	private void salvaAttivitaProfessionale(BaseDTO dto, CsASoggettoLAZY soggetto, RelazioneDTO relazioneDTO,UploadMobileAttivitaProfessionaliDTO d,CsOOperatore operatoreCorrente)throws Exception{
	//Long idCaso, CsDTriage csDTriage, CsOOperatoreSettore csOOperatoreSettore, DiarioAnagraficaDTO[] diarioAnagraficaDTOs) 
		
		dto.setObj(relazioneDTO.getRelazione());
		// trovo il caso
		
		
		relazioneDTO.setEnteId(dto.getEnteId());
		relazioneDTO.setUserId(dto.getUserId());
		relazioneDTO.setSessionId(dto.getSessionId());
		
		
		
		
        CsACaso csa = new CsACaso();
        csa.setId(d.getIdCaso());
        
        CsTbTipoDiario cstd = new CsTbTipoDiario(); 
        cstd.setId(new Long(DataModelCostanti.TipoDiario.RELAZIONE_ID)); 
        
        CsOOperatoreBASIC operatoreResponsabile = null;

		BaseDTO baseDto = new BaseDTO();
		baseDto.setEnteId(dto.getEnteId());
		baseDto.setUserId(dto.getUserId());
		baseDto.setSessionId(dto.getSessionId());
		baseDto.setObj(d.getIdCaso());
		operatoreResponsabile = casoService.findResponsabileBASIC(baseDto);
		
		
		//TODO ricostruire liste oggetti/oggetti da liste id/id
		
		List<CsCDiarioConchi> lstConChiSel = new ArrayList<CsCDiarioConchi>(); 
		for (String strConChi : d.getLstConChiSel()) {
			CsCDiarioConchi cs=new CsCDiarioConchi();
			if(strConChi.equalsIgnoreCase("9999"))
			{
				cs.setDescrizione("Altri");
			}
			cs.setId(Long.parseLong(strConChi));
			lstConChiSel.add(cs);
		}
		relazioneDTO.getRelazione().setLstConChi(lstConChiSel);
		
		CsOSettore riunioneCon =new CsOSettore();
		riunioneCon.setId(d.getId_riunioneCon());
		relazioneDTO.getRelazione().setRiunioneCon(riunioneCon);
		
		
		//*****************************************
		
		
		// gestione  probblematiche
		//devo passare dalla classe piatta delle problematiche su un oggetto piatto con gli id degli oggetti referenziati e poi ricostruirli qui (basta istanzairli e metterci l'id)
		//attenzione ricostruisci l'embedded id di CsRelRelazioneProbl
		List<CsRelRelazioneProbl> relProblToAdd=new ArrayList<CsRelRelazioneProbl>();
		
		for (CsRelRelazioneProblRequestDTO relProbleCurr : d.getCsRelRelazioneProbl()) {
			CsRelRelazioneProbl problAdd = new CsRelRelazioneProbl();
			problAdd.getId().setProblId(relProbleCurr.getProblId());
			problAdd.getId().setRelazioneDiarioId(relProbleCurr.getRelazioneDiarioId());
			CsDRelazione csRel =new CsDRelazione();
			csRel.setDiarioId(relProbleCurr.getRelazioneDiarioId());
			problAdd.setCsDRelazione(csRel);
			CsDRelazione csDRelazioneRif =new CsDRelazione();
			csDRelazioneRif.setDiarioId(relProbleCurr.getRelazioneDiarioRifId());
			problAdd.setCsDRelazioneRif(csDRelazioneRif);
			problAdd.setFlagRisolta(relProbleCurr.getFlagRisolta());
			problAdd.setFlagVerificata(relProbleCurr.getFlagVerificata());
			CsTbProbl TbProbl = new CsTbProbl();
			TbProbl.setId(relProbleCurr.getProblId());
			problAdd.setCsTbProbl(TbProbl);
			
			relProblToAdd.add(problAdd);
			} 
		relazioneDTO.getRelazione().setCsRelRelazioneProbl(relProblToAdd);
		
//		List<CsRelRelazioneTipoint> interventi = new ArrayList<CsRelRelazioneTipoint>();
//		for (long tipoInterventoId : d.getId_csRelRelazioneTipoint()) {
//			CsRelRelazioneTipoint tipoIntToAdd=new CsRelRelazioneTipoint();
//						
//		}
		
		//*******************
		relazioneDTO.getRelazione().getCsDDiario().setDtAmministrativa(d.getDiarioDtAmministrativa());
        
        relazioneDTO.getRelazione().getCsDDiario().setCsACaso(csa);
        relazioneDTO.getRelazione().getCsDDiario().setResponsabileCaso(operatoreResponsabile.getId());
        relazioneDTO.getRelazione().getCsDDiario().setCsTbTipoDiario(cstd);
        relazioneDTO.getRelazione().getCsDDiario().setDtIns(new Date());
        relazioneDTO.getRelazione().getCsDDiario().setUserIns(dto.getUserId());
        
        //********* recupero CsOOperatoreSettore tramite operatore_id e settore_id
        List<SelectItem> listaSettori;
        CsOOperatoreSettore CsOOperatoreSettoreCurr=new CsOOperatoreSettore();
        
        List<CsOSettore> listaSettoriOrg;
        HashMap<Long, List<CsOSettore>> mappaOrgSettori = new HashMap<Long, List<CsOSettore>>();
		Map<Long, CsOOrganizzazione>    mappaOrg = new HashMap<Long, CsOOrganizzazione>();
		List<CsOOperatoreSettore> listaCsOOperatoreSettore = loadListaOpSettoreAttivi(new ArrayList<CsOOperatoreSettore>(operatoreCorrente.getCsOOperatoreSettores()));
		if(listaCsOOperatoreSettore.size() > 0) {
			for(CsOOperatoreSettore os: listaCsOOperatoreSettore){
			/* Verifico che l'operatore settore sia attivo */
				CsOSettore settore = os.getCsOSettore();
				if(mappaOrgSettori.containsKey(settore.getCsOOrganizzazione().getId()))
					listaSettoriOrg = mappaOrgSettori.get(settore.getCsOOrganizzazione().getId());
				else {
					listaSettoriOrg = new ArrayList<CsOSettore>();
					mappaOrg.put(settore.getCsOOrganizzazione().getId(), settore.getCsOOrganizzazione());
				}
				aggiungiSettore(listaSettoriOrg, settore);
				mappaOrgSettori.put(settore.getCsOOrganizzazione().getId(), listaSettoriOrg);
				
			}
			
			
			Long idSettore = (Long) d.getCsOSettore_id();
			for(CsOOperatoreSettore opsettore: listaCsOOperatoreSettore){
				if(idSettore.equals(opsettore.getCsOSettore().getId())){
					CsOOperatoreSettoreCurr= opsettore;
					break;
				}
			}
		}
        
        relazioneDTO.getRelazione().getCsDDiario().setCsOOperatoreSettore(CsOOperatoreSettoreCurr);
        relazioneDTO.getRelazione().getCsDDiario().setVisSecondoLivello(CsOOperatoreSettoreCurr.getCsOSettore().getId());
		//********
        dto.setObj(relazioneDTO.getRelazione());
        relazioneDTO = diarioService.saveRelazione(dto);
		
		
		List<DiarioAnagraficaDTO> famigliaSelezionata = new ArrayList<DiarioAnagraficaDTO>();
		for(DiarioAnagraficaDTO da : d.getFamigliaSelezionata()){
			if(da.getSelezionato()){
				da.setDiarioId(relazioneDTO.getRelazione().getDiarioId());
				famigliaSelezionata.add(da);
			}
		}
		BaseDTO dtoDiarioAnagrafica = new BaseDTO();
		dtoDiarioAnagrafica = new BaseDTO();
		dtoDiarioAnagrafica.setObj(famigliaSelezionata);
		dtoDiarioAnagrafica.setObj2(relazioneDTO.getRelazione().getDiarioId());
		dtoDiarioAnagrafica.setEnteId(dto.getEnteId());
		dtoDiarioAnagrafica.setUserId(dto.getUserId());
		dtoDiarioAnagrafica.setSessionId(dto.getSessionId());
		
		famigliaSelezionata = diarioService.saveDiarioAnagrafica(dtoDiarioAnagrafica);
		
		}
	

		
}
