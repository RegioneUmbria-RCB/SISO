package it.webred.ct.data.access.basic.pregeo.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.pregeo.PregeoQueryBuilder;
import it.webred.ct.data.access.basic.pregeo.PregeoServiceException;
import it.webred.ct.data.access.basic.pregeo.dto.RicercaPregeoDTO;
import it.webred.ct.data.model.pregeo.PregeoInfo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class PregeoJPAImpl extends CTServiceBaseDAO implements PregeoDAO {

	private static final long serialVersionUID = 2121976071581059267L;

	@Override
	public List<PregeoInfo> getDatiPregeoByFabbricato(RicercaPregeoDTO rp)	throws PregeoServiceException {
		List<PregeoInfo> lista=null;
		logger.debug("getDatiPregeoByFabbricato-parms[FOGLIO: "+rp.getFoglio()+"];" + "[PARTICELLA: "+rp.getParticella()+"]; "+ "[DATARIF: "+rp.getDataRif()+"]" );
		try {
			Query q= null;
			
			if (rp.getDataRif()!= null){
				if(rp.isFoglioPregeo())
					q =manager_diogene.createNamedQuery("PregeoInfo.getInfoByFPData");
				else
					q =manager_diogene.createNamedQuery("PregeoInfo.getInfoByFsPData");
			}else{
				if(rp.isFoglioPregeo())
					q =manager_diogene.createNamedQuery("PregeoInfo.getInfoByFP");
				else
					q =manager_diogene.createNamedQuery("PregeoInfo.getInfoByFsP");
			}
			
			q.setParameter("foglio", rp.getFoglio().toString());
			q.setParameter("particella", rp.getParticella());
			
			if (rp.getDataRif()!= null)
				q.setParameter("data", rp.getDataRif());
			
			if (rp.getLimit()!=null && rp.getLimit()>0)
				q.setFirstResult(0).setMaxResults(rp.getLimit());
			
			lista =(List<PregeoInfo>) q.getResultList();
			logger.debug("TROVATI DATI PREGO . N.ELE: "+ lista.size());
	
		} catch (Throwable t) {
			logger.error("", t);
			throw new PregeoServiceException (t);
		}
		return lista;
	}

	@Override
	public List<String> getListaParticelleByNomeFilePdf(RicercaPregeoDTO rp)
			throws PregeoServiceException {
		
		List<String> lstPart= new ArrayList<String>();
		try {
			Query q= null;
			
			q =manager_diogene.createNamedQuery("PregeoInfo.getListaParticellaByNomeFile");
			
			q.setParameter("nomeFile", rp.getNomeFilePdf());
			
			lstPart = q.getResultList();
		
		} catch (Throwable t) {
			logger.error("", t);
			throw new PregeoServiceException (t);
		}
		
		return lstPart;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<PregeoInfo> getPregeoByCriteria(RicercaPregeoDTO rp) throws PregeoServiceException {
		
		List<PregeoInfo> lstPregeo = new ArrayList<PregeoInfo>();
		try {
			String hql = new PregeoQueryBuilder().createQueryPregeoByCriteria(rp);
			logger.debug("getPregeoByCriteria: " + hql );			
			Query q = manager_diogene.createQuery(hql);

			if (rp.getLimit()>0)
				q.setFirstResult(0).setMaxResults(rp.getLimit());
			
			lstPregeo = q.getResultList();
			if (lstPregeo != null && lstPregeo.size()>0)
				logger.warn("getPregeoByCriteria TROVATO. N.ELE: "+ lstPregeo.size());
			else
				logger.warn("getPregeoByCriteria TROVATO. N.ELE: 0");
		
		} catch (Throwable t) {
			logger.error("", t);
			throw new PregeoServiceException (t);
		}
		
		return lstPregeo;
	}//-------------------------------------------------------------------------
	

}
