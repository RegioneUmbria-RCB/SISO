package it.webred.ct.data.access.basic.cnc.statoriscossione;

import java.util.ArrayList;
import java.util.List;

import it.webred.ct.data.access.basic.cnc.CNCBaseService;
import it.webred.ct.data.access.basic.cnc.CNCDataIn;
import it.webred.ct.data.access.basic.cnc.CNCSearchCriteria;
import it.webred.ct.data.access.basic.cnc.flusso750.Flusso750ServiceException;
import it.webred.ct.data.access.basic.cnc.statoriscossione.dao.StatoRiscossioneDAO;
import it.webred.ct.data.access.basic.cnc.statoriscossione.dto.FullRiscossioneInfo;
import it.webred.ct.data.access.basic.cnc.statoriscossione.dto.StatoRiscossioneSearchCriteria;
import it.webred.ct.data.model.cnc.flusso750.VArticolo;
import it.webred.ct.data.model.cnc.statoriscossione.ChiaveULStatoRiscossione;
import it.webred.ct.data.model.cnc.statoriscossione.SRiscossioni;
import it.webred.ct.data.model.cnc.statoriscossione.SRiversamenti;
import it.webred.ct.support.audit.AuditStateless;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class StatoRiscossioneServiceBean
 */
@Stateless
public class StatoRiscossioneServiceBean  extends CNCBaseService implements StatoRiscossioneService {

	@Autowired
	private StatoRiscossioneDAO statoRiscossioneDAO;
	
	@Override
	@Interceptors(AuditStateless.class)
	public List<SRiscossioni> getRiscossioni(
			StatoRiscossioneSearchCriteria criteria, int start, int numRecord) {
		
		List<SRiscossioni> riscossioniList = new ArrayList<SRiscossioni>();
		
		try {
			riscossioniList = statoRiscossioneDAO.getRiscossioni(criteria, start, numRecord);
		}
		catch(Throwable t) {
			throw new StatoRiscossioneServiceException(t);			
		}
		
		return riscossioniList;
		
	}

	@Override
	public Long getRecordCountRiscossioni(
			StatoRiscossioneSearchCriteria criteria) {
		try {

			return statoRiscossioneDAO.getRecordCountRiscossioni(criteria);
		}
		catch(Throwable t) {
			throw new StatoRiscossioneServiceException(t);
		}
	}

	@Override
	public FullRiscossioneInfo getRiscossioneInfo(CNCDataIn dataIn
			) {
		
		FullRiscossioneInfo info = new FullRiscossioneInfo();
		
		
		ChiaveULStatoRiscossione chiave = (ChiaveULStatoRiscossione) dataIn.getObj();
		
		try {
			List<SRiscossioni> risList = statoRiscossioneDAO.getRiscossioni(chiave);
			List<SRiversamenti> rivList = statoRiscossioneDAO.getRiversamenti(chiave);
			
			info.setRiscossioniList(risList);
			info.setRiversamentiList(rivList);
		}
		catch(Throwable t) {
			throw new StatoRiscossioneServiceException(t);
		}
		
		return info;
	}

	@Override
	@Interceptors(AuditStateless.class)
	public List<SRiscossioni> getRiscossioniByAnnoComuneCF(StatoRiscossioneSearchCriteria criteria) {
		List<SRiscossioni> lista =null;
		try {
			lista=statoRiscossioneDAO.getRiscossioniByAnnoComuneCF(criteria);
		}
		catch (Throwable t) {
			throw new Flusso750ServiceException(t); 
		}
		return lista;
	}



}
