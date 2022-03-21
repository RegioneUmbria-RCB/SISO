<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ taglib uri="/WEB-INF/displaytag-11.tld" prefix="display"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

	      <tr>
          <td align="right" >Direzione*</td>
            <td align="left" >
            	<%if (request.getAttribute("direzione") != null)  {%>
            		<input type="text" name="direzione"  value="<%=request.getAttribute("direzione")%>">
            	<%} else {%>
            		<input type="text" name="direzione" >
            	<%}%>
            </td>
        
          <td align="right" >Settore*</td>
            <td align="left" >
            	<%if (request.getAttribute("settore") != null)  {%>
            		<input type="text" name="settore"  value="<%=request.getAttribute("settore")%>">
            	<%} else {%>
            		<input type="text" name="settore">
            	<%}%>
            </td>
          </tr>
            <tr>
          <td align="right" >Telefono Ufficio*</td>
            <td align="left" >
            	<%if (request.getAttribute("telUfficio") != null)  {%>
            		<input type="text" name="telUfficio"  value="<%=request.getAttribute("telUfficio")%>">
            	<%} else {%>
            		<input type="text" name="telUfficio" >
            	<%}%>
            </td>
          
          <td align="right" >E-mail Ufficio*</td>
            <td align="left" >
            	<%if (request.getAttribute("emailUfficio") != null)  {%>
            		<input type="text" name="emailUfficio"  value="<%=request.getAttribute("emailUfficio")%>">
            	<%} else {%>
            		<input type="text" name="emailUfficio" >
            	<%}%>
            </td>
          </tr>