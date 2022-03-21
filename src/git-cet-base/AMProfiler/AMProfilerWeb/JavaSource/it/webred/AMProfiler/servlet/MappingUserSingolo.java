package it.webred.AMProfiler.servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import it.umbriadigitale.ldap.LdapFacade;
import it.umbriadigitale.ldap.LdapUser;
import it.umbriadigitale.ldap.UserAssoc;
import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;

/**
 * Servlet implementation class for Servlet: SalvaUtente
 * 
 */
public class MappingUserSingolo extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	
	private static final Logger LOG = Logger.getLogger(MappingUserSingolo.class.getName());

	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/AnagraficaServiceBean")
	protected AnagraficaService anagraficaService;
	
	private static final long serialVersionUID = 1L;

	protected static Logger logger = Logger.getLogger("am.log");

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public MappingUserSingolo() {
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		if(mode!=null && mode.equals("ricerca")){
			LdapFacade ldap = new LdapFacade();
			
			String forUser = request.getParameter("forUser");
			UserAssoc current = null;
			
			if(forUser != null){
				List<UserAssoc> listaAnagrafica = (List<UserAssoc>) request.getSession().getAttribute("userList");
				for(UserAssoc ua : listaAnagrafica){
					if(forUser.equals(ua.getGitUserName())){
						current = ua;
						request.getSession().setAttribute("currUserAssoc", current);
						break;
					}
				}
			}else{
				current = (UserAssoc)request.getSession().getAttribute("currUserAssoc");
			}
			
			String nome = request.getParameter("ricNome");
			String cognome = request.getParameter("ricCognome");
			String user = request.getParameter("ricUser");
			String mail = request.getParameter("ricMail");
			
			List<LdapUser> listaLdapUser = ldap.getUserAssoc(current.getAnag(), nome, cognome, user, mail);
			request.getSession().setAttribute("ldapList", listaLdapUser);
			request.getRequestDispatcher("/jsp/MappingUserSingolo.jsp").forward(request,response);
			
		}else if(mode!=null && mode.equals("assocSingle")){
			
			String user = request.getParameter("user");
			UserAssoc current = (UserAssoc)request.getSession().getAttribute("currUserAssoc");
			try {
				LdapFacade.associaUser(current, user);
			} catch (Exception e) {
				e.printStackTrace();
//				MappingUsers.cleanSession(request.getSession());//dato che ritorna nella pagina da cui è scoppiato se cancello le variabili salvate poi scoppia per forza per NullointerException
				BaseAction.toErrorPage(request, response, e);
				return;
			}
			request.getRequestDispatcher("/jsp/MappingUsers.jsp").forward(request,response);
		}else if(mode!=null && mode.equals("back")){
			request.getRequestDispatcher("/jsp/MappingUsers.jsp").forward(request,response);
		}
	}

	
	
	
}


