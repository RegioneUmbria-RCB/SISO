package it.webred.ct.data.access.basic.ruolo.tarsu;

import it.webred.ct.data.access.basic.ruolo.RuoloDataIn;
import it.webred.ct.data.access.basic.ruolo.RuoloServiceException;
import it.webred.ct.data.access.basic.ruolo.tarsu.dto.RuoloTarsuDTO;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface RTarsuService {

	public List<RuoloTarsuDTO> getListaRuoliByCodFis(RuoloDataIn dataIn) throws RTarsuServiceException;
	
	public RuoloTarsuDTO getRuoloById(RuoloDataIn dataIn) throws RTarsuServiceException;

}
