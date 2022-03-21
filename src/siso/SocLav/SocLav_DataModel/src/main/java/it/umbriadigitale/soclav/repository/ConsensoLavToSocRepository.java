package it.umbriadigitale.soclav.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.privacy.RdcConsensiLavToSoc;
import it.umbriadigitale.soclav.model.privacy.RdcConsensiLavToSocPK;

@Repository("consensoLavToSocRepository")
public interface ConsensoLavToSocRepository extends CrudRepository<RdcConsensiLavToSoc, RdcConsensiLavToSocPK> {

}
