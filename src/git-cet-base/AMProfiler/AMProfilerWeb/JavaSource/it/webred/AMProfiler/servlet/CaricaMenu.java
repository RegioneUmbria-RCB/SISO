package it.webred.AMProfiler.servlet;

import it.umbriadigitale.rss.model.Feed;
import it.umbriadigitale.rss.read.RSSFeedParser;
import it.umbriadigitale.utility.Handshaking;
import it.webred.AMProfiler.beans.AmApplication;
import it.webred.AMProfiler.util.NetIsAvailable;
import it.webred.AMProfiler.util.Token;
import it.webred.AMProfiler.util.TokenUtils;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUserUfficio;
import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.dto.IstanzaApplicazioneDTO;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.permessi.AuthContext;
import it.webred.permessi.GestionePermessi;

import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Encoder;

/**
 * Servlet implementation class for Servlet: CaricaMenu
 * 
 */
public class CaricaMenu extends AccessoBase{

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
			logger.info("PRINCIPAL USERNAME: " + user.getName());
			usrStr=user.toString();
			Connection con = null;
			//Soclav
			TokenUtils tokenUtils = new TokenUtils();
			try {
				
				//Controlla presenza dati soggetto, e richiedi se non presenti
				boolean anagraficaPresente = this.verificaDatiSoggettoObbligatori(user);
				logger.debug("CaricaMenu-anagraficaPresente:"+anagraficaPresente);
				if(!anagraficaPresente){
					request.getRequestDispatcher("/SalvaUtente?mode=vis&soloDatiUfficio=true&userName="+user.getName()).forward(request,response);
					return;
				}
				
				con = BaseAction.apriConnessione();
				
				Boolean gitInserimentoMotivoAccesso = false;
				
				String val =  getParametroGlobale("git.inserimento.motivo.accesso");
				if(val!=null) gitInserimentoMotivoAccesso = Boolean.parseBoolean(val);
				 
				if (gitInserimentoMotivoAccesso && ( request.getParameter("doneInsPratica") == null || !new Boolean(request.getParameter("doneInsPratica")).booleanValue()) ) {
					/*
					 * In questo ramo le configurazioni dicono di inserire la pratica e il parametro indica che ancora va fatta
					 * ancora la registrazione dell'accesso
					 */
					request.getRequestDispatcher("/SceltaEnte?doOnlyAMInsPratica=true").forward(request,response);
					return;
				}else if (!gitInserimentoMotivoAccesso && ( request.getParameter("doneInsPratica") == null || !new Boolean(request.getParameter("doneInsPratica")).booleanValue()) ) {
					/*
					 * In questo ramo le configurazioni dicono di NON inserire la pratica e il parametro indica che ancora va fatta 
					 * la registrazione dell'accesso 
					 */
					request.getRequestDispatcher("/SceltaEnte?doOnlyAMInsPratica=false").forward(request, response);
					return;
				}
				//else if (!gitInserimentoMotivoAccesso && request.getParameter("doneInsPratica") != null && new Boolean(request.getParameter("doneInsPratica")).booleanValue() ){
					/*
					 * Nonostante sia stata configurato il salto della introduzione del motivo di accesso e la pratica
					 * dobbiamo comunque registrare l'accesso
					 */
					//salvaTracciaAccessi(request,user.getName(), ((HttpServletRequest) request).getRequestURL().toString().replace("SceltaEnte", ""), "SKIP", "SKIP", enteScelto, sessionId);
				//	request.getRequestDispatcher("/SceltaEnte?doOnlyAMInsPratica=false").forward(request, response);
				//}
				
				
				List<String> listaEntiGestionePermessi = GestionePermessi.getEntiGestionepermessi(new AuthContext(user, con));

				List<AmComune> listaComuni = comuneService.getListaComune();
				Hashtable<String, String> decodeComuni = new Hashtable<String, String>();
				for (AmComune c : listaComuni) {
					decodeComuni.put(c.getBelfiore(), c.getDescrizione());
				}
				
				List<String> intestazioneTab = new ArrayList<String>();
				intestazioneTab.add("Ente");
				List<String> tipoApp = applicationService.getListaTipoAppMenu();
				intestazioneTab.addAll(tipoApp);
				
				// controllo quante applicazioni ho trovato
				if (intestazioneTab.size() != 1) {
					
					/*Versione*/
					String versione = parameterService.getVersionePiattaforma();
					String versionesw = parameterService.getVersioneSmartWelfare();
					request.setAttribute("versione", versione!=null ? versione : "");
					request.setAttribute("versionesw", versionesw!=null ? versionesw : "");
					
					/*MESSAGGIO DI TESTATA*/
					caricaMessaggioTestata(session);
					
					/*GIT NEWS*/
					caricaNews(session, versione);
					
					request.setAttribute("intestazioneTab", intestazioneTab);

					LinkedHashMap<String, LinkedHashMap> mappa = new LinkedHashMap();
					
					HashMap<String, Boolean> permessoDiAccessoAdComune = new HashMap<String, Boolean>();

					String urlScheme = request.getScheme();
					String urlServerName = request.getServerName();
					String urlServerPort = new Integer( request.getServerPort() ).toString();
					String urlUri = request.getRequestURI();
					String urlQueryString = request.getQueryString();

					logger.info("URL RICHIESTA: " + urlScheme + "://" + urlServerName + ":" + urlServerPort + urlUri + "?" + urlQueryString);
					
					String comunePrev = "";
					ArrayList<String> listaApplUtente = null;
					
					List<IstanzaApplicazioneDTO> istanze = applicationService.getListaIstanzeMenu(user.getName());
					for(IstanzaApplicazioneDTO istanza : istanze) {
						String comune = istanza.getEnteCod();
						if (mappa.get(comune) == null) {
							permessoDiAccessoAdComune.put(istanza.getEnteCod(), false);
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
						ap.setName(istanza.getAppName());
						
						String url = null;
						if(istanza.getConcatUrl()){
							String port = !StringUtils.isBlank(istanza.getPortRewrite()) ? istanza.getPortRewrite() : urlServerPort;
							url = urlScheme + "://" + urlServerName + ":" + port + istanza.getUri();
						
							/**	soclav **/
							String tokenStr = "/jsp/public/index.jsp?token=";
							if("SocLav".equalsIgnoreCase(istanza.getAppName())) {
							 
								String tokenGen ="";
								Token token = new Token();
								token.addClaimToToekn("ente", 	comune);
								token.addClaimToToekn("login", usrStr);
								tokenGen = token.getNotExpired();
								tokenStr = tokenStr.concat(tokenGen);
								tokenUtils.addTokenEnte(tokenGen, comune);
								url = url.concat(tokenStr);
							}
						}else{
							url = istanza.getUrl();
						}
						
						/************/
						logger.info("URL RISPOSTA: " + url);
						ap.setUrl( url );
						//ap.setUrl(rs.getString("URL"));
						ap.getComune().setBelfiore(istanza.getEnteCod());
						ap.setTipo_app(istanza.getAppTipo().toLowerCase());
						ap.setCat_app(istanza.getAppCategory());
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
					//SOCLAV
					session.setAttribute("token", tokenUtils);
					//SOCLAV Fine
					
					if (listaEntiGestionePermessi.size()>0) {
						request.setAttribute("gestPerm", "true");
					} 
					
					// tolgo dalla mappa gli enti per i quali l'utente non
					// ha nessun diritto di accesso
					for (Iterator it = permessoDiAccessoAdComune.keySet().iterator(); it.hasNext();) {
						String name = (String) it.next();
						if (!(Boolean) permessoDiAccessoAdComune.get(name) && !listaEntiGestionePermessi.contains(name)) {
							mappa.remove(name);
							// System.out.println("rimossa "+ name );
						}
					}
					
					request.setAttribute("listaApplicazioni", mappa);
					request.setAttribute("myMenu", getMyMenu(mappa, decodeComuni, listaEntiGestionePermessi,usrStr, sessionId));
					
				}
				request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
				
			} catch (Throwable e) {
				BaseAction.toErrorPage(request, response, e);
			} finally {
				BaseAction.chiudiConnessione(con);
			}
		} catch (Exception e) {
			BaseAction.toErrorPage(request, response, e);
		}

	}//-------------------------------------------------------------------------

