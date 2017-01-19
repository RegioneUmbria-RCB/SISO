package it.webred.ss.dao;

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
import it.webred.ss.ejb.dto.DatiSchedaListDTO;
import it.webred.ss.ejb.dto.OperatoreDTO;
import it.webred.ss.ejb.dto.SsSearchCriteria;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

@Named
public class SsSchedaDAO implements Serializable {
private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="SegretariatoSoc_DataModel")
	protected EntityManager em;
	protected static Logger logger = Logger.getLogger("segretariatosoc.log");
	
	public SsSchedaAccesso saveAccesso(SsSchedaAccesso accesso){
		return em.merge(accesso);
	}
	
	public SsSchedaSegnalante saveSegnalante(SsSchedaSegnalante segnalante){
		return em.merge(segnalante);
	}
	
	public SsSchedaRiferimento saveRiferimento(SsSchedaRiferimento riferimento){
		return em.merge(riferimento);
	}
	
	public SsSchedaMotivazione saveMotivazione(SsSchedaMotivazione motivazione){
		return em.merge(motivazione);
	}
	
	public SsSchedaInterventi saveServizi(SsSchedaInterventi servizi){
		return em.merge(servizi);
	}
	
	public SsScheda saveScheda(SsScheda ssScheda){
		if(ssScheda.getIdentificativo()==null || ssScheda.getIdentificativo()==0){
			String sql =" SELECT SQ_CODICE_UDC.nextval FROM DUAL";
			Query q = em.createNativeQuery(sql);
			Long ide = ((BigDecimal)q.getSingleResult()).longValue();
			logger.debug("Assegnazione identificativo "+ide);
			ssScheda.setIdentificativo(ide);
		}
		return em.merge(ssScheda);
	}

	public SsDiario writeNotaDiario(SsDiario nota) {
		return em.merge(nota);
	}
	
	public void updateCompletamentoScheda(Long id, Boolean completa, Long tipo){
		SsScheda scheda = readScheda(id);
		scheda.setCompleta(completa);
		scheda.setTipo(tipo);
		scheda.setDtMod(new Date());
		em.merge(scheda);
	}
	
	public void updateMotivazione(Long id, String altro){
		SsSchedaMotivazione motivazione = em.find(SsSchedaMotivazione.class, id);
		motivazione.setAltro(altro);
		em.merge(motivazione);
	}
	
	public void updateInterventi(Long id, String altro){
		SsSchedaInterventi interventi = em.find(SsSchedaInterventi.class, id);
		interventi.setAltro(altro);
		em.merge(interventi);
	}
	
	public void updateNotaDiario(Long id, String nota) {
		SsDiario diario = em.find(SsDiario.class, id);
		diario.setNota(nota);
		em.merge(diario);
	}
	
	public void updateIndirizzo(Long id, String via, String comune) {
		SsIndirizzo indirizzo = em.find(SsIndirizzo.class, id);
		indirizzo.setVia(via);
		indirizzo.setComune(comune);
		em.merge(indirizzo);
	}
	
	public SsScheda readScheda(Long id){
		SsScheda scheda = em.find(SsScheda.class, id);
		return scheda;
	}
	
	@SuppressWarnings("unchecked")
	public List<SsScheda> readSchedeIn(List<Long> idList){
		Query q = em.createNamedQuery("SsScheda.readSchedeIn")
				.setParameter("idList", idList);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<SsUfficio> readUffici() {
		Query q = em.createNamedQuery("SsUfficio.findAll");
		return q.getResultList();
	}

	public SsUfficio readUfficio(Long id) {
		Query q = em.createNamedQuery("SsScheda.readUfficio")
				.setParameter("id", id);
		return (SsUfficio)q.getResultList().get(0);
	}

	public void eliminaScheda(Long id) {
		SsScheda scheda = readScheda(id);
		scheda.setEliminata(true);
		scheda.setDtMod(new Date());
		em.merge(scheda);
	}

	@SuppressWarnings("unchecked")
	public List<SsIntervento> readInterventi() {
		Query q = em.createNamedQuery("SsIntervento.findAll");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<SsMotivazione> readMotivazioni() {
		Query q = em.createNamedQuery("SsMotivazione.findAll");
		return q.getResultList();
	}

	public SsMotivazione readMotivazione(Long id) {
		Query q = em.createNamedQuery("SsScheda.readMotivazione")
				.setParameter("id", id);
		return (SsMotivazione)q.getResultList().get(0);
	}
	
	public SsIntervento readIntervento(Long id) {
		Query q = em.createNamedQuery("SsScheda.readIntervento")
				.setParameter("id", id);
		return (SsIntervento)q.getResultList().get(0);
	}

	public void writeMotivazioneScheda(SsMotivazioniSchede motivo) {
		em.merge(motivo);
	}
	
	public void writeInterventoScheda(SsInterventiSchede intervento) {
		em.merge(intervento);
	}
	
	public SsIndirizzo writeIndirizzo(SsIndirizzo indirizzo) {
		return em.merge(indirizzo);
	}

	public void deleteMotivazioniScheda(SsSchedaMotivazione scheda) {
		Query q = em.createNamedQuery("SsScheda.deleteMotivazioniScheda")
				.setParameter("scheda", scheda);
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<SsMotivazioniSchede> readMotivazioniScheda(SsSchedaMotivazione scheda) {
		Query q = em.createNamedQuery("SsScheda.readMotivazioniScheda")
				.setParameter("scheda", scheda);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> readMotivazioniSchedaById(Long id) {
		SsMotivazione idM = new SsMotivazione();
		idM.setId(id);
		Query q = em.createNamedQuery("SsScheda.readMotivazioniSchedaById").setParameter("idM", idM);
				
		return q.getResultList();
	}
	

	@SuppressWarnings("unchecked")
	public List<SsInterventiSchede> readInterventiScheda(SsSchedaInterventi scheda) {
		Query q = em.createNamedQuery("SsScheda.readInterventiScheda")
				.setParameter("scheda", scheda);
		return q.getResultList();
	}

	public void deleteInterventiScheda(SsSchedaInterventi scheda) {
		Query q = em.createNamedQuery("SsScheda.deleteInterventiScheda")
				.setParameter("scheda", scheda);
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public SsAnagrafica readAnagraficaByCf(String cf) {
		Query q = em.createNamedQuery("SsScheda.readAnagraficaByCf")
				.setParameter("cf", cf);
		List<SsAnagrafica> results = (List<SsAnagrafica>)q.getResultList();
		if(results != null && !results.isEmpty())
			return (SsAnagrafica) q.getResultList().get(0);
		
		return null;
	}

	@SuppressWarnings("unchecked")
	//ID ORGANIZZAZIONE, NUM.SCHEDE
	public HashMap<Long,Integer> countAnagraficaInSs(String cf, boolean completa) {
		HashMap<Long,Integer> mappaSchede = new HashMap<Long,Integer>();
		Query q = em.createNamedQuery("SsScheda.readIdSegnalatoByCf");
		q.setParameter("cf", cf);
		List<Long> segnalati = (List<Long>)q.getResultList();
		if(segnalati == null || segnalati.isEmpty())
			return mappaSchede;
		
		for(Long segnalato: segnalati){
			q = em.createNamedQuery("SsScheda.readSchedaBySegnalatoId")
					.setParameter("segnalato", segnalato);
			List<SsScheda> schede = (List<SsScheda>)q.getResultList();
			if(schede != null && !schede.isEmpty()){
				for(SsScheda s : schede){
					Long idOrganizzazione = s.getAccesso()!=null ? s.getAccesso().getSsRelUffPcontOrg().getSsOOrganizzazione().getId() : -1;
					int numSchede = mappaSchede.get(idOrganizzazione)!=null ? mappaSchede.get(idOrganizzazione) : 0;
					if(!s.getEsterna()){
						if(completa && s.getCompleta())
							numSchede++;
						else if(!completa && !s.getCompleta())
							numSchede++;
					}
					
					if(numSchede>0)
						mappaSchede.put(idOrganizzazione, numSchede);
				}
			}
		}
	
		return mappaSchede;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean isAnagraficaEsterna(String cf) {
		Query q = em.createNamedQuery("SsScheda.readIdSegnalatoByCf")
				.setParameter("cf", cf);
		List<Long> segnalati = (List<Long>)q.getResultList();
		if(segnalati == null || segnalati.isEmpty())
			return false;
		
		for(Long segnalato: segnalati){
			q = em.createNamedQuery("SsScheda.readSchedaCompletaBySegnalatoId")
					.setParameter("segnalato", segnalato);
			List<SsScheda> schede = (List<SsScheda>)q.getResultList();
			if(schede != null && !schede.isEmpty()){
				for(SsScheda s: schede)
					if(s.getEsterna())
						return true;
			}
		}
		
		return false;
	}

	public SsAnagrafica writeAnagrafica(SsAnagrafica anagrafica) {
		return em.merge(anagrafica);
	}

	public SsSchedaSegnalato saveSegnalato(SsSchedaSegnalato segnalato) {
		return em.merge(segnalato);
	}

	public void updateAnagraficaRef(Long id, SsAnagrafica anagrafica) {
		SsSchedaSegnalato segnalato = em.find(SsSchedaSegnalato.class, id);
		segnalato.setAnagrafica(anagrafica);
	}

	public void updateSegnalatoRef(SsScheda scheda, Long segnalato) {
		scheda.setSegnalato(segnalato);
		em.merge(scheda);
	}

	@SuppressWarnings("unchecked")
	public SsAnagrafica readAnagraficaById(Long id) {
		Query q = em.createNamedQuery("SsScheda.readAnagraficaById")
				.setParameter("id", id);
		List<SsAnagrafica> results = (List<SsAnagrafica>)q.getResultList();
		if(results != null && !results.isEmpty())
			return results.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<SsSchedaSegnalato> readSegnalati() {
		Query q = em.createNamedQuery("SsScheda.readSegnalati");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<SsScheda> readSchede() {
		Query q = em.createNamedQuery("SsScheda.readSchedeWithSegnalato");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public SsSchedaSegnalato readSegnalatoById(Long id) {
		Query q = em.createNamedQuery("SsScheda.readSegnalatoById")
				.setParameter("id", id);
		List<SsSchedaSegnalato> results = (List<SsSchedaSegnalato>)q.getResultList();
		if(results != null && !results.isEmpty())
			return results.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<SsDiario> readDiarioSociale(SsAnagrafica anagrafica) {
		Query q = em.createNamedQuery("SsScheda.readDiarioSociale")
				.setParameter("anagrafica", anagrafica);
		return (List<SsDiario>)q.getResultList();
	}



	@SuppressWarnings("unchecked")
	public List<SsInterventoEconomico> readInterventiEconomici(SsAnagrafica anagrafica) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		Query q = em.createNamedQuery("SsScheda.readInterventoEconomicoByAnagrafica")
				.setParameter("anagrafica", anagrafica)
				.setParameter("data", calendar.getTime());
		return (List<SsInterventoEconomico>)q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<SsInterventoEconomicoTipo> readInterventiEconomiciTipi() {
		Query q = em.createNamedQuery("SsInterventoEconomicoTipo.findAll");
		return (List<SsInterventoEconomicoTipo>)q.getResultList();
	}

	public void writeInterventoEconomico(SsInterventoEconomico intervento) {
		em.merge(intervento);
	}

	public SsInterventoEconomicoTipo readInterventoEconomicoTipoByTipo(String tipo) {
		Query q = em.createNamedQuery("SsScheda.readInterventoEconomicoTipoByTipo")
				.setParameter("tipo", tipo);
		return (SsInterventoEconomicoTipo)q.getResultList().get(0);
	}

	public boolean isSchedaCompleta(Long id) {
		SsScheda scheda = this.readScheda(id);
		if(scheda!=null)
			return scheda.getCompleta();
		else 
			return false;
	}

	@SuppressWarnings("unchecked")
	public List<SsTipoScheda> readTipiScheda() {
		Query q = em.createNamedQuery("SsTipoScheda.findAll");
		return (List<SsTipoScheda>)q.getResultList();
	}

	public SsTipoScheda readTipoSchedaByTipo(String tipo) {
		Query q = em.createNamedQuery("SsScheda.readTipoSchedaByTipo")
				.setParameter("tipo", tipo);
		return (SsTipoScheda)q.getResultList().get(0);
	}

	@SuppressWarnings("unchecked")
	public List<SsAnagrafica> readAnagraficheByCf(String cf) {
		Query q = em.createNamedQuery("SsScheda.readAnagraficaByCf")
				.setParameter("cf", cf);
		return (List<SsAnagrafica>)q.getResultList();
	}

	public SsTipoScheda readTipoSchedaById(Long id) {
		Query q = em.createNamedQuery("SsScheda.readTipoSchedaById")
				.setParameter("id", id);
		if(!q.getResultList().isEmpty())
			return (SsTipoScheda)q.getResultList().get(0);
		else
			return null;
	}

	public List<SsRelUffPcontOrg> readUffPcontByOrganizzazione(Long id) {
		Query q = em.createNamedQuery("SsRelUffPcontOrg.listaByOrganizzazione")
				.setParameter("organizzazione", id);
		return q.getResultList();
	}

	public SsRelUffPcontOrg getSsRelUffPcontOrg(SsRelUffPcontOrgPK id) {
		return em.find(SsRelUffPcontOrg.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<SsClassificazioneMotivazione> readClassificazioneMotivazione() {
		Query q = em.createNamedQuery("SsClassificazioneMotivazione.findAll");
		return q.getResultList();
	}

	public List<SsMotivazione> readMotivazioniByClasId(Long id) {
		Query q = em.createNamedQuery("SsMotivazione.motivazioniByIdClassificazioni")
				.setParameter("classificazioneId", id);
		return q.getResultList();
		
	}

	@SuppressWarnings("unchecked")
	public SsSchedaSegnalante readSegnalanteById(Long id) {
		Query q = em.createNamedQuery("SsScheda.readSegnalanteById")
				.setParameter("id", id);
		List<SsSchedaSegnalante> results = (List<SsSchedaSegnalante>)q.getResultList();
		if(results != null && !results.isEmpty())
			return results.get(0);
		
		return null;
	}

 
	public List<SsSchedaSegnalato> readSchedeSegnalatoByDenom(String cognome, String nome) {
		Query q = em.createNamedQuery("SsSchedaSegnalato.readSchedeByDenominazione");
		q.setParameter("nome", nome!=null ? "%"+nome.toUpperCase()+"%" : "%");
		q.setParameter("cognome", cognome!=null ? "%"+cognome.toUpperCase()+"%" : "%");
		return q.getResultList();
	}

	public List<SsSchedaSegnalato> readSchedeSegnalatoByCF(String cf) {
		Query q = em.createNamedQuery("SsSchedaSegnalato.readSchedeSegnalatoByCF");
		q.setParameter("codfiscale", cf!=null ? cf.toUpperCase() : cf);
		return q.getResultList();
	}
	
	public List<SsScheda> readSchedeByCF(String cf) {
		Query q = em.createNamedQuery("SsScheda.readSchedeByCF");
		q.setParameter("codfiscale", cf!=null ? cf.toUpperCase() : cf);
		return q.getResultList();
	}

/*	SOSTITUITE CON IL METODO SEARCH --> QUERY DINAMICA IN BASE AI PARAMETRI PASSATI
 * 
 * public List<SsScheda> searchSchedeInUfficio(SsSearchCriteria dto, boolean isCount) {
		logger.debug("searchSchedeInUfficio - param: ufficioId["+dto.getUfficioId()+"],enteId["+dto.getOrganizzazioneId()+"]");
		Query q = em.createNamedQuery("SsScheda.readSchedeInUfficio")
				.setParameter("ufficio", dto.getUfficioId())
				.setParameter("ente", dto.getOrganizzazioneId());
		
		if(!isCount){	
			if(dto.getFirst() != null)
				q.setFirstResult(dto.getFirst());
			if(dto.getPageSize() != null)
				q.setMaxResults(dto.getPageSize());
		}
		
		List<SsScheda> lst = q.getResultList();
		
		logger.debug("searchSchedeInUfficio - result size["+lst.size()+"]");
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<SsScheda> readSchedeSoggetto(Long anagraficaID) {
		List<SsScheda> schede = new ArrayList<SsScheda>();
		
		Query q = em.createNamedQuery("SsScheda.readAnagraficaById")
				.setParameter("id", anagraficaID);
		
		List<SsAnagrafica> results = (List<SsAnagrafica>)q.getResultList();
		
		if(results!=null && !results.isEmpty()){
			SsAnagrafica ana = results.get(0);
			q = em.createNamedQuery("SsScheda.readSegnalatoByAnagrafica")
					.setParameter("anagrafica", ana);
			
			List<SsSchedaSegnalato> segnalati = (List<SsSchedaSegnalato>)q.getResultList();
			if(segnalati!= null && !segnalati.isEmpty()){
				for(SsSchedaSegnalato segnalato: segnalati){
					q = em.createNamedQuery("SsScheda.readSchedaCompletaBySegnalatoId")
							.setParameter("segnalato", segnalato.getId());
					
					List<SsScheda> s = (List<SsScheda>)q.getResultList();
					if(s!=null && !s.isEmpty())
						schede.add(s.get(0));
				}
			}
		}
		return schede;
	}*/
	
	public Long countSchedeUDC(SsSearchCriteria criteria){
		String sql = new SsQueryBuilder(criteria).createQueryLista(true);
		logger.debug("count SQL LISTA SCHEDE UDC[" + sql+"]");
		
		Query q = em.createNativeQuery(sql);
		BigDecimal bd = (BigDecimal)q.getSingleResult();
		return bd.longValue();
	}
	
	public List<DatiSchedaListDTO> searchSchedeUDC(SsSearchCriteria criteria){
		List<DatiSchedaListDTO> lstout = new ArrayList<DatiSchedaListDTO>();
		String sql = new SsQueryBuilder(criteria).createQueryLista(false);
		logger.debug("SQL LISTA SCHEDE UDC[" + sql+"]");
		
		Query q = em.createNativeQuery(sql);
		if(criteria.getFirst() != null)
			q.setFirstResult(criteria.getFirst());
		if(criteria.getPageSize() != null)
			q.setMaxResults(criteria.getPageSize());
		
		List<Object[]> lst = q.getResultList();
		
		for (Object[] o : lst){
			DatiSchedaListDTO dto = new DatiSchedaListDTO();
			dto.setId(((BigDecimal)o[0]).longValue());
			dto.setDataAccesso((Date)o[1]);
			dto.setUfficioId(((BigDecimal)o[2]).longValue());
			dto.setUfficio((String)o[3]);
			dto.setpContattoId(((BigDecimal)o[4]).longValue());
			dto.setpContatto((String)o[5]);
			dto.setCognomeUtente((String)o[6]);
			dto.setNomeUtente((String)o[7]);
			dto.setDataNascita((Date)o[8]);
			dto.setOperatore((String)o[9]);
			dto.setTipo((String)o[10]);
			dto.setDataModifica((Date)o[11]);
			dto.setDataPrivacy((Date)o[12]);
			dto.setIdentificativo((BigDecimal)o[13]);
			lstout.add(dto);
		}
		
		logger.debug("searchSchedeUDC - result size["+lst.size()+"]");
		return lstout;
		
	}
	
	public List<OperatoreDTO> getListaOperatori(Long organizzazioneId, Long ufficioId){
		List<OperatoreDTO> operatori = new ArrayList<OperatoreDTO>();
		
		String sql = "Select distinct operatore, cognome, nome  "+
					 "from SS_OPERATORE_ANAGRAFICA "+
				     "where 1=1 ";
		sql+=organizzazioneId!=null ?  " AND ORG_ID="+organizzazioneId : "";
		sql+=ufficioId!=null ? " AND UFFICIO_ID="+ufficioId : "";
		sql+="order by cognome, nome, operatore";
		
		Query q = em.createNativeQuery(sql);
		List<Object[]> lst = q.getResultList();
		
		for (Object[] o : lst){
			OperatoreDTO oper = new OperatoreDTO();
			oper.setUsername((String)o[0]);
			oper.setCognome((String)o[1]);
			oper.setNome((String)o[2]);
			operatori.add(oper);
		}
		
		return operatori;
	}

	public void saveSchedaPrivacy(SsSchedaPrivacy privacy) {
		em.merge(privacy);
	}

	public SsSchedaPrivacy findSchedaPrivacy(SsSchedaPrivacyPK pk) {
		return em.find(SsSchedaPrivacy.class, pk);
	}
	
	public SsOOrganizzazione findOrganizzazione(Long id){
		if(id>=0)
			return em.find(SsOOrganizzazione.class, id);
		else
			return null;
	}
	
}
