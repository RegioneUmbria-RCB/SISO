package com.osmosit.siso.flussoinps.services;

import com.osmosit.siso.flussoinps.psa_invioprestazioni.Ente;

public interface IEnteService {
	public Ente createEnte(String denomEnte, String codEnte, String cfOperatore, String indirEnte);	
}
