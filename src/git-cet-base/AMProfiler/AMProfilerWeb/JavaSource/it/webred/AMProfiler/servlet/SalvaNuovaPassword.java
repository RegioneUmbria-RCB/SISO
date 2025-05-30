package it.webred.AMProfiler.servlet;

import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUser;
import it.webred.amprofiler.model.AmUserUfficio;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
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
public class SalvaNuovaPassword extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/ParameterBaseService")
	protected ParameterService parameterService;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/AnagraficaServiceBean")
	protected AnagraficaService anagraficaService;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/UserServiceBean")
	protected UserService userService;
	
	protected static Logger logger = Logger.getLogger("am.log");
	
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public SalvaNuovaPassword() {
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
		try {
			String mode = request.getParameter("mode");
			String userName = request.getParameter("userName");

			request.setAttribute("mode", mode);
			request.setAttribute("showPwd", true);
			request.setAttribute("showGroup", !"pwd".equals(mode));
			request.setAttribute("uN", userName);

			if ("Salva".equals(request.getParameter("submit"))) {
					
					// Salva utente
					String password = request.getParameter("password");
					String password2= request.getParameter("password2");

					if( StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(password2) && validateDatiUtente(request) ) {
						BaseAction.SalvaUtente(request);
					}
					
					//Salva Email
					if(validateEmail(request))
						BaseAction.UpdateEmail(request); 
					
					// Salva Anagrafica
					if(BaseAction.validateDatiAnagrafica(request, userName))
						anagraficaService.updateAnagrafica(fillAnagrafica(request));
					
					if(BaseAction.validateDatiUfficio(request, userName))
						this.salvaDatiUfficio(request);
					

			}
				
			//Carica Anagrafica
			AmAnagrafica anagrafica = anagraficaService.findAnagraficaByUserName(userName);
			request.setAttribute("email", anagrafica.getAmUser().getEmail());
			setAnagraficaAttribute(request, anagrafica);
			
			//Carica Ufficio
			AmUserUfficio ufficio = userService.getDatiUfficio(userName);
		
			request.setAttribute("direzione", ufficio.getDirezione());
			request.setAttribute("settore", ufficio.getSettore());
			request.setAttribute("emailUfficio", ufficio.getEmail());
			request.setAttribute("telUfficio", ufficio.getTelefono());

			request.getRequestDispatcher("/jsp/updPassword.jsp").forward(request, response);
		}
		catch (Exception e) {
			if (e.toString().indexOf("ORA-00001") == -1) {
				BaseAction.toErrorPage(request, response, e);
			}
			else {
				request.setAttribute("giaPresente", "true");
				request.getRequestDispatcher("/jsp/updPassword.jsp").forward(request, response);
			}
		}
		finally {
			BaseAction.chiudiConnessione(con);
		}

	}

	private boolean validateDatiUtente(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String oldpassword = request.getParameter("oldpassword");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String email = request.getParameter("email");

		boolean stato = true;

		if (userName == null || userName.trim().equals("")) {
			request.setAttribute("uN", "");
			stato = false;
		}

		if (oldpassword.length() == 0) {
			request.setAttribute("uN", userName);
			request.setAttribute("noOldPwd", "true");
			stato = false;
		}
		if (password.length() == 0) {
			request.setAttribute("uN", userName);
			request.setAttribute("noPwd", "true");
			stato = false;
		}
		if (!password.equals(password2)) {
			request.setAttribute("uN", userName);
			request.setAttribute("pwdError", "true");
			stato = false;
		}
		
		try {
			final MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(oldpassword.getBytes());
			final byte[] digest = md.digest();
			String hexOldPassword = BaseAction.toHexString(digest);
			AmUser amUser = userService.getUserByName(userName);
			String ctrlPassword = amUser == null ? "" : amUser.getPwd();
			if (!ctrlPassword.equals(hexOldPassword)) {
				request.setAttribute("uN", userName);
				request.setAttribute("badOldPwd", "true");
				stato = false;
			} 
		} catch (Exception e) {
			logger.error("Eccezione in fase di controllo della password attuale: " + e.getMessage());
			request.setAttribute("uN", userName);
			request.setAttribute("errOldPwd", "true");
			stato = false;
		}
		
		String livSicurCredenz = "A"; //default
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey("livello.sicurezza.credenziali");
		AmKeyValueExt livSicurCredenzKey = parameterService.getAmKeyValueExt(criteria);
		if (livSicurCredenzKey != null && livSicurCredenzKey.getValueConf() != null) {
			String myKeyValue = livSicurCredenzKey.getValueConf().toUpperCase();
			if (myKeyValue.equals("C") || (myKeyValue.equals("B") && !livSicurCredenz.equals("C"))) {
				livSicurCredenz = myKeyValue;
			}
		}
		
		boolean doCtrls = livSicurCredenz != null && (livSicurCredenz.toUpperCase().equals("B") || livSicurCredenz.toUpperCase().equals("C"));
		if (doCtrls) {
			if (password.length() < 8) {
				request.setAttribute("uN", userName);
				request.setAttribute("lenPwdError", "true");
				stato = false;
			}
			
			if (livSicurCredenz.toUpperCase().equals("C")) {
				String ctrlError = doCLevelCtrls(oldpassword, password);
				if (ctrlError != null && !ctrlError.equals("")) {
					request.setAttribute("uN", userName);
					request.setAttribute(ctrlError, "true");
					stato = false;
				}
			}
		}
		
		if (email.length() == 0) {
			stato = false;
		}
		
		return stato;
	}
	
	private String doCLevelCtrls(String oldpassword, String password) {		
		if (password.equals(password.toUpperCase()) || password.equals(password.toLowerCase())) {
			return "carNumUppLowError";
		}
		
		boolean okCarSpec = false;
		for (char car : password.toCharArray()) {
			if (" `!\"?$?%^&*()_-+={[}]:;@'~#|\\<,>.?/".indexOf(car) > -1) {
				okCarSpec = true;
				break;
			}
		}
		if (!okCarSpec) {
			return "carNumUppLowError";
		}
		
		boolean okNum = false;
		for (char car : password.toCharArray()) {
			if ("0123456789".indexOf(car) > -1) {
				okNum = true;
				break;
			}
		}
		if (!okNum) {
			return "carNumUppLowError";
		}
		
		int contaDiff = password.length() - oldpassword.length();
		if (contaDiff > -3 || contaDiff < 3) {
			String ctrlPassword = new String(password);
			String ctrlOldPassword = new String(oldpassword);
			if (contaDiff < 0) {
				contaDiff = -(contaDiff);
				ctrlOldPassword = ctrlOldPassword.substring(0, ctrlPassword.length());
			} else {
				ctrlPassword = ctrlPassword.substring(0, ctrlOldPassword.length());
			}
			for (int i = 0; i < ctrlPassword.length(); i++) {
				if (ctrlPassword.charAt(i) != ctrlOldPassword.charAt(i)) {
					contaDiff++;
				}
			}
		}
		if (contaDiff < 3) {
			return "diffError";
		}
		
		char myChar = 0;
		int repCount = 1;
		for (char car : password.toCharArray()) {
			if (car != myChar) {
				myChar = car;
				repCount = 1;
			} else {
				repCount++;
			}
			if (repCount > 2) {
				return "repCharError";
			}
		}		
		
		return "";
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
	
	
	private AmAnagrafica fillAnagrafica(HttpServletRequest request) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		AmAnagrafica ana = new AmAnagrafica();
		
		try {
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
			//solo update anagrafica
			ana.setId(Integer.parseInt(idAnagrafica));
			String dtIns = request.getParameter("dtInsAna");
			if(!"".equals(dtIns))
				ana.setDtIns(sdf.parse(dtIns));
			String usrIns = request.getParameter("usrInsAna");
			if(!"".equals(usrIns))
				ana.setUserIns(usrIns);
			ana.setDtUpd(new Date());
			ana.setUserUpd(request.getUserPrincipal().getName());
			String dataNascita = request.getParameter("dtNascita");
			if(!"".equals(dataNascita))
				ana.setDataNascita(sdf.parse(dataNascita));
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
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