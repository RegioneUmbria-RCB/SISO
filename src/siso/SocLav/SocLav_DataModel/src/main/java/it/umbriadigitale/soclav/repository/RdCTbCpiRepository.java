package it.umbriadigitale.soclav.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.anpal.RdCTbCpi;

@Repository("rdcTbCpiRepository")
public interface RdCTbCpiRepository extends CrudRepository<RdCTbCpi, String> {

	
}
