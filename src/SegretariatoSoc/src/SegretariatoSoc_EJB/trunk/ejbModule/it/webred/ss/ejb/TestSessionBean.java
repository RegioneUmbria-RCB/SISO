package it.webred.ss.ejb;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import it.webred.ss.dao.TestDAO;
import it.webred.ss.data.model.SsTest;
import it.webred.ss.ejb.client.TestSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;

@Stateless
public class TestSessionBean implements TestSessionBeanRemote  {
	
	@Autowired
	private TestDAO testDao;
	
	@Override
	public List<SsTest> getTestByDescrizione(BaseDTO dto) {

		System.out.println("______*_*_* SONO DENTRO L'EJB");
		//qui verranno preparate le chiamate per il/i DAO e gestiti i dati ottenuti 
		return testDao.getTestByDescrizione((String) dto.getObj());
		
	}
	
	@Override
	public void saveTest(BaseDTO dto) {

		testDao.saveTest((SsTest) dto.getObj());
		
	}
	
}
