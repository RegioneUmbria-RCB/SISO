package it.umbriadigitale.soclav.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.anpal.RdCTbCodiciSap;
import it.umbriadigitale.soclav.model.anpal.RdCTbCodiciSapPK;

@Repository("codificheSapRepository")
public interface CodificheSapRepository extends CrudRepository<RdCTbCodiciSap, RdCTbCodiciSapPK> {
	
	@Query("from RdCTbCodiciSap a where a.id.tabella= :tabella")
	public List<RdCTbCodiciSap> findCodiciByTabella(@Param("tabella") String tabella);

	@Query("from RdCTbCodiciSap a where a.id.tabella= :tabella and a.id.codice= :codice")
	public RdCTbCodiciSap findDecodifica(@Param("tabella") String string, @Param("codice") String codstatus);
}
