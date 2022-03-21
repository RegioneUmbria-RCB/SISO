<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="./css/newstyle.css" rel="stylesheet" type="text/css" />
<title>AMProfiler - Gestione Ruoli e Permessi</title>
<%@include file="user.jsp"%>

</head>
<body>

<p class="spacer">&nbsp;</p>
<div id="clearheader"></div>
<center>
<div class="divTableContainerListaG">
<form action="CaricaPermessi" name="permessiForm" method="POST">
<br>
<br>

<c:if test="${appType == 'diogenedb'}">
	<img style="border: none;" src="img/title_Diogene.png" width="174"
		height="35"></img>
	<br />
	<br />
</c:if> <c:if test="${appType == 'rulengine'}">
	<img style="border: none;" src="img/title_RulEngine.png" width="155"
		height="35"></img>
	<br />
	<br />
</c:if> <c:if test="${appType == 'caronte'}">
	<img style="border: none;" src="img/title_Caronte.png" width="124"
		height="32"></img>
	<br />
	<br />
</c:if> <c:if test="${appType == 'diritti sui dati'}">
	<img style="border: none;" src="img/title_DirittiDati.png" width="212"
		height="32"></img>
	<br />
	<br />
</c:if> <input type="hidden" name="application"
	value='<c:out value="${application}"/>' /> <input type="hidden"
	name="appType" value='<c:out value="${appType}"/>' /> <!--
<p>
Applicazione <select NAME="application" onchange="document.permessiForm.item.value=''; document.permessiForm.submit()"  >
 <option value=""> -- Selezionere Applicazione --</option>
      <c:forEach items="${applications}" var="app">
          <c:if test="${application == app.name}">
	        <option selected>
	          <c:out value="${app.name}"/>
	        </option>
        </c:if>
        <c:if test="${application != app.name}">
	        <option >
	          <c:out value="${app.name}"/>
	        </option>
        </c:if>
      </c:forEach>
 </select>
</p>
-->
<p>Oggetti applicazione <select NAME="item"
	onchange="document.permessiForm.submit()">
	<option value="">-- Selezione Oggetto --</option>
	<c:forEach items="${items}" var="it">
		<c:if test="${item == it.name}">
			<option selected><c:out value="${it.name}" /></option>
		</c:if>
		<c:if test="${item != it.name}">
			<option><c:out value="${it.name}" /></option>
		</c:if>
	</c:forEach>
</select></p>

