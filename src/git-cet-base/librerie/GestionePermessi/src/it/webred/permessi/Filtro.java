package it.webred.permessi;

import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Filtro implements Filter {

	FilterConfig conf = null;
	
	public void init(FilterConfig conf) throws ServletException {
		this.conf = conf;
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {		
		
		Connection conn = null;
		try {
			
			Principal user = ((HttpServletRequest)req).getUserPrincipal();
			if (user == null) {
				chain.doFilter(req, res);
				return;
			}
			int numGiorniVal = Integer.parseInt(conf.getInitParameter("numGiorniVal") == null ? "90" : conf.getInitParameter("numGiorniVal"));
			
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/AMProfiler");
			conn = ds.getConnection();
			
			AuthContext authContext = new AuthContext(user, conn);
			Object[] userPwdValida = GestionePermessi.pwdValida(authContext, numGiorniVal);
			boolean pwdValida = ((Boolean)userPwdValida[1]).booleanValue();
			
			String paginaCambioPwd = conf.getInitParameter("paginaCambioPwd") + "&userName=" + userPwdValida[0] + "&pathApp=" + ((HttpServletRequest)req).getContextPath();
			
			if (pwdValida) {
				chain.doFilter(req, res);
			} else {
				((HttpServletResponse)res).sendRedirect(paginaCambioPwd);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}				
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}			
		}
		
	}

	public void destroy() {
		this.conf = null;
	}

}
