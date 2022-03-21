<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="./css/newstyle.css" rel="stylesheet" type="text/css" />
<title>AMProfiler - Gestione Ruoli e Permessi</title>
<%@include  file="user.jsp"%>

</head>
<body>

	<p class="spacer">&nbsp;</p>
	<div id="clearheader"></div>
		<center>
			<div class="divTableContainerListaG">	
			<form action="CaricaPermessi" name="permessiForm" method="POST">
				<br><br>

				<c:if test="${appType == 'diogenedb'}">
					<img style="border:none;" src="img/title_Diogene.png" width="174" height="35"></img>
					<br/><br/>
				</c:if> 		
				<c:if test="${appType == 'rulengine'}">
					<img style="border:none;" src="img/title_RulEngine.png" width="155" height="35"></img>
                    <br/><br/>
				</c:if> 		
				<c:if test="${appType == 'caronte'}">
					<img style="border:none;" src="img/title_Caronte.png" width="124" height="32"></img>
                    <br/><br/>
				</c:if> 			
				<c:if test="${appType == 'diritti sui dati'}">
					<img style="border:none;" src="img/title_DirittiDati.png" width="212" height="32"></img>
          <br/><br/>
				</c:if> 			

      	<input type="hidden" name="application" value='<c:out value="${application}"/>' />
      	<input type="hidden" name="appType" value='<c:out value="${appType}"/>' />

<c:if test="${size >0}">
  <table  cellpadding="0" cellspacing="0" style="margin-top: 10px; margin-bottom: 15px;">
    <tr>
    	<td style="vertical-align: top">
    		<table width="250 px" class="griglia" border="0" cellpadding="0" cellspacing="0">
    			<tr class="header" style="height: 35px;"><td nowrap>Servizi</td></tr>
    				<c:forEach items="${servizi}" var="servizio" >
    					<tr style="height: 35px;">
    						<td style="padding: 0px; vertical-align: center; border-bottom: 1px solid #bebebe;"><c:out value="${servizio.name}" /></td>
    					</tr>
    				</c:forEach>
    		</table>
      </td> 

	<c:set var="i" value="0"/>
 	<c:forEach items="${checks}" var="check" >
		<c:set var="i" value="${i+1}"/>
		<c:if test="${i==1}">
			<td style="vertical-align: top">
				<table width="90 px" border="0" class="griglia"  cellpadding="0" cellspacing="0">
					<tr class="header" style="height: 35px;">
            <td nowrap align="center"><c:out value="${check.key}" /></td>
          </tr>
    </c:if>
    		<c:if test="${i!=1}">
    			<tr style="height: 35px;">
    				<td align="center" style="padding: 0px; vertical-align: center; border-bottom: 1px solid #bebebe;">
    					<c:if test="${check.value.checked==true}">
                <c:if test="${check.value.disabled==true}">
                  <input type="checkbox" value ="true" checked disabled="disabled" name="<c:out value="${check.key}" />"  />
                </c:if> 
                <c:if test="${check.value.disabled!=true}">
                  <input type="checkbox" value ="true" checked name="<c:out value="${check.key}" />"  />
                </c:if> 
    					</c:if>	
    					<c:if test="${check.value.checked!=true}">
                <c:if test="${check.value.disabled==true}">
                  <input type="checkbox" value="true" disabled="disabled" name="<c:out value="${check.key}" />"  />
                </c:if> 
                <c:if test="${check.value.disabled!=true}">
                  <input type="checkbox" value="true" name="<c:out value="${check.key}" />"  />
                </c:if> 
    					</c:if>	
    				</td>
    			</tr>
    		</c:if>	
 		<c:if test="${i==size+1}">
  		</table>
		</td> 
		<c:set var="i" value="0"/>
		</c:if>
	</c:forEach>
	</tr>

</table>
<input type="button" name="salva" value="Salva" onclick="document.permessiForm.action='SalvaServiziRuoli';document.permessiForm.submit()"/>
</c:if>
<input type="button" name="indietro" value="Indietro" onclick="location.href='../AMProfiler'"/>
</form>
		
			</div>	
		</center>  
		
	 <div id="header">
		  <img src="img/title_Catasto.png" width="259" height="32" />
		  <img src="img/gestione_servizi.png" width="448" height="24" />		  
	 </div>

</body>
</html>