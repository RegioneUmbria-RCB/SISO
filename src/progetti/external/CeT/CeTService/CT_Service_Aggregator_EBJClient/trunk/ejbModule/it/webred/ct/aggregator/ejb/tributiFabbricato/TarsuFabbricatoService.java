package it.webred.ct.aggregator.ejb.tributiFabbricato;

import it.webred.ct.aggregator.ejb.tributiFabbricato.dto.DatiTarsuDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface TarsuFabbricatoService {
	
	public List<DatiTarsuDTO> getDatiTarsuFabbricato(RicercaOggettoDTO rs);
	
	public List<DatiTarsuDTO> getDatiTarsuUI(RicercaOggettoDTO rs);

	public List<DatiTarsuDTO> getDatiTarsuCiviciDelFabbricato(RicercaOggettoDTO rs);
}
