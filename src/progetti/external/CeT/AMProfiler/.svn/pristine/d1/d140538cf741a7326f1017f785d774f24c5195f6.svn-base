package it.webred.AMProfiler.servlet;

import it.webred.AMProfiler.beans.AmComune;
import it.webred.AMProfiler.beans.AmGroup;
import it.webred.AMProfiler.util.OreMinuti;
import it.webred.AMProfiler.util.SendEmail;
import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUserUfficio;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.application.ApplicationService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.AnagraficaDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class for Servlet: SalvaUtente
 * 
 */
public class SalvaUtente extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/AnagrafeServiceBean")
	protected AnagrafeService anagrafeService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/ParameterBaseService")
	protected ParameterService parameterService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/ComuneServiceBean")
	protected ComuneService comuneService;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/AnagraficaServiceBean")
	protected AnagraficaService anagraficaService;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/UserServiceBean")
	protected UserService userService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/LuoghiServiceBean")
	protected LuoghiService luoghiService;
	
	/*TEST*/
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/ApplicationServiceBean")
	protected ApplicationService applicationService;

	private static final long serialVersionUID = 1L;

	protected static Logger logger = Logger.getLogger("am.log");

	
	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public SalvaUtente() {
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

		Connection con = null;
		Statement statment = null;
		try {
			String mode = request.getParameter("mode");
			String userName = request.getParameter("userName");
			String pForce = request.getParameter("soloDatiUfficio");
			pForce = pForce!=null && !pForce.equals("") ? pForce : null;
			
			String pathApp = request.getParameter("pathApp");
			String myparam = request.getParameter("myparam");
			
			logger.debug("SalvaUtente-pathApp "+pathApp);
			logger.debug("SalvaUtente-myparam "+myparam);

			logger.debug("mode :"+mode);
			logger.debug("submit : "+request.getParameter("submit"));
			
			
			String loggedUser = request.getUserPrincipal().getName();

			request.setAttribute("mode", mode);
			request.setAttribute("showPwd", true);
			request.setAttribute("showGroup", !"pwd".equals(mode));
			
			request.setAttribute("soloDatiUfficio", pForce);
			request.setAttribute("pathApp", pathApp);
			request.setAttribute("myparam", myparam);
			
			request.setAttribute("ore", new OreMinuti().getOre());
			request.setAttribute("minuti", new OreMinuti().getMinuti());

			logger.debug("ECCOCI!");

			
			if (mode != null) {
				if (!"new".equals(mode)) {
					request.setAttribute("uN", userName);
					request.setAttribute("idAnagrafica", request.getParameter("idAnagrafica"));
					request.setAttribute("dtInsAna", request.getParameter("dtInsAna"));
					request.setAttribute("usrInsAna", request.getParameter("usrInsAna"));
				}
			}

			if ("Salva".equals(request.getParameter("submit"))) {
				logger.debug("Salva");
				if ("new".equals(mode)) {

					if (validate(request) && validateEmail(request) && validatePermessiAccesso(request) && BaseAction.validateDatiAnagrafica(request, userName) && BaseAction.validateDatiUfficio(request, userName)) {
						AmAnagrafica newAnag = fillAnagrafica(request);
						
						// Salva utente
						BaseAction.SalvaUtente(request);
						request.setAttribute("mode", "vis");
						request.setAttribute("uN", userName);
						
						// Salva Anagrafica
						newAnag.setAmUser(userService.getUserByName(request.getParameter("userName")));
						anagraficaService.createAnagrafica(newAnag);
						
						//Salva Dati Ufficio
						this.salvaDatiUfficio(request);
						
						
						// Salva permessi accesso
						BaseAction.UpdatePermessiAccessoUtente(request);
						
						// Salva Gruppi
						BaseAction.SalvaGruppi(request);
						
						
						//mando email all'utente
						String oggetto = "Comunicazione portale GIT: password";
						String messaggio = "Comunicazione proveniente dal portale GIT\n\n" +
						"La informiamo che la password per la sua utenza è stata settata: " + request.getParameter("password") + "\n\n" +
						"Distinti saluti, Amministratore del sistema";
						String esitoMail = sendEmail(request, oggetto, messaggio);
						
						String[] gruppi = (String[]) request.getParameterValues("gruppi");
						if (gruppi == null || gruppi.length == 0) {
							esitoMail = "Per l'invio della mail all'utente occorre specificare l'appartenza ad un gruppo";
						}
						request.setAttribute("esitoEmail", esitoMail);
					}else{
						//rimetto in request i dati inseriti
						AmAnagrafica ana = fillAnagrafica(request);
						
						setAnagraficaAttribute(request, ana);
						setPermessiAccessoAttribute(request);
						setDatiUfficioAttribute(request, BaseAction.fillDatiUfficio(request));
					}

				}

				if ("vis".equals(mode)) {
					
					boolean ok = false;
					boolean esito = true;
					
					if(pForce==null){
						// Salva utente
						String password = request.getParameter("password");
						String password2= request.getParameter("password2");
						String esitoMail = null;
						
						// Salva Gruppi
						BaseAction.SalvaGruppi(request);
						
						if( StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(password2) && validate(request) ) {
							BaseAction.SalvaUtente(request);
							
							if( request.getAttribute("salvaOk") == null) 
								esito = false;
							
							//mando email all'utente
							String oggetto = "Comunicazione portale GIT: password modificata";
							String messaggio = "Comunicazione proveniente dal portale GIT\n\n" +
							"La informiamo che la sua password è stata cambiata in: " + password + "\n\n" +
							"Distinti saluti, Amministratore del sistema";
							esitoMail = sendEmail(request, oggetto, messaggio);
						}
						
						// Salva permessi accesso
						if(validatePermessiAccesso(request))
							BaseAction.UpdatePermessiAccessoUtente(request);
						
						//Salva Email
						if(validateEmail(request)){
							BaseAction.UpdateEmail(request); 
							if( request.getAttribute("salvaOk") == null) 
								esito = false;
							ok = true;
						}
						
						// salvataggio OK
						if(ok && password.equals(password2)){
							request.setAttribute("salvaOk","Aggiornamento avvenuto correttamente");
							String[] gruppi = (String[]) request.getParameterValues("gruppi");
							if (gruppi == null || gruppi.length == 0) {
								esitoMail = "Per l'invio della mail all'utente occorre specificare l'appartenza ad un gruppo";
							}
							request.setAttribute("esitoEmail",esitoMail);
						}else
							esito = false;
					
					}
					
					

					// Salva Anagrafica
					if(BaseAction.validateDatiAnagrafica(request, userName)){
						anagraficaService.updateAnagrafica(fillAnagrafica(request));
					}else
						esito = false;
					
					//Salva i dati Ufficio
					if(BaseAction.validateDatiUfficio(request, userName))
						this.salvaDatiUfficio(request);
					else
						esito = false;
					
					
					// salvataggio OK
					if(esito)
						request.setAttribute("salvaOk","Aggiornamento dati avvenuto correttamente");
					else
						request.removeAttribute("salvaOk");
					
				}

				if ("pwd".equals(mode)) {
					
					logger.debug("cambio password " + request.getUserPrincipal().getName() );

					// Salva utente
					if (validate(request)) {
						BaseAction.SalvaUtente(request);
					}

					boolean isPwdScaduta = request.getParameter("pwdScaduta") != null && new Boolean(request.getParameter("pwdScaduta")).booleanValue();
					boolean stessoUtente = isPwdScaduta || request.getUserPrincipal().getName().equals(request.getAttribute("uN"));
					boolean doLogout = stessoUtente && request.getAttribute("salvaOk") != null;
					RequestDispatcher dispa = request.getRequestDispatcher("/jsp/updUser.jsp?doLogout=" + doLogout + (request.getAttribute("salvaOk") != null && isPwdScaduta ? "&soloMsg=true" : ""));
					

                    logger.debug("Sto facendo il logout......" + dispa.toString());
					
					dispa.forward(request, response);
					return;
				}
			}
			else if ("Invio".equals(request.getParameter("submit"))) {
				
				//Carica dati inseriti
				request.setAttribute("userName", userName);
				request.setAttribute("password", request.getParameter("password"));
				request.setAttribute("password2", request.getParameter("password2"));
				request.setAttribute("email", request.getParameter("email"));
				request.setAttribute("flPwdValida", request.getParameter("flPwdValida"));
				request.setAttribute("daOra", request.getParameter("daOra"));
				request.setAttribute("daMinuto", request.getParameter("daMinuto"));
				request.setAttribute("aOra", request.getParameter("aOra"));
				request.setAttribute("aMinuto", request.getParameter("aMinuto"));
				request.setAttribute("ipFidati", request.getParameter("ipFidati"));
				
				String[] gruppi = (String[]) request.getParameterValues("gruppi");
				if(gruppi != null){
					
					ArrayList<AmGroup> tuttigruppi = userName == null? BaseAction.listaGruppiByUser(loggedUser):BaseAction.listaTuttiGruppi(userName);
					for (AmGroup gruppo : tuttigruppi) {
						for (String selezione : gruppi) {
						if(gruppo.getName().equals(selezione.trim()))
							gruppo.setChecked(true);
						}
					}
					request.setAttribute("tuttigruppi", tuttigruppi);
				}
				
				//Carica anagrafe 
				RicercaSoggettoDTO ricercaDto = new RicercaSoggettoDTO();
				ricercaDto.setEnteId(request.getParameter("ente").trim());
				ricercaDto.setCodFis(request.getParameter("codfis"));
				List<AnagraficaDTO> anagrafeList = anagrafeService.getAnagrafeByCF(ricercaDto);
				if(anagrafeList.size() > 0){
					AnagraficaDTO anagrafe = anagrafeList.get(0);
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					request.setAttribute("idPersona", anagrafe.getPersona().getId());
					request.setAttribute("ente", ricercaDto.getEnteId());
					request.setAttribute("codfis", anagrafe.getPersona().getCodfisc());
					request.setAttribute("cognome", anagrafe.getPersona().getCognome());
					request.setAttribute("nome", anagrafe.getPersona().getNome());
					request.setAttribute("dtNascita", anagrafe.getPersona().getDataNascita() != null? sdf.format(anagrafe.getPersona().getDataNascita()): "");
					request.setAttribute("cmNascita", anagrafe.getComuneNascita() != null? anagrafe.getComuneNascita().getDescrizione(): "");
					request.setAttribute("prNascita", anagrafe.getProvNascita() != null? anagrafe.getProvNascita().getSigla(): "");
					if(anagrafe.getPersona().getDataEmi() == null && anagrafe.getComuneResidenza() != null){
						AmTabComuni comRes = luoghiService.getComuneItaByIstat(anagrafe.getComuneResidenza().getCodIstat());
						if(comRes != null){
							request.setAttribute("cmResidenza",comRes.getDenominazione());
							request.setAttribute("prResidenza",comRes.getSiglaProv());
						}
					}else if (anagrafe.getComuneResidenzaEmig() != null){
						request.setAttribute("cmResidenza",anagrafe.getComuneResidenzaEmig().getDescrizione());
						request.setAttribute("prResidenza",anagrafe.getProvResidenzaEmig().getSigla());
					}
					request.setAttribute("sesso", anagrafe.getPersona().getSesso());
					request.setAttribute("cap", (anagrafe.getCivico() != null && anagrafe.getCivico().getCap() != null)? anagrafe.getCivico().getCap().toString():"");
					if(anagrafe.getVia() != null)
						request.setAttribute("indirizzo", (anagrafe.getVia().getViasedime() != null? anagrafe.getVia().getViasedime(): "") +
							" " + (anagrafe.getVia().getDescrizione() != null? anagrafe.getVia().getDescrizione(): ""));
					if(anagrafe.getCivico() != null)
						request.setAttribute("civico", (anagrafe.getCivico().getCivLiv1()!= null? anagrafe.getCivico().getCivLiv1(): "") +
							" " + (anagrafe.getCivico().getCivLiv2() != null? anagrafe.getCivico().getCivLiv2(): "") +
							" " + (anagrafe.getCivico().getCivLiv3() != null? anagrafe.getCivico().getCivLiv3(): ""));
				}

			}else if("Procedi".equals(request.getParameter("submit"))){
				if(pathApp==null || "".equals(pathApp))
					request.getRequestDispatcher("/CaricaMenu?userName="+userName).forward(request,response);
				else
					request.getRequestDispatcher("/SceltaEnte?userName="+userName+"&pathApp="+pathApp+"&myparam="+myparam).forward(request,response);
				return;
				
			}
			
			if ("vis".equals(mode)) {
				
				//Carica Anagrafica
				AmAnagrafica anagrafica = anagraficaService.findAnagraficaByUserName(userName);
				request.setAttribute("email", anagrafica.getAmUser().getEmail());
				String orarioDa = anagrafica.getAmUser().getPermOraDa();
				String orarioA = anagrafica.getAmUser().getPermOraA();
				if(orarioDa != null && orarioA!=null){
					String[] s = orarioDa.split(":");
					request.setAttribute("daOra", s[0]);
					request.setAttribute("daMinuto", s[1]);
					s = orarioA.split(":");
					request.setAttribute("aOra", s[0]);
					request.setAttribute("aMinuto", s[1]);
				}
				if(anagrafica.getAmUser().getPermRangeIp()!=null)
					request.setAttribute("ipFidati", anagrafica.getAmUser().getPermRangeIp());
				setAnagraficaAttribute(request, anagrafica);
				
			}
			
			logger.debug("sopra Carica Gruppi");
			// Carica Gruppi
			if(request.getAttribute("tuttigruppi") == null){
				ArrayList<AmGroup> tuttigruppi = userName == null? BaseAction.listaGruppiByUser(loggedUser):BaseAction.listaTuttiGruppi(userName);
				request.setAttribute("tuttigruppi", tuttigruppi);
			}

			logger.debug("sopra Carica Enti");
			// Carica enti
			ArrayList<AmComune> comuniUtente = BaseAction.listaComuniByUser(loggedUser);
			request.setAttribute("comuniUtente", comuniUtente);
			
			logger.debug("sopra Carica Ufficio");
			//Carica Ufficio 
			AmUserUfficio ufficio = userService.getDatiUfficio(userName);

			request.setAttribute("direzione", ufficio.getDirezione());
			request.setAttribute("settore", ufficio.getSettore());
			request.setAttribute("emailUfficio", ufficio.getEmail());
			request.setAttribute("telUfficio", ufficio.getTelefono());
			
			request.setAttribute("soloDatiUfficio", pForce);
			
			request.setAttribute("flPwdValida", request.getParameter("flPwdValida"));
			
			RequestDispatcher disp = request.getRequestDispatcher("/jsp/updUser.jsp");
			
			logger.debug("RequestDispatcher " + disp.toString() );

			
			disp.forward(request, response);
		}
		catch (ParseException e) {
			logger.error("Errore di parsing:", e);
			BaseAction.toErrorPage(request, response, e);
		}
		catch (Exception e) {
			logger.error("Errore :", e);
			if (e.toString().indexOf("ORA-00001") == -1) {
				BaseAction.toErrorPage(request, response, e);
			}
			else {
				request.setAttribute("giaPresente", "true");
				request.getRequestDispatcher("/jsp/updUser.jsp").forward(request, response);
			}
		}
		finally {
			logger.debug("Chiudi Sessione");
			BaseAction.chiudiConnessione(con, statment);
		}

	}

	private boolean validate(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");

		if (userName == null || userName.trim().equals("")) {
			request.setAttribute("uN", "");
			return false;
		}

		if (password.length() == 0) {
			request.setAttribute("uN", userName);
			request.setAttribute("noPwd", "true");
			return false;
		}
		if (!password.equals(password2)) {
			request.setAttribute("uN", userName);
			request.setAttribute("pwdError", "true");
			return false;
		}

		return true;
	}
	
	private boolean validatePermessiAccesso(HttpServletRequest request) {
		
		String daOra = request.getParameter("daOra");
		String aOra = request.getParameter("aOra");
		String daMinuto = request.getParameter("daMinuto");
		String aMinuto = request.getParameter("aMinuto");
		
		if (daOra!=null&&!daOra.equals("")&&aOra!=null&&!aOra.equals("")&&
				daMinuto!=null&&!daMinuto.equals("")&&aMinuto!=null&&!aMinuto.equals("")){
			SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
			try {
				Date da = sdf.parse(daOra + daMinuto);
				Date a = sdf.parse(aOra + aMinuto);
				if(a.before(da)){
					request.setAttribute("timeError", "Il secondo orario è antecedente al primo");
					return false;
				}
			} catch (ParseException e) {
				logger.error(e.getMessage(),e);
			}
		}
		
		return true;
	}
	
	private boolean validateEmail(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String email = request.getParameter("email");

		if (email.length() == 0) {
			request.setAttribute("uN", userName);
			request.setAttribute("password", password);
			request.setAttribute("password2", password2);
			request.setAttribute("noEmail", "true");
			return false;
		}
		return true;
	}
	
	private AmAnagrafica fillAnagrafica(HttpServletRequest request) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		AmAnagrafica ana = new AmAnagrafica();
		
		String idAnagrafica = request.getParameter("idAnagrafica");
		
		ana.setCapResidenza(request.getParameter("cap"));
		ana.setCittadinanza(request.getParameter("cittadinanza"));
		ana.setCodiceFiscale(request.getParameter("codfis"));
		ana.setCognome(request.getParameter("cognome"));
		ana.setComuneNascita(request.getParameter("cmNascita"));
		ana.setComuneResidenza(request.getParameter("cmResidenza"));
		ana.setViaResidenza(request.getParameter("indirizzo"));
		ana.setNome(request.getParameter("nome"));
		ana.setCivicoResidenza(request.getParameter("civico"));
		ana.setProvinciaNascita(request.getParameter("prNascita"));
		ana.setProvinciaResidenza(request.getParameter("prResidenza"));
		ana.setSesso(request.getParameter("sesso"));
		ana.setStatoEsteroNascita(request.getParameter("esteroNascita"));
		ana.setTelefonoResidenza(request.getParameter("telefono"));
		ana.setCellulareResidenza(request.getParameter("cellulare"));
		ana.setFaxResidenza(request.getParameter("fax"));
		
		if(request.getParameter("indirizzoDomicilio") != null && !request.getParameter("indirizzoDomicilio").equals("")){
			ana.setComuneDomicilio(request.getParameter("cmDomicilio"));
			ana.setProvinciaDomicilio(request.getParameter("prDomicilio"));
			ana.setCapDomicilio(request.getParameter("capDomicilio"));
			ana.setViaDomicilio(request.getParameter("indirizzoDomicilio"));
			ana.setCivicoDomicilio(request.getParameter("civicoDomicilio"));
			ana.setTelefonoDomicilio(request.getParameter("telefonoDomicilio"));
			ana.setCellulareDomicilio(request.getParameter("cellulareDomicilio"));
			ana.setFaxDomicilio(request.getParameter("faxDomicilio"));
		}else{
			ana.setComuneDomicilio(request.getParameter("cmResidenza"));
			ana.setProvinciaDomicilio(request.getParameter("prResidenza"));
			ana.setCapDomicilio(request.getParameter("cap"));
			ana.setViaDomicilio(request.getParameter("indirizzo"));
			ana.setCivicoDomicilio(request.getParameter("civico"));
			ana.setTelefonoDomicilio(request.getParameter("telefono"));
			ana.setCellulareDomicilio(request.getParameter("cellulare"));
			ana.setFaxDomicilio(request.getParameter("fax"));
		}
		
		ana.setAmUser(userService.getUserByName(request.getParameter("userName")));
		if(request.getParameter("idPersona") != null)
			ana.setFkCetAnagrafica(request.getParameter("idPersona"));
		if(request.getParameter("ente") != null)
			ana.setFkCetEnte(request.getParameter("ente").trim());
		if(idAnagrafica == null || "".equals(idAnagrafica)){
			//solo nuova anagrafica
			ana.setDtIns(new Date());
			ana.setUserIns(request.getUserPrincipal().getName());
		}else{
			//solo update anagrafica
			ana.setId(Integer.parseInt(idAnagrafica));
			String dtIns = request.getParameter("dtInsAna");
			if(!"".equals(dtIns))
				ana.setDtIns(sdf.parse(dtIns));
			String usrIns = request.getParameter("usrInsAna");
			if(!"".equals(usrIns))
				ana.setUserIns(usrIns);
		}
		ana.setDtUpd(new Date());
		ana.setUserUpd(request.getUserPrincipal().getName());
		String dataNascita = request.getParameter("dtNascita");
		if(!"".equals(dataNascita))
			ana.setDataNascita(sdf.parse(dataNascita));

		return ana;
	}
	
	
	
	private void setAnagraficaAttribute(HttpServletRequest request, AmAnagrafica ana) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			request.setAttribute("idAnagrafica", ana.getId());
			request.setAttribute("dtInsAna", sdf.format(ana.getDtIns()));
			request.setAttribute("usrInsAna", ana.getUserIns());
			request.setAttribute("idPersona", ana.getFkCetAnagrafica());
			request.setAttribute("ente", ana.getFkCetEnte());
			
			request.setAttribute("cap", ana.getCapResidenza());
			request.setAttribute("cittadinanza", ana.getCittadinanza());
			request.setAttribute("codfis", ana.getCodiceFiscale());
			request.setAttribute("cognome", ana.getCognome());
			request.setAttribute("cmNascita", ana.getComuneNascita());
			request.setAttribute("cmResidenza", ana.getComuneResidenza());
			if(ana.getDataNascita() != null)
				request.setAttribute("dtNascita", sdf.format(ana.getDataNascita()));
			request.setAttribute("esteroNascita", ana.getStatoEsteroNascita());
			request.setAttribute("indirizzo", ana.getViaResidenza());
			request.setAttribute("nome", ana.getNome());
			request.setAttribute("civico", ana.getCivicoResidenza());
			request.setAttribute("prNascita", ana.getProvinciaNascita());
			request.setAttribute("prResidenza", ana.getProvinciaResidenza());
			request.setAttribute("sesso", ana.getSesso());
			request.setAttribute("telefono", ana.getTelefonoResidenza());
			request.setAttribute("cellulare", ana.getCellulareResidenza());
			request.setAttribute("fax", ana.getFaxResidenza());
			
			if(ana.getViaDomicilio() != null && !ana.getViaDomicilio().equals(ana.getViaResidenza())){
				request.setAttribute("cmDomicilio", ana.getComuneDomicilio());
				request.setAttribute("prDomicilio", ana.getProvinciaDomicilio());
				request.setAttribute("capDomicilio", ana.getCapDomicilio());
				request.setAttribute("indirizzoDomicilio", ana.getViaDomicilio());
				request.setAttribute("civicoDomicilio", ana.getCivicoDomicilio());
				request.setAttribute("telefonoDomicilio", ana.getTelefonoDomicilio());
				request.setAttribute("cellulareDomicilio", ana.getCellulareDomicilio());
				request.setAttribute("faxDomicilio", ana.getFaxDomicilio());
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

	}
	
	private void setDatiUfficioAttribute(HttpServletRequest request, AmUserUfficio ufficio) {
		request.setAttribute("direzione", ufficio.getDirezione());
		request.setAttribute("settore", ufficio.getSettore());
		request.setAttribute("emailUfficio", ufficio.getEmail());
		request.setAttribute("telUfficio", ufficio.getTelefono());
	}
	
	private void setPermessiAccessoAttribute(HttpServletRequest request) {
		request.setAttribute("daOra", request.getParameter("daOra"));
		request.setAttribute("aOra", request.getParameter("aOra"));
		request.setAttribute("aMinuto", request.getParameter("aMinuto"));
		request.setAttribute("daMinuto", request.getParameter("daMinuto"));
	}
	
	private String sendEmail(HttpServletRequest request, String oggetto, String messaggio){
		SendEmail send = new SendEmail();
		
		try {
			List<it.webred.ct.config.model.AmComune> lista = comuneService.getListaComuneByUsername(request.getParameter("userName"));
			if(lista.size()>0 && lista.get(0)!=null){
				String belfiore = lista.get(0).getBelfiore();
				ParameterSearchCriteria criteria = new ParameterSearchCriteria();
				criteria.setComune(belfiore);
				criteria.setKey("mail.server");
				AmKeyValueExt extServer = parameterService.getAmKeyValueExt(criteria);
				criteria.setKey("mail.server.port");
				AmKeyValueExt extPort = parameterService.getAmKeyValueExt(criteria);
				criteria.setKey("email.admin");
				AmKeyValueExt extMailAdmin = parameterService.getAmKeyValueExt(criteria);
				String[] dest = {request.getParameter("email")};
				
				try {
					boolean esito = send.sendMail(extServer.getValueConf(), extPort.getValueConf(), dest, oggetto, messaggio, extMailAdmin.getValueConf());
					if(esito)
						return "Invio Password all'utente avvenuto correttamente";
				} catch (MessagingException e) {
					logger.error(e.getMessage(),e);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return "Invio Password all'utente fallito";
	}
	
	public void salvaDatiUfficio(HttpServletRequest request) throws Exception{
		
		try{
		
		userService.saveDatiUfficio(BaseAction.fillDatiUfficio(request));
		
		// salvataggio OK
		request.setAttribute("salvaOk","Aggiornamento dati avvenuto correttamente");
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
}