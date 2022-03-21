package it.webred.ct.aggregator.ejb.immobiliAccatastati;

import it.webred.ct.aggregator.ejb.immobiliAccatastati.dto.ImmobiliAccatastatiInputDTO;
import it.webred.ct.aggregator.ejb.immobiliAccatastati.dto.ImmobiliAccatastatiOutputDTO;

import javax.ejb.Remote;

@Remote
public interface ImmobiliAccatastatiService {
	
	public ImmobiliAccatastatiOutputDTO[] getImmobiliAccatastatiDTO(ImmobiliAccatastatiInputDTO input);
	
	public String getImmobiliAccatastati(String input);
}
