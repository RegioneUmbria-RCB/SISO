<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  	<link href="<%=request.getContextPath()%>/css/newstyle.css" rel="stylesheet" type="text/css" />
    <title>AMProfiler - Gestione Utenti</title>
  	<%String pathWebApp = request.getContextPath(); %>
    <style type="text/css">
    	.imgLnk A:link {text-decoration: none;}
    	.imgLnk A:visited {text-decoration: none;}
    	.imgLnk A:active {text-decoration: none;}
    	.imgLnk A:hover {text-decoration: none;}
    	.lnkImg {border: 1px solid #808080;}
    </style>
  </head>

  <script type="text/javascript">
     function creaUtente() {
       var browser=navigator.appName;
       if(browser.indexOf("Microsoft") == -1)
         window.open("<%=pathWebApp%>/SalvaUtente?mode=new","Crea Utente","width=800,height=800,left=300,top=0,scrollbars=yes,resizable=yes,status=yes");
       else
         window.open("<%=pathWebApp%>/SalvaUtente?mode=new","Crea_Utente","width=800,height=800,left=300,top=0,scrollbars=yes,resizable=yes,status=yes"); 
     }

     function updPassword(uN) {		
         var browser=navigator.appName;
         if(browser.indexOf("Microsoft") == -1)
         	window.open("<%=pathWebApp%>/SalvaUtente?mode=pwd&userName=" + uN,"Cambia_password","width=800,height=350,left=300,top=0,scrollbars=yes,resizable=yes,status=yes");
         else
         	window.open("<%=pathWebApp%>/SalvaUtente?mode=pwd&userName=" + uN,"Cambia_password","width=800,height=350,left=300,top=0,scrollbars=yes,resizable=yes,status=yes");	
     }

     function resetPassword(uN) {
    	if (confirm("Si desidera effettuare il reset della password dell'utente " + uN + "?")) {
    		location.href = "<%=pathWebApp%>/SalvaUtente?mode=resetPwd&userName=" + uN;
        }
     }
     
     function updUtente(uN) {		
       var browser=navigator.appName;
       if(browser.indexOf("Microsoft") == -1)
       	window.open("<%=pathWebApp%>/SalvaUtente?mode=vis&userName=" + uN,"Modifica Utente","width=800,height=800,left=300,top=0,scrollbars=yes,resizable=yes,status=yes");
       else
       	window.open("<%=pathWebApp%>/SalvaUtente?mode=vis&userName=" + uN,"Modifica_Utente","width=800,height=800,left=300,top=0,scrollbars=yes,resizable=yes,status=yes");	
  	 }
  	 
     function disableUtente(uN) {		
         var browser=navigator.appName;
         if(browser.indexOf("Microsoft") == -1)
         	window.open("<%=pathWebApp%>/CancellaUtente?userName=" + uN,"Invalida Utente","width=800,height=800,left=300,top=0,scrollbars=yes,resizable=yes,status=yes");
         else
         	window.open("<%=pathWebApp%>/CancellaUtente?userName=" + uN,"Invalida_Utente","width=800,height=800,left=300,top=0,scrollbars=yes,resizable=yes,status=yes");	
    	 }
     function enableUtente(uN) {		
         var browser=navigator.appName;
         if(browser.indexOf("Microsoft") == -1)
         	window.open("<%=pathWebApp%>/CancellaUtente?ripristina=yes&userName=" + uN,"Ripristina Utente","width=800,height=800,left=300,top=0,scrollbars=yes,resizable=yes,status=yes");
         else
         	window.open("<%=pathWebApp%>/CancellaUtente?ripristina=yes&userName=" + uN,"Ripristina_Utente","width=800,height=800,left=300,top=0,scrollbars=yes,resizable=yes,status=yes");	
    	 }
	function creaGruppo() {
         var browser=navigator.appName;
         if(browser.indexOf("Microsoft") == -1)
           window.open("<%=pathWebApp%>/NuovoGruppo","Gestione Gruppi","width=600,height=600,left=300,top=0,scrollbars=yes,resizable=yes,status=yes");
         else
           window.open("<%=pathWebApp%>/NuovoGruppo","Gestione_Gruppi","width=600,height=600,left=300,top=0,scrollbars=yes,resizable=yes,status=yes"); 
     }
     function mappingUsers(){
         var browser=navigator.appName;
         if(browser.indexOf("Microsoft") == -1)
           window.open("<%=pathWebApp%>/MappingUsers","Associazione Utenti","width=1200,height=900,left=10,top=10,scrollbars=yes,resizable=yes,status=yes");
         else
           window.open("<%=pathWebApp%>/MappingUsers","Associazione_Utenti","width=1200,height=900,left=10,top=10,scrollbars=yes,resizable=yes,status=yes"); 
     }
  	 function getUpdPwd(statoPwd) {
  		var pathWebApp = "<%=pathWebApp%>";
   		var semaforo = "verde";
  		if (statoPwd.indexOf("in scadenza") != -1)
  			semaforo = "giallo";
  		if (statoPwd.indexOf("scaduta") != -1)
  			semaforo = "rosso";
  		return "<img class='lnkImg' src='" + pathWebApp + "/img/sem_"+ semaforo +".gif' title='" + statoPwd + "' />";
  	}
  </script>

  <body>
  
  <br />
  <table width="90%" align="center" class="hidden">
	    <tr>
	         <th align="left">
				<form name="userForm" method="POST" action="<%=pathWebApp%>/SalvaUtente?mode=new" style="float: left">
	        		<input type="button" name="nuovoUtente" value="Nuovo Utente" onclick="creaUtente()"/>
	    		</form>
				<form name="groupForm" method="POST" action="NuovoGruppo" style="float: left; padding-left: 10px">
	        		<input type="button" name="gestGruppi" value="Gestione Gruppi" onclick="creaGruppo()"/>
	    		</form>
	    		<form name="mappingForm" method="POST" action="MappingUsers" style="float: left; padding-left: 10px">
	        		<input type="button" name="gestGruppi" value="Associazione Utenti" onclick="mappingUsers()"/>
	    		</form>
			</th>
	     </tr>
  </table>
  
  <div id="listautenti" align="left" style="padding-left: 10px"><!--centre content goes here --> 
    <font color="red">
      <c:if test="${giaPresente}">
        <c:out value="Nome utente gi� presente!"></c:out>
      </c:if>
      <c:if test="${noCancellazioneUtente}">
        <c:out value="${msgCancellazioneUtente}"></c:out>
      </c:if>
    </font>
    <font color="green">
    	<c:if test="${okResetPwd}">
	        <c:out value="${msgResetPwd}"></c:out>
	      </c:if>
    </font>
  </div>

	<form name="searchForm" method="POST" action="<%=pathWebApp%>/CaricaUtenti?mode=search" >
	<table width="90%" cellpadding="0" cellspacing="0" align="center" class="clean">
	  		    <tr class="header">
		            <td align="center" colspan="4">Filtro di ricerca</td>
		        </tr>
		        <tr>
	  		      <td align="right">Username</td>
	  			  <td align="left"><input type="text" name="ricUser"/></td>
	  			  
	  			  <td align="right">Codice fiscale</td>
	  			  <td align="left"><input type="text" name="ricCF"/></td>
	  		   </tr>
	  		   <tr>
	  		   	  <td align="right">Cognome</td>
	  			  <td align="left"><input type="text" name="ricCognome"/></td>
	  			  
	  		      <td align="right">Nome</td>
	  			  <td align="left"><input type="text" name="ricNome"/></td>
	  		   </tr>
	  		   <tr>
	  		   	  <td align="right">Stato</td>
	  			  <td align="left">
		  			  <select NAME="ricStato">
							<option value="">-- Selezione --</option>
							<option value="CANCELLED">Cancellato</option>
							<option value="PWD VALID">Password valida</option>
							<option value="PWD ABOUT TO EXPIRE">Password in scadenza</option>
							<option value="PWD EXPIRED">Password scaduta</option>
						</select>
	  			  </td>
<!-- 	  			  <td align="right">Nome GIT</td> -->
<!-- 	  			  <td align="left"><input type="text" name="ricOldUser"/></td> -->
	  		   </tr>	   
	  		   <tr>
	  		   	  <td><br /></td>
	  		   	  <td align="left"><input type="submit" name="submit" value="Invio"></td>
	  		   </tr>
	  		   <tr>
	  		   	  <td colspan="2">Sar� visualizzato un numero massimo di 100 utenti</td>
	  		   </tr>
	</table>
	</form>
	<br>

  <div align="center" style="padding-left: 10px">
    <display:table id="row" name="${userList}" pagesize="10" class="griglia" >
      <display:column title="Nome Utente">
      	<c:if test="${empty row.anagrafica.amUser.disableCause}">
     			<c:out value="${row.anagrafica.amUser.name}"></c:out>
     		</c:if>
    		<c:if test="${not empty row.anagrafica.amUser.disableCause}">
    			<font color="red"><c:out value="${row.anagrafica.amUser.name}"></c:out></font>
    		</c:if>
      </display:column>
      <display:column title="Stato Password">
			<c:out value="${row.statoPwd}"></c:out>
    	</display:column>
      <display:column title="Stato Utenza">
    		<c:if test="${empty row.anagrafica.amUser.disableCause}">
     			<c:out value="ATTIVA"></c:out>
     		</c:if>
    		<c:if test="${not empty row.anagrafica.amUser.disableCause}">
    			<c:out value="${row.anagrafica.amUser.disableCause}"></c:out>
    		</c:if>
    	</display:column>	
  	  <display:column title="Cognome">
     	<c:out value="${row.anagrafica.cognome}"></c:out>
      </display:column>
      <display:column title="Nome">
     	<c:out value="${row.anagrafica.nome}"></c:out>
      </display:column>
      <display:column title="Codice fiscale">
     	<c:out value="${row.anagrafica.codiceFiscale}"></c:out>
      </display:column>
  	<%
  	Connection con = null;
  	Statement st = null;
  	con = BaseAction.apriConnessione();
  	if (GestionePermessi.autorizzato(new AuthContext(request.getUserPrincipal(),con, CodificaPermessi.NOME_APP, CodificaPermessi.ITEM_MAPPING), CodificaPermessi.CANCELLA_UTENTE, true)){
  	%>
  		<display:column title="Gestione">
  			<c:if test="${empty row.anagrafica.amUser.disableCause}">
  				<a class="imgLnk" style="text-decoration: none" href="javascript:void(0);" onclick="updPassword('${row.anagrafica.amUser.name}')"> 
  					<img class="lnkImg" src="<%=pathWebApp%>/img/key.png" title="modifica password"/>
  				</a>
  				<a class="imgLnk" style="text-decoration: none" href="javascript:void(0);" onclick="resetPassword('${row.anagrafica.amUser.name}')"> 
  					<img class="lnkImg" src="<%=pathWebApp%>/img/reset.png" title="reset password"/>
  				</a>
  				<a class="imgLnk" style="text-decoration: none" href="javascript:void(0);" onclick="updUtente('${row.anagrafica.amUser.name}')"> 
  					<img class="lnkImg" src="<%=pathWebApp%>/img/edit.gif" title="modifica utente"/>
  				</a>
  				<a class="imgLnk" style="text-decoration: none" href="javascript:void(0);" onclick="disableUtente('${row.anagrafica.amUser.name}')"> 
  					<img class="lnkImg" src="<%=pathWebApp%>/img/error.gif" title="invalida"/>
  				</a>
  			</c:if>
  			<c:if test="${not empty row.anagrafica.amUser.disableCause}">
  				<a class="imgLnk" style="text-decoration: none" href="<%=pathWebApp%>/CancellaUtente?ripristina=yes&userName=${row.anagrafica.amUser.name}"> 
  					<img class="lnkImg" src="<%=pathWebApp%>/img/passed.gif" title="ripristina"/>
  				</a>
  			</c:if>
  			<a class="imgLnk" style="text-decoration: none" href="javascript:void(0);" onclick=""> 
				<img class="lnkImg" src="<%=pathWebApp%>/img/key.png" title="associa user ldap"/>
			</a>
  		</display:column>
  	<%}
  	BaseAction.chiudiConnessione(con, st);
  	%>
    
    </display:table>
  </div>

</body>
</html>
