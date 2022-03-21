<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ taglib uri="/WEB-INF/displaytag-11.tld" prefix="display"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.webred.AMProfiler.beans.AmComune"%>

<html>
	<head>
	  	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	  	<link rel="icon" href="favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
	  	<link href="<%=request.getContextPath()%>/css/newstyle.css" rel="stylesheet" type="text/css" />
	  	<title>Utente disabilitato</title>
	</head>
	
	<body>
	
		<table width="100%" cellpadding="0" cellspacing="0" align="center" border="0" height="50" style="border-bottom:6px solid #4A75B5;">    		
	  		<tr>
	 			<td style="text-align: center;">
	 				<img src="img/git.jpg" width="165" height="43" style="padding: 5px 10px 5px 0px; vertical-align: middle;"/>
	 				<font size='5' color="#4A75B5" style="font-weight: bold; vertical-align: middle;">Login</font>
	 			</td>
	 		</tr>
	    </table>
	    
	    <div align="center" style="margin-top: 50px">
	      <font size='4' color='red'>
	        	ATTENZIONE!
	      </font>
	      <br/><br/>
	       <font size='4'>
	        	L'utente <span style="color: red"><%=request.getUserPrincipal().getName()%></span> è disabilitato.
	      </font>
	      <br/><br/>
	       <font size='4'>
	        	Motivo: <span style="color: red"><%=request.getAttribute("disableCause").toString().toUpperCase()%></span>
	      </font>
	      <br/><br/>
	       <font size='4'>
	        	Contattare l'Amministratore del sistema.
	      </font>
	      <br/><br/><br/><br/>
	      <font size='4' color='red'>
	      		<a href='<%=request.getContextPath()%>/Logout'>Cambia utente</a>
	      </font>	      
	    </div>
    	
	</body>
</html>
