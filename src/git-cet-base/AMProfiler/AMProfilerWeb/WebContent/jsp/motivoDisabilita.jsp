<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ taglib uri="/WEB-INF/displaytag-11.tld" prefix="display"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html>

	<script type="text/javascript" src="js/date_picker.js"></script>
	<script type="text/javascript">
	 	function inviaDati(v) {
			if (document.getElementById("selMotivo").value!=="ALTRO"){
		 		document.getElementById("motivoDis").value = document.getElementById("selMotivo").value;
			}else{
				strAltro = document.getElementById("motivoAltro").value;
				document.getElementById("motivoDis").value = strAltro.toUpperCase();
			}
	 		var f = v.form;
	 		f.target = window.opener;	
	 		f.submit();
	 		window.close();
	        
	    }

	    function controllaValoreDisabilita(){
	   		var elementoSel = document.getElementById("selMotivo");
		 	if (elementoSel){
			 	if (elementoSel.value=="ALTRO"){
			 		document.getElementById("motivoAltro").disabled=false;
				}else{
					document.getElementById("motivoAltro").value="";
					document.getElementById("motivoAltro").disabled=true;
				}
			}
		}
	</script>

	<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  	<link href="<%=request.getContextPath()%>/css/newstyle.css" rel="stylesheet" type="text/css" />
  	<title>AMProfiler - Disabilita Utente</title>
  	<%String pathWebApp = request.getContextPath(); %>
	</head>
	
	<body>	

		<div id="centrecontent"><!--centre content goes here --> 
			<form name="userForm" id="userForm" method="POST" target="" action="<%=pathWebApp%>/CancellaUtente?userName=<%=request.getAttribute("userName")%>" >
				
				<table width="100%" cellpadding="0" cellspacing="0" align="center" border="0" height="50" style="border-bottom:6px solid #4A75B5;">
		    		<tr><td><font size='5' color="#4A75B5" style="font-weight: bold;padding-left: 5px;">Disabilita Utente</font></td></tr>
		    	</table>
		    	<br><br>
				
		
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
		    <tr>
			      <td align="right">Motivo Disabilitazione:</td>
			      <td align="left">
			      		<select name="selMotivo" id="selMotivo" onchange="controllaValoreDisabilita();">
						  <option value="" selected="selected"></option>
						  <option value="CANCELLATO">CANCELLATO</option>
						  <option value="DISABILITATO">DISABILITATO</option>
						  <option value="SOSPESO">SOSPESO</option>
						  <option value="ALTRO">ALTRO</option>
						</select>
			     	    <input type="text" name="motivoAltro" id="motivoAltro" disabled="disabled"></td>
		    </tr>
         	<tr>
			      <td align="center" colspan="2"><input type="button" name="btnSalva" value="Salva" onclick="inviaDati(this);"></td>
		    </tr>
		    <input name="ripristina" type="hidden" value="<%=request.getParameter("ripristina") == null ? "" : request.getParameter("ripristina")%>"></input>
        	<input name="userName" type="hidden" value="<%=request.getParameter("userName") %>"></input>
        	<input type="hidden" name="motivoDis" id="motivoDis" value="">
			</form>
		</div>
	</body>
</html>
