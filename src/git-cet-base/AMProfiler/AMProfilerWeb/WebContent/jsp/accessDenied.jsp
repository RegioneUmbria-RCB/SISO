<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ taglib uri="/WEB-INF/displaytag-11.tld" prefix="display"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html>
	<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  	<link href="<%=request.getContextPath()%>/css/newstyle.css" rel="stylesheet" type="text/css" />
  	<title>Access Denied</title>
	</head>
	
	<body>
	<table width="100%" cellpadding="0" cellspacing="0" align="center" border="0" height="50" style="border-bottom:6px solid #4A75B5;">
    		<tr><td><font size='5' color="#4A75B5" style="font-weight: bold;padding-left: 5px;">Accesso negato</font></td></tr>
    </table>
    <br><br>
	

		<div align="center"><!--centre content goes here --> 
			<c:if test="${ipCurrent!=null}">
					<br />
					<font color="red" style="font-size: 15px; font-weight: bold;">
						<c:out value="L'ip che si sta usando: ${ipCurrent}, non è previsto in quelli autorizzati: ${ipOk}"/>
					</font>
			</c:if>
			<c:if test="${oraDa!=null}">
					<br />
					<font color="red" style="font-size: 15px; font-weight: bold;">
						<c:out value="L'accesso per la sua utenza è consentito solo dalle ${oraDa} alle ${oraA}"/>
					</font>
			</c:if>
		</div>
	</body>
</html>
