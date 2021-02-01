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
  	<title>AMProfiler - Modifica Utente</title>
  	<%String pathWebApp = request.getContextPath(); %>
	</head>
	
	<body>
		<script>
			var doLogout = <%=request.getParameter("doLogout") != null && new Boolean(request.getParameter("doLogout")).booleanValue()%>
			<% 
			  String ctxPath;
				if (request.getParameter("pwdScaduta") != null && (new Boolean(request.getParameter("pwdScaduta"))).booleanValue()) {
					  if (request.getParameter("pathApp") != null && !request.getParameter("pathApp").equals("")) {
						  ctxPath = request.getParameter("pathApp");
            } else {
            	  ctxPath = request.getContextPath();
        	  }
				} else {
					ctxPath = request.getContextPath();
				}
      %>

      if (doLogout) {
    	  if (opener && opener.opener) {
					opener.opener.location = "<%=ctxPath%>/Logout";
					opener.close();
					window.close();
				} else if (opener) {
					opener.location = "<%=ctxPath%>/Logout";
					window.close();
				} else {
					window.location = "<%=ctxPath%>/Logout";
				}
			}
		</script>		
		
	   <% if(request.getAttribute("soloDatiUfficio")!=null){ %>
	 
	 	        <div align="center" id="informativa" >
		          <br>          
		          <br>
		          <table cellpadding="5" style="width: 90%;">
		          	<tr>
		              <td>
		              	<table style="width: 100%; border-width: 0px;">
		              		<tr>
		              			<td style="width: 100%; text-align: center; border-width: 0px; font-weight: bold;">
		              				INFORMATIVA PER GLI UTENTI
		              			</td>
		              		</tr>
		              		<tr>
		              			<td style="width: 100%; text-align: justify; border-width: 0px;">
		              				<p>Al fine di verificare l'attendibilità dei dati anagrafici ed al fine di consentire di risalire direttamente all'utente per eventuali 
												comunicazioni si chiede di indicare i dati richiesti dal sistema (I campi contrassegnati da asterisco sono obbligatori). </p>
		              				<p>Le informazioni verranno richieste solo al primo accesso.</p>
		              			</td>
		              		</tr>
		              	</table>
		              </td>
		            </tr>                     
		          </table>
		        </div>
	 <%} %>
	

		<div id="centrecontent"><!--centre content goes here --> 
			<br>
			<form name="userForm" method="POST" action="<%=pathWebApp%>/SalvaUtente?mode=<%=request.getAttribute("mode")%>" >
				
				<c:if test="${noPwd}">
					<font color="red" style="font-size: 18px; font-weight: bold;">
	  				Attenzione. Nuova password non inserita.
	  			</font>
		 		</c:if> 

				<c:if test="${pwdError}">
					<font color="red" style="font-size: 18px; font-weight: bold;">
	  				Attenzione.&nbsp;La conferma della nuova password non &egrave; valida: la <b>password</b> e la <b>conferma password</b> devono coincidere.
	  			</font>
		 		</c:if> 

				<c:if test="${noEmail}">
					<font color="red" style="font-size: 18px; font-weight: bold;">
	  				Attenzione. Email non inserita.
	  			</font>
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
				<c:if test="${esitoEmail!=null}">
					<br />
					<font color="red" style="font-size: 15px; font-weight: bold;">
						<c:out value="${esitoEmail}"/>
					</font>
				</c:if>
				<c:if test="${timeError!=null}">
					<br />
					<font color="red" style="font-size: 15px; font-weight: bold;">
						<c:out value="${timeError}"/>
					</font>
				</c:if>
		
				<% if (request.getParameter("pwdScaduta") != null && (new Boolean(request.getParameter("pwdScaduta"))).booleanValue()) {
					if (request.getAttribute("noPwd") == null && request.getAttribute("pwdError") == null && request.getAttribute("salvaOk") == null) {%>
					<div style="color: red; text-align: middle; font-size: 14px; font-weight: bold;">
		  				Password scaduta: inserire nuova password
		  			</div>
				<%}}%>
		
				<table width="90%" cellspacing="5" align="center" class="clean">
				
			
  		    <tr>
	            <th align="center" colspan="4">Dati Utenza</th>
	        </tr>
	        <tr>
  		      <td align="right">Username*:</td>
	  		      <%if (request.getAttribute("uN") != null && !"".equals(request.getAttribute("uN")))  {%>
	  			      <td align="left"><input type="text" name="userName" readonly="readonly" value="<%=request.getAttribute("uN")%>"/></td>
	  		      <%} else if (request.getAttribute("userName") != null) {%>
	  			      <td align="left"><input type="text" name="userName" value="<%=request.getAttribute("userName")%>"></td>
	  		      <%} else {%>
	  			      <td align="left"><input type="text" name="userName"></td>
	  		      <%}%>
  		     
  		      <% if(request.getAttribute("soloDatiUfficio")==null){ %>	
  		      <% if ((request.getAttribute("showGroup") != null && (new Boolean(request.getAttribute("showGroup").toString())).booleanValue() )) {%>
	            <td align="left" rowspan="5" colspan="2">Gruppi: 
	              <br/><br/>
	              <div id="listagruppi" style="overflow:auto;height:250px;width:350px;">
					<c:forEach var="gruppo" items="${tuttigruppi}">
							<c:if test="${gruppo.checked == true}">
								<input type="checkbox" checked="checked" name="gruppi" value="<c:out value="${gruppo.name}" />"/>
							</c:if>
							<c:if test="${gruppo.checked != true}">
								<input type="checkbox" name="gruppi" value="<c:out value="${gruppo.name}" />"/>
							</c:if>
							<c:out value="${gruppo.name} (${gruppo.descrComune})" />
							<br />
					</c:forEach>
				</div>
	            </td>
	          </tr>
	          <% }%>   
	         <%} %>

	<% if (!(request.getParameter("soloMsg") != null && (new Boolean(request.getParameter("soloMsg"))).booleanValue())) {%>
    
          <% if ((request.getAttribute("showPwd") != null && (new Boolean(request.getAttribute("showPwd").toString())).booleanValue()) && request.getAttribute("soloDatiUfficio")==null) {%>
			    <tr>
			      <td align="right">Nuova Password*:</td>
			      <td align="left">
			      	<%if (request.getAttribute("password") != null)  {%>
	            		<input type="password" name="password" value="<%=request.getAttribute("password")%>">
	            	<%} else {%>
	            		<input type="password" name="password">
	            	<%}%>
			      </td>
			    </tr>

          <tr>
            <td align="right">Conferma Nuova Password*:</td>
            <td align="left">
			      	<%if (request.getAttribute("password2") != null)  {%>
	            		<input type="password" name="password2" value="<%=request.getAttribute("password2")%>">
	            	<%} else {%>
	            		<input type="password" name="password2">
	            	<%}%>
	        </td>
          </tr>
          <tr>
            <td align="right">Password di default valida:</td>
            <td align="left" colspan="3">
           		<%if ("on".equals(request.getAttribute("flPwdValida")))  {%>
 					<input type=checkbox name="flPwdValida" checked="checked"
                     title="Se selezionato, assegna all'utente una password non scaduta; viceversa, l'utente dovrà, al primo accesso, effettuare subito la modifica dalla password."> 
                <%} else {%>
 					<input type=checkbox name="flPwdValida"
                     title="Se selezionato, assegna all'utente una password non scaduta; viceversa, l'utente dovrà, al primo accesso, effettuare subito la modifica dalla password.">
                <%}%>
            </td>
          </tr>  
          <%}%>
        
          <% if ((request.getAttribute("showGroup") != null && (new Boolean(request.getAttribute("showGroup").toString())).booleanValue() )) {%>
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
          <tr>
            <th align="center" colspan="4">Dati Anagrafici</th>
          </tr>
          <tr>
            <td align="right">Codice fiscale</td>
            <td align="left">
            	<%if (request.getAttribute("codfis") != null && request.getAttribute("uN") != null && !"".equals(request.getAttribute("uN"))) {%>
            		<input type="text" name="codfis" value="<%=request.getAttribute("codfis")%>" readonly="readonly">
            	<%} else if (request.getAttribute("codfis") != null)  {%>
            		<input type="text" name="codfis" value="<%=request.getAttribute("codfis")%>">
            	<%} else {%>
            		<input type="text" name="codfis">
            	<%}%>
            </td>
            <%if (request.getAttribute("uN") == null || "".equals(request.getAttribute("uN"))) {%>
            <td align="right">Cerca dati su</td>
            <td align="left"><select NAME="ente">
	                  <c:forEach var="comuneUtente" items="${comuniUtente}">
	                      <option value=" <c:out value="${comuneUtente.belfiore}" />">
	                        <c:out value="${comuneUtente.descrizione}" />
	                      </option>
	                  </c:forEach>
	                </select>
	        <input type="submit" name="submit" value="Invio">
	        </td>
	        <%}%>
	      </tr>
          <tr><td colspan="4"></td></tr>
          <tr>
          	<td id="coglabel" align="right">Cognome*</td>
            <td id="cogtext" align="left">
            	<%if (request.getAttribute("cognome") != null)  {%>
            		<input type="text" name="cognome" value="<%=request.getAttribute("cognome")%>">
            	<%} else {%>
            		<input type="text" name="cognome">
            	<%}%>
            </td>
            <td id="nomelabel" align="right">Nome*</td>
            <td id="nometext"align="left">
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
         
           <tr>
	            <th align="center" colspan="4">Dati Ufficio</th>
	       </tr>
 		   <jsp:include page="frgDatiUfficio.jsp" />
          
          <% if(request.getAttribute("soloDatiUfficio")==null){ %>
        	<jsp:include page="frgGestioneAccesso.jsp" />
		  <%}%>	
			
           <% }%>  
          
          <%}%>  
          <tr>
			      <td align="center" colspan="4">
			      	<% if (!(request.getParameter("soloMsg") != null && (new Boolean(request.getParameter("soloMsg"))).booleanValue())) {%>
			      		<input type="submit" name="submit" value="Salva">
			      	<% }%>
			      	<% if(request.getAttribute("soloDatiUfficio")==null){ %>
			      		<input type="button" value="Chiudi" onclick="window.close();">
			      	<%}else if(request.getAttribute("soloDatiUfficio")!=null && request.getAttribute("salvaOk")!=null){ %>
			      		<input type="submit" name="submit" value="Procedi">
			     	<%} %>
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
		<input name="usrInsAna" type="hidden" value='<c:out value="${usrInsAna}"/>'></input>
		<input name="dtInsAna" type="hidden" value='<c:out value="${dtInsAna}"/>'></input>
		<input name="idPersona" type="hidden" value='<c:out value="${idPersona}"/>'></input>
        <input name="pwdScaduta" type="hidden" value="<%=request.getParameter("pwdScaduta") == null ? false : request.getParameter("pwdScaduta")%>"></input>
        <input name="pathApp" type="hidden" value="<%=request.getParameter("pathApp") == null ? "" : request.getParameter("pathApp")%>"></input>
      	<input name="soloDatiUfficio" type="hidden" value='<c:out value="${soloDatiUfficio}"/>'></input>
      	<input name="pathApp" type="hidden" value='<c:out value="${pathApp}"/>'></input>
      	<input name="myparam" type="hidden" value='<c:out value="${myparam}"/>'></input>
      	
			</form>
		</div>
	</body>
</html>
