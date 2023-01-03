package it.webred.ss.ejb;

import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.ct.support.validation.ValidationStateless;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;
import it.webred.ss.dao.ConfigurazioneDAO;
import it.webred.ss.dao.SsSchedaDAO;
import it.webred.ss.data.model.ArBufferSsInvio;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsAnagraficaLog;
import it.webred.ss.data.model.SsCCategoriaSociale;
import it.webred.ss.data.model.SsClassificazioneMotivazione;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsIndirizzo;
import it.webred.ss.data.model.SsInterventiSchede;
import it.webred.ss.data.model.SsIntervento;
import it.webred.ss.data.model.SsInterventoEconomico;
import it.webred.ss.data.model.SsInterventoEconomicoTipo;
import it.webred.ss.data.model.SsMotivazione;
import it.webred.ss.data.model.SsMotivazioniSchede;
import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.data.model.SsPuntoContatto;
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsRelUffPcontOrgPK;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.data.model.SsSchedaAccessoInviante;
import it.webred.ss.data.model.SsSchedaInterventi;
import it.webred.ss.data.model.SsSchedaMotivazione;
import it.webred.ss.data.model.SsSchedaRiferimento;
import it.webred.ss.data.model.SsSchedaSegnalante;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.data.model.SsTipoScheda;
import it.webred.ss.data.model.SsUfficio;
import it.webred.ss.data.model.privacy.CsSsPrivacy;
import it.webred.ss.data.model.privacy.CsSsPrivacyPK;
import it.webred.ss.data.model.privacy.RdcConsensiSocToLav;
import it.webred.ss.data.model.privacy.RdcConsensiSocToLavPK;
import it.webred.ss.data.model.privacy.SsSchedaPrivacy;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.ejb.dto.DatiPrivacyDTO;
import it.webred.ss.ejb.dto.DatiSchedaListDTO;
import it.webred.ss.ejb.dto.SchedaUdcBaseDTO;
import it.webred.ss.ejb.dto.SchedaUdcDTO;
import it.webred.ss.ejb.dto.SintesiSchedeUfficioDTO;
import it.webred.ss.ejb.dto.SsSearchCriteria;
import it.webred.ss.ejb.dto.report.DatiPrivacyPdfDTO;
import it.webred.ss.ejb.dto.report.DatiSchedaPdfDTO;
import it.webred.ss.ejb.dto.report.RiferimentoPdfDTO;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class SsSchedaSessionBean
 */
@Stateless
@Interceptors(ValidationStateless.class)
public class SsSchedaSessionBean implements SsSchedaSessionBeanRemote {
	public static final String SENZA_FISSA_DIMORA= "Senza fissa dimora";
	private SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	private SsSchedaDAO dao;
	
	@Autowired
	private ConfigurazioneDAO confdao;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/LoginBean")
	protected LoginBeanService loginService;
	
	protected static Logger logger = Logger.getLogger("segretariatosoc.log");
	
	@Override
	public void saveConsensoPrivacy(DatiPrivacyDTO dto) {
		SsSchedaPrivacy privacy = new SsSchedaPrivacy();
		CsSsPrivacyPK pk = new CsSsPrivacyPK();
		String cf = !StringUtils.isBlank(dto.getCf()) ? dto.getCf().toUpperCase() : null;
		pk.setCf(cf);
		pk.setOrganizzazioneId(dto.getOrganizzazioneId());
		privacy.setId(pk);
		privacy.setDtSottoscrizione(dto.getDtSottoscrizione());
		privacy.setSchedaId(dto.getSchedaId());
		
		privacy.setUserIns(dto.getUserId());
		Date today = new Date();
		privacy.setDtIns(today);
		if(privacy.getDtSottoscrizione()==null)
			privacy.setDtSottoscrizione(today);
		dao.saveSchedaPrivacy(privacy);
		
		if(dto.isBeneficiarioRdC()) {
			SsOOrganizzazione org = confdao.findOrganizzazione(dto.getOrganizzazioneId());
			if(org!=null) {
				RdcConsensiSocToLavPK idc = new RdcConsensiSocToLavPK();
				idc.setCf(cf);
				idc.setCodEnteFrom(org.getCodCatastale());
				RdcConsensiSocToLav c = dao.findConsensoToLavoro(idc); 
				if(c==null) {
					c = new RdcConsensiSocToLav();
					c.setId(idc);
					c.setDtIns(today);
					c.setUserIns(dto.getUserId());
				}else {
					c.setDtMod(today);
					c.setUserMod(dto.getUserId());
				}
				c.setFlagConsenso(dto.getFlagCentriImpiego());
				dao.saveConsensoToLavoro(c);
			}
		}
	}
	
	@Override
	public Long saveAccessoInviante(BaseDTO dto) {
		return dao.saveAccessoInviante((SsSchedaAccessoInviante) dto.getObj()).getId(); 
	}
	
	
	@Override
	public Long saveSegnalante(BaseDTO dto) {
		return dao.saveSegnalante((SsSchedaSegnalante) dto.getObj()).getId(); 
	}
	
	@Override
	public Long saveRiferimento(BaseDTO dto) {
		return dao.saveRiferimento((SsSchedaRiferimento) dto.getObj()).getId(); 
	}

	@Override
	public Long saveServizi(BaseDTO dto) {
		return dao.saveServizi((SsSchedaInterventi) dto.getObj()).getId();
	}

	@Override
	public Long saveMotivazione(BaseDTO dto) {
		return dao.saveMotivazione((SsSchedaMotivazione) dto.getObj()).getId();
	}

	@Override
	public SsScheda saveScheda(BaseDTO dto) {
		SsScheda scheda = (SsScheda) dto.getObj();
		if(scheda.getId()!=null){
			scheda.setDtMod(new Date());
			scheda.setUsrMod(dto.getUserId());
		}else{
			scheda.setDtIns(new Date());
			scheda.setUserIns(dto.getUserId());
		}
		return dao.saveScheda(scheda);
	}

