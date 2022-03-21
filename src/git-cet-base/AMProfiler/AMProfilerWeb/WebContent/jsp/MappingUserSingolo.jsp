<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ taglib uri="/WEB-INF/displaytag-11.tld" prefix="display"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="it.webred.AMProfiler.beans.CodificaPermessi"%>
<%@page import="it.webred.AMProfiler.servlet.BaseAction"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet,org.displaytag.tags.TableTag"%>
<%@page import="org.apache.commons.beanutils.RowSetDynaClass"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.webred.permessi.GestionePermessi"%>
<%@page import="it.webred.permessi.AuthContext"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="it.webred.AMProfiler.servlet.SalvaUtente"%>
<%@page import="it.webred.AMProfiler.servlet.CaricaUtenti"%>
<html>
	<script type="text/javascript" src="js/date_picker.js"></script>
	<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  	<link href="<%=request.getContextPath()%>/css/newstyle.css" rel="stylesheet" type="text/css" />
  	<title>AMProfiler - Mapping Utente</title>
  	<%String pathWebApp = request.getContextPath(); %>
	</head>
	
	<body>
		<script>
		</script>		
		
		<div id="centrecontent"><!--centre content goes here --> 
			<br>
			<form name="searchForm" method="POST" action="<%=pathWebApp%>/MappingUserSingolo?mode=ricerca" >
		 		
		 		<table width="90%" cellpadding="0" cellspacing="0" align="center" class="clean">
		  		   <tr class="header">
						<td align="center" colspan="4">Filtro di ricerca</td>
			       </tr>
		  		   <tr>
		  		   		<td align="right">Nome</td>
		  			  	<td align="left"><input type="text" name="ricNome"/></td>
		  			  	
		  			  	<td align="right">Cognome</td>
		  			  	<td align="left"><input type="text" name="ricCognome"/></td>
		  		   </tr>
		  		   <tr>
		  		   		<td align="right">User</td>
		  			  	<td align="left"><input type="text" name="ricUser"/></td>
		  			  	
		  			  	<td align="right">Mail</td>
		  			  	<td align="left"><input type="text" name="ricMail"/></td>
		  		   </tr>
		  		   <tr>
		  		   	  <td><br/></td>
		  		   	  <td align="left">
		  		   	  	<input type="submit" name="submit" value="Invio">
		  		   	  </td>
		  		   </tr>
				</table>
				
			</form>	
			<form name="mappingForm" method="POST" >	
		 		
				<div align="center" style="padding-left: 10px">
			    <display:table id="row" name="${ldapList}"  class="griglia">
			    	<display:column title="User Principal">
						<c:out value="${row.principal}"></c:out>
					</display:column>
					<display:column title="User">
						<c:out value="${row.user}"></c:out>
					</display:column>
					<display:column title="Nominativo">
						<c:out value="${row.nominativo}"></c:out>
					</display:column>
					<display:column title="Mail">
						<c:out value="${row.mail}"></c:out>
					</display:column>
			      
					<display:column title="Associa">
						<a class="imgLnk" href="<%=pathWebApp%>/MappingUserSingolo?mode=assocSingle&user=${row.principal}" >  
							<img class="lnkImg" src="<%=pathWebApp%>/img/services.png" title="Associa" />Associa
						</a>
					</display:column>
			    
			    </display:table>
 		 		</div>
 		 		<br>
 		 		<a class="imgLnk" href="<%=pathWebApp%>/MappingUsers?mode=back" >Torna a Mapping Utente</a>
			</form>
		</div>
	</body>
</html>
