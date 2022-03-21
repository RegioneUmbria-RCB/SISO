package it.umbriadigitale.soclav.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.privacy.RdcConsensiSocToLav;
import it.umbriadigitale.soclav.model.privacy.RdcConsensiSocToLavPK;

@Repository("consensoSocToLavRepository")
public interface ConsensoSocToLavRepository extends CrudRepository<RdcConsensiSocToLav, RdcConsensiSocToLavPK> {

}
