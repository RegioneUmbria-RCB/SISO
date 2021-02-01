package it.webred.ct.data.access.basic.indice.soggetto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import it.webred.ct.config.model.AmFonteTipoinfo;
import it.webred.ct.data.access.basic.indice.IndiceBaseService;
import it.webred.ct.data.access.basic.indice.IndiceDataIn;
import it.webred.ct.data.access.basic.indice.IndiceService;
import it.webred.ct.data.access.basic.indice.IndiceServiceException;
import it.webred.ct.data.access.basic.indice.dao.IndiceBaseDAO;
import it.webred.ct.data.access.basic.indice.dto.IndiceOperationCriteria;
import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;
import it.webred.ct.data.access.basic.indice.dto.SitNuovoDTO;
import it.webred.ct.data.access.basic.indice.dto.SitSorgenteDTO;
import it.webred.ct.data.access.basic.indice.soggetto.dao.SoggettoDAO;
import it.webred.ct.data.access.basic.indice.soggetto.dao.SoggettoQueryBuilder;
import it.webred.ct.data.access.basic.indice.soggetto.dto.SitSoggettoDTO;
import it.webred.ct.data.access.basic.indice.via.dao.ViaDAO;
import it.webred.ct.data.model.indice.SitEnteSorgente;
import it.webred.ct.data.model.indice.SitSoggettoTotale;
import it.webred.ct.data.model.indice.SitSoggettoUnico;
import it.webred.ct.data.model.indice.SitViaUnico;

@Stateless
public class SoggettoServiceBean extends IndiceBaseService implements IndiceService{

	
	@Autowired
	private SoggettoDAO soggettoDAO;
	
	@PostConstruct
	private void init() {
		super.setIndiceBaseDAO((IndiceBaseDAO) soggettoDAO);
	}
	
	@Override
	//public List<SitSoggettoUnico> getListaUnico(IndiceSearchCriteria criteria, int startm, int numberRecord) {
	public List<SitSoggettoUnico> getListaUnico(IndiceDataIn indDataIn) {
		
		IndiceSearchCriteria criteria = indDataIn.getListaUnico().getCriteria();
		int startm = indDataIn.getListaUnico().getStartm();
		int numberRecord = indDataIn.getListaUnico().getNumberRecord();
		
		List<SitSoggettoUnico> result = new ArrayList<SitSoggettoUnico>();
		
		try{
			String sql = (new SoggettoQueryBuilder(criteria)).createQueryUnico(false);
			
			if (sql == null)
				return result;
			
			/*Query q = manager.createQuery(sql);
			if(startm != 0 || numberRecord != 0){
				q.setFirstResult(startm);
				q.setMaxResults(numberRecord);
			}
			
			return q.getResultList();*/
			
			List<SitSoggettoUnico> res = soggettoDAO.getListaUnico(startm, numberRecord, sql);
			
			return res;
			
		}catch(Throwable t) {
			throw new IndiceServiceException(t);
		}
	}
	
	@Override
	public Long getListaUnicoRecordCount(IndiceSearchCriteria criteria) {

		Long ol = 0L;

		try {

			String sql = (new SoggettoQueryBuilder(criteria)).createQueryUnico(true);
			
			logger.debug("COUNT LISTA UNICO - SQL["+sql+"]");
			
			if (sql != null) {
				
				/*Query q = manager.createQuery(sql);
				Object o = q.getSingleResult();
				ol = (Long) o;*/
				
				ol = soggettoDAO.getListaUnicoRecordCount(sql);
				
				logger.debug("COUNT LISTA UNICO ["+ol+"]");
				
			}
				
		} catch (NoResultException nre) {
			
			logger.warn("Result size [0] " + nre.getMessage());

		} catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}

