package it.umbriadigitale.soclav.repository;

import org.springframework.data.repository.CrudRepository;

import it.umbriadigitale.soclav.model.SlAnagrafica;

public interface AnagraficaRepository extends CrudRepository<SlAnagrafica, Long> {

	//@Query("from Auction a join a.category c where c.name=:categoryName")
	//public Iterable<Auction> findByCategory(@Param("categoryName") String categoryName);
}
