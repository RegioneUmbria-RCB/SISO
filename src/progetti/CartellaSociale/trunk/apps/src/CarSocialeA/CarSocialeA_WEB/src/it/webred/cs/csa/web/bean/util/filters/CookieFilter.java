package it.webred.cs.csa.web.bean.util.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.primefaces.util.Constants;

public class CookieFilter implements Filter{

	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		  
		ResponseCookieWrapper wrapper = new ResponseCookieWrapper((HttpServletRequest)request, (HttpServletResponse)response);
        // create a response wrapper to capture the original response
          
        // allow the request to reach the target page,
        // but the response is written to the wrapper
         
        chain.doFilter(request, wrapper);
          
        // modify the response
        // write the altered content to the response
    }
	
	public class ResponseCookieWrapper extends HttpServletResponseWrapper {
	
	private final HttpServletRequest request;
	
	public ResponseCookieWrapper(HttpServletRequest request, HttpServletResponse response) {
	    super(response);
	    this.request = request;
	
	}
	
	@Override
	public void addCookie(Cookie cookie) {
	    if(Constants.DOWNLOAD_COOKIE.equals(cookie.getName())){
	        String refererHeader = request.getHeader("Referer");
	
	        String contextPath = request.getContextPath();          
	        String requestURI = request.getRequestURI();
	
	        String refererURI = refererHeader.substring(refererHeader.indexOf(contextPath));
	        if(!requestURI.equals(refererURI)){
	            String refererPath = refererURI.substring(0, refererURI.lastIndexOf("/"));
	            System.out.println("Aggiorno il percorso: [nuovo:"+refererPath+"][vecchio:"+requestURI+"]");
	            cookie.setPath(refererPath);
	           
	        }
	        System.out.println(Constants.DOWNLOAD_COOKIE+"Sto impostando HTTP_ONLY=false per accederlo da JS");
	        cookie.setHttpOnly(false);
	        //cookie.setSecure(true);
	    }
	    super.addCookie(cookie);
	}
	}

	
	
	
	@Override
	public void destroy() {
		
		
	}


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	
	}
}