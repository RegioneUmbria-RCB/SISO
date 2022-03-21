package it.umbriadigitale.soclav.service.interfaccia;

import java.util.List;

import org.springframework.data.domain.Page;

import it.umbriadigitale.soclav.model.RdCUser;

public interface IUserService{

	public RdCUser findUserByLoginEnte(String username, String ente);
	public RdCUser findUserByLogin(String username);
	
	public List<String> findEntiCompetenzaByUsername(String username);
	public Page<RdCUser> findAllUsers(int first, int size);
	public long countAllUsers();
}
