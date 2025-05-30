package it.webred.AMProfiler.servlet;

import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.dto.AmBaseDTO;
import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmTracciaAccessi;
import it.webred.amprofiler.model.AmUserUfficio;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.application.ApplicationService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

public class AccessoBase extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = -5287078022975184239L;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/LoginBean")
	protected LoginBeanService loginService;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/UserServiceBean")
	protected UserService userService;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/AnagraficaServiceBean")
	protected AnagraficaService anagraficaService;
	
	protected ApplicationService applicationService = (ApplicationService)BaseAction.getCTConfigEjb("ApplicationServiceBean");
	protected ParameterService parameterService = (ParameterService)BaseAction.getCTConfigEjb("ParameterBaseService");
	protected ComuneService comuneService = (ComuneService)BaseAction.getCTConfigEjb("ComuneServiceBean");
	
	protected AmAnagrafica anagrafica = null;
	
	protected static Logger logger = Logger.getLogger("am.log");

	public AccessoBase() {
	}//-------------------------------------------------------------------------
	
	//SOCLAV 
	protected void aggiornaTracciaAccessi(String userName,  String ragioneAccesso, String pratica, String ente, String sessionId, String prik, String pubk) {
		try {
			 
			if (ragioneAccesso == null || ragioneAccesso.equalsIgnoreCase(""))
				ragioneAccesso = "NON INDICATA";
			if (pratica == null || pratica.equalsIgnoreCase(""))
				pratica = "NON INDICATA";
			 

			AmTracciaAccessi traccia = new AmTracciaAccessi();
			traccia.setUserName(userName);
			traccia.setRagioneAccesso(ragioneAccesso);
		 	traccia.setPratica(pratica);
			traccia.setEnte(ente);
			traccia.setSessionId(sessionId);
			
			traccia.setPubk(pubk);
			traccia.setPrik(prik);
			AmBaseDTO dto = new AmBaseDTO();
	    	dto.setEnteId(ente);
	    	dto.setUserId(userName);
	    	dto.setSessionId(sessionId);
	    	dto.setObj1(traccia);
			
			loginService.aggiornaTracciaAccessiBySessionID(dto);
			

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			 
		}  
	}
	//fine SOCLAV
	protected void salvaTracciaAccessi(HttpServletRequest request,String userName, String pathApp, String ragioneAccesso, String pratica, String ente, String sessionId,String prik, String pubk) {
		
		// salvo accesso come traccia
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = BaseAction.apriConnessione();
			con.setAutoCommit(false);

			if (ragioneAccesso == null || ragioneAccesso.equalsIgnoreCase(""))
				ragioneAccesso = "NON INDICATA";
			if (pratica == null || pratica.equalsIgnoreCase(""))
				pratica = "NON INDICATA";
			
			// sto modificando l'ente nella combo , non sto accedendo
			if (pathApp==null) {
				this.aggiornaTracciaAccessi(userName, ragioneAccesso, pratica, ente, sessionId,prik ,pubk);
				return;
			}
				
				
			if (pathApp.endsWith("/") || pathApp.endsWith("\\"))
				pathApp = pathApp.substring(0, pathApp.length() - 1);
			
		    
			
			String fkAmApp = getApplicationByPathApp(pathApp,con);
			if(fkAmApp==null){ //Estraggo dal link la radice
				String rootApp = this.getRootApp(pathApp, request);
				fkAmApp = getApplicationByPathApp(rootApp,con);
			}

			AmTracciaAccessi traccia = new AmTracciaAccessi();
			traccia.setUserName(userName);
			traccia.setRagioneAccesso(ragioneAccesso);
			traccia.setFkAmItem(fkAmApp);
			traccia.setPratica(pratica);
			traccia.setEnte(ente);
			traccia.setSessionId(sessionId);
			
			//SOCLAV : paggiunto parametro
			traccia.setPrik(prik);
			//traccia.setPubk(pubk); // non tuilizzabile perch� gi� in uso in alcuni ambiti di Monza
			
			AmBaseDTO dto = new AmBaseDTO();
	    	dto.setEnteId(ente);
	    	dto.setUserId(userName);
	    	dto.setSessionId(sessionId);
	    	dto.setObj1(traccia);
			
			loginService.salvaTracciaAccessi(dto);
			

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			try {
				BaseAction.rollback(con);
			} catch (Exception e1) {
			}
		} finally {
			BaseAction.chiudiConnessione(con, st);
		}
	}//-------------------------------------------------------------------------
	
	protected String getApplicationByPathApp(String pathApp,Connection con) throws SQLException{
		String fkAmApp = null;
		PreparedStatement st = null;
		ResultSet rs=null;
		String p1 = pathApp;
		if(pathApp!=null){
			
			//La request restituisce http anche quando l'accesso è da https
			p1 = p1.replace("http://", "");
			p1 = p1.replace("https://", "");
			
			try{
				String sql = "SELECT NAME,FK_AM_APPLICATION FROM AM_INSTANCE WHERE URL LIKE ? ";
				st = con.prepareStatement(sql);
				st.setString(1, "%"+p1);
				rs = st.executeQuery();
				if (rs.next()) {
					fkAmApp = rs.getString("NAME");
				}
				
				if(fkAmApp==null)
					logger.warn("Nessuna applicazione associata all'URL:"+pathApp);
				else
					logger.info("Trovata applicazione "+fkAmApp+" associata all'URL:"+pathApp);
			
			}catch(Exception e){
				logger.warn(e.getMessage(),e);
			}finally{
				try {
					rs.close();
					st.close();
				} catch (SQLException e) {
				}
			}
		}
		return fkAmApp;
	}//-------------------------------------------------------------------------
	
	protected String getRootApp(String pathApp,HttpServletRequest request){
		String rootPath =null;
		String[] aPath = pathApp.split("\\/");
		String serverPort = request.getServerName()+":"+ request.getServerPort();
		String server = request.getServerName();
		boolean trovato = false;
		int i = 0;
		while(!trovato && i<aPath.length){
			if(aPath[i].indexOf(serverPort)!=-1 || aPath[i].indexOf(server)!=-1)
				trovato = true;
			i++;
		}
		
		if(trovato){
			rootPath ="";
			for(int j=0;j<=i;j++)
			    rootPath+= aPath[j]+"/";
			
			if (rootPath.endsWith("/") || rootPath.endsWith("\\"))
				rootPath = rootPath.substring(0, rootPath.length() - 1);
		}else{
			logger.warn("getRootApp: non ho trovato la stringa ["+server+"] in ["+pathApp+"]");
		}
		return rootPath;
		
	}//-------------------------------------------------------------------------
	
	public String encode(String stringToEncode) {
		String returnValue = "";
		BASE64Encoder encrypt = new BASE64Encoder();
		try {
			String codedString = encrypt.encode(stringToEncode.getBytes());
			returnValue = codedString;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return returnValue;
	}//-------------------------------------------------------------------------
	
	protected boolean verificaDatiSoggettoObbligatori(String user){
		
		boolean anagraficaPresente = true;
		AmUserUfficio ufficio = userService.getDatiUfficioNoCaseSens(user);
		anagrafica = anagraficaService.findAnagraficaByUserName(user);
		
		if(anagrafica==null)
			anagraficaPresente=false;
		else{
			
			if(anagrafica.getCognome()==null || anagrafica.getCognome().equals(""))
				anagraficaPresente=false;
			if(anagrafica.getNome()==null || anagrafica.getNome().equals(""))
				anagraficaPresente=false;
			if(anagrafica.getDataNascita()==null)
				anagraficaPresente=false;
		}
		
		if(ufficio.getDirezione()==null || ufficio.getDirezione().equals(""))
			anagraficaPresente = false;
		if(ufficio.getSettore()==null || ufficio.getSettore().equals(""))
			anagraficaPresente = false;
		if(ufficio.getTelefono()==null || ufficio.getTelefono().equals(""))
			anagraficaPresente = false;
		if(ufficio.getEmail()==null || ufficio.getEmail().equals(""))
			anagraficaPresente = false;
		
		return anagraficaPresente;
	}//-------------------------------------------------------------------------


	
	protected String getParametroGlobale(String key){
		return this.getParametro(key, "param.globali");
	}
	
	protected String getParametro(String key, String section){
		String val = null;
		
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey(key);
		criteria.setSection(section);
		AmKeyValueExt amKey = parameterService.getAmKeyValueExt(criteria);
		if (amKey != null)
			val =  amKey.getValueConf();
		else
			val = null;
		return val;
	}
	
}
