package it.webred.ct.data.access.basic.cnc.statoriscossione.dao;

import it.webred.ct.data.access.basic.cnc.dao.CNCDAOException;
import it.webred.ct.data.access.basic.cnc.statoriscossione.dto.FullRiscossioneInfo;
import it.webred.ct.data.access.basic.cnc.statoriscossione.dto.StatoRiscossioneSearchCriteria;
import it.webred.ct.data.model.cnc.statoriscossione.ChiaveULStatoRiscossione;
import it.webred.ct.data.model.cnc.statoriscossione.SRiscossioni;
import it.webred.ct.data.model.cnc.statoriscossione.SRiversamenti;

import java.util.List;

public interface StatoRiscossioneDAO {

	public List<SRiscossioni> getRiscossioni(StatoRiscossioneSearchCriteria criteria, int start, int numRecord) throws CNCDAOException;
	
	public Long getRecordCountRiscossioni(StatoRiscossioneSearchCriteria criteria) throws CNCDAOException;
		
	public List<SRiscossioni> getRiscossioni(ChiaveULStatoRiscossione chiave) throws CNCDAOException ;
	
	public List<SRiversamenti> getRiversamenti(ChiaveULStatoRiscossione chiave) throws CNCDAOException ;

	public List<SRiscossioni> getRiscossioniByAnnoComuneCF(StatoRiscossioneSearchCriteria criteria) throws CNCDAOException ;
	
}
