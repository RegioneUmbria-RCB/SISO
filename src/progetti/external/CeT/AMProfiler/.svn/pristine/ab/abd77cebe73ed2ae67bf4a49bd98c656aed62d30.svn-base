package it.webred.AMProfiler.servlet;

import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.anagrafica.dto.AnagraficaDTO;
import it.webred.amprofiler.ejb.anagrafica.dto.AnagraficaSearchCriteria;
import it.webred.amprofiler.model.AmAnagrafica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: SalvaUtente
 * 
 */
public class CaricaUtenti extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/AnagraficaServiceBean")
	protected AnagraficaService anagraficaService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int numGiorniVal = 90; // default
	public static int numGiorniAnteScad = 5; // default
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CaricaUtenti() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		numGiorniVal = Integer.parseInt(config.getInitParameter("numGiorniVal"));
		numGiorniAnteScad = Integer.parseInt(config.getInitParameter("numGiorniAnteScad"));
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

		Connection con = null;
		Statement statment = null;

		AnagraficaSearchCriteria ricerca = new AnagraficaSearchCriteria();
		String loggedUser = request.getUserPrincipal().getName();
		
		try {			
			List<AmAnagrafica> listaAnagrafica = new ArrayList<AmAnagrafica>();
			
			String mode = request.getParameter("mode");
			if ("search".equals(mode)) {
				
				String user = request.getParameter("ricUser");
				String cognome = request.getParameter("ricCognome");
				String nome = request.getParameter("ricNome");
				String cf = request.getParameter("ricCF");
				String stato = request.getParameter("ricStato");
				ricerca.setUserName(user);
				ricerca.setCognome(cognome);
				ricerca.setNome(nome);
				ricerca.setCodiceFiscale(cf);
				ricerca.setStato(stato);
				
				//la lista viene visualizzata solo in seguito a ricerca, e non alla prima apertura della pagina
				listaAnagrafica = anagraficaService.getListaAnagrafica(ricerca);
			}
			
			
			List<AnagraficaDTO> listaAnagraficaDTO = new ArrayList<AnagraficaDTO>();
			
			for(AmAnagrafica an: listaAnagrafica){
				
				if(BaseAction.checkUserVisible(loggedUser, an.getAmUser().getName())){
				
					AnagraficaDTO dto = new AnagraficaDTO();
					dto.setAnagrafica(an);
					
					Date dtUpdPwd = an.getAmUser().getDtUpdPwd();
					Calendar calUpdPwd = Calendar.getInstance();
					calUpdPwd.setTime(dtUpdPwd);
					calUpdPwd.add(Calendar.DAY_OF_YEAR, numGiorniVal);
					Calendar calAnteUpdPwd = Calendar.getInstance();
					calAnteUpdPwd.setTime(dtUpdPwd);
					calAnteUpdPwd.add(Calendar.DAY_OF_YEAR, numGiorniVal - numGiorniAnteScad);
					Calendar calOggi = Calendar.getInstance();
					calOggi.set(Calendar.HOUR_OF_DAY, 0);
					calOggi.set(Calendar.MINUTE, 0);
					calOggi.set(Calendar.SECOND, 0);
					calOggi.set(Calendar.MILLISECOND, 0);
					
					dto.setStatoPwd("Password valida: scadenza " + sdf.format(calUpdPwd.getTime()));
					if (calOggi.getTime().getTime() > calAnteUpdPwd.getTime().getTime())
						dto.setStatoPwd("Password in scadenza (" + sdf.format(calUpdPwd.getTime()) + ")");
					if (calOggi.getTime().getTime() > calUpdPwd.getTime().getTime())
						dto.setStatoPwd("Password scaduta");
					
					if(ricerca.getStato() != null && ricerca.getStato().contains("PWD")){
						if(ricerca.getStato().equals("PWD VALID") && dto.getStatoPwd().contains("Password valida"))
							listaAnagraficaDTO.add(dto);
						if(ricerca.getStato().equals("PWD ABOUT TO EXPIRE") && dto.getStatoPwd().contains("Password in scadenza"))
							listaAnagraficaDTO.add(dto);
						if(ricerca.getStato().equals("PWD EXPIRED") && dto.getStatoPwd().contains("Password scaduta"))
							listaAnagraficaDTO.add(dto);
					}else
						listaAnagraficaDTO.add(dto);
				}
			}
			
			request.getSession().setAttribute("userList", listaAnagraficaDTO);
			
			request.getRequestDispatcher("/jsp/newUser.jsp").forward(request, response);
		}
		catch (Exception e) {
			if (e.toString().indexOf("ORA-00001") == -1) {
				BaseAction.toErrorPage(request, response, e);
			}
			else {
				request.setAttribute("giaPresente", "true");
				request.getRequestDispatcher("/jsp/newUser.jsp").forward(request, response);
			}
		}
		finally {
			BaseAction.chiudiConnessione(con, statment);
		}

	}

}