package it.webred.cet.permission;

import it.webred.amprofiler.ejb.perm.LoginBeanService;

import java.io.IOException;
import java.security.Principal;

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

import org.apache.log4j.Logger;

public class PwdValidationFilter implements Filter {

	protected Logger logger = Logger.getLogger("audit.log");
    private Context ctx = null;
    FilterConfig conf = null;
    
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession(false);
		Boolean validata = (Boolean) session.getAttribute("pwdValida");
		
		if (session != null && validata != null && validata.equals(new Boolean(true))) {
			chain.doFilter(req, resp);
			return;
		}
		
		Principal princ = request.getUserPrincipal();
		
		try {
			logger.debug("PwdValidationFilter - call isPwdValida "+ getCurrentUrlFromRequest((HttpServletRequest)req));
			int numGiorniVal = Integer.parseInt(conf.getInitParameter("numGiorniVal") == null ? "90" : conf.getInitParameter("numGiorniVal"));
			
		    LoginBeanService service = (LoginBeanService) ctx.lookup("java:global/AmProfiler/AmProfilerEjb/LoginBean");
		    Boolean pwdValida = service.isPwdValida(princ.getName(), numGiorniVal);  
		    session.setAttribute("pwdValida", pwdValida);
		    
			if (pwdValida) {
				chain.doFilter(req, resp);
			} else {
				String paginaCambioPwd = "/AMProfiler/SalvaNuovaPassword?pwdScaduta=true&userName=" + princ.getName() + "&pathApp=" + ((HttpServletRequest)req).getContextPath();
				((HttpServletResponse)resp).sendRedirect(paginaCambioPwd);
			}
		} catch (NamingException e) {	    
		    e.printStackTrace();	    
		}
	
    }

    @Override
    public void init(FilterConfig conf) throws ServletException {
	try {
	    ctx = new InitialContext();
	    this.conf = conf;
	} catch (NamingException e) {	    
	    e.printStackTrace();
	}

    }
    
	private String getCurrentUrlFromRequest(HttpServletRequest request)
	{
	    StringBuffer requestURL = request.getRequestURL();
	    String queryString = request.getQueryString();

	    if (queryString == null)
	        return requestURL.toString();

	    return requestURL.append('?').append(queryString).toString();
	}

}
