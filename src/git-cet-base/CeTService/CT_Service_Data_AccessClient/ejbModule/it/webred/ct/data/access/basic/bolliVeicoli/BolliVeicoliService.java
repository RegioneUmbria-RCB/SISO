package it.webred.ct.data.access.basic.bolliVeicoli;

import java.util.List;

import it.webred.ct.data.access.basic.bolliVeicoli.dto.RicercaBolliVeicoliDTO;
import it.webred.ct.data.model.bolliVeicoli.BolloVeicolo;

import javax.ejb.Remote;

@Remote
public interface BolliVeicoliService {
	
	public List<BolloVeicolo> getBolliVeicoliByCFPI(RicercaBolliVeicoliDTO rbv);
	
	public List<BolloVeicolo> getBolliVeicoliByCriteria(RicercaBolliVeicoliDTO rbv);
	
}
