package it.umbriadigitale.soclav.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.gepi.RdCGepiDomanda;

@Repository("gepiRepository")
public interface GepiRepository extends CrudRepository<RdCGepiDomanda, String> {

	@Query("from RdCGepiDomanda a where a.residenzaComuneCod IN (:codEnteResidenza)")
	public Page<RdCGepiDomanda> findRichiedentiByEnte(@Param("codEnteResidenza") List<String> codEnteResidenza, Pageable pageable);

	@Query("from RdCGepiDomanda a")
	public Page<RdCGepiDomanda> findAllRichiedenti(Pageable pageable);
	
	@Query("from RdCGepiDomanda a")
	public List<RdCGepiDomanda> findAllRichiedenti();
	
}
