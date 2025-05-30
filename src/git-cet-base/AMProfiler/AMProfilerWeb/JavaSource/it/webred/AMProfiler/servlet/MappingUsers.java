package it.webred.AMProfiler.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import it.umbriadigitale.ldap.LdapFacade;
import it.umbriadigitale.ldap.UserAssoc;
import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.anagrafica.dto.AnagraficaSearchCriteria;
import it.webred.amprofiler.model.AmAnagrafica;

/**
 * Servlet implementation class for Servlet: SalvaUtente
 * 
 */
public class MappingUsers extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/AnagraficaServiceBean")
	protected AnagraficaService anagraficaService;

	private static final long serialVersionUID = 1L;
	protected static Logger LOG = Logger.getLogger("am.log");
	
	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public MappingUsers() {
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
		String mode = request.getParameter("mode");//mode=null
		if(mode!=null && mode.equals("assoc")){
			
			List<UserAssoc> listaAnagrafica = (List<UserAssoc>) request.getSession().getAttribute("userList");
			List<UserAssoc> listaAssoc = new ArrayList<UserAssoc>(); 
			for(UserAssoc ua: listaAnagrafica){
				String chekBox = request.getParameter(ua.getGitUserName());	 
				if(null != chekBox){
					ua.setMapping(true);
					listaAssoc.add(ua);
				}
			}
			try {
				LdapFacade.associaUser(listaAssoc);
			} catch (Exception e) {
				e.printStackTrace();
				MappingUsers.cleanSession(request.getSession());
				BaseAction.toErrorPage(request, response, e);
				return;
			}
			request.getRequestDispatcher("/jsp/MappingUsers.jsp").forward(request,response);
			
		}else if(mode!=null && mode.equals("assocSingle")){//non c'è nulla da fare, è una lista di puntamenti quindi li trova già cambiati ed allo stesso posto.
//			List<UserAssoc> listaAnagrafica = (List<UserAssoc>) request.getSession().getAttribute("userList");//TODO: con la modifica dell'utente già associato
		}else if(mode!=null && mode.equals("findLdap")){
			
			List<AmAnagrafica> listaAnagraficaDaAssociare = (List<AmAnagrafica>) request.getSession().getAttribute("userListDaAssoc");
			List<AmAnagrafica> listaAnagraficaAssociata = (List<AmAnagrafica>) request.getSession().getAttribute("userListAssoc");
			LdapFacade ldap = new LdapFacade();
			
			List<UserAssoc> listaAnagrafica = new ArrayList<UserAssoc>();
			listaAnagrafica.addAll(UserAssoc.assoc(listaAnagraficaDaAssociare, ldap));
			for(AmAnagrafica an: listaAnagraficaAssociata){
				UserAssoc ua = new UserAssoc(an, null);
				listaAnagrafica.add(ua);
			}
			Collections.sort(listaAnagrafica);
			request.getSession().setAttribute("userList", listaAnagrafica);
			request.getRequestDispatcher("/jsp/MappingUsers.jsp").forward(request,response);
			
		}else{
			AnagraficaSearchCriteria ricerca = new AnagraficaSearchCriteria();
			String loggedUser = request.getUserPrincipal().getName();
			try {
				//criteri generali
				ricerca.setMaxResult(1000);
				ricerca.setDisableCause("@@null");
				//criteri x quelli da associare
				List<AmAnagrafica> listaAnagraficaDaAssociare = new ArrayList<AmAnagrafica>();
				ricerca.setOldUser("@@null");
				listaAnagraficaDaAssociare 	= anagraficaService.getListaAnagrafica(ricerca);
				ricerca.setOldUser(null);
				//criteri x quelli già associati
				List<AmAnagrafica> listaAnagraficaAssociata = new ArrayList<AmAnagrafica>();
				ricerca.setOldUser("@@!null");
				listaAnagraficaAssociata 	= anagraficaService.getListaAnagrafica(ricerca);
				
				if(LOG.isInfoEnabled())LOG.info("listaAnagraficaDaAssociare:"+listaAnagraficaDaAssociare.size()+"; listaAnagraficaAssociata:"+listaAnagraficaAssociata.size());
				
				request.getSession().setAttribute("nUserTot", listaAnagraficaDaAssociare.size()+listaAnagraficaAssociata.size());
				request.getSession().setAttribute("nUserToAssoc", listaAnagraficaDaAssociare.size());
				request.getSession().setAttribute("userListDaAssoc", listaAnagraficaDaAssociare);
				request.getSession().setAttribute("userListAssoc", listaAnagraficaAssociata);
				
				request.getRequestDispatcher("/jsp/MappingUsers.jsp").forward(request,response);
			}
			catch (Exception e) {
				BaseAction.toErrorPage(request, response, e);
			}
		}
	}
	
	public static void cleanSession(HttpSession session){
		session.removeAttribute("userList");
		session.removeAttribute("currUserAssoc");
		session.removeAttribute("ldapList");
		
		session.removeAttribute("nUserTot");
		session.removeAttribute("nUserToAssoc");
		session.removeAttribute("userListDaAssoc");
		session.removeAttribute("userListAssoc");
	}

	
	
}


