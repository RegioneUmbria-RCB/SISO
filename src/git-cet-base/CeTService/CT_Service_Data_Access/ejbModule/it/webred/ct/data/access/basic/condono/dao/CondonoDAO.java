package it.webred.ct.data.access.basic.condono.dao;

import it.webred.ct.data.access.basic.condono.dto.RicercaCondonoDTO;
import it.webred.ct.data.model.condono.CondonoPratica;

import java.util.List;

public interface CondonoDAO {

	public List<CondonoPratica> getCondoni(RicercaCondonoDTO rc);
	
	public CondonoPratica getPraticaCondono(RicercaCondonoDTO rc);
	
}
