package it.webred.cet.permission;

import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.amprofiler.model.AmGroup;
import it.webred.amprofiler.model.perm.PermAccesso;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.misc.BASE64Decoder;

public class PermissionFilter implements Filter {

	private Context ctx = null;
	private static final String noAccessIP = "NO_IP";
	private static final String noAccessOrario = "NO_ORARIO";

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession(false);
		
		String es = request.getParameter("es");
		String enteDefault = null;
		if(es != null && !es.equals("")){
			BASE64Decoder dec = new BASE64Decoder();
			byte[] val = dec.decodeBuffer(es);
			enteDefault = new String(val);
		}
		
		try {
			LoginBeanService service = (LoginBeanService) ctx
					.lookup("java:global/AmProfiler/AmProfilerEjb/LoginBean");
		
			if (session != null && session.getAttribute("user") != null) {

				CeTUser user = (CeTUser) session.getAttribute("user");

				if (es != null && !es.equals("")) {
					
					System.out.println("Ente corrente ["+enteDefault+"]");
					user.setCurrentEnte(enteDefault);
					HashMap<String, String> permList = service.getPermissions(user.getName(), enteDefault);
					user.setPermList(permList);
					//recupero dati limitazione accesso
					PermAccesso accesso = service.getPermissionAccesso(user.getName(), enteDefault);
					if(accesso.getId() != null && accesso.getIpRange() != null)
						user.setPermRangeIp(Arrays.asList(accesso.getIpRange().split(",")));
					if(accesso.getId() != null && accesso.getOraDa() != null && accesso.getOraA() != null){
						user.setPermOraDa(accesso.getOraDa());
						user.setPermOraA(accesso.getOraA());
					}
					List<AmGroup> listaGruppi = service.getGruppi(user.getName(), enteDefault);
					user.setGroupList(listaGruppi);
				}
				
				String esitoAccesso = controlloAccessoLimitato(req, user);
				if(esitoAccesso.equals(noAccessIP)){
					String ipOk="";
					for(String ip: user.getPermRangeIp()){
						ipOk+=","+ip;
					}
					((HttpServletResponse) resp).sendRedirect("/AMProfiler/AccessDenied?" +
							"ipCurrent="+req.getRemoteAddr()+"&ipOk="+ipOk.substring(1));
				}
				if(esitoAccesso.equals(noAccessOrario))
					((HttpServletResponse) resp).sendRedirect("/AMProfiler/AccessDenied?" +
							"oraDa="+user.getPermOraDa()+"&oraA="+user.getPermOraA());
				
				chain.doFilter(req, resp);
				return;
			}
			// logger.info("Extracting user profile for user []");
			Principal princ = request.getUserPrincipal();
			String sessionId = session.getId();
			// logger.info("Extracting user profile for user ["+princ.getName()+"]");
			System.out.println("Extracting user profile for user ["
					+ princ.getName() + "]");

		
			List<String> enteList = service.getEnte(princ.getName());
			if(enteDefault == null)
				enteDefault = enteList.get(0);
			
			HashMap<String, String> permList = service.getPermissions(princ
					.getName(), enteDefault);
			
			List<AmGroup> listaGruppi = service.getGruppi(princ.getName(), enteDefault);

			System.out.println("Ente [" + enteList + "]");
			CeTUser user = new CeTUser();
			user.setUsername(princ.getName());
			user.setSessionId(sessionId);
			user.setPermList(permList);
			user.setEnteList(enteList);
			user.setCurrentEnte(enteDefault);
			user.setGroupList(listaGruppi);
			
			session.setAttribute("user", user);
			
		
			
			// logger.info("User ["+user+"]");
			System.out.println("-- User [" + user + "]");
			
			//aggiunta per passare comunque da AmProfiler e inserire ragione d'accesso e pratica lavorata
			//era solo per Diogene
			//boolean doInsPratica = ((HttpServletRequest) req).getRequestURL().toString().indexOf("/diogene/") > -1;
			//ora invece è per tutti gli applicativi
			boolean doInsPratica = true;
			//fine aggiunta
			
			if ((enteList.size() > 1 || doInsPratica) && es == null) {
				String parametersIn = request.getQueryString();
				if(parametersIn!=null && !parametersIn.equals("")){
					parametersIn = parametersIn.replaceAll("\\&", "|");
					parametersIn = parametersIn.replaceAll("=", "_");
					parametersIn = "myparam=" + parametersIn;
				}
				String paginaSceltaEnte = "/AMProfiler/SceltaEnte?userName="
						+ princ.getName() + "&pathApp="
						+ ((HttpServletRequest) req).getRequestURL().toString() + 
						(parametersIn!=null && !parametersIn.equals("")?"&"+parametersIn:"");
				((HttpServletResponse) resp).sendRedirect(paginaSceltaEnte);
			} else {
				//recupero dati limitazione accesso
				PermAccesso accesso = service.getPermissionAccesso(user.getName(), user.getCurrentEnte());
				if(accesso.getId() != null && accesso.getIpRange() != null)
					user.setPermRangeIp(Arrays.asList(accesso.getIpRange().split(",")));
				if(accesso.getId() != null && accesso.getOraDa() != null && accesso.getOraA() != null){
					user.setPermOraDa(accesso.getOraDa());
					user.setPermOraA(accesso.getOraA());
				}
				chain.doFilter(req, resp);
			}

		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	private String controlloAccessoLimitato(ServletRequest req, CeTUser user) {
		String ret = "ok";
		
		//controllo range ip
		if(user.getPermRangeIp() != null && user.getPermRangeIp().size()>0){
			ret = noAccessIP;
			String ipClient = req.getRemoteAddr();
			for(String ip: user.getPermRangeIp()){
				ip = ip.replace("*", "");
				if(ipClient.startsWith(ip)){
					ret = "ok";
					break;
				}
			}
		}
		//controllo orario
		if(ret.equals("ok") && user.getPermOraDa() != null && user.getPermOraA() != null){
			try {
				String[] oraDa = user.getPermOraDa().split(":");
				String[] oraA = user.getPermOraA().split(":");
				Calendar calNow = Calendar.getInstance();
				Calendar calDa = Calendar.getInstance();
				calDa.set(Calendar.HOUR_OF_DAY, new Integer(oraDa[0]));
				calDa.set(Calendar.MINUTE, new Integer(oraDa[1]));
				Calendar calA = Calendar.getInstance();
				calA.set(Calendar.HOUR_OF_DAY, new Integer(oraA[0]));
				calA.set(Calendar.MINUTE, new Integer(oraA[1]));
				if(calNow.getTimeInMillis() < calDa.getTimeInMillis() || calNow.getTimeInMillis() > calA.getTimeInMillis())
					ret = noAccessOrario;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

}
