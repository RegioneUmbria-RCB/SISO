package it.webred.AMProfiler.servlet;

import it.webred.AMProfiler.beans.AmGroup;

import it.webred.AMProfiler.beans.AmItemRole;
import it.webred.AMProfiler.beans.AmPermission;
import it.webred.AMProfiler.beans.AmRole;
import it.webred.AMProfiler.beans.AmUser;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * Servlet implementation class for Servlet: CaricaPermessi
 * 
 */
public class CaricaPermessi extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CaricaPermessi() {
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

			request.setAttribute("applications", BaseAction.listaApplication());


			String app = BaseAction.getPerameter(request, "application");
			String item = BaseAction.getPerameter(request, "item");
			String ente = BaseAction.getPerameter(request, "ente");

			if (app != null) {
				request.setAttribute("items", BaseAction.listaItemForApp(app));
				request.setAttribute("application", app);
				request.setAttribute("ente", ente);
				request.setAttribute("appType", BaseAction.getPerameter(request, "appType"));
			}

			if (item != null) {

				ArrayList<AmGroup> alGroups = BaseAction.listaGruppiPerEnteApplication(ente, app);
				
				//do Utente-Ruoli
				ArrayList<AmItemRole> alRoles = doUtenteRuoli(request, app, item, ente, alGroups);

				//do Ruolo-Utente
				doRuoloUtenti(request, app, item, alRoles , ente);
				
				//Gruppi - Ruoli
				doGruppoRuoli(request, app, item, alGroups );
				
				//Permessi - Ruoli
				doPermessiRuoli(request, app, item);
				
				//Ruoli
				doRuoli(request, app, item);
			}

			request.getRequestDispatcher("/jsp/permessi.jsp").forward(request, response);
		}
		catch (Exception e) {
			BaseAction.toErrorPage(request, response, e);
		}
	}
	
	protected ArrayList<AmItemRole> doUtenteRuoli(HttpServletRequest request, String app, String item, String ente, ArrayList<AmGroup> srcGroups) throws Exception
	{
		//Carica Gruppi
		request.setAttribute("gruppi4utente", srcGroups);
		//seleziona gruppo
		String gruppo4utenteScelto = request.getParameter("gruppi4utente");
		request.setAttribute("gruppo4utenteScelto", gruppo4utenteScelto);

		//Carica Utenti
		ArrayList<AmUser> utentiXgruppo = BaseAction.listaUsersPerGroup(gruppo4utenteScelto);
		request.setAttribute("utentiXgruppo", utentiXgruppo);
		//seleziona utente
		String utenteScelto = request.getParameter("utentiXgruppo");
		if( StringUtils.isEmpty(utenteScelto) ){
			if( utentiXgruppo.size() > 0 )
				utenteScelto = utentiXgruppo.get(0).getName();
		}
		else
		{
			boolean bFound = false;
			for (AmUser amUser : utentiXgruppo) {
				bFound = amUser.getName().equals(utenteScelto);
				if( bFound )break;
			}
			if( !bFound && utentiXgruppo.size() > 0)
				utenteScelto = utentiXgruppo.get(0).getName();
		}
		request.setAttribute("utenteScelto", utenteScelto);
		
		request.setAttribute("salvaRuoliUtenteAbilitato", StringUtils.isNotEmpty(utenteScelto) );
				
		//Carica Ruoli
		ArrayList<AmItemRole> srcRoles = BaseAction.listaItemRolePerItemUserGroup(app, item, ente, gruppo4utenteScelto, utenteScelto);
		
		ArrayList<AmItemRole> trgRoles = new ArrayList<AmItemRole>();
		for (AmItemRole src : srcRoles) {

			boolean bAdd = true;
			for (AmItemRole trg : trgRoles) {
				/* verificato Filippo Mazzini 31/05/2016:
				 * il controllo seguente (String contains String) impedisce ad esempio che venga inserito il ruolo "A" se è stato prima inserito il ruolo "Avanzato";
				 * al momento non è stato effettuato l'intervento lato codice, sono stati semplicemente rinominati i ruoli che venivano esclusi */
				if( trg.getAmRoleS().contains(src.getAmRoleS()) )
				{
					bAdd = false;
					if( src.isGrouped() )
						trgRoles.set(trgRoles.indexOf(trg), src );
					break;
				}
			}

			if( bAdd )
				trgRoles.add(src);
		}
		request.setAttribute("ruoliXutente", trgRoles);

		return srcRoles;
//		//get ruoli selezionati
//		String[] rules = request.getParameterValues("ruoliXutente");
//		if (rules != null && rules.length == 1) {
//			request.setAttribute("ruoloScelto", BaseAction.decodeRule(rules[0]));
//		}

	}

	protected void doRuoloUtenti(HttpServletRequest request, String app, String item, ArrayList<AmItemRole> srcRoles , String ente) throws Exception
	{
		ArrayList<AmItemRole> trgRoles = new ArrayList<AmItemRole>();
		for (AmItemRole src : srcRoles) {
			if( !src.isGrouped() )
			{
				boolean bAdd = true;
				for (AmItemRole trg : trgRoles) {
					if( trg.getId() == src.getId())
					{
						bAdd = false;
						break;
					}
				}
				
				if( bAdd )
					trgRoles.add(src);
			}
		}
		request.setAttribute("ruoli4utente", trgRoles);

		String ruoli4utente = request.getParameter("ruoli4utente");
		boolean isIn = false;
		if (ruoli4utente != null && !ruoli4utente.equals("")) {
			for (AmItemRole rule : trgRoles) {
				if (ruoli4utente.trim().equals("" + rule.getId())) {
					isIn = true;
					break;
				}
			}
		}

		if (isIn) {
			request.setAttribute("ruoli4utenteScelto", BaseAction.RoleId2Name(ruoli4utente));
			ArrayList<AmUser> utentiXruolo = BaseAction.listaUsersForRule(app, item, ruoli4utente, ente);
			request.setAttribute("utentiXruolo", utentiXruolo);
			request.setAttribute("utentiXruoloSize", utentiXruolo == null ? 0 : utentiXruolo.size());
		}
		else {
			request.setAttribute("ruoli4utenteScelto", null);
			request.setAttribute("utentiXruolo", null);
			request.setAttribute("utentiXruoloSize", 0);
		}
	}
	
	protected void doGruppoRuoli(HttpServletRequest request, String app, String item, ArrayList<AmGroup> gruppi ) throws Exception
	{
		request.setAttribute("gruppi", gruppi);

		String gruppoScelto = request.getParameter("gruppi");
		request.setAttribute("gruppoScelto", gruppoScelto);

		ArrayList<AmItemRole> ruoliXgruppo = BaseAction.listaItemRolePerItemGroup(app, item, gruppoScelto);
		request.setAttribute("ruoliXgruppo", ruoliXgruppo);
	
		request.setAttribute("salvaRuoliGruppoAbilitato", StringUtils.isNotEmpty(gruppoScelto));
	}

	protected void doPermessiRuoli(HttpServletRequest request, String app, String item ) throws Exception
	{
		ArrayList<AmPermission> permessi = BaseAction.listaPermissionForItem(item);
		request.setAttribute("permessi", permessi);
		request.setAttribute("permessiSize", permessi.size());
		request.setAttribute("checks", BaseAction.getPermessiRuoliMap(app, item));
		request.setAttribute("item", item);
	}
	
	protected void doRuoli(HttpServletRequest request, String app, String item ) throws Exception
	{
		ArrayList<AmRole> ruoli = BaseAction.getlistaRole(app, item);
		request.setAttribute("ruoli", ruoli);
	}
}