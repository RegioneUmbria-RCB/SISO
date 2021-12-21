package it.umbriadigitale.soclav.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import it.umbriadigitale.soclav.model.RdCUser;
import it.umbriadigitale.soclav.model.anpal.RdCTbCpiCompetenza;
import it.umbriadigitale.soclav.repository.UserRepository;
import it.umbriadigitale.soclav.service.interfaccia.IUserService;

@Component("userService")
public class UserServiceImpl implements IUserService{

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	
	@Override
	public RdCUser findUserByLoginEnte(String username, String ente) {
		// TODO Auto-generated method stub
		return userRepository.findUtenteByLoginAndEnte(username, ente);
	}
	
	@Override
	public RdCUser findUserByLogin(String username) {
		// TODO Auto-generated method stub
				return userRepository.findUtenteByLogin(username);
	}

	@Override
	public List<String> findEntiCompetenzaByUsername(String username) {
		List<RdCTbCpiCompetenza> res = userRepository.findCpiByUsername(username);
		List<String> enti = new ArrayList<String>();
		for(RdCTbCpiCompetenza c : res) { 
			String belfiore = c.getId().getComuneCod();
			if(!StringUtils.isBlank(belfiore) && !enti.contains(belfiore))
				enti.add(belfiore); 
		}
		return enti;
	}
	
	@Override
	public Page<RdCUser> findAllUsers(int first, int size) {
		Sort sort = new Sort(Sort.Direction.ASC, "cognome");
		Pageable pageable = new PageRequest(first, size, sort);
		Page<RdCUser> utenti = userRepository.findAllPageable(pageable);
		return utenti;
	}
	
	@Override
	public long countAllUsers() {
		return userRepository.count();
	}
}
