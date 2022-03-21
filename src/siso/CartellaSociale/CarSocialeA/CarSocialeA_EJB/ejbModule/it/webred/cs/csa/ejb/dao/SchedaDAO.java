package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.dto.cartella.RisorsaCalcDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsADatiInvalidita;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsADisabilita;
import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsATribunale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
			
		Query q = em.createNamedQuery("CsADatiSociali.getSocialiBySoggettoCf");
		q.setParameter("cfSoggetto", cfSoggetto);
		return q.getResultList();
			
	}
	
	@SuppressWarnings("unchecked")
	public CsADatiSociali findDatiSocialiAttiviBySoggettoCf(String cfSoggetto) {
		Query q = em.createNamedQuery("CsADatiSociali.getSocialiAttiviBySoggettoCf");
		q.setParameter("cfSoggetto", cfSoggetto);
		List<CsADatiSociali> ds = q.getResultList();
		if(ds!=null && !ds.isEmpty())
			return ds.get(0);
		return null;
			
	}
	
	public void saveDatiSociali(CsADatiSociali newDatiSociali) {

		em.persist(newDatiSociali);

	}
	
	public void updateDatiSociali(CsADatiSociali datiSociali) {

		try {
			em.merge(datiSociali);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

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
	public List<CsADatiInvalidita> findDatiInvaliditaBySoggettoId(long idSoggetto, Date dtRif) {
		String nomeQuery = dtRif!=null ? "CsADatiInvalidita.getInvaliditaBySoggettoIdAllaData" : "CsADatiInvalidita.getInvaliditaBySoggettoId";
		Query q = em.createNamedQuery(nomeQuery);
		q.setParameter("idSoggetto", idSoggetto);
		
		if(dtRif!=null)
		q.setParameter("dtRif", dtRif);	
		
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
		
	//PARENTI
	public CsAFamigliaGruppo getFamigliaGruppoById(Long id) {
		CsAFamigliaGruppo famigliaAttuale = null;
		try{
			Query q = em.createNamedQuery("CsAFamigliaGruppo.getFamigliaGruppoById");
			q.setParameter("id", id);
			famigliaAttuale = (CsAFamigliaGruppo) q.getSingleResult();
			
		}catch(NoResultException nre){}
		return famigliaAttuale;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsAFamigliaGruppo> findFamigliaGruppoBySoggettoId(long idSoggetto) {
		Query q = em.createNamedQuery("CsAFamigliaGruppo.getFamigliaGruppoBySoggettoId");
		q.setParameter("idSoggetto", idSoggetto);
		List<CsAFamigliaGruppo> fams = q.getResultList();
		for(CsAFamigliaGruppo f : fams)
			f.getCsAComponentes().size();
		return fams;
			
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
			listaComp.addAll(gr.getCsAComponentes());
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
	  if(famigliaId!=null){
		CsAFamigliaGruppo famigliaAttuale = this.getFamigliaGruppoById(famigliaId);
		if(famigliaAttuale!=null){
			for(CsAComponente cs: famigliaAttuale.getCsAComponentes()) {
				Query q2 = em.createNamedQuery("CsAComponente.eliminaComponenteById");
				q2.setParameter("id", cs.getId());
				q2.executeUpdate();
				
				Query q3 = em.createNamedQuery("CsAAnagrafica.eliminaAnagraficaById");
				q3.setParameter("id", cs.getCsAAnagrafica().getId());
				q3.executeUpdate();
			}
		}
	  }
	}
	
	@SuppressWarnings("unchecked")
	public List<CsAComponente> findComponentiFamigliaAllaDataBySoggettoId(Long idSoggetto, Date dtRif) {
		List<CsAComponente> lst = new ArrayList<CsAComponente>();
		Date dtVal = dtRif!=null ? dtRif : DataModelCostanti.END_DATE;
		logger.debug("findComponentiFamigliaAllaDataBySoggettoId idSoggetto["+idSoggetto+" dtVal["+dtVal+"]");
		Query q = em.createNamedQuery("CsAComponente.getComponentiAllaDataBySoggettoId");
		q.setParameter("idSoggetto", idSoggetto);
		q.setParameter("dtVal", dtVal);
		lst = q.getResultList();
		logger.debug("findComponentiFamigliaAllaDataBySoggettoId idSoggetto["+idSoggetto+" dtVal["+dtVal+"] result["+lst.size()+"]");
		
		return lst;
	}



	public List<RisorsaCalcDTO> findComponentiGiaFamigliariBySoggettoCf(String cfSoggetto, Date dataDa, Date dataA) {
		List<RisorsaCalcDTO> result= new ArrayList<RisorsaCalcDTO>();
		logger.debug("INIT findComponentiGiaFamigliariBySoggettoCf ["+cfSoggetto+"]["+dataDa+"]["+dataA+"]");
		
		boolean intervallo = dataDa!=null && dataA!=null;
		
		//Recupero le famiglie che contengono il soggetto (come riferimento o componente) - lo escludo dalla lista in un secondo momento
		String sql = "select distinct fg.id from CsAFamigliaGruppo fg inner join fg.csAComponentes comp "
				+ "where (upper(fg.csASoggetto.csAAnagrafica.cf) = :cfSoggetto or upper(comp.csAAnagrafica.cf) = :cfSoggetto ) ";
		
		if(intervallo) sql+= " and fg.dataInizioApp <= :dataA and fg.dataFineApp >= :dataDa " ;
		else if(dataDa!=null) sql+= " and :dataRif between fg.dataInizioApp and fg.dataFineApp " ;
		
		Query q = em.createQuery(sql);
		q.setParameter("cfSoggetto", cfSoggetto.toUpperCase());
		
		if(intervallo){
			q.setParameter("dataDa", dataDa);
			q.setParameter("dataA", dataA);
		}else if(dataDa!=null) q.setParameter("dataRif", dataDa);
		
		List<Long> lstFamiglieIds = q.getResultList();
		List<Long> idFamiglias=new ArrayList<Long>();
		for (Long fId : lstFamiglieIds) {
			if(!idFamiglias.contains(fId)) 
				idFamiglias.add(fId);
		}
			
		logger.debug("INIT findComponentiGiaFamigliariBySoggettoCf STEP2 idFamiglia["+idFamiglias+"]");
		//Recupero i titolari della cartella
		if(idFamiglias.size()>0)
		{	
			Query q1b = em.createNamedQuery("CsAFamigliaGruppo.getAnaSoggettoFamiglieGruppo");
			q1b.setParameter("famigliaIds",idFamiglias);
			List<CsASoggettoLAZY> result1b = q1b.getResultList();
			List<RisorsaCalcDTO> result1b_ana=new ArrayList<RisorsaCalcDTO>();
			for (CsASoggettoLAZY csASoggettoLAZY : result1b) {
				CsAAnagrafica anaCurr=csASoggettoLAZY.getCsAAnagrafica();
				if(!cfSoggetto.equalsIgnoreCase(anaCurr.getCf())){
					RisorsaCalcDTO r = new RisorsaCalcDTO(anaCurr);
					r.setSoggetto(csASoggettoLAZY);
					List<Long> famIds = getFamiglieBySoggettoId(csASoggettoLAZY.getAnagraficaId());
					r.setFamiglieSoggettoIds(famIds);
					result1b_ana.add(r);
				}
			}
			result.addAll(result1b_ana);	
			
			logger.debug("INIT findComponentiGiaFamigliariBySoggettoCf STEP3");
			//Recupero i familiari
			Query q1a = em.createNamedQuery("CsAFamigliaGruppo.getAnaComponentiGiaFamigliariSoggettoCf");
			q1a.setParameter("cfSoggetto", cfSoggetto);
			q1a.setParameter("famigliaIds",idFamiglias);
			List<CsAComponente> result1a = q1a.getResultList();
			List<RisorsaCalcDTO> result1a_ana=new ArrayList<RisorsaCalcDTO>();
			for (CsAComponente componente : result1a) {
				RisorsaCalcDTO r = new RisorsaCalcDTO(componente.getCsAAnagrafica());
				r.setComponente(componente);
				Date dataInizio = componente.getCsAFamigliaGruppo().getDataInizioApp();
				Date dataFine = componente.getCsAFamigliaGruppo().getDataFineApp();
				String validita = "["+ (dataInizio!=null ? ddMMyyyy.format(dataInizio) : "") +" - "+ (dataFine!=null ? ddMMyyyy.format(dataFine) : "attuale")+"]";
				r.setDateValidita(validita);
				result1a_ana.add(r);
			}
			result.addAll(result1a_ana);
		}
		
		logger.debug("INIT findComponentiGiaFamigliariBySoggettoCf ["+cfSoggetto+"]["+dataDa+"]["+dataA+"] result["+result.size()+"]");
		
		return result;
	}

   private List<Long> getFamiglieBySoggettoId(Long anagraficaId){
	   Query q = em.createNamedQuery("CsAFamigliaGruppo.getIdsFamigliaGruppoBySoggettoId");
	   q.setParameter("anagraficaId", anagraficaId);
	   return q.getResultList();
   }
}