	@Override
	public void updateCompletamentoScheda(BaseDTO dto) {
		Object[] parameters = (Object[]) dto.getObj();
		// param = id, scheda_completa, tipo scheda
		//dao.updateCompletamentoScheda((Long)parameters[0], (Boolean)parameters[1], (Long)parameters[2]);
		try {
			
			SsScheda ssSchedaTmp = new SsScheda();
			SsScheda ssScheda = dao.cleanCategoriaSocialeScheda((Long)parameters[0]);
//			dao.updateCompletamentoScheda((Long)parameters[0], (Boolean)parameters[1], (Long)parameters[2], (Long)parameters[3]);
			
			ssSchedaTmp.setId(ssScheda.getId());
			ssSchedaTmp.setAccesso(ssScheda.getAccesso());
			ssSchedaTmp.setCompleta( (Boolean)parameters[1]);
			ssSchedaTmp.setDtIns(ssScheda.getDtIns());
			ssSchedaTmp.setDtMod(ssScheda.getDtMod());
			ssSchedaTmp.setEliminata(ssScheda.getEliminata());
			ssSchedaTmp.setEsterna(ssScheda.getEsterna());
			ssSchedaTmp.setIdentificativo(ssScheda.getIdentificativo());
			ssSchedaTmp.setInterventi(ssScheda.getInterventi());
			ssSchedaTmp.setLstCategorieSociali(new ArrayList(1));
			ssSchedaTmp.setMotivazione(ssScheda.getMotivazione());
			ssSchedaTmp.setRiferimento(ssScheda.getRiferimento());
			ssSchedaTmp.setRiferimento2(ssScheda.getRiferimento2());//SISO-947
			ssSchedaTmp.setRiferimento3(ssScheda.getRiferimento3());//SISO-947
			ssSchedaTmp.setSegnalante(ssScheda.getSegnalante());
			ssSchedaTmp.setSegnalato(ssScheda.getSegnalato());
			ssSchedaTmp.setTipo( (Long)parameters[2]);
			ssSchedaTmp.setUserIns(ssScheda.getUserIns());
			ssSchedaTmp.setUsrMod(ssScheda.getUsrMod());
			
			if(parameters[3] != null && (Long)parameters[3] != 0 ){
				SsCCategoriaSociale ssCategoriaSociale = new SsCCategoriaSociale();
				ssCategoriaSociale.setId((Long)parameters[3]);
				ssSchedaTmp.getLstCategorieSociali().add(ssCategoriaSociale);
			}
			
			dao.updateCompletamentoScheda(ssSchedaTmp);
			//dao.updateCompletamentoScheda(ssSchedaTmp,  (Long)parameters[3]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("************ Eccezione propagata... " + e.getMessage());
		}
		
	}

	@Override
	public void updateSegnalante(BaseDTO dto) {
		SsSchedaSegnalante segnalante = (SsSchedaSegnalante) dto.getObj();
		dao.saveSegnalante(segnalante);
	}

	@Override
	public void updateRiferimento(BaseDTO dto) {
		SsSchedaRiferimento riferimento = (SsSchedaRiferimento) dto.getObj();
		dao.saveRiferimento(riferimento);
		
	}

	@Override
	public void updateMotivazione(BaseDTO dto) {
		SsSchedaMotivazione motivazione = (SsSchedaMotivazione) dto.getObj();
		dao.updateMotivazione(motivazione.getId(), motivazione.getAltro());
		
	}

	@Override
	public void updateInterventi(BaseDTO dto) {
		SsSchedaInterventi interventi = (SsSchedaInterventi) dto.getObj();
		dao.updateInterventi(interventi.getId(), interventi.getAltro());
		
	}
	
	@Override
	public SchedaUdcBaseDTO loadSchedaUdcBase(BaseDTO dto){
		SchedaUdcBaseDTO out = new SchedaUdcBaseDTO();
		Long id = (Long)dto.getObj();
		
		SsScheda ssScheda = dao.readScheda(id);
		out.setScheda(ssScheda);
		if (ssScheda != null) {
			SsSchedaSegnalato ssSchedaSegnalato = dao.readSegnalatoById(ssScheda.getSegnalato());
			out.setSegnalato(ssSchedaSegnalato);
			if (ssSchedaSegnalato != null && ssSchedaSegnalato.getAnagrafica() != null) {
				SsAnagrafica anagrafica = ssSchedaSegnalato.getAnagrafica();
				Long organizzazioneID = ssScheda.getAccesso().getSsRelUffPcontOrg().getSsOOrganizzazione().getId();
				
				CsSsPrivacy privacy = dao.findSchedaPrivacy(anagrafica.getCf(), organizzazioneID);
				out.setPrivacySottoscritta(privacy!=null);
			}
		}
		return out;
	}

