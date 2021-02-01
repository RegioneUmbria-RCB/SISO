<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ taglib uri="/WEB-INF/displaytag-11.tld" prefix="display"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.util.ArrayList"%>
<%@page import="it.webred.AMProfiler.beans.AmComune"%>

<html>
	<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  	<link rel="icon" href="favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
  	<link href="<%=request.getContextPath()%>/css/newstyle.css" rel="stylesheet" type="text/css" />
  	<title>Inizializzazione servizio</title>
  	<script>
    	function doSubmit() {
    		if (document.enteForm.ragioneAccesso.value == "") {
    			if (document.enteForm.pratica.value == "") {
    				alert("ATTENZIONE: Inserire ragione di accesso e pratica lavorata");
    				return false;
    			} else {
    				alert("ATTENZIONE: Inserire ragione di accesso");
    				return false;
    			}    				
        	} else if (document.enteForm.pratica.value == "") {
				alert("ATTENZIONE: Inserire pratica lavorata");
				return false;
        	}
        	       	
    		document.enteForm.submit();
    		return true;
    	}
    </script>
	</head>
	
	<body>
		<form name="enteForm" method="POST" action="SceltaEnte" >
	
			<%ArrayList<AmComune> entiUtente = (ArrayList) request.getAttribute("entiUtente");
			boolean doOnlyAMInsPratica = request.getParameter("doOnlyAMInsPratica") == null ? false : new Boolean(request.getParameter("doOnlyAMInsPratica")).booleanValue();
			if (!doOnlyAMInsPratica && entiUtente.size() > 1) {%>
				<table width="100%" cellpadding="0" cellspacing="0" align="center" border="0" height="50" style="border-bottom:6px solid #4A75B5;">
			    		<tr><td><font size='5' color="#4A75B5" style="font-weight: bold;padding-left: 5px;">Scelta ente</font></td></tr>
			    </table>
			    <br><br>
			
		
				<div align="center"><!--centre content goes here --> 
					
						
					<table>
		           		<tr><td>
		            		<select NAME="enteScelto">
							<c:forEach var="ente" items="${entiUtente}">
									<option value="${ente.belfiore}"><c:out value="${ente.descrizione}" /></option>
							</c:forEach>
							</select>
						</td></tr>
					</table>
					<br>			
					
				</div>
				
				<br><br>
			<% } else if (!doOnlyAMInsPratica && entiUtente.size() ==1) {%>
					<input type="hidden" name="enteScelto" value='<c:out value="<%=entiUtente.get(0).getBelfiore()%>"/>' />	
			<% } %>
					<input type="hidden" name="pathApp" value='<c:out value="${pathApp}"/>' />
					<input type="hidden" name="myparam" value='<c:out value="${myparam}"/>' />
					<input type="hidden" name="userName" value='<c:out value="${userName}"/>' />
					<input type="hidden" name="doneInsPratica" value='<%="" + doOnlyAMInsPratica%>' />					
			    
		    <table width="100%" cellpadding="0" cellspacing="0" align="center" border="0" height="50" style="border-bottom:6px solid #4A75B5;">
	    		<tr>
		   			<td style="text-align: center;">
		   				<img src="img/git.jpg" width="165" height="43" style="padding: 5px 10px 5px 0px; vertical-align: middle;"/>
		   				<font size='5' color="#4A75B5" style="font-weight: bold; vertical-align: middle;">Pratica lavorata</font>
		   			</td>
		   		</tr>
	    	</table>
	    
	    	<br><br>
	    	
	    	<div align="center">
			    <table cellpadding="5" style="width: 50%;">
		          	<tr>
		              <td>
		              	<table style="width: 100%; border-width: 0px;">
		              		<tr>
		              			<td style="width: 100%; text-align: center; border-width: 0px; font-weight: bold;">
		              				ATTENZIONE!
		              			</td>
		              		</tr>
		              		<tr>
		              			<td style="width: 100%; text-align: justify; border-width: 0px;">
		              				<p>Si informa che in aderenza alle disposizioni in materia di protezione dei dati personali (D. lgs. 196/2003) e secondo gli orientamenti 
		              				dell'Autorità garante per la privacy espressi per analoghi ambiti, è stato introdotto un nuovo campo 
		              				denominato "pratica lavorata" per collegare effettivamente gli accessi compiuti dagli operatori alle finalità loro consentite.</p>
		              				<p>Si tratta di un campo obbligatorio necessario all'effettuazione della sessione di consultazione dei dati.</p>
		              				<p>Tutti gli operatori dovranno pertanto inserire necessariamente in tale campo, in formato di testo libero, 
		              				la ragione dell'accesso e il o i riferimenti della pratica per la quale si rende necessario procedere alla consultazione 
		              				di una o più posizioni.</p>
		              				<p>La mancata compilazione del campo non consentirà la prosecuzione della attività di consultazione. 
		              				I passi successivi saranno cioè inibiti fintanto che il campo non verrà compilato. Sarà possibile, inoltre, una volta compilato il campo, 
		              				modificarlo nel corso della navigazione qualora se ne presenti la necessità.</p>
		              			</td>
		              		</tr>
		              		<tr>
		              			<td style="width: 100%; text-align: center; border-width: 0px; font-weight: bold; padding-top: 10px;">
		              				PRATICA LAVORATA
		              			</td>
		              		</tr>
		              		<tr>
		              			<td style="width: 100%; text-align: center; border-width: 0px; font-weight: bold;">
		              				Inserire la ragione d'accesso
		              			</td>
		              		</tr>
		              		<tr>
		              			<td style="width: 100%; text-align: center; border-width: 0px; font-style: italic;">
		              				costituita da una <span style="font-weight: bold;">stringa di testo</span> lunga al massimo 
		              				<span style="font-weight: bold;">25 caratteri</span>
		              			</td>
		              		</tr>
		              		<tr>
		              			<td style="width: 100%; text-align: center; border-width: 0px; font-style: italic; padding-top: 10px;">
		              				<input name="ragioneAccesso" type="text" maxlength="25" style="width: 250px;" />
		              			</td>
		              		</tr>
		              		<tr>
		              			<td style="width: 100%; text-align: center; border-width: 0px; font-weight: bold;">
		              				e l'identificativo della pratica in lavorazione
		              			</td>
		              		</tr>
		              		<tr>
		              			<td style="width: 100%; text-align: center; border-width: 0px; font-style: italic;">
		              				costituito da una <span style="font-weight: bold;">stringa di testo</span> lunga al massimo 
		              				<span style="font-weight: bold;">20 caratteri</span>
		              			</td>
		              		</tr>
		              		<tr>
		              			<td style="width: 100%; text-align: center; border-width: 0px; font-style: italic; padding-top: 10px;">
		              				<input name="pratica" type="text" maxlength="20" style="width: 200px;" />
		              			</td>
		              		</tr>
		              	</table>
		              </td>
		            </tr>                     
		        </table>
		    </div>
		    
		    <br><br>
		    
		    <div align="center">
			    <input type="button" name="scegliEnte" value="Invia"
				onclick="javascript:return doSubmit()" style="color: white; background-color: #4A75B5; border-bottom: 1px solid #606060; border-right: 1px solid #606060; border-top: 1px solid #c0c0c0; border-left: 1px solid #c0c0c0;"/>
	    	</div>
	    
	    </form>
	
	</body>
</html>
