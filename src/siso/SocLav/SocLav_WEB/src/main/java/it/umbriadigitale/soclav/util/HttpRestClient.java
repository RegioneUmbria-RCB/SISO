package it.umbriadigitale.soclav.util;

import java.net.URI;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.umbriadigitale.soclav.service.dto.LoginDTO;
import it.webred.ct.support.validation.CeTToken;



public class HttpRestClient {

	 public EsitoAut startLogin(LoginDTO d, String percorsoAuth) throws LoginException{
		 EsitoAut esitoAut = new EsitoAut();
		 
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
						
					}
				  
			    	URIBuilder builder = new URIBuilder();
				      //builder.setScheme("http").setHost(host).setPort(port).setPath("/AMProfiler/rest/login/getPermessiUtente")
				      builder.setPath(percorsoAuth);
				       URI uri = builder.build();
				      HttpGet httpget = new HttpGet(uri);
				      httpget.setHeader("req", loginDTOJson);
				        
				      System.out.println("executing request to " + httpget);
				 
				      HttpResponse httpResponse = httpClient.execute( httpget);
				      HttpEntity entity = httpResponse.getEntity();
		 
				      System.out.println("----------------------------------------");
				      System.out.println(httpResponse.getStatusLine());
				      if(httpResponse.getStatusLine().getStatusCode()  == 401) {
				    	  esitoAut.setEsito(- httpResponse.getStatusLine().getStatusCode());
				    	  esitoAut.setMessaggio("Login o password non valide!");
				    	  esitoAut.setUrl("jsp/public/AccessoNegato.xhtml");
				    	  return esitoAut;
				      }
				      else  if(httpResponse.getStatusLine().getStatusCode()  != 200) {
				    	  esitoAut.setEsito(- httpResponse.getStatusLine().getStatusCode());
				    	  esitoAut.setMessaggio("Errore : ");
				    	  esitoAut.setUrl("jsp/public/AccessoNegato.xhtml");
				    	  return esitoAut;
				      }
				      Header[] headers = httpResponse.getAllHeaders();
				      for (int i = 0; i < headers.length; i++) {
				        System.out.println(headers[i]);
				      }
				      System.out.println("----------------------------------------");
				 
