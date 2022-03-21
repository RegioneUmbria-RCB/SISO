package it.webred.ct.support.web.test;

import it.webred.ct.support.model.CodiceEntrata;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.CodiceEntrataService;
import test.TestParam;
/**
 * Classe di test per verifcare il funzionamento del datasource router
 * 
 * 
 * Francesco Azzola - 2010
 * 
 * */

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}
	
	
	private void handleRequest(HttpServletRequest request, HttpServletResponse response) {
		try {
			InitialContext ctx = new InitialContext();
			Object o = ctx.lookup("CeT_Support/CodiceEntrataServiceBean/remote");
			CodiceEntrataService cs = (CodiceEntrataService) o;
			TestParam param = new TestParam();
			param.setEnteId(request.getParameter("id"));
			
			List<CodiceEntrata> result = cs.getListaCodici(param);
			PrintWriter pw = response.getWriter();
			pw.print("Result ["+result+"]");
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
