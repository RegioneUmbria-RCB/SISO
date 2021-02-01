<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ taglib uri="/WEB-INF/displaytag-11.tld" prefix="display"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

          <tr>
	            <th align="center" colspan="4">Gestione accesso</th>
	      </tr>
	      <tr>
          <td align="right" colspan="2">Indirizzi IP fidati*</td>
            <td align="left" colspan="2">
            	<%if (request.getAttribute("ipFidati") != null)  {%>
            		<input type="text" name="ipFidati"  value="<%=request.getAttribute("ipFidati")%>">
            	<%} else {%>
            		<input type="text" name="ipFidati" >
            	<%}%>
            </td>
          </tr>
          
          <tr>
          <td align="right" colspan="2">Orario consentito</td>
            <td align="left" colspan="2">
            	da
            	<select NAME="daOra">
					<option value="">--</option>
					<c:forEach items="${ore}" var="ora">
						<c:if test="${daOra == ora}">
							<option selected><c:out value="${ora}" /></option>
						</c:if>
						<c:if test="${daOra != ora}">
							<option><c:out value="${ora}" /></option>
						</c:if>
					</c:forEach>
				</select>
				:
				<select NAME="daMinuto">
					<option value="">--</option>
					<c:forEach items="${minuti}" var="minuto">
						<c:if test="${daMinuto == minuto}">
							<option selected><c:out value="${minuto}" /></option>
						</c:if>
						<c:if test="${daMinuto != minuto}">
							<option><c:out value="${minuto}" /></option>
						</c:if>
					</c:forEach>
				</select>
            	a
            	<select NAME="aOra">
					<option value="">--</option>
					<c:forEach items="${ore}" var="ora">
						<c:if test="${aOra == ora}">
							<option selected><c:out value="${ora}" /></option>
						</c:if>
						<c:if test="${aOra != ora}">
							<option><c:out value="${ora}" /></option>
						</c:if>
					</c:forEach>
				</select>
				:
				<select NAME="aMinuto">
					<option value="">--</option>
					<c:forEach items="${minuti}" var="minuto">
						<c:if test="${aMinuto == minuto}">
							<option selected><c:out value="${minuto}" /></option>
						</c:if>
						<c:if test="${aMinuto != minuto}">
							<option><c:out value="${minuto}" /></option>
						</c:if>
					</c:forEach>
				</select>
            </td>
          </tr>
          <tr>
          	<td align="center" colspan="4">*Es. 1.1.1.1 Es. 1.1.1.* Es. 1.1.1.1,1.1.1.2</td>
          </tr>
        
          