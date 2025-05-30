package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableParentiGitSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dao.CasoDAO;
import it.webred.cs.csa.ejb.dao.CatSocialeDAO;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.IndirizzoDAO;
import it.webred.cs.csa.ejb.dao.SoggettoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.CasoSearchCriteria;
import it.webred.cs.csa.ejb.dto.ContatoreCasiDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.PaginationDTO;
import it.webred.cs.csa.ejb.dto.SearchRdCDTO;
import it.webred.cs.csa.ejb.dto.SettoreDTO;
import it.webred.cs.csa.ejb.dto.cartella.DatiAnagraficaCasoDTO;
import it.webred.cs.csa.ejb.dto.cartella.DatiAnagraficaCasoSaveDTO;
import it.webred.cs.csa.ejb.dto.cartella.ValiditaDTO;
import it.webred.cs.csa.ejb.dto.prospettoSintesi.CasiCatSocialeBean;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.csa.ejb.dto.retvalue.DatiCasoListaDTO;
import it.webred.cs.csa.ejb.forkJoinTask.InitDatiCaso;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipoAggiornamentoAnagrafica;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsAComponenteAnagCasoGit;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsASoggettoMedico;
import it.webred.cs.data.model.CsASoggettoMedicoPK;
import it.webred.cs.data.model.CsASoggettoStatoCivile;
import it.webred.cs.data.model.CsASoggettoStatoCivilePK;
import it.webred.cs.data.model.CsASoggettoStatus;
import it.webred.cs.data.model.CsASoggettoStatusPK;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCMedico;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.view.CsRdcAnagraficaGepi;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.ValidationStateless;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;
import it.webred.utils.TaskPoolExecutor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTableSoggettoSessionBean extends CarSocialeBaseSessionBean implements AccessTableSoggettoSessionBeanRemote {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SoggettoDAO soggettoDAO;
	
	@Autowired
	private ConfigurazioneDAO configurazioneDAO;
	
	@Autowired
	private CatSocialeDAO catSocialeDAO;
	
	@Autowired 
	private CasoDAO casoDAO;
	
	@Autowired
	private IndirizzoDAO indirizzoDAO;
	
	@EJB
	private AccessTableIndirizzoSessionBeanRemote indirizzoService;
	
	@EJB
	private AccessTableParentiGitSessionBeanRemote parentiService; 
	
	@EJB
	private AccessTableSchedaSegrSessionBeanRemote schedaSegrService;

	@EJB
	private AccessTableIterStepSessionBeanRemote iterService;
		
	@Override
	public CsASoggettoLAZY getSoggettoById(BaseDTO dto) {
		return soggettoDAO.getSoggettoById((Long) dto.getObj());
	}
    
	@Override
	public List<CsASoggettoLAZY> getSoggettiByCF(BaseDTO dto) {
		return soggettoDAO.getSoggettiByCF((String) dto.getObj());
	}
	
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public CsASoggettoLAZY getSoggettoByCF(BaseDTO dto) {
		return soggettoDAO.getSoggettoByCF((String) dto.getObj());
	}
	
	public Boolean esisteSchedaSoggettoByCF(BaseDTO dto) {
		Boolean x = null;
		
		CsASoggettoLAZY s = soggettoDAO.getSoggettoByCF((String) dto.getObj());
		if(s!=null) x = true;
		else x = false;

		return x;
	}
	
    @Override
	public SettoreDTO getLastItStepSettoreSoggetto(BaseDTO dto) throws Exception{
		
    	CsASoggettoLAZY s = soggettoDAO.getSoggettoByCF((String) dto.getObj());
    	if(s!=null){
			BaseDTO idto = new BaseDTO();
			copiaCsTBaseObject(dto, idto);
			idto.setObj(s.getCsACaso().getId());
			
			CsIterStepByCasoDTO it = iterService.getLastIterStepByCasoDTO(idto);
			SettoreDTO settoreSegnalante = it!=null ? it.getSettoreSegnalante() : null;
			return settoreSegnalante;
    	}
    	return null;
	}
    
	@Override
	public List<CsASoggettoLAZY> getSoggettiByDenominazione(BaseDTO dto) {
		return soggettoDAO.getSoggettiByDenominazione((String) dto.getObj());
	}
	
	private CsASoggettoLAZY saveSoggetto(CsASoggettoLAZY cs, String usernameOperatore) {
		cs.setDtIns(new Date());
		cs.setUserIns(usernameOperatore);
		
		CsACaso newCaso = casoDAO.inizializzaNuovoCaso(usernameOperatore);
		newCaso.setCsASoggetto(cs);
		cs.setCsACaso(newCaso);
		
		//CsAAnagrafica csAna = soggettoDAO.saveAnagrafica(cs.getCsAAnagrafica());
		//cs.setCsAAnagrafica(csAna);
		soggettoDAO.saveSoggetto(cs);
		return cs;
	}
	
	private CsASoggettoLAZY updateSoggetto(CsASoggettoLAZY cs, String usernameOperatore) {
		cs.setDtMod(new Date());
		cs.setUsrMod(usernameOperatore);
		
		soggettoDAO.saveAnagrafica(cs.getCsAAnagrafica());
		return soggettoDAO.updateSoggetto(cs);
	}

	@Override
	public List<CsASoggettoCategoriaSoc> getSoggettoCategorieBySoggetto(BaseDTO dto) {
		return soggettoDAO.getSoggCategorieBySoggetto((Long) dto.getObj());
	}
	
	@Override
	public List<CsASoggettoCategoriaSoc> getSoggettoCategorieAttualiBySoggetto(BaseDTO dto) {
		return soggettoDAO.getSoggCategorieAttualiBySoggetto((Long) dto.getObj());
	}
	
	@Override
	public void saveSoggettoCategoria(BaseDTO dto) {
		soggettoDAO.saveSoggettoCategoria((CsASoggettoCategoriaSoc) dto.getObj());
	}
	
	@Override
	public boolean eliminaSoggettoCategorieBySoggetto(BaseDTO dto) {
		return soggettoDAO.eliminaSoggettoCategorieBySoggetto((Long) dto.getObj());
	}
	
	@Override
	public List<CsCCategoriaSociale> getCatSocAttualiBySoggetto(BaseDTO dto) {
		List<CsASoggettoCategoriaSoc> lst = soggettoDAO.getSoggCategorieAttualiBySoggetto((Long) dto.getObj());
		List<CsCCategoriaSociale> attuale = new ArrayList<CsCCategoriaSociale>();
		for(CsASoggettoCategoriaSoc cs : lst)
			attuale.add(cs.getCsCCategoriaSociale());
		
		return attuale;
	}
	
	@Override 
	public List<CsCCategoriaSociale> getCatSocAttualiByCF(BaseDTO dto){
		List<CsCCategoriaSociale> attuale = new ArrayList<CsCCategoriaSociale>();
		String cf = (String) dto.getObj();
		if (!StringUtils.isBlank(cf)) {
			CsASoggettoLAZY sogg = soggettoDAO.getSoggettoByCF(cf);
			if (sogg != null) {
				dto.setObj(sogg.getAnagraficaId());
				attuale = this.getCatSocAttualiBySoggetto(dto);
			}
		}
		return attuale;
	}
	
	public Boolean existsCatSocAttualiBySoggetto(BaseDTO dto) {
		List<CsASoggettoCategoriaSoc> lst = soggettoDAO.getSoggCategorieAttualiBySoggetto((Long) dto.getObj());
		return !lst.isEmpty();
	}
		
	@Override
	public List<DatiCasoListaDTO> getListaCasiSoggetto(PaginationDTO pdto) throws Throwable {
		CasoSearchCriteria criteria = (CasoSearchCriteria) pdto.getObj();
		criteria.setUsername(pdto.getUserId());
		List<DatiCasoListaDTO> lst = soggettoDAO.getCasiSoggetto(pdto.getFirst(), pdto.getPageSize(), criteria);
		
		CeTBaseObject cet = new CeTBaseObject();
		copiaCsTBaseObject(pdto, cet);
/*		for(DatiCasoListaDTO dc : lst)
			popolaDatiAggiuntiviListaCasi(dc, cet);
	*/	
		List<DatiCasoListaDTO> data = loadDatiCaso(lst, cet);
		
		return lst;
	}
	
	private List<DatiCasoListaDTO> loadDatiCaso(List<DatiCasoListaDTO> in, CeTBaseObject cet) throws Exception{
		List<DatiCasoListaDTO> out = new ArrayList<DatiCasoListaDTO>();
		if(!in.isEmpty()){
			logger.debug("INIZIO caricamento dati per inizializzazione listaCasi con ForkJoinPool");
			
			TaskPoolExecutor pool = new TaskPoolExecutor();
			List<InitDatiCaso> classiInit = new ArrayList<InitDatiCaso>();

			for (DatiCasoListaDTO dc : in) {
				InitDatiCaso init = null;
				init = new InitDatiCaso(dc, cet);
				classiInit.add(init);
				pool.addTask(init);
			}
			
			boolean abnormal = pool.execute();
			logger.debug("FINE caricamento dati per inizializzazione listaCasi con ForkJoinPool");
			
			if(abnormal){
				for(InitDatiCaso ic : classiInit){
					if(ic.isCompletedAbnormally()){
						Throwable e = ic.getException();
						logger.error("Errore esecuzione InitDatiCaso Identificativo["+ ic.getBean().getIdentificativo()+"], CF["+ic.getBean().getCf()+"]"+e.getMessage(), e);
					}
				}
				throw new CarSocialeServiceException("Errore nel recupero della LISTA CASI");
			}
			
			
			for (InitDatiCaso initDatiCaso : classiInit) {
				DatiCasoListaDTO bean = (DatiCasoListaDTO) initDatiCaso.getRawResult();
				if (bean != null) out.add(bean);
			}
		}
		return out;
	}

	
	
	@Override
	public Integer getListaCasiSoggettoCount(PaginationDTO dto) {
		CasoSearchCriteria criteria = (CasoSearchCriteria) dto.getObj();
		criteria.setUsername(dto.getUserId());
		return soggettoDAO.getCasiSoggettoCount((CasoSearchCriteria) dto.getObj());
	}
	
	//SISO-1127 Inizio
	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public List<CsASoggettoMedico> getSoggettoMedicoBySoggetto(BaseDTO dto) {
		return soggettoDAO.getSoggettoMedicoBySoggetto((Long) dto.getObj());
	}
	
	@Override
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    public List<CsAComponenteAnagCasoGit> getAnagraficaComponenteCasoProvenienza(BaseDTO bDto){
  		
    	return soggettoDAO.getAnagraficaSoggettoGitByProvenienzaId((String) bDto.getObj());
       
  	}
	@Override
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public List<CsAComponenteAnagCasoGit> getAnagraficaComponenteCasoNoIdProvenienza(BaseDTO bDto){
		return soggettoDAO.getAnagraficaSoggettoGitByEscudiProvenienzaId();
	}
	@Override
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    public CsAComponenteAnagCasoGit getAnagraficaComponenteCasoSoggettoId(BaseDTO bDto){
  		
    	return soggettoDAO.getAnagraficaSoggettoGitBySoggettoId((Long) bDto.getObj());
       
  	}
	
	private CsAComponenteAnagCasoGit initAnagraficaSoggetto(PersonaDettaglio c, CsAComponenteAnagCasoGit anagraficaGit, String userId){
		CsAComponenteAnagCasoGit componenteAnagCasoGit = new CsAComponenteAnagCasoGit();
		
		componenteAnagCasoGit.setId(anagraficaGit.getId());
		componenteAnagCasoGit.setCsASoggetto(anagraficaGit.getCsASoggetto());
		//componenteAnagCasoGit.setUserIns(userId);
		componenteAnagCasoGit.setDtIns(new Date());
		
		componenteAnagCasoGit.setCognome(c.getCognome());
		componenteAnagCasoGit.setNome(c.getNome());
		componenteAnagCasoGit.setCf(c.getCodfisc());
		//componenteGit.setDataNascita(c.getDataNascita());
		componenteAnagCasoGit.setDataDecesso(c.getDataMorte());
		
		componenteAnagCasoGit.setSesso(c.getSesso());
		// Cittadinanza
		if(c.getCittadinanza() != null) {
			componenteAnagCasoGit.setCittadinanza( c.getCittadinanza());	
		}
		if(c.getTelefono() != null) {
			componenteAnagCasoGit.setTelefono( c.getTelefono());	
		}
        if(c.getMedicoDataScelta() != null) {
        	componenteAnagCasoGit.setDataSceltaMedico(c.getMedicoDataScelta());
        }
        if(c.getMedicoDataRevoca() != null) {
        	componenteAnagCasoGit.setDataRevocaMedico(c.getMedicoDataRevoca());
        }
        
        if(c.getMedicoCodiceFiscale()  != null) {
        	componenteAnagCasoGit.setCodiceFiscaleMedico(c.getMedicoCodiceFiscale());
        }
 
        if(c.getStatoCivile() != null) {
        	componenteAnagCasoGit.setStatoCivile(c.getStatoCivile());
        }
        componenteAnagCasoGit.setIndirizzoDom(c.getIndirizzoDomicilio());
        componenteAnagCasoGit.setNumCivDom(c.getCivicoDomicilio());
        
		componenteAnagCasoGit.setIndirizzoRes(c.getIndirizzoResidenza());
		componenteAnagCasoGit.setNumCivRes(c.getCivicoResidenza());
		
		componenteAnagCasoGit.setCognomeMedico(c.getMedicoCognome());
		componenteAnagCasoGit.setNomeMedico(c.getMedicoNome());
		
		if(c.getMedicoCodRegionale()!=null) {
			componenteAnagCasoGit.setCodiceRegionaleMedico(c.getMedicoCodRegionale());
		}
		
		if(c.getComuneResidenza()!=null){
			componenteAnagCasoGit.setComResCod(c.getComuneResidenza().getCodIstatComune());
			componenteAnagCasoGit.setComResDes(c.getComuneResidenza().getDenominazione());
			componenteAnagCasoGit.setProvResCod(c.getComuneResidenza().getSiglaProv());
		}
		if(c.getComuneDomicilio()!=null){
			componenteAnagCasoGit.setComDomCod(c.getComuneDomicilio().getCodIstatComune());
			componenteAnagCasoGit.setComDomDes(c.getComuneDomicilio().getDenominazione());
			componenteAnagCasoGit.setProvDomCod(c.getComuneDomicilio().getSiglaProv());
		}
		return componenteAnagCasoGit;
    }
	




	
	@Override 
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public void elaboraVariazioniSoggettoCasoGit(BaseDTO dto) {
		
		CsAComponenteAnagCasoGit anagraficaCaso = (CsAComponenteAnagCasoGit)dto.getObj();
		List<PersonaDettaglio> lista = (List<PersonaDettaglio>)dto.getObj2();
		String tipoRicerca = (String)dto.getObj3();
		String cfIntestatario = anagraficaCaso.getCsASoggetto().getCsAAnagrafica().getCf();
		String identificativo = "";
		
		CsAComponenteAnagCasoGit c = null;
		for(PersonaDettaglio f : lista){
			//soggetto individuato nell'elenco dei soggetti presenti su DB
			if(cfIntestatario.equalsIgnoreCase(f.getCodfisc())) {
				c = this.initAnagraficaSoggetto(f, anagraficaCaso, dto.getUserId());
				identificativo = f.getIdentificativo();
				break;	
			}
			
			
		}
		
		CsAAnagrafica anagraficaOld = anagraficaCaso.getCsASoggetto().getCsAAnagrafica();
		List<BigDecimal> toRemove = new ArrayList<BigDecimal>();
		CsAAnaIndirizzo indirizzoRes = null;
		CsAAnaIndirizzo indirizzoDom = null;
		CsASoggettoMedico sm = null;
		CsASoggettoLAZY csASoggetto = null;
		CsASoggettoStatoCivile staCiv = null; 
		
		Long idSoggetto = (anagraficaCaso != null && anagraficaCaso.getCsASoggetto() != null ? anagraficaCaso.getCsASoggetto().getAnagraficaId() :  null);
	 	if(idSoggetto != null){
		    indirizzoRes  =   indirizzoDAO.getResidenzaBySoggetto(idSoggetto);
		    indirizzoDom  =   indirizzoDAO.getDomicilioBySoggetto(idSoggetto);
		    List<CsASoggettoMedico> listSoggettiMedico = soggettoDAO.getSoggettoMedicoBySoggetto(idSoggetto);
		    for(CsASoggettoMedico m : listSoggettiMedico){
		    	if((new Date()).before(m.getId().getDataFineApp())){
		    		sm = m;
		    		break;
		    	}
		    }
		    
		    staCiv = soggettoDAO.getSoggettoStatoCivileAllaDataBySoggetto(idSoggetto, new Date());
			
		    csASoggetto = soggettoDAO.getSoggettoById(idSoggetto);
	 	}
		
		boolean segnalazione = false;
		LinkedHashMap<String, String> mappaVariazioniAnag = new LinkedHashMap<String, String>();
		
		if(c!=null){ //IN CASO CONTRARIO DEVE ESSERE ELIMINATO
			if(!compare(c.getCognome(), csASoggetto.getCsAAnagrafica().getCognome())){
				segnalazione=true;
				anagraficaCaso.setCognome(c.getCognome());
				mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.ANAGRAFICA_COGNOME , c.getCognome());
			}
			if(!compare(c.getNome(), csASoggetto.getCsAAnagrafica().getNome())){
				segnalazione=true;
				anagraficaCaso.setNome(c.getNome());
				mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.ANAGRAFICA_NOME, c.getNome());
			}
			if(!compare(c.getCf(), csASoggetto.getCsAAnagrafica().getCf())){
				segnalazione=true;
				anagraficaCaso.setCf(c.getCf());
				mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.ANAGRAFICA_CODICE_FISCALE , c.getCf());
			}
			if(!StringUtils.isBlank(c.getTelefono())) {
				if(c.getTelefono().startsWith("0")) {
					if(!compareB(c.getTelefono(), csASoggetto.getCsAAnagrafica().getTel())){
						segnalazione=true;
						anagraficaCaso.setTelefono(c.getTelefono());
						mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.ANAGRAFICA_TELEFONO , c.getTelefono());
					}
			}else if(!compareB(c.getTelefono(), csASoggetto.getCsAAnagrafica().getCell())){
					segnalazione=true;
					anagraficaCaso.setTelefono(c.getTelefono());
					mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.ANAGRAFICA_CELLULARE , c.getTelefono());
				}	
				
		}
			
			CsTbStatoCivile statoCivileNew = configurazioneDAO.getStatoCivileByIdExtCet(dto.getEnteId(),c.getStatoCivile());
			if(statoCivileNew!=null) {
				if((staCiv == null && c.getStatoCivile() != null) ||
						(staCiv != null && !compare(staCiv.getCsTbStatoCivile().getDescrizione(), statoCivileNew.getDescrizione()))){
					segnalazione=true;
					anagraficaCaso.setStatoCivile(c.getStatoCivile());
					mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.ANAGRAFICA_STATO_CIVILE , statoCivileNew.getDescrizione());
				}
			}
			if(!compare(c.getDataDecesso(), csASoggetto.getCsAAnagrafica().getDataDecesso())){
				segnalazione=true;
				anagraficaCaso.setDataDecesso(c.getDataDecesso());
				
				mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.ANAGRAFICA_DATA_DECESSO, dateToStringVal(c.getDataDecesso()) );
			}
				//se indirizzo Res è nullo

			if(c.getCittadinanza() != null && !compare(c.getCittadinanza(), csASoggetto.getCsAAnagrafica().getCittadinanza())){
				segnalazione=true;
				anagraficaCaso.setCittadinanza(c.getCittadinanza());
				mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.ANAGRAFICA_CITTADINANZA, c.getCittadinanza() );
			}
			if(c.getSesso() != null && !compare(c.getSesso(), csASoggetto.getCsAAnagrafica().getSesso())){
				segnalazione=true;
				anagraficaCaso.setSesso(c.getSesso());
				mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.ANAGRAFICA_SESSO, c.getSesso() );
			}
			
			//Inizio modifiche 29-09-2020
			/****
			 * Importante 
			 * escludere il numero civico se uguale = '' e se l'indirizzo domicilio coincide con l'indirizzo residenza
			 * 
			 * */
			if(c.getIndirizzoRes() != null) {
				segnalazione = (compareIndirizzoToIndirizzoDb(c, indirizzoRes,mappaVariazioniAnag, 2) == false ? segnalazione : true);;
			}
			//Confronto il precedente indirizzo su DB con il domicilio, se DIVERSI  procedo...
			// Se indirizzo DOM non presente nel database ed uguale all'indirizzo residenza NON procedo
			if(c.getIndirizzoDom() != null && indirizzoDom != null && indirizzoDom.getId() != 0L) {
					segnalazione = ( this.compareIndirizzoToIndirizzoDb(c, indirizzoDom,mappaVariazioniAnag, 1) == false ?  segnalazione : true);
					
			}else if(c.getIndirizzoDom() != null) {
				if(this.compareIndirizzoToIndirizzoDb(c, indirizzoRes,null, 1)) {
				   segnalazione = (this.compareIndirizzoToIndirizzoDb(c, indirizzoDom,mappaVariazioniAnag, 1) == false ? segnalazione : true);
				}
 			
			}		
