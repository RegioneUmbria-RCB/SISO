package it.webred.AMProfiler.servlet;

import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.webred.AMProfiler.beans.AmComune;
import it.webred.AMProfiler.util.TokenUtils;
import it.webred.amprofiler.model.AmTracciaAccessi;
import it.webred.amprofiler.model.AmUser;

/**
 * Servlet implementation class for Servlet: SalvaUtente
 * 
 */
public class SceltaEnte extends AccessoBase {
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public SceltaEnte() {
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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	private void updateTracciaToken(HttpServletRequest request, String userName, String ragioneAccesso, String pratica) {
		TokenUtils tu = (TokenUtils)request.getSession().getAttribute("token");
		if(tu!= null && tu.getFirstToken()!=null)
			aggiornaTracciaAccessi(userName,  request.getParameter("ragioneAccesso"), request.getParameter("pratica"), tu.getFirstEnte(), request.getSession().getId(), tu.getFirstToken(), null );
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

			String userName = request.getParameter("userName");
			String enteScelto = BaseAction.getParameter(request, "enteScelto");
			if (enteScelto == null || "".equals(enteScelto))
				enteScelto = (String)request.getSession().getAttribute("enteScelto");
			String pathApp = request.getParameter("pathApp");
			String myparam = request.getParameter("myparam");
			String rdoMotivoAccesso = request.getParameter("rdoMotivoAccesso");

			String sessionId = request.getSession().getId();
			Principal user = request.getUserPrincipal();

			//Controlla presenza dati soggetto, e richiedi se non presenti
			logger.debug("SceltaEnte-userName:"+userName);
			logger.debug("SceltaEnte-user:"+user.getName());
			logger.debug("SceltaEnte-pathApp:"+pathApp);

			String motivoDiAccesso = "STANDARD";
			if(user.getName()!=null && !user.getName().equals("")){
				boolean anagraficaPresente = this.verificaDatiSoggettoObbligatori(user.getName());
				logger.debug("SceltaEnte-anagraficaPresente: " + anagraficaPresente );
				if(!anagraficaPresente && pathApp!=null && !pathApp.equals("")){
					request.getRequestDispatcher("/SalvaUtente?pathApp="+pathApp+"&myParam="+myparam+"&mode=vis&soloDatiUfficio=true&userName="+user.getName()).forward(request,response);
					return;
				}
				logger.debug("SceltaEnte-anagraficaPresente: "+anagraficaPresente + " COG: " + anagrafica.getCognome() + " NOM: " + anagrafica.getNome() );
				if (anagrafica != null && anagrafica.getMotivoAccesso() != null && !anagrafica.getMotivoAccesso().trim().equalsIgnoreCase(""))
					motivoDiAccesso = anagrafica.getMotivoAccesso().trim();
			}
			
			String disableCause = getDisableCause(user.getName());
			if (disableCause != null && !disableCause.trim().equals("")) {
				//p1.1
				request.setAttribute("disableCause", disableCause);
				request.getRequestDispatcher("/jsp/disabledUser.jsp").forward(request, response);
				return;
			} 
			
			String doOnlyAMInsPratica = request.getParameter("doOnlyAMInsPratica");
			String doneInsPratica = request.getParameter("doneInsPratica");
			if (doOnlyAMInsPratica != null && new Boolean(doOnlyAMInsPratica).booleanValue()) {
					/*
					 * recuperare la modalita di accesso selezionata in precedenza e:
					 * se STANDARD fare nulla e selezionare il primo radio
					 * se LAST recuperare da AM_TRACCIA_ACCESSI la ragione e la pratica dell'ultimo accesso e proporlo nella schermata di inserimento pratica
					 * se SKIP recuperare da AM_TRACCIA_ACCESSI la ragione e la pratica dell'ultimo accesso, effettuare insert in AM_TRACCIA_ACCESSI con le medesime informazioni saltando la pagina di inserimento pratica
					 */

					if (motivoDiAccesso != null && !motivoDiAccesso.trim().equalsIgnoreCase("") && motivoDiAccesso.trim().equalsIgnoreCase("STANDARD")){

						request.setAttribute("rdoMotivoAccesso", motivoDiAccesso);		
						request.getRequestDispatcher("/jsp/sceltaEnte.jsp").forward(request, response);
					}else if (motivoDiAccesso != null && !motivoDiAccesso.trim().equalsIgnoreCase("") && (motivoDiAccesso.trim().equalsIgnoreCase("LAST") || motivoDiAccesso.trim().equalsIgnoreCase("SKIP")) ){
						String ragioneAccesso = "";
						String praticaAccesso = "";												
						/*
						 * recupero da AM_TRACCIA_ACCESSI la ragione e la pratica dell'ultimo accesso 
						 */
						List<AmTracciaAccessi> lstTracciaAccessi = loginService.findTracciaAccessiByLastAccessUser(user.getName());
						if (lstTracciaAccessi != null && lstTracciaAccessi.size()>0){
							AmTracciaAccessi amta = (AmTracciaAccessi)lstTracciaAccessi.get(0);
							ragioneAccesso = amta.getRagioneAccesso();
							praticaAccesso = amta.getPratica();
						}
						if (motivoDiAccesso != null && !motivoDiAccesso.trim().equalsIgnoreCase("") && motivoDiAccesso.trim().equalsIgnoreCase("LAST")){
							
							request.setAttribute("ragioneAccesso", ragioneAccesso);
							request.setAttribute("praticaAccesso", praticaAccesso);
							request.setAttribute("rdoMotivoAccesso", motivoDiAccesso);		
							request.getRequestDispatcher("/jsp/sceltaEnte.jsp").forward(request, response);
						}else if (motivoDiAccesso != null && !motivoDiAccesso.trim().equalsIgnoreCase("") && motivoDiAccesso.trim().equalsIgnoreCase("SKIP") && myparam != null && myparam.equalsIgnoreCase("change")){
							/*
							 * Qui si arriva dal cambio pratica in AMProfiler in caso di utente con modalita di accesso corrente uguale a SKIP
							 */
							request.setAttribute("ragioneAccesso", ragioneAccesso);
							request.setAttribute("praticaAccesso", praticaAccesso);
							request.setAttribute("rdoMotivoAccesso", motivoDiAccesso);		
							request.getRequestDispatcher("/jsp/sceltaEnte.jsp").forward(request, response);
						}else if (motivoDiAccesso != null && !motivoDiAccesso.trim().equalsIgnoreCase("") && motivoDiAccesso.trim().equalsIgnoreCase("SKIP") && (myparam == null || !myparam.equalsIgnoreCase("change")) ){
							/*
							 * l'utente ha già inserito il motivo e la pratica durante l'accesso precedente in cui ha selezionato la modalità SKIP, quindi  
							 * non vedrà piu l'avviso 
							 */
							salvaTracciaAccessi(request,user.getName(), ((HttpServletRequest) request).getRequestURL().toString().replace("SceltaEnte", ""), ragioneAccesso, praticaAccesso, enteScelto, sessionId,null, null);
							request.getRequestDispatcher("/CaricaMenu?doneInsPratica=true").forward(request, response);
							//SOCLAV salvataggio ente default
							updateTracciaToken(request, user.getName(), request.getParameter("ragioneAccesso"), request.getParameter("pratica"));

						}else{
							request.setAttribute("rdoMotivoAccesso", motivoDiAccesso);		
							request.getRequestDispatcher("/jsp/sceltaEnte.jsp").forward(request, response);
						}
					}
				return;
			} else if (doneInsPratica != null && new Boolean(doneInsPratica).booleanValue()) {
				//p2
				/*
				 * 
				 * qui si passa in caso di modalita di accesso STANDARD e LAST per cui in entrambe i casi si recuperano le informazioni (=la ragione e la pratica) dalla request;
				 * se è stata selezionata una modalita di accesso diversa dalla corrente si aggiorna il record in am_anagrafica e sarà valida dall'accesso successivo
				 */
				if (rdoMotivoAccesso != null && motivoDiAccesso != null && rdoMotivoAccesso.trim().equalsIgnoreCase(motivoDiAccesso)){
					/*
					 * la scelta corrente è identica a quella precedente pertanto non c'è bisogno di aggiornare il record in AM_ANAGRAFICA
					 */
				}else{
					/*
					 * la scelta corrente è diversa da quella precedente pertanto dobbiamo aggiornare il record in AM_ANAGRAFICA
					 */
					anagrafica.setMotivoAccesso(rdoMotivoAccesso);
					anagraficaService.updateAnagrafica(anagrafica);
				}
				//SOCLAV salvataggio primo accesso
				salvaTracciaAccessi(request,user.getName(), ((HttpServletRequest) request).getRequestURL().toString().replace("SceltaEnte", ""), request.getParameter("ragioneAccesso"), request.getParameter("pratica"), enteScelto, sessionId,null, null);
				request.getRequestDispatcher("/CaricaMenu?doneInsPratica=true").forward(request, response);
				//SOCLAV salvataggio ente default
				//SOCLAV salvataggio ente default
				updateTracciaToken(request, user.getName(), request.getParameter("ragioneAccesso"), request.getParameter("pratica"));
					
				return;
			}else if (doOnlyAMInsPratica != null && !new Boolean(doOnlyAMInsPratica).booleanValue()) {
				salvaTracciaAccessi(request,user.getName(), ((HttpServletRequest) request).getRequestURL().toString().replace("SceltaEnte", ""), "SKIPPED BY AM PARAM", "SKIPPED BY AM PARAM", enteScelto, sessionId, null, null);
				request.getRequestDispatcher("/CaricaMenu?doneInsPratica=true").forward(request, response);
				//SOCLAV salvataggio ente default
				updateTracciaToken(request, user.getName(), request.getParameter("ragioneAccesso"), request.getParameter("pratica"));

				return;
			}
			 
			ArrayList<AmComune> entiUtente = BaseAction.listaComuniByUser(userName);
			request.setAttribute("entiUtente", entiUtente);
			request.setAttribute("pathApp", pathApp);
			request.setAttribute("myparam", myparam);
			request.setAttribute("userName", userName);
			
			
			request.getSession().setAttribute("enteScelto", enteScelto);

			if (enteScelto == null || "".equals(enteScelto))
				request.getRequestDispatcher("/jsp/sceltaEnte.jsp").forward(request, response);
			else {
				 
				/**	soclav  lo recupero e non lo rigenero **/
				TokenUtils tu = (TokenUtils)request.getSession().getAttribute("token");
				String tokenStr = "";
				if(tu!= null) {
					
					tokenStr = tu.getToken(enteScelto);
				}
				
				/************/
				salvaTracciaAccessi(request,userName, pathApp,
						request.getParameter("ragioneAccesso"),
						request.getParameter("pratica"), enteScelto, sessionId,tokenStr , null);
				if (myparam != null && !myparam.equals("")) {
					myparam = myparam.replaceAll("\\|", "\\&");
					myparam = myparam.replaceAll("_", "=");
					((HttpServletResponse) response).sendRedirect(pathApp+ "?es=" + encode(enteScelto) + "&" + myparam);
				} else{
					((HttpServletResponse) response).sendRedirect(pathApp + "?es=" + encode(enteScelto));
				}
			}

		} catch (Exception e) {
			BaseAction.toErrorPage(request, response, e);
		}

	}//-------------------------------------------------------------------------


	
	private String getDisableCause(String username) {
		try {
			AmUser user = userService.getUserByName(username);
			if (user == null) {
				user = userService.getUserByName(username.toUpperCase());
			}
			return user.getDisableCause();
		} catch (Exception e) {
			logger.error("ERRORE NELLA LETTURA DEI DATI DELL'UTENTE", e);
			return "ERRORE NELLA LETTURA DEI DATI DELL'UTENTE: " + e.getMessage();
		}
	}
	
