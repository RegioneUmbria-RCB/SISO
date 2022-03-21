package it.webred.wsClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.security.Base64Encoder;
import org.springframework.web.client.RestTemplate;

import com.gargoylesoftware.htmlunit.WebClient;

import it.netservice.pda.utenze.ServiziUtenzeWSProxy;


public class WsAvvocaturaClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	
		 System.out.println("COD.FISCALE:");
		 String cf = bufferRead.readLine();
		Boolean response = isAvvocato(cf);
		
		System.out.println("cf: "+cf+" "+ (response ? "" : " non ") +"è un avvocato  ");
	
		}catch(SSLHandshakeException re){
			System.out.println("Impossibile verificare lo stato del soggetto: Errore SSL."+re.getMessage());
			re.printStackTrace();
		} catch (Exception e) { 
			System.out.println(e.getMessage());
			//e.printStackTrace();
			
		
		}

	}
	
	public static boolean isAvvocato(String cf) throws WsAvvocaturaException{
		Boolean result = false;
		ServiziUtenzeWSProxy proxy = new ServiziUtenzeWSProxy();   		
		try{   
		    result = proxy.isPdAUser(cf);
		}catch(RemoteException re){
			re.printStackTrace();
			throw new WsAvvocaturaException("Impossibile verificare lo stato del soggetto: "+re.getMessage());
		}
		return result;
			
	}


/*	 public static void setCredentials(String username, String password,DefaultHttpClient httpClient) {
	        UsernamePasswordCredentials creds =
	                new UsernamePasswordCredentials(username, password);
	        AuthScope authScope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);

	        httpClient.getCredentialsProvider().setCredentials(authScope, creds);
	    }
	
	 public static String isAvvocatoRest(String cf) throws IOException{
		 DefaultHttpClient httpClient = new DefaultHttpClient();
	      	String JAVA_VERSION = "java.version";
	        String JAVAX_NET_DEBUG = "javax.net.debug";
	        String JAVAX_NET_SSL_TRUSTSTORE = "javax.net.ssl.trustStore";
	        String JAVAX_NET_SSL_KEYSTORE = "javax.net.ssl.keyStore";

	        String DEBUG_OPTS = "ssl,handshake";
	        //String LOCAL_KS = "C:/Users/USER/Desktop/SERVERcert";
	        
	        System.out.println("Java Version: " + System.getProperty(JAVA_VERSION));
	    
	        System.setProperty(JAVAX_NET_DEBUG, DEBUG_OPTS);
	        System.setProperty("java.security.debug", "all");  
	        System.setProperty(JAVAX_NET_SSL_TRUSTSTORE, "C:\\keystoreAvv.jks"); //"C:\\Program Files\\Java\\jre6\\lib\\security\\cacerts");
	        System.setProperty(JAVAX_NET_SSL_KEYSTORE, "C:\\keystoreAvv.jks");
	        
	        //System.setProperty(JAVAX_NET_SSL_KEYSTORE, "C:\\Program Files\\Java\\jre6\\lib\\security\\cacerts");
	        
	       System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
	        System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
		 
		  String serviceURL = "https://monza.ul.consiglioordineavvocati.it:8082/utenze";
		  String username="portale.monza";
		  String password="7cfr#tg_6y!";
		  setCredentials(username, password,httpClient);
		  
		 StringEntity input = new StringEntity(cf);
		 HttpPost post = new HttpPost(serviceURL);
		 
		 post.addHeader("Content-type", "text/xml");
		 post.setEntity(input);
		 
	
		 
		 System.out.println("executing request " + post.getRequestLine());
		 HttpResponse response = httpClient.execute(post);
		 HttpEntity entity = response.getEntity();
		 
		 BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));
		 String line = "";
		 while ((line = rd.readLine()) != null) {
		 System.out.println(line);
		 
		}
		 
		 return line;
	}*/

}
