package it.umbriadigitale.argo.ejb.cs.dao;

import java.util.List;
import it.umbriadigitale.argo.data.cs.data.ArCsSoggettoCs;

/**
 * 
 * @author andrea.niccolini
 *
 */
public interface ArCsSoggettoCsDAO {

	List<ArCsSoggettoCs> getSoggetti();
	
	
	public void save(ArCsSoggettoCs soggetto);
}
