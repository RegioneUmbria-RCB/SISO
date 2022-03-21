package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.persist.CsAlert;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsAlertBASIC;
import it.webred.cs.data.model.CsAlertConfig;
import it.webred.cs.data.model.CsCfTemp;
import it.webred.cs.data.model.CsOOpsettoreAlertConfig;
import it.webred.cs.data.model.CsTbTipoAlert;
//import it.webred.ss.data.model.SsAnagrafica;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.springframework.util.StringUtils;

/**
 * @author 
 *
 */
@Named
public class CfTempDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public CsCfTemp saveCfTemp(CsCfTemp newCfTemp) throws Exception {
		newCfTemp.setDtIns(new Date());
		em.persist(newCfTemp);
		return newCfTemp;
	}
	
	public CsCfTemp updateCfTemp(CsCfTemp cfTemp) {
		cfTemp.setDtMod(new Date());
		em.merge(cfTemp);
		return cfTemp;
	}

	public CsCfTemp findById(long id) throws Exception {
		CsCfTemp cftemp= em.find(CsCfTemp.class,  id);
		return cftemp;
	}
	
		
	@SuppressWarnings("unchecked")
	public List<CsCfTemp> findByCfTempAndCf(String cfTemp, String cf) {
		try{
			if(!StringUtils.hasText(cf))
			{
				cf="null";
			}

			Query q = em.createNamedQuery("CsCfTemp.findByCfTempAndCf")
					.setParameter("cfTemp", cfTemp)
					.setParameter("cf", cf);			
			
			return q.getResultList();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	public List<CsCfTemp> findByCfTemp(String cfTemp) {
		try{
			Query q = em.createNamedQuery("CsCfTemp.findByCfTemp")
					.setParameter("cfTemp", cfTemp);								
			return q.getResultList();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	
	
	
	public CsCfTemp setCf(Long idCfTemp, String cf) throws Exception {
		CsCfTemp cftemp = em.find(CsCfTemp.class, idCfTemp);
		cftemp.setDtMod(new Date());
		
		em.merge(cftemp);
		return cftemp;
	}

	public List<CsCfTemp> findByAttribs(String attribsString) {
		try{
			Query q = em.createNamedQuery("CsCfTemp.findByAttribs")
					.setParameter("attribs", attribsString);								
			return q.getResultList();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return null;
		}
	}

	public void replaceAllCfInSS(String cfTempOld, String cfNew) {

		//"PROCEDURE_CF_TEMP_UPDATE"
		Query nq = em.createNativeQuery("{ call PROCEDURE_CF_TEMP_UPDATE(?,?) }");
		nq.setParameter(1, cfTempOld);
		nq.setParameter(2, cfNew);
		
		int ret = nq.executeUpdate();
	}

	public void replaceAllCfInCS(String cfTempOld, String cfNew) 
	{			
		Query q = em.createNamedQuery("CsAAnagrafica.findAnagraficaByCf")
				.setParameter("CodFisc", cfTempOld);
		List<CsAAnagrafica> to_update = q.getResultList();
		if (to_update!=null)
			for(CsAAnagrafica item: to_update)
			{
				item.setCf(cfNew);				
				em.merge(item);
			}						
	}


}
