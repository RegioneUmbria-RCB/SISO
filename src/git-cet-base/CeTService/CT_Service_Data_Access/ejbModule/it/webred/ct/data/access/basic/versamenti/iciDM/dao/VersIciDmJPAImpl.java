package it.webred.ct.data.access.basic.versamenti.iciDM.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.versamenti.iciDM.VersIciDmServiceException;
import it.webred.ct.data.model.versamenti.iciDM.SitTIciDmAnag;
import it.webred.ct.data.model.versamenti.iciDM.SitTIciDmVers;
import it.webred.ct.data.model.versamenti.iciDM.SitTIciDmViolazione;

import java.util.List;

import javax.persistence.Query;

public class VersIciDmJPAImpl extends CTServiceBaseDAO implements VersIciDmDAO {

	@Override
	public   List<SitTIciDmVers> getListaVersamentiByCodFis(String codFiscale)
			throws VersIciDmServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitTIciDmVers.getListaVersamentiByCF");
			q.setParameter("codfisc",codFiscale);
			return q.getResultList();

		} catch (Throwable t) {
			throw new VersIciDmServiceException(t);
		}
	}
	
	@Override
	public   List<SitTIciDmVers> getListaVersamentiByCodFisAnno(String codFiscale, String anno)
			throws VersIciDmServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitTIciDmVers.getListaVersamentiByCFAnno");
			q.setParameter("codfisc",codFiscale);
			q.setParameter("anno", anno);
			return q.getResultList();

		} catch (Throwable t) {
			throw new VersIciDmServiceException(t);
		}
	}

	@Override
	public List<SitTIciDmViolazione> getListaViolazioniByCodFis(String codFiscale)
			throws VersIciDmServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitTIciDmViolazione.getListaViolazioniByCF");
			q.setParameter("codfisc",codFiscale);
			return q.getResultList();

		} catch (Throwable t) {
			throw new VersIciDmServiceException(t);
		}
	}
	
	@Override
	public SitTIciDmAnag getSoggByIdExt(String idExtAn)
			throws VersIciDmServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitTIciDmAnag.getListaSoggettoByIdExt");
			q.setParameter("idExt",idExtAn);
			List<SitTIciDmAnag> lst = (List<SitTIciDmAnag>)q.getResultList();
			if(lst.size()>0)
				return lst.get(0);
			else
				return null;

		} catch (Throwable t) {
			throw new VersIciDmServiceException(t);
		}
	}
	
	@Override
	public String getDescrizioneByCodValue(String colonna, String valore)
			 {
	
			Query q = manager_diogene.createNamedQuery(
					"SitTIciDmDecode.getDescrizioneByCodValue");
			q.setParameter("nomeCampo",colonna);
			q.setParameter("valore", valore);
			List<String> lst = (List<String>)q.getResultList();			
			if(lst.size()>0)
				return lst.get(0);
			else
				return null;
			
	
		
	}

	@Override
	public SitTIciDmVers getVersamentoById(String id){
		SitTIciDmVers s = null;
		try{
			s = manager_diogene.find(SitTIciDmVers.class, id);
		} catch (Throwable t) {
			logger.error(t.getMessage(),t);
		}
		return s;
	}

	@Override
	public SitTIciDmViolazione getViolazioneById(String id){
		SitTIciDmViolazione s = null;
		try {
			s = manager_diogene.find(SitTIciDmViolazione.class, id);
		} catch (Throwable t) {
			logger.error(t.getMessage(),t);
		}
		return s;
	}
	
}
