package it.umbriadigitale.soclav.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.umbriadigitale.soclav.model.RdCKeyValueExt;
import it.umbriadigitale.soclav.repository.RdCKeyValueExtRepository;
import it.umbriadigitale.soclav.service.interfaccia.IRdCKeyValueExtService;

@Component("keyValueExtService")
public class KeyValueExtServiceImpl implements IRdCKeyValueExtService {

	@Autowired
	@Qualifier("keyValueExtRepository")
	private RdCKeyValueExtRepository keyValueRepository;
	
	@Override
	public RdCKeyValueExt getKeyValueExtparam(String keyConf) {
		return keyValueRepository.getParamValue(keyConf);
	}

}