//				if(this.compareIndirizzoDomToIndirizzoDb(c, indirizzoRes,null, 1)) {
//					//verifico che sia diverso anche dall'attuale indirizzo in INPUT
//					if(this.compareIndirizzoDomToIndirizzoDb(c, null,null, 3)) {
//						//procedo con l'aggiornamento DOMICILIO
//						segnalazione = this.compareIndirizzoDomToIndirizzoDb(c, indirizzoDom,mappaVariazioniAnag, 1);
//					}
//				}
				
				 
			
			popolaAnagraficaCasoGit(anagraficaCaso,mappaVariazioniAnag);
			//fine modifiche 29-09-2020
			
			if(sm != null){
						
					/**
					 * Se il codice regionale o il codice fiscale (per le Marche) coincide, mi trovo ad avere una data di 
					 * fine validità del medico già presente in siso e lo inserisco come medico PRECEDENTE
					 * 
					 * */
			/*	boolean segnalazioneMedicoOld = false;
				 
				
				if(!StringUtils.isEmpty(c.getCodiceRegionaleMedico()) && compare(c.getCodiceRegionaleMedico() , sm.getCsCMedico().getCodiceRegionale())){
					segnalazioneMedicoOld=true;
					anagraficaCaso.setCodiceRegionaleMedico(c.getCodiceRegionaleMedico());
			 	}
				else if((!StringUtils.isEmpty(c.getCodiceFiscaleMedico()) && compare(c.getCodiceFiscaleMedico() , sm.getCsCMedico().getCodiceRegionale()))){
					segnalazioneMedicoOld=true;
					anagraficaCaso.setCodiceFiscaleMedico(c.getCodiceFiscaleMedico());
			 	}
				if(segnalazioneMedicoOld) {
					if(c.getDataRevocaMedico() != null &&  sm.getId().getDataFineApp() == null) {
						segnalazione = true;
						mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.COGNOME_MEDICO_REVOCATO, sm.getCsCMedico().getCognome());
						mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.NOME_MEDICO_REVOCATO, sm.getCsCMedico().getNome());
						mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.DATA_REVOCA_MEDICO_REVOCATO, c.getDataRevocaMedico());
						mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.COD_REGIONALE_MEDICO_REVOCATO, sm.getCsCMedico().getCodiceRegionale());
						if(c.getDataSceltaMedico() != null)
							mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.DATA_SCELTA_MEDICO_REVOCATO, c.getDataSceltaMedico());
						
					}
					
				} */
				
				
					CsCMedico medico = sm.getCsCMedico();	
					
					boolean medicoAggiornato = false;
					if(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA.equals(tipoRicerca) && 
							!StringUtils.isBlank(c.getCognomeMedico()) &&
							!StringUtils.isBlank(c.getCodiceRegionaleMedico()) && !c.getCodiceRegionaleMedico().equalsIgnoreCase(medico.getCodiceRegionale())){
						medicoAggiornato=true;
						anagraficaCaso.setCodiceRegionaleMedico(c.getCodiceRegionaleMedico());
						mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.COD_REGIONALE_NUOVO_MEDICO, c.getCodiceRegionaleMedico());
						
						
				 	}else if(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE.equals(tipoRicerca) && 
				 			(!StringUtils.isBlank(c.getCodiceFiscaleMedico()) && !c.getCodiceFiscaleMedico().equalsIgnoreCase(medico.getCodiceRegionale()))){
				 		medicoAggiornato=true;
						anagraficaCaso.setCodiceFiscaleMedico(c.getCodiceFiscaleMedico());
						mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.CODICE_FISCALE_NUOVO_MEDICO, c.getCodiceFiscaleMedico());
				 	}
				 	
					if(medicoAggiornato && !StringUtils.isBlank(c.getCognomeMedico()) && !StringUtils.isBlank(c.getNomeMedico())){ 
						//Serve per evitare la segnalazione incompleta, per ws non funzionante: se non ho cognome e nome non posso aggiornare.
						segnalazione = true;
						
						anagraficaCaso.setCognomeMedico(c.getCognomeMedico());
						mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.COGNOME_NUOVO_MEDICO, c.getCognomeMedico());
						
						anagraficaCaso.setNomeMedico(c.getNomeMedico());
						mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.NOME_NUOVO_MEDICO, c.getNomeMedico());
						
						if(c.getDataSceltaMedico()!=null){
							anagraficaCaso.setDataSceltaMedico(c.getDataSceltaMedico());
							String dataScelta = ddMMyyyy.format(c.getDataSceltaMedico());
							mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.DATA_SCELTA_NUOVO_MEDICO, dataScelta);
						}
						
						if(c.getDataRevocaMedico()!=null){
							anagraficaCaso.setDataRevocaMedico(c.getDataRevocaMedico());
							String dataRevoca = ddMMyyyy.format(c.getDataRevocaMedico());
							mappaVariazioniAnag.put(TipoAggiornamentoAnagrafica.DATA_REVOCA_NUOVO_MEDICO, dataRevoca );
						}
						
					}		
				}	
			
				anagraficaCaso.setDtMod(new Date());
				anagraficaCaso.setFlgSegnalazione(segnalazione);
				if(!StringUtils.isBlank(identificativo))
					anagraficaCaso.setIdOrigWs(tipoRicerca+"@"+identificativo);
				
				if(segnalazione) {
					anagraficaCaso.setSegnalazione(serializzaJsonHashMap(mappaVariazioniAnag));
				}
				else
					anagraficaCaso.setSegnalazione("-");
					
		
				soggettoDAO.updateAnagraficaCasoGIT(anagraficaCaso);
		} 
		 
		
		
			
	}
	
  
	
	private String serializzaHashMap(HashMap<String, String> hashmap ) {
		String result ="";
		
		if(hashmap != null && hashmap.size() > 0) {
			
			result = "Sono state rilevate le seguenti variazioni : \n";
			for (Entry<String, String> entry : hashmap.entrySet()) {
			  
				result.concat( entry.getKey() + ": " + entry.getValue());
				
			}
		}
			
		return result;
	}
	
	private String serializzaJsonHashMap(HashMap<String, String> hashmap ) {
		String result ="";
		
		if(hashmap != null && hashmap.size() > 0) {
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hashmap);
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage(), e);
			}
		}
			
		return result;
	}
	
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    public List<CsAComponenteAnagCasoGit> getAnagraficheCasiGitAggiornate(BaseDTO dto) {
    	return soggettoDAO.getAnagraficheCasiGitAggiornate();
    }
	
	public CsAComponenteAnagCasoGit getAnagraficaCasoGitAggiornataBySoggettoId(BaseDTO dto) {
		Long idSoggetto = (Long)dto.getObj();
		try {
			Date dtRif =  dto.getObj2() == null ? DataModelCostanti.START_DATE : (java.util.Date)dto.getObj2();
	    	return soggettoDAO.getAnagraficaCasoGitAggiornatoBySoggettoId(idSoggetto, dtRif);
		}
		catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return null;
    }
	private CsAComponenteAnagCasoGit getAnagraficaSoggettoGitBySoggettoId(Long idAnagraficaSoggetto){
		return soggettoDAO.getAnagraficaSoggettoGitBySoggettoId(idAnagraficaSoggetto);
	}
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public void updateAnagraficaComponenteCaso(BaseDTO bDto){
	  		
	    	 soggettoDAO.updateAnagraficaCasoGIT((CsAComponenteAnagCasoGit) bDto.getObj());
	       
	}

	private String dateToStringVal(Date dataVal) {
		
		SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	
		String result = "";
		
		if (dataVal == null)
			return result = "";
		else{
			String descData = SDF.format(dataVal);
			return descData;
		}
	}
	
	 private void popolaAnagraficaCasoGit(CsAComponenteAnagCasoGit anagraficaCaso, LinkedHashMap<String, String> mappaVariazioniAnag) {
		   
		  if(mappaVariazioniAnag.containsKey(TipoAggiornamentoAnagrafica.INDIRIZZO_DOMICILIO)) {
			  anagraficaCaso.setIndirizzoDom(mappaVariazioniAnag.get(TipoAggiornamentoAnagrafica.INDIRIZZO_DOMICILIO));
		  }
		  if(mappaVariazioniAnag.containsKey(TipoAggiornamentoAnagrafica.CODICE_COMUNE_DOMICILIO)) {
			  anagraficaCaso.setComDomCod(mappaVariazioniAnag.get(TipoAggiornamentoAnagrafica.CODICE_COMUNE_DOMICILIO));
		  }
		  if(mappaVariazioniAnag.containsKey(TipoAggiornamentoAnagrafica.COMUNE_DOMICILIO)) {
			  anagraficaCaso.setComDomDes(mappaVariazioniAnag.get(TipoAggiornamentoAnagrafica.COMUNE_DOMICILIO));
		  }
		  if(mappaVariazioniAnag.containsKey(TipoAggiornamentoAnagrafica.NUM_CIVICO_DOMICILIO)) {
			  anagraficaCaso.setNumCivDom(mappaVariazioniAnag.get(TipoAggiornamentoAnagrafica.NUM_CIVICO_DOMICILIO));
		  }
		  if(mappaVariazioniAnag.containsKey(TipoAggiornamentoAnagrafica.PROVINCIA_DI_DOMICILIO)) {
			  anagraficaCaso.setProvDomCod(mappaVariazioniAnag.get(TipoAggiornamentoAnagrafica.PROVINCIA_DI_DOMICILIO));
		  }
		  
		  if(mappaVariazioniAnag.containsKey(TipoAggiornamentoAnagrafica.INDIRIZZO_RESIDENZA)) {
			  anagraficaCaso.setIndirizzoRes(mappaVariazioniAnag.get(TipoAggiornamentoAnagrafica.INDIRIZZO_RESIDENZA));
		  }
		  if(mappaVariazioniAnag.containsKey(TipoAggiornamentoAnagrafica.COD_COM_RESIDENZA)) {
			  anagraficaCaso.setComResCod(mappaVariazioniAnag.get(TipoAggiornamentoAnagrafica.COD_COM_RESIDENZA));
		  }
		  if(mappaVariazioniAnag.containsKey(TipoAggiornamentoAnagrafica.COMUNE_DI_RESIDENZA)) {
			  anagraficaCaso.setComResDes(mappaVariazioniAnag.get(TipoAggiornamentoAnagrafica.COMUNE_DI_RESIDENZA));  
		  }
		  if(mappaVariazioniAnag.containsKey(TipoAggiornamentoAnagrafica.NUM_CIVICO_RESIDENZA)) {
			  anagraficaCaso.setNumCivRes(mappaVariazioniAnag.get(TipoAggiornamentoAnagrafica.NUM_CIVICO_RESIDENZA));
		  }
		  if(mappaVariazioniAnag.containsKey(TipoAggiornamentoAnagrafica.PROVINCIA_DI_RESIDENZA)) {
			  anagraficaCaso.setProvResCod(mappaVariazioniAnag.get(TipoAggiornamentoAnagrafica.PROVINCIA_DI_RESIDENZA));
		  }
	   }
	
	/***
	 * 
	 * 
	 * @param componenteInput
	 * @param indirizzo
	 * @param tipoIndirizzoDB 1 = confronta indirizzo input con domicilio 2= confronta indirizzo input con indirizzo Residenza
	 * @return
	 */

	 
	private  boolean compareIndirizzoToIndirizzoDb(CsAComponenteAnagCasoGit componenteInput, 
			CsAAnaIndirizzo indirizzo,LinkedHashMap<String, String> mapVariazioni, int tipoIndirizzoDB ) {
		boolean diversi = false;
		 
		if(indirizzo == null || indirizzo.getId() == 0L)
			return diversi = true;
		 
			switch(tipoIndirizzoDB) {
				case 1 : // DOMICILIO 
					if(!StringUtils.isBlank(componenteInput.getIndirizzoDom())) {
						if(!compareB(componenteInput.getIndirizzoDom(), indirizzo.getIndirizzo()) ) {
							diversi = true;
							if(mapVariazioni != null)
								mapVariazioni.put(TipoAggiornamentoAnagrafica.INDIRIZZO_DOMICILIO, componenteInput.getIndirizzoDom());
						}
					}
					if(!StringUtils.isBlank(componenteInput.getComDomDes())) {
						if(!compareB(componenteInput.getComDomDes(), indirizzo.getComDes()) ) {
							diversi = true;
							if(mapVariazioni != null)
								mapVariazioni.put(TipoAggiornamentoAnagrafica.COMUNE_DOMICILIO, componenteInput.getComDomDes());
						}
					}
					if(!StringUtils.isBlank(componenteInput.getNumCivDom())) {
						if(!compareB(componenteInput.getNumCivDom(), indirizzo.getCivicoNumero()) ) {
							diversi = true;
							if(mapVariazioni != null)
								mapVariazioni.put(TipoAggiornamentoAnagrafica.NUM_CIVICO_DOMICILIO, componenteInput.getNumCivDom());
						}
					}
					if(!StringUtils.isBlank(componenteInput.getProvDomCod())){
						
						if(!compareB(componenteInput.getProvDomCod(), indirizzo.getProv() ) ) {
							diversi = true;
							if(mapVariazioni != null)
								mapVariazioni.put(TipoAggiornamentoAnagrafica.PROVINCIA_DI_DOMICILIO, componenteInput.getProvDomCod());
						}
					}
					if(!StringUtils.isBlank(componenteInput.getComDomCod()) && !compareB(componenteInput.getComDomCod() , indirizzo.getComCod())){
						diversi = true;
						if(mapVariazioni != null)
							mapVariazioni.put(TipoAggiornamentoAnagrafica.CODICE_COMUNE_DOMICILIO,componenteInput.getComDomCod());
					}
					break;
				case 2: // RESIDENZA
					if(!StringUtils.isBlank(componenteInput.getIndirizzoRes())) {
						if(!compareB(componenteInput.getIndirizzoRes(), indirizzo.getIndirizzo()) ) {
							diversi = true;
							if(mapVariazioni != null)
								mapVariazioni.put(TipoAggiornamentoAnagrafica.INDIRIZZO_RESIDENZA, componenteInput.getIndirizzoRes());
						}
					}
					if(!StringUtils.isBlank(componenteInput.getComResDes())) {
						if(!compareB(componenteInput.getComResDes(), indirizzo.getComDes()) ) {
							diversi = true;
							if(mapVariazioni != null)
								mapVariazioni.put(TipoAggiornamentoAnagrafica.COMUNE_DI_RESIDENZA, componenteInput.getComResDes());
						}
					}
					if(!StringUtils.isBlank(componenteInput.getNumCivRes())) {
						if(!compareB(componenteInput.getNumCivRes(), indirizzo.getCivicoNumero()) ) {
							diversi = true;
							if(mapVariazioni != null)
								mapVariazioni.put(TipoAggiornamentoAnagrafica.NUM_CIVICO_RESIDENZA, componenteInput.getNumCivRes());
						}
					}
					if(!StringUtils.isBlank(componenteInput.getProvResCod()) 
							&& !compareB(componenteInput.getProvResCod() , indirizzo.getProv())){
						diversi = true;
						if(mapVariazioni != null)
							mapVariazioni.put(TipoAggiornamentoAnagrafica.PROVINCIA_DI_RESIDENZA, componenteInput.getProvResCod());
					}
					if(!StringUtils.isBlank(componenteInput.getComResCod()) && !compareB(componenteInput.getComResCod() , indirizzo.getComCod())){
						diversi = true;
						if(mapVariazioni != null)
							mapVariazioni.put(TipoAggiornamentoAnagrafica.COD_COM_RESIDENZA,componenteInput.getComResCod());
					}
				
					break;
				case 3: // RESIDENZA INPUT -DOMICILIO INPUT
					if(StringUtils.isBlank(componenteInput.getIndirizzoRes()) && !StringUtils.isBlank(componenteInput.getIndirizzoDom())) {
						diversi = true;
						break;
					}
						
					if(!StringUtils.isBlank(componenteInput.getIndirizzoRes()) && !StringUtils.isBlank(componenteInput.getIndirizzoDom())) {
						if(!compareB(componenteInput.getIndirizzoRes(), componenteInput.getIndirizzoDom()) ) {
							diversi = true;
						}
					}
					if(!StringUtils.isBlank(componenteInput.getComResDes()) && !StringUtils.isBlank(componenteInput.getComDomDes())) {
						if(!compareB(componenteInput.getComResDes(), componenteInput.getComDomDes()) ) {
							diversi = true;
						}
					}
//					if(componenteInput.getNumCivRes() != null && !componenteInput.getNumCivRes().equals("")) {
//						if(!compareB(componenteInput.getNumCivRes(), indirizzo.getCivicoNumero()) ) {
//							diversi = true;
//							 
//						}
//					}
					if(!StringUtils.isBlank(componenteInput.getProvResCod()) && !StringUtils.isBlank(componenteInput.getProvDomCod())
							&& !compareB(componenteInput.getProvResCod() , componenteInput.getProvDomCod())){
						diversi = true;
					}
//					if(componenteInput.getComResCod() != null   && !componenteInput.getComResCod().equals("") 
//							&& !compareB(componenteInput.getComResCod() , indirizzo.getComCod())){
//					diversi = true;
//					 
//				}		
			}
		return diversi;
		
	}
	/**
	 * Torna TRUE se la prima stringa è diversa da NULL quando la seconda è UGUALE a NULL, oppure se la seconda stringa è uguale alla prima<br> .
	 * Torna FALSE se la prima stringa è diversa dalla seconda, oppure  se la prima stringa è NULL e la seconda è DIVERSA DA NULL
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean compare(String str1, String str2) {
	    return ((str1 == null ||str2 == null) 
	    			? str2 == null : str1.equalsIgnoreCase(str2));
//	    return ((str1 != null && str2 == null) ||
//	    			(str1 == null && str2 != null) ||
//	    				(str1!=null && str2!=null && !str1.toString().trim().equalsIgnoreCase(str2.toString().trim())));
//	    				 
	   
	}
	/***
	 * torna TRUE quando STR1 è nulla e lo è anche STR2 oppure quando STR1 è UGUALE a STR2 (che non è nulla)
	 * torna FALSE quando STR1 è NULL e STR2 è diverso da NULL oppure quando STR1 è DIVERSO da STR2 (che puo' essere nullo)
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean compareB(String str1, String str2) {
	    return (str1 == null ? str2 == null : str1.equalsIgnoreCase(str2));
	}
	
	public static boolean compare(Date str1, Date str2) {
	    return (str1 == null ? str2 == null : str1.equals(str2));
	}
	//SISO-1127 Fine
	
	@Override
	public void saveAggiornamentoAnagraficaCasoGit(BaseDTO dto) {
		soggettoDAO.addAnagraficaCasoGIT((CsAComponenteAnagCasoGit) dto.getObj());
	}
	
	public CsAAnagrafica getAnagraficaById(BaseDTO dto) {
		return soggettoDAO.getAnagraficaById((Long) dto.getObj());
	}

	@Override
	public CsAAnagrafica saveAnagrafica(BaseDTO dto) {
		CsAAnagrafica csa = (CsAAnagrafica) dto.getObj();
		return soggettoDAO.saveAnagrafica(csa);
	}

	@Override
	public CsASoggettoLAZY salvaAnagraficaCaso(DatiAnagraficaCasoSaveDTO casoDTO) throws Exception{
		
		BaseDTO dto = new BaseDTO();
		IterDTO itDto = new IterDTO();
		copiaCsTBaseObject(casoDTO, dto);
		copiaCsTBaseObject(casoDTO, itDto);
		
		CsASoggettoLAZY soggetto = casoDTO.getSoggetto();
		boolean nuovoInserimento = casoDTO.isNuovoInserimento();
		String username = casoDTO.getUsernameOperatore();
		
		if(nuovoInserimento) soggetto = saveSoggetto(soggetto, casoDTO.getUsernameOperatore());
		else 				 soggetto =	updateSoggetto(soggetto, casoDTO.getUsernameOperatore()); 
		
		Long soggettoId = soggetto.getAnagraficaId();
		
		if(nuovoInserimento && casoDTO.getSsSchedaId()!=null){ //Se è la creazione aggiorno la CS_SS
			dto.setObj(casoDTO.getSsSchedaId());
			// SISO-938
			dto.setObj2(casoDTO.getTipoScheda());
			dto.setObj3(soggetto);
			schedaSegrService.agganciaCartellaASchedaUDC(dto);
		}
		
		logger.debug("Salvataggio SOGGETTO completato");

		//indirizzi
		if(!nuovoInserimento) {
			dto.setObj(soggettoId);
			indirizzoService.eliminaIndirizziBySoggetto(dto);
		}
		for(CsAIndirizzo csInd: casoDTO.getListaIndirizzi()) {
			csInd.setAnaIndirizzoId(null);
			csInd.getCsAAnaIndirizzo().setId(null);
			csInd.setDtIns(new Date());
			csInd.setUserIns(username);
			csInd.setDataInizioSys(new Date());
			csInd.setCsASoggetto(soggetto);
			if(csInd.getDataFineApp() != null && !csInd.getDataFineApp().equals(DataModelCostanti.END_DATE))
				csInd.setDataFineSys(new Date());
			indirizzoDAO.saveIndirizzo(csInd);	
		}
		
		logger.debug("Salvataggio INDIRIZZI completato");
		
		//medici
		if(!nuovoInserimento) {
			soggettoDAO.eliminaSoggettoMedicoBySoggetto(soggettoId);
		}
		for(ValiditaDTO comp: casoDTO.getListaMedici()) {
			CsASoggettoMedico cs = new CsASoggettoMedico();
			CsASoggettoMedicoPK csPK = new CsASoggettoMedicoPK();
			//cs.setCsASoggetto(soggetto);
			cs.setDataInizioApp(comp.getDataInizio());
			cs.setDataInizioSys(new Date());
			cs.setDtIns(new Date());
			cs.setUserIns(username);
			csPK.setMedicoId(comp.getId());
			csPK.setDataFineApp(comp.getDataFine());
			csPK.setSoggettoAnagraficaId(soggettoId);
			cs.setId(csPK);
			soggettoDAO.saveSoggettoMedico(cs);
		}
		
		logger.debug("Salvataggio MEDICI completato");
		
		//status
		if(!nuovoInserimento) {
			soggettoDAO.eliminaSoggettoStatusBySoggetto(soggettoId);
		}
		for(ValiditaDTO comp: casoDTO.getListaStatus()) {
			CsASoggettoStatus cs = new CsASoggettoStatus();
			CsASoggettoStatusPK csPK = new CsASoggettoStatusPK();
			//cs.setCsASoggetto(soggetto);
			cs.setDataInizioApp(comp.getDataInizio());
			cs.setDataInizioSys(new Date());
			cs.setDtIns(new Date());
			cs.setUserIns(username);
			csPK.setStatusId(comp.getId());
			csPK.setDataFineApp(comp.getDataFine());
			csPK.setSoggettoAnagraficaId(soggettoId);
			cs.setId(csPK);
			
			dto.setObj(cs);
			soggettoDAO.saveSoggettoStatus(cs);
		}
		
		logger.debug("Salvataggio STATUS completato");
		
		//stato civile
		if(!nuovoInserimento) {
			soggettoDAO.eliminaSoggettoStatoCivileBySoggetto(soggettoId);
		}
		for(ValiditaDTO comp: casoDTO.getListaStatoCivile()) {
			CsASoggettoStatoCivile cs = new CsASoggettoStatoCivile();
			CsASoggettoStatoCivilePK csPK = new CsASoggettoStatoCivilePK();
			//cs.setCsASoggetto(soggetto);
			cs.setDataInizioApp(comp.getDataInizio());
			cs.setDataInizioSys(new Date());
			cs.setDtIns(new Date());
			cs.setUserIns(username);
			
			csPK.setStatoCivileCod(comp.getId().toString());
			csPK.setDataFineApp(comp.getDataFine());
			csPK.setSoggettoAnagraficaId(soggettoId);
			cs.setId(csPK);
			
			soggettoDAO.saveSoggettoStatoCivile(cs);
		}
		
		logger.debug("Salvataggio STATO CIVILE completato");
		
		
		//iterstep
		if(nuovoInserimento) {
			//itDto.setNomeOperatore(username);			
			CsOOperatoreSettore opSettore = casoDTO.getCurrentOpSettore();
			itDto.setIdSettore(opSettore.getCsOSettore().getId());
			itDto.setIdOperatore(opSettore.getCsOOperatore().getId());
			itDto.setAlertUrl("");
			itDto.setNotificaSettoreSegnalante(true);
			itDto.setDataInserimento(new Date());
			itDto.setCsACaso(soggetto.getCsACaso());
			iterService.newIter(itDto);
		}
		logger.debug("Salvataggio ITER completato");
		
		//1127- inizio
		//Inserimento in AnagraficaCasoGIT per controllo aggiornamento dati anagrafici del soggetto
		//vengono salvati solamente alcuni valori, il confronto avviene su dati freschi, salvati nelle tabelle di appartenenza
		CsAComponenteAnagCasoGit csCompAnagCasoGit = this.getAnagraficaSoggettoGitBySoggettoId(soggetto.getAnagraficaId());
		if(csCompAnagCasoGit == null) {
			csCompAnagCasoGit = new CsAComponenteAnagCasoGit();
			csCompAnagCasoGit.setIdOrigWs(soggetto.getCsAAnagrafica().getIdOrigWs());
			csCompAnagCasoGit.setCf(soggetto.getCsAAnagrafica().getCf());
			csCompAnagCasoGit.setDtIns(new Date());
			csCompAnagCasoGit.setCognome(soggetto.getCsAAnagrafica().getCognome());
			csCompAnagCasoGit.setNome(soggetto.getCsAAnagrafica().getNome());
			csCompAnagCasoGit.setCsASoggetto(soggetto);
			csCompAnagCasoGit.setSesso(soggetto.getCsAAnagrafica().getSesso());
			csCompAnagCasoGit.setCittadinanza(soggetto.getCsAAnagrafica().getCittadinanza());
			csCompAnagCasoGit.setFlgSegnalazione(false);
			csCompAnagCasoGit.setSegnalazione("-");
			
			soggettoDAO.addAnagraficaCasoGIT(csCompAnagCasoGit);
			logger.debug("Salvataggio STORICO ANAGRAFICHE completato");
		}
		
	  //1127 - fine
		
		if(nuovoInserimento) {
			//creo la famiglia GIT
			dto.setObj(soggetto);
			dto.setObj2(casoDTO.getListaFamiliari());
			parentiService.createFamigliaGruppoGit(dto);
		}
		
		logger.debug("Salvataggio FAMIGLIA completato");

		return soggetto;
	}

	@Override
	public List<CsRdcAnagraficaGepi> getAnagraficheRdCGepi(SearchRdCDTO dto) throws Throwable{
		return soggettoDAO.getAnagraficheRdCGepi(dto);
	}

	@Override
	public Integer getAnagraficheRdCGepiCount(SearchRdCDTO dto) {
		 return soggettoDAO.getAnagraficheRdCGepiCount(dto);
	}

	@Override
	public Boolean isBeneficiarioRdC(BaseDTO dto) {
		return soggettoDAO.isBeneficiarioRdC((String)dto.getObj());
	}
	
	@Override
	public Boolean hasNucleoBeneficiarioRdC(BaseDTO dto) {
		Long idSoggetto = (Long)dto.getObj(); 
		String cfSoggetto = (String)dto.getObj2(); 
		Date dtVal = (Date)dto.getObj3();
		return this.hasNucleoBeneficiarioRdC(idSoggetto, cfSoggetto, dtVal);
	}
	
	private Boolean hasNucleoBeneficiarioRdC(Long idSoggetto, String cfSoggetto, Date dtVal){
		boolean exists = false;
		if(idSoggetto!=null && !StringUtils.isBlank(cfSoggetto))
			exists = soggettoDAO.hasNucleoBeneficiarioRdC(idSoggetto, cfSoggetto, dtVal);
		return exists;
	}

	@Override
	public List<CasiCatSocialeBean> loadProspettoSintesi(BaseDTO bDto) {
		List<CasiCatSocialeBean> lstCasiCatSociale = new ArrayList<CasiCatSocialeBean>();
		Long settoreId = (Long) bDto.getSettoreId();
		List<CsRelSettoreCatsoc> listaCatSociali = catSocialeDAO.findRelSettoreCatsocBySettore(settoreId);

		CasoSearchCriteria searchCriteria = new CasoSearchCriteria();
		searchCriteria.setUsername(bDto.getUserId());
		
		// Per ciascuna cat.sociale conteggio il numero di casi
		// registrati/in carico/chiusi dell'organizzazione
		for (CsRelSettoreCatsoc settCatSoc : listaCatSociali) {
			
			CsCCategoriaSociale csoc = settCatSoc.getCsCCategoriaSociale();
			CsOSettore settore = settCatSoc.getCsOSettore();
			
			CasiCatSocialeBean comp = new CasiCatSocialeBean();
			comp.setCatSocialeId(csoc.getId());
			comp.setCatSocialeDesc(csoc.getDescrizione());
			
			comp.setSettore(settore.getDescrizione());
			comp.setOrganizzazione(settore.getCsOOrganizzazione().getNome());

			searchCriteria.setWithResponsabile(false);
			searchCriteria.setIdLastIter(null);
			searchCriteria.setIdCatSociale(settCatSoc.getCsCCategoriaSociale().getId());
			// Ricerco le categorie sociali del settore selezionato,
			// altrimenti becco tutto anche le altre org.
			searchCriteria.setIdSettore(settCatSoc.getCsOSettore().getId());
			searchCriteria.setIdOrganizzazione(settCatSoc.getCsOSettore().getCsOOrganizzazione().getId());

			ContatoreCasiDTO numCasi = soggettoDAO.getCasiPerCategoriaCount(searchCriteria);

			searchCriteria.setWithResponsabile(true);
			searchCriteria.setIdLastIter(null);
			ContatoreCasiDTO numCasiInCarico = soggettoDAO.getCasiPerCategoriaCount(searchCriteria);
			searchCriteria.setWithResponsabile(false);
			searchCriteria.setIdLastIter(DataModelCostanti.IterStatoInfo.CHIUSO);
			ContatoreCasiDTO numCasiChiusi = soggettoDAO.getCasiPerCategoriaCount(searchCriteria);

			comp.setCasi(numCasi);
			comp.setCasiInCarico(numCasiInCarico);
			comp.setCasiChiusi(numCasiChiusi);
			lstCasiCatSociale.add(comp);
		}
		
		return lstCasiCatSociale;
	}
	
	@Override
	public DatiAnagraficaCasoDTO loadDatiAnagraficaCaso(BaseDTO dto){
		Long idSoggetto = (Long)dto.getObj();
		
		DatiAnagraficaCasoDTO da = new DatiAnagraficaCasoDTO();
		
		//carico indirizzi
		List<CsAIndirizzo> listaIndirizzi = indirizzoService.getIndirizziBySoggetto(dto);
		da.setListaIndirizzi(listaIndirizzi);
	
		//carico medici
		List<ValiditaDTO> lstMedici = new ArrayList<ValiditaDTO>();
		List<CsASoggettoMedico> lstSoggMedico = soggettoDAO.getSoggettoMedicoBySoggetto(idSoggetto);
		for(CsASoggettoMedico csSoggComp: lstSoggMedico) {
			ValiditaDTO comp = new ValiditaDTO();
			comp.setDataFine(csSoggComp.getId().getDataFineApp());
			comp.setDataInizio(csSoggComp.getDataInizioApp());
			comp.setDescrizione(csSoggComp.getCsCMedico().getDenominazione());
			comp.setId(csSoggComp.getId().getMedicoId());
			lstMedici.add(comp);
		}
		da.setListaMedici(lstMedici);
		
		//carico status
		List<ValiditaDTO> lstStatus = new ArrayList<ValiditaDTO>();
		List<CsASoggettoStatus> lstSoggStatus = soggettoDAO.getSoggettoStatusBySoggetto(idSoggetto);
		for(CsASoggettoStatus csSoggComp: lstSoggStatus) {
			ValiditaDTO comp = new ValiditaDTO();
			comp.setDataFine(csSoggComp.getId().getDataFineApp());
			comp.setDataInizio(csSoggComp.getDataInizioApp());
			comp.setDescrizione(csSoggComp.getCsTbStatus().getDescrizione());
			comp.setId(csSoggComp.getId().getStatusId());
			lstStatus.add(comp);
		}
		da.setListaStatus(lstStatus);
		
		//carico stato civile
		List<ValiditaDTO> lstStatoCivile = new ArrayList<ValiditaDTO>();
		List<CsASoggettoStatoCivile> lstSoggStatoCiv = soggettoDAO.getSoggettoStatoCivileBySoggetto(idSoggetto);
		for(CsASoggettoStatoCivile csSoggComp: lstSoggStatoCiv) {
			ValiditaDTO comp = new ValiditaDTO();
			comp.setDataFine(csSoggComp.getId().getDataFineApp());
			comp.setDataInizio(csSoggComp.getDataInizioApp());
			comp.setDescrizione(csSoggComp.getCsTbStatoCivile().getDescrizione());
			comp.setId(new Long(csSoggComp.getId().getStatoCivileCod()));
			lstStatoCivile.add(comp);
		}
		da.setListaStatoCivile(lstStatoCivile);
		
		da.setExistsCatSociali( existsCatSocAttualiBySoggetto(dto));
		
		return da;
	}
 
}
