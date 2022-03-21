package it.webred.ct.data.access.basic.ruolo.tares;

import it.webred.ct.data.access.basic.ruolo.RuoloDataIn;
import it.webred.ct.data.access.basic.ruolo.tares.dto.RuoloTaresDTO;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface RTaresService {

	public List<RuoloTaresDTO> getListaRuoliByCodFis(RuoloDataIn dataIn) throws RTaresServiceException;
	
	public RuoloTaresDTO getRuoloById(RuoloDataIn dataIn) throws RTaresServiceException;

}
