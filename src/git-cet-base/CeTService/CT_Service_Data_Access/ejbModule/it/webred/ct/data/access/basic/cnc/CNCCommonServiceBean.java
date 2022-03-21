package it.webred.ct.data.access.basic.cnc;



import it.webred.ct.data.access.basic.cnc.dao.CNCDAO;
import it.webred.ct.data.access.basic.cnc.flusso290.Flusso290ServiceException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class CNCCommonServiceBean
 */
@Stateless
public class CNCCommonServiceBean implements CNCCommonService {

	@Autowired
	private CNCDAO cncDAO;

	
	@Override
	public String getCodiceUfficioDescr(CNCDTO dto) {
		try {
			return cncDAO.getCodiceUfficioDescr(dto.getCodUfficio(), dto.getCodiceEnte(), dto.getCodiceTipoUfficio());
		}
		catch(Throwable t) {
			throw new CNCServiceException(t);
		}
	}

	@Override
	public String getCodiceUfficioDescrDaPartita(CNCDTO dto) {
		try {
			return cncDAO.getCodiceUfficioDescrDaPartita(dto.getCodiceEnte(), dto.getCodicePartita());
		}
		catch(Throwable t) {
			throw new CNCServiceException(t);
		}
	}

	@Override
	public String getCodiceUfficioDescrFull(CNCDTO dto) {
		try {
		return cncDAO.getCodiceUfficioDescrFull(dto.getCodiceEnte(), dto.getCodiceTipoUfficio(), dto.getCodUfficio());
		}
		catch(Throwable t) {
			throw new CNCServiceException(t);
		}
	}
	/*
	 Questa query recupera il codice ente creditore che rappresenta il Comune in input (il codice ente creditore è quello che poi si trova negli archivi 
	 del CNC )
	 */
	@Override
	public String getCodiceEnte(CNCDTO dto) {
		try {
		return cncDAO.getCodiceEnte(dto.getCodiceEnte());
		}
		catch(Throwable t) {
			throw new CNCServiceException(t);
		}
	}

	@Override
	public String getCodiceTipoEntrataDescr(CNCDTO dto) {
		try {
			return cncDAO.getCodiceTipoEntrataDescr(dto.getCodTipoEntrata());
			}
			catch(Throwable t) {
				throw new CNCServiceException(t);
			}
	}

    

} 