		return ol;
	}

	@Override
	//public List<SitSorgenteDTO> getListaTotale(IndiceSearchCriteria criteria, int startm,int numberRecord) {
	public List<SitSorgenteDTO> getListaTotale(IndiceDataIn indDataIn) {
		
		IndiceSearchCriteria criteria = indDataIn.getListaTotale().getCriteria();
		int startm = indDataIn.getListaTotale().getStartm();
		int numberRecord = indDataIn.getListaTotale().getNumberRecord();
		
		List<SitSorgenteDTO> resultDTO = new ArrayList<SitSorgenteDTO>();
		
		try{
			String sql = (new SoggettoQueryBuilder(criteria)).createQueryEntiByUnico(criteria.getUnicoId());
			logger.debug("LISTA ENTI PROGRESSIVI BY UNICO SQL["+sql+"]");
			
			if (sql != null) {
				
				/*Query q = manager.createQuery(sql);
				if(startm != 0 || numberRecord != 0){
					q.setFirstResult(startm);
					q.setMaxResults(numberRecord);
				}

				List<Object[]> result = q.getResultList();*/
				
				List<Object[]> result = soggettoDAO.getListaTotale1(sql, startm, numberRecord);
				
				logger.debug("Result size ["+result.size()+"]");

				for (Object[] rs : result) {
					
					SitSorgenteDTO dto = new SitSorgenteDTO();
					dto.setEnteSorgente((SitEnteSorgente) rs[0]);
					dto.setProgressivoES(((Long)rs[1]).toString());
					AmFonteTipoinfo fonteTipoinfo = getFonteTipoinfo(new Integer((int) dto.getEnteSorgente().getId()), new BigDecimal((Long) rs[1]));
					dto.setInformazione(fonteTipoinfo.getInformazione());
					
					criteria.setEnteSorgenteId(""+dto.getEnteSorgente().getId());
					criteria.setProgressivoES(dto.getProgressivoES());
					String sql2 = (new SoggettoQueryBuilder(criteria)).createQueryTotale(false, false);
					
					List<Object> resultTotali = new ArrayList<Object>();
					
					/*q = manager.createQuery(sql2);
					List<Object[]> result2 = q.getResultList();*/
					
					List<Object[]> result2 = soggettoDAO.getListaTotale2(sql2);
					
					for (Object[] rs2 : result2) {
						
						Object o = associaCampi(rs2, false);
						resultTotali.add(o);
						
					}
					
					dto.setListaTotali(resultTotali);
					resultDTO.add(dto);
				}
			}
			
		}catch(Throwable t) {
			throw new IndiceServiceException(t);
		}
		
		return resultDTO;
	}
	
	private SitSoggettoDTO associaCampi(Object[] oggetto, boolean addUnico){
		
		SitSoggettoDTO sogg = new SitSoggettoDTO();
		sogg.setCtrHash((String)oggetto[0]);
		sogg.setDenominazione((String)oggetto[1]);
		if(sogg.getDenominazione() == null)
			sogg.setDenominazione((String)oggetto[2] + " " + (String)oggetto[3]);
		sogg.setCodFis((String)oggetto[4]);
		sogg.setpIva((String)oggetto[5]);
		sogg.setDataNascita((Date)oggetto[6]);
		sogg.setComuneNascita((String)oggetto[7]);
		if(new BigDecimal(1).equals((BigDecimal)oggetto[8]))
			sogg.setValidato(true);
		else sogg.setValidato(false);
		sogg.setStato(statoMap.get((String)oggetto[9]));
		sogg.setLabel(sogg.getDenominazione());
		sogg.setFkEnteSorgente(((Long) oggetto[10]).longValue());

		
		if(addUnico){
			sogg.setSitSoggettoUnico((SitSoggettoUnico)oggetto[11]);
		}
		
		return sogg;
	}

	@Override
	public Long getListaTotaleRecordCount(IndiceSearchCriteria criteria) {
		Long ol = 0L;

		try {

			String sql = (new SoggettoQueryBuilder(criteria)).createQueryEntiByUnico(criteria.getUnicoId());
			logger.debug("COUNT LISTA ENTI PROGRESSIVI - SQL["+sql+"]");

			if (sql != null) {
				
				/*Query q = manager.createQuery(sql);
				List<Object[]> result = q.getResultList();*/
				
				ol = soggettoDAO.getListaTotaleRecordCount(sql);
								
				logger.debug("COUNT LISTA ENTI PROGRESSIVI ["+ol+"]");
				
			}
				
		} catch (NoResultException nre) {
			
			logger.warn("Result size [0] " + nre.getMessage());

		} catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}

		return ol;
	}

	@Override
	//public List<SitSoggettoDTO> getListaTotaleBySorgente(IndiceSearchCriteria criteria, int startm, int numberRecord) {
	public List<SitSoggettoDTO> getListaTotaleBySorgente(IndiceDataIn indDataIn) {
		
		
		IndiceSearchCriteria criteria = indDataIn.getListaTotaleBySorgente().getCriteria();
		int startm = indDataIn.getListaTotaleBySorgente().getStartm();
		int numberRecord = indDataIn.getListaTotaleBySorgente().getNumberRecord();
		
		List<SitSoggettoDTO> result = new ArrayList<SitSoggettoDTO>();
		
		try{
			String sql = (new SoggettoQueryBuilder(criteria)).createQueryTotale(false, true);
			
			if (sql == null)
				return result;
			
			/*Query q = manager.createQuery(sql);
			if(startm != 0 || numberRecord != 0){
				q.setFirstResult(startm);
				q.setMaxResults(numberRecord);
			}
			
			List<Object[]> resultQuery = q.getResultList();*/
			
			List<Object[]> resultQuery = soggettoDAO.getListaTotaleBySorgente(sql, startm, numberRecord);
			
			
			for (Object[] rs : resultQuery) {
				
				SitSoggettoDTO o = associaCampi(rs, true);
				result.add(o);
				
			}
			
			return result;
		}catch(Throwable t) {
			throw new IndiceServiceException(t);
		}
	}

	@Override
	public Long getListaTotaleBySorgenteRecordCount(IndiceSearchCriteria criteria) {
		
		Long ol = 0L;

		try {

			String sql = (new SoggettoQueryBuilder(criteria)).createQueryTotale(true, true);
			
			logger.debug("COUNT LISTA TOTALE - SQL["+sql+"]");
			
			if (sql != null) {
				
				/*Query q = manager.createQuery(sql);
				Object o = q.getSingleResult();*/
				
				Object o = soggettoDAO.getListaTotaleRecordCount(sql);
				
				ol = (Long) o;
				
				logger.debug("COUNT LISTA TOTALE ["+ol+"]");
				
			}
				
		} catch (NoResultException nre) {
			
			logger.warn("Result size [0] " + nre.getMessage());

		} catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}

		return ol;
	}
	
	@Override
	public void validaSitTotale(IndiceOperationCriteria criteria) {

		try{
			logger.debug("VALIDAZIONE SIT_SOGGETTO_TOTALE");
			
			/*Query q = manager.createNamedQuery("SitSoggettoTotale.getSoggettoTotaleByHashByEs");
			q.setParameter("hash", criteria.getHash());
			q.setParameter("enteSorgente", criteria.getIdSorgente());
			q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
			List<SitSoggettoTotale> result = q.getResultList();*/
			
			List<SitSoggettoTotale> result = soggettoDAO.validaSitSoggettoTotale(criteria);
			
			
			for (SitSoggettoTotale sogg : result) {
				sogg.setValidato(new BigDecimal(1));
				sogg.setStato("C");
				//manager.merge(sogg);
				
				soggettoDAO.mergeSoggetto(sogg);
			}
			
		}catch (Throwable t) {
			logger.error("",t);
			throw new IndiceServiceException(t);
		}
		
	}

	@Override
	public void invalidaSitTotale(IndiceOperationCriteria criteria) {
		
		try{
			logger.debug("INVALIDAZIONE SIT_SOGGETTO_TOTALE");
			
			/*Query q = manager.createNamedQuery("SitSoggettoTotale.getSoggettoTotaleByHashByEs");
			q.setParameter("hash", criteria.getHash());
			q.setParameter("enteSorgente", criteria.getIdSorgente());
			q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
			List<SitSoggettoTotale> result = q.getResultList();*/
			
			List<SitSoggettoTotale> result = soggettoDAO.invalidaSitSoggettoTotale(criteria);
			
			for (SitSoggettoTotale sogg : result) {
				sogg.setValidato(new BigDecimal(0));
				sogg.setStato("A");
				//manager.merge(sogg);
				
				soggettoDAO.mergeSoggetto(sogg);
			}
			
		}catch (Throwable t) {
			logger.error("",t);
			throw new IndiceServiceException(t);
		}
		
	}

	@Override
	//public void cambiaUnico(IndiceOperationCriteria criteria, String nuovoIdUnico) {
	public void cambiaUnico(IndiceDataIn indDataIn) {
		
		IndiceOperationCriteria criteria = indDataIn.getCambiaUnico().getCriteria();
		String nuovoIdUnico = indDataIn.getCambiaUnico().getNuovoIdUnico();
		
		try{
			logger.debug("CAMBIO UNICO SIT_SOGGETTO_TOTALE");
			BigDecimal id = new BigDecimal(nuovoIdUnico);
			
			if(indDataIn.getCambiaUnico().getNativaOld() != null){
				
				/*Query q = manager.createNamedQuery("SitSoggettoTotale.getSoggettoTotaleByUnico");
				q.setParameter("idUnico", idUnicoOld);
				List<SitSoggettoTotale> result = q.getResultList();*/
				
				List<SitSoggettoTotale> result = soggettoDAO.getSoggettoTotaleByUnico(indDataIn.getCambiaUnico().getNativaOld());
				
				for (SitSoggettoTotale sogg : result) {
					sogg.setFkSoggetto(id);
					sogg.setValidato(new BigDecimal(1));
					sogg.setStato("C");
					//manager.merge(sogg);
					
					soggettoDAO.mergeSoggetto(sogg);
				}
				
			}else{
			
				/*Query q = manager.createNamedQuery("SitSoggettoTotale.getSoggettoTotaleByHashByEs");
				q.setParameter("hash", criteria.getHash());
				q.setParameter("enteSorgente", criteria.getIdSorgente());
				q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
				List<SitSoggettoTotale> result = q.getResultList();*/
				
				List<SitSoggettoTotale> result = soggettoDAO.getSoggettoTotaleByHashByEs(criteria);
				
				for (SitSoggettoTotale sogg : result) {
					sogg.setFkSoggetto(id);
					sogg.setValidato(new BigDecimal(1));
					sogg.setStato("C");
					//manager.merge(sogg);
					
					soggettoDAO.mergeSoggetto(sogg);
				}
			
			}
			
		}catch (Throwable t) {
			logger.error("",t);
			throw new IndiceServiceException(t);
		}
		
	}

	@Override
	public void associaANuovoUnico(IndiceOperationCriteria criteria) {
		
		try{
			logger.debug("ASSOCIAZIONE A NUOVO UNICO SIT_SOGGETTO_TOTALE");
			
			/*Query q = manager.createNamedQuery("SitSoggettoTotale.getSoggettoTotaleByHashByEs");
			q.setParameter("hash", criteria.getHash());
			q.setParameter("enteSorgente", criteria.getIdSorgente());
			q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
			List<SitSoggettoTotale> result = q.getResultList();*/
			
			List<SitSoggettoTotale> result = soggettoDAO.getSoggettoTotaleByHashByEs(criteria);
			
			if(result != null && result.size() > 0){
				
				SitSoggettoTotale example = result.get(0);
				SitSoggettoUnico nuovo = new SitSoggettoUnico();
				nuovo.setDtIns(getCurrentDate());
				nuovo.setCognome(example.getCognome());
				nuovo.setNome(example.getNome());
				nuovo.setDenominazione(example.getDenominazione());
				nuovo.setSesso(example.getSesso());
				nuovo.setCodfisc(example.getCodfisc());
				nuovo.setPi(example.getPi());
				nuovo.setDtNascita(example.getDtNascita());
				nuovo.setTipoPersona(example.getTipoPersona());
				nuovo.setCodProvinciaNascita(example.getCodProvinciaNascita());
				nuovo.setCodComuneNascita(example.getCodComuneNascita());
				nuovo.setDescProvinciaNascita(example.getDescProvinciaNascita());
				nuovo.setDescComuneNascita(example.getDescComuneNascita());
				nuovo.setCodProvinciaRes(example.getCodProvinciaRes());
				nuovo.setCodComuneRes(example.getCodComuneRes());
				nuovo.setDescProvinciaRes(example.getDescProvinciaRes());
				nuovo.setDescComuneRes(example.getDescComuneRes());
				
				nuovo.setValidato(new BigDecimal(0));
				//manager.persist(nuovo);
				
				soggettoDAO.persist(nuovo);
				
				
				for (SitSoggettoTotale sogg : result) {
					sogg.setFkSoggetto(new BigDecimal(nuovo.getIdSoggetto()));
					sogg.setRelDescr("NATIVA");
					sogg.setValidato(new BigDecimal(1));
					sogg.setStato("C");
					//manager.merge(sogg);
					
					soggettoDAO.mergeSoggetto(sogg);
				}
			}
			
		}catch (Throwable t) {
			logger.error("",t);
			throw new IndiceServiceException(t);
		}
		
	}

	@Override
	public BigDecimal getUnicoDaNativoTotale(IndiceOperationCriteria criteria) {
		
		try{
			logger.debug("CONTROLLO NATIVO SIT_SOGGETTO_TOTALE");
			/*Query q = manager.createNamedQuery("SitSoggettoTotale.getSoggettoTotaleNativaByHash");
			q.setParameter("hash", criteria.getHash());
			List<SitSoggettoTotale> result = q.getResultList();*/
			
			List<SitSoggettoTotale> result = soggettoDAO.getSoggettoTotaleNativaByHash(criteria);
			
			if(result != null && result.size() > 0){
				return result.get(0).getFkSoggetto();
			}
			
			return null;
		}catch (Throwable t) {
			logger.error("",t);
			throw new IndiceServiceException(t);
		}
	}
	
	
	@Override
	//public void cancellaUnicoById(long id) {
	public void cancellaUnicoById(IndiceDataIn indDataIn) {

		long id = (Long)indDataIn.getObj();
		
		_cancellaUnicoById(id);
	}
	
	
	private void _cancellaUnicoById(long id) {
		
		try {
			logger.debug("RIMOZIONE SIT_SOGGETTO_UNICO");
			/*Query q = manager.createNamedQuery("SitSoggettoUnico.deleteById");
			q.setParameter("id", id);
			int deleted = q.executeUpdate();*/
			
			int deleted = soggettoDAO.deleteById(id);
			
			logger.debug("Record eliminati ["+deleted+"]");
						
		} catch (Throwable t) {
			logger.error("",t);
			throw new IndiceServiceException(t);
		}
	}

	@Override
	//public void aggregaUnici(BigDecimal idUno, BigDecimal idDue, SitNuovoDTO sitNuovoDTO) {
	public void aggregaUnici(IndiceDataIn indDataIn) {

		BigDecimal idUno = indDataIn.getAggregaUnici().getIdUno();
		BigDecimal idDue = indDataIn.getAggregaUnici().getIdDue();
		SitNuovoDTO sitNuovoDTO = indDataIn.getAggregaUnici().getNuovoUnico();
		
		try{
			logger.debug("AGGREGAZIONE UNICI SIT_SOGGETTO_UNICO");
			
			//creazione nuovo unico
			SitSoggettoUnico nuovo = sitNuovoDTO.getSitSoggettoUnico();
			
			/*Query q = manager.createNamedQuery("SitSoggettoUnico.getSoggettoUnicoById");
			q.setParameter("id", idUno.longValue());
			SitSoggettoUnico example = (SitSoggettoUnico) q.getSingleResult();*/
			
			SitSoggettoUnico example = soggettoDAO.getSoggettoUnicoById(idUno);
			
			nuovo.setDtIns(getCurrentDate());
			nuovo.setValidato(new BigDecimal(0));
			nuovo.setCognome(example.getCognome());
			nuovo.setNome(example.getNome());
			nuovo.setSesso(example.getSesso());
			nuovo.setCodfisc(example.getCodfisc());
			nuovo.setPi(example.getPi());
			nuovo.setDtNascita(example.getDtNascita());
			nuovo.setTipoPersona(example.getTipoPersona());
			nuovo.setCodProvinciaNascita(example.getCodProvinciaNascita());
			nuovo.setCodComuneNascita(example.getCodComuneNascita());
			nuovo.setDescProvinciaNascita(example.getDescProvinciaNascita());
			nuovo.setDescComuneNascita(example.getDescComuneNascita());
			nuovo.setCodProvinciaRes(example.getCodProvinciaRes());
			nuovo.setCodComuneRes(example.getCodComuneRes());
			nuovo.setDescProvinciaRes(example.getDescProvinciaRes());
			nuovo.setDescComuneRes(example.getDescComuneRes());
			nuovo.setCtrlUtil(example.getCtrlUtil());
			
			//manager.persist(nuovo);
			soggettoDAO.persist(nuovo);
			
			//associazione totali a nuovo unico
			/*q = manager.createNamedQuery("SitSoggettoTotale.getSoggettoTotaleByUnico");
			q.setParameter("idUnico", idUno);
			List<SitSoggettoTotale> result = q.getResultList();*/
			
			List<SitSoggettoTotale> result = soggettoDAO.getSoggettoTotaleByUnico(idUno);
			
			/*q = manager.createNamedQuery("SitSoggettoTotale.getSoggettoTotaleByUnico");
			q.setParameter("idUnico", idDue);
			result.addAll(q.getResultList());*/
			
			List<SitSoggettoTotale> lst = soggettoDAO.getSoggettoTotaleByUnico(idDue);
			
			result.addAll(lst);
						
			for (SitSoggettoTotale sogg : result) {
				sogg.setFkSoggetto(new BigDecimal(nuovo.getIdSoggetto()));
				//manager.merge(sogg);
				soggettoDAO.mergeSoggetto(sogg);
			}
			
		}catch (Throwable t) {
			logger.error("",t);
			throw new IndiceServiceException(t);
		}
		
	}
	
}
