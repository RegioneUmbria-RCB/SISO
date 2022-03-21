package it.umbriadigitale.soclav.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.umbriadigitale.soclav.model.Test;
import it.umbriadigitale.soclav.repository.TestRepository;
import it.umbriadigitale.soclav.service.interfaccia.IGeneralService;

@Component("testService")
public class DefaultTestServiceImpl implements IGeneralService {

	
	@Autowired
	@Qualifier("testRepository")
	private TestRepository testRepository;

		
	@Transactional
	public Test find(Long id) {
		return testRepository.findOne(id);
	}


	@Transactional
	public Test save(Test t) {
		return testRepository.save(t);
	}

}
