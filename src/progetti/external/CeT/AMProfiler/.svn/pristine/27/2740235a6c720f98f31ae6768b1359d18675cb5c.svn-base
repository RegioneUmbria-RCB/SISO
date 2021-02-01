package it.webred.AMProfiler.servlet;

import it.webred.AMProfiler.exception.AMException;
import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.anagrafica.dto.AnagraficaDTO;
import it.webred.amprofiler.ejb.anagrafica.dto.AnagraficaSearchCriteria;
import it.webred.amprofiler.model.AmAnagrafica;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: CancellaUtente
 * 
 */
public class CancellaUtente extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/AnagraficaServiceBean")
	protected AnagraficaService anagraficaService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CancellaUtente() {
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
			if (validate(request)) {
				BaseAction.CancellaUtente(request);
			}

			List<AmAnagrafica> listaAnagrafica = anagraficaService.getListaAnagrafica(new AnagraficaSearchCriteria());
			List<AnagraficaDTO> listaAnagraficaDTO = new ArrayList<AnagraficaDTO>();
			
			for(AmAnagrafica an: listaAnagrafica){
				AnagraficaDTO dto = new AnagraficaDTO();
				dto.setAnagrafica(an);
				
				Date dtUpdPwd = an.getAmUser().getDtUpdPwd();
				Calendar calUpdPwd = Calendar.getInstance();
				calUpdPwd.setTime(dtUpdPwd);
				calUpdPwd.add(Calendar.DAY_OF_YEAR, CaricaUtenti.numGiorniVal);
				Calendar calAnteUpdPwd = Calendar.getInstance();
				calAnteUpdPwd.setTime(dtUpdPwd);
				calAnteUpdPwd.add(Calendar.DAY_OF_YEAR, CaricaUtenti.numGiorniVal - CaricaUtenti.numGiorniAnteScad);
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
				
				listaAnagraficaDTO.add(dto);
			}
			
			request.getSession().setAttribute("userList", listaAnagraficaDTO);
			
			request.getRequestDispatcher("/jsp/newUser.jsp").forward(request, response);
		}
		catch (AMException e) {
			request.setAttribute("noCancellazioneUtente", "true");
			request.setAttribute("msgCancellazioneUtente", e.getMessage());
		}
		catch (Exception e) {
			request.setAttribute("noCancellazioneUtente", "true");
			request.setAttribute("msgCancellazioneUtente", "Errore non previsto nella cancellazione utente");
		}

	}

	private boolean validate(HttpServletRequest request) {
		if (request.getParameter("userName") == null || request.getParameter("userName").trim().equals(""))
			return false;
		return true;
	}
}