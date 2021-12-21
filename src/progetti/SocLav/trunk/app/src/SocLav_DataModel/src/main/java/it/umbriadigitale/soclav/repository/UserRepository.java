package it.umbriadigitale.soclav.repository;

 
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.umbriadigitale.soclav.model.RdCUser;
import it.umbriadigitale.soclav.model.anpal.RdCTbCpiCompetenza;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<RdCUser, String> {
	@Query("from RdCUser u where u.username = :username and  u.enteDefault = :ente")
	public RdCUser  findUtenteByLoginAndEnte(@Param("username") String username, @Param("ente") String ente);
	
	@Query("from RdCUser u where u.username = :username")
	public RdCUser  findUtenteByLogin(@Param("username") String username);
	
	@Query("from RdCUser u")
	public Page<RdCUser> findAllPageable(Pageable pageable);
	
	@Query("from RdCTbCpiCompetenza c where  exists "
	  		+ "(select 1 from RdCUserCpi u where u.id.codCpi=c.id.codCpi and u.id.username=:username)") 
	 public List<RdCTbCpiCompetenza> findCpiByUsername(@Param("username") String username);
	 
	
}

