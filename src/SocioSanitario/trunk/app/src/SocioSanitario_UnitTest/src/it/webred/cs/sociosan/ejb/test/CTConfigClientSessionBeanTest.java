package it.webred.cs.sociosan.ejb.test;

import static org.junit.Assert.fail;
import it.webred.cs.sociosan.ejb.client.ArgoBufferManagerSessionBeanRemote;
import it.webred.cs.sociosan.ejb.client.CTConfigClientSessionBeanRemote;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.ejb.utility.ClientUtility;
import it.webred.mailing.MailUtils.MailParamBean;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;
 

public class CTConfigClientSessionBeanTest {
 
	
	@Test
	public void testSendSimpleMailFromSISO() {

		ArgoBufferManagerSessionBeanRemote sendsisomail =null;
		try {
			sendsisomail = ClientUtility.getEJBInterfaceForSTANDALONERemoteClient(ArgoBufferManagerSessionBeanRemote.class , "SocioSanitario", "SocioSanitario_EJB", "ArgoBufferManagerSessionBean", "");
		} catch (NamingException e1) {
			
			e1.printStackTrace();
			fail(e1.getMessage());
		}

		String emailDest=null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Email destinatario------------------->");
        try {
        	emailDest = br.readLine();
		} catch (IOException e) {
			fail("errore input mail");
			e.printStackTrace();
		}
        
	   try {
		sendsisomail.sendSimpleMailFromSISO(emailDest, "prova mail SISO", "CORPO PROVA MAIL SISO");
	} catch (SocioSanitarioException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		fail(e.getMessage());
		
	}
       Assert.assertNull(null);


		
	}

	
	@Test
	public void testSISOMailParameters() {

		CTConfigClientSessionBeanRemote confTest=null;
		try {
			confTest = ClientUtility.getEJBInterfaceForSTANDALONERemoteClient(CTConfigClientSessionBeanRemote.class , "SocioSanitario", "SocioSanitario_EJB", "CTConfigClientSessionBean", "");
		} catch (NamingException e1) {
			
			e1.printStackTrace();
			fail(e1.getMessage());
		}

		MailParamBean mailParams = null;
		try {
			mailParams = confTest.getSISOMailParametres();
		} catch (SocioSanitarioException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fail(e1.getMessage());

		}
		

      
       Assert.assertNull(null);
	//	MailUtils.sendEmail(mailParams, new MailAddressList(emailDest), null, null, "prova mailutils", "corpo del messaggio", null);


		
	}
	
	
	@Test
	public void testCallRemoteEJB() {

		 SsSchedaSessionBeanRemote ssSchedaSegrService =null;

		try {
			ssSchedaSegrService = ClientUtility.getEJBInterfaceForSTANDALONERemoteClient( SsSchedaSessionBeanRemote.class , "SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean", "", "localhost");
		} catch (NamingException e1) {
			
			e1.printStackTrace();
			fail(e1.getMessage());
		}

		 BaseDTO bDto = new BaseDTO();
			bDto.setEnteId("G148");
			bDto.setObj(new Long(148));
			SsScheda ss = ssSchedaSegrService.readScheda(bDto);
					
		

      
       Assert.assertNull(null);
	//	MailUtils.sendEmail(mailParams, new MailAddressList(emailDest), null, null, "prova mailutils", "corpo del messaggio", null);


		
	}
  
}