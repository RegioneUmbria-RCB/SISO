package it.webred.cs.csa.ejb.client;

import java.util.List;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAAnagraficaLog;


public interface AccessTableAnagraficaLogSessionBeanRemote {

	public CsAAnagraficaLog findAnagraficaLogById(BaseDTO dto);
	public boolean saveAnagraficaLog(BaseDTO dto);
	public List<CsAAnagraficaLog> findStoricoBySoggetto(BaseDTO dto);

}
