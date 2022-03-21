package it.webred.ss.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.data.model.SsPuntoContatto;
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsRelUffPcontOrgPK;
import it.webred.ss.data.model.SsTipoScheda;
import it.webred.ss.data.model.SsUfficio;
import it.webred.ss.ejb.dto.OperatoreDTO;
import it.webred.ss.ejb.dto.OrganizzazioneDTO;

@Named
public class ConfigurazioneDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "SegretariatoSoc_DataModel")
	protected EntityManager em;
	protected static Logger logger = Logger.getLogger("segretariatosoc.log");

	public void salvaUfficio(SsUfficio u) {
		em.merge(u);
	}

	public void salvaPuntoContatto(SsPuntoContatto pc) {
		em.merge(pc);
	}

	public void salvaRelazione(SsRelUffPcontOrg rel) {
		em.merge(rel);
	}

	@Transactional
	public void eliminaUfficio(SsUfficio u) {
		try {
			Query q = em.createNamedQuery("SsUfficio.eliminaUfficioById");
			q.setParameter("id", u.getId());
			q.executeUpdate();
		} catch (Exception e) {
			logger.error("Errore elimina Ufficio " + e.getMessage(), e);
		}

	}

	public List<SsPuntoContatto> getPuntiContatto() {
		Query q = em.createNamedQuery("SsPuntoContatto.findAll");
		return q.getResultList();
	}

	public List<SsUfficio> getUffici() {
		Query q = em.createNamedQuery("SsUfficio.findAll");
		return q.getResultList();
	}

	public List<SsOOrganizzazione> getOrganzzazioniAccesso() {
		Query q = em.createNamedQuery("SsOOrganizzazione.listOrganizzazioneDataRouting");
		return q.getResultList();
	}

	public List<SsRelUffPcontOrg> getRelazioni() {
		Query q = em.createNamedQuery("SsRelUffPcontOrg.findAll");
		return q.getResultList();
	}

	@Transactional
	public void eliminaPuntoContatto(SsPuntoContatto pc) {
		try {
			Query q = em.createNamedQuery("SsPuntoContatto.eliminaPuntoContattoById");
			q.setParameter("id", pc.getId());
			q.executeUpdate();
		} catch (Exception e) {
			logger.error("Errore elimina Punto di Contatto " + e.getMessage(), e);
		}
	}

	public void eliminaRelazione(SsRelUffPcontOrg rel) {
		try {
			Query q = em.createNamedQuery("SsRelUffPcontOrg.eliminaRelazioneById");
			q.setParameter("id", rel.getId());
			q.executeUpdate();
		} catch (Exception e) {
			logger.error("Errore elimina Relazione " + e.getMessage(), e);
		}
	}

	public List<SsRelUffPcontOrg> verificaRelazioni(SsRelUffPcontOrg rel) {
		try {

			Query q = em.createNamedQuery("SsRelUffPcontOrg.existsSchedaAccesso");
			q.setParameter("organizzazioneId", Long.valueOf(rel.getSsOOrganizzazione().getId()));
			q.setParameter("puntoContattoId", Long.valueOf(rel.getSsPuntoContatto().getId()));
			q.setParameter("ufficioId", Long.valueOf(rel.getSsUfficio().getId()));
			return q.getResultList();
		} catch (Exception e) {
			logger.error("Errore verifica Relazione " + e.getMessage(), e);
		}
		return null;
	}

	public void gestisciAttivazioneRelazioniUfficio(Long id, boolean abilita) {
		Query q = em.createNamedQuery("SsRelUffPcontOrg.gestisciAttivazioneByUfficio");
		q.setParameter("ufficioId", id);
		q.setParameter("abilita", abilita);
		q.executeUpdate();
	}

	public void gestisciAttivazioneRelPContatto(Long id, boolean abilita) {
		Query q = em.createNamedQuery("SsRelUffPcontOrg.gestisciAttivazioneByPuntoContatto");
		q.setParameter("puntoContattoId", id);
		q.setParameter("abilita", abilita);
		q.executeUpdate();
		
	}
	
	/*LETTURA*/
	@SuppressWarnings("unchecked")
	public List<SsUfficio> readUffici() {
		Query q = em.createNamedQuery("SsUfficio.findAll");
		return q.getResultList();
	}

	public SsUfficio readUfficio(Long id) {
		Query q = em.createNamedQuery("SsScheda.readUfficio").setParameter("id", id);
		 List<SsUfficio> results = q.getResultList();
		 
		 if(!results.isEmpty())
			 return results.get(0);
			 else return null;
	}

	public List<SsRelUffPcontOrg> readUffPcontByOrganizzazione(Long id) {
		Query q = em.createNamedQuery("SsRelUffPcontOrg.listaByOrganizzazione").setParameter("organizzazione", id);
		return q.getResultList();
	}

	public SsRelUffPcontOrg getSsRelUffPcontOrg(SsRelUffPcontOrgPK id) {
		return em.find(SsRelUffPcontOrg.class, id);
	}

	public List<OperatoreDTO> getListaOperatori(Long organizzazioneId,
			Long ufficioId) {
		List<OperatoreDTO> operatori = new ArrayList<OperatoreDTO>();
		
		String sql = "Select distinct a.id.operatore, a.cognome, a.nome  "+
					 "from SsUffOperatoreAnagrafica a "+
				     "where 1=1 ";
		sql+=organizzazioneId!=null ?  " AND a.id.organizzazioneId = :organizzazioneId " : "";
		sql+=ufficioId!=null ? " AND a.id.ufficioId = :ufficioId " : "";
		sql+="order by a.cognome, a.nome, a.id.operatore";
		
		Query q = em.createQuery(sql);
		
		if(organizzazioneId!=null) q.setParameter("organizzazioneId", organizzazioneId);
		if(ufficioId!=null) 	   q.setParameter("ufficioId", ufficioId);
		
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
	
	public List<SsOOrganizzazione> getListaOrganizzazioniZona() {
		List<SsOOrganizzazione> result = new ArrayList<SsOOrganizzazione>();
		List<Object[]> list = new ArrayList<Object[]>();

		Query query = em.createNamedQuery("SsOOrganizzazione.listOrganizzazioneZona");
		result = query.getResultList();
		// for (SsOOrganizzazione obj : list) {
		// SsOOrganizzazione tmp = new SsOOrganizzazione();
		// tmp = (SsOOrganizzazione) obj[0];
		//
		// result.add(tmp);
		// }

		return result;
	}

	public List<SsOOrganizzazione> getListaOrganizzazioniAltre() {
		List<SsOOrganizzazione> result = new ArrayList<SsOOrganizzazione>();
		List<Object[]> list = new ArrayList<Object[]>();

		Query query = em.createNamedQuery("SsOOrganizzazione.listOrganizzazioneAltre");
		result = query.getResultList();
	
		return result;
	}
	

	public SsOOrganizzazione readSsOrganizzazione(Long id) {
		SsOOrganizzazione orga = em.find(SsOOrganizzazione.class, id);
		return orga;
	}
	
	public SsOOrganizzazione findOrganizzazione(Long id){
		if(id>=0)
			return em.find(SsOOrganizzazione.class, id);
		else
			return null;
	}
	
	public OrganizzazioneDTO readArOrganizzazione(Long id) {
		OrganizzazioneDTO dest = null;
		String sql = "SELECT ID, BELFIORE, NOME, ZONA_NOME FROM AR_O_ORGANIZZAZIONE WHERE id = :id";
		Query q = em.createNativeQuery(sql);
		Object[] o = (Object[])q.getSingleResult();
		if(o!=null){
			dest = new OrganizzazioneDTO();
			dest.setId((Long)o[0]);
			dest.setBelfiore((String)o[1]);
			dest.setNome((String)o[2]);
			dest.setZonaSociale((String)o[3]);
		}
		return dest;
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

	public SsTipoScheda readTipoSchedaById(Long id) {
		Query q = em.createNamedQuery("SsScheda.readTipoSchedaById")
				.setParameter("id", id);
		if(!q.getResultList().isEmpty())
			return (SsTipoScheda)q.getResultList().get(0);
		else
			return null;
	}
}
