package it.webred.ct.data.access.aggregator.catastoDocfaConcedilizie;

import it.webred.ct.data.access.aggregator.catastoDocfaConcedilizie.dao.CatastoDocfaConcedilizieDAO;
import it.webred.ct.data.access.aggregator.catastoDocfaConcedilizie.dto.SearchCriteriaDTO;
import it.webred.ct.data.access.basic.catasto.dto.ParametriCatastaliDTO;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class CatastoDocfaConcedilizieServiceBean implements CatastoDocfaConcedilizieService{
	
	@Autowired
	private CatastoDocfaConcedilizieDAO concEdiCatDcf;

	public List<ParametriCatastaliDTO> ricercaDisgiuntaListaUiu(SearchCriteriaDTO ro){
		return concEdiCatDcf.ricercaDisgiuntaListaUiu(ro);
	}
	
	public Long ricercaDisgiuntaSearchCount(SearchCriteriaDTO ro){
		return concEdiCatDcf.ricercaDisgiuntaSearchCount(ro);
	}

	@Override
	public List<ParametriCatastaliDTO> ricercaDisgiuntaListaParticelle(SearchCriteriaDTO ro) {
		return concEdiCatDcf.ricercaDisgiuntaListaParticelle(ro);
		
	}

	
}
