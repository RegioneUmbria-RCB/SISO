package it.umbriadigitale.soclav.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.RdCKeyValueExt;

@Repository("keyValueExtRepository")
public interface RdCKeyValueExtRepository extends CrudRepository<RdCKeyValueExt, Integer> {

	@Query("from RdCKeyValueExt a where a.keyConf = :inputKeyConf")
	public RdCKeyValueExt getParamValue(@Param("inputKeyConf") String keyConf);
}
