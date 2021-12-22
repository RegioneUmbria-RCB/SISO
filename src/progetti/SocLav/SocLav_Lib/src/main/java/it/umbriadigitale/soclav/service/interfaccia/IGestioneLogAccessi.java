package it.umbriadigitale.soclav.service.interfaccia;

import java.util.List;

import it.umbriadigitale.soclav.model.RdcAccessoLog;

public interface IGestioneLogAccessi {

	public List<RdcAccessoLog> findAccessi(String tipo, List<String> enti, String cf);
	
	public void salva(String tipo, String ente, String cf, String username);
}
