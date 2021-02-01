<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ taglib uri="/WEB-INF/displaytag-11.tld" prefix="display"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html>

	<script type="text/javascript" src="js/date_picker.js"></script>
	<script type="text/javascript">
		var dp_cal,ms_cal;      
	        window.onload = function () {
		dp_cal  = new Epoch('epoch_popup','popup',document.getElementById('datepick'));
			
		};
	</script>

	<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  	<link href="<%=request.getContextPath()%>/css/newstyle.css" rel="stylesheet" type="text/css" />
  	<title>AMProfiler - update Password</title>
  	<%String pathWebApp = request.getContextPath(); %>
	</head>
	
	<body>	

		<div id="centrecontent"><!--centre content goes here --> 
			<form name="userForm" method="POST" action="<%=pathWebApp%>/SalvaNuovaPassword?mode=<%=request.getAttribute("mode")%>" >
				
				<table width="100%" cellpadding="0" cellspacing="0" align="center" border="0" height="50" style="border-bottom:6px solid #4A75B5;">
		    		<tr><td><font size='5' color="#4A75B5" style="font-weight: bold;padding-left: 5px;">Gestione password</font></td></tr>
		    	</table>
		    	<br><br>
				
				<c:if test="${noPwd}">
					<font color="red" style="font-size: 18px; font-weight: bold;">
	  				Attenzione. Nuova password non inserita.
	  			</font><br>
		 		</c:if> 

				<c:if test="${pwdError}">
					<font color="red" style="font-size: 18px; font-weight: bold;">
	  				Attenzione.&nbsp;La conferma della nuova password non &egrave; valida: la <b>password</b> e la <b>conferma password</b> devono coincidere.
	  			</font><br>
		 		</c:if> 

				<jsp:include page="frgDatiMancantiMessage.jsp" />
		 		

				<c:if test="${salvaOk!=null}">
					<font color="red" style="font-size: 18px; font-weight: bold;">
						<% if (!(request.getParameter("soloMsg") != null && (new Boolean(request.getParameter("soloMsg"))).booleanValue())) {%>
		  					<c:out value="${salvaOk}. Per tornare all'applicazione, premere il pulsante Home."/>
			  		<% } else {%>
		  					<c:out value="Modifica password effettuata correttamente! Chiudi e riapri l'applicazione per rendere effettiva la variazione."/>
			  		<% } %>
			  	</font>
				</c:if>
		
				<% if (request.getParameter("pwdScaduta") != null && (new Boolean(request.getParameter("pwdScaduta"))).booleanValue()) {
					if (request.getAttribute("noPwd") == null && request.getAttribute("pwdError") == null && request.getAttribute("salvaOk") == null) {%>
					<div style="color: red; text-align: middle; font-size: 14px; font-weight: bold;">
		  				Password scaduta: inserire nuova password
		  			</div>
				<%}}%>
		
				<table width="90%" cellpadding="0" cellspacing="0" align="center" class="clean">
  		    <tr class="header">
	            <td align="center" colspan="4">Dati Utenza</td>
	        </tr>
  		    <tr>
  		      <td align="right">Username:</td>
  			      <td align="left">
                <input type="text" name="userName" readonly="readonly" value="<%=request.getParameter("userName")%>"/>
              </td>
  		    </tr>

			    <% if (!(request.getParameter("soloMsg") != null && (new Boolean(request.getParameter("soloMsg"))).booleanValue())) {%>
    
          <% if ((request.getAttribute("showPwd") != null && (new Boolean(request.getAttribute("showPwd").toString())).booleanValue() )) {%>
			    <tr>
			      <td align="right">Nuova Password*:</td>
			      <td align="left"><input type="password" name="password"></td>
			    </tr>

          <tr>
            <td align="right">Conferma Nuova Password*:</td>
            <td align="left"><input type="password" name="password2"></td>
          </tr>
          <tr>
            <td align="right">Email*:</td>
            <td align="left">
            	<%if (request.getAttribute("email") != null)  {%>
            		<input type="text" name="email" value="<%=request.getAttribute("email")%>">
            	<%} else {%>
            		<input type="text" name="email">
            	<%}%>
            </td>
          </tr>
          <%}%>
          <%}%>   

		<tr class="header">
            <td align="center" colspan="4">Dati Anagrafici</td>
          </tr>
          <tr>
            <td align="right">Codice fiscale</td>
            <td align="left" colspan="3">
            	<%if (request.getAttribute("codfis") != null)  {%>
            		<input type="text" name="codfis" value="<%=request.getAttribute("codfis")%>" readonly="readonly">
            	<%}%>
            </td>
	      </tr>
          <tr><td colspan="4"><br /></td></tr>
          <tr>
          	<td align="right">Cognome*</td>
            <td align="left">
            	<%if (request.getAttribute("cognome") != null)  {%>
            		<input type="text" name="cognome" value="<%=request.getAttribute("cognome")%>">
            	<%} else {%>
            		<input type="text" name="cognome">
            	<%}%>
            </td>
            <td align="right">Nome*</td>
            <td align="left">
            	<%if (request.getAttribute("nome") != null)  {%>
            		<input type="text" name="nome" value="<%=request.getAttribute("nome")%>">
            	<%} else {%>
            		<input type="text" name="nome">
            	<%}%>
            </td>
          </tr>
          
          <tr>
          	<td align="right">Sesso</td>
            <td align="left">
            	<%if ("M".equals(request.getAttribute("sesso")))  {%>
            		<input type=radio name="sesso" value="M" checked>M
            	<%} else {%>
            		<input type=radio name="sesso" value="M">M
            	<%}%>
            	<%if ("F".equals(request.getAttribute("sesso")))  {%>
            		<input type=radio name="sesso" value="F" checked>F
            	<%} else {%>
            		<input type=radio name="sesso" value="F">F
            	<%}%>
            </td>
            <td align="right">Data di nascita (gg/MM/aaaa)*</td>
            <td align="left">
            	<%if (request.getAttribute("dtNascita") != null)  {%>
            		<input type="text" name="dtNascita" value="<%=request.getAttribute("dtNascita")%>" id="datepick">
            	<%} else {%>
            		<input type="text" name="dtNascita" id="datepick">
            	<%}%>
            </td>
          </tr>
          
          <tr>
            <td align="right">Comune di nascita*</td>
            <td align="left">
            	<%if (request.getAttribute("cmNascita") != null)  {%>
            		<input type="text" name="cmNascita" value="<%=request.getAttribute("cmNascita")%>">
            	<%} else {%>
            		<input type="text" name="cmNascita">
            	<%}%>
            </td>
            <td align="right">Stato estero nascita*</td>
            <td align="left">
            	<%if (request.getAttribute("esteroNascita") != null)  {%>
            		<input type="text" name="esteroNascita" value="<%=request.getAttribute("esteroNascita")%>">
            	<%} else {%>
            		<input type="text" name="esteroNascita">
            	<%}%>
            </td>
          </tr>
          
          <tr>
          	<td align="right">Comune residenza</td>
            <td align="left">
                <%if (request.getAttribute("cmResidenza") != null)  {%>
            		<input type="text" name="cmResidenza" value="<%=request.getAttribute("cmResidenza")%>">
            	<%} else {%>
            		<input type="text" name="cmResidenza">
            	<%}%>            </td>
            <td align="right">Provincia residenza</td>
            <td align="left">
                <%if (request.getAttribute("prResidenza") != null)  {%>
            		<input type="text" name="prResidenza" value="<%=request.getAttribute("prResidenza")%>">
            	<%} else {%>
            		<input type="text" name="prResidenza">
            	<%}%>
            </td>
          </tr>
          
          <tr>
          	<td align="right">CAP</td>
            <td align="left">
            	<%if (request.getAttribute("cap") != null)  {%>
            		<input type="text" name="cap" value="<%=request.getAttribute("cap")%>">
            	<%} else {%>
            		<input type="text" name="cap">
            	<%}%>
            </td>
            <td align="right">Cittadinanza</td>
            <td align="left">
            	<%if (request.getAttribute("cittadinanza") != null)  {%>
            		<input type="text" name="cittadinanza" value="<%=request.getAttribute("cittadinanza")%>">
            	<%} else {%>
            		<input type="text" name="cittadinanza">
            	<%}%>
            </td>
          </tr>
          
          <tr>
          	<td align="right">Indirizzo</td>
            <td align="left">
            	<%if (request.getAttribute("indirizzo") != null)  {%>
            		<input type="text" name="indirizzo" value="<%=request.getAttribute("indirizzo")%>">
            	<%} else {%>
            		<input type="text" name="indirizzo">
            	<%}%>
            </td>
            <td align="right">Civico</td>
            <td align="left">
            	<%if (request.getAttribute("civico") != null)  {%>
            		<input type="text" name="civico" value="<%=request.getAttribute("civico")%>">
            	<%} else {%>
            		<input type="text" name="civico">
            	<%}%>
            </td>
          </tr>
          <tr>
            <td align="right">Telefono</td>
            <td align="left">
            	<%if (request.getAttribute("telefono") != null)  {%>
            		<input type="text" name="telefono" value="<%=request.getAttribute("telefono")%>">
            	<%} else {%>
            		<input type="text" name="telefono">
            	<%}%>
            </td>
            <td align="right">Cellulare</td>
            <td align="left">
            	<%if (request.getAttribute("cellulare") != null)  {%>
            		<input type="text" name="cellulare" value="<%=request.getAttribute("cellulare")%>">
            	<%} else {%>
            		<input type="text" name="cellulare">
            	<%}%>
            </td>
          </tr>
          <tr>
            <td align="right">Fax</td>
            <td align="left">
            	<%if (request.getAttribute("fax") != null)  {%>
            		<input type="text" name="fax" value="<%=request.getAttribute("fax")%>">
            	<%} else {%>
            		<input type="text" name="fax">
            	<%}%>
            </td>
          </tr>
          
          <tr>
            <td align="left" colspan="4">
            		Compilare solo se la residenza è diversa dal domicilio
			</td>
          </tr>
          <tr>
          	<td align="right">Comune domicilio</td>
            <td align="left">
                <%if (request.getAttribute("cmDomicilio") != null)  {%>
            		<input type="text" name="cmDomicilio" value="<%=request.getAttribute("cmDomicilio")%>">
            	<%} else {%>
            		<input type="text" name="cmDomicilio">
            	<%}%>            </td>
            <td align="right">Provincia domicilio</td>
            <td align="left">
                <%if (request.getAttribute("prDomicilio") != null)  {%>
            		<input type="text" name="prDomicilio" value="<%=request.getAttribute("prDomicilio")%>">
            	<%} else {%>
            		<input type="text" name="prDomicilio">
            	<%}%>
            </td>
          </tr>
          <tr>
          	<td align="right">CAP domicilio</td>
            <td align="left">
            	<%if (request.getAttribute("capDomicilio") != null)  {%>
            		<input type="text" name="capDomicilio" value="<%=request.getAttribute("capDomicilio")%>">
            	<%} else {%>
            		<input type="text" name="capDomicilio">
            	<%}%>
            </td>
            <td align="right">Indirizzo domicilio</td>
            <td align="left">
            	<%if (request.getAttribute("indirizzoDomicilio") != null)  {%>
            		<input type="text" name="indirizzoDomicilio" value="<%=request.getAttribute("indirizzoDomicilio")%>">
            	<%} else {%>
            		<input type="text" name="indirizzoDomicilio">
            	<%}%>
            </td>
          </tr>
          <tr>
          <td align="right">Civico domicilio</td>
            <td align="left">
            	<%if (request.getAttribute("civicoDomicilio") != null)  {%>
            		<input type="text" name="civicoDomicilio" value="<%=request.getAttribute("civicoDomicilio")%>">
            	<%} else {%>
            		<input type="text" name="civicoDomicilio">
            	<%}%>
            </td>
            <td align="right">Telefono domicilio</td>
            <td align="left">
            	<%if (request.getAttribute("telefonoDomicilio") != null)  {%>
            		<input type="text" name="telefonoDomicilio" value="<%=request.getAttribute("telefonoDomicilio")%>">
            	<%} else {%>
            		<input type="text" name="telefonoDomicilio">
            	<%}%>
            </td>
          </tr>
          <tr>
          <td align="right">Cellulare domicilio</td>
            <td align="left">
            	<%if (request.getAttribute("cellulareDomicilio") != null)  {%>
            		<input type="text" name="cellulareDomicilio" value="<%=request.getAttribute("cellulareDomicilio")%>">
            	<%} else {%>
            		<input type="text" name="cellulareDomicilio">
            	<%}%>
            </td>
           <td align="right">Fax domicilio</td>
            <td align="left">
            	<%if (request.getAttribute("faxDomicilio") != null)  {%>
            		<input type="text" name="faxDomicilio" value="<%=request.getAttribute("faxDomicilio")%>">
            	<%} else {%>
            		<input type="text" name="faxDomicilio">
            	<%}%>
            </td>
          </tr>
          
          <tr class="header">
            <td align="center" colspan="4">Dati Ufficio</td>
          </tr>
		
		 <jsp:include page="frgDatiUfficio.jsp" />
          

          <tr>
			      <td align="center" colspan="4">
			      	<% if (!(request.getParameter("soloMsg") != null && (new Boolean(request.getParameter("soloMsg"))).booleanValue())) {%>
			      		<input type="submit" name="submit" value="Salva">
			      	<% }%>
			   		<% if (request.getParameter("pathApp") != null && request.getAttribute("salvaOk") != null) {%>
			      		<input type="button" name="home" value="Home" onclick="location.href='../<%=request.getParameter("pathApp")%>'">
			      	<% }%>
			      </td>
			    </tr>
				</table>
				
        <% if ((request.getParameter("pwdScaduta") != null && new Boolean(request.getParameter("pwdScaduta")).booleanValue()) ||
						(request.getUserPrincipal()).getName().equals(request.getAttribute("uN"))) {%>
			    	<!--<div style="color: red; text-align: middle; font-size: 14px; font-weight: bold;">
	  					Al termine dell'operazione, si verrà reindirizzati alla Home.
	  				</div>
			    --><% } %>
				
		<input name="idAnagrafica" type="hidden" value='<c:out value="${idAnagrafica}"/>'></input>
		<input name="ente" type="hidden" value='<c:out value="${ente}"/>'></input>
		<input name="idPersona" type="hidden" value='<c:out value="${idPersona}"/>'></input>
		<input name="usrInsAna" type="hidden" value='<c:out value="${usrInsAna}"/>'></input>
		<input name="dtInsAna" type="hidden" value='<c:out value="${dtInsAna}"/>'></input>
		<input name="flPwdValida" type="hidden" value="on"></input>
        <input name="pwdScaduta" type="hidden" value="<%=request.getParameter("pwdScaduta") == null ? false : request.getParameter("pwdScaduta")%>"></input>
        <input name="pathApp" type="hidden" value="<%=request.getParameter("pathApp") == null ? "" : request.getParameter("pathApp")%>"></input>
			</form>
		</div>
	</body>
</html>
