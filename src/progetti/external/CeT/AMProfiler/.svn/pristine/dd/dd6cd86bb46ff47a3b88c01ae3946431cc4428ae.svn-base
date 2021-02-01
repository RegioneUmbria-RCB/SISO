package it.webred.AMProfiler.servlet.test;


import it.webred.ct.data.access.basic.rette.RetteDataIn;
import it.webred.ct.data.access.basic.rette.RetteService;
import it.webred.ct.data.model.rette.SitRttBollette;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class for Servlet: SalvaUtente
 * 
 */
public class RetteTest extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	@EJB(mappedName = "CT_Service/RetteServiceBean/remote")
	protected RetteService retteService;

	protected static Logger logger = Logger.getLogger("am_log");

	
	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public RetteTest() {
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

		try {
			RetteDataIn di = new RetteDataIn();
			
			if ("RicercaRetteSoggetto".equals(request.getParameter("submit"))) {
				String codFisc = request.getParameter("codFisc");
				di.setEnteId(request.getParameter("ente"));
				di.setObj(codFisc);
				di.setSessionId(request.getSession().getId());
				di.setUserId(request.getUserPrincipal().getName());
				List<SitRttBollette> lista = retteService.getListaBollettePagateByCodFis(di);
			}

			if ("RicercaRettaPerPagare".equals(request.getParameter("submit"))) {
				String codBolletta = request.getParameter("codBolletta");
				di.setEnteId(request.getParameter("ente"));
				di.setObj(null);
				di.setObj2(codBolletta);
				di.setSessionId(request.getSession().getId());
				di.setUserId(request.getUserPrincipal().getName());
				List<SitRttBollette> lista = retteService.getListaBolletteNonPagateByCodFis(di);
			}
			

				
			request.getRequestDispatcher("/jsp/testEJB/rette.jsp").forward(request, response);
		}
		catch (Exception e) {
			logger.error("",e);
		}
		finally {
		}

	}

	
}