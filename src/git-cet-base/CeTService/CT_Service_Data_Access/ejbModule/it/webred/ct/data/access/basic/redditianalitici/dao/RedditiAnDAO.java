package it.webred.ct.data.access.basic.redditianalitici.dao;

import it.webred.ct.data.access.basic.redditi.dto.KeySoggDTO;
import it.webred.ct.data.access.basic.redditianalitici.RedditiAnServiceException;
import it.webred.ct.data.model.redditi.analitici.RedAnFabbricati;

import java.util.List;

public interface RedditiAnDAO {
	
	public List<RedAnFabbricati> getQuadroRBFabbricatiByKeyAnno(KeySoggDTO rs) throws RedditiAnServiceException;
	
}
