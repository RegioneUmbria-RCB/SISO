package it.webred.ct.data.access.basic.cnc.dao;



import javax.persistence.NoResultException;
import javax.persistence.Query;


public class CNCJPAImpl extends CNCBaseDAO implements CNCDAO {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getCodiceTipoEntrataDescr(String codTipoEntrata) throws CNCDAOException {
		try {
			//logger.info("Codice ["+codTipoEntrata+"]");
			Query q = manager.createNamedQuery("CodiciTipoEntrata.getCodiceDescr");
			q.setParameter("codice", codTipoEntrata);
			String name = (String) q.getSingleResult();
			return name;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}
	}

	@Override
	public String getCodiceUfficioDescr(String codUfficio, String codiceEnte,
			String codiceTipoUfficio) throws CNCDAOException {
		try {
			//logger.info("Codice ["+codTipoEntrata+"]");
			Query q = manager.createNamedQuery("CodiceEnteCredBenef.getCodiceUfficioDescr");
			q.setParameter("codEnte", codiceEnte);
			q.setParameter("codTipoUfficio", codiceTipoUfficio);
			q.setParameter("codUfficio", codUfficio);
			String name = (String) q.getSingleResult();
			return name;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}
	}
 
	@Override
	public String getCodiceUfficioDescrDaPartita(String codiceEnte,
			String codicePartita) throws CNCDAOException {
		try {
			//logger.info("Codice ["+codTipoEntrata+"]");
			Query q = manager.createNamedQuery("CodiceEnteCredBenef.getCodiceUfficioDescrTipo");
			q.setParameter("codEnte", codiceEnte);
			q.setParameter("codTipoUfficio", codicePartita.substring(0,1));
			
			String name = (String) q.getSingleResult();
			return name;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}	
	}

	@Override
	public String getCodiceUfficioDescrFull(String codiceEnte,
			String codTipoUfficio, String codUfficio) throws CNCDAOException  {
		
		try {
			//logger.info("Codice ["+codTipoEntrata+"]");
			Query q = manager.createNamedQuery("CodiceEnteCredBenef.getCodiceUfficioDescrFullParam");
			q.setParameter("codEnte", codiceEnte);
			q.setParameter("codTipoUfficio", codTipoUfficio);
			q.setParameter("codUfficio", codUfficio);
			
			String name = (String) q.getSingleResult();
			return name;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}	
	}
	/*
	 Questa query recupera il codice ente creditore che rappresenta il Comune in input (il codice ente creditore è quello che poi si trova negli archivi 
	 del CNC )
	 */
	@Override
	public String getCodiceEnte(String codComune) throws CNCDAOException  {
		String codEnte= null;
		try {
			logger.info("getCodEnte()- Cod belfiore Comune:  ["+codComune+"]");
			Query q = manager.createNamedQuery("CodiceEnteCredBenef.getCodiceEnte");
			q.setParameter("codComune", codComune);	
			codEnte = (String) q.getSingleResult();
			return codEnte;
		}
		catch(NoResultException nre) {
			logger.info("CodEnte non presente in S_CNC_COD_ENTE_CRED_BENEF per cod. belfiore Comune= " + codComune);
			return codEnte;
		}	
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}	
	}
}
