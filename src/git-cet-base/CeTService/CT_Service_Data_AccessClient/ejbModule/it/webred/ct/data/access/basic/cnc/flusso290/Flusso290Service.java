package it.webred.ct.data.access.basic.cnc.flusso290;
import it.webred.ct.data.access.basic.cnc.flusso290.dto.AnagraficaImpostaDTO;
import it.webred.ct.data.access.basic.cnc.flusso290.dto.Flusso290SearchCriteria;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface Flusso290Service {

	public List<AnagraficaImpostaDTO> getAnagraficaImpostaSintesi(Flusso290SearchCriteria criteria, int startm, int numberRecord);
	
	public Long getRecordCount(Flusso290SearchCriteria criteria) ;
}
