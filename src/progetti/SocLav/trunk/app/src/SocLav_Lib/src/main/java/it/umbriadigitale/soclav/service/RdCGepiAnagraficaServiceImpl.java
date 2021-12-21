package it.umbriadigitale.soclav.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import it.umbriadigitale.soclav.model.gepi.RdCAnagraficaGepi;
import it.umbriadigitale.soclav.model.gepi.RdCGepiDomanda;
import it.umbriadigitale.soclav.repository.RdCAnagraficaGepiRepository; 

@Service("rdcGepiAnagraficaService")
 public class RdCGepiAnagraficaServiceImpl implements IRdCGepiAnagraficaService {

	@Autowired
	@Qualifier("rdCAnagraficaGepiRepository")
	private RdCAnagraficaGepiRepository  rdcDomandaRepository;

	@Override
	public List<RdCAnagraficaGepi> findRdcAnagraficaByCodEnte(String codEnte) {
		// TODO Auto-generated method stub
		return null;
	}

}
