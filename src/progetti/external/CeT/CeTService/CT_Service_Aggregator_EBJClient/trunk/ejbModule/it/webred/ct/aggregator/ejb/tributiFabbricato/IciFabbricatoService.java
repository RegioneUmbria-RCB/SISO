package it.webred.ct.aggregator.ejb.tributiFabbricato;

import it.webred.ct.aggregator.ejb.tributiFabbricato.dto.DatiIciDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface IciFabbricatoService {

	public List<DatiIciDTO> getDatiIciCiviciDelFabbricato(RicercaOggettoDTO ro) ;
	
	public List<DatiIciDTO> getDatiIciFabbricato(RicercaOggettoDTO rs);
	
	public List<DatiIciDTO> getDatiIciUI(RicercaOggettoDTO rs);
}
