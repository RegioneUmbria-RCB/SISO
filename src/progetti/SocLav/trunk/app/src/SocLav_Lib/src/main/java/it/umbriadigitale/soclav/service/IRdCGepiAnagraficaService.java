package it.umbriadigitale.soclav.service;

import java.util.List;

import javax.ejb.Remote;

import org.springframework.data.repository.CrudRepository;

import it.umbriadigitale.soclav.model.gepi.RdCAnagraficaGepi;
import it.umbriadigitale.soclav.model.gepi.RdCGepiDomanda;

public interface IRdCGepiAnagraficaService {

	public List<RdCAnagraficaGepi> findRdcAnagraficaByCodEnte(String codEnte);
}
