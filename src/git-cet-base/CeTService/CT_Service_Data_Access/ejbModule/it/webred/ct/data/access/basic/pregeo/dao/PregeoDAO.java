package it.webred.ct.data.access.basic.pregeo.dao;

import it.webred.ct.data.access.basic.pregeo.PregeoServiceException;
import it.webred.ct.data.access.basic.pregeo.dto.RicercaPregeoDTO;
import it.webred.ct.data.model.pregeo.PregeoInfo;

import java.util.List;

public interface PregeoDAO {
	
	public List<PregeoInfo> getDatiPregeoByFabbricato(RicercaPregeoDTO rp) throws PregeoServiceException;
	
	public List<String> getListaParticelleByNomeFilePdf(RicercaPregeoDTO rp) throws PregeoServiceException;
	
	public List<PregeoInfo> getPregeoByCriteria(RicercaPregeoDTO rp) throws PregeoServiceException;
	
}
