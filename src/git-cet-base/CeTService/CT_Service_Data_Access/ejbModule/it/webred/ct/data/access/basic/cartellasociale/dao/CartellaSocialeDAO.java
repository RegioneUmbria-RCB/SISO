package it.webred.ct.data.access.basic.cartellasociale.dao;

import java.util.List;

import it.webred.ct.data.access.basic.cartellasociale.dto.InterventoDTO;

public interface CartellaSocialeDAO {
	
	public List<InterventoDTO> getInterventiByCF(String cf);
	
}
