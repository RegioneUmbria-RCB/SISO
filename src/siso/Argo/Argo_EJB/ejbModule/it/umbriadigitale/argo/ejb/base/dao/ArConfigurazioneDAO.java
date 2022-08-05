package it.umbriadigitale.argo.ejb.base.dao;

import it.umbriadigitale.argo.data.cs.data.ArFfFondo;
import it.umbriadigitale.argo.data.cs.data.ArFfLineafin;
import it.umbriadigitale.argo.data.cs.data.ArFfLineafinOrg;
import it.umbriadigitale.argo.data.cs.data.ArFfProgetto;
import it.umbriadigitale.argo.data.cs.data.ArFfProgettoAttivita;
import it.umbriadigitale.argo.data.cs.data.ArFfProgettoOrg;
import it.umbriadigitale.argo.data.cs.data.ArOOrganizzazione;
import it.umbriadigitale.argo.data.cs.data.ImportSiruProgettiAttivita;
import it.umbriadigitale.argo.data.cs.data.ImportSiruProgettiAttivitaPK;
import it.umbriadigitale.argo.ejb.ArgoBaseDAO;
import it.umbriadigitale.argo.ejb.client.base.ejbclient.ArgoServiceException;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArProgettoDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ProgettiSearchCriteria;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

@Named
public class ArConfigurazioneDAO extends ArgoBaseDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	public void save(ArFfProgetto progetto) {
		em.merge(progetto);
	}	
	
	public ImportSiruProgettiAttivita findSiruProgettiAttivita(ImportSiruProgettiAttivitaPK id){
		return em.find(ImportSiruProgettiAttivita.class, id);
	}

	public List<ArFfProgetto> getListaProgetti(ProgettiSearchCriteria sc, boolean count) {
		
		String sql = "select distinct a FROM ArFfProgetto a where 1=1 ";
		sql+= !StringUtils.isBlank(sc.getCodiceMemo()) ? " and upper(a.codiceMemo) like :codiceMemo " : "";
		sql+= !StringUtils.isBlank(sc.getDescrizione()) ? " and upper(a.descrizione) like :descrizione " : "";
		sql+= " ORDER BY a.descrizione";
		Query q = em.createQuery(sql);
		
		if(!StringUtils.isBlank(sc.getCodiceMemo())) 
			q.setParameter("codiceMemo", "%"+sc.getCodiceMemo().toUpperCase()+"%");
		if(!StringUtils.isBlank(sc.getDescrizione())) 
			q.setParameter("descrizione", "%"+sc.getDescrizione().toUpperCase()+"%");
		
		if(!count){
			if(sc.getFirst()!=null) q.setFirstResult(sc.getFirst());
			if(sc.getPageSize()!=null) q.setMaxResults(sc.getPageSize());
		}
		return q.getResultList();
	}
	
	public List<ArProgettoDTO> getListaProgetti(List<Long> idOrganizzazioni) {
		List<ArProgettoDTO> lstOut = new ArrayList<ArProgettoDTO>();
		if(idOrganizzazioni!=null && !idOrganizzazioni.isEmpty()) {
			String sql = "select distinct p.id, p.codice_Memo, p.descrizione, p.abilitato"
					+ " FROM AR_FF_PROGETTO_ORG porg, AR_FF_PROGETTO p, AR_O_ORGANIZZAZIONE o "
					+ " where porg.organizzazione_id = o.id and p.id = porg.progetto_id and porg.abilitato = 1 "
					+ " and porg.organizzazione_Id in :listaOrganizzazioni ";
			sql+= " ORDER BY p.descrizione";
			Query q = em.createNativeQuery(sql);
			
			if(!idOrganizzazioni.isEmpty()) 
				q.setParameter("listaOrganizzazioni", idOrganizzazioni);
			
			List<Object[]> lst = q.getResultList();
			for(Object[] o : lst) {
				ArProgettoDTO p = new ArProgettoDTO();
				BigDecimal id = (BigDecimal)o[0];
				p.setId(id.longValue());
				p.setCodiceMemo((String)o[1]);
				p.setDescrizione((String)o[2]);
				Character abilitato = (Character)o[3];
				p.setAbilitato(abilitato!=null && "1".equalsIgnoreCase(abilitato.toString()));
				lstOut.add(p);
			}
		}
		return lstOut;
	}
	
	
	public List<ArFfProgettoOrg> getListaOrganizzazioniProgetto(Long progettoId){
		Query q = em.createNamedQuery("ArFfProgettoOrg.findByProgettoId");
		q.setParameter("progettoId",progettoId);
		return q.getResultList();
	}
	
	public List<ArFfProgettoAttivita> getListaAttivitaProgetto(Long progettoId){
		Query q = em.createNamedQuery("ArFfProgettoAttivita.findByProgettoId");
		q.setParameter("progettoId",progettoId);
		return q.getResultList();
	}
	
	public List<ArOOrganizzazione> getOrganizzazioni(){
		Query q = em.createNamedQuery("ArOOrganizzazione.findAll");
		return q.getResultList();
	}
	
	public List<ArOOrganizzazione> getListaOrganizzazioniFuoriZona(String zonaSociale) {
		List<ArOOrganizzazione> result = new ArrayList<ArOOrganizzazione>();
		List<Object[]> list = new ArrayList<Object[]>();

		Query q = em.createNamedQuery("ArOOrganizzazione.listOrganizzazioneFuoriZona");
		q.setParameter("zonaSociale", zonaSociale);
		result = q.getResultList();

		return result;
	}
	
	public ArFfProgetto salvaProgetto(ArFfProgetto progetto) throws Exception {
		try {
			if(progetto.getId()==null) {
				em.persist(progetto);
			}else {
				em.merge(progetto);
			}
			
			return progetto;
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
			throw new ArgoServiceException(e);
		}
		
		
	}
	
	public ArFfProgettoAttivita salvaAttivita(ArFfProgettoAttivita attivita) throws Exception {
		try {
			attivita.setDtIns(new Date());
			if(attivita.getId()==null) {
				em.persist(attivita);
			}else {
				em.merge(attivita);
			}
			
			return attivita;
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
			throw new ArgoServiceException(e);
		}
		
		
	}
	public ArFfProgettoOrg salvaProgettoOrg(ArFfProgettoOrg progettoOrg) throws Exception {
		em.merge(progettoOrg);
		return progettoOrg;
	}
	
	public ArOOrganizzazione getOrganizzazioneById(Long idOrganizzazione){
		Query q = em.createNamedQuery("ArOOrganizzazione.findById");
		q.setParameter("idOrganizzazione", idOrganizzazione);
		return (ArOOrganizzazione)q.getSingleResult();
	}
	
	public ArFfProgetto findArFfProgetto(Long idProgetto) {
		if(idProgetto!=null)
			return em.find(ArFfProgetto.class, idProgetto);
		else
			return null;
	}

	public List<ArFfProgettoAttivita> findAttivitaByCodiceProgetto(String codice, Long progettoId) {
		Query q = em.createNamedQuery("ArFfProgettoAttivita.findByCodiceProgetto");
		q.setParameter("codiceMemo", codice);
		q.setParameter("progettoId", progettoId);
		return q.getResultList();
	}
	
	public List<ArFfProgetto> findProgettoByCodiceProgetto(String codice) {
		Query q = em.createNamedQuery("ArFfProgetto.findByCodiceProgetto");
		q.setParameter("codiceMemo", codice);
		return q.getResultList();
	}

	public void eliminaAttivita(Long attivitaId) {
		Query q = em.createNamedQuery("ArFfProgettoAttivita.elimina");
		q.setParameter("id", attivitaId);
		q.executeUpdate();
	}

	public void eliminaProgetto(Long progettoId) {
		Query q = em.createNamedQuery("ArFfProgetto.elimina");
		q.setParameter("id", progettoId);
		q.executeUpdate();
	}

	public void eliminaOrganizzazioniProgetto(Long progettoId) {
		Query q = em.createNamedQuery("ArFfProgettoOrg.eliminaByProgettoId");
		q.setParameter("progettoId", progettoId);
		q.executeUpdate();
	}
	
	public void eliminaOrganizzazioniProgetto(Long progettoId, List<Long> orgsId){
		Query q = em.createNamedQuery("ArFfProgettoOrg.eliminaByOrgProgettoId");
		q.setParameter("progettoId",progettoId);
		q.setParameter("listaOrganizzazioni", orgsId);
		q.executeUpdate();
	}

	public void gestisciAbilitazioneProgetti(List<Long> progettiSelezionati, Boolean valore) {
		Query q = em.createNamedQuery("ArFfProgetto.gestisciAbilitazione");
		q.setParameter("listaProgettoId", progettiSelezionati);
		q.setParameter("abilitato", valore);
		q.executeUpdate();
		
		q = em.createNamedQuery("ArFfProgettoOrg.gestisciAbilitazione");
		q.setParameter("listaProgettoId", progettiSelezionati);
		q.setParameter("abilitato", valore);
		q.executeUpdate();
		
		q = em.createNamedQuery("ArFfProgettoAttivita.gestisciAbilitazione");
		q.setParameter("listaProgettoId", progettiSelezionati);
		q.setParameter("abilitato", valore);
		q.executeUpdate();
		
	}

	public List<ArFfLineafin> getListaFontiFinanziamento(ProgettiSearchCriteria sc, boolean count) {
		String sql = "select distinct a FROM ArFfLineafin a where 1=1 ";
		sql+= !StringUtils.isBlank(sc.getCodiceMemo()) ? " and upper(a.codiceMemo) like :codiceMemo " : "";
		sql+= !StringUtils.isBlank(sc.getDescrizione()) ? " and upper(a.descrizione) like :descrizione " : "";
		sql+= " ORDER BY a.descrizione";
		Query q = em.createQuery(sql);
		
		if(!StringUtils.isBlank(sc.getCodiceMemo())) 
			q.setParameter("codiceMemo", "%"+sc.getCodiceMemo().toUpperCase()+"%");
		if(!StringUtils.isBlank(sc.getDescrizione())) 
			q.setParameter("descrizione", "%"+sc.getDescrizione().toUpperCase()+"%");
		
		if(!count){
			if(sc.getFirst()!=null) q.setFirstResult(sc.getFirst());
			if(sc.getPageSize()!=null) q.setMaxResults(sc.getPageSize());
		}
		return q.getResultList();
	}

	public List<ArFfFondo> getListaArFfFondo() {
		Query q = em.createNamedQuery("ArFfFondo.findAll");
		return q.getResultList();
	}

	public void gestisciAbilitazioneFonti(List<Long> fontiSelezionate, Boolean valore) {
		Query q = em.createNamedQuery("ArFfLineafin.gestisciAbilitazione");
		q.setParameter("listaId", fontiSelezionate);
		q.setParameter("abilitato", valore);
		q.executeUpdate();
	}

	public ArFfLineafin findArFfLineafin(Long id) {
		if(id!=null)
			return em.find(ArFfLineafin.class, id);
		else return null;
	}
	
	public ArFfFondo findArFfFondo(Long id) {
		return em.find(ArFfFondo.class, id);
	}

	public void eliminaOrganizzazioniFonte(Long fonteId, List<Long> orgToRem) {
		Query q = em.createNamedQuery("ArFfLineafinOrg.eliminaByOrgFonteId");
		q.setParameter("fonteId", fonteId);
		q.setParameter("listaOrganizzazioni", orgToRem);
		q.executeUpdate();
		
	}

	public ArFfLineafin salvaFonte(ArFfLineafin target) {
		try {
			if(target.getId()==null) {
				em.persist(target);
			}else {
				em.merge(target);
			}
			
			return target;
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
			throw new ArgoServiceException(e);
		}
	}

	public List<ArFfLineafinOrg> getListaOrganizzazioniFonte(Long id) {
		Query q = em.createNamedQuery("ArFfLineafinOrg.findByLineafinId");
		q.setParameter("lineaFinId", id);
		return q.getResultList();
	}

	public ArFfLineafinOrg salvaFonteOrg(ArFfLineafinOrg po) {
		return em.merge(po);
	}

	public void eliminaOrganizzazioniFonte(Long id) {
		Query q = em.createNamedQuery("ArFfLineafinOrg.eliminaByLineafinId");
		q.setParameter("lineaFinId", id);
		q.executeUpdate();
	}

	public void eliminaFonte(Long id) {
		Query q = em.createNamedQuery("ArFfLineafin.elimina");
		q.setParameter("id", id);
		q.executeUpdate();
	}

	public List<ArFfLineafin> findFonteByCodice(String codiceMemo) {
		Query q = em.createNamedQuery("ArFfLineafin.findByCodiceMemo");
		q.setParameter("codiceMemo", codiceMemo);
		return q.getResultList();
	}
	
	
	
}
