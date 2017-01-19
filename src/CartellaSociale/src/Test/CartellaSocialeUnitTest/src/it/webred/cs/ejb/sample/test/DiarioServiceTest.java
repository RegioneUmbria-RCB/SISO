package it.webred.cs.ejb.sample.test;

import static org.junit.Assert.assertNotNull;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsDColloquio;
import it.webred.cs.data.model.CsDColloquioBASIC;
import it.webred.cs.jsf.bean.colloquio.ColloquioRowBean;
import it.webred.cs.sociosan.ejb.client.AtlanteSessionBeanRemote;
import it.webred.cs.sociosan.ejb.dto.ServiziDTO;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.ws.client.atlante.client.AtlanteClient;
import it.webred.siso.ws.client.atlante.client.dto.GetServiziOspiteDTO;

import java.io.Console;
import java.net.URL;
import java.util.List;

import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;

public class DiarioServiceTest {

    private static AccessTableDiarioSessionBeanRemote lookupRemoteEJB() throws NamingException {
    	AccessTableDiarioSessionBeanRemote ejb = ClientUtility.getEJBInterfaceForRemoteClient(AccessTableDiarioSessionBeanRemote.class , "CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean", "");
    	
    	return ejb;
    }
    
    @Test
	public void getColloqiosTest() throws Exception {
		AccessTableDiarioSessionBeanRemote bean = lookupRemoteEJB();

		BaseDTO dto = new BaseDTO();
		dto.setEnteId("G148");

		Long soggettoId = 7626100L;
		dto.setObj(soggettoId);

		List<CsDColloquioBASIC> lstColloquio = bean.getColloquios(dto);
		if(lstColloquio != null)
			System.out.println("Lista colloqui : " + lstColloquio.size());
	}
}
