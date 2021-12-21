package it.umbriadigitale.soclav.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.anpal.RdCAnpalBeneficiario;
import it.umbriadigitale.soclav.model.anpal.RdCBeneficiarioPK;
import it.umbriadigitale.soclav.model.anpal.RdCTbCpi;

@Repository("anpalRepository")
public interface AnpalRepository extends CrudRepository<RdCAnpalBeneficiario, RdCBeneficiarioPK> {

	@Query("from RdCAnpalBeneficiario a where a.residenzaComuneCod IN (:codEnteResidenza) ")
	public Page<RdCAnpalBeneficiario> findByEnte(@Param("codEnteResidenza") List<String> codEnteResidenza, Pageable pageable);
	
	@Query("from RdCAnpalBeneficiario a where a.cfRichiedente=a.id.cf and a.ruolo='R' and a.residenzaComuneCod IN (:codEnteResidenza)  ")
	public Page<RdCAnpalBeneficiario> findRichiedentiByEnte(@Param("codEnteResidenza") List<String> codEnteResidenza, Pageable pageable);

	@Query("from RdCAnpalBeneficiario a where a.cfRichiedente=a.id.cf and a.ruolo='R'")
	public Page<RdCAnpalBeneficiario> findAllRichiedenti(Pageable pageable);
	
	@Query("from RdCAnpalBeneficiario a where a.id.cf = :cf and a.id.protocolloINPSCod = :numProtINPS")
	public  RdCAnpalBeneficiario  findByCFAndNumProtINPS(@Param("cf") String cf, @Param("numProtINPS") String numProtINPS);

	@Query("from RdCAnpalBeneficiario a "
			+ "where a.cfRichiedente<>a.id.cf and a.ruolo='M' "
			+ "and   a.cfRichiedente= :cfRichiedente and a.id.protocolloINPSCod=:protocolloINPS")
	public List<RdCAnpalBeneficiario> findFamiliari(@Param("cfRichiedente") String cf, @Param("protocolloINPS") String protocollo);
	
	@Query("from RdCAnpalBeneficiario a where a.codSap = :codSap")
	public RdCAnpalBeneficiario findSAPById(@Param("codSap") String codSAP);
	
	@Query("from RdCTbCpi a where a.codice=:codCPI ")
	public RdCTbCpi findCPIById(@Param("codCPI") String codCPI);

}
