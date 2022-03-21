package it.webred.ct.data.access.basic.indice.oggetto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
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
import it.webred.ct.data.access.basic.indice.oggetto.dao.OggettoDAO;
import it.webred.ct.data.access.basic.indice.oggetto.dao.OggettoQueryBuilder;
import it.webred.ct.data.access.basic.indice.oggetto.dto.SitOggettoDTO;
import it.webred.ct.data.model.indice.SitEnteSorgente;
import it.webred.ct.data.model.indice.SitOggettoTotale;
import it.webred.ct.data.model.indice.SitOggettoUnico;

@Stateless
public class OggettoServiceBean extends IndiceBaseService implements IndiceService{

	
	@Autowired
	private OggettoDAO oggettoDAO;
	
	@PostConstruct
	private void init() {
		super.setIndiceBaseDAO((IndiceBaseDAO) oggettoDAO);
	}
	
	@Override
	//public List<SitOggettoUnico> getListaUnico(IndiceSearchCriteria criteria, int startm, int numberRecord) {
	public List<SitOggettoUnico> getListaUnico(IndiceDataIn indDataIn) {
		
		IndiceSearchCriteria criteria = indDataIn.getListaUnico().getCriteria();
		int startm = indDataIn.getListaUnico().getStartm();
		int numberRecord = indDataIn.getListaUnico().getNumberRecord();
		
		List<SitOggettoUnico> result = new ArrayList<SitOggettoUnico>();
		
		try{
			String sql = (new OggettoQueryBuilder(criteria)).createQueryUnico(false);
			
			if (sql == null)
				return result;
			
			/*Query q = manager.createQuery(sql);
			if(startm != 0 || numberRecord != 0){
				q.setFirstResult(startm);
				q.setMaxResults(numberRecord);
			}
			
			return q.getResultList();*/
			
			return oggettoDAO.getListaUnico(startm, numberRecord, sql);
			
		}catch(Throwable t) {
			throw new IndiceServiceException(t);
		}
	}
	
