<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="./css/newstyle.css" rel="stylesheet" type="text/css" />
	<title>AMProfiler - Gestione Gruppi</title>
  	<%String pathWebApp = request.getContextPath(); %>
	</head>
	
	<body>

		<div id="centrecontent"><!--centre content goes here --> 
			<br>
		<form action="<%=pathWebApp%>/NuovoGruppo" name="groupForm" method="post">

		<table width="90%" border="0" cellpadding="0" cellspacing="0" align="center" class="clean">
		  <tr>
		  <th align="center" colspan="3">Ente
            	<select NAME="ente" onchange="document.groupForm.submit();">
            	  <option value="">-- Seleziona --</option>
                  <c:forEach var="comune" items="${comuni}">
                      <c:if test="${ente == comune.belfiore}">
	                      <option value="<c:out value="${comune.belfiore}"/>" selected>
	                        <c:out value="${comune.descrizione}" />
	                      </option>
                      </c:if>
                      <c:if test="${ente != comune.belfiore}">
	                      <option value="<c:out value="${comune.belfiore}"/>">
	                        <c:out value="${comune.descrizione}" />
	                      </option>
                      </c:if>
                  </c:forEach>
                </select>
            </th>
          </tr>
		  <tr>
            <th align="center" colspan="2">Modifica</th>
            <th align="center">Utenti</th>
          </tr>
          <tr>
            <td align="right">Gruppo</td>
            <td>
            	<select NAME="gruppo" onchange="document.groupForm.submit()">
                  <option value="">-- Seleziona --</option>
                  <c:forEach var="grp" items="${gruppiEnte}">
                      <c:if test="${gruppo == grp.name}">
	                      <option value="<c:out value="${grp.name}"/>" selected>
	                        <c:out value="${grp.name}" />
	                      </option>
                      </c:if>
                      <c:if test="${gruppo != grp.name}">
	                      <option value="<c:out value="${grp.name}"/>">
	                        <c:out value="${grp.name}" />
	                      </option>
                      </c:if>
                  </c:forEach>
                </select>
            </td>
            <td rowspan="10">
                
                <div style="overflow:scroll;height:300px;width:200px;">
					<c:forEach var="utente" items="${utentiGruppi}">
							<c:if test="${utente.checked == true}">
								<input type="checkbox" checked="checked" name="utenti" value="<c:out value="${utente.name}" />"/>
							</c:if>
							<c:if test="${utente.checked != true}">
								<input type="checkbox" name="utenti" value="<c:out value="${utente.name}" />"/>
							</c:if>
							<c:out value="${utente.name}" />
							<br />
					</c:forEach>
				</div>
            </td>
          </tr>
          <tr>
            <th align="center" colspan="2">Nuovo</th>
          </tr>
          <tr>
            <td align="right">Descrizione</td>
            <td><input id="nuovoGruppo" type="text" name="nuovoGruppo" /></td>
          </tr>
          <tr><td><br /></td></tr>
          <tr><td><br /></td></tr>
          <tr><td><br /></td></tr>
          <tr><td><br /></td></tr>
          <tr><td><br /></td></tr>
          <tr><td><br /></td></tr>
          <tr><td><br /></td></tr>
          
          <jsp:include page="frgGestioneAccesso.jsp" />

          <tr>
		      <th align="center" colspan="3">
	      		<input type="submit" name="salva" value="Salva">
		      	<input type="button" value="Chiudi" onclick="window.close();">
		      </th>
		  </tr>
       </table>

          <c:if test="${msgNuovoGruppo != null}">
             <font color="red" style="font-size: 12px; font-weight: bold;">
               <c:out value="${msgNuovoGruppo}"></c:out>
             </font>
          </c:if>
          <c:if test="${timeError!=null}">
			<br />
			<font color="red" style="font-size: 15px; font-weight: bold;">
				<c:out value="${timeError}"/>
			</font>
		</c:if> 

        <input name="urlReferer" type="hidden" value="${urlReferer}"></input>
		<input name="gruppo" type="hidden" value='<c:out value="${gruppo}"/>'></input>
		<input name="ente" type="hidden" value='<c:out value="${ente}"/>'></input>
		</form>
		</div>
	</body>
</html>
