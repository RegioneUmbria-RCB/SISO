package it.webred.ct.data.access.aggregator.catastoDocfaConcedilizie.dao;

import it.webred.ct.data.access.aggregator.catastoDocfaConcedilizie.dto.SearchCriteriaDTO;
import it.webred.ct.data.access.basic.catasto.CatastoServiceException;
import it.webred.ct.data.access.basic.catasto.dao.CatastoBaseDAO;
import it.webred.ct.data.access.basic.catasto.dto.ParametriCatastaliDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class CatastoDocfaConcedilizieJPAImpl extends CatastoBaseDAO implements CatastoDocfaConcedilizieDAO  {

	private static final long serialVersionUID = 1L;

	@Override
	public List<ParametriCatastaliDTO> ricercaDisgiuntaListaUiu(SearchCriteriaDTO ro){
		List<ParametriCatastaliDTO> lista = new ArrayList<ParametriCatastaliDTO>();
		
		try {
			String sql = new CatastoDocfaConcEdilizieQueryBuilder(ro).createQueryGettingFPS(false);
				
			logger.debug("LISTA IMMOBILI CATASTO/DOCFA/CONC.EDILIZIE - SQL["+sql+"]");
			
			if (sql != null && sql.trim().length()>0) {
				
				Query q = manager_diogene.createNativeQuery(sql);
				if(ro.getStartm()!=null)
					q.setFirstResult(ro.getStartm().intValue());
				if(ro.getNumberRecord()!=null)
					q.setMaxResults(ro.getNumberRecord().intValue());
				
				List<Object[]> result = q.getResultList();
				logger.debug("LISTA IMMOBILI CATASTO/DOCFA/CONC.EDILIZIE ["+result.size()+"]");
				for(Object[] res : result){
					ParametriCatastaliDTO uiu = new ParametriCatastaliDTO();
					uiu.setSezione((String)res[0]);
					uiu.setFoglio((String)res[1]);
					uiu.setParticella((String)res[2]);
					uiu.setSubalterno((String)res[3]);
					
					lista.add(uiu);
				}
				
			}
				
		} catch (NoResultException nre) {
			
			logger.warn("Result size [0] " + nre.getMessage());

		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		
		return lista;
	}
	
	@Override
	public Long ricercaDisgiuntaSearchCount(SearchCriteriaDTO ro){

		Long ol = 0L;

		try {
			
			String sql = "";
			if (ro.getTipoRicerca()!=null )   {
				if (ro.getTipoRicerca().equals("maiDic"))
					sql=new CatastoDocfaConcEdilizieQueryBuilder(ro).createQueryGettingParticelle(true);
				else 
					sql=new CatastoDocfaConcEdilizieQueryBuilder(ro).createQueryGettingFPS(true);
			}
			else	
				sql=new CatastoDocfaConcEdilizieQueryBuilder(ro).createQueryGettingFPS(true);
			
			logger.debug("COUNT LISTA IMMOBILI CATASTO/DOCFA/CONC.EDILIZIE - SQL["+sql+"]");
			
			if (sql != null && sql.trim().length()>0) {
				
				Query q = manager_diogene.createNativeQuery(sql);
				Object o = q.getSingleResult();
				ol = new Long(((BigDecimal) o).longValue());
				
				logger.debug("COUNT LISTA IMMOBILI CATASTO/DOCFA/CONC.EDILIZIE ["+ol+"]");
				
			}
				
		} catch (NoResultException nre) {
			
			logger.warn("Result size [0] " + nre.getMessage());

		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}

		return ol;
	}

	@Override
	public List<ParametriCatastaliDTO> ricercaDisgiuntaListaParticelle(SearchCriteriaDTO ro) {
List<ParametriCatastaliDTO> lista = new ArrayList<ParametriCatastaliDTO>();
		
		try {
			
			
			String sql = new CatastoDocfaConcEdilizieQueryBuilder(ro).createQueryGettingParticelle(false);
			
			logger.debug("LISTA PARTICELLE CATASTO/DOCFA - SQL["+sql+"]");
			
			if (sql != null && sql.trim().length()>0) {
				
				Query q = manager_diogene.createNativeQuery(sql);
				if(ro.getStartm()!=null)
					q.setFirstResult(ro.getStartm().intValue());
				if(ro.getNumberRecord()!=null)
					q.setMaxResults(ro.getNumberRecord().intValue());
				
				List<Object[]> result = q.getResultList();
				logger.debug("LISTA IMMOBILI CATASTO/DOCFA ["+result.size()+"]");
				for(Object[] res : result){
					ParametriCatastaliDTO part = new ParametriCatastaliDTO();
					part.setSezione((String)res[0]);
					part.setFoglio((String)res[1]);
					if (res[2]!=null ) {
						logger.debug("valore ["+ res[2]+"]");
						part.setParticella(res[2].toString());
					}
						
					else
						part.setParticella("");
									
					lista.add(part);
				}
				
			}
				
		}catch (NoResultException nre) {
			
			logger.warn("Result size [0] " + nre.getMessage());

		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		
		return lista;
	}

}
