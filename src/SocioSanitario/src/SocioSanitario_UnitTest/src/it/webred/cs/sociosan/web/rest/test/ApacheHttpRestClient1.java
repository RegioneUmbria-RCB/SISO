package it.webred.cs.sociosan.web.rest.test;

import it.webred.AMProfiler.rest.services.dto.LoginDTO;
import it.webred.cs.sociosan.web.rest.dto.EchoRequest;
import it.webred.cs.sociosan.web.rest.dto.EchoResponse;
import it.webred.cs.sociosan.web.rest.dto.GeoCodeRequest;
import it.webred.cs.sociosan.web.rest.dto.GeoCodeResponse;
import it.webred.ct.support.validation.CeTToken;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
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
 		    builder.setScheme("http").setHost("localhost").setPort(8080).setPath("/SocioSanitario_WEB/rest/main/execute/Echo");
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
	
	private GeoCodeResponse startGeoCodeService(Object d) throws ClientProtocolException, IOException, URISyntaxException {
		  HttpClient httpClient = HttpClientBuilder.create().build();

		  GeoCodeResponse resp = null;
		try   {
			String json = null;
	     	ObjectMapper objectMapper = new ObjectMapper();
		    	try {
		    		json = objectMapper.writeValueAsString(d);
				    System.out.println("REQUEST=\n" + json);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					Assert.fail(e.getMessage());
				}
		    	
			 // specify the host, protocol, and port
		    HttpHost target = new HttpHost("localhost", 8080, "http");
		     

		    
		    URIBuilder builder = new URIBuilder();
 		    builder.setScheme("http").setHost("localhost").setPort(8080).setPath("/SocioSanitario_WEB/rest/main/execute/GeoCodeRestService");
		    builder.addParameter("req", json);

		    HttpGet getRequest = new HttpGet(builder.build());
		    
	    
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
			    System.out.println(pagesource);
			    resp = objectMapper.readValue(pagesource, GeoCodeResponse.class);
		  	  }
		    }
		    return resp;
		} finally  {
			httpClient.getConnectionManager().shutdown();  
		} 
	}
	
   
	@Test
	  public void testGeoCodeRestService() {
		    try {
				
				CeTToken cetToken = startLogin(prepareLoginDTO());
				GeoCodeRequest d = prepareGeoCodeRequest("VIA ROMA, 14, UMBERTIDE, PG",cetToken);
				
				startGeoCodeService(d);
			  	 
				Assert.assertNotNull(d);

			  	  
		    } catch (Exception e) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
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
	
	private GeoCodeRequest prepareGeoCodeRequest(String indirizzo, CeTToken token) {
		GeoCodeRequest req = new GeoCodeRequest();
		req.setEnte(token.getEnte());
		req.setSessionId(token.getSessionId());
		req.setInput(indirizzo);
		return	 req;
		
	}
	
		
	
  @Test
  public void testLogin() {
	    try {
			LoginDTO d = prepareLoginDTO();
			
			startLogin(d);
		  	 
			Assert.assertTrue(true);

		  	  
	    } catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
	    } 

  }
	
  public CeTToken startLogin(LoginDTO d) {
	  HttpClient httpClient = HttpClientBuilder.create().build();

	  CeTToken cetToken = null;
	  
	    try {
			String loginDTOJson = null;
	     	ObjectMapper objectMapper = new ObjectMapper();
		    	try {
					loginDTOJson = objectMapper.writeValueAsString(d);
				      System.out.println(loginDTOJson);
				 	 
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					Assert.fail(e.getMessage());
				}
				    	
				    	
			  
			  
	      // specify the host, protocol, and port
	      HttpHost target = new HttpHost("localhost", 8080, "http");
	       
	      // specify the get request
	      HttpGet getRequest = new HttpGet("/AMProfiler/rest/login/access");
	      getRequest.setHeader("req", loginDTOJson);
	      //getRequest.setEntity(new ByteArrayEntity( loginDTOJson.getBytes()));
	      
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
	    	  System.out.println(pagesource);
	    	  cetToken = objectMapper.readValue(pagesource, CeTToken.class);
	    	  Assert.assertTrue(true);
	    	  }
	      }

	      
	    } catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
	    } finally {

	    	httpClient.getConnectionManager().shutdown();    
	  }
		return cetToken;
  }
	    	
		  
  private LoginDTO prepareLoginDTO() {
	  LoginDTO d = new LoginDTO();
	  d.setEnte("G148");
	  d.setPwd("profiler");
	  d.setUserName("profiler");
	  
	  return d;
	  
  }
	 
  public static void main(String[] args) {
	  
   // DefaultHttpClient httpclient = new DefaultHttpClient();
   
  
  }
  
}