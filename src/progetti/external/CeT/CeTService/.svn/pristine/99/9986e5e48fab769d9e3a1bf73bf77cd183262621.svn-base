package it.webred.ct.data.access.basic.cnc.statoriscossione;

import it.webred.ct.data.access.basic.cnc.CNCDataIn;
import it.webred.ct.data.access.basic.cnc.statoriscossione.dto.FullRiscossioneInfo;
import it.webred.ct.data.access.basic.cnc.statoriscossione.dto.StatoRiscossioneSearchCriteria;
import it.webred.ct.data.model.cnc.statoriscossione.ChiaveULStatoRiscossione;
import it.webred.ct.data.model.cnc.statoriscossione.SRiscossioni;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface StatoRiscossioneService {

	public List<SRiscossioni> getRiscossioni(StatoRiscossioneSearchCriteria criteria, int start, int numRecord);
	
	public Long getRecordCountRiscossioni(StatoRiscossioneSearchCriteria criteria);
	
	public FullRiscossioneInfo getRiscossioneInfo(CNCDataIn dataIn);
	
	public List<SRiscossioni> getRiscossioniByAnnoComuneCF(StatoRiscossioneSearchCriteria criteria);
}
