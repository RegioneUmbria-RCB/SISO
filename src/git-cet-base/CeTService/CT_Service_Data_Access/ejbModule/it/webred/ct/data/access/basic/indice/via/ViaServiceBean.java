package it.webred.ct.data.access.basic.indice.via;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;

import org.springframework.beans.factory.annotation.Autowired;
import it.webred.ct.config.model.AmFonteTipoinfo;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.indice.IndiceBaseService;
import it.webred.ct.data.access.basic.indice.IndiceDataIn;
import it.webred.ct.data.access.basic.indice.IndiceServiceException;
import it.webred.ct.data.access.basic.indice.IndiceService;
import it.webred.ct.data.access.basic.indice.civico.dao.CivicoDAO;
import it.webred.ct.data.access.basic.indice.civico.dao.CivicoQueryBuilder;
import it.webred.ct.data.access.basic.indice.civico.dto.RicercaCivicoIndiceDTO;
import it.webred.ct.data.access.basic.indice.civico.dto.SitCivicoDTO;
import it.webred.ct.data.access.basic.indice.dao.IndiceBaseDAO;
import it.webred.ct.data.access.basic.indice.dao.IndiceDAOException;
import it.webred.ct.data.access.basic.indice.dto.IndiceOperationCriteria;
import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;
import it.webred.ct.data.access.basic.indice.dto.SitNuovoDTO;
import it.webred.ct.data.access.basic.indice.dto.SitSorgenteDTO;
import it.webred.ct.data.access.basic.indice.via.dao.ViaDAO;
import it.webred.ct.data.access.basic.indice.via.dao.ViaQueryBuilder;
import it.webred.ct.data.access.basic.indice.via.dto.SitViaDTO;
import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.ct.data.model.indice.SitCivicoUnico;
import it.webred.ct.data.model.indice.SitEnteSorgente;
import it.webred.ct.data.model.indice.SitViaTotale;
import it.webred.ct.data.model.indice.SitViaUnico;

@Stateless
public class ViaServiceBean extends IndiceBaseService implements ViaService {

	@Autowired
	private ViaDAO viaDAO;
	
	@PostConstruct
	private void init() {
		super.setIndiceBaseDAO((IndiceBaseDAO) viaDAO);
	}
	
	@Override
	//public List<SitViaUnico> getListaUnico(IndiceSearchCriteria criteria, int startm, int numberRecord) {
	public List<SitViaUnico> getListaUnico(IndiceDataIn indDataIn) {
		
		IndiceSearchCriteria criteria = indDataIn.getListaUnico().getCriteria();
		int startm = indDataIn.getListaUnico().getStartm();
		int numberRecord = indDataIn.getListaUnico().getNumberRecord();
		
		//List<SitViaUnico> result = new ArrayList<SitViaUnico>();
		List<SitViaUnico> result = null;
		
		try {
			
			//result = viaDAO.getListaUnico(criteria, startm, numberRecord);
			
			String sql = (new ViaQueryBuilder(criteria)).createQueryUnico(false);

			if (sql == null)
				return result;

			/*Query q = manager.createQuery(sql);
			if (startm != 0 || numberRecord != 0) {
				q.setFirstResult(startm);
				q.setMaxResults(numberRecord);
			}

			return q.getResultList();*/
			
			List<SitViaUnico> res = viaDAO.getListaUnico(startm, numberRecord, sql);
			
			return res;
			
		} catch (Throwable t) {
			throw new IndiceServiceException(t);
		}
		
	}

