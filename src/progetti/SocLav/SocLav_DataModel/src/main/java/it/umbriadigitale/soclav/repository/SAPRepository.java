package it.umbriadigitale.soclav.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.anpal.RdCAnpalSAP;

@Repository("sapRepository")
public interface SAPRepository extends CrudRepository<RdCAnpalSAP, String> {
	
	 
}

