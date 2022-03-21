package it.webred.ct.data.access.basic.condono;

import it.webred.ct.data.access.basic.condono.dto.RicercaCondonoDTO;
import it.webred.ct.data.model.condono.CondonoPratica;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface CondonoService {

	public List<CondonoPratica> getCondoni(RicercaCondonoDTO rc);
	
	public CondonoPratica getPraticaCondono(RicercaCondonoDTO rc);
}
