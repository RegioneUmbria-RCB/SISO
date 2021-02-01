package it.webred.AMProfiler.servlet;

import it.webred.AMProfiler.exception.AMException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: NuovoPermesso
 * 
 */
public class NuovoPermesso extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public NuovoPermesso() {
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
			BaseAction.NuovoPermesso(request);
			request.getRequestDispatcher("CaricaPermessi").forward(request, response);
		}
		catch (AMException e) {
			request.setAttribute("noNuovoPermesso", "true");
			request.setAttribute("msgNuovoPermesso", e.getMessage());
			request.getRequestDispatcher("CaricaPermessi").forward(request, response);
		}
		catch (Exception e) {
			BaseAction.toErrorPage(request, response, e);
		}

	}
}