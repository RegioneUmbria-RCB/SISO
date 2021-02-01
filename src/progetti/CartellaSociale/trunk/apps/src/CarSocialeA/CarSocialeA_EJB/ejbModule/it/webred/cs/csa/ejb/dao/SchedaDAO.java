package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsAContributi;
import it.webred.cs.data.model.CsADatiInvalidita;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsADisabilita;
import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.cs.data.model.CsAServizi;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsATribunale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * @author Andrea
 *
 */
@Named
public class SchedaDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public CsADisabilita getDisabilitaById(Long id) {

		CsADisabilita cs = em.find(CsADisabilita.class, id);
		return cs;
		
	}
	
	
	
	//DISABILITA
	@SuppressWarnings("unchecked")
	public List<CsADisabilita> findDisabilitaBySoggettoId(long idSoggetto) {
			
		Query q = em.createNamedQuery("CsADisabilita.getDisabilitaBySoggettoId").setParameter("idSoggetto", idSoggetto);
		return q.getResultList();
			
	}
	
	public void saveDisabilita(CsADisabilita newDisabilita) {

		em.persist(newDisabilita);

	}
	
	public void updateDisabilita(CsADisabilita disabilita) {

		em.merge(disabilita);

	}
	
	public void eliminaDisabilita(Long id) {
		
		Query q = em.createNamedQuery("CsADisabilita.eliminaDisabilitaById");
		q.setParameter("id", id);
		q.executeUpdate();
		
	}
	
	//SOCIALI
	public CsADatiSociali getDatiSocialiById(Long id) {

		CsADatiSociali cs = em.find(CsADatiSociali.class, id);
		return cs;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<CsADatiSociali> findDatiSocialiBySoggettoId(long idSoggetto) {
			
		Query q = em.createNamedQuery("CsADatiSociali.getSocialiBySoggettoId").setParameter("idSoggetto", idSoggetto);
		return q.getResultList();
			
	}
	
	@SuppressWarnings("unchecked")
	public List<CsADatiSociali> findDatiSocialiBySoggettoCf(String cfSoggetto) {
			
		Query q = em.createNamedQuery("CsADatiSociali.getSocialiBySoggettoCf").setParameter("cfSoggetto", cfSoggetto);
		return q.getResultList();
			
	}
	
	public void saveDatiSociali(CsADatiSociali newDatiSociali) {

		em.persist(newDatiSociali);

	}
	
	public void updateDatiSociali(CsADatiSociali datiSociali) {

		em.merge(datiSociali);

	}
	
	public void eliminaDatiSociali(Long id) {
		
		Query q = em.createNamedQuery("CsADatiSociali.eliminaSocialiById");
		q.setParameter("id", id);
		q.executeUpdate();
		
	}
	
	//INVALIDITA
	public CsADatiInvalidita getDatiInvaliditaById(Long id) {

		CsADatiInvalidita cs = em.find(CsADatiInvalidita.class, id);
		return cs;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<CsADatiInvalidita> findDatiInvaliditaBySoggettoId(long idSoggetto) {
			
		Query q = em.createNamedQuery("CsADatiInvalidita.getInvaliditaBySoggettoId").setParameter("idSoggetto", idSoggetto);
		return q.getResultList();
			
	}
	
	public void saveDatiInvalidita(CsADatiInvalidita newInvalidita) {

		em.persist(newInvalidita);

	}
	
	public void updateDatiInvalidita(CsADatiInvalidita invalidita) {

		em.merge(invalidita);

	}
	
	public void eliminaDatiInvalidita(Long id) {
		
		Query q = em.createNamedQuery("CsADatiInvalidita.eliminaInvaliditaById");
		q.setParameter("id", id);
		q.executeUpdate();
		
	}
	
	//TRIBUNALE
	public CsATribunale getTribunaleById(Long id) {

		CsATribunale cs = em.find(CsATribunale.class, id);
		return cs;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<CsATribunale> findTribunaleBySoggettoId(long idSoggetto) {
			
		Query q = em.createNamedQuery("CsATribunale.getTribunaleBySoggettoId").setParameter("idSoggetto", idSoggetto);
		return q.getResultList();
			
	}
	
	public void saveTribunale(CsATribunale newTribunale) {

		em.persist(newTribunale);

	}
	
	public void updateTribunale(CsATribunale tribunale) {

		em.merge(tribunale);

	}
	
	public void eliminaTribunale(Long id) {
		
		Query q = em.createNamedQuery("CsATribunale.eliminaTribunaleById");
		q.setParameter("id", id);
		q.executeUpdate();
		
	}
	
	//SERVIZI
	public CsAServizi getServiziById(Long id) {

		CsAServizi cs = em.find(CsAServizi.class, id);
		return cs;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<CsAServizi> findServiziBySoggettoId(long idSoggetto) {
			
		Query q = em.createNamedQuery("CsAServizi.getServiziBySoggettoId").setParameter("idSoggetto", idSoggetto);
		return q.getResultList();
			
	}
	
	public void saveServizi(CsAServizi newServizi) {

		em.persist(newServizi);

	}
	
	public void updateServizi(CsAServizi servizi) {

		em.merge(servizi);

	}
	
	public void eliminaServizi(Long id) {
		
		Query q = em.createNamedQuery("CsAServizi.eliminaServiziById");
		q.setParameter("id", id);
		q.executeUpdate();
		
	}
	
	//CONTRIBUTI
	public CsAContributi getContributiById(Long id) {

		CsAContributi cs = em.find(CsAContributi.class, id);
		return cs;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<CsAContributi> findContributiBySoggettoId(long idSoggetto) {
			
		Query q = em.createNamedQuery("CsAContributi.getContributiBySoggettoId").setParameter("idSoggetto", idSoggetto);
		return q.getResultList();
			
	}
	
	public void saveContributi(CsAContributi newContributi) {

		em.persist(newContributi);

	}
	
	public void updateContributi(CsAContributi contributi) {

		em.merge(contributi);

	}
	
	public void eliminaContributi(Long id) {
		
		Query q = em.createNamedQuery("CsAContributi.eliminaContributiById");
		q.setParameter("id", id);
		q.executeUpdate();
		
	}
	
	//PARENTI
	public CsAFamigliaGruppo getFamigliaGruppoById(Long id) {

		CsAFamigliaGruppo cs = em.find(CsAFamigliaGruppo.class, id);
		return cs;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<CsAFamigliaGruppo> findFamigliaGruppoBySoggettoId(long idSoggetto) {
			
		Query q = em.createNamedQuery("CsAFamigliaGruppo.getFamigliaGruppoBySoggettoId").setParameter("idSoggetto", idSoggetto);
		return q.getResultList();
			
	}
	
	public Long saveFamigliaGruppo(CsAFamigliaGruppo famigliaGruppo) {
		em.persist(famigliaGruppo);
		logger.debug("Ho inserito/aggiornato il Gruppo Famiglia:"+ famigliaGruppo.getId());
		return famigliaGruppo.getId();
	}
	
	public CsAAnagrafica saveAnagrafica(CsAAnagrafica anagrafica) {
		if(anagrafica!=null && anagrafica.getId()!=null)
			anagrafica = em.merge(anagrafica);
		else
			em.persist(anagrafica);
		return anagrafica;
	}
	
	public void saveComponente(CsAComponente componente) {
		CsAAnagrafica ana = saveAnagrafica(componente.getCsAAnagrafica());
		componente.setCsAAnagrafica(ana);
		
		if(componente!=null && componente.getId()!=null)
			em.merge(componente);
		else
			em.persist(componente);
	}
	
	public void eliminaFamigliaGruppo(Long id) {
		
		Query q = em.createNamedQuery("CsAFamigliaGruppo.eliminaFamigliaGruppoById");
		q.setParameter("id", id);
		q.executeUpdate();
		
	}
	
	public void updateFamigliaGruppo(CsAFamigliaGruppo cs){
		em.merge(cs);
	}
	
	@SuppressWarnings("unchecked")
	public void eliminaComponenteNotInByFamigliaGruppo(Long id, List<Long> listaIdComp) {
		List<CsAComponente> listaComp = new ArrayList<CsAComponente>();
		if(listaIdComp.size()>0){
			Query q = em.createNamedQuery("CsAComponente.getComponenteNotInByFamigliaGruppoId");
			q.setParameter("id", id);
			q.setParameter("listaId", listaIdComp);
			listaComp = q.getResultList();
		}else{
			//Se le lista di elementi attivi è vuota, elimino tutti i componenti agganciati alla famiglia
			CsAFamigliaGruppo gr = this.getFamigliaGruppoById(id);
			listaComp = gr.getCsAComponentes();
		}
			
		for(CsAComponente cs: listaComp) {
			Query q2 = em.createNamedQuery("CsAComponente.eliminaComponenteById");
			q2.setParameter("id", cs.getId());
			q2.executeUpdate();
			
			Query q3 = em.createNamedQuery("CsAAnagrafica.eliminaAnagraficaById");
			q3.setParameter("id", cs.getCsAAnagrafica().getId());
			q3.executeUpdate();
			
		}
	}
	
	public void eliminaComponenteByFamigliaId(Long famigliaId) {
	  try{
		  if(famigliaId!=null){
			Query q = em.createNamedQuery("CsAFamigliaGruppo.getFamigliaGruppoById").setParameter("id", famigliaId);
			CsAFamigliaGruppo famigliaAttuale = (CsAFamigliaGruppo) q.getSingleResult();
			
			for(CsAComponente cs: famigliaAttuale.getCsAComponentes()) {
			
				Query q2 = em.createNamedQuery("CsAComponente.eliminaComponenteById");
				q2.setParameter("id", cs.getId());
				q2.executeUpdate();
				
				Query q3 = em.createNamedQuery("CsAAnagrafica.eliminaAnagraficaById");
				q3.setParameter("id", cs.getCsAAnagrafica().getId());
				q3.executeUpdate();
			
			}
		  }
		}catch(NoResultException e){}
	}
	
	@SuppressWarnings("unchecked")
	public CsAFamigliaGruppo findFamigliaGruppoAllaDataBySoggettoId(Long idSoggetto, Date dtRif, boolean loadCorrelati) {
		Query q = em.createNamedQuery("CsAFamigliaGruppo.getFamigliaGruppoAllaDataBySoggettoId");
		q.setParameter("idSoggetto", idSoggetto);
		q.setParameter("dtVal", dtRif!=null ? dtRif : DataModelCostanti.END_DATE);
		List<CsAFamigliaGruppo> lst = q.getResultList();
		CsAFamigliaGruppo famGr= null;
		if(lst.size()>0)
			famGr = lst.get(0);
		
		if(famGr!=null && loadCorrelati)
			famGr.getCsAComponentes();
		return famGr;
	}



	public List<CsAAnagrafica> findComponentiGiaFamigliariBySoggettoCf(String cfSoggetto, Date dataRif) {
		List<CsAAnagrafica> result= new ArrayList<CsAAnagrafica>();
		
		//Recupero le famiglie che contengono il soggetto (come riferimento o componente) - lo escludo dalla lista in un secondo momento
		String sql = "select distinct fg from CsAFamigliaGruppo fg inner join fg.csAComponentes comp "
				+ "where fg.csASoggetto.csAAnagrafica.cf = :cfSoggetto or comp.csAAnagrafica.cf = :cfSoggetto ";
		
		if(dataRif!=null) sql+= " and fg.dataFineApp >= :dataRif" ;
		
		Query q = em.createQuery(sql);
		q.setParameter("cfSoggetto", cfSoggetto);
		if(dataRif!=null) q.setParameter("dataRif", dataRif);
		List<CsAFamigliaGruppo> lstFamiglie = q.getResultList();
		List<Long> idFamiglias=new ArrayList<Long>();
		for (CsAFamigliaGruppo famiglia : lstFamiglie) {
			if(!idFamiglias.contains(famiglia.getId())) 
				idFamiglias.add(famiglia.getId());
		}
			
		if(idFamiglias.size()>0)
		{	
			Query q1b = em.createNamedQuery("CsAFamigliaGruppo.getAnaSoggettoFamiglieGruppo");
			q1b.setParameter("famigliaIds",idFamiglias);
			List<CsASoggettoLAZY> result1b = q1b.getResultList();
			List<CsAAnagrafica> result1b_ana=new ArrayList<CsAAnagrafica>();
			for (CsASoggettoLAZY csASoggettoLAZY : result1b) {
				CsAAnagrafica anaCurr=csASoggettoLAZY.getCsAAnagrafica();
				if(!cfSoggetto.equalsIgnoreCase(anaCurr.getCf())){
					anaCurr.setCsASoggetto(csASoggettoLAZY);
					result1b_ana.add(anaCurr);
				}
			}
			result.addAll(result1b_ana);	
			
			Query q1a = em.createNamedQuery("CsAFamigliaGruppo.getAnaComponentiGiaFamigliariSoggettoCf");
			q1a.setParameter("cfSoggetto", cfSoggetto);
			q1a.setParameter("famigliaIds",idFamiglias);
			List<CsAComponente> result1a = q1a.getResultList();
			List<CsAAnagrafica> result1a_ana=new ArrayList<CsAAnagrafica>();
			for (CsAComponente componente : result1a) {
				CsAAnagrafica anaCurr=componente.getCsAAnagrafica();
				anaCurr.setCsAComponente(componente);
				result1a_ana.add(anaCurr);
			}
			result.addAll(result1a_ana);
			

		}
		
		
		return result;
	}






}
