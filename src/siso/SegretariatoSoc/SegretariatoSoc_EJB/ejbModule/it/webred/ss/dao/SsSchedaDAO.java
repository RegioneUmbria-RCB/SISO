package it.webred.ss.dao;

import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;
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
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsRelUffPcontOrgPK;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccessoInviante;
import it.webred.ss.data.model.SsSchedaInterventi;
import it.webred.ss.data.model.SsSchedaMotivazione;
import it.webred.ss.data.model.SsSchedaRiferimento;
import it.webred.ss.data.model.SsSchedaSegnalante;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.data.model.SsTipoScheda;
import it.webred.ss.data.model.SsUfficio;
import it.webred.ss.data.model.privacy.CsSsPrivacy;
import it.webred.ss.data.model.privacy.RdcConsensiSocToLav;
import it.webred.ss.data.model.privacy.RdcConsensiSocToLavPK;
import it.webred.ss.data.model.privacy.SsSchedaPrivacy;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.ejb.dto.DatiSchedaListDTO;
import it.webred.ss.ejb.dto.OperatoreDTO;
import it.webred.ss.ejb.dto.OrganizzazioneDTO;
import it.webred.ss.ejb.dto.SsSearchCriteria;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

@Named
public class SsSchedaDAO implements Serializable {
private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="SegretariatoSoc_DataModel")
	protected EntityManager em;
	protected static Logger logger = Logger.getLogger("segretariatosoc.log");

	public List<BigDecimal> readBelfioreDb(String codIstatComune) {
		Query q = em.createNativeQuery("SELECT count(*) FROM CS_O_ORGANIZZAZIONE a, AM_TAB_COMUNI b WHERE a.COD_CATASTALE IS NOT NULL AND a.COD_CATASTALE = b.cod_nazionale AND b.cod_istat_comune = :codice");
		q.setParameter("codice", codIstatComune);	
		List<BigDecimal> risultato = q.getResultList();
		return risultato;
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
	
//	public void updateCompletamentoScheda(Long id, Boolean completa, Long tipo){
//		SsScheda scheda = readScheda(id);
//		scheda.setCompleta(completa);
//		scheda.setTipo(tipo);
//		scheda.setDtMod(new Date());
//		em.merge(scheda);
//	}
//	public void updateCompletamentoScheda(Long id, Boolean completa, Long tipo, Long categoriaId)throws Exception{
//		SsScheda scheda = readScheda(id);
//		scheda.setCompleta(completa);
//		scheda.setTipo(tipo);
//		scheda.setDtMod(new Date());
//		//em.merge(scheda);
//		
//		if(categoriaId!=null &&  categoriaId!= 0){
//		
//	 		logger.debug("INDIVIDUATA CATEGORIA CON ID " + categoriaId + " PER SCHEDA CON ID" + id );
//   			//  SsCCategoriaSociale ssCatSoc = new SsCCategoriaSociale();
//   			//  ssCatSoc.setId(categoriaId);
//   			
//			SsCCategoriaSociale ssCatSoc = readCategoriaSociale(categoriaId);
// 			//List<SsCCategoriaSociale> listSSCatSoc = new ArrayList<SsCCategoriaSociale>();
// 			//listSSCatSoc.add(ssCatSoc);
// 		 	scheda.setSsCategorieSociali(ssCatSoc);
// 			//scheda.setSsCategorieSociali(new SsCCategoriaSociale());
// 			//scheda.getSsCategorieSociali().setId(categoriaId);
// 			//em.persist(scheda);
// 			//add the new children list created above to the existing list
// 			//scheda.getSsCategorieSociali().(ssCatSoc);
//		}
//		else{
//			scheda.setSsCategorieSociali(null);
//			
//		}
//		em.merge(scheda);
//		em.flush();
//	}	
	public void updateCompletamentoScheda(Long id, Boolean completa, Long tipo, Long categoriaId)throws Exception{
		 
		
		if(categoriaId!=null &&  categoriaId!= 0){
		
	 		logger.debug("INDIVIDUATA CATEGORIA CON ID " + categoriaId + " PER SCHEDA CON ID" + id );
	 		readCategoriaSocialeAndUpdateScheda( categoriaId,   id,  completa,  tipo);
		}
		else{
			SsScheda scheda = readScheda(id);
			scheda.setCompleta(completa);
			scheda.setTipo(tipo);
			scheda.setDtMod(new Date());
			em.merge(scheda);
		}
	 
	}
public void updateCompletamentoScheda(SsScheda  scheda)throws Exception{
		 em.merge(scheda); 
		 em.flush();
		
//		if(categoriaId!=null &&  categoriaId!= 0){
//
//			 readCategoriaSocialeAndUpdateScheda(scheda);
//		}
//		else{
//		 	em.merge(scheda);
//		}
	 
	}
	public SsCCategoriaSociale readCategoriaSociale(Long id){
		SsCCategoriaSociale categoriaSociale = em.find(SsCCategoriaSociale.class, id);
		return categoriaSociale;
	}
//	public void readCategoriaSocialeAndUpdateScheda(SsScheda scheda ){
//		 
//		//SsCCategoriaSociale categoriaSociale = em.find(SsCCategoriaSociale.class, id);
//		
//		 em.merge(scheda); 
//		 em.flush();
//		 
//	}
	
	public void readCategoriaSocialeAndUpdateScheda(Long id, Long idScheda, Boolean completa, Long tipo){
		 
		//SsCCategoriaSociale categoriaSociale = em.find(SsCCategoriaSociale.class, id);
		 SsScheda scheda = em.find(SsScheda.class, idScheda);
		SsCCategoriaSociale categoriaSociale = new SsCCategoriaSociale();
		 categoriaSociale.setId(id);
		 List<SsCCategoriaSociale> listSSCatSoc = new ArrayList<SsCCategoriaSociale>();
		 listSSCatSoc.add(categoriaSociale);
		 
//		if(scheda.getLSsCategorieSociali() == null || scheda.getLSsCategorieSociali().size() == 0){
//			scheda.setLSsCategorieSociali(new ArrayList<SsCCategoriaSociale>());
//			SsScheda  schedaMod =  em.merge(scheda);
//			schedaMod.getLSsCategorieSociali().clear();
//			schedaMod.getLSsCategorieSociali().add(categoriaSociale);
//			em.merge(schedaMod); 
//			
//		}
//		else{
			scheda.getLstCategorieSociali().add(categoriaSociale);
			scheda.setCompleta(completa);
			scheda.setTipo(tipo);
			em.merge(scheda); 
		//}
	}
	
	public SsScheda cleanCategoriaSocialeScheda( Long idScheda){
		 
		  SsScheda scheda = em.find(SsScheda.class, idScheda);
		
		 List<SsCCategoriaSociale> listSSCatSoc = new ArrayList<SsCCategoriaSociale>();
		  
		  scheda.getLstCategorieSociali().clear();
		  SsScheda ssScheda =	em.merge(scheda); 
			return ssScheda;
		 
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
	
	public SsScheda readScheda(Long id){
		SsScheda scheda = em.find(SsScheda.class, id);
		return scheda;
	}

	@SuppressWarnings("unchecked")
	public List<SsScheda> readSchedeIn(List<Long> idList){
		Query q = em.createNamedQuery("SsScheda.readSchedeIn").setParameter("idList", idList);
		return q.getResultList();
	}

	public void eliminaScheda(Long id) {
		SsScheda scheda = readScheda(id);
		scheda.setEliminata(true);
		scheda.setDtMod(new Date());
		em.merge(scheda);
	}
	@SuppressWarnings("unchecked")
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<SsIntervento> readInterventi() {
		Query q = em.createNamedQuery("SsIntervento.findAll");
		return q.getResultList();
	}

	
	//SISO-1160 Inizio
	@SuppressWarnings("unchecked")
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<String> readInterventiTrascodifiche() {
		List<SsIntervento> ssIntervento = readInterventi();
		List<String> listDescrizioni = new ArrayList<String>();
		for(SsIntervento ssInter : ssIntervento){
			listDescrizioni.add(ssInter.getIntervento());
		}
		return listDescrizioni;
	}
	//SISO-1160 Fine
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
	
	public void deleteNotaDiario(Long idNota) {
		Query q = em.createNamedQuery("SsDiario.deleteById");
		q.setParameter("id", idNota);
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
	public HashMap<String,Integer> countAnagraficaInSs(String cf, boolean completa) {
		HashMap<String,Integer> mappaSchede = new HashMap<String,Integer>();
		String alias ="";
		Query q =null;
		//discrimina per Cf o Cf_alias 
		String[] arrCfAlias = cf!=null ? cf.split("_") : null;	  
		    if (arrCfAlias != null && arrCfAlias.length>1){
		    	cf = arrCfAlias[0];
		    	alias = arrCfAlias[1];
		    	q = em.createNamedQuery("SsScheda.readIdSegnalatoByAlias");
				q.setParameter("cf", cf);
				q.setParameter("alias", alias);
		    }else{
		    	q = em.createNamedQuery("SsScheda.readIdSegnalatoByCf");
				q.setParameter("cf", cf);
		    }
		
		List<Long> segnalati = (List<Long>)q.getResultList();
		if(segnalati == null || segnalati.isEmpty())
			return mappaSchede;
		
		for(Long segnalato: segnalati){
			q = em.createNamedQuery("SsScheda.readSchedaBySegnalatoId").setParameter("segnalato", segnalato);
			List<SsScheda> schede = (List<SsScheda>)q.getResultList();
			if(schede != null && !schede.isEmpty()){
				for(SsScheda s : schede){
					Long idOrganizzazione = s.getAccesso()!=null ? s.getAccesso().getSsRelUffPcontOrg().getSsOOrganizzazione().getId() : -1;
					Long idUfficio = s.getAccesso()!=null ? s.getAccesso().getSsRelUffPcontOrg().getSsUfficio().getId() : -1;
					
					String key = idOrganizzazione+"@"+idUfficio;
					
					int numSchede = mappaSchede.get(key)!=null ? mappaSchede.get(key) : 0;
					if(!s.getEsterna()){
						if(completa && s.getCompleta())
							numSchede++;
						else if(!completa && !s.getCompleta())
							numSchede++;
					}
					
					if(numSchede>0)
						mappaSchede.put(key, numSchede);
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
		Query q = em.createNamedQuery("SsScheda.readSegnalatoById");
		q.setParameter("id", id);
		List<SsSchedaSegnalato> results = (List<SsSchedaSegnalato>)q.getResultList();
		if(results != null && !results.isEmpty())
			return results.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<SsDiario> readDiarioSociale(SsAnagrafica anagrafica, Long organizzazioneID) {
		Query q = em.createNamedQuery("SsScheda.readDiarioSociale");
		q.setParameter("anagrafica", anagrafica);
		q.setParameter("organizzazioneID", organizzazioneID);
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
	public List<SsAnagrafica> readAnagraficheByCf(String cf) {
		Query q = em.createNamedQuery("SsScheda.readAnagraficaByCf")
				.setParameter("cf", cf);
		return (List<SsAnagrafica>)q.getResultList();
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
	
	public List<SsScheda> readSchedeByUfficioCF(String cf, Long idUfficio) {
		Query q = em.createNamedQuery("SsScheda.readSchedeByCFUfficio");
		q.setParameter("codfiscale", cf!=null ? cf.toUpperCase() : cf);
		q.setParameter("ufficioId", idUfficio);
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
			dto.setUfficioId(o[2]!=null ? ((BigDecimal)o[2]).longValue() : null);
			dto.setUfficio((String)o[3]);
			dto.setpContattoId(o[3]!=null ? ((BigDecimal)o[4]).longValue() : null);
			dto.setpContatto((String)o[5]);
			dto.setCognomeUtente((String)o[6]);
			dto.setNomeUtente((String)o[7]);
			dto.setDataNascita((Date)o[8]);
			dto.setOperatore((String)o[9]);
			dto.setTipo((String)o[10]);
			dto.setDataModifica((Date)o[11]);
			dto.setDataPrivacy((Date)o[12]);
			dto.setIdentificativo((BigDecimal)o[13]);
			dto.setStatoCS((String)o[14]);
			dto.setEnteSegnalazionePIC((String)o[15]);
			dto.setCfUtente((String)o[16]);
			dto.setAlias((String)o[17]);
			
			lstout.add(dto);
		}
		
		logger.debug("searchSchedeUDC - result size["+lst.size()+"]");
		return lstout;
		
	}
	
	public List<DatiSchedaListDTO> searchSchedeInviateUDC(SsSearchCriteria criteria) {
		List<DatiSchedaListDTO> lstout = new ArrayList<DatiSchedaListDTO>();
		String sql = new SsQueryBuilder(criteria).createQueryListaInviate(false);
		logger.debug("SQL LISTA SCHEDE UDC[" + sql + "]");

		Query q = em.createNativeQuery(sql);
		if (criteria.getFirst() != null)
			q.setFirstResult(criteria.getFirst());
		if (criteria.getPageSize() != null)
			q.setMaxResults(criteria.getPageSize());

		List<Object[]> lst = q.getResultList();

		for (Object[] o : lst) {
			DatiSchedaListDTO dto = new DatiSchedaListDTO();
			dto.setId(((BigDecimal) o[0]).longValue());
			dto.setDataAccesso((Date) o[1]);
			dto.setUfficio((String) o[2]);
			dto.setpContatto((String) o[3]);
			dto.setCognomeUtente((String) o[4]);
			dto.setNomeUtente((String) o[5]);
			dto.setDataNascita((Date) o[6]);
			dto.setOperatore((String) o[7]);
			dto.setTipo((String) o[8]);
			dto.setDataInvio((Date) o[9]);
			dto.setDataRicezione((Date)o[10]);
			dto.setNomeZonaInviante((String)o[11]);
			dto.setDescOrganizzazione((String)o[12]);
			
			lstout.add(dto);
		}

		logger.debug("searchSchedeUDC - result size[" + lst.size() + "]");
		return lstout;

	}

	public SsSchedaPrivacy saveSchedaPrivacy(SsSchedaPrivacy privacy) {
		return em.merge(privacy);
	}
	
	public RdcConsensiSocToLav saveConsensoToLavoro(RdcConsensiSocToLav privacy) {
		return em.merge(privacy);
	}
	
	public RdcConsensiSocToLav findConsensoToLavoro(RdcConsensiSocToLavPK pk) {
		return em.find(RdcConsensiSocToLav.class, pk);
	}

	public CsSsPrivacy findSchedaPrivacy(String cf, Long organizzazioneId) {
		CsSsPrivacy p = null;
		try{
			Query q = em.createNamedQuery("CsSsPrivacy.findById");
			q.setParameter("cf", cf.toUpperCase());
			q.setParameter("organizzazioneId", organizzazioneId);
			p = (CsSsPrivacy)q.getSingleResult();
		}catch(NoResultException nre){}
		return p;
	}
		
		public int countSchedeInviateUfficio(SsSearchCriteria dto)
	{
		int count=0;
		Query query = em.createNamedQuery("ArBufferSsInvio.countSchedeInviateUfficioNonLette");
		query.setParameter("ufficioId", (Long)dto.getUfficioId());
		query.setParameter("zs", dto.getZonaSociale());
		query.setParameter("organizzazione", dto.getOrganizzazioneId());
		count = ((Long) query.getSingleResult()).intValue();
		
		return count;
	}
	
	public int countSchedeInviateEnte(Long organizzazioneId, String zs) {
		int count=0;
		Query query = em.createNamedQuery("ArBufferSsInvio.countSchedeInviateEnteNonLette");
		query.setParameter("organizzazione", organizzazioneId);
		query.setParameter("zs", zs);
		count = ((Long) query.getSingleResult()).intValue();
		
		return count;
	}
	
	public Long countSchedeInviateUfficioTutte(SsSearchCriteria dto) {
		long count=0;
		Query query = em.createNamedQuery("ArBufferSsInvio.countSchedeInviateUfficio");
		query.setParameter("ufficioId", dto.getUfficioId());
		query.setParameter("organizzazione", dto.getOrganizzazioneId());
		query.setParameter("zs", dto.getZonaSociale());
		count = (Long) query.getSingleResult();
		
		return count;
	}

	public Long countSchedeInviateEnteTutte(SsSearchCriteria dto) {
		long count=0;
		Query query = em.createNamedQuery("ArBufferSsInvio.countSchedeInviateEnte");
		query.setParameter("zs", dto.getZonaSociale());
		query.setParameter("organizzazione", (Long)dto.getOrganizzazioneId());
		count = (Long) query.getSingleResult();
		
		return count;
	}


	public ArBufferSsInvio inviaScheda(BaseDTO dto) {
		ArBufferSsInvio schedaInviare = (ArBufferSsInvio) dto.getObj();
		try {
			em.merge(schedaInviare);
		} catch (Exception e) {
			logger.error(e);
		}
		return schedaInviare;
	}
	
	
	public ArBufferSsInvio riceviScheda(BaseDTO dto) {
		ArBufferSsInvio schedaInviare = (ArBufferSsInvio) dto.getObj();
		try {
			em.merge(schedaInviare);
		} catch (Exception e) {
			logger.error(e);
		}
		return schedaInviare;
	}

	public List<ArBufferSsInvio> readSchedeInviateByIdOrigZs(BaseDTO dto) {
		long schedaInviareId = (Long) dto.getObj();
		Query q = em.createNamedQuery("ArBufferSsInvio.readSchedaInviataById");
		q.setParameter("schedaId", schedaInviareId);
		q.setParameter("origZonaSociale", dto.getObj2());
		return q.getResultList();

	}

	public void annullaInvioScheda(BaseDTO dto) {
		// ArBufferSsInvio schedaInviata=
		// em.find(ArBufferSsInvio.class,((ArBufferSsInvio)dto.getObj()).getId()
		// );
		try {
			Query query = em
					.createNamedQuery("ArBufferSsInvio.deleteSchedaInviataById");
			query.setParameter("idBufferSchedaInviata",
					((ArBufferSsInvio) dto.getObj()).getId());
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}

	}

	public SsSchedaAccessoInviante saveAccessoInviante(SsSchedaAccessoInviante accessoInviante) {
		return em.merge(accessoInviante);
	}

	public SsSchedaAccessoInviante readSsSchedaAccessoInvianteByIdNuovaScheda(Long nuovaSchedaId) {
		List<SsSchedaAccessoInviante> results = new ArrayList<SsSchedaAccessoInviante>();
		SsSchedaAccessoInviante result= new SsSchedaAccessoInviante();
		
		Query query = em
				.createNamedQuery("SsSchedaAccessoInviante.readSsSchedaAccessoInvianteByIdNuovaScheda");
		query.setParameter("nuovaSchedaId",nuovaSchedaId);
		results = query.getResultList();
		if(results!=null && results.size()>0)
		{
			result=results.get(0);
		}
		

		return result;
	}

	public boolean esistonoDuplicatiCF(String cf, String cognome, String nome) {
		try {
			Query q = em.createNamedQuery("SsAnagrafica.verificaCF");
			q.setParameter("cf", cf.toUpperCase());
			q.setParameter("cognome", cognome.toUpperCase());
			q.setParameter("nome", nome.toUpperCase());
			List<SsSchedaSegnalato> lst =  q.getResultList();
			return !lst.isEmpty();
		} catch (Exception e) {
			logger.error(e);
		}
		
		return false;
	}

	public List<SsSchedaSegnalato> searchSchedeBySoggetto(SsSearchCriteria criteria) {
		String sql = new SsQueryBuilder(criteria).createQueryListaSoggetti();
		logger.debug("SQL LISTA SCHEDE UDC[" + sql + "]");
		Query q = em.createQuery(sql);
		return q.getResultList();
	}
	//SISO-948
	public boolean esisteDuplicatoAlias(String alias, String cognome, String nome) {
		try {
			Query q = em.createNamedQuery("SsAnagrafica.verificaAlias");
			q.setParameter("alias", alias.toUpperCase());
			q.setParameter("cognome", cognome.toUpperCase());
			q.setParameter("nome", nome.toUpperCase());
			List<SsAnagrafica> lst =  q.getResultList();
			return !lst.isEmpty();
		} catch (Exception e) {
			logger.error(e);
		}
		
		return false;
	}
	//SISO-948
	public List<SsAnagrafica> readAnagraficheByAlias(String alias) {
		Query q = em.createNamedQuery("SsScheda.readAnagraficaByAlias")
				.setParameter("alias", alias);
		return (List<SsAnagrafica>)q.getResultList();
	}
	
	//SISO-874
	public SsAnagraficaLog findAnagraficaLogById(Long id) {
		SsAnagraficaLog anagraficaLog = em.find(SsAnagraficaLog.class, id);
		return anagraficaLog;
	}

	
	public void saveAnagraficaLog(SsAnagraficaLog newAnagraficaLog) {
		em.persist(newAnagraficaLog);
	}

	public List<BigDecimal> findUfficioNota(Long anagraficaId) {
		String sql = "select distinct u.id from ss_Scheda_segnalato se left join ss_Scheda s on SE.ID = s.segnalato "+
				"left join ss_Scheda_accesso a on a.id=s.accesso "+
				"left join ss_ufficio u  on a.rel_upo_ufficio_id = u.id "+
				"where se.anagrafica = :anagraficaId";
		Query q = em.createNativeQuery(sql);
		q.setParameter("anagraficaId", anagraficaId);
		return (List<BigDecimal>) q.getResultList();
	}
}

