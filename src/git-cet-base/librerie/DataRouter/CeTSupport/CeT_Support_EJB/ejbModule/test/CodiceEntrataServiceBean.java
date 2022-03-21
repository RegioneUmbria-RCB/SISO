package test;


import it.webred.ct.support.model.CodiceEntrata;


import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import test.dao.CodiceEntrataDAO;

/**
 * Session Bean implementation class CodiceEntrataServiceBean
 */
@Stateless
public class CodiceEntrataServiceBean implements CodiceEntrataService {

	@Autowired
	private CodiceEntrataDAO codiceDAO;
 
	public List<CodiceEntrata> getListaCodici(TestParam param) {
		return codiceDAO.getListaCodici();
	}

   

}