	private String getMyMenu(LinkedHashMap<String, LinkedHashMap> mappa,
			Hashtable<String, String> decodeComuni, List<String> listaEntiGestionePermessi,String user, String sessionId) {
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
				ArrayList<AmApplication> l = (ArrayList<AmApplication>) app.get(k);
				for (AmApplication a : l) {
					if (a.getCat_app().equals("1")) {
						String href = null;

						if (a.isAccessoAutorizzato()) {
							if (a.getUrl() == null || a.getUrl().trim().equals("")) {
								href = "<a href=\"javascript:void(0);\" onclick=\"alert('URL non impostata! Contattare il supporto tecnico');\">";
							} else {
								href = "<a href=\"" + a.getUrl()+ "\" target=\"_blank\" >";
							}
						}
						appAcq.append("			<div style=\"width:150px;text-align:center; float:left; margin-left:10px\">\n");
						appAcq.append("				<div style=\"width:150px;text-align:center;clear:left; \">\n");
						if (href != null) {
							appAcq.append(href);
						}
						appAcq.append("<img border=\"0\" src=\"jsp/img/"+ a.getTipo_app().toLowerCase()+ ".gif\" width=\"150\" height=\"150\" />\n");
						if (href != null) {
							appAcq.append("</a>");
						}
						appAcq.append("				</div>\n");
						appAcq.append("				<div style=\"width:150px;text-align:center;clear:left;\">");
						if (href != null) {
							appAcq.append(href);
						}
						appAcq.append(a.getName());
						if (href != null) {
							appAcq.append("</a>");
						}

						if (listaEntiGestionePermessi.contains(key)) {
							appAcq.append("<a href=\"CaricaPermessi?application="
											+ a.getName()
											+ "&appType="
											+ a.getTipo_app()
											+ "&ente="
											+ key
											+ "\"><img style=\"border:none;\" src=\"img/keys.gif\" ></img></a>");

							appAcq.append("<a href=\"CaricaServizi?application="
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
						appVis.append("			<div style=\"width:150px;text-align:center; float:left; margin-left:10px\">\n");
						appVis.append("				<div style=\"width:150px;text-align:center;clear:left; \">\n");
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
						appVis.append("				<div style=\"width:150px;text-align:center;clear:left;\">");
						if (href != null) {
							appVis.append(href);
						}
						appVis.append(a.getName());
						if (href != null) {
							appVis.append("</a>");
						}

						if (listaEntiGestionePermessi.contains(key)) {
							appVis.append("<a href=\"CaricaPermessi?application="
											+ a.getName()
											+ "&appType="
											+ a.getTipo_app()
											+ "&ente="
											+ key
											+ "\"><img style=\"border:none;\" src=\"img/keys.gif\" ></img></a>");

							appVis.append("<a href=\"CaricaServizi?application="
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
								
								/**
								 * SOCLAV : Deve essere esclusa la concatenazione della URL
								 * */
								if(a.getName().equalsIgnoreCase("SocLav")) {
									href = "<a href=\"" + a.getUrl() + "\"  target=\"_blank\" >"; 
								}
								else
									href = "<a href=\"" + a.getUrl() + "?es=" + encode(key) + "&sid="+ sessionId
										+ "\"  target=\"_blank\" >";
						
							}
						}
						appVert.append("			<div style=\"width:150px;text-align:center; float:left; margin-left:10px\">\n");
						appVert.append("				<div style=\"width:150px;text-align:center;clear:left; \">\n");
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
						appVert.append("				<div style=\"width:150px;text-align:center;clear:left;\">");
						if (href != null) {
							appVert.append(href);
						}
						appVert.append(a.getName());
						if (href != null) {
							appVert.append("</a>");
						}

						if (listaEntiGestionePermessi.contains(key)) {
							appVert.append("<a href=\"CaricaPermessi?application="
											+ a.getName()
											+ "&appType="
											+ a.getTipo_app()
											+ "&ente="
											+ key
											+ "\"><img style=\"border:none;\" src=\"img/keys.gif\" ></img></a>");

							appVert.append("<a href=\"CaricaServizi?application="
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
							appAltri.append("<a href=\"CaricaPermessi?application="
											+ a.getName()
											+ "&appType="
											+ a.getTipo_app()
											+ "&ente="
											+ key
											+ "\"><img style=\"border:none;\" src=\"img/keys.gif\" ></img></a>");

							appAltri.append("<a href=\"CaricaServizi?application="
											+ a.getName()
											+ "&appType="
											+ a.getTipo_app()
											+ "\"><img style=\"border:none;\" src=\"img/services.png\" ></img></a>");
						}

						appAltri.append("&nbsp;");
					}

				}

			}

			out.append("<div class=\"Page\"><table width=\"100%\" border=\"1\" align=\"center\" class=\"griglia\" > \n");

			String descrizioneSezione = null;
			String descAltriApp = null;
			if (key == null) {
				descrizioneSezione = "&nbsp;";
				descAltriApp = "UTILITY";
			} else {
				descrizioneSezione = "ENTE:" + key;
				descAltriApp = "Altri Applicativi";
			}
			out.append("<tr><th colspan=\"2\" nowrap=\"nowrap\" class=\"divTableTitle\" id=\"codiceEnte\">"
							+ descrizioneSezione + " </th></tr>\n");

			if (appAcq != null && appAcq.toString().length() > 1) {
				out.append("  <tr >\n");
				out.append("    <td width=\"1%\" nowrap=\"nowrap\">Processi di acquisizione<br /> e trattamento dati </td>\n");
				out.append("    <td align=\"left\" >\n");
				out.append(appAcq);
				out.append("	 </td>\n");
				out.append("  </tr>\n");
			}
			if (appVis != null && appVis.toString().length() > 1) {
				out.append("  <tr >\n");
				out.append("    <td width=\"1%\" nowrap=\"nowrap\">Visualizzazione<br /> fonti dati  </td>\n");
				out.append("    <td align=\"left\" >\n");
				out.append(appVis);
				out.append("	 </td>\n");
				out.append("  </tr>\n");
			}
			if (appVert != null && appVert.toString().length() > 1) {
				out.append("  <tr >\n");
				out.append("    <td width=\"1%\" nowrap=\"nowrap\">Applicativi verticali </td>\n");
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
			out.append("document.getElementById(\"codiceEnte\").innerHTML  += \" - \"+ t.innerHTML ;\n");
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
		AmUserUfficio ufficio = userService.getDatiUfficioNoCaseSens(user.getName());
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
	
	private void caricaMessaggioTestata(HttpSession session) {
		String msgTestata = "";
		try {
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setKey("git.news.messaggio");
			AmKeyValueExt amKey = parameterService.getAmKeyValueExt(criteria);
			msgTestata = amKey == null || amKey.getValueConf() == null ? "" : amKey.getValueConf();
		} catch (Exception e) {
			//errore non bloccante
			logger.warn("IMPOSSIBILE VISUALIZZARE IL MESSAGGIO DI TESTATA (git.news.messaggio): " + e.getMessage());
		}
		session.setAttribute("msgTestata", msgTestata);
	}
	
	private void caricaNews(HttpSession session, String versione){
		String visualizzaGitnews = getParametroGlobale("gitnews.enabled");
		if(visualizzaGitnews!=null){
			session.setAttribute("GitNews", visualizzaGitnews);
			if(visualizzaGitnews.equals("T")){
				String serverUrlGitnews = getParametroGlobale("gitnews.serverUrl");	
				String proxyIp = this.getParametroGlobale("proxy.ip");
				String proxyPort = this.getParametroGlobale("proxy.port");
				
				/*
				 * Test internet connection
				 * Parse Feed RSS and generate news List
				 */
				int portProxy = 0;
				if (proxyPort != null)
					portProxy = Integer.parseInt(proxyPort);
				
				//String urlBase = "http://127.0.0.1:9090";
				//String urlBase = "http://www.google.com";
				//String proxyIp = "192.168.200.30";
				//int portProxy = 8080;
				String urlBase = serverUrlGitnews;
				boolean netOk = false;
				if (portProxy > 0){
					logger.info("RSS FEED: Test internet con PROXY [" + proxyIp + ":" + proxyPort + "]");
					netOk = NetIsAvailable.internetIsAvailable( urlBase, proxyIp, portProxy );
				}else{
					logger.info("RSS FEED: Test internet senza PROXY ");
					netOk = NetIsAvailable.internetIsAvailable( urlBase );
				}

				if ( netOk ){
					logger.info("RSS FEED: Test internet con esito POSITIVO [urlBase: " + urlBase + "] ");
					/*
					 * Generazione OTP
					 */
					Handshaking h = new Handshaking();
					String handshake = h.getHandshakeString();
//					handshake = "fake";
					String urlFeedRss = urlBase+"/Git_News/feed/RilasciGit.rss";
					try{
						RSSFeedParser parser = null;
						if (portProxy > 0)
							parser = new RSSFeedParser(urlFeedRss + "?gitVersion="+versione+"&handshake="+handshake, proxyIp, portProxy);
						else
							parser = new RSSFeedParser(urlFeedRss + "?gitVersion="+versione+"&handshake="+handshake);
						logger.info("RSS FEED: " + urlFeedRss + "?gitVersion="+versione+"&handshake="+handshake);
						Feed feed = parser.readFeed();
						session.setAttribute("lstFeedMsg", feed.getMessages());
		                //for (FeedMessage feedMsg : feed.getMessages()) {
		                	//XXX Il filtro in base alla versione viene applicato lato server
		                    //System.out.println(feedMsg.getDescription());
//		                }
					}catch ( Exception e ) {
						session.setAttribute("ErrMsg", "Consultazione aggiornamenti GIT momentaneamente non disponibile!");
						e.printStackTrace();
					} 
				}else{
					logger.info("RSS FEED: Test internet con esito NEGATIVO [urlBase: " + urlBase + "] ");
					session.setAttribute("ErrMsg", "Consultazione aggiornamenti GIT momentaneamente non disponibile.");
					//System.out.println("GitNews: Servizio momentaneamente non disponibile");
				}
			}
		}
	}

	
}