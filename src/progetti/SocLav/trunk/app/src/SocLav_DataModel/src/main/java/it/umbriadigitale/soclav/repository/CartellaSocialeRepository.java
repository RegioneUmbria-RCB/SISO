package it.umbriadigitale.soclav.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.VArbiCasi;

@Repository("cartellaSocialeRepository")
public interface CartellaSocialeRepository extends CrudRepository<VArbiCasi, Long> {
	
	@Query("from VArbiCasi a "
			+ "where a.cf= :cf ")
	public List<VArbiCasi> findCartelle(@Param("cf") String cf);
	

}
