<%@page import="javax.faces.webapp.FacesServlet"%>
<%@page import="it.umbriadigitale.soclav.managedbeans.AutenticazioneBean"%>
<%@page import="org.omnifaces.util.Faces"%>
<%@ page import="it.umbriadigitale.soclav.util.Token, it.umbriadigitale.soclav.util.TokenClaim,
it.umbriadigitale.soclav.util.HttpRestClient, java.util.HashMap, it.umbriadigitale.soclav.managedbeans.AutenticazioneBean"
 %>
<%

	String url = request.getContextPath();
	String tokenP = request.getParameter("token");
	
	if(tokenP == null || tokenP.equals(""))
			url += "/jsp/public/AccessoNegato.xhtml";	
	else{
			 
		  session.setAttribute("token", tokenP);
		 url += "/login.xhtml";
	}
	
	
	response.sendRedirect(url );
%>
 
 