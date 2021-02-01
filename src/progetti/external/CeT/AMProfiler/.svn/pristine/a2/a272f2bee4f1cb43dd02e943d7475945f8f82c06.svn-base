package it.webred.AMProfiler.servlet;

import it.webred.AMProfiler.beans.AmComune;
import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUser;
import it.webred.amprofiler.model.AmUserUfficio;

import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

/**
 * Servlet implementation class for Servlet: SalvaUtente
 * 
 */
public class SceltaEnte extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	

	protected static Logger logger = Logger.getLogger("am.log");
	private static final long serialVersionUID = 1L;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/UserServiceBean")
	protected UserService userService;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/AnagraficaServiceBean")
	protected AnagraficaService anagraficaService;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public SceltaEnte() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection con = null;
		Statement statment = null;
		try {

			String userName = request.getParameter("userName");
			String enteScelto = BaseAction.getPerameter(request, "enteScelto");
			if (enteScelto == null || "".equals(enteScelto))
				enteScelto = (String)request.getSession().getAttribute("enteScelto");
			String pathApp = request.getParameter("pathApp");
			String myparam = request.getParameter("myparam");
			String sessionId = request.getSession().getId();
			Principal user = request.getUserPrincipal();
		
			//Controlla presenza dati soggetto, e richiedi se non presenti
			logger.debug("SceltaEnte-userName:"+userName);
			logger.debug("SceltaEnte-user:"+user.getName());
			logger.debug("SceltaEnte-pathApp:"+pathApp);

			
			if(user.getName()!=null && !user.getName().equals("")){
				boolean anagraficaPresente = this.verificaDatiSoggettoObbligatori(user.getName());
				logger.debug("SceltaEnte-anagraficaPresente:"+anagraficaPresente);
				if(!anagraficaPresente && pathApp!=null && !pathApp.equals("")){
					request.getRequestDispatcher("/SalvaUtente?pathApp="+pathApp+"&myParam="+myparam+"&mode=vis&soloDatiUfficio=true&userName="+user.getName()).forward(request,response);
					return;
				}
			}
			
			if (request.getParameter("doOnlyAMInsPratica") != null
					&& new Boolean(request.getParameter("doOnlyAMInsPratica"))
							.booleanValue()) {
				String disableCause = getDisableCause(user.getName());
				if (disableCause != null && !disableCause.trim().equals("")) {
					request.setAttribute("disableCause", disableCause);
					request.getRequestDispatcher("/jsp/disabledUser.jsp").forward(request, response);
				} else {
					request.getRequestDispatcher("/jsp/sceltaEnte.jsp").forward(request, response);
				}
				return;
			} else if (request.getParameter("doneInsPratica") != null
					&& new Boolean(request.getParameter("doneInsPratica"))
							.booleanValue()) {
			
				salvaTracciaAccessi(request,user.getName(),
						((HttpServletRequest) request).getRequestURL()
								.toString().replace("SceltaEnte", ""),
						request.getParameter("ragioneAccesso"),
						request.getParameter("pratica"), enteScelto, sessionId);
				request.getRequestDispatcher("/CaricaMenu?doneInsPratica=true")
						.forward(request, response);
				return;
			}

			ArrayList<AmComune> entiUtente = BaseAction
					.listaComuniByUser(userName);
			request.setAttribute("entiUtente", entiUtente);
			request.setAttribute("pathApp", pathApp);
			request.setAttribute("myparam", myparam);
			request.setAttribute("userName", userName);
			request.getSession().setAttribute("enteScelto", enteScelto);

			if (enteScelto == null || "".equals(enteScelto))
				request.getRequestDispatcher("/jsp/sceltaEnte.jsp").forward(
						request, response);
			else {
				
				
				salvaTracciaAccessi(request,userName, pathApp,
						request.getParameter("ragioneAccesso"),
						request.getParameter("pratica"), enteScelto, sessionId);
				if (myparam != null && !myparam.equals("")) {
					myparam = myparam.replaceAll("\\|", "\\&");
					myparam = myparam.replaceAll("_", "=");
					((HttpServletResponse) response).sendRedirect(pathApp
							+ "?es=" + encode(enteScelto) + "&" + myparam);
				} else{
					((HttpServletResponse) response).sendRedirect(pathApp + "?es=" + encode(enteScelto));
				}
			}

		} catch (Exception e) {
			BaseAction.toErrorPage(request, response, e);
		} finally {
			BaseAction.chiudiConnessione(con, statment);
		}

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
	
	
	private String getRootApp(String pathApp,HttpServletRequest request){
		
		String[] aPath = pathApp.split("\\/");
		String server = request.getServerName()+":"+ request.getServerPort();
		boolean trovato = false;
		int i = 0;
		while(!trovato && i<aPath.length){
			if(aPath[i].indexOf(server)!=-1)
				trovato = true;
			i++;
		}
		
		String rootPath ="";
		for(int j=0;j<=i;j++)
		    rootPath+= aPath[j]+"/";
		
		if (rootPath.endsWith("/") || rootPath.endsWith("\\"))
			rootPath = rootPath.substring(0, rootPath.length() - 1);
		
		return rootPath;
		
	}//-------------------------------------------------------------------------
	
	private String getApplicationByPathApp(String pathApp,Connection con) throws SQLException{
		String fkAmApp = null;
		PreparedStatement st = null;
		ResultSet rs=null;
		
		try{
			String sql = "SELECT NAME,FK_AM_APPLICATION FROM AM_INSTANCE WHERE URL = ?";
			st = con.prepareStatement(sql);
			st.setString(1, pathApp);
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
		
		return fkAmApp;
	}
	

	private void salvaTracciaAccessi(HttpServletRequest request,String userName, String pathApp, String ragioneAccesso, String pratica, String ente, String sessionId) {
		
		// salvo accesso come audit
		// TOLTO 13/11/2015
		/*AuditDBWriter dbWriter = new AuditDBWriter();
		String className = "it.webred.AMProfiler.servlet.SceltaEnte";
		String methodName = "salvaTracciaAccessi";
		String arguments[] = {pathApp,ragioneAccesso,pratica};
		try {
			dbWriter.auditMethod(ente, userName, sessionId,
					className, methodName, arguments, null, null);
		} catch (Throwable e) {
			logger.error(e.getMessage(),e);
		}*/
		
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
			if (pathApp==null)
				return;
			
			if (pathApp.endsWith("/") || pathApp.endsWith("\\"))
				pathApp = pathApp.substring(0, pathApp.length() - 1);
			
			String fkAmApp = getApplicationByPathApp(pathApp,con);
			if(fkAmApp==null){ //Estraggo dal link la radice
				String rootApp = this.getRootApp(pathApp, request);
				fkAmApp = getApplicationByPathApp(rootApp,con);
			}

			String sql = "SELECT NVL(MAX(ID), 0) + 1 AS NEWID FROM AM_TRACCIA_ACCESSI";
			st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			int newId = 0;
			while (rs.next()) {
				newId = rs.getInt("NEWID");
			}
			rs.close();
			st.cancel();

			sql = "INSERT INTO AM_TRACCIA_ACCESSI (USER_NAME, RAGIONE_ACCESSO, FK_AM_ITEM, PRATICA, ID) "
					+ "VALUES (?,?,?,?,?)";
			st = con.prepareStatement(sql);

			int paramIndex = 0;
			st.setString(++paramIndex, userName);
			st.setString(++paramIndex, ragioneAccesso);
			st.setString(++paramIndex, fkAmApp);
			st.setString(++paramIndex, pratica);
			st.setInt(++paramIndex, newId);
			st.executeUpdate();

			st.cancel();

			con.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			try {
				BaseAction.rollback(con);
			} catch (Exception e1) {
			}
		} finally {
			BaseAction.chiudiConnessione(con, st);
		}
	}
	
	private boolean verificaDatiSoggettoObbligatori(String user){
		
		boolean anagraficaPresente = true;
		AmUserUfficio ufficio = userService.getDatiUfficio(user);
		AmAnagrafica anagrafica = anagraficaService.findAnagraficaByUserName(user);
		
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
	}
	
	private String getDisableCause(String username) {
		try {
			AmUser user = userService.getUserByName(username);
			if (user == null) {
				user = userService.getUserByName(username.toUpperCase());
			}
			return user.getDisableCause();
		} catch (Exception e) {
			logger.error("ERRORE NELLA LETTURA DEI DATI DELL'UTENTE", e);
			return "ERRORE NELLA LETTURA DEI DATI DELL'UTENTE: " + e.getMessage();
		}
	}	
	
}