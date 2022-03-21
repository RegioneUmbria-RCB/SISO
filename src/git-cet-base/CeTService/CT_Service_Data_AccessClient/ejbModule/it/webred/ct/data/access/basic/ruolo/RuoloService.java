package it.webred.ct.data.access.basic.ruolo;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface RuoloService {

	
	List<String> getPercorsiPdfByCfCu(RuoloDataIn dataIn)
			throws RuoloServiceException;

	List<String> getPercorsiPdfByCf(RuoloDataIn dataIn)
			throws RuoloServiceException;

}
