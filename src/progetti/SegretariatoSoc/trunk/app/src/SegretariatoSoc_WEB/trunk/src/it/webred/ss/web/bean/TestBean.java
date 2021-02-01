package it.webred.ss.web.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsTest;
import it.webred.ss.ejb.client.TestSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

@ManagedBean
@ViewScoped
public class TestBean extends SegretariatoSocBaseBean{

	public void getTest() {
		
		try {
			//questo metodo recupera un ejb in base al nome dell'EAR, al nome del jar contenente l'ejb e il nome della classe dell'ejb
    		TestSessionBeanRemote testService = (TestSessionBeanRemote) ClientUtility.getEjbInterface(
    				"SegretariatoSoc", "SegretariatoSoc_EJB", "TestSessionBean");
    		
    		System.out.println("______*_*_* SONO DENTRO IL BEAN");
    		
    		//per poter gestire diversi database (configurati in datarouter.xml)
    		//in base all'utente loggato occorre usare come argomento 
    		//una classe che estenda CeTBaseObject, come BaseDTO e valorizzare almeno EnteId,
    		//questo e altri campi sono gestiti nel metodo fillUserData
    		BaseDTO dto = new BaseDTO();
        	fillUserData(dto);
        	dto.setObj("descrizione");
    		List<SsTest> lista = testService.getTestByDescrizione(dto);
    		
    		for(SsTest test: lista)
    			System.out.println("_______*_*_* " + test.getId() + " " + test.getDescrizione());

    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.error");
		}
		
	}
	
	public void saveTest() {
		
		try {
    		
			SsTest ssTest = new SsTest();
			ssTest.setDescrizione("ciao");
			ssTest.setPrezzo(new BigDecimal(4.49));
			ssTest.setData(new Date());
			
    		TestSessionBeanRemote testService = (TestSessionBeanRemote) ClientUtility.getEjbInterface(
    				"SegretariatoSoc", "SegretariatoSoc_EJB", "TestSessionBean");
			
    		BaseDTO dto = new BaseDTO();
        	fillUserData(dto);
        	dto.setObj(ssTest);
    		testService.saveTest(dto);

    		addInfo("salva.ok");
    		
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		addError("salva.error");
		}
		
	}
	
}
