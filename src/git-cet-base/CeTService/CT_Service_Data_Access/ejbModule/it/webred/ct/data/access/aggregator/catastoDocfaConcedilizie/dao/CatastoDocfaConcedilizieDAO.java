package it.webred.ct.data.access.aggregator.catastoDocfaConcedilizie.dao;

import it.webred.ct.data.access.aggregator.catastoDocfaConcedilizie.dto.SearchCriteriaDTO;
import it.webred.ct.data.access.basic.catasto.dto.ParametriCatastaliDTO;

import java.io.Serializable;
import java.util.List;

public interface CatastoDocfaConcedilizieDAO {
	
	public List<ParametriCatastaliDTO> ricercaDisgiuntaListaUiu(SearchCriteriaDTO ro);
	
	public Long ricercaDisgiuntaSearchCount(SearchCriteriaDTO ro);
	
	public List<ParametriCatastaliDTO> ricercaDisgiuntaListaParticelle(SearchCriteriaDTO ro);

}
