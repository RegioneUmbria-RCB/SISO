package it.umbriadigitale.soclav.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.umbriadigitale.soclav.model.RdcAccessoLog;
import it.umbriadigitale.soclav.repository.RdcAccessoLogRepository;
import it.umbriadigitale.soclav.service.interfaccia.IGestioneLogAccessi;

@Component("gestioneLogAccessi")
public class GestioneLogAccessiImpl implements IGestioneLogAccessi {

	@Autowired
	@Qualifier("rdcAccessoLogRepository")
	private RdcAccessoLogRepository repo;

	@Override
	public List<RdcAccessoLog> findAccessi(String tipo, List<String> enti, String cf) {
		if(enti.size()==1 && enti.get(0).equalsIgnoreCase("ALL"))
			return repo.findAccessi(cf, tipo);
		else
			return repo.findAccessi(cf, enti, tipo);
	}

	@Override
	public void salva(String tipo, String ente, String cf, String username) {
		RdcAccessoLog log = new RdcAccessoLog();
		log.setData(new Date());
		log.setCfVisualizzato(cf);
		log.setOperatoreEnte(ente);
		log.setTipo(tipo);
		log.setOperatoreUsername(username);
		
		repo.save(log);	
	}
	


}
