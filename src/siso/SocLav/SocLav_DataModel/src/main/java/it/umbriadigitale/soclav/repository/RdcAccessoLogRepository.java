package it.umbriadigitale.soclav.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.RdcAccessoLog;

@Repository("rdcAccessoLogRepository")
public interface RdcAccessoLogRepository extends CrudRepository<RdcAccessoLog, Long> {
	
	@Query("from RdcAccessoLog a "
			+ "where a.cfVisualizzato= :cf and a.operatoreEnte IN (:enti) and a.tipo = :tipo order by data desc ")
	public List<RdcAccessoLog> findAccessi(@Param("cf") String cf, @Param("enti") List<String> enti, @Param("tipo") String tipo);
	
	@Query("from RdcAccessoLog a "
			+ "where a.cfVisualizzato= :cf and a.tipo = :tipo order by data desc ")
	public List<RdcAccessoLog> findAccessi(@Param("cf") String cf, @Param("tipo") String tipo);
	
}