	@Override
	public SchedaUdcDTO loadSchedaUdcCompleta(BaseDTO dto){
		SchedaUdcDTO out = new SchedaUdcDTO();
		Long id = (Long)dto.getObj();
		Long organizzazioneId = dto.getOrganizzazione();
    	Boolean canReadDiario = (Boolean)dto.getObj2();
    	
		SsScheda ssScheda = dao.readScheda(id);
		out.setScheda(ssScheda);
		if (ssScheda != null) {
			SsSchedaSegnalato ssSchedaSegnalato = dao.readSegnalatoById(ssScheda.getSegnalato());
			out.setSegnalato(ssSchedaSegnalato);
			
			if (ssScheda.getMotivazione() != null) {
				List<SsMotivazioniSchede> listaMotivazioni = dao.readMotivazioniScheda(ssScheda.getMotivazione());
				List<String> motivazioni = new ArrayList<String>();
				for(SsMotivazioniSchede m : listaMotivazioni)
					motivazioni.add(m.getMotivazione().getMotivo());
				out.setListaMotivazioni(motivazioni);
			}

			if (ssSchedaSegnalato != null && ssSchedaSegnalato.getAnagrafica() != null) {
				SsAnagrafica anagrafica = ssSchedaSegnalato.getAnagrafica();
				Long organizzazioneID = ssScheda.getAccesso().getSsRelUffPcontOrg().getSsOOrganizzazione().getId();
				List<SsDiario> listaDiari = dao.readDiarioSociale(anagrafica, organizzazioneID);
				List<String> diari = new ArrayList<String>();
				for(SsDiario d : listaDiari)
					diari.add(d.getNota());
				out.setListaDiari(diari);
				
				List<SsInterventoEconomico> listaInterventiEcon = dao.readInterventiEconomici(anagrafica);
				out.setListaInterventiEconomici(listaInterventiEcon);
				
				CsSsPrivacy privacy = dao.findSchedaPrivacy(anagrafica.getCf(), organizzazioneID);
				out.setPrivacySottoscritta(privacy!=null);
			
		        DatiPrivacyPdfDTO datiPrivacy = loadDatiReportPrivacyPDF(ssScheda, ssSchedaSegnalato);
		        out.setDatiPrivacyPDF(datiPrivacy);
			}
		
			//SISO-947 dati riferimenti
				
			List<SsSchedaRiferimento> listaRiferimenti = new ArrayList<SsSchedaRiferimento>();
			listaRiferimenti.add(ssScheda.getRiferimento());
			listaRiferimenti.add( ssScheda.getRiferimento2());
		    listaRiferimenti.add(ssScheda.getRiferimento3());
		    out.setListaRiferimenti(listaRiferimenti);
		    
			if(ssScheda.getInterventi() != null) {
				List<SsInterventiSchede> listaInterventi = dao.readInterventiScheda(ssScheda.getInterventi());
				List<String> interventi = new ArrayList<String>();
				for(SsInterventiSchede d : listaInterventi)
					interventi.add(d.getIntervento().getIntervento());
				out.setListaInterventi(interventi);
			}
			
			//FINE SISO 961
			// dati diario sociale
        	List<SsAnagrafica> anagrafiche = dao.readAnagraficheByCf(ssSchedaSegnalato.getAnagrafica().getCf());
        	
        	
        	List<SsDiario>  noteDiario = new ArrayList<SsDiario>();
        	for(SsAnagrafica ana: anagrafiche){
        		Long orgId = ssScheda.getAccesso().getSsRelUffPcontOrg().getSsOOrganizzazione().getId();
        		List<SsDiario> lstDiari = dao.readDiarioSociale(ana, orgId);
        		for(SsDiario diario : lstDiari){
        			if(!canReadNotaDiario(diario, ssScheda.getAccesso().getOperatore(), organizzazioneId, dto.getUserId(), canReadDiario))
        				diario.setNota(" ** L'operatore corrente non è autorizzato a leggere il contenuto della nota **");
        			noteDiario.add(diario); 
        		}
        	}
        	out.setNoteDiario(noteDiario);

		}
		return out;
	}
	
	private boolean canReadNotaDiario(SsDiario nota, String operatoreAccesso, Long organizzazioneId, String uNameOperatoreCorrente, Boolean canReadDiario){
		
		if(nota.getPubblica()) //la nota è pubblica
			return true;
		
		boolean responsabileSsEnte = loginService.appartieneAGruppoLike(uNameOperatoreCorrente, nota.getEnte().getCodRouting(), "SSOCIALE_RESPONSABILI");
		//responsabile dell'organizzazione in cui è stata inserita la nota
		if(responsabileSsEnte)
			return true;
		
		//l'utente che ha scritto la nota è l'operatore corrente
		if(nota.getAutore().equals(uNameOperatoreCorrente))
			return true;
		
		//l'operatore che risulta registrato in SS_SCHEDA_ACCESSO è l'utente corrente
		if(operatoreAccesso.equals(uNameOperatoreCorrente)) 
			return true;

		//l'operatore possiede il permesso di leggere i DIARI in UDC e si è loggato con la stessa organizzazione di creazione della nota
		if(canReadDiario && nota.getEnte().getId()== organizzazioneId)
			return true;
		
		return false;
	}
	
