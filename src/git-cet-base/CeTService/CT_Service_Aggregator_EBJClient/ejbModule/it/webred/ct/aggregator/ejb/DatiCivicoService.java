package it.webred.ct.aggregator.ejb;

import javax.ejb.Remote;

import it.webred.ct.aggregator.ejb.dto.DatiCivicoDTO;
import it.webred.ct.aggregator.ejb.dto.RichiestaDatiCivicoDTO;

@Remote
public interface DatiCivicoService {
	
	public DatiCivicoDTO getDatiCivico(RichiestaDatiCivicoDTO input);
	
	public String getDatiCivico(String richiestaDatiCivicoDTO);
}
