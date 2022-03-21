package it.webred.ct.data.access.aggregator.catastoDocfaConcedilizie;

import it.webred.ct.data.access.aggregator.catastoDocfaConcedilizie.dto.SearchCriteriaDTO;
import it.webred.ct.data.access.basic.catasto.dto.ParametriCatastaliDTO;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface CatastoDocfaConcedilizieService {
	
	public List<ParametriCatastaliDTO> ricercaDisgiuntaListaUiu(SearchCriteriaDTO ro);
	
	public Long ricercaDisgiuntaSearchCount(SearchCriteriaDTO ro);
	
	public List<ParametriCatastaliDTO> ricercaDisgiuntaListaParticelle(SearchCriteriaDTO ro);
	
}