<p>
<table class="hidden" cellpadding="0" cellspacing="0">
	<tr>

		<td width="300px" style="vertical-align: top">
		<table class="griglia" cellpadding="0" cellspacing="0">
			<tr class="header">
				<td>ASSEGNAZIONE RUOLI A GRUPPO</td>
			</tr>
			<tr>
				<td>Selezionare il ruolo per visualizzare i gruppi assegnati
				relativamente all'oggetto selezionato:</td>
			</tr>
			<tr class="header">
				<td style="padding: 5px;">Gruppo</td>
			</tr>
			<tr>
				<td style="vertical-align: top"><select NAME="gruppi"
					style="width: 175px;" onchange="document.permessiForm.submit()">
					<c:forEach var="gruppo" items="${gruppi}">
						<c:if test="${gruppoScelto == gruppo.name}">
							<option selected value="<c:out value="${gruppo.name}" />">
							<c:out value="${gruppo.name}" /></option>
						</c:if>
						<c:if test="${gruppoScelto != gruppo.name}">
							<option value="<c:out value="${gruppo.name}" />"><c:out
								value="${gruppo.name}" /></option>
						</c:if>
					</c:forEach>
				</select></td>
			</tr>
			<tr class="header">
				<td style="padding: 5px;">Ruoli</td>
			</tr>
			<tr>
				<td style="vertical-align: top">
				
				<div style="overflow:scroll;height:120px;width:280px;">
					<c:forEach var="ruolo" items="${ruoliXgruppo}">
							<c:if test="${ruolo.checked == true}">
								<input type="checkbox" checked="checked" name="ruoliXgruppo" value="<c:out value="${ruolo.id}" />"/>
							</c:if>
							<c:if test="${ruolo.checked != true}">
								<input type="checkbox" name="ruoliXgruppo" value="<c:out value="${ruolo.id}" />"/>
							</c:if>
							<c:out value="${ruolo.amRoleS}" />
							<br />
					</c:forEach>
				</div>
				
				<br />
				<c:if test="${salvaRuoliGruppoAbilitato == true}">
					<input type="button" name="salvaRuoliGruppo" value="Salva"
						onclick="var answer = confirm('Confermare il salvataggio?'); if (answer) { document.permessiForm.action='SalvaRuoliGruppo';document.permessiForm.submit(); }" />
				</c:if> <c:if test="${salvaRuoliGruppoAbilitato == false}">
					<input type="button" name="salvaRuoliGruppo" value="Salva"
						disabled="disabled"
						onclick="var answer = confirm('Confermare il salvataggio?'); if (answer) { document.permessiForm.action='SalvaRuoliGruppo';document.permessiForm.submit(); }" />
				</c:if></td>
			</tr>
			<tr>
				<td style="text-align: center"></td>
			</tr>
		</table>
		</td>
		<td width="20px">&nbsp;</td>
		<td width="300px">
		<table class="griglia" cellpadding="0" cellspacing="0">
			<tr class="header">
				<td>ASSEGNAZIONE RUOLI UTENTE</td>
			</tr>
			<tr>
				<td>Selezionare i ruoli da assegnare ad un utente relativamente
				ad un oggetto:</td>
			</tr>
			<tr class="header">
				<td style="padding: 5px;">Utenti del gruppo</td>
			</tr>
			<tr>
				<td style="vertical-align: top">Gruppi<br />
				<select NAME=gruppi4utente style="width: 175px;"
					onchange="document.permessiForm.submit()">
					<option value="">-------</option>
					<c:forEach var="gruppo" items="${gruppi4utente}">
						<c:if test="${gruppo4utenteScelto == gruppo.name}">
							<option selected value="<c:out value="${gruppo.name}" />">
							<c:out value="${gruppo.name}" /></option>
						</c:if>
						<c:if test="${gruppo4utenteScelto != gruppo.name}">
							<option value="<c:out value="${gruppo.name}" />"><c:out
								value="${gruppo.name}" /></option>
						</c:if>
					</c:forEach>
				</select> <br />
				<br />
				Utenti<br />
				<select NAME="utentiXgruppo" style="width: 175px;"
					onchange="document.permessiForm.submit()">
					<c:forEach var="user" items="${utentiXgruppo}">
						<c:if test="${utenteScelto == user.name}">
							<option selected><c:out value="${user.name}" /></option>
						</c:if>
						<c:if test="${utenteScelto != user.name}">
							<option><c:out value="${user.name}" /></option>
						</c:if>
					</c:forEach>
				</select></td>
			</tr>
			<tr class="header">
				<td style="padding: 5px;">Assegna ruoli</td>
			</tr>
			<tr>
				<td style="vertical-align: top">
				
				<div style="overflow:scroll;height:120px;width:280px;">
					<c:forEach var="rule" items="${ruoliXutente}">
						<c:if test="${rule.checked == true}">
							<c:if test="${rule.grouped == true}">
								<input type="checkbox" disabled="disabled" checked="checked"
									name="ruoliXutente" value="<c:out value="${rule.amRoleS}" />"/>
							</c:if>
							<c:if test="${rule.grouped != true}">
								<input type="checkbox" checked="checked"
									name="ruoliXutente" value="<c:out value="${rule.amRoleS}" />"/>
							</c:if>
						</c:if>

						<c:if test="${rule.checked != true}">
							<c:if test="${rule.grouped == true}">
								<input type="checkbox" disabled="disabled"
									name="ruoliXutente" value="<c:out value="${rule.amRoleS}" />"/>
							</c:if>
							<c:if test="${rule.grouped != true}">
								<input type="checkbox"
									name="ruoliXutente" value="<c:out value="${rule.amRoleS}" />"/>
							</c:if>
						</c:if>
						<c:out value="${rule.amRoleS}" />
						<br />
					</c:forEach>
				</div>
				
				<br />
				I Ruoli (*) appartengono ad un gruppo e <br />
				non sono modificabili. <br />
				<br />
				<c:if test="${salvaRuoliUtenteAbilitato == false}">
					<input type="button" name="salvaRuoliUtente" value="Salva"
						disabled="disabled"
						onclick="var answer = confirm('Confermare il salvataggio?'); if (answer) { document.permessiForm.action='SalvaRuoliUtente';document.permessiForm.submit(); }" />
				</c:if> <c:if test="${salvaRuoliUtenteAbilitato == true}">
					<input type="button" name="salvaRuoliUtente" value="Salva"
						onclick="var answer = confirm('Confermare il salvataggio?'); if (answer) { document.permessiForm.action='SalvaRuoliUtente';document.permessiForm.submit(); }" />
				</c:if></td>
			</tr>
			<tr>
				<td style="text-align: center"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<br />

