package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.dto.CasoSearchCriteria;
import it.webred.cs.csa.ejb.dto.ContatoreCasiDTO;
import it.webred.cs.csa.ejb.dto.retvalue.DatiCasoListaDTO;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoCategoriaSocLAZY;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsASoggettoMedico;
import it.webred.cs.data.model.CsASoggettoStatoCivile;
import it.webred.cs.data.model.CsASoggettoStatus;
import it.webred.cs.data.model.CsCCategoriaSociale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class SoggettoDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public CsASoggettoLAZY getSoggettoByCaso(CsACaso caso){
		
		Query q = em.createNamedQuery("CsASoggetto.findByCaso");
		q.setParameter("casoId", caso.getId());
		List<CsASoggettoLAZY> lst =  q.getResultList();
		if(lst.size()>0)
			return lst.get(0);
		else 
			return null;
		
	}
	
	public CsASoggettoLAZY getSoggettoById(Long id) {

		Query q = em.createNamedQuery("CsASoggetto.findById");
		q.setParameter("Id", id);
		CsASoggettoLAZY cs =  (CsASoggettoLAZY) q.getSingleResult();

		return cs;
		
	}
	
	@SuppressWarnings("unchecked")
	public CsASoggettoLAZY getSoggettoByCF(String cf) {
		List<CsASoggettoLAZY> lista = this.getSoggettiByCF(cf);
		if(lista.size() > 0)
			return lista.get(0);
		
		return null;

	}
	
	@SuppressWarnings("unchecked")
	public List<CsASoggettoLAZY> getSoggettiByCF(String cf) {
		Query q = em.createNamedQuery("CsASoggetto.findByCF")
				.setParameter("cf", cf);
		return q.getResultList();
	
	}
	
	@SuppressWarnings("unchecked")
	public List<CsASoggettoLAZY> getSoggettiByDenominazione(String denominazione) {

		Query q = em.createNamedQuery("CsASoggetto.findByDenominazione")
				.setParameter("denominazione", denominazione);
		return q.getResultList();

	}
	
	public void saveSoggetto(CsASoggettoLAZY soggetto) {

		em.persist(soggetto);

	}
	
	public void updateSoggetto(CsASoggettoLAZY soggetto) {

		em.merge(soggetto);

	}
	
	@SuppressWarnings("unchecked")
	public List<CsASoggettoCategoriaSoc> getSoggCategorieBySoggetto(long idSoggetto) {

		Query q = em.createNamedQuery("CsASoggettoCategoriaSoc.findBySoggetto")
				.setParameter("idSoggetto", idSoggetto);
		return q.getResultList();

	}
	

	
	@SuppressWarnings("unchecked")
	public List<CsASoggettoCategoriaSocLAZY> getSoggCategorieAttualiLAZYBySoggetto(long idSoggetto) {

		Query q = em.createNamedQuery("CsASoggettoCategoriaSoc.findAttualiLAZYBySoggetto");
		q.setParameter("idSoggetto", idSoggetto);
		q.setParameter("dtRif", new Date());
		return q.getResultList();

	}
	
	
	public void saveSoggettoCategoria(CsASoggettoCategoriaSoc cs) {

		em.persist(cs);

	}
	
	public boolean eliminaSoggettoCategorieBySoggetto(Long soggettoId) {
		
		Query q = em.createNamedQuery("CsASoggettoCategoriaSoc.eliminaBySoggettoId");
		q.setParameter("id", soggettoId);
		int num = q.executeUpdate();
		return num>0;
		
	}

	@SuppressWarnings("unchecked")
	public List<DatiCasoListaDTO> getCasiSoggetto(Integer first, Integer pagesize, CasoSearchCriteria criteria) throws Throwable {
		List<DatiCasoListaDTO> lstCasi = new ArrayList<DatiCasoListaDTO>();
		
		try{
		String sql = new SoggettoQueryBuilder(criteria).createQueryListaCasi(false);
		logger.info("getCasiSoggetto SQL LISTA CASI[" + sql+"]");
		
		Query q = em.createNativeQuery(sql);
		if(first != null)
			q.setFirstResult(first);
		if(pagesize != null)
			q.setMaxResults(pagesize);

		List<Object[]> lista = q.getResultList();
		List<Long> idAnagrafici = new ArrayList<Long>();
		HashMap<Long,Date> mappaDate = new HashMap<Long,Date>();
		for(Object[] objArr: lista) {
			Long idAnagrafica = ((BigDecimal) objArr[0]).longValue();
			idAnagrafici.add(idAnagrafica);
			mappaDate.put(idAnagrafica, (Date)objArr[4]);
		}
		
		if (idAnagrafici!=null && idAnagrafici.size()>0) {
			Query qList = em.createQuery("select s from CsASoggettoLAZY s inner join fetch s.csACaso c inner join fetch s.csAAnagrafica a where s.anagraficaId in (:idAnagrafici) order by s.csAAnagrafica.cognome, s.csAAnagrafica.nome");
			qList.setParameter("idAnagrafici", idAnagrafici ); 
			List<CsASoggettoLAZY> listaSogg = qList.getResultList();
			for(CsASoggettoLAZY s : listaSogg){
				DatiCasoListaDTO dc = new DatiCasoListaDTO();
				dc.setSoggetto(s);
				dc.setDataApertura(mappaDate.get(s.getAnagraficaId()));
				lstCasi.add(dc);
			}
		}
		
		logger.info("restituisco getCasiSoggetto "+ lista.size());
		
		}catch(Throwable e){
			logger.error("Errore recupero lista casi - getCasiSoggetto["+e.getMessage()+"]", e);
			throw e;
		}
		
		return lstCasi;
	}
	
	public Integer getCasiSoggettoCount(CasoSearchCriteria criteria) {
		String sql = new SoggettoQueryBuilder(criteria).createQueryListaCasi(true);
		Query q = em.createNativeQuery(sql);
		BigDecimal o = (BigDecimal) q.getSingleResult();
		return new Integer(o.intValue());
	}
	
	public ContatoreCasiDTO getCasiPerCategoriaCount(CasoSearchCriteria criteria) {
		ContatoreCasiDTO counter = new ContatoreCasiDTO();
		String sql = new SoggettoQueryBuilder(criteria).createQueryCasiPerCategoria(true);
		logger.debug("getCasiPerCategoriaCount SQL["+sql+"]");
		Query q = em.createNativeQuery(sql);
		List<Object[]> results = q.getResultList();
		int countOrganizzazione = 0;
		int countZonaSociale=0;
		for(Object[] o : results){
			BigDecimal orgId = (BigDecimal)o[0];
			BigDecimal settId = (BigDecimal)o[1];
			BigDecimal countSettore = (BigDecimal)o[2]; 
			if(settId.longValue()==criteria.getIdSettore())
				counter.setCountSettore(countSettore.intValue());
			if(orgId.longValue()==criteria.getIdOrganizzazione())
				countOrganizzazione+=countSettore.intValue();
			
			countZonaSociale+=countSettore.intValue();
		}
		counter.setCountOrganizzazione(countOrganizzazione);
		counter.setCountZonaSociale(countZonaSociale);
		return counter;
	}
	
	public CsAAnagrafica getAnagraficaById(Long id) {

		CsAAnagrafica cs = em.find(CsAAnagrafica.class, id);
		return cs;
		
	}
	
	
	public CsAAnagrafica saveAnagrafica(CsAAnagrafica anagrafica) {
		if(anagrafica!=null && anagrafica.getId()!=null)
			anagrafica = em.merge(anagrafica);
		else
			em.persist(anagrafica);
		return anagrafica;

	}
		
	//Medici
	@SuppressWarnings("unchecked")
	public List<CsASoggettoMedico> getSoggettoMedicoBySoggetto(long idSoggetto) {

		Query q = em.createNamedQuery("CsASoggettoMedico.findBySoggetto").setParameter("idSoggetto", idSoggetto);
		return q.getResultList();

	}
	
	public void saveSoggettoMedico(CsASoggettoMedico cs) {

		em.persist(cs);

	}
	
	public void updateSoggettoMedico(CsASoggettoMedico cs) {

		em.merge(cs);

	}
	
	public void eliminaSoggettoMedicoBySoggetto(Long soggettoId) {
		
		Query q = em.createNamedQuery("CsASoggettoMedico.eliminaBySoggettoId");
		q.setParameter("id", soggettoId);
		q.executeUpdate();
		
	}
	
	//Stato Civile
	@SuppressWarnings("unchecked")
	public List<CsASoggettoStatoCivile> getSoggettoStatoCivileBySoggetto(long idSoggetto) {

		Query q = em.createNamedQuery("CsASoggettoStatoCivile.findBySoggetto")
				.setParameter("idSoggetto", idSoggetto);
		return q.getResultList();

	}
	
	public void saveSoggettoStatoCivile(CsASoggettoStatoCivile cs) {

		em.persist(cs);

	}
	
	public void updateSoggettoStatoCivile(CsASoggettoStatoCivile cs) {

		em.merge(cs);

	}
	
	public void eliminaSoggettoStatoCivileBySoggetto(Long soggettoId) {
		
		Query q = em.createNamedQuery("CsASoggettoStatoCivile.eliminaBySoggettoId");
		q.setParameter("id", soggettoId);
		q.executeUpdate();
		
	}
	
	//Status
	@SuppressWarnings("unchecked")
	public List<CsASoggettoStatus> getSoggettoStatusBySoggetto(long idSoggetto) {

		Query q = em.createNamedQuery("CsASoggettoStatus.findBySoggetto")
				.setParameter("idSoggetto", idSoggetto);
		return q.getResultList();

	}
	
	public void saveSoggettoStatus(CsASoggettoStatus cs) {

		em.persist(cs);

	}
	
	public void updateSoggettoStatus(CsASoggettoStatus cs) {

		em.merge(cs);

	}
	
	public void eliminaSoggettoStatusBySoggetto(Long soggettoId) {
		
		Query q = em.createNamedQuery("CsASoggettoStatus.eliminaBySoggettoId");
		q.setParameter("id", soggettoId);
		q.executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	public List<CsAAnagrafica> findAnagraficaFamigliaByIdSoggetto(Long soggettoId) {
		Query q = em.createNamedQuery("CsASoggettoStatus.findAnagraficaFamigliaByIdSoggetto")
				.setParameter("soggettoId", soggettoId);
		return q.getResultList();
	}
}
