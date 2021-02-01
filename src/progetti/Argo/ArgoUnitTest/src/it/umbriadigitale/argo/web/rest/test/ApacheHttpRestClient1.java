package it.umbriadigitale.argo.web.rest.test;

import it.umbriadigitale.argo.web.rest.dto.EchoRequest;
import it.umbriadigitale.argo.web.rest.dto.EchoResponse;
import it.webred.ct.support.validation.CeTToken;
import it.webred.ejb.utility.ClientUtility;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert; 
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
 
/**
 * A simple Java REST GET example using the Apache HTTP library.
 * This executes a call against the Yahoo Weather API service, which is
 * actually an RSS service (<a href="http://developer.yahoo.com/weather/" title="http://developer.yahoo.com/weather/">http://developer.yahoo.com/weather/</a>).
 * 
 * Try this Twitter API URL for another example (it returns JSON results):
 * <a href="http://search.twitter.com/search.json?q=%40apple" title="http://search.twitter.com/search.json?q=%40apple">http://search.twitter.com/search.json?q=%40apple</a>
 * (see this url for more twitter info: <a href="https://dev.twitter.com/docs/using-search" title="https://dev.twitter.com/docs/using-search">https://dev.twitter.com/docs/using-search</a>)
 * 
 * Apache HttpClient: <a href="http://hc.apache.org/httpclient-3.x/" title="http://hc.apache.org/httpclient-3.x/">http://hc.apache.org/httpclient-3.x/</a>
 *
 */
public class ApacheHttpRestClient1 {
 
	//http://localhost:8080/SocioSanitario_WEB/rest/main/execute/Echo
	//HttpHost target = new HttpHost("localhost", 8080 , "http");
	
	private String host="localhost";   
	private int port=8080;			
	
/*	private String host="172.29.1.6";   
	private int port=8080;					*/
	
	private void startEcho(Object d) throws ClientProtocolException, IOException, URISyntaxException {
		  HttpClient httpClient = HttpClientBuilder.create().build();

		try   {
			String json = null;
	     	ObjectMapper objectMapper = new ObjectMapper();
		    	try {
		    		json = objectMapper.writeValueAsString(d);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					Assert.fail(e.getMessage());
				}
		    	
			 // specify the host, protocol, and port
		    HttpHost target = new HttpHost("localhost", 8080, "http");
		     
		    
		    URIBuilder builder = new URIBuilder();
 		    builder.setScheme("http").setHost(host).setPort(port).setPath("/Argo_WEB/rest/main/execute/Echo");
		    builder.addParameter("req", json);

		    HttpGet getRequest = new HttpGet(builder.build());

		 
		    
		    // specify the get request
		 //   HttpGet getRequest = new HttpGet("/SocioSanitario_WEB/rest/main/execute/Echo");
		 //   getRequest. r("req", json);
		    
		    System.out.println("executing request to " + target);
	
		    HttpResponse httpResponse = httpClient.execute(target, getRequest);
		    HttpEntity entity = httpResponse.getEntity();
	
		    System.out.println("----------------------------------------");
		    System.out.println(httpResponse.getStatusLine());
		    Header[] headers = httpResponse.getAllHeaders();
		    for (int i = 0; i < headers.length; i++) {
		      System.out.println(headers[i]);
		    }
		    System.out.println("----------------------------------------");
	
		    if (entity != null) {
		  	  if (entity.getContentType().getValue().equals("application/json")) 
		  	  {
		  	  String pagesource = EntityUtils.toString(entity); //IOUtils.toString( entity.getContent(), "UTF-8");  
		  	  EchoResponse resp = objectMapper.readValue(pagesource, EchoResponse.class);
		  	  }
		    }
		} finally  {
			httpClient.getConnectionManager().shutdown();  
		} 
	}
	
	
	@Test
	  public void testEcho() {
		    try {
				EchoRequest d = prepareEchoRequest();
				
				startEcho(d);
			  	 
				Assert.assertTrue(true);

			  	  
		    } catch (Exception e) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
		    } 
	  }
	
	@Test
	  public void testEchoFail() {
		    try {
				
		    	// inviando una stringa invece che l'oggetto desiderao dal servizio, DEVE tornare una response di errore
				startEcho("pippo");
				
				Assert.fail("Errore NON riscontrato come invece atteso");
			  	

		    } catch (Exception e) {
				
				Assert.assertTrue(true);
		    } 
	  }
	
	private EchoRequest prepareEchoRequest() {
		EchoRequest req = new EchoRequest();
		req.setInput("ciao! come stai oggi?!" );
		return	 req;
		
	}
	

		
	
 
 
	 
  public static void main(String[] args) {
	  
   // DefaultHttpClient httpclient = new DefaultHttpClient();
   
  
  }
  
}