<table class="hidden" cellpadding="0" cellspacing="0">
	<tr>

		<td width="300px">
		<table class="griglia" cellpadding="0" cellspacing="0">
			<tr class="header">
				<td>ASSOCIAZIONE RUOLI</td>
			</tr>
			<tr>
				<td>Selezionare il ruolo/i per associarlo all'oggetto
				selezionato:</td>
			</tr>
			<tr class="header">
				<td style="padding: 5px;">Ruoli</td>
			</tr>
			<tr>
				<td style="vertical-align: top">Nuovo ruolo <input type="text"
					name="nuovoRuolo" id="nuovoRuolo" /> <br />
				<br />
				<input type="button" name="nuovoRuoloButton" value="Inserisci"
					onclick="if(document.getElementById('nuovoRuolo').value == ''){alert('Inserire nome nuovo ruolo')}else {document.permessiForm.action='NuovoRuolo';document.permessiForm.submit()}" />
				</td>
			</tr>
			<tr>
				<td style="vertical-align: top">
				
				<div style="overflow:scroll;height:120px;width:280px;">
					<c:forEach var="ruolo" items="${ruoli}">
							<c:if test="${ruolo.checked == true}">
								<input type="checkbox" checked="checked" name="ruoliXitem" value="<c:out value="${ruolo.name}" />"/>
							</c:if>
							<c:if test="${ruolo.checked != true}">
								<input type="checkbox" name="ruoliXitem" value="<c:out value="${ruolo.name}" />"/>
							</c:if>
							<c:out value="${ruolo.name}" />
							<br />
					</c:forEach>
				</div>

				<br />
				<input type="button" name="salvaRuoliItem" value="Salva"
					onclick="var answer = confirm('Confermare il salvataggio?'); if (answer) { document.permessiForm.action='SalvaRuoliItem';document.permessiForm.submit(); }" />
				</td>
			</tr>
		</table>
		</td>
		<td width="20px">&nbsp;</td>
		<td width="300px" style="vertical-align: top">

		<table class="griglia" cellpadding="0" cellspacing="0">
			<tr class="header">
				<td>VISUALIZZAZIONE UTENTI PER RUOLO</td>
			</tr>
			<tr>
				<td>Selezionare il ruolo per visualizzare gli utenti
				assegnatari relativamente all'oggetto selezionato:</td>
			</tr>
			<tr class="header">
				<td style="padding: 5px;">Ruolo</td>
			</tr>
			<tr>
				<td style="vertical-align: top"><select NAME=ruoli4utente
					style="width: 175px;" onchange="document.permessiForm.submit()">
					<c:forEach var="rule" items="${ruoli4utente}">
						<c:if test="${ruoli4utenteScelto == rule.amRoleS}">
							<option selected value="<c:out value="${rule.id}" />"><c:out
								value="${rule.amRoleS}" /></option>
						</c:if>
						<c:if test="${ruoli4utenteScelto != rule.amRoleS}">
							<option value="<c:out value="${rule.id}" />"><c:out
								value="${rule.amRoleS}" /></option>
						</c:if>
					</c:forEach>
				</select> <br />
				I Ruoli (*) appartengono ad un gruppo.</td>
			</tr>
			<tr class="header">
				<td style="padding: 5px;">Utenti</td>
			</tr>
			<tr>
				<td style="vertical-align: top"><c:forEach var="user"
					items="${utentiXruolo}">
					<c:out value="${user.name}, " />
				</c:forEach> <c:if test="${utentiXruoloSize == null || utentiXruoloSize == 0}">
          				&nbsp;
          			</c:if></td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<c:if test="${item!=null}">
	<br />
	<br />
	<font color="red"> <c:if test="${noNuovoPermesso}">
		<c:out value="${msgNuovoPermesso}"></c:out>
	</c:if> </font>
	<table class="griglia" border="0" cellpadding="0" cellspacing="0">
		<tr style="height: 35px;">
			<td style="width: 250px; padding: 5px;">Numero di permessi
			assegnabili: <c:out value="${permessiSize}" /></td>
			<td style="width: 350px; padding: 5px;">Nuovo Permesso <input
				type="text" name="nuovoPermesso" id="nuovoPermesso" /> <input
				type="button" name="nuovoPermessoButton" value="Inserisci"
				onclick="if(document.getElementById('nuovoPermesso').value == ''){alert('Inserire nome nuovo permesso')}else {document.permessiForm.action='NuovoPermesso';document.permessiForm.submit()}" />
			</td>
		</tr>
	</table>
