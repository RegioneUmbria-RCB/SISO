package it.umbriadigitale.argo.ejb.client.cs.bean;

import java.util.List;

import javax.ejb.Remote;

import it.umbriadigitale.argo.ejb.client.cs.dto.ArCsSoggettoCsDTO;

@Remote
public interface ArCsSoggettoCsService {

	List<ArCsSoggettoCsDTO> getSoggetti();
	
	void save(ArCsSoggettoCsDTO soggetto);
}