	@Override
	public SsScheda readScheda(BaseDTO dto) {
		Long id = (Long) dto.getObj();
		return dao.readScheda(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SsScheda> readSchedeIn(BaseDTO dto) {
		List<Long> idList = (List<Long>) dto.getObj();
		return dao.readSchedeIn(idList);
	}
	
	@Override
	public Long countTotaleSchedeInUfficio(BaseDTO dto) {
		SsSearchCriteria ss = this.getSearchCriteria((Long)dto.getObj(), dto.getOrganizzazione());
		ss.setCompleta(true);
		return dao.countSchedeUDC(ss);
	}

	@Override
	public Long countSchedeSospeseInUfficio(BaseDTO dto) {
		SsSearchCriteria ss = this.getSearchCriteria((Long)dto.getObj(), dto.getOrganizzazione());
		ss.setCompleta(true);
		ss.setEliminata(false);
		ss.setTipoScheda(2);
		return dao.countSchedeUDC(ss);
		
	}

	@Override
	public Long countSchedeInCaricoInUfficio(BaseDTO dto) {
		SsSearchCriteria ss = this.getSearchCriteria((Long)dto.getObj(), dto.getOrganizzazione());
		ss.setCompleta(true);
		ss.setEliminata(false);
		ss.setTipoScheda(3);
		return dao.countSchedeUDC(ss);
	}

	@Override
	public Long countSchedeEliminateInUfficio(BaseDTO dto) {
		SsSearchCriteria ss = this.getSearchCriteria((Long)dto.getObj(), dto.getOrganizzazione());
		ss.setCompleta(true);
		ss.setEliminata(true);
		return dao.countSchedeUDC(ss);
	}

/*	@Override
	public List<SsScheda> readSchedeInUfficio(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		return dao.readSchedeInUfficio(id, dto.getOrganizzazione());
	}*/

	@Override
	public List<DatiSchedaListDTO> searchSchedeInUfficio(SsSearchCriteria ss) {
		ss.setCompleta(true);
		ss.setEliminata(false);
		return dao.searchSchedeUDC(ss);
	}
	
	@Override
	public Long countSchedeInUfficio(SsSearchCriteria dto) {
		dto.setCompleta(true);
		dto.setEliminata(false);
		return dao.countSchedeUDC(dto);
	}
	
	/*	@Override
	public List<SsScheda> readSchedeSoggetto(BaseDTO dto) {
		//Long anagraficaID = (Long)dto.getObj();
		//return dao.readSchedeSoggetto(anagraficaID);
	}*/
	
	@Override
	public List<DatiSchedaListDTO> searchSchedeCompleteSoggetto(SsSearchCriteria ss){
		ss.setCompleta(true);
		ss.setEliminata(false);
		return dao.searchSchedeUDC(ss);
	}
	
	@Override
	public Long countSchedeCompleteSoggetto(SsSearchCriteria ss){
		ss.setCompleta(true);
		ss.setEliminata(false);
		return dao.countSchedeUDC(ss);
	}
	
	@Override
	public List<DatiSchedaListDTO> searchSchedeIncomplete(SsSearchCriteria dto) {
		dto.setCompleta(false);
		dto.setEliminata(false);
		return dao.searchSchedeUDC(dto);
	}
	
	@Override
	public Long countSchedeIncomplete(SsSearchCriteria dto) {
		dto.setCompleta(false);
		dto.setEliminata(false);
		return dao.countSchedeUDC(dto);
	}
	
	@Override
	public void eliminaScheda(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		dao.eliminaScheda(id);
	}
	
	private SsSearchCriteria getSearchCriteria(Long ufficioId, Long orgId){
		SsSearchCriteria ss = new SsSearchCriteria();
		ss.setUfficioId(ufficioId);
		ss.setOrganizzazioneId(orgId);
		return ss;
	}

	@Override
	public List<SsMotivazione> readMotivazioni(BaseDTO dto) {
		return dao.readMotivazioni();
	}

	@Override
	public List<SsIntervento> readInterventi(BaseDTO dto) {
		return confdao.readInterventi();
	}
	

	@Override
	public SsMotivazione readMotivazione(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		return dao.readMotivazione(id);
	}

	
	
	
	@Override
	public void writeMotivazioneScheda(BaseDTO dto) {
		Object[] parameters = (Object[]) dto.getObj();
		SsMotivazioniSchede motivo = new SsMotivazioniSchede();
		motivo.setMotivazione((SsMotivazione)parameters[0]);
		motivo.setScheda((SsSchedaMotivazione)parameters[1]);
		dao.writeMotivazioneScheda(motivo);
	
	}

	@Override
	public void writeInterventoScheda(BaseDTO dto) {
		Object[] parameters = (Object[]) dto.getObj();
		SsInterventiSchede intervento = new SsInterventiSchede();
		intervento.setIntervento((SsIntervento)parameters[0]);
		intervento.setScheda((SsSchedaInterventi)parameters[1]);
		dao.writeInterventoScheda(intervento);
	}

	@Override
	public void deleteMotivazioniScheda(BaseDTO dto) {
		SsSchedaMotivazione scheda = (SsSchedaMotivazione)dto.getObj();
		dao.deleteMotivazioniScheda(scheda);
		
	}

	@Override
	public List<SsMotivazioniSchede> readMotivazioniScheda(BaseDTO dto) {
		SsSchedaMotivazione scheda = (SsSchedaMotivazione)dto.getObj();
		return dao.readMotivazioniScheda(scheda);
	}
	
	//modifica motivazioni schede by id
	
	public List<String> readMotivazioniSchedaById(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		return dao.readMotivazioniSchedaById(id);
	}

	public List<SsMotivazione> readMotivazioniByClasId(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		return dao.readMotivazioniByClasId(id);
	}
	
	
	@Override
	public SsIntervento readIntervento(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		return dao.readIntervento(id);
	}

	@Override
	public List<SsInterventiSchede> readInterventiScheda(BaseDTO dto) {
		SsSchedaInterventi scheda = (SsSchedaInterventi)dto.getObj();
		return dao.readInterventiScheda(scheda);
	}
	
	@Override
	public void deleteInterventiScheda(BaseDTO dto) {
		SsSchedaInterventi scheda = (SsSchedaInterventi)dto.getObj();
		dao.deleteInterventiScheda(scheda);
	}

	@Override
	public SsAnagrafica readAnagraficaByCf(BaseDTO dto) {
		String cf = (String) dto.getObj();
		return dao.readAnagraficaByCf(cf);
	}

	@Override
	public HashMap<SsOOrganizzazione, List<SintesiSchedeUfficioDTO>>  countAnagraficaInSsComplete(BaseDTO dto) {
		String cf = (String)dto.getObj();
		HashMap<String,Integer> mapIn =  dao.countAnagraficaInSs(cf, true);
		return mappaOrgUfficioNumSchede(mapIn, cf);
	}
	
	@Override
	public HashMap<SsOOrganizzazione, List<SintesiSchedeUfficioDTO>>  countAnagraficaInSsInComplete(BaseDTO dto) {
		String cf = (String)dto.getObj();
		HashMap<String,Integer> mapIn =  dao.countAnagraficaInSs(cf, false);
		return mappaOrgUfficioNumSchede(mapIn, cf);
	}
	
	private HashMap<SsOOrganizzazione, List<SintesiSchedeUfficioDTO>>  mappaOrgUfficioNumSchede(HashMap<String,Integer> mapIn, String cf){
		HashMap<SsOOrganizzazione, List<SintesiSchedeUfficioDTO>> mappa = new HashMap<SsOOrganizzazione, List<SintesiSchedeUfficioDTO>> ();
		Iterator<String> iter = (Iterator<String>)mapIn.keySet().iterator();
		while(iter.hasNext()){
			String skey = (String)iter.next();
			String[] key = skey.split("@");
			Long idOrg = new Long(key[0]);
			Long idUfficio = new Long(key[1]);
			SsOOrganizzazione o = confdao.findOrganizzazione(idOrg);
			SsUfficio u = confdao.readUfficio(idUfficio);
			
			if(o==null){
				o = new SsOOrganizzazione();
				o.setId(idOrg);
				o.setNome("Ente non definito");
			}
			
			if(u==null){
				u = new SsUfficio();
				u.setId(idUfficio);
				u.setNome("Ufficio non definito");
			}
			
			//TODO Object Org-Ufficio-COUNT
			List<SintesiSchedeUfficioDTO> lst = mappa.get(o);
			if(lst==null) lst = new ArrayList<SintesiSchedeUfficioDTO>();
			
			SintesiSchedeUfficioDTO dto = new SintesiSchedeUfficioDTO(u, mapIn.get(skey));
			lst.add(dto);
			
			mappa.put(o,lst);
		}
		return mappa;
	}

	@Override
	public Boolean isAnagraficaEsterna(BaseDTO dto) {
		String cf = (String)dto.getObj();
		return dao.isAnagraficaEsterna(cf);
	}

	@Override
	public Long saveAnagrafica(BaseDTO dto) {
		SsAnagrafica anagrafica = (SsAnagrafica)dto.getObj();
		return dao.writeAnagrafica(anagrafica).getId();
	}
	
	@Override
	public boolean saveAnagraficaLog(BaseDTO dto) {
		try {
			
			//SOLO INSERIMENTO
			SsAnagraficaLog anaLog = (SsAnagraficaLog)dto.getObj();
			dao.saveAnagraficaLog(anaLog);

		return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	return false;
	}

	@Override
	public SsSchedaSegnalato saveSegnalato(BaseDTO dto) {
		SsSchedaSegnalato segnalato = (SsSchedaSegnalato)dto.getObj();
		return dao.saveSegnalato(segnalato);
	}

	@Override
	public SsAnagrafica readAnagraficaById(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		return dao.readAnagraficaById(id);
	}

	@Override
	public List<SsSchedaSegnalato> readSegnalati(BaseDTO dto) {
		return dao.readSegnalati();
	}

	@Override
	public List<SsScheda> readSchede(BaseDTO dto) {
		return dao.readSchede();
	}

	@Override
	public SsSchedaSegnalato readSegnalatoById(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		return dao.readSegnalatoById(id);
	}

	@Override
	public Long writeNotaDiario(BaseDTO dto) {
		SsDiario nota = (SsDiario)dto.getObj();
		return dao.writeNotaDiario(nota).getId();
	}

	@Override
	public void updateNotaDiario(BaseDTO dto) {
		SsDiario nota = (SsDiario)dto.getObj();
		dao.updateNotaDiario(nota.getId(), nota.getNota());
	}
	
	@Override
	public List<SsDiario> readDiarioSoggettoEnte(BaseDTO dto) {
		SsAnagrafica anagrafica = (SsAnagrafica)dto.getObj();
		Long organizzazioneID = (Long) dto.getObj2();
		return dao.readDiarioSociale(anagrafica, organizzazioneID);
	}

	@Override
	public Long writeIndirizzo(BaseDTO dto) {
		SsIndirizzo indirizzo = (SsIndirizzo)dto.getObj();
		return dao.writeIndirizzo(indirizzo).getId();
	}

	@Override
	public List<SsInterventoEconomico> readInterventiEconomici(BaseDTO dto) {
		SsAnagrafica anagrafica = (SsAnagrafica)dto.getObj();
		return dao.readInterventiEconomici(anagrafica);
	}

	@Override
	public List<SsInterventoEconomicoTipo> readInterventiEconomiciTipi(BaseDTO dto) {
		return dao.readInterventiEconomiciTipi();
	}

	@Override
	public void writeInterventoEconomico(BaseDTO dto) {
		SsInterventoEconomico intervento = (SsInterventoEconomico) dto.getObj();
		dao.writeInterventoEconomico(intervento);
	}

	@Override
	public SsInterventoEconomicoTipo readInterventoEconomicoTipoByTipo(BaseDTO dto) {
		String tipo = (String)dto.getObj();
		return dao.readInterventoEconomicoTipoByTipo(tipo);
	}

	@Override
	public boolean isSchedaCompleta(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		return dao.isSchedaCompleta(id);
	}

	@Override
	public List<SsAnagrafica> readAnagraficheByCf(BaseDTO dto) {
		String cf = (String) dto.getObj();
		return dao.readAnagraficheByCf(cf);
	}

	@Override
	public List<SsClassificazioneMotivazione> readClassificazioneMotivazione(BaseDTO dto) {
		return dao.readClassificazioneMotivazione();
	}

	@Override
	public SsSchedaSegnalante readSegnalanteById(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		return dao.readSegnalanteById(id);
	}

	@Override
	public List<SsSchedaSegnalato> readSchedeSegnalatoByCF(BaseDTO dto) {
		return dao.readSchedeSegnalatoByCF((String)dto.getObj());
	}

	@Override
	public List<SsSchedaSegnalato> readSchedeByDenominazione(BaseDTO dto) {
		String[] denom = (String[])dto.getObj();
		return dao.readSchedeSegnalatoByDenom(denom[0],denom[1]);
	}

	@Override
	public List<SsScheda> readSchedeByCF(BaseDTO dto) {
		return dao.readSchedeByCF((String)dto.getObj());
	}

	@Override
	public List<SsScheda> readSchedeByUfficioCF(BaseDTO dto) {
		return dao.readSchedeByUfficioCF((String)dto.getObj(), (Long)dto.getObj2());
	}

	@Override
	public DatiPrivacyDTO findSchedaPrivacyByCfEnte(DatiPrivacyDTO dto) {
		DatiPrivacyDTO res = null;
		String cf = dto.getCf().toUpperCase();
		if(!StringUtils.isEmpty(cf)) {
			CsSsPrivacy ssp = dao.findSchedaPrivacy(cf, dto.getOrganizzazioneId());
			if(ssp!=null) {
				res = new DatiPrivacyDTO();
				res.setCf(ssp.getId().getCf());
				res.setOrganizzazione(ssp.getOrganizzazione());
				res.setDtSottoscrizione(ssp.getDtSottoscrizione());
				res.setSchedaId(ssp.getSchedaId());
				res.setFlagCentriImpiego(ssp.getFlagCentriImpiego());
			}
		}
		return res;
	}
	
	@Override
	public SsAnagraficaLog findAnagraficaLogById(BaseDTO dto) {	
				return dao.findAnagraficaLogById((Long) dto.getObj());
	}

	@Override
	public ArBufferSsInvio inviaScheda(BaseDTO dto) {
		return dao.inviaScheda(dto);
		
	}
	
	@Override
	public ArBufferSsInvio riceviSchedaBuffer(BaseDTO dto) {
		return dao.riceviScheda(dto);
		
	}
	
	@Override
	public List<ArBufferSsInvio> readSchedeInviateByIdOrigZs(BaseDTO dto) {
		return dao.readSchedeInviateByIdOrigZs(dto);
	}

	@Override
	public void annullaInvioScheda(BaseDTO dto) {
		dao.annullaInvioScheda(dto);
	}

	@Override
	public int countSchedeInviateUfficioNonLette(SsSearchCriteria dto) {
		return dao.countSchedeInviateUfficio(dto);
	}

	@Override
	public int countSchedeInviateEnteNonLette(SsSearchCriteria dto) {
		return dao.countSchedeInviateEnte((Long)dto.getOrganizzazioneId(), dto.getZonaSociale());
	}

	@Override
	public Long countSchedeInviateUfficio(SsSearchCriteria dto) {
		return dao.countSchedeInviateUfficioTutte(dto);
	}

	@Override
	public Long countSchedeInviateEnte(SsSearchCriteria dto) {
		return dao.countSchedeInviateEnteTutte(dto);
	}

	@Override
	public List<DatiSchedaListDTO> searchSchedeInviateUfficio(
			SsSearchCriteria dto) {
		dto.setCompleta(true);
		dto.setEliminata(false);
		dto.setSoloOrganizzazione(false);
		return dao.searchSchedeInviateUDC(dto);
	}

	@Override
	public List<DatiSchedaListDTO> searchSchedeInviateEnte(SsSearchCriteria dto) {
		dto.setCompleta(true);
		dto.setEliminata(false);
		dto.setSoloOrganizzazione(true); // Ricerco quelle che hanno UFFICIO_DEST = NULL
		return dao.searchSchedeInviateUDC(dto);
	}

	@Override
	public SsSchedaAccessoInviante readSsSchedaAccessoInvianteByIdNuovaScheda(
			BaseDTO dto) {
		return dao.readSsSchedaAccessoInvianteByIdNuovaScheda((Long)dto.getObj());
	}

	@Override
	public boolean esistonoDuplicatiCF(BaseDTO dto) {
		return dao.esistonoDuplicatiCF( (String)dto.getObj(),  (String)dto.getObj2(),  (String)dto.getObj3());
	}

	@Override
	public void deleteNotaDiario(BaseDTO dto){
		dao.deleteNotaDiario((Long)dto.getObj());
	}

	@Override
	public List<SsSchedaSegnalato> searchSchedeBySoggetto(SsSearchCriteria dto) {
		return dao.searchSchedeBySoggetto(dto);
	}

	//SISO-948
	@Override
	public boolean esisteDuplicatoAlias(BaseDTO dto) {
		return dao.esisteDuplicatoAlias( (String)dto.getObj(), (String)dto.getObj2(),  (String)dto.getObj3());
	}

	@Override
	public List<BigDecimal> abilitaCodIstatComune(BaseDTO dto) {
		return dao.readBelfioreDb((String) dto.getObj());
	}
	
	
	@Override
	public List<SsAnagrafica> readAnagraficheByAlias(BaseDTO dto) {
		String alias = (String) dto.getObj();
		return dao.readAnagraficheByAlias(alias);
	}
	
	@Override
	public List<BigDecimal> findUfficioNota(BaseDTO dto){
		return dao.findUfficioNota((Long)dto.getObj());
	}

	@Override
	public DatiPrivacyPdfDTO getDatiReportPrivacy(BaseDTO dto){
		Long idScheda = (Long)dto.getObj();
		DatiPrivacyPdfDTO dati = null; 	
			
		dto.setObj(idScheda);
		SsScheda s = dao.readScheda(idScheda);
		if(s!=null){
			SsSchedaSegnalato segnalato = dao.readSegnalatoById(s.getSegnalato());
			dati = loadDatiReportPrivacyPDF(s, segnalato);
		}
		return dati;
	}
	
	private DatiPrivacyPdfDTO loadDatiReportPrivacyPDF(SsScheda s, SsSchedaSegnalato segnalato){
		DatiPrivacyPdfDTO dati = null; 	
		if(s!=null && segnalato!=null) {
			dati = new DatiPrivacyPdfDTO();
			dati.setAnonimo(segnalato.getAnagrafica().isAnonimo());
			if(!segnalato.getAnagrafica().isAnonimo()){
				
				// accesso
				SsSchedaAccesso accesso = s.getAccesso();
				dati.setAccessoData(accesso.getData()!=null ? ddMMyyyy.format(accesso.getData()) : "");
				dati.setAccessoOperatore(format(accesso.getStampaDesOperatore()));
				dati.setAccessoInterlocutore(format(accesso.getStampaDesInterlocutore()));
				dati.setAccessoMotivo( format(accesso.getStampaDesMotivoDes()));
				dati.setAccessoComune(accesso.getSsRelUffPcontOrg().getSsOOrganizzazione().getNome());
						
				// segnalante
				SsSchedaSegnalante segnalante = s.getSegnalante();
				
				if(segnalante != null){
					
					dati.setSegnalanteCognome(format(segnalante.getCognome()).toUpperCase());
					dati.setSegnalanteNome(format(segnalante.getNome()).toUpperCase());
					dati.setSegnalanteData_nascita(segnalante.getDataNascita()!=null ? ddMMyyyy.format(segnalante.getDataNascita()) : "");
					dati.setSegnalanteLuogo_nascita(format(segnalante.getLuogoDiNascita()));
				    dati.setSegnalanteCel(format(segnalante.getCel()));
				    dati.setSegnalanteTel(format(segnalante.getTelefono()));
				    dati.setSegnalanteSesso(format(segnalante.getSesso()));
					dati.setSegnalanteEmail(format(segnalante.getEmail()));
					
					if(segnalante.getVia()!=null || segnalante.getComune()!=null)
						 dati.setSegnalanteResidenza(format(segnalante.getVia())+", "+format(segnalante.getStampaDesComuneResidenza()));
					
					//SISO-906 -Specifica del parente quando affidatario
					dati.setSegnalanteStatoCivile(segnalante.getTbStatoCivile()!=null ? format(segnalante.getTbStatoCivile().getDescrizione()) : "");
					dati.setSegnalanteRelazione(segnalante.getTbRelazione()!=null ? format(segnalante.getTbRelazione().getDescrizione().concat(segnalante.getAffidatario()? " - Affidatario" : "")) : "");				
				}
								
				// segnalato
			 	SsAnagrafica anagrafica = segnalato.getAnagrafica();
				dati.setCognome(format(anagrafica.getCognome()).toUpperCase());
				dati.setNome(format(anagrafica.getNome()).toUpperCase());
				dati.setCf(anagrafica.getCf());
				
				dati.setSesso(anagrafica.getSesso());
				dati.setData_nascita(anagrafica.getData_nascita()!=null ? ddMMyyyy.format(anagrafica.getData_nascita()) : "");
				dati.setLuogo_nascita(format(anagrafica.getLuogoDiNascita()));
				
				
				String residenza = segnalato.getSenzaFissaDimora()!=null && segnalato.getSenzaFissaDimora() ? SENZA_FISSA_DIMORA +" ": "";
				residenza += segnalato.getResidenza()!=null ? segnalato.getResidenza().getStampaDesIndirizzo() : "";
				
				dati.setResidenza(residenza);
				
				dati.setTel(format(segnalato.getTelefono()));
				dati.setCel(format(segnalato.getCel()));
				dati.setEmail(format(segnalato.getEmail()));
		
				dati.setMedico(format(segnalato.getMedico()));
				
				//SISO-947: riferimento
				dati.setLstRiferimenti(fillRiferimento(s));	
			}
		}
		
		return dati;
	}

	protected List<RiferimentoPdfDTO> fillRiferimento(SsScheda scheda) {
	    List<RiferimentoPdfDTO> listaRiferimenti = new ArrayList<RiferimentoPdfDTO>();
	 
	    SsSchedaRiferimento riferimento = new SsSchedaRiferimento();
	 
		for (int i = 1; i <= 3; i++){
			   RiferimentoPdfDTO rifPdf = new RiferimentoPdfDTO();
		        
			switch (i){
		
				case 1: {
					riferimento= scheda.getRiferimento();
					break;
				}
				
				case 2: {
					riferimento= scheda.getRiferimento2();
					break;
				}
				
				case 3:{
					riferimento= scheda.getRiferimento3();
					break;
				}
				
			}
			
		if (riferimento != null) {
			
			rifPdf.setCognome_rif(format(riferimento.getCognome()).toUpperCase());		
			rifPdf.setNome_rif(format(riferimento.getNome()).toUpperCase());
			rifPdf.setProblemi_rif(format(riferimento.getProblemi_desc()));
			rifPdf.setIndirizzo_rif(format(riferimento.getRecapito()));
			rifPdf.setTel_rif(format(riferimento.getTelefono()));
			rifPdf.setCel_rif(format(riferimento.getCel()));
			rifPdf.setSesso_rif(format(riferimento.getSesso()));
			rifPdf.setLuogo_nascita_rif(format(riferimento.getLuogoDiNascita()));
			rifPdf.setData_nascita_rif(riferimento.getDataNascita() != null ? ddMMyyyy.format(riferimento.getDataNascita()) : "");
			rifPdf.setSc_rif(riferimento.getTbStatoCivile() != null ? format(riferimento.getTbStatoCivile().getDescrizione()) : "");
			//SISO-906
			rifPdf.setParentela_rif(riferimento.getTbRelazione()!=null ? format(riferimento.getTbRelazione().getDescrizione().concat(riferimento.getAffidatario() ? " - Affidatario" : "")) : "");
			rifPdf.setEmail_rif(format(riferimento.getEmail()));
			listaRiferimenti.add(rifPdf);
		}
					
		
		}
			
		return listaRiferimenti;
	}

	
	protected String format(String arg){
		if(arg!=null)
			return arg;
		else
			return "";
	}
	
	
	protected String format(Boolean arg){
		if(arg!=null && arg)
			return "Si";
		return "No";
	}

	@Override
	public DatiSchedaPdfDTO getDatiReportScheda(BaseDTO dto) {
		Long idScheda = (Long)dto.getObj();
		DatiSchedaPdfDTO dati = null; 	
			
		SsScheda s = dao.readScheda(idScheda);
		
		if(s!=null) {
			dati = new DatiSchedaPdfDTO();

			SsSchedaSegnalato segnalato = dao.readSegnalatoById(s.getSegnalato());
				
			// accesso
			SsSchedaAccesso accesso = s.getAccesso();
			if(accesso!=null){
				dati.setAccessoData(accesso.getData()!=null ? ddMMyyyy.format(accesso.getData()) : "");
				dati.setAccessoOperatore(format(accesso.getStampaDesOperatore()));
				dati.setAccessoInterlocutore(format(accesso.getStampaDesInterlocutore()));
				dati.setAccessoMotivo( format(accesso.getStampaDesMotivoDes()));
				dati.setAccessoComune(accesso.getSsRelUffPcontOrg().getSsOOrganizzazione().getNome());
				dati.setAccessoUfficio(accesso.getSsRelUffPcontOrg().getSsUfficio().getNome());
				
				dati.setAccessoModalita(accesso.getModalita());
				
				SsPuntoContatto pc = accesso.getSsRelUffPcontOrg().getSsPuntoContatto();
				dati.setAccessoPuntoContatto(pc.getNome());
				dati.setAccessoPuntoContattoIndirizzo(pc.getIndirLocalizzazione() != null ? pc.getIndirLocalizzazione() : "");
				dati.setAccessoPuntoContattoTel(pc.getTel() != null ? "Tel. " + pc.getTel() : "");
				dati.setAccessoPuntoContattoEmail(pc.getMail() != null ? "e-mail: " + pc.getMail() : "");
			}
			
			// segnalante
			SsSchedaSegnalante segnalante = s.getSegnalante();
			
			if(segnalante != null){
				dati.setRenderSegnalante(true);
				dati.setSegnalanteCognome(format(segnalante.getCognome()).toUpperCase());
				dati.setSegnalanteNome(format(segnalante.getNome()).toUpperCase());
				dati.setSegnalanteData_nascita(segnalante.getDataNascita()!=null ? ddMMyyyy.format(segnalante.getDataNascita()) : "");
				dati.setSegnalanteLuogo_nascita(format(segnalante.getLuogoDiNascita()));
			    dati.setSegnalanteCel(format(segnalante.getCel()));
			    dati.setSegnalanteTel(format(segnalante.getTelefono()));
			    dati.setSegnalanteSesso(format(segnalante.getSesso()));
				dati.setSegnalanteEmail(format(segnalante.getEmail()));
				
				if(segnalante.getVia()!=null || segnalante.getComune()!=null)
					 dati.setSegnalanteResidenza(format(segnalante.getVia())+", "+format(segnalante.getStampaDesComuneResidenza()));
				
				//SISO-906 -Specifica del parente quando affidatario
				dati.setSegnalanteStatoCivile(segnalante.getTbStatoCivile()!=null ? format(segnalante.getTbStatoCivile().getDescrizione()) : "");
				dati.setSegnalanteRelazione(segnalante.getTbRelazione()!=null ? format(segnalante.getTbRelazione().getDescrizione().concat(segnalante.getAffidatario()? " - Affidatario" : "")) : "");				
				
				dati.setSegnalanteRuolo(format(segnalante.getRuolo()));
				dati.setSegnalanteEnte(segnalante.getSettore()!=null ? segnalante.getSettore().getNome() : segnalante.getEnte_servizio());
			}else
				dati.setRenderSegnalante(false);
							
			// segnalato
		 	SsAnagrafica anagrafica = segnalato.getAnagrafica();
			dati.setCognome(format(anagrafica.getCognome()).toUpperCase());
			dati.setNome(format(anagrafica.getNome()).toUpperCase());
			dati.setAlias(format(anagrafica.getAlias()).toUpperCase());
			dati.setCf(anagrafica.getCf());
			
			dati.setSesso(anagrafica.getSesso());
			dati.setData_nascita(anagrafica.getData_nascita()!=null ? ddMMyyyy.format(anagrafica.getData_nascita()) : "");
			dati.setLuogo_nascita(format(anagrafica.getLuogoDiNascita()));
			
			String residenza = segnalato.getSenzaFissaDimora()!=null && segnalato.getSenzaFissaDimora() ? SENZA_FISSA_DIMORA +" ": "";
			residenza += segnalato.getResidenza()!=null ? segnalato.getResidenza().getStampaDesIndirizzo() : "";
			
			dati.setResidenza(residenza);
			
			String tel = format(segnalato.getTelefono());
			tel+= segnalato.getTitolareTelefono() != null ? " (" + format(segnalato.getTitolareTelefono()) + ")" : "";
			dati.setTel(tel);
			
			String cel = format(segnalato.getCel());
			cel+= segnalato.getTitolareCellulare() != null ? " (" + format(segnalato.getTitolareCellulare()) + ")" : "";
			dati.setCel(cel);
			dati.setEmail(format(segnalato.getEmail()));
			
			String cittadinanza = format(anagrafica.getCittadinanza());
			if (anagrafica.getTbCittadinanzaAcq() != null)
				cittadinanza += " (" + anagrafica.getTbCittadinanzaAcq().getDescrizione() + ")";
			
			dati.setCittadinanza(cittadinanza);
			dati.setCittadinanza2(format(anagrafica.getCittadinanza2()));
	
			dati.setMedico(format(segnalato.getMedico()));
						
			if(segnalato.getTbCondLavoro()!=null)
				dati.setCondLavoro(segnalato.getTbCondLavoro().getDescrizione());
			if(segnalato.getTbSettoreImpiego()!=null)
				dati.setSettoreImpiego(segnalato.getTbSettoreImpiego().getDescrizione());
			if(segnalato.getTbProfessione()!=null)
				dati.setProfessione(segnalato.getTbProfessione().getDescrizione());
			if(segnalato.getTbTitoloStudio()!=null)
				dati.setTitoloStudio(segnalato.getTbTitoloStudio().getDescrizione());
		
			//SISO-947: riferimento
			dati.setLstRiferimenti(fillRiferimento(s));	
			
			// motivo
			String temp = "";
			if(s.getMotivazione()!=null){
			List<SsMotivazioniSchede> motivazioni = dao.readMotivazioniScheda(s.getMotivazione());
			for (SsMotivazioniSchede m : motivazioni)
				temp += m.getMotivazione().getMotivo() + "\n";
			}
			dati.setMotivi(temp);
			dati.setMotivoAltro(format(s.getMotivazione().getAltro()));
			
			
			// servizi
			temp = "";
			if (s.getInterventi() != null) {
				List<SsInterventiSchede> interventi = dao.readInterventiScheda(s.getInterventi());
				for (SsInterventiSchede i : interventi)
					temp += i.getIntervento().getIntervento() + "\n";
			}
			dati.setInterventi(temp);
			dati.setInterventoAltro(format(s.getInterventi().getAltro()));


			// tipo
			SsTipoScheda tipo = confdao.readTipoSchedaById(s.getTipo());
			dati.setTipoScheda(format(tipo!=null ? tipo.getTipo() : null));
	
		}
		
		return dati;
		
	}

}
