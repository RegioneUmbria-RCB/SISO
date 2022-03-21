package it.roma.comune.servizi.test;

import it.roma.comune.servizi.verificheanagrafiche.NVALocator;
import it.roma.comune.servizi.verificheanagrafiche.NVASoap;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Random;
 





import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.ConfigurationException;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.Handler;

import javax.xml.rpc.Call;

import org.apache.axis.configuration.FileProvider;
import org.apache.axis.deployment.wsdd.WSDDDeployment;
import org.apache.axis.deployment.wsdd.WSDDService;
import org.apache.ws.security.util.Base64;


public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		EngineConfiguration config = new FileProvider("it/roma/comune/servizi/config/client-deploy.wsdd");
	 

		
		
		NVALocator s= new NVALocator(config);

		NVASoap soap=null;
		
		try {
			 
			soap= s.getNVASoap(new URL("http://10.173.2.133/pa/pri/verifiche/nva.asmx"));
			
			//DEBUG
			//TestVerificaAnagrafica t= new TestVerificaAnagrafica(soap);
//	 		t.doTestDebug();
			//t.doTest();
		
		//FINE DEBUG
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(args[0] != null && args[0].equals("A")){
			TestVerificaAnagrafica t= new TestVerificaAnagrafica(soap);
	 			t.doTest(args[1],args[2], args[3], args[4],args[5], args[6], args[7], args[8],args[9]);
			try {
				//t.doTestDebug();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(args[0] != null && args[0].equals("E")){
			TestVerificaAnagraficaEstesa t1= new TestVerificaAnagraficaEstesa(soap);
			 t1.doTest(args[1],args[2], args[3], args[4],args[5], args[6]);
			try {
				//t1.doTestDebug();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(args[0] != null && args[0].equals("V")){
			TestVerificaStatoFamiglia t1= new TestVerificaStatoFamiglia(soap);
			 t1.doTest(args[1],args[2], args[3], args[4]);
			try {
				//t1.doTestDebug();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String getUID(){
		byte[] bytes = new byte[128];
		String temp=String.valueOf(new Random().nextDouble()) + String.valueOf(System.currentTimeMillis());
		java.security.MessageDigest sha=null;
		try {
		sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Random r= new Random(System.currentTimeMillis());
		r.nextBytes(bytes);
        sha.reset();
        sha.update(bytes);
        return Base64.encode(sha.digest());
	}
	
	
}
