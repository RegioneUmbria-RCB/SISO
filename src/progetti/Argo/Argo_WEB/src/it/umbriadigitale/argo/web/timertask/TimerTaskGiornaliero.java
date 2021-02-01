package it.umbriadigitale.argo.web.timertask;

import it.umbriadigitale.argo.ejb.client.cs.bean.ArSiruService;
import it.umbriadigitale.argo.ejb.client.cs.dto.siru.SiruJsonProgettiDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.siru.SiruJsonTokenDTO;
import it.umbriadigitale.argo.web.base.ArgoBaseBean;
import it.webred.ejb.utility.ClientUtility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.URL;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.TimerTask;

import javax.interceptor.ExcludeDefaultInterceptors;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExcludeDefaultInterceptors
public class TimerTaskGiornaliero extends TimerTask {

	private static Logger logger;
	private final String KEY_AUTHENTICATION = "smartwelfare.umbria.api.authentication";
	private final String KEY_URL_TOKEN = "smartwelfare.umbria.api.token.url";
	private final String KEY_URL_PROGETTI = "smartwelfare.umbria.api.progettiSiru.url";
	private final String KEY_JKS_PATH = "smartwelfare.umbria.api.jks.path";
	private final String KEY_JKS_PASSWORD = "smartwelfare.umbria.api.jks.password";
	
	public TimerTaskGiornaliero() {
		super();
	}

	@Override
	public void run() {

		try {
			logger.debug("__ INIZIO Esecuzione ARGO TimerTaskGiornaliero__");
			
	    	this.aggiornaProgettiSiru();
	    	
			logger.debug("__ FINE Esecuzione ARGO TimerTaskGiornaliero__");

		} catch (Exception e) {
			logger.error("__ ARGO TimerTaskGiornaliero: Eccezione su : " + e.getMessage(), e);
		}
	}
		