	@Override
	protected void salvaTracciaAccessi(HttpServletRequest request,String userName, String pathApp, String ragioneAccesso, String pratica, String ente, String sessionId,String prkk, String pubk) {
		
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = BaseAction.apriConnessione();
			con.setAutoCommit(false);
			
			java.sql.Date sqlNow = new java.sql.Date(new Date().getTime());
			
			if (ente != null) {
				super.salvaTracciaAccessi(request, userName, pathApp, ragioneAccesso, pratica, ente, sessionId, prkk, pubk);
			} else {
				if (ragioneAccesso == null || ragioneAccesso.equalsIgnoreCase(""))
					ragioneAccesso = "NON INDICATA";
				if (pratica == null || pratica.equalsIgnoreCase(""))
					pratica = "NON INDICATA";
				
				// sto modificando l'ente nella combo, non sto accedendo
				if (pathApp == null)
					return;
				
				if (pathApp.endsWith("/") || pathApp.endsWith("\\"))
					pathApp = pathApp.substring(0, pathApp.length() - 1);
				
				String fkAmApp = getApplicationByPathApp(pathApp,con);
				if(fkAmApp == null){ //Estraggo dal link la radice
					String rootApp = this.getRootApp(pathApp, request);
					fkAmApp = getApplicationByPathApp(rootApp,con);
				}
				
				//recupero max(id) per insert in AM_TRACCIA_ACCESSI
	 			String sql = "SELECT NVL(MAX(ID), 0) + 1 AS NEWID FROM AM_TRACCIA_ACCESSI";
				st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery();
				int newId = 0;
				while (rs.next()) {
					newId = rs.getInt("NEWID");
				}
				rs.close();
				st.close();
				
				st = con.prepareStatement("INSERT INTO AM_TRACCIA_ACCESSI VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				
				int paramIndex = 0;
				st.setString(++paramIndex, userName);
				st.setString(++paramIndex, ragioneAccesso);
				st.setString(++paramIndex, fkAmApp);
				st.setString(++paramIndex, pratica);
				st.setDate(++paramIndex, sqlNow);
				st.setInt(++paramIndex, newId);
				st.setNull(++paramIndex, Types.VARCHAR);
				st.setNull(++paramIndex, Types.VARCHAR);
				
				//st.setNull(++paramIndex, Types.VARCHAR);
				st.setString(++paramIndex, prkk);
				st.setNull(++paramIndex, Types.VARCHAR);
				st.setString(++paramIndex, sessionId);
				st.executeUpdate();	
				st.close();			
			}
			
			st = con.prepareStatement("UPDATE AM_USER SET LAST_ACCESS = ? WHERE NAME = ?");
			int paramIndex = 0;
			st.setDate(++paramIndex, sqlNow);
			st.setString(++paramIndex, userName);
			st.executeUpdate();	
			st.close();
			
			con.commit();	
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			try {
				BaseAction.rollback(con);
			} catch (Exception e1) {
			}
		} finally {
			BaseAction.chiudiConnessione(con, st);
		}
	}
	
}