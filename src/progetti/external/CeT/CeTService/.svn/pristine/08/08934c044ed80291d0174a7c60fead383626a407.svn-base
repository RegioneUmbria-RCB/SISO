package it.webred.ct.data.access.aggregator.isee;

import it.webred.ct.data.access.aggregator.isee.dto.InfoIseeDTO;
import it.webred.ct.data.access.aggregator.isee.dto.IseeCalcDataIn;
import it.webred.ct.data.access.aggregator.isee.dto.IseeDTO;
import it.webred.ct.data.access.aggregator.isee.dto.IseeDataIn;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.model.isee.IseeAnnoDich;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface IseeService {

	public List<IseeAnnoDich> getAnnoDich(CeTBaseObject cet);

	public InfoIseeDTO getInfoIsee(IseeDataIn dataIn);

	public IseeDTO getCalcoloIsee(InfoIseeDTO infoIsee);

	public InfoIseeDTO aggiornaSomme(InfoIseeDTO infoIsee);
	
}