				      if (entity != null) {
				    	  if (entity.getContentType().getValue().equals("application/json")) 
				    	  {
				    	  String pagesource = EntityUtils.toString(entity);
				    	  System.out.println(pagesource);
				    	  cetToken = objectMapper.readValue(pagesource, CeTToken.class);
				    	  esitoAut.setCetToken(cetToken);
				    	  esitoAut.setEsito(0);
				    	  }
				      }
		
				      
				    } 
		    		catch (Exception e) {
		    			 esitoAut.setEsito(-1100);
				    	 esitoAut.setMessaggio("Eccezione nella richiesta di autenticazione al servizio AMProfiler");
						 esitoAut.setEccezione(e);
				    }
		    		finally {
		
				    	httpClient.getConnectionManager().shutdown();    
				  }
					return esitoAut;
	  }
	 
	 public HashMap<String, String> getPermissionUtente(String host, int port, String method, String login, String  ente) {
		  HttpClient httpClient = HttpClientBuilder.create().build();

		  HashMap<String, String> cetToken = null;
		  
		    try {
			 
		    	//HttpHost target = new HttpHost(host,port);
		    	String loginDTOJson = null;
		     	ObjectMapper objectMapper = new ObjectMapper();
              HttpGet getRequest = new HttpGet("/AMProfiler/rest/login/getPermessiUtente");
		      
		      URIBuilder builder = new URIBuilder();
		      //builder.setScheme("http").setHost(host).setPort(port).setPath("/AMProfiler/rest/login/getPermessiUtente")
		      builder.setScheme("http").setHost(host).setPort(port).setPath("/AMProfiler/rest/login/getPermessiUtente")
		          .setParameter("u", login)
		          .setParameter("e", ente);
		      URI uri = builder.build();
		      HttpGet httpget = new HttpGet(uri);
		      System.out.println(httpget.getURI());
		      
		      //getRequest.setHeader("req", loginDTOJson);
		      //getRequest.setEntity(new ByteArrayEntity( loginDTOJson.getBytes()));
		      
		      System.out.println("executing request to " + httpget);
		 
		      HttpResponse httpResponse = httpClient.execute( httpget);
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
		    	  String pagesource = EntityUtils.toString(entity);
		    	  System.out.println(pagesource);
		    	  cetToken = objectMapper.readValue(pagesource, HashMap.class);
		    	  
		    	  }
		      }

		      
		    } catch (Exception e) {
				e.printStackTrace();
				 
		    } finally {

		    	httpClient.getConnectionManager().shutdown();    
		  }
			return cetToken;
	  }
	 
	 public HashMap<String, String> getPermissionUtente(String percorso, String login, String  ente) throws Exception {
		  HttpClient httpClient = HttpClientBuilder.create().build();

		  HashMap<String, String> cetToken = null;
		  
		    try {
			 
		     	String loginDTOJson = null;
		     	ObjectMapper objectMapper = new ObjectMapper();
               URIBuilder builder = new URIBuilder();
		      //builder.setScheme("http").setHost(host).setPort(port).setPath("/AMProfiler/rest/login/getPermessiUtente")
		      builder.setPath(percorso)
		          .setParameter("u", login)
		          .setParameter("e", ente);
		      URI uri = builder.build();
		      HttpGet httpget = new HttpGet(uri);
		      System.out.println(httpget.getURI());
		       
		      System.out.println("executing request to " + httpget);
		 
		      HttpResponse httpResponse = httpClient.execute( httpget);
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
		    	  String pagesource = EntityUtils.toString(entity);
		    	  System.out.println(pagesource);
		    	  cetToken = objectMapper.readValue(pagesource, HashMap.class);
		    	  
		    	  }
		      }

		      
		    } catch (Exception e) {
				e.printStackTrace();
				throw e;
				 
		    } finally {

		    	httpClient.getConnectionManager().shutdown();    
		  }
			return cetToken;
	  }
	 
	 public EsitoAut verificaPriKUtente(String percorso, String priK) throws Exception {
		  HttpClient httpClient = HttpClientBuilder.create().build();

		  EsitoAut esitoAut = null;
		  
		    try {
			 
		     	String loginDTOJson = null;
		     	ObjectMapper objectMapper = new ObjectMapper();
	              URIBuilder builder = new URIBuilder();
			       builder.setPath(percorso)
			          .setParameter("priK", priK);
			      URI uri = builder.build();
			      HttpGet httpget = new HttpGet(uri);
			      System.out.println(httpget.getURI());
		       
		      System.out.println("executing request to " + httpget);
		 
		      HttpResponse httpResponse = httpClient.execute( httpget);
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
		    	  String pagesource = EntityUtils.toString(entity);
		    	  System.out.println(pagesource);
		    	  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		    	  esitoAut = objectMapper.readValue(pagesource, EsitoAut.class);
		    	  
		    	  }
		      }

		      
		    } catch (Exception e) {
				e.printStackTrace();
				throw e;
				 
		    } finally {

		    	httpClient.getConnectionManager().shutdown();    
		  }
			return esitoAut;
	  }
	 public CeTToken testRest(String token, String host, int port, String method) {
		  HttpClient httpClient = HttpClientBuilder.create().build();

		  CeTToken cetToken = null;
		  
		    try {
//				String loginDTOJson = null;
//		     	ObjectMapper objectMapper = new ObjectMapper();
//			    	try {
//						loginDTOJson = objectMapper.writeValueAsString(d);
//					      System.out.println(loginDTOJson);
//					 	 
//					} catch (JsonProcessingException e) {
//						e.printStackTrace();
//						
//					}
				  
		      // specify the host, protocol, and port
		      //HttpHost target = new HttpHost(host, port, "http");
			   HttpHost target = new HttpHost(host,port);
			   
		      // specify the get request
		      HttpGet getRequest = new HttpGet(method);
		      getRequest.setHeader("token", token);
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
		 
//		      if (entity != null) {
//		    	  if (entity.getContentType().getValue().equals("application/json")) 
//		    	  {
//		    	  String pagesource = EntityUtils.toString(entity);
//		    	  System.out.println(pagesource);
//		    	  cetToken = objectMapper.readValue(pagesource, CeTToken.class);
//		    	  
//		    	  }
//		      }

		      
		    } catch (Exception e) {
				e.printStackTrace();
				 
		    } finally {

		    	httpClient.getConnectionManager().shutdown();    
		  }
			return cetToken;
	  }
}
