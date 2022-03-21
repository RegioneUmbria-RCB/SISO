<%@page import="it.marche.regione.pddnica.client.*"%>
<%@page import="it.marche.regione.pddnica.client.RicercaCF"%>
<%@page import="it.marche.regione.pddnica.client.DatiRichiestaISEE"%>

 <%@page import="it.siso.isee.obj.*"%>
<%@page import="java.util.ArrayList"%>
 

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Attendere..</title>
</head>
<body>
	<%
	
	String tipoOp = request.getParameter("p_tipoOp");
	 if(tipoOp.equals("ATT")){
		 RequestDispatcher rd = request.getRequestDispatcher("/JSP2.jsp");
		    rd.forward(request, response);
	 }
		
 
	%>  
</body>
</html>