	@Override
	public Long getListaUnicoRecordCount(IndiceSearchCriteria criteria) {

		Long ol = 0L;

		try {
			
			String sql = (new ViaQueryBuilder(criteria)).createQueryUnico(true);

			logger.debug("COUNT LISTA UNICO - SQL[" + sql + "]");

			if (sql != null) {

				/*Query q = manager.createQuery(sql);
				Object o = q.getSingleResult();
				ol = (Long) o;*/
				
				ol = viaDAO.getListaUnicoRecordCount(sql);

				logger.debug("COUNT LISTA UNICO [" + ol + "]");

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
	//public List<SitSorgenteDTO> getListaTotale(IndiceSearchCriteria criteria,int startm, int numberRecord) {
	public List<SitSorgenteDTO> getListaTotale(IndiceDataIn indDataIn) {
		
		IndiceSearchCriteria criteria = indDataIn.getListaTotale().getCriteria();
		int startm = indDataIn.getListaTotale().getStartm();
		int numberRecord = indDataIn.getListaTotale().getNumberRecord();
		
		List<SitSorgenteDTO> resultDTO = new ArrayList<SitSorgenteDTO>();

		try {
			String sql = (new ViaQueryBuilder(criteria)).createQueryEntiByUnico(criteria.getUnicoId());
			logger.debug("LISTA ENTI PROGRESSIVI BY UNICO SQL[" + sql + "]");

			if (sql != null) {
				
				/*Query q = manager.createQuery(sql);
				if (startm != 0 || numberRecord != 0) {
					q.setFirstResult(startm);
					q.setMaxResults(numberRecord);
				}

				List<Object[]> result = q.getResultList();*/
				
				List<Object[]> result = viaDAO.getListaTotale1(sql, startm, numberRecord);
								
				logger.debug("Result size [" + result.size() + "]");

				for (Object[] rs : result) {

					SitSorgenteDTO dto = new SitSorgenteDTO();
					dto.setEnteSorgente((SitEnteSorgente) rs[0]);
					dto.setProgressivoES(((Long) rs[1]).toString());
					AmFonteTipoinfo fonteTipoinfo = getFonteTipoinfo(new Integer((int) dto.getEnteSorgente().getId()), new BigDecimal((Long) rs[1]));
					dto.setInformazione(fonteTipoinfo.getInformazione());

					criteria.setEnteSorgenteId(""+ dto.getEnteSorgente().getId());
					criteria.setProgressivoES(dto.getProgressivoES());
					String sql2 = (new ViaQueryBuilder(criteria)).createQueryTotale(false, false);

					List resultTotali = new ArrayList();
					
					/*q = manager.createQuery(sql2);
					List<Object[]> result2 = q.getResultList();*/
					List<Object[]> result2 = viaDAO.getListaTotale2(sql2);
					
					for (Object[] rs2 : result2) {

						Object o = associaCampi(rs2, false);
						resultTotali.add(o);

					}

					dto.setListaTotali(resultTotali);
					resultDTO.add(dto);
				}
			}

		} catch (Throwable t) {
			throw new IndiceServiceException(t);
		}

		return resultDTO;
	}

	private SitViaDTO associaCampi(Object[] oggetto, boolean addUnico) {

		SitViaDTO via = new SitViaDTO();
		via.setCtrHash((String) oggetto[0]);
		via.setSedIme((String) oggetto[1]);
		if(via.getSedIme() == null)
			via.setSedIme("");
		via.setIndirizzo((String) oggetto[2]);
		if (new BigDecimal(1).equals((BigDecimal) oggetto[3]))
			via.setValidato(true);
		else
			via.setValidato(false);
		via.setStato(statoMap.get((String) oggetto[4]));
		via.setLabel(via.getSedIme() + " " + via.getIndirizzo());
		via.setFkEnteSorgente(((Long) oggetto[5]).longValue());
		
		if (addUnico) {
			via.setSitViaUnico((SitViaUnico) oggetto[6]);
		}

		return via;
	}

	@Override
	public Long getListaTotaleRecordCount(IndiceSearchCriteria criteria) {
		Long ol = 0L;

		try {

			String sql = (new ViaQueryBuilder(criteria)).createQueryEntiByUnico(criteria.getUnicoId());
			logger.debug("COUNT LISTA ENTI PROGRESSIVI - SQL[" + sql + "]");

			if (sql != null) {
				/*Query q = manager.createQuery(sql);
				List<Object[]> result = q.getResultList();*/
				ol = viaDAO.getListaTotaleRecordCount(sql);
				
				logger.debug("COUNT LISTA ENTI PROGRESSIVI [" + ol + "]");

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
	//public List<SitViaDTO> getListaTotaleBySorgente(IndiceSearchCriteria criteria, int startm, int numberRecord) {
	public List<SitViaDTO> getListaTotaleBySorgente(IndiceDataIn indDataIn) {

		IndiceSearchCriteria criteria = indDataIn.getListaTotaleBySorgente().getCriteria();
		int startm = indDataIn.getListaTotaleBySorgente().getStartm();
		int numberRecord = indDataIn.getListaTotaleBySorgente().getNumberRecord();
		
		List<SitViaDTO> result = new ArrayList<SitViaDTO>();

		try {
			String sql = (new ViaQueryBuilder(criteria)).createQueryTotale(false, true);

			if (sql == null)
				return result;
					
			/*Query q = manager.createQuery(sql);
			if (startm != 0 || numberRecord != 0) {
				q.setFirstResult(startm);
				q.setMaxResults(numberRecord);
			}

			List<Object[]> resultQuery = q.getResultList();*/
			
			List<Object[]> resultQuery = viaDAO.getListaTotaleBySorgente(sql, startm, numberRecord);
			
			for (Object[] rs : resultQuery) {

				SitViaDTO o = associaCampi(rs, true);
				result.add(o);

			}

			return result;
		} catch (Throwable t) {
			throw new IndiceServiceException(t);
		}
	}

	@Override
	public Long getListaTotaleBySorgenteRecordCount(IndiceSearchCriteria criteria) {

		Long ol = 0L;

		try {

			String sql = (new ViaQueryBuilder(criteria)).createQueryTotale(true, true);

			logger.debug("COUNT LISTA TOTALE - SQL[" + sql + "]");

			if (sql != null) {

				/*Query q = manager.createQuery(sql);
				Object o = q.getSingleResult();*/
				
				Object o = viaDAO.getListaTotaleRecordCount(sql);
				
				ol = (Long) o;

				logger.debug("COUNT LISTA TOTALE [" + ol + "]");

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

		try {
			logger.debug("VALIDAZIONE SIT_VIA_TOTALE");
			/*Query q = manager.createNamedQuery("SitViaTotale.getViaTotaleByHashByEs");
			q.setParameter("hash", criteria.getHash());
			q.setParameter("enteSorgente", criteria.getIdSorgente());
			q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
			List<SitViaTotale> result = q.getResultList();*/
			
			List<SitViaTotale> result = viaDAO.validaSitViaTotale(criteria);

			for (SitViaTotale via : result) {
				via.setValidato(new BigDecimal(1));
				via.setStato("C");
				
				//manager.merge(via);
				viaDAO.mergeVia(via);
				
								
				// validazione civici totale
				/*Query q2 = manager.createNamedQuery("SitCivicoTotale.getCivicoTotaleByViaTotale");
				q2.setParameter("idVia", via.getId().getIdDwh());
				List<SitCivicoTotale> result2 = q2.getResultList();*/
				
				List<SitCivicoTotale> result2 = viaDAO.validaSitCivicoTotale(via);

				for (SitCivicoTotale civicoTot : result2) {
					civicoTot.setValidato(new BigDecimal(1));
					civicoTot.setStato("C");
					//manager.merge(civicoTot);
					viaDAO.mergeCivico(civicoTot);
				}
			}

		} catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}

	}

	@Override
	public void invalidaSitTotale(IndiceOperationCriteria criteria) {

		try {
			logger.debug("INVALIDAZIONE SIT_VIA_TOTALE");
			/*Query q = manager.createNamedQuery("SitViaTotale.getViaTotaleByHashByEs");
			q.setParameter("hash", criteria.getHash());
			q.setParameter("enteSorgente", criteria.getIdSorgente());
			q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
			List<SitViaTotale> result = q.getResultList();*/
			
			List<SitViaTotale> result = viaDAO.invalidaSitViaTotale(criteria);
			
			
			for (SitViaTotale via : result) {
				via.setValidato(new BigDecimal(0));
				via.setStato("A");
				//manager.merge(via);
				viaDAO.mergeVia(via);
				
				// invalidazione civici totale
				/*Query q2 = manager.createNamedQuery("SitCivicoTotale.getCivicoTotaleByViaTotale");
				q2.setParameter("idVia", via.getId().getIdDwh());
				List<SitCivicoTotale> result2 = q2.getResultList();*/
				
				List<SitCivicoTotale> result2 = viaDAO.invalidaSitCivicoTotale(via);

				for (SitCivicoTotale civicoTot : result2) {
					civicoTot.setValidato(new BigDecimal(0));
					civicoTot.setStato("A");
					
					//manager.merge(civicoTot);
					viaDAO.mergeCivico(civicoTot);
				}
			}

		} catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}

	}

	@Override
	//public void cambiaUnico(IndiceOperationCriteria criteria,String nuovoIdUnico) {
	public void cambiaUnico(IndiceDataIn indDataIn) {

		IndiceOperationCriteria criteria = indDataIn.getCambiaUnico().getCriteria();
		String nuovoIdUnico = indDataIn.getCambiaUnico().getNuovoIdUnico();
		
		
		try {
			logger.debug("CAMBIO UNICO SIT_VIA_TOTALE");
			BigDecimal id = new BigDecimal(nuovoIdUnico);

			if (indDataIn.getCambiaUnico().getNativaOld() != null) {

				/*Query q = manager.createNamedQuery("SitViaTotale.getViaTotaleByUnico");
				q.setParameter("idUnico", idUnicoOld);
				List<SitViaTotale> result = q.getResultList();*/
				
				List<SitViaTotale> result = viaDAO.getViaTotaleByUnico(indDataIn.getCambiaUnico().getNativaOld());
				

				for (SitViaTotale via : result) {
					via.setFkVia(id);
					via.setValidato(new BigDecimal(1));
					via.setStato("C");
					
					//manager.merge(via);
					viaDAO.mergeVia(via);

					// associazione civici totale a nuovo unico
					/*Query q2 = manager.createNamedQuery("SitCivicoTotale.getCivicoTotaleByViaTotale");
					q2.setParameter("idVia", via.getId().getIdDwh());
					List<SitCivicoTotale> result2 = q2.getResultList();*/
					
					List<SitCivicoTotale> result2 = viaDAO.getCivicoTotaleByViaTotale(via);
					

					for (SitCivicoTotale civicoTot : result2) {
						civicoTot.setFkVia(id);
						civicoTot.setValidato(new BigDecimal(1));
						civicoTot.setStato("C");
						
						//manager.merge(civicoTot);
						viaDAO.mergeCivico(civicoTot);

						// associazione civici unico a nuovo unico
						/*Query q3 = manager.createNamedQuery("SitCivicoUnico.getCivicoUnicoById");
						q3.setParameter("id", civicoTot.getFkCivico().longValue());
						SitCivicoUnico civicoUni = (SitCivicoUnico) q3.getSingleResult();*/
						
						SitCivicoUnico civicoUni = viaDAO.getCivicoUnicoById(civicoTot);

						civicoUni.setFkVia(new BigDecimal(nuovoIdUnico));
						
						//manager.merge(civicoUni);
						viaDAO.mergeCivicoUnico(civicoUni);
					}
				}

			} else {

				/*Query q = manager.createNamedQuery("SitViaTotale.getViaTotaleByHashByEs");
				q.setParameter("hash", criteria.getHash());
				q.setParameter("enteSorgente", criteria.getIdSorgente());
				q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
				List<SitViaTotale> result = q.getResultList();*/
							
				List<SitViaTotale> result = viaDAO.getViaTotaleByHashByEs(criteria);
				
				

				for (SitViaTotale via : result) {
					via.setFkVia(id);
					via.setValidato(new BigDecimal(1));
					via.setStato("C");
					
					//manager.merge(via);
					viaDAO.mergeVia(via);

					// associazione civici totale a nuovo unico
					/*Query q2 = manager.createNamedQuery("SitCivicoTotale.getCivicoTotaleByViaTotale");
					q2.setParameter("idVia", via.getId().getIdDwh());
					List<SitCivicoTotale> result2 = q2.getResultList();*/
					
					List<SitCivicoTotale> result2 = viaDAO.getCivicoTotaleByViaTotale(via);
					
					for (SitCivicoTotale civicoTot : result2) {
						civicoTot.setFkVia(id);
						civicoTot.setValidato(new BigDecimal(1));
						civicoTot.setStato("C");
						
						//manager.merge(civicoTot);
						viaDAO.mergeCivico(civicoTot);

						// associazione civici unico a nuovo unico
						/*Query q3 = manager.createNamedQuery("SitCivicoUnico.getCivicoUnicoById");
						q3.setParameter("id", civicoTot.getFkCivico().longValue());
						SitCivicoUnico civicoUni = (SitCivicoUnico) q3.getSingleResult();*/
						
						SitCivicoUnico civicoUni = viaDAO.getCivicoUnicoById(civicoTot);
						
						civicoUni.setFkVia(new BigDecimal(nuovoIdUnico));
						
						//manager.merge(civicoUni);
						viaDAO.mergeCivicoUnico(civicoUni);
					}

				}

			}

		} catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}

	}

	@Override
	public void associaANuovoUnico(IndiceOperationCriteria criteria) {

		try {
			logger.debug("ASSOCIAZIONE A NUOVO UNICO SIT_VIA_TOTALE");
			/*Query q = manager.createNamedQuery("SitViaTotale.getViaTotaleByHashByEs");
			q.setParameter("hash", criteria.getHash());
			q.setParameter("enteSorgente", criteria.getIdSorgente());
			q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
			List<SitViaTotale> result = q.getResultList();*/
					
			List<SitViaTotale> result = viaDAO.getViaTotaleByHashByEs(criteria);

			if (result != null && result.size() > 0) {

				SitViaTotale example = result.get(0);
				SitViaUnico nuovo = new SitViaUnico();
				nuovo.setDtIns(getCurrentDate());
				nuovo.setSedime(example.getSedime());
				nuovo.setIndirizzo(example.getIndirizzo());
				nuovo.setValidato(new BigDecimal(0));
				
				//manager.persist(nuovo);
				viaDAO.persist(nuovo);

				for (SitViaTotale via : result) {
					via.setFkVia(new BigDecimal(nuovo.getIdVia()));
					via.setRelDescr("NATIVA");
					via.setValidato(new BigDecimal(1));
					via.setStato("C");
					
					//manager.merge(via);
					viaDAO.mergeVia(via);

					// associazione civici totale a nuovo unico
					/*Query q2 = manager.createNamedQuery("SitCivicoTotale.getCivicoTotaleByViaTotale");
					q2.setParameter("idVia", via.getId().getIdDwh());
					List<SitCivicoTotale> result2 = q2.getResultList();*/
	
					List<SitCivicoTotale> result2 = viaDAO.getCivicoTotaleByViaTotale(via);

					for (SitCivicoTotale civicoTot : result2) {
						civicoTot.setFkVia(new BigDecimal(nuovo.getIdVia()));
						civicoTot.setValidato(new BigDecimal(1));
						civicoTot.setStato("C");
						
						//manager.merge(civicoTot);
						viaDAO.mergeCivico(civicoTot);

						// associazione civici unico a nuovo unico
						/*Query q3 = manager.createNamedQuery("SitCivicoUnico.getCivicoUnicoById");
						q3.setParameter("id", civicoTot.getFkCivico().longValue());
						SitCivicoUnico civicoUni = (SitCivicoUnico) q3.getSingleResult();*/

						SitCivicoUnico civicoUni = viaDAO.getCivicoUnicoById(civicoTot);
						
						civicoUni.setFkVia(new BigDecimal(nuovo.getIdVia()));
						
						//manager.merge(civicoUni);
						viaDAO.mergeCivicoUnico(civicoUni);
					}
				}
			}

		} catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}

	}

	@Override
	public BigDecimal getUnicoDaNativoTotale(IndiceOperationCriteria criteria) {

		try {
			logger.debug("CONTROLLO NATIVO SIT_VIA_TOTALE");
			/*Query q = manager.createNamedQuery("SitViaTotale.getViaTotaleNativaByHash");
			q.setParameter("hash", criteria.getHash());
			List<SitViaTotale> result = q.getResultList();*/
						
			List<SitViaTotale> result = viaDAO.getViaTotaleNativaByHash(criteria);

			if (result != null && result.size() > 0) {
				return result.get(0).getFkVia();
			}

			return null;
		} catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}
	}

	@Override
	//public void cancellaUnicoById(long id) {
	public void cancellaUnicoById(IndiceDataIn indDataIn) {

		if(indDataIn.getAggregaUnici() != null){
			
			try {
				BigDecimal idUno = indDataIn.getAggregaUnici().getIdUno();
				BigDecimal idDue = indDataIn.getAggregaUnici().getIdDue();
				viaDAO.deleteCivicoUnicoByIdVie(idUno, idDue);
				
			} catch (Throwable t) {
				logger.error("", t);
				throw new IndiceServiceException(t);
			}
		}
		
		long id = (Long)indDataIn.getObj();
		
		_cancellaUnicoById(id);

	}
	
	private void _cancellaUnicoById(long id) {
		
		try {
			logger.debug("RIMOZIONE SIT_VIA_UNICO");
			/*Query q = manager.createNamedQuery("SitViaUnico.deleteById");
			q.setParameter("id", id);
			int deleted = q.executeUpdate();*/
			
			int deleted = viaDAO.deleteById(id);
			
			logger.debug("Record eliminati [" + deleted + "]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}
	}

	@Override
	public void aggregaUnici(IndiceDataIn indDataIn) {

		BigDecimal idUno = indDataIn.getAggregaUnici().getIdUno();
		BigDecimal idDue = indDataIn.getAggregaUnici().getIdDue();
		SitNuovoDTO sitNuovoDTO = indDataIn.getAggregaUnici().getNuovoUnico();
		
		try {
			logger.debug("AGGREGAZIONE UNICI SIT_VIA_UNICO");

			// creazione nuovo unico
			SitViaUnico nuovo = sitNuovoDTO.getSitViaUnico();
			nuovo.setDtIns(getCurrentDate());
			nuovo.setValidato(new BigDecimal(0));
			
			//manager.persist(nuovo);
			viaDAO.persist(nuovo);

			// associazione totali a nuovo unico			
			List<SitViaTotale> result = viaDAO.getViaTotaleByUnico(idUno);
			
			List<SitViaTotale> vieBtUnico = viaDAO.getViaTotaleByUnico(idDue);
			result.addAll(vieBtUnico);

			
			for (SitViaTotale via : result) {
				via.setFkVia(new BigDecimal(nuovo.getIdVia()));
				
				viaDAO.mergeVia(via);
			}
			
			// associazione civici totali a nuovo civico unico
			List<String> civiciUniciDaAggregare = viaDAO.getDistinctCivicoUnicoByIdVie(idUno, idDue);
			
			for(String civico: civiciUniciDaAggregare){
				SitCivicoUnico nuovoCivUnico = new SitCivicoUnico();
				nuovoCivUnico.setCivico(civico);
				nuovoCivUnico.setDtIns(getCurrentDate());
				nuovoCivUnico.setFkVia(new BigDecimal(nuovo.getIdVia()));
				nuovoCivUnico.setValidato(new BigDecimal(0));
				nuovoCivUnico.setRating(new BigDecimal(0));
				
				viaDAO.persist(nuovoCivUnico);
				
				// associazione civici totale a nuovo unico	
				List<SitCivicoTotale> result3 = viaDAO.getCivicoTotaleByVieUnicoCivico(idUno, idDue, civico);
				
				for (SitCivicoTotale civicoTot : result3) {
					
					civicoTot.setFkVia(new BigDecimal(nuovo.getIdVia()));
					civicoTot.setFkCivico(new BigDecimal(nuovoCivUnico.getIdCivico()));
					
					viaDAO.mergeCivico(civicoTot);
				}
			}
			
		} catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}

	}

	@Override
	//public void creaNuovaVia(SitViaUnico nuovo) {
	public void creaNuovaVia(IndiceDataIn indDataIn) {

		SitViaUnico nuovo = (SitViaUnico)indDataIn.getObj();
		
		try {
			
			nuovo.setDtIns(getCurrentDate());
			nuovo.setValidato(new BigDecimal(1));
			
			//manager.persist(nuovo);
			viaDAO.persist(nuovo);
			
		} catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}

	}
	
	@Override
	public List<SitViaUnico> getListaViaUnicoByDescr(RicercaCivicoDTO rc){
		List<SitViaUnico> result = null;
		try{
			result = viaDAO.getListaViaUnicoByDescr(rc.getToponimoVia(), rc.getDescrizioneVia());
		}catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}
		return result;
	}
	
	@Override
	public List<SitViaTotale> getViaTotaleByUnicoFonte(RicercaCivicoIndiceDTO rc) {
		List<SitViaTotale> result = new ArrayList<SitViaTotale>();
	
		try{
			result =  viaDAO.getViaTotaleByUnicoFonte(new BigDecimal(rc.getIdVia()), rc.getEnteSorgente(), rc.getProgEs());
		}catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}
		return result;
	}
}