	@Override
	public Long getListaUnicoRecordCount(IndiceSearchCriteria criteria) {

		Long ol = 0L;

		try {

			String sql = (new OggettoQueryBuilder(criteria)).createQueryUnico(true);
			
			logger.debug("COUNT LISTA UNICO - SQL["+sql+"]");
			
			if (sql != null) {
				
				/*Query q = manager.createQuery(sql);
				Object o = q.getSingleResult();
				ol = (Long) o;*/
				
				ol = oggettoDAO.getListaUnicoRecordCount(sql);
				
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
	public List<SitSorgenteDTO> getListaTotale(IndiceDataIn indDataIn){
		
		IndiceSearchCriteria criteria = indDataIn.getListaTotale().getCriteria();
		int startm = indDataIn.getListaTotale().getStartm();
		int numberRecord = indDataIn.getListaTotale().getNumberRecord();
		
		List<SitSorgenteDTO> resultDTO = new ArrayList<SitSorgenteDTO>();
		
		try{
			String sql = (new OggettoQueryBuilder(criteria)).createQueryEntiByUnico(criteria.getUnicoId());
			logger.debug("LISTA ENTI PROGRESSIVI BY UNICO SQL["+sql+"]");
			
			if (sql != null) {
				/*Query q = manager.createQuery(sql);
				if(startm != 0 || numberRecord != 0){
					q.setFirstResult(startm);
					q.setMaxResults(numberRecord);
				}

				List<Object[]> result = q.getResultList();*/
				
				List<Object[]> result = oggettoDAO.getListaTotale1(sql, startm, numberRecord);
				
				logger.debug("Result size ["+result.size()+"]");

				for (Object[] rs : result) {
					
					SitSorgenteDTO dto = new SitSorgenteDTO();
					dto.setEnteSorgente((SitEnteSorgente) rs[0]);
					dto.setProgressivoES(((Long)rs[1]).toString());
					AmFonteTipoinfo fonteTipoinfo = getFonteTipoinfo(new Integer((int) dto.getEnteSorgente().getId()), new BigDecimal((Long) rs[1]));
					dto.setInformazione(fonteTipoinfo.getInformazione());
					
					criteria.setEnteSorgenteId(""+dto.getEnteSorgente().getId());
					criteria.setProgressivoES(dto.getProgressivoES());
					String sql2 = (new OggettoQueryBuilder(criteria)).createQueryTotale(false, false);
					
					List resultTotali = new ArrayList();
					/*q = manager.createQuery(sql2);
					List<Object[]> result2 = q.getResultList();*/
					
					List<Object[]> result2 = oggettoDAO.getListaTotale2(sql2);
					
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
	
	private SitOggettoDTO associaCampi(Object[] oggetto, boolean addUnico){
		
		SitOggettoDTO ogg = new SitOggettoDTO();
		ogg.setCtrHash((String)oggetto[0]);
		ogg.setFoglio((String)oggetto[1]);
		ogg.setParticella((String)oggetto[2]);
		ogg.setSub((String)oggetto[3]);
		if(new BigDecimal(1).equals((BigDecimal)oggetto[4]))
			ogg.setValidato(true);
		else ogg.setValidato(false);
		ogg.setStato(statoMap.get((String)oggetto[5]));
		ogg.setLabel("Foglio: " + ogg.getFoglio() + " Particella: " + ogg.getParticella() + " Subalterno: " + ogg.getSub());
		
		if(addUnico){
			ogg.setSitOggettoUnico((SitOggettoUnico)oggetto[6]);
		}
		
		return ogg;
	}

	@Override
	public Long getListaTotaleRecordCount(IndiceSearchCriteria criteria) {
		Long ol = 0L;

		try {

			String sql = (new OggettoQueryBuilder(criteria)).createQueryEntiByUnico(criteria.getUnicoId());
			logger.debug("COUNT LISTA ENTI PROGRESSIVI - SQL["+sql+"]");

			if (sql != null) {
				/*Query q = manager.createQuery(sql);
				List<Object[]> result = q.getResultList();*/
				
				ol = oggettoDAO.getListaTotaleRecordCount(sql);
								
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
	//public List<SitOggettoDTO> getListaTotaleBySorgente(IndiceSearchCriteria criteria,int startm, int numberRecord) {
	public List<SitOggettoDTO> getListaTotaleBySorgente(IndiceDataIn indDataIn) {
		
		IndiceSearchCriteria criteria = indDataIn.getListaTotaleBySorgente().getCriteria();
		int startm = indDataIn.getListaTotaleBySorgente().getStartm();
		int numberRecord = indDataIn.getListaTotaleBySorgente().getNumberRecord();
		
		List<SitOggettoDTO> result = new ArrayList<SitOggettoDTO>();
		
		try{
			String sql = (new OggettoQueryBuilder(criteria)).createQueryTotale(false, true);
			
			if (sql == null)
				return result;
			
			/*Query q = manager.createQuery(sql);
			if(startm != 0 || numberRecord != 0){
				q.setFirstResult(startm);
				q.setMaxResults(numberRecord);
			}
			
			List<Object[]> resultQuery = q.getResultList();*/
			
			List<Object[]> resultQuery = oggettoDAO.getListaTotaleBySorgente(sql, startm, numberRecord);
			
			for (Object[] rs : resultQuery) {
				
				SitOggettoDTO o = associaCampi(rs, true);
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

			String sql = (new OggettoQueryBuilder(criteria)).createQueryTotale(true, true);
			
			logger.debug("COUNT LISTA TOTALE - SQL["+sql+"]");
			
			if (sql != null) {
				
				/*Query q = manager.createQuery(sql);
				Object o = q.getSingleResult();
				ol = (Long) o;*/
				
				Object o = oggettoDAO.getListaTotaleRecordCount(sql);
				
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
			logger.debug("VALIDAZIONE SIT_OGGETTO_TOTALE");
			
			/*Query q = manager.createNamedQuery("SitOggettoTotale.getOggettoTotaleByHashByEs");
			q.setParameter("hash", criteria.getHash());
			q.setParameter("enteSorgente", criteria.getIdSorgente());
			q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
			List<SitOggettoTotale> result = q.getResultList();*/
			
			List<SitOggettoTotale> result = oggettoDAO.validaSitOggettoTotale(criteria);
			
			for (SitOggettoTotale ogg : result) {
				ogg.setValidato(new BigDecimal(1));
				ogg.setStato("C");
				//manager.merge(ogg);
				oggettoDAO.mergeOggetto(ogg);
			}
			
		}catch (Throwable t) {
			logger.error("",t);
			throw new IndiceServiceException(t);
		}
		
	}

	@Override
	public void invalidaSitTotale(IndiceOperationCriteria criteria) {
		
		try{
			logger.debug("INVALIDAZIONE SIT_OGGETTO_TOTALE");
			
			/*Query q = manager.createNamedQuery("SitOggettoTotale.getOggettoTotaleByHashByEs");
			q.setParameter("hash", criteria.getHash());
			q.setParameter("enteSorgente", criteria.getIdSorgente());
			q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
			List<SitOggettoTotale> result = q.getResultList();*/
			
			List<SitOggettoTotale> result = oggettoDAO.invalidaSitOggettoTotale(criteria);
			
			for (SitOggettoTotale ogg : result) {
				ogg.setValidato(new BigDecimal(0));
				ogg.setStato("A");
				//manager.merge(ogg);
				oggettoDAO.mergeOggetto(ogg);
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
			logger.debug("CAMBIO UNICO SIT_OGGETTO_TOTALE");
			BigDecimal id = new BigDecimal(nuovoIdUnico);
			
			if(indDataIn.getCambiaUnico().getNativaOld() != null){
				
				/*Query q = manager.createNamedQuery("SitOggettoTotale.getOggettoTotaleByUnico");
				q.setParameter("idUnico", idUnicoOld);
				List<SitOggettoTotale> result = q.getResultList();*/
				
				List<SitOggettoTotale> result = oggettoDAO.getOggettoTotaleByUnico(indDataIn.getCambiaUnico().getNativaOld());
				
				for (SitOggettoTotale ogg : result) {
					ogg.setFkOggetto(id);
					ogg.setValidato(new BigDecimal(1));
					ogg.setStato("C");
					//manager.merge(ogg);
					oggettoDAO.mergeOggetto(ogg);
				}
				
			}else{
			
				/*Query q = manager.createNamedQuery("SitOggettoTotale.getOggettoTotaleByHashByEs");
				q.setParameter("hash", criteria.getHash());
				q.setParameter("enteSorgente", criteria.getIdSorgente());
				q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
				List<SitOggettoTotale> result = q.getResultList();*/
				
				List<SitOggettoTotale> result = oggettoDAO.getOggettoTotaleByHashByEs(criteria);
				
				for (SitOggettoTotale ogg : result) {
					ogg.setFkOggetto(id);
					ogg.setValidato(new BigDecimal(1));
					ogg.setStato("C");
					//manager.merge(ogg);
					oggettoDAO.mergeOggetto(ogg);
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
			logger.debug("ASSOCIAZIONE A NUOVO UNICO SIT_OGGETTO_TOTALE");
			
			/*Query q = manager.createNamedQuery("SitOggettoTotale.getOggettoTotaleByHashByEs");
			q.setParameter("hash", criteria.getHash());
			q.setParameter("enteSorgente", criteria.getIdSorgente());
			q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
			List<SitOggettoTotale> result = q.getResultList();*/
			
			List<SitOggettoTotale> result = oggettoDAO.getOggettoTotaleByHashByEs(criteria);
			
			if(result != null && result.size() > 0){
				
				SitOggettoTotale example = result.get(0);
				SitOggettoUnico nuovo = new SitOggettoUnico();
				nuovo.setDtIns(getCurrentDate());
				nuovo.setFoglio(example.getFoglio());
				nuovo.setParticella(example.getParticella());
				nuovo.setSub(example.getSub());
				
				nuovo.setValidato(new BigDecimal(0));
				
				//manager.persist(nuovo);
				oggettoDAO.persist(nuovo);
				
				
				for (SitOggettoTotale ogg : result) {
					ogg.setFkOggetto(new BigDecimal(nuovo.getIdOggetto()));
					ogg.setRelDescr("NATIVA");
					ogg.setValidato(new BigDecimal(1));
					ogg.setStato("C");
					//manager.merge(ogg);
					oggettoDAO.mergeOggetto(ogg);
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
			logger.debug("CONTROLLO NATIVO SIT_OGGETTO_TOTALE");
			/*Query q = manager.createNamedQuery("SitOggettoTotale.getOggettoTotaleNativaByHash");
			q.setParameter("hash", criteria.getHash());
			List<SitOggettoTotale> result = q.getResultList();*/
			
			List<SitOggettoTotale> result = oggettoDAO.getOggettoTotaleNativaByHash(criteria);
			
			if(result != null && result.size() > 0){
				return result.get(0).getFkOggetto();
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
			logger.debug("RIMOZIONE SIT_OGGETTO_UNICO");
			/*Query q = manager.createNamedQuery("SitOggettoUnico.deleteById");
			q.setParameter("id", id);
			int deleted = q.executeUpdate();*/
			
			int deleted = oggettoDAO.deleteById(id);
			
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
			logger.debug("AGGREGAZIONE UNICI SIT_OGGETTO_UNICO");
			
			//creazione nuovo unico
			SitOggettoUnico nuovo = sitNuovoDTO.getSitOggettoUnico();
			String foglio = nuovo.getFoglio();
			String particella = nuovo.getParticella();
			String sub = nuovo.getSub();
			nuovo.setFoglio(String.format("%4s", foglio).replace(' ', '0'));
			nuovo.setParticella(String.format("%5s", particella).replace(' ', '0'));
			nuovo.setSub(String.format("%5s", sub).replace(' ', '0'));
			nuovo.setDtIns(getCurrentDate());
			nuovo.setValidato(new BigDecimal(0));
			//manager.persist(nuovo);
			oggettoDAO.persist(nuovo);
			
			
			//associazione totali a nuovo unico
			/*Query q = manager.createNamedQuery("SitOggettoTotale.getOggettoTotaleByUnico");
			q.setParameter("idUnico", idUno);
			List<SitOggettoTotale> result = q.getResultList();*/
			
			List<SitOggettoTotale> result = oggettoDAO.getOggettoTotaleByUnico(idUno);
			
			
			/*q = manager.createNamedQuery("SitOggettoTotale.getOggettoTotaleByUnico");
			q.setParameter("idUnico", idDue);*/
			
			List<SitOggettoTotale> lot = oggettoDAO.getOggettoTotaleByUnico(idDue);
			
			result.addAll(lot);
						
			for (SitOggettoTotale ogg : result) {
				ogg.setFkOggetto(new BigDecimal(nuovo.getIdOggetto()));				
				oggettoDAO.mergeOggetto(ogg);
			}
			
		}catch (Throwable t) {
			logger.error("",t);
			throw new IndiceServiceException(t);
		}
		
	}
	
}