	private String getAuthToken(){
		String responseToken = null;
		
	    String authentication = ArgoBaseBean.getParametroGlobale(this.KEY_AUTHENTICATION);
	    String tokenUrl = ArgoBaseBean.getParametroGlobale(this.KEY_URL_TOKEN);
	    if(!StringUtils.isBlank(authentication) && !StringUtils.isBlank(tokenUrl)){
	   
		    BufferedReader reader = null;
		    HttpsURLConnection connection = null;
		    try {
		        URL url = new URL(tokenUrl);
		        connection = (HttpsURLConnection) url.openConnection();
		        connection.setRequestMethod("POST");
		        connection.setDoOutput(true);
		        connection.setRequestProperty("Authorization", "Basic " + authentication);
		        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		        connection.setRequestProperty("Accept", "application/json");
		        
		        configuraCertificato(connection);
		        
		        String content = "grant_type=client_credentials";
		        PrintStream os = new PrintStream(connection.getOutputStream());
		        os.print(content);
		        os.close();
		        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		        String line = null;
		        StringWriter out = new StringWriter(connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
		        while ((line = reader.readLine()) != null) {
		            out.append(line);
		        }
		        String response = out.toString();
		        //logger.debug("getAuthToken:"+response);
		        if(response!=null && !response.isEmpty()){ 
		    		ObjectMapper objectMapper = new ObjectMapper();
		    		SiruJsonTokenDTO tokenDTO = objectMapper.readValue(response, SiruJsonTokenDTO.class);
		    		responseToken = tokenDTO.getAccess_token();
		        }
		       
		        
		    } catch (Exception e) {
		        logger.error("Error : " + e.getMessage());
		    } finally {
		        if (reader != null) {
		            try {
		                reader.close();
		            } catch (IOException e) {
		            }
		        }
		        connection.disconnect();
		    }
	    }else{ 
	    	if(StringUtils.isBlank(authentication)) logger.warn("Parametro "+this.KEY_AUTHENTICATION+" non impostato");
	    	if(StringUtils.isBlank(tokenUrl)) logger.warn("Parametro "+this.KEY_URL_TOKEN+" non impostato");
	    }

		return responseToken;
    	
	}
	
	private void configuraCertificato(HttpsURLConnection con) {
	    SSLContext ctx;
	    String jksPwd = ArgoBaseBean.getParametroGlobale(this.KEY_JKS_PASSWORD);
	    String jksPath = ArgoBaseBean.getParametroGlobale(this.KEY_JKS_PATH);
	    
	    if(!StringUtils.isBlank(jksPath) && !StringUtils.isBlank(jksPwd) ){
		    try {
		      KeyStore truststore = KeyStore.getInstance("JKS");
	
		      FileInputStream in = new FileInputStream(jksPath);
		      truststore.load(in,jksPwd.toCharArray());
		      
		      ctx = SSLContext.getInstance("SSL");
		      TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		      tmf.init(truststore);
		      ctx.init(null, tmf.getTrustManagers(), null);
		    
		     con.setSSLSocketFactory(ctx.getSocketFactory());
			
		    } catch (Exception e) {
			      logger.error("Errore configurazione certificato: "+ e.getMessage(), e);
		    }
	    
	    }else{
	    	if(StringUtils.isBlank(jksPath)) logger.warn("Parametro "+this.KEY_JKS_PATH+" non impostato");
	    	if(StringUtils.isBlank(jksPwd)) logger.warn("Parametro "+this.KEY_JKS_PASSWORD+" non impostato");
	    }
	}	
	
	private String readJsonFile() throws IOException{

		FileReader fileReader = new FileReader("C:\\Load\\SISO\\response_siru.json");
		StringBuffer stringBuffer = new StringBuffer();
		int numCharsRead;
		char[] charArray = new char[1024];
		while ((numCharsRead = fileReader.read(charArray)) > 0) {
			stringBuffer.append(charArray, 0, numCharsRead);
		}
		
		return stringBuffer.toString();
	}
	

	public void aggiornaProgettiSiru() {
		
		logger.debug("__ INIZIO TimerTaskGiornaliero: aggiornaProgettiSiru");
		int numInsert = 0;
	  // HttpClient client = new HttpClient();	
	  String token = this.getAuthToken();
	  String progettiURL = ArgoBaseBean.getParametroGlobale(KEY_URL_PROGETTI);
	  HttpsURLConnection con = null;
	  BufferedReader reader = null;
	  if(!StringUtils.isBlank(token) && !StringUtils.isBlank(progettiURL)){	      
	  
	   try{        

			URL url = new URL(progettiURL);
			con = (HttpsURLConnection)url.openConnection();
			con.addRequestProperty("Authorization", "Bearer "+token);
			con.addRequestProperty("accept", "application/json");
			con.setDoOutput(true);
			configuraCertificato(con);
		 
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String line = null;
	        StringWriter out = new StringWriter(con.getContentLength() > 0 ? con.getContentLength() : 2048);
	        while ((line = reader.readLine()) != null) {
	            out.append(line);
	        }
	        
	      //String json = this.readJsonFile();
	        String json = out.toString();
	        logger.debug("getStream:"+json);	
	        
    	    if(json!=null && !json.isEmpty()){ 
	    		ObjectMapper objectMapper = new ObjectMapper();
	    		SiruJsonProgettiDTO[] elencoProgetti = objectMapper.readValue(json, SiruJsonProgettiDTO[].class);
	    		
	    		ArSiruService service = (ArSiruService) ClientUtility.getEjbInterface("Argo", "Argo_EJB","ArSiruServiceBean");
				
	    		if(elencoProgetti!=null) numInsert = service.save(Arrays.asList(elencoProgetti));
	    		
	    		//Se va a buon fine il caricamento della tabella di staging, aggiorno i progetti.
	    		service.aggiornaTabelleFinali();
    	    }
    		
    		logger.debug("__ FINE TimerTaskGiornaliero: aggiornaProgettiSiru: inseriti " + numInsert + " nuovi progetti");
			
    	} catch (Exception e2) {
			logger.error("__ERRORE TimerTaskGiornaliero: aggiornaProgettiSiru : " + e2.getMessage(), e2);
		}finally{
			 if (reader != null) {
		            try {
		                reader.close();
		            } catch (IOException e) {
		            }
		        }
			 con.disconnect();
		}
	  }else{
		  if(StringUtils.isBlank(token)) logger.warn("Token non valorizzato");
		  if(StringUtils.isBlank(progettiURL)) logger.warn("Parametro "+this.KEY_JKS_PASSWORD+" non impostato");
	  }
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		TimerTaskGiornaliero.logger = logger;
	}
	
	

}
