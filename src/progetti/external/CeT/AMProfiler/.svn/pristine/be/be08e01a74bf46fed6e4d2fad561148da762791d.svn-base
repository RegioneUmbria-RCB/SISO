package it.webred.AMProfiler.servlet;

import it.webred.AMProfiler.beans.AmGroup;
import it.webred.AMProfiler.beans.AmItem;
import it.webred.AMProfiler.beans.AmItemRole;
import it.webred.AMProfiler.beans.AmPermission;
import it.webred.AMProfiler.beans.AmUser;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: CaricaServizi
 * 
 */
public class CaricaServizi extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CaricaServizi() {
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
			request.setAttribute("utentiXgruppo", BaseAction.listaUsers());
			request.setAttribute("applications", BaseAction.listaApplication());

			String app = BaseAction.getPerameter(request, "application");

			if (app != null) {
				request.setAttribute("application", app);
				request.setAttribute("appType", BaseAction.getPerameter(request, "appType"));
			}

			ArrayList<AmItem> servizi = BaseAction.listaItemForApp(app);
			request.setAttribute("servizi", servizi);
			request.setAttribute("size", servizi.size());
			request.setAttribute("checks", BaseAction.getServiziRuoliMap(app));
			request.getRequestDispatcher("/jsp/servizi.jsp").forward(request, response);
		}
		catch (Exception e) {
			BaseAction.toErrorPage(request, response, e);
		}
	}
	
	protected void doPermessiRuoli(HttpServletRequest request, String app, String item, String user ) throws Exception
	{
	}
}