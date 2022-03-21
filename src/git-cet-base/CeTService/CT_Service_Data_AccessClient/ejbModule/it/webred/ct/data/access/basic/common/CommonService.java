package it.webred.ct.data.access.basic.common;

import java.util.List;

import it.webred.ct.data.access.aggregator.elaborazioni.dto.ElaborazioniDataOut;
import it.webred.ct.data.model.common.SitEnte;
import it.webred.ct.support.datarouter.CeTBaseObject;

import javax.ejb.Remote;

@Remote
public interface CommonService {
	
	public SitEnte getEnte(CeTBaseObject cet);

	public  List<Object[]> executeNativeQuery(CommonDataIn dataIn);
	
}
