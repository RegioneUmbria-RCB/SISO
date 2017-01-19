package it.webred.ss.ejb;

import it.webred.ss.dao.SsSchedaDAO;
import it.webred.ss.data.model.SsAnagrafica;
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
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsRelUffPcontOrgPK;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.data.model.SsSchedaInterventi;
import it.webred.ss.data.model.SsSchedaMotivazione;
import it.webred.ss.data.model.SsSchedaPrivacy;
import it.webred.ss.data.model.SsSchedaPrivacyPK;
import it.webred.ss.data.model.SsSchedaRiferimento;
import it.webred.ss.data.model.SsSchedaSegnalante;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.data.model.SsTipoScheda;
import it.webred.ss.data.model.SsUfficio;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.ejb.dto.DatiSchedaListDTO;
import it.webred.ss.ejb.dto.OperatoreDTO;
import it.webred.ss.ejb.dto.SsSearchCriteria;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class SsSchedaSessionBean
 */
@Stateless
public class SsSchedaSessionBean implements SsSchedaSessionBeanRemote {

	@Autowired
	private SsSchedaDAO dao;
	
	protected static Logger logger = Logger.getLogger("segretariatosoc.log");
	
	@Override
	public void saveConsensoPrivacy(BaseDTO dto) {
		SsSchedaPrivacy privacy = (SsSchedaPrivacy)dto.getObj();
		privacy.setUserIns(dto.getUserId());
		if(privacy.getDtIns()==null)
			privacy.setDtIns(new Date());
		dao.saveSchedaPrivacy(privacy);
	}
	
	@Override
	public Long saveAccesso(BaseDTO dto) {
		return dao.saveAccesso((SsSchedaAccesso) dto.getObj()).getId(); 
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
		dao.updateCompletamentoScheda((Long)parameters[0], (Boolean)parameters[1], (Long)parameters[2]);
	}


	@Override
	public void updateAccesso(BaseDTO dto) {
		SsSchedaAccesso accesso = (SsSchedaAccesso) dto.getObj();
		dao.saveAccesso(accesso);
	}

	@Override
	public void updateSegnalante(BaseDTO dto) {
		SsSchedaSegnalante segnalante = (SsSchedaSegnalante) dto.getObj();
		dao.saveSegnalante(segnalante);
	}
	
	@Override
	public void updateSegnalato(BaseDTO dto) {
		SsSchedaSegnalato s = (SsSchedaSegnalato) dto.getObj();
		dao.saveSegnalato(s);
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
	public List<SsUfficio> readUffici(BaseDTO dto) {
		return dao.readUffici();
	}

	@Override
	public SsUfficio readUfficio(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		return dao.readUfficio(id);
	}
	
	public List<SsRelUffPcontOrg> readUffPcontByOrganizzazione(BaseDTO dto){
		return dao.readUffPcontByOrganizzazione(new Long(dto.getOrganizzazione()));
	}
	
	public SsRelUffPcontOrg getSsRelUffPcontOrg(BaseDTO dto){
		return dao.getSsRelUffPcontOrg((SsRelUffPcontOrgPK)dto.getObj());
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
		return dao.readInterventi();
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
	public HashMap<SsOOrganizzazione,Integer> countAnagraficaInSsComplete(BaseDTO dto) {
		String cf = (String)dto.getObj();
		HashMap<Long,Integer> mapIn =  dao.countAnagraficaInSs(cf, true);
		return mappaOrganizzazioneNumSchede(mapIn, cf);
	}
	
	@Override
	public HashMap<SsOOrganizzazione,Integer> countAnagraficaInSsInComplete(BaseDTO dto) {
		String cf = (String)dto.getObj();
		HashMap<Long,Integer> mapIn =  dao.countAnagraficaInSs(cf, false);
		return mappaOrganizzazioneNumSchede(mapIn, cf);
	}
	
	private HashMap<SsOOrganizzazione,Integer> mappaOrganizzazioneNumSchede(HashMap<Long,Integer> mapIn, String cf){
		HashMap<SsOOrganizzazione,Integer> mappa = new HashMap<SsOOrganizzazione,Integer> ();
		Iterator<Long> iter = (Iterator<Long>)mapIn.keySet().iterator();
		while(iter.hasNext()){
			Long idOrg = (Long)iter.next();
			SsOOrganizzazione o = dao.findOrganizzazione(idOrg);
			if(o==null){
				logger.warn("Trovate "+mapIn.get(idOrg)+" schede UDC per il soggetto "+cf+" che non sono associate ad alcuna organizzazione!");
				o = new SsOOrganizzazione();
				o.setId(idOrg);
				o.setNome("Ente non definito");
			}
			mappa.put(o, mapIn.get(idOrg));
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
	public void updateIndirizzo(BaseDTO dto) {
		SsIndirizzo indirizzo = (SsIndirizzo)dto.getObj();
		dao.updateIndirizzo(indirizzo.getId(), indirizzo.getVia(), indirizzo.getComune());
	}
	
	@Override
	public List<SsDiario> readDiarioSociale(BaseDTO dto) {
		SsAnagrafica anagrafica = (SsAnagrafica)dto.getObj();
		return dao.readDiarioSociale(anagrafica);
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
	public List<SsTipoScheda> readTipiScheda(BaseDTO dto) {
		return dao.readTipiScheda();
	}

	@Override
	public SsTipoScheda readTipoSchedaByTipo(BaseDTO dto) {
		String tipo = (String)dto.getObj();
		return dao.readTipoSchedaByTipo(tipo);
	}

	@Override
	public List<SsAnagrafica> readAnagraficheByCf(BaseDTO dto) {
		String cf = (String) dto.getObj();
		return dao.readAnagraficheByCf(cf);
	}

	@Override
	public SsTipoScheda readTipoSchedaById(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		return dao.readTipoSchedaById(id);
	}

	@Override
	public boolean esistonoPContatto(BaseDTO dto) {
		try{
			Long id = (Long)dto.getObj();
			List<SsRelUffPcontOrg> lst = dao.readUffPcontByOrganizzazione(id);
			return lst!=null && lst.size()>0;
		}catch(Exception e){
			logger.error("esistonoPContatto",e);
		}
		return false;
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
	public List<OperatoreDTO> getListaOperatori(BaseDTO dto){
		return dao.getListaOperatori(dto.getOrganizzazione(), (Long)dto.getObj());
	}

	@Override
	public List<SsScheda> readSchedeByCF(BaseDTO dto) {
		return dao.readSchedeByCF((String)dto.getObj());
	}

	@Override
	public SsSchedaPrivacy findSchedaPrivacyById(BaseDTO dto) {
		return dao.findSchedaPrivacy((SsSchedaPrivacyPK)dto.getObj());
	}

}
