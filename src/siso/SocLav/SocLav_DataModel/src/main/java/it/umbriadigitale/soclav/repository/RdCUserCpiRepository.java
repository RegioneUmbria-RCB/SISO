package it.umbriadigitale.soclav.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.RdCUserCpi;


@Repository("rdcUserCpiRepository")
public interface RdCUserCpiRepository extends CrudRepository<RdCUserCpi, String> {


}
