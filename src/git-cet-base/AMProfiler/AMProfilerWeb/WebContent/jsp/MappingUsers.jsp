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
	<style type="text/css">
		.right {
		    text-align: right;
		}
	</style>
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
			<form name="mappingForm" method="POST" action="<%=pathWebApp%>/MappingUsers?mode=assoc" >
		 		
				<div align="center" style="padding-left: 10px">
				
				<c:choose>
					<c:when test="${empty userList}">
						<br>
						<br>
						Sono starti trovati <c:out value="${nUserTot}"></c:out> utenti GIT di cui <c:out value="${nUserToAssoc}"></c:out> non hanno associato l'utente Ldap.
						<br>
						<br>
						<b></b><a class="imgLnk" href="<%=pathWebApp%>/MappingUsers?mode=findLdap" style="text-decoration: none">Trova utenti Ldap</a></b>
						
					</c:when>
					<c:otherwise>
						
						<table >
							<tr style="background-color: white">
								<td class="right"><input type="submit" name="submit" value="Associa"></td>
							</tr>
							<tr>
								<td>
									<display:table id="row" name="${userList}"  class="griglia">
								      <display:column title="User Git">
								      	<c:out value="${row.gitUserName}"></c:out>
								      </display:column>
								      <display:column title="User Ldap">
								      	<c:out value="${row.ldpUserName}"></c:out>
								      </display:column>
								      <display:column title="Nominativo">
								     	<c:out value="${row.nominativo}"></c:out>
								      </display:column>
								      <display:column title="Mail">
								     	<c:out value="${row.mail}"></c:out>
								      </display:column>
								      
								  		<display:column title="Gestione">
									  		<c:choose>
											  <c:when test="${row.mapped}">
											    <img class="lnkImg" src="<%=pathWebApp%>/img/sem_verde.gif" title="Utente mappato"/>
											    <a class="imgLnk" href="<%=pathWebApp%>/MappingUserSingolo?mode=ricerca&forUser=${row.gitUserName}" style="text-decoration: none">  
								  					<img class="lnkImg" src="<%=pathWebApp%>/img/edit.gif" title="ricerca"/>
								  				</a>
											  </c:when>
											  <c:otherwise>
												<c:choose>
													<c:when test="${row.mappable}">
														<img class="lnkImg" src="<%=pathWebApp%>/img/sem_giallo.gif" title="Utente con possibile associazione"/>
														<a class="imgLnk" href="<%=pathWebApp%>/MappingUserSingolo?mode=ricerca&forUser=${row.gitUserName}" style="text-decoration: none">  
										  					<img class="lnkImg" src="<%=pathWebApp%>/img/edit.gif" title="ricerca"/>
										  				</a>
														<input name="${row.gitUserName}" type="checkbox"  title="Spunta per inserirlo nel gruppo di quelli che verranno associati">
													</c:when>
													<c:otherwise>
														<img class="lnkImg" src="<%=pathWebApp%>/img/sem_rosso.gif" title="Nessun Utente Ldap trovato"/>
														<a class="imgLnk" href="<%=pathWebApp%>/MappingUserSingolo?mode=ricerca&forUser=${row.gitUserName}" style="text-decoration: none">  
										  					<img class="lnkImg" src="<%=pathWebApp%>/img/edit.gif" title="ricerca"/>
									  					</a>
													</c:otherwise>
												</c:choose>
											  </c:otherwise>
											</c:choose>
								  		</display:column>
								    </display:table>
								</td>
							</tr>
							<tr style="background-color: white">
								<td class="right"><input type="submit" name="submit" value="Associa"></td>
							</tr>
						</table>
		 		 		
					</c:otherwise>
				</c:choose>	  
				</div>	  
				
			</form>
		</div>
		
	</body>
</html>
