package it.webred.AMProfiler.servlet;

import it.webred.AMProfiler.beans.AmApplication;
import it.webred.AMProfiler.beans.VersioneBean;
import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUserUfficio;
import it.webred.permessi.AuthContext;
import it.webred.permessi.GestionePermessi;

import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

/**
 * Servlet implementation class for Servlet: CaricaMenu
 * 
 */
public class CaricaMenu extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	
	protected static Logger logger = Logger.getLogger("am.log");
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/LoginBean")
	protected LoginBeanService loginService;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/UserServiceBean")
	protected UserService userService;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/AnagraficaServiceBean")
	protected AnagraficaService anagraficaService;
	
	private static final long serialVersionUID = 1L;
	
	
	private Context ctx = null;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CaricaMenu() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			
		try {
			String usrStr;
			Principal user = request.getUserPrincipal();
			HttpSession session = request.getSession(false);
			String sessionId = session.getId();
			session.setAttribute("user", user);
			usrStr=user.toString();
			Connection con = null;
			Statement statment = null;
			
			try {
				
				//Controlla presenza dati soggetto, e richiedi se non presenti
				boolean anagraficaPresente = this.verificaDatiSoggettoObbligatori(user);
				logger.debug("CaricaMenu-anagraficaPresente:"+anagraficaPresente);
				if(!anagraficaPresente){
					request.getRequestDispatcher("/SalvaUtente?mode=vis&soloDatiUfficio=true&userName="+user.getName()).forward(request,response);
					return;
				}
				
				if (request.getParameter("doneInsPratica") == null || !new Boolean(request.getParameter("doneInsPratica")).booleanValue()) {
					request.getRequestDispatcher("/SceltaEnte?doOnlyAMInsPratica=true").forward(request,response);
					return;
				}
				
	
				
				con = BaseAction.apriConnessione();
				ArrayList listaEntiGestionePermessi = GestionePermessi.getEntiGestionepermessi(new AuthContext(user, con));

				
				statment = con.createStatement();
		  		
		  		ResultSet rsc = statment
						.executeQuery("select distinct belfiore, descrizione from am_comune order by descrizione");
				Hashtable<String, String> decodeComuni = new Hashtable<String, String>();
				while (rsc.next()) {
					decodeComuni.put(rsc.getString("belfiore"), rsc
							.getString("descrizione"));
				}
				rsc.close();
				
				String sqlApplication = "select distinct APP_TYPE from am_application where APP_TYPE !='AMProfiler' order by APP_TYPE";
				ResultSet rs = statment.executeQuery(sqlApplication);
				
				ArrayList<String> intestazioneTab = new ArrayList<String>();
				intestazioneTab.add("Ente");
				while (rs.next()) {
					intestazioneTab.add(rs.getString("APP_TYPE"));
				}
				rs.close();
				// controllo quante applicazioni ho trovato
				if (intestazioneTab.size() != 1) {
					request.setAttribute("intestazioneTab", intestazioneTab);

					LinkedHashMap<String, LinkedHashMap> mappa = new LinkedHashMap();
					
					/*String sql = "SELECT DISTINCT a.*, c.FK_AM_COMUNE, i.URL " +
                    "FROM am_application a, am_instance i, am_instance_comune c " +
                    "WHERE i.fk_am_application = a.NAME " +
                    "AND c.FK_AM_INSTANCE  = i.NAME " +
                    "AND a.NAME != 'AMProfiler' " +
                    "order by c.FK_AM_COMUNE, a.APP_CATEGORY, a.NAME";*/
					
					/* 14/02/2011
					 * Query (precedente) modificata per far comparire in home page dell'AMProfiler solo la lista degli enti 
					 * cui l'utente è abilitato ad accedere (direttamente o come gruppo).
					*/
					
					String sql = "SELECT DISTINCT * FROM( " +
							"SELECT DISTINCT a.*, c.FK_AM_COMUNE, i.URL " + 
							    "FROM am_application a, am_instance i, am_instance_comune c, am_user_air air " + 
							    "WHERE i.fk_am_application = a.NAME  " +
							    "AND c.FK_AM_INSTANCE  = i.NAME  " +
							    "AND a.NAME != 'AMProfiler'  " +
							    "AND AIR.FK_AM_USER = '" + user.getName() +"' "+
							    "AND (AIR.FK_AM_COMUNE = C.FK_AM_COMUNE OR AIR.FK_AM_COMUNE IS NULL)  " +
							"UNION " +
							"SELECT DISTINCT a.*, c.FK_AM_COMUNE, i.URL  " +
							    "FROM am_application a, am_instance i, am_instance_comune c, am_user_group ugroup, am_group  gruppo " +
							    "WHERE i.fk_am_application = a.NAME  " +
							    "AND c.FK_AM_INSTANCE  = i.NAME  " +
							    "AND a.NAME != 'AMProfiler'  " +
							    "AND ugroup.FK_AM_USER = '" + user.getName() +"' "+
							    "AND (GRUPPO.FK_AM_COMUNE = C.FK_AM_COMUNE OR GRUPPO.FK_AM_COMUNE IS NULL) " +
							    "AND UGROUP.FK_AM_GROUP = GRUPPO.NAME " +
							") order by FK_AM_COMUNE, APP_CATEGORY, NAME ";
					
					rs = statment.executeQuery(sql);
					
					HashMap<String, Boolean> permessoDiAccessoAdComune = new HashMap<String, Boolean>();

					ArrayList<String> listaPermessi = GestionePermessi.getPermissionsList(new AuthContext(user, con), true);
					
					String comunePrev = "";
					ArrayList<String> listaApplUtente = null;
					while (rs.next()) {
						String comune = rs.getString("FK_AM_COMUNE");
						if (mappa.get(comune) == null) {
							permessoDiAccessoAdComune.put(comune, false);
							LinkedHashMap mappaEnte = new LinkedHashMap();
							for (Object nomeApp : intestazioneTab) {

								/*
								 * if(((String)nomeApp).toLowerCase().endsWith("ente"
								 * )) { AmApplication a=new AmApplication();
								 * a.setName(ente); a.setUrl(ente);
								 * a.setEnte(ente); a.setTipo_app(ente);
								 * ArrayList appo=new ArrayList(); appo.add(a);
								 * mappaEnte
								 * .put(((String)nomeApp).toLowerCase(),appo);
								 * 
								 * } else
								 */
								mappaEnte.put(((String) nomeApp).toLowerCase(), new ArrayList());
							}
							mappa.put(comune, mappaEnte);
						}

						AmApplication ap = new AmApplication();
						ap.setName(rs.getString("NAME"));
						ap.setUrl(rs.getString("URL"));
						ap.getComune().setBelfiore(comune);
						ap.setTipo_app(rs.getString("APP_TYPE").toLowerCase());
						ap.setCat_app(rs.getString("APP_CATEGORY"));
						// controllo se l'utente può accedere all'applicazione
						if(!comunePrev.equals(comune)) {
							//ricarico applicazioni permesse
							listaApplUtente = GestionePermessi.getApplications(new AuthContext(user, con), comune);
						}
						comunePrev = comune;
						
						if (listaApplUtente.contains(ap.getName())) {
							
							permessoDiAccessoAdComune.put(comune, true);
							ap.setAccessoAutorizzato(true);
							
						}

						((List) mappa.get(comune).get(ap.getTipo_app())).add(ap);

					}

					if (listaEntiGestionePermessi.size()>0) {
						request.setAttribute("gestPerm", "true");
					} 
					
					// tolgo dalla mappa gli enti per i quali l'utente non
					// ha nessun diritto di accesso
					for (Iterator it = permessoDiAccessoAdComune.keySet()
							.iterator(); it.hasNext();) {
						String name = (String) it.next();
						if (!(Boolean) permessoDiAccessoAdComune.get(name) && !listaEntiGestionePermessi.contains(name)) {
							mappa.remove(name);
							// System.out.println("rimossa "+ name );
						}
					}
					
					String enteScelto = request.getParameter("enteScelto");
					String es = request.getParameter("es");
					
					request.setAttribute("listaApplicazioni", mappa);
					request.setAttribute("myMenu", getMyMenu(mappa,
							decodeComuni, listaEntiGestionePermessi,usrStr, sessionId));
					
					VersioneBean v = new VersioneBean();
					String versione = v.getVersione();
					String versionesw = v.getVersioneSmartWelfare();
					request.setAttribute("versione", versione);
					request.setAttribute("versionesw", versionesw);
					
				}

				request.getRequestDispatcher("/jsp/index.jsp").forward(request,
						response);
			} catch (Throwable e) {
				BaseAction.toErrorPage(request, response, e);
			} finally {
				BaseAction.chiudiConnessione(con, statment);
			}
		} catch (Exception e) {
			BaseAction.toErrorPage(request, response, e);
		}

	}

	

	private String getMyMenu(LinkedHashMap<String, LinkedHashMap> mappa,
			Hashtable<String, String> decodeComuni, ArrayList listaEntiGestionePermessi,String user, String sessionId) {
		StringBuffer out = new StringBuffer();
		
		
		out.append("<div class=\"TabView\" id=\"TabView1\" >\n");
		out.append("<div class=\"Tabs\" id=\"Tabs\"> ");
		for (String key : mappa.keySet()) {
			if (key != null)
				out.append("<a> " + /*decodeComuni.get(key) +*/ " </a>");
		}
		out.append("<select NAME=comuni onchange=\"javascript:TabView.switchTab(0, this);\">");
		for (String key : mappa.keySet()) {
			if (key != null)
				out.append("<option value=\'"+key+"\'>" +decodeComuni.get(key)+ "</option>");
		}
		out.append("</select>");
		out.append("</div><div class=\"Pages\">\n");

		for (String key : mappa.keySet()) {
			
			

			StringBuffer appAcq = new StringBuffer();
			StringBuffer appVis = new StringBuffer();
			StringBuffer appVert = new StringBuffer();
			StringBuffer appAltri = new StringBuffer();
			LinkedHashMap app = mappa.get(key);

			for (Object k : app.keySet()) {
				ArrayList<AmApplication> l = (ArrayList<AmApplication>) app
						.get(k);
				for (AmApplication a : l) {
					if (a.getCat_app().equals("1")) {
						String href = null;

						if (a.isAccessoAutorizzato()) {
							if (a.getUrl() == null
									|| a.getUrl().trim().equals("")) {
								href = "<a href=\"javascript:void(0);\" onclick=\"alert('URL non impostata! Contattare il supporto tecnico');\">";
							} else {
								href = "<a href=\"" + a.getUrl()
										+ "\" target=\"_blank\" >";
							}
						}
						appAcq
								.append("			<div style=\"width:150px;text-align:center; float:left; margin-left:10px\">\n");
						appAcq
								.append("				<div style=\"width:150px;text-align:center;clear:left; \">\n");
						if (href != null) {
							appAcq.append(href);
						}
						appAcq.append("				<img border=\"0\" src=\"jsp/img/"
								+ a.getTipo_app().toLowerCase()
								+ ".gif\" width=\"150\" height=\"150\" />\n");
						if (href != null) {
							appAcq.append("</a>");
						}
						appAcq.append("				</div>\n");
						appAcq
								.append("				<div style=\"width:150px;text-align:center;clear:left;\">");
						if (href != null) {
							appAcq.append(href);
						}
						appAcq.append(a.getName());
						if (href != null) {
							appAcq.append("</a>");
						}

						if (listaEntiGestionePermessi.contains(key)) {
							appAcq
									.append("<a href=\"CaricaPermessi?application="
											+ a.getName()
											+ "&appType="
											+ a.getTipo_app()
											+ "&ente="
											+ key
											+ "\"><img style=\"border:none;\" src=\"img/keys.gif\" ></img></a>");

							appAcq
									.append("<a href=\"CaricaServizi?application="
											+ a.getName()
											+ "&appType="
											+ a.getTipo_app()
											+ "\"><img style=\"border:none;\" src=\"img/services.png\" ></img></a>");
						}

						appAcq.append("</div>\n");
						appAcq.append("			</div>\n");
					} else if (a.getCat_app().equals("2")) {
						String href = null;

						if (a.isAccessoAutorizzato()) {
							if (a.getUrl() == null
									|| a.getUrl().trim().equals("")) {
								href = "<a href=\"javascript:void(0);\" onclick=\"alert('URL non impostata! Contattare il supporto tecnico');\">";
							} else {
								href = "<a href=\"" + a.getUrl() + "?es=" + encode(key) + "&sid="+ sessionId
										+ "\"  target=\"_blank\" >";
							}
						}
						appVis
								.append("			<div style=\"width:150px;text-align:center; float:left; margin-left:10px\">\n");
						appVis
								.append("				<div style=\"width:150px;text-align:center;clear:left; \">\n");
						if (href != null) {
							appVis.append(href);
						}
						appVis.append("				<img border=\"0\" src=\"jsp/img/"
								+ a.getTipo_app().toLowerCase()
								+ ".gif\" width=\"150\" height=\"150\" />\n");
						if (href != null) {
							appVis.append("</a>");
						}
						appVis.append("				</div>\n");
						appVis
								.append("				<div style=\"width:150px;text-align:center;clear:left;\">");
						if (href != null) {
							appVis.append(href);
						}
						appVis.append(a.getName());
						if (href != null) {
							appVis.append("</a>");
						}

						if (listaEntiGestionePermessi.contains(key)) {
							appVis
									.append("<a href=\"CaricaPermessi?application="
											+ a.getName()
											+ "&appType="
											+ a.getTipo_app()
											+ "&ente="
											+ key
											+ "\"><img style=\"border:none;\" src=\"img/keys.gif\" ></img></a>");

							appVis
									.append("<a href=\"CaricaServizi?application="
											+ a.getName()
											+ "&appType="
											+ a.getTipo_app()
											+ "\"><img style=\"border:none;\" src=\"img/services.png\" ></img></a>");
						}

						appVis.append("</div>\n");
						appVis.append("			</div>\n");
					} else if (a.getCat_app().equals("3")) {
						String href = null;

						if (a.isAccessoAutorizzato()) {
							if (a.getUrl() == null
									|| a.getUrl().trim().equals("")) {
								href = "<a href=\"javascript:void(0);\" onclick=\"alert('URL non impostata! Contattare il supporto tecnico');\">";
							} else {
								
								href = "<a href=\"" + a.getUrl() + "?es=" + encode(key) + "&sid="+ sessionId
										+ "\"  target=\"_blank\" >";
						
							}
						}
						appVert
								.append("			<div style=\"width:150px;text-align:center; float:left; margin-left:10px\">\n");
						appVert
								.append("				<div style=\"width:150px;text-align:center;clear:left; \">\n");
						if (href != null) {
							appVert.append(href);
						}
						appVert.append("				<img border=\"0\" src=\"jsp/img/"
								+ a.getTipo_app().toLowerCase()
								+ ".gif\" width=\"150\" height=\"150\" />\n");
						if (href != null) {
							appVert.append("</a>");
						}
						appVert.append("				</div>\n");
						appVert
								.append("				<div style=\"width:150px;text-align:center;clear:left;\">");
						if (href != null) {
							appVert.append(href);
						}
						appVert.append(a.getName());
						if (href != null) {
							appVert.append("</a>");
						}

						if (listaEntiGestionePermessi.contains(key)) {
							appVert
									.append("<a href=\"CaricaPermessi?application="
											+ a.getName()
											+ "&appType="
											+ a.getTipo_app()
											+ "&ente="
											+ key
											+ "\"><img style=\"border:none;\" src=\"img/keys.gif\" ></img></a>");

							appVert
									.append("<a href=\"CaricaServizi?application="
											+ a.getName()
											+ "&appType="
											+ a.getTipo_app()
											+ "\"><img style=\"border:none;\" src=\"img/services.png\" ></img></a>");
						}

						appVert.append("</div>\n");
						appVert.append("			</div>\n");
					} else {
						appAltri.append("&nbsp;");
						String href = null;
						if (a.isAccessoAutorizzato()) {
							if (a.getUrl() == null
									|| a.getUrl().trim().equals("")) {
								href = "&nbsp;<a href=\"javascript:void(0);\" onclick=\"alert('URL non impostata! Contattare il supporto tecnico');\">";
							} else {
								href = "<a href=\"" + a.getUrl()
										+ "\"  target=\"_blank\" >";
							}
						}
						if (href != null) {
							appAltri.append(href);
						}
						appAltri.append(a.getName());
						if (href != null) {
							appAltri.append("</a>");
						}
						if (listaEntiGestionePermessi.contains(key)) {
							appAltri
									.append("<a href=\"CaricaPermessi?application="
											+ a.getName()
											+ "&appType="
											+ a.getTipo_app()
											+ "&ente="
											+ key
											+ "\"><img style=\"border:none;\" src=\"img/keys.gif\" ></img></a>");

							appAltri
									.append("<a href=\"CaricaServizi?application="
											+ a.getName()
											+ "&appType="
											+ a.getTipo_app()
											+ "\"><img style=\"border:none;\" src=\"img/services.png\" ></img></a>");
						}

						appAltri.append("&nbsp;");
					}

				}

			}

			out
					.append("<div class=\"Page\"><table width=\"100%\" border=\"1\" align=\"center\" class=\"griglia\" > \n");

			String descrizioneSezione = null;
			String descAltriApp = null;
			if (key == null) {
				descrizioneSezione = "&nbsp;";
				descAltriApp = "UTILITY";
			} else {
				descrizioneSezione = "ENTE:" + key;
				descAltriApp = "Altri Applicativi";
			}
			out
					.append("<tr><th colspan=\"2\" nowrap=\"nowrap\" class=\"divTableTitle\" id=\"codiceEnte\">"
							+ descrizioneSezione + " </th></tr>\n");

			if (appAcq != null && appAcq.toString().length() > 1) {
				out.append("  <tr >\n");
				out
						.append("    <td width=\"1%\" nowrap=\"nowrap\">Processi di acquisizione<br /> e trattamento dati </td>\n");
				out.append("    <td align=\"left\" >\n");
				out.append(appAcq);
				out.append("	 </td>\n");
				out.append("  </tr>\n");
			}
			if (appVis != null && appVis.toString().length() > 1) {
				out.append("  <tr >\n");
				out
						.append("    <td width=\"1%\" nowrap=\"nowrap\">Visualizzazione<br /> fonti dati  </td>\n");
				out.append("    <td align=\"left\" >\n");
				out.append(appVis);
				out.append("	 </td>\n");
				out.append("  </tr>\n");
			}
			if (appVert != null && appVert.toString().length() > 1) {
				out.append("  <tr >\n");
				out
						.append("    <td width=\"1%\" nowrap=\"nowrap\">Applicativi verticali </td>\n");
				out.append("    <td align=\"left\" >\n");
				out.append(appVert);
				out.append("	 </td>\n");
				out.append("  </tr>\n");
			}
			if (appAltri != null && appAltri.toString().length() > 1) {
				out.append("  <tr >\n");
				out.append("    <td width=\"1%\" nowrap=\"nowrap\">"
						+ descAltriApp + "</td>\n");
				out.append("    <td align=\"left\" >\n");
				out.append(appAltri);
				out.append("	 </td>\n");
				out.append("  </tr>\n");
			}
			out.append("</table></div>");
		}
		out.append("</div></div>");
		// se piu enti
		if (mappa.size() > 1) {
			out.append("<script>init();</script>");
		} else {
			out.append("\n<script>\n");
			out.append("var t = document.getElementById(\"Tabs\");\n");
			out
					.append("document.getElementById(\"codiceEnte\").innerHTML  += \" - \"+ t.innerHTML ;\n");
			out.append("t.style.display = \"none\";\n");
			out.append("</script>\n");
		}
		out.append("<script type='text/javascript'>");
		out.append("function lancia(){");
		out.append("document.forms['carsoc'].submit();}");
		out.append("</script>");
		return out.toString();
	}
	
	public String encode(String stringToEncode){
		String returnValue = "";
		BASE64Encoder encrypt = new BASE64Encoder();
		try{
			String codedString = encrypt.encode(stringToEncode.getBytes());
			returnValue = codedString;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return returnValue;
	}
	
	private boolean verificaDatiSoggettoObbligatori(Principal user){
		
		boolean anagraficaPresente = true;
		AmUserUfficio ufficio = userService.getDatiUfficio(user.getName());
		AmAnagrafica anagrafica = anagraficaService.findAnagraficaByUserName(user.getName());
		
		if(anagrafica==null)
			anagraficaPresente=false;
		else{
			
			if(anagrafica.getCognome()==null || anagrafica.getCognome().equals(""))
				anagraficaPresente=false;
			if(anagrafica.getNome()==null || anagrafica.getNome().equals(""))
				anagraficaPresente=false;
			if(anagrafica.getDataNascita()==null)
				anagraficaPresente=false;
		}
		
		if(ufficio.getDirezione()==null || ufficio.getDirezione().equals(""))
			anagraficaPresente = false;
		if(ufficio.getSettore()==null || ufficio.getSettore().equals(""))
			anagraficaPresente = false;
		if(ufficio.getTelefono()==null || ufficio.getTelefono().equals(""))
			anagraficaPresente = false;
		if(ufficio.getEmail()==null || ufficio.getEmail().equals(""))
			anagraficaPresente = false;
		
		return anagraficaPresente;
	}

	
}