package it.webred.cs.sociosan.web.rest.test;

import it.webred.AMProfiler.rest.services.dto.LoginDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO;
import it.webred.cs.csa.ejb.dto.mobi.upload.UploadInterventiErogazioneResponseDTO;
import it.webred.cs.sociosan.ejb.client.AccessTableInterventoErogazioneSessionBeanClientRemote;
import it.webred.cs.sociosan.web.rest.dto.EchoRequest;
import it.webred.cs.sociosan.web.rest.dto.EchoResponse;
import it.webred.cs.sociosan.web.rest.dto.FindCasiByUsernameOperatoreRequest;
import it.webred.cs.sociosan.web.rest.dto.FindCasiByUsernameOperatoreResponse;
import it.webred.cs.sociosan.web.rest.dto.FindInterventoErogazioneByIdSettoreEroganteDataRequest;
import it.webred.cs.sociosan.web.rest.dto.FindInterventoErogazioneByIdSettoreEroganteDataResponse;
import it.webred.cs.sociosan.web.rest.dto.GeoCodeRequest;
import it.webred.cs.sociosan.web.rest.dto.GeoCodeResponse;
import it.webred.cs.sociosan.web.rest.dto.InvioSegnalazioneRequest;
import it.webred.cs.sociosan.web.rest.dto.InvioSegnalazioneRequestDTO;
import it.webred.cs.sociosan.web.rest.dto.InvioSegnalazioneResponse;
import it.webred.cs.sociosan.web.rest.dto.StatusSegnalazioneRequest;
import it.webred.cs.sociosan.web.rest.dto.StatusSegnalazioneResponse;
import it.webred.cs.sociosan.web.rest.dto.UploadDataRestServiceDataRequest;
import it.webred.cs.sociosan.web.rest.dto.UploadDataRestServiceDataResponse;
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
 		    builder.setScheme("http").setHost(host).setPort(port).setPath("/SocioSanitario_WEB/rest/main/execute/Echo");
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
		    HttpHost target = new HttpHost(host, port, "http");
		     
		    
		    URIBuilder builder = new URIBuilder();
 		    builder.setScheme("http").setHost(host).setPort(port).setPath("/SocioSanitario_WEB/rest/main/execute/GeoCodeRestService");
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
	  public void testFindInterventoErogazioneByIdSettoreEroganteDataService() {
		    try {
				
				CeTToken token = startLogin(prepareLoginDTO());
				
				FindInterventoErogazioneByIdSettoreEroganteDataRequest d = 
						prepareFindInterventoErogazioneByIdSettoreEroganteDataRequest(token);
				
				startFindInterventoErogazioneByIdSettoreEroganteDataService(d);
			  	 
				Assert.assertNotNull(d);

		    } catch (Exception e) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
		    } 
	  }
	
	
	
	@Test
	  public void testUploadInterventoErogazioneDataService() {
		    try {
				
				CeTToken token = startLogin(prepareLoginDTO());
			
				UploadDataRestServiceDataRequest d = new UploadDataRestServiceDataRequest();
				d.setToken(token); 
				
				startUploadInterventoErogazioneDataService(d);
			  	 
				Assert.assertNotNull(d);

			  	  
		    } catch (Exception e) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
		    } 
	  }
	
	
	private FindInterventoErogazioneByIdSettoreEroganteDataResponse startFindInterventoErogazioneByIdSettoreEroganteDataService(
			FindInterventoErogazioneByIdSettoreEroganteDataRequest d) throws URISyntaxException, ClientProtocolException, IOException {

		  HttpClient httpClient = HttpClientBuilder.create().build();

		  FindInterventoErogazioneByIdSettoreEroganteDataResponse resp = null;
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
		    HttpHost target = new HttpHost(host, port, "http");
			
		    
		    URIBuilder builder = new URIBuilder();
		    builder.setScheme("http").setHost(host).setPort(port).setPath("/SocioSanitario_WEB/rest/main/execute/FindInterventoErogazioneByIdSettoreEroganteDataRestService");
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
			    resp = objectMapper.readValue(pagesource,  FindInterventoErogazioneByIdSettoreEroganteDataResponse.class);
		  	  }
		    }
		    return resp;
		} finally  {
			httpClient.getConnectionManager().shutdown();  
		} 
		
	}
	
	private UploadDataRestServiceDataResponse startUploadInterventoErogazioneDataService(
			UploadDataRestServiceDataRequest d) throws URISyntaxException, ClientProtocolException, IOException {

		  HttpClient httpClient = HttpClientBuilder.create().build();

		  UploadDataRestServiceDataResponse resp = null;
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
		    HttpHost target = new HttpHost(host, port, "http");
		 
		    URIBuilder builder = new URIBuilder();
		    builder.setScheme("http").setHost(host).setPort(port).setPath("/SocioSanitario_WEB/rest/main/execute/UploadInterventoErogazioneDataRestService");
		    builder.addParameter("req", json);
		   
		    HttpPost httpost = new HttpPost(builder.build());
		   // httpost.setEntity(new StringEntity("{\"filters\":true}"));
	    
		    System.out.println("executing request to " + target);
	
		    HttpResponse httpResponse = httpClient.execute(target, httpost);
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
			    resp = objectMapper.readValue(pagesource,  UploadDataRestServiceDataResponse.class);
		  	  }
		    }
		    return resp;
		} finally  {
			httpClient.getConnectionManager().shutdown();  
		} 
		
	}
	
	
	  @Test
	  public void testInvioSegnalazioneService() {
		    try {
				
				CeTToken token = startLogin(prepareLoginDTO());

						
				InvioSegnalazioneRequest req = new InvioSegnalazioneRequest();
				req.setToken(token); 
				
				InvioSegnalazioneRequestDTO dtoInput =  new InvioSegnalazioneRequestDTO();

				dtoInput.setCognome("PIERINI");
				dtoInput.setNome("PIERINI");
				dtoInput.setComuneResidenza("G148");
				dtoInput.setDataNascita("2007-12-15");
				req.setInput(dtoInput);

				startInvioSegnalazioneService(req);
			  	 
				Assert.assertNotNull(req);

			  	  
		    } catch (Exception e) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
		    } 
	  }
	

	  @Test
	  public void testStatusSegnalazioneService() {
		    try {
				
				CeTToken token = startLogin(prepareLoginDTO());

						
				StatusSegnalazioneRequest req = new StatusSegnalazioneRequest();
				req.setToken(token); 
				
				String unId =  "21212121212";

				req.setInput(unId);

				startStatusSegnalazioneService(req);
			  	 
				Assert.assertNotNull(req);

			  	  
		    } catch (Exception e) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
		    } 
	  }

	  
		private StatusSegnalazioneResponse startStatusSegnalazioneService(
				StatusSegnalazioneRequest d) throws URISyntaxException, ClientProtocolException, IOException {

			  HttpClient httpClient = HttpClientBuilder.create().build();

			  StatusSegnalazioneResponse resp = null;
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
			    HttpHost target = new HttpHost(host, port, "http");
			     
			    	
			  
			    URIBuilder builder = new URIBuilder();
			    builder.setScheme("http").setHost(host).setPort(port).setPath("/SocioSanitario_WEB/rest/main/execute/StatusSegnalazioneRestService");
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
				    resp = objectMapper.readValue(pagesource,  StatusSegnalazioneResponse.class);
			  	  }
			    }
			    return resp;
			} finally  {
				httpClient.getConnectionManager().shutdown();  
			} 
			
		}
		
	  
	private InvioSegnalazioneResponse startInvioSegnalazioneService(
			InvioSegnalazioneRequest d) throws URISyntaxException, ClientProtocolException, IOException {

		  HttpClient httpClient = HttpClientBuilder.create().build();

		  InvioSegnalazioneResponse resp = null;
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
		    HttpHost target = new HttpHost(host, port, "http");
		     
		    	
		  
		    URIBuilder builder = new URIBuilder();
		    builder.setScheme("http").setHost(host).setPort(port).setPath("/SocioSanitario_WEB/rest/main/execute/InvioSegnalazioneRestService");
		    builder.addParameter("req", json);

		    HttpPost getRequest = new HttpPost(builder.build());
		    
	    
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
			    resp = objectMapper.readValue(pagesource,  InvioSegnalazioneResponse.class);
		  	  }
		    }
		    return resp;
		} finally  {
			httpClient.getConnectionManager().shutdown();  
		} 
		
	}
	

	@Test
	  public void testFindCasiByUsernameOperatoreService() {
		    try {
				
				CeTToken token = startLogin(prepareLoginDTO());

						
				FindCasiByUsernameOperatoreRequest req = new FindCasiByUsernameOperatoreRequest();
				req.setToken(token); 
				
				it.webred.cs.csa.ejb.dto.mobi.FindCasiByUsernameOperatoreRequestDTO dtoInput = new it.webred.cs.csa.ejb.dto.mobi.FindCasiByUsernameOperatoreRequestDTO();
				dtoInput.setUsername("CPRMRA50A01F704P");
				
				req.setInput(dtoInput);

				startFindCasiByUsernameOperatoreService(req);
			  	 
				Assert.assertNotNull(req);

			  	  
		    } catch (Exception e) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
		    } 
	  }
	
	private FindCasiByUsernameOperatoreResponse startFindCasiByUsernameOperatoreService(
			FindCasiByUsernameOperatoreRequest d) throws URISyntaxException, ClientProtocolException, IOException {

		  HttpClient httpClient = HttpClientBuilder.create().build();

		  FindCasiByUsernameOperatoreResponse resp = null;
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
		    HttpHost target = new HttpHost(host, port, "http");
		     
		    	
		  
		    URIBuilder builder = new URIBuilder();
		    builder.setScheme("http").setHost(host).setPort(port).setPath("/SocioSanitario_WEB/rest/main/execute/FindCasiByUsernameOperatoreRestService");
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
			    resp = objectMapper.readValue(pagesource,  FindCasiByUsernameOperatoreResponse.class);
		  	  }
		    }
		    return resp;
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
	
	private GeoCodeRequest prepareGeoCodeRequest(String indirizzo, CeTToken token) {
		GeoCodeRequest req = new GeoCodeRequest();
		req.setToken(token); 
		req.setInput(indirizzo);
		return	 req;
		
	}
	
	private FindInterventoErogazioneByIdSettoreEroganteDataRequest prepareFindInterventoErogazioneByIdSettoreEroganteDataRequest(CeTToken token) {
		FindInterventoErogazioneByIdSettoreEroganteDataRequest req = new FindInterventoErogazioneByIdSettoreEroganteDataRequest();
		req.setToken(token); 
		
	/*	it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO dtoInput = new it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO();
		dtoInput.setIdSettori(idSettori);
		dtoInput.setDataValiditaErogazione(data);
		
		req.setInput(dtoInput);*/
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
	      HttpHost target = new HttpHost(host, port, "http");
	       
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
	  d.setPwd("test");
	  d.setUserName("CPRMRA50A01F704P");
	  
	  return d;
	  
  }
	 
  public static void main(String[] args) {
	  
   // DefaultHttpClient httpclient = new DefaultHttpClient();
   
  
  }
  
}