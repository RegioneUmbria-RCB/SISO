<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link href="./css/newstyle.css" rel="stylesheet" type="text/css" />
  <title>AMProfiler - Utenti per Ruolo</title>
</head>

<body>
  <div id="centrecontent"><!--centre content goes here --> 
    <br>
  
    <form name="userForm" method="POST" action="SalvaUtente" >
      <c:if test="${pwdError}">
        <font color="red">
           Attenzione. &nbsp; La conferma della nuova password non &egrave; valida: la <b>password</b> e la <b>conferma password</b> devono coincidere.
        </font>
      </c:if> 

      <c:if test="${salvaOk!=null}">
        <font color="red">
          <c:out value="${salvaOk}. Per tornare all'applicazione, premere il pulsante Home."/>
        </font>
      </c:if>
    
      <table class="griglia"  cellpadding="0" cellspacing="0">
        <tr class="header" >
        	<td width="150 px" colspan="3" >ASSEGNAZIONE RUOLI UTENTE</td>
        </tr>
        <tr class="header">
          <td width="150 px">Utenti</td>
          <td width="150 px">Ruoli</td>
          <td width="100 px">&nbsp;</td>
        </tr>
        <tr>
        <td>
          <select NAME="rules"  >
        	  <c:forEach var="rule" items="${rules}">
        		    <option value=" <c:out value="${rule.id}" />">
        		      <c:out value="${rule.amRoleS}" />
        		    </option>
        
        	  </c:forEach>
          </select>
        </td>
      	<td>
      	  <c:forEach var="userInRule" items="${users}">
            <c:out value="${user.name}" /><br>
      	  </c:forEach>
        </td>
        <td width="100 px">
          Selezionare i ruoli da assegnare ad un utente relativamente ad un oggetto 
          <input type="button" name="salvaRuoli" value="Salva" onclick="document.permessiForm.action='SalvaRuoliUtente';document.permessiForm.submit()"/>
        </td>
        </tr>
      </table>
    </form>
  </div>

  <c:if test="${giaPresente}">
    alert("Nome utente già presente!")
  </c:if>
</body>
</html>
