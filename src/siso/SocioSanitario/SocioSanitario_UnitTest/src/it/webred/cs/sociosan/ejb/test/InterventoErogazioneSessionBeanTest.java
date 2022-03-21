package it.webred.cs.sociosan.ejb.test;


import static org.junit.Assert.fail;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO;
import it.webred.cs.sociosan.ejb.client.AccessTableInterventoErogazioneSessionBeanClientRemote;
import it.webred.cs.sociosan.ejb.client.ArgoBufferManagerSessionBeanRemote;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.ct.support.validation.CeTToken;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;
 

public class InterventoErogazioneSessionBeanTest {
   
	@Test
	public void testFindInterventoErogazioneByIdSettoreEroganteData() {

		
		ArgoBufferManagerSessionBeanRemote sendsisomail =null;
		try {
			sendsisomail = ClientUtility.getEJBInterfaceForSTANDALONERemoteClient(ArgoBufferManagerSessionBeanRemote.class , "SocioSanitario", "SocioSanitario_EJB", "ArgoBufferManagerSessionBean", "");
		} catch (NamingException e1) {
			
			e1.printStackTrace();
			fail(e1.getMessage());
		}
	
		AccessTableInterventoErogazioneSessionBeanClientRemote sb=null;
		try {
			sb = ClientUtility.getEJBInterfaceForSTANDALONERemoteClient(AccessTableInterventoErogazioneSessionBeanClientRemote.class ,"SocioSanitario", "SocioSanitario_EJB",  "AccessTableInterventoErogazioneSessionBeanClient", "");
		} catch (NamingException e1) {
			
			e1.printStackTrace();
			fail(e1.getMessage());
		}

		
		Object out = null;
		try {
			
			if(sb!=null){
				CeTToken tok = new CeTToken();
				
				tok.setEnte("A262");
				tok.setSessionId("67de69a3-e4c7-4e03-8d97-8db54f0afce2");
				
				FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO dtoInput = new FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO();
	/*			ArrayList<BigDecimal> settori = new ArrayList<BigDecimal>();
				//settori.add(new BigDecimal(8654));
				settori.add(new BigDecimal(24050));
				//settori.add(new BigDecimal(8652));
				//settori.add(new BigDecimal(8653));
			
				dtoInput.setIdSettori(settori);*/
				dtoInput.setDataValiditaErogazione(new Date());
				
				out = sb.findInterventoErogazioneByIdSettoreEroganteData(tok, dtoInput) ;
			}
			
		} catch (SocioSanitarioException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fail(e1.getMessage());

		}
		
       Assert.assertNotNull(out);

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
			bDto.setEnteId("G148"); // inserire orig_belfiore
			bDto.setObj(new Long(148)); // inserire orig_id
			SsScheda ss = ssSchedaSegrService.readScheda(bDto);
					
		

      
       Assert.assertNull(null);
	//	MailUtils.sendEmail(mailParams, new MailAddressList(emailDest), null, null, "prova mailutils", "corpo del messaggio", null);


		
	}
  
}