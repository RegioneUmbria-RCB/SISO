package it.webred.ss.ejb.client;

import java.util.List;

import javax.ejb.Remote;

import it.webred.ss.data.model.SsTest;
import it.webred.ss.ejb.dto.BaseDTO;


@Remote
public interface TestSessionBeanRemote {

	public List<SsTest> getTestByDescrizione(BaseDTO dto);
	
	public void saveTest(BaseDTO dto);
	
}