</c:if>
</p>

<c:if test="${permessiSize >0}">
	<table cellpadding="0" cellspacing="0"
		style="margin-top: 10px; margin-bottom: 15px;">
		<tr>
			<td style="vertical-align: top">
			<table width="250 px" class="griglia" border="0" cellpadding="0"
				cellspacing="0">
				<tr class="header" style="height: 35px;">
					<td nowrap>Ruoli</td>
				</tr>
				<c:forEach items="${permessi}" var="perm">
					<tr style="height: 35px;">
						<td
							style="padding: 0px; vertical-align: center; border-bottom: 1px solid #bebebe;"><c:out
							value="${perm.permission}" /></td>
					</tr>
				</c:forEach>
			</table>
			</td>
			<c:set var="i" value="0" />
			<c:forEach items="${checks}" var="check">
				<c:set var="i" value="${i+1}" />
				<c:if test="${i==1}">
					<td style="vertical-align: top">
					<table width="90 px" border="0" class="griglia" cellpadding="0"
						cellspacing="0">
						<tr class="header" style="height: 35px;">
							<td nowrap align="center"><c:out value="${check.key}" /></td>
						</tr>
						</c:if>
						<c:if test="${i!=1}">
							<tr style="height: 35px;">
								<td align="center"
									style="padding: 0px; vertical-align: center; border-bottom: 1px solid #bebebe;">
								<c:if test="${check.value==true}">
									<input type="checkbox" value="true" checked
										name="<c:out value="${check.key}" />" />
								</c:if> <c:if test="${check.value!=true}">
									<input type="checkbox" value="true"
										name="<c:out value="${check.key}" />" />
								</c:if></td>
							</tr>
						</c:if>
						<c:if test="${i==permessiSize+1}">
					</table>
					</td>
					<c:set var="i" value="0" />
				</c:if>
			</c:forEach>
			</td>
		</tr>

	</table>
	<input type="button" name="salva" value="Salva"
		onclick="var answer = confirm('Confermare il salvataggio?'); if (answer) { document.permessiForm.action='SalvaPermessi';document.permessiForm.submit(); }" />
	<input type="submit" name="annulla" value="Annulla" />
</c:if> <input type="button" name="indietro" value="Indietro"
	onclick="location.href='../AMProfiler'" />
	<input name="ente" type="hidden" value="${ente}"></input>
	</form>

</div>
</center>

<div id="header"><img src="img/title_Catasto.png" width="259"
	height="32" /> <img src="img/gestione_permessi.png" width="448"
	height="24" /></div>




</body>
</html>