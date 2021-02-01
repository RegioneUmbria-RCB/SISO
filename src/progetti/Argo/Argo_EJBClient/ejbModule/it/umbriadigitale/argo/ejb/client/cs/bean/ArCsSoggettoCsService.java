package it.umbriadigitale.argo.ejb.client.cs.bean;

import java.util.List;
import it.umbriadigitale.argo.ejb.client.cs.dto.ArCsSoggettoCsDTO;

/**
 * 
 * @author andrea.niccolini
 *
 */
public interface ArCsSoggettoCsService {

	List<ArCsSoggettoCsDTO> getSoggetti();
	
	void save(ArCsSoggettoCsDTO soggetto);
}
