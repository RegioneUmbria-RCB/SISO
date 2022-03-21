package it.umbriadigitale.soclav.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.anpal.RdCTbCpi;
import it.umbriadigitale.soclav.model.anpal.RdCTbCpiCompetenza;

@Repository("rdcTbCpiCompetenzaRepository")
public interface RdCTbCpiCompetenzaRepository extends CrudRepository<RdCTbCpiCompetenza, String> {

	@Query("SELECT DISTINCT a.cpi from RdCTbCpiCompetenza a WHERE a.cpiSedeRegione = :regione ")
	public List<RdCTbCpi> findListaOrdinataRegione(@Param("regione") String regione);
	
	@Query("SELECT DISTINCT a.cpi from RdCTbCpiCompetenza a WHERE a.cpiSedeProv = :provincia ")
	public List<RdCTbCpi> findListaOrdinataProvincia(@Param("provincia") String provincia);
	
	@Query("SELECT DISTINCT a.cpiSedeRegione from RdCTbCpiCompetenza a order by a.cpiSedeRegione")
	public List<String> getListaRegioni();
	
	@Query("SELECT DISTINCT a.cpiSedeProv from RdCTbCpiCompetenza a where a.cpiSedeRegione = :regione order by a.cpiSedeProv")
	public List<String> getListaProv(@Param("regione") String regione);
	
}
