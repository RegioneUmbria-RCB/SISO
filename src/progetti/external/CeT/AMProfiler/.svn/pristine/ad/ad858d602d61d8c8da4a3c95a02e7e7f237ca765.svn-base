package it.webred.AMProfiler.servlet;

import it.webred.AMProfiler.beans.AmComune;
import it.webred.AMProfiler.beans.AmGroup;
import it.webred.AMProfiler.beans.AmUser;
import it.webred.AMProfiler.exception.AMException;
import it.webred.AMProfiler.util.OreMinuti;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class for Servlet: NuovoGruppo
 * 
 */
public class NuovoGruppo extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	
	protected static Logger logger = Logger.getLogger("am.log");
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public NuovoGruppo() {
		super();
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
		try {
			
			String ente = request.getParameter("ente");
			String gruppo = request.getParameter("gruppo");
			String nuovoGruppo = request.getParameter("nuovoGruppo");
			String loggedUser = request.getUserPrincipal().getName();
			
			request.setAttribute("ore", new OreMinuti().getOre());
			request.setAttribute("minuti", new OreMinuti().getMinuti());
			
			if("Salva".equals(request.getParameter("salva")))
			{
				if( validate(gruppo, nuovoGruppo, ente, request)){
					if(nuovoGruppo != null && !nuovoGruppo.equals("")){
						BaseAction.NuovoGruppo(nuovoGruppo, ente, request);
						gruppo = nuovoGruppo;
					}
					
					BaseAction.SalvaUserGruppi(gruppo, request.getParameterValues("utenti"));
					request.setAttribute("msgNuovoGruppo", "Salvataggio avvenuto correttamente");
					
					if(validatePermessiAccesso(request))
						BaseAction.UpdatePermessiAccessoGruppo(request);
				}
				
			}
			
			if(ente != null && !"".equals(ente)){
				ArrayList<AmGroup> gruppi = BaseAction.listaGruppiPerEnte(ente.trim());
				ArrayList<AmUser> utentiGruppi = BaseAction.listaUsers();
				ArrayList<AmUser> utentiGruppiOk = new ArrayList();
				for(AmUser u: utentiGruppi){
					if(BaseAction.checkUserVisible(loggedUser, u.getName()))
						utentiGruppiOk.add(u);
				}
				request.setAttribute("utentiGruppi", utentiGruppiOk);
				request.setAttribute("gruppiEnte", gruppi);
				request.setAttribute("ente", ente);
			}
			
			if(gruppo != null && !"".equals(gruppo)){
				ArrayList<AmUser> utentiGruppi = BaseAction.listaUsers(gruppo.trim());
				ArrayList<AmUser> utentiGruppiOk = new ArrayList();
				for(AmUser u: utentiGruppi){
					if(BaseAction.checkUserVisible(loggedUser, u.getName()))
						utentiGruppiOk.add(u);
				}
				request.setAttribute("utentiGruppi", utentiGruppiOk);
				request.setAttribute("gruppo", gruppo);	
				AmGroup am = BaseAction.gruppoByNome(gruppo);
				if(am != null){
					if(am.getPermRangeIp()!=null)
						request.setAttribute("ipFidati", am.getPermRangeIp());
					String orarioDa = am.getPermOraDa();
					String orarioA = am.getPermOraA();
					if(orarioDa != null && orarioA!=null){
						String[] s = orarioDa.split(":");
						request.setAttribute("daOra", s[0]);
						request.setAttribute("daMinuto", s[1]);
						s = orarioA.split(":");
						request.setAttribute("aOra", s[0]);
						request.setAttribute("aMinuto", s[1]);
					}
				}
			}

			ArrayList<AmComune> comuni = BaseAction.listaComuniByUser(loggedUser);
			request.setAttribute("comuni", comuni);
			
			ArrayList<AmGroup> gruppi = BaseAction.listaGruppiByUser(loggedUser);
			request.setAttribute("gruppi", gruppi);

			request.getRequestDispatcher("/jsp/newGroup.jsp").forward(request, response);
		}
		catch (AMException e) {
			request.setAttribute("msgNuovoGruppo", e.getMessage());
			request.getRequestDispatcher("/jsp/newGroup.jsp").forward(request, response);
		}
		catch (Exception e) {
			BaseAction.toErrorPage(request, response, e);
		}
	}
	
	private boolean validate(String gruppo, String nuovoGruppo, String ente, HttpServletRequest request)
	{

		if(nuovoGruppo != null && !nuovoGruppo.equals(""))
			gruppo = nuovoGruppo;
			
		boolean isGruppoOk = StringUtils.isNotEmpty(gruppo);
		boolean isEnteOk = StringUtils.isNotEmpty(ente);
		   
		if( !isGruppoOk && !isEnteOk )
			request.setAttribute("msgNuovoGruppo", "Specificare il nome del gruppo e l'ente di appartenza");

		if( isGruppoOk && !isEnteOk )
			request.setAttribute("msgNuovoGruppo", "Specificare l'ente");

		if( !isGruppoOk && isEnteOk )
			request.setAttribute("msgNuovoGruppo", "Specificare il nome del gruppo");

		return isGruppoOk && isEnteOk;

	}
	
	private boolean validatePermessiAccesso(HttpServletRequest request) {
		
		String daOra = request.getParameter("daOra");
		String aOra = request.getParameter("aOra");
		String daMinuto = request.getParameter("daMinuto");
		String aMinuto = request.getParameter("aMinuto");
		
		if (daOra!=null&&!daOra.equals("")&&aOra!=null&&!aOra.equals("")&&
				daMinuto!=null&&!daMinuto.equals("")&&aMinuto!=null&&!aMinuto.equals("")){
			SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
			try {
				Date da = sdf.parse(daOra + daMinuto);
				Date a = sdf.parse(aOra + aMinuto);
				if(a.before(da)){
					request.setAttribute("timeError", "Il secondo orario è antecedente al primo");
					return false;
				}
			} catch (ParseException e) {
				logger.error(e.getMessage(),e);
			}
		}
		
		return true;
	}
}