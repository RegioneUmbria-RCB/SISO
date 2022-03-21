package it.umbriadigitale.soclav.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.Test;

@Repository("testRepository")
public interface TestRepository extends CrudRepository<Test, Long> {


}
