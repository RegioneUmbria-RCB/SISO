package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.CasoSearchCriteria;
import it.webred.cs.csa.ejb.dto.ContatoreCasiDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.SearchRdCDTO;
import it.webred.cs.csa.ejb.dto.retvalue.DatiCasoListaDTO;
import it.webred.cs.csa.ejb.queryBuilder.SoggettoQueryBuilder;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAAnagraficaLog;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsAComponenteAnagCasoGit;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsASoggettoMedico;
import it.webred.cs.data.model.CsASoggettoStatoCivile;
import it.webred.cs.data.model.CsASoggettoStatus;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.view.CsRdcAnagraficaGepi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

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
		CsASoggettoLAZY cs = null;
		if(id!=null){
			Query q = em.createNamedQuery("CsASoggetto.findById");
			q.setParameter("Id", id);
			cs =  (CsASoggettoLAZY) q.getSingleResult();
		}
		return cs;
		
	}
	//SISO-1127 Inizio
		@SuppressWarnings("unchecked")
		public List<CsAComponenteAnagCasoGit> getAnagraficheCasiGitAggiornate() {
			List<CsAComponenteAnagCasoGit> s = new ArrayList<CsAComponenteAnagCasoGit>();
			Query q;
			try{
				q = em.createNamedQuery("CsAComponenteAnagCasoGit.getAnagraficheAggiornate");
				s = q.getResultList();
			
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new CarSocialeServiceException(e);
			}
			return s;
		}
		
		@SuppressWarnings("unchecked")
		public CsAComponenteAnagCasoGit getAnagraficaCasoGitAggiornatoBySoggettoId(Long idAnagraficaSoggetto, Date dataUltMod) {
			List<CsAComponenteAnagCasoGit> s = new ArrayList<CsAComponenteAnagCasoGit>();
			Query q;
			try{
				q = em.createNamedQuery("CsAComponenteAnagCasoGit.getAnagraficaAggiornataBySoggId");
				q.setParameter("idSoggettoAnagrafica", idAnagraficaSoggetto);
				q.setParameter("dtVal", dataUltMod);
				s = q.getResultList();
			
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new CarSocialeServiceException(e);
			}
			if(s.size()>0)
				return s.get(0);
			else 
				return null;
		}
		public void updateAnagraficheCasiGitGit(CsAComponenteAnagCasoGit caso) {
			try{
				em.merge(caso);
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new CarSocialeServiceException(e);
			}
		}
		
		public void updateAnagraficaCasoGIT(CsAComponenteAnagCasoGit anagraficaCasoGIT) {
			try{
				em.merge(anagraficaCasoGIT);
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new CarSocialeServiceException(e);
			}
		}
		public void addAnagraficaCasoGIT(CsAComponenteAnagCasoGit anagraficaCasoGIT) {
			try{
				em.persist(anagraficaCasoGIT);
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new CarSocialeServiceException(e);
			}
		}
		public List<CsAComponenteAnagCasoGit> getAnagraficaSoggettoGitByProvenienzaId(String provenienza){
			
			Query q = em.createNamedQuery("CsAComponenteAnagCasoGit.getAnagraficaCasoGitByProvenienza");
			q.setParameter("provenienza", provenienza);
			List<CsAComponenteAnagCasoGit> lst =  q.getResultList();
			 return lst;
			
		}
		//SISO-1266
		public List<CsAComponenteAnagCasoGit> getAnagraficaSoggettoGitByEscudiProvenienzaId(){
			
			Query q = em.createNamedQuery("CsAComponenteAnagCasoGit.getAnagraficaSoggettoGitByEscudiProvenienzaId");
		 	List<CsAComponenteAnagCasoGit> lst =  q.getResultList();
			 return lst;
		 	//DEBUG
			// return lst.subList(0, 1);
				
		}
	   public CsAComponenteAnagCasoGit getAnagraficaSoggettoGitBySoggettoId(Long idAnagraficaSoggetto){
			
			Query q = em.createNamedQuery("CsAComponenteAnagCasoGit.getAnagraficaCasoGitByIdSoggetto");
			q.setParameter("idSoggettoAnagrafica", idAnagraficaSoggetto);
			
			List<CsAComponenteAnagCasoGit> lst =  q.getResultList();
			if(lst.size()>0)
				return lst.get(0);
			else 
				return null;
		}
		
		//SISO-1127 Fine
	
	@SuppressWarnings("unchecked")
	public CsASoggettoLAZY getSoggettoByCF(String cf) {
		List<CsASoggettoLAZY> lista = this.getSoggettiByCF(cf);
		if(lista.size() > 0)
			return lista.get(0);
		
		return null;

	}
	
	@SuppressWarnings("unchecked")
	public List<CsASoggettoLAZY> getSoggettiByCF(String cf) {
		List<CsASoggettoLAZY> lista = new ArrayList<CsASoggettoLAZY>();
		if(!StringUtils.isBlank(cf)){
			Query q = em.createNamedQuery("CsASoggetto.findByCF");
			q.setParameter("cf", cf);
			lista =  q.getResultList();
		}
		return lista;
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
	
	public CsASoggettoLAZY updateSoggetto(CsASoggettoLAZY soggetto) {

		return em.merge(soggetto);

	}
	
	@SuppressWarnings("unchecked")
	public List<CsASoggettoCategoriaSoc> getSoggCategorieBySoggetto(long idSoggetto) {

		Query q = em.createNamedQuery("CsASoggettoCategoriaSoc.findBySoggetto")
				.setParameter("idSoggetto", idSoggetto);
		return q.getResultList();

	}
	

	
	@SuppressWarnings("unchecked")
	public List<CsASoggettoCategoriaSoc> getSoggCategorieAttualiBySoggetto(long idSoggetto) {
		Query q = em.createNamedQuery("CsASoggettoCategoriaSoc.findAttualiBySoggetto");
		q.setParameter("idSoggetto", idSoggetto);
		q.setParameter("dtRif", new Date());
		List<CsASoggettoCategoriaSoc> lst = q.getResultList();
		return lst;
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
		String sql = "";
		SoggettoQueryBuilder qb = new SoggettoQueryBuilder(criteria);
		if(criteria.getPermessiScheda())
			sql = qb.createQueryListaCasiAssegnati(false);
		else
			sql = qb.createQueryListaCasi(false);
		
		logger.info("getCasiSoggetto SQL LISTA CASI[" + sql+"]");
		
		Query q = em.createNativeQuery(sql);
		String params = qb.setParameters(q);
		logger.info("getCasiSoggetto PARAMS LISTA CASI first["+first+"] pageSize["+pagesize+"]" + params );
		
		if(first != null)
			q.setFirstResult(first);
		if(pagesize != null)
			q.setMaxResults(pagesize);

		List<Object[]> lista = q.getResultList();
		for(Object[] objArr: lista) {
			int index = 0;
			Long idAnagrafica = ((BigDecimal) objArr[index++]).longValue();
			Long idCaso = ((BigDecimal) objArr[index++]).longValue();
			Long identificativoCaso = ((BigDecimal) objArr[index++]).longValue();
			String denominazione = (String)objArr[index++];
			Date dataNascita = (Date)objArr[index++];
			String cf = (String)objArr[index++];
			Date dataApertura = (Date)objArr[index++];
			
			String residenza = (String)objArr[index++];
			Date dataCreazione = (Date)objArr[index++];
			BigDecimal cfgItStato = (BigDecimal)objArr[index++];
			BigDecimal existsInterventi = (BigDecimal)objArr[index++];
			
			DatiCasoListaDTO dc = new DatiCasoListaDTO();
			dc.setAnagraficaId(idAnagrafica);
			dc.setCasoId(idCaso);
			dc.setIdentificativo(identificativoCaso);
			dc.setDenominazione(denominazione);
			dc.setCf(cf);
			dc.setDataNascita(dataNascita);
			dc.setDataApertura(dataApertura);
			dc.setResidenza(residenza);
			dc.setExistsInterventi(BigDecimal.ZERO.equals(existsInterventi) ? false : true);
			lstCasi.add(dc);
		}
		
		logger.info("restituisco getCasiSoggetto "+ lista.size());
		
		}catch(Throwable e){
			logger.error("Errore recupero lista casi - getCasiSoggetto["+e.getMessage()+"]", e);
			throw e;
		}
		
		return lstCasi;
	}
	public List<CsRdcAnagraficaGepi> getAnagraficheRdCGepi(SearchRdCDTO criteria) throws Throwable {
		List<CsRdcAnagraficaGepi> lstCasi = new ArrayList<CsRdcAnagraficaGepi>();
		
		try{
			String sql = this.getQueryRdC(false, criteria);
			
			logger.info("getAnagraficheRdCGepi SQL LISTA RDC[" + sql+"]");
			
			Query q = em.createQuery(sql);
			q.setFirstResult(criteria.getFirst());
			q.setMaxResults(criteria.getPageSize());
	
			lstCasi = (List<CsRdcAnagraficaGepi>)q.getResultList();
			logger.info("restituisco anagrafiche RdCGePI "+ lstCasi.size());
			
		}catch(Throwable e){
			logger.error("Errore recupero lista anagrafiche RdCGePI ["+e.getMessage()+"]", e);
			throw e;
		}
		
		return lstCasi;
	}
	public Integer getAnagraficheRdCGepiCount(SearchRdCDTO criteria) {
		String sql = this.getQueryRdC(true, criteria);
		Query q = em.createQuery(sql);
		Long o = (Long) q.getSingleResult();
		return new Integer(o.intValue());
	}
	public Integer getCasiSoggettoCount(CasoSearchCriteria criteria) {
		String sql = "";	
		SoggettoQueryBuilder qb = new SoggettoQueryBuilder(criteria);
		if(criteria.getPermessiScheda())
			sql = qb.createQueryListaCasiAssegnati(true);
		else
			sql = qb.createQueryListaCasi(true);
		logger.info("getCasiSoggettoCount SQL LISTA CASI[" + sql+"]");
		Query q = em.createNativeQuery(sql);
		String params = qb.setParameters(q);
		logger.info("getCasiSoggettoCount PARAMS LISTA CASI " + params );
		BigDecimal o = (BigDecimal) q.getSingleResult();
		return new Integer(o.intValue());
	}
	
	public ContatoreCasiDTO getCasiPerCategoriaCount(CasoSearchCriteria criteria) {
		ContatoreCasiDTO counter = new ContatoreCasiDTO();
		 SoggettoQueryBuilder qb = new SoggettoQueryBuilder(criteria);
		 Query q = qb.createQueryCasiPerCategoria(em, true);
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
		CsAAnagrafica cs = null;
		if(id!=null)
			cs = em.find(CsAAnagrafica.class, id);
		else
			logger.error("getAnagraficaById - ID ANAGRAFICA non specificato");
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
		Query q = em.createNamedQuery("CsASoggettoStatoCivile.findBySoggetto");
		q.setParameter("idSoggetto", idSoggetto);
		return q.getResultList();

	}
	
	public CsASoggettoStatoCivile getSoggettoStatoCivileAllaDataBySoggetto(long idSoggetto, Date dtRif) {

		Query q = em.createNamedQuery("CsASoggettoStatoCivile.findAttualeBySoggetto");
		q.setParameter("idSoggetto", idSoggetto);
		q.setParameter("dtRif", dtRif);
		List<CsASoggettoStatoCivile> lst =  q.getResultList();
		if(!lst.isEmpty()) 
			return lst.get(0);
		return null;
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

	private String getQueryRdC(boolean count, SearchRdCDTO criteria) {
		
		String sql = "";
		String join ="";
		if(!count) {
			join = " left join fetch ss.csASoggetto s left join fetch s.csACaso c ";
			sql += "SELECT  ss ";
		}
		else sql += "SELECT COUNT(ss) ";
		
		sql += " FROM CsRdcAnagraficaGepi ss " + join +
				" WHERE ss.resComuneCod='"+criteria.getEnteId()+"' ";
		
		
		sql+= !StringUtils.isBlank(criteria.getCognome()) ? " AND UPPER(ss.cognome) like '%" + criteria.getCognome().trim().toUpperCase()+"%'" : "";
		sql+= !StringUtils.isBlank(criteria.getNome()) ? " AND UPPER(ss.nome) like '%" + criteria.getNome().trim().toUpperCase()+"%'" : "";
		sql+= !StringUtils.isBlank(criteria.getCf()) ? " AND UPPER(ss.cf) like '%" + criteria.getCf().trim().toUpperCase()+"%'" : "";
		
		if(!count)
			sql += " ORDER BY cognome, nome, (CASE WHEN ss.csASoggetto IS NULL THEN 0 ELSE 1 END) ";
		
		return sql;
	}

	public Boolean isBeneficiarioRdC(String cf) {
		boolean trovato = false;
		if(!StringUtils.isEmpty(cf)) {
			CsRdcAnagraficaGepi val = em.find(CsRdcAnagraficaGepi.class, cf.toUpperCase());
			trovato = val!=null;
		}
		return trovato;
	}
	
	public Boolean hasNucleoBeneficiarioRdC(Long idAnagrafica,String cf, Date dtRif) {
		boolean trovato = false;
		Date dtVal = dtRif!=null ? dtRif : DataModelCostanti.END_DATE;
		Query q = em.createNamedQuery("CsRdcAnagraficaGepi.findInNucleo");
		q.setParameter("idSoggetto", idAnagrafica);
		q.setParameter("cfSoggetto", cf.toUpperCase());
		q.setParameter("dtVal", dtVal);
		
		List<CsRdcAnagraficaGepi> lista = (List<CsRdcAnagraficaGepi>) q.getResultList();
		trovato = !lista.isEmpty();
			
		return trovato;
	}

}
