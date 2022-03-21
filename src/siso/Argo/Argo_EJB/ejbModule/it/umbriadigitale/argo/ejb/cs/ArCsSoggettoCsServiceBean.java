package it.umbriadigitale.argo.ejb.cs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import it.umbriadigitale.argo.data.cs.data.ArCsSoggettoCs;
import it.umbriadigitale.argo.ejb.client.cs.bean.ArCsSoggettoCsService;
import it.umbriadigitale.argo.ejb.client.cs.dto.ArCsSoggettoCsDTO;
import it.umbriadigitale.argo.ejb.cs.dao.ArCsSoggettoCsDAO;

/**
 * 
 * @author andrea.niccolini
 *
 */
@Stateless
public class ArCsSoggettoCsServiceBean implements ArCsSoggettoCsService {

	@Autowired
	private ArCsSoggettoCsDAO arCsSoggettoCsDAO;
	
	
	@Override
	public List<ArCsSoggettoCsDTO> getSoggetti() {

		List<ArCsSoggettoCs> soggetti = arCsSoggettoCsDAO.getSoggetti();
		
		List<ArCsSoggettoCsDTO> soggettiDTO = new ArrayList<ArCsSoggettoCsDTO>();
		
		// encoding DTO
		
		return soggettiDTO;
	}

	@Override
	public void save(ArCsSoggettoCsDTO soggetto) {
		// TODO Auto-generated method stub

	}

}
