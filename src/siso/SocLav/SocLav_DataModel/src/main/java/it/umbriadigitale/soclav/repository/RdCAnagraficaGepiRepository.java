package it.umbriadigitale.soclav.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.gepi.RdCAnagraficaGepi;
import it.umbriadigitale.soclav.model.gepi.RdCGepiDomanda;

@Repository("rdCAnagraficaGepiRepository")
public interface RdCAnagraficaGepiRepository extends CrudRepository<RdCAnagraficaGepi, String> {
 
 }
