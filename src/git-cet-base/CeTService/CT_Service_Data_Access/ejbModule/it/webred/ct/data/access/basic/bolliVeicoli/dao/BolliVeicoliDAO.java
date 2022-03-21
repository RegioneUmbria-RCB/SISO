package it.webred.ct.data.access.basic.bolliVeicoli.dao;


import it.webred.ct.data.access.basic.bolliVeicoli.BolliVeicoliServiceException;
import it.webred.ct.data.access.basic.bolliVeicoli.dto.RicercaBolliVeicoliDTO;
import it.webred.ct.data.model.bolliVeicoli.BolloVeicolo;

import java.util.List;

public interface BolliVeicoliDAO {
	
	public List<BolloVeicolo> getBolliVeicoliByCFPI(RicercaBolliVeicoliDTO rbv) throws BolliVeicoliServiceException;
	
	public List<BolloVeicolo> getBolliVeicoliByCriteria(RicercaBolliVeicoliDTO rbv) throws BolliVeicoliServiceException;
	